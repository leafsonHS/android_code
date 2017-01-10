package com.leafson.lifecycle;

import android.util.Base64;

/**
 * 利用JAVA提供的MessageDigest进行加密处理
 * 
 * @author Administrator
 */
public class EncryptUtil
{
    public EncryptUtil()
    {
    }
    public static String getBase64(String str) {  
    	return new String(Base64.encode(str.getBytes(), Base64.DEFAULT));
    }  
  
}
