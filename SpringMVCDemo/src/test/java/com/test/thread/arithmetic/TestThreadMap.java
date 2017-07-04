package com.test.thread.arithmetic;import org.junit.Test;

import java.io.*;
import java.util.*;

/**
 * <p>
 * <p/>
 * </p>
 *
 * @author tanzp
 * @CreateDate 2017/6/5 19:27
 */
public class TestThreadMap implements Serializable {

    @Test
    public void test() {
        Map mHash = new HashMap<>();
        Map mTable = new Hashtable<>();
        Collections.synchronizedMap(mHash);
        Collections.synchronizedList(new ArrayList());
    }

    @Test
    public void testArr() {
        int f = -1;
        List l = new ArrayList(f);
    }

    @Test
    public void testObj() throws ClassNotFoundException {
        TestObj obj = new TestObj();

        byte[] bytes = serialize(obj);
        System.out.println(new String(bytes));

        Object objs = unserialize(bytes);

        if(objs instanceof TestObj){
            int v = ((TestObj) objs).getFlag();
            System.out.println(v);
        }
    }

    public byte[] serialize(Object obj) {

        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                baos.close();
                oos.close();
            } catch (IOException e) {
            }
            oos = null;
            baos = null;
        }
    }

    public Object unserialize(byte[] bytes) throws ClassNotFoundException {
        ByteArrayInputStream bais = null;
        Object obj = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                bais.close();
            } catch (IOException e) {
            }
            bais = null;
        }
    }


    class TestObj implements Serializable {
        private String id;
        private String name;
        transient int flag;

        public TestObj() {

        }

        public TestObj(String id, String name) {
            this.id = id;
            this.name = name;
        }

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

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }
    }
}
