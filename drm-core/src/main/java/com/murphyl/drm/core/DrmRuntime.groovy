package com.murphyl.drm.core

import com.murphyl.drm.plug.DynamicPlugin
import com.murphyl.drm.spec.DynamicSpecific
import groovy.util.logging.Slf4j

import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

/**
 * 应用 - 容器
 * @author murph*
 * @date 2019/9/17 9:30
 */
@Slf4j
abstract class DrmRuntime extends Script {

    private final String[] keywords = ['drm', 'spec', 'app']

    final String id = 'app'

    private Set<String> loadedDynamicIds

    private Set<String> loadedSpecType

    private Set<String> loadedPlugType

    private final static Properties SPEC_RULES = new Properties()

    static {
        def specRules = URLClassLoader.getSystemResources("drm_spec.properties")
        specRules.each { SPEC_RULES.load(it.openStream()) }
        def plugRules = URLClassLoader.getSystemResources("drm_spec.properties")
        plugRules.each { SPEC_RULES.load(it.openStream()) }
    }

    def app(Closure initClosure = {}) {
        this.loadedDynamicIds = new HashSet<>()
        this.loadedSpecType = new HashSet<>()
        binding.setVariable(id, this)
        log.info("应用容器初始化完成，上下文中识别为：${id}")
    }

    def use(String useType = 'common', Closure closure) {
        // 获取类型
        def targetClass = SPEC_RULES.getProperty(useType)
        // 构造
        DynamicObject dynamic = Class.forName(targetClass).newInstance()
        // 验证孤儿组件
        if (dynamic.orphan && loadedSpecType.contains(useType)) {
            throw new IllegalStateException("${useType}${dynamic.type}只能注册一次")
        }
        // 初始化
        dynamic.with(closure)
        // 验证关键字不能被使用
        if (keywords.contains(dynamic.id)) {
            throw new IllegalArgumentException("不能使用保留字（${dynamic.id}）作为动态对象的ID")
        }
        // 验证全局标记唯一
        if (loadedDynamicIds.contains(dynamic.id)) {
            throw new IllegalArgumentException("动态对象的ID（${dynamic.id}）必须全局唯一")
        }
        // 分类型初始化
        if (dynamic instanceof DynamicSpecific) {
            useSpec(dynamic)
        } else if (dynamic instanceof DynamicPlugin) {
            usePlug(dynamic)
        }
        this.loadedDynamicIds.add(dynamic.id)
        log.info("${dynamic.name}${dynamic.type}初始化完成，上下文中识别为：${dynamic.id}")
    }

    def ready() {
        ThreadGroup group = new ThreadGroup("specific")
        AtomicInteger counter = new AtomicInteger(0)
        ThreadFactory factory = (Runnable specInstance) -> {
            return new Thread(group, specInstance, "specific-${counter.incrementAndGet()}")
        }
        LinkedBlockingDeque queue = new LinkedBlockingDeque()
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 15, 2, TimeUnit.MINUTES, queue, factory)
        if (loadedDynamicIds.isEmpty()) {
            use('common', { log.warn('使用默认配置运行服务') })
        }
        loadedDynamicIds.each { executor.submit(binding.getVariable(it)) }
        log.info("容器（id=${id}）准备就绪")
    }

    private void useSpec(DynamicSpecific spec) {
        loadedSpecType.add(spec.kind)
        binding.setVariable(spec.id, spec)
    }

    private void usePlug(DynamicPlugin spec) {
        loadedPlugType.add(spec.kind)
        binding.setVariable(spec.id, spec)
    }

}
