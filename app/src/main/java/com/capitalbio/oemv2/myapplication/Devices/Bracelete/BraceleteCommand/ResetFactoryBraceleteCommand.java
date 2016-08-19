package com.capitalbio.oemv2.myapplication.Devices.Bracelete.BraceleteCommand;

import android.content.Context;
import android.util.Log;

/**
 * Created by chengkun on 15-12-16.
 */
public class ResetFactoryBraceleteCommand extends BraceleteCommand {

    public static final String TAG = "ResetFactory";


    public ResetFactoryBraceleteCommand(Context context, int type) {
        super();
    }

    @Override
    public void excuteCommand() {
        Log.d(TAG, "excuteCommand...");
        braceleteDevices.reSetFactoryMode();
    }
}
