package com.test.model;

import java.io.Serializable;

/**
 * Created by tanzepeng on 2016/11/28.
 */
public class SessionLoginUser implements Serializable {

    /**
     * Serializable
     */
    private static final long serialVersionUID = 1L;

    private String username;

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
