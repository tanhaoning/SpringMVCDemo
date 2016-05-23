package com.test.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

/**
 * Created by tanzepeng on 2015/7/9.
 */
@Component
@Lazy(value = false)
public class DataEngine implements ServletContextAware {

    private final static Logger log = LoggerFactory.getLogger(DataEngine.class);

    private static ServletContext servletContext;

    @PostConstruct
    public void contextInitialized() {
        log.info("===============DataEngine contextInitialized:[{}]", this.getClass().getName());
        servletContext.setAttribute("contextPath", servletContext.getContextPath());
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }
}
