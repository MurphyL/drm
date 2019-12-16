package com.murphyl.drm.spec


import groovy.util.logging.Slf4j
import io.vertx.core.http.HttpMethod

@Slf4j
class WebApplication extends DynamicApplication {

    int port = 8080

    String name = 'WebApplication'

    private Set<String> rules = new HashSet<>()

    private String urlTemplate = '%s'

    def get(String url = '', Closure closure = {}) {
        register(HttpMethod.GET, url, closure)
    }

    def post(String url = '', Closure closure = {}) {
        register(HttpMethod.POST, url, closure)
    }

    def put(String url = '', Closure closure = {}) {
        register(HttpMethod.PUT, url, closure)
    }

    def delete(String url = '', Closure closure = {}) {
        register(HttpMethod.DELETE, url, closure)
    }

    def patch(String url = '', Closure closure = {}) {
        register(HttpMethod.PATCH, url, closure)
    }

    /**
     * 注册请求
     * @param method
     * @param url
     * @param closure
     * @return
     */
    def register(HttpMethod method, url, Closure closure) {
        String validMethod = method.name()
        String target = String.format(urlTemplate, url)
        String path = new URI(target).normalize().toString()
        def unique = "${validMethod} ${path}"
        if (rules.contains(unique)) {
            log.warn("重复注册请求 - ${unique}，将覆盖原有规则")
        }
        rules.add(unique)
        log.info("注册请求完成 - ${validMethod} ${path} ${closure.toString()}")
    }

}
