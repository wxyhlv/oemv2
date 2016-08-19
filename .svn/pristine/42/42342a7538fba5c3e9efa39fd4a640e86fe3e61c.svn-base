package com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.command;

import android.content.Context;

import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.IEsptouchListener;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.IEsptouchResult;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.ISetTargetApCallBack;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;

//设置空气猫外连ap的命令
public class SetTargetAPCommand implements Command {

    public static final String TAG = "SetTargetAPCommand";

    private Context mContext = MyApplication.getInstance();
    
    private IEsptouchListener mListener;

    private boolean isSetTargetAp = false;

    private AirCatDevices mAirCatDevices;

    private IEsptouchListener myListener = new IEsptouchListener() {
        @Override
        public void onEsptouchResultAdded(IEsptouchResult result) {

        }
    };

    public SetTargetAPCommand(String wifiPassWord, ISetTargetApCallBack setTargetApCallBack) {
        mAirCatDevices = new AirCatDevices(mContext, myListener, wifiPassWord, setTargetApCallBack, null);
    }

    @Override
    public void excuteCommand() {
        OemLog.log(TAG, "excuteCommand...");
        mAirCatDevices.setTargetAp();
    }

    public void stopPreviousTask() {
        if (mAirCatDevices != null) {
            OemLog.log(TAG, "stopPreviousTask...");
            mAirCatDevices.stopPreviousTask();
        }
    }

}
