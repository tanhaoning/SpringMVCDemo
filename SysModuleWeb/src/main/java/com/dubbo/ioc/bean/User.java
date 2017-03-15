package com.dubbo.ioc.bean;

/**
 * Created by tanzepeng on 2016/12/28.
 */
public class User {

    private String userName;
    private Address address;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

   /* @Override
    public String toString() {
        return getUserName() + "'s" + getAddress().toString();
    }*/
}
