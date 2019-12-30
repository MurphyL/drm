package com.murphyl.drm.spec

import groovy.util.logging.Slf4j

/**
 * 命令行门面
 * @author murph*
 * @date 2019/9/17 9:30
 */
@Slf4j
class CommonSpecific extends DynamicSpecific implements OrphanSpecific {

    String id = 'common'

    final String kind = 'common'

    final String name = '通用'

    boolean orphan = true

    @Override
    void run() {
        log.info("命令行框架启动 - ${id}")
    }
}
