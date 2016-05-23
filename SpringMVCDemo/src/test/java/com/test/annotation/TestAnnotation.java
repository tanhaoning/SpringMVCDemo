package com.test.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by tanzepeng on 2015/8/20.
 */
public class TestAnnotation {

    public static void main(String[] args) {
        User u1 = new User();
        u1.setAge(18);
        User u2 = new User();
        u2.setName("小名");
        User u3 = new User();
        u3.setAge(15);
        u3.setName("小花");
        System.out.println(annParse(u1));
        System.out.println(annParse(u2));
        System.out.println(annParse(u3));


        Person p1 = new Person();
        p1.setSex("男");
        Person p2 = new Person();
        p2.setMail("xx@163.com,gg@qq.com");
        System.out.println(annParse(p1));
        System.out.println(annParse(p2));
    }

    public static String annParse(Object object) {
        StringBuilder sb = new StringBuilder();

        Class c = object.getClass();
        boolean tExist = c.isAnnotationPresent(Table.class);
        if (!tExist) {
            return null;
        }
        Table table = (Table) c.getAnnotation(Table.class);
        String tableName = table.value();
        sb.append("select * from ").append(tableName).append(" where 1=1 ");
        Field[] arrField = c.getDeclaredFields();
        for (Field field : arrField) {
            boolean fExist = field.isAnnotationPresent(Columns.class);
            if (!fExist) {
                continue;
            }
            Columns columns = field.getAnnotation(Columns.class);
            String columnsName = columns.value();
            // 获取字段的值
            Object fieldValue = null;
            try {
                Method method = c.getMethod("get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1));
                fieldValue = method.invoke(object);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (null == fieldValue || (fieldValue instanceof Integer && (Integer) fieldValue == 0)) {
                continue;
            }
            sb.append(" and ").append(columnsName);
            if (fieldValue instanceof String) {
                if (((String) fieldValue).contains(",")) {
                    String[] splits = ((String) fieldValue).split(",");
                    sb.append(" in (");
                    for (String split : splits) {
                        sb.append("'").append(split).append("',");
                    }
                    sb.deleteCharAt(sb.length() - 1);
                    sb.append(")");
                } else {
                    sb.append(" = ").append("'").append(fieldValue).append("'");
                }
            } else {
                sb.append(" = ").append(fieldValue);
            }
        }
        return sb.toString();
    }
}
