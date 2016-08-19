package com.capitalbio.oemv2.myapplication.Devices.Bracelete.BraceleteCommand;

import android.util.Log;

import com.capitalbio.oemv2.myapplication.Utils.OemLog;

/**
 * Created by chengkun on 15-12-16.
 */
public class GetSportDataDetailBraceleteCommand extends BraceleteCommand {

    public static final String TAG = "SportDataDetailCommand";

    public GetSportDataDetailBraceleteCommand() {
        super();
    }

    @Override
    public void excuteCommand() {
        super.excuteCommand();
        Log.d(TAG, "excuteCommand...");
        if (isBraceleteConnected) {
            braceleteDevices.getSportDataDetail();
        } else {
            braceleteDevices.ignoreCommandExecute();
            OemLog.log(TAG, "devices not connected, can not execute command...");
        }

    }
}
