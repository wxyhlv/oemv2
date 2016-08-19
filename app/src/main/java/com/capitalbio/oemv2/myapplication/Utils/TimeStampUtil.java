package com.capitalbio.oemv2.myapplication.Utils;

import com.capitalbio.oemv2.myapplication.Bean.TimeBean;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
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

	/**
	 * 将2016-03转成2016-3
	 * @param ym
	 * @return
	 */
	public static String getSingleYm(String ym){
		StringBuilder result = new StringBuilder();
		String[] arrays = ym.split("-");
		if(arrays[1].length()==2&&arrays[1].startsWith("0")){
			String strings="-"+arrays[1].replaceFirst("0","");
			result.append(arrays[0]).append(strings);
		}

          return result.toString();
	}

	/**
	 *   昨天晚上8：00到今天晚上8：00的记录 都算今天

	 * @param time
	 * @return
	 */
	public static String getSleepYmd(long time){
		int hour = getHour(time);
		String ymd = currTime3(time);
		if(hour>=20){
			return getSpecifiedDayAfter(ymd);
		}else{
			return ymd;
		}

	}
	//
	public static int getDay(long currenttime){
		Timestamp timestamp = new Timestamp(currenttime);
		DateFormat dateFormat = new SimpleDateFormat("dd");
		String day = dateFormat.format(timestamp);
		return Integer.parseInt(day);
	}
	public static int getDay(String ymd){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date parse = null;
		try {
			 parse = dateFormat.parse(ymd);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int day = getDay(parse.getTime());
		return day;
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


	public static String getDoubleDay(long currenttime){
		Timestamp timestamp = new Timestamp(currenttime);
		DateFormat dateFormat = new SimpleDateFormat("dd");
		String day = dateFormat.format(timestamp);
		String sday = singleIntToDoubleString(Integer.parseInt(day));
		return sday;
	}

	public static long getLongTime(String formattime){
		Date date=null;
		try {
			date = new SimpleDateFormat("yy-MM-dd HH:mm:dd").parse(formattime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return  date.getTime();
	}

	/**
	 * yyyy-MM
	 * @param month
	 * @return
	 */
	public static String getLastDayOfMonth_(String month){


		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		String lastdayString = null;
		try {
			Date date = format.parse(month);
			Date lastday = getLastDayOfMonth(date);
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			lastdayString=format1.format(lastday);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return lastdayString;
	}
	public   static   Date   getLastDayOfMonth(Date   sDate1)   {
		Calendar   cDay1   =   Calendar.getInstance();
		cDay1.setTime(sDate1);
		final   int   lastDay   =   cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);
		Date   lastDate   =   cDay1.getTime();
		lastDate.setDate(lastDay);
		return   lastDate;
	}

	/**
	 * 获取某月的最后一天
	 * @Title:getLastDayOfMonth
	 * @Description:
	 * @param:@param year
	 * @param:@param month
	 * @param:@return
	 * @return:String
	 * @throws
	 */
	public static String getLastDayOfMonth(int year,int month)
	{
		Calendar cal = Calendar.getInstance();
		//设置年份
		cal.set(Calendar.YEAR,year);
		//设置月份
		cal.set(Calendar.MONTH, month-1);
		//获取某月最大天数
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		//设置日历中月份的最大天数
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		//格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String lastDayOfMonth = sdf.format(cal.getTime());

		return lastDayOfMonth;
	}
	/**
	 * 获取过去七天
	 * @return
	 */
	public static  List<TimeBean> getPastSevenDay() {
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
	 * @param flag 0:完全 1：只有年月日
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
	 * 获取昨天时间 yyyy-MM-dd
	 *
	 * @return
	 */
	public static String yestdayTime(){
		Calendar   cal   =   Calendar.getInstance();
		cal.add(Calendar.DATE,   -1);
		String yesterday = new SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());
		return yesterday;
	}

	/**
	 * 获取昨天 int day
	 *
	 * @return
	 */
	public static int yestdayTime(long time){
		Calendar   cal   =   Calendar.getInstance();
		cal.setTimeInMillis(time);
		cal.add(Calendar.DATE,   -1);
		String yesterday = new SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());
		int result = getDay(yesterday);
		return result;
	}

	/**
	 * 获得指定日期的前一天
	 * @param specifiedDay
	 * @return
	 * @throws Exception
	 */
	public static String getSpecifiedDayBefore(String specifiedDay){
		Calendar c = Calendar.getInstance();
		Date date=null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day=c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);

		String dayBefore=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayBefore;
	}

	/**
	 * 获得指定日期的后一天
	 * @param specifiedDay
	 * @return
	 * @throws Exception
	 */
	public static String getSpecifiedDayAfter(String specifiedDay){
		Calendar c = Calendar.getInstance();
		Date date=null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day=c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);

		String dayafter=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayafter;
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
	 * 根据时间戳，转换成时间字符串如yyyy-MM-dd
	 * @param timestamp
	 * @return
	 */
	public static String currTime3(long timestamp){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(timestamp));

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);


		String smonth = singleIntToDoubleString(month);

		String sday = singleIntToDoubleString(day);


		return year+"-"+smonth+"-"+sday;
	}
	/**
	 * 用于空气猫历史记录界面传参
	 * @return
	 */
	public static String currTimeforHistory(){

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());

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
	 * 根据时间戳，转换成时间字符串如yyyy-MM
	 * @param timestamp
	 * @return
	 */
	public static String currMonth(long timestamp){

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(timestamp));

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		/*int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);*/

		String smonth = singleIntToDoubleString(month);

		return year+"-"+smonth;
	}

	/**
	 * 根据时间戳，转换成时间字符串如yyyy-MM-dd
	 * @param timestamp
	 * @return
	 */
	public static String currDay(long timestamp){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(timestamp));
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		int day=calendar.get(Calendar.DAY_OF_MONTH);
		String smonth = singleIntToDoubleString(month);
		String sday=singleIntToDoubleString(day);
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
	 * 用于空气猫历史记录界面传参
	 * 获取24小时前的时间
	 * @return
	 */
	public static String past24HYm(){
		Calendar calendar =Calendar.getInstance();
		calendar.add(Calendar.HOUR_OF_DAY, -24);
		Date date = calendar.getTime();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		/*System.out.println("24小时前的日期="+date.getDay());
		System.out.println("24小时前的小时="+date.getHours());
		System.out.println("24小时前的分钟="+date.getMinutes());
		System.out.println("24小时前的秒="+date.getSeconds());*/
		return sf.format(date);
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
		is = Utility.isSingleBitInt(i);
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
		calendar.set(Calendar.YEAR, Utility.currYear());
		calendar.set(Calendar.MONTH, month);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 根据月份获取月份的天数
	 * @param month yyyy-mm
	 * @return
	 */
	public static int daysInMonth(String month){
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		int i = month.indexOf("-");
		String year = month.substring(0, i);
		String month_ = month.substring(i + 1, month.length());
		calendar.set(Calendar.YEAR, Integer.valueOf(year));
		calendar.set(Calendar.MONTH, Integer.valueOf(month_)-1);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 根据时间戳，转换成时间字符串如yyyy-MM-dd HH:mm
	 * @param timestamp
	 * @return
	 */
	public static String timestamp2date(long timestamp){

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(timestamp));

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		//int second = calendar.get(Calendar.SECOND);

		String smonth = singleIntToDoubleString(month);

		String sday = singleIntToDoubleString(day);

		String shour = singleIntToDoubleString(hour);

		String sminute = singleIntToDoubleString(minute);

		//String ssecond = singleIntToDoubleString(second);


		return year+"-"+smonth+"-"+sday+" "+shour+":"+sminute;
	}

	/**
	 * 传入：分钟的int值
	 * 返回：几小时几分钟
	 * @return
	 */

	public static  String formatMinute(int value){

		return  value/60 +"时"+value%60 + "分";
	}

	/**
	 *
	 * 由出生日期算年龄
	 *
	 * param :yyyy年mm月dd日
	 */

	public  static  int getAgeByBirth(String birth){

		if(birth == null||("").equals(birth)||!birth.contains("年")){
			return 0;
		}else{
			int i = birth.indexOf("年");
			String value = birth.substring(0, i);
			int birthInt = Integer.valueOf(value);
			Calendar   cal   =   Calendar.getInstance();
			cal.setTime(new Date());
			int year = cal.get(Calendar.YEAR);
			return year - birthInt ;
		}


	}

	/**
	 * 获取当前时间 yyyy-MM-dd HH-mm-ss
	 * @param flag 0:完全 1：只有年月日
	 * @return
	 */
	public static String latestTestTime(int flag){
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
				time = year+"-"+smonth+"-"+sday+" "+shour+":"+sminute+":"+ssecond;
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

	public static String dateToYmd(Date date){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String result = format.format(date);
		return result;
	}

	public static Date ymdToDate(String ymd){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date result = null;
		try {
			result = format.parse(ymd);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}
	public static String getHistoryDefaultDay(String month){
		String curday = TimeStampUtil.currTime2(1);
		if(curday.startsWith(month)){
			return curday;
		}else{
			return month+"-01";
		}
	}

	/**
	 *
	 *
	 * void
	 */
	public static float getFloatTime(long timeWidth ) {

		// 毫秒转秒
		final float timeWidthF = Utility.div(timeWidth, 1000);
		final float timeWidthMinute = Utility.div(timeWidthF, 60);

		// 计算睡眠时长 - 小时
		final int hour = (int) (timeWidthMinute / 60);
		// 计算睡眠时长 - 分钟
		final int min = (int) (timeWidthMinute % 60);
		float result = hour + min*1f/60;
		return  result;
	}
}
