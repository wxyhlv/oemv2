package com.capitalbio.oemv2.myapplication.Devices.Bracelete.BraceleteCommand;

import android.content.Context;
import android.util.Log;

/**
 * Created by chengkun on 15-12-16.
 */
public class SetCallNumBraceleteCommand extends BraceleteCommand {

    public static final String TAG = "SetCallNumBracelete";
    private String name;

    public SetCallNumBraceleteCommand(Context context, String name) {
        super();
        name = name;
    }

    @Override
    public void excuteCommand() {
        Log.d(TAG, "excuteCommand...");
        braceleteDevices.setPushCallName(name);
    }
}
