package com.test.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dubbo.rpc.ILogin;
import com.spring.utils.SpringContextUtil;
import com.test.annotation.AuthValid;
import com.test.annotation.LogRecord;
import com.test.common.Constant;
import com.test.model.SessionLoginUser;
import com.test.service.bmo.LoginService;
import org.eclipse.core.runtime.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tanzepeng on 2015/6/9.
 */
@Controller("com.test.controller.LoginController")
@RequestMapping("/login/*")
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    @Qualifier("com.test.service.bmo.LoginService")
    private LoginService loginService;

    /** Controller 属于加载SpringMVC上下文扫描,dubbo注解配置在根上下文扫描配置 **/
    /*@Reference
    private ILogin loginService;*/
    /**
     * 获取dubbo消费方接口注入 *
     */
    //private ILogin loginService = (ILogin) SpringContextUtil.getBean("loginService");

    @Autowired
    private MessageSource messageSource;

    @AuthValid(validate = false)
    @LogRecord(isSwitch = false)
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String login(Model model, HttpServletRequest request) {
        return "/bootstrap/login";
    }

    @AuthValid(validate = false)
    @LogRecord(isSwitch = false)
    @RequestMapping(value = "/home", method = {RequestMethod.GET, RequestMethod.POST})
    public String home(SessionLoginUser loginUser, HttpServletRequest request, HttpServletResponse response) {


        Map<String, Object> inParam = new HashMap<String, Object>();
        inParam.put("username", loginUser.getUsername());
        inParam.put("password", loginUser.getPassword());

        String retJson = loginService.checkLogin(inParam);
        if (!"OK".equals(retJson)) {
            return "/error/404";
        }
        //Map<String, Object> retJson = loginService.checkLogin(inParam);
        log.debug("outParam:" + retJson);

        HttpSession session = request.getSession();
        session.setAttribute(Constant.SESSION_LOGIN_USER, loginUser);
        return "/player";
    }
}
