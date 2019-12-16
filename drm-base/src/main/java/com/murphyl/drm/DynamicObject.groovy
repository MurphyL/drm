package com.murphyl.drm

import ch.qos.logback.classic.Logger
import groovy.util.logging.Slf4j
import org.slf4j.LoggerFactory

/**
 * DRM - 基础对象
 * @author murph*
 * @date 2019/9/17 9:30
 */
interface DynamicObject {

    String getName()

    String getType()

    default void afterInitialized() {
        Logger logger = LoggerFactory.getLogger(this.class)
        logger.info("${name} -  ${type}初始化完成！")
    }

}