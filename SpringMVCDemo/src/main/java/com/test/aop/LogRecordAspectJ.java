package com.test.aop;

import com.test.annotation.LogRecord;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by tanzepeng on 2015/11/10.
 */
@Component
@Aspect
public class LogRecordAspectJ {

    private static final Logger log = LoggerFactory.getLogger(LogRecordAspectJ.class);
    public static final String ASPECT_SERVICE_EXCUTION = "execution(* com.leanyu.lcsystem.service.*.*(..))";

    // 前置/后置/异常通知的函数都没有返回值,只有环绕通知有返回值
    /**
     * 方法调用之前
     */
    //@Before("within(com.test.controller.*) && @annotation(lr)")
    public void addLogInfoBefore(JoinPoint jp, LogRecord lr) {
        Object[] params = jp.getArgs(); //获取方法参数
        String param = parseParames(params);
        String classNameFor = jp.getTarget().getClass().toString();// 获取目标类名
        String className = classNameFor.substring(classNameFor.indexOf("com"));
        System.out.println("ClassNanemFor=[" + classNameFor + "],ClassNanem=[" + className + "],");
    }

    /**
     * 方法调用之后
     */
    //@After
    public Object addLogInfoAfter() {
        return new Object();
    }

    /**
     * 返回成功后调用,具有可以指定返回值
     */
    //@AfterReturning("within(com.test.controller.*) && @annotation(lr)")
    public void addLogInfoAfterReturning(JoinPoint jp, LogRecord lr) {
        if (lr.isSwitch()) {
            Object[] parames = jp.getArgs();//获取目标方法体参数
            //String params = parseParames(parames); //解析 目标方法体的参数
            String className = jp.getTarget().getClass().toString();//获取目标类名
            className = className.substring(className.indexOf("com"));
            String signature = jp.getSignature().toString();//获取目标方法签名
            String methodName = signature.substring(signature.lastIndexOf(".") + 1, signature.indexOf("("));
            System.out.println(".........addLogInfoAfterReturning.........");
        }
    }

    /**
     * 环绕通知
     */
    @Around("within(com.test.controller.*) && @annotation(lr)")
    public Object addLogInfoAround(ProceedingJoinPoint pjp, LogRecord lr) throws Throwable {
        log.info("AOP环绕通知开始.....");
        Object[] params = pjp.getArgs();
        int i = 0;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        for (Object param : params) {
            if (0 == i) {
                paramMap.put("inParam", param.toString());
            } else {
                paramMap.put("inParam" + i, param.toString());
            }
            ++i;
        }
        for (Map.Entry ent : paramMap.entrySet()) {
            System.out.println(ent.getKey() + " = " + ent.getValue());
        }
        Object object = pjp.proceed();
        if (object instanceof String) {
            System.out.println(object.toString());
        }
        log.info("AOP环绕通知结束.....");
        return object;
    }

    /**
     * 异常通知
     */
    //@AfterThrowing
    public Object addLogInfoAfterThrowing() {
        return new Object();
    }

    /**
     * 根据包名查询检索其所属的模块
     *
     * @param packageName 包名
     * @return 模块名称
     */
    private String getModelName(String packageName) {
        String modelName = "";
        SAXReader reader = new SAXReader();
        try {
            //读取project.xml 模块信息描述xml文档
            File proj = ResourceUtils.getFile("classpath:project.xml");
            Document doc = reader.read(proj);
            //获取文档根节点
            Element root = doc.getRootElement();
            //查询模块名称
            modelName = searchModelName(root, packageName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelName;
    }

    /**
     * 采用递归方式根据包名逐级检索所属模块
     *
     * @param element     元素节点
     * @param packageName 包名
     * @return 模块名称
     */
    private String searchModelName(Element element, String packageName) {
        String modelName = searchModelNodes(element, packageName);
        //若将包名解析到最后的根目录后仍未检索到模块名称，则返回空
        if (packageName.lastIndexOf(".") == -1) {
            return modelName;
        }
        //逐级检索模块名称
        if (modelName.equals("")) {
            packageName = packageName.substring(0, packageName.lastIndexOf("."));
            modelName = searchModelName(element, packageName);
        }
        return modelName;
    }

    /**
     * 根据xml文档逐个节点检索模块名称
     *
     * @param element     节点元素
     * @param packageName 包名
     * @return 模块名称
     */
    @SuppressWarnings("unchecked")
    private String searchModelNodes(Element element, String packageName) {

        String modelName = "";
        Element modules = element.element("modules");
        Iterator it = modules.elementIterator();
        if (!it.hasNext()) {
            return modelName;
        }
        while (it.hasNext()) {
            Element model = (Element) it.next();
            String pack = model.attributeValue("packageName");
            String name = model.elementText("moduleCHPath");
            if (packageName.equals(pack)) {
                modelName = name;
                return modelName;
            }
            if (modelName != null && !modelName.equals("")) {
                break;
            }
            modelName = searchModelNodes(model, packageName);
        }

        return modelName;
    }

    /**
     * 解析方法参数
     *
     * @param parames 方法参数
     * @return 解析后的方法参数
     */
    private String parseParames(Object[] parames) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < parames.length; i++) {
            if (parames[i] instanceof Object[] || parames[i] instanceof Collection) {
                JSONArray json = JSONArray.fromObject(parames[i]);
                if (i == parames.length - 1) {
                    sb.append(json.toString());
                } else {
                    sb.append(json.toString() + ",");
                }
            } else {
                JSONObject json = JSONObject.fromObject(parames[i]);
                if (i == parames.length - 1) {
                    sb.append(json.toString());
                } else {
                    sb.append(json.toString() + ",");
                }
            }
        }
        String params = sb.toString();
        params = params.replaceAll("(\"\\w+\":\"\",)", "");
        params = params.replaceAll("(,\"\\w+\":\"\")", "");
        return params;
    }
}
