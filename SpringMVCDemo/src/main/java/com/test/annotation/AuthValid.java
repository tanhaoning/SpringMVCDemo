package com.test.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by tanzepeng on 2015/7/15.
 */
@Target(value = {ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthValid {
    /**
     * 是否验证
     *
     * @return boolean true:验证
     */
    boolean validate() default true;
}
