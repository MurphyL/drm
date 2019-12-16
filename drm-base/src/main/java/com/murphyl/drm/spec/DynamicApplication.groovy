package com.murphyl.drm.spec


import com.murphyl.drm.DynamicObject
import com.murphyl.drm.support.DrmDocument
import groovy.util.logging.Slf4j

/**
 * 测试应用
 * @author murph*
 * @date 2019/9/17 9:30
 */
@Slf4j
abstract class DynamicApplication implements DynamicObject, Runnable {

    final String type = '应用'

    final String id = 'app'

    @DrmDocument(usage = "ready()", desc = '准备就绪，启动应用程序')
    void ready() {
        this.run()
    }

}
