package com.capitalbio.oemv2.myapplication.Devices.BodyFatDevices.src.com.bayh.sdk.ble.utils;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

public class BtscanUtil {
	private BluetoothAdapter mBluetoothAdapter;
	private String TAG = BtscanUtil.class.getSimpleName();
	private boolean mScanning;
	private Context context;
	private static final int REQUEST_ENABLE_BT = 1;
	    // 10���ֹͣ��������.
	private static final long SCAN_PERIOD = 10000;
    private String address = null;
    private String name = null;
    private BluetoothOpenReceiver bluetoothOpenReceiver;
    //ACTION_STATE_CHANGED
    public BtscanUtil(Context context,String name) {
			this.context = context;
			this.name = name;
			bluetoothOpenReceiver = new BluetoothOpenReceiver();
			init();
		}
     @SuppressLint("NewApi")
    private BluetoothAdapter.LeScanCallback  mLeScanCallback = new BluetoothAdapter.LeScanCallback() {

        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
        	Log.i(TAG, "SCAN  �ѵ��豸���Ϊ" + device.getName());
        
        	if(name.equals(device.getName())){
            	//����
        		
        		Log.i(TAG, "SCAN stop" + device.getName());
        		scanLeDevice(false);
        		address = device.getAddress();
        		Log.i(TAG, address);
        
            }else{
            	Log.i(TAG, device.getAddress());
            }
        }
    };

    /**
     * ��ʼ������Ƿ�֧������
     * û�п���������������
     */
    @SuppressLint("NewApi")
	public void init(){
		   // ��鵱ǰ�ֻ��Ƿ�֧��ble ����,���֧���˳�����
	        if (!context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
	        Toast.makeText(context, "���豸��֧��ble����", Toast.LENGTH_SHORT).show();
	        return;
	        }

	        // ��ʼ�� Bluetooth adapter, ͨ�������������õ�һ���ο�����������(API����������android4.3�����ϺͰ汾)
	        final BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
	        mBluetoothAdapter = bluetoothManager.getAdapter();

	        // ����豸���Ƿ�֧������
	        if (mBluetoothAdapter == null) {
	            Toast.makeText(context, "���豸��֧������", Toast.LENGTH_SHORT).show();
	            return;
	      }
	  
		     if (mBluetoothAdapter.isEnabled()) {
				scanLeDevice(true);
			} else {
				
				mBluetoothAdapter.enable();
				IntentFilter intentfilter = new IntentFilter();
				intentfilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
				registBroadCast(intentfilter);
			}
	    }
    
    public void  registBroadCast(IntentFilter intentfilter){
         context.registerReceiver(bluetoothOpenReceiver, intentfilter);
    }

    /**
     * ɨ�������豸
     * @param enable
     * @return
     */

    @SuppressLint("NewApi")
	public String scanLeDevice(final boolean enable) {

        if (enable) {
            Thread thread = new Thread(new Runnable() {
                @SuppressLint("NewApi")
				@Override
                public void run() {
                    mScanning = true;
                    mBluetoothAdapter.startLeScan(mLeScanCallback);
                    Log.i(TAG, "SCAN--START");
                }
            });
            thread.start();

        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
            Log.i(TAG, "stopBT");
        }
        return address;

    }

    class BluetoothOpenReceiver extends BroadcastReceiver {
    	
    	@Override
		public void onReceive(Context context, Intent intent) {

			if (intent.getAction()
					.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
				int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
						BluetoothAdapter.ERROR);
				if (mBluetoothAdapter.STATE_ON == state) {
					Log.i(TAG, "RECEIVER--���������ɹ�");
					scanLeDevice(true);
				}
			}

		}
    }
}
