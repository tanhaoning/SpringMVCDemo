package com.test.spring;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;


/**
 * Created by tanzepeng on 2016/7/1.
 */
public class TestBeanFactory {
    public static void main(String[] args) {

        Resource resource = new ClassPathResource("spring/application-test.xml");
        BeanFactory factory = new XmlBeanFactory(resource);
        TestBean testBean  = (TestBean)factory.getBean("testBean");
        System.out.println(testBean);
    }

    public static void loadBeanFactory1(){
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
