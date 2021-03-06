package com.murphyl.drm.spec

import com.murphyl.drm.core.DrmServer
import com.murphyl.drm.support.DrmDocument
import groovy.util.logging.Slf4j
import io.vertx.core.Vertx
import io.vertx.core.http.HttpMethod
import lombok.Setter

@Slf4j
class WebSpecific extends DynamicSpecific {

    @Setter
    private int port = 8080

    final String kind = 'web'

    String id = kind

    final String name = 'Web服务'

    private Set<String> rules = new HashSet<>()

    private String urlTemplate = '%s'

    WebSpecific() {
        def vertx = Vertx.vertx()
        def instance = new DrmServer(port)
        vertx.deployVerticle(instance)
    }

    @DrmDocument(usage = 'get(String url, Closure handler)', desc = "接收GET请求，参数可选")
    def get(String url = '', Closure closure = {}) {
        register(HttpMethod.GET, url, closure)
    }

    @DrmDocument(usage = 'post(String url, Closure handler)', desc = "接收POST请求，参数可选")
    def post(String url = '', Closure closure = {}) {
        register(HttpMethod.POST, url, closure)
    }

    @DrmDocument(usage = 'put(String url, Closure handler)', desc = "接收PUT请求，参数可选")
    def put(String url = '', Closure closure = {}) {
        register(HttpMethod.PUT, url, closure)
    }

    @DrmDocument(usage = 'delete(String url, Closure handler)', desc = "接收DELETE请求，参数可选")
    def delete(String url = '', Closure closure = {}) {
        register(HttpMethod.DELETE, url, closure)
    }

    @DrmDocument(usage = 'patch(String url, Closure handler)', desc = "接收PATCH请求，参数可选")
    def patch(String url = '', Closure closure = {}) {
        register(HttpMethod.PATCH, url, closure)
    }

    @Override
    void run() {
        Thread.currentThread().join()
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
        router.route(method, target).handler((routingContext) -> {
            def response = routingContext.response()
            response.end('hi')
        })
        log.debug("注册请求完成 - ${validMethod} ${path}")
    }

}
