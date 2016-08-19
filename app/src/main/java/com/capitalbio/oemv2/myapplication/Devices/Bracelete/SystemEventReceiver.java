package com.capitalbio.oemv2.myapplication.Devices.Bracelete;

import android.app.ActivityManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.capitalbio.oemv2.myapplication.BraceleteLib.BraceleteDevices;
import com.capitalbio.oemv2.myapplication.Devices.Bracelete.BraceleteCommand.SetCallNumBraceleteCommand;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;

public class SystemEventReceiver extends BroadcastReceiver {

    public static final String TAG = "SystemEventReceiver";
    private Context mContext;

    private static boolean incomingFlag = false;

    private static String incoming_number = null;

    public SystemEventReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        OemLog.log(TAG, "onReceive...action is " + intent.getAction());
        mContext = context;
        if (!isOemServiceRunning()) {
            OemLog.log(TAG, "services is not running and start services");
            Intent serviceIntent = new Intent(mContext, OemBackgroundService.class);
            mContext.startService(serviceIntent);

        } else {
            OemLog.log(TAG, "services is running !");
        }

    }

    private boolean isOemServiceRunning() {

        ActivityManager manager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if ("com.capitalbio.oemv2.myapplication.DevicesBean.Bracelete.OemBackgroundService".equals(service.service.getClassName())) {
                return true;
            }
        }
        return  false;
    }



}
