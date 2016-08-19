package com.capitalbio.oemv2.myapplication.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Environment;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;


@SuppressLint("SimpleDateFormat")
@SuppressWarnings("unused")
public class ExceptionHandler implements UncaughtExceptionHandler {
	private static final String TAG = "CrashHandler";
	private static ExceptionHandler INSTANCE = new ExceptionHandler();// CrashHandler实例
	private UncaughtExceptionHandler mDefaultHandler;// 系统默认的UncaughtException处理类
	private Context mContext;
	private Map<String, String> info = new HashMap<String, String>();// 用来存储设备信息和异常信息
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
	private String path = "";//file path
	private static final String HPROF_FILE_PATH = getSDPath()+ "/data.hprof";

	private ExceptionHandler() {

	}
	
	public final static String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
		} else
			return null;
		return sdDir.toString();
	}

	public static ExceptionHandler getInstance() {
		return INSTANCE;
	}

	public void init(Context context) {
		mContext = context;
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();// 获取系统默认的UncaughtException处理器
		Thread.setDefaultUncaughtExceptionHandler(this);// 设置该CrashHandler为程序的默认处理器
	}

	/*
	 * 当UncaughtException发生时会转入该重写的方法来处理
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		ex.printStackTrace();

		Log.i(TAG,"88888888888888888888888888888888888888" +ex.getMessage());

		// 保存日志文件，并上传到服务器
		saveAndUploadCrashFile(ex);
		
	}

	/*
	 * 保存异常信息,并上传到服务器
	 */
	public void saveAndUploadCrashFile(Throwable ex) {
		Writer writer = new StringWriter();
		PrintWriter pw = new PrintWriter(writer);  
		ex.printStackTrace(pw);
		pw.close();
		String exceptionType = ex.getClass().getName();
		String exceptionMsg = writer.toString();
		postException(exceptionMsg, getIMEI(), exceptionType);
	}
	
	//获取手机串号
	public String getIMEI() {
		TelephonyManager telephonemanage = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonemanage != null ? telephonemanage.getDeviceId() : "未知IMEI";
	}

	//上传到服务器
	private void postException(final String message, String mIMEI, final String exceptionType) {
		if (message == null || message.trim().length() == 0) return;
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				File logdir = new File(HPROF_FILE_PATH);
				File parentFile = logdir.getParentFile();
				if (!parentFile.exists())
					parentFile.mkdirs();
				if (!logdir.exists()) {
					try {
						logdir.createNewFile();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				try {
					SimpleDateFormat simple=new SimpleDateFormat("yyyy-MM-dd : HH:mm:ss");
					String str=new String("\n"+simple.format(new Date())+"\n异常类型:"+exceptionType+"\n异常信息:"+message+"<-------------------------------------------->");
					OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(logdir,true),"utf-8");      
					write.write(str);
					write.flush(); 
					write.close();
					
				} catch (FileNotFoundException e) {
				} catch (IOException e) {	
				}
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							Thread.sleep(4000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.exit(0);
						
					}
				}).start();
				Looper.prepare();
				Toast toast=Toast.makeText(mContext, "程序出现异常，即将退出...", Toast.LENGTH_LONG);
				Log.i(TAG,"88888888888888888888888888888888888888");
				//toast.show();
				Looper.loop();   
				
			}
		}).start();
	
	}
	
	protected void dialog(final String message, final String exceptionType) {
		Builder builder = new Builder(mContext);
		builder.setMessage("程序出现异常，即将退出...");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				System.exit(0);
			}
		});
		builder.show();
	}
}