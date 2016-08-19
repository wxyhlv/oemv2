package com.capitalbio.oemv2.myapplication.Devices.Bracelete.BraceleteCommand;

import android.content.Context;
import android.util.Log;

/**
 * Created by chengkun on 15-12-16.
 */
public class SetEmailNumBraceleteCommand extends BraceleteCommand {

    public static final String TAG = "SetEmailNumBracelete";
    private int emailNum;

    public SetEmailNumBraceleteCommand(Context context, int num) {
        super();
        emailNum = num;
    }

    @Override
    public void excuteCommand() {
        Log.d(TAG, "excuteCommand...");
        braceleteDevices.setPushUEmailNum(emailNum);
    }
}
