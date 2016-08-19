package com.capitalbio.oemv2.myapplication.Devices.BodyFatDevices.src.com.bayh.bluetooth.fatScale.command;


import android.os.Message;

import com.capitalbio.oemv2.myapplication.BraceleteLib.BraceleteDevices;
import com.capitalbio.oemv2.myapplication.Devices.BodyFatDevices.src.com.bayh.bluetooth.util.Constant;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;

public class FatScaleConnectCommand  extends BodyFatCommand {

	public static final String TAG = "FatScaleConnectCommand";

	//体脂称连接地址
	private String bodyFatAddress;

	public FatScaleConnectCommand() {
		super();
	}

	@Override
	public void excuteCommand() {
		BraceleteDevices.getInstance().ignoreCommandExecute();
		OemLog.log(TAG, "excuteCommand address is " + bodyFatAddress);
		Message msg = new Message();
		msg.obj = bodyFatAddress;
		msg.what = Constant.FATSCALE_PRESS_COMMAND_CONNECT;

		//向状态机发送连接消息
		fatScaleStateMachine.sendMessage(msg);
	}


	public void setBodyFatAddress(String bodyFatAddress) {
		this.bodyFatAddress = bodyFatAddress;
	}
}
