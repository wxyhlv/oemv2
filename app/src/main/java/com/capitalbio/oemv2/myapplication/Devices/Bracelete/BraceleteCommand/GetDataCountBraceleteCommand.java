package com.capitalbio.oemv2.myapplication.Devices.Bracelete.BraceleteCommand;

import android.content.Context;
import android.util.Log;

/**
 * Created by chengkun on 15-12-16.
 */
public class GetDataCountBraceleteCommand extends BraceleteCommand {

    public static final String TAG = "GetDataCount";

    private int dataType;

    public GetDataCountBraceleteCommand(Context context, int type) {
        super();

        dataType = type;
    }

    @Override
    public void excuteCommand() {
        Log.d(TAG, "excuteCommand...");
        braceleteDevices.getSportDataCount(dataType);
    }
}
