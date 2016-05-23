import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by tanzepeng on 2016/1/5.
 */
public class TestSimpleUtils {
    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        TestSimpleUtils threadUtils = new TestSimpleUtils();
        List<Integer> res = new ArrayList<Integer>();
        for (int i = 0; i <= 10; i++) {
            res.add(threadUtils.random());
        }
        int totle = 0;
        for (Integer num : res) {
            totle += num.intValue();
        }
        long end = System.currentTimeMillis();
        System.out.println("运算结束 耗时：" + (end - start) + "ms  totle：" + totle);
        System.out.println("退出main线程！");
    }

    int random() throws InterruptedException {
        Thread.sleep(1000);
        int num = new Random().nextInt(100);
        //System.out.println("开始生产随机数:["+num+"]");
        return num;
    }

}
