package com.test.thread.Message;

/**
 * Created by tanzepeng on 2015/8/18.
 * <p/>
 * 定义消息对象,用来表示生产或传递的信息
 */
public class MessageInfo {

    private String messageId;
    private String messageDesc;

    public MessageInfo() {

    }

    public MessageInfo(String messageId, String messageDesc) {
        this.messageId = messageId;
        this.messageDesc = messageDesc;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageDesc() {
        return messageDesc;
    }

    public void setMessageDesc(String messageDesc) {
        this.messageDesc = messageDesc;
    }
}
