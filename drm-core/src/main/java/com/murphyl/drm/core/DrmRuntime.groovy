package com.murphyl.drm.core

import com.murphyl.drm.spec.DrmSpecific
import groovy.util.logging.Slf4j
import org.jetbrains.annotations.NotNull

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
abstract class DrmRuntime extends Script implements DynamicObject {

    private final String[] keywords = ['drm', 'spec', 'app']

    final String id = 'app'

    private Set<String> loadedSpec

    private Set<String> facades

    private final static Properties SPEC_RULES = new Properties()

    static {
        def rules = URLClassLoader.getSystemResources("drm_spec.properties")
        rules.each { SPEC_RULES.load(it.openStream()) }
    }

    def createApp(Closure initClosure = {}) {
        this.loadedSpec = new HashSet<>()
        this.facades = new HashSet<>()
        binding.setVariable(id, this)
        log.info("应用容器初始化完成，上下文中识别为：${id}")
    }

    def require(String specType = 'common', Closure closure) {
        def targetClass = SPEC_RULES.getProperty(specType)
        DrmSpecific spec = Class.forName(targetClass).newInstance()
        if (spec.orphan && loadedSpec.contains(specType)) {
            throw new IllegalStateException("${specType}${spec.type}只能注册一次")
        }
        spec.with(closure)
        String specId = spec.id
        if (keywords.contains(specId)) {
            throw new IllegalArgumentException("不能使用保留字（${specId}）作为动态对象的ID")
        }
        if (facades.contains(specId)) {
            throw new IllegalArgumentException("动态对象的ID（${specId}）必须全局唯一")
        }
        binding.setVariable(specId, spec)
        this.facades.add(specId)
        this.loadedSpec.add(specType)
        log.info("${spec.name}${spec.type}初始化完成，上下文中识别为：${specId}")
    }

    def ready() {
        ThreadGroup group = new ThreadGroup("specific")
        AtomicInteger counter = new AtomicInteger(0)
        ThreadFactory factory = (Runnable specInstance) -> {
            return new Thread(group, specInstance, "specific-${counter.incrementAndGet()}")
        }
        LinkedBlockingDeque queue = new LinkedBlockingDeque()
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 15, 2, TimeUnit.MINUTES, queue, factory)
        if (facades.isEmpty()) {
            require('common')
        }
        facades.each { executor.submit(binding.getVariable(it)) }
        log.info("容器（id=${id}）准备就绪")
    }

}
