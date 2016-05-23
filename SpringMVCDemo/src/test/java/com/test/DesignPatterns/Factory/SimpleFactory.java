package com.test.DesignPatterns.Factory;

/**
 * 简单工厂
 * Created by tanzepeng on 2016/3/3.
 */
public class SimpleFactory {
    private static IHair getHair(String model) {
       /* if ("left".equals(model)) {
            return new LeftHair();
        } else if ("right".equals(model)) {
            return new RightHair();
        } else if ("center".equals(model)){
            return new CenterHair();
        }else{
            return null;
        }*/
        IHair hair = null;
        try {
            hair = (IHair) Class.forName(model).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return hair;
    }

    public static void main(String[] args) {
        IHair hair = SimpleFactory.getHair("com.test.DesignPatterns.Factory.LeftHair");
        hair.draw();
    }
}

interface IHair {
    public void draw();
}

class LeftHair implements IHair {
    public void draw() {
        System.out.println("------------左分发型------------");
    }
}

class RightHair implements IHair {
    public void draw() {
        System.out.println("------------右分发型------------");

    }
}

class CenterHair implements IHair {
    public void draw() {
        System.out.println("------------中分发型------------");

    }
}

