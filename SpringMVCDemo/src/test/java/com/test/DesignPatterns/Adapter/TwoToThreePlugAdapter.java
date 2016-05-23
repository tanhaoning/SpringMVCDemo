package com.test.DesignPatterns.Adapter;

/**
 * Created by tanzepeng on 2016/3/7.
 */
public class TwoToThreePlugAdapter implements ThreePlug {

    public IPlug iPlug;

    public TwoToThreePlugAdapter(IPlug iPlug){
        this.iPlug = iPlug;
    }

    public void power() {
        iPlug.power();
    }
}
