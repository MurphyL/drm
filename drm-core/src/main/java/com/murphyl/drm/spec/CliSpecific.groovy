package com.murphyl.drm.spec

import groovy.util.logging.Slf4j

/**
 * 命令行门面
 * @author murph*
 * @date 2019/9/17 9:30
 */
@Slf4j
class CliSpecific extends DynamicSpecific implements OrphanSpecific {

    String id = 'cli'

    final String kind = 'cli'

    final String name = '通用'

    boolean orphan = true

    @Override
    void run() {
        log.info("命令行框架启动 - ${id}")
    }

    def exec(Closure closure) {
        def result = this.with(closure)
        log.info("执行结果：{}", result)
    }
}
