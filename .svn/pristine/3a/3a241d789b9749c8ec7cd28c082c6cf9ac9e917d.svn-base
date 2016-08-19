package com.capitalbio.oemv2.myapplication.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.Utils.Utility;
import com.capitalbio.oemv2.myapplication.adapter.SearchDeviceAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lzq
 * @Time 2015/12/21 16:23
 */
public class DeviceBindActivity extends BaseActivity implements View.OnClickListener {

    //返回键
    private RelativeLayout rlback;
   //标题
    private TextView tvtitlename;
    //搜索到的设备的列表
    private ListView lvdevices;
    private List<String> list;
    private SearchDeviceAdapter adapter;

    //当前是哪个设备
    private String devicename = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devicebind);
        getWhoStarMe();
        initView();
        initViewEvent();
        initViewData();

    }

    private void getWhoStarMe(){
        devicename = getIntent().getStringExtra("type");
    }

    private void initView(){
        rlback = (RelativeLayout) this.findViewById(R.id.rl_devicebind_back);


        tvtitlename = (TextView) this.findViewById(R.id.tv_devicebind_titlename);

        lvdevices = (ListView) this.findViewById(R.id.lv_devicebind_devicelist);
    }

    private void initViewEvent(){
        rlback.setOnClickListener(this);

    }

    private void initViewData(){

        //初始化标题
        if (devicename.equals("体脂秤")){
            tvtitlename.setText(R.string.tizhichengshezhi);
        }
        if (devicename.equals("血压计")){
            tvtitlename.setText(R.string.xueyajishezhi);
        }

        //初始化listview显示的数据
        list = new ArrayList<>();
        list.add("device1");
        list.add("device2");
        list.add("device3");
        adapter = new SearchDeviceAdapter(getContext(),list);
        lvdevices.setAdapter(adapter);
        lvdevices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.rl_devicebind_back:
                this.finish();
                break;
        }
    }
}
