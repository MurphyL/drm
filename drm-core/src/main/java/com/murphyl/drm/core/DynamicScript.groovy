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

    /**
     * 初始化 - App
     * @param closure
     * @return
     */
    def app(String type = 'web', Closure initClosure, logicClosure = {}) {
        def appInstance = Applications.valueOf(type).create()
        appInstance.with(false, initClosure)
        appInstance.afterPropertiesSet()
        appInstance.with(false, logicClosure)
        binding.setVariable("app", appInstance)
    }

}
