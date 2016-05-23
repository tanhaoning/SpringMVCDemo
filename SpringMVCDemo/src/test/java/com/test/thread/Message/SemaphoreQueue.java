package com.test.thread.Message;

import java.sql.SQLOutput;
import java.util.concurrent.Semaphore;

/**
 * Created by tanzepeng on 2015/8/19.
 * <p/>
 * 基于信号量Semaphore实现的有序队列
 */
public class SemaphoreQueue {

    /* 非满锁 */
    private final Semaphore notFull;
    /* 非空锁 */
    private final Semaphore notEmpty = new Semaphore(0);
    /* 互斥锁 */
    private final Semaphore mutex = new Semaphore(1);

    private final Object[] items;

    private int putIndex, takeIndex, count;

    public SemaphoreQueue(int size) {
        notFull = new Semaphore(size);
        items = new Object[size];
    }

    public void put(Object object) throws InterruptedException {
        // 保证非满
        notFull.acquire();
        // 同步
        mutex.acquire();

        try {
            items[putIndex] = object;
            if (++putIndex == items.length) {
                putIndex = 0;
            }
            ++count;
            System.out.println("仓库生产[" + putIndex + "],仓库总量[" + count + "]");
        } finally {
            mutex.release();
            notEmpty.release();
        }
    }

    public Object take() throws InterruptedException {
        // 保证非空
        notEmpty.acquire();
        // 同步
        mutex.acquire();
        try {
            Object object = items[takeIndex];
            if (++takeIndex == items.length) {
                takeIndex = 0;
            }
            --count;
            System.out.println("仓库消费[" + putIndex + "],仓库总量[" + count + "]");
            return object;
        } finally {
            mutex.release();
            notFull.release();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SemaphoreQueue semaphoreQueue = new SemaphoreQueue(20);
       /* for (int i = 0; i < 10; i++) {
            String good = "商品[" + i + "]";
            semaphoreQueue.put(good);
        }
        String result = (String) semaphoreQueue.take();
        System.out.println("result = [" + result + "]");*/
        Thread producer = new Thread(semaphoreQueue.new Producer(semaphoreQueue), "Producer");
        Thread consumer = new Thread(semaphoreQueue.new Consumer(semaphoreQueue), "Consumer");
        producer.start();
        consumer.start();

    }

    // 生产者，负责增加
    protected class Producer implements Runnable {
        SemaphoreQueue semaphoreQueue;
        public int num = 1;

        public Producer(SemaphoreQueue semaphoreQueue) {
            this.semaphoreQueue = semaphoreQueue;
        }

        public void run() {
            ++num;
            while (true) {
                try {
                    semaphoreQueue.put("物品[" + num + "]");
                    // 速度较快。休息10毫秒
                    System.out.println(Thread.currentThread().getName() + "生产了" + 1 + "个商品!");
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 消费者，负责减少
    protected class Consumer implements Runnable {
        SemaphoreQueue semaphoreQueue;

        public Consumer(SemaphoreQueue semaphoreQueue) {
            this.semaphoreQueue = semaphoreQueue;
        }

        public void run() {
            while (true) {
                try {
                    semaphoreQueue.take();
                    System.out.println(Thread.currentThread().getName() + "消费了" + 1 + "个商品!");
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
