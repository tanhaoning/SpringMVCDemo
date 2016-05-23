package com.test.thread.model;

/**
 * Created by tanzepeng on 2015/7/6.
 */
public class Producer extends Thread {

    // 每次生产的产品数量
    private int num;
    // 所放置的仓库
    private Storage storage;

    public Producer(Storage storage) {
        this.storage = storage;
    }

    public void run() {
        produce(num);
    }

    // 调用仓库Storage的生产函数
    public void produce(int num) {
        storage.produce(num);
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }
}
