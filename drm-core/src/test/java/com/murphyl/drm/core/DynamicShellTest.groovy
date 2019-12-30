package com.murphyl.drm.core

import groovy.test.GroovyTestCase
import org.junit.After
import org.junit.Before

/**
 *
 * @author murph*
 * @date 2019/9/17 9:30
 */
class DynamicShellTest extends GroovyTestCase {

    DynamicShell runtime

    @Before
    void before() {
        runtime = new DynamicShell()
    }

    void after() {
        Thread.currentThread().join(3000)
    }

    void testEmpty() {
        before()
        runtime.eval('createApp()')
    }

    void testDemo() {
        before()
        runtime.eval('''
            createApp({
                name = 'demo'
            })
        ''')
    }

    void testDemo2() {
        before()
        runtime.eval('''
            createApp({
                println('hello')
            })
        ''')
    }

    void testSpec() {
        before()
        runtime.eval('''
            createApp()
            use({
                id = 'local'
            })
            ready()
        ''')
        after()
    }

    void testPlugIn() {
        before()
        runtime.eval('''
            createApp()
            use({
                id = 'local'
                
                require('xx')
            })
            ready()
        ''')
        after()
    }

}
