package com.capitalbio.oemv2.myapplication.Devices;

import android.content.Context;

import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.BraceleteLib.BraceleteDevices;
import com.capitalbio.oemv2.myapplication.Devices.Bracelete.BraceleteCommand.GetSleepDetailBraceleteCommand;
import com.capitalbio.oemv2.myapplication.Devices.Bracelete.BraceleteCommand.GetSportDataDetailBraceleteCommand;
import com.capitalbio.oemv2.myapplication.Devices.Bracelete.BraceleteCommand.GetSportDataTotalBraceleteCommand;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;
import com.capitalbio.oemv2.myapplication.Utils.PreferencesUtils;

/**
 * Created by chengkun on 15-12-18.
 */
public class SyncThread extends Thread {

    public static final String TAG = "SyncThread";

    private boolean isConnected = false;
    private Context mContext = MyApplication.getInstance();
    private BraceleteDevices braceleteDevices = BraceleteDevices.getInstance();
    private GetSportDataDetailBraceleteCommand getSportDataDetailCommand = new GetSportDataDetailBraceleteCommand();
    private GetSleepDetailBraceleteCommand getSleepDetailCommand = new GetSleepDetailBraceleteCommand();
    private GetSportDataTotalBraceleteCommand getSportDataTotalCommand = new GetSportDataTotalBraceleteCommand();
    @Override
    public void run() {
        try {
            while (true) {
                isConnected = PreferencesUtils.getBoolean(mContext, "connect_state", false);
                OemLog.log(TAG, "sync data connect status is " + isConnected);
                if (isConnected) {
                    //在连接的状态下每隔40秒取一次数据
                    braceleteDevices.addCommandToQueue(getSportDataDetailCommand);
//                    braceleteDevices.addCommandToQueue(getSportDataTotalCommand);
                    braceleteDevices.addCommandToQueue(getSleepDetailCommand);
                }
                sleep(20000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
