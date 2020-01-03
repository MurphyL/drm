package com.murphyl.drm.spec;

import io.vertx.core.AbstractVerticle;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * -
 *
 * @author luohao
 * @date 2020/1/3 14:39
 */
@Slf4j
public class AbstractSpecific extends AbstractVerticle {

    @Getter
    protected String id;

}
