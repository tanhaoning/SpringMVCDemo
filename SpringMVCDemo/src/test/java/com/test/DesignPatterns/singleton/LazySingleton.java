package com.test.DesignPatterns.singleton;

import java.util.concurrent.locks.Lock;

/**
 * 单例模式之懒汉模式 -- 非线程安全
 * 应用场景：有些对象只需一个实例就足够了
 * Created by tanzepeng on 2016/3/3.
 */
public class LazySingleton {
    /**
     * 如果只判断一次null==instance的话，就没必要了。但如果像本博客代码里写的那样，
     * 判断了两次null==instance，那么volatile是必要的。
     * <p/>
     * 为什么呢？举个反例，假设线程thread1走到了第15行的if判断发现instance==null成立，于是都进入了外部的if体。
     * 这时候thread1先获取了synchronized块的锁，于是thread1线程会执行第18行的instance = new SingleTon();
     * 这句代码，问题就出在这里，这条语句它不是原子性执行的。在Java里，实例化一个对象的过程简单地讲，可以分为两步
     * 1）先为instance对象分配一块内存，
     * 2）在这块内存里为instance对象里的成员变量赋值（比如第11行里为url赋值）。
     * 假设当thread1执行完第1）步而还没有执行第2）步的时候，另外一个线程thread2走到了第15行，
     * 这时候instance已经不是null了，于是thread2直接返回了这个instance对象。有什么问题呢？
     * instance对象的初始化（变量赋值等操作）还没执行完呢！
     * thread2里直接得到了一个没有初始化完全的对象，就有可能导致很严重的问题了。
     */
    private static volatile LazySingleton instance;
    public String name;

    private LazySingleton() {
        name = "Lazy!";
    }

    private static LazySingleton getInstance() {
        /**
         * 第一个null==instance判断，为保证实例化后再次获取实例不用对对象加锁
         */
        if (null == instance) {
            synchronized (LazySingleton.class) {
                if (null == instance) {
                    instance = new LazySingleton();
                }
            }
        }
        return instance;
    }
}
