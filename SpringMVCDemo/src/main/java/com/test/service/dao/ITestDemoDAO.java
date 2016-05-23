package com.test.service.dao;

import com.test.model.TestModel;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by tanzepeng on 2015/6/16.
 */
@Repository
public interface ITestDemoDAO {

    public List<TestModel> querySysParam(String areaId);

    public int queryCountNum();
}
