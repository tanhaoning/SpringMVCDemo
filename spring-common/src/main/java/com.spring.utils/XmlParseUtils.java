package com.spring.utils;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

/**
 * Created by tanzepeng on 2016/12/22.
 * <p/>
 * jaxp Dom模型解析xml
 */
public class XmlParseUtils {

    public static String FILERESOURCE = "xmlres/bookstore.xml";


    public static void main(String[] args) throws Exception {
        // 创建xml解析器工厂
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        // 创建xml解析器
        DocumentBuilder builder = factory.newDocumentBuilder();

        /**
         * <pre>
         * 获取到Document对象,Document对象代表着DOM树的根节点
         * 使用Document对象可以对XML文档的数据进行基本操作
         * Document对象在DOM解析模型中也属于一个Node对象,
         * * 它是其他Node对象所在的上下文,其他Node对象包括
         * Element,Text,Attr,Comment,Processing Instruction等等
         * 根节点 != 根元素,它们是包含关系
         * </pre>
         */

        // 包路径
        InputStream input = XmlParseUtils.class.getResourceAsStream('/' + FILERESOURCE);
        Document document = builder.parse(input);

        // 文件绝对路径
        //Document document = builder.parse(new File("E:\\bookstore.xml"));

        /**
         * <pre>
         * 获取到XML文档的根元素
         * 对于XML中的Document是XML文档的根节点,而它的子元素是XML文档的XML文档根元素
         * </pre>
         */
        Element root = document.getDocumentElement();
        System.out.println("Xml文档根节点标签名称：" + root.getTagName() + ",节点名称:" + root.getNodeName());

        /**
         * <pre>
         * 获取根元素节点的子节点
         * 对于XML文档而言,无论是Document,Elment,Text等等,
         * 它们在DOM解析模型中都属性一个Node,因此这里需要注意的一点是
         * 空白字符在DOM解析中也会被作为一个Node元素来处理。
         * </pre>
         */
        // 获取到根元素的子节点
        NodeList nodeList = root.getChildNodes();
        System.out.println("root节点子元素:" + nodeList.getLength());

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node instanceof Element) {
                Element element = (Element) node;
                // 获取节点属性
                NamedNodeMap nodeMap = element.getAttributes();
                System.out.println(nodeMap.getLength());
                for (int j = 0; j < nodeMap.getLength(); j++) {
                    Node nodeAttr = nodeMap.item(j);
                    System.out.println(nodeAttr.getNodeValue());
                    if (nodeAttr instanceof Attr) {
                        System.out.println("Attr");
                    }

                }
            }
        }
    }
}
