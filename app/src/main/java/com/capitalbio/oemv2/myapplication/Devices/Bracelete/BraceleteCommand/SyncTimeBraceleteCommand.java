package com.capitalbio.oemv2.myapplication.Devices.Bracelete.BraceleteCommand;

import android.util.Log;

import com.capitalbio.oemv2.myapplication.Utils.OemLog;

/**
 * Created by chengkun on 15-12-16.
 */
public class SyncTimeBraceleteCommand extends BraceleteCommand {

    public static final String TAG = "SyncTimeBracelete";

    public SyncTimeBraceleteCommand() {
        super();
    }

    @Override
    public void excuteCommand() {
        super.excuteCommand();
        Log.d(TAG, "excuteCommand...");
        if (isBraceleteConnected) {
            braceleteDevices.setSynTime();
        } else {
            braceleteDevices.ignoreCommandExecute();
            OemLog.log(TAG, "devices not connected ignore...");
        }
    }
}
