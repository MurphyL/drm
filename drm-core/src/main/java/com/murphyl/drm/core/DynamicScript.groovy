package com.murphyl.drm.core

import com.murphyl.drm.utils.Applications
import groovy.util.logging.Slf4j

/**
 * 动态脚本 - 基类
 * @author murh*
 * @date 2019/9/17 9:30
 */
@Slf4j
abstract class DynamicScript extends Script {

    private Closure setting = {
        log.info('使用默认配置启动服务')
    }

    private Closure require = {
        log.info('无特定依赖模块')
    }

    /**
     * 初始化 - App
     * @param closure
     * @return
     */
    def createApp(String type = 'web', Closure initClosure = setting, requireClosure = require) {
        def appInstance = Applications.valueOf(type).create()
        appInstance.with(false, initClosure)
        appInstance.afterInitialized()
        binding.setVariable("app", appInstance)
        appInstance.with(false, requireClosure)
    }

}
