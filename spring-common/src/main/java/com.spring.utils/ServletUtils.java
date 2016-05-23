package com.spring.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

/**
 * Servlet 工具类.
 * <BR>
 * 主要包含 常量，cookie,header,ip 操作
 * <p/>
 *
 * @author tangzhengyu
 * @version V1.0 2011-12-22
 * @createDate 2011-12-22 下午11:31:00
 * @modifyDate tang  2011-12-22<BR>
 * @copyRight 亚信联创电信CRM研发部
 */
public class ServletUtils {

    //-- Content Type 定义 --//
    /**
     * Content Type 文本.
     */
    public static final String TEXT_TYPE = "text/plain";
    /**
     * Content Type json.
     */
    public static final String JSON_TYPE = "application/json";
    /**
     * Content Type xml.
     */
    public static final String XML_TYPE = "text/xml";
    /**
     * Content Type html.
     */
    public static final String HTML_TYPE = "text/html";
    /**
     * Content Type javascript.
     */
    public static final String JS_TYPE = "text/javascript";
    /**
     * Content Type excel.
     */
    public static final String EXCEL_TYPE = "application/vnd.ms-excel";

    /**
     * -- Header 定义.
     */
    public static final String AUTHENTICATION_HEADER = "Authorization";

    /**
     * 一年秒数值.
     */
    public static final long ONE_YEAR_SECONDS = 60 * 60 * 24 * 365;
    /**
     * 一月数值.
     */
    public static final long ONE_MONTH_SECONDS = 60 * 60 * 24 * 30;
    /**
     * 一周数值.
     */
    public static final int ONE_WEEK_SECONDS = 60 * 60 * 24 * 6;
    /**
     * 一天数值.
     */
    public static final int ONE_DAY_SECONDS = 60 * 60 * 24;
    /**
     * 半天数值.
     */
    public static final int HALF_DAY_SECONDS = 60 * 60 * 12;
    /**
     * 1/4天数值.
     */
    public static final int HALF_FOUR_DAY_SECONDS = 60 * 60 * 6;
    /**
     * 一小时数值.
     */
    public static final int ONE_HOUR_SECONDS = 60 * 60;
    /**
     * 半小时数值.
     */
    public static final int HALF_HOUR_SECONDS = 60 * 30;

    /**
     * 常量:1000 数值.
     */
    public static final int NUM_THOUND = 1000;
    /**
     * UTF-8.
     */
    public static final String DEFAULT_ENCODING = "UTF-8";
    /**
     * UTF-8.
     */
    public static final String CHARACTERSET_UTF8 = "UTF-8";

    /**
     * serviceAxClient.js.
     */
    public static final String JS_FILE_SERVICEAXCLIENT = "/serviceAxClient.js";
    /**
     * HTTP etag header.
     */
    public static final String HEADER_ETAG = "ETag";
    /**
     * HTTP etag equivalent of HEADER_IF_MODIFIED.
     */
    public static final String HEADER_IF_NONE = "If-None-Match";

    /**
     * HTTP header for when a file was last modified.
     */
    public static final String HEADER_LAST_MODIFIED = "Last-Modified";

    /**
     * HTTP header to request only modified data.
     */
    public static final String HEADER_IF_MODIFIED = "If-Modified-Since";

    /**
     * The name of the user agent HTTP header.
     */
    public static final String HEADER_USER_AGENT = "User-Agent";

    /**
     * 取得HttpSession的简化函数.
     *
     * @param request HttpServletRequest
     * @return HttpSession HttpSession
     */
    public static HttpSession getSession(HttpServletRequest request) {
        if (request != null) {
            return request.getSession();
        } else {
            return null;
        }
    }

    /**
     * 取得HttpSession的简化函数.
     *
     * @param isNew   true: not exist be  createed
     * @param request HttpServletRequest
     * @return HttpSession HttpSession
     */
    public static HttpSession getSession(HttpServletRequest request, boolean isNew) {
        if (request != null) {
            return request.getSession(isNew);
        } else {
            return null;
        }
    }

    /**
     * 取得HttpSession(false)中Attribute的简化函数.
     *
     * @param name    key
     * @param request HttpServletRequest
     * @return HttpSession HttpSession
     */
    public static Object getSessionAttribute(HttpServletRequest request, String name) {
        HttpSession session = getSession(request, false);
        return session != null ? session.getAttribute(name) : null;
    }

    /**
     * 设置HttpSession中Attribute的简化函数.
     *
     * @param sessionKey   sessionKey
     * @param sessionValue sessionValue
     * @param request      HttpServletRequest
     */
    public static void setSessionAttribute(HttpServletRequest request, String sessionKey, Object sessionValue) {
        HttpSession session = getSession(request, false);
        if (session != null) {
            session.setAttribute(sessionKey, sessionValue);
        }
    }

    /**
     * 删除HttpSession中Attribute的简化函数.
     */
    public static void removeSessionAttribute(HttpServletRequest request, String sessionKey) {
        HttpSession session = getSession(request, false);
        if (session != null) {
            session.removeAttribute(sessionKey);
        }
    }

    /**
     * 设置客户端缓存过期时间 Header.
     *
     * @param response       HttpServletResponse
     * @param expiresSeconds 秒
     */
    public static void setExpiresHeader(HttpServletResponse response, long expiresSeconds) {
        //Http 1.0 header
        response.setDateHeader("Expires", System.currentTimeMillis() + expiresSeconds * NUM_THOUND);
        //Http 1.1 header
        response.setHeader("Cache-Control", "private, max-age=" + expiresSeconds);
    }

    /**
     * 设置客户端无缓存Header.
     *
     * @param response HttpServletResponse
     */
    public static void setNoCacheHeader(HttpServletResponse response) {
        //Http 1.0 header
        response.setDateHeader("Expires", 0);
        response.addHeader("Pragma", "no-cache");
        //Http 1.1 header
        response.setHeader("Cache-Control", "no-cache");
    }

    /**
     * 设置LastModified Header.
     *
     * @param response         HttpServletResponse
     * @param lastModifiedDate 过期日期，long型
     */
    public static void setLastModifiedHeader(HttpServletResponse response, long lastModifiedDate) {
        response.setDateHeader("Last-Modified", lastModifiedDate);
    }

    /**
     * 设置Etag Header.
     *
     * @param response HttpServletResponse
     * @param etag     etag
     */
    public static void setEtag(HttpServletResponse response, String etag) {
        response.setHeader("ETag", etag);
    }

    /**
     * 根据浏览器If-Modified-Since Header, 计算文件是否已被修改.
     * <BR>
     * 如果无修改, checkIfModify返回false ,设置304 not modify status.
     * <p/>
     *
     * @param request      HttpServletRequest
     * @param response     HttpServletResponse
     * @param lastModified 内容的最后修改时间.
     * @return true 文件是否已被修改
     */
    public static boolean checkIfModifiedSince(HttpServletRequest request, HttpServletResponse response,
                                               long lastModified) {
        long ifModifiedSince = request.getDateHeader("If-Modified-Since");
        if ((ifModifiedSince != -1) && (lastModified < ifModifiedSince + NUM_THOUND)) {
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            return false;
        }
        return true;
    }

    /**
     * 根据浏览器 If-None-Match Header, 计算Etag是否已无效.
     * <p/>
     * 如果Etag有效, checkIfNoneMatch返回false, 设置304 not modify status.
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param etag     内容的ETag.
     * @return boolean etag是否失效
     */
    public static boolean checkIfNoneMatchEtag(HttpServletRequest request, HttpServletResponse response, String etag) {
        String headerValue = request.getHeader("If-None-Match");
        if (headerValue != null) {
            boolean conditionSatisfied = false;
            if (!"*".equals(headerValue)) {
                StringTokenizer commaTokenizer = new StringTokenizer(headerValue, ",");

                while (!conditionSatisfied && commaTokenizer.hasMoreTokens()) {
                    String currentToken = commaTokenizer.nextToken();
                    if (currentToken.trim().equals(etag)) {
                        conditionSatisfied = true;
                    }
                }
            } else {
                conditionSatisfied = true;
            }

            if (conditionSatisfied) {
                response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                response.setHeader("ETag", etag);
                return false;
            }
        }
        return true;
    }

    /**
     * 设置让浏览器弹出下载对话框的Header.
     * <p/>
     *
     * @param response HttpServletResponse
     * @param fileName 下载后的文件名.
     */
    public static void setFileDownloadHeader(HttpServletResponse response, HttpServletRequest request, String fileName) {
        try {
            //中文文件名支持
            String userAgent = request.getHeader("User-Agent");
            String encodedfileName = fileName;
            if ("IE".equalsIgnoreCase(BrowserUtil.getBrowserName(userAgent).get(0))) {
                encodedfileName = URLEncoder.encode(fileName, "UTF8");
            } else {
                encodedfileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
            }
            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + "\"");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 取得带相同前缀的Request Parameters.
     * <p/>
     * 返回的结果Parameter名已去除前缀.
     *
     * @param request HttpServletRequest
     * @param prefix  前缀
     * @return Map  带相同前缀参数
     */
    @SuppressWarnings("unchecked")
    public static Map<String, ?> getParametersStartingWith(HttpServletRequest request, String prefix) {
        Enumeration<Object> paramNames = request.getParameterNames();
        Map<String, Object> params = new TreeMap<String, Object>();
        if (prefix == null) {
            prefix = "";
        }
        while (paramNames != null && paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            if ("".equals(prefix) || paramName.startsWith(prefix)) {
                String unprefixed = paramName.substring(prefix.length());
                String[] values = request.getParameterValues(paramName);
                //NOSONAR
                if (values != null && values.length > 0) {
                    if (values.length > 1) {
                        params.put(unprefixed, values);
                    } else {
                        params.put(unprefixed, values[0]);
                    }
                }
            }
        }
        return params;
    }

    /**
     * 对Http Basic验证的 Header进行编码.
     * <p/>
     *
     * @param userName 用户名
     * @param password 密码
     * @return String  Header进行编码
     */
    public static String encodeHttpBasic(String userName, String password) {
        String encode = userName + ":" + password;
        return "Basic " + EncodeUtils.base64Encode(encode.getBytes());
    }

    /**
     * 根据传入的cookie name取得客户端请求的cookie.
     * <p/>
     *
     * @param request http的请求
     * @param name    cookie名称
     * @return Cookie 如果对应名称的cookie不存在,返回null
     */
    public static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (name.equals(cookies[i].getName())) {
                    return cookies[i];
                }
            }
        }
        return null;
    }

    /**
     * 根据cookie名称返回cookie的值.
     * <p/>
     *
     * @param request http请求
     * @param name    cookie名称
     * @return String 返回cookie值,值不存在则返回null
     */
    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie cookie = getCookie(request, name);
        //为null
        if (cookie == null) {
            return null;
        }
        return cookie.getValue();
    }

    /**
     * 在http响应中增加cookie返回给客户端.
     * <p/>
     *
     * @param response http响应
     * @param path     cookie的路径
     * @param expiry   秒: cookie的有效期,-1为会话cookie,表示关闭浏览器的时候关闭cookie,秒
     * @param key      cookie的名称
     * @param value    cookie的值
     * @return Cookie 增加的cookie对象
     */
    public static Cookie addCookie(HttpServletResponse response, String path, int expiry, String key, String value) {
        Cookie cook = new Cookie(key, value);
        cook.setMaxAge(expiry);
        cook.setPath(path);
        response.addCookie(cook);
        return cook;
    }

    /**
     * 在http响应中增加cookie返回给客户端.HttpOnly
     * <p/>
     *
     * @param response http响应
     * @param path     cookie的路径
     * @param expiry   秒: cookie的有效期,-1为会话cookie,表示关闭浏览器的时候关闭cookie,秒
     * @param key      cookie的名称
     * @param value    cookie的值
     * @return Cookie 增加的cookie对象
     */
    public static Cookie addCookieHttpOnly(HttpServletResponse response, String path, int expiry, String key,
                                           String value) {
        final StringBuffer sb = new StringBuffer();
        Cookie cook = new Cookie(key, value);
        cook.setMaxAge(expiry);
        cook.setPath(path);
        sb.append(key);
        sb.append("=");
        sb.append(value);
        if (path != null) {
            sb.append(";Path");
            sb.append("=");
            sb.append(path);
        }
        if (expiry > 0) {
            sb.append(";Max-Age=");
            sb.append("=");
            sb.append(expiry);
        }
        sb.append(";HttpOnly");
        response.addHeader("Set-Cookie", sb.toString());
        return cook;
    }

    /**
     * 在http响应中增加cookie返回给客户端.HttpOnly
     * <p/>
     *
     * @param response http响应
     * @param secure   是否安全cookie,安全cookie需要https访问
     * @param domain   cookie的域名
     * @param path     cookie的路径
     * @param expiry   cookie的有效期,-1为会话cookie,表示关闭浏览器的时候关闭cookie
     * @param key      cookie的名称
     * @param value    cookie的值
     * @return 增加的cookie对象
     */
    public static Cookie addCookieHttpOnly(HttpServletResponse response, boolean secure, String domain, String path,
                                           int expiry, String key, String value) {
        final StringBuffer sb = new StringBuffer();
        Cookie cook = new Cookie(key, value);
        cook.setSecure(secure);
        cook.setMaxAge(expiry);
        cook.setDomain(domain);
        cook.setPath(path);
        sb.append(key);
        sb.append("=");
        sb.append(value);
        if (path != null) {
            sb.append(";Path");
            sb.append("=");
            sb.append(path);
        }
        if (domain != null) {
            sb.append(";Domain");
            sb.append("=");
            sb.append(domain);
        }
        if (expiry > 0) {
            sb.append(";Max-Age=");
            sb.append("=");
            sb.append(expiry);
        }
        sb.append(";Secure=");
        sb.append("=");
        sb.append(secure);
        sb.append(";HttpOnly");
        response.addHeader("Set-Cookie", sb.toString());
        return cook;
    }

    /**
     * 在http响应中增加cookie返回给客户端.
     * <p/>
     *
     * @param response http响应
     * @param secure   是否安全cookie,安全cookie需要https访问
     * @param domain   cookie的域名
     * @param path     cookie的路径
     * @param expiry   cookie的有效期,-1为会话cookie,表示关闭浏览器的时候关闭cookie
     * @param key      cookie的名称
     * @param value    cookie的值
     * @return 增加的cookie对象
     */
    public static Cookie addCookie(HttpServletResponse response, boolean secure, String domain, String path,
                                   int expiry, String key, String value) {
        Cookie cook = new Cookie(key, value);
        cook.setSecure(secure);
        cook.setMaxAge(expiry);
        cook.setDomain(domain);
        cook.setPath(path);
        response.addCookie(cook);
        return cook;
    }

    /**
     * 删除某个cookie.
     * <p/>
     *
     * @param response http响应
     * @param key      要删除的cookie的名称
     * @param domain   domain XXX.com
     * @param path     路径
     * @param request  http请求
     */
    public static void delCookie(HttpServletResponse response, String key, String domain, String path,
                                 HttpServletRequest request) {
        Cookie[] cooks = request.getCookies();
        //为NULL
        if (cooks == null) {
            return;
        }
        for (int i = 0; i < cooks.length; i++) {
            Cookie cook = cooks[i];
            String name = cook.getName();
            if (name.equals(key)) {
                cook.setMaxAge(0);
                if (domain != null) {
                    cook.setDomain(domain);
                }
                if (path != null) {
                    cook.setPath(path);
                }
                response.addCookie(cook);
            }
        }

    }

    /**
     * 删除某个cookie.
     * <p/>
     *
     * @param response http响应
     * @param key      要删除的cookie的名称
     * @param path     路径
     * @param request  http请求
     */
    public static void delCookie(HttpServletResponse response, String key, String path, HttpServletRequest request) {
        delCookie(response, key, null, path, request);
    }

    /**
     * 删除某个cookie.
     * <p/>
     *
     * @param response http响应
     * @param key      要删除的cookie的名称
     * @param request  http请求
     */
    public static void delCookie(HttpServletResponse response, String key, HttpServletRequest request) {
        delCookie(response, key, null, null, request);
    }

    /**
     * 判断请求是否是一个全新的页面请求
     *
     * @param request
     * @return
     */
    public static boolean checkIsNewPageRequest(HttpServletRequest request) {
        if (!AjaxUtils.isAjaxRequest(request) && "GET".equals(request.getMethod())) {
            String accept = request.getHeader("Accept");
            if (accept != null && accept.indexOf(HTML_TYPE) >= 0) {
                return true;
            } else {
                return false;
            }

        } else {
            return false;
        }
    }

    /**
     * 取得客户端请求的ip地址.
     *
     * @param request HttpServletRequest
     * @return String 客户端ip地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        //20140715 先取x-forwarded-forout，再取x-forwarded-for
        String ip = request.getHeader("x-forwarded-forout");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        } else {
            ip = "o:" + ip;
            return ip;
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        } else {
            ip = "i:" + ip;
            return ip;
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null) {
            String[] ips = ip.split(",");
            for (String ipp : ips) {
                if (!"unknown".equalsIgnoreCase(ipp)) {
                    return ipp;
                }
            }
        }
        return null;
    }

    /**
     * 取得web容器绝对地址包括域名及上下文
     *
     * @param request
     * @return
     * @see
     */
    public static String getRealPath(HttpServletRequest request) {
        String cp = request.getContextPath();
        StringBuffer rUrl = request.getRequestURL();
        return rUrl.substring(0, rUrl.indexOf(cp) + cp.length());
    }

    @Deprecated
    /**
     * 使用 SysConstant.APPDESC_SIM 做替换
     */
    public static String getAppDesc(HttpServletRequest request) {
        return request.getSession().getServletContext().getInitParameter("appDesc");
    }
}
