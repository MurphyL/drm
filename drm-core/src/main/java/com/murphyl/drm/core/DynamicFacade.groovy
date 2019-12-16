package com.murphyl.drm.core

import com.murphyl.drm.plugin.DemoPlugIn
import com.murphyl.drm.plugin.DynamicPlugIn
import com.murphyl.drm.support.DrmRuntime
import groovy.util.logging.Slf4j

/**
 * DSL - 门面
 * @author murh*
 * @date 2019/9/17 9:30
 */
@Slf4j
abstract class DynamicFacade extends Script {

    private Closure appInitClosure = {
        log.info('使用默认配置运行服务')
    }

    /**
     * 初始化 - App
     * @param closure
     * @return
     */
    def createApp(String type = 'web', Closure initClosure = appInitClosure) {
        def appInstance = DrmRuntime.createApp(type)
        appInstance.with(false, initClosure)
        appInstance.afterInitialized()
        binding.setVariable("app", appInstance)
    }

    DynamicPlugIn require(String moduleName = 'demo', Closure initClosure = {}) {
        def instance = DrmRuntime.createPlugIn(moduleName)
        instance.with(initClosure)
        instance.afterInitialized()
        return instance
    }

    def name() {}

}
