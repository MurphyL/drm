package com.murphyl.drm

import com.murphyl.drm.core.DrmContext
import groovy.test.GroovyTestCase

class DrmTest extends GroovyTestCase {

    void testDrm() {
        DrmContext.from()
        Thread.currentThread().join()
    }

}
