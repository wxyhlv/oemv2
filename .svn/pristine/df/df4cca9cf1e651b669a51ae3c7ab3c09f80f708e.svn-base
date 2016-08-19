package com.capitalbio.oemv2.myapplication.Devices.Bracelete.BraceleteCommand;

import com.capitalbio.oemv2.myapplication.Utils.OemLog;

/**
 * Created by chengkun on 15-12-16.
 */
public class SetPersonDataBraceleteCommand extends BraceleteCommand {

    public static final String TAG = "SetPersonDataBracelete";

    private int mSex;
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHeight;
    private int mWeight;


    public SetPersonDataBraceleteCommand(int sex, int year, int month, int day, int height, int weight) {
        super();
        mSex = sex;
        mYear = year;
        mMonth = month;
        mDay = day;
        mHeight = height;
        mWeight = weight;
    }


    @Override
    public void excuteCommand() {
        super.excuteCommand();
        OemLog.log(TAG, "excuteCommand...");
        if (isBraceleteConnected) {
            braceleteDevices.synPersonData(mSex, mYear, mMonth, mDay, mHeight, mWeight);
        } else {
            braceleteDevices.ignoreCommandExecute();
            OemLog.log(TAG, "devices not connected ignore...");
        }
    }
}
