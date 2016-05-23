package com.web.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by tanzepeng on 2015/7/7.
 */
public class TestSessionListener implements HttpSessionListener {

    private final static Logger log = LoggerFactory.getLogger(TestSessionListener.class);

    public void sessionDestroyed(HttpSessionEvent se) {
        log.debug("=======sessionDestroyed:[{}]", se.getSession().getId());
    }

    public void sessionCreated(HttpSessionEvent se) {
        log.debug("=======sessionCreated:[{}]", se.getSession().getId());
    }
}
