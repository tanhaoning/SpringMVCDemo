package com.test.thread.Message;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by tanzepeng on 2015/8/18.
 */
public class MsgQueueManager implements IMsgQueue {

    private BlockingQueue<MessageInfo> blockingQueue;

    public MsgQueueManager() {
        blockingQueue = new LinkedBlockingQueue<MessageInfo>();
    }

    public void put(MessageInfo messageInfo) {
        System.out.println("put()");
    }

    public MessageInfo take() {
        System.out.println("take()");
        return null;
    }
}
