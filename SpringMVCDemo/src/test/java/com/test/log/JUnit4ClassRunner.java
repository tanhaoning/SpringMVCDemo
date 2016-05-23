package com.test.log;

import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Log4jConfigurer;

import java.io.FileNotFoundException;

/**
 * Created by tanzepeng on 2015/7/3.
 */
public class JUnit4ClassRunner extends SpringJUnit4ClassRunner {
    static {
        try {
            Log4jConfigurer.initLogging("classpath:log4j/log4j.properties");
        } catch (FileNotFoundException ex) {
            System.err.println("Cannot Initialize log4j");
        }
    }

    public JUnit4ClassRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
    }
}
