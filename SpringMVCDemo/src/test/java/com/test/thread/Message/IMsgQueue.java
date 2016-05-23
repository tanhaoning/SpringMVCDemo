package com.test.thread.Message;

/**
 * Created by tanzepeng on 2015/8/18.
 * <p/>
 * 消息队列总线
 *
 *                                ---------
 *                               | 生产者 1 |
 *                                ---------
 *                                    |
 *                                ----------
 *                               | 阻塞列队 1 |
 *                                ----------
 *                                    |
 *                                ---------
 *                               | 消费者 1 |
 *                                ---------
 *                 /                  |                    \
 *            ----------          ----------            ----------
 *           | 阻塞列队 1 |       | 阻塞列队 2 |          | 阻塞列队 3 |
 *            ----------          ----------            ----------
 *                 /                  |                    \
 *            ---------           ---------             ---------
 *           | 消费者 1 |         | 消费者 2 |           | 消费者 3 |
 *            ---------           ---------             ---------
 *
 */
public interface IMsgQueue {

    public void put(MessageInfo messageInfo);

    public MessageInfo take();
}
