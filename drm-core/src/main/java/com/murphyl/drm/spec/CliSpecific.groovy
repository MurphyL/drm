package com.murphyl.drm.spec

import groovy.util.logging.Slf4j

/**
 * 命令行门面
 * @author murph*
 * @date 2019/9/17 9:30
 */
 @Slf4j
class CliSpecific extends DrmSpecific {

    String name = 'CLI'

    @Override
    void ready() {
        log.info("命令行框架启动 - ${id}")
    }
}
