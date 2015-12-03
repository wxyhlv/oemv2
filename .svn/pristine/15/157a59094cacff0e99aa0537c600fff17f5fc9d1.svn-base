package com.capitalbio.oemv2.myapplication.Tools;

import java.security.MessageDigest;
import java.util.Random;

//获取16位随机字符串
public class MDTools {
	public static String getRandomString(int length) { // length表示生成字符串的长度
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	// MD5加密
	/*public static String getMD5(String val) throws NoSuchAlgorithmException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(val.getBytes());
		byte[] m = md5.digest();// 加密
		return getString(m);
	}

	private static String getString(byte[] b) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			sb.append(b[i]);
		}
		return sb.toString();
	}*/
	 public   final   static  String MD5(String s) {  
		  char  hexDigits[] = {  '0' ,  '1' ,  '2' ,  '3' ,  '4' ,  '5' ,  '6' ,  '7' ,  '8' ,  '9' ,  
		    'a' ,  'b' ,  'c' ,  'd' ,  'e' ,  'f'  };  
		  try  {  
		   byte [] strTemp = s.getBytes();  
		   MessageDigest mdTemp = MessageDigest.getInstance("MD5" );  
		   mdTemp.update(strTemp);  
		   byte [] md = mdTemp.digest();  
		   int  j = md.length;  
		   char  str[] =  new   char [j *  2 ];  
		   int  k =  0 ;  
		   for  ( int  i =  0 ; i < j; i++) {  
		    byte  byte0 = md[i];  
		    str[k++] = hexDigits[byte0 >>> 4  &  0xf ];  
		    str[k++] = hexDigits[byte0 & 0xf ];  
		   }  
		   return   new  String(str);  
		  } catch  (Exception e) {  
		   return   null ;  
		  }  
		 }  
}
