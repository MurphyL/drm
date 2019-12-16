package com.murphyl.drm.core

import groovy.test.GroovyTestCase

/**
 * 运行时测试
 * @author murph*
 * @date 2019/9/17 9:30
 */
class WebShellTest extends GroovyTestCase {

    DynamicShell runtime

    void before() {
        runtime = new DynamicShell()
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

    void testInclude() {
        before()
        runtime.eval('''
            createApp('web', {
                port = 100
                name = '测试Web应用'
            })
            include("D:/a.groovy")
            include("D:/a.groovy")
        ''')
    }

    void testPlugin() {
        before()
        runtime.eval('''
            createApp('web', {
                port = 100
                name = '测试Web应用'
            })
            require('demo')
            include("D:/a.groovy")
            include("D:/a.groovy")
        ''')
    }
}
