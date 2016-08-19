package com.capitalbio.oemv2.myapplication.Base;

import android.content.Context;
import android.mtp.MtpConstants;
import android.os.Handler;

import com.capitalbio.oemv2.myapplication.BraceleteLib.BraceleteDevices;
import com.capitalbio.oemv2.myapplication.Devices.Bracelete.OemBackgroundService;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import org.w3c.dom.ProcessingInstruction;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by chengkun on 15-12-27.
 * 后台一直存在的搜索线程
 */
public class OemBleConnectThread extends Thread {
    public static final String TAG = "OemBleConnectThread";
    private BraceleteDevices braceleteDevices = BraceleteDevices.getInstance();
    private Context mContext = MyApplication.getInstance();

    private boolean isBleSearching = false;

    private Handler servicesCallBackHandler;



    @Override
    public void run() {
        while (true) {
            OemLog.log(TAG, "isAutoSearch is " + MyApplication.getInstance().isAllowBleAutoScan() + " isBleSearching " + isBleSearching);
            try {
                //每隔500毫秒检查一下是否需要启动搜索
                if (MyApplication.getInstance().isAllowBleAutoScan() && !isBleSearching) {
                    braceleteDevices.scanAutoConnect();
                    isBleSearching = true;
                    servicesCallBackHandler.sendEmptyMessageDelayed(OemBackgroundService.BLE_AUTO_SEARCH_COMEPLETE, 10 * 1000);

                }
                sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setServicesCallBackHandler(Handler servicesCallBackHandler) {
        this.servicesCallBackHandler = servicesCallBackHandler;
    }

    public void notifyAutoSearchComeplete() {
        isBleSearching = false;
    }

}
