package com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.task;

import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.Const;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.EsptouchResult;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.IEsptouchListener;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.IEsptouchResult;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.ISearchMacCallBack;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.ISetTargetApCallBack;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.command.Constant;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.command.SearchMacCommand;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.protocol.EsptouchGenerator;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.udp.IUdpDataGetComeplete;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.udp.UDPSocketClient;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.udp.UDPSocketServer;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.util.ByteUtil;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.util.EspNetUtil;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;
import com.capitalbio.oemv2.myapplication.Utils.PreferencesUtils;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class __EsptouchTask implements __IEsptouchTask, IUdpDataGetComeplete{

    /**
     * one indivisible data contain 3 9bits info
     */
    private static final int ONE_DATA_LEN = 3;

    private static final String TAG = "EsptouchTask";

    private volatile List<IEsptouchResult> mEsptouchResultList;
    private volatile boolean mIsSuc = false;
    private volatile boolean mIsInterrupt = false;
    private volatile boolean mIsExecuted = false;
    private boolean isDataReiceved = false;
    private final UDPSocketClient mSocketClient;
    private final UDPSocketServer mSocketServer;
    private String mApSsid;
    private String mApBssid;
    private final boolean mIsSsidHidden;
    private String mApPassword;
    private final Context mContext;
    private AtomicBoolean mIsCancelled;
    // 链路监听参数类
    private IEsptouchTaskParameter mParameter;
    private volatile Map<String, Integer> mBssidTaskSucCountMap;
    private IEsptouchListener mEsptouchListener;
    private ISetTargetApCallBack setTargetApCallBack;
    private ISearchMacCallBack searchMacCallBack;

    public __EsptouchTask(String apSsid, String apBssid, String apPassword,
            Context context, IEsptouchTaskParameter parameter,
            boolean isSsidHidden, ISetTargetApCallBack setTargetApCallBack) {
        if (TextUtils.isEmpty(apSsid)) {
            throw new IllegalArgumentException(
                    "the apSsid should be null or empty");
        }
        if (apPassword == null) {
            apPassword = "";
        }
        mContext = context;
        mApSsid = apSsid;
        mApBssid = apBssid;
        mApPassword = apPassword;
        mIsCancelled = new AtomicBoolean(false);
        mSocketClient = new UDPSocketClient();
        mParameter = parameter;
        mSocketServer = new UDPSocketServer(mParameter.getPortListening(),
                mParameter.getWaitUdpTotalMillisecond(), context, setTargetApCallBack, null);
        mIsSsidHidden = isSsidHidden;
        mEsptouchResultList = new ArrayList<IEsptouchResult>();
        mBssidTaskSucCountMap = new HashMap<String, Integer>();
        this.setTargetApCallBack = setTargetApCallBack;
    }

    public __EsptouchTask(Context context, IEsptouchTaskParameter parameter,
            boolean isSsidHidden, ISearchMacCallBack searchMacCallBack) {

        mContext = context;
        mIsCancelled = new AtomicBoolean(false);
        mSocketClient = new UDPSocketClient();
        mParameter = parameter;
        mSocketServer = new UDPSocketServer(mParameter.getPortListening(),
                mParameter.getWaitUdpTotalMillisecond(), context, null, searchMacCallBack);
        mSocketServer.setUdpDataGetComeplete(this);
        mIsSsidHidden = isSsidHidden;
        mEsptouchResultList = new ArrayList<IEsptouchResult>();
        mBssidTaskSucCountMap = new HashMap<String, Integer>();
        this.searchMacCallBack = searchMacCallBack;
    }

    private void __putEsptouchResult(boolean isSuc, String bssid,
            InetAddress inetAddress) {
        synchronized (mEsptouchResultList) {
            // check whether the result receive enough UDP response
            boolean isTaskSucCountEnough = false;
            Integer count = mBssidTaskSucCountMap.get(bssid);
            if (count == null) {
                count = 0;
            }
            ++count;
            if (__IEsptouchTask.DEBUG) {
                Log.d(TAG, "__putEsptouchResult(): count = " + count);
            }
            mBssidTaskSucCountMap.put(bssid, count);
            isTaskSucCountEnough = count >= mParameter
                    .getThresholdSucBroadcastCount();
            if (!isTaskSucCountEnough) {
                if (__IEsptouchTask.DEBUG) {
                    Log.d(TAG, "__putEsptouchResult(): count = " + count
                            + ", isn't enough");
                }
                return;
            }
            // check whether the result is in the mEsptouchResultList already
            boolean isExist = false;
            for (IEsptouchResult esptouchResultInList : mEsptouchResultList) {
                if (esptouchResultInList.getBssid().equals(bssid)) {
                    isExist = true;
                    break;
                }
            }
            // only add the result who isn't in the mEsptouchResultList
            if (!isExist) {
                if (__IEsptouchTask.DEBUG) {
                    Log.d(TAG, "__putEsptouchResult(): put one more result");
                }
                final IEsptouchResult esptouchResult = new EsptouchResult(
                        isSuc, bssid, inetAddress);
                mEsptouchResultList.add(esptouchResult);
                if (mEsptouchListener != null) {
                    mEsptouchListener.onEsptouchResultAdded(esptouchResult);
                }
            }
        }
    }

    private List<IEsptouchResult> __getEsptouchResultList() {
        synchronized (mEsptouchResultList) {
            if (mEsptouchResultList.isEmpty()) {
                EsptouchResult esptouchResultFail = new EsptouchResult(false,
                        null, null);
                esptouchResultFail.setIsCancelled(mIsCancelled.get());
                mEsptouchResultList.add(esptouchResultFail);
            }

            return mEsptouchResultList;
        }
    }

    private synchronized void __interrupt() {
        if (!mIsInterrupt) {
            mIsInterrupt = true;
            mSocketClient.interrupt();
            mSocketServer.interrupt();
            // interrupt the current Thread which is used to wait for udp
            // response
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void interrupt() {
        if (__IEsptouchTask.DEBUG) {
            Log.d(TAG, "interrupt()");
        }
        mIsCancelled.set(true);
        __interrupt();
    }

    private void __listenAsyn(final int expectDataLen) {
        new Thread() {
            public void run() {
                if (__IEsptouchTask.DEBUG) {
                    Log.d(TAG, "__listenAsyn() start");
                }
                long startTimestamp = System.currentTimeMillis();
                byte[] apSsidAndPassword = ByteUtil.getBytesByString(mApSsid
                        + mApPassword);
                byte expectOneByte = (byte) (apSsidAndPassword.length + 9);
                if (__IEsptouchTask.DEBUG) {
                    Log.i(TAG, "expectOneByte: " + (0 + expectOneByte));
                }
                byte receiveOneByte = -1;
                byte[] receiveBytes = null;
                while (mEsptouchResultList.size() < mParameter
                        .getExpectTaskResultCount() && !mIsInterrupt) {

                    receiveBytes = mSocketServer
                            .receiveSpecLenBytes(expectDataLen);
                    Log.d(TAG,
                            "receiveBytes array is "
                                    + Arrays.toString(receiveBytes));
                    if (receiveBytes != null) {
                        receiveOneByte = receiveBytes[0];
                    } else {
                        receiveOneByte = -1;
                    }
                    if (receiveOneByte == expectOneByte) {
                        if (__IEsptouchTask.DEBUG) {
                            Log.i(TAG, "receive correct broadcast");
                        }
                        // change the socket's timeout
                        long consume = System.currentTimeMillis()
                                - startTimestamp;
                        int timeout = (int) (mParameter
                                .getWaitUdpTotalMillisecond() - consume);
                        if (timeout < 0) {
                            if (__IEsptouchTask.DEBUG) {
                                Log.i(TAG, "esptouch timeout");
                                if (setTargetApCallBack != null) {
                                    PreferencesUtils.putBoolean(MyApplication.getInstance(), "isSetTargetAp", false);
                                    setTargetApCallBack.onSetTargetAp(Const.AIRCAT_COMMAND_SETTARGETAP_FAILTURE);
                                }
                            }
                            break;
                        } else {
                            if (__IEsptouchTask.DEBUG) {
                                Log.i(TAG, "mSocketServer's new timeout is "
                                        + timeout + " milliseconds");
                            }
                            mSocketServer.setSoTimeout(timeout);
                            if (__IEsptouchTask.DEBUG) {
                                Log.i(TAG, "receive correct broadcast");
                            }
                            if (receiveBytes != null) {
                                String bssid = ByteUtil.parseBssid(
                                        receiveBytes,
                                        mParameter.getEsptouchResultOneLen(),
                                        mParameter.getEsptouchResultMacLen());
                                InetAddress inetAddress = EspNetUtil
                                        .parseInetAddr(
                                                receiveBytes,
                                                mParameter
                                                        .getEsptouchResultOneLen()
                                                        + mParameter
                                                        .getEsptouchResultMacLen(),
                                                mParameter
                                                        .getEsptouchResultIpLen());
                                __putEsptouchResult(true, bssid, inetAddress);
                                OemLog.log(TAG, "search successful " + "setTargetApCallBack is " + searchMacCallBack);
                                //增加回调接口
                                if (setTargetApCallBack != null) {
                                    PreferencesUtils.putBoolean(MyApplication.getInstance(), "isSetTargetAp", false);
                                    setTargetApCallBack.onSetTargetAp(Const.AIRCAT_COMMAND_SETTARGETAP_SUCCESSFUL);
                                    SearchMacCommand searchMacCommand = new SearchMacCommand(null);
                                    searchMacCommand.excuteCommand();
                                    break;
                                }
                            }
                        }
                    } else {
                        if (__IEsptouchTask.DEBUG) {
                            Log.i(TAG, "receive rubbish message, just ignore");
                        }
                    }
                }
                mIsSuc = mEsptouchResultList.size() >= mParameter
                        .getExpectTaskResultCount();
                __EsptouchTask.this.__interrupt();
                if (__IEsptouchTask.DEBUG) {
                    Log.d(TAG, "__listenAsyn() finish");
                }
            }
        }.start();
    }

    private boolean __execute(IEsptouchGenerator generator) {

        long startTime = System.currentTimeMillis();
        long currentTime = startTime;
        long lastTime = currentTime
                - mParameter.getTimeoutTotalCodeMillisecond();

        byte[][] gcBytes2 = generator.getGCBytes2();
        byte[][] dcBytes2 = generator.getDCBytes2();

        int index = 0;
        while (!mIsInterrupt) {
            if (currentTime - lastTime >= mParameter
                    .getTimeoutTotalCodeMillisecond()) {
                if (__IEsptouchTask.DEBUG) {
                    Log.d(TAG, "send gc code ");
                }
                // send guide code
                while (!mIsInterrupt
                        && System.currentTimeMillis() - currentTime < mParameter
                                .getTimeoutGuideCodeMillisecond()) {
                    mSocketClient.sendData(gcBytes2,
                            mParameter.getTargetHostname(),
                            mParameter.getTargetPort(),
                            mParameter.getIntervalGuideCodeMillisecond());
                    // check whether the udp is send enough time
                    if (System.currentTimeMillis() - startTime > mParameter
                            .getWaitUdpSendingMillisecond()) {
                        break;
                    }
                }
                lastTime = currentTime;
            } else {
                mSocketClient.sendData(dcBytes2, index, ONE_DATA_LEN,
                        mParameter.getTargetHostname(),
                        mParameter.getTargetPort(),
                        mParameter.getIntervalDataCodeMillisecond());
                index = (index + ONE_DATA_LEN) % dcBytes2.length;
            }
            currentTime = System.currentTimeMillis();
            // check whether the udp is send enough time
            if (currentTime - startTime > mParameter
                    .getWaitUdpSendingMillisecond()) {
                break;
            }
        }

        return mIsSuc;
    }

    private void __checkTaskValid() {
        // !!!NOTE: the esptouch task could be executed only once
        if (this.mIsExecuted) {
            throw new IllegalStateException(
                    "the Esptouch task could be executed only once");
        }
        this.mIsExecuted = true;
    }

    @Override
    public IEsptouchResult executeForResult() throws RuntimeException {
        return executeForResults(1).get(0);
    }

    @Override
    public boolean isCancelled() {
        return this.mIsCancelled.get();
    }

    @Override
    public List<IEsptouchResult> executeForResults(int expectTaskResultCount)
            throws RuntimeException {
        __checkTaskValid();

        mParameter.setExpectTaskResultCount(expectTaskResultCount);

        if (__IEsptouchTask.DEBUG) {
            Log.d(TAG, "execute()");
        }
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new RuntimeException(
                    "Don't call the esptouch Task at Main(UI) thread directly.");
        }
        InetAddress localInetAddress = EspNetUtil.getLocalInetAddress(mContext);
        if (__IEsptouchTask.DEBUG) {
            Log.i(TAG, "localInetAddress: " + localInetAddress);
        }
        // generator the esptouch byte[][] to be transformed, which will cost
        // some time(maybe a bit much)
        IEsptouchGenerator generator = new EsptouchGenerator(mApSsid, mApBssid,
                mApPassword, localInetAddress, mIsSsidHidden);
        // listen the esptouch result asyn

        if (mParameter.getPortListening() == Constant.AIRCAT_SET_AP_COMMAND_LISTEN_PORT) {
            // 走原来的逻辑
            __listenAsyn(mParameter.getEsptouchResultTotalLen());
        }
        boolean isSuc = false;
        for (int i = 0; i < mParameter.getTotalRepeatTime(); i++) {
            isSuc = __execute(generator);
            if (isSuc) {
                return __getEsptouchResultList();
            }
        }

        if (!mIsInterrupt) {
            // wait the udp response without sending udp broadcast
            try {
                Thread.sleep(mParameter.getWaitUdpReceivingMillisecond());
            } catch (InterruptedException e) {
                // receive the udp broadcast or the user interrupt the task
                if (this.mIsSuc) {
                    return __getEsptouchResultList();
                } else {
                    this.__interrupt();
                    return __getEsptouchResultList();
                }
            }
            this.__interrupt();
        }

        return __getEsptouchResultList();
    }

    @Override
    public void setEsptouchListener(IEsptouchListener esptouchListener) {
        mEsptouchListener = esptouchListener;
    }

    public void listenMacBroadcast(final int expectDataLen) {
        
        //开启一个udp监听线程
        new Thread() {
            public void run() {
                if (__IEsptouchTask.DEBUG) {
                    Log.d(TAG, "__listenMacAsyn() start");
                }
                long startTimestamp = System.currentTimeMillis();

                byte receiveOneByte = -1;
                byte[] receiveBytes = null;
                while (mEsptouchResultList.size() < mParameter
                        .getExpectTaskResultCount() && !mIsInterrupt && !isDataReiceved) {
                    receiveBytes = mSocketServer
                            .receiveMacSpecLenBytes(expectDataLen);

                    // change the socket's timeout
                    long consume = System.currentTimeMillis() - startTimestamp;
                    int timeout = (int) (mParameter
                            .getWaitUdpTotalMillisecond() - consume);
                    if (timeout < 0) {
                        if (__IEsptouchTask.DEBUG) {
                            Log.i(TAG, "esptouch timeout");
                        }
                        break;
                    } else {
                        mSocketServer.setSoTimeout(timeout);
                        if (__IEsptouchTask.DEBUG) {
                            Log.i(TAG,
                                    "receive correct broadcast and reset timeout");
                        }
                        if (receiveBytes != null) {
                            // 进行具体的业务处理
                            Log.d(TAG,
                                    "receive data byte code is "
                                            + Arrays.toString(receiveBytes));
                            mIsSuc = true;
                        } else {
                            if (__IEsptouchTask.DEBUG) {
                                Log.i(TAG,
                                        "receive rubbish message, just ignore");
                            }
                        }
                    }
                }
                __EsptouchTask.this.__interrupt();
                if (__IEsptouchTask.DEBUG) {
                    Log.d(TAG, "__listenAsyn() finish");
                }
            }
        }.start();

    }

    @Override
    public List<IEsptouchResult> executeForSearchMacResults(
            int expectTaskResultCount) throws RuntimeException {
        __checkTaskValid();
        mParameter.setExpectTaskResultCount(expectTaskResultCount);

        if (__IEsptouchTask.DEBUG) {
            Log.d(TAG, "execute()");
        }
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new RuntimeException(
                    "Don't call the esptouch Task at Main(UI) thread directly.");
        }

        listenMacBroadcast(128);

        boolean isSuc = false;
        for (int i = 0; i < mParameter.getTotalRepeatTime(); i++) {
            isSuc = executeSearchMacAddress();
            if (isSuc) {
                return __getEsptouchResultList();
            }
        }

        if (!mIsInterrupt) {
            // wait the udp response without sending udp broadcast
            try {
                Thread.sleep(mParameter.getWaitUdpReceivingMillisecond());
            } catch (InterruptedException e) {
                // receive the udp broadcast or the user interrupt the task
                if (this.mIsSuc) {
                    return __getEsptouchResultList();
                } else {
                    this.__interrupt();
                    return __getEsptouchResultList();
                }
            }
            this.__interrupt();
        }

        return __getEsptouchResultList();
    }

    private boolean executeSearchMacAddress() {

        long startTime = System.currentTimeMillis();
        long currentTime = startTime;
        long lastTime = currentTime
                - mParameter.getTimeoutTotalCodeMillisecond();

        int index = 0;
        while (!mIsInterrupt) {
            if (currentTime - lastTime >= mParameter
                    .getTimeoutTotalCodeMillisecond()) {
                OemLog.log(TAG, "send query packet...");
                if (__IEsptouchTask.DEBUG) {
                    Log.d(TAG, "send gc code ");
                }
                // send guide code
                while (!mIsInterrupt
                        && System.currentTimeMillis() - currentTime < mParameter
                                .getTimeoutGuideCodeMillisecond()) {
                    mSocketClient.sendData(null,
                            mParameter.getTargetHostname(),
                            mParameter.getTargetPort(),
                            mParameter.getIntervalGuideCodeMillisecond());
                    // check whether the udp is send enough time
                    if (System.currentTimeMillis() - startTime > mParameter
                            .getWaitUdpSendingMillisecond()) {

                        break;
                    }
                    try {
                        Thread.sleep(600l);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                lastTime = currentTime;
            } else {
                OemLog.log(TAG, "send query packet timeout...");
                PreferencesUtils.putBoolean(MyApplication.getInstance(), "isAircatSearchMac", false);
            }
            currentTime = System.currentTimeMillis();
            // check whether the udp is send enough time
            if (currentTime - startTime > mParameter
                    .getWaitUdpSendingMillisecond()) {
                break;
            }
        }

        return mIsSuc;

    }

    @Override
    public void onDataGetComeplete() {
        OemLog.log("onDataGetComeplete", "reiceve data close the socket...");
        isDataReiceved = true;
    }
}
