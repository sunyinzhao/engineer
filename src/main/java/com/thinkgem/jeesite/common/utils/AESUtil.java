package com.thinkgem.jeesite.common.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.Key;

/**
 * AESUtil.java
 * Description: AES加密
 * Copyright (c) 正保远程教育(www.cdeledu.com）.
 * All Rights Reserved. 
 * 2017-2-28 下午02:35:45 李求智 (liqiuzhi@cdeledu.com) created
 * @version 1.0 
 */
public class AESUtil {

	private static final String AESTYPE ="AES/ECB/PKCS5Padding"; 
	
    /**
	 * Description: 加密
	 * 2017-2-28 下午02:32:37 李求智 (liqiuzhi@cdeledu.com) created
	 * @param keyStr
	 * @param plainText
	 * @return
	 * @throws Exception
	 * @Version1.0 
	 */
	public static String AES_Encrypt(String keyStr, String plainText) throws Exception { 
       byte[] encrypt = null; 
       try{ 
           Key key = generateKey(keyStr); 
           Cipher cipher = Cipher.getInstance(AESTYPE); 
           cipher.init(Cipher.ENCRYPT_MODE, key); 
           encrypt = cipher.doFinal(plainText.getBytes("utf-8"));     
           return new String(Base64.encodeBase64(encrypt)); 
       }catch(Exception e){ 
       	throw e; 
       }
       
   } 
	
   /**
	 * Description: 解密
	 * 2017-2-28 下午02:33:15 李求智 (liqiuzhi@cdeledu.com) created
	 * @param keyStr
	 * @param encryptData
	 * @return
	 * @throws Exception
	 * @Version1.0 
	 */
	public static String AES_Decrypt(String keyStr, String encryptData) throws Exception {
       byte[] decrypt = null; 
       try{ 
           Key key = generateKey(keyStr); 
           Cipher cipher = Cipher.getInstance(AESTYPE); 
           cipher.init(Cipher.DECRYPT_MODE, key); 
           decrypt = cipher.doFinal(base642byte(encryptData)); 
           return new String(decrypt,"UTF-8").trim(); 
       }catch(Exception e){ 
       	throw e; 
       } 
      
   } 
   
	/**
	 * Description: 编码
	 * 2017-2-28 下午02:33:37 李求智 (liqiuzhi@cdeledu.com) created
	 * @param str
	 * @return
	 * @Version1.0 
	 */
	public static byte[] base642byte(String str) {
		byte[] bt = null;
		try {
			sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
			bt = decoder.decodeBuffer(str);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bt;
	}
	
	/**
	 * Description: 生成秘钥
	 * 2017-2-28 下午02:34:10 李求智 (liqiuzhi@cdeledu.com) created
	 * @param key
	 * @return
	 * @throws Exception
	 * @Version1.0 
	 */
	private static Key generateKey(String key)throws Exception{ 
       try{            
           SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("gbk"), "AES");
           return keySpec; 
       }catch(Exception e){ 
           e.printStackTrace(); 
           throw e; 
       } 

   } 
}
