package com.capitalbio.oemv2.myapplication.Activity;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.capitalbio.oemv2.myapplication.Const.BodyFatConst;
import com.capitalbio.oemv2.myapplication.Const.GlycolipidConst;
import com.capitalbio.oemv2.myapplication.Fragment.GlycolipidFragmentDay;
import com.capitalbio.oemv2.myapplication.Fragment.GlycolipidFragmentMonth;
import com.capitalbio.oemv2.myapplication.Fragment.GlycolipidFragmentRealTime;
import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.Utils.TimeStampUtil;
import com.capitalbio.oemv2.myapplication.dialog.ChangeDateDialog;
import com.capitalbio.oemv2.myapplication.dialog.DateChooseYMDialog;

import java.util.Calendar;
import java.util.Date;


public class GlycolipidActivity extends Activity implements View.OnClickListener{
    private ImageView iv_top_right,iv_glycolipid_set;
    private TextView tv_date,tv_state,tv_switch_realtime,tv_switch_history,tv_switch_day,tv_switch_month;
    private ProgressBar pb_progress;
    private LinearLayout ll_switch_background,ll_switch_date;
    private ScrollView sc_gly_scroll;
    private FragmentManager fragmentManager;
    private Fragment realtimeFragment,dayFragment,monFragment,currentFragment;
    private RelativeLayout rl_switch_date,rl_set,rl_top_left_return;
    private Bundle args=new Bundle();
    private String currentdate,currentYear,currentDay,currentMonth,dateString;
    private Boolean isDay;
    private int yearInt,monthInt,dayInt,yearInt1,monthInt1;
    private String selectedDay,selectedMonth;
    private String Fmonth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  //无title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);  //全屏
        setContentView(R.layout.activity_glycolipid);
        initView();
        initDate();
    }
    private void initDate() {
        //获取当前时间
        isDay=true;
        Date time = Calendar.getInstance().getTime();
        currentYear=time.getYear() + 1900 + "年";
        currentMonth=time.getMonth() + 1 + "月";
        currentDay=time.getDate()+"日";
        currentdate=currentYear+" "+currentMonth+" "+currentDay;
        tv_date.setText(currentdate);
        selectedMonth=TimeStampUtil.currMonth(System.currentTimeMillis());

        Fmonth = selectedMonth;
        GlycolipidConst.month = Fmonth;

        args.putCharSequence("currmonth",selectedMonth);


        fragmentManager=getFragmentManager();
        realtimeFragment=new GlycolipidFragmentRealTime();
        //天
        dayFragment=new GlycolipidFragmentDay();
        dayFragment.setArguments(args);
        //月
        monFragment=new GlycolipidFragmentMonth();
        monFragment.setArguments(args);

        fragmentManager.beginTransaction().add(R.id.ll_fg_body,realtimeFragment).commit();
        currentFragment=realtimeFragment;
    }
    private void initView() {
        rl_top_left_return= (RelativeLayout) findViewById(R.id.rl_top_left_return);
        iv_top_right= (ImageView) findViewById(R.id.iv_top_right);
        iv_glycolipid_set= (ImageView) findViewById(R.id.iv_glycolipid_set);
        ll_switch_background= (LinearLayout) findViewById(R.id.ll_switch_background);
        ll_switch_date= (LinearLayout) findViewById(R.id.ll_switch_date);
        sc_gly_scroll= (ScrollView)findViewById(R.id.sc_gly_scroll);
        tv_date= (TextView) findViewById(R.id.tv_date);//显示时间
        tv_state= (TextView) findViewById(R.id.tv_state);//设备连接状态
        tv_switch_realtime= (TextView) findViewById(R.id.tv_switch_realtime);
        tv_switch_history= (TextView) findViewById(R.id.tv_switch_history);
        pb_progress= (ProgressBar) findViewById(R.id.pb_progress);
        rl_switch_date= (RelativeLayout) findViewById(R.id.rl_switch_date);
        rl_set= (RelativeLayout) findViewById(R.id.rl_set);
        tv_switch_day= (TextView) findViewById(R.id.tv_switch_day);
        tv_switch_month= (TextView) findViewById(R.id.tv_switch_month);

        tv_switch_month.setOnClickListener(this);
        tv_switch_day.setOnClickListener(this);
        tv_switch_history.setOnClickListener(this);
        tv_switch_realtime.setOnClickListener(this);
        iv_glycolipid_set.setOnClickListener(this);
        rl_top_left_return.setOnClickListener(this);
        iv_top_right.setOnClickListener(this);
        tv_date.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_switch_realtime:
                currentdate=currentYear+" "+currentMonth+" "+currentDay;
                tv_date.setText(currentdate);
                rl_switch_date.setVisibility(View.INVISIBLE);
                rl_set.setVisibility(View.VISIBLE);
                sc_gly_scroll.setBackground(getResources().getDrawable(R.color.white));
                ll_switch_background.setBackground(getResources().getDrawable(R.drawable.switch_sportorsleep));
                tv_switch_realtime.setTextColor(Color.WHITE);
                tv_switch_history.setTextColor(Color.parseColor("#00AEB5"));
                if (realtimeFragment.isAdded()){
                    fragmentManager.beginTransaction().hide(currentFragment).show(realtimeFragment).commit();
                }else{
                    fragmentManager.beginTransaction().hide(currentFragment).add(R.id.ll_fg_body,realtimeFragment,"realtimeFragment").commit();
                }
                currentFragment=realtimeFragment;
                isDay=true;
                break;
            case R.id.tv_switch_history:
                currentdate=currentYear+" "+currentMonth;
                tv_date.setText(currentdate);
                rl_switch_date.setVisibility(View.VISIBLE);
                rl_switch_date.setBackground(null);
                ll_switch_date.setBackground(getResources().getDrawable(R.drawable.switch_bracelete_day));
                rl_set.setVisibility(View.INVISIBLE);
                sc_gly_scroll.setBackground(null);
                ll_switch_background.setBackground(getResources().getDrawable(R.drawable.switch_sleeporsports));
                tv_switch_history.setTextColor(Color.WHITE);
                tv_switch_realtime.setTextColor(Color.parseColor("#00AEB5"));
                if (dayFragment.isAdded()){
                    fragmentManager.beginTransaction().hide(currentFragment).show(dayFragment).commit();
                }else{
                    fragmentManager.beginTransaction().hide(currentFragment).add(R.id.ll_fg_body,dayFragment,"dayFragment").commit();
                }
                currentFragment=dayFragment;
                isDay=false;
                break;
            case R.id.tv_switch_day:
                currentdate=currentYear+" "+currentMonth;
                tv_date.setText(currentdate);
                rl_switch_date.setVisibility(View.VISIBLE);
                rl_switch_date.setBackground(null);
                ll_switch_date.setBackground(getResources().getDrawable(R.drawable.switch_bracelete_day));
                rl_set.setVisibility(View.INVISIBLE);
                sc_gly_scroll.setBackground(null);
                ll_switch_background.setBackground(getResources().getDrawable(R.drawable.switch_sleeporsports));
                tv_switch_history.setTextColor(Color.WHITE);
                tv_switch_realtime.setTextColor(Color.parseColor("#00AEB5"));
                if (dayFragment.isAdded()){
                    fragmentManager.beginTransaction().hide(currentFragment).show(dayFragment).commit();
                }else{
                    fragmentManager.beginTransaction().hide(currentFragment).add(R.id.ll_fg_body,dayFragment,"dayFragment").commit();
                }
                currentFragment=dayFragment;
                isDay=false;
                break;
            case R.id.tv_switch_month:
                currentdate=currentYear+" "+currentMonth;
                tv_date.setText(currentdate);

                rl_switch_date.setVisibility(View.VISIBLE);
                rl_switch_date.setBackground(getResources().getDrawable(R.color.white));
                ll_switch_date.setBackground(getResources().getDrawable(R.drawable.switch_date2));
                rl_set.setVisibility(View.INVISIBLE);
                sc_gly_scroll.setBackground(getResources().getDrawable(R.color.white));
                ll_switch_background.setBackground(getResources().getDrawable(R.drawable.switch_sleeporsports));
                tv_switch_history.setTextColor(Color.WHITE);
                tv_switch_realtime.setTextColor(Color.parseColor("#00AEB5"));
                if (monFragment.isAdded()){
                    fragmentManager.beginTransaction().hide(currentFragment).show(monFragment).commit();
                }else{
                    fragmentManager.beginTransaction().hide(currentFragment).add(R.id.ll_fg_body,monFragment,"monFragment").commit();
                }
                currentFragment=monFragment;
                isDay=false;
                break;
            case R.id.iv_glycolipid_set:
               /* Intent intent=new Intent(GlycolipidActivity.this,DeviceSettingActivity.class);
                intent.putExtra("type","糖脂五项");
                startActivity(intent);*/
                break;
            case R.id.rl_top_left_return:
                onBackPressed();
                finish();
                break;
            case R.id.iv_top_right:
                Intent intent1=new Intent(GlycolipidActivity.this,DataInputGlycolipid.class);
                startActivity(intent1);
                break;
            case R.id.tv_date:
                dialogDate();

                break;
        }

    }

    private void dialogDate() {
        dateString=tv_date.getText().toString().replace(" ", "");
        ChangeDateDialog dateDialog=new ChangeDateDialog(GlycolipidActivity.this,isDay);
        Log.d("无空格日期", dateString + "");
        if (isDay){
            yearInt = Integer.valueOf(dateString.substring(0, 4));
            String[] ss=dateString.split("月");
            monthInt=Integer.valueOf(ss[0].substring(5, ss[0].length()));
            dayInt=Integer.valueOf(ss[1].substring(0, ss[1].length()-1));

            Log.d("year", yearInt+"");
            Log.d("month", monthInt+"");
            Log.d("day",dayInt+"");
            dateDialog.setDate(yearInt,monthInt,dayInt);
        }else{
            yearInt1 = Integer.valueOf(dateString.substring(0, 4));
            monthInt1 = Integer.valueOf(dateString.substring(5, dateString.length() - 1));
            dateDialog.setDate(yearInt1,monthInt1);
            Log.d("year", yearInt1+"");
            Log.d("month", monthInt1+"");
        }
        dateDialog.show();

        dateDialog.setBirthdayListener(new ChangeDateDialog.OnBirthListener() {
            @Override
            public void onClick(String year, String month, String day) {
                if ((year + "年" + month + "月").equals(dateString)) {
                    return;
                }
                GlycolipidConst.month=year+"-"+TimeStampUtil.singleIntToDoubleString(Integer.valueOf(month)); //yyyy-MM
                if (isDay){
                    tv_date.setText(year+"年 "+month+"月 "+day+"日");
                }else {
                    tv_date.setText(year+"年 "+month+"月");
                }
                if (currentFragment==dayFragment){
                    GlycolipidFragmentDay df= (GlycolipidFragmentDay) getFragmentManager().findFragmentByTag("dayFragment");
                    df.update();
                }else if (currentFragment==monFragment){

                }
            }
        });
    }
}
