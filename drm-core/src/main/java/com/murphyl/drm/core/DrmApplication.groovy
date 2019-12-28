package com.murphyl.drm.core

import com.murphyl.drm.DynamicObject
import com.murphyl.drm.spec.DrmSpec
import com.murphyl.drm.support.DrmRuntime
import groovy.util.logging.Slf4j

/**
 * 应用 - 容器
 * @author murph*
 * @date 2019/9/17 9:30
 */
@Slf4j
abstract class DrmApplication extends Script implements DynamicObject {

    final String type = '应用'

    final String id = 'app'

    String name

    private List<DrmSpec> facades

    def createApp(String appName = 'app', Closure initClosure = {}) {
        this.name = appName
        this.facades = new ArrayList<>()
        initClosure()
        afterInitialized()
        binding.setProperty(appName, this)
        log.info("容器${appName}初始化完成，上下文中识别为：${appName}！")
    }

    def require(String specType, Closure initClosure = {}) {
        DrmSpec spec = DrmRuntime.createSpec(specType)
        spec.with(initClosure)
        if (binding.hasProperty(spec.id)) {
            throw new IllegalArgumentException('重复的标记：应用、框架、插件的名称不能重复')
        }
        spec.afterInitialized()
        facades.add(spec)
        binding.setProperty(spec.id, spec)
        log.info("框架${specType}初始化完成！")
    }

    def ready() {
        if (facades.isEmpty()) {
            require('cli', { log.info('默认初始化为CLI应用') })
        }
        facades.eachWithIndex((DrmSpec service, int i) -> {
            service.ready()
        })
    }

}
