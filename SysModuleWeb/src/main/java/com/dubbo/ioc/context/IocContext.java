package com.dubbo.ioc.context;


import com.dubbo.ioc.bean.Address;
import com.dubbo.ioc.bean.BeanObject;
import com.dubbo.ioc.bean.Property;
import com.dubbo.ioc.bean.User;
import com.dubbo.ioc.factory.BeanFactory;
import com.sun.org.apache.xerces.internal.impl.dv.xs.TypeValidator;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by tanzepeng on 2016/12/28.
 */
public class IocContext implements BeanFactory {

    private final static String IOC_LOCATION = "/applicationContext.xml";

    private final static String NODE_BEAN = "bean";

    public static final String ID_ATTRIBUTE = "id";

    public static final String CLASS_ATTRIBUTE = "class";

    public static final String PROPERTY_ELEMENT = "property";


    public static final String NAME_ATTRIBUTE = "name";

    public static final String VALUE_ATTRIBUTE = "value";

    public static final String REF_ATTRIBUTE = "ref";

    private final Map<String, BeanObject> beanObjectMap = new ConcurrentHashMap<String, BeanObject>();
    private Map<String, Object> objectMap = new ConcurrentHashMap<String, Object>();

    public IocContext(String location) {

        // 创建xml解析器工厂
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        // 创建xml解析器
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
            // 包路径
            InputStream input = IocContext.class.getResourceAsStream(location);

            Document doc = builder.parse(input);

            // 初始化容器
            initIocContext(doc);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void initIocContext(Document doc) {
        Element root = doc.getDocumentElement();
        parseElement(root);
        //parseBean();
    }

    private void parseBean() {
        for (Map.Entry<String, BeanObject> entry : beanObjectMap.entrySet()) {
            String id = entry.getKey();
            BeanObject bean = entry.getValue();

            Object obj = createBean(bean);

            objectMap.put(bean.getId(), obj);
        }
    }

    private Object createBean(BeanObject bean) {
        Class objClass = null;
        Object obj = null;

        String className = bean.getName();
        try {
            objClass = Class.forName(className);

            if (null != objClass) {
                obj = objClass.newInstance();
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if (null != objClass) {
            for (Property property : bean.getPropertys()) {
                Field[] fields = objClass.getDeclaredFields();
                for (Field filed : fields) {
                    if (StringUtils.isNotBlank(property.getRef())) {
                        Object refObject = objectMap.get(property.getRef());
                        if (null == refObject) { // 未实例化
                            refObject = createBean(beanObjectMap.get(property.getRef()));
                        }
                        if (property.getRef().equals(filed.getName())) {
                            try {
                                PropertyDescriptor pd = new PropertyDescriptor(filed.getName(), objClass);
                                //获得set方法
                                Method method = pd.getWriteMethod();
                                method.invoke(obj, refObject);
                            } catch (IntrospectionException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    } else { // name value
                        if (property.getName().equals(filed.getName())) {
                            try {
                                PropertyDescriptor pd = new PropertyDescriptor(filed.getName(), objClass);
                                //获得set方法
                                Method method = pd.getWriteMethod();
                                method.invoke(obj, property.getValue());
                            } catch (IntrospectionException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        return obj;
    }

    /*private String convertSetMethodName(String method) {
        return "get" + method.substring(0, 1).toUpperCase() + method.substring(1);
    }

    private String convertGetMethodName(String method) {
        return "get" + method.substring(0, 1).toUpperCase() + method.substring(1);
    }*/

    private void parseElement(Element root) {
        NodeList nodeList = root.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node instanceof Element) {
                Element ele = (Element) node;
                parseElementNode(ele);
            }
        }
    }


    private void parseElementNode(Element ele) {
        if (NODE_BEAN.equals(ele.getNodeName()) || NODE_BEAN.equals(ele.getLocalName())) {
            BeanObject beanObject = new BeanObject();
            beanObject.setId(ele.getAttribute(ID_ATTRIBUTE));
            beanObject.setName(ele.getAttribute(CLASS_ATTRIBUTE).trim());

            List<Property> propertyList = new ArrayList<Property>();
            NodeList nodeList = ele.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node instanceof Element) {
                    Element e = (Element) node;
                    parsePropertyElement(e, propertyList);
                }
            }
            beanObject.getPropertys().addAll(propertyList);
            beanObjectMap.put(ele.getAttribute(ID_ATTRIBUTE), beanObject);
        }
    }

    private void parsePropertyElement(Element ele, List<Property> propertyList) {
        if (PROPERTY_ELEMENT.equals(ele.getNodeName()) || PROPERTY_ELEMENT.equals(ele.getLocalName())) {
            Property property = new Property();
            property.setName(ele.getAttribute(NAME_ATTRIBUTE));
            property.setValue(ele.getAttribute(VALUE_ATTRIBUTE));
            property.setRef(ele.getAttribute(REF_ATTRIBUTE));
            propertyList.add(property);
        }
    }

    public Object getBean(String id) throws Exception {
        parseBean();
        return objectMap.get(id);
    }

    public static void main(String[] args) throws Exception {
        IocContext context = new IocContext("/ioc" + IOC_LOCATION);
        User user = (User) context.getBean("user");
        System.out.println(user.getUserName());
        Address ad = user.getAddress();
        System.out.println(ad.getCity());
    }
}
