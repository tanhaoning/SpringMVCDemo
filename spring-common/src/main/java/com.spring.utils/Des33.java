package com.spring.utils;

import java.io.UnsupportedEncodingException;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * 3DES加密工具类
 * 
 * @author liufeng
 * @date 2012-10-11
 */
public class Des33 {
	// 密钥
//	private final static String secretKey = "F982EF6B71FB0C7DF07EE218C57511FCF982EF6B71FB0C7D";
//	 private final static String secretKey = "4bb57add409f4ca28a5235161167ffee4bb57add409f4ca2";
	private final static String secretKey = "6091aab92a16a0808d5243221167ffee6091aab92a16a080";
	// 向量
	private final static byte[] keyiv = { 0, 1, 2, 3, 4, 5, 6, 7 };
	// 加解密统一使用的编码方式
	private final static String encoding = "utf-8";

	public static void main(String[] args) {
		//String data = "<certificate><partyName>黄斌</partyName>";
		String data = "<certificate><partyName>黄斌</partyName><certNumber>350103198703093516</certNumber><gender>1</gender><certAddress>福建省福州市台江区中平路153号嘉盛苑1座505单元</certAddress><nation>汉</nation><bornDay>19870309</bornDay><certOrg>福州市公安局台江分局</certOrg><effDate>20110104</effDate><expDate>20210104</expDate></certificate>";
		System.out.println("data length~~" + data.length());
		String str1;
		try {
			str1 = encode(data,secretKey);
			System.out.println("str1~~~" + str1);
//			String str2 = decode(str1);
			 String str2String =
//			 decode1("0/ANrmO6IRenet9A3ublvq1+rvAFal8gXuVrm49+VbAKDFADFsEi4NvTVSLrK16Btbhy7wfjPgpOEd5M4w8BgDgozCNAe6rzKcq03u6RutHb2mdo6aUjfMaXrMvkk9EBHhmmyo917PjG3HDsiABnJCgLgF3VSsD9sNt/An0qmPGD2fy/3w/iI4htTqq1TxMt6lw0d2XEDXTgs0IRkrQfP+A475rkYBbKUqf72CzvBku/l/gcU13Ov/m8Pu4Uoy/RtKQZUsNiMfpoeWDA5EAyhQ1a9U5crG3ZsIxmVz2+hwLo2ACYE3ODkap3AV7MT1Yac5zl1dNryVpLBB4+Scq0e24WBPRzETcigS8TBYhgfVCHNG0PP3Ufh6x77yuynjrWcgEU3wjEHJdZ/UnNDqE6xnVesE4QHomHKgmx+crtiUhvNy6wvFXJN2msgeuNZzrT5YsRdk5C3DCvv6WjX2Xw8xZFqh4vGNS8");
			 decode1("sXhAmKwIu3PrOZYlkRCi953ctG2uuec0jEM1OZLVr+C3MjBnM1nsOVjhK7AO 2jC+Ez6lLCzIzRtV49IS0/feFoFsSHaeja1L7OH+pxwI3M3CTJsZjH0VpeWk eKLa7pRlYiwPjTSJMF7w38q2DN7DwAhcevaMYyE5qwiJl7/eG/poX1Gw4Bu6 leRBljvdR2DXvj4Cn9oemVxsx38q3u+0/M0UFZj+32cHbK6K2rxRupvoE0Ci efFEnPhdZ30K3GFRKAoBrUXsjjqIT/wj2q2LCv9J3cRwpVchoynM4oH0AVVG BGSHrcaRT4loHHZHVCHNqu3MMefhUGGu2SFYZTpQJgdNAnZhbpNmKZPxkgdv N1gdDFyg1l6Ou5teFsJ60AsDBx8yYOximQyEGyLiBE2uCbXK7dOFoN6Z50KB maiMzjLza4sZ3pycs+ejWKICD+2dQTznzbzbBXquaBPQHQGxQI/m3O5cwI/o 31skxGdn3ZA=",
					 secretKey);
			 System.out.println("str2String~~~" + str2String.trim() + ",,,,");
//			 String str2 = decode1("0/ANrmO6IReiv7txTlyQxFSTXIrCLJqlpifMSmdoSMV0Q7SMKnuA0XqtmKaY HsemX1fXvJKb6Q5zyRFxBz2EIGODhAMjZJTGXXqsQwYEzQUf3c+f2ZK9B2AG DB5fQ6zhi4rxN7atwF7K/7Rbj+Wj6SqZxwvMIeYK3rw0TkUMnFNBr2d53nf8 83lqicqd8uBTqkOj82NIbVTKHN7b8n3U5J0/4O90Jpleg/OthbPWwAIenMWD FP7cjOxQgsXbe3noIYBAW664Ln5wTs33M/2ay0TPZ2rRw0xBREjNyNTbLUsq a9Qf7ouUnf553Fv+ruyCK97glWs2VjcHepAwKDu/NynCTlMM7hba7n9UB8io dFExvYEEGSYoJ2Cv+WBncYE8lP92l1ZkGLmnkrWI5lHWo07fVY5Z4lBk00h2 e798vz2sJa1zRwKK+ggPBuFec0S7WvXIyaSl+RGKOa9A+4r41WmJgy7gjdth WKsrj+kiraCUhQbiX92kcE3Mqo7a24z3cn9LumyVCujqH9zzuO+Do9NhtXR6 uPqg");
//			 String str2 = decode("1/E/Ad4TrbO7AGoVsRRZezX3pIfsUVlKJ3V1gJsIVl7Q6Z6m/+mTNnx7mWx9 ZiKxWFN3ran+PbjctfnzdT1w9ObrvNPpl4PJlUVybBdgstpTbzKykYhPiPsn 1sfi7CbFfeDS3C3GYhhJTKRI8wpoEFCiJNqpX2+GBd87s0PTLKq8m83e8fvz WM0O8yvECzcjVGEMI4y2PV7cYu+TJX6QruYvlLMGnHNjrUb2iPOv78irgeXR NfSRFaSpcB8SY1a+cAbM7P8iIK2Hn+546sQlHsVmsEh4etlSQ7ymuNdk7/Mh ymcCkzBXMalvebp7/3gITzNTPny8u6jOSvizaOFjctsZ1gT3yDZC5qwoYN3T GLz4GZbMp6Un8o0ssCzvmOKPU0ekyOwP7Lkx5MMoauCEpjpg6QyLzRi3AYpG 1kOl148+OmY2x+VHwErxKVVZQJFVjLvJd0lrIsy6VEHbgSvIi14iZP/OWQhB nIDoVYw2uJ1VxSkiFJyNC6AY2bsZ7AL9M6JseM6Tsmbn8AjSx/4s0d+0sgA7 qnNhX8QffbY6ful7wN3yT8JKb0uVOfB/OgWlzZjOHDij4AlZvJN0WPzlfW4F Iw7NHG70kluP/XKFNzPPf6VEPaZM1PCJ0c8F82VgCqpXhavPaGpXqC2YAsVO ++4xQHnMX6h7OQDOB1Dv4wB3ndihjKZlWAnGAVrdrVVOPbo/Se3WDqFno9dz G2ngeiY1D+c97GFsBIeAKFUOks9ns9GRBlCuqXQT4zySMR7o7DMfGBF2CpOh YBAZJGva9N9KlU3R5Urkk1dAZuVFhXPIZksscORC7zVCMZBjocKOWQ8aD+rR PPMDVKUiig62Sw==");
//			 System.out.println("str2~~~" + str2.trim() + ",,,,");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 秘钥
	private static byte[] hexStr2Bytes(String src) {
		int m = 0, n = 0;
		int l = src.length() / 2;
		System.out.println(l);
		byte[] ret = new byte[l];
		for (int i = 0; i < l; i++) {
			m = i * 2 + 1;
			n = m + 1;
			ret[i] = (byte) (Integer.decode("0x" + src.substring(i * 2, m) + src.substring(m, n)) & 0xFF);
		}
		return ret;
	}

	/**
	 * 3DES加密
	 * 
	 * @param plainText
	 *            普通文本
	 * @return
	 * @throws Exception
	 */
	public static String encode(String plainText,String secretKey) throws Exception {
		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(hexStr2Bytes(secretKey));
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);

		Cipher cipher = Cipher.getInstance("desede" + "/CBC/NoPadding"); // PKCS5Padding
		IvParameterSpec ips = new IvParameterSpec(keyiv);
		cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
		byte[] encryptData = cipher.doFinal(FormateData(plainText));
		return Base64.encode(encryptData);
	}

	/** 
	 * 密码加密时，填充字符串为8的倍数。<p> 
	 * （此方法在模式是CBC模式，填充方式为NoPadding方式的情况下，用字节零填充.） 
	 *   
	 * @param str  密码 
	 * @return 加密后的密文 
	 */  
	public static byte[] FormateData(String str) throws UnsupportedEncodingException {

			if((str.length()%8)==0){
				return str.getBytes(encoding);
			}
		
			byte[] data = str.getBytes(encoding);
			int size = 8- data.length%8;
			System.out.println("data.length~~"+data.length);
			System.out.println("需要补~~"+size);
			byte[] arr = new byte[data.length + size];
			System.out.println("arr个~~"+arr.length);
			int i = 0;
			for (; i < data.length; i++) {
				arr[i] = data[i];
			}
			for (int j = 0; j < size; j++, i++) {
				System.out.println("i==="+i);
				arr[i] = new byte[] { 0 }[0];
			}
			return arr;
	}

	/**
	 * 3DES解密
	 * 
	 * @param encryptText
	 *            加密文本
	 * @return
	 * @throws Exception
	 */
	public static String decode(String encryptText) throws Exception {
		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(hexStr2Bytes(secretKey));
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);
		Cipher cipher = Cipher.getInstance("desede" + "/CBC/NoPadding");
		IvParameterSpec ips = new IvParameterSpec(keyiv);
		cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
		System.out.println("encryptText~~~"+encryptText);
		byte[] decryptData = cipher.doFinal(Base64.decode(encryptText));
		 String bOut = byte2Ucs2(decryptData, 0, decryptData.length);
		return bOut;
//		return new String(decryptData,encoding);
	}
	

	/**
	 * 3DES解密
	 * 
	 * @param encryptText
	 *            加密文本
	 * @return
	 * @throws Exception
	 */
	public static String decode1(String encryptText,String secretKey) throws Exception {
		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(hexStr2Bytes(secretKey));
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);
		Cipher cipher = Cipher.getInstance("desede" + "/CBC/NoPadding");
		IvParameterSpec ips = new IvParameterSpec(keyiv);
		cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
		byte[] decryptData = cipher.doFinal(Base64.decode(encryptText));
		return new String(FormateByte(decryptData), encoding);
	}
	
	/** 
	 * 密码解密时，将填充的字节零去掉！<p> 
	 * (此方法只在模式是CBC模式，填充方式为NoPadding方式，用字节零填充 的情况下使用。) 
	 * @param arr 
	 *          密文字节组 
	 *  
	 * @return 密码字节组 
	 */  
	public static byte [] FormateByte(byte [] arr){  
	      
	    int i = 0;  
	    for (; i < arr.length; i++) {  
	        if(arr[i] == new Byte("0")){  
	            break;  
	        }  
	    }  
	    byte [] result = new byte [i];  
	    for (int j = 0; j < i; j++) {  
	        result[j] = arr[j];  
	    }  
	    return result;  
	} 
	
	public static String byte2Ucs2(byte[] data, int iPos, int iLen)
	{
		try
		{
			return (new String(data, iPos, iLen, "UTF-16LE")).trim();
		}
		catch (UnsupportedEncodingException e)
		{
			System.out.println("转换UTF8失败");
		}
		return "";
	}

}