package com.test.thread.arithmetic;import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * <p/>
 * </p>
 *
 * @author tanzp
 * @CreateDate 2017/6/7 19:12
 */
public class TestHashMap {
    public static void main(String[] args) {
        /*Map m1 = new HashMap();
        long startTime = System.currentTimeMillis();
        for(int i = 1;i<=10000;i++){
            m1.put(i,"M:"+i);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("M1:"+(endTime-startTime));

        new Thread(new Runnable() {
            @Override
            public void run() {
                Map m2 = new HashMap(13334);
                long startTime1 = System.currentTimeMillis();
                for(int i = 1;i<=10000;i++){
                    m2.put(i,"M:"+i);
                }
                long endTime1 = System.currentTimeMillis();
                System.out.println("M2:"+(endTime1-startTime1));
            }
        }).start();*/
        //System.out.println(1 << 30);
        System.out.println("result:"+ (100 >> 5));
        System.out.println("result:" + highestOneBit(100));
    }

    public static int highestOneBit(int i) {
        // HD, Figure 3-1
        i |= (i >>  1);
        i |= (i >>  2);
        i |= (i >>  4);
        i |= (i >>  8);
        i |= (i >> 16);
        return i - (i >>> 1);
    }

}
