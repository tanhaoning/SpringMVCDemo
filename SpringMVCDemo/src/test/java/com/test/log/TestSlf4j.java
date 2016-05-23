package com.test.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by tanzepeng on 2015/7/3.
 */
public class TestSlf4j {
    private static final Logger log = LoggerFactory.getLogger(TestSlf4j.class);

    public static void main(String[] args) {
        log.debug("This is debug message");
        log.info("This is info message");
        log.warn("This is warn message");
        log.error("This is error message");
    }
}
