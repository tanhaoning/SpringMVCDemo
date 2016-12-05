package com.test.rpc;

/**
 * Created by tanzepeng on 2016/11/29.
 */
public class RpcConsumer {
    public static void main(String[] args) throws Exception {
        ServerService service = RpcFramework.refer(ServerService.class, "127.0.0.1", 1234);
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            String hello = service.hello("World" + i);
            //System.out.println(hello);
            Thread.sleep(5000);
        }
    }
}
