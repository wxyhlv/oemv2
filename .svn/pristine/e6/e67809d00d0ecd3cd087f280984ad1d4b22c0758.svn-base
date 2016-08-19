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
import com.capitalbio.oemv2.myapplication.FirstPeriod.Utils.HTG;
import com.capitalbio.oemv2.myapplication.FirstPeriod.Utils.ToastMaster;
import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.Utils.TimeStampCreater;
import com.capitalbio.oemv2.myapplication.Utils.TimeStampUtil;
import com.capitalbio.oemv2.myapplication.Utils.UpLoader;
import com.capitalbio.oemv2.myapplication.Utils.UpLoaderAsyncTask;
import com.capitalbio.oemv2.myapplication.dataFormatCheck.BloodPressFormatCheck;
import com.capitalbio.oemv2.myapplication.dialog.TimeSelectDialog;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

/**
 * @author lzq
 * @Time 2015/12/22 10:17
 */
public class DatainputBloodpressActivity extends BaseActivity implements View.OnClickListener, UpLoaderAsyncTask.OnCallBack {

    //返回
    private RelativeLayout rlback;
    //保存
    private RelativeLayout rlsave;

    //时间选择
    private LinearLayout lltimeselect;

    //选择时间的显示
    private TextView tvtimeselect;

    private String yearselect, monthselect, dayselect, hourselect, minuteselect;

    //舒张压
    private EditText etszy;

    //收缩压
    private EditText etssy;

    //心率
    private EditText etxl;

    //保存按钮是否可以点击
    private boolean isClick = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datainputbloodpress);

        initView();
        initEvent();
    }

    private void initView() {
        rlback = (RelativeLayout) this.findViewById(R.id.rl_datainputbloodpress_back);
        rlsave = (RelativeLayout) this.findViewById(R.id.rl_datainputbloodpress_save);

        lltimeselect = (LinearLayout) this.findViewById(R.id.ll_bloodpressinput_time);
        tvtimeselect = (TextView) this.findViewById(R.id.tv_bloodpressinput_testtime);



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

        tvtimeselect.setText(HTG.currYear()+"-"+currmonth_s+"-"+currday_s+" "+currhour_s+":"+currminute_s);


        etszy = (EditText) this.findViewById(R.id.et_bloodpressinput_szy);
        etssy = (EditText) this.findViewById(R.id.et_bloodpressinput_ssy);
        etxl = (EditText) this.findViewById(R.id.et_bloodpressinput_xl);
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
            case R.id.rl_datainputbloodpress_back:
                this.finish();
                break;
            //保存
            case R.id.rl_datainputbloodpress_save:
                if(isClick){
                    isClick = false;
                    save();
                }
                break;
            //时间选择
            case R.id.ll_bloodpressinput_time:
                TimeSelectDialog mtimeselect = new TimeSelectDialog(
                        DatainputBloodpressActivity.this);
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
                        tvtimeselect.setText(HTG.currYear() + "-" + month + "-" + day + " " + hour + ":" + minute);
                        yearselect = HTG.currYear() + "";
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
            ToastMaster.showToast(this, "请选择测量时间");
            isClick = true;
            return;
        }

        String szy = etszy.getText().toString();
        String ssy = etssy.getText().toString();
        String xl = etxl.getText().toString();



        if (ssy == null || ssy.equals("")) {
            ToastMaster.showToast(this, "请输入收缩压值");
            isClick = true;
            return;
        }else{
            if(!BloodPressFormatCheck.isLegitimate(ssy,BloodPressFormatCheck.SYS)){
                ToastMaster.showToast(this, "收缩压输入在1-300之间");
                isClick = true;
                return;
            }
        }

        if (szy == null || szy.equals("")) {
            ToastMaster.showToast(this, "请输入舒张压值");
            isClick = true;
            return;
        }else{
            if(!BloodPressFormatCheck.isLegitimate(szy,BloodPressFormatCheck.DIA)){
                ToastMaster.showToast(this, "舒展压输入在1-200之间");
                isClick = true;
                return;
            }
        }

        if (xl == null || xl.equals("")) {
            ToastMaster.showToast(this, "请输入心率值");
            isClick = true;
            return;
        }else{
            if(!BloodPressFormatCheck.isLegitimate(xl,BloodPressFormatCheck.HR)){
                ToastMaster.showToast(this, "心率输入在1-300之间");
                isClick = true;
                return;
            }
        }

        TimeStampCreater timeStampCreater = new TimeStampCreater();
        long timestamp = timeStampCreater.create(monthselect, dayselect, hourselect, minuteselect);

        BloodPressureBean bloodPressureBean = new BloodPressureBean();
        bloodPressureBean.setDiaBp(Integer.parseInt(szy));
        bloodPressureBean.setSysBp(Integer.parseInt(ssy));
        bloodPressureBean.setHeartRate(Integer.parseInt(xl));
        bloodPressureBean.setTime(timestamp);


        UpLoader upLoader = new UpLoader(this);
        upLoader.setOnCallBack(this);
        upLoader.go(bloodPressureBean);

        //保存本地数据库
        DbManager dbManager = MyApplication.getDB();
        bloodPressureBean.setUsername(MyApplication.getInstance().getCurrentUserName());
        bloodPressureBean.setDataSource("手动录入");
        bloodPressureBean.setTestDate(TimeStampUtil.currTime3(timestamp));
        bloodPressureBean.setTestHour(TimeStampUtil.getHour(timestamp) + "");
        bloodPressureBean.setTestMinute(TimeStampUtil.getMinute(timestamp) + "");
        bloodPressureBean.setIsUpload(true);
        try {
            dbManager.saveOrUpdate(bloodPressureBean);
            Log.i("info","===========本地保存数据======");
        } catch (DbException e) {
            e.printStackTrace();
            Log.i("info", "===========本地保存数据异常======");
        }

        try {
            BloodPressureBean bloodPressBean = MyApplication.getDB().selector(BloodPressureBean.class).findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }
        Log.i("info","======当前本地数据库第一条数据========"+ bloodPressureBean.toString());

    }

    @Override
    public void lesgo() {
        Log.i("info", "======保存血压==开始======");
    }

    @Override
    public void over(Object o) {
        Log.i("info", "======保存血压==结束======" + (String) o);
        if(o!=null){
            String result = (String) o;
            if (result!=null&&!result.equals("")){
                JSONObject jsonObject = JSONObject.parseObject(result);
                if(jsonObject!=null){
                    if(jsonObject.containsKey("message")){
                       String message = jsonObject.getString("message");
                        if(message!=null&&message.equals("success")){
                            Toast.makeText(this,"保存成功",Toast.LENGTH_LONG).show();
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

            DatainputBloodpressActivity.this.finish();
        }
    };
}
