package com.test.annotation;

import java.lang.annotation.*;

/**
 * Created by tanzepeng on 2015/11/10.
 */
@Target(value = {ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogRecord {
    boolean isSwitch() default false;
}
