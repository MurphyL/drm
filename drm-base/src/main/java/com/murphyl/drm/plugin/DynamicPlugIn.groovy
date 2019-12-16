package com.murphyl.drm.plugin

import com.murphyl.drm.DynamicObject
import groovy.util.logging.Slf4j
import org.slf4j.Logger

/**
 * 依赖对象
 * @author murph*
 * @date 2019/9/17 9:30
 */
@Slf4j
abstract class DynamicPlugIn implements DynamicObject {

    final String type = '插件'

}
