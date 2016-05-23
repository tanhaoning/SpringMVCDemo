package com.test.service.smo;

import com.test.dbroute.DBRouteContext;
import com.test.service.bmo.ITestDemoBMO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by tanzepeng on 2015/6/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:conf/applicationContext.xml",
        "classpath:conf/springmvctest_context.xml",
        "classpath:conf/springmvctest_controlls.xml"})
public class TestDemoSMOImplTest {

    private static final Logger log = LoggerFactory.getLogger(TestDemoSMOImplTest.class);

    @Autowired
    private ITestDemoBMO testDemoBMO;

    @Before
    public void setUp() throws Exception {
        log.debug("测试方法-----setUp()");
    }

    @Test
    public void testQueryCountNum() throws Exception {
        log.debug("测试方法-----testQueryCountNum()");
        String[] arrKey = {"guangdong", "jiangsu", "sichuan", "zhejiang"};
        for (int i = 0; i < arrKey.length; i++) {
            String dbKey = arrKey[i];
            DBRouteContext.setName(dbKey);
            int countNum = testDemoBMO.queryCountNum();
            System.out.println(dbKey + "-----countNum=" + countNum);
        }
    }
}