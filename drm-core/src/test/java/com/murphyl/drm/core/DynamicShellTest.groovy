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
        runtime.eval('app()')
    }

    void testDemo() {
        before()
        runtime.eval('''
            app({
                name = 'demo'
            })
        ''')
    }

    void testDemo2() {
        before()
        runtime.eval('''
            app({
                println('hello')
            })
        ''')
    }

    void testSpec() {
        before()
        runtime.eval('''
            app()
            spec({
                id = 'local'
            })
            ready()
        ''')
        after()
    }

    void testPlugIn() {
        before()
        runtime.eval('''
            app()
            use('cli', {
                id = 'local'
            })
            use('jdbc', {
                id = 'mysql'
            })
            ready()
            local.exec({
                mysql.read('select 1')
                mysql.write('select 1')
                mysql.read('select 1')
                mysql.write('select 1')
            })
        ''')
        after()
    }

}
