package com.capitalbio.oemv2.myapplication.BraceleteLib;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 鏃堕棿鎿嶄綔绫�
 * @author Jiantao.tu
 *
 */
public class DateUtil {

	public final static String YMD = "yyyy-MM-dd";
	public final static String YMDHM = "yyyy-MM-dd HH:mm";
	public final static String HMS = "HH:mm:ss.SSS";
	public final static String FULLFORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
	public final static String YMDHMFORMAT = "yyyy-MM-dd HH:mm:ss";
	public final static String YMDHMS_POINT = "yyyy.MM.dd HH:mm:ss";
	public final static String YMDHM_POINT = "yyyy.MM.dd HH:mm";
	public final static String YMDHMS_GANG = "yyyy-MM-dd HH:mm:ss";
	private short now = 0;// 鏃�
	private short minutes = 0;// 鍒�
	private short seconds = 0;// 绉�
	private int sumSeconds;//鎬诲叡鑰楁椂澶氭壂绉�
	public static String getDateToString(Date date, String format) {
		if (date == null)
			return null;
		else {
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			String dateString = formatter.format(date);
			return dateString;
		}
	}

	public static Date getStringToDate(String date, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		try {
			return formatter.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	

	
	
	public void setSumSeconds(int sumSeconds){
		this.sumSeconds=sumSeconds;
	}
	
	public int getSumSeconds(){
		return sumSeconds;
	}
	/**
	 * 璁℃椂
	 * 姣忚皟鐢ㄤ竴娆″姞涓�锛屼粠0寮�鏈�ぇ鍗曚綅涓哄皬鏃惰秴鍑�4灏忔椂鍏ㄩ儴娓呴浂
	 * @return 姣斿锛�0:01鎴栬�01:01:34
	 */
	public String reckonByTime() {
		StringBuilder sb = new StringBuilder();
		seconds++;
		if (seconds > 59) {
			seconds = 0;
			minutes++;
			if (minutes > 59) {
				minutes = 0;
				now++;
				if (now > 24) {
					now = 0;
					minutes = 0;
					seconds = 0;
				}
			}
		}
		boolean flag = false;
		if (now > 0) {
			if (now <= 9) {
				sb.append("0").append(now);
			} else {
				sb.append(now);
			}
			flag=true;
		}
		if (minutes > 0) {
			if(flag)
				sb.append(":");
			else
				flag=true;
			if (minutes <= 9) {
				sb.append("0").append(minutes);
			} else {
				sb.append(minutes);
			}
			
		}else{
			sb.append("00:");
		}
		if (seconds > 0) {
			if(flag)
				sb.append(":");
			if (seconds <= 9) {
				sb.append("0").append(seconds);
			} else {
				sb.append(seconds);
			}
		}else{
			sb.append(":00");
		}
		sumSeconds++;
		return sb.toString();
	}



}
