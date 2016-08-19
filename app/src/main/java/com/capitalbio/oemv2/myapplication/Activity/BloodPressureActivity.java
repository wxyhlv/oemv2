package com.capitalbio.oemv2.myapplication.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.capitalbio.oemv2.myapplication.Base.IBloodPressDevicesCallBack;
import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.Bean.BloodPressureBean;
import com.capitalbio.oemv2.myapplication.Const.BloodConst;
import com.capitalbio.oemv2.myapplication.Fragment.BloodPressFragmentDay;
import com.capitalbio.oemv2.myapplication.Fragment.BloodPressFragmentMeasure;
import com.capitalbio.oemv2.myapplication.Fragment.BloodPressFragmentMonth;
import com.capitalbio.oemv2.myapplication.Fragment.BodyFatFragmentDay;
import com.capitalbio.oemv2.myapplication.Fragment.BodyFatFragmentMonth;
import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;
import com.capitalbio.oemv2.myapplication.Utils.TimeStampUtil;
import com.capitalbio.oemv2.myapplication.Utils.Utility;
import com.capitalbio.oemv2.myapplication.dialog.DateChooseYMDialog;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chengkun on 15-11-4.
 */
public class BloodPressureActivity extends Activity implements View.OnClickListener {
    private TextView title, tvDate;
    private LinearLayout llSwitchDate;
    private TextView tv_switch_day, tv_switch_month;

    private RelativeLayout rl_switch_bottom, rl_switch_date,rl_date;
    private LinearLayout ll_switch_bottom;
    private TextView tv_switch_left, tv_switch_right;//底部切换
    private ImageView iv_device_set,iv_top_right;// setbutton
    private String dateString;
    private SimpleDateFormat format, sf;
    private LinearLayout fgbody;
    Fragment bloodFgDay, bloodFgMonth, bloodFgMeasure;
    private boolean isDay;
    private String selectedDate;//日起滚动控件选中的日期
    private String curDay, curDay1;
    private Bundle args = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  //无title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);  //全屏
        setContentView(R.layout.activity_base);
        findViewById();
        init();
    }


    public void findViewById() {
        rl_date = (RelativeLayout) findViewById(R.id.rl_date);
        title = (TextView) findViewById(R.id.tv_top_title);
        tvDate = (TextView) findViewById(R.id.tv_date);
        llSwitchDate = (LinearLayout) findViewById(R.id.ll_switch_date);
        ll_switch_bottom = (LinearLayout) findViewById(R.id.ll_switch_bottom);
        tv_switch_day = (TextView) findViewById(R.id.tv_switch_day);
        tv_switch_month = (TextView) findViewById(R.id.tv_switch_month);
        rl_switch_bottom = (RelativeLayout) findViewById(R.id.rl_switch_bottom);
        tv_switch_left = (TextView) findViewById(R.id.tv_switch_left);
        tv_switch_right = (TextView) findViewById(R.id.tv_switch_right);
        rl_switch_date = (RelativeLayout) findViewById(R.id.rl_switch_date);
        tvDate.setOnClickListener(this);
        tv_switch_day.setOnClickListener(this);
        tv_switch_month.setOnClickListener(this);
        tv_switch_left.setOnClickListener(this);
        tv_switch_right.setOnClickListener(this);
        iv_top_right = (ImageView) findViewById(R.id.iv_top_right);
        iv_top_right.setOnClickListener(this);

    }

    public void init() {
        title.setText(getResources().getString(R.string.bloodpress));
        String curYear = new Date().getYear() + 1900 + "年  ";
        String curMonth = new Date().getMonth() + 1 + "月  ";
        isDay = true;
        tvDate.setText(curYear + curMonth);

        selectedDate = TimeStampUtil.currMonth(System.currentTimeMillis());
        BloodConst.blood_month = selectedDate;
        tv_switch_left.setText(getResources().getString(R.string.switch_mesure));
        tv_switch_right.setText(getResources().getString(R.string.switch_history));
        bloodFgDay = new BloodPressFragmentDay();
        args.putCharSequence("curmonth", selectedDate);
        bloodFgDay.setArguments(args);
        bloodFgMonth = new BloodPressFragmentMonth();
        bloodFgMonth.setArguments(args);

        bloodFgMeasure = new BloodPressFragmentMeasure();
        rl_date.setVisibility(View.GONE);
        rl_switch_date.setVisibility(View.GONE);
        getFragmentManager().beginTransaction().add(R.id.ll_fg_body, bloodFgMeasure).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_switch_day:
                llSwitchDate.setBackground(getResources().getDrawable(R.drawable.switch_bracelete_day));
                rl_switch_date.setBackground(null);
                tv_switch_day.setTextColor(Color.parseColor("#0EA8C3"));
                tv_switch_month.setTextColor(Color.WHITE);
                isDay = true;
                args.putCharSequence("curmonth", selectedDate);
                getFragmentManager().beginTransaction().replace(R.id.ll_fg_body, bloodFgDay, "fragmentDay").commit();
                break;
            case R.id.tv_switch_month:
                llSwitchDate.setBackground(getResources().getDrawable(R.drawable.switch_date2));
                rl_switch_date.setBackgroundColor(Color.WHITE);
                tv_switch_day.setTextColor(Color.parseColor("#0EA8C3"));
                tv_switch_month.setTextColor(Color.WHITE);
                isDay = false;
                args.putCharSequence("curmonth", selectedDate);
                getFragmentManager().beginTransaction().replace(R.id.ll_fg_body, bloodFgMonth, "fragmentMonth").commit();
                break;
            case R.id.tv_switch_left:
                ll_switch_bottom.setBackground(getResources().getDrawable(R.drawable.switch_sportorsleep));
                tv_switch_right.setTextColor(Color.parseColor("#0EA8C3"));
                tv_switch_left.setTextColor(Color.WHITE);
                rl_date.setVisibility(View.GONE);
                rl_switch_date.setVisibility(View.GONE);
                getFragmentManager().beginTransaction().replace(R.id.ll_fg_body, bloodFgMeasure,"fragmentMeasure").commit();
                break;
            case R.id.tv_switch_right:

                isDay = true;
                llSwitchDate.setBackground(getResources().getDrawable(R.drawable.switch_bracelete_day));
                rl_switch_date.setBackground(null);
                ll_switch_bottom.setBackground(getResources().getDrawable(R.drawable.switch_sleeporsports));
                tv_switch_left.setTextColor(Color.parseColor("#0EA8C3"));
                tv_switch_right.setTextColor(Color.WHITE);
                rl_date.setVisibility(View.VISIBLE);
                rl_switch_date.setVisibility(View.VISIBLE);

                getFragmentManager().beginTransaction().replace(R.id.ll_fg_body, bloodFgDay,"fragmentDay").commit();
                break;

            case R.id.tv_date:

                DateChooseYMDialog mChangeDateDialog = new DateChooseYMDialog(
                        BloodPressureActivity.this);
                dateString = tvDate.getText().toString().replace(" ", "");
                Log.d("year", dateString + "");
                int yearInt = Integer.valueOf(dateString.substring(0, 4));
                int monthInt = Integer.valueOf(dateString.substring(5, dateString.length() - 1));
                Log.d("year", yearInt + "");
                Log.d("month", monthInt + "");

                mChangeDateDialog.setDate(yearInt, monthInt);
                mChangeDateDialog.show();
                mChangeDateDialog.setBirthdayListener(new DateChooseYMDialog.OnBirthListener() {

                    @Override
                    public void onClick(String year, String month) {
                        // TODO Auto-generated method stub
                       /* Toast.makeText(BloodPressureActivity.this,
                                year + "-" + month + "-",
                                Toast.LENGTH_LONG).show();*/
                        if ((year + "年" + month + "月").equals(dateString)) {
                            return;
                        }
                        tvDate.setText(year + "年" + "  " + month + "月");
                        month =TimeStampUtil.singleIntToDoubleString(Integer.valueOf(month));
                        selectedDate = year + "-" + month;
                        BloodConst.blood_month = selectedDate;
                        //TODO
                        //传入月份给fragment
                        BloodPressFragmentDay fgDay = (BloodPressFragmentDay) getFragmentManager().findFragmentByTag("fragmentDay");
                        Log.i("selsectedDate", selectedDate);
                        if(fgDay!=null){
                            fgDay.update(year + "-" + month);
                        }
                        BloodPressFragmentMonth fgMonth = (BloodPressFragmentMonth) getFragmentManager().findFragmentByTag("fragmentMonth");
                        if(fgMonth!=null){
                            fgMonth.update(year+"-"+month);
                        }
                        /*if (isDay) {
                            BloodPressFragmentDay fgDay = (BloodPressFragmentDay) getFragmentManager().findFragmentByTag("fragmentDay");
                            Log.i("selsectedDate", selectedDate);
                            fgDay.update(year+"-"+month);
                        } else {
                            BloodPressFragmentMonth fgMonth = (BloodPressFragmentMonth) getFragmentManager().findFragmentByTag("fragmentMonth");

                            fgMonth.update(year+"-"+month);
                        }*/

                    }
                });
                break;
            case R.id.iv_top_right:
                Utility.startActivity(BloodPressureActivity.this, DatainputBloodpressActivity.class);
                break;
        }
    }

    public void back(View view) {
        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();

        MyApplication.getInstance().setIsOnDevicesMeasureUI(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        OemLog.log("BloodPressure", "onPause...");
        MyApplication.getInstance().stopBleScan();

        MyApplication.getInstance().setIsOnDevicesMeasureUI(false);
    }
}
