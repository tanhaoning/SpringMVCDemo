package com.test.service.bmo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tanzepeng on 2015/6/16.
 */
@Service("com.test.service.bmo.ILoginBMO")
public class LoginBMOImpl implements ILoginBMO {

    private static Logger log = LoggerFactory.getLogger(LoginBMOImpl.class);

    public Map<String, Object> checkLogin(Map<String, Object> inParam) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            log.debug("resultMap={}", resultMap);
        } catch (Exception e) {
            log.debug("error=", e);
        }
        return resultMap;
    }

}
