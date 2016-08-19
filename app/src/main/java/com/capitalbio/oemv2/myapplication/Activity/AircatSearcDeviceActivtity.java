package com.capitalbio.oemv2.myapplication.Activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.capitalbio.oemv2.myapplication.Const.AircatConst;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.IEsptouchListener;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.IEsptouchResult;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.ISearchMacCallBack;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.ISetTargetApCallBack;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.command.SearchMacCommand;
import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.Utils.HexUtils;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;
import com.capitalbio.oemv2.myapplication.Utils.PreferencesUtils;
import com.capitalbio.oemv2.myapplication.adapter.SearchAircatDeviceAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wxy on 15-12-31.
 * 空气猫的搜索界面
 */
public class AircatSearcDeviceActivtity extends BaseActivity implements View.OnClickListener, ISearchMacCallBack, ISetTargetApCallBack {
    LinearLayout contentLayout;
    ImageView ivProgeress;
    ListView listview;
    private final static int SCANNIN_GREQUEST_CODE = 1;
    private AnimationDrawable aniDrawble;
    SearchAircatDeviceAdapter adapter;
    List<String> list = new ArrayList<>();
    String mac;
    SearchMacCommand searchMacCommand;

    private IEsptouchListener myListener = new IEsptouchListener() {
        @Override
        public void onEsptouchResultAdded(IEsptouchResult result) {

        }
    };

    @Override
    protected void initChildLayout() {
        initChildView();
    }

    private void initChildView() {
        rl.setBackgroundResource(R.drawable.bg_main);
        ivSplitLine.setBackgroundResource(R.color.mainSplitLine);
        //    rlNavigateBar.setBackgroundResource(R.drawable.bg_tang_zhi_san_xiang);
        setLeftTopIcon(R.drawable.ic_back);
        contentLayout = (LinearLayout) View.inflate(this, R.layout.activity_search_airdevice, null);
        llBody.addView(contentLayout);
        setTvTopTitle(R.string.device_search);
        ivProgeress = (ImageView) findViewById(R.id.iv_aircatProgress);
        listview = (ListView) findViewById(R.id.lv_aircat);
        aniDrawble = (AnimationDrawable) ivProgeress.getDrawable();
        aniDrawble.start();

        adapter = new SearchAircatDeviceAdapter(getContext(), list);

        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PreferencesUtils.putBoolean(context, "secondAddAircat", true);

                Intent intent = new Intent();
                intent.setClass(AircatSearcDeviceActivtity.this, AircatScanResultActivtity.class);
                intent.putExtra("way", AircatConst.Way_search);
                intent.putExtra("result",list.get(position) );
                startActivity(intent);
                finish();
               // Utility.startActivity(getContext(), AirCatActivity.class);

            }
        });

        searchMacCommand = new SearchMacCommand(this);
        searchMacCommand.excuteCommand();

    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            aniDrawble.stop();
            String result = (String) msg.obj;
            if (!list.contains(result)) {
                list.add(result);
            }

            adapter.notifyDataSetChanged();
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_scanqrcode:

                break;
        }

    }

    @Override
    public void onSearchMacResult(byte[] mac) {
        String macstring = HexUtils.byte2HexStr(mac);
        if (!macstring.startsWith("AA")) {
            return;
        }
        String data[] = macstring.split(" ");
        StringBuilder builder = new StringBuilder();
        OemLog.log("macresult", macstring);
        for (int i = 6; i < data.length - 2; i++) {
            builder.append(data[i] + ":");
        }
        String macResult = builder.toString().substring(0, builder.toString().length() - 1);
        Message msg = new Message();
        msg.obj = macResult;
        handler.sendMessage(msg);

    }

    @Override
    public void onSetTargetAp(int result) {
        //设置ap命令执行成功之后进行搜索操作

    }
}
