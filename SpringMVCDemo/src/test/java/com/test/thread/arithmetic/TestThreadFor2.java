package com.test.thread.arithmetic;import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>
 * <p/>
 * </p>
 *
 * @author tanzp
 * @CreateDate 2017/6/6 14:29
 */
public class TestThreadFor2 {

    public volatile static int count = 0;

    public static void main(String[] args) {
        //定义一个许可权为1的信号灯
        final Semaphore semaphore = new Semaphore(1);
        final BlockingQueue<String> queue = new ArrayBlockingQueue<String>(10);

        final Object objLock = new Object();
        final Lock lock = new ReentrantLock(); // 锁

        // 10个线程,从队列中取
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        // 消费数据
                        // 线程互斥三种
                        // 1.信号量(1)
                        // 2.Lock锁
                        // 3.同步对象锁
                        //semaphore.acquire();
                        //lock.lock();
                        synchronized (objLock) {
                            String input = queue.take();
                            String output = TestDo.doSome(input);
                            System.out.println(Thread.currentThread().getName() + ":" + output);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        //semaphore.release();
                        //lock.unlock();
                    }
                }
            }
            ).start();
        }

        System.out.println("begin:" + (System.currentTimeMillis() / 1000));

        for (int i = 0; i < 10; i++) {  //这行不能改动
            // 生产数据
            String input = i + "";  //这行不能改动

            try {
                queue.put(input);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //@Test
    public void test() {
        System.out.println("begin:" + (System.currentTimeMillis() / 1000));
        for (int i = 0; i < 10; i++) {  //这行不能改动
            // 生产数据
            String input = i + "";  //这行不能改动

            // 消费数据
            String output = TestDo.doSome(input);

            System.out.println(Thread.currentThread().getName() + ":" + output);
        }
    }

    //不能改动此TestDo类
    static class TestDo {
        public static String doSome(String input) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String output = input + ":" + (System.currentTimeMillis() / 1000);
            return output;
        }
    }
}
