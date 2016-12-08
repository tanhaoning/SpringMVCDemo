package com.dubbo.provider;

import com.dubbo.rpc.ILogin;
import org.apache.commons.collections.MapUtils;

import java.util.Map;

/**
 * Created by tanzepeng on 2016/12/6.
 */
public class LoginImpl implements ILogin {
    public String checkLogin(Map param) {

        String username = MapUtils.getString(param, "username", "");
        String password = MapUtils.getString(param, "password", "");

        if ("".equals(username) || "".equals(password)) {
            throw new IllegalArgumentException("Inparam argument is Error:" + param.toString());
        }

        if (!"admin".equals(username) || !"123456".equals(password)) {
            return "";
        }

        return "OK";
    }
}
