package com.test.service.bmo;

import java.util.Map;

/**
 * Created by tanzepeng on 2015/6/16.
 */
@SuppressWarnings("JavaDoc")
public interface ITestDemoBMO {
    /**
     * @param areaId
     * @return List<TestModel>
     */
    public Map<String, Object> querySysParam(String areaId);

    public int queryCountNum();
}
