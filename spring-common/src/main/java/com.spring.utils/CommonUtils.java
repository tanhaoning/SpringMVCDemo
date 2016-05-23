package com.spring.utils;

import java.net.InetAddress;

public class CommonUtils {
	
	public static String getSerAddrPart(){
		String sIP = "" ;
		try{
            InetAddress address = InetAddress.getLocalHost();  
            sIP = ""+ address.getHostAddress();//10.128.21.56
            String[] sIPS = sIP.split("\\.");
            if(sIPS.length>3){
            	sIP = sIPS[2]+"."+sIPS[3];
            }
    	}catch(Exception e){
    		//e.printStackTrace();
    	}
		return sIP ;
	}

}
