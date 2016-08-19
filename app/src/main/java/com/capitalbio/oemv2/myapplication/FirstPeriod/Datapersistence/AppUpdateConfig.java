package com.capitalbio.oemv2.myapplication.FirstPeriod.Datapersistence;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.capitalbio.oemv2.myapplication.FirstPeriod.Bean.AppUpdateBean;
import com.capitalbio.oemv2.myapplication.FirstPeriod.Config.AppConstants;

/**
 * 存储APP版本信息
 * @author xusun-n
 *
 */
public class AppUpdateConfig {
	SharedPreferences sp;
	private Editor edit;
	
	private String APP_NAME = "appname";
	private String APK_NAME = "apkname";
	private String VER_NAME = "verName";
	private String VER_CODE = "verCode";
	private String PRO_MPT= "prompt";
	
	public AppUpdateConfig(Context context){
		sp = context.getSharedPreferences(AppConstants.APP_UPDATE_SP, Context.MODE_PRIVATE);
		edit = sp.edit();
	}
	
	public void setVerState(AppUpdateBean bean){
		edit.putString(APP_NAME, bean.getAppname());
		edit.putString(APK_NAME, bean.getApkname());
		edit.putString(VER_NAME, bean.getVerName());
		edit.putInt(VER_CODE, bean.getVerCode());
		edit.putString(PRO_MPT, bean.getPrompt());
		edit.commit();
	}
	
	public AppUpdateBean getVerState(){
		String appname = sp.getString(APP_NAME, "");
		String apkname = sp.getString(APK_NAME, "");
		String verName = sp.getString(VER_NAME, "");
		String prompt = sp.getString(PRO_MPT, "");
		int verCode = sp.getInt(VER_CODE, 0);
		return new AppUpdateBean(appname, apkname, verName,prompt, verCode);
	}

	
}
