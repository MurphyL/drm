package com.murphyl.drm.core

import groovy.util.logging.Slf4j
import io.vertx.core.AbstractVerticle
import io.vertx.ext.web.Router

/**
 *
 * @author murph*
 * @date 2019/9/17 9:30
 */
@Slf4j
class DrmServer extends AbstractVerticle {

    private int port
    private Router router

    DrmServer(int port) {
        this.port = port
        this.router = router
    }

    void start() {
        def server = vertx.createHttpServer()
        server.requestHandler(router).listen(port)
        log.info('服务已启动 - {}', port)
    }

}
