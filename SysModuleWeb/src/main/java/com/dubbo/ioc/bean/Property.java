package com.dubbo.ioc.bean;

/**
 * Created by tanzepeng on 2016/12/28.
 */
public class Property {

    private String name;
    private String value;

    private String ref;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
