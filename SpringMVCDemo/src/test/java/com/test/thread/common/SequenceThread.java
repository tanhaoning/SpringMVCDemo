package com.test.thread.common;

/**
 * Created by tanzepeng on 2015/7/6.
 */
public class SequenceThread implements Runnable {


    public SequenceThread() {

    }

    public void run() {
        long time = System.currentTimeMillis();
        for (int i = 0; i <= 3; i++) {
            try {
                TestThread.getInstance().addI();
                //testThread.addP();
                //Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " : 当前I值= " + TestThread.getInstance().getI() + " , 当前P值= " + TestThread.getInstance().getP());
        }

        System.out.println(Thread.currentThread().getName() + " : 结果I= " + TestThread.getInstance().getI() + " , P= " + TestThread.getInstance().getP());
        //System.out.println("时间：" + (System.currentTimeMillis() - time));
    }
}
