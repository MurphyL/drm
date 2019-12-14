package com.murphyl.drm.core

import groovy.util.logging.Slf4j

/**
 * 动态脚本 - 基类
 * @author murh*
 * @date 2019/9/17 9:30
 */
@Slf4j
abstract class DynamicScript extends Script {

    /**
     * 嵌入 - 文件
     * TODO - 脚本重复载入的问题
     * @param path
     * @return
     */
    def include(String path) {
        inject('load', { it.eval(new File(path)) })
        log.info("脚本载入完成 - {}", path)
    }

    /**
     * 初始化 - App
     * @param closure
     * @return
     */
    def app(Closure closure) {
        inject('app', closure)
    }

    /**
     * GET - 请求
     * @param url
     * @param closure
     * @return
     */
    def get(String url, Closure closure) {
        request('get', url, closure)
    }

    /**
     * 注册请求
     * @param method - TODO
     * @param url
     * @param closure
     */
    void request(String method, url, Closure closure) {
        inject('app', { it.invokeMethod(method, [url, closure]) })
    }

    /**
     * 注入
     * @param key
     * @param closure
     */
    void inject(String key, Closure closure) {
        binding.getVariable(key).with(closure)
    }
}
