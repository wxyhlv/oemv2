package com.capitalbio.oemv2.myapplication.Devices.BodyFatDevices.src.com.bayh.sdk.ble.utils;

import android.util.Log;

public class MLog {
	public static boolean DEBUG=true;
	public final static void i(String tag,String msg){
		if(DEBUG)Log.i(tag, msg);
	};
}
