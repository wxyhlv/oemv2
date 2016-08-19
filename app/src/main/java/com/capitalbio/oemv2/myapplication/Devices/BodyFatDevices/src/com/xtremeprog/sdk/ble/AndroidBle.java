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

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.util.Log;

import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.Devices.BodyFatDevices.src.com.bayh.bluetooth.fatScale.command.BodyFatShutdownCommand;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;

import org.apache.commons.codec.binary.Hex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@SuppressLint("NewApi")
public class AndroidBle implements IBle, IBleRequestHandler,IBleExpand {

	protected static final String TAG = "AndroidBle";

	public static final int GATT_CONNECT_EXECUTE_FAILED = 200;
	public static final int GATT_CONNECT_EXECUTE_SUCCESS = 201;

	public static final int BODYFAT_SET_PERSON_INFO_MESSAGE = 20001;

	public static final int BODYFAT_PERSON_INFO_DELAY_TIME = 50;

	private BluetoothDevice currentDevice;
	private String connectAddress;
	private BodyFatDevices mDevices;
	private BluetoothAdapter mBtAdapter;
	private Map<String, BluetoothGatt> mBluetoothGatts;

	private BluetoothGattDescriptor setPersonDescriptor;

	private int enableDescStatus;

	private boolean isBodyfatConnected = false;
	//具体设备的contex对象
	private Context mContext = MyApplication.getInstance();
	
	// private BTQuery btQuery;
	private Handler delayHandler = new Handler() {
	    public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
				case GATT_CONNECT_EXECUTE_FAILED:
					OemLog.log(TAG, "receive reconnect message...");
					mDevices.addBleRequest(new BleRequest(BleRequest.RequestType.CONNECT_GATT, connectAddress));
					break;
				case BODYFAT_SET_PERSON_INFO_MESSAGE:
					OemLog.log(TAG, "receive set person info message...");
					mDevices.bleCharacteristicNotification(connectAddress, setPersonDescriptor
									.getCharacteristic().getUuid().toString(), true,
							enableDescStatus);
					break;
				case GATT_CONNECT_EXECUTE_SUCCESS:
					OemLog.log(TAG, "gatt connect successfull...");
					if (hasMessages(GATT_CONNECT_EXECUTE_FAILED)) {
						removeMessages(GATT_CONNECT_EXECUTE_FAILED);
					}
					mDevices.bleGattConnected(currentDevice);
					mDevices.addBleRequest(new BleRequest(BleRequest.RequestType.DISCOVER_SERVICE, connectAddress));
					break;
				default:
					break;
			}

		};
	};
	private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
		@Override
		public void onLeScan(final BluetoothDevice device, int rssi,
				byte[] scanRecord) {
			/*mService.bleDeviceFound(device, rssi, scanRecord,
					BleService.DEVICE_SOURCE_SCAN);*/
		}
	};

	private BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
		@Override
		public void onConnectionStateChange(BluetoothGatt gatt, int status,
				int newState) {
			String address = gatt.getDevice().getAddress();
			currentDevice = gatt.getDevice();
			connectAddress = address;
			OemLog.log(TAG, "onConnectionStateChange " + address + " status "
					+ status + " newState " + newState);
			if (status != BluetoothGatt.GATT_SUCCESS) {
				disconnect(address);
				mDevices.bleGattDisConnected(address);

				    /*delayHandler.postDelayed(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub

						}
					}, 800l);*/
				if (delayHandler.hasMessages(GATT_CONNECT_EXECUTE_FAILED)) {
					delayHandler.removeMessages(GATT_CONNECT_EXECUTE_FAILED);
				}
				delayHandler.sendEmptyMessageDelayed(GATT_CONNECT_EXECUTE_FAILED, 600l);
				return;
			}

			if (newState == BluetoothProfile.STATE_CONNECTED) {

				if (delayHandler.hasMessages(GATT_CONNECT_EXECUTE_FAILED)) {
					delayHandler.removeMessages(GATT_CONNECT_EXECUTE_FAILED);
				}
				delayHandler.sendEmptyMessageDelayed(GATT_CONNECT_EXECUTE_SUCCESS, 200l);
				MyApplication.getInstance().setIsDevicesMeasuring(true);
				
			} else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
				mDevices.bleGattDisConnected(address);
				disconnect(address);
				MyApplication.getInstance().setIsDevicesMeasuring(false);
			}
		}

		@Override
		public void onServicesDiscovered(BluetoothGatt gatt, int status) {
			String address = gatt.getDevice().getAddress();
			OemLog.log(TAG, "onServicesDiscovered " + address + " status " + status);
			if (status != BluetoothGatt.GATT_SUCCESS) {
				disconnect(address);
				mDevices.bleGattDisConnected(address);
				mDevices.requestProcessed(address,
						BleRequest.RequestType.DISCOVER_SERVICE, false);
				if (delayHandler.hasMessages(GATT_CONNECT_EXECUTE_FAILED)) {
					delayHandler.removeMessages(GATT_CONNECT_EXECUTE_FAILED);
				}

				delayHandler.sendEmptyMessageDelayed(GATT_CONNECT_EXECUTE_FAILED, 600l);
				return;
			}
			isBodyfatConnected = true;
			//发现服务后才为连接成功
			mDevices.bleServiceDiscovered(gatt.getDevice().getAddress());
			
		}

		@Override
		public void onCharacteristicRead(BluetoothGatt gatt,
				BluetoothGattCharacteristic characteristic, int status) {
			String address = gatt.getDevice().getAddress();
			OemLog.log(TAG, "onCharacteristicRead " + address + " status " + status);
			if (status != BluetoothGatt.GATT_SUCCESS) {

				mDevices.requestProcessed(address,
						BleRequest.RequestType.READ_CHARACTERISTIC, false);
				return;
			}
			// OemLog.log(TAG, "data " + characteristic.getStringValue(0));
			mDevices.bleCharacteristicRead(gatt.getDevice().getAddress(),
					characteristic.getUuid().toString(), status,
					characteristic.getValue());
		}

		@Override
		public void onCharacteristicChanged(BluetoothGatt gatt,
				BluetoothGattCharacteristic characteristic) {
			String address = gatt.getDevice().getAddress();
			OemLog.log(TAG, "onCharacteristicChanged " + address);
			mDevices.bleCharacteristicChanged(address, characteristic.getUuid()
					.toString(), characteristic.getValue());
		}

		public void onCharacteristicWrite(BluetoothGatt gatt,
				BluetoothGattCharacteristic characteristic, int status) {
			String address = gatt.getDevice().getAddress();
			OemLog.log(TAG, "onCharacteristicWrite " + address + " status " + status);
			if (status != BluetoothGatt.GATT_SUCCESS) {
				mDevices.requestProcessed(address,
						BleRequest.RequestType.WRITE_CHARACTERISTIC, false);
				if (delayHandler.hasMessages(GATT_CONNECT_EXECUTE_FAILED)) {
					delayHandler.removeMessages(GATT_CONNECT_EXECUTE_FAILED);
				}
				delayHandler.sendEmptyMessageDelayed(GATT_CONNECT_EXECUTE_FAILED, 600l);
				return;
			}
			mDevices.bleCharacteristicWrite(gatt.getDevice().getAddress(),
					characteristic.getUuid().toString(), status);
		};

		public void onDescriptorWrite(BluetoothGatt gatt,
				BluetoothGattDescriptor descriptor, int status) {
			connectAddress = gatt.getDevice().getAddress();
			setPersonDescriptor = descriptor;
			enableDescStatus = status;
			OemLog.log(TAG, "onDescriptorWrite " + connectAddress + " status " + status);
			BleRequest request = mDevices.getCurrentRequest();
			if (request.type == BleRequest.RequestType.CHARACTERISTIC_NOTIFICATION
					|| request.type == BleRequest.RequestType.CHARACTERISTIC_INDICATION
					|| request.type == BleRequest.RequestType.CHARACTERISTIC_STOP_NOTIFICATION) {
				if (status != BluetoothGatt.GATT_SUCCESS) {
					BodyFatShutdownCommand shutdownCommand = new BodyFatShutdownCommand();
					shutdownCommand.excuteCommand();
					mDevices.bleGattDisConnected(connectAddress);
					mDevices.requestProcessed(connectAddress,
							BleRequest.RequestType.CHARACTERISTIC_NOTIFICATION, false);
					if (delayHandler.hasMessages(GATT_CONNECT_EXECUTE_FAILED)) {
						delayHandler.removeMessages(GATT_CONNECT_EXECUTE_FAILED);
					}
					delayHandler.sendEmptyMessageDelayed(GATT_CONNECT_EXECUTE_FAILED, 600l);
					return;
				}
				if (request.type == BleRequest.RequestType.CHARACTERISTIC_NOTIFICATION) {
					delayHandler.sendEmptyMessageDelayed(BODYFAT_SET_PERSON_INFO_MESSAGE, BODYFAT_PERSON_INFO_DELAY_TIME);
				} else if (request.type == BleRequest.RequestType.CHARACTERISTIC_INDICATION) {
					mDevices.bleCharacteristicIndication(connectAddress, descriptor
							.getCharacteristic().getUuid().toString(), status);
				} else {
					mDevices.bleCharacteristicNotification(connectAddress, descriptor
							.getCharacteristic().getUuid().toString(), false,
							status);
				}
				return;
			}
		};
	};

	public AndroidBle(BodyFatDevices devices) {
		mDevices = devices;
		// btQuery = BTQuery.getInstance();
		if (!mContext.getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_BLUETOOTH_LE)) {
			mDevices.bleNotSupported();
			return;
		}

		final BluetoothManager bluetoothManager = (BluetoothManager) mContext
				.getSystemService(Context.BLUETOOTH_SERVICE);

		mBtAdapter = bluetoothManager.getAdapter();
		if (mBtAdapter == null) {
			mDevices.bleNoBtAdapter();
		}
		mBluetoothGatts = new HashMap<String, BluetoothGatt>();
	}

	@Override
	public void startScan() {
		mBtAdapter.startLeScan(mLeScanCallback);
	}

	@Override
	public void startScan(UUID uuid) {
		mBtAdapter.startLeScan(new UUID[]{uuid},mLeScanCallback);
	}

	@Override
	public void stopScan() {
		mBtAdapter.stopLeScan(mLeScanCallback);
	}

	@Override
	public boolean adapterEnabled() {
		if (mBtAdapter != null) {
			return mBtAdapter.isEnabled();
		}
		return false;
	}

	@Override
	public boolean connect(String address) {
		OemLog.log(TAG, "connect...address is " + address);
		if (address == null) {
			return false;
		} else {
			connectAddress = address;
		}
		BluetoothDevice device = mBtAdapter.getRemoteDevice(address);
		BluetoothGatt gatt = device.connectGatt(mContext, false, mGattCallback);
		if (gatt == null) {
			mBluetoothGatts.remove(address);
			return false;
		} else {
			// TODO: if state is 141, it can be connected again after about 15
			// seconds
			mBluetoothGatts.put(address, gatt);
			return true;
		}
	}

	@Override
	public void disconnect(String address) {
		Log.i(TAG, "disconnect is called", new Throwable());
		if (mBluetoothGatts.containsKey(address)) {
			BluetoothGatt gatt = mBluetoothGatts.remove(address);
			if (gatt != null) {
				OemLog.log(TAG, "disconnect gatt connection...");
				gatt.disconnect();
				gatt.close();
			}
		}
		mBluetoothGatts.clear();

	}

	@Override
	public ArrayList<BleGattService> getServices(String address) {
		BluetoothGatt gatt = mBluetoothGatts.get(address);
		if (gatt == null) {
			return null;
		}

		ArrayList<BleGattService> list = new ArrayList<BleGattService>();
		List<BluetoothGattService> services = gatt.getServices();
		for (BluetoothGattService s : services) {
			BleGattService service = new BleGattService(s);
			// service.setInfo(btQuery.getGattServiceInfo(s.getUuid()));
			list.add(service);
		}
		return list;
	}

	@Override
	public boolean requestReadCharacteristic(String address,
			BleGattCharacteristic characteristic) {
		BluetoothGatt gatt = mBluetoothGatts.get(address);
		if (gatt == null || characteristic == null) {
			return false;
		}

		mDevices.addBleRequest(new BleRequest(BleRequest.RequestType.READ_CHARACTERISTIC,
				gatt.getDevice().getAddress(), characteristic));
		return true;
	}

	public boolean readCharacteristic(String address,
			BleGattCharacteristic characteristic) {
		BluetoothGatt gatt = mBluetoothGatts.get(address);
		if (gatt == null) {
			return false;
		}

		return gatt.readCharacteristic(characteristic.getGattCharacteristicA());
	}

	@Override
	public boolean discoverServices(String address) {
		BluetoothGatt gatt = mBluetoothGatts.get(address);
		if (gatt == null) {
			return false;
		}

		boolean ret = gatt.discoverServices();
		if (!ret) {
			disconnect(address);
		}
		return ret;
	}

	@Override
	public BleGattService getService(String address, UUID uuid) {
		BluetoothGatt gatt = mBluetoothGatts.get(address);
		if (gatt == null) {
			return null;
		}

		BluetoothGattService service = gatt.getService(uuid);
		if (service == null) {
			return null;
		} else {
			return new BleGattService(service);
		}
	}

	@Override
	public boolean requestCharacteristicNotification(String address,
			BleGattCharacteristic characteristic) {
		BluetoothGatt gatt = mBluetoothGatts.get(address);
		if (gatt == null || characteristic == null) {
			return false;
		}

		mDevices.addBleRequest(new BleRequest(
				BleRequest.RequestType.CHARACTERISTIC_NOTIFICATION, gatt.getDevice()
						.getAddress(), characteristic));
		return true;
	}

	@Override
	public boolean characteristicNotification(String address,
			BleGattCharacteristic characteristic) {
		BleRequest request = mDevices.getCurrentRequest();
		BluetoothGatt gatt = mBluetoothGatts.get(address);
		if (gatt == null || characteristic == null) {
			return false;
		}

		boolean enable = true;
		if (request.type == BleRequest.RequestType.CHARACTERISTIC_STOP_NOTIFICATION) {
			enable = false;
		}
		BluetoothGattCharacteristic c = characteristic.getGattCharacteristicA();
		if (!gatt.setCharacteristicNotification(c, enable)) {
			return false;
		}
		BluetoothGattDescriptor descriptor = c
				.getDescriptor(BodyFatDevices.DESC_CCC);
		if (descriptor == null) {
			return false;
		}

		byte[] val_set = null;
		if (request.type == BleRequest.RequestType.CHARACTERISTIC_NOTIFICATION) {
			val_set = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE;
		} else if (request.type == BleRequest.RequestType.CHARACTERISTIC_INDICATION) {
			val_set = BluetoothGattDescriptor.ENABLE_INDICATION_VALUE;
		} else {
			val_set = BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE;
		}
		if (!descriptor.setValue(val_set)) {
			return false;
		}

		return gatt.writeDescriptor(descriptor);
	}

	@Override
	public boolean requestWriteCharacteristic(String address,
			BleGattCharacteristic characteristic, String remark) {
		BluetoothGatt gatt = mBluetoothGatts.get(address);
		if (gatt == null || characteristic == null) {
			return false;
		}

		mDevices.addBleRequest(new BleRequest(BleRequest.RequestType.WRITE_CHARACTERISTIC,
				gatt.getDevice().getAddress(), characteristic, remark));
		return true;
	}

	@Override
	public boolean writeCharacteristic(String address,
			BleGattCharacteristic characteristic) {
		BluetoothGatt gatt = mBluetoothGatts.get(address);
		if (gatt == null) {
			return false;
		}

		OemLog.log("blelib", new String(Hex.encodeHex(characteristic.getGattCharacteristicA().getValue())));
		return gatt.writeCharacteristic(characteristic.getGattCharacteristicA());
	}

	@Override
	public boolean requestConnect(String address) {
		BluetoothGatt gatt = mBluetoothGatts.get(address);
		if (gatt != null && gatt.getServices().size() == 0) {
			return false;
		}

		mDevices.addBleRequest(new BleRequest(BleRequest.RequestType.CONNECT_GATT, address));
		return true;
	}

	@Override
	public String getBTAdapterMacAddr() {
		if (mBtAdapter != null) {
			return mBtAdapter.getAddress();
		}
		return null;
	}

	@Override
	public boolean requestIndication(String address,
			BleGattCharacteristic characteristic) {
		BluetoothGatt gatt = mBluetoothGatts.get(address);
		if (gatt == null || characteristic == null) {
			return false;
		}

		mDevices.addBleRequest(new BleRequest(
				BleRequest.RequestType.CHARACTERISTIC_INDICATION, gatt.getDevice()
						.getAddress(), characteristic));
		return true;
	}

	@Override
	public boolean requestStopNotification(String address,
			BleGattCharacteristic characteristic) {
		BluetoothGatt gatt = mBluetoothGatts.get(address);
		if (gatt == null || characteristic == null) {
			return false;
		}

		mDevices.addBleRequest(new BleRequest(
				BleRequest.RequestType.CHARACTERISTIC_NOTIFICATION, gatt.getDevice()
						.getAddress(), characteristic));
		return true;
	}
}
