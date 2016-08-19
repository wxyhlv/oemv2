package com.capitalbio.oemv2.myapplication.Devices.BodyFatDevices.src.com.bayh.bluetooth.fatScale.command;

import android.content.Context;

import com.capitalbio.oemv2.myapplication.Base.BaseCommand;
import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.Utils.PreferencesUtils;

/**
 * Created by chengkun on 16-3-6.
 */
public class BodyFatCommand implements BaseCommand{

    protected FatScaleStateMachine fatScaleStateMachine = FatScaleStateMachine.getInstance();
    protected Context mContext = MyApplication.getInstance();
    protected boolean isBodyFatConnected;

    public BodyFatCommand() {
    }

    @Override
    public void excuteCommand() {
        isBodyFatConnected = PreferencesUtils.getBoolean(mContext, "bodyfat_connect_state", false);
    }
}
