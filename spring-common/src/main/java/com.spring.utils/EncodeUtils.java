package com.spring.utils; /**
 * Copyright (c) 2005-2009 springside.org.cn
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * <p/>
 * $Id: EncodeUtils.java 984 2010-03-21 13:02:44Z
 */

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 各种格式的编码加码工具类.
 * <BR>
 * 集成Commons-Codec,Commons-Lang及JDK提供的编解码方法.
 * <P>
 * @author tangzhengyu
 */
public class EncodeUtils {

    /** 默认编码 utf-8. */
    private static final String DEFAULT_URL_ENCODING = "UTF-8";

    /**
     * Hex编码.
     * <P>
     * @param input 输入
     * @return String 输出
     */
    public static String hexEncode(byte[] input) {
        return Hex.encodeHexString(input);
    }

    /**
     * Hex解码.
     * <P>
     * @param input 输入
     * @return byte[] 输出
     */
    public static byte[] hexDecode(String input) {
        try {
            return Hex.decodeHex(input.toCharArray());
        } catch (DecoderException e) {
            throw new IllegalStateException("Hex Decoder exception", e);
        }
    }

    /**
     * Base64编码.
     * <P>
     * @param input 输入
     * @return String 输出
     */
    public static String base64Encode(byte[] input) {
        return new String(Base64.encodeBase64(input));
    }

    /**
     * Base64编码, URL安全(将Base64中的URL非法字符如+,/=转为其他字符, 见RFC3548).
     * <P>
     * @param input 输入
     * @return String 输出
     */
    public static String base64UrlSafeEncode(byte[] input) {
        return Base64.encodeBase64URLSafeString(input);
    }

    /**
     * Base64解码.
     * <P>
     * @param input 输入
     * @return byte[] 输出
     */
    public static byte[] base64Decode(String input) {
        return Base64.decodeBase64(input);
    }

    /**
     * URL 编码, Encode默认为UTF-8.
     * <P>
     * @param input 输入
     * @return String 输出
     */
    public static String urlEncode(String input) {
        return urlEncode(input, DEFAULT_URL_ENCODING);
    }

    /**
     * URL 编码.
     * <P>
     * @param input 输入
     * @param encoding 编码
     * @return String 输出
     */
    public static String urlEncode(String input, String encoding) {
        try {
            return URLEncoder.encode(input, encoding);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Unsupported Encoding Exception", e);
        }
    }

    /**
     * URL 解码, Encode默认为UTF-8.
     * <P>
     * @param input 输入
     * @return String 输出
     */
    public static String urlDecode(String input) {
        return urlDecode(input, DEFAULT_URL_ENCODING);
    }

    /**
     * URL 解码.
     * <P>
     * @param input 输入
     *  @param encoding 编码
     * @return String 输出
     */
    public static String urlDecode(String input, String encoding) {
        try {
            return URLDecoder.decode(input, encoding);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Unsupported Encoding Exception", e);
        }
    }

    /**
     * Html 转码.
     * <P>
     * @param html 输入
     *  @return String 输出
     */
    public static String htmlEscape(String html) {
        return StringEscapeUtils.escapeHtml(html);
    }

    /**
     * Html 解码.
     * <P>
     * @param htmlEscaped 输入
     *  @return String 输出	 *
     */
    public static String htmlUnescape(String htmlEscaped) {
        return StringEscapeUtils.unescapeHtml(htmlEscaped);
    }

    /**
     * Xml 转码.
     * <P>
     * @param xml 输入
     *  @return String 输出
     */
    public static String xmlEscape(String xml) {
        return StringEscapeUtils.escapeXml(xml);
    }

    /**
     * Xml 解码.
     * <P>
     * @param xmlEscaped 输入
     *  @return String 输出
     */
    public static String xmlUnescape(String xmlEscaped) {
        return StringEscapeUtils.unescapeXml(xmlEscaped);
    }


    /**
     * sql 转义.
     * <P>
     * @param sql 输入
     *  @return String 输出
     */
    public static String escapeSql(String sql) {
        if (sql == null) {
            return null;
        }
        return StringUtils.replace(sql, "'", "''");
    }
}
