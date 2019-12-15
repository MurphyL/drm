package com.murphyl.drm.core

import groovy.util.logging.Slf4j
import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.ImportCustomizer

import java.nio.file.Path
import java.nio.file.Paths

/**
 * 动态运行时
 * @author murph*
 * @date 2019/9/17 9:30
 */
@Slf4j
class DynamicRuntime {

    def defaultCharset = 'UTF-8'

    private GroovyShell shell

    private Set<String> includeItems

    DynamicRuntime() {
        def config = new CompilerConfiguration()
        config.setSourceEncoding(defaultCharset)
        config.setScriptBaseClass(DynamicScript.class.name)
        def icz = new ImportCustomizer()
        config.addCompilationCustomizers(icz)
        def binding = new Binding(["include": include])
        shell = new GroovyShell(binding, config)
        includeItems = new HashSet<>()
    }

    def include = (String path) -> {
        log.info("正在准备脚本 - ${path}")
        Path target = Paths.get(path).normalize()
        if (includeItems.contains(target.toString())) {
            log.warn("重复加载脚本 - ${path}（文件绝对路径：${target.toString()}）")
            log.info("放弃加载脚本 - ${path}")
            return false
        }
        eval(target.toFile())
        includeItems.add(target.toString())
        log.info("载入脚本完毕 - ${path}")
    }

    /**
     * 执行脚本
     * @param source
     */
    def eval(String source) {
        shell.evaluate(source, "Main.groovy")
    }

    def eval(File file) {
        Objects.requireNonNull(file, "加载数据文件不能为空")
        shell.evaluate(file)
    }

}
