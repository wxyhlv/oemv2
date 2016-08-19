package com.capitalbio.oemv2.myapplication.Devices.Bracelete.BraceleteCommand;

import android.content.Context;
import android.util.Log;

/**
 * Created by chengkun on 15-12-16.
 */
public class GetSportDataCountBraceleteCommand extends BraceleteCommand {

    public static final String TAG = "GetDevicesInfo";

    private int devicesType;


    public GetSportDataCountBraceleteCommand(Context context, int type) {
        super();
        devicesType = type;
    }

    @Override
    public void excuteCommand() {
        Log.d(TAG, "excuteCommand...");
        braceleteDevices.getDeviceInfo(devicesType);
    }
}
