package com.murphyl.drm.core;

import com.murphyl.drm.utils.ConfigUtils;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Properties;

/**
 * 上下文
 *
 * @author luohao
 * @date 2020/1/2 11:23
 */
@Slf4j
public final class DrmContext extends AbstractVerticle {

    public void ready(){
        vertx.deployVerticle("com.murphyl.drm.core.DrmContext", res -> {
            if (res.succeeded()) {
                log.info("上下文（{}）初始化完成！", res.result());
            } else {
                log.info("上下文初始化失败：{}", res.cause());
            }
        });
    }

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
        Properties items = ConfigUtils.loadClasspathProperties("act.properties");
        DeploymentOptions options = new DeploymentOptions().setWorker(true).setWorkerPoolName("drm");
        for (String key : items.stringPropertyNames()) {
            instance.deployVerticle(items.getProperty(key), options);
            log.info("加载{}（{}）完成", key, items.getProperty(key));
        }
    }

    @Override
    public void start() throws IOException {
        log.info("上下文初始化~");
        deployDynamicItems(vertx);
    }

}
