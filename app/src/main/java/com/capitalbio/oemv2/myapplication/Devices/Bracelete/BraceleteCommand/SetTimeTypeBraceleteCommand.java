package com.capitalbio.oemv2.myapplication.Devices.Bracelete.BraceleteCommand;

import android.content.Context;
import android.util.Log;

/**
 * Created by chengkun on 15-12-16.
 */
public class SetTimeTypeBraceleteCommand extends BraceleteCommand {

    public static final String TAG = "SetTimeTypeBracelete";
    private boolean is24H = false;
    private boolean isKm = false;
    private boolean isShowDate = false;
    private boolean isShowBattery = false;
    private int dateType;

    public SetTimeTypeBraceleteCommand(Context context, boolean is24, boolean isK, boolean isShowDa, boolean isShowBat, int dateT) {
        super();
        is24H = is24;
        isKm = isK;
        isShowDate = isShowDa;
        isShowBattery = isShowBat;
        dateType = dateT;
    }

    @Override
    public void excuteCommand() {
        Log.d(TAG, "excuteCommand...");
        braceleteDevices.setTimeType(is24H, isKm, isShowDate, isShowBattery, dateType);
    }
}
