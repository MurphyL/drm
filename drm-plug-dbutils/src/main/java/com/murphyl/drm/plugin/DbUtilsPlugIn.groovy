package com.murphyl.drm.plugin

import com.murphyl.drm.utils.DrmPlugIns

/**
 * Apache DbUtils - 插件
 * @author murph*
 * @date 2019/9/17 9:30
 */
 
class DbUtilsPlugIn implements DynamicPlugIn {

    String name = 'Apache DBUtils - PlugIn'

    static {
        DrmPlugIns.dbutils.setTarget(DbUtilsPlugIn)
    }


}
