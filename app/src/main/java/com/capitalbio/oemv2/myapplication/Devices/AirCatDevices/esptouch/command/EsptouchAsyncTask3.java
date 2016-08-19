package com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.command;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.EsptouchTask;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.IEsptouchListener;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.IEsptouchResult;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.IEsptouchTask;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.ISearchMacCallBack;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.ISetTargetApCallBack;
import com.capitalbio.oemv2.myapplication.Utils.PreferencesUtils;

import java.util.List;


public class EsptouchAsyncTask3 extends
        AsyncTask<String, Void, List<IEsptouchResult>> {

    public static final String TAG = "EsptouchAsyncTask3";

    private Context mContext;

    private IEsptouchListener mListener;

    private ProgressDialog mProgressDialog;

    private IEsptouchTask mEsptouchTask;
    private ISetTargetApCallBack setTargetApCallBack;
    private ISearchMacCallBack searchMacCallBack;


    // without the lock, if the user tap confirm and cancel quickly enough,
    // the bug will arise. the reason is follows:
    // 0. task is starting created, but not finished
    // 1. the task is cancel for the task hasn't been created, it do nothing
    // 2. task is created
    // 3. Oops, the task should be cancelled, but it is running
    private final Object mLock = new Object();

    public EsptouchAsyncTask3(Context context, IEsptouchListener listener, ISetTargetApCallBack setTargetApCallBack, ISearchMacCallBack searchMacCallBack) {
        mContext = context;
        mListener = listener;
        this.setTargetApCallBack = setTargetApCallBack;
        this.searchMacCallBack = searchMacCallBack;
    }

    @Override
    protected void onPreExecute() {
        Log.d(TAG, "before send broadcast");
    }

    @Override
    protected List<IEsptouchResult> doInBackground(String... params) {

        List<IEsptouchResult> resultList = null;
        
        if (Constant.AIRCAT_SEARCH_MAC_COMMAND.equals(params[0])) {
            Log.d(TAG, "aircat command is " + params[0]);
            // 为搜索空气猫mac新增加的逻辑,此函数属于android原生逻辑，不容易扩展新的方法
            mEsptouchTask = new EsptouchTask(null, null, null,
                    false, mContext, Constant.AIRCAT_SEARCH_COMMAND, setTargetApCallBack, searchMacCallBack);
            resultList = mEsptouchTask.executeForResults(5);
        } else {
            Log.d(TAG, "command is " + "setTargetAp");
            PreferencesUtils.putBoolean(MyApplication.getInstance(), "isSetTargetAp", true);
            int taskResultCount = -1;
            synchronized (mLock) {
                String apSsid = params[0];
                String apBssid = params[1];
                String apPassword = params[2];
                String isSsidHiddenStr = params[3];
                String taskResultCountStr = params[4];
                boolean isSsidHidden = false;
                if (isSsidHiddenStr.equals("YES")) {
                    isSsidHidden = true;
                }
                taskResultCount = Integer.parseInt(taskResultCountStr);
                mEsptouchTask = new EsptouchTask(apSsid, apBssid, apPassword,
                        isSsidHidden, mContext, Constant.AIRCAT_SET_AP_COMMAND, setTargetApCallBack, searchMacCallBack);
                // 设置自身为监听器
                mEsptouchTask.setEsptouchListener(mListener);
            }
            // 执行
            resultList = mEsptouchTask.executeForResults(taskResultCount);
        }

        return resultList;
    }

    @Override
    protected void onPostExecute(List<IEsptouchResult> result) {
        for (IEsptouchResult tmp : result) {
            Log.d(TAG,"bssid is " + tmp.getBssid() + " address is " + tmp.getInetAddress());
        }
    }

    private List<IEsptouchResult> searchMacAddresss() {
        
        return null;
    }

    public void stopPreviousTask() {
        if (mEsptouchTask != null) {
            mEsptouchTask.interrupt();
        }
    }


}
