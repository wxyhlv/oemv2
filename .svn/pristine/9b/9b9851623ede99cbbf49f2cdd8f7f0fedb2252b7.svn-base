package com.capitalbio.oemv2.myapplication.Devices.Bracelete.BraceleteCommand;

import android.util.Log;

import com.capitalbio.oemv2.myapplication.Utils.OemLog;

/**
 * Created by chengkun on 15-12-16.
 */
public class GetSportDataTotalBraceleteCommand extends BraceleteCommand {

    public static final String TAG = "GetSportDataTotalCom";

    public GetSportDataTotalBraceleteCommand() {
        super();
    }

    @Override
    public void excuteCommand() {
        super.excuteCommand();

        Log.d(TAG, "excuteCommand...");
        if (isBraceleteConnected) {
            braceleteDevices.getSportDataTotal();
        } else {
            braceleteDevices.ignoreCommandExecute();
            OemLog.log(TAG, "devices not conneced ignore...");
        }

    }
}