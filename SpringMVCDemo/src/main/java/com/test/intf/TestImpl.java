package com.test.intf;


import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by tanzepeng on 2015/6/9.
 */
@Service("com.test.intf.TestImpl")
public class TestImpl implements ITest {
    private String msg = "初始化";

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String sayHi() {
        return "当前时间" + new Date() + " msg:" + msg;
    }
}
