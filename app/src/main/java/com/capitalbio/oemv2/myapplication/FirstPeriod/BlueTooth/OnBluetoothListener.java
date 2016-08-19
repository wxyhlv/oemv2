package com.capitalbio.oemv2.myapplication.FirstPeriod.BlueTooth;

import android.bluetooth.BluetoothDevice;

import com.capitalbio.oemv2.myapplication.FirstPeriod.AircatProtocolParse.AirCatBean;

import java.util.List;

/**
 * 蓝牙状态接口
 * @author sunxu
 *
 */
public interface OnBluetoothListener {
	
	/**
	 * 蓝牙开关状态
	 * @param state {@link BluetoothState}
	 */
	void BluetoothChanged(int state);
	
	/**
	 * 蓝牙当前连接状态
	 * @param device 当前设备
	 * @param state {@link BluetoothState}
	 */
	void BluetoothConnected(BluetoothDevice device, int state);
	
	/**
	 * 扫描完成后返回发现的蓝牙设备信息
	 * @param devices 设备集合
	 */
	void BluetoothDevice(List<BluetoothDevice> devices);
	
	/**
	 * 发现蓝牙设备
	 * @param device
	 */
	void BluetoothFound(BluetoothDevice device);
	/**
	 * 接收蓝牙传来的数据
	 * @param data 接收到数据
	 */
	void BluetoothData(AirCatBean data);
}
