package com.capitalbio.oemv2.myapplication.FirstPeriod.Config;




/**
 * 系统配置信息设置
 * @author qinzhu-n
 *
 */
public class AppConstants {
	/**显示LOG测试环境 true 生产环境 false */
	public static final boolean isLog = false ;
	/**用户信息保存--sharepreference**/
	public static final String USERINFO_SP = "user_info";
	/**APP更新版本信息保存--sharepreference**/
	public static final String APP_UPDATE_SP = "app_update_info";
	/**APP更新版本信息保存--sharepreference**/
	public static final String PICTURE_SP = "picture_info";

	public static final String PARAM_USER_ID = "userid";
	public static final String PARAM_TEMPERATURE = "temperature";
	public static final String PARAM_HUMIDITY = "humidity";
	public static final String PARAM_BATTERY_TEMPERATURE = "battery_temperatury";
	public static final String PARAM_INFORMATION_ID ="information_id";
	
	public static final String PARAM_GET_LOCAT_FAILED_DESC = "获取位置失败";
	public static final String PARAM_LOCAT_FAILED_DESC = "定位失败";
	public static final String PARAM_GET_WETAHRE_FALIED_DESC = "更新天气失败";
	
	/**错误描述*/
	public static final String PARAM_FAIL_DESC = "fail_desc";
	public static final String PARAM_CITY = "city";
	/**城市id*/
	public static final String PARAM_AREAID = "areaid";
	public static final String PARAM = "param";
	public static final String PARAM_WEATHER = "weather";
	/**获取天气信息失败*/
	public static final String BROADCAST_GET_WEATHER_FAILED = "intent.action.getweather.failed";
	/**天气信息获取完毕*/
	public static final String BROADCAST_GET_WEATHER_COMPLETE = "intent.action.getweather.complete";
	/**获取指定城市的天气*/
	public static final String BROADCAST_GET_WEATHER_BY_CITY = "intent.action.getweather";
	/**获取指定城市天气信息*/
	public static final String BROADCAST_GET_WEATHER_ONE_CITY = "intent.action.getweather.one";
	/**获取所有收藏城市的天气*/
	public static final String BROADCAST_GET_WEATHER_ALL_CITY = "intent.action.getweather.all";
	/***获取当前位置的天气*/
	public static final String BROADCAST_GET_WEATHER_BY_LOCATION = "intent.action.getweather.by.location";
	/**更新自动刷新天气规则*/
	public static final String BROADCAST_UPDATE_WEATHER_RULE = "intent.action.update.weather.rule";
	/**开始更新天气*/
	public static final String BROADCAST_UPDATE_WEATHRE_START = "intent.action.update.weather.start";
	/**点击通知栏切换下载状态*/
	public static final String BROADCAST_CHANGE_DOWNLOAD_STATUS = "intent.action.change.download.status";
	/**闹钟定时唤醒服务广播*/
	public static final String BROADCAST_ALARM_TIMER = "intent.action.alarm.timer";
	/**自动更新天气广播*/
	public static final String BROADCAST_AUTO_UPDATE_WEATHER = "intent.action.auto.update.weather";
	/**更新手机传感器温度*/
	public static final String BROADCAST_UPDATE_SENSOR_TEMPERATURE = "itent.action.update.temperature";
	/**更新手机传感器湿度*/
	public static final String BROADCAST_UPDATE_SENSOR_HUMIDITY = "intent.action.update.humidity";
	/**电池温度*/
	public static final String BROADCAST_UPDATE_BATTERY_TEMPERATURE = "intent.action.update.battery.temperature";
	/**获取空气质量完毕*/
	public static final String BROADCAST_GET_AIR_QUALITY_COMPLETE = "intent.action.get.air.quality.complete";
	/**通知栏天气是否显示切换*/
	public static final String BROADCAST_SHOW_NOTIFICATION_WEATHER_CHANGED = "intent.action.shownotification.changed";
	/*默认城市更改**/
	public static final String BROADCAST_DEFAULT_CITY_CHANGED = "intent.action.default.city.changed";
	/**发布分享的网站网址*/
	public static final String SITE_URL = "http://weather.capitalbiohealth.com/";
	/**资讯阅读url*/
	public static final String NEW_URL = "http://m.capitalbiohealth.com/mhealth/newsinfo.do?nid=";
	/**发布分享的网站名称*/
	public static final String SITE_NAME = "健康天气";
	
	/**添加删除城市*/
	public static final int REQUEST_CODE_CHOOSE_CITY = 1;
	
	/**您的网络出错了*/
	public static String ERROR_NETWORK_CONNECT = "您的网络出错了!";
	
	//注册
	public static final int REQUEST_CODE_REGIST = 7;
	public static final int AUTHOR_COMPLETE = 8;
	
	/**显示当前状态为更新*/
	public static boolean isUpdate = true;
	/**初始状态 间隔时间 单位分钟*/
	public static int  INTERVAL_UPDATE = 30;
	/**初始更新时间毫秒*/
	public static long START_UPDATE_HOUR = 1387837841418L;
	/**结束更新时间毫秒*/
	public static long STOP_UPDATE_HOUR = 1387895441020L;
									
	public static String WX_APPID = "wxe2a4e20a1201cfea";
	public static String QQ_APPID = "101021625";
	public static String SINA_APPKEY = "106901273";
	public static String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";

}
