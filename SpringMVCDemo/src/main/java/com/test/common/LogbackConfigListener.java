package com.test.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.OptionHelper;
import ch.qos.logback.core.util.StatusPrinter;

/**
 * LogbackConfigListener 侦听日志.
 * <p/>
 */
public class LogbackConfigListener implements ServletContextListener {
    /**
     * log param name 配置名字
     */
    public static final String CONFIG_LOCATION_PARAM = "logbackConfigLocation";
    /**
     * classpath前缀
     */
    public static final String CLASSPATH_URL_PREFIX = "classpath";

    /**
     * @param sce ServletContextEvent
     */
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();
        ILoggerFactory ilc = LoggerFactory.getILoggerFactory();

        if (!(ilc instanceof LoggerContext)) {
            sc.log("Can not configure logback. " + LoggerFactory.class
                    + " is using " + ilc.getClass() + " which is not a "
                    + LoggerContext.class);
            return;
        }

        LoggerContext lc = (LoggerContext) ilc;
        lc.stop();
        lc.reset();

        String location = getLocation(sc, lc);

        if (location == null) {
            return;
        }

        sc.log("Configuring logback from config resource located at "
                + location);

        InputStream is = openInputStream(sc, location);
        if (is == null) {
            sc.log("Could not open logback config neither as servlet context-, " +
                    "nor as url-, nor as file system resource. Location: "
                    + location);
            return;
        }
        try {
            configureLogback(sc, lc, is);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                sc.log("Could not close logback config inputstream.", e);
            }
        }
    }

    /**
     * Location
     *
     * @param sc ServletContext
     * @param lc LoggerContext
     * @return String Location
     */
    protected String getLocation(ServletContext sc, LoggerContext lc) {
        String location = sc.getInitParameter(CONFIG_LOCATION_PARAM);
        location = OptionHelper.substVars(location, lc);
        return location;
    }

    /**
     * @param sc       ServletContext
     * @param location String
     * @return InputStream InputStream
     */
    protected InputStream openInputStream(ServletContext sc, String location) {
        InputStream is = null;
        if (location == null) {
            return is;
        }
        if (location.startsWith("/")) {
            is = sc.getResourceAsStream(location);
        } else {
            try {
                is = new URL(location).openStream();
            } catch (IOException e) {
            }
        }
        if (is == null) {
            try {
                is = new FileInputStream(location);
            } catch (FileNotFoundException e) {
            }
        }
        if (is == null && location.startsWith(CLASSPATH_URL_PREFIX)) {
            try {
                String pathToUse = StringUtils.cleanPath(location);
                pathToUse = pathToUse.substring(pathToUse.indexOf(":") + 1);
                ClassPathResource resource = new ClassPathResource(pathToUse);
                is = resource.getInputStream();
            } catch (Exception e) {
            }
        }
        if (is == null) {
            try {
                is = new FileInputStream(ResourceUtils.getURL(location)
                        .getPath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return is;
    }

    /**
     * @param sc ServletContext
     * @param lc LoggerContext
     * @param is InputStream
     */
    protected void configureLogback(ServletContext sc, LoggerContext lc,
                                    InputStream is) {
        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(lc);
        lc.stop();
        try {
            configurator.doConfigure(is);
        } catch (JoranException e) {
            sc.log("Logback configuration failed.", e);
        }
        StatusPrinter.print(lc);
    }

    /**
     * @param sce ServletContextEvent
     */
    public void contextDestroyed(ServletContextEvent sce) {
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        lc.stop();
    }
}
