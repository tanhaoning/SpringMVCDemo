package com.test.annotation;

/**
 * Created by tanzepeng on 2015/8/20.
 */
@Table("User")
public class Person {
    @Columns("ID")
    private String id;

    @Columns("AGE")
    private int age;

    @Columns("SEX")
    private String sex;

    @Columns("MAIL")
    private String mail;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
