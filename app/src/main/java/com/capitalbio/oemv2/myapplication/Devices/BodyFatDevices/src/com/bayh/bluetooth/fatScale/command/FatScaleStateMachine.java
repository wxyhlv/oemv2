package com.capitalbio.oemv2.myapplication.Devices.BodyFatDevices.src.com.bayh.bluetooth.fatScale.command;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.Devices.BodyFatDevices.src.com.bayh.bluetooth.StateMachine.State;
import com.capitalbio.oemv2.myapplication.Devices.BodyFatDevices.src.com.bayh.bluetooth.StateMachine.StateMachine;
import com.capitalbio.oemv2.myapplication.Devices.BodyFatDevices.src.com.bayh.bluetooth.fatScale.callback.CommandListener;
import com.capitalbio.oemv2.myapplication.Devices.BodyFatDevices.src.com.bayh.bluetooth.util.Constant;
import com.capitalbio.oemv2.myapplication.Devices.BodyFatDevices.src.com.bayh.sdk.ble.bean.BleConfigBean;
import com.capitalbio.oemv2.myapplication.Devices.BodyFatDevices.src.com.bayh.sdk.ble.utils.HexUtils;
import com.capitalbio.oemv2.myapplication.Devices.BodyFatDevices.src.com.xtremeprog.sdk.ble.BleGattCharacteristic;
import com.capitalbio.oemv2.myapplication.Devices.BodyFatDevices.src.com.xtremeprog.sdk.ble.BleGattService;
import com.capitalbio.oemv2.myapplication.Devices.BodyFatDevices.src.com.xtremeprog.sdk.ble.BodyFatDevices;
import com.capitalbio.oemv2.myapplication.Devices.BodyFatDevices.src.com.xtremeprog.sdk.ble.IBleExpand;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;
import com.capitalbio.oemv2.myapplication.Utils.PreferencesUtils;


public class FatScaleStateMachine extends StateMachine {

    public static final String TAG = "FatScaleStateMachine";

    public static final int BODY_FAT_DISCOVER_SERVICES_SUCCESSFUL = 100;

    private static FatScaleStateMachine mInstance;
    
    private Context mContext = MyApplication.getInstance();
    private CommandListener mFatScaleCommandCallBack;//接收上层传递多来的回调
    private DisConnectState mDisconnectState = new DisConnectState();
    private ConnectedState mConnectedState = new ConnectedState();
    private MeasureingState mMeasureingState = new MeasureingState();
    private BodyFatDevices mDevices = BodyFatDevices.getInstance();
    private CommandResult mCommandResult;
    
    private int currentState;
    private BleGattCharacteristic readCharacteristic;

    private String connectAddress;
    /**
     * Ble接口实现实例
     */
	public IBleExpand mBle;
	
	
	private Handler delayHandler = new Handler() {
	    public void handleMessage(Message msg) {

            switch (msg.what) {
                case BODY_FAT_DISCOVER_SERVICES_SUCCESSFUL:
                    OemLog.log("AndroidBle", "receive enable bodyfat notification...");
                    mBle.requestCharacteristicNotification(connectAddress, readCharacteristic);
                    break;
                default:
                    break;
            }

        };
	};
	
    
    private CommandListener mFatScaleCommandListener = new CommandListener() {
        
        @Override
        public void onCommandResult(CommandResult commandResult) {
            Log.d(TAG, "command excute result callback command type is " + commandResult.getFatScaleCommandType() + " command result is " + commandResult.getmFatScalecommandResult()
                     + " result is " + commandResult.getHex());
            mCommandResult = commandResult;
            sendMessage(commandResult.getmFatScalecommandResult());
        }
    };
    BleConfigBean configBean=new BleConfigBean(BleConfigBean.TZC_DEVICE_NAME, BleConfigBean.TZC_DEVICE_TYPE,
			BleConfigBean.TZC_UUID, BleConfigBean.TZC_READ_UUID, BleConfigBean.TZC_WRITE_UUID);
    private FatScaleStateMachine() {
        super("BloodPressStateMachine");
        addState(mDisconnectState);
        addState(mConnectedState);
        addState(mMeasureingState);
        
        //设置初始状态
        setInitialState(mDisconnectState);

        //获得相应设备的接口对象
        mBle = (IBleExpand) mDevices.getBle();
        mDevices.registerCallBack(mFatScaleCommandListener);
    }
    /**
     * �����״̬��
     * @param context
     * @param bloodPressCommandCallBack
     * @return
     */
    public static FatScaleStateMachine getInstance() {
        Log.d(TAG, "FatScaleStateMachine make.....");
        
        if (mInstance == null) {
            mInstance = new FatScaleStateMachine();
            //开启状态机
            mInstance.start();
        }
        
        return mInstance;
    }
    
    private class DisConnectState extends State {
        @Override
        public void enter() {
            // TODO Auto-generated method stub
            Log.d(TAG, "enter DisConnectState..");
        }
        
        @Override
        public boolean processMessage(Message msg) {
            // TODO Auto-generated method stub
            Log.d(TAG, "current state is DisConnectState msg is " + msg.what + ", msg obj is " + msg.obj);
            switch (msg.what) {
            case Constant.FATSCALE_PRESS_COMMAND_CONNECT:
                connectAddress = (String) msg.obj;
                OemLog.log(TAG, "recevice connect bodyfat address is " + connectAddress);
                //请求连接gatt和服务
                sendMessageDelayed(Constant.BODYFAT_CONNECT_TIMEOUT_MESSAGE, Constant.BODYFAT_CONNECT_TIMEOUT_MESSAGE_DELAY_TIME);
                mBle.requestConnect(connectAddress);
           
                break;
            case Constant.FATSCALE_PRESS_COMMAND_SET:
                //设置信息命令
                Log.d(TAG, "DisconnectState can not measure");
                break;
            case Constant.FATSCALE_PRESS_COMMAND_DISCONNECT:
                //设备断开
                Log.d(TAG, "current state is still disconnect state");
                break;
            case Constant.FATSCALE_PRESS_CONNECT_COMMAND_SUCCESS:
                Toast.makeText(MyApplication.getInstance(), "连接成功,开始测量!", Toast.LENGTH_SHORT);
                //连接成功
                removeMessages(Constant.BODYFAT_CONNECT_TIMEOUT_MESSAGE);
               transitionTo(mConnectedState);
                if (mFatScaleCommandCallBack != null) {
                    mFatScaleCommandCallBack.onCommandResult(mCommandResult);
                }
               BleGattService service = mBle.getService(connectAddress, configBean.getServiceUUID());
                OemLog.log(TAG, "getservices is " + service + ", address is " + connectAddress);
                if (service != null && connectAddress != null) {
                    readCharacteristic = service.getCharacteristic(configBean.getReadUUID());
                    // 02 20 DD 07 FD 53 01 00 40 95 AF 2F
                    // 6e 01 02 01 8f
                    /*delayHandler.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub

                        }
                    }, 800l);*/

                    delayHandler.sendEmptyMessageDelayed(BODY_FAT_DISCOVER_SERVICES_SUCCESSFUL, 50l);

                    PreferencesUtils.putBoolean(mContext, "bodyfat_connect_state", true);
                }
                break;
            case Constant.FATSCALE_PRESS_CONNECT_COMMAND_FAILTRUE:
                transitionTo(mDisconnectState);
                break;
            case Constant.BODYFAT_CONNECT_TIMEOUT_MESSAGE:
                OemLog.log(TAG, "nofify bodyfat gatt connect timeout...");
                mDevices.notifyConncetTimeout();
                MyApplication.getInstance().setIsDevicesMeasuring(false);
                break;
            default:
                Log.d(TAG, "unrecognize message...");
                break;
            }
            return true;
        }
    }
    
    private class ConnectedState extends State {
        @Override
        public void enter() {
            // TODO Auto-generated method stub
            Log.d(TAG, "enter ConnectState...");
        }
        
        @Override
        public boolean processMessage(Message msg) {
            // TODO Auto-generated method stub
            Log.d(TAG, "current state is ConnectedState msg is " + msg.what);
            switch (msg.what) {
            case Constant.FATSCALE_PRESS_COMMAND_CONNECT:
                Log.d(TAG, "current state is still ConnectState");
                break;
            case Constant.FATSCALE_PRESS_COMMAND_SET:
                //设置信息命令
        		final byte[] writeData=(byte[]) msg.obj;
            	wirte(writeData);
                break;
            case Constant.FATSCALE_PRESS_COMMAND_DISCONNECT:
            	
                Log.d(TAG, "measure state can not dissconnect");
                break;
            case Constant.WRITE_SUCESS:
                if (mFatScaleCommandCallBack != null) {
                    mFatScaleCommandCallBack.onCommandResult(mCommandResult);
                }
                transitionTo(mMeasureingState);
                read();
                break;
            case Constant.FATSCALE_PRESS_MEASURE_COMMAND_FAILTURE: 
            	break;
            case Constant.WRITE_FAILURE:
            case Constant.READ_FAILURE:
            	 Log.d(TAG, "info set is wrong or ...");
                break;
            case Constant.FATSCALE_PRESS_CONNECT_COMMAND_FAILTRUE:
            	Log.i(TAG, "device disconnecet ...");
            	transitionTo(mDisconnectState);
                if (mFatScaleCommandCallBack != null) {
                    mFatScaleCommandCallBack.onCommandResult(mCommandResult);
                }
            	break;
              
                
            default:
                Log.d(TAG, "unrecognize message...");
                break;
            }
            
            return true;
        }
    }
    
    
    private class MeasureingState extends State {
        @Override
        public void enter() {
            // TODO Auto-generated method stub
            Log.d(TAG, "enter MeasureingState...");
        }
        
        @Override
        public boolean processMessage(Message msg) {
            // TODO Auto-generated method stub
            Log.d(TAG, "current state is MeasureingState msg is " + msg.what);
            switch (msg.what) {
            case Constant.FATSCALE_PRESS_COMMAND_CONNECT:
                break;
            case Constant.FATSCALE_PRESS_COMMAND_SET:
            	
                break;
            case Constant.FATSCALE_PRESS_COMMAND_DISCONNECT:
                OemLog.log(TAG, "current is measuring state and disconnect the bodyfat...");
                //断开命令
            	wirte(HexUtils.hexStr2Bytes("FD30"));
                break;
           /* case Constant.FATSCALE_PRESS_MEASURE_COMMAND_FAILTURE: 
            	  transitionTo(mDisconnectState);
            	break; */
            case Constant.FATSCALE_PRESS_CONNECT_COMMAND_FAILTRUE: 
                transitionTo(mDisconnectState);
            	Log.i(TAG, "device disconnecet ...");
                if (mFatScaleCommandCallBack != null) {
                    mFatScaleCommandCallBack.onCommandResult(mCommandResult);
                }
            	transitionTo(mDisconnectState);
            	break; 
            case Constant.WRITE_FAILURE:
            case Constant.READ_FAILURE:
            	 Log.d(TAG, "info set is wrong or ...");
                break;
	
            case Constant.READ_SUCESS:
                //测量成功断开
                if (mFatScaleCommandCallBack != null) {
                    mFatScaleCommandCallBack.onCommandResult(mCommandResult);
                }
                break;
            case Constant.BODYFAT_SHUTDOWN_COMMAND_MESSAGE:
                break;
            default:
                Log.d(TAG, "unrecognize message...");
                break;
            }
            
            return true;
        }
    }
    
    
    /**
	 * ���豸д�����
	 * 
	 * @param bytes
	 */
	public synchronized void wirte(byte[] writeData) {
		   // TODO Auto-generated method stub
        Log.d(TAG, "enterwrite()...");
		
		if (null == mBle) {
			return;
		}
		BleGattService service = mBle.getService(connectAddress,
				configBean.getServiceUUID());
		if (service == null)
			return;
		BleGattCharacteristic characteristic = service
				.getCharacteristic(configBean.getWriteUUID());
		if (characteristic == null)
			return;
		characteristic.setValue(writeData);
		// 02 20 DD 07 FD 53 01 00 40 95 AF 2F
		// 6e 01 02 01 8f

		mBle.requestWriteCharacteristic(connectAddress, characteristic, "");
	}

	/**
	 * ���豸��ȡ���
	 */
	public synchronized void read() {
		 Log.d(TAG, "enterread()...");
		if (null == mBle) {
			return;
		}
		BleGattService service = mBle.getService(Constant.FATSCALE_DEVICE_ADDRESS,
				configBean.getServiceUUID());
		if (service == null)
			return;
		readCharacteristic = service
				.getCharacteristic(configBean.getReadUUID());
		if (readCharacteristic == null)
			return;
		// 02 20 DD 07 FD 53 01 00 40 95 AF 2F
		// 6e 01 02 01 8f
//		mBle.requestCharacteristicNotification(Constant.FATSCALE_DEVICE_ADDRESS, readCharacteristic);
		
		/*delayHandler.postDelayed(new Runnable() {
            
            @Override
            public void run() {
                // TODO Auto-generated method stub
                mBle.requestReadCharacteristic(Constant.FATSCALE_DEVICE_ADDRESS, readCharacteristic);
            }
        }, 2000l);*/
	}
	
	
}


