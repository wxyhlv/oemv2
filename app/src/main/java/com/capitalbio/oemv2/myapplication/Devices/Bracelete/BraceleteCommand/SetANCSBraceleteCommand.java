package com.capitalbio.oemv2.myapplication.Devices.Bracelete.BraceleteCommand;

import android.content.Context;
import android.util.Log;

/**
 * Created by chengkun on 15-12-16.
 */
public class SetANCSBraceleteCommand extends BraceleteCommand {

    public static final String TAG = "SetGoalCal";
    private boolean isCallOn;
    private boolean isMisCallON;
    private boolean isSMSON;
    private boolean isEmailON;
    private boolean isSocialON;
    private boolean isCalendarON;
    private boolean isAntiLostON;

    public SetANCSBraceleteCommand(Context context, boolean isCall, boolean isMisCall, boolean isSMS, boolean isEmail, boolean isSocial, boolean isCal, boolean isAnti) {
        super();
        isCallOn = isCall;
        isMisCallON = isMisCall;
        isSMSON = isSMS;
        isEmailON = isEmail;
        isSocialON = isSocial;
        isCalendarON = isCal;
        isAntiLostON = isAnti;
    }

    @Override
    public void excuteCommand() {
        Log.d(TAG, "excuteCommand...");
        braceleteDevices.setANCS_SW(isCallOn, isMisCallON, isSMSON, isEmailON, isSocialON, isCalendarON, isAntiLostON);
    }
}
