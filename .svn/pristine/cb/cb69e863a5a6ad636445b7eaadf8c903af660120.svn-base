package com.capitalbio.oemv2.myapplication.Devices.BodyFatDevices.src.com.bayh.sdk.ble;/*package com.bayh.sdk.ble;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;

import com.bayh.sdk.ble.bean.BleConfigBean;
import com.bayh.sdk.ble.utils.HexUtils;
import com.bayh.sdk.ble.utils.MLog;
import com.xtremeprog.sdk.ble.BleGattCharacteristic;
import com.xtremeprog.sdk.ble.BleGattService;
import com.xtremeprog.sdk.ble.BleService;
import com.xtremeprog.sdk.ble.IBleExpand;

*//**
 * �豸ͨ�ſ�����
 * 
 * @author jiantao.tu
 * 
 *//*
public class BleControlConnection {

	*//**
	 * �ص�
	 *//*
	private AbstractResultCallBack callback;

	private Handler mHandler;

	private BluetoothDevice device;

	private static final String TAG = "BleControlConnection";

	*//**
	 * �Ƿ�ֹͣ����
	 *//*
	private boolean isScanning;

	public BleService mService;

	*//**
	 * Ble�ӿ�ʵ��ʵ��
	 *//*
	public IBleExpand mBle;

	*//**
	 * �Ƿ�����
	 *//*
	private boolean isConnected = false;
	*//**
	 * �Ƿ���
	 * 
	 * *//*
	private boolean isbind = false;
	*//**
	 * �豸Ψһ��ʶ��
	 *//*
	private String mDeviceAddress;

	private static final long SCAN_PERIOD = 10000;

	private Context context;
	private BleConfigBean configBean;

	public BleControlConnection(BleConfigBean configBean) {
		this.configBean = configBean;
		mHandler = new Handler();
	}

	private final BroadcastReceiver mBleReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle extras = intent.getExtras();
			if (mDeviceAddress != null) {
				if (!mDeviceAddress.equals(extras
						.getString(BleService.EXTRA_ADDR)))
					return;
			}
			String action = intent.getAction();
			if (BleService.BLE_DEVICE_FOUND.equals(action)) {// �����豸
				device = extras.getParcelable(BleService.EXTRA_DEVICE);
				try {
					if (device != null
							&& device.getName() != null
							&& device.getName().contains(
									configBean.getDeviceName())) {
						mDeviceAddress = device.getAddress();
						mBle.stopScan();
						mBle.requestConnect(mDeviceAddress);
					}
				} catch (NullPointerException e) {
				}
				MLog.i(TAG, "�����豸");
				return;
			} else if (BleService.BLE_GATT_DISCONNECTED.equals(action)) {// ���豸�Ͽ�����
				isConnected = false;
				if(!BleControlConnection.this.isIsbind())
					unbindBloodPressDevices(context);
				callback.disConnect();
				MLog.i(TAG, "���豸�Ͽ�����");
				return;
			} else if (BleService.BLE_GATT_CONNECTED.equals(action)) {// ���ӳɹ�
				isConnected = true;
				MLog.i(TAG, "���ӳɹ�");
//				Toast.makeText(context, "���ӳɹ�", Toast.LENGTH_SHORT).show();
				return;
			} else if (BleService.BLE_SERVICE_DISCOVERED.equals(action)) {// ��ȡ���豸�ķ���
				callback.connectOk();
				MLog.i(TAG, "��ȡ���豸�ķ���");
//				Toast.makeText(context, "��ȡ���豸�ķ���", Toast.LENGTH_SHORT).show();
				return;
			} else if (BleService.BLE_CHARACTERISTIC_NOTIFICATION
					.equals(action)) {// ע��֪ͨ
				MLog.i(TAG, "ע��֪ͨ");
			} else if (BleService.BLE_CHARACTERISTIC_READ.equals(action)) {// ��ȡ���
//				byte[] bytes = extras.getByteArray(BleService.EXTRA_VALUE);
				callback.readResult(HexUtils.byte2HexStr(extras
						.getByteArray(BleService.EXTRA_VALUE)));
				MLog.i(TAG, "��ȡ��ݳɹ�");
			} else if (BleService.BLE_CHARACTERISTIC_WRITE.equals(action)) {// д����ݳɹ�
				MLog.i(TAG, "д����ݳɹ�");
			} else if (BleService.BLE_CHARACTERISTIC_CHANGED.equals(action)) {// �豸��Ӧ�����
				callback.writeResult(HexUtils.byte2HexStr(extras
						.getByteArray(BleService.EXTRA_VALUE)));
				MLog.i(TAG, "��ȡ�������ݳɹ�");
			}
		}
	};

	private final ServiceConnection mServiceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName className,
				IBinder rawBinder) {
			// 1. �� service �ɹ�����ȡ mBle ����
			mService = ((BleService.LocalBinder) rawBinder).getService();
			mBle = (IBleExpand) mService.getBle();
			scanLeDevice(true);
		}

		@Override
		public void onServiceDisconnected(ComponentName classname) {
			unbindBloodPressDevices(context);
		}
	};

	*//**
	 * ����ֹͣ�豸
	 * 
	 * @param enable
	 *//*
	public void scanLeDevice(final boolean enable) {
		if (mBle == null) {
			return;
		}
		if (enable) {
			// Stops scanning after a pre-defined scan period.
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					isScanning = false;
					if (mBle != null) {
						mBle.stopScan();
					}
				}
			}, SCAN_PERIOD + 20000);

			isScanning = true;
			if (mBle != null) {
				mBle.startScan();
			}
		} else {
			isScanning = false;
			if (mBle != null) {
				mBle.stopScan();
			}
		}
	}

	*//**
	 * ���豸д�����
	 * 
	 * @param bytes
	 *//*
	public synchronized void wirte(byte[] bytes) {
		if (null == mBle) {
			return;
		}
		BleGattService service = mBle.getService(mDeviceAddress,
				configBean.getServiceUUID());
		if (service == null)
			return;
		BleGattCharacteristic characteristic = service
				.getCharacteristic(configBean.getWriteUUID());
		if (characteristic == null)
			return;
		characteristic.setValue(bytes);
		// 02 20 DD 07 FD 53 01 00 40 95 AF 2F
		// 6e 01 02 01 8f
		mBle.requestCharacteristicNotification(mDeviceAddress,
				service.getCharacteristic(configBean.getReadUUID()));
		mBle.requestWriteCharacteristic(mDeviceAddress, characteristic, "");
	}

	*//**
	 * ���豸��ȡ���
	 *//*
	public synchronized void read() {
		if (null == mBle) {
			return;
		}
		BleGattService service = mBle.getService(mDeviceAddress,
				configBean.getServiceUUID());
		if (service == null)
			return;
		BleGattCharacteristic characteristic = service
				.getCharacteristic(configBean.getReadUUID());
		if (characteristic == null)
			return;
		// 02 20 DD 07 FD 53 01 00 40 95 AF 2F
		// 6e 01 02 01 8f
		mBle.requestCharacteristicNotification(mDeviceAddress, characteristic);
		mBle.requestReadCharacteristic(mDeviceAddress, characteristic);
	}

	*//**
	 * ���豸��������
	 * 
	 * @param context
	 * @param callback
	 *//*
	public synchronized void bind(Context context,
			AbstractResultCallBack callback) {
		this.callback = callback;
		Intent bindIntent = new Intent(context, BleService.class);
		this.context = context;
		context.bindService(bindIntent, mServiceConnection,
				Context.BIND_AUTO_CREATE);
		// 3. ���� service ���͵Ĺ㲥
		context.registerReceiver(mBleReceiver, BleService.getIntentFilter());
		isbind=true;
		// context.registerReceiver(utils.mBleReceiver1,
		// BleService.getIntentFilter());

	}

	*//**
	 * ����
	 * 
	 * @param context
	 *//*
	public synchronized void unbindBloodPressDevices(Context context) {
		// 5. ֹͣ���չ㲥
		if (isbind) {
			isbind=false;
		}else
			return;
		context.unregisterReceiver(mBleReceiver);
		context.unbindService(mServiceConnection);
		scanLeDevice(false);
		device = null;
		mBle.disconnect(mDeviceAddress);
		mService = null;
		mBle = null;

	}
	
	public void disConnect(){
		MLog.i(TAG, "�Ͽ�����disConnect����");
		mBle.disconnect(mDeviceAddress);
	}
	public boolean isIsbind() {
		return isbind;
	}

	*//**
	 * �Ƿ�ֹͣ����
	 *//*
	public boolean isScanning() {
		return isScanning;
	}

	*//**
	 * �Ƿ������豸
	 *//*
	public boolean isConnected() {
		return isConnected;
	}

	*//**
	 * ���豸ͨ�Żص��ӿ�
	 * 
	 * @author jiantao.tu
	 * 
	 *//*
	public static interface IResultCallback {

		*//**
		 * ���豸��ȡ���ص���Ϣ
		 * 
		 * @param hexStr
		 *            16�����ַ� AA 55 B3 FF ÿ�����Կո����
		 *//*
		void readResult(String hexStr);

		*//**
		 * ���豸д�뷵�ص���Ϣ��һ���ڴ˷���������豸���ص���Ϣ
		 * 
		 * @param hexStr
		 *            16�����ַ� AA 55 B3 FF ÿ�����Կո����
		 *//*
		void writeResult(String hexStr);

		*//**
		 * ���豸���ӳɹ���һ���ڴ˷�����������豸��ȡ����д�����
		 * 
		 * @return
		 *//*
		void connectOk();

		*//**
		 * ���豸���ӶϿ���ʧȥ���ӱ���ͨ�Ž���
		 * 
		 * @return
		 *//*
		void disConnect();

	}

	*//**
	 * IResultCallback��ʵ���࣬��������ʵ�����нӿڵĿ��Դ��ݴ˶����ʵ��
	 * 
	 * @author jiantao.tu
	 * 
	 *//*
	public static abstract class AbstractResultCallBack implements
			IResultCallback {
		@Override
		public void readResult(String hexStr) {

		}

		@Override
		public void writeResult(String hexStr) {

		}

		@Override
		public void connectOk() {
		}

		@Override
		public void disConnect() {
		}

	}
}
*/