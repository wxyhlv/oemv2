package com.capitalbio.oemv2.myapplication.FirstPeriod.BlueTooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.util.Log;

import com.capitalbio.oemv2.myapplication.FirstPeriod.AircatProtocolParse.AirCatBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 蓝牙通信管理
 * @author sunxu
 *
 */
public class BluetoothManage {
	private String TAG = "BluetoothManage";
	private Context mContext;
	private BluetoothAdapter mBluetoothAdapter;
	private BluetoothService mBluetoothService;


	
	/**配对请求**/
	private String ACTION_PAIRING_REQUEST = "android.bluetooth.device.action.PAIRING_REQUEST";
	
	/**
	 * 接收数据
	 */
	private Handler mBluetoothHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			
			switch (msg.what) {
				//开始连接
				case BluetoothState.BLUETOOTH_STATE_START:
					mOnBluetoothListener.BluetoothConnected((BluetoothDevice)msg.obj, msg.what);
				break;
				
				//连接成功
				case BluetoothState.BLUETOOTH_STATE_SUCCEED:
					mOnBluetoothListener.BluetoothConnected((BluetoothDevice)msg.obj, msg.what);
				break;
				
				//连接失败
				case BluetoothState.BLUETOOTH_STATE_FAIL:
					mOnBluetoothListener.BluetoothConnected((BluetoothDevice)msg.obj, msg.what);
				break;
			
				//连接断开
				case BluetoothState.BLUETOOTH_STATE_DISCONNECT:
					mOnBluetoothListener.BluetoothConnected((BluetoothDevice)msg.obj, msg.what);
				break;

				//数据接收中	
				case BluetoothState.BLUETOOTH_STATE_RECEIPT:
					if(mOnBluetoothListener != null){
						AirCatBean data = (AirCatBean) msg.obj;
						mOnBluetoothListener.BluetoothData(data);
					}
				break;
			}
		};
	};

	private String charsetName = null;
	//指定设备名称
	private String DEVICE_NAME = "";
	//连接单一设备或多个设备
	private boolean isSingle = false;
	//是否打印调试日志
	private boolean isDebug = false;
	//蓝牙状态监听
	private OnBluetoothListener mOnBluetoothListener;
	//发现设备时是否主动连接
	private boolean isAutomaticConnection = false;
	
	private BluetoothManage(Builder builder){
		this.mContext = builder.mContext;
		this.DEVICE_NAME = builder.DEVICE_NAME;
		this.isSingle = builder.isSingle;
		this.charsetName = builder.charsetName;
		LogUtils.isDebug = builder.isDebug;
		this.mOnBluetoothListener = builder.mOnBluetoothListener;
		this.isAutomaticConnection = builder.isAutomaticConnection;
		
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	}
	
	/**
	 * 打开蓝牙
	 */
	public void enable(){
		
		if(!mBluetoothAdapter.isEnabled()){
			mBluetoothAdapter.enable();
		}
		
	}
	
	
	/**
	 * 获取
	 */
	public BluetoothAdapter getBluetoothAdapter(){
		
		if (mBluetoothAdapter == null) {
			mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		}
		
		return mBluetoothAdapter;
	}
	
	/**
	 * 解除注册广播,断开通讯连接
	 */
	public void disable(){
//		mBluetoothAdapter.disable();
		cancelDiscovery();
		try {
			mContext.unregisterReceiver(mStateReceiver);
			mContext.unregisterReceiver(mDiscoveryReceiver);
		} catch (Exception e){
			// TODO: handle exception
		}
		disConnect();
	}
	
	/**
	 * 
	 * 扫描周围设备，调用此方法前需先开启蓝牙
	 */
	public void startDiscovery(){
		registerStateReceiver();
		registerDiscoveryReceiver();
		mBluetoothService = new BluetoothService(mBluetoothHandler, mBluetoothAdapter, charsetName);
		
		if(! mBluetoothAdapter.isDiscovering()){
			//开始扫描
			mBluetoothAdapter.startDiscovery();
		}
	}
	
	/**
	 * 停止扫描设备
	 */
	public void cancelDiscovery(){
		if(mBluetoothAdapter.isDiscovering()){
			mBluetoothAdapter.cancelDiscovery();
		}
	}
	
	/**
	 * 连接设备
	 * @param device
	 */
	public void connect(BluetoothDevice device){
		mBluetoothService.startConnect(device);
	}
	
	/**
	 * 断开连接
	 */
	public void disConnect(){
		if(mBluetoothService != null){
			mBluetoothService.closeConnect();
		}
	}
	
	/**
	 * 关闭蓝牙
	 */
	public void off(){
		if(mBluetoothAdapter != null){
			mBluetoothAdapter.disable();
		}
	}
	
	/**
	 * 注册蓝牙状态广播
	 */
	private void registerStateReceiver(){
		IntentFilter filter = new IntentFilter();
		filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
		filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
		filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
		filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
		filter.addAction(ACTION_PAIRING_REQUEST);
		mContext.registerReceiver(mStateReceiver, filter);
	}
	
	/**
	 * 接受蓝牙状态事件
	 */
	private BroadcastReceiver mStateReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			
			String action = intent.getAction();
			
			//监听蓝牙开关状态
			if(BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)){
				
				int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothDevice.BOND_NONE);
				
				if(state == BluetoothAdapter.STATE_ON){
					LogUtils.d(TAG, "蓝牙开启！");
					
					mOnBluetoothListener.BluetoothChanged(BluetoothState.BLUETOOTH_STATE_ON);
					
					startDiscovery();
					
				}else if(state == BluetoothAdapter.STATE_OFF){
					mOnBluetoothListener.BluetoothChanged(BluetoothState.BLUETOOTH_STATE_OFF);
					Log.d(TAG, "蓝牙关闭！");
				}
			}
			
			else if(BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)){
				LogUtils.d(TAG, "蓝牙连接已连接！");
				
			}
			
			else if(BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)){
				LogUtils.d(TAG, "蓝牙连接已经断开！");

			}
			
			//配对
			else if(BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)){
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				switch (device.getBondState()) {
				case BluetoothDevice.BOND_BONDING:
					LogUtils.d(TAG, "开始配对！");
					
					break;

				case BluetoothDevice.BOND_BONDED:
					LogUtils.d(TAG, "配对成功！");
					
					break;
					
				case BluetoothDevice.BOND_NONE:
					LogUtils.d(TAG, "配对取消！");
					
					break;
				}
			}
			
			else if(ACTION_PAIRING_REQUEST.equals(action)){
				LogUtils.d(TAG, "开始配对活动！");
				
			}
		}
	};
	
	/**
	 * 注册蓝牙扫描广播
	 */
	private void registerDiscoveryReceiver(){
		IntentFilter filter = new IntentFilter();
		filter.addAction(BluetoothDevice.ACTION_FOUND);
		filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
		filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		mContext.registerReceiver(mDiscoveryReceiver, filter);
	}
	
	/**
	 * 蓝牙扫描广播接收
	 */
	private List<BluetoothDevice> devices = new ArrayList<BluetoothDevice>();
	private BroadcastReceiver mDiscoveryReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			
			//开始扫描周围设备
			if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)){
				if(devices == null){
					devices = new ArrayList<BluetoothDevice>();
				}
				devices.clear();
				LogUtils.d(TAG, "开始扫描周围蓝牙设备");
			}
			
			//发现一个设备
			else if(BluetoothDevice.ACTION_FOUND.equals(action)){
				
				//获得扫描到的蓝牙设配器的对象
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				 
				if(device == null){
					return;
				}
				LogUtils.d(TAG, "发现设备："+device.getAddress()+"---"+device.getName());
				String deviceName = device.getName();

				//判断发现的是不是指定的设备
				if(!DEVICE_NAME.equals("") && deviceName != null && !deviceName.startsWith(DEVICE_NAME)){
					return;
				}

				
				//防止重复添加
				for (int i = 0; i < devices.size(); i++) {
					if(devices.get(i).getAddress().equals(device.getAddress())){
						return;
					}
				}
				devices.add(device);
				
				//判断是否需要自动连接
				if(isAutomaticConnection == true){
					
					//判断是否只需要连接一个设备
					if(isSingle == true){
						//发现到一个设备时马上停止扫描
						cancelDiscovery();
						
						if(devices.size() > 1){
							return ;
						}
					}
					
					//发起连接
					mBluetoothService.startConnect(device);
				}else{
					mOnBluetoothListener.BluetoothFound(device);
				}
			}
			//扫描结束
			else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
				
				mOnBluetoothListener.BluetoothDevice(devices);
				
				LogUtils.d(TAG, "蓝牙扫描结束");
			}
			}
	};
	
	/**
	 * 发送数据
	 * @param str
	 */
	public void write(String str){
		mBluetoothService.pushs.push(str);
	}
	
	
	public void sendMessage(String str){
		mBluetoothService.sendMessage(str.getBytes());
	}
	
	
	public boolean isEnabled(){
		return mBluetoothAdapter.isEnabled();
	}


	/**
	 * 蓝牙管理器参数配置
	 * @author sunxu
	 *
	 */
	public static class Builder {
		private Context mContext;
		private BluetoothManage mBluetoothManage;
		
		private String charsetName = null;
		//指定设备名称
		private String DEVICE_NAME = "";
		//连接单一设备或多个设备
		private boolean isSingle = false;
		//是否打印日志
		private boolean isDebug = true;
		//蓝牙状态监听
		private OnBluetoothListener mOnBluetoothListener;
		//发现设备时是否主动连接
		private boolean isAutomaticConnection = false;
		
		public Builder(Context context) {
			mContext = context;
		}
		
		/**
		 * 需要按设备名扫描时设置此参数
		 * @param deviceName
		 * @return
		 */
		public Builder DeviceName(String deviceName){
			DEVICE_NAME = deviceName;
			return this;
		}
		
		/**
		 * true 连接发现的第一个设备结束扫描，false 连接发现的全部设备
		 * @param isSingle
		 * @return
		 */
		public Builder isSingle(boolean isSingle){
			this.isSingle = isSingle;
			return this;
		}
		
		public Builder charsetName(String charsetName)
		{
			this.charsetName = charsetName;
			return this;
		}
		/**
		 * 发现设备时是否需要自动连接
		 * @param isAutomaticConnection
		 * @return
		 */
		public Builder isAutomaticConnection(boolean isAutomaticConnection){
			this.isAutomaticConnection = isAutomaticConnection;
			return this;
		}
		
		/**
		 * true 输出Log
		 * @param isPrintLog
		 * @return
		 */
		public Builder isDebug(boolean isDebug){
			this.isDebug = isDebug;
			return this;
		}
		
		/**
		 * 注册状态监听
		 * @param listener
		 * @return
		 */
		public Builder OnBluetoothListener(OnBluetoothListener listener){
			this.mOnBluetoothListener = listener;
			return this;
		}
		
		public BluetoothManage build(){
			BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
			//设备不支持蓝牙时返回null
			if(adapter == null){
				return null;
			}
			return new BluetoothManage(this);
		};
	}
	
	
	//以下为数据转换的一些函数
		//判断单一字符是否符合16进制格式 
		private boolean CharInRange(char c)
				{
					boolean result = false;
					if (c >= '0' && c <= '9')
						result = true;
					if (c >= 'a' && c <= 'f')
						result = true;
					if  (c >= 'A' && c <= 'F')
						result = true;		
					return result;
				}
				//string 转 byte。。结果是通过一系列转化转换成string对应的十进制数值再转换成byte
				private byte StrToByte(String s)
				{
					return Integer.valueOf(String.valueOf(Integer.parseInt(s, 16))).byteValue();
				}
				//发送16进制数
				private byte[] getHexData(String SData) 
				{	
					
					int datalen = SData.getBytes().length;//输入字符的长度
					int bytelen = 0;
					if ((datalen % 2)==0)
						bytelen = (int)(datalen / 2);//字节长度=字符串长度除以2
					else
						bytelen = (int)(datalen / 2) + 1;//不能整除，则bytelen+1
					
					byte[] sBuffer = new byte[bytelen];//定义byte[]缓存
					int i = 0, j = 0;
					while (i < datalen)//第一层循环，字符串长度增
					{
						while (i >= 0 && (!CharInRange(SData.charAt(i))))//条件i>=0并且为false，则执行i++
							i++;
						
						if (((i + 1) >= datalen) || (!CharInRange(SData.charAt(i + 1))))//如果i+1>=datalen或者false即字符不在16进制范围，则执行
						{
							sBuffer[j] = StrToByte(String.valueOf(SData.charAt(i)));//不执行i+1字符的转化
							j++;
						} 
						else
						{
							sBuffer[j] = StrToByte(SData.substring(i, i + 2));
							j++;
						}
						
						i+= 2;
					}
					
					return sBuffer;
				}
		
		
}
