package com.capitalbio.oemv2.myapplication.Activity;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.capitalbio.oemv2.myapplication.Bean.SportDataTotalBean;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.IEsptouchListener;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.IEsptouchResult;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.ISearchMacCallBack;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.ISetTargetApCallBack;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.command.SearchMacCommand;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.command.SetTargetAPCommand;
import com.capitalbio.oemv2.myapplication.Devices.Bracelete.ISportDataCallback;
import com.capitalbio.oemv2.myapplication.Devices.Bracelete.DevicesDataObserver;
import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;

import java.util.Arrays;

public class CommandTestActivity extends Activity implements View.OnClickListener, ISportDataCallback, ISetTargetApCallBack, ISearchMacCallBack{

    public static final String TAG = "CommandTestActivity";

    private Button setAp;
    private Button searchMac;


    private String wifiPassWord = "biouser12";
    private SetTargetAPCommand setTargetAPCommand;
    private SearchMacCommand searchMacCommand;

    private IEsptouchListener myListener = new IEsptouchListener() {
        @Override
        public void onEsptouchResultAdded(IEsptouchResult result) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.command_test);

        setAp = (Button) findViewById(R.id.setAp);
        searchMac = (Button) findViewById(R.id.searchMac);

        setAp.setOnClickListener(this);
        searchMac.setOnClickListener(this);

        DevicesDataObserver.getObserver().registerObserver(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setAp:
                setTargetAPCommand = new SetTargetAPCommand(wifiPassWord, this);
                setTargetAPCommand.excuteCommand();
                break;

            case R.id.searchMac:
                searchMacCommand = new SearchMacCommand(this);
                searchMacCommand.excuteCommand();
                break;
        }
    }

    @Override
    public void onLoadData(SportDataTotalBean sportDataTotalBean) {

    }

    @Override
    public void onSearchMacResult(byte[] mac) {
        OemLog.log(TAG, "mac is " + Arrays.toString(mac));
    }

    @Override
    public void onSetTargetAp(int result) {
        OemLog.log(TAG, "result is " + result);
    }
}
















