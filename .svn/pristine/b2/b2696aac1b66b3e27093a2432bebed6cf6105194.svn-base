package com.capitalbio.oemv2.myapplication.Devices.Bracelete.BraceleteCommand;

import android.content.Context;
import android.util.Log;

/**
 * Created by chengkun on 15-12-16.
 */
public class SetGoalCalBraceleteCommand extends BraceleteCommand {

    public static final String TAG = "SetGoalCalBracelete";
    private int mCal;

    public SetGoalCalBraceleteCommand(Context context, int cal) {
        super();
        mCal = cal;
    }

    @Override
    public void excuteCommand() {
        Log.d(TAG, "excuteCommand...");
        braceleteDevices.setGoalSteps(mCal);
    }
}
