package com.murphyl.drm.spec


import groovy.util.logging.Slf4j

import java.nio.file.Paths

@Slf4j
class WebApplication extends Application {

    int port = 8080

    String name = 'WebApplication'

    private Set<String> rules = new HashSet<>()

    private String urlPrefix = ''
    private String urlSuffix = ''

    def setPath(def path) {
        if (path instanceof Map) {
            if (path.prefix) {
                urlPrefix = path.prefix
            }
            if (path.suffix) {
                urlSuffix = path.suffix
            }
        } else {
            urlPrefix = String.cast(path)
        }
    }

    def get(String url = '', Closure closure = {}) {
        register("GET", url, closure)
    }

    /**
     * 注册请求
     * @param method
     * @param url
     * @param closure
     * @return
     */
    def register(String method, url, Closure closure) {
        String validMethod = method.toUpperCase()
        String path = Paths.get('/', urlPrefix, '/', url, urlSuffix).normalize().toString()
        def unique = "${validMethod} ${path}"
        if (rules.contains(unique)) {
            log.warn("重复注册请求 - ${unique}，将覆盖原有规则")
        }
        rules.add(unique)
        log.info("注册请求完成 - ${validMethod} ${path} ${closure.toString()}")
    }

}
