package com.test.annotation;

import java.lang.annotation.*;

/**
* Created by tanzepeng on 2015/8/20.
        */
@Target({ElementType.METHOD, ElementType.TYPE}) //可用范围性
@Retention(RetentionPolicy.RUNTIME) // 执行范围性
@Inherited // 继承可用性
@Documented // 文档展示性
public @interface Table {
    String value();
}
