package com.test.spring;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * Created by tanzepeng on 2016/7/1.
 */
public class TestBeanFactory {
    public static void main(String[] args) {
        /**
         * 1.加载Bean文件
         */
        ApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{"classpath:spring/application-test.xml"});
        BeanFactory factory = (BeanFactory) context;

        System.out.println(factory.getBean("testBean"));
        //System.out.println(factory.getBean("test"));
    }
}
