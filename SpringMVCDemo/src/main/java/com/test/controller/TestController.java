package com.test.controller;

import com.spring.utils.DateUtil;
import com.spring.utils.StringUtil;
import com.test.annotation.AuthValid;
import com.test.annotation.LogRecord;
import com.test.dbroute.DBRouteContext;
import com.test.model.JsonBean;
import com.test.model.TestModel;
import com.test.service.smo.ITestDemoSMO;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * Created by tanzepeng on 2015/6/9.
 */
@Controller("com.test.controller.TestController")
@RequestMapping("/test/*")
public class TestController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @Autowired
    @Qualifier("com.test.service.smo.ITestDemoSMO")
    private ITestDemoSMO testDemoSMO;

    @Autowired
    private MessageSource messageSource;

    @AuthValid(validate = false)
    @LogRecord(isSwitch = false)
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(@RequestParam Map<String, Object> paramMap, Model model, HttpServletRequest request) {
        model.addAttribute("session", request.getSession().getId());
        return "/bootstrap/login";
    }

    @AuthValid(validate = false)
    @LogRecord(isSwitch = false)
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody Map<String, Object> paramMap, Model model, HttpServletRequest request) {
        if (!MapUtils.isEmpty(paramMap) && paramMap.containsKey("areaId")) {
            String areaId = MapUtils.getString(paramMap, "areaId");
            log.info("[areaId={}]", areaId);
        }
        String str = request.getParameter("queryString");
        if (StringUtil.isEmptyStr(str)) {
            log.info("[{}] is Empty!!", str);
        }
        DateUtil.getFormatTimeString(new Date(), "yyyy-dd-mm");
        model.addAttribute("session", request.getSession().getId());
        //request.getSession().invalidate();
        return "/player";
        //return "redirect:/test.js/queryTestInfo";
    }

    @RequestMapping(value = "/getPlayResource", method = {RequestMethod.GET})
    @ResponseBody
    public JsonBean getPlayResource(Model model, HttpServletRequest request, HttpServletResponse response) {
        JsonBean jsonBean = new JsonBean();
        try {
            //Thread.currentThread().sleep(6000);
            String param = request.getParameter("test");
            String msg = request.getParameter("msg");
            String dir = "play_list.txt";
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(dir);
            //String fileDir = this.getClass().getClassLoader().getResource(dir).getFile();
            /*File file = new File(fileDir);
            if (!file.exists()) {
                throw new FileNotFoundException();
            }*/
            //BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "utf8"));
            //BufferedReader br = new BufferedReader(new FileReader(fileDir));
            String temp = null;
            StringBuffer sb = new StringBuffer();
            temp = br.readLine();
            while (temp != null) {
                sb.append(temp);
                temp = br.readLine();
            }
            String strJson = sb.toString();
            jsonBean.setCode(0);
            jsonBean.setData(strJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonBean;
    }

    @LogRecord(isSwitch = true)
    @RequestMapping(value = "/queryTestInfo", method = {RequestMethod.GET})
    public String queryTestInfo(Model model) {
        String labelTestUS = messageSource.getMessage("label.test", new Object[]{}, Locale.US);
        String labelTestCHINA = messageSource.getMessage("label.test", new Object[]{}, Locale.CHINA);

        String labelUS = messageSource.getMessage("label", new Object[]{}, Locale.US);
        String labelCHINA = messageSource.getMessage("label", new Object[]{}, Locale.CHINA);

        System.out.println("**********labelTestUS=" + labelTestUS);
        System.out.println("**********labelTestCHINA=" + labelTestCHINA);
        System.out.println("**********labelUS=" + labelUS);
        System.out.println("**********labelCHINA=" + labelCHINA);
        TestModel testModel = new TestModel();
        testModel.setParamCode("testCode");
        testModel.setParamValue("testValue");
        model.addAttribute("test", testModel);
        return "/indexFor";
    }

    @RequestMapping(value = "/querySysParam", method = {RequestMethod.GET})
    public String querySysParam(HttpServletRequest request) {
        String areaId = request.getParameter("areaId");
        Map<String, Object> resultMap = testDemoSMO.querySysParam(areaId);
        if (resultMap != null) {
            System.out.println(resultMap);
            return "/bootstrap/login";
        }
        return null;
    }

    @RequestMapping(value = "/queryCountNum", method = {RequestMethod.GET})
    public String queryCountNum(HttpServletRequest request) {
        String dbKey = request.getParameter("dbKey");
        DBRouteContext.setName(dbKey);
        int countNum = testDemoSMO.queryCountNum();
        System.out.println("输出结果【" + dbKey + "】为【" + countNum + "】");
        return "/bootstrap/login";
    }

    @AuthValid(validate = true)
    @RequestMapping(value = "/testLog4j", method = {RequestMethod.GET, RequestMethod.POST})
    public void testLog4j() {
        log.trace("trace message");
        log.debug("debug message");
        log.info("info message");
        log.warn("warn message");
        log.error("error message");
        System.out.println("Hello World!");
    }

    @RequestMapping(value = "/setCookie", method = {RequestMethod.GET, RequestMethod.POST})
    public void setCookie() {

    }
}
