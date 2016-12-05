package com.test.rpc;


/**
 * Created by tanzepeng on 2016/11/29.
 */
public class ServerServiceImpl implements ServerService {
    public String hello(String name) {
        return "Hello " + name;
    }
}
