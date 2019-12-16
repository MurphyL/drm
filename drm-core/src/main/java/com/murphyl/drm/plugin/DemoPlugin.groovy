package com.murphyl.drm.plugin

import com.murphyl.drm.support.DrmDocument
import groovy.util.logging.Slf4j


/**
 * 演示插件
 * @author murph*
 * @date 2019/9/17 9:30
 */
@Slf4j
class DemoPlugin extends DynamicPlugin {

    String name = '演示插件'

    @DrmDocument(usage = "(String message)", desc = '接收一个参数，格式化后返回')
    def demo(String message) {
        return "${desc()} - 回传的消息： ${message}"
    }

}
