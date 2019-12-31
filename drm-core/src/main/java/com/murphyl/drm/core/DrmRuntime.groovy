package com.murphyl.drm.core

import com.google.common.collect.HashBasedTable
import com.murphyl.drm.plug.DynamicPlugin
import com.murphyl.drm.spec.DynamicSpecific
import groovy.util.logging.Slf4j

/**
 * 应用 - 容器
 * @author murph*
 * @date 2019/9/17 9:30
 */
@Slf4j
abstract class DrmRuntime extends Script {

    private final String[] keywords = ['drm', 'spec', 'app']

    private final static String DEFAULT_SPEC_TYPE = 'cli'
    private final static Closure DEFAULT_SPEC_INIT = {
        log.info('使用默认配置初始化主框架')
    }

    private Map<String, DynamicPlugin> dynamicPlug;
    private HashBasedTable<String, String, Object> dynamicItems;

    private final static Properties DRM_RULES = new Properties()

    static {
        def rules = URLClassLoader.getSystemResources("drm.properties")
        rules.each { DRM_RULES.load(it.openStream()) }
    }

    def app(Closure closure = DEFAULT_SPEC_INIT) {
        app(DEFAULT_SPEC_TYPE, closure)
    }

    def app(String defaultSpec, Closure initClosure = DEFAULT_SPEC_INIT) {
        log.info("使用${defaultSpec}框架初始化容器")
        this.dynamicPlug = new Hashtable<>()
        this.dynamicItems = HashBasedTable.create()
        def app = use(defaultSpec, initClosure)
        binding.setVariable('app', app)
        binding.setVariable('ready', true)
        log.info("应用容器初始化完成，主框架：app、${app.id}")
    }

    def use(String useType, Closure closure) {
        // 获取类型
        def targetClass = DRM_RULES.getProperty(useType)
        // 构造
        DynamicObject dynamic = Class.forName(targetClass).newInstance()
        // 验证孤儿组件
        if (dynamic.orphan && dynamicItems.column(dynamic.kind)) {
            throw new IllegalStateException("${useType}${dynamic.type}只能注册一次")
        }
        // 初始化
        dynamic.with(closure)
        // 验证关键字不能被使用
        if (keywords.contains(dynamic.id)) {
            throw new IllegalArgumentException("不能使用保留字（${dynamic.id}）作为动态对象的ID")
        }
        // 验证全局标记唯一
        if (dynamicItems.containsColumn(dynamic.id)) {
            throw new IllegalArgumentException("动态对象的ID（${dynamic.id}）必须全局唯一")
        }
        // 分类型初始化
        if (dynamic instanceof DynamicSpecific) {
            useSpec(dynamic)
        } else if (dynamic instanceof DynamicPlugin) {
            usePlug(dynamic)
        }
        log.info("${dynamic.name}${dynamic.type}初始化完成，上下文中识别为：${dynamic.id}")
        return dynamic
    }

    def ready() {
        Map<String, Object> specItems = dynamicItems.row('spec')
        def plugins = dynamicPlug
        specItems.each {
            DynamicSpecific spec = binding.getProperty(it.value)
            plugins.each((plug) -> {
                spec.metaClass[plug.key] = plug.value
            })
            spec.run()
        }
        log.info("容器准备就绪")
    }

    private void useSpec(DynamicSpecific spec) {
        dynamicItems.put('spec', spec.kind, spec.id)
        binding.setVariable(spec.id, spec)
    }

    private void usePlug(DynamicPlugin plug) {
        dynamicItems.put('plug', plug.kind, plug.id)
        dynamicPlug.put(plug.id, plug)
    }

}
