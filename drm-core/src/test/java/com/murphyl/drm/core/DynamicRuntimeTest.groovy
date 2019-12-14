package com.murphyl.drm.core

import groovy.test.GroovyTestCase

/**
 * 运行时测试
 * @author murph*
 * @date 2019/9/17 9:30
 */
class DynamicRuntimeTest extends GroovyTestCase {

    void testCreate() {
        DynamicRuntime runtime = new DynamicRuntime()
        runtime.eval('''
            app {
                port = 100
            }
            get "/hello", { 
                println("test, hello") 
            }
            include("/home/murph/Source/test/a.groovy")
            include("/home/murph/Source/test/a.groovy")
            include("/home/murph/Source/test/a.groovy")
        ''')
    }

}
