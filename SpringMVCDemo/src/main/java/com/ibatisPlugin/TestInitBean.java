package com.ibatisPlugin;

import org.springframework.beans.factory.InitializingBean;

/**
 * Created by tanzepeng on 2015/11/16.
 */
public class TestInitBean implements InitializingBean {
    private String prop1 ;
    private String prop2 ;

    public void setProp1(String prop1) {
        this.prop1 = prop1;
    }
    public void setProp2(String prop2) {
        this.prop2 = prop2;
    }

    public TestInitBean(){
        // Spring 生产bean自动调用无参构造函数(如未配置参数构造函数),此时所有变量未初始化
        System.out.println("prop1:"+prop1+",prop2:"+prop2);
    }

    // bean 对象创建,数据配置后会调用初始化方法
    public void afterPropertiesSet() throws Exception {
        System.out.println("init Spring bean!!");
        System.out.println("prop1:"+prop1+",prop2:"+prop2);
    }
}
