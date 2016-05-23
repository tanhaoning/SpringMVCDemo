package com.test.proxy.staticProxy;

/**
 * Created by tanzepeng on 2015/6/30.
 */
public class TestDoProxy implements ITestDo {

    private TestDoImpl testDo;

    public TestDoProxy(TestDoImpl testDo) {
        this.testDo = testDo;
    }

    public void doSomething() {
        preDoSomething();
        testDo.doSomething();
        lastDoSomething();
    }

    private void preDoSomething() {
        System.out.println("preDoSomething");
    }

    private void lastDoSomething() {
        System.out.println("lastDoSomething");
    }

    public static void main(String[] args) {
        TestDoProxy testDoProxy = new TestDoProxy(new TestDoImpl());
        testDoProxy.doSomething();
    }
}
