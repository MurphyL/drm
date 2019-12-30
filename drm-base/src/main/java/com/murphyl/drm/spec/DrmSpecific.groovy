package com.murphyl.drm.spec

import com.murphyl.drm.DynamicObject
import groovy.util.logging.Slf4j

/**
 * 测试应用
 * @author murph*
 * @date 2019/9/17 9:30
 */
@Slf4j
abstract class DrmSpecific implements DynamicObject {

    String id

    final String type = '框架'

    /**
     * 服务框架就绪
     */
    abstract void ready()

}