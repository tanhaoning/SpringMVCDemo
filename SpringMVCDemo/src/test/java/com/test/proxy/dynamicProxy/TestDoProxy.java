package com.test.proxy.dynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by tanzepeng on 2015/6/30.
 */
public class TestDoProxy implements InvocationHandler {
    private Object object;

    public Object bind(Object object) {
        this.object = object;
        return Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(), this);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        preDoSomething();
        result = method.invoke(object, args);
        lastDoSomething();
        return result;
    }

    private void preDoSomething() {
        System.out.println("preDoSomething");
    }

    private void lastDoSomething() {
        System.out.println("lastDoSomething");
    }

    public static void main(String[] args) {
        TestDoProxy testDoProxy = new TestDoProxy();
        ITestDo testDo = (ITestDo) testDoProxy.bind(new TestDoImpl());
        testDo.doSomething();
    }
}
