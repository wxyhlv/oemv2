package com.capitalbio.oemv2.myapplication.FirstPeriod.BlueTooth;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.capitalbio.oemv2.myapplication.FirstPeriod.AircatProtocolParse.AirCatBean;
import com.capitalbio.oemv2.myapplication.FirstPeriod.AircatProtocolParse.AirCatDataParse;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;
import java.util.UUID;


public class BluetoothService {
	private String TAG = "BluetoothService";

	public  static byte[] command = new byte[] {(byte)0xaa, (byte)0x04, (byte)0x00,
			(byte)0x03, (byte)0x00, (byte)0x01,
			(byte)0x00, (byte)0x01, (byte)0x00};

	private UUID mUUID = UUID
			.fromString("00001101-0000-1000-8000-00805F9B34FB");
//			.fromString("00001000-0000-1000-8000-00805F9B34FB");
//			.fromString("00000003-0000-1000-8000-00805F9B34FB");
//			.fromString("04c6093b-0000-1000-8000-00805f9b34fb");
//			.fromString("00001810-0000-1000-8000-00805f9b34fb");
//			.fromString("00002a51-0000-1000-8000-00805f9b34fb");
//			.fromString("00002A50-0000-1000-8000-00805F9B34FB");
	private Handler mHandler;

	private InputStream mInputStream = null;
	private OutputStream mOutputStream = null;
	private int readCount = 0;
	private int addCount = 0;
	private boolean isFirst = true;
	private String charsetName;
	private BluetoothAdapter mBluetoothAdapter;
	private BluetoothDevice mBluetoothDevice;
	/**蓝牙通信管理线程集合*/
	public List<ManageConectedThread> mManageConectedThreads;
	/**通知远端蓝牙设备开始发送数据指令**/
	//启动设备
	private byte[] START_DEVICE = {(byte)0xFF ,(byte)0xAA ,(byte)0x00 ,(byte)0xDD,(byte)0x86};
	//确认收到数据
    private byte[] RECEIVE_DATA_OK = {(byte)0xFF ,(byte)0xBB ,(byte)0x00 ,(byte)0xDD};
	//接收数据错误
	private byte[] RECEIVE_DATA_ERROR = {(byte)0xFF ,(byte)0xAA ,(byte)0x00 ,(byte)0xEE};
//	private byte[] START_DEVICE = {0x73,0x74,0x61};
//	private byte[] START_DEVICE = {0x4c,0x45,0x44,0x78};
//	private String START_DEVICE = "LEDx";
	
	private int port = 1;
	
	//存放蓝牙设备连接状态
	private Set<String> currentConnectionAdds = new TreeSet<String>();
	
	//存放需要发送到数据
	public static Stack<String> pushs = new Stack<String>();

	public BluetoothService(Handler handler, BluetoothAdapter adapter, String charsetName) {
		mHandler = handler;
		mBluetoothAdapter = adapter;
		this.charsetName = charsetName;

	}

	/**
	 * 发起连接
	 */
	public void startConnect(BluetoothDevice device){
		if(device != null){
			//判断设备当前是否已经连接
			if(!currentConnectionAdds.contains(device.getAddress())){
				new ConnectThread(device).start();
				mHandler.obtainMessage(BluetoothState.BLUETOOTH_STATE_START, this.mBluetoothDevice).sendToTarget();
			}
		}
	}

	/**
	 * 断开连接
	 */
	public void closeConnect() {
		if(mManageConectedThreads == null){
			return ;
		}
		while (!mManageConectedThreads.isEmpty()) {
			mManageConectedThreads.get(0).close();
		}
	}

	/**
	 * 发起连接线程
	 * @author xusun-n
	 *
	 */
	class ConnectThread extends Thread {
		private BluetoothSocket mSocket;
		private BluetoothDevice mBluetoothDevice;

		public ConnectThread(BluetoothDevice bluetoothDevice) {
			mBluetoothDevice = bluetoothDevice;
			createSocket();
		}

		@SuppressLint("NewApi")
		private void createSocket() {
			BluetoothSocket tmp = null;
			try {
				tmp = mBluetoothDevice.createInsecureRfcommSocketToServiceRecord(mUUID);
//				tmp = mBluetoothDevice.createRfcommSocketToServiceRecord(mUUID);
				LogUtils.d(TAG, "createInsecure()");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			mSocket = tmp;
		}

		@Override
		public void run() {
			LogUtils.d(TAG, "启动连接！");
			for (int i = 1; i < 4; i++) {

				try {
					mBluetoothAdapter.cancelDiscovery();
					mSocket.connect();
					LogUtils.d(TAG, "连接成功！=" + i);
					startManageConected(mSocket, this.mBluetoothDevice);
					break;
				} catch (IOException e) {
					e.printStackTrace();
					LogUtils.d(TAG, "连接失败！=" + i);
					try {
						mSocket.close();
					} catch (IOException e1) {

					}
					
					if (i == 3) {
						mHandler.obtainMessage(BluetoothState.BLUETOOTH_STATE_FAIL, this.mBluetoothDevice).sendToTarget();
					}

					if (i < 3) {
						//增加1000毫秒延时提高连接安全性
						try {
							sleep(1000);
						} catch (InterruptedException e2) {
							e2.printStackTrace();
						}
						//再次创建Socket
						createSocket();
					}
				}
			}
		}

		public void close() {
			try {
				if(mSocket != null){
					mSocket.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 获得当前连接设备Mac
	 * @return
	 */
	public Set<String> getCurrentConnectionAdds() {
		return currentConnectionAdds;
	}

	/**
	 * 启动处理接收数据
	 * @param socket
	 * @param device当前设备
	 */
	private void startManageConected(BluetoothSocket socket, BluetoothDevice device) {
		ManageConectedThread conectedThread = new ManageConectedThread(socket, device);
		conectedThread.start();
		if(mManageConectedThreads == null){
			mManageConectedThreads = new ArrayList<ManageConectedThread>();
		}
		mManageConectedThreads.add(conectedThread);
		LogUtils.d(TAG+"1", " mManageConectedThreads.size : "+mManageConectedThreads.size());
	}

	/**
	 * 处理连接成功后数据
	 * @author xusun-n
	 *
	 */
	class ManageConectedThread extends Thread {
		private BluetoothSocket mSocket;
		private BluetoothDevice mBluetoothDevice;
		
		private boolean redFlag = true;
		private boolean start = true;

		public ManageConectedThread(BluetoothSocket bluetoothSocket, BluetoothDevice device) {
			mSocket = bluetoothSocket;
			mBluetoothDevice = device;
			if(mSocket != null){
				
				try {
					mInputStream = mSocket.getInputStream();
					mOutputStream = mSocket.getOutputStream();
					
					currentConnectionAdds.add(mSocket.getRemoteDevice().getAddress());
					mHandler.obtainMessage(BluetoothState.BLUETOOTH_STATE_SUCCEED, this.mBluetoothDevice).sendToTarget();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					mHandler.obtainMessage(BluetoothState.BLUETOOTH_STATE_FAIL, this.mBluetoothDevice).sendToTarget();
					e.printStackTrace();
				}
			}
		}

		@Override
		public void run() {
			String message = "";
			byte [] dataByte = new byte[140];
			byte [] parseData = new byte[69];
			InputStreamReader inputStreamReader;
			BufferedReader bufferedReader;
			
			byte[] buffer = new byte[50];
			int bytes;
			if(mSocket != null){
				try {
					sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (mInputStream != null && mOutputStream != null) {
					try {
						if(TextUtils.isEmpty(charsetName)){
							inputStreamReader = new InputStreamReader(mInputStream);
						}else{
							inputStreamReader = new InputStreamReader(mInputStream, charsetName);
						}
						bufferedReader = new BufferedReader(inputStreamReader);
						
						while (redFlag) {
							if(isFirst) {
								mOutputStream.write(command);
								OemLog.log(TAG, "send byte is " + Arrays.toString(command));
								isFirst = false;
							} else {
								readCount = mInputStream.read(dataByte);
								OemLog.log(TAG, "readCount is " + readCount);
								while (readCount < 140) {
									addCount = mInputStream.read(dataByte, readCount, 140 - readCount);
									OemLog.log(TAG, "addCount is " + addCount);
									readCount += addCount;
								}

								OemLog.log(TAG, "recieve byte is " + Arrays.toString(dataByte));

								readCount = 0;
								addCount = 0;

								//截取一个完整的数据包进行解析
								for (int i = 0; i < dataByte.length; i++) {
									if (dataByte[i] == (byte)0xaa && dataByte[i + 1] == (byte)0x04 && dataByte[i + 3] == (byte)0x03 && dataByte[i + 5] == (byte)0x02) {
										byte verfy = 0x00;
										verfy += dataByte[i+4];
										verfy += dataByte[i+5];
										verfy += dataByte[i+6];
										OemLog.log(TAG, ", i is " + i + ", verify code is " + verfy + ", packet verify is " + dataByte[i + 7]);
										if (verfy == dataByte[i + 7]) {
											System.arraycopy(dataByte, i, parseData, 0, 69);
											break;
										}
									}
								}

								OemLog.log(TAG, "parse byte is " + Arrays.toString(parseData));
								AirCatBean airCatBean = AirCatDataParse.dataParse(parseData);
								Message msg = new Message();
								msg.what = BluetoothState.BLUETOOTH_STATE_RECEIPT;
								msg.obj = airCatBean;
								mHandler.sendMessage(msg);
								LogUtils.d(TAG, "蓝牙接收数据：" + message);
							}

							sleep(3000l);

						}
					} catch (Exception e) {
						e.printStackTrace();
						LogUtils.d(TAG, "连接断开。。。。。");
						redFlag = false;
					} 
					finally{
						close();
					}
				}
			}
		}
		
		/**
		 * 断开连接
		 */
		private synchronized void close() {
			redFlag = false;
			mManageConectedThreads.remove(this);
			try {
				if (mSocket != null) {
					currentConnectionAdds.remove(mSocket.getRemoteDevice().getAddress());
					//当前没有任何连接时通知界面更新连接状态
					if(currentConnectionAdds.isEmpty()){
						mHandler.obtainMessage(BluetoothState.BLUETOOTH_STATE_DISCONNECT, this.mBluetoothDevice).sendToTarget();
					}

					mSocket.close();
				}
				if(mInputStream != null){
					mInputStream.close();
					mInputStream = null;
				}
				if(mOutputStream != null){
					mOutputStream.close();
					mOutputStream = null;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void sendMessage(byte[] msg){
		
		if (mOutputStream == null)return;
		
		try {
			mOutputStream.write(msg);
			Log.i("aaa", new String(msg).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
