package com.test.DesignPatterns.singleton;

/**
 * 单例模式之饿汉模式 -- 线程安全
 * 应用场景：有些对象只需一个实例就足够了
 * Created by tanzepeng on 2016/3/3.
 */
public class HungrySingleton {

    private static HungrySingleton instance = new HungrySingleton();

    private HungrySingleton(){}

    private static HungrySingleton getInstance(){
        return instance;
    }
}
