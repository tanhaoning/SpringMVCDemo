import com.test.dbroute.DBRouteContext;
import com.test.service.dao.ITestDemoDAO;
import com.test.service.smo.ITestDemoSMO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by tanzepeng on 2015/6/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:conf/applicationContext-test.xml"})
/*@ContextConfiguration(locations = {"classpath:conf/applicationContext.xml",
        "classpath:conf/springmvctest_context.xml"})*/
@Transactional
@TransactionConfiguration(transactionManager = "ecsp-transactionManager", defaultRollback = true)
public class TestForDemo {

    private static final Logger log = LoggerFactory.getLogger(TestForDemo.class);

    @Autowired
    @Qualifier("com.test.service.smo.ITestDemoSMO")
    private ITestDemoSMO testDemoSMO;

    @Autowired
    @Resource
    private ITestDemoDAO testDemoDAO;

    @Before
    public void init() {
        log.info("===========TestJunit(Init)========");
    }

    @Test
    public void queryCountNum() {
        log.debug("测试数据");
        String[] arrKey = {"guangdong", "jiangsu", "sichuan", "zhejiang"};
        for (int i = 0; i < arrKey.length; i++) {
            String dbKey = arrKey[i];
            DBRouteContext.setName(dbKey);
            int countNum = testDemoSMO.queryCountNum();
            System.out.println(dbKey + "-----countNum=" + countNum);
        }

        if (true) {
            System.out.println(testDemoDAO.queryCountNum());
        }
    }
}
