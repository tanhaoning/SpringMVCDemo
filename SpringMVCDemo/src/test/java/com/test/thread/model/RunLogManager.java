package com.test.thread.model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RunLogManager {

    private static RunLogManager runLogManager;
    protected static BlockingQueue<Object> list;
    protected static Warehouse buffer = new Warehouse();

    public static RunLogManager getInstance() {
        if (null == runLogManager) {
            runLogManager = new RunLogManager();
        }
        return runLogManager;
    }

    private RunLogManager() {
        list = new LinkedBlockingQueue<Object>();
    }

    // 生产者，负责增加
    protected class Producer implements Runnable {
        public void run() {
            while (true) {
                try {
                    buffer.put(list.take());
                    // 速度较快。休息10毫秒
                    Thread.sleep(10);
                    System.out.println("【" + Thread.currentThread().getName() + "】仓库现存：" + list.size());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 消费者，负责减少
    protected class Consumer implements Runnable {
        public void run() {
            while (true) {
                try {
                    buffer.take();
                    Thread.sleep(500);
                    System.out.println("【" + Thread.currentThread().getName() + "】仓库现存：" + list.size());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 日志仓库
     *
     * @author xuj
     */
    static class Warehouse {
        // 非满锁
        final Semaphore notFull = new Semaphore(10);
        // 非空锁
        final Semaphore notEmpty = new Semaphore(0);
        // 核心锁
        final Semaphore mutex = new Semaphore(1);
        // 库存容量
        final Object[] items = new Object[10];
        int putptr, takeptr, count;

        /**
         * 把日志放入库.<br>
         *
         * @param x
         * @throws InterruptedException
         */
        public void put(Object x) throws InterruptedException {
            // 保证非满
            notFull.acquire();
            // 保证不冲突
            mutex.acquire();
            try {
                // 增加库存
                items[putptr] = x;
                if (++putptr == items.length)
                    putptr = 0;
                ++count;
            } finally {
                // 退出核心区
                mutex.release();
                // 增加非空信号量，允许获取日志
                notEmpty.release();
            }
        }

        /**
         * 从仓库获取日志
         *
         * @return
         * @throws InterruptedException
         */
        public Object take() throws InterruptedException {
            // 保证非空
            notEmpty.acquire();
            // 核心区
            mutex.acquire();
            try {
                // 减少库存
                Object x = items[takeptr];
                if (++takeptr == items.length)
                    takeptr = 0;
                --count;
                return x;
            } finally {
                // 退出核心区
                mutex.release();
                // 增加非满的信号量，允许加入日志
                notFull.release();
            }
        }
    }

    public static void main(String[] args) {
        RunLogManager runLogManager = RunLogManager.getInstance();

        for (int i = 0; i < 100; i++) {
            runLogManager.list.add(new Object());
        }
        new Thread(runLogManager.new Producer(), "Producer").start();
        new Thread(runLogManager.new Consumer(), "Consumer").start();
    }
}
