package com.dubbo.ioc.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tanzepeng on 2016/12/28.
 */
public class BeanObject {

    private String id;

    private String name;

    private List<Property> propertys = new ArrayList<Property>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Property> getPropertys() {
        return propertys;
    }

    public void setPropertys(List<Property> property) {
        this.propertys = propertys;
    }
}
