package com.capitalbio.oemv2.myapplication.Devices.Bracelete.BraceleteCommand;

import android.content.Context;
import android.util.Log;

/**
 * Created by chengkun on 15-12-16.
 */
public class SetSocialMesCountBraceleteCommand extends BraceleteCommand {

    public static final String TAG = "SetGoalCalBracelete";

    private int mesCount;

    public SetSocialMesCountBraceleteCommand(Context context, int count) {
        super();
        mesCount = count;
    }

    @Override
    public void excuteCommand() {
        Log.d(TAG, "excuteCommand...");
        braceleteDevices.setPushUSocNum(mesCount);
    }
}
