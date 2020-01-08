package com.murphyl.drm

import com.murphyl.drm.core.DrmContext
import groovy.test.GroovyTestCase

class DrmTest extends GroovyTestCase {

    String getLocation(String path) {
        return getClass().getResource(path).getFile()
    }

    void setProfile(String path) {
        String target = getLocation(path)
        System.setProperty('drm.profile', target)
        println('Use profile: ' + target)
    }

    void testDrm() {
        setProfile('/scripts/test.groovy')
        DrmContext.from()
        Thread.currentThread().join()
    }

}
