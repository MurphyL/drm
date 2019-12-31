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
        runtime.eval('app()')
    }

    void testWeb(){
        before()
        runtime.eval('app("web")')
    }

    void testInit(){
        before()
        runtime.eval('''
            app({})
            ready()
        ''')
    }

    void testInclude() {
        before()
        runtime.eval('''
            app()
            include("D:/a.groovy")
            include("D:/a.groovy")
        ''')
    }

    void testPlugin() {
        before()
        runtime.eval('''
            app('web', {
                name = '测试管理系统'
            })
            include("D:/a.groovy")
            include("D:/a.groovy")
        ''')
    }
}
