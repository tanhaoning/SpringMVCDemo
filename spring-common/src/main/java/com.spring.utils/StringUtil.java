package com.spring.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 *  字符串操作类.
 *<P>
 * @author tangzhengyu
 * @version 2011-12-22 
 */

public class StringUtil {
    public static final char openCurly = '{';
    public static final char closeCurly = '}';
    public static final char openSquare = '[';
    public static final char closeSquare = ']';
    public static final char comma = ',';
    public static final char quote = '"';
    public static final char colon = ':';
    public static final char slash = '\\';
    public static final char minus = '-';
    public static final char point = '.';

    /**
     * 字符串为空返回true,否则返回false.
     * @param str
     * @return boolean true:为空
     */
    public static boolean isEmptyStr(String str) {
        return (str == null) || (str.trim().length() == 0);
    }

    /**
     * 数组按指定分隔符拼接成字符串.
     * <P>
     * @param params   数组参数
     * @param delimiter 分隔符
     * @return String 按分隔符拼接成字符串
     */
    public static String join(Object[] params, String delimiter) {
        if (params != null) {
            StringBuffer paramStr = new StringBuffer("");
            for (Object param : params) {
                paramStr.append(param);
                paramStr.append(delimiter);
            }
            String result = paramStr.toString();
            int srcLen = result.length();
            if (srcLen > 0) {
                result = result.substring(0, srcLen - delimiter.length());
            }
            return result;
        } else {
            return null;
        }
    }

    /**
     * 数组按指定分隔符拼接成字符串.
     * <P>
     * @param list   列表参数
     * @param delimiter 分隔符
     * @return String 按分隔符拼接成字符串
     */
    public static String join(List list, String delimiter) {
        if (list != null) {
            StringBuffer paramStr = new StringBuffer("");
            for (Object param : list) {
                paramStr.append(param);
                paramStr.append(delimiter);
            }
            String result = paramStr.toString();
            int srcLen = result.length();
            if (srcLen > 0) {
                result = result.substring(0, srcLen - delimiter.length());
            }
            return result;
        } else {
            return null;
        }
    }

    /**
     * 数组按 空格 分隔符拼接成字符串.
     * <P>
     * @param params   数组参数
     * @return String 按空格分隔符拼接成字符串
     */
    public static String join(Object[] params) {
        if (params != null) {
            StringBuffer paramStr = new StringBuffer("");
            for (Object param : params) {
                paramStr.append(param);
                paramStr.append(" ");
            }
            return paramStr.toString().trim();
        } else {
            return null;
        }
    }

    /**
     * 截取字节数，包含中英混合字符串.
     * @param s 字符串
     * @param length 长度
     * @return String
     * @throws UnsupportedEncodingException 
     */
    public static String bSubstring(String s, int length) throws UnsupportedEncodingException {
        if (s == null) {
            return null;
        }
        byte[] bytes = s.getBytes("Unicode");
        // 表示当前的字节数
        int n = 0;
        // 要截取的字节数，从第3个字节开始
        int i = 2;
        for (; i < bytes.length && n < length; i++) {
            // 奇数位置，如3、5、7等，为UCS2编码中两个字节的第二个字节  
            if (i % 2 == 1) {
                // 在UCS2第二个字节时n加1
                n++;
            } else {
                // 当UCS2编码的第一个字节不等于0时，该UCS2字符为汉字，一个汉字算两个字节  
                if (bytes[i] != 0) {
                    n++;
                }
            }
        }
        // 如果i为奇数时，处理成偶数  
        if (i % 2 == 1) {
            // 该UCS2字符是汉字时，去掉这个截一半的汉字  
            if (bytes[i - 1] != 0) {
                i = i - 1;
            } else {
                // 该UCS2字符是字母或数字，则保留该字符
                i = i + 1;
            }
        }
        return new String(bytes, 0, i, "Unicode");
    }

    /**
     * 字符串替换.
     * <P>
     * @param text 原始字符串
     * @param repl  想被替换的内容
     * @param with 替换后的内容
     * @return String
     */
    public static String replace(String text, String repl, String with) {
        if (text == null || repl == null || with == null || repl.length() == 0) {
            return text;
        }

        StringBuffer buf = new StringBuffer(text.length());
        int searchFrom = 0;
        while (true) {
            int foundAt = text.indexOf(repl, searchFrom);
            if (foundAt == -1) {
                break;
            }

            buf.append(text.substring(searchFrom, foundAt)).append(with);
            searchFrom = foundAt + repl.length();
        }
        buf.append(text.substring(searchFrom));

        return buf.toString();
    }

    /**
     * 去掉html标签
     * @param html
     * @return String 去掉html标签字符串
     */
    public static String removeHtml(String html) {
        if (html != null) {
            html = html.replaceAll("<[a-zA-Z]+[1-9]?[^><]*>", "").replaceAll("</[a-zA-Z]+[1-9]?>", "");
        }
        return html;
    }

    /**
     * 字符串替换
     * <P>
     *  原始消息=21312{a}  map={"a":"c"} 替换后  21312c
     * <P>
     * @param message  原始消息
     * @param map　要替换的参数
     * @return 替换后的
     */
    public static String format(String message, Map map) {
        return format(message, map, null);
    }

    /**
     * 字符串替换
     * <P>
     *  原始消息=21312{a}  map={"a":"c"} 替换后  21312c
     * <P>
     * @param message  原始消息
     * @param map　要替换的参数
     * @param replace　找不到替换值，默认替换成的值
     * @return 替换后的
     */
    public static String format(String message, Map map, String replace) {
        StringBuffer newMessage = new StringBuffer("");
        if (message != null) {
            int start = message.indexOf("{");
            if (start > 0) {
                int end = message.indexOf("}", start);
                if (end > 0) {
                    String key = message.substring(start + 1, end);
                    if (map != null && map.containsKey(key)) {
                        newMessage.append(message.substring(0, start));
                        newMessage.append(String.valueOf(map.get(key)));
                        newMessage.append(message.substring(end + 1));
                        return format(newMessage.toString(), map, replace);
                    } else if (replace != null) {
                        newMessage.append(message.substring(0, start));
                        newMessage.append(replace);
                        newMessage.append(message.substring(end + 1));
                        return format(newMessage.toString(), map, replace);
                    } else {
                        return message;
                    }
                } else {
                    return message;
                }
            } else {
                return message;
            }
        } else {
            return message;
        }
    }

    /**
     * 通过根目录和文件路径获取绝对路径
     * @param dir 根目录
     * @param path 路径
     * @return String 绝对路径
     */
    public static String getFilePath(String dir, String path) {
        int dirIndex = dir.lastIndexOf("/");
        int pathIndex = path.indexOf("/");
        //首尾都有,去掉一个
        if (dirIndex > 0 && dirIndex == (dir.length() - 1) && pathIndex == 0) {
            return dir.substring(0, dirIndex) + path;
        }
        //都没有
        if (dirIndex != (dir.length() - 1) && pathIndex != 0) {
            return dir + "/" + path;
        } else {
            return dir + path;
        }
    }

    /**
     * 去掉回车,换行,Tab
     * @param text
     * @return
     */
    public static String removeFormat(String text) {
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(text);
        return m.replaceAll("");
    }

    /**
     * 判断是否是数字，包含小数点数字
     * @param str　入参
     * @return　boolean true:是数字
     */
    public static boolean isDigits(String str) {
        if (isEmptyStr(str)) {
            return false;
        }
        int point = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '.' && i > 0 && i != str.length() - 1) {
                point = point + 1;
                if (point == 1) {
                    continue;
                }
            }
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 对key添加双引号
     * @param keys key
     * @return String 对key添加双引号后字符串
     */
    private static String keyAddQuote(String keys) {
        StringBuilder build = new StringBuilder();
        int i = 0;
        int startIndex = 0;
        int endIndex = 0;
        int beign = 0;
        boolean end = false;
        while (i < keys.length()) {
            char ch = keys.charAt(i);
            switch (ch) {
                case colon: {
                    endIndex = i;
                    end = true;
                    break;
                }
                case comma:
                case openCurly: {
                    if (!end) {
                        startIndex = i;
                    }
                    break;
                }
            }
            i++;
            if (end && startIndex < endIndex) {
                String key = keys.substring(startIndex + 1, endIndex);
                build.append(keys.substring(beign, startIndex + 1));
                build.append(quote);
                build.append(key);
                build.append(quote);
                beign = endIndex;
                end = false;
            }
        }
        build.append(colon);
        return build.toString();
    }

    /**
     * 将Map字符串格式转换成JSon字符串格式
     * @param mapFormat
     * @return String 转换成JSon字符串格式
     */
    public static String mapToJson(String mapFormat) {
        String jsonFormat = mapFormat.replaceAll("=", ":");
        jsonFormat = removeFormat(jsonFormat);
        StringBuilder build = new StringBuilder();
        int i = 0;
        int startIndex = 0;
        int endIndex = 0;
        int beign = 0;
        boolean start = false;
        while (i < jsonFormat.length()) {
            char ch = jsonFormat.charAt(i);
            switch (ch) {
                case colon: {
                    startIndex = i;
                    start = true;
                    break;
                }
                case comma:
                case closeCurly: {
                    if (start) {
                        endIndex = i;
                    }
                    break;
                }
            }
            i++;
            if (start && startIndex < endIndex) {
                String startString = jsonFormat.substring(beign, startIndex + 1);
                build.append(keyAddQuote(startString));
                String value = jsonFormat.substring(startIndex + 1, endIndex);
                if (isDigits(value)) {
                    build.append(value);
                } else {
                    build.append(quote);
                    build.append(value);
                    build.append(quote);
                }
                beign = endIndex;
                start = false;

            }
        }
        build.append(jsonFormat.substring(beign));

        return build.toString();
    }

    /**
     * inputStream转换String
     * @param is InputStream
     * @param charName 默认是 UTF-8
     * @return String
     */
    public static String is2String(InputStream is, String charName) {
        InputStreamReader isr = null;
        int i = -1;
        char[] b = new char[1024];
        StringBuffer sb = new StringBuffer();
        try {
            isr = new InputStreamReader(is, charName);
            while ((i = isr.read(b)) != -1) {
                sb.append(new String(b, 0, i));
            }
        } catch (IOException e) {
            return null;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {}
            }
        }
        String content = sb.toString();
        return content;
    }

    /**
     * inputStream转换String
     * @param is InputStream
     * @return String
     */
    public static String is2String(InputStream is) {
        return is2String(is, "UTF-8");
    }

    /**
     * 指定长度替换内容
     * @param src
     * @param begin
     * @param end
     * @param replaceStr
     * @return
     * @see
     */
    public static String replaceSubStr(String src, int begin, int end, String replaceStr) {
        StringBuffer rp = new StringBuffer(100);
        if (src == null || "".equals(src))
            return "";
        if (replaceStr == null || "".equals(replaceStr))
            return src;
        if (src.length() < begin || src.length() < end || begin < 0 || end < 0)
            return src;
        for (int i = 0; i < src.length(); i++) {
            if (i < begin || i > end)
                rp.append(src.charAt(i));
            else
                rp.append(replaceStr);
        }
        return rp.toString();

    }
    
    public static String transforLowerCase(String str) {
        String returnStr = "";
        String a[] = str.split("_");
        if (a.length == 1) {
            return str.toLowerCase();
        }
        for (int i = 0; i < a.length; i++) {
            if (i == 0) {
                returnStr = a[i].toLowerCase();
            } else {
                returnStr = returnStr + a[i].substring(0, 1).toUpperCase() + a[i].substring(1).toLowerCase();
            }
        }
        return returnStr;
    }
}
