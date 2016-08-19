package com.capitalbio.oemv2.myapplication.Devices.Bracelete.BraceleteCommand;

import android.content.Context;
import android.util.Log;

/**
 * Created by chengkun on 15-12-16.
 */
public class SetGoalDisBraceleteCommand extends BraceleteCommand {

    public static final String TAG = "SetGoalDisBracelete";
    private int mDistance;

    public SetGoalDisBraceleteCommand(Context context, int dis) {
        super();
        mDistance = dis;
    }

    @Override
    public void excuteCommand() {
        Log.d(TAG, "excuteCommand...");
        braceleteDevices.setGoalDis(mDistance);
    }
}
