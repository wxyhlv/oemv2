package com.capitalbio.oemv2.myapplication.Devices.Bracelete.BraceleteCommand;

import android.util.Log;

import com.capitalbio.oemv2.myapplication.Utils.OemLog;
import com.capitalbio.oemv2.myapplication.Utils.TimeStampUtil;

/**
 * Created by chengkun on 15-12-16.
 */
public class GetSleepDetailBraceleteCommand extends BraceleteCommand {

    public static final String TAG = "GetSleepDetail";

    private long currentTime;
    private int currentHour;
    private int currentMin;

    public GetSleepDetailBraceleteCommand() {
        super();
    }

    @Override
    public void excuteCommand() {
        super.excuteCommand();
        Log.d(TAG, "excuteCommand...");
        if (isBraceleteConnected) {
            currentTime = System.currentTimeMillis();
            currentHour = TimeStampUtil.getHour(currentTime);
            currentMin = TimeStampUtil.getMinute(currentTime);
            OemLog.log(TAG, "current hour is " + currentHour + ", current minute is " + currentMin);

            //判断是不是同步睡眠的窗口时间，二十分钟内都可以同步手环睡眠数据
            if (isExecuteWindowTime()) {
                braceleteDevices.getSleepDataDetail();
            } else {
                braceleteDevices.ignoreCommandExecute();
                OemLog.log(TAG, "out of window time ignore the command...");
            }
        } else {
            braceleteDevices.ignoreCommandExecute();
            OemLog.log(TAG, "devices not connected ignore...");
        }
    }

    private boolean isExecuteWindowTime() {
        return currentHour > 7 && currentHour < 9 && currentMin > 30 && currentMin < 50;
    }

}






