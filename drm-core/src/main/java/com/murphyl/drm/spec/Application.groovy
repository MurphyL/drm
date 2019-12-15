package com.murphyl.drm.spec


import groovy.util.logging.Slf4j

/**
 * 测试应用
 * @author murph*
 * @date 2019/9/17 9:30
 */
@Slf4j
abstract class Application {

    void afterPropertiesSet() {
        log.info("${name} - 应用初始化完成")
    }

    abstract String getName()

}
