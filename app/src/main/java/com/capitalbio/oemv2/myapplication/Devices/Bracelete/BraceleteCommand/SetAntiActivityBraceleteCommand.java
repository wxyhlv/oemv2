package com.capitalbio.oemv2.myapplication.Devices.Bracelete.BraceleteCommand;

import android.content.Context;
import android.util.Log;

/**
 * Created by chengkun on 15-12-16.
 * 设置静坐提醒
 */
public class SetAntiActivityBraceleteCommand extends BraceleteCommand {

    public static final String TAG = "SetAntiActivity";

    private boolean isAntiSwOn;
    private String repeatWeeks;
    private int interval;
    private int startHour;
    private int startMin;
    private int endHour;
    private int endMin;
    private int stepLimit;

    public SetAntiActivityBraceleteCommand(Context context, boolean isOn, String repeatW, int inter, int startH, int startM, int endH, int endM, int stepL) {
        super();
        isAntiSwOn = isOn;
        repeatWeeks = repeatW;
        interval = inter;
        startHour = startH;
        startMin = startM;
        endHour = endH;
        endMin = endM;
        stepLimit = stepL;
    }

    @Override
    public void excuteCommand() {
        Log.d(TAG, "excuteCommand...");
        braceleteDevices.setAntiActivtiy(isAntiSwOn, repeatWeeks, interval, startHour, startMin, endHour, endMin, stepLimit);
    }
}
