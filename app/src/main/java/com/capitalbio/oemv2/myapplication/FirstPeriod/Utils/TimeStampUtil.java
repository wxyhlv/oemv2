package com.capitalbio.oemv2.myapplication.FirstPeriod.Utils;

import com.capitalbio.oemv2.myapplication.Bean.TimeBean;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class TimeStampUtil {
	
	//
	public static int getYear(long currenttime){
		Timestamp timestamp = new Timestamp(currenttime);
		DateFormat dateFormat = new SimpleDateFormat("yyyy");
		String year = dateFormat.format(timestamp);
		return Integer.parseInt(year);
	}
	//
	public static int getMonth(long currenttime){
		Timestamp timestamp = new Timestamp(currenttime);
		DateFormat dateFormat = new SimpleDateFormat("MM");
		String month = dateFormat.format(timestamp);
		return Integer.parseInt(month);
	}
	//
	public static int getDay(long currenttime){
		Timestamp timestamp = new Timestamp(currenttime);
		DateFormat dateFormat = new SimpleDateFormat("dd");
		String day = dateFormat.format(timestamp);
		return Integer.parseInt(day);
	}
	
	public static int getHour(long currenttime){
		Timestamp timestamp = new Timestamp(currenttime);
		DateFormat dateFormat = new SimpleDateFormat("HH");//hh:下午一点1.HH：下午一点13.
		String hour = dateFormat.format(timestamp);
		return Integer.parseInt(hour);
	}
	
	public static int getMinute(long currenttime){
		Timestamp timestamp = new Timestamp(currenttime);
		DateFormat dateFormat = new SimpleDateFormat("mm");
		String minute = dateFormat.format(timestamp);
		return Integer.parseInt(minute);
	}
	
	/**
	 * 获取过去七天
	 * @return
	 */
	public static List<TimeBean> getPastSevenDay() {
		List<TimeBean> beans = new ArrayList<TimeBean>();
		for (int i = -6; i < 1; i++) {
			TimeBean bean = new TimeBean();
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, i);
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH) + 1;
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			bean.setYear(year);
			bean.setMonth(month);
			bean.setDay(day);
			beans.add(bean);
		}
		return beans;
	}
	
	/**
	 * 获取过去24小时
	 * @return
	 */
	public static List<TimeBean> getPast24Hour(){
		List<TimeBean> beans = new ArrayList<TimeBean>();
		for(int i=-23;i<1;i++){
			TimeBean bean = new TimeBean();
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.HOUR, i);
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH)+1;
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			int minute = calendar.get(Calendar.MINUTE);
			bean.setYear(year);
			bean.setMonth(month);
			bean.setDay(day);
			bean.setHour(hour);
			bean.setMinute(minute);
			beans.add(bean);
		}
		return beans;
	}
	
	/**
	 * 获取当前时间
	 * @return
	 */
	public static Date currTime(){
		Date date = new Date(System.currentTimeMillis());
		
		return date;
	}
	
	/**
	 * 获取当前时间 yyyy-MM-dd HH-mm-ss
	 * @param flag 0:完全 1：只有年月日2:00月00日 星期一
	 * @return
	 */
	public static String currTime2(int flag){
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		int week = calendar.get(Calendar.DAY_OF_WEEK);
		
		String smonth = singleIntToDoubleString(month);
		
		String sday = singleIntToDoubleString(day);
		
		String shour = singleIntToDoubleString(hour);
		
		String sminute = singleIntToDoubleString(minute);
		
		String ssecond = singleIntToDoubleString(second);
		
		String sweek = "星期日";
		
		switch (week) {
		case 1:
			sweek = "星期日";
			break;
		case 2:
			sweek = "星期一";
			break;
		case 3:
			sweek = "星期二";
			break;
		case 4:
			sweek = "星期三";
			break;
		case 5:
			sweek = "星期四";
			break;
		case 6:
			sweek = "星期五";
			break;
		case 7:
			sweek = "星期六";
			break;

		default:
			break;
		}
		
		String time ="";
		switch (flag){
		case 0:
			time = year+"-"+smonth+"-"+sday+"%20"+shour+":"+sminute+":"+ssecond;
			break;
		case 1:
			time = year+"-"+smonth+"-"+sday;
			break;
		case 2:
			time = smonth+"月"+sday+"日"+" "+sweek;
			break;

		}
		
		return time;
	}
	
	/**
	 * 根据时间戳，转换成时间字符串如yyyy-MM-dd HH:mm:ss
	 * @param timestamp
	 * @return
	 */
	public static String currTime3(String timestamp){
		long ts = Long.parseLong(timestamp);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(ts));
		
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		
		String smonth = singleIntToDoubleString(month);
		
		String sday = singleIntToDoubleString(day);
		
		String shour = singleIntToDoubleString(hour);
		
		String sminute = singleIntToDoubleString(minute);
		
		String ssecond = singleIntToDoubleString(second);
		
		
		return year+"-"+smonth+"-"+sday+" "+shour+":"+sminute+":"+ssecond;
	}


	/**
	 * 根据时间戳，转换成时间字符串如yyyy-MM-dd HH:mm:ss
	 * @param
	 * @return
	 */
	public static String currTime4(){
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		String smonth = singleIntToDoubleString(month);

		String sday = singleIntToDoubleString(day);


		return year+"-"+smonth+"-"+sday;
	}
	/**
	 * 获取24小时前的时间
	 * @return
	 */
	public static Date past24Hour(){
		Calendar calendar =Calendar.getInstance();
		calendar.add(Calendar.HOUR_OF_DAY, -24);
		Date date = calendar.getTime();
		/*System.out.println("24小时前的日期="+date.getDay());
		System.out.println("24小时前的小时="+date.getHours());
		System.out.println("24小时前的分钟="+date.getMinutes());
		System.out.println("24小时前的秒="+date.getSeconds());*/
		return date;
	}
	
	/**
	 * 获取24小时前的时间 字符串
	 * @param flag 0：全部 1：只有年月日
	 * @return
	 */
	public static String past24Hour2(int flag){
		Calendar calendar =Calendar.getInstance();
		calendar.add(Calendar.HOUR_OF_DAY, -24);
		
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		
		
		String smonth = singleIntToDoubleString(month);
		
		String sday = singleIntToDoubleString(day);
		
		String shour = singleIntToDoubleString(hour);
		
		String sminute = singleIntToDoubleString(minute);
		
		String ssecond = singleIntToDoubleString(second);
		
		String time = "";
		switch (flag) {
		case 0:
			time = year+"-"+smonth+"-"+sday+"%20"+shour+":"+sminute+":"+ssecond;
			break;
		case 1:
			time = year+"-"+smonth+"-"+sday;
			break;
		}
		
		return time;
	}
	
	/**
	 * 判断一个整数是否是一位，如果是一位前面填个零返回，如果不是则返回原本字符串
	 * @param i
	 * @return
	 */
	public static String singleIntToDoubleString(int i){
		boolean is;
		is = HTG.isSingleBitInt(i);
		String s;
		if(is){
			s = "0"+i;
		}else{
			s = ""+i;
		}
		return s;
	}
	
	
	/**
	 * 根据月份获取月份的天数
	 * @param month
	 * @return
	 */
	public static int daysInMonth(int month){
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, HTG.currYear());
		calendar.set(Calendar.MONTH, month);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	

}
