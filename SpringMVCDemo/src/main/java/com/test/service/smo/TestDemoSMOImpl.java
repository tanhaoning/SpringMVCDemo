package com.test.service.smo;

import com.test.model.TestModel;
import com.test.service.bmo.ITestDemoBMO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by tanzepeng on 2015/6/16.
 */
@Service("com.test.service.smo.ITestDemoSMO")
public class TestDemoSMOImpl implements ITestDemoSMO {

    @Autowired
    @Qualifier("com.test.service.bmo.ITestDemoBMO")
    private ITestDemoBMO testDemoBMO;

    public Map<String, Object> querySysParam(String areaId) {
        return testDemoBMO.querySysParam(areaId);
    }

    public int queryCountNum() {
        return testDemoBMO.queryCountNum();
    }
}
