package com.murphyl.drm.core;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DrmApplication {

    public DrmApplication() {
        log.info("应用已创建完毕");
    }

    public void hello() {
        log.info("hello, script!");
    }
}
