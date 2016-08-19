/**
 * This XPG software is supplied to you by Xtreme Programming Group, Inc.
 * ("XPG") in consideration of your agreement to the following terms, and your
 * use, installation, modification or redistribution of this XPG software
 * constitutes acceptance of these terms.锟�If you do not agree with these terms,
 * please do not use, install, modify or redistribute this XPG software.
 * 
 * In consideration of your agreement to abide by the following terms, and
 * subject to these terms, XPG grants you a non-exclusive license, under XPG's
 * copyrights in this original XPG software (the "XPG Software"), to use and
 * redistribute the XPG Software, in source and/or binary forms; provided that
 * if you redistribute the XPG Software, with or without modifications, you must
 * retain this notice and the following text and disclaimers in all such
 * redistributions of the XPG Software. Neither the name, trademarks, service
 * marks or logos of XPG Inc. may be used to endorse or promote products derived
 * from the XPG Software without specific prior written permission from XPG.锟� * Except as expressly stated in this notice, no other rights or licenses,
 * express or implied, are granted by XPG herein, including but not limited to
 * any patent rights that may be infringed by your derivative works or by other
 * works in which the XPG Software may be incorporated.
 * 
 * The XPG Software is provided by XPG on an "AS IS" basis.锟�XPG MAKES NO
 * WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE IMPLIED
 * WARRANTIES OF NON-INFRINGEMENT, MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE, REGARDING THE XPG SOFTWARE OR ITS USE AND OPERATION ALONE OR IN
 * COMBINATION WITH YOUR PRODUCTS.
 * 
 * IN NO EVENT SHALL XPG BE LIABLE FOR ANY SPECIAL, INDIRECT, INCIDENTAL OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) ARISING IN ANY WAY OUT OF THE USE, REPRODUCTION, MODIFICATION
 * AND/OR DISTRIBUTION OF THE XPG SOFTWARE, HOWEVER CAUSED AND WHETHER UNDER
 * THEORY OF CONTRACT, TORT (INCLUDING NEGLIGENCE), STRICT LIABILITY OR
 * OTHERWISE, EVEN IF XPG HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * ABOUT XPG: Established since June 2005, Xtreme Programming Group, Inc. (XPG)
 * is a digital solutions company based in the United States and China. XPG
 * integrates cutting-edge hardware designs, mobile applications, and cloud
 * computing technologies to bring innovative products to the marketplace. XPG's
 * partners and customers include global leading corporations in semiconductor,
 * home appliances, health/wellness electronics, toys and games, and automotive
 * industries. Visit www.xtremeprog.com for more information.
 * 
 * Copyright (C) 2013 Xtreme Programming Group, Inc. All Rights Reserved.
 */

package com.capitalbio.oemv2.myapplication.Devices.BodyFatDevices.src.com.xtremeprog.sdk.ble;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.capitalbio.oemv2.myapplication.Base.Const;
import com.capitalbio.oemv2.myapplication.Base.IBodyFatDevicesCallBack;
import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.Devices.BodyFatDevices.src.com.bayh.bluetooth.fatScale.callback.CommandListener;
import com.capitalbio.oemv2.myapplication.Devices.BodyFatDevices.src.com.bayh.bluetooth.fatScale.command.CommandResult;
import com.capitalbio.oemv2.myapplication.Devices.BodyFatDevices.src.com.bayh.bluetooth.fatScale.command.FatScaleSetCommand;
import com.capitalbio.oemv2.myapplication.Devices.BodyFatDevices.src.com.bayh.bluetooth.util.Constant;
import com.capitalbio.oemv2.myapplication.Devices.BodyFatDevices.src.com.bayh.sdk.ble.AnalyzeDataUtils;
import com.capitalbio.oemv2.myapplication.Devices.BodyFatDevices.src.com.bayh.sdk.ble.bean.BodyFatSaid;
import com.capitalbio.oemv2.myapplication.Devices.BodyFatDevices.src.com.bayh.sdk.ble.utils.HexUtils;
import com.capitalbio.oemv2.myapplication.Devices.Bracelete.OemBackgroundService;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;
import com.capitalbio.oemv2.myapplication.Utils.PreferencesUtils;
import com.capitalbio.oemv2.myapplication.Utils.TimeStampUtil;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;


public class BodyFatDevices{
	private static final String TAG = "BodyFatDevices";
	
	private Context mContext = MyApplication.getInstance();

	private DbManager dbManager = MyApplication.getDB();

	//�������
	private static BodyFatDevices mInstance;
	private Handler servecieCallBackHandler;

	private String currentAddress;

	private boolean isDataTransfer = false;

	private BodyFatPersonInfo personInfo = new BodyFatPersonInfo();

	private IBodyFatDevicesCallBack bodyFatDevicesCallBack;
	/** Intent for broadcast */
	public static final String BLE_NOT_SUPPORTED = "com.xtremeprog.sdk.ble.not_supported";
	public static final String BLE_NO_BT_ADAPTER = "com.xtremeprog.sdk.ble.no_bt_adapter";
	public static final String BLE_STATUS_ABNORMAL = "com.xtremeprog.sdk.ble.status_abnormal";

	public static final int REMOVE_MULTI_BODYFAT_DATA_MESSAGE = 1000;
	public static final int REMOVE_MULTI_BODYFAT_DATA_MESSAGE_DELAY_TIME = 1000 * 8;



	/**
	 * @see
	 */
	public static final String BLE_REQUEST_FAILED = "com.xtremeprog.sdk.ble.request_failed";
	/**
	 * @see
	 */
	public static final String BLE_DEVICE_FOUND = "com.xtremeprog.sdk.ble.device_found";
	/**
	 * @see
	 */
	public static final String BLE_GATT_CONNECTED = "com.xtremeprog.sdk.ble.gatt_connected";
	/**
	 * @see
	 */
	public static final String BLE_GATT_DISCONNECTED = "com.xtremeprog.sdk.ble.gatt_disconnected";
	/**
	 * @see
	 */
	public static final String BLE_SERVICE_DISCOVERED = "com.xtremeprog.sdk.ble.service_discovered";
	/**
	 * @see
	 */
	public static final String BLE_CHARACTERISTIC_READ = "com.xtremeprog.sdk.ble.characteristic_read";
	/**
	 * @see
	 */
	public static final String BLE_CHARACTERISTIC_NOTIFICATION = "com.xtremeprog.sdk.ble.characteristic_notification";
	/**
	 * @see
	 */
	public static final String BLE_CHARACTERISTIC_INDICATION = "com.xtremeprog.sdk.ble.characteristic_indication";
	/**
	 * @see
	 */
	public static final String BLE_CHARACTERISTIC_WRITE = "com.xtremeprog.sdk.ble.characteristic_write";
	/**
	 * @see
	 */
	public static final String BLE_CHARACTERISTIC_CHANGED = "com.xtremeprog.sdk.ble.characteristic_changed";

	/** Intent extras */
	public static final String EXTRA_DEVICE = "DEVICE";
	public static final String EXTRA_RSSI = "RSSI";
	public static final String EXTRA_SCAN_RECORD = "SCAN_RECORD";
	public static final String EXTRA_SOURCE = "SOURCE";
	public static final String EXTRA_ADDR = "ADDRESS";
	public static final String EXTRA_CONNECTED = "CONNECTED";
	public static final String EXTRA_STATUS = "STATUS";
	public static final String EXTRA_UUID = "UUID";
	public static final String EXTRA_VALUE = "VALUE";
	public static final String EXTRA_REQUEST = "REQUEST";
	public static final String EXTRA_REASON = "REASON";

	/** Source of device entries in the device list */
	public static final int DEVICE_SOURCE_SCAN = 0;
	public static final int DEVICE_SOURCE_BONDED = 1;
	public static final int DEVICE_SOURCE_CONNECTED = 2;

	public static final UUID DESC_CCC = UUID
			.fromString("00002902-0000-1000-8000-00805f9b34fb");

	public enum BLESDK {
		NOT_SUPPORTED, ANDROID, SAMSUNG, BROADCOM
	}
	
	private BLESDK mBleSDK;
	private IBle mBle;
	private Queue<BleRequest> mRequestQueue = new LinkedList<BleRequest>();
	private BleRequest mCurrentRequest = null;
	private static final int REQUEST_TIMEOUT = 10 * 100; // total timeout =
														// REQUEST_TIMEOUT *
														// 100ms
	private boolean mCheckTimeout = false;
	private int mElapsed = 0;
	private Thread mRequestTimeout;
	private String mNotificationAddress;

	//体脂称测量结果
	private BodyFatSaid bodyFatResult;

	private BodyFatSaid previousResult;

	private CommandListener commandlistener;

	private Handler removeMutiHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			switch (msg.what) {
				case REMOVE_MULTI_BODYFAT_DATA_MESSAGE:
					OemLog.log(TAG, "receive the delay message and reset the data tranfer state...");
					isDataTransfer = false;
					break;
				default:
					break;
			}

		}
	};
	
	public static BodyFatDevices getInstance() {
	    if (mInstance == null) {
            mInstance = new BodyFatDevices();
        }
	    
	    return mInstance;
	}
	
	
	private Runnable mTimeoutRunnable = new Runnable() {
		@Override
		public void run() {
			Log.d(TAG, "monitoring thread start");
			mElapsed = 0;
			try {
				while (mCheckTimeout) {
					// Log.d(TAG, "monitoring timeout seconds: " + mElapsed);
					Thread.sleep(100);
					mElapsed++;

					if (mElapsed > REQUEST_TIMEOUT && mCurrentRequest != null) {
						Log.d(TAG, "-processrequest type "
								+ mCurrentRequest.type + " address "
								+ mCurrentRequest.address + " [timeout]");
						bleRequestFailed(mCurrentRequest.address,
								mCurrentRequest.type, BleRequest.FailReason.TIMEOUT);
						bleStatusAbnormal("-processrequest type "
								+ mCurrentRequest.type + " address "
								+ mCurrentRequest.address + " [timeout]");
						if (mBle != null) {
							mBle.disconnect(mCurrentRequest.address);
						}
						new Thread(new Runnable() {
							@Override
							public void run() {
								mCurrentRequest = null;
								processNextRequest();
							}
						}, "th-ble").start();
						break;
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				Log.d(TAG, "monitoring thread exception");
			}
			Log.d(TAG, "monitoring thread stop");
		}
	};

	
	/*广播改为回调
	 * 
	 * public static IntentFilter getIntentFilter() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(BLE_NOT_SUPPORTED);
		intentFilter.addAction(BLE_NO_BT_ADAPTER);
		intentFilter.addAction(BLE_STATUS_ABNORMAL);
		intentFilter.addAction(BLE_REQUEST_FAILED);
		intentFilter.addAction(BLE_DEVICE_FOUND);
		intentFilter.addAction(BLE_GATT_CONNECTED);
		intentFilter.addAction(BLE_GATT_DISCONNECTED);
		intentFilter.addAction(BLE_SERVICE_DISCOVERED);
		intentFilter.addAction(BLE_CHARACTERISTIC_READ);
		intentFilter.addAction(BLE_CHARACTERISTIC_NOTIFICATION);
		intentFilter.addAction(BLE_CHARACTERISTIC_WRITE);
		intentFilter.addAction(BLE_CHARACTERISTIC_CHANGED);
		return intentFilter;
	}*/
	public void registerCallBack(CommandListener commandListener){
		commandlistener = commandListener;
		
	}

	
	private BodyFatDevices() {
		mBleSDK = getBleSDK();
		if (mBleSDK == BLESDK.NOT_SUPPORTED) {
			return;
		}

		Log.d(TAG, " " + mBleSDK);
		if (mBleSDK == BLESDK.BROADCOM) {
			mBle = new BroadcomBle(this);
		} else if (mBleSDK == BLESDK.ANDROID) {
			mBle = new AndroidBle(this);
		} else if (mBleSDK == BLESDK.SAMSUNG) {
			mBle = new SamsungBle(this);
		}
	}

	protected void bleNotSupported() {
		Intent intent = new Intent(BodyFatDevices.BLE_NOT_SUPPORTED);
		mContext.sendBroadcast(intent);
	}

	protected void bleNoBtAdapter() {
		Intent intent = new Intent(BodyFatDevices.BLE_NO_BT_ADAPTER);
		mContext.sendBroadcast(intent);
	}

	private BLESDK getBleSDK() {
		if (mContext.getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_BLUETOOTH_LE)) {
			// android 4.3
			return BLESDK.ANDROID;
		}

		ArrayList<String> libraries = new ArrayList<String>();
		for (String i : mContext.getPackageManager().getSystemSharedLibraryNames()) {
			libraries.add(i);
		}

		if (android.os.Build.VERSION.SDK_INT >= 17) {
			// android 4.2.2
			if (libraries.contains("com.samsung.android.sdk.bt")) {
				return BLESDK.SAMSUNG;
			} else if (libraries.contains("com.broadcom.bt")) {
				return BLESDK.BROADCOM;
			}
		}

		bleNotSupported();
		return BLESDK.NOT_SUPPORTED;
	}

	public IBle getBle() {
		return mBle;
	}

	/**
	 * Send {@link } broadcast. <br>
	 * <br>
	 * Data in the broadcast intent: <br>
	 * {@link } device {@link BluetoothDevice} <br>
	 * {@link } rssi int<br>
	 * {@link } scan record byte[] <br>
	 * {@link } source int, not used now <br>
	 */
	protected void bleDeviceFound(BluetoothDevice device, int rssi,
			byte[] scanRecord, int source) {
		Log.d("blelib", "[" + new Date().toLocaleString() + "] device found "
				+ device.getAddress());
		Intent intent = new Intent(BodyFatDevices.BLE_DEVICE_FOUND);
		intent.putExtra(BodyFatDevices.EXTRA_DEVICE, device);
		intent.putExtra(BodyFatDevices.EXTRA_RSSI, rssi);
		intent.putExtra(BodyFatDevices.EXTRA_SCAN_RECORD, scanRecord);
		intent.putExtra(BodyFatDevices.EXTRA_SOURCE, source);
		mContext.sendBroadcast(intent);
	}

	/**
	 * Send {@link } broadcast. <br>
	 * <br>
	 * Data in the broadcast intent: <br>
	 * {@link } device {@link BluetoothDevice} <br>
	 */
	protected void bleGattConnected(BluetoothDevice device) {
		Intent intent = new Intent(BLE_GATT_CONNECTED);
		intent.putExtra(EXTRA_DEVICE, device);
		intent.putExtra(EXTRA_ADDR, device.getAddress());
		mContext.sendBroadcast(intent);
		requestProcessed(device.getAddress(), BleRequest.RequestType.CONNECT_GATT, true);
	}

	/**
	 * Send {@link } broadcast. <br>
	 * <br>
	 * Data in the broadcast intent: <br>
	 * {@link } device address {@link String} <br>
	 * 
	 * @param address
	 */
	protected void bleGattDisConnected(String address) {
		CommandResult result = new CommandResult();
		result.setmFatScalecommandResult(Constant.FATSCALE_PRESS_CONNECT_COMMAND_FAILTRUE);
		//����ɹ��ص�״̬������ӳɹ��µĽӿ�
		if (commandlistener != null) {
			commandlistener.onCommandResult(result);
		}
		Intent intent = new Intent(BLE_GATT_DISCONNECTED);
		intent.putExtra(EXTRA_ADDR, address);
		mContext.sendBroadcast(intent);
		requestProcessed(address, BleRequest.RequestType.CONNECT_GATT, false);
		if (servecieCallBackHandler.hasMessages(Const.BODYFAT_GATT_DISCONNECT_MESSAGE)) {
			servecieCallBackHandler.removeMessages(Const.BODYFAT_GATT_DISCONNECT_MESSAGE);
		}
		servecieCallBackHandler.sendEmptyMessageDelayed(Const.BODYFAT_GATT_DISCONNECT_MESSAGE, 10 * 1000l);

		mRequestQueue.clear();
		mCurrentRequest = null;

	}

	/**
	 * Send {@link } broadcast. <br>
	 * <br>
	 * Data in the broadcast intent: <br>
	 * {@link} device address {@link String} <br>
	 * 
	 * @param address
	 */
	protected void bleServiceDiscovered(String address) {
		Intent intent = new Intent(BLE_SERVICE_DISCOVERED);
		intent.putExtra(EXTRA_ADDR, address);
		mContext.sendBroadcast(intent);
		CommandResult result = new CommandResult();
		result.setFatScaleCommandType(Constant.FATSCALE_PRESS_COMMAND_CONNECT);
		result.setmFatScalecommandResult(Constant.FATSCALE_PRESS_CONNECT_COMMAND_SUCCESS);

		//����ɹ��ص�״̬������ӳɹ��µĽӿ�
		if (commandlistener != null) {
			commandlistener.onCommandResult(result);
		}
		OemLog.log(TAG, "bodyFatDevicesCallBack is " + bodyFatDevicesCallBack);
		if (bodyFatDevicesCallBack != null) {
			bodyFatDevicesCallBack.devicesMeasureStartCallBack();
		}
		requestProcessed(address, BleRequest.RequestType.DISCOVER_SERVICE, true);
	}

	protected void requestProcessed(String address, BleRequest.RequestType requestType,
			boolean success) {
		if (mCurrentRequest != null && mCurrentRequest.type == requestType) {
			if (mRequestTimeout != null) {

				clearTimeoutThread();
			}
			Log.d(TAG, "-processrequest type " + requestType + " address "
					+ address + " [success: " + success + "]");
			if (!success) {

				CommandResult result = new CommandResult();
				switch (mCurrentRequest.type) {
				case DISCOVER_SERVICE:
					result.setmFatScalecommandResult(Constant.FATSCALE_PRESS_CONNECT_COMMAND_FAILTRUE);
					break;
				case WRITE_CHARACTERISTIC:
					result.setFatScaleCommandType(Constant.WRITE_FAILURE);
					break;
				case READ_CHARACTERISTIC:
					result.setFatScaleCommandType(Constant.READ_FAILURE);
					break;

				default:
					break;
				}
				if (commandlistener != null) {
					commandlistener.onCommandResult(result);
				}
				//执行不成功放弃下一个请求
				return;

			}
			new Thread(new Runnable() {
				@Override
				public void run() {
					mCurrentRequest = null;
					processNextRequest();
				}
			}, "th-ble").start();
		}
	}

	private void clearTimeoutThread() {
		if (mRequestTimeout.isAlive()) {
			try {
				mCheckTimeout = false;
				mRequestTimeout.join();
				mRequestTimeout = null;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Send {@link } broadcast. <br>
	 * <br>
	 * Data in the broadcast intent: <br>
	 * {@link } device address {@link String} <br>
	 * {@link } characteristic uuid {@link String}<br>
	 * {@link} read status {@link Integer} Not used now <br>
	 * {@link } data byte[] <br>
	 * 
	 * @param address
	 * @param uuid
	 * @param status
	 * @param value
	 */
	protected void bleCharacteristicRead(String address, String uuid,
			int status, byte[] value) {
		Intent intent = new Intent(BLE_CHARACTERISTIC_READ);
		intent.putExtra(EXTRA_ADDR, address);
		intent.putExtra(EXTRA_UUID, uuid);
		intent.putExtra(EXTRA_STATUS, status);
		intent.putExtra(EXTRA_VALUE, value);
		mContext.sendBroadcast(intent);
		requestProcessed(address, BleRequest.RequestType.READ_CHARACTERISTIC, true);
	}

	protected void addBleRequest(BleRequest request) {
		synchronized (mRequestQueue) {
			mRequestQueue.add(request);
			processNextRequest();
		}
	}

	private void processNextRequest() {
		if (mCurrentRequest != null) {
			OemLog.log(TAG, "mCurrentRequest is not null, type is " + ((mCurrentRequest == null) ? null : mCurrentRequest.type));
			return;
		}

		synchronized (mRequestQueue) {
			if (mRequestQueue.isEmpty()) {
				return;
			}
			mCurrentRequest = mRequestQueue.remove();
		}
		Log.d(TAG, "+processrequest type " + mCurrentRequest.type + " address "
				+ mCurrentRequest.address + " remark " + mCurrentRequest.remark);
		boolean ret = false;
		switch (mCurrentRequest.type) {
		case CONNECT_GATT:
			ret = ((IBleRequestHandler) mBle).connect(mCurrentRequest.address);
			break;
		case DISCOVER_SERVICE:
			ret = mBle.discoverServices(mCurrentRequest.address);
			break;
		case CHARACTERISTIC_NOTIFICATION:
		case CHARACTERISTIC_INDICATION:
		case CHARACTERISTIC_STOP_NOTIFICATION:
			ret = ((IBleRequestHandler) mBle).characteristicNotification(
					mCurrentRequest.address, mCurrentRequest.characteristic);
			break;
		case READ_CHARACTERISTIC:
			ret = ((IBleRequestHandler) mBle).readCharacteristic(
					mCurrentRequest.address, mCurrentRequest.characteristic);
			break;
		case WRITE_CHARACTERISTIC:
			ret = ((IBleRequestHandler) mBle).writeCharacteristic(
					mCurrentRequest.address, mCurrentRequest.characteristic);
			break;
		case READ_DESCRIPTOR:
			break;
		default:
			break;
		}

		if (ret) {
			startTimeoutThread();
		} else {
			if (mCurrentRequest.address == null)return;
			Log.d(TAG, "-processrequest type " + mCurrentRequest.type
					+ " address " + mCurrentRequest.address + " [fail start]");
			bleRequestFailed(mCurrentRequest.address, mCurrentRequest.type,
					BleRequest.FailReason.START_FAILED);
			new Thread(new Runnable() {
				@Override
				public void run() {
					mCurrentRequest = null;
//					processNextRequest();
				}
			}, "th-ble").start();
		}
	}

	private void startTimeoutThread() {
		mCheckTimeout = true;
		mRequestTimeout = new Thread(mTimeoutRunnable);
		mRequestTimeout.start();
	}

	protected BleRequest getCurrentRequest() {
		return mCurrentRequest;
	}

	protected void setCurrentRequest(BleRequest mCurrentRequest) {
		this.mCurrentRequest = mCurrentRequest;
	}

	/**
	 * Send {@link } broadcast. <br>
	 * <br>
	 * Data in the broadcast intent: <br>
	 * {@link } device address {@link String} <br>
	 * {@link } characteristic uuid {@link String}<br>
	 * {@link } read status {@link Integer} Not used now <br>
	 * 
	 * @param address
	 * @param uuid
	 * @param status
	 */
	protected void bleCharacteristicNotification(String address, String uuid,
			boolean isEnabled, int status) {
		Intent intent = new Intent(BLE_CHARACTERISTIC_NOTIFICATION);
		intent.putExtra(EXTRA_ADDR, address);
		intent.putExtra(EXTRA_UUID, uuid);
		intent.putExtra(EXTRA_VALUE, isEnabled);
		intent.putExtra(EXTRA_STATUS, status);
		mContext.sendBroadcast(intent);
		if (isEnabled) {
			requestProcessed(address, BleRequest.RequestType.CHARACTERISTIC_NOTIFICATION,
					true);
			//设置个人信息到体脂称
			if ("男".equals(PreferencesUtils.getString(mContext, "sex", ""))) {
				personInfo.sex = '1';
			} else {
				personInfo.sex = '0';
			}

			personInfo.heitht = Integer.valueOf(PreferencesUtils.getString(mContext, "height", "170"));
			personInfo.age = Integer.valueOf(PreferencesUtils.getString(mContext, "age", "20"));
			OemLog.log(TAG, "height is " + personInfo.heitht + ", age is " + personInfo.age + ", sex is " + personInfo.sex);
			final byte[] writeData= AnalyzeDataUtils.getFatWriteData(personInfo.heitht, personInfo.sex, personInfo.age, "00");
			OemLog.log(TAG, "wirte person data byte is " + Arrays.toString(writeData));
			FatScaleSetCommand fatScaleMeasureCommand = new FatScaleSetCommand(writeData);
			fatScaleMeasureCommand.excuteCommand();
		} else {
			requestProcessed(address, BleRequest.RequestType.CHARACTERISTIC_STOP_NOTIFICATION, true);
		}
		setNotificationAddress(address);
	}

	/**
	 * Send {@link } broadcast. <br>
	 * <br>
	 * Data in the broadcast intent: <br>
	 * {@link } device address {@link String} <br>
	 * {@link} characteristic uuid {@link String}<br>
	 * {@link } read status {@link Integer} Not used now <br>
	 * 
	 * @param address
	 * @param uuid
	 * @param status
	 */
	protected void bleCharacteristicIndication(String address, String uuid,
			int status) {
		Intent intent = new Intent(BLE_CHARACTERISTIC_INDICATION);
		intent.putExtra(EXTRA_ADDR, address);
		intent.putExtra(EXTRA_UUID, uuid);
		intent.putExtra(EXTRA_STATUS, status);
		mContext.sendBroadcast(intent);
		requestProcessed(address, BleRequest.RequestType.CHARACTERISTIC_INDICATION, true);
		setNotificationAddress(address);
	}

	/**
	 * Send {@link} broadcast. <br>
	 * <br>
	 * Data in the broadcast intent: <br>
	 * {@link } device address {@link String} <br>
	 * {@link } characteristic uuid {@link String}<br>
	 * {@link } read status {@link Integer} Not used now <br>
	 * 
	 * @param address
	 * @param uuid
	 * @param status
	 */
	protected void bleCharacteristicWrite(String address, String uuid,
			int status) {
		/*Intent intent = new Intent(BLE_CHARACTERISTIC_WRITE);
		intent.putExtra(EXTRA_ADDR, address);
		intent.putExtra(EXTRA_UUID, uuid);
		intent.putExtra(EXTRA_STATUS, status);
		sendBroadcast(intent);*/
		//������Ϣ�ɹ�
		CommandResult commandResult =new CommandResult();
		commandResult.setFatScaleCommandType(Constant.FATSCALE_PRESS_COMMAND_SET);
		commandResult.setmFatScalecommandResult(Constant.WRITE_SUCESS);
		commandlistener.onCommandResult(commandResult);
		requestProcessed(address, BleRequest.RequestType.WRITE_CHARACTERISTIC, true);
	}

	/**
	 * Send {@link } broadcast. <br>
	 * <br>
	 * Data in the broadcast intent: <br>
	 * {@link} device address {@link String} <br>
	 * {@link } characteristic uuid {@link String}<br>
	 * {@link } data byte[] <br>
	 * 
	 * @param address
	 * @param uuid
	 * @param value
	 */
	protected void bleCharacteristicChanged(String address, String uuid,
			byte[] value) {
		currentAddress = address;
	/*	Intent intent = new Intent(BLE_CHARACTERISTIC_CHANGED);
		intent.putExtra(EXTRA_ADDR, address);
		intent.putExtra(EXTRA_UUID, uuid);
		intent.putExtra(EXTRA_VALUE, value);
		sendBroadcast(intent);*/
		CommandResult commandResult =new CommandResult();
		commandResult.setFatScaleCommandType(Constant.FATSCALE_PRESS_COMMAND_SET);
		commandResult.setmFatScalecommandResult(Constant.READ_SUCESS);
		commandResult.setHex(HexUtils.byte2HexStr(value));
		OemLog.log(TAG, "byte is " + HexUtils.byte2HexStr(value));
		if (commandlistener != null) {
			commandlistener.onCommandResult(commandResult);
		}
		parse(HexUtils.byte2HexStr(value));
	}

	/**
	 * @param reason
	 */
	protected void bleStatusAbnormal(String reason) {
		Intent intent = new Intent(BLE_STATUS_ABNORMAL);
		intent.putExtra(EXTRA_VALUE, reason);
		mContext.sendBroadcast(intent);
	}

	/**
	 * Sent when BLE request failed.<br>
	 * <br>
	 * Data in the broadcast intent: <br>
	 * {@link } device address {@link String} <br>
	 * {@link } request type
	 * {@link BleRequest.RequestType} <br>
	 * {@link} fail reason {@link BleRequest.FailReason} <br>
	 */
	protected void bleRequestFailed(String address, BleRequest.RequestType type,
			BleRequest.FailReason reason) {
		Intent intent = new Intent(BLE_REQUEST_FAILED);
		intent.putExtra(EXTRA_ADDR, address);
		intent.putExtra(EXTRA_REQUEST, type);
		intent.putExtra(EXTRA_REASON, reason.ordinal());
		mContext.sendBroadcast(intent);
	}

	protected String getNotificationAddress() {
		return mNotificationAddress;
	}

	protected void setNotificationAddress(String mNotificationAddress) {
		this.mNotificationAddress = mNotificationAddress;
	}

	public void setServecieCallBackHandler(Handler servecieCallBackHandler) {
		this.servecieCallBackHandler = servecieCallBackHandler;
	}


	public void parse(final String hexStr){

		long lastResultTime;
		long currentResultTime;

		StringBuilder displayText=new StringBuilder();
		String [] data = HexUtils.analyzeHexToBinaryArray(hexStr.replaceAll(" ", ""));
		Log.i("result", "enter parse()..." + hexStr);
		if(hexStr.split(" ").length==20){

			//解析信息
			bodyFatResult = AnalyzeDataUtils.analyzeArrayBinaryToData(data);

			/*if (previousResult != null) {
				lastResultTime = Long.parseLong(previousResult.getLongTime());
				currentResultTime = Long.parseLong(bodyFatResult.getLongTime());
				if ((currentResultTime - lastResultTime) < 30 * 1000) return;
			}*/

			if(bodyFatResult.isBarefoot()) {
				displayText.append("体重：").append(bodyFatResult.getWeight()).append("\n");
				displayText.append("骨量：").append(bodyFatResult.getBone()).append("\n");
				displayText.append("脂肪率：").append(bodyFatResult.getFat()).append("\n");
				displayText.append("热量：").append(bodyFatResult.getKcal()).append("\n");
				displayText.append("肌肉含量：").append(bodyFatResult.getMuscle()).append("\n");
				displayText.append("内脏脂肪：").append(bodyFatResult.getVisceraFat()).append("\n");
				displayText.append("水份：").append(bodyFatResult.getWater()).append("\n");
				//你终于光脚测了
			} else {
				//干嘛不光脚？
				displayText.append("体重：").append(bodyFatResult.getWeight()).append("\n");
				displayText.append("<font color='red' >你为什么不光着脚，你是不是怕脚臭啊？</font>");
			}
			Log.i("bodyfatresult", displayText.toString());
			Log.i("bodyfatresult", "------" + bodyFatResult.toString());
			if (bodyFatDevicesCallBack != null) {
				bodyFatDevicesCallBack.devicesMeasureMeasureComepleteCallBack(bodyFatResult);
			}

			if (!isDataTransfer) {
				PreferencesUtils.putString(mContext, MyApplication.getInstance().getCurrentUserName() + "bodyfat_latest_measure_time", TimeStampUtil.currTime2(0));
				insertToDB();
				isDataTransfer = true;
				OemLog.log(TAG, "before send the ignore multi data message...");

				/*BodyFatShutdownCommand shutdownCommand = new BodyFatShutdownCommand();
				shutdownCommand.excuteCommand();*/
				removeMutiHandler.postDelayed(new Runnable() {
					@Override
					public void run() {
//
					}
				}, 2000);

				mBle.disconnect(currentAddress);

				removeMutiHandler.sendEmptyMessageDelayed(REMOVE_MULTI_BODYFAT_DATA_MESSAGE, REMOVE_MULTI_BODYFAT_DATA_MESSAGE_DELAY_TIME);

				bleGattDisConnected(currentAddress);

			}

			PreferencesUtils.putString(mContext, MyApplication.getInstance().getCurrentUserName() + "bodyfat_latest_measure_time", TimeStampUtil.latestTestTime(0));

		} else if (hexStr.split(" ").length==3) {
		}

	}

	private void insertToDB() {

		Thread insertThread = new Thread() {
			@Override
			public void run() {
				super.run();
				try {
					if (bodyFatResult != null) {
						OemLog.log(TAG, "bodyFatResult is not null and insert to DB...");
						dbManager.save(bodyFatResult);
//						previousResult = bodyFatResult;
					}
				} catch (DbException e) {
					Log.i(TAG,"save fail" + e.getMessage());
					e.printStackTrace();
				}
			}
		};
		insertThread.start();

	}

	public void registerBodyFatDevicesCallBack(IBodyFatDevicesCallBack bodyFatDevicesCallBack) {
		this.bodyFatDevicesCallBack = bodyFatDevicesCallBack;
	}


	private class BodyFatPersonInfo {
		public char sex;
		public int birthYear;
		public int birthMon;
		public int birDay;
		public int heitht;
		public int weitht;
		public int age;
	}

	public void notifyConncetTimeout() {
		servecieCallBackHandler.sendEmptyMessage(OemBackgroundService.BODYFAT_CONNECT_TIMEOUT_MESSAGE);
		if (mCurrentRequest != null) {
			mBle.disconnect(mCurrentRequest.address);
		}
		mCurrentRequest = null;
	}


}
