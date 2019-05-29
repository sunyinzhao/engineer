package com.thinkgem.jeesite.common.utils;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import sun.misc.BASE64Encoder;

public class TokenMethod implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TokenMethod(){};
    
    //随机数发生器
    public String getToken(){
        String token = System.currentTimeMillis() + "" + new Random().nextInt();//获得毫秒数加随机数
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] md5 = md.digest(token.getBytes());
            BASE64Encoder base = new BASE64Encoder();
            base.encode(md5);
            
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        
        return token;
    }
    
}
