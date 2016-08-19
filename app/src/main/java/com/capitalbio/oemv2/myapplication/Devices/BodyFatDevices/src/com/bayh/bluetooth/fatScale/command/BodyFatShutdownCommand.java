package com.capitalbio.oemv2.myapplication.Devices.BodyFatDevices.src.com.bayh.bluetooth.fatScale.command;

import android.os.Message;

import com.capitalbio.oemv2.myapplication.Devices.BodyFatDevices.src.com.bayh.bluetooth.util.Constant;
import com.capitalbio.oemv2.myapplication.Devices.BodyFatDevices.src.com.bayh.sdk.ble.AnalyzeDataUtils;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;

/**
 * Created by chengkun on 16/6/19.
 */
public class BodyFatShutdownCommand extends BodyFatCommand{

    public static final String TAG = "BodyFatShutdownCommand";

    private byte[] info;
    public BodyFatShutdownCommand(){
        super();
        info = AnalyzeDataUtils.getBodyFatShutdownCommand();
    }

    @Override
    public void excuteCommand() {
        super.excuteCommand();

        if (isBodyFatConnected) {
            Message msg = new Message();
            msg.what = Constant.FATSCALE_PRESS_COMMAND_DISCONNECT;

            fatScaleStateMachine.sendMessageDelayed(msg, 1000l);
        } else {
            OemLog.log(TAG, "bodyfat is not connected ignore...");
        }

    }


}
