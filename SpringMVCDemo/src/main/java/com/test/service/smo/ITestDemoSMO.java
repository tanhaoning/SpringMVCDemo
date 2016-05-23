package com.test.service.smo;

import com.test.model.TestModel;

import java.util.List;
import java.util.Map;

/**
 * Created by tanzepeng on 2015/6/16.
 */
public interface ITestDemoSMO {

    public Map<String, Object> querySysParam(String areaId);

    public int queryCountNum();
}
