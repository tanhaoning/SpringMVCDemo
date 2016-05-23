package com.test.json;

import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tanzepeng on 2015/9/6.
 */
public class JsonObjectTest {

    public static void main(String[] args) {
        JSONObject json = new JSONObject();
        json.put("string", "haha");
        json.put("int", 1);
        List list0 =new ArrayList();
        list0.add("begin");
        json.put("list", list0);
        System.out.println("json = " + json);
        json.put("string", "sss");
        System.out.println("json = " + json);
        json.element("sssss", "gfgffgfg");
        List list  = new ArrayList();
        list.add("nihao");
        list.add("buhao");
        json.element("list", list);
        System.out.println("json = " + json);

        String testNull = null;
        json.put("null", testNull);
        System.out.println("json = " + json);

        json.accumulate("list", "123");
        System.out.println("json = " + json);
        json.accumulate("string", "123");
        System.out.println("json = " + json);
    }
}
