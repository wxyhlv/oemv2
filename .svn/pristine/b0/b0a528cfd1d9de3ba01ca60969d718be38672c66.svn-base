package com.capitalbio.oemv2.myapplication.Devices.Bracelete.BraceleteCommand;

import android.util.Log;

import com.capitalbio.oemv2.myapplication.Utils.OemLog;

/**
 * Created by chengkun on 15-12-16.
 */
public class SetSMSNumBraceleteCommand extends BraceleteCommand {

    public static final String TAG = "SetSMSNumBracelete";
    private int smsNum;

    public SetSMSNumBraceleteCommand(int num) {
        super();
        smsNum = num;
    }

    @Override
    public void excuteCommand() {
        super.excuteCommand();

        Log.d(TAG, "excuteCommand...");
        if (isBraceleteConnected) {
            braceleteDevices.setPushSMSNum(smsNum);
        } else {
            braceleteDevices.ignoreCommandExecute();
            OemLog.log(TAG, "devices not conneced ignore...");
        }

    }
}
