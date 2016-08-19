package com.capitalbio.oemv2.myapplication.Devices.BloodPressDevice;

import android.util.Log;


public class BloodPressDisconnectCommand extends BloodPressCommand {
    
    public static final String TAG = "BloodPressDisconnectCom";
    
    @Override
    public void excuteCommand() {
        Log.d(TAG, "disconnect...");
        stateMachine.sendMessage(Constant.BLOOD_PRESS_COMMAND_DISCONNECT);
    }

}
