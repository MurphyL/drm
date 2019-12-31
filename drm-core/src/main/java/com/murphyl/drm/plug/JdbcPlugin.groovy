package com.murphyl.drm.plug

import groovy.util.logging.Slf4j

/**
 *
 * @author murph*
 * @date 2019/9/17 9:30
 */
@Slf4j
class JdbcPlugin extends DynamicPlugin {

    final String kind = 'jdbc'

    final String name = 'JDBC'

    synchronized def read(String sql) {
        log.info("执行数据读取SQL：{}", sql)
        return sql
    }

    synchronized def write(String sql) {
        log.info("执行数据写入SQL：{}", sql)
        return 0
    }

}
