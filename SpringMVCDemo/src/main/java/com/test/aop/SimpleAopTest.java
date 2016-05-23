package com.test.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by tanzepeng on 2015/11/10.
 */
//@Component
//@Aspect
public class SimpleAopTest {

    public SimpleAopTest() {
        System.out.println("SimpleAopTest");
    }

    @PostConstruct
    public void contextInitialized() {
        System.out.println("contextInitialized");
    }

    private static final Logger log = LoggerFactory.getLogger(SimpleAopTest.class);
    public static final String ASPECT_SERVICE_EXCUTION = "execution(* com.test.controller.TestController.*(..))";

    @Before(ASPECT_SERVICE_EXCUTION)
    public void testBefore(ProceedingJoinPoint pjp) {
        System.out.println("@Before");
    }

    @After(ASPECT_SERVICE_EXCUTION)
    public void testAfter() {
        System.out.println("@After");
    }

    @AfterReturning(ASPECT_SERVICE_EXCUTION)
    public void testAfterReturning() {
        System.out.println("@AfterReturning");
    }

    @Around(value = ASPECT_SERVICE_EXCUTION)
    public Object testAround(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("@Around");
        Object o = pjp.proceed();
        return o;
    }

    @AfterThrowing(ASPECT_SERVICE_EXCUTION)
    public void testAfterThrowing() throws Throwable {
        System.out.println("@AfterThrowing");
    }


}
