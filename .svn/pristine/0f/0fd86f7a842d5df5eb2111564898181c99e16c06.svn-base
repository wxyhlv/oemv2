package com.capitalbio.oemv2.myapplication.Fragment;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.capitalbio.oemv2.myapplication.Activity.DeviceSettingActivity;
import com.capitalbio.oemv2.myapplication.Base.IBloodPressDevicesCallBack;
import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.Bean.BloodPressureBean;
import com.capitalbio.oemv2.myapplication.Devices.Bracelete.DevicesDataObserver;
import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.Utils.BloodPressDataTransfer;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;
import com.capitalbio.oemv2.myapplication.Utils.PreferencesUtils;
import com.capitalbio.oemv2.myapplication.View.views.BloodpressRuler;
import com.capitalbio.oemv2.myapplication.View.views.SphygmomanometerTurntable;


public class BloodPressFragmentMeasure extends BaseFragment implements IBloodPressDevicesCallBack {

    private static final int NORMAL_START = 0;
    //数据观察者
    private DevicesDataObserver devicesDataObserver = DevicesDataObserver.getObserver();

    //设置按钮
    private RelativeLayout rlsetting;

    //转盘
    private SphygmomanometerTurntable st;

    //血压尺子
    private BloodpressRuler bprv;

    //收缩压显示
    private TextView tvsysbp;

    //舒张压显示
    private TextView tvdiabp;

    //心率显示
    private TextView tvwater;


    //测量过程提示语
    private TextView tvprompt;

    // 收缩压 圆
    private RelativeLayout rlsys;

    //舒张压 圆
    private RelativeLayout rldia;

    //心率
    private RelativeLayout rlheart;

    //最外层布局
    private LinearLayout llparent;

    //启动设置页面的返回结果
    public static final int BLOOD_PRESS_BIND_STATE_NO_CHANGE = 100;
    public static final int BLOOD_PRESS_UNBIND_SUCCESSFULL = 101;


    @Override
    protected View loadViewLayout(LayoutInflater inflater, ViewGroup container) {
        View v = inflater.inflate(R.layout.fg_bloodpress_measure, container, false);
        devicesDataObserver.registerBloodPressCallBack(this);
        return v;
    }

    @Override
    protected void findViewById(View view) {
        rlsetting = (RelativeLayout) view.findViewById(R.id.rl_bloodpressmeasure_setting);
        rlsetting.setOnClickListener(this);
        st = (SphygmomanometerTurntable) view.findViewById(R.id.st);

        bprv = (BloodpressRuler) view.findViewById(R.id.bprv);

        tvsysbp = (TextView) view.findViewById(R.id.tv_bloodpressmeasure_value_sysbp);
        tvdiabp = (TextView) view.findViewById(R.id.tv_bloodpressmeasure_value_diabp);
        tvwater = (TextView) view.findViewById(R.id.tv_bloodpressmeasure_value_water);
        tvprompt = (TextView) view.findViewById(R.id.tv_bloodpressmeasure_prompt);

        rlsys = (RelativeLayout) view.findViewById(R.id.rl_bloodpressmeasure_high);
        rlsys.setOnClickListener(this);
        rldia = (RelativeLayout) view.findViewById(R.id.rl_bloodpressmeasure_low);
        rldia.setOnClickListener(this);
        rlheart = (RelativeLayout) view.findViewById(R.id.rl_bloodpressmeasure_heartrate);
        rlheart.setOnClickListener(this);

        llparent = (LinearLayout) view.findViewById(R.id.ll_bloodpressmeasure);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //设置按钮
            case R.id.rl_bloodpressmeasure_setting:
                Intent to = new Intent();
                to.setClass(getActivity(), DeviceSettingActivity.class);
                to.putExtra("type", "血压计");
                to.putExtra("currentDevices", "blood_press");
                startActivityForResult(to, NORMAL_START);
                break;
            case R.id.rl_bloodpressmeasure_high:
                //Toast.makeText(getActivity(),R.string.describe_sys,Toast.LENGTH_LONG).show();
                //弹popwindow
                View popview_high = LayoutInflater.from(getActivity()).inflate(R.layout.popwindow_prompt,null);
                TextView textView_high = (TextView) popview_high.findViewById(R.id.tv_popwindow_prompt);
                textView_high.setText(R.string.describe_sys);
                PopupWindow popupWindow_high = new PopupWindow(popview_high, ActionBar.LayoutParams.WRAP_CONTENT,ActionBar.LayoutParams.WRAP_CONTENT,true);
                popupWindow_high.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_popwindow_prompt));
                popupWindow_high.showAtLocation(llparent, Gravity.CENTER,0,0);
                //popupWindow_high.showAsDropDown(rlsys,0,0);
                break;
            case R.id.rl_bloodpressmeasure_low:
                //Toast.makeText(getActivity(),R.string.describe_dia,Toast.LENGTH_LONG).show();
                //弹popwindow
                View popview_low = LayoutInflater.from(getActivity()).inflate(R.layout.popwindow_prompt,null);
                TextView textView_low = (TextView) popview_low.findViewById(R.id.tv_popwindow_prompt);
                textView_low.setText(R.string.describe_dia);
                PopupWindow popupWindow_low = new PopupWindow(popview_low, ActionBar.LayoutParams.WRAP_CONTENT,ActionBar.LayoutParams.WRAP_CONTENT,true);
                popupWindow_low.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_popwindow_prompt));
                //popupWindow_low.showAsDropDown(rldia,0,0);
                popupWindow_low.showAtLocation(rldia,Gravity.CENTER,0,0);
                break;
            case R.id.rl_bloodpressmeasure_heartrate:
                //Toast.makeText(getActivity(),R.string.describe_hearterate,Toast.LENGTH_LONG).show();
                //弹popwindow
                View popview_heartrate = LayoutInflater.from(getActivity()).inflate(R.layout.popwindow_prompt,null);
                TextView textView_heartrate = (TextView) popview_heartrate.findViewById(R.id.tv_popwindow_prompt);
                textView_heartrate.setText(R.string.describe_hearterate);
                PopupWindow popupWindow_heartrate = new PopupWindow(popview_heartrate, ActionBar.LayoutParams.WRAP_CONTENT,ActionBar.LayoutParams.WRAP_CONTENT,true);
                popupWindow_heartrate.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_popwindow_prompt));
                //popupWindow_heartrate.showAsDropDown(rlheart,0,0);
                popupWindow_heartrate.showAtLocation(rlheart,Gravity.CENTER,0,0);

                break;
        }
    }

    @Override
    protected void processLogic() {

    }

    @Override
    public void pressureDataCallBack(BloodPressureBean bloodPressureBean) {
        OemLog.log("pressureDataCallBack", "hight is " + bloodPressureBean.getSysBp() + ", low press is " + bloodPressureBean.getDiaBp()
                + ", heartrate is " + bloodPressureBean.getHeartRate());

        //Log.i("info", "===========血压计==============pressureDataCallBack======开始==="+Thread.currentThread().getName());

        if (bloodPressureBean != null) {
            Message message = new Message();
            message.obj = bloodPressureBean;
            startMeasureHandler.sendMessage(message);
            st.setOverTime(5000, new SphygmomanometerTurntable.OnOvertimeListener() {
                @Override
                public void overTime() {
                    tvprompt.setText("测量结束");
                    refreshCircle(null);
                }
            });
            st.startTurn();
        }


    }

    @Override
    public void resultDataCallBack(BloodPressureBean bloodPressureBean) {
        OemLog.log("resultDataCallBack", "hight is " + bloodPressureBean.getSysBp() + ", low press is " + bloodPressureBean.getDiaBp()
                + ", heartrate is " + bloodPressureBean.getHeartRate());
        //Log.i("info", "===========血压计==============resultDataCallBack======结束==="+Thread.currentThread().getName());

        if (bloodPressureBean != null) {
            Message message = new Message();
            message.obj = bloodPressureBean;
            stopMeasureHandler.sendMessage(message);
            st.stopTurn(BloodPressDataTransfer.discDegree(bloodPressureBean.getSysBp(), bloodPressureBean.getDiaBp()));
        }else{
            Message message = new Message();
            message.obj = null;
            stopMeasureHandler.sendMessage(message);
            st.stopTurn(SphygmomanometerTurntable.VALUE_EXCEPTION);
        }

    }

    private Handler startMeasureHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            BloodPressureBean bloodPressureBean = (BloodPressureBean) msg.obj;

            tvprompt.setText("测量中...");

            refreshDo(bloodPressureBean);
            resetCircle();
        }
    };

    private Handler stopMeasureHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            BloodPressureBean bloodPressureBean = (BloodPressureBean) msg.obj;

            tvprompt.setText("测量结束");

            refreshDo(bloodPressureBean);
            refreshCircle(bloodPressureBean);

            //本地保存测量结果(供首页提示使用)
            if(bloodPressureBean!=null){
                PreferencesUtils.putInt(MyApplication.getInstance(),"bloodpressvalue",BloodPressDataTransfer.discDegree(bloodPressureBean.getSysBp(), bloodPressureBean.getDiaBp()));
            }
        }
    };


    //测量过程中刷新显示数据
    private void refreshDo(BloodPressureBean bloodPressureBean) {
        //Log.i("info", "============refresh========" + Thread.currentThread().getName());

        if(bloodPressureBean==null){
            return;
        }

        bprv.setValue(bloodPressureBean.getSysBp());

        tvsysbp.setText(bloodPressureBean.getSysBp() + "");
        tvdiabp.setText(bloodPressureBean.getDiaBp() + "");
        tvwater.setText(bloodPressureBean.getHeartRate() + "");

    }

    //更新 三个圆
    private void refreshCircle(BloodPressureBean bloodPressureBean){
        if(bloodPressureBean==null){
            //异常
            rlsys.setBackgroundResource(R.drawable.bodyfat_high);
            rldia.setBackgroundResource(R.drawable.bodyfat_high);
            rlheart.setBackgroundResource(R.drawable.bodyfat_high);
            return;
        }
        int sys_level = BloodPressDataTransfer.parseSYS(bloodPressureBean.getSysBp());
        int dia_level = BloodPressDataTransfer.parseDIA(bloodPressureBean.getDiaBp());
        int heart_level = BloodPressDataTransfer.parseHeart(bloodPressureBean.getHeartRate(),getActivity());

        //收缩压
        if(sys_level==BloodPressDataTransfer.BLOODPRESS_HIGH||sys_level==BloodPressDataTransfer.BLOODPRESS_MIDDLE){
            rlsys.setBackgroundResource(R.drawable.bodyfat_middle);
        }else{
            rlsys.setBackgroundResource(R.drawable.bodyfat_high);
        }

        //舒张压
        if(dia_level==BloodPressDataTransfer.BLOODPRESS_HIGH||dia_level==BloodPressDataTransfer.BLOODPRESS_MIDDLE){
            rldia.setBackgroundResource(R.drawable.bodyfat_middle);
        }else{
            rldia.setBackgroundResource(R.drawable.bodyfat_high);
        }

        //心率
        if(heart_level==BloodPressDataTransfer.HEARTRATE_MIDDLE){
            rlheart.setBackgroundResource(R.drawable.bodyfat_middle);
        }else{
            rlheart.setBackgroundResource(R.drawable.bodyfat_high);
        }

    }

    //三个圆的状态重置
    private void resetCircle(){
        rlsys.setBackgroundResource(R.drawable.bodyfat_middle);
        rldia.setBackgroundResource(R.drawable.bodyfat_middle);
        rlheart.setBackgroundResource(R.drawable.bodyfat_middle);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        OemLog.log("BloodPressFragmentMeasure", "start activity requestcode is " + requestCode + ", resultcode is " + resultCode);
        if(resultCode == BLOOD_PRESS_UNBIND_SUCCESSFULL && requestCode == NORMAL_START) {
            getActivity().finish();
        }
    }
}
