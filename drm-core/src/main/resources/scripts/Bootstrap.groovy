package com.murphyl.drm.core

import org.slf4j.LoggerFactory

def logger = 'Bootstrap'

setProperty('ready', false)

def log = LoggerFactory.getLogger(logger)

setProperty('log', log)

log.info('类：' + this.class)

setProperty('app', (closure) -> {
    closure()
    setProperty('ready', true)
})