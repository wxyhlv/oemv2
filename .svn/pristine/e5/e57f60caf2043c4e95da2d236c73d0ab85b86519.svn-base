//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.capitalbio.oemv2.myapplication.BraceleteLib;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.capitalbio.oemv2.myapplication.Base.BaseCommand;
import com.capitalbio.oemv2.myapplication.Base.IConnectionStateChange;
import com.capitalbio.oemv2.myapplication.Base.IDeviceSearchCallBack;
import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.Const.Const;
import com.capitalbio.oemv2.myapplication.Devices.BloodPressDevice.BloodPressConnectCommand;
import com.capitalbio.oemv2.myapplication.Devices.BloodPressDevice.BloodPressGattCallBack;
import com.capitalbio.oemv2.myapplication.Devices.BodyFatDevices.src.com.bayh.bluetooth.fatScale.command.FatScaleConnectCommand;
import com.capitalbio.oemv2.myapplication.Devices.Bracelete.BraceleteCommand.BraceleteCommand;
import com.capitalbio.oemv2.myapplication.Devices.Bracelete.ISportDataCallback;
import com.capitalbio.oemv2.myapplication.Devices.Bracelete.OemBackgroundService;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;
import com.capitalbio.oemv2.myapplication.Utils.PreferencesUtils;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingDeque;

import static com.capitalbio.oemv2.myapplication.Utils.OemLog.log;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class BraceleteDevices {

    //提醒设置
    public static final byte MONDAY_REMINDER = 0X01;
    public static final byte TUESDAY_REMINDER = 0X02;
    public static final byte WEDNESDAY_REMINDER = 0X04;
    public static final byte THURDAY_REMINDER = 0X08;
    public static final byte FRIDAY_REMINDER = 0X10;
    public static final byte SATURDAY_REMINDER = 0X20;
    public static final byte SUNDAY_REMINDER = 0X40;

    public static final String ACTION_DATA_AVAILABLE = "cn.appscomm.pedometer.service.ACTION_DATA_AVAILABLE";
    public static final String ACTION_GATT_CONNECTED = "cn.appscomm.pedometer.service.ACTION_GATT_CONNECTED";
    public static final String ACTION_GATT_DISCONNECTED = "cn.appscomm.pedometer.service.ACTION_GATT_DISCONNECTED";
    public static final String ACTION_GATT_SERVICES_DISCOVERED = "cn.appscomm.pedometer.service.ACTION_GATT_SERVICES_DISCOVERED";
    public static final String ACTION_GATT_SERVICES_TIMEOUT = "cn.appscomm.pedometer.service.ACTION_GATT_TIMEOUT";
    public static final String EXTRA_DATA = "cn.appscomm.pedometer.service.EXTRA_DATA";
    public static final String ACTION_GATT_MACADDR = "cn.appscomm.pedometer.service.ACTION_GATT_MACADDR";
    public static final String EXTRA_DN_DATA = "cn.appscomm.pedometer.service.EXTRA_DN_DATA";
    public static final String EXTRA_MAC_DATA = "cn.appscomm.pedometer.service.EXTRA_MAC_DATA";
    private static final int STATE_CONNECTED = 2;
    private static final int STATE_CONNECTING = 1;
    private static final int STATE_DISCONNECTED = 0;
    private static final boolean NEED_BOND_FIRST = false;
    public static boolean isConnected = false;
    public static boolean isBloodPressConnected = false;
    private static boolean isServiceDisvered = false;
    private static boolean isEnable_time = false;
    private static long lastConnectTime;
    private boolean isPaired = false;
    private static final String TAG = "BraceleteDevices";
    private static final UUID UUID_CHARACTERISTIC_1 = UUID.fromString("00008001-0000-1000-8000-00805f9b34fb");
    private static final UUID UUID_CHARACTERISTIC_2 = UUID.fromString("00008002-0000-1000-8000-00805f9b34fb");
    private static final UUID UUID_CHARACTERISTIC_2_CONFIG_DESCRIPTOR = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    private static final UUID UUID_SERVICE = UUID.fromString("00006006-0000-1000-8000-00805f9b34fb");
    private BluetoothAdapter mBluetoothAdapter = null;
    private LocalBroadcastManager localBroadcastManager = null;
    private Timer timer1 = null;
    public static String devNamePrefix;
    private static Pedometer_TypeInfo.Pedometer_Type pType;
    private final String protocal_ver = "1.1";
    private BluetoothGatt mBraceleteBluetoothGatt = null;
    private BluetoothGatt mBloodPressBluetoothGatt = null;
    private BluetoothGatt mBodyFatBluetoothGatt = null;

    private BluetoothManager mBluetoothManager = null;
    private static String mDeviceAddress;
    private static String mBloodPressAddress;
    private static String mBodyFatAddress;
    private static String REG_SN;
    private final int MAXTIMEOUT = 12;
    private int timeoutCount = 0;
    private static int timeOutCount2;
    private static boolean SendTimeOut;
    private static boolean IsOSKikat;
    private byte[] lastPacket = null;
    private int connectTimes = 0;
    private static BluetoothDevice bluetoothdevice;
    private static BluetoothDevice bloodPressDevice;
    private static BluetoothDevice bodyFatDevice;
    private static BluetoothDevice lastpairedble;
    private Context mContext;
    private static BraceleteDevices Instance = null;
    private int discoverTimes = 0;
    private CommandDataParser commandDataParser = new CommandDataParser();
    private boolean isBloodPressConnecting = false;
    //services回调Handller
    private Handler servicesCallBackHandler;

    private IConnectionStateChange connectionStateChange;
    private IDeviceSearchCallBack deviceSearchCallBack;
    private Queue<BaseCommand> commandList = new LinkedBlockingDeque<>();

    private BloodPressGattCallBack bloodPressGattCallBack = new BloodPressGattCallBack();
    private boolean isBloodPressMeasuring = false;
    private boolean isBodyFatMeasuring = false;
    private BloodPressConnectCommand bloodPressConnectCommand = new BloodPressConnectCommand(null);
    private FatScaleConnectCommand bodyFatConnectCommand = new FatScaleConnectCommand();

    private Object bloodPressConnectLock = new Object();
    private Object bodyfatConnectLock = new Object();

    //默认的血压计设备地址
    private String defaultBloodPressAddress;
    //默认的体脂称设备地址
    private String defaultBodyFatAddress;




    public static BraceleteDevices getInstance() {

        if (Instance == null) {
            Instance = new BraceleteDevices(MyApplication.getInstance());
        }

        return Instance;
    }


    private BroadcastReceiver mbroadRec = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.i("BraceleteDevices", "BroadcastReceiver.action=" + action);
            Log.i("new-test", "BroadcastReceiver.action=" + action);
        }
    };
    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        private void confirmByWriting0x03ToCharacteristic2() {
            if(BraceleteDevices.this.mBraceleteBluetoothGatt != null) {
                BluetoothGattCharacteristic bluetoothgattcharacteristic = BraceleteDevices.this.mBraceleteBluetoothGatt.getService(BraceleteDevices.UUID_SERVICE).getCharacteristic(BraceleteDevices.UUID_CHARACTERISTIC_2);
                byte[] abyte0 = new byte[]{(byte)3};
                bluetoothgattcharacteristic.setValue(abyte0);
                BraceleteDevices.this.mBraceleteBluetoothGatt.writeCharacteristic(bluetoothgattcharacteristic);
            }
        }

        private void enableNotificationForCharacteristic2() {
            if(BraceleteDevices.this.mBraceleteBluetoothGatt != null) {
                try {
                    BluetoothGattCharacteristic bluetoothgattcharacteristic = BraceleteDevices.this.mBraceleteBluetoothGatt.getService(BraceleteDevices.UUID_SERVICE).getCharacteristic(BraceleteDevices.UUID_CHARACTERISTIC_2);
                    BraceleteDevices.this.mBraceleteBluetoothGatt.setCharacteristicNotification(bluetoothgattcharacteristic, true);
                    BluetoothGattDescriptor bluetoothgattdescriptor = bluetoothgattcharacteristic.getDescriptor(BraceleteDevices.UUID_CHARACTERISTIC_2_CONFIG_DESCRIPTOR);
                    bluetoothgattdescriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                    BraceleteDevices.this.mBraceleteBluetoothGatt.writeDescriptor(bluetoothgattdescriptor);
                } catch (Exception var3) {
                    ;
                }

            }
        }

        public void onCharacteristicChanged(BluetoothGatt bluetoothgatt, BluetoothGattCharacteristic bluetoothgattcharacteristic) {
            OemLog.log(TAG, "onCharacteristicChanged" + " value is " + Arrays.toString(bluetoothgattcharacteristic.getValue()));
//            servicesCallBackHandler.sendEmptyMessage(Const.BRACELETE_COMMAND_EXECUTE_STATUS_COMPLETE);
            if(BraceleteDevices.this.mBraceleteBluetoothGatt != null) {
                if(BraceleteDevices.UUID_CHARACTERISTIC_2.equals(bluetoothgattcharacteristic.getUuid())) {
                    if(BraceleteDevices.this.isUseNotifiMode()) {
                        byte[] bluetoothgattcharacteristic1 = bluetoothgattcharacteristic.getValue();
                        if(bluetoothgattcharacteristic1 != null && bluetoothgattcharacteristic1.length > 1) {
                            BraceleteDevices.timeOutCount2 = 0;
                        }

                        BraceleteDevices.this.timeoutCount = 0;
                        BraceleteDevices.isEnable_time = false;
                        if(bluetoothgattcharacteristic1 != null && bluetoothgattcharacteristic1.length > 5 && bluetoothgattcharacteristic1[0] == 110 && bluetoothgattcharacteristic1[1] == 1 && (bluetoothgattcharacteristic1[2] == 5 || bluetoothgattcharacteristic1[2] == 19)) {
                            BraceleteDevices.isEnable_time = true;
                            OemLog.log("BraceleteDevices", "current in transmission Mode, wait...." + " byte is " + Arrays.toString(bluetoothgattcharacteristic1));
                        }

                        commandDataParser.pasrseData(bluetoothgattcharacteristic1);
                        
                        if(BraceleteDevices.this.lastPacket != null) {
                            byte[] newbyte = new byte[20 + bluetoothgattcharacteristic1.length];
                            System.arraycopy(BraceleteDevices.this.lastPacket, 0, newbyte, 0, 20);
                            System.arraycopy(bluetoothgattcharacteristic1, 0, newbyte, 20, bluetoothgattcharacteristic1.length);
                            BraceleteDevices.this.broadcastUpdate("cn.appscomm.pedometer.service.ACTION_DATA_AVAILABLE", newbyte);
                            BraceleteDevices.this.lastPacket = null;
                        } else {
                            if(bluetoothgattcharacteristic1 != null & bluetoothgattcharacteristic1.length == 20) {
                                if(bluetoothgattcharacteristic1[0] == 110 && bluetoothgattcharacteristic1[1] == 1 && bluetoothgattcharacteristic1[2] == 4) {
                                    BraceleteDevices.this.lastPacket = new byte[20];
                                    System.arraycopy(bluetoothgattcharacteristic1, 0, BraceleteDevices.this.lastPacket, 0, 20);
                                    return;
                                }
                            } else {
                                BraceleteDevices.this.lastPacket = null;
                            }

                            //数据解析和存储工具类
                            
                            BraceleteDevices.this.broadcastUpdate("cn.appscomm.pedometer.service.ACTION_DATA_AVAILABLE", bluetoothgattcharacteristic1);
                        }
                    } else {
                        BluetoothGattCharacteristic bluetoothgattcharacteristic11 = BraceleteDevices.this.mBraceleteBluetoothGatt.getService(BraceleteDevices.UUID_SERVICE).getCharacteristic(BraceleteDevices.UUID_CHARACTERISTIC_1);
                        BraceleteDevices.this.mBraceleteBluetoothGatt.readCharacteristic(bluetoothgattcharacteristic11);
                    }
                }

            }
        }

        public void onCharacteristicRead(BluetoothGatt bluetoothgatt, BluetoothGattCharacteristic bluetoothgattcharacteristic, int i) {
            OemLog.log(TAG, "onCharacteristicRead" + " value is " + Arrays.toString(bluetoothgattcharacteristic.getValue()));
//            servicesCallBackHandler.sendEmptyMessage(Const.BRACELETE_COMMAND_EXECUTE_STATUS_COMPLETE);
            if(BraceleteDevices.this.mBraceleteBluetoothGatt != null) {
                if(!BraceleteDevices.this.isUseNotifiMode()) {
                    byte[] abyte0 = bluetoothgattcharacteristic.getValue();
                    if(BraceleteDevices.UUID_CHARACTERISTIC_1.equals(bluetoothgattcharacteristic.getUuid())) {
                        BraceleteDevices.this.timeoutCount = 0;
                        BraceleteDevices.this.broadcastUpdate("cn.appscomm.pedometer.service.ACTION_DATA_AVAILABLE", abyte0);
                    }
                }

            }
        }

        public void onCharacteristicWrite(BluetoothGatt bluetoothgatt, BluetoothGattCharacteristic bluetoothgattcharacteristic, int i) {
            OemLog.log(TAG, "onCharacteristicWrite" + " value is " + Arrays.toString(bluetoothgattcharacteristic.getValue()));
//            servicesCallBackHandler.sendEmptyMessage(Const.BRACELETE_COMMAND_EXECUTE_STATUS_COMPLETE);
            if(BraceleteDevices.this.mBraceleteBluetoothGatt != null) {
                if(!BraceleteDevices.this.isUseNotifiMode() && BraceleteDevices.UUID_CHARACTERISTIC_1.equals(bluetoothgattcharacteristic.getUuid())) {
                    BraceleteDevices.this.timeoutCount = 0;
                    this.confirmByWriting0x03ToCharacteristic2();
                }
            }
        }

        @SuppressLint({"NewApi"})
        public void onConnectionStateChange(BluetoothGatt bluetoothgatt, int status, int newState) {
            OemLog.log("BraceleteDevices", "connect state change..status:" + status + " newState:" + newState);
            if(newState == 0) {
                PreferencesUtils.putBoolean(mContext, "connect_state", false);
                PreferencesUtils.putBoolean(mContext, "pre_connect_state", false);
                PreferencesUtils.putBoolean(mContext, "bracelete_init_complete", false);
                commandList.clear();
                servicesCallBackHandler.sendEmptyMessage(Const.BRACELETE_COMMAND_EXECUTE_STATUS_DISCONNECTED);
                BraceleteDevices.isConnected = false;
                BraceleteDevices.isServiceDisvered = false;
                BraceleteDevices.this.timeoutCount = -2;
                if(BraceleteDevices.this.mBraceleteBluetoothGatt != null) {
                    try {
                        BraceleteDevices.this.mBraceleteBluetoothGatt.disconnect();
                        BraceleteDevices.this.mBraceleteBluetoothGatt.close();
                        BraceleteDevices.this.mBraceleteBluetoothGatt = null;
                    } catch (Exception var5) {
                        ;
                    }
                }

                BraceleteDevices.this.broadcastUpdate("cn.appscomm.pedometer.service.ACTION_GATT_DISCONNECTED", (byte[])null);
                //给ui层的连接状态回调
                if (connectionStateChange != null) {
                    OemLog.log(TAG, "callback to ui state is " +  BluetoothProfile.STATE_DISCONNECTED);
                    connectionStateChange.onConnectStateChange(Const.OEM_DEVICES_TYPE_BRACELETE, BluetoothProfile.STATE_DISCONNECTED);
                }
                OemLog.log("BraceleteDevices", "Disconnected from GATT server.");
            } else if(newState == 2 && status == 0) {
                BraceleteDevices.isConnected = true;
                BraceleteDevices.isServiceDisvered = false;
                if(!BraceleteDevices.IsOSKikat) {
                    BraceleteDevices.this.timeoutCount = -10;
                } else {
                    BraceleteDevices.this.timeoutCount = -8;
                }
                BraceleteDevices.this.broadcastUpdate("cn.appscomm.pedometer.service.ACTION_GATT_CONNECTED", (byte[]) null);
                OemLog.log(TAG, "开始post消息之前...");
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        OemLog.log(TAG, "开始发现服务前...mBraceleteBluetoothGatt is null " + (mBraceleteBluetoothGatt == null));
                        if (mBraceleteBluetoothGatt != null) {
                            mBraceleteBluetoothGatt.discoverServices();
                        }
                    }
                }, 200l);
                //给ui层的回调
                if (connectionStateChange != null) {
                    OemLog.log(TAG, "callback to ui state is " +  BluetoothProfile.STATE_CONNECTED);
                    connectionStateChange.onConnectStateChange(Const.OEM_DEVICES_TYPE_BRACELETE, BluetoothProfile.STATE_CONNECTED);
                }

            } else if(status == 133 && newState == 2 && BraceleteDevices.this.connectTimes < 4) {
                OemLog.log("BraceleteDevices", " repeat connect.." + BraceleteDevices.this.connectTimes);
                BraceleteDevices.this.connectTimes = BraceleteDevices.this.connectTimes + 1;
                BraceleteDevices.this.mHandler.postDelayed(new Runnable() {
                    public void run() {
                        BraceleteDevices.this.connect2(BraceleteDevices.mDeviceAddress);
                    }
                }, 600l);
            }

        }

        public void onDescriptorRead(BluetoothGatt bluetoothgatt, BluetoothGattDescriptor bluetoothgattdescriptor, int i) {
            OemLog.log(TAG, "onDescriptorRead" + " value is " + Arrays.toString(bluetoothgattdescriptor.getValue()));
//            servicesCallBackHandler.sendEmptyMessage(Const.BRACELETE_COMMAND_EXECUTE_STATUS_COMPLETE);
            byte[] abyte0 = bluetoothgattdescriptor.getValue();
            if(BraceleteDevices.UUID_CHARACTERISTIC_2_CONFIG_DESCRIPTOR.equals(bluetoothgattdescriptor.getUuid())) {
                BraceleteDevices.isServiceDisvered = true;
                OemLog.log("BraceleteDevices", "charactertic2 found");
                PreferencesUtils.putBoolean(mContext, "connect_state", true);
                servicesCallBackHandler.sendEmptyMessage(Const.BRACELETE_COMMAND_EXECUTE_STATUS_COMPLETE);
                BraceleteDevices.this.mHandler.postDelayed(new Runnable() {
                    public void run() {
                        BraceleteDevices.this.broadcastUpdate("cn.appscomm.pedometer.service.ACTION_GATT_SERVICES_DISCOVERED", (byte[]) null);
                    }
                }, 300L);
            }
        }

        public void onDescriptorWrite(BluetoothGatt bluetoothgatt, BluetoothGattDescriptor bluetoothgattdescriptor, int i) {
            OemLog.log(TAG, "onDescriptorRead" + " value is " + Arrays.toString(bluetoothgattdescriptor.getValue()));
//            servicesCallBackHandler.sendEmptyMessage(Const.BRACELETE_COMMAND_EXECUTE_STATUS_COMPLETE);
            Object[] aobj = new Object[]{bluetoothgattdescriptor.getUuid(), Integer.valueOf(i)};
            OemLog.log("BraceleteDevices", ">>>> onDescriptorWrite: %s, %d" + aobj);
            bluetoothgatt.readDescriptor(bluetoothgattdescriptor);
        }

        public void onServicesDiscovered(BluetoothGatt bluetoothgatt, int i) {


            OemLog.log("BraceleteDevices", ">>>> onServicesDiscovered," + new Object[0]);
//            servicesCallBackHandler.sendEmptyMessage(Const.BRACELETE_COMMAND_EXECUTE_STATUS_COMPLETE);
            if(i == 0) {
                if(!BraceleteDevices.IsOSKikat) {
                    BraceleteDevices.this.timeoutCount = -6;
                } else {
                    BraceleteDevices.this.timeoutCount = -4;
                }

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        enableNotificationForCharacteristic2();
                    }
                }, 400l);


            }

        }
    };
    private Handler mHandler = new Handler() {

    };
    private BluetoothAdapter.LeScanCallback mLeScanCallback = new LeScanCallback() {
        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            OemLog.log(TAG, "onLeScan devices name is " + device.getName() + ", devices address is " + device.getAddress() + ", callback is " + deviceSearchCallBack + ", isBloodPressMeasure is " + isBloodPressMeasuring);
            if (deviceSearchCallBack != null) {
                deviceSearchCallBack.deviceSearchCallBack(device);
            }
//            servicesCallBackHandler.sendEmptyMessage(Const.BRACELETE_COMMAND_EXECUTE_STATUS_COMPLETE);
            try {
                String devname = device.getName();
                Log.i("BraceleteDevices", "device.getUuids=" + device.getUuids());
                Log.i("BraceleteDevices", "device.getdevice=" + device.getName());
                synchronized (bloodPressConnectLock) {
                    if("Bluetooth BP".equals(devname) && !isBloodPressMeasuring) {
                        OemLog.log("BraceleteDevices", "found blood press devices auto connect" + BraceleteDevices.mDeviceAddress);
                        defaultBloodPressAddress = PreferencesUtils.getString(mContext, "default_bloodpress_address", "");
                        //取默认的血压计设备进行连接
                        if (device.getAddress().equals(defaultBloodPressAddress)) {
                            MyApplication.getInstance().setIsDevicesMeasuring(true);
                            stopBleScan();
                            bloodPressConnectCommand.setTargetAddress(device.getAddress());
                            servicesCallBackHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    addCommandToQueue(bloodPressConnectCommand);
                                }
                            }, 200l);

                            isBloodPressMeasuring = true;
                            //通知有其他蓝牙设备发现
                            servicesCallBackHandler.sendEmptyMessage(OemBackgroundService.FOUND_OTHER_DEVICES_NOTIFY);
                        }
                    }
                }

                synchronized (bodyfatConnectLock) {
                    if ("eBody-Fat-Scale".equals(devname) && !isBodyFatMeasuring) {
                        //搜索到体脂称设备开始自动连接
                        OemLog.log(TAG, "found bodyfat devices and auto connect..." + ", defalut address is " + PreferencesUtils.getString(mContext, "default_bodyfat_address", "")
                                    + ", current devices is " + device.getAddress());
                        //取默认的体脂称地址进行连接
                        defaultBodyFatAddress = PreferencesUtils.getString(mContext, "default_bodyfat_address", "");
                        if (device.getAddress().equals(defaultBodyFatAddress)) {

                            MyApplication.getInstance().setIsDevicesMeasuring(true);
                            stopBleScan();
                            bodyFatConnectCommand.setBodyFatAddress(device.getAddress());
                            addCommandToQueue(bodyFatConnectCommand);
                            isBodyFatMeasuring = true;
                            //通知有其他蓝牙设备发现
                            servicesCallBackHandler.sendEmptyMessage(OemBackgroundService.FOUND_OTHER_DEVICES_NOTIFY);

                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    static {
        devNamePrefix = Pedometer_TypeInfo.Pedometer_NamePrefix[1];
        pType = Pedometer_TypeInfo.Pedometer_Type.L11;
        mDeviceAddress = "";
        REG_SN = "";
        timeOutCount2 = 0;
        SendTimeOut = true;
        IsOSKikat = false;
        bluetoothdevice = null;
        lastpairedble = null;
    }

    private BraceleteDevices(Context context) {
        mContext = context;
        OemLog.log("BraceleteDevices", "service create");
        this.localBroadcastManager = LocalBroadcastManager.getInstance(mContext.getApplicationContext());
        if(VERSION.SDK_INT >= 19) {
            IsOSKikat = true;
        } else {
            IsOSKikat = false;
        }

        this.timeoutCount = 0;
        if(this.timer1 == null) {
            this.timer1 = new Timer();
            this.timer1.schedule(new TimerTask() {
                public void run() {
                    if(!BraceleteDevices.isEnable_time) {
                        BraceleteDevices.this.timeoutCount = 0;
                    } else {
                        BraceleteDevices.this.timeoutCount = BraceleteDevices.this.timeoutCount + 1;
                        if(BraceleteDevices.this.timeoutCount > 12) {
                            BraceleteDevices.isEnable_time = false;
                            BraceleteDevices.timeOutCount2 = BraceleteDevices.timeOutCount2 + 1;
                            OemLog.log("BraceleteDevices", "...................TIMEOUT....................., timoutCount2:" + BraceleteDevices.timeOutCount2);
                            servicesCallBackHandler.sendEmptyMessage(Const.BRACELETE_COMMAND_EXECUTE_STATUS_COMPLETE);
                            if(BraceleteDevices.SendTimeOut) {
                                BraceleteDevices.this.timeoutCount = -2;
                                BraceleteDevices.this.broadcastUpdate("cn.appscomm.pedometer.service.ACTION_GATT_TIMEOUT", (byte[])null);
                            } else {
                                BraceleteDevices.this.timeoutCount = -2;
                                BraceleteDevices.this.broadcastUpdate("cn.appscomm.pedometer.service.ACTION_GATT_DISCONNECTED", (byte[])null);
                            }

                            if(BraceleteDevices.timeOutCount2 > 1) {
                                BraceleteDevices.timeOutCount2 = 0;
                                Log.e("BraceleteDevices", ">>>>>>>>>>>>>>>>>>>>>>>>>timeOutCount2 over threshold " + BraceleteDevices.timeOutCount2);
                                BraceleteDevices.isConnected = false;
                                BraceleteDevices.isServiceDisvered = false;
                                if(BraceleteDevices.this.mBraceleteBluetoothGatt != null) {
                                    try {
                                        BraceleteDevices.this.mBraceleteBluetoothGatt.disconnect();
                                        BraceleteDevices.this.mBraceleteBluetoothGatt.close();
                                        BraceleteDevices.this.mBraceleteBluetoothGatt = null;
                                    } catch (Exception var2) {

                                    }
                                }
                            }
                        }

                    }
                }
            }, 0L, 500L);
        }

        this.mBluetoothManager = (BluetoothManager)mContext.getSystemService("bluetooth");
        if(this.mBluetoothManager != null) {
            this.mBluetoothAdapter = this.mBluetoothManager.getAdapter();
        }

        IntentFilter intent = new IntentFilter();
        intent.addAction("android.bluetooth.device.action.BOND_STATE_CHANGED");
        intent.addAction("android.bluetooth.device.action.ACL_DISCONNECTED");
        intent.addAction("android.bluetooth.device.action.ACL_CONNECTED");
        intent.setPriority(999);
        mContext.registerReceiver(this.mbroadRec, intent);
    }

    public String getProtocal_ver() {
        return "1.1";
    }

    public Pedometer_TypeInfo.Pedometer_Type getpType() {
        return pType;
    }

    public static void setPedometerType(Pedometer_TypeInfo.Pedometer_Type aType) {
        pType = aType;
        ProtocolParser.setPedometer_typeInfo(aType);
        devNamePrefix = Pedometer_TypeInfo.Pedometer_NamePrefix[aType.ordinal()];
    }

    private void broadcastUpdate(String s, byte[] abyte0) {
        Intent intent = new Intent(s);
        if(abyte0 != null) {
            intent.putExtra("cn.appscomm.pedometer.service.EXTRA_DATA", abyte0);
        }

        this.localBroadcastManager.sendBroadcast(intent);
    }

    public static IntentFilter makeGattUpdateIntentFilter() {
        IntentFilter intentfilter = new IntentFilter();
        intentfilter.addAction("cn.appscomm.pedometer.service.ACTION_GATT_CONNECTED");
        intentfilter.addAction("cn.appscomm.pedometer.service.ACTION_GATT_DISCONNECTED");
        intentfilter.addAction("cn.appscomm.pedometer.service.ACTION_GATT_SERVICES_DISCOVERED");
        intentfilter.addAction("cn.appscomm.pedometer.service.ACTION_DATA_AVAILABLE");
        intentfilter.addAction("cn.appscomm.pedometer.service.ACTION_GATT_MACADDR");
        intentfilter.addAction("cn.appscomm.pedometer.service.ACTION_GATT_TIMEOUT");
        return intentfilter;
    }

    private void syncTimeToDevice() {
        Calendar calendar = Calendar.getInstance();
        int i = calendar.get(1);
        byte[] abyte0 = new byte[]{(byte)110, (byte)1, (byte)21, (byte)i, (byte)(i >> 8), (byte)(1 + calendar.get(2)), (byte)calendar.get(5), (byte)calendar.get(11), (byte)calendar.get(12), (byte)calendar.get(13), (byte)-113};
        this.writeDataToCharateristic1(abyte0);
    }

    @TargetApi(19)
    private void writeDataToCharateristic1(byte[] abyte0) {
        if(abyte0 != null) {
            if(this.mBraceleteBluetoothGatt != null) {
                isEnable_time = true;
                this.timeoutCount = 0;
                OemLog.log("test-data", "send: " + ProtocolParser.binaryToHexString(abyte0));
                BluetoothGattCharacteristic bluetoothgattcharacteristic = null;

                try {
                    bluetoothgattcharacteristic = this.mBraceleteBluetoothGatt.getService(UUID_SERVICE).getCharacteristic(UUID_CHARACTERISTIC_1);
                    bluetoothgattcharacteristic.setValue(abyte0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    this.mBraceleteBluetoothGatt.writeCharacteristic(bluetoothgattcharacteristic);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void close() {
    }

    public void disconnect() {
        OemLog.log(TAG, "bracelete disconnect...");
        isConnected = false;
        isServiceDisvered = false;
        if(this.timer1 != null) {
            this.timeoutCount = 0;
            timeOutCount2 = 0;
            isEnable_time = false;
        }

        if(this.mBraceleteBluetoothGatt != null) {
            try {
                this.mBraceleteBluetoothGatt.disconnect();
                this.mBraceleteBluetoothGatt.close();
                this.mBraceleteBluetoothGatt = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void real_close() {
        isConnected = false;
        isServiceDisvered = false;
        if(this.timer1 != null) {
            this.timer1.cancel();
            this.timer1 = null;
        }

        if(this.mBraceleteBluetoothGatt != null) {
            try {
                this.mBraceleteBluetoothGatt.disconnect();
                this.mBraceleteBluetoothGatt.close();
                this.mBraceleteBluetoothGatt = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void ScanConnect() {
    }

    @SuppressLint({"NewApi"})
    public boolean connect(String macAddr, String dnNO) {
        if(macAddr != null && !"".equals(macAddr)) {
            this.connectTimes = 1;
            isConnected = PreferencesUtils.getBoolean(mContext, "connect_state", false);
            if(isConnected && macAddr.equals(mDeviceAddress) && mBluetoothAdapter.isEnabled()) {
                this.broadcastUpdate("cn.appscomm.pedometer.service.ACTION_GATT_SERVICES_DISCOVERED", (byte[]) null);
                OemLog.log("BraceleteDevices", "already connected&paired... Not Need reconnect");
                return true;
            } else {
                this.mBluetoothManager = (BluetoothManager)mContext.getSystemService(Context.BLUETOOTH_SERVICE);
                if(this.mBluetoothManager != null) {
                    this.mBluetoothAdapter = this.mBluetoothManager.getAdapter();
                }

                mDeviceAddress = macAddr;
                boolean flag = true;
                Log.i("BraceleteDevices", "connect(final String address)=" + macAddr);
                if(this.mBluetoothAdapter != null && macAddr != null) {
                    if(this.mBraceleteBluetoothGatt != null) {
                        try {
                            if (this.mBraceleteBluetoothGatt != null) {
                                this.mBraceleteBluetoothGatt.disconnect();
                                this.mBraceleteBluetoothGatt.close();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            Thread.sleep(500L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        this.mBraceleteBluetoothGatt = null;
                    }

                    int icount = 2;
                    bluetoothdevice = null;

                    while(true) {
                        bluetoothdevice = this.mBluetoothAdapter.getRemoteDevice(macAddr);
                        --icount;
                        if(icount < 0 || bluetoothdevice != null) {
                            if(bluetoothdevice == null) {
                                flag = false;
                            } else {
                                if(!IsOSKikat) {
                                    this.timeoutCount = -8;
                                } else {
                                    this.timeoutCount = -5;
                                }

                                this.mBraceleteBluetoothGatt = bluetoothdevice.connectGatt(mContext, !IsOSKikat, this.mGattCallback);
                                OemLog.log("BraceleteDevices", "SendTimeOut flag is :" + SendTimeOut);
                                OemLog.log("BraceleteDevices", "device.getBondState==" + bluetoothdevice.getBondState());
                            }
                            break;
                        }

                        OemLog.log("BraceleteDevices", "not find a  bluetooth device .................");

                        try {
                            Thread.sleep(500L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    flag = false;
                }

                return flag;
            }
        } else if(dnNO != null && !"".equals(dnNO)) {
            REG_SN = dnNO;
            this.scanLeDevice(true);
            macAddr = "FF:FF:FF:FF:FF:FF";
            return false;
        } else {
            return false;
        }
    }

    private boolean isDevPaird(BluetoothDevice dev) {
        if(dev == null) {
            return false;
        } else {
            Set pairedDevices = this.mBluetoothAdapter.getBondedDevices();
            if(pairedDevices == null) {
                OemLog.log("BraceleteDevices", "pairedDeviceList is null");
                return false;
            } else {
                Iterator iterator = pairedDevices.iterator();

                while(iterator.hasNext()) {
                    BluetoothDevice bledev = (BluetoothDevice)iterator.next();
                    if(bledev.getAddress().equals(dev.getAddress())) {
                        OemLog.log("BraceleteDevices", "isDevPaired : True");
                        return true;
                    }
                }

                OemLog.log("BraceleteDevices", "isDevPaired : True");
                return false;
            }
        }
    }

    @SuppressLint({"NewApi"})
    private void makeBlePair() {
    }

    @SuppressLint({"NewApi"})
    private boolean connect2(String s) {
        if(isConnected && s.equals(mDeviceAddress)) {
            this.broadcastUpdate("cn.appscomm.pedometer.service.ACTION_GATT_SERVICES_DISCOVERED", (byte[])null);
            OemLog.log("BraceleteDevices", "not need reconnect....");
            return true;
        } else {
            this.mBluetoothManager = (BluetoothManager)mContext.getSystemService("bluetooth");
            if(this.mBluetoothManager != null) {
                this.mBluetoothAdapter = this.mBluetoothManager.getAdapter();
            }

            if(s != null && !"".equals(s)) {
                mDeviceAddress = s;
                boolean flag = true;
                Log.i("BraceleteDevices", "connect(final String address)=" + s);
                if(this.mBluetoothAdapter != null && s != null) {
                    if(this.mBraceleteBluetoothGatt != null) {
                        try {
                            this.mBraceleteBluetoothGatt.disconnect();
                            this.mBraceleteBluetoothGatt.close();
                        } catch (Exception var7) {
                            ;
                        }

                        try {
                            Thread.sleep(500L);
                        } catch (InterruptedException var6) {
                            var6.printStackTrace();
                        }

                        this.mBraceleteBluetoothGatt = null;
                    }

                    int icount = 2;
                    bluetoothdevice = null;

                    while(true) {
                        bluetoothdevice = this.mBluetoothAdapter.getRemoteDevice(s);
                        --icount;
                        if(icount < 0 || bluetoothdevice != null) {
                            if(bluetoothdevice == null) {
                                flag = false;
                            } else {
                                if(!IsOSKikat) {
                                    this.timeoutCount = -8;
                                } else {
                                    this.timeoutCount = -5;
                                }

                                this.mBraceleteBluetoothGatt = bluetoothdevice.connectGatt(mContext, !IsOSKikat, this.mGattCallback);

                                OemLog.log("BraceleteDevices", "SendTimeOut flag is :" + SendTimeOut);
                                OemLog.log("BraceleteDevices", "device.getBondState==" + bluetoothdevice.getBondState());
                            }
                            break;
                        }

                        OemLog.log("BraceleteDevices", "not find a  bluetooth device .................");

                        try {
                            Thread.sleep(500L);
                        } catch (InterruptedException var5) {
                            var5.printStackTrace();
                        }
                    }
                } else {
                    flag = false;
                }

                return flag;
            } else {
                s = "FF:FF:FF:FF:FF:FF";
                return false;
            }
        }
    }

    public BluetoothGattService getPedometerGattService() {
        BluetoothGattService bluetoothgattservice;
        if(this.mBraceleteBluetoothGatt == null) {
            bluetoothgattservice = null;
        } else {
            bluetoothgattservice = this.mBraceleteBluetoothGatt.getService(UUID_SERVICE);
        }

        return bluetoothgattservice;
    }

    public void onDestroy() {
        OemLog.log("BraceleteDevices", "service destroy");
        mContext.unregisterReceiver(this.mbroadRec);
        if(this.timer1 != null) {
            this.timer1.cancel();
            this.timer1 = null;
        }

        if(this.mBraceleteBluetoothGatt != null) {
            OemLog.log("BraceleteDevices", "Close Bluetooth");
        }

    }

    private boolean isUseNotifiMode() {
        return pType == Pedometer_TypeInfo.Pedometer_Type.L11 || pType == Pedometer_TypeInfo.Pedometer_Type.L28T || pType == Pedometer_TypeInfo.Pedometer_Type.L28W || pType == Pedometer_TypeInfo.Pedometer_Type.L28H;
    }


    public void sendDataToPedometer(byte[] abyte0) {
        this.writeDataToCharateristic1(abyte0);
    }

    private void scanLeDevice(boolean enable) {
        OemLog.log(TAG, "start search devices...");
        if(enable) {
            this.mHandler.postDelayed(new Runnable() {
                @SuppressLint({"NewApi"})
                public void run() {
                    BraceleteDevices.this.mBluetoothAdapter.stopLeScan(BraceleteDevices.this.mLeScanCallback);
                }
            }, 10000L);
            this.mBluetoothAdapter.startLeScan(this.mLeScanCallback);
        } else {
            this.mBluetoothAdapter.stopLeScan(this.mLeScanCallback);
        }
    }

    public void setSynTime() {
        this.syncTimeToDevice();
    }

    public void getDeviceInfo(int getType) {
        byte[] bytes = new byte[]{(byte)110, (byte)1, (byte)3, (byte)getType, (byte)-113};
        this.writeDataToCharateristic1(bytes);
    }

    public void getSportDataDetail() {
        byte[] bytes = new byte[]{(byte)110, (byte)1, (byte)6, (byte)1, (byte)-113};
        this.writeDataToCharateristic1(bytes);
    }

    public void deleteAReminder(int hour, int min) {
        byte[] bytes = new byte[]{(byte)110, (byte)1, (byte)9, ProtocolParser.intToByteArray(hour)[3], ProtocolParser.intToByteArray(min)[3], (byte)-113};
        this.writeDataToCharateristic1(bytes);
    }

    public void synPersonData(int sex, int year, int month, int day, int height, int weight) {
        byte[] bytes = new byte[]{(byte)110, (byte)1, (byte)18, (byte)0, (byte)7, (byte)-70, (byte)8, (byte)2, (byte)-86, (byte)27, (byte)88, (byte)-113};
        bytes[2] = 18;
        weight *= 100;
        bytes[3] = (byte)sex;
        bytes[4] = ProtocolParser.intToByteArray(year)[3];
        bytes[5] = ProtocolParser.intToByteArray(year)[2];
        bytes[6] = (byte)month;
        bytes[7] = (byte)day;
        bytes[8] = (byte)height;
        bytes[9] = ProtocolParser.intToByteArray(weight)[3];
        bytes[10] = ProtocolParser.intToByteArray(weight)[2];
        this.writeDataToCharateristic1(bytes);
    }

    public void setGoalSteps(int steps) {
        byte[] bytes = new byte[]{(byte)110, (byte)1, (byte)13, ProtocolParser.intToByteArray(steps)[3], ProtocolParser.intToByteArray(steps)[2], ProtocolParser.intToByteArray(steps)[1], ProtocolParser.intToByteArray(steps)[0], (byte)-113};
        this.writeDataToCharateristic1(bytes);
    }

    public void setGoalDis(int Dis) {
        byte[] bytes = new byte[]{(byte)110, (byte)1, (byte)-94, (byte)2, (byte)0, (byte)0, (byte)0, (byte)-113};
        bytes[3] = ProtocolParser.intToByteArray(Dis)[3];
        bytes[4] = ProtocolParser.intToByteArray(Dis)[2];
        bytes[5] = ProtocolParser.intToByteArray(Dis)[1];
        bytes[6] = ProtocolParser.intToByteArray(Dis)[0];
        this.writeDataToCharateristic1(bytes);
    }

    public void setGoalCal(int Cal) {
        byte[] bytes = new byte[]{(byte)110, (byte)1, (byte)-94, (byte)1, (byte)0, (byte)0, (byte)0, (byte)-113};
        bytes[3] = ProtocolParser.intToByteArray(Cal)[3];
        bytes[4] = ProtocolParser.intToByteArray(Cal)[2];
        bytes[5] = ProtocolParser.intToByteArray(Cal)[1];
        bytes[6] = ProtocolParser.intToByteArray(Cal)[0];
        this.writeDataToCharateristic1(bytes);
    }

    public void setAutoSleepRange(boolean isEnAutoSleep, int startHours, int startMins, int endHours, int endMins) {
        byte[] var10000 = new byte[]{(byte)110, (byte)1, (byte)54, (byte)startHours, (byte)startMins, (byte)endHours, (byte)endMins, (byte)-113};
        byte[] bytes;
        if(isEnAutoSleep) {
            bytes = new byte[]{(byte)110, (byte)1, (byte)54, (byte)startHours, (byte)startMins, (byte)endHours, (byte)endMins, (byte)-113};
        } else {
            bytes = new byte[]{(byte)110, (byte)1, (byte)54, (byte)0, (byte)0, (byte)0, (byte)0, (byte)-113};
        }

        this.writeDataToCharateristic1(bytes);
    }

    public void setSleepStatus(int status) {
        if(status == 0 && status == 1 && status == 2) {
            byte[] bytes = new byte[]{(byte)110, (byte)1, (byte)54, (byte)0, (byte)0, (byte)0, (byte)0, (byte)-113};
            byte a = 1;
            byte a1 = (byte)(a << 5 + status);
            bytes[3] = a1;
            this.writeDataToCharateristic1(bytes);
        }
    }

    public void setBrightness(int level) {
        byte[] bytes = new byte[]{(byte)110, (byte)1, (byte)55, (byte)(level - 1), (byte)-113};
        this.writeDataToCharateristic1(bytes);
    }

    public void getBatteryLevel() {
        byte[] bytes = new byte[]{(byte)110, (byte)1, (byte)15, (byte)1, (byte)-113};
        this.writeDataToCharateristic1(bytes);
    }

    public void getSportDataCount(int type) {
        byte[] bytes = new byte[]{(byte)110, (byte)1, (byte)48, (byte)type, (byte)-113};
        this.writeDataToCharateristic1(bytes);
    }

    public void reSetFactoryMode() {
        byte[] bytes = new byte[]{(byte)110, (byte)1, (byte)17, (byte)1, (byte)-113};
        this.writeDataToCharateristic1(bytes);
    }

    public void getPersonData() {
        byte[] bytes = new byte[]{(byte)110, (byte)1, (byte)20, (byte)1, (byte)-113};
        this.writeDataToCharateristic1(bytes);
    }

    public void getSportDataTotal() {
        byte[] bytes = new byte[]{(byte)110, (byte)1, (byte)27, (byte)1, (byte)-113};
        this.writeDataToCharateristic1(bytes);
    }

    public void cleanAllReminder() {
        byte[] bytes = new byte[]{(byte)110, (byte)1, (byte)33, (byte)1, (byte)-113};
        this.writeDataToCharateristic1(bytes);
    }

    public void getSleepDataDetail() {
        byte[] bytes = new byte[]{(byte)110, (byte)1, (byte)49, (byte)1, (byte)-113};
        this.writeDataToCharateristic1(bytes);
    }

    public void setManualMode(int mode) {
        byte[] bytes = new byte[]{(byte)110, (byte)1, (byte)50, (byte)mode, (byte)-113};
        this.writeDataToCharateristic1(bytes);
    }

    public void addAReminder(int id, int hour, int min, String repeat) {
        byte[] bytes = new byte[8];
        bytes[0] = 110;
        bytes[1] = 1;
        bytes[2] = 64;
        if(id == 5) {
            bytes[3] = -1;
        } else {
            bytes[3] = (byte)id;
        }

        bytes[4] = ProtocolParser.intToByteArray(hour)[3];
        bytes[5] = ProtocolParser.intToByteArray(min)[3];
        bytes[6] = ProtocolParser.binaryStr2Bytes(repeat)[0];
        bytes[7] = -113;
        this.writeDataToCharateristic1(bytes);
    }

    public void setTimeType(boolean is24H, boolean isKm, boolean isShowDate, boolean isShowBattery, int dateType) {
        byte unit;
        byte[] bytes;
        if(this.isUseNotifiMode()) {
            unit = 0;
            if(!is24H) {
                unit = (byte)(unit | 1);
            }

            if(!isKm) {
                unit = (byte)(unit | 2);
            }

            if(isShowDate) {
                unit = (byte)(unit | 4);
            }

            if(isShowBattery) {
                unit = (byte)(unit | 32);
            }

            switch(dateType) {
            case 1:
                unit = (byte)(unit | 8);
                break;
            case 2:
                unit = (byte)(unit | 16);
            }

            bytes = new byte[]{(byte)110, (byte)1, (byte)52, unit, (byte)-113};
            this.writeDataToCharateristic1(bytes);
        } else {
            unit = 0;
            if(!is24H) {
                unit = (byte)(unit | 1);
            }

            if(!isKm) {
                unit = (byte)(unit | 2);
            }

            bytes = new byte[]{(byte)110, (byte)1, (byte)52, unit, (byte)-113};
            this.writeDataToCharateristic1(bytes);
        }

    }

    public void getWatchId() {
        byte[] bytes = new byte[]{(byte)110, (byte)1, (byte)4, (byte)1, (byte)-113};
        this.writeDataToCharateristic1(bytes);
    }

    public void setAntiActivtiy(boolean isAntiSwON, String repeatWeeks, int interval, int startHour, int startMin, int endHour, int endMin, int stepLimit) {
        int v1 = 0;
        if(repeatWeeks != null && repeatWeeks.length() == 7) {
            StringBuilder sb = new StringBuilder();
            if(isAntiSwON) {
                v1 |= 128;
            }

            if(repeatWeeks.charAt(0) == 49) {
                v1 |= 1;
                sb.append('1');
            } else {
                sb.append('0');
            }

            if(repeatWeeks.charAt(1) == 49) {
                sb.append('1');
                v1 |= 2;
            } else {
                sb.append('0');
            }

            if(repeatWeeks.charAt(2) == 49) {
                sb.append('1');
                v1 |= 4;
            } else {
                sb.append('0');
            }

            if(repeatWeeks.charAt(3) == 49) {
                sb.append('1');
                v1 |= 8;
            } else {
                sb.append('0');
            }

            if(repeatWeeks.charAt(4) == 49) {
                sb.append('1');
                v1 |= 16;
            } else {
                sb.append('0');
            }

            if(repeatWeeks.charAt(5) == 49) {
                sb.append('1');
                v1 |= 32;
            } else {
                sb.append('0');
            }

            if(repeatWeeks.charAt(6) == 49) {
                sb.append('1');
                v1 |= 64;
            } else {
                sb.append('0');
            }

            OemLog.log("BraceleteDevices", "inactivity v1 :" + v1);
            byte[] bytes = new byte[]{(byte)110, (byte)1, (byte)67, (byte)1, (byte)v1, (byte)interval, (byte)startHour, (byte)startMin, (byte)endHour, (byte)endMin, ProtocolParser.intToByteArray(stepLimit)[3], ProtocolParser.intToByteArray(stepLimit)[2], (byte)-113};
            this.writeDataToCharateristic1(bytes);
        } else {
            OemLog.log("BraceleteDevices", "repeatWeeks error...");
        }
    }

    public void setANCS_SW(boolean isCallON, boolean isMisCallON, boolean isSMSON, boolean isEmailON, boolean isSocialON, boolean iscalendarON, boolean isAntiLostON) {
        byte r1 = 0;
        byte[] bytes = new byte[]{(byte)110, (byte)1, (byte)-76, (byte)65, (byte)0, (byte)0, (byte)0, (byte)-113};
        if(isCallON) {
            r1 = (byte)(r1 | 1);
        }

        if(isMisCallON) {
            r1 = (byte)(r1 | 2);
        }

        if(isSMSON) {
            r1 = (byte)(r1 | 4);
        }

        if(isEmailON) {
            r1 = (byte)(r1 | 8);
        }

        if(isSocialON) {
            r1 = (byte)(r1 | 16);
        }

        if(iscalendarON) {
            r1 = (byte)(r1 | 32);
        }

        if(isAntiLostON) {
            r1 = (byte)(r1 | 64);
        }

        bytes[3] = r1;
        this.writeDataToCharateristic1(bytes);
    }

    public void setPushCallName(String name) {
        byte[] bytes = new byte[56];
        bytes[0] = 110;
        bytes[1] = 1;
        bytes[2] = -78;
        bytes[55] = -113;
        Object abytes = null;
        byte[] abytes1 = name.getBytes();
        if(abytes1.length < 52) {
            System.arraycopy(abytes1, 0, bytes, 3, abytes1.length);
        } else {
            System.arraycopy(abytes1, 0, bytes, 3, 52);
        }

        this.writeDataToCharateristic1(bytes);
    }

    public void setPushUCallNum(int mNum) {
        byte[] bytes = new byte[]{(byte)110, (byte)1, (byte)-77, (byte)1, (byte)2, (byte)mNum, (byte)-113};
        this.writeDataToCharateristic1(bytes);
    }

    public void setPushSMSNum(int mNum) {
        byte[] bytes = new byte[]{(byte)110, (byte)1, (byte)-77, (byte)1, (byte)8, (byte)mNum, (byte)-113};
        this.writeDataToCharateristic1(bytes);
    }

    public void setPushUEmailNum(int mNum) {
        byte[] bytes = new byte[]{(byte)110, (byte)1, (byte)-77, (byte)1, (byte)1, (byte)mNum, (byte)-113};
        this.writeDataToCharateristic1(bytes);
    }

    public void setPushUCalNum(int mNum) {
        byte[] bytes = new byte[]{(byte)110, (byte)1, (byte)-77, (byte)1, (byte)4, (byte)mNum, (byte)-113};
        this.writeDataToCharateristic1(bytes);
    }

    public void setPushUSocNum(int mNum) {
        byte[] bytes = new byte[]{(byte)110, (byte)1, (byte)-77, (byte)1, (byte)32, (byte)mNum, (byte)-113};
        this.writeDataToCharateristic1(bytes);
    }

    public BaseCommand pullCommandFromQueue() {
        BaseCommand baseCommand = commandList.poll();
        if (MyApplication.getInstance().isOnBraceleteUI()) {
            if (commandList.size() > 5) {
                commandList.clear();
            }
        }
        return  baseCommand;
    }

    public void addCommandToQueue(BaseCommand command) {
        //保证2秒添加一个命令

        if (MyApplication.getInstance().isOtherDevicesMeasuring() || MyApplication.getInstance().isOnDevicesMeasureUI()) {
            if (command instanceof BraceleteCommand) {
                OemLog.log(TAG, "while the other devices is measuring ignore the bracelete Command...");
                return;
            }
        }

        commandList.add(command);
        log(TAG, "before send add message");
        servicesCallBackHandler.sendEmptyMessage(Const.BRACELETE_COMMAND_EXECUTE_STATUS_COMMAND_ADD);
    }

    public void setServicesCallBackHandler(Handler servicesCallBackHandler) {
        this.servicesCallBackHandler = servicesCallBackHandler;
        commandDataParser.setCallbackHandler(servicesCallBackHandler);
    }

    public void discoverServices() {
        BraceleteDevices.this.mBraceleteBluetoothGatt.discoverServices();
    }

    public void scanAutoConnect() {
        scanLeDevice(true);
    }

    public void registerSportDataCallBack(ISportDataCallback sportDataCallback) {
        commandDataParser.registerSportDataCallback(sportDataCallback);
    }

    public void registerConnectStateChangeCallBack(IConnectionStateChange connectionStateChange) {
        this.connectionStateChange = connectionStateChange;
    }

    public void registerDeviceSearchCallBack(IDeviceSearchCallBack deviceSearchCallBack) {
        this.deviceSearchCallBack = deviceSearchCallBack;
    }

    public Queue<BaseCommand> getCommandList() {
        return commandList;
    }

    //在命令执行的前置条件不满足的情况下，忽略命令的执行，保证下一条命令的顺利执行
    public void ignoreCommandExecute() {
        servicesCallBackHandler.sendEmptyMessage(Const.BRACELETE_COMMAND_EXECUTE_STATUS_COMPLETE);
    }

    public void setIsBloodPressMeasuring(boolean isBloodPressMeasuring) {
        this.isBloodPressMeasuring = isBloodPressMeasuring;
    }

    public void setIsBodyFatMeasuring(boolean isBodyFatMeasuring) {
        this.isBodyFatMeasuring = isBodyFatMeasuring;
    }

    public void stopBleScan() {
        scanLeDevice(false);
    }





}

