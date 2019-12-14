package com.murphyl.drm.core

import groovy.util.logging.Slf4j

/**
 * 测试应用
 * @author murph*
 * @date 2019/9/17 9:30
 */
@Slf4j
class Application {

    int port = 8080

    String name = "Application"

    def get(String url, Closure closure) {
        register("GET", url, closure)
    }

    /**
     * TODO 请求重复载入的问题
     * @param method
     * @param url
     * @param closure
     * @return
     */
    def register(String method, url, Closure closure){
        log.info("注册请求 - ${method.toUpperCase()} ${url} ${closure.toString()}")
    }

}
