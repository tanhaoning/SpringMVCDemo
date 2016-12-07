package com.test.service.bmo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dubbo.rpc.ILogin;
import org.eclipse.core.runtime.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tanzepeng on 2016/12/7.
 */
@Service("com.test.service.bmo.LoginService")
public class LoginService {

    private static Logger log = LoggerFactory.getLogger(LoginService.class);

    @Reference
    private ILogin loginService;

    public String checkLogin(Map<String, Object> inParam) {
        Assert.isNotNull(loginService);
        String retJson = "";
        //Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            retJson = loginService.checkLogin(inParam);
            log.debug("retJson={}", retJson);
        } catch (Exception e) {
            log.debug("error=", e);
        }
        return retJson;
    }
}
