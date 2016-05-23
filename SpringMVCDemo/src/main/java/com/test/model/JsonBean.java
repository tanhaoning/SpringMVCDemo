package com.test.model;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.List;

/**
 * Created by tanzepeng on 2016/3/4.
 */
public class JsonBean {
    private boolean isSuccess = true;

    private int code = 0;

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private List<String> errorList = null;

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private Object data = null;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<String> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<String> errorList) {
        this.errorList = errorList;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
