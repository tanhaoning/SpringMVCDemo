package com.spring.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 浏览器工具 类 概述 .
 * <p/>
 *
 * @author tang zheng yu
 * @version V1.0 2013-4-26
 * @createDate 2013-4-26 上午9:59:20
 * @copyRight 亚信联创电信EC研发部
 */
public class BrowserUtil {
    /**
     * IE语言(地区)
     */
    public static final String language = "language";
    /**
     * 用户代理信息
     */
    public static final String userAgent = "userAgent";
    /**
     * cookie是否能用
     */
    public static final String cookieEnabled = "cookieEnabled";
    /**
     * 操作系统
     */
    public static final String platform = "platform";

    /**
     * 获取浏览器名称和版本号
     *
     * @param userAgent 代理信息
     * @return List<String>  0:是名称,1:版本号
     */
    public static List<String> getBrowserName(String userAgent) {
        String browserName = userAgent.toLowerCase();
        String msieRegx = ".*msie.*";
        String operaRegx = ".*opera.*";
        String firefoxRegx = ".*firefox.*";
        String chromeRegx = ".*chrome.*";
        String webkitRegx = ".*webkit.*";
        String mozillaRegx = ".*mozilla.*";
        String safariRegx = ".*safari.*";
        String version = "";
        List<String> browserList = new ArrayList<String>();
        if (Pattern.matches(msieRegx, browserName) && !Pattern.matches(operaRegx, browserName)) {
            browserList.add("IE");
        } else if (Pattern.matches(firefoxRegx, browserName)) {
            browserList.add("Firefox");
        } else if (Pattern.matches(chromeRegx, browserName) && Pattern.matches(webkitRegx, browserName) && Pattern.matches(mozillaRegx, browserName)) {
            browserList.add("Chrome");
        } else if (Pattern.matches(operaRegx, browserName)) {
            browserList.add("Opera");
        } else if (Pattern.matches(safariRegx, browserName) && !Pattern.matches(chromeRegx, browserName) && Pattern.matches(webkitRegx, browserName) && Pattern.matches(mozillaRegx, browserName)) {
            browserList.add("Safari");
        } else {
            browserList.add("unknow");
        }
        if (!browserList.get(0).equals("unknow")) {
            if (browserList.get(0).equals("IE")) {
                version = browserName.substring(browserName.indexOf("msie"));
                version = version.split(";")[0].split(" ")[1];
                if (version != null && version.indexOf(".") > 0) {
                    version = version.substring(0, version.indexOf("."));
                }
            } else {
                version = browserName.substring(browserName.indexOf(browserList.get(0).toLowerCase()));
                version = version.split(" ")[0].split("/")[1];
                if (version != null && version.indexOf(".") > 0) {
                    version = version.substring(0, version.indexOf("."));
                }
            }
        }
        browserList.add(version);
        return browserList;
    }

    public static String fromCharCode(int... codePoints) {
        StringBuilder builder = new StringBuilder(codePoints.length);
        for (int codePoint : codePoints) {
            builder.append(Character.toChars(codePoint));
        }
        return builder.toString();
    }

    /**
     * 获取浏览器名称和版本号
     *
     * @param userAgent 代理信息
     * @return boolean  true:是ipad浏览器
     */
    public static boolean isIpadOrIphone(String userAgent) {
        String browserName = userAgent.toLowerCase();
        String ipadRegx = ".*ipad.*";
        String iphoneRegx = ".*iphone.*";
        if (Pattern.matches(ipadRegx, browserName) || Pattern.matches(iphoneRegx, browserName)) {
            return true;
        } else {
            return false;
        }
    }
}
