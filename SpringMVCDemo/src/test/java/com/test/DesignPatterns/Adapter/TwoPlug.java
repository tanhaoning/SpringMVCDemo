package com.test.DesignPatterns.Adapter;

/**
 * Created by tanzepeng on 2016/3/7.
 */
public class TwoPlug implements IPlug {
    public void power() {
        System.out.println("二相充电");
    }
}
