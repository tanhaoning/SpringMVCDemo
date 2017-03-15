package com.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by tanzepeng on 2015/7/7.
 */
public class TestRequestFilter extends OncePerRequestFilter {

    private final static Logger log = LoggerFactory.getLogger(TestRequestFilter.class);

    private UrlPathHelper urlPathHelper = new UrlPathHelper();

    private final String URL = "/test/login";

    protected void initFilterBean() throws ServletException {
        log.debug("!!!!!!!!!!!!!!!!!!!![initFilterBean]!!!!!!!!!!!!!!!!!!!");
        super.initFilterBean();
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        log.info("!!!!!!!!!!!!!!!!!!!![shouldNotFilter=" + urlPathHelper.getOriginatingRequestUri(request).substring(request.getContextPath().length()) + "]!!!!!!!!!!!!!!!!!!!");
        log.info("request.getScheme()=[{}]", request.getScheme());
        log.info("request.getHeader('host')=[{}]", request.getHeader("host"));
        log.info("request.getMethod()=[{}]", request.getMethod());
        log.info("request.getContextPath()=[{}]", request.getContextPath());
        log.info("xxxxxxx[{}],[{}]", new String[]{urlPathHelper.getRequestUri(request), "123"});
        return super.shouldNotFilter(request);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.debug("!!!!!!!!!!!!!!!!!!!![doFilterInternal=" + urlPathHelper.getOriginatingRequestUri(request).substring(request.getContextPath().length()) + "]!!!!!!!!!!!!!!!!!!!");
        //if (URL.equals(urlPathHelper.getOriginatingRequestUri(request).substring(request.getContextPath().length()))) {
            /*StringBuilder builder = new StringBuilder();
            builder.append("<script type=\"text/javascript\">");
            builder.append("alert(\"请重新登陆\")");
            builder.append("</script>");
            response.setContentType("text/html;charset=utf-8");
            response.setHeader("Content-Language", "utf-8");
            response.getWriter().write(new String(builder));*/
        //response.sendRedirect("https://www.baidu.com/");
        // response.sendRedirect(request.getContextPath() + "/test.js/queryCountNum");
        //return;
        //}
        filterChain.doFilter(request, response);
    }
}
