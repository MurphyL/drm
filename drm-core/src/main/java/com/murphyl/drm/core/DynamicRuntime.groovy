package com.murphyl.drm.core

import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.ImportCustomizer

/**
 * 动态运行时
 * @author murph*
 * @date 2019/9/17 9:30
 */
class DynamicRuntime {

    def defaultCharset = 'UTF-8'

    GroovyShell shell

    DynamicRuntime() {
        def config = new CompilerConfiguration()
        config.scriptBaseClass = DynamicScript.class.name
        def icz = new ImportCustomizer()
        config.addCompilationCustomizers(icz)
        def binding = new Binding(["runtime": this])
        shell = new GroovyShell(binding, config)
    }
/**
 * 执行脚本
 * @param source
 */
    def eval(String source) {
        shell.evaluate(source, "Main.groovy")
    }

    def eval(File file) {
        eval(file, defaultCharset)
    }

    def eval(File file, String charset) {
        Objects.requireNonNull(file, "加载数据文件不能为空")
        eval(file.getText(charset))
    }

}
