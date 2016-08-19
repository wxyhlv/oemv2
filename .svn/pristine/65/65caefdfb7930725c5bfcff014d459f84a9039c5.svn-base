package com.capitalbio.oemv2.myapplication.FirstPeriod.Datapersistence;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;

import com.alibaba.fastjson.JSON;
import com.capitalbio.oemv2.myapplication.FirstPeriod.Bean.AirQualityBean;
import com.capitalbio.oemv2.myapplication.FirstPeriod.Bean.WeatherBean;
import com.capitalbio.oemv2.myapplication.FirstPeriod.Utils.JsonUtil;
import com.capitalbio.oemv2.myapplication.FirstPeriod.Utils.StringUtil;

public class WeatherConfig {
	private final String WEATHER_CONFIG = "weather_config";
	private final String CITY = "city";
	private final String AIR_QUALITY = "airQualityInfo";
	private final String LOCATION_AREA_ID = "location_areaId";
	private final String WEATHER_INFO = "weatherInfo";
	private final String AUTO_LOCATE_OPEN = "auto_locate";
	
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;
	
	public WeatherConfig(Context context) {
		preferences = context.getSharedPreferences(WEATHER_CONFIG, Context.MODE_PRIVATE);
		editor = preferences.edit();
	}
	
	
	/**自动定位是否开启*/
	public boolean isAutoLocate()
	{
		return preferences.getBoolean(AUTO_LOCATE_OPEN, false);
	}
	
	/***设置是否开启自动定位*/
	public void autoLocate(boolean isOpen)
	{
		editor.putBoolean(AUTO_LOCATE_OPEN, isOpen).commit();
	}
	
	/**
	 * 保存位置信息
	 * @param longitude	经度
	 * @param latitude	维度
	 * @param province	省
	 * @param city		市
	 * @param district	区/县
	 */
	public void saveLocationCity(Double longitude, Double latitude, String province, String city, String district){
		editor.putString(CITY, longitude + "," + latitude + "," + province + "," + city + "," + district);
		editor.commit();
	}
	
	/**
	 * 获得城市信息
	 * [0]经度
	 * [1]维度
	 * [2]省
	 * [3]市
	 * [4]区/县
	 * @return
	 */
	public String[] getLocationCity(){
		String str = preferences.getString(CITY, null);
		if(str != null){
			return str.split(",");
		}
		return null;
	}
	
	/**
	 * 获取当前定位到城市名称
	 * @return
	 */
	public String getLocateCityName()
	{
		String arr[] = getLocationCity();
		if(arr == null || arr.length != 5){
			return "";
		}
		return arr[4];
	}
	
	/**
	 * 最近一次上传用户地理位置的时间
	 * @return
	 */
	public long uploadLocationTime()
	{
		return preferences.getLong("uploadlocationtime", 0);
	}
	/**
	 * 最近一次上传用户地理位置的时间
	 * @param time
	 */
	public void uploadLocationTime(long time)
	{
		editor.putLong("uploadlocationtime", time).commit();
	}
	/**
	 * 保存从天气通网站获取的区域id<br>
	 * 该值对应百度定位到的地理位置
	 * @param areaId
	 */
	public void saveLocationAreaId(String areaId)
	{
		editor.putString(LOCATION_AREA_ID, areaId);
		editor.commit();
	}
	
	/**
	 * 获取区域id<br>
	 * 该值对应百度定位到的地理位置
	 * @return
	 */
	public String getLocationAreaId()
	{
		return preferences.getString(LOCATION_AREA_ID, null);
	}
	
	
	
	/**
	 * 保存最后一次更新的空气质量信息
	 * @param json
	 */
	public void saveAirQualityInfo(String areaid, List<AirQualityBean> list){
		String json = JSON.toJSONString(list);
		editor.putString(AIR_QUALITY + areaid, json).commit();
	}
	
	/**
	 * 获取最后一期更新的空气质量信息
	 * @return
	 */
	public List<AirQualityBean> getAirQualityInfo(String areaid){
		String json = preferences.getString(AIR_QUALITY + areaid, null);
		return JSON.parseArray(json, AirQualityBean.class);
	}
	
	
	/***
	 * 将城市的天气信息保存起来
	 * @param areaid
	 * @param bean
	 */
	public void saveWeather(String areaid, WeatherBean bean)
	{
		String str = JsonUtil.creatJsonString(bean);
		editor.putString(areaid + WEATHER_INFO , str).commit();
	}
	
	/**
	 * 获取指定城市的天气信息
	 * @param areaid
	 * @return
	 */
	public WeatherBean getWeather(String areaid)
	{
		String str = preferences.getString(areaid + WEATHER_INFO, null);
		if(StringUtil.isEmpty(str)){
			return null;
		}
 		return JsonUtil.getBean(str, WeatherBean.class);
	}
	
	/**
	 * 清除指定城市的天气信息
	 * @param areaid
	 */
	public void delWeatherByAreaId(String areaid)
	{
		editor.remove(areaid + WEATHER_INFO).commit();
	}
	
	
}
