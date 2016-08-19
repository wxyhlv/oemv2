package com.capitalbio.oemv2.myapplication.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.Bean.BloodPressureBean;
import com.capitalbio.oemv2.myapplication.Bean.BodyFatBean2;
import com.capitalbio.oemv2.myapplication.Const.BodyFatConst;
import com.capitalbio.oemv2.myapplication.Devices.BodyFatDevices.src.com.bayh.sdk.ble.bean.BodyFatSaid;
import com.capitalbio.oemv2.myapplication.FirstPeriod.Utils.HTG;
import com.capitalbio.oemv2.myapplication.FirstPeriod.Utils.ToastMaster;
import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.Utils.FatRule;
import com.capitalbio.oemv2.myapplication.Utils.PreferencesUtils;
import com.capitalbio.oemv2.myapplication.Utils.TimeStampCreater;
import com.capitalbio.oemv2.myapplication.Utils.TimeStampUtil;
import com.capitalbio.oemv2.myapplication.Utils.UpLoader;
import com.capitalbio.oemv2.myapplication.Utils.UpLoaderAsyncTask;
import com.capitalbio.oemv2.myapplication.dataFormatCheck.BodyfatFormatCheck;
import com.capitalbio.oemv2.myapplication.dialog.TimeSelectDialog;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

/**
 * @author lzq
 * @Time 2015/12/22 10:45
 */
public class DatainputBodyfatActivity extends BaseActivity implements View.OnClickListener, UpLoaderAsyncTask.OnCallBack {

    //返回
    private RelativeLayout rlback;
    //保存
    private RelativeLayout rlsave;

    //时间选择
    private LinearLayout lltimeselect;

    //时间选择后的显示
    private TextView tvtimeshow;

    private String yearselect, monthselect, dayselect, hourselect, minuteselect;

    //体重
    private EditText ettizhong;

    //保存键是否可以点击
    private boolean isClick = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datainputbodyfat);
        initView();
        initEvent();
    }

    private void initView() {
        rlback = (RelativeLayout) this.findViewById(R.id.rl_datainputbodyfat_back);
        rlsave = (RelativeLayout) this.findViewById(R.id.rl_datainputbodyfat_save);

        lltimeselect = (LinearLayout) this.findViewById(R.id.ll_bodyfatinput_time);
        tvtimeshow = (TextView) this.findViewById(R.id.tv_bodyfatinput_testtime);

        yearselect = HTG.currYear()+"";

        int currmonth = HTG.currMonth();
        String currmonth_s = currmonth+"";
        if(HTG.isSingleBitInt(currmonth)){
            currmonth_s = "0"+currmonth_s;
        }
        monthselect = currmonth_s;

        int currday = HTG.currDay();
        String currday_s = currday+"";
        if(HTG.isSingleBitInt(currday)){
            currday_s = "0"+currday_s;
        }
        dayselect = currday_s;

        int currhour = HTG.currHour();
        String currhour_s = currhour+"";
        if(HTG.isSingleBitInt(currhour)){
            currhour_s = "0"+currhour_s;
        }
        hourselect = currhour_s;

        int currminute = HTG.currMinute();
        String currminute_s = currminute+"";
        if(HTG.isSingleBitInt(currminute)){
            currminute_s = "0"+currminute_s;
        }
        minuteselect = currminute_s;

        tvtimeshow.setText(HTG.currYear()+"-"+currmonth_s+"-"+currday_s+" "+currhour_s+":"+currminute_s);

        ettizhong = (EditText) this.findViewById(R.id.et_bodyfatinput_tz);
    }

    private void initEvent() {
        rlback.setOnClickListener(this);
        rlsave.setOnClickListener(this);
        lltimeselect.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //返回
            case R.id.rl_datainputbodyfat_back:
                this.finish();
                break;
            //保存
            case R.id.rl_datainputbodyfat_save:
                if(isClick){
                    isClick = false;
                    save();
                }
                break;
            //时间选择
            case R.id.ll_bodyfatinput_time:
                TimeSelectDialog mtimeselect = new TimeSelectDialog(
                        DatainputBodyfatActivity.this);
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
                        tvtimeshow.setText(HTG.currYear() + "-" + month + "-" + day + " " + hour + ":" + minute);
                        yearselect = HTG.currYear()+"";
                        monthselect = month;
                        dayselect = day;
                        hourselect = hour;
                        minuteselect = minute;
                    }
                });
                break;
        }

    }

    //数据保存
    private void save() {
        if (yearselect == null || yearselect.equals("") || monthselect == null || monthselect.equals("") || dayselect == null || dayselect.equals("") || hourselect == null || hourselect.equals("") || minuteselect == null || minuteselect.equals("")) {
            ToastMaster.showToast(this,"请选择测量时间");
            isClick = true;
            return;
        }

        String tz = ettizhong.getText().toString();

        if(tz==null||tz.equals("")){
            ToastMaster.showToast(this,"请输入体重值");
            isClick = true;
            return ;
        }else{
            if(!BodyfatFormatCheck.isLegitimate(tz)){
                ToastMaster.showToast(this,"体重输入在1-150之间");
                isClick = true;
                return ;
            }
        }

        TimeStampCreater timeStampCreater = new TimeStampCreater();
        long testtime =  timeStampCreater.create(monthselect,dayselect,hourselect,minuteselect);

        BodyFatSaid bodyFatSaid = new BodyFatSaid();
        bodyFatSaid.setLongTime(testtime);
        bodyFatSaid.setWeight(Float.parseFloat(tz));

        UpLoader upLoader = new UpLoader(this);
        upLoader.setOnCallBack(this);
        upLoader.go(bodyFatSaid);

        //保存本地数据库
        DbManager dbManager = MyApplication.getDB();
        bodyFatSaid.setUserName(MyApplication.getInstance().getCurrentUserName());
        bodyFatSaid.setDataSource("手动录入");
        bodyFatSaid.setIsUpload(true);
        bodyFatSaid.setYmd(TimeStampUtil.currTime3(testtime));
        bodyFatSaid.setTestHour(TimeStampUtil.getHour(testtime) + "");
        bodyFatSaid.setTestMinute(TimeStampUtil.getDoubleDay(testtime) + "");

        //---------------
        String sex = PreferencesUtils.getString(context, "sex", "男");
        String age = PreferencesUtils.getString(context, "age", "20");
        String height = PreferencesUtils.getString(context,"height","175");
        String weightGrade = FatRule.getRate(Float.parseFloat(tz), BodyFatConst.TYPE_WEIGHT, sex, Integer.parseInt(age), Integer.valueOf(height), Float.parseFloat(tz));
        bodyFatSaid.setWeightGrade(weightGrade);
        bodyFatSaid.setBmi(FatRule.computeBmi(Float.parseFloat(tz), Float.parseFloat(height)));

        try {
            dbManager.saveOrUpdate(bodyFatSaid);
            Log.i("info", "===========本地保存数据======");
            //Toast.makeText(context,"保存成功！",Toast.LENGTH_SHORT).show();
            //finish();
        } catch (DbException e) {
            e.printStackTrace();
            Log.i("info", "===========本地保存数据异常======");
        }



    }

    @Override
    public void lesgo() {
        Log.i("info","=========开始");
    }

    @Override
    public void over(Object o) {
        Log.i("info","=========结束===="+(String)o);
        if(o!=null){
            String result = (String) o;
            if (result!=null&&!result.equals("")){
                JSONObject jsonObject = JSONObject.parseObject(result);
                if(jsonObject!=null){
                    if(jsonObject.containsKey("message")){
                        String message = jsonObject.getString("message");
                        if(message!=null&&message.equals("success")){
                            Toast.makeText(this, "保存成功", Toast.LENGTH_LONG).show();
                            delayHandler.sendEmptyMessageDelayed(0, 500);
                            return;
                        }
                    }
                }
            }
        }
        Toast.makeText(this,"保存失败",Toast.LENGTH_LONG).show();
        isClick = true;
    }

    //延时关闭当前activity ，优化用户体验
    private Handler delayHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            DatainputBodyfatActivity.this.finish();
        }
    };
}
