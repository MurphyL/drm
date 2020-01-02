package com.murphyl.drm.core;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.Properties;

/**
 * 上下文
 *
 * @author luohao
 * @date 2020/1/2 11:23
 */
@Slf4j
public final class DrmContext extends AbstractVerticle {

    private final static Properties SPEC_RULE = new Properties();

    public static final void from() {
        VertxOptions options = new VertxOptions().setWorkerPoolSize(40);
        Vertx instance = Vertx.vertx(options);
        instance.deployVerticle("com.murphyl.drm.core.DrmContext", res -> {
            if (res.succeeded()) {
                log.info("上下文（{}）初始化完成！", res.result());
            } else {
                log.info("上下文初始化失败：{}", res.cause());
            }
        });
    }

    private void deployDynamicItems(Vertx instance) throws IOException {
        Enumeration<URL> rules = URLClassLoader.getSystemResources("drm.properties");
        while (rules.hasMoreElements()) {
            SPEC_RULE.load(rules.nextElement().openStream());
        }
        DeploymentOptions options = new DeploymentOptions().setWorker(true).setWorkerPoolName("drm");
        for (String key : SPEC_RULE.stringPropertyNames()) {
            instance.deployVerticle(SPEC_RULE.getProperty(key), options);
            log.info("{}（{}）已加载完成", key, SPEC_RULE.getProperty(key));
        }
    }

    @Override
    public void start() throws IOException {
        log.info("上下文初始化~");
        deployDynamicItems(vertx);
    }

}
