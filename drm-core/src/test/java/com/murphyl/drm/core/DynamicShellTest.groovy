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
        ''')
    }

    void testDemo2() {
        before()
        runtime.eval('''
            createApp('demo', {
                println('hello')
            })
        ''')
    }

    void testPluginInit() {
        before()
        runtime.eval('''
            createApp('demo')
            require('cli', {
                id = 'xxx'
            })
            ready()
        ''')
    }

}