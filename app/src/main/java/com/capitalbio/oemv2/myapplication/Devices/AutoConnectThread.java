package com.capitalbio.oemv2.myapplication.Devices;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.BraceleteLib.BraceleteDevices;
import com.capitalbio.oemv2.myapplication.Devices.Bracelete.BraceleteCommand.SetManualModeBraceleteCommand;
import com.capitalbio.oemv2.myapplication.Devices.Bracelete.BraceleteCommand.SetPersonDataBraceleteCommand;
import com.capitalbio.oemv2.myapplication.Devices.Bracelete.BraceleteCommand.SyncTimeBraceleteCommand;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;
import com.capitalbio.oemv2.myapplication.Utils.PreferencesUtils;

/**
 * Created by chengkun on 15-12-14.
 * 自动连接线程类
 * 在蓝牙开启的情况下
 * 自动重连15分钟
 *
 */
public class AutoConnectThread extends Thread {

    public static final String TAG = "AutoConnectThread";
    private boolean isConnected = false;
    private boolean preConnectState = false;
    private Context mContext = MyApplication.getInstance();
    private BraceleteDevices mBraceleteDevices = BraceleteDevices.getInstance();
    private BluetoothAdapter mBlutoothAdapter = BluetoothAdapter.getDefaultAdapter();
    //以后需要留偏好设置可以动态的修改，这里只做调试使用
    private String mAddress = "20:C3:8F:FE:90:1A";
    private int reConnectTime = 15 * 60 * 1000;

    private Handler callbackHandler;


    @Override
    public void run() {
        Looper.prepare();
        int remainningTime = 0;
            try {
                while (true) {
                    synchronized (this) {
                        OemLog.log(TAG, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "connect statu is " + isConnected + " preconnect statu is " + preConnectState);
                        preConnectState = PreferencesUtils.getBoolean(mContext, "pre_connect_state", false);
                        isConnected = PreferencesUtils.getBoolean(mContext, "connect_state", false);
                        if (!isConnected && mBlutoothAdapter.isEnabled() && remainningTime <= reConnectTime) {
//                            mBraceleteDevices.connect(mAddress,null);
                            remainningTime += 10000;
                        } else if (isConnected && !preConnectState) {
                            //每次断开重新连接都需要同步时间
                            mBraceleteDevices.addCommandToQueue(new SyncTimeBraceleteCommand());
                            //设置为自动删除数据
                            mBraceleteDevices.addCommandToQueue(new SetManualModeBraceleteCommand(0X03));
                            //设置个人信息
                            mBraceleteDevices.addCommandToQueue(new SetPersonDataBraceleteCommand(0, 1987, 10, 16, 175, 72));
                            //更新之前的连接状态信息
                            PreferencesUtils.putBoolean(mContext, "pre_connect_state", true);
                        } else {
                            remainningTime = 0;
                            wait();
                        }
                        OemLog.log(TAG, "reconnect totaltime is " + remainningTime + " connect state is " + isConnected);
                    }
                    sleep(10000);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
    }

    public void setCallbackHandler(Handler callbackHandler) {
        this.callbackHandler = callbackHandler;
    }
}
