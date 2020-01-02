package com.murphyl.drm.core;

import io.vertx.core.AbstractVerticle;
import lombok.extern.slf4j.Slf4j;

/**
 * -
 *
 * @author luohao
 * @date 2020/1/2 19:14
 */
@Slf4j
public class DrmRuntimeV1 extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        log.info("创建DRM环境~");
    }
}
