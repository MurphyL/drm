package com.murphyl.drm.plugin

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

    default void selfCheck() {
        Objects.requireNonNull(id, "必须指定${desc()}的id")
    }

}
