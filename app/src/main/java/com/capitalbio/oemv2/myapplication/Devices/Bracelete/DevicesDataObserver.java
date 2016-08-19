package com.capitalbio.oemv2.myapplication.Devices.Bracelete;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.capitalbio.oemv2.myapplication.Base.IBloodPressDevicesCallBack;
import com.capitalbio.oemv2.myapplication.Base.IBodyFatDevicesCallBack;
import com.capitalbio.oemv2.myapplication.Base.IConnectionStateChange;
import com.capitalbio.oemv2.myapplication.Base.IDeviceSearchCallBack;
import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;

/**
 * Created by chengkun on 15-12-23.
 */
public class DevicesDataObserver {

    public static final String TAG = "SportDataObserver";

    private static DevicesDataObserver mInstance;
    private Context mContext = MyApplication.getInstance();
    private OemServiceConnection mConnection = new OemServiceConnection();
    private OemBackgroundService.OemBackgroundServicesBindrer mOemBinder;
    private ISportDataCallback sportDataCallback;

    private DevicesDataObserver() {
        Intent intent = new Intent(mContext, OemBackgroundService.class);
        mContext.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    private class OemServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            OemLog.log("SportDataObserver", "onServiceConnected get the binder...");
            mOemBinder = (OemBackgroundService.OemBackgroundServicesBindrer) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            OemLog.log(TAG, "onServiceDisconnected clear the binder...");
            mOemBinder = null;
        }
    }


    public static DevicesDataObserver getObserver() {
        if (mInstance == null) {
            mInstance = new DevicesDataObserver();
        }
        return mInstance;
    }
    public void registerObserver(ISportDataCallback sportCallback) {
        OemLog.log(TAG, "registerObserver...");
        sportDataCallback = sportCallback;
        if (mOemBinder != null) {
            mOemBinder.registerSportDataCallBack(sportDataCallback);
        } else {
            OemLog.log(TAG, "oemBackgrundService binder is null can not register callback");
        }
    }

    public void unregisterObserver() {
        OemLog.log(TAG, "unregisterObserver...");
        if (mOemBinder != null) {
            mOemBinder.unregisterSportDataCallback();
        } else {
            OemLog.log(TAG, "oemBackgrundService binder is null can not unregister callback");
        }
    }

    public void registerConnectStateCallBack(IConnectionStateChange connectionStateChange) {
        OemLog.log(TAG, "registerConnectStateCallBack...");
        if (mOemBinder != null) {
            mOemBinder.registerConnectStateChangeCallBack(connectionStateChange);
        } else {
            OemLog.log(TAG, "oemBackgrundService binder is null");
        }

    }

    public void registerDeviceSearchCallBack(IDeviceSearchCallBack deviceSearchCallBack) {
        OemLog.log(TAG, "registerDeviceSearchCallBack...");
        if (mOemBinder != null) {
            mOemBinder.registerDeviceSearchCallBack(deviceSearchCallBack);
        } else {
            OemLog.log(TAG, "oemBackgrundService binder is null");
        }
    }

    public void registerBloodPressCallBack(IBloodPressDevicesCallBack bloodPressDevicesCallBack) {
        OemLog.log(TAG, "registerBloodPressCallBack...");
        if (mOemBinder != null) {
            mOemBinder.registerBloodPressCallBack(bloodPressDevicesCallBack);
        } else {
            OemLog.log(TAG, "oemBackgrundService binder is null");
        }
    }

    public void registerBodyFatDevicesCallBack(IBodyFatDevicesCallBack bodyFatDevicesCallBack) {
        OemLog.log(TAG, "registerBodyFatDevicesCallBack");
        if (mOemBinder != null) {
            mOemBinder.registerBodyFatDevicesCallBack(bodyFatDevicesCallBack);
        } else {
            OemLog.log(TAG, "oemBackgrundService binder is null");
        }

    }

}






