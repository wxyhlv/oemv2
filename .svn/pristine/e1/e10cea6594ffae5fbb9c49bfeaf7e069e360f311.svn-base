package com.capitalbio.oemv2.myapplication.Devices.Bracelete.BraceleteCommand;

import android.content.Context;
import android.util.Log;

import com.capitalbio.oemv2.myapplication.Utils.OemLog;

/**
 * Created by chengkun on 15-12-16.
 */
public class SetCallNameBraceleteCommand extends BraceleteCommand {

    public static final String TAG = "SetCallName";
    private String callName;

    public SetCallNameBraceleteCommand(Context context, String name) {
        super();
        callName = name;
    }

    @Override
    public void excuteCommand() {
        super.excuteCommand();
        Log.d(TAG, "excuteCommand...");

        if (isBraceleteConnected) {
            OemLog.log(TAG, "call is coming...");
            braceleteDevices.setPushCallName(callName);
        } else {
            braceleteDevices.ignoreCommandExecute();
            OemLog.log(TAG, "gatt is disconnected ignore");
        }



    }
}
