package com.capitalbio.oemv2.myapplication.FirstPeriod.Datapersistence;


import android.content.Context;
/**
 * SharedPreferences 配置文件管理类
 * @author xusun-n
 *
 */
public class DataPersistenceManage {
	private Context context;
	private SystemConfig systemConfig;
	private WeatherConfig weatherConfig;
	private AppUpdateConfig appUpdateConfig;
	private PictureInputConfig pictureInputConfig;
	private static DataPersistenceManage mDataPersistenceManage;
	
	private DataPersistenceManage(Context context) {
		this.context = context;
	}
	
	public static DataPersistenceManage getInstance(Context context){
		if(mDataPersistenceManage == null){
			mDataPersistenceManage = new DataPersistenceManage(context);
		}
		return mDataPersistenceManage;
	}

	
	public WeatherConfig getWeatherConfig() {
		if(weatherConfig == null){
			weatherConfig = new WeatherConfig(this.context);
		}
		return weatherConfig;
	}

	public SystemConfig getSystemConfig() {
		if(systemConfig == null){
			systemConfig = new SystemConfig(this.context);
		}
		return systemConfig;
	}

	public AppUpdateConfig getAppUpdateConfig() {
		if(appUpdateConfig == null){
			appUpdateConfig = new AppUpdateConfig(this.context);
		}
		return appUpdateConfig;
	}
	public PictureInputConfig getPictureInputConfig() {
		if(pictureInputConfig == null){
			pictureInputConfig = new PictureInputConfig(this.context);
		}
		return pictureInputConfig;
	}
	
}
