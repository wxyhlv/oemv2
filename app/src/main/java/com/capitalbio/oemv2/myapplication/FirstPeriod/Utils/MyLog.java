package com.capitalbio.oemv2.myapplication.FirstPeriod.Utils;

import android.util.Log;

/**
 * 打印Log
 * 
 * @author Yan.Sen Feb 16, 2013 11:52:35 AM
 */
public class MyLog {
	private static int v = 0;
	private static int d = 1;
	private static int i = 2;
	private static int w = 3;
	private static int e = 4;
	private static int TAG = 10;

	private Class<?> clazz;
	private String LogInfo;

	// 是否打log, log开关
	private boolean log = true;

	public MyLog(Class<?> clazz) {
		this.clazz = clazz;
		LogInfo = clazz.getSimpleName();
	}

	public MyLog(Class<?> clazz, String logInfo) {
		this.clazz = clazz;
		LogInfo = clazz.getSimpleName() + "---" + logInfo;
	}

	public void info(String mess) {
		if (log && i < TAG) {
			Log.i(LogInfo, mess);
		}
	}

	public void info(String mess, Throwable tr) {
		if (log && i < TAG) {
			Log.i(LogInfo, mess, tr);
		}
	}

	public void debug(String mess) {
		if (log && d < TAG) {
			Log.d(LogInfo, mess);
		}
	}

	public void debug(String msg, Throwable tr) {
		if (log && d < TAG) {
			Log.d(LogInfo, msg, tr);
		}
	}

	public void error(String msg) {
		if (log && e < TAG) {
			Log.e(LogInfo, msg);
		}
	}

	public void error(String msg, Throwable tr) {
		if (log && e < TAG) {
			Log.e(LogInfo, msg, tr);
		}
	}

	public void warning(String msg) {
		if (log && w < TAG) {
			Log.w(LogInfo, msg);
		}
	}

	public void warning(String msg, Throwable tr) {
		if (log && w < TAG) {
			Log.w(LogInfo, msg, tr);
		}
	}

	public void verbose(String msg) {
		if (log && v < TAG) {
			Log.v(LogInfo, msg);
		}
	}

	public void verbose(String msg, Throwable tr) {
		if (log && v < TAG) {
			Log.v(LogInfo, msg, tr);
		}
	}

	public String getStackTraceString(Throwable tr) {
		return Log.getStackTraceString(tr);
	}
}
