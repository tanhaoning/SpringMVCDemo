package com.test.service.bmo;

import com.test.model.TestModel;
import com.test.service.dao.ITestDemoDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tanzepeng on 2015/6/16.
 */
@Service("com.test.service.bmo.ITestDemoBMO")
public class TestDemoBMOImpl implements ITestDemoBMO {

    private static Logger log = LoggerFactory.getLogger(TestDemoBMOImpl.class);

    @Autowired
    private ITestDemoDAO testDemoDAO;

    public Map<String, Object> querySysParam(String areaId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            areaId = areaId == null ? null : areaId;
            List<TestModel> testModelList = testDemoDAO.querySysParam(areaId);
            for (TestModel testModel : testModelList) {
                if (resultMap.get(testModel.getParamCode()) == null) {
                    resultMap.put(testModel.getParamCode(), testModel.getParamValue());
                }
            }
            log.debug("result={}", testModelList);
            log.debug("resultMap={}", resultMap);
        } catch (Exception e) {
            log.debug("error=", e);
        }
        return resultMap;
    }

    public int queryCountNum() {
        return testDemoDAO.queryCountNum();
    }
}
