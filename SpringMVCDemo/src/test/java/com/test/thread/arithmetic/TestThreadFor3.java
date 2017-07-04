package com.test.thread.arithmetic;import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Semaphore;

/**
 * <p>
 * <p/>
 * </p>
 *
 * @author tanzp
 * @CreateDate 2017/6/6 16:39
 */
public class TestThreadFor3 extends Thread {

    private TestDo testDo;
    private String key;
    private String value;

    public TestThreadFor3(String key, String key2, String value) {
        this.testDo = TestDo.getInstance();
            /*常量"1"和"1"是同一个对象，下面这行代码就是要用"1"+""的方式产生新的对象，
            以实现内容没有改变，仍然相等（都还为"1"），但对象却不再是同一个的效果*/
        this.key = key + key2;
        this.value = value;
    }

    public static void main(String[] args) throws InterruptedException {
        TestThreadFor3 a = new TestThreadFor3("1", "", "1");
        TestThreadFor3 b = new TestThreadFor3("1", "", "2");
        TestThreadFor3 c = new TestThreadFor3("3", "", "3");
        TestThreadFor3 d = new TestThreadFor3("4", "", "4");
        System.out.println("begin:" + (System.currentTimeMillis() / 1000));
        a.start();
        b.start();
        c.start();
        d.start();
    }

    public void run() {
        testDo.doSome(key, value);
    }
}

class TestDo {

    private TestDo() {
    }

    private static TestDo _instance = new TestDo();

    public static TestDo getInstance() {
        return _instance;
    }


    private CopyOnWriteArrayList keys = new CopyOnWriteArrayList();
    final Semaphore semaphore = new Semaphore(1);

    public void doSome(Object key, String value) {
        Object o = key;
        if (!keys.contains(o)) {
            keys.add(key);
        } else {
            Iterator itor = keys.iterator();
            if(itor.hasNext()){
                Object k = itor.next();
                if(k.equals(o)){
                    o = k;
                }
            }
        }

        synchronized (o)
        // 以大括号内的是需要局部同步的代码，不能改动!
        {
            try {
                Thread.sleep(1000);
                System.out.println(key + ":" + value + ":"
                        + (System.currentTimeMillis() / 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if(semaphore.hasQueuedThreads()){
                    semaphore.release();
                }
            }
        }
    }
}
