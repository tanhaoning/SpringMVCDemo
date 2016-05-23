package com.web.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by tanzepeng on 2015/7/9.
 */
public class TestLoginInterceptor implements HandlerInterceptor {
    private final static Logger log = LoggerFactory.getLogger(TestLoginInterceptor.class);
    private UrlPathHelper urlPathHelper = new UrlPathHelper();
    private String[] interceptorUrl;

    public void setInterceptorUrl(String[] interceptorUrl) {
        this.interceptorUrl = interceptorUrl;
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("=======Test Login Redirect to 404 , preHandle:[{}]", "404.jsp");
        String url = urlPathHelper.getOriginatingRequestUri(request).substring(request.getContextPath().length());
        boolean isContain = false;
        for (String iurl : interceptorUrl) {
            if (url.startsWith(iurl)) {
                isContain = true;
            }
        }
        if (!isContain) {
            response.sendRedirect(request.getContextPath() + "/error/404.jsp");
            return false;
        }
        return true;
    }
}
