package com.murphyl.drm.support;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 插件 - 对外暴露的方法
 *
 * @author luohao
 * @date 2019/12/16 16:05
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DrmDocument {

    String version() default "0.0.1";

    String desc() default "暂无描述";
    String usage();

}
