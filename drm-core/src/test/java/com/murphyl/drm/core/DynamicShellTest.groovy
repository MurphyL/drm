package com.murphyl.drm.core

import groovy.test.GroovyTestCase

/**
 *
 * @author murph*
 * @date 2019/9/17 9:30
 */
class DynamicShellTest extends GroovyTestCase {

    DynamicShell runtime

    void before() {
        runtime = new DynamicShell()
    }

    void testEmpty() {
        before()
        runtime.eval('createApp()')
    }

    void testDemo() {
        before()
        runtime.eval('''
            createApp('demo')
            require('demo')
        ''')
    }

    void testPluginInit() {
        before()
        runtime.eval('''
            createApp('demo')
            require('demo', {
                id = 'xxx'
            })
            println(app.help())
            println(xxx.help())
            app.with {
                demo('hello')
                demo(xxx.demo('测试插件'))
            }
        ''')
        runtime.eval('app.ready()')
    }

}
