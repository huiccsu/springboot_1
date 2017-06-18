package com.bter;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

public class Encrypt {
	
	static String key ="E91343EF-B7F3-4916-B6A1-CA3423508835";
	static String secret="82ca3debae1b3ce36b669cb9cfb2a893c7f1194b229e1310807e27c3c6da8a7d";
	  //用Secret作为密码
	public static String sha512Param(String content)
	{
		 return SHA512(content,secret);
	}
	  /** 
	   * 传入文本内容，返回 SHA-512 串 
	   *  
	   * @param strText 
	   * @return 
	   */  
	  private static String SHA512(String data,String key)  
	  {  
		  String result = "";
	        byte[] bytesKey = key.getBytes();
	        final SecretKeySpec secretKey = new SecretKeySpec(bytesKey, "HmacSHA512");
	        try {
	            Mac mac = Mac.getInstance("HmacSHA512");
	            mac.init(secretKey);
	            final byte[] macData = mac.doFinal(data.getBytes());
	            byte[] hex = new Hex().encode(macData);
	            result = new String(hex, "ISO-8859-1");
	        } catch (NoSuchAlgorithmException e) {
	            // TODO 自动生成的 catch 块
	            e.printStackTrace();
	        } catch (InvalidKeyException e) {
	            // TODO 自动生成的 catch 块
	            e.printStackTrace();
	        } catch (UnsupportedEncodingException e) {
	            // TODO 自动生成的 catch 块
	            e.printStackTrace();
	        }
	        return result; 
	  }  
	  
	  
}
