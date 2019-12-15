package com.murphyl.drm.core

import groovy.test.GroovyTestCase

/**
 * 运行时测试
 * @author murph*
 * @date 2019/9/17 9:30
 */
class DynamicRuntimeTest extends GroovyTestCase {

    void testWebApplication() {
        DynamicRuntime runtime = new DynamicRuntime()
        runtime.eval('''
            app('web', {
                port = 100
                name = '测试Web应用'
                path = '/test'
            }, {
                get '/join', {}
            })
            include("/home/murph/Source/test/a.groovy")
            include("/home/murph/Source/test/a.groovy")
        ''')
    }

}
