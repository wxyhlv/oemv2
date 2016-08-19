package com.capitalbio.oemv2.myapplication.Devices.Bracelete.BraceleteCommand;

import android.util.Log;

import com.capitalbio.oemv2.myapplication.Utils.OemLog;

/**
 * Created by chengkun on 15-12-16.
 */
public class AndReminderBraceleteCommand extends BraceleteCommand {

    public static final String TAG = "AndReminderBracelete";

    private int id;
    private int hour;
    private int min;
    private String repeat;

    public AndReminderBraceleteCommand(int i, int h, int m, String re) {
        super();
        id = i;
        hour = h;
        min = m;
        repeat = re;
    }

    @Override
    public void excuteCommand() {
        super.excuteCommand();

        Log.d(TAG, "excuteCommand...");
        if (isBraceleteConnected) {
            braceleteDevices.addAReminder(id, hour, min, repeat);
        } else {
            braceleteDevices.ignoreCommandExecute();
            OemLog.log(TAG, "devices not conneced ignore...");
        }

    }
}
