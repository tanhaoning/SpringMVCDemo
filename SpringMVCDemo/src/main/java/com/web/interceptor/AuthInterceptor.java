package com.web.interceptor;

import com.test.annotation.AuthValid;
import org.apache.oro.text.perl.Perl5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * Created by tanzepeng on 2015/7/15.
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {

    private static final Logger log = LoggerFactory.getLogger(AuthInterceptor.class);

    private static final String HEADER_REFERER = "Referer";

    private static final String DOMAIN = "";

    private static final String HEADER_HOST = "Host";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("AuthInterceptor============preHandle()======");
        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            AuthValid authValid = ((HandlerMethod) handler).getMethodAnnotation(AuthValid.class);
            //没有声明需要权限，或者不验证
            if (authValid == null || authValid.validate() == false) {
                return true;
            } else {
                //这里实现权限校验逻辑
                if (checkHeaderParam(request)) {
                    log.debug("==========Dosomething to validate======");
                    return true;
                } else {
                    log.debug("==========response.sendRedirect(\"test/login\");==========");
                    response.sendRedirect(request.getContextPath() + "/test/login");
                    return false;
                }
            }
        }
        return super.preHandle(request, response, handler);
    }

    private boolean checkReferer(HttpServletRequest request) {
        String headerReferer = request.getHeader(HEADER_REFERER);
        if (DOMAIN != null) {
            if (headerReferer == null || headerReferer.length() < 8) {
                return false;
            }
            String headDomain = headerReferer.substring(0, headerReferer.indexOf("/", headerReferer.indexOf(".")));
            Perl5Util matcher = new Perl5Util();
            if (matcher.match("/(http|https):\\/\\/" + DOMAIN + ".*$/i", headDomain)) {
                return true;
            }
            return false;
        }
        return true;
    }

    private boolean checkHeaderParam(HttpServletRequest request) {
        Enumeration enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String headName = (String) enumeration.nextElement();
            String headValue = request.getHeader(headName);
            System.out.println("*********hadName=" + headName + ",headValue=" + headValue);
        }
        return true;
    }
}
