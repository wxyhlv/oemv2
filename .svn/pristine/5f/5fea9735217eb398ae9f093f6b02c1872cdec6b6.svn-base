package com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.command;

import android.content.Context;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.demo_activity.WifiUtils;

public class LocalWiFiInfo {
    
    //WiFi配置信息工具类
    private WifiUtils mWifiUtils = null;
    private Context mContext     = null;
    
    
    //WiFi私有信息成员变量
    private String mApSsid     = null;
    private String mApPassword = null;
    private String mApBssid    = null;
    
    public String getmApSsid() {
        return mApSsid;
    }

    public void setmApSsid(String mApSsid) {
        this.mApSsid = mApSsid;
    }

    public String getmApPassword() {
        return mApPassword;
    }

    public void setmApPassword(String mApPassword) {
        this.mApPassword = mApPassword;
    }

    public String getmApBssid() {
        return mApBssid;
    }

    public void setmApBssid(String mApBssid) {
        this.mApBssid = mApBssid;
    }

    public LocalWiFiInfo (Context context, String passWord) {
        mContext = context;
        mWifiUtils = new WifiUtils(mContext);
        mApSsid = mWifiUtils.getWifiConnectedSsid();
        mApBssid = mWifiUtils.getWifiConnectedBssid();
        mApPassword = passWord;
    }
}
