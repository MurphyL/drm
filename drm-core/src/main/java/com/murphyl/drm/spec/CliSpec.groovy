package com.murphyl.drm.spec

import groovy.util.logging.Slf4j

/**
 *
 * @author murph*
 * @date 2019/9/17 9:30
 */
 @Slf4j
class CliSpec extends DrmSpec {

    String name = 'CLI框架'

    @Override
    void ready() {
        log.info('命令行应用程序启动')
    }
}
