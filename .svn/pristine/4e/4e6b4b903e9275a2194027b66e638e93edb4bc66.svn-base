package com.capitalbio.oemv2.myapplication.Fragment;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.capitalbio.oemv2.myapplication.Activity.DeviceSettingActivity;
import com.capitalbio.oemv2.myapplication.Base.IBodyFatDevicesCallBack;
import com.capitalbio.oemv2.myapplication.Const.BodyFatConst;
import com.capitalbio.oemv2.myapplication.Devices.BodyFatDevices.src.com.bayh.sdk.ble.bean.BodyFatSaid;
import com.capitalbio.oemv2.myapplication.Devices.Bracelete.DevicesDataObserver;
import com.capitalbio.oemv2.myapplication.FirstPeriod.Utils.ToastMaster;
import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.Utils.FatRule;
import com.capitalbio.oemv2.myapplication.Utils.PreferencesUtils;
import com.capitalbio.oemv2.myapplication.View.views.BodyFat2View;
import com.capitalbio.oemv2.myapplication.View.views.BodyFatBalanceView;

import java.text.DecimalFormat;

/**
 * @author lzq
 * @Time 2015/12/14 14:09
 */
public class BodyFatFragmentMeasure extends BaseFragment implements IBodyFatDevicesCallBack, BodyFatBalanceView.OnValueChangingListen {

    public static final String TAG = "BodyFatFragmentMeasure";
    private static final int NOMAL_START = 0;
    public static final int BODYFAT_UNBIND_SUCCESSFULL = 101;

    private BodyFatBalanceView bfbv;
    //private BodyFat2View bf2v;

    //设置按钮
    private RelativeLayout rlsetting;

    //数据观察者
    private DevicesDataObserver devicesDataObserver = DevicesDataObserver.getObserver();

    private TextView tvweight;

    public static final int START = 1;
    public static final int END = 0;

    //UI
    private TextView tv_weight_value;
    private TextView tv_weight_des;

    private TextView tv_bmi_value;
    private TextView tv_bmi_des;

    private TextView tv_bmr_value;
    private TextView tv_bmr_des;

    private TextView tv_visceral_value;
    private TextView tv_visceral_des;

    private TextView tv_bone_value;
    private TextView tv_bone_des;

    private TextView tv_fat_value;
    private TextView tv_fat_des;

    private TextView tv_muscle_value;
    private TextView tv_muscle_des;

    private TextView tv_water_value;
    private TextView tv_water_des;

    //各项指标-圆
    private RelativeLayout rl_weight;
    private RelativeLayout rl_bmi;
    private RelativeLayout rl_bmr;
    private RelativeLayout rl_visceralgrade;
    private RelativeLayout rl_bonecontent;
    private RelativeLayout rl_fatcontent;
    private RelativeLayout rl_musclecontent;
    private RelativeLayout rl_watercontent;

    //绑定，连接，状态的提示
    private TextView tvpromote;


    @Override
    protected View loadViewLayout(LayoutInflater inflater, ViewGroup container) {
        View v = inflater.inflate(R.layout.fg_bodyfat_measure, null, false);

        devicesDataObserver.registerBodyFatDevicesCallBack(this);

        return v;
    }

    @Override
    protected void findViewById(View view) {
        bfbv = (BodyFatBalanceView) view.findViewById(R.id.bfbv);
        //bfbv.setOnChangingListener(this);
        //bf2v = (BodyFat2View) view.findViewById(R.id.bf2v);
        rlsetting = (RelativeLayout) view.findViewById(R.id.rl_fatsmeasure_setting);
        rlsetting.setOnClickListener(this);
        tvweight = (TextView) view.findViewById(R.id.tv_bodyfatmeasure_weight);

        tv_weight_value = (TextView) view.findViewById(R.id.tv_value_weight_measure);
        tv_weight_des = (TextView) view.findViewById(R.id.tv_grade_weight_measure);

        tv_bmi_value = (TextView) view.findViewById(R.id.tv_value_bmi_measure);
        tv_bmi_des = (TextView) view.findViewById(R.id.tv_grade_bmi_measure);

        tv_bmr_value = (TextView) view.findViewById(R.id.tv_value_bmr_measure);
        tv_bmr_des = (TextView) view.findViewById(R.id.tv_grade_bmr_measure);

        tv_visceral_value = (TextView) view.findViewById(R.id.tv_value_viscus_measure);
        tv_visceral_des = (TextView) view.findViewById(R.id.tv_grade_viscus_measure);

        tv_bone_value = (TextView) view.findViewById(R.id.tv_value_bone_measure);
        tv_bone_des = (TextView) view.findViewById(R.id.tv_grade_bone_measure);

        tv_fat_value = (TextView) view.findViewById(R.id.tv_value_fat_measure);
        tv_fat_des = (TextView) view.findViewById(R.id.tv_grade_fat_measure);

        tv_muscle_value = (TextView) view.findViewById(R.id.tv_value_muscle_measure);
        tv_muscle_des = (TextView) view.findViewById(R.id.tv_grade_muscle_measure);

        tv_water_value = (TextView) view.findViewById(R.id.tv_value_water_measure);
        tv_water_des = (TextView) view.findViewById(R.id.tv_grade_water_measure);

        rl_weight = (RelativeLayout) view.findViewById(R.id.rl_bodyfatmeasure_weight);
        rl_bmi = (RelativeLayout) view.findViewById(R.id.rl_bodyfatmeasure_bmi);
        rl_bmr = (RelativeLayout) view.findViewById(R.id.rl_bodyfatmeasure_bmr);
        rl_visceralgrade = (RelativeLayout) view.findViewById(R.id.rl_bodyfatmeasure_visceralgrade);
        rl_bonecontent = (RelativeLayout) view.findViewById(R.id.rl_bodyfatmeasure_bonecontent);
        rl_fatcontent = (RelativeLayout) view.findViewById(R.id.rl_bodyfatmeasure_fatcontent);
        rl_musclecontent = (RelativeLayout) view.findViewById(R.id.rl_bodyfatmeasure_musclecontent);
        rl_watercontent = (RelativeLayout) view.findViewById(R.id.rl_bodyfatmeasure_watercontent);

        tvpromote = (TextView) view.findViewById(R.id.tv_bodyfatmeasure_promote);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_fatsmeasure_setting:
                //设置按钮
                Intent to = new Intent();
                to.setClass(getActivity(), DeviceSettingActivity.class);
                to.putExtra("type", "体脂秤");
                to.putExtra("currentDevices", "body_fat");
                startActivityForResult(to, NOMAL_START);
                break;
            case R.id.rl_bodyfatmeasure_bmi:
                //Toast.makeText(getActivity(), R.string.describe_bmi, Toast.LENGTH_LONG).show();
                View v_bmi = LayoutInflater.from(getActivity()).inflate(R.layout.popwindow_prompt,null);
                TextView tv_bmi = (TextView) v_bmi.findViewById(R.id.tv_popwindow_prompt);
                tv_bmi.setText(R.string.describe_bmi);
                PopupWindow pw_bmi = new PopupWindow(v_bmi, ActionBar.LayoutParams.WRAP_CONTENT,ActionBar.LayoutParams.WRAP_CONTENT,true);
                pw_bmi.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_popwindow_prompt));
                pw_bmi.showAtLocation(rl_bmi, Gravity.CENTER,0,0);
                break;
            case R.id.rl_bodyfatmeasure_bmr:
                //Toast.makeText(getActivity(), R.string.describe_bmr, Toast.LENGTH_LONG).show();
                View v_bmr = LayoutInflater.from(getActivity()).inflate(R.layout.popwindow_prompt,null);
                TextView tv_bmr = (TextView) v_bmr.findViewById(R.id.tv_popwindow_prompt);
                tv_bmr.setText(R.string.describe_bmr);
                PopupWindow pw_bmr = new PopupWindow(v_bmr, ActionBar.LayoutParams.WRAP_CONTENT,ActionBar.LayoutParams.WRAP_CONTENT,true);
                pw_bmr.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_popwindow_prompt));
                pw_bmr.showAtLocation(rl_bmr, Gravity.CENTER,0,0);
                break;
            case R.id.rl_bodyfatmeasure_visceralgrade:
                //Toast.makeText(getActivity(), R.string.describe_visceralgrade, Toast.LENGTH_LONG).show();
                View v_visceralgrade = LayoutInflater.from(getActivity()).inflate(R.layout.popwindow_prompt,null);
                TextView tv_visceralgrade = (TextView) v_visceralgrade.findViewById(R.id.tv_popwindow_prompt);
                tv_visceralgrade.setText(R.string.describe_visceralgrade);
                PopupWindow pw_visceralgrade = new PopupWindow(v_visceralgrade, ActionBar.LayoutParams.WRAP_CONTENT,ActionBar.LayoutParams.WRAP_CONTENT,true);
                pw_visceralgrade.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_popwindow_prompt));
                pw_visceralgrade.showAtLocation(rl_visceralgrade, Gravity.CENTER,0,0);
                break;
            case R.id.rl_bodyfatmeasure_bonecontent:
                //Toast.makeText(getActivity(), R.string.describe_bonecontent, Toast.LENGTH_LONG).show();
                View v_bonecontent = LayoutInflater.from(getActivity()).inflate(R.layout.popwindow_prompt,null);
                TextView tv_bonecontent = (TextView) v_bonecontent.findViewById(R.id.tv_popwindow_prompt);
                tv_bonecontent.setText(R.string.describe_bonecontent);
                PopupWindow pw_bonecontent = new PopupWindow(v_bonecontent, ActionBar.LayoutParams.WRAP_CONTENT,ActionBar.LayoutParams.WRAP_CONTENT,true);
                pw_bonecontent.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_popwindow_prompt));
                pw_bonecontent.showAtLocation(rl_bonecontent, Gravity.CENTER,0,0);
                break;
            case R.id.rl_bodyfatmeasure_fatcontent:
                //Toast.makeText(getActivity(), R.string.describe_fatcontent, Toast.LENGTH_LONG).show();
                View v_fatcontent = LayoutInflater.from(getActivity()).inflate(R.layout.popwindow_prompt,null);
                TextView tv_fatcontent = (TextView) v_fatcontent.findViewById(R.id.tv_popwindow_prompt);
                tv_fatcontent.setText(R.string.describe_fatcontent);
                PopupWindow pw_fatcontent = new PopupWindow(v_fatcontent, ActionBar.LayoutParams.WRAP_CONTENT,ActionBar.LayoutParams.WRAP_CONTENT,true);
                pw_fatcontent.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_popwindow_prompt));
                pw_fatcontent.showAtLocation(rl_fatcontent, Gravity.CENTER,0,0);
                break;
            case R.id.rl_bodyfatmeasure_musclecontent:
                //Toast.makeText(getActivity(), R.string.describe_musclecontent, Toast.LENGTH_LONG).show();
                View v_musclecontent = LayoutInflater.from(getActivity()).inflate(R.layout.popwindow_prompt,null);
                TextView tv_musclecontent = (TextView) v_musclecontent.findViewById(R.id.tv_popwindow_prompt);
                tv_musclecontent.setText(R.string.describe_musclecontent);
                PopupWindow pw_musclecontent = new PopupWindow(v_musclecontent, ActionBar.LayoutParams.WRAP_CONTENT,ActionBar.LayoutParams.WRAP_CONTENT,true);
                pw_musclecontent.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_popwindow_prompt));
                pw_musclecontent.showAtLocation(rl_musclecontent, Gravity.CENTER,0,0);
                break;
            case R.id.rl_bodyfatmeasure_watercontent:
                //Toast.makeText(getActivity(), R.string.describe_watercontent, Toast.LENGTH_LONG).show();
                View v_watercontent = LayoutInflater.from(getActivity()).inflate(R.layout.popwindow_prompt,null);
                TextView tv_watercontent = (TextView) v_watercontent.findViewById(R.id.tv_popwindow_prompt);
                tv_watercontent.setText(R.string.describe_watercontent);
                PopupWindow pw_watercontent = new PopupWindow(v_watercontent, ActionBar.LayoutParams.WRAP_CONTENT,ActionBar.LayoutParams.WRAP_CONTENT,true);
                pw_watercontent.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_popwindow_prompt));
                pw_watercontent.showAtLocation(rl_watercontent, Gravity.CENTER,0,0);
                break;

        }
    }

    @Override
    protected void processLogic() {
        rl_bmi.setOnClickListener(this);
        rl_bmr.setOnClickListener(this);
        rl_visceralgrade.setOnClickListener(this);
        rl_bonecontent.setOnClickListener(this);
        rl_fatcontent.setOnClickListener(this);
        rl_musclecontent.setOnClickListener(this);
        rl_watercontent.setOnClickListener(this);
    }


    //设备采集数据回调
    @Override
    public void devicesMeasureStartCallBack() {
        Log.i("info", "==========================devicesMeasureStartCallBack====");
        Message msg = new Message();
        msg.what = START;
        handler.sendMessage(msg);
    }

    @Override
    public void devicesMeasureMeasureComepleteCallBack(BodyFatSaid bodyFatSaid) {

        Log.i("info", "==========================devicesMeasureMeasureComepleteCallBack====" + "weight is " + bodyFatSaid.getWeight()+"==什么线程=="+Thread.currentThread().getName());

        Message msg = new Message();
        msg.obj = bodyFatSaid;
        msg.what = END;
        handler.sendMessage(msg);

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case START:
                    //开始
                    bfbv.start(25000);
                    //bf2v.startFreedom(20000);
                    tvpromote.setText("测量中...");
                    tvweight.setText("0.0");
                    resetCircle();
                    break;
                case END:
                    //结束
                    BodyFatSaid bodyFatSaid = (BodyFatSaid) msg.obj;
                    Log.i("info", "===============END===============" + "weight is " + bodyFatSaid.getWeight()+"==什么线程=="+Thread.currentThread().getName());
                    refreshUI(bodyFatSaid);
                    bfbv.stop(bodyFatSaid.getWeight());
                    //bf2v.stopValue(bodyFatSaid.getWeight());
                    tvweight.setText(""+bodyFatSaid.getWeight());
                    //如果不是光脚
                    if (!bodyFatSaid.isBarefoot()) {
                        ToastMaster.showToast(getActivity(), "请光脚测量");
                    }
                    break;
            }
        }
    };

    //更新UI
    private void refreshUI(BodyFatSaid bodyFatSaid) {
        if (bodyFatSaid == null) {
            return;
        }

        Log.i("info", "=========bmi=========" + bodyFatSaid.getBmi());
        Log.i("info", "=========bmr=========" + bodyFatSaid.getKcal());
        Log.i("info", "=========vis=========" + bodyFatSaid.getVisceraFat());
        Log.i("info", "=========bone=========" + bodyFatSaid.getBone());
        Log.i("info", "=========fat=========" + bodyFatSaid.getFat());
        Log.i("info", "=========mus=========" + bodyFatSaid.getMuscle());
        Log.i("info", "=========wat=========" + bodyFatSaid.getWater());

        String sex = PreferencesUtils.getString(getActivity(), "sex");
        int age = Integer.parseInt(PreferencesUtils.getString(getActivity(), "age"));
        int height = Integer.parseInt(PreferencesUtils.getString(getActivity(), "height"));

        tv_weight_value.setText(bodyFatSaid.getWeight() + "");
        float weight = bodyFatSaid.getWeight();
        tv_weight_des.setText(FatRule.getRate(weight, BodyFatConst.TYPE_WEIGHT, sex, age, height, bodyFatSaid.getWeight()));

        tv_bmi_value.setText(bodyFatSaid.getBmi());
        String bmi = bodyFatSaid.getBmi();
        if (bmi != null && !bmi.equals("")) {
            tv_bmi_des.setText(FatRule.getRate(weight, BodyFatConst.TYPE_BMI, sex, age, height, Float.parseFloat(bodyFatSaid.getBmi())));
        }

        if(bodyFatSaid.getKcal()==0){
            tv_bmr_value.setText("");
            tv_bmr_des.setText("----");
        }else{
            tv_bmr_value.setText(bodyFatSaid.getKcal()+"");
            tv_bmr_des.setText(FatRule.getRate(weight, BodyFatConst.TYPE_BMR, sex, age, height, bodyFatSaid.getKcal()));
        }

        if(bodyFatSaid.getVisceraFat()==0){
            tv_visceral_value.setText("");
            tv_visceral_des.setText("----");
        }else{
            tv_visceral_value.setText(bodyFatSaid.getVisceraFat() + "");
            tv_visceral_des.setText(FatRule.getRate(weight, BodyFatConst.TYPE_VISCERAL_LEVAL, sex, age, height, bodyFatSaid.getVisceraFat()));
        }

        if(bodyFatSaid.getBone()==0){
            tv_bone_value.setText("");
            tv_bone_des.setText("----");
        }else{
            tv_bone_value.setText(bodyFatSaid.getBone() + "");
            tv_bone_des.setText(FatRule.getRate(weight, BodyFatConst.TYPE_BONECONTENT, sex, age, height, bodyFatSaid.getBone()));
        }

        if(bodyFatSaid.getFat()==0){
            tv_fat_value.setText("");
            tv_fat_des.setText("----");
        }else{
            tv_fat_value.setText(bodyFatSaid.getFat() + "%");
            tv_fat_des.setText(FatRule.getRate(weight, BodyFatConst.TYPE_FAT_RATION, sex, age, height, bodyFatSaid.getFat()));
        }

        if(bodyFatSaid.getMuscle()==0){
            tv_muscle_value.setText("");
            tv_muscle_des.setText("----");
        }else{
            tv_muscle_value.setText(bodyFatSaid.getMuscle() + "%");
            tv_muscle_des.setText(FatRule.getRate(weight, BodyFatConst.TYPE_MUSLE_CONTENT, sex, age, height, bodyFatSaid.getMuscle()/100));
            Log.i("info","============???================"+FatRule.getRate(weight, BodyFatConst.TYPE_MUSLE_CONTENT, sex, age, height, bodyFatSaid.getMuscle()/100));
        }

        if(bodyFatSaid.getWater()==0){
            tv_water_value.setText("");
            tv_water_des.setText("----");
        }else{
            //Log.i("info","============"+bodyFatSaid.getWater()+"============"+FatRule.getRate(weight, BodyFatConst.TYPE_WATER_CONTENT, sex, age, height, bodyFatSaid.getWater())+"==="+sex+"==="+age+"==="+height+"==="+weight);
            tv_water_value.setText(bodyFatSaid.getWater() + "%");
            tv_water_des.setText(FatRule.getRate(weight, BodyFatConst.TYPE_WATER_CONTENT, sex, age, height, bodyFatSaid.getWater()/100));
        }


        //显示圆的颜色部分
        //体重
        String weight_level = FatRule.getRate(weight, BodyFatConst.TYPE_WEIGHT, sex, age, height, bodyFatSaid.getWeight());
        if (weight_level != null && !weight_level.equals("")) {
            if (weight_level.equals("标准")) {
                rl_weight.setBackgroundResource(R.drawable.bodyfat_middle);
            } else {
                rl_weight.setBackgroundResource(R.drawable.bodyfat_high);
            }
        } else {
            rl_weight.setBackgroundResource(R.drawable.bodyfat_high);
        }


        //bmi
        String bmi_value = bodyFatSaid.getBmi();
        Log.i("info","=============age============="+age);
        if (bmi_value != null && !bmi_value.equals("")) {
            String bmi_level = FatRule.getRate(weight, BodyFatConst.TYPE_BMI, sex, age, height, Float.parseFloat(bmi_value));
            if(bmi_level!=null){//小于18岁时，会等于null
                if (bmi_level.equals("标准")) {
                    rl_bmi.setBackgroundResource(R.drawable.bodyfat_middle);
                } else {
                    rl_bmi.setBackgroundResource(R.drawable.bodyfat_high);
                }
            }
        } else {
            rl_bmi.setBackgroundResource(R.drawable.bodyfat_high);
        }


        //bmr
        String bmr_level = bodyFatSaid.getBmrGrade();
        if (bmr_level != null && !bmr_level.equals("")) {
            if (bmr_level.equals("标准")) {
                rl_bmr.setBackgroundResource(R.drawable.bodyfat_middle);
            } else {
                rl_bmr.setBackgroundResource(R.drawable.bodyfat_high);
            }
        } else {
            rl_bmr.setBackgroundResource(R.drawable.bodyfat_high);
        }


        //内脏等级
        String visceral_level = bodyFatSaid.getVisceraGrade();
        if (visceral_level != null && !visceral_level.equals("")) {
            if (visceral_level.equals("标准")) {
                rl_visceralgrade.setBackgroundResource(R.drawable.bodyfat_middle);
            } else {
                rl_visceralgrade.setBackgroundResource(R.drawable.bodyfat_high);
            }
        } else {
            rl_visceralgrade.setBackgroundResource(R.drawable.bodyfat_high);
        }


        //骨含量
        String bone_level = bodyFatSaid.getBoneGrade();
        if (bone_level != null && !bone_level.equals("")) {
            if (bone_level.equals("标准")) {
                rl_bonecontent.setBackgroundResource(R.drawable.bodyfat_middle);
            } else {
                rl_bonecontent.setBackgroundResource(R.drawable.bodyfat_high);
            }
        } else {
            rl_bonecontent.setBackgroundResource(R.drawable.bodyfat_high);
        }


        //脂肪率
        String fat_level = bodyFatSaid.getFatGrade();
        if (fat_level != null && !fat_level.equals("")) {
            if (fat_level.equals("标准")) {
                rl_fatcontent.setBackgroundResource(R.drawable.bodyfat_middle);
            } else {
                rl_fatcontent.setBackgroundResource(R.drawable.bodyfat_high);
            }
        } else {
            rl_fatcontent.setBackgroundResource(R.drawable.bodyfat_high);
        }


        //肌肉含量             FatRule.getRate(weight, BodyFatConst.TYPE_MUSLE_CONTENT, sex, age, height, bodyFatSaid.getMuscle()/100)
        String muscle_level = FatRule.getRate(weight, BodyFatConst.TYPE_MUSLE_CONTENT, sex, age, height, bodyFatSaid.getMuscle() / 100);
        Log.i("info","=========muscle_level========="+muscle_level);
        if (muscle_level != null) {
            if (muscle_level.equals("标准")) {
                rl_musclecontent.setBackgroundResource(R.drawable.bodyfat_middle);
            } else {
                rl_musclecontent.setBackgroundResource(R.drawable.bodyfat_high);
            }
        } else {
            rl_musclecontent.setBackgroundResource(R.drawable.bodyfat_high);
        }


        //水分含量
        String water_level = FatRule.getRate(weight, BodyFatConst.TYPE_WATER_CONTENT, sex, age, height, bodyFatSaid.getWater() / 100);
        Log.i("info","=========water_level========="+water_level);
        if (water_level != null) {
            if (water_level.equals("标准")) {
                rl_watercontent.setBackgroundResource(R.drawable.bodyfat_middle);
            } else {
                rl_watercontent.setBackgroundResource(R.drawable.bodyfat_high);
            }
        } else {
            rl_watercontent.setBackgroundResource(R.drawable.bodyfat_high);
        }


        //
        tvpromote.setText("测量结束");


    }

    //八个圆状态重置
    private void resetCircle(){
        tv_weight_value.setText("0.0");
        tv_weight_des.setText("----");
        rl_weight.setBackgroundResource(R.drawable.bodyfat_middle);

        tv_bmi_value.setText("0.0");
        tv_bmi_des.setText("----");
        rl_bmi.setBackgroundResource(R.drawable.bodyfat_middle);

        tv_bmr_value.setText("0");
        tv_bmr_des.setText("----");
        rl_bmr.setBackgroundResource(R.drawable.bodyfat_middle);

        tv_visceral_value.setText("0");
        tv_visceral_des.setText("----");
        rl_visceralgrade.setBackgroundResource(R.drawable.bodyfat_middle);

        tv_bone_value.setText("0.0");
        tv_bone_des.setText("----");
        rl_bonecontent.setBackgroundResource(R.drawable.bodyfat_middle);

        tv_fat_value.setText("0.0%");
        tv_fat_des.setText("----");
        rl_fatcontent.setBackgroundResource(R.drawable.bodyfat_middle);

        tv_muscle_value.setText("0.0%");
        tv_muscle_des.setText("----");
        rl_musclecontent.setBackgroundResource(R.drawable.bodyfat_middle);

        tv_water_value.setText("0.0%");
        tv_water_des.setText("----");
        rl_watercontent.setBackgroundResource(R.drawable.bodyfat_middle);
    }

    //体脂称回调
    @Override
    public void changing(float v) {
        //Log.i(TAG,"========================changing");
        DecimalFormat decimalFormat = new DecimalFormat("#.0");
        tvweight.setText(decimalFormat.format(v));
    }

    @Override
    public void overtime() {
        //Log.i(TAG,"========================overtime");
        tvweight.setText("0.0");
        tvpromote.setText("测量结束");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NOMAL_START && resultCode == BODYFAT_UNBIND_SUCCESSFULL) {
            getActivity().finish();
        }

    }
}
