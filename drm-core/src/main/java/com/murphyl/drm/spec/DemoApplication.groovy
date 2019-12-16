package com.murphyl.drm.spec

import com.murphyl.drm.support.DrmDocument
import groovy.util.logging.Slf4j

@Slf4j
class DemoApplication extends DynamicApplication {

    String name = 'DemoApplication'

    private List<Closure> items = new ArrayList<>()

    void run() {
        log.info("${desc()} - 开始执行")
        items.each { it.run() }
        log.info("${desc()} - 执行完成")
    }

    @DrmDocument(usage = "demo(String message)", desc = "接收一个参数，将其作为日志输出")
    void demo(String message) {
        this.items.add({ log.info(message) })
    }
}
