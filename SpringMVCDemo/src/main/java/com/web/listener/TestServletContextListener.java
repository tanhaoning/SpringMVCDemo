package com.web.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by tanzepeng on 2015/7/9.
 */
public class TestServletContextListener implements ServletContextListener {

    private final static Logger log = LoggerFactory.getLogger(TestServletContextListener.class);

    public void contextInitialized(ServletContextEvent sce) {
        log.info("Set dubbo log factory by slf4j");
        // 设置dubbo日志解析适配器为slf4j
        System.setProperty("dubbo.application.logger", "slf4j");
    }

    public void contextDestroyed(ServletContextEvent sce) {
        log.info("=======contextDestroyed:[{}]", sce.getServletContext().getContextPath());

    }
}
