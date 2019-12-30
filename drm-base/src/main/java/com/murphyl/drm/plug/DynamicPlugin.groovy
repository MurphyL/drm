package com.murphyl.drm.plug

import com.murphyl.drm.DynamicObject
import groovy.util.logging.Slf4j

/**
 * 依赖对象
 * @author murph*
 * @date 2019/9/17 9:30
 */
@Slf4j
abstract class DynamicPlugin implements DynamicObject {

    String id

    final String type = '插件'

}
