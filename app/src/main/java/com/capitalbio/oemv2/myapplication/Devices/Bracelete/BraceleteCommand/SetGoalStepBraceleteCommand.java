package com.capitalbio.oemv2.myapplication.Devices.Bracelete.BraceleteCommand;

import android.util.Log;

import com.capitalbio.oemv2.myapplication.Utils.OemLog;

/**
 * Created by chengkun on 15-12-16.
 */
public class SetGoalStepBraceleteCommand extends BraceleteCommand {

    public static final String TAG = "SetGoalStepBracelete";
    private int steps;

    public SetGoalStepBraceleteCommand(int step) {
        super();
        steps = step;
    }

    @Override
    public void excuteCommand() {
        super.excuteCommand();
        Log.d(TAG, "excuteCommand...");
        if (isBraceleteConnected) {
            braceleteDevices.setGoalSteps(steps);
        } else {
            OemLog.log(TAG, "bracelete is not connected ignore...");
        }

    }
}
