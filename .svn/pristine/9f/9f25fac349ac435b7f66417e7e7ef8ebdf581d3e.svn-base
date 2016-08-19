package com.capitalbio.oemv2.myapplication.Devices.Bracelete.BraceleteCommand;

import android.content.Context;
import android.util.Log;

/**
 * Created by chengkun on 15-12-16.
 */
public class DeleteAllReminderBraceleteCommand extends BraceleteCommand {

    public static final String TAG = "DeleteAllReminderComma";

    private int hour;
    private int min;

    public DeleteAllReminderBraceleteCommand(Context context, int h, int m) {
        super();
        hour = h;
        min = m;
    }

    @Override
    public void excuteCommand() {
        Log.d(TAG, "excuteCommand...");
        braceleteDevices.deleteAReminder(hour, min);
    }
}
