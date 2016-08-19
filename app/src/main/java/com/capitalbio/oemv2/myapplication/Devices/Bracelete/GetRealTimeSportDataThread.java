package com.capitalbio.oemv2.myapplication.Devices.Bracelete;

import android.bluetooth.BluetoothAdapter;
import android.os.Handler;

import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.BraceleteLib.BraceleteDevices;
import com.capitalbio.oemv2.myapplication.Devices.Bracelete.BraceleteCommand.GetSportDataTotalBraceleteCommand;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by chengkun on 15-12-23.
 */
public class GetRealTimeSportDataThread extends Thread {

    public static final String TAG = "GetRealTimeSportDataThread";

    public static final int GAVE_UP_EXECUTETION_MESSAGE = 200;

    private boolean isOtherDevicesWindowTime = false;
    private Timer timer;
    private Handler servicesCallBackHandler;
    private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private BraceleteDevices braceleteDevices = BraceleteDevices.getInstance();

    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            isOtherDevicesWindowTime = false;
            task.cancel();
        }
    };

    public GetRealTimeSportDataThread(Handler callBackHandler) {
        servicesCallBackHandler = callBackHandler;
    }
//    private GetSportDataTotalBraceleteCommand getSportDataTotalCommand = new GetSportDataTotalBraceleteCommand();

    @Override
    public void run() {
        try {

            while (true) {
                if (!isOtherDevicesWindowTime && MyApplication.getInstance().isOnBraceleteUI()) {
                    OemLog.log(TAG, "send get real time sport data command isOtherDevicesWindowTime is " + isOtherDevicesWindowTime);
                    if (mBluetoothAdapter.isEnabled()) {
                        braceleteDevices.addCommandToQueue(new GetSportDataTotalBraceleteCommand());
                    }
                    sleep(1000l);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void nofifyTmpPause() {
        OemLog.log(TAG, "nofifyTmpPause...");
        isOtherDevicesWindowTime = true;
        servicesCallBackHandler.sendEmptyMessageDelayed(OemBackgroundService.FOUND_OTHER_DEVICES_NOTIFY_END, 45 * 1000);
    }

    public void notifyOtherDevicesMeasureComeplete() {
        OemLog.log(TAG, "notifyOtherDevicesMeasureComeplete...");
        isOtherDevicesWindowTime = false;
    }


}
