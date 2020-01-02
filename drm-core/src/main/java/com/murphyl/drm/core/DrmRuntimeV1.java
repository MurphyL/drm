package com.murphyl.drm.core;

import groovy.lang.GroovyShell;
import io.vertx.core.AbstractVerticle;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * -
 *
 * @author luohao
 * @date 2020/1/2 19:14
 */
@Slf4j
public class DrmRuntimeV1 extends AbstractVerticle {

    @Override
    public void start() {
        log.info("创建DRM环境~");
        GroovyShell shell = new GroovyShell();
        try {
            evalInitScripts(shell);
            evalUserScripts(shell);
        } catch (Exception e) {
            log.error("加载脚本出错", e);
        }
        log.info("脚本已经执行完成！");
    }

    private void evalInitScripts(GroovyShell shell) {
        String bootstrap = "/scripts/Bootstrap.groovy";
        URL init = getClass().getResource(bootstrap);
        shell.evaluate(init.getFile(), "Bootstrap");
        log.info("初始化脚本已执行～");
    }

    private void evalUserScripts(GroovyShell shell) {
        shell.evaluate("app({ log.info('hello') })");
        log.info("用户脚本已执行～");
    }


}
