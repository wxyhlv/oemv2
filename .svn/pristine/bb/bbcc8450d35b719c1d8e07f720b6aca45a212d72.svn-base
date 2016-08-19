package com.capitalbio.oemv2.myapplication.Devices.Bracelete;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.os.Handler;

import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.BraceleteLib.BraceleteDevices;
import com.capitalbio.oemv2.myapplication.Devices.Bracelete.BraceleteCommand.ConnectBraceleteCommand;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;
import com.capitalbio.oemv2.myapplication.Utils.PreferencesUtils;

/**
 * Created by chengkun on 15-12-14.
 * 自动连接线程类
 * 在蓝牙开启的情况下
 * 自动重连15分钟
 */
public class AutoConnectThread extends Thread {

    public static final String TAG = "AutoConnectThread";
    private boolean isConnected = false;
    private boolean preConnectState = false;
    private Context mContext = MyApplication.getInstance();
    private BraceleteDevices mBraceleteDevices = BraceleteDevices.getInstance();
    private BluetoothAdapter mBlutoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private Handler servicesCallBackHandler;
    //以后需要留偏好设置可以动态的修改，这里只做调试使用
    private String mAddress;
    private int reConnectTime = 15 * 60 * 1000;
    private ConnectBraceleteCommand connectBraceleteCommand = new ConnectBraceleteCommand(mAddress);

    @Override
    public void run() {
        OemLog.log(TAG, "connect statu is " + isConnected + " preconnect statu is " + preConnectState + ", command execute is " + connectBraceleteCommand.isCommandRunning());
        isConnected = PreferencesUtils.getBoolean(mContext, "connect_state", false);
        //每次进行新的连接都对上一次的连接状况进行判断，如果已经连接了一个手环则断开
        if (mBlutoothAdapter.isEnabled() && !isConnected && !connectBraceleteCommand.isCommandRunning()) {
            mBraceleteDevices.addCommandToQueue(connectBraceleteCommand);
        }
    }

    public void setServicesCallBackHandler(Handler servicesCallBackHandler) {
        this.servicesCallBackHandler = servicesCallBackHandler;
    }
}
