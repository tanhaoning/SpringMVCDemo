package com.test.thread.arithmetic;import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * <p>
 * <p/>
 * </p>
 *
 * @author tanzp
 * @CreateDate 2017/6/6 9:30
 */
public class TestThreadFor {

    //parseLog方法内部的代码不能改动
    private static void parseLog(String log) {
        System.out.println(log + ":" + (System.currentTimeMillis() / 1000));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test1() {
        System.out.println("begin:" + (System.currentTimeMillis() / 1000));
            /*模拟处理16行日志，下面的代码产生了16个日志对象，当前代码需要运行16秒才能打印完这些日志。
            修改程序代码，开四个线程让这16个对象在4秒钟打完。
            */
        for (int i = 0; i < 16; i++) {  //这行代码不能改动
            final String log = "" + (i + 1);   //这行代码不能改动
            {
                parseLog(log);
            }
        }
    }

    public void testUpdate() {

        //创建一个空间大小为16的阻塞队列，空间大小可以任意，因为每次打印都要1秒，在此期间，
        //4个线程足以不断去从队列中取数据，然后打印,即在1秒内打印4条日志信息
        final BlockingQueue<String> queue = new ArrayBlockingQueue<String>(16);


        /*for (int i = 0; i < 4; i++) {
            new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        try {
                            String log = queue.take();
                            parseLog(log);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }*/
        //开启4个线程打印
        //for (int i = 0; i < 4; i++) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        System.out.println("test");
                        String log = queue.take();  //开始没有数据，阻塞，一旦有其中一个线程就去取
                        //数据，即不再阻塞，就开始打印
                        //parseLog(log);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }).start();
        //}

        System.out.println("begin:" + (System.currentTimeMillis() / 1000));

        /*模拟处理16行日志，下面的代码产生了16个日志对象，当前代码需要运行16秒才能打印完这些日志。
            修改程序代码，开四个线程让这16个对象在4秒钟打完。
            */
        for (int i = 0; i < 16; i++) {  //这行代码不能改动
            final String log = "" + (i + 1);   //这行代码不能改动
            {
                try {
                    queue.put(log);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        final BlockingQueue<String> queue = new ArrayBlockingQueue<String>(16);

        for (int i = 0; i < 4; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (queue.size()>0) {
                        try {
                            System.out.println(Thread.currentThread().getName());
                            String log = queue.take();  //开始没有数据，阻塞，一旦有其中一个线程就去取
                            //数据，即不再阻塞，就开始打印
                            parseLog(log);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }).start();
        }

        System.out.println("begin:" + (System.currentTimeMillis() / 1000));

        for (int i = 0; i < 16; i++) {  //这行代码不能改动
            final String log = "" + (i + 1);   //这行代码不能改动
            {
                try {
                    queue.put(log);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
