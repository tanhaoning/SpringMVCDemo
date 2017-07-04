package com.test.thread.arithmetic;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @author tanzp
 * @CreateDate 2017/6/30 11:06
 */
public class InsertMath {

    public static void main(String[] args) {
        int[] randomNum = {6, 5, 3, 1, 8, 7, 2, 4};

        //randomNum = insert_sort(randomNum);

        int[] intArr = randomNum(250);

        for (int a : intArr) {
            System.out.print(a + " ");
        }
    }

    public static int[] randomNum(int size) {

        int[] result = new int[size];

        Set numSet = new HashSet();

        Random random = new Random();

        for (int i = 0; i <= size; i++) {
            int a = randomInt(numSet, random, size);
            result[i] = a;
        }

        return result;
    }

    public static int randomInt(Set set, Random r, int size) {
        int a = r.nextInt(size);
        if (set.add(a)) {
            return a;
        } else {
            return randomInt(set, r, size);
        }
    }


    /**
     * 插入排序算法
     *
     * @param inAarry
     * @return
     */
    public static int[] insert_sort(int[] inAarry) {
        int[] randomNum = inAarry;
        int i, j;
        for (i = 1; i < randomNum.length; i++) {

            int temp = randomNum[i];

            for (j = i; j > 0 && randomNum[j - 1] > temp; j--) {
                randomNum[j] = randomNum[j - 1];
            }

            randomNum[j] = temp;
        }
        return randomNum;
    }


}
