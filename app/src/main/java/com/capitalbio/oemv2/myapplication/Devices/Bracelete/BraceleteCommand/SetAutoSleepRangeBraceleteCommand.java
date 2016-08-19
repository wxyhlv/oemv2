package com.capitalbio.oemv2.myapplication.Devices.Bracelete.BraceleteCommand;

import android.util.Log;

import com.capitalbio.oemv2.myapplication.Utils.OemLog;

/**
 * Created by chengkun on 15-12-16.
 */
public class SetAutoSleepRangeBraceleteCommand extends BraceleteCommand {

    public static final String TAG = "SetAutoSleepRangeComm";

    private boolean isAutoSleep;
    private int startHours;
    private int startMins;
    private int endHours;
    private int endMins;


    public SetAutoSleepRangeBraceleteCommand(boolean isAuto, int startH, int startM, int endH, int endM) {
        super();
        isAutoSleep = isAuto;
        startHours = startH;
        startMins = startM;
        endHours = endH;
        endMins = endM;
    }

    @Override
    public void excuteCommand() {
        super.excuteCommand();
        Log.d(TAG, "excuteCommand...");

        if (isBraceleteConnected) {
            braceleteDevices.setAutoSleepRange(isAutoSleep, startHours, startMins, endHours, endMins);
        } else {
            OemLog.log(TAG, "bracelete is not connected ignore...");
        }


    }
}











