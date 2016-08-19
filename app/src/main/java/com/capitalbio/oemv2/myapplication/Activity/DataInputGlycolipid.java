package com.capitalbio.oemv2.myapplication.Activity;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.capitalbio.oemv2.myapplication.FirstPeriod.Utils.HTG;
import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.dialog.TimeSelectDialog;

public class DataInputGlycolipid extends Activity implements View.OnClickListener{
    private RelativeLayout rl_datainputgly_back,rl_datainputgly_save;
    private TextView tv_glyinput_time;
    private LinearLayout ll_glyinput_time;
    private EditText et_gly_bloodinput,et_gly_choleinput,et_gly_triinput,et_gly_highlipinput,et_gly_lowlipinput;
    private String year_select, month_select, day_select, hour_select, minute_select;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  //无title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);  //全屏
        setContentView(R.layout.activity_data_input_glycolipid);
        initView();
        initData();
    }

    private void initData() {
        //年
        year_select = HTG.currYear()+"";
        //月
        int currmonth = HTG.currMonth();
        String currmonth_s = currmonth+"";
        if(HTG.isSingleBitInt(currmonth)){
            currmonth_s = "0"+currmonth_s;
        }
        month_select = currmonth_s;
        //天
        int currday = HTG.currDay();
        String currday_s = currday+"";
        if(HTG.isSingleBitInt(currday)){
            currday_s = "0"+currday_s;
        }
        day_select = currday_s;
        //时
        int currhour = HTG.currHour();
        String currhour_s = currhour+"";
        if(HTG.isSingleBitInt(currhour)){
            currhour_s = "0"+currhour_s;
        }
        hour_select = currhour_s;
        //分
        int currminute = HTG.currMinute();
        String currminute_s = currminute+"";
        if(HTG.isSingleBitInt(currminute)){
            currminute_s = "0"+currminute_s;
        }
        minute_select = currminute_s;

        tv_glyinput_time.setText(HTG.currYear()+"-"+currmonth_s+"-"+currday_s+" "+currhour_s+":"+currminute_s);
    }

    private void initView() {
        rl_datainputgly_back= (RelativeLayout) findViewById(R.id.rl_datainputgly_back);
        rl_datainputgly_save= (RelativeLayout) findViewById(R.id.rl_datainputgly_save);
        ll_glyinput_time= (LinearLayout) findViewById(R.id.ll_glyinput_time);
        et_gly_bloodinput= (EditText) findViewById(R.id.et_gly_bloodinput);
        et_gly_choleinput= (EditText) findViewById(R.id.et_gly_choleinput);
        et_gly_triinput= (EditText) findViewById(R.id.et_gly_triinput);
        et_gly_highlipinput= (EditText) findViewById(R.id.et_gly_highlipinput);
        et_gly_lowlipinput= (EditText) findViewById(R.id.et_gly_lowlipinput);
        tv_glyinput_time= (TextView) findViewById(R.id.tv_glyinput_time);


        ll_glyinput_time.setOnClickListener(this);
        rl_datainputgly_save.setOnClickListener(this);
        rl_datainputgly_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_datainputgly_back:
                onBackPressed();
                finish();
                break;
            case R.id.rl_datainputgly_save:





                break;
            case R.id.ll_glyinput_time:
                TimeSelectDialog mtimeselect = new TimeSelectDialog(
                       DataInputGlycolipid.this);
                mtimeselect.show();
                mtimeselect.setOnCancelListener(new TimeSelectDialog.onCancel() {
                    @Override
                    public void onCancel() {
                        // TODO Auto-generated method stub
                    }
                });
                mtimeselect.setOnOkListener(new TimeSelectDialog.onOK() {
                    @Override
                    public void onOk(String month, String day, String hour, String minute) {
                        //TODO Auto-generated method stub
                        tv_glyinput_time.setText(HTG.currYear() + "-" + month + "-" + day + " " + hour + ":" + minute);
                        year_select = HTG.currYear() + "";
                        month_select = month;
                        day_select = day;
                        hour_select = hour;
                        minute_select = minute;
                    }
                });
                break;
        }
    }
}
