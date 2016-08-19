package com.capitalbio.oemv2.myapplication.FirstPeriod.BlueTooth;

import android.app.AlertDialog;
import android.content.Context;
import android.view.WindowManager;

/**
 * 打印 LOG 
 */
public class LogUtils {
	/**是否打印标识*/
	public static boolean isDebug = true ;
	
	
	public static void v(String tag, String msg) {
		if (isDebug)
			android.util.Log.v(tag, msg);
	}
	public static void v(String tag, String msg, Throwable t) {
		if (isDebug)
			android.util.Log.v(tag, msg, t);
	}

	public static void d(String tag, String msg) {
		if (isDebug)
			android.util.Log.d(tag, msg);
	}

	public static void d(String tag, String msg, Throwable t) {
		if (isDebug)
			android.util.Log.d(tag, msg, t);
	}

	public static void i(String tag, String msg) {
		if (isDebug)
			android.util.Log.i(tag, msg);
	}

	public static void i(String tag, String msg, Throwable t) {
		if (isDebug)
			android.util.Log.i(tag, msg, t);
	}

	public static void w(String tag, String msg) {
		if (isDebug)
			android.util.Log.w(tag, msg);
	}

	public static void w(String tag, String msg, Throwable t) {
		if (isDebug)
			android.util.Log.w(tag, msg, t);
	}

	public static void e(String tag, String msg) {
		if (isDebug)
			android.util.Log.e(tag, msg);
	}

	public static void e(String tag, String msg, Throwable t) {
		if (isDebug)
			android.util.Log.e(tag, msg, t);
	}
	/**
	 * 显示弹窗
	 * @param context
	 * @param msg
	 */
	public static void showMsg(Context context, String msg)
	{
		if(isDebug){
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setMessage(msg);
			AlertDialog dialog = builder.create();
			dialog.getWindow().setType((WindowManager.LayoutParams.TYPE_SYSTEM_ALERT)); //设为系统全局的对话框
			dialog.setCanceledOnTouchOutside(true);
			dialog.show();
		}
	}
	


}
