package com.murphyl.drm.core;

import com.murphyl.drm.spec.AbstractSpecific;
import com.murphyl.drm.utils.ConfigUtils;
import groovy.lang.Closure;
import groovy.lang.Script;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class DynamicScript extends Script {

    private AtomicInteger countApp = new AtomicInteger(0);

    private DeploymentOptions options = new DeploymentOptions().setWorker(true).setWorkerPoolName("spec");

    private Properties drmRules;

    protected static final Logger log = LoggerFactory.getLogger(DynamicScript.class);


    public String getKind() {
        return "DRM";
    }

    public void app(String specType) throws Exception {
        app(specType, null);
    }

    public void app(String specType, Closure closure) throws Exception {
        if (countApp.getAndIncrement() > 0) {
            log.error("App默认框架已经初始化完成，请勿重复初始化");
            return;
        }
        drmRules = ConfigUtils.loadClasspathProperties("drm.properties");
        loadSpec(specType, closure);
        log.info("使用【{}】框架构造APP~", specType);
    }

    public void spec(String specType, Closure closure) throws Exception {
        loadSpec(specType, closure);
    }

    public void include(String fileLocation) {
        Vertx instance = (Vertx) getProperty("vertx");
        instance.eventBus().publish("include_script", fileLocation);
        log.info("准备加载文件：{}", fileLocation);
    }

    private void loadSpec(String specType, Closure closure) throws Exception {
        String rule = drmRules.getProperty(specType);
        Vertx instance = (Vertx) getProperty("vertx");
        Object target = Class.forName(rule).newInstance();
        AbstractSpecific specific = (AbstractSpecific) target;
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.setDelegate(specific);
        closure.call();
        getBinding().setVariable(specific.getId(), specific);
        instance.deployVerticle(specific, options);
        log.info("框架【{}】加载完成：{}", specType, specific.getId());
    }

}
