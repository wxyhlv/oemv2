package com.capitalbio.oemv2.myapplication.Devices.Bracelete.BraceleteCommand;

import android.bluetooth.BluetoothAdapter;
import android.util.Log;

import com.capitalbio.oemv2.myapplication.Utils.OemLog;
import com.capitalbio.oemv2.myapplication.Utils.PreferencesUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by chengkun on 15-12-16.
 */
public class ConnectBraceleteCommand extends BraceleteCommand {

    public static final String TAG = "ConnectBraceleteCommand";
    private String address;
    private String preAddress;
    private String newAddress;

    private BluetoothAdapter mAdatpter = BluetoothAdapter.getDefaultAdapter();
    private boolean preConnectState = false;
    private boolean isConnected = false;
    private boolean braceleteInitComeplete = false;
    private BraceletePersonInfo personInfo = new BraceletePersonInfo();

    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            OemLog.log("TimeTask", "TimeTask message...");
            {
                OemLog.log(TAG, "ConnectBraceleteCommand is executed...");
                preConnectState = PreferencesUtils.getBoolean(mContext, "pre_connect_state", false);
                isBraceleteConnected = PreferencesUtils.getBoolean(mContext, "connect_state", false);
                braceleteInitComeplete = PreferencesUtils.getBoolean(mContext, "bracelete_init_complete", false);
                address = PreferencesUtils.getString(mContext, "default_bracelete_address", null);

                OemLog.log(TAG, "pre_connect_state is " + preConnectState + ", isBraceleteConnected is " + isBraceleteConnected  + ", braceleteInitComeplete is " + braceleteInitComeplete);

                if (isBraceleteConnected && !preConnectState && !braceleteInitComeplete) {

                    OemLog.log(TAG, "connect successful SyncTimeBraceleteCommand... preConnect address is " + preAddress + ", current address is " + address + ", address === preaddress is " + address.equals(preAddress));
                    //每次断开重新连接都需要同步时间
                    braceleteDevices.addCommandToQueue(new SyncTimeBraceleteCommand());
                    //设置为不自动删除数据
                    braceleteDevices.addCommandToQueue(new SetManualModeBraceleteCommand(0X03));

                    //如果绑定的蓝牙地址发生变化，则清空手环的数据
                    if (!address.equals(preAddress) && preAddress != null) {
                        OemLog.log(TAG, "bind new bracelete devices and reset the person info...");
                        //给手环设置个人信息
                        personInfo.sex = PreferencesUtils.getString(mContext, "sex", "");
                        String tmp = PreferencesUtils.getString(mContext, "birth", "");
                        String [] YMD;
                        tmp = tmp.replace("年", "_");
                        tmp = tmp.replace("月", "_");
                        tmp = tmp.replace("日", "_");
                        YMD = tmp.split("_");

                        personInfo.birthYear = Integer.valueOf(YMD[0]);
                        personInfo.birthMon = Integer.valueOf(YMD[1]);
                        personInfo.birDay = Integer.valueOf(YMD[2]);
                        personInfo.heitht = Integer.parseInt(PreferencesUtils.getString(mContext, "height", "170"));
                        personInfo.weitht = Integer.parseInt(PreferencesUtils.getString(mContext, "weight", "65"));
                        OemLog.log(TAG, "birth year is " + personInfo.birthYear + ", birth mon is " + personInfo.birthMon + ", birth day is " + personInfo.birDay + ", weight is " + personInfo.weitht  + ", height is " + personInfo.heitht);
                        braceleteDevices.addCommandToQueue(new SetPersonDataBraceleteCommand("男".equals(personInfo.sex) ? 0 : 1, personInfo.birthYear, personInfo.birthMon, personInfo.birDay, personInfo.heitht, personInfo.weitht));
                    }

                    //更新之前的连接状态信息
                    PreferencesUtils.putBoolean(mContext, "pre_connect_state", true);
                    PreferencesUtils.putBoolean(mContext, "bracelete_init_complete", true);
                    preAddress = address;
                    return;
                }

                if (!mAdatpter.isEnabled() || isBraceleteConnected) {
                    OemLog.log(TAG, "bluetooth statu is " + mAdatpter.isEnabled() + ", bracelete state is " + isBraceleteConnected + ", ignore the command...");
                    return;
                }
                //修改连接命令的重连机制，放到命令中去进行重新连接
                try {
                        if (!mAdatpter.isEnabled() || isBraceleteConnected) {
                            OemLog.log(TAG, "bluetooth state is off can not to connect to bracelete...");
                            return;
                        }

                        OemLog.log(TAG, "before connect address is " + address);
                        if (address != null && !isBraceleteConnected) {
                            OemLog.log(TAG, "connetc address is " + address);
                            braceleteDevices.disconnect();
                            braceleteDevices.connect(address, null);
                        }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public ConnectBraceleteCommand(String addr) {
        super();
        address = addr;
        timer.schedule(task, 2000, 10 * 1000);
    }

    @Override
    public void excuteCommand() {
        super.excuteCommand();
        Log.d(TAG, "excuteCommand...");

        //连接命令不应该阻塞其他命令的执行
        braceleteDevices.ignoreCommandExecute();

    }

    public void setAddress(String address) {
        this.address = address;
    }


    public boolean isCommandRunning() {
        return isBraceleteConnected;
    }



    private class BraceletePersonInfo {
        public String sex;
        public int birthYear;
        public int birthMon;
        public int birDay;
        public int heitht;
        public int weitht;
    }


}














