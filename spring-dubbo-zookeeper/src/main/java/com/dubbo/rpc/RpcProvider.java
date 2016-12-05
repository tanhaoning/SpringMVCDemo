package com.dubbo.rpc;

/**
 * Created by tanzepeng on 2016/11/29.
 */
public class RpcProvider {
    public static void main(String[] args) throws Exception {
        ServerService service = new ServerServiceImpl();
        RpcFramework.export(service, 1234);
    }
}
