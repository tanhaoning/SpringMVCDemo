package com.test.thread.arithmetic;import java.util.concurrent.Semaphore;

/**
 * <p>
 * <p/>
 * </p>
 *
 * @author tanzp
 * @CreateDate 2017/6/6 15:30
 */
public class TestVolatileNum {
    public volatile static int count = 0;



    public static void main(String[] args) {
        final Semaphore semaphore = new Semaphore(1);

        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    count++;
                }
            }).start();
        }
        System.out.println("结果:"+count);
    }
}
