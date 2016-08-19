package com.capitalbio.oemv2.myapplication.Devices.BloodPressDevice;


import android.content.Context;
import android.os.Message;

import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.StateMachine.State;
import com.capitalbio.oemv2.myapplication.StateMachine.StateMachine;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;


public class BloodPressStateMachine extends StateMachine {

    public static final String TAG = "BloodPressStateMachine"; 


    public static final int BLOOD_PRESS_CONNECT_TIMEOUT = 30001;
    public static final int BLOOD_PRESS_CONNECT_TIMEOUT_TIME = 12 * 1000;

    public static final int BLOOD_PRESS_MEASURE_DEALY_MESSAGE = 9000;
    public static final int BLOOD_PRESS_MEASURE_DEALY_MESSAGE_TIME = 200;


    private static BloodPressStateMachine mInstance;
    private Context mContext;
    //上层ui的回调接口
    private BloodPressCommandListener mBloodPressCommandCallBack;
    private DisConnectState mDisconnectState = new DisConnectState();
    private ConnectedState mConnectedState = new ConnectedState();
    private MeasureingState mMeasureingState = new MeasureingState();
    private PendingState mPendingState = new PendingState();
    private BloodPressDevices mBloodPressDevices = BloodPressDevices.getInstance();
    private CommandResult mCommandResult;
    
    private int currentState;
    
    private BloodPressCommandListener mBloodPressCommandListener = new BloodPressCommandListener() {
        
        @Override
        public void onCommandResult(CommandResult commandResult) {
            OemLog.log(TAG, "command excute result callback command type is " + commandResult.getmBloodPressCommandType() + " command result is " + commandResult.getmBloodPresscommandResult()
                     + " Blood Press bean is " + commandResult.getmBean());
            mCommandResult = commandResult;
            sendMessage(commandResult.getmBloodPresscommandResult());
        }

        @Override
        public void onGattDisconnect() {
            sendMessage(Constant.BLOOD_PRESS_GATT_DISCONNECT);
        }

        @Override
        public void onDiscoverServiceComeplete() {
            sendMessage(Constant.BLOOD_PRESS_DISCOVER_SERVICES_COMEPLETE);
        }
    };
    
    private BloodPressStateMachine() {
        super("BloodPressStateMachine");
        addState(mDisconnectState);
        addState(mConnectedState);
        addState(mMeasureingState);
        addState(mPendingState);
        
        mBloodPressDevices = BloodPressDevices.getInstance();
        
        mBloodPressDevices.registerCallBack(mBloodPressCommandListener);
        setInitialState(mDisconnectState);
    }

    public static BloodPressStateMachine getInstance() {
        OemLog.log(TAG, "getInstance...");
        
        if (mInstance == null) {
            mInstance = new BloodPressStateMachine();
            mInstance.start();
        }
        
        return mInstance;
    }
    
    private class DisConnectState extends State {
        @Override
        public void enter() {
            // TODO Auto-generated method stub
            OemLog.log(TAG, "enter DisConnectState..");
        }
        
        @Override
        public boolean processMessage(Message msg) {
            // TODO Auto-generated method stub
            OemLog.log(TAG, "current state is DisConnectState msg is " + msg.what);
            switch (msg.what) {
            case Constant.BLOOD_PRESS_COMMAND_CONNECT:
                String address = (String) msg.obj;
                mBloodPressDevices.connect(address);
                removeMessages(BLOOD_PRESS_CONNECT_TIMEOUT);
                sendMessageDelayed(BLOOD_PRESS_CONNECT_TIMEOUT, BLOOD_PRESS_CONNECT_TIMEOUT_TIME);
                transitionTo(mPendingState);
                break;
            case Constant.BLOOD_PRESS_COMMAND_MEASURE:
                OemLog.log(TAG, "DisconnectState can not measure");
                break;
            case Constant.BLOOD_PRESS_COMMAND_DISCONNECT:
                OemLog.log(TAG, "current state is still disconnect state");
                break;
            case Constant.BLOOD_PRESS_CONNECT_COMMAND_SUCCESS:

                transitionTo(mConnectedState);
                if (mBloodPressCommandCallBack != null) {
                    mBloodPressCommandCallBack.onCommandResult(mCommandResult);
                }
                break;
            case Constant.BLOOD_PRESS_CONNECT_COMMAND_FAILTRUE:
                transitionTo(mDisconnectState);
                mBloodPressDevices.disconnect();
            case Constant.BLOOD_PRESS_GATT_DISCONNECT:
                transitionTo(mDisconnectState);
                if (mBloodPressCommandCallBack != null) {
                    mBloodPressCommandCallBack.onGattDisconnect();
                }
                break;
            case BLOOD_PRESS_CONNECT_TIMEOUT:
                OemLog.log(TAG, "notify bloodpress connect timeout...");
                mBloodPressDevices.disconnectGatt();
                MyApplication.getInstance().setIsDevicesMeasuring(false);
                mBloodPressDevices.notifyServicesConnectFailture();
                break;
            default:
                OemLog.log(TAG, "unrecognize message...");
                break;
            }
            return true;
        }
    }
    
    private class ConnectedState extends State {
        @Override
        public void enter() {
            // TODO Auto-generated method stub
            OemLog.log(TAG, "enter ConnectState...");
        }
        
        @Override
        public boolean processMessage(Message msg) {
            // TODO Auto-generated method stub
            OemLog.log(TAG, "current state is ConnectedState msg is " + msg.what);
            switch (msg.what) {
            case Constant.BLOOD_PRESS_COMMAND_CONNECT:
                OemLog.log(TAG, "current state is still ConnectState");
                break;
            case Constant.BLOOD_PRESS_COMMAND_MEASURE:
                OemLog.log(TAG, "send measure delay message...");
                sendMessageDelayed(BLOOD_PRESS_MEASURE_DEALY_MESSAGE, BLOOD_PRESS_MEASURE_DEALY_MESSAGE_TIME);
                break;
            case Constant.BLOOD_PRESS_COMMAND_DISCONNECT:
                OemLog.log(TAG, "measure state can not dissconnect");
                break;
            case Constant.BLOOD_PRESS_MEASURRE_COMMAND_SUCCESS:
                transitionTo(mMeasureingState);
                break;
            case Constant.BLOOD_PRESS_MEASURRE_COMMAND_FAILTURE:
                transitionTo(mConnectedState);
                break;
            case Constant.BLOOD_PRESS_GATT_DISCONNECT:
                removeMessages(Constant.BLOOD_PRESS_COMMAND_MEASURE);
                removeMessages(Constant.BLOOD_PRESS_DISCOVER_SERVICES_COMEPLETE);
                transitionTo(mDisconnectState);
                if (mBloodPressCommandCallBack != null) {
                    mBloodPressCommandCallBack.onGattDisconnect();
                }
                break;
            case Constant.BLOOD_PRESS_DISCOVER_SERVICES_COMEPLETE:
                sendMessage(Constant.BLOOD_PRESS_COMMAND_MEASURE);
                break;
            case BLOOD_PRESS_MEASURE_DEALY_MESSAGE:
                OemLog.log(TAG, "receive message measure delay and measure...");
                mBloodPressDevices.measure();
                break;
            default:
                OemLog.log(TAG, "unrecognize message...");
                break;
            }
            
            return true;
        }
    }

    private class PendingState extends State {
        @Override
        public void enter() {
            // TODO Auto-generated method stub
            OemLog.log(TAG, "enter PendingState..");
        }

        @Override
        public boolean processMessage(Message msg) {
            // TODO Auto-generated method stub
            OemLog.log(TAG, "current state is PendingState msg is " + msg.what);
            switch (msg.what) {
                case Constant.BLOOD_PRESS_COMMAND_CONNECT:
                    break;
                case Constant.BLOOD_PRESS_COMMAND_MEASURE:
                    break;
                case Constant.BLOOD_PRESS_COMMAND_DISCONNECT:
                    transitionTo(mDisconnectState);
                    break;
                case Constant.BLOOD_PRESS_CONNECT_COMMAND_SUCCESS:
                    OemLog.log(TAG, "remove connect timeout message...");
                    removeMessages(BLOOD_PRESS_CONNECT_TIMEOUT);
                    transitionTo(mConnectedState);
                    break;
                case Constant.BLOOD_PRESS_CONNECT_COMMAND_FAILTRUE:
                    break;
                case Constant.BLOOD_PRESS_GATT_DISCONNECT:
                    transitionTo(mDisconnectState);
                    break;
                case BLOOD_PRESS_CONNECT_TIMEOUT:
                    //8秒如果连接不成功则通知设备可以进行下一次连接
                    OemLog.log(TAG, "notify bloodpress connect timeout...");
                    transitionTo(mDisconnectState);
                    mBloodPressDevices.notifyServicesConnectFailture();
                    break;
                default:
                    OemLog.log(TAG, "unrecognize message...");
                    break;
            }

            return true;
        }
    }
    
    private class MeasureingState extends State {
        @Override
        public void enter() {
            OemLog.log(TAG, "enter MeasureingState...");
        }
        
        @Override
        public boolean processMessage(Message msg) {
            // TODO Auto-generated method stub
            OemLog.log(TAG, "current state is MeasureingState msg is " + msg.what);
            switch (msg.what) {
            case Constant.BLOOD_PRESS_COMMAND_CONNECT:
                break;
            case Constant.BLOOD_PRESS_COMMAND_MEASURE:
                break;
            case Constant.BLOOD_PRESS_COMMAND_DISCONNECT:
                break;
            case Constant.BLOOD_PRESS_MEASURE_COMPLETED:
//                mBloodPressDevices.disconnect();

                if (mBloodPressCommandCallBack != null) {
                    mBloodPressCommandCallBack.onCommandResult(mCommandResult);
                }
                break;
             case Constant.BLOOD_PRESS_MEASURE_RESULT_EXCEPTION:
                 mBloodPressDevices.disconnect();
                break;
            case Constant.BLOOD_PRESS_GATT_DISCONNECT:
                transitionTo(mDisconnectState);
                break;
            default:
                OemLog.log(TAG, "unrecognize message...");
                break;
            }
            
            return true;
        }
    }

}


