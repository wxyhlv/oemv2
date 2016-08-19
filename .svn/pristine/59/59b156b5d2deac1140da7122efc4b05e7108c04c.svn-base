package com.capitalbio.oemv2.myapplication.Devices;

import android.content.Context;
import android.os.Handler;

import com.capitalbio.oemv2.myapplication.Base.BaseCommand;
import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.BraceleteLib.BraceleteDevices;
import com.capitalbio.oemv2.myapplication.Const.Const;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;
import com.capitalbio.oemv2.myapplication.Utils.PreferencesUtils;

/**
 * Created by chengkun on 15-12-17.
 */
public class CommandExecuteThread  extends Thread{

    public static final String TAG = "CommandExecuteThread";

    private BraceleteDevices braceleteDevices = BraceleteDevices.getInstance();
    private int threadStaus = Const.BRACELETE_COMMAND_EXECUTE_STATUS_INITIAL;
    private boolean isConnected = false;
    private Context mContext = MyApplication.getInstance();
    private Handler handler;

    @Override
    public void run() {
        BaseCommand currentCommand = null;

        synchronized (this) {
            try {
                while (true) {
                    isConnected = PreferencesUtils.getBoolean(mContext, "connect_state", false);
                    OemLog.log(TAG, "current status is " + threadStaus + " connect state is " + isConnected);
                    //所有命令的执行都必须在连接状态下且上一个命令执行完成的情况下进行
                    if ((isConnected && threadStaus == Const.BRACELETE_COMMAND_EXECUTE_STATUS_COMPLETE || threadStaus == Const.BRACELETE_COMMAND_EXECUTE_STATUS_INITIAL)) {
                        currentCommand = braceleteDevices.pullCommandFromQueue();
                        OemLog.log(TAG, " before command executeing... command is " + ((currentCommand == null) ? currentCommand : currentCommand.getClass().getSimpleName()));
                        if (currentCommand != null) {
                            currentCommand.excuteCommand();
                            threadStaus = Const.BRACELETE_COMMAND_EXECUTE_STATUS_RUNNING;
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
}
