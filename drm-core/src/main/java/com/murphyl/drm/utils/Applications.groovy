package com.murphyl.drm.utils

import com.murphyl.drm.spec.Application

enum Applications {

    web("com.murphyl.drm.spec.WebApplication")

    Applications(String target) {
        this.target = target
    }

    final String target;

    Application create() {
        Class type = Class.forName(target)
        return type.newInstance()
    }

}