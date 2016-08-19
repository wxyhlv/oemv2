package com.capitalbio.oemv2.myapplication.FirstPeriod.Interfaces;

import com.baidu.location.BDLocation;

import cn.com.weather.api.Weather;

/**
 * 用途：
 * 在catactivity中接受catservice中获取的天气
 * @author jiantaotu
 *
 */

public interface OnWeatherReceivedListener {
	
	void onLocationReceived(BDLocation cityname);
	
	void onWeatherReceived(Object BDLocation);

}
