package com.murphyl.drm.support

import com.murphyl.drm.plugin.DynamicPlugIn
import com.murphyl.drm.spec.DynamicApplication
import groovy.util.logging.Slf4j

@Slf4j
final class DrmRuntime {

    private final static Properties config = new Properties()

    static {
        def rules = URLClassLoader.getSystemResources("drm.properties")
        rules.each {
            config.load(it.openStream())
        }
    }

    static DynamicApplication createApp(String type) {
        def target = config.getProperty("spec_${type}")
        return Class.forName(target).newInstance()
    }

    static DynamicPlugIn createPlugIn(String type) {
        def target = config.getProperty("plug_${type}")
        return Class.forName(target).newInstance()
    }

}