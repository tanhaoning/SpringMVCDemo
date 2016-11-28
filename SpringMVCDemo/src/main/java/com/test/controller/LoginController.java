package com.test.controller;

import com.al.common.utils.MapUtil;
import com.spring.utils.DateUtil;
import com.spring.utils.StringUtil;
import com.test.annotation.AuthValid;
import com.test.annotation.LogRecord;
import com.test.common.Constant;
import com.test.model.JsonBean;
import com.test.model.SessionLoginUser;
import com.test.model.TestModel;
import com.test.service.bmo.ILoginBMO;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * Created by tanzepeng on 2015/6/9.
 */
@Controller("com.test.controller.LoginController")
@RequestMapping("/login/*")
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    @Qualifier("com.test.service.bmo.ILoginBMO")
    private ILoginBMO loginBMO;

    @Autowired
    private MessageSource messageSource;

    @AuthValid(validate = false)
    @LogRecord(isSwitch = false)
    @RequestMapping(value = "/index", method = {RequestMethod.GET, RequestMethod.POST})
    public String login(@RequestParam Map<String, Object> paramMap, Model model, HttpServletRequest request) {

        String userLoginCode; // 用户登录名
        String userPassWord;  // 用户登录密码

        userLoginCode = request.getParameter("userLoginCode") == null ? "" : request.getParameter("userLoginCode").toString();
        userPassWord = request.getParameter("userPassWord") == null ? "" : request.getParameter("userPassWord").toString();

        SessionLoginUser loginUser = new SessionLoginUser();
        loginUser.setUserLoginCode(userLoginCode);
        loginUser.setUserPassWord(userPassWord);

        HttpSession session = request.getSession();

        session.setAttribute(Constant.SESSION_LOGIN_USER,loginUser);

        return "/bootstrap/login";
    }
}
