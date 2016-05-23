package com.test.thread.model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by tanzepeng on 2015/7/6.
 */
public class StorageManager {

    private static StorageManager storageManager;
    private final int MAX_SIZE = 20;

    private static BlockingQueue<Object> list;

    public StorageManager() {
        list = new LinkedBlockingDeque<Object>(MAX_SIZE);
    }

    public static StorageManager getInstance() {
        if (null == storageManager) {
            storageManager = new StorageManager();
        }
        return storageManager;
    }

    //生产者
    class Producer implements Runnable {
        public void run() {
            while (true) {
                try {
                    list.put(new Object());
                    Thread.sleep(100);
                    System.out.println("【" + Thread.currentThread().getName() + "】仓库现存：" + list.size());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    //消费者
    class Consumer implements Runnable {
        public void run() {
            while (true) {
                try {
                    list.take();
                    Thread.sleep(200);
                    System.out.println("【" + Thread.currentThread().getName() + "】仓库现存：" + list.size());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) {
        StorageManager storageManager = StorageManager.getInstance();
        new Thread(storageManager.new Producer(), "Producer").start();
        new Thread(storageManager.new Consumer(), "Consumer").start();
    }
}
