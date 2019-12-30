package com.murphyl.drm.core

import com.murphyl.drm.support.DrmDocument

/**
 *
 * @author murph*
 * @date 2019/9/17 9:30
 */
trait DynamicObject {

    default def help() {
        def document = new StringJoiner('\n')
        document.add("${this.class.simpleName}支持的命令：")
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