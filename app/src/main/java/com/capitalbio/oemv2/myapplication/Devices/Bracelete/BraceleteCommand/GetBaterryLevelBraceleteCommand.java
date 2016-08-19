package com.capitalbio.oemv2.myapplication.Devices.Bracelete.BraceleteCommand;

import android.util.Log;

/**
 * Created by chengkun on 15-12-16.
 */
public class GetBaterryLevelBraceleteCommand extends BraceleteCommand {

    public static final String TAG = "GetBaterryLevel";

    public GetBaterryLevelBraceleteCommand() {
        super();
    }

    @Override
    public void excuteCommand() {
        Log.d(TAG, "excuteCommand...");
        braceleteDevices.getBatteryLevel();
    }
}
