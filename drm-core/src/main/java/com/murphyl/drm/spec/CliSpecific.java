package com.murphyl.drm.spec;

import io.vertx.core.AbstractVerticle;
import lombok.extern.slf4j.Slf4j;

/**
 * 命令行框架
 *
 * @author luohao
 * @date 2020/1/3 8:39
 */
@Slf4j
public class CliSpecific extends AbstractSpecific {

    @Override
    public void start() {
        log.info("CMD环境已加载！");
    }

    protected void hello(String message) {
        log.info("{} - hello, {}", id, message);
    }
}
