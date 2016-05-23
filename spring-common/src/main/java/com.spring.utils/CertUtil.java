package com.spring.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
/**
 * 
 * @author linm
 * 数字签名相关工具类
 *
 */
public class CertUtil {
	private static String password = "sads";
	private static String alias="usr";
	private static String exStr = "thisisatest";
	private static String ssl = "sha1WithRSA";
	private static final String KEY_STORE = "pkcs12";
	private static final String X509 = "X.509";
	private static final int CACHE_SIZE = 2048;
	private static String privateCert="/resources/cert/Usr.pfx";
	private static String publicCert="/resources/cert/Usr.cer";
    
    /**
     * <p>
     * 获得密钥库
     * </p>
     * 
     * @param keyStorePath 密钥库存储路径
     * @param password 密钥库密码
     * @return
     * @throws Exception
     */
    private static KeyStore getKeyStore(InputStream in, String password)
            throws Exception {
    	//FileInputStream in = new FileInputStream(keyStorePath);
		KeyStore inputKeyStore = KeyStore.getInstance("pkcs12");
		inputKeyStore.load(in, password.toCharArray());
        in.close();
        return inputKeyStore;
    }



    /**
     * <p>
     * 获得证书
     * </p>
     * 
     * @param certificatePath 证书存储路径
     * @return
     * @throws Exception
     */
    private static Certificate getCertificate(String certificatePath)
            throws Exception {
        CertificateFactory certificateFactory = CertificateFactory.getInstance(X509);
        FileInputStream in = new FileInputStream(certificatePath);
        Certificate certificate = certificateFactory.generateCertificate(in);
        in.close();
        return certificate;
    }

    /**
     * <p>
     * 根据密钥库获得证书
     * </p>
     * 
     * @param keyStorePath 密钥库存储路径
     * @param alias 密钥库别名
     * @param password 密钥库密码
     * @return
     * @throws Exception
     */
    private static Certificate getCertificate(String keyStorePath, String alias, String password,HttpServletRequest request) 
            throws Exception {
    	//InputStream in = new FileInputStream(keyStorePath);
    	InputStream in =request.getSession().getServletContext().getResourceAsStream(keyStorePath);
        KeyStore keyStore = getKeyStore(in, password);
        Certificate certificate = keyStore.getCertificate(alias);
        in.close();
        return certificate;
    }

    
    
     
    /**
     * <p>
     * 生成文件数字签名
     * </p>
     * 
      * <p>
     * <b>注意：</b><br>
     * 生成签名时update的byte数组大小和验证签名时的大小应相同，否则验证无法通过
     * </p>
     * 
     * @param filePath
     * @param keyStorePath
     * @param alias
     * @param password
     * @return
     * @throws Exception
     */
    private static byte[] generateFileSign(InputStream in,HttpServletRequest request)
            throws Exception {
        byte[] sign = new byte[0];
  	    //String keyStorePath="D:/lteworkspace/lte-portal-4G/src/main/resources/cert/Usr.pfx";
        //InputStream in1 = new FileInputStream(keyStorePath);
        String keyStorePath=privateCert;
        InputStream in1 = request.getSession().getServletContext().getResourceAsStream(keyStorePath);
        // 获得证书
        X509Certificate x509Certificate = (X509Certificate) getCertificate(keyStorePath, alias, password,request);
        // 获取私钥
        KeyStore keyStore = getKeyStore(in1, password);
        // 取得私钥
        PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, password.toCharArray());
        // 构建签名
        Signature signature = Signature.getInstance(x509Certificate.getSigAlgName());
        signature.initSign(privateKey);
        byte[] cache = new byte[CACHE_SIZE];
        int nRead = 0;
        while ((nRead = in.read(cache)) != -1) {
            signature.update(cache, 0, nRead);
        }
        in.close();
        sign = signature.sign();
        return sign;
    }
    
    /**
     * <p>
     * 文件签名成BASE64编码字符串
     * </p>
     * 
     * @param filePath
     * @param keyStorePath
     * @param alias
     * @param password
     * @return
     * @throws Exception
     */
    public static String signFileToBase64(InputStream in,HttpServletRequest request)
            throws Exception {
        return Base64.encodeBase64String(generateFileSign(in,request));
    }
    
  
    
    /**
     * <p>
     * 校验文件签名
     * </p>
     * 
     * @param filePath
     * @param sign
     * @param certificatePath
     * @return
     * @throws Exception
     */
    public static boolean validateFileSign(File file, String sign, String certificatePath) 
            throws Exception {
        boolean result = false;
        // 获得证书
        X509Certificate x509Certificate = (X509Certificate) getCertificate(certificatePath);
        // 获得公钥
        PublicKey publicKey = x509Certificate.getPublicKey();
        // 构建签名
        Signature signature = Signature.getInstance(x509Certificate.getSigAlgName());
        signature.initVerify(publicKey);
        if (file.exists()) {
            byte[] decodedSign = Base64.decodeBase64(sign);
            FileInputStream in = new FileInputStream(file);
            byte[] cache = new byte[CACHE_SIZE];
            int nRead = 0;
            while ((nRead = in.read(cache)) != -1) {
                signature.update(cache, 0, nRead);
            }
            in.close();
            result = signature.verify(decodedSign);
         }
        return result;
    }

    
    /**
     * <p>
     * 文件转换为byte数组
     * </p>
     * 
     * @param filePath 文件路径
     * @return
     * @throws Exception
     */
    public static byte[] fileToByte(String filePath) throws Exception {
        byte[] data = new byte[0];
        File file = new File(filePath);
        if (file.exists()) {
            FileInputStream in = new FileInputStream(file);
            ByteArrayOutputStream out = new ByteArrayOutputStream(2048);
            byte[] cache = new byte[CACHE_SIZE];
            int nRead = 0;
            while ((nRead = in.read(cache)) != -1) {
                out.write(cache, 0, nRead);
                out.flush();
            }
            out.close();
            in.close();
            data = out.toByteArray();
         }
        return data;
    }
   public static void main(String[] args) {
	   String filePath = "E:/计费运营网外部接口规范1-集团CRM分册-20131204(外部提供).doc";
	   File file = new File(filePath);
       if(file.exists()){
           try {
			InputStream in=new  FileInputStream(file);
			System.out.println(signFileToBase64(in,null));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       }
   }
}
