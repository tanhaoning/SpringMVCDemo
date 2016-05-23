package com.test.common;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by tanzepeng on 2015/7/2.
 */
public class HttpTestCall {

    private static final Logger log = LoggerFactory.getLogger(HttpTestCall.class);
    private String url;
    private HttpClient httpClient;
    private int connTime = 15000;
    private int tranTime = 30000;

    public HttpTestCall(String url) {
        this.url = url;
        if (httpClient == null) {
            httpClient = new DefaultHttpClient();
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, this.connTime); //连接时间15s
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, tranTime);//数据传输时间30s
        }
    }

    public void httpPostCall(String param) {
        HttpPost post = null;
        HttpEntity entity = null;
        try {
            log.debug(url);
            post = new HttpPost(url);
            post.setHeader("Content-Type", "application/json");
            entity = new StringEntity(param, "UTF-8");
            post.setEntity(entity);
            HttpResponse response = httpClient.execute(post);
            String result = EntityUtils.toString(response.getEntity(), "UTF-8");
            System.out.println("输出结果：" + result);
        } catch (IOException e) {
            log.error("HTTP调用异常", e);
        } finally {
            //关闭连接，释放资源
            post.abort();
            try {
                if (null != entity) {
                    EntityUtils.consume(entity);
                }
            } catch (IOException e) {
                log.error("HTTP调用释放资源异常", e);
            }
        }
    }

    public void httpGetCall() {
        String getUrl = "http://10.128.90.194:8101/provPortal/staff/login/setLoginCookie?token=740000000000000000000000000054360&areaId=8640000";
    }

    public static void main(String[] args) {
        String url = "http://10.128.90.194:8888/SoWeb/service/intf.custService/queryCust";
        String param = "{\n" +
                "    \"acctNbr\": \"17705162513\",\n" +
                "    \"areaId\": \"8320111\",\n" +
                "    \"diffPlace\": \"local\",\n" +
                "    \"identidies_type\": \"接入号码\",\n" +
                "    \"identityCd\": \"\",\n" +
                "    \"identityNum\": \"\",\n" +
                "    \"partyName\": \"\",\n" +
                "    \"queryType\": \"\",\n" +
                "    \"queryTypeValue\": \"\",\n" +
                "    \"staffId\": \"1705282\",\n" +
                "    \"transactionId\": \"15070220571070615286135\"\n" +
                "}";
        System.out.println(param);
        HttpTestCall testCall = new HttpTestCall(url);
        testCall.httpPostCall(param);
    }
}
