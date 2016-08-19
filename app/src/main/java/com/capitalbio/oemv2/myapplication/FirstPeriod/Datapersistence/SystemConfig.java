package com.capitalbio.oemv2.myapplication.FirstPeriod.Datapersistence;

import java.util.Date;


import android.content.Context;
import android.content.SharedPreferences;

import com.capitalbio.oemv2.myapplication.FirstPeriod.Bean.AppSavebean;
import com.capitalbio.oemv2.myapplication.FirstPeriod.Bean.UpdateTimeBean;
import com.capitalbio.oemv2.myapplication.FirstPeriod.Config.AppConstants;
import com.capitalbio.oemv2.myapplication.FirstPeriod.Utils.JsonUtil;
import com.capitalbio.oemv2.myapplication.FirstPeriod.Utils.StringUtil;

/**
 * 系统配置文件
 * @author xusun-n
 *
 */
public class SystemConfig {
	/**配置文件名**/
	private final String SYSTEM_CONFIG = "system_config";
	/**是否显示测量界面登录注册提示框**/
	private final String LOGIN_PROMPT = "login_prompt";
	/**s是否显示天气通知栏显示**/
	private final String NOTICE = "notice";
	/**资讯更新时间**/
	private final String INFORMATION_UPDATE_TIME = "information_update_time";
	/**天气刷新规则*/
	private final String UPDATE_WEATHER_RULE = "update_weather_rull";
	/**是否已经创建了桌面快捷方式*/
	private final String SHORTCUT_CREATE = "has_create_shortcut";
	/**版本更新规则*/
	private final String UPDATE_APP_RULE = "update_app_rule";
	/***是否只查找耳聋资讯*/
	private final String FILTER_EAR = "filter_ear";
	
	/***蓝牙设备灯光亮度模式*/
	private final String DEVICE_LED_LUMINANCE = "device_led_luminance";
	/***蓝牙设备灯光提示模式*/
	private final String DEVICE_LED_MODE = "device_led_mode";
	
	/**用户是否初次使用硬件检测功能**/
	private final String HELP_FIRST_DEVICE = "help_first_device";
	
	/**初次打开应用时提示绑定bong手环**/
	private final String BONG = "bong";
	
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;
	
	public SystemConfig(Context context) {
		preferences = context.getSharedPreferences(SYSTEM_CONFIG, Context.MODE_PRIVATE);
		editor = preferences.edit();
	}
	/**设置是否指查找耳聋资讯*/
	public void filterEar(boolean ear)
	{
		editor.putBoolean(FILTER_EAR, ear);
	}
	/**是否指查找耳聋的资讯*/
	public boolean getFilterEar()
	{
		return preferences.getBoolean(FILTER_EAR, false);
	}
	/**
	 * 保存下次是否提示的状态
	 * @param isShow
	 */
	public void saveLoginPrompt(boolean isShow){
		editor.putBoolean(LOGIN_PROMPT, isShow).commit();
	}
	
	/**
	 * 获得是否提示状态
	 * @return
	 */
	public boolean getLoginPrompt(){
		return preferences.getBoolean(LOGIN_PROMPT, false);
	}
	
	
	
	/**
	 * 保存下次是否提示的状态
	 * @param isShow
	 */
	public void saveNotice(boolean isShow){
		editor.putBoolean(NOTICE, isShow).commit();
	}
	
	/**
	 * 获得是否提示状态
	 * @return
	 */
	public boolean getNotice(){
		return preferences.getBoolean(NOTICE, true);
	}
	
	/**
	 * 保存资讯同步时间
	 * @param date
	 */
	public void saveInformationUpdateTime(Date date){
		editor.putLong(INFORMATION_UPDATE_TIME, date.getTime()).commit();
	}
	
	/**
	 * 获得资讯上次同步时间
	 * @return
	 */
	public long getInformationUpdateTime(){
		long time = preferences.getLong(INFORMATION_UPDATE_TIME, new Date().getTime());
		return time;
	}
	
	/**
	 * 保存天气更新规则*/
	public void saveUpdateweatherRule(UpdateTimeBean bean){
		String str = JsonUtil.creatJsonString(bean);
		editor.putString(UPDATE_WEATHER_RULE, str).commit();
	}
	/**
	 *获得天气更新规则 
	 * */
	public UpdateTimeBean getUpdateweatherRule(){
		String str = preferences.getString(UPDATE_WEATHER_RULE, null);
		if(StringUtil.isEmpty(str)){
			return createUpdateTimeBean();
		}
		UpdateTimeBean bean = JsonUtil.getBean(str, UpdateTimeBean.class);
		if(bean == null)
		{
			return createUpdateTimeBean();
		}
		return bean;
	}
	
	private UpdateTimeBean createUpdateTimeBean()
	{
		UpdateTimeBean updateTimeBean = new UpdateTimeBean();
		updateTimeBean.setAutomatic_update(AppConstants.isUpdate);
		updateTimeBean.setInterval(AppConstants.INTERVAL_UPDATE);
		updateTimeBean.setStart_update(AppConstants.START_UPDATE_HOUR);
		updateTimeBean.setStop_update(AppConstants.STOP_UPDATE_HOUR);
		saveUpdateweatherRule(updateTimeBean);
		return updateTimeBean;
	}
	/**
	 * 保存创建快捷桌面标示
	 * */
	public void shortCutCreate(){
		editor.putBoolean(SHORTCUT_CREATE, true).commit();
	}
	
	/**
	 * 获得创建快捷桌面标示
	 * */
	public boolean isShortCutCreate(){
		return preferences.getBoolean(SHORTCUT_CREATE, false);
	}
	/**
	 * 保存版本更新信息
	 * */
	public void saveAppInformation(AppSavebean bean){
		String str = JsonUtil.creatJsonString(bean);
		editor.putString(UPDATE_APP_RULE, str).commit();
	}
	
	/**
	 * 获得版本更新信息
	 * */
	public AppSavebean getAppInformation(){
		String str = preferences.getString(UPDATE_APP_RULE, null);
		if(StringUtil.isEmpty(str)){
			return null;
		}
		AppSavebean bean = JsonUtil.getBean(str, AppSavebean.class);
		
		return bean;
	}
	
	/**
	 * 保存蓝牙设备灯光亮度模式
	 * @param mode
	 */
	public void setDeviceLedLuminance(int mode){
		editor.putInt(DEVICE_LED_LUMINANCE, mode).commit();
	}
	
	/**
	 * 获取蓝牙设备灯光亮度模式
	 * @param mode
	 * @return
	 */
	public int getDeviceLedLuminance(){
		return preferences.getInt(DEVICE_LED_LUMINANCE, 0);
	}
	
	/**
	 * 保存蓝牙设备灯光提示模式
	 * @param mode
	 */
	public void setDeviceLedMode(int mode){
		editor.putInt(DEVICE_LED_MODE, mode).commit();
	}
	
	/**
	 * 获取蓝牙设备灯光提示模式
	 * @param mode
	 */
	public int getDeviceLedMode(){
		return preferences.getInt(DEVICE_LED_MODE, 0);
	}
	
	/**
	 * 设置用户是否第一次打开空气检测功能
	 * @param is
	 */
	public void setHelpFirstDevice(boolean is){
		editor.putBoolean(HELP_FIRST_DEVICE, is).commit();
	}
	
	/**
	 * 获取用户是否第一次打开空气检测功能
	 * @return
	 */
	public boolean getHelpFirstDevice(){
		return preferences.getBoolean(HELP_FIRST_DEVICE, true);
	}
	
	/**
	 * 设置用户是否第一次提示绑定bong手环
	 * @param is
	 */
	public void setBong(boolean is){
		editor.putBoolean(BONG, is).commit();
	}
	
	/**
	 * 获取用户是否第一次提示绑定bong手环
	 * @return
	 */
	public boolean getBong(){
		return preferences.getBoolean(BONG, false);
	}
}
