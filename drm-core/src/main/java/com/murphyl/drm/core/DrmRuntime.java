package com.murphyl.drm.core;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * -
 *
 * @author luohao
 * @date 2020/1/2 19:14
 */
@Slf4j
public class DrmRuntime extends AbstractVerticle {

    @Override
    public void start() {
        CompilerConfiguration config = new CompilerConfiguration();
        config.setScriptBaseClass(DynamicScript.class.getName());
        Binding binding = new Binding();
        binding.setVariable("vertx", vertx);
        GroovyShell shell = new GroovyShell(binding, config);
        try {
            evalUserScripts(shell);
            log.info("用户自定义脚本已执行～");
        } catch (Throwable e) {
            log.error("用户自定义脚本执行出错：", e);
        }
    }

    private void evalUserScripts(GroovyShell shell) throws IOException {
        String scope = System.getProperty("drm.scope", "vm.options");
        String profile = System.getProperty("drm.profile");
        log.info("DRM Profile：{} - {}", scope, profile);
        EventBus bus = vertx.eventBus();
        bus.consumer("include_script", (Message<String> message) -> {
            String location = message.body();
            try {
                shell.evaluate(new File(location));
                log.info("载入脚本完成：{}", location);
            } catch (Exception e) {
                log.info("载入脚本失败：{}", location, e);
            }

        });
        shell.evaluate(new File(profile));
    }

}
