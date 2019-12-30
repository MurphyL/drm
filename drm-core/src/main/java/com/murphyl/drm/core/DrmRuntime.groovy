package com.murphyl.drm.core

import com.murphyl.drm.DynamicObject
import com.murphyl.drm.spec.DrmSpecific
import groovy.util.logging.Slf4j

/**
 * 应用 - 容器
 * @author murph*
 * @date 2019/9/17 9:30
 */
@Slf4j
abstract class DrmRuntime extends Script implements DynamicObject {

    final String type = '应用'

    final String id = 'app'

    String name

    private List<DrmSpecific> facades

    private final static Properties SPEC_RULES = new Properties()

    static {
        def rules = URLClassLoader.getSystemResources("drm.properties")
        rules.each {
            SPEC_RULES.load(it.openStream())
        }
    }

    def createApp(String appName = 'drm', Closure initClosure = {}) {
        this.name = appName
        this.facades = new ArrayList<>()
        initClosure()
        afterInitialized()
        binding.setProperty(id, this)
        log.info("${appName}容器初始化完成，上下文中识别为：${id}")
    }

    def require(String specType, Closure initClosure = {}) {
        DrmSpecific spec = create('spec', specType)
        spec.with(initClosure)
        String specId = spec.id
        if (binding.hasProperty(specId)) {
            throw new IllegalArgumentException('重复的标记：应用、框架、插件的名称不能重复')
        }
        spec.afterInitialized()
        facades.add(spec)
        binding.setProperty(specId, spec)
        log.info("${spec.name}框架初始化完成，上下文中识别为：${specId}")
    }

    def ready() {
        if (facades.isEmpty()) {
            require('cli', { log.info('默认初始化为CLI应用') })
        }
        facades.eachWithIndex((DrmSpecific service, int i) -> {
            service.ready()
        })
        log.info("容器（id=${id}、name=${name}）准备就绪")
    }
    /**
     * 本地工具方法
     *
     * @param kind
     * @param name
     * @return
     */
    private <T> T create(String kind, String name) {
        def target = SPEC_RULES.getProperty("${kind}_${name}")
        return Class.forName(target).newInstance()
    }

}
