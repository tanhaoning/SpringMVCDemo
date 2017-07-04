package com.test.IOC;

import sun.security.jca.GetInstance;

import java.lang.reflect.Method;

/**
 * <p>
 * <p/>
 * </p>
 *
 * @author tanzp
 * @CreateDate 2017/5/22 20:16
 */
public class IocTest {

    private static String configLearn = "com.test.IOC.EnglishLearn";

    public static void main(String[] args) {
        try {
            Class obj = Class.forName(configLearn);
            Object object = obj.newInstance();
            Class[] argsClass = null;
            Method method = obj.getMethod("learn",argsClass);
            method.invoke(object, null);
        } catch (Exception e) {
        }
    }
}
