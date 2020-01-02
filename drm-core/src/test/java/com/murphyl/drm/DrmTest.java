package com.murphyl.drm;

import com.murphyl.drm.core.DrmContext;
import io.vertx.core.*;
import io.vertx.core.json.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * -
 *
 * @author luohao
 * @date 2020/1/2 18:04
 */
@Slf4j
public class DrmTest {

    public static void main(String[] args) {
        Vertx instance = Vertx.vertx(new VertxOptions().setWorkerPoolSize(40));
        Context context = instance.getOrCreateContext();
        context.put("app", "app");
        JsonObject config = new JsonObject().put("name", "tim");
        DeploymentOptions options = new DeploymentOptions().setWorker(true).setConfig(config);
        instance.deployVerticle("com.murphyl.drm.core.DrmContext", options, res -> {
            if (res.succeeded()) {
                log.info("主线程ID：" + res.result());
            } else {
                log.info("服务启动失败");
            }
        });
    }

    @Test
    public void testFrom() throws IOException, InterruptedException {
        DrmContext.from();
        Thread.currentThread().join();
    }


}