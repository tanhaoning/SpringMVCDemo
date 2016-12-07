package com.dubbo.provider;

import java.util.Map;

/**
 * Created by tanzepeng on 2016/12/6.
 */
public class LoginImpl implements ILogin {
    public String checkLogin(Map param) {
        return "Hello:" + param.toString();
    }
}
