package com.murphyl.drm.core

import groovy.util.logging.Slf4j

/**
 * 动态脚本 - 基类
 * @author murh*
 * @date 2019/9/17 9:30
 */
@Slf4j
abstract class DynamicScript extends Script {

    private Application instance

    private DynamicRuntime runtime

    def include(String path) {
        if (null == runtime) {
            this.runtime = binding.getVariable("runtime")
        }
        instance = new Application()
        instance.name = "include: ${path}"
        runtime.shell.evaluate(new File(path))
        log.info("Included script file - {}", path)
    }

    Application app(Closure closure) {
        instance = new Application()
        instance.with(closure)
        return instance
    }

    def get(String url, Closure closure) {
        instance.get(url, closure)
    }

}
