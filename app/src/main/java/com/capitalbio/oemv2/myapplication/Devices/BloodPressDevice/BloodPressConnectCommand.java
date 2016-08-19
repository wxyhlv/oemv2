package com.capitalbio.oemv2.myapplication.Devices.BloodPressDevice;


import android.os.Message;

import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.BraceleteLib.BraceleteDevices;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;

public class BloodPressConnectCommand extends BloodPressCommand {
    
    public static final String TAG = "BloodPressConnectCommand";
    private String targetAddress;

    public BloodPressConnectCommand(String address) {
        targetAddress = address;
    }

    /**
     * 在未连接状态
     * 向状态机发送连接消息
     */
    @Override
    public void excuteCommand() {
        OemLog.log(TAG, "connect...");
        BraceleteDevices.getInstance().ignoreCommandExecute();
        Message msg = new Message();
        msg.what = Constant.BLOOD_PRESS_COMMAND_CONNECT;
        msg.obj = targetAddress;
        stateMachine.sendMessage(msg);
    }

    public void setTargetAddress(String targetAddress) {
        this.targetAddress = targetAddress;
    }
}
