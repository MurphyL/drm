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
     * @param path
     * @return
     */
    def include(String path) {
        inject('load', { it.eval(new File(path)) })
        log.info("Included script file - {}", path)
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
        register('get', url, closure)
    }

    /**
     * 注册请求
     * @param method
     * @param url
     * @param closure
     */
    void register(String method, url, Closure closure) {
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
