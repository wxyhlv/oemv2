package com.capitalbio.oemv2.myapplication.Devices.Bracelete;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.capitalbio.oemv2.myapplication.Base.BaseCommand;
import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.BraceleteLib.BraceleteDevices;
import com.capitalbio.oemv2.myapplication.Const.Const;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;
import com.capitalbio.oemv2.myapplication.Utils.PreferencesUtils;

/**
 * Created by chengkun on 15-12-17.
 * 此线程类主要负责从队列中取出命令并
 * 不断执行命令，如果命令在5秒内无响应
 * 则执行下一条命令
 */
public class CommandExecuteThread extends Thread {

    public static final String TAG = "CommandExecuteThread";

    private static final int DELAY_MESSAGE = 0;
    private BraceleteDevices braceleteDevices = BraceleteDevices.getInstance();
    private int threadStaus = Const.BRACELETE_COMMAND_EXECUTE_STATUS_INITIAL;
    private boolean isConnected = false;
    private Context mContext = MyApplication.getInstance();
    private TimeOutHandler timeOutHandler;


    public CommandExecuteThread() {
        timeOutHandler = new TimeOutHandler();
    }

    @Override
    public void run() {
        BaseCommand currentCommand = null;
        synchronized (this) {
            try {
                while (true) {
                    isConnected = PreferencesUtils.getBoolean(mContext, "connect_state", false);
                    OemLog.log(TAG, "current status is " + threadStaus + " connect state is " + isConnected);
                    //所有命令的执行都必须在连接状态下且上一个命令执行完成的情况下进行
                    if (threadStaus == Const.BRACELETE_COMMAND_EXECUTE_STATUS_COMPLETE || threadStaus == Const.BRACELETE_COMMAND_EXECUTE_STATUS_INITIAL) {
                        currentCommand = braceleteDevices.pullCommandFromQueue();
                        OemLog.log(TAG, " before command executeing... command is " + ((currentCommand == null) ? currentCommand : currentCommand.getClass().getSimpleName()));
                        if (currentCommand != null) {
                            if (timeOutHandler.hasMessages(DELAY_MESSAGE)) {
                                timeOutHandler.removeMessages(DELAY_MESSAGE);
                            }
                            currentCommand.excuteCommand();
                            threadStaus = Const.BRACELETE_COMMAND_EXECUTE_STATUS_RUNNING;
                            timeOutHandler.sendEmptyMessageDelayed(DELAY_MESSAGE, Const.OEM_COMMAND_TIMEOUT);
                        }
                    }
                    wait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setThreadStaus(int threadStaus) {
        this.threadStaus = threadStaus;
    }


    private class TimeOutHandler extends Handler {
        public static final String TAG = "TimeOutHandler";
        @Override
        public void handleMessage(Message msg) {
            OemLog.log(TAG, "commandTimeOut set the thread staus execute the next command...");
            switch (msg.what) {
                case DELAY_MESSAGE:
                    threadStaus = Const.BRACELETE_COMMAND_EXECUTE_STATUS_COMPLETE;
                    break;
            }
        }
    }
}
















