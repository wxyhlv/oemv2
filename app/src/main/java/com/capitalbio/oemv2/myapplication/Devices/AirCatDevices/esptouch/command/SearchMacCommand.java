package com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.command;

import android.content.Context;

import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.IEsptouchListener;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.IEsptouchResult;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.ISearchMacCallBack;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;

public class SearchMacCommand implements Command{

    public static final String TAG = "SearchMacCommand";
    private Context mContext = MyApplication.getInstance();
    private AirCatDevices mAirCatDevices;
    private boolean isAircatSearch = false;
    private IEsptouchListener myListener = new IEsptouchListener() {
        @Override
        public void onEsptouchResultAdded(IEsptouchResult result) {

        }
    };

    public SearchMacCommand(ISearchMacCallBack searchMacCallBack) {
        mAirCatDevices = new AirCatDevices(mContext, myListener, null, null, searchMacCallBack);
    }
    
    @Override
    public void excuteCommand() {
        OemLog.log(TAG, "excuteCommand...");
        mAirCatDevices.searchMacAddress();
    }

    public void stopPreviousTask() {
        if (mAirCatDevices != null) {
            OemLog.log(TAG, "stopPreviousTask...");
            mAirCatDevices.stopPreviousTask();
        }
    }


}
