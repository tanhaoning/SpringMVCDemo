package ArtConcurrentBook.chapter01;

/**
 * ��������
 * 
 * @author tengfei.fangtf
 * @version $Id: DeadLockDemo.java, v 0.1 2015-7-18 ����10:08:28 tengfei.fangtf Exp $
 */
public class DeadLockDemo {

    /** A�� */
    private static String A = "A";
    /** B�� */
    private static String B = "B";

    public static void main(String[] args) {
        new DeadLockDemo().deadLock();
    }

    private void deadLock() {
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                synchronized (A) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (B) {
                        System.out.println("1");
                    }
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                synchronized (B) {
                    synchronized (A) {
                        System.out.println("2");
                    }
                }
            }
        });
        t1.start();
        t2.start();
    }

}
