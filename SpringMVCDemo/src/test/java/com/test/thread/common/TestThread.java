package com.test.thread.common;

/**
 * Created by tanzepeng on 2015/7/6.
 */
public class TestThread {

    private static TestThread testThread;
    private static int p = 0;
    private int i = 0;

    public static TestThread getInstance() {
        if (null == testThread) {
            testThread = new TestThread();
        }
        return testThread;
    }

    public static int getP() {
        return p;
    }

    public static void setP(int p) {
        TestThread.p = p;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public void addI() {
        ++i;
    }

    public void addP() {
        ++p;
    }

    public static void main(String[] args) {
        Thread threadA = new Thread(new SequenceThread(), "Thread-A");
        Thread threadB = new Thread(new SequenceThread(), "Thread-B");
        Thread threadC = new Thread(new SequenceThread(), "Thread-C");
        threadA.start();
        threadB.start();
        threadC.start();
    }
}
