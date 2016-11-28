package com.test.model;

import java.io.Serializable;

/**
 * Created by tanzepeng on 2016/11/28.
 */
public class SessionLoginUser implements Serializable {

    /** Serializable */
    private static final long serialVersionUID = 1L;

    String userLoginCode;

    String userPassWord;

    public String getUserLoginCode() {
        return userLoginCode;
    }

    public void setUserLoginCode(String userLoginCode) {
        this.userLoginCode = userLoginCode;
    }

    public String getUserPassWord() {
        return userPassWord;
    }

    public void setUserPassWord(String userPassWord) {
        this.userPassWord = userPassWord;
    }
}
