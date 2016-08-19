package com.capitalbio.oemv2.myapplication.FirstPeriod.Datapersistence;

import java.text.SimpleDateFormat;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.capitalbio.oemv2.myapplication.FirstPeriod.Bean.PictureUpdateBean;
import com.capitalbio.oemv2.myapplication.FirstPeriod.Config.AppConstants;


/**
 * 储存启动页图片推广信息
 * @author keyang
 *
 */
public class PictureInputConfig {
	private SharedPreferences sp;
	private Editor edit;
	
	private String PICTURE_LING = "link";
	private String PICTURE_URL  = "picture";
	private String APPNAME = "appname";
	private String END_TIME = "e";
	private SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	public PictureInputConfig(Context context){
		sp = context.getSharedPreferences(AppConstants.PICTURE_SP, Context.MODE_PRIVATE);
		edit = sp.edit();
	}
	
	/**
	 * 保存启动页图片推广信息
	 * @param bean
	 */
	public void setPicture(PictureUpdateBean bean){
		edit.putString(PICTURE_LING, bean.getLink());
		edit.putString(PICTURE_URL, bean.getPicture());
		edit.putString(APPNAME, bean.getAppname());
		edit.putString(END_TIME, bean.getEndtime());
		edit.commit();
	}
	/**
	 * 获取启动页图片推广信息信息
	 * */
	public PictureUpdateBean getVerState(){
		String link = sp.getString(PICTURE_LING, "");
		String picture = sp.getString(PICTURE_URL, "");
		String verCode = sp.getString(APPNAME, null);
		String time = sp.getString(END_TIME, null);
		return new PictureUpdateBean(link, picture,verCode, time);
	}

}
