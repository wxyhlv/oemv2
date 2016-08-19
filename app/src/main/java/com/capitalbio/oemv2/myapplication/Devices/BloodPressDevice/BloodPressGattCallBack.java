package com.capitalbio.oemv2.myapplication.Devices.BloodPressDevice;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Context;
import android.os.Build;

import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.Const.Const;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;
import com.capitalbio.oemv2.myapplication.Utils.PreferencesUtils;

/**
 * Created by chengkun on 15-12-27.
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class BloodPressGattCallBack extends BluetoothGattCallback{
    public static final String TAG = "BloodPressGattCallBack";

    private Context mContext = MyApplication.getInstance();
    private BloodPressStateMachine stateMachine = BloodPressStateMachine.getInstance();

    @Override
    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        super.onCharacteristicWrite(gatt, characteristic, status);
    }

    @Override
    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        super.onCharacteristicRead(gatt, characteristic, status);
    }

    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {

    }

    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        OemLog.log(TAG, "connect to devices device name is " + gatt.getDevice().getName() + " previous state is " + status + ", new state is " + newState);
        if (newState == Const.GATT_SERVICES_CONNECTED) {
            PreferencesUtils.putBoolean(mContext, "blood_press_connect_state", true);
            stateMachine.sendMessage(Constant.BLOOD_PRESS_CONNECT_COMMAND_SUCCESS);
        } else {
            PreferencesUtils.putBoolean(mContext, "blood_press_connect_state", false);
        }
    }
}













