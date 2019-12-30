package com.murphyl.drm.spec


import com.murphyl.drm.core.DynamicObject
import groovy.util.logging.Slf4j

/**
 * 测试应用
 * @author murph*
 * @date 2019/9/17 9:30
 */
@Slf4j
abstract class DrmSpecific implements DynamicObject, Runnable {

    final String type = '框架'
    /**
     * 是否为孤儿框架，全局只能注册一次
     */
    boolean orphan = false
    /**
     * 全局标识
     * @return
     */
    abstract String getId()

    abstract String getName()

}
