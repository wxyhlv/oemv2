package com.capitalbio.oemv2.myapplication.Devices.Bracelete.BraceleteCommand;

import android.content.Context;

import com.capitalbio.oemv2.myapplication.Base.BaseCommand;
import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.BraceleteLib.BraceleteDevices;
import com.capitalbio.oemv2.myapplication.Utils.PreferencesUtils;

/**
 * Created by chengkun on 15-12-16.
 */
public abstract class BraceleteCommand implements BaseCommand{

    protected BraceleteDevices braceleteDevices;
    protected Context mContext = MyApplication.getInstance();
    protected boolean isBraceleteConnected = false;

    protected BraceleteCommand() {
        braceleteDevices = BraceleteDevices.getInstance();
    }

    @Override
    public void excuteCommand() {
        isBraceleteConnected = PreferencesUtils.getBoolean(mContext, "connect_state", false);
    }
}
