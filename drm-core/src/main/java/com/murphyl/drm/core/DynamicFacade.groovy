package com.murphyl.drm.core

import com.murphyl.drm.plugin.DynamicPlugin
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
        log.info("使用默认配置运行${it.desc()}服务")
    }

    /**
     * 初始化 - App
     * @param closure
     * @return
     */
    def createApp(String type = 'web', Closure initClosure = appInitClosure) {
        def appInstance = DrmRuntime.createApp(type)
        initClosure.delegate = appInstance
        initClosure.resolveStrategy = Closure.DELEGATE_FIRST
        appInstance.with(false, initClosure)
        appInstance.afterInitialized()
        binding.setVariable('app', appInstance)
    }

    DynamicPlugin require(String moduleName = 'demo', Closure initClosure = {}) {
        Objects.requireNonNull(binding.getVariable('app'), '请首先执行createApp()')
        def instance = DrmRuntime.createPlugIn(moduleName)
        instance.with(initClosure)
        instance.afterInitialized()
        binding.setVariable(instance.id, instance)
    }

}
