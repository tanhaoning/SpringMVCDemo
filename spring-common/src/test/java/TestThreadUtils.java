import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by tanzepeng on 2016/1/5.
 */
public class TestThreadUtils {

    private static List<Integer> res = new ArrayList<Integer>();

    static class CountThread extends Thread {
        public void run(){
            res.add(new Random().nextInt(100));
        }
    }

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        for(int i = 0;i<=4;i++){
            Thread testThread = new CountThread();
            testThread.start();
            testThread.join();
        }
        int totle = 0;
        for (Integer num : res) {
            totle += num.intValue();
        }
        long end = System.currentTimeMillis();
        System.out.println("运算结束 耗时：" + (end - start) + "ms  totle：" + totle);
        System.out.println("退出main线程！");
    }
}

