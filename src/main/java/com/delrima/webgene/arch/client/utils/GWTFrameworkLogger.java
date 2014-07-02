package com.delrima.webgene.arch.client.utils;

import java.util.logging.Logger;

/**
 * <code><B>GWTFrameworkLogger<code><B>
 * <p/>
 * Accepts a class type that will be prepended to each message 
 * The logger aggregates all log messages within a view and flushes them automatically after all RPCs have completed  
 * <p/>
 * 
 * @author bhavesh.thakker@ihg.com
 * @since Mar 21, 2009
 */

public class GWTFrameworkLogger {

    public static Logger getCustomLogger(Class<?> clazz) {
        return Logger.getLogger(getSimpleClassName(clazz));
    }

    private static String getSimpleClassName(Class<?> classType) {
        int index = classType.getName().lastIndexOf(".") + 1;
        return classType.getName().substring(index);
    }

}
