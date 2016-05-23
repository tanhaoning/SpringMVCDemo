package com.test.thread.Message;


/**
 * Created by tanzepeng on 2015/8/18.
 */
public class TestMessage {
    public static void main(String[] args) {
        MessageInfo messageInfo = new MessageInfo("1", "message");
        IMsgQueue msgQueue = MsgQueueFactory.getMessageQueue();
        msgQueue.put(messageInfo);
        msgQueue.take();
    }

    public static void createMessage() {
        for (int i = 0; i <= 1000; i++) {
            MessageInfo messageInfo = new MessageInfo();
            messageInfo.setMessageId("消息标识[" + i + "]");
            messageInfo.setMessageDesc("消息内容[" + i + "]");
        }
    }
}
