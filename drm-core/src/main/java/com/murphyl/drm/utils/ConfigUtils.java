package com.murphyl.drm.utils;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.Properties;

/**
 * -
 *
 * @author luohao
 * @date 2020/1/3 10:52
 */
public final class ConfigUtils {

    public static final Properties loadClasspathProperties(String path) throws IOException {
        Properties result = new Properties();
        Enumeration<URL> rules = URLClassLoader.getSystemResources(path);
        while (rules.hasMoreElements()) {
            result.load(rules.nextElement().openStream());
        }
        return result;
    }

}
