package com.murphyl.drm.core

/**
 * 测试应用
 * @author murph*
 * @date 2019/9/17 9:30
 */
class Application {

    int port = 8080

    String name = "main"

    def get(String url, Closure closure) {
        register("GET", url, closure)
    }

    def register(String method, url, Closure closure){
        println("${method} ${url} - ${closure()}")
    }

}
