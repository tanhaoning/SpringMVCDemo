package com.test.thread.arithmetic;import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * <p>
 * <p/>
 * </p>
 *
 * @author tanzp
 * @CreateDate 2017/5/24 16:20
 */

public class TestInstClass extends TestCase{

    @Test
    public void test(){
        String varA = "abc";
        String varB = "ABc";
        String varC = "abc";
        System.out.println(varA.hashCode());
        System.out.println(varB.hashCode());
        System.out.println(varC.hashCode());
        char[] v1 = varA.toCharArray();
        int i = 0;
        int n = v1.length;
        while(n-- != 0){
            System.out.println(v1[i]);
            i++;
        }
        if(varA.equalsIgnoreCase(varB)){
            System.out.println("true");
        }else {
            System.out.println("false");
        }
        System.out.println(1<<4);

        System.out.println(1<<32);
    }

    @Test
    public void test1(){
        List list = new ArrayList(10);
    }
}
