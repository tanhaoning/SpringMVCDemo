package com.spring.utils;

import java.security.Key;
import java.security.Security;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESSecurity {
    private static final int KEY_BIT = 128;

    private static final String TYPE = "AES";

    private static final String DEFAULT_MODE = "ECB";

    private static final String DEFAULT_PADDING = "PKCS5Padding";
    // private static final String DEFAULT_PADDING = "ZeroBytePadding";

    static {
        //Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    private static final Key getKey(String key) {
        byte[] keys = key.getBytes();
        byte[] keyBTmp = new byte[KEY_BIT / 8];

        for (int i = 0; i < keys.length && i < keyBTmp.length; i++) {
            keyBTmp[i] = keys[i];
        }
        return new SecretKeySpec(keyBTmp, TYPE);
    }

    private static final String getSecurityType(String mode, String padding) {
        StringBuffer sbuf = new StringBuffer(TYPE);
        sbuf.append("/").append(mode);
        sbuf.append("/").append(padding);
        return sbuf.toString();
    }

    public static String encode(String str, String key, String mode, String padding) {
        Key secretKey = getKey(key);
        Cipher cipher;
        try {
            cipher = Cipher.getInstance(getSecurityType(mode, padding));
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] b = cipher.doFinal(str.getBytes());
            return byte2hex(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String decode(String str, String key, String mode, String padding) {
        Key secretKey = getKey(key);
        Cipher cipher;
        try {
            byte[] b = hex2byte(str);
            cipher = Cipher.getInstance(getSecurityType(mode, padding));
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(b));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String encode(String str, String key) {
        return encode(str, key, DEFAULT_MODE, DEFAULT_PADDING);
    }

    public static String decode(String str, String key) {
        return decode(str, key, DEFAULT_MODE, DEFAULT_PADDING);
    }

    public static byte[] hex2byte(String src) {
        byte[] bytes = src.getBytes();
        int iLen = bytes.length;
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(bytes, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    public static String byte2hex(byte[] src) {
        String tmp;
        int iLen = src.length;
        StringBuffer str = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            tmp = (Integer.toHexString(src[i] & 0XFF));
            if (tmp.length() == 1) {
                str.append("0");
                str.append(tmp);
            } else {
                str.append(tmp);
            }
        }
        return (str.toString()).toUpperCase();
    }
}
