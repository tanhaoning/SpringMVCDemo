package com.test.log;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by tanzepeng on 2015/7/3.
 */

public class TestLog4j {

    private static final Logger log = Logger.getLogger(TestLog4j.class);

    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String nowDate = sDateFormat.format(calendar.getTime());
        System.setProperty("file.name", nowDate);
        System.out.println(nowDate);
        PropertyConfigurator.configure("E:\\springtestspace\\SpringMVCDemo\\src\\test.js\\java\\com\\test.js\\log\\log4j.properties");
        log.debug("This is debug message");
        log.info("This is info message");
        log.warn("This is warn message");
        log.error("This is error message");
    }
}
