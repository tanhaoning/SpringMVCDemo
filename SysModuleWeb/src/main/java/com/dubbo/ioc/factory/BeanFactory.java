package com.dubbo.ioc.factory;

/**
 * Created by tanzepeng on 2016/12/28.
 */
public interface BeanFactory {

    Object getBean(String id) throws Exception;

}
