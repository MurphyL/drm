package com.murphyl.drm.core

import groovy.test.GroovyTestCase
import org.junit.Before

/**
 * 运行时测试
 * @author murph*
 * @date 2019/9/17 9:30
 */
class DynamicRuntimeTest extends GroovyTestCase {

    DynamicRuntime runtime

    void before() {
        runtime = new DynamicRuntime()
    }

    void testEmpty(){
        before()
        runtime.eval('createApp()')
    }

    void testWeb(){
        before()
        runtime.eval('createApp("web")')
    }

    void testInit(){
        before()
        runtime.eval('createApp("web", {})')
    }

    void testFull() {
        before()
        runtime.eval('''
            createApp('web', {
                port = 100
                name = '测试Web应用'
                path = '/test'
            }, {
                println('加载外部依赖')
            })
            include("/home/murph/Source/test/a.groovy")
            include("/home/murph/Source/test/a.groovy")
        ''')
    }

}
