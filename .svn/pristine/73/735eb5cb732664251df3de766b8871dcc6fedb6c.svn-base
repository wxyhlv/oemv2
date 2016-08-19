package com.capitalbio.oemv2.myapplication.Devices.BodyFatDevices.src.com.bayh.bluetooth.fatScale.command;

import android.os.Message;

import com.capitalbio.oemv2.myapplication.Devices.BodyFatDevices.src.com.bayh.bluetooth.util.Constant;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;

public class FatScaleSetCommand extends BodyFatCommand {

	public static final String TAG = "FatScaleSetCommand";

	private byte[] info;
	public FatScaleSetCommand(byte[] info){
		super();
		this.info = info;
	}

	@Override
	public void excuteCommand() {
		super.excuteCommand();

		if (isBodyFatConnected) {
			Message msg = new Message();
			msg.what = Constant.FATSCALE_PRESS_COMMAND_SET;
			msg.obj = this.info;
			fatScaleStateMachine.sendMessageDelayed(msg, 1000l);
		} else {
			OemLog.log(TAG, "bodyfat is not connected ignore...");
		}

	}

}
