package com.murphyl.drm

import com.murphyl.drm.support.DrmDocument
import org.slf4j.Logger


/**
 * DRM - 基础对象
 * @author murph*
 * @date 2019/9/17 9:30
 */
interface DynamicObject {

    /**
     * 通用描述，便于运行时的对象识别
     * @return
     */
    String getId()

    /**
     * 动态类型对象分类
     * @return
     */
    String getType()

    /**
     * 通用描述，便于DSL开发时的对象识别
     * @return
     */
    String getName()

    /**
     * 通用描述，便于框架开发时的对象识别
     * @return
     */
    final String sign = "${type}（${this.class.simpleName}）"

    default String desc() {
        return "${name}（${id}）"
    }

    /**
     * 初始化完成
     */
    default void afterInitialized() {
    }

    default def help() {
        def document = new StringJoiner('\n')
        document.add("${sign}支持的命令：")
        Set<String> methods = new TreeSet<>()
        this.class.getMethods().each {
            if (!it.isAnnotationPresent(DrmDocument)) {
                return
            }
            DrmDocument cmd = it.getAnnotation(DrmDocument)
            methods.add(" - ${cmd.usage()}：${cmd.desc()}")
        }
        return document.add(methods.join('\n')).toString()
    }

}