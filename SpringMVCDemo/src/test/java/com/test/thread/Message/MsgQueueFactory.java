package com.test.thread.Message;

/**
 * Created by tanzepeng on 2015/8/18.
 */
public class MsgQueueFactory {

    private static IMsgQueue msgQueue;

    public static IMsgQueue getMessageQueue() {
        if (null == msgQueue) {
            msgQueue = new MsgQueueManager();
        }
        return msgQueue;
    }
}
