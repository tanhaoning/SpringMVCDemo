package com.test.DesignPatterns.singleton;

/**
 * 单例模式之私有静态类
 * Created by tanzepeng on 2016/3/3.
 */
public class OtherSingleton {
    private static class ResourceHolder {
        public static OtherSingleton instance = new OtherSingleton();
    }

    public static OtherSingleton getInstance() {
        return ResourceHolder.instance;
    }

}
