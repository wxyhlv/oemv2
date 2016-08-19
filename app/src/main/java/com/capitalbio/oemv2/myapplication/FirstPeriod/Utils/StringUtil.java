package com.capitalbio.oemv2.myapplication.FirstPeriod.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	/**
	 * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
	 * 
	 * @param input
	 * @return boolean
	 */
	public static boolean isEmpty(String input) {
		if (input == null || "".equals(input) || "null".equals(input.trim()))
			return true;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}
	/**
	 * 判断两个字符串是否相等
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean isEqusls(String a,String b){
		if(isEmpty(a)|| isEmpty(b))
			return false ;
		if(a==b|| a.equals(b)){
			return true;
		}
		return false ;
	}
	/**
	 * 手机号格式判断
	 * @param phoneNumber
	 * @return
	 */
	public static boolean isMobileNO(String phoneNumber){
		Pattern p = Pattern.compile("^((14[0-9])|(13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(phoneNumber);
		return m.matches();
	}
	/**
	 * 邮箱格式判断
	 * @param mailAddress
	 * @return
	 */
	public static boolean isMailAddress(String mailAddress){
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
//		String str = "^[a-zA-Z0-9|_|@|.]+$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(mailAddress);
		return m.matches();
	}
	
	/**
	 * 用户名最大长度30位只允许a-z A-Z 0-9  汉字 _ @ .
	 * @param username
	 * @return
	 */
	public static boolean isUsername(String username){
		String str = "^[a-zA-Z0-9\u4E00-\u9FFF_@.，。？（）{}【】；‘’“”：《》<>?,.;''(){}]+$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(username);
		return m.matches();
	}
	
	/**
	 * 用户名最大长度30位只允许a-z A-Z 0-9   _ @ .，。？（）{}【】；‘’“”：《》<>?,.;''(){}]+$
	 * @param username
	 * @return
	 */
	public static boolean isInputMorml(String username){
		String str = "^[a-zA-Z0-9_@.，。？（）{}【】；‘’“”：《》<>?,.;''(){}]+$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(username);
		return m.matches();
	}

	public static int parseInt(String value)
	{
		int result = 0;
		try {
			result = Integer.parseInt(value);
		} catch (Exception e) {
		}
		
		return result;
	}
	
	public static double parseDouble(String value)
	{
		double result = 0;
		try {
			return Double.parseDouble(value);
		} catch (Exception e) {
		}
		return 0;
	}
	
	public static float parseFloat(String value)
	{
		float result = 0;
		try {
			result = Float.parseFloat(value);
		} catch (Exception e) {
		}
		return result;
	}
	
	public static long parseLong(String value)
	{
		long result  = 0; 
		try {
			result = Long.parseLong(value);
		} catch (Exception e) {
		}
		return result ;
	}
	
}
