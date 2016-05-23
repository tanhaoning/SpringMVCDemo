package com.test.dbroute;

/**
 * Created by tanzepeng on 2015/6/26.
 */
public class DBRouteContext {

    private static final ThreadLocal<String> contextHandler = new ThreadLocal<String>();

    public static void setName(String dbKey) {
        contextHandler.set(dbKey);
    }

    public static String getName() {
        return contextHandler.get();
    }

    public static void clear() {
        contextHandler.remove();
    }
}
