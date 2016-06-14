package com.test.proxy.cglibProxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by tanzepeng on 2015/6/30.
 */
public class TestDoProxy implements MethodInterceptor {
    private Object object;

    public Object getProxyInstance(Object target){
        this.object = target;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.object.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        preDoSomething();
        Object obj = methodProxy.invokeSuper(o,objects);
        lastDoSomething();
        return obj;
    }

    private void preDoSomething() {
        System.out.println("preDoSomething");
    }

    private void lastDoSomething() {
        System.out.println("lastDoSomething");
    }

    public static void main(String[] args) {
        TestDoProxy testDoProxy = new TestDoProxy();
        ITestDo testDo = (ITestDo)testDoProxy.getProxyInstance(new TestDoImpl());
        testDo.doSomething();
    }
}
