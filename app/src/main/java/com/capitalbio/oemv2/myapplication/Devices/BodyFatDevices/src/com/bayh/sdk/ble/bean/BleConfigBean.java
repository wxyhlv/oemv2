package com.capitalbio.oemv2.myapplication.Devices.BodyFatDevices.src.com.bayh.sdk.ble.bean;

import java.util.UUID;
/**
 * 手环名称
 */
public class BleConfigBean {

	/**
	 * 体脂称名称
	 */
	public static final String SH_DEVICE_NAME="L28_";

	/**
	 * 设备类型：手环
	 */
	public static final String TZC_DEVICE_NAME="eBody";

	/**
	 * 设备类型：体脂称
	 */
	public final static int SH_DEVICE_TYPE = 1;

	/**
	 * 手环Service UUID
	 */
	public final static int TZC_DEVICE_TYPE = 2;

	/**
	 * 手环读取通道UUID
	 */
	public final static UUID SH_UUID = UUID.fromString("00006006-0000-1000-8000-00805f9b34fb");//��UUID

	/**
	 * 手环写入通道UUID
	 */
	public final static UUID SH_READ_UUID = UUID
			.fromString("00008001-0000-1000-8000-00805f9b34fb");//��

	/**
	 * 体脂称Service UUID
	 */
	public final static UUID SH_WRITE_UUID = UUID
			.fromString("00008002-0000-1000-8000-00805f9b34fb");//д

	/**
	 * 体脂称写入通道 UUID
	 */
	public final static UUID TZC_UUID = UUID
			.fromString("0000fff0-0000-1000-8000-00805f9b34fb");

	/**
	 * 体脂称读取通道UUID
	 */
	public final static UUID TZC_WRITE_UUID = UUID
			.fromString("0000fff3-0000-1000-8000-00805f9b34fb");//д

	/**
	 * 设备名称
	 */
	public final static UUID TZC_READ_UUID = UUID
			.fromString("0000fff4-0000-1000-8000-00805f9b34fb");//��


	/**
	 * 设备类型
	 */
	private String deviceName;

	/**
	 * 设备服务UUID
	 */
	private int type = -1;

	/**
	 * 读取通道UUID
	 */
	private UUID serviceUUID;
	/**
	 * 读取通道UUID
	 */
	private UUID readUUID;

	/**
	 * 读取通道UUID
	 */
	private UUID writeUUID;
	

	public BleConfigBean() {
		super();
	}

	public BleConfigBean(String deviceName, int type, UUID serviceUUID,
			UUID readUUID, UUID writeUUID) {
		super();
		this.deviceName = deviceName;
		this.type = type;
		this.serviceUUID = serviceUUID;
		this.readUUID = readUUID;
		this.writeUUID = writeUUID;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public UUID getServiceUUID() {
		return serviceUUID;
	}

	public void setServiceUUID(UUID serviceUUID) {
		this.serviceUUID = serviceUUID;
	}

	public UUID getReadUUID() {
		return readUUID;
	}

	public void setReadUUID(UUID readUUID) {
		this.readUUID = readUUID;
	}

	public UUID getWriteUUID() {
		return writeUUID;
	}

	public void setWriteUUID(UUID writeUUID) {
		this.writeUUID = writeUUID;
	}

	
}
