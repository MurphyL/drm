package com.murphyl.drm.plug


import com.murphyl.drm.core.DynamicObject
import groovy.util.logging.Slf4j

/**
 * 插件
 * @author murph*
 * @date 2019/9/17 9:30
 */
@Slf4j
abstract class DynamicPlugin implements DynamicObject {

    final String type = '插件'

}
