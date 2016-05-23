package com.test.annotation;

/**
 * Created by tanzepeng on 2015/8/20.
 */
@Table("User")
public class User {
    @Columns("ID")
    private String id;

    @Columns("AGE")
    private int age;

    @Columns("NAME")
    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
