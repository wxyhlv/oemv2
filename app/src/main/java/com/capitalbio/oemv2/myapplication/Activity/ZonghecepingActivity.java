package com.capitalbio.oemv2.myapplication.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.Bean.AirCatInfo;
import com.capitalbio.oemv2.myapplication.Bean.BloodPressureBean;
import com.capitalbio.oemv2.myapplication.Bean.ComprehensiveBean;
import com.capitalbio.oemv2.myapplication.Bean.SleepDataAllBean;
import com.capitalbio.oemv2.myapplication.Bean.ViewBean;
import com.capitalbio.oemv2.myapplication.Comprehensive.BloodPressureRuler;
import com.capitalbio.oemv2.myapplication.Comprehensive.HeartrateRuler;
import com.capitalbio.oemv2.myapplication.Comprehensive.MuscleContentRuler;
import com.capitalbio.oemv2.myapplication.Comprehensive.WeightRuler;
import com.capitalbio.oemv2.myapplication.Const.BodyFatConst;
import com.capitalbio.oemv2.myapplication.Devices.BodyFatDevices.src.com.bayh.sdk.ble.bean.BodyFatSaid;
import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.Utils.BloodPressDataTransfer;
import com.capitalbio.oemv2.myapplication.Utils.ComprehensiveEvaluationUtil;
import com.capitalbio.oemv2.myapplication.Utils.FatRule;
import com.capitalbio.oemv2.myapplication.Utils.PreferencesUtils;
import com.capitalbio.oemv2.myapplication.Utils.RateOfFatRuler;
import com.capitalbio.oemv2.myapplication.Utils.SleepRuler;
import com.capitalbio.oemv2.myapplication.View.StageView;
import com.capitalbio.oemv2.myapplication.View.views.RingView;
import com.capitalbio.oemv2.myapplication.View.views.WindowScrollView;

public class ZonghecepingActivity extends BaseActivity implements WindowScrollView.OnShowInWindowListener, View.OnClickListener, ComprehensiveEvaluationUtil.OnAllDataDownListener {

    //back
    private RelativeLayout rlback;

    //scrollview
    private WindowScrollView wsv;

    //舞台
    private StageView stageView;

    //----------------------------------------------------------圆环
    //脂肪
    private RingView rvfathigh;
    private TextView tvfathigh;
    private RingView rvfatlow;
    private TextView tvfatlow;

    //血压
    private RingView rvbloodpressurehigh;
    private TextView tvbloodpressurehigh;
    private RingView rvbloodpressurelow;
    private TextView tvbloodpressurelow;

    //血糖
    //private RingView rvbloodsugarhigh;
    //private TextView tvbloodsugarhigh;
    //private RingView rvbloodsugarlow;
    //private TextView tvbloodsugarlow;

    //血脂
    //private RingView rvbloodfathigh;
    //private TextView tvbloodfathigh;
    //private RingView rvbloodfatlow;
    //private TextView tvbloodfatlow;


    //--------------------------------------------------------garlic
    //睡眠
    private ImageView iv_garlic_sleep;
    private TextView tv_garlic_sleep;
    //肌肉
    private ImageView iv_garlic_muscle;
    private TextView tv_garlic_muscle;

    //心率
    private ImageView iv_garlic_heartrate;
    private TextView tv_garlic_heartrate;

    //血脂
    private ImageView iv_garlic_bloodfat;
    private TextView tv_garlic_bloodfat;

    //血糖
    private ImageView iv_garlic_bloodsugar;
    private TextView tv_garlic_bloodsugar;

    //血压
    private ImageView iv_garlic_bloodpressure;
    private TextView tv_garlic_bloodpressure;

    //-----------------------------------------------------
    //获取屏幕宽高
    private int width_window;
    private int height_window;

    //当前用户的性别
    private String sex;

    //当前用户的年龄
    private String age;

    //当前用户的身高
    private String height;


    //超标区域
    private TextView tv_banners_high;
    private RelativeLayout rl_rv_fat_high;
    private RelativeLayout rl_rv_bp_high;
    //private RelativeLayout rl_rv_bs_high;
    //private RelativeLayout rl_rv_bf_high;
    private LinearLayout ll_high_toast;

    private float ratio_fat_exceed;
    private float ratio_bp_exceed;

    //偏低区域
    private LinearLayout ll_zonghe_low;
    private TextView tv_banners_low;
    private RelativeLayout rl_rv_fat_low;
    private RelativeLayout rl_rv_bp_low;
    //private RelativeLayout rl_rv_bs_low;
    //private RelativeLayout rl_rv_bf_low;
    private LinearLayout ll_low_toast;

    private float ratio_fat_insufficient;
    private float ratio_bp_insufficient;

    //肌肉区域
    private TextView tv_muscle_des;
    private ImageView iv_muscle_des;

    //室内空气质量部分
    private TextView tv_indoorairquality_des;
    private LinearLayout ll_indoorairquality_des;

    //睡眠区域
    private TextView tv_sleep_des;
    private LinearLayout ll_sleep_des;

    //心率部分
    private TextView tv_heartrate_des;
    private LinearLayout ll_heartrate_des;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zongheceping);
        sah();
        find();
        init();

        logic();


    }

    private void logic() {
        ComprehensiveEvaluationUtil comprehensiveEvaluationUtil = new ComprehensiveEvaluationUtil(this, this);
        comprehensiveEvaluationUtil.pullAllData(MyApplication.getInstance().getAircatDeviceMac());
    }


    //
    private void find() {

        rlback = (RelativeLayout) this.findViewById(R.id.rl_zonghe_back);

        wsv = (WindowScrollView) this.findViewById(R.id.wsv_zonghe);


        stageView = (StageView) this.findViewById(R.id.stageview);

        rvfathigh = (RingView) this.findViewById(R.id.rv_zonghe_high_fat);
        rvbloodpressurehigh = (RingView) this.findViewById(R.id.rv_zonghe_high_bloodpressure);
        //rvbloodsugarhigh = (RingView) this.findViewById(R.id.rv_zonghe_high_bloodsugar);
        //rvbloodfathigh = (RingView) this.findViewById(R.id.rv_zonghe_high_bloodfat);

        rvfatlow = (RingView) this.findViewById(R.id.rv_zonghe_low_fat);
        rvbloodpressurelow = (RingView) this.findViewById(R.id.rv_zonghe_low_bp);
        //rvbloodsugarlow = (RingView) this.findViewById(R.id.rv_zonghe_low_bs);
        //rvbloodfatlow = (RingView) this.findViewById(R.id.rv_zonghe_low_bloodfat);


        tvfathigh = (TextView) this.findViewById(R.id.tv_zonghe_rv_high_fat);
        tvbloodpressurehigh = (TextView) this.findViewById(R.id.tv_zonghe_rv_high_bloodpressure);
        //tvbloodsugarhigh = (TextView) this.findViewById(R.id.tv_zonghe_rv_high_bloodsugar);
        //tvbloodfathigh = (TextView) this.findViewById(R.id.tv_zonghe_rv_high_bloodfat);

        tvfatlow = (TextView) this.findViewById(R.id.tv_zonghe_rv_low_fat);
        tvbloodpressurelow = (TextView) this.findViewById(R.id.tv_zonghe_rv_low_bp);
        //tvbloodsugarlow = (TextView) this.findViewById(R.id.tv_zonghe_rv_low_bs);
        //tvbloodfatlow = (TextView) this.findViewById(R.id.tv_zonghe_rv_low_bloodfat);


        //蒜头
        iv_garlic_sleep = (ImageView) this.findViewById(R.id.iv_zonghe_garlic_sleep);
        tv_garlic_sleep = (TextView) this.findViewById(R.id.tv_zonghe_garlic_sleep);
        iv_garlic_muscle = (ImageView) this.findViewById(R.id.iv_zonghe_garlic_muscle);
        tv_garlic_muscle = (TextView) this.findViewById(R.id.tv_zonghe_garlic_muscle);
        iv_garlic_heartrate = (ImageView) this.findViewById(R.id.iv_zonghe_garlic_heartrate);
        tv_garlic_heartrate = (TextView) this.findViewById(R.id.tv_zonghe_garlic_heartrate);
        iv_garlic_bloodfat = (ImageView) this.findViewById(R.id.iv_zonghe_garlic_bloodfat);
        tv_garlic_bloodfat = (TextView) this.findViewById(R.id.tv_zonghe_garlic_bloodfat);
        iv_garlic_bloodsugar = (ImageView) this.findViewById(R.id.iv_zonghe_garlic_bloodsugar);
        tv_garlic_bloodsugar = (TextView) this.findViewById(R.id.tv_zonghe_garlic_bloodsugar);
        iv_garlic_bloodpressure = (ImageView) this.findViewById(R.id.iv_zonghe_garlic_bloodpressure);
        tv_garlic_bloodpressure = (TextView) this.findViewById(R.id.tv_zonghe_garlic_bloodpressure);

        //超标区域
        tv_banners_high = (TextView) this.findViewById(R.id.tv_zonghe_banners_high);
        rl_rv_fat_high = (RelativeLayout) this.findViewById(R.id.rl_zonghe_rv_high_fat);
        rl_rv_bp_high = (RelativeLayout) this.findViewById(R.id.rl_zonghe_rv_high_bp);
        //rl_rv_bs_high = (RelativeLayout) this.findViewById(R.id.rl_zonghe_rv_high_bs);
        //rl_rv_bf_high = (RelativeLayout) this.findViewById(R.id.rl_zonghe_rv_high_bf);
        ll_high_toast = (LinearLayout) this.findViewById(R.id.ll_zonghe_high_toast);

        //偏低区域
        ll_zonghe_low = (LinearLayout) this.findViewById(R.id.ll_zonghe_low);
        tv_banners_low = (TextView) this.findViewById(R.id.tv_zonghe_banners_low);
        rl_rv_fat_low = (RelativeLayout) this.findViewById(R.id.rl_zonghe_rv_low_fat);
        rl_rv_bp_low = (RelativeLayout) this.findViewById(R.id.rl_zonghe_rv_low_bp);
        //rl_rv_bs_low = (RelativeLayout) this.findViewById(R.id.rl_zonghe_rv_low_bs);
        //rl_rv_bf_low = (RelativeLayout) this.findViewById(R.id.rl_zonghe_rv_low_bf);
        ll_low_toast = (LinearLayout) this.findViewById(R.id.ll_zonghe_low_toast);

        //肌肉
        tv_muscle_des = (TextView) this.findViewById(R.id.tv_zonghe_muscle_des);
        iv_muscle_des = (ImageView) this.findViewById(R.id.iv_zonghe_muscle_des);

        //室内空气质量部分
        tv_indoorairquality_des = (TextView) this.findViewById(R.id.tv_zonghe_indoorairquality_des);
        ll_indoorairquality_des = (LinearLayout) this.findViewById(R.id.ll_zonghe_indoorairquality_des);

        //睡眠区域
        tv_sleep_des = (TextView) this.findViewById(R.id.tv_zonghe_sleep_des);
        ll_sleep_des = (LinearLayout) this.findViewById(R.id.ll_zonghe_sleep_des);

        //心率区域
        tv_heartrate_des = (TextView) this.findViewById(R.id.tv_zonghe_heartrate_des);
        ll_heartrate_des = (LinearLayout) this.findViewById(R.id.ll_zonghe_heartrate_des);

    }


    private void init() {

        rlback.setOnClickListener(this);

        //注册屏幕出现回调
        wsv.regist(new ViewBean().setV(rvfathigh).setInWindow(false));
        wsv.regist(new ViewBean().setV(rvbloodpressurehigh).setInWindow(false));
        //wsv.regist(new ViewBean().setV(rvbloodsugarhigh).setInWindow(false));
        //wsv.regist(new ViewBean().setV(rvbloodfathigh).setInWindow(false));
        wsv.regist(new ViewBean().setV(rvfatlow).setInWindow(false));
        wsv.regist(new ViewBean().setV(rvbloodpressurelow).setInWindow(false));
        //wsv.regist(new ViewBean().setV(rvbloodsugarlow).setInWindow(false));
        //wsv.regist(new ViewBean().setV(rvbloodfatlow).setInWindow(false));

        wsv.setOnShowInWindowListener(this);


    }

    //初始化当前用户的性别，年龄，身高等属性
    private void sah() {
        sex = PreferencesUtils.getString(this, "sex");
        age = PreferencesUtils.getString(this, "age");
        height = PreferencesUtils.getString(this, "height");
    }


    //设置脂肪-高
    private void setFathigh(float f) {
        rvfathigh.setColor_fg_ring(Color.parseColor("#51C8B5"));
        rvfathigh.setCurr_progress(f);
        rvfathigh.setOnProgressChangeListener(new RingView.OnProgressChangeListener() {
            @Override
            public void onStart(float start) {
                tvfathigh.setText("超标" + (int) (start * 100) + "%");
            }

            @Override
            public void onChanging(float curr) {
                tvfathigh.setText("超标" + (int) (curr * 100) + "%");
            }

            @Override
            public void onStop(float stop) {
                tvfathigh.setText("超标" + (int) (stop * 100) + "%");
            }
        });
    }

    //设置血压-高
    private void setBloodPressurehigh(float f) {
        rvbloodpressurehigh.setColor_fg_ring(Color.parseColor("#844D77"));
        rvbloodpressurehigh.setCurr_progress(f);
        rvbloodpressurehigh.setOnProgressChangeListener(new RingView.OnProgressChangeListener() {
            @Override
            public void onStart(float start) {
                tvbloodpressurehigh.setText("超标" + (int) (start * 100) + "%");
            }

            @Override
            public void onChanging(float curr) {
                tvbloodpressurehigh.setText("超标" + (int) (curr * 100) + "%");
            }

            @Override
            public void onStop(float stop) {
                tvbloodpressurehigh.setText("超标" + (int) (stop * 100) + "%");
            }
        });
    }

    //设置血糖-高
    /*private void setBloodSugarhigh(float f) {
        rvbloodsugarhigh.setColor_fg_ring(Color.parseColor("#FD625E"));
        rvbloodsugarhigh.setCurr_progress(f);
        rvbloodsugarhigh.setOnProgressChangeListener(new RingView.OnProgressChangeListener() {
            @Override
            public void onStart(float start) {
                tvbloodsugarhigh.setText("超标" + (int) (start * 100) + "%");
            }

            @Override
            public void onChanging(float curr) {
                tvbloodsugarhigh.setText("超标" + (int) (curr * 100) + "%");
            }

            @Override
            public void onStop(float stop) {
                tvbloodsugarhigh.setText("超标" + (int) (stop * 100) + "%");
            }
        });
    }*/

    //设置血脂-高
    /*private void setBloodFathigh(float f) {
        rvbloodfathigh.setCurr_progress(f);
        rvbloodfathigh.setOnProgressChangeListener(new RingView.OnProgressChangeListener() {
            @Override
            public void onStart(float start) {
                tvbloodfathigh.setText("超标" + (int) (start * 100) + "%");
            }

            @Override
            public void onChanging(float curr) {
                tvbloodfathigh.setText("超标" + (int) (curr * 100) + "%");
            }

            @Override
            public void onStop(float stop) {
                tvbloodfathigh.setText("超标" + (int) (stop * 100) + "%");
            }
        });
    }*/

    //设置脂肪-低
    private void setFatlow(float f) {
        rvfatlow.setColor_fg_ring(Color.parseColor("#51C8B5"));
        rvfatlow.setCurr_progress(f);
        rvfatlow.setOnProgressChangeListener(new RingView.OnProgressChangeListener() {
            @Override
            public void onStart(float start) {
                tvfatlow.setText("超标" + (int) (start * 100) + "%");
            }

            @Override
            public void onChanging(float curr) {
                tvfatlow.setText("超标" + (int) (curr * 100) + "%");
            }

            @Override
            public void onStop(float stop) {
                tvfatlow.setText("超标" + (int) (stop * 100) + "%");
            }
        });
    }

    //设置血压-低
    private void setBloodPressurelow(float f) {
        rvbloodpressurelow.setColor_fg_ring(Color.parseColor("#844D77"));
        rvbloodpressurelow.setCurr_progress(f);
        rvbloodpressurelow.setOnProgressChangeListener(new RingView.OnProgressChangeListener() {
            @Override
            public void onStart(float start) {
                tvbloodpressurelow.setText("超标" + (int) (start * 100) + "%");
            }

            @Override
            public void onChanging(float curr) {
                tvbloodpressurelow.setText("超标" + (int) (curr * 100) + "%");
            }

            @Override
            public void onStop(float stop) {
                tvbloodpressurelow.setText("超标" + (int) (stop * 100) + "%");
            }
        });
    }

    //设置血糖-低
    /*private void setBloodSugarlow(float f) {
        rvbloodsugarlow.setColor_fg_ring(Color.parseColor("#FD625E"));
        rvbloodsugarlow.setCurr_progress(f);
        rvbloodsugarlow.setOnProgressChangeListener(new RingView.OnProgressChangeListener() {
            @Override
            public void onStart(float start) {
                tvbloodsugarlow.setText("超标" + (int) (start * 100) + "%");
            }

            @Override
            public void onChanging(float curr) {
                tvbloodsugarlow.setText("超标" + (int) (curr * 100) + "%");
            }

            @Override
            public void onStop(float stop) {
                tvbloodsugarlow.setText("超标" + (int) (stop * 100) + "%");
            }
        });
    }*/

    //设置血脂-低
    /*private void setBloodFatlow(float f) {
        rvbloodfatlow.setCurr_progress(f);
        rvbloodfatlow.setOnProgressChangeListener(new RingView.OnProgressChangeListener() {
            @Override
            public void onStart(float start) {
                tvbloodfatlow.setText("超标" + (int) (start * 100) + "%");
            }

            @Override
            public void onChanging(float curr) {
                tvbloodfatlow.setText("超标" + (int) (curr * 100) + "%");
            }

            @Override
            public void onStop(float stop) {
                tvbloodfatlow.setText("超标" + (int) (stop * 100) + "%");
            }
        });
    }*/


    @Override
    public void inWindow(View v) {
        switch (v.getId()) {
            case R.id.rv_zonghe_high_fat:
                setFathigh(ratio_fat_exceed);
                break;
            case R.id.rv_zonghe_high_bloodpressure:
                setBloodPressurehigh(ratio_bp_exceed);
                break;
            /*case R.id.rv_zonghe_high_bloodsugar:
                setBloodSugarhigh(0.09f);
                break;
            case R.id.rv_zonghe_high_bloodfat:
                setBloodFathigh(0.89f);
                break;*/

            case R.id.rv_zonghe_low_fat:
                setFatlow(ratio_fat_insufficient);
                break;
            case R.id.rv_zonghe_low_bp:
                setBloodPressurelow(ratio_bp_insufficient);
                break;
            /*case R.id.rv_zonghe_low_bs:
                setBloodSugarlow(0.09f);
                break;
            case R.id.rv_zonghe_low_bloodfat:
                setBloodFatlow(0.89f);
                break;*/

        }
    }

    @Override
    public void outWindow(View v) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_zonghe_back:
                this.finish();
                break;
        }
    }

    //开始获取上一周所有数据
    @Override
    public void start() {

    }

    //获取上一周数据结束
    @Override
    public void stop(ComprehensiveBean comprehensiveBean) {
        if(comprehensiveBean==null){
            //Log.i("info","=======综合bean=======空");
        }else{
            //Log.i("info", "=======综合bean=======非空");
        }
        refreshUI(comprehensiveBean);
    }

    //刷新UI
    private void refreshUI(ComprehensiveBean comprehensiveBean) {
        refreshStage(comprehensiveBean);
        refreshGarlic(comprehensiveBean);
        areaOfHigh(comprehensiveBean);
        areaOfLow(comprehensiveBean);
        areaOfMuscle(comprehensiveBean);
        areaOfIndoor(comprehensiveBean);
        areaOfSleep(comprehensiveBean);
        areaOfHeartRate(comprehensiveBean);
    }

    //刷新舞台任务
    private void refreshStage(ComprehensiveBean comprehensiveBean){
        BodyFatSaid bodyFatSaid = comprehensiveBean.getBodyFatSaid();
        if(isShowStage(bodyFatSaid)){
            //显示
            stageView.setVisibility(View.VISIBLE);
            stageviewGo(bodyFatSaid);
        }else{
            //不显示
            stageView.setVisibility(View.GONE);
        }
    }
    private boolean isShowStage(BodyFatSaid bodyFatSaid){
        if(bodyFatSaid==null){
            //Log.i("info","==========体脂称数据===========空");
            return false;
        }
        //Log.i("info", "==========体脂称数据===========非空");
        return true;
    }

    private void stageviewGo(BodyFatSaid bodyFatSaid){
        stageView.startFocus();
        String des = WeightRuler.ruler(this,bodyFatSaid.getWeight());

        int index = 1;
        if(des.equals("偏低")){
            index = 1;
        }
        if(des.equals("正常")){
            index = 2;
        }
        if(des.equals("偏高")){
            index = 3;
        }

        final int finalIndex = index;
        stageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                stageView.stopFocus(finalIndex);
            }
        }, 5000);

    }

    //刷新蒜头表情
    private void refreshGarlic(ComprehensiveBean comprehensiveBean) {
        sleepQuality(comprehensiveBean.getSleepDataAllBean());
        muscleContent(comprehensiveBean.getBodyFatSaid());
        heartRate(comprehensiveBean.getBloodPressureBean());
        bloodFat();
        bloodSugar();
        bloodPressure(comprehensiveBean.getBloodPressureBean());
    }

    //睡眠质量
    private void sleepQuality(SleepDataAllBean sleepDataAllBean) {
        if (sleepDataAllBean == null) {
            //Log.i("info","==========睡眠总数据===========空");
            return;
        }
        //Log.i("info","==========睡眠总数据===========非空");
        String sleep_des = SleepRuler.ruler(this, sleepDataAllBean.getTotalTime());
        if (sleep_des.equals("较差")) {
            iv_garlic_sleep.setImageResource(R.drawable.sleep_poor);
        }
        if (sleep_des.equals("一般")) {
            iv_garlic_sleep.setImageResource(R.drawable.sleep_commonly);
        }
        if (sleep_des.equals("良好")) {
            iv_garlic_sleep.setImageResource(R.drawable.sleep_good);
        }
        if (sleep_des.equals("优质")) {
            iv_garlic_sleep.setImageResource(R.drawable.sleep_excellent);
        }
        tv_garlic_sleep.setText(sleep_des);
    }

    //肌肉含量
    private void muscleContent(BodyFatSaid bodyFatSaid) {
        if (bodyFatSaid == null) {
            return;
        }
        float weight = bodyFatSaid.getWeight();
        String muscle_des = FatRule.getRate(weight, BodyFatConst.TYPE_MUSLE_CONTENT, sex, Integer.parseInt(age), Integer.parseInt(height), bodyFatSaid.getMuscle());
        if (muscle_des.equals("偏高")) {
            iv_garlic_muscle.setImageResource(R.drawable.muscle_high);
        }
        if (muscle_des.equals("偏低")) {
            iv_garlic_muscle.setImageResource(R.drawable.muscle_low);
        }
        if (muscle_des.equals("标准")) {
            iv_garlic_muscle.setImageResource(R.drawable.muscle_middle);
        }
        tv_garlic_muscle.setText(muscle_des);
    }

    //心率
    private void heartRate(BloodPressureBean bloodPressureBean) {
        if (bloodPressureBean == null) {
            //Log.i("info","==========血压数据===========空");
            return;
        }
        //Log.i("info", "==========血压数据===========非空");
        int heartrate_des = BloodPressDataTransfer.parseHeart(bloodPressureBean.getHeartRate(), this);
        if (heartrate_des==BloodPressDataTransfer.HEARTRATE_SLOW) {
            iv_garlic_heartrate.setImageResource(R.drawable.heartrate_slow);
            tv_garlic_heartrate.setText("过缓");
        }
        if (heartrate_des==BloodPressDataTransfer.HEARTRATE_FAST) {
            iv_garlic_heartrate.setImageResource(R.drawable.heartrate_fast);
            tv_garlic_heartrate.setText("过速");
        }
        if (heartrate_des==BloodPressDataTransfer.HEARTRATE_MIDDLE) {
            iv_garlic_heartrate.setImageResource(R.drawable.heartrate_normal);
            tv_garlic_heartrate.setText("正常");
        }

    }

    //血脂
    private void bloodFat() {

    }

    //血糖
    private void bloodSugar() {

    }

    //血压
    private void bloodPressure(BloodPressureBean bloodPressureBean) {
        if (bloodPressureBean == null) {
            return;
        }
        //Log.i("info","========bloodPressureBean.getSysBp()======="+bloodPressureBean.getSysBp());
        //Log.i("info","========bloodPressureBean.getDiaBp()======="+bloodPressureBean.getDiaBp());
        String bloodpressure_des = BloodPressDataTransfer.parseThreeLevel(bloodPressureBean.getSysBp(), bloodPressureBean.getDiaBp());
        //Log.i("info","========bloodpressure_des======="+bloodpressure_des);
        if (bloodpressure_des.equals("正常")) {
            iv_garlic_bloodpressure.setImageResource(R.drawable.bloodpressure_good);
        }
        if (bloodpressure_des.equals("低血压")) {
            iv_garlic_bloodpressure.setImageResource(R.drawable.bloodpressure_low);
        }
        if (bloodpressure_des.equals("高血压")) {
            iv_garlic_bloodpressure.setImageResource(R.drawable.bloodpressure_high);
        }
        if(bloodpressure_des==null||bloodpressure_des.equals("")){
            tv_garlic_bloodpressure.setText("无数据");
        }else{
            tv_garlic_bloodpressure.setText(bloodpressure_des);
        }

    }

    //刷新-偏高区域的ui
    private void areaOfHigh(ComprehensiveBean comprehensiveBean) {
        //是否偏高
        boolean ishigh = high(comprehensiveBean.getBodyFatSaid(), comprehensiveBean.getBloodPressureBean());
        if (ishigh) {
            bannersOfHigh(comprehensiveBean.getBodyFatSaid(), comprehensiveBean.getBloodPressureBean());
            fathigh(comprehensiveBean.getBodyFatSaid());
            bphigh(comprehensiveBean.getBloodPressureBean());
            bfhigh();
            bshigh();
        }
    }

    private boolean high(BodyFatSaid bodyFatSaid, BloodPressureBean bloodPressureBean) {

        String fat_des = "";
        String bloodpressure_des = "";

        if (bodyFatSaid != null) {
            float weight = bodyFatSaid.getWeight();
            fat_des = FatRule.getRate(weight,BodyFatConst.TYPE_FAT_RATION, sex, Integer.parseInt(age), Integer.parseInt(height), bodyFatSaid.getFat());
        }

        if (bloodPressureBean != null) {
            bloodpressure_des = BloodPressDataTransfer.parseThreeLevel(bloodPressureBean.getSysBp(), bloodPressureBean.getDiaBp());
        }

        if (fat_des.equals("偏高") || bloodpressure_des.equals("高血压")) {
            return true;
        } else {
            tv_banners_high.setVisibility(View.GONE);
            rl_rv_fat_high.setVisibility(View.GONE);
            rl_rv_bp_high.setVisibility(View.GONE);
            //rl_rv_bs_high.setVisibility(View.GONE);
            //rl_rv_bf_high.setVisibility(View.GONE);
            ll_high_toast.setVisibility(View.GONE);
            return false;
        }

    }

    private void bannersOfHigh(BodyFatSaid bodyFatSaid, BloodPressureBean bloodPressureBean) {
        String fat_des = "";
        String bloodpressure_des = "";

        if (bodyFatSaid != null) {
            float weight = bodyFatSaid.getWeight();
            fat_des = FatRule.getRate(weight,BodyFatConst.TYPE_FAT_RATION, sex, Integer.parseInt(age), Integer.parseInt(height), bodyFatSaid.getFat());
        }

        if (bloodPressureBean != null) {
            bloodpressure_des = BloodPressDataTransfer.parseThreeLevel(bloodPressureBean.getSysBp(), bloodPressureBean.getDiaBp());
        }

        StringBuilder sb = new StringBuilder();
        if (!fat_des.equals("")) {
            sb.append(fat_des);
        }
        if (!bloodpressure_des.equals("")) {
            sb.append("、");
            sb.append(bloodpressure_des);
        }
        sb.append("超标");

        tv_banners_high.setText(sb.toString());

    }

    private void fathigh(BodyFatSaid bodyFatSaid) {
        if (bodyFatSaid == null) {
            rl_rv_fat_high.setVisibility(View.GONE);
            return;
        }
        RateOfFatRuler.RateFatRulerBean rateFatRulerBean = RateOfFatRuler.ruler(this, bodyFatSaid.getFat());
        if (rateFatRulerBean == null) {
            rl_rv_fat_high.setVisibility(View.GONE);
            return;
        }
        String state = rateFatRulerBean.getState();
        if (state.equals("偏低") || state.equals("标准")) {
            rl_rv_fat_high.setVisibility(View.GONE);
            return;
        }

        ratio_fat_exceed = rateFatRulerBean.getRatio();
        //rvfathigh.setCurr_progress(rateFatRulerBean.getRatio());
    }

    private void bphigh(BloodPressureBean bloodPressureBean) {
        if (bloodPressureBean == null) {
            rl_rv_bp_high.setVisibility(View.GONE);
            return;
        }

        BloodPressureRuler.BloodPressureRulerBean bloodPressureRulerBean = BloodPressureRuler.ruler(bloodPressureBean.getSysBp(), bloodPressureBean.getDiaBp());
        if (bloodPressureRulerBean == null) {
            rl_rv_bp_high.setVisibility(View.GONE);
            return;
        }

        String state = bloodPressureRulerBean.getState();
        float ratio = bloodPressureRulerBean.getRatio();

        if (state == null || state.equals("") || state.equals("低血压") || state.equals("正常")) {
            rl_rv_bp_high.setVisibility(View.GONE);
            return;
        }

        ratio_bp_exceed = ratio;
        //rvbloodpressurehigh.setCurr_progress(ratio);

    }

    private void bshigh() {

    }

    private void bfhigh() {

    }


    //刷新-偏低区域的UI
    private void areaOfLow(ComprehensiveBean comprehensiveBean) {
        //是否偏低
        boolean islow = low(comprehensiveBean.getBodyFatSaid(), comprehensiveBean.getBloodPressureBean());
        if (islow) {
            bannersOfLow(comprehensiveBean.getBodyFatSaid(), comprehensiveBean.getBloodPressureBean());
            fatlow(comprehensiveBean.getBodyFatSaid());
            bplow(comprehensiveBean.getBloodPressureBean());
            bflow();
            bslow();
        }
    }

    private boolean low(BodyFatSaid bodyFatSaid, BloodPressureBean bloodPressureBean) {

        String fat_des = "";
        String bloodpressure_des = "";

        if (bodyFatSaid != null) {
            float weight = bodyFatSaid.getWeight();
            fat_des = FatRule.getRate(weight,BodyFatConst.TYPE_FAT_RATION, sex, Integer.parseInt(age), Integer.parseInt(height), bodyFatSaid.getFat());
        }

        if (bloodPressureBean != null) {
            bloodpressure_des = BloodPressDataTransfer.parseThreeLevel(bloodPressureBean.getSysBp(), bloodPressureBean.getDiaBp());
        }

        if (fat_des.equals("偏低") || bloodpressure_des.equals("低血压")) {
            return true;
        } else {
            tv_banners_low.setVisibility(View.GONE);
            //rl_rv_fat_low.setVisibility(View.GONE);
            //rl_rv_bp_low.setVisibility(View.GONE);
            //rl_rv_bs_low.setVisibility(View.GONE);
            //rl_rv_bf_low.setVisibility(View.GONE);
            ll_low_toast.setVisibility(View.GONE);
            ll_zonghe_low.setVisibility(View.GONE);
            return false;
        }

    }

    private void bannersOfLow(BodyFatSaid bodyFatSaid, BloodPressureBean bloodPressureBean) {
        String fat_des = "";
        String bloodpressure_des = "";

        if (bodyFatSaid != null) {
            float weight = bodyFatSaid.getWeight();
            fat_des = FatRule.getRate(weight,BodyFatConst.TYPE_FAT_RATION, sex, Integer.parseInt(age), Integer.parseInt(height), bodyFatSaid.getFat());
        }

        if (bloodPressureBean != null) {
            bloodpressure_des = BloodPressDataTransfer.parseThreeLevel(bloodPressureBean.getSysBp(), bloodPressureBean.getDiaBp());
        }

        StringBuilder sb = new StringBuilder();
        if (!fat_des.equals("")) {
            //sb.append(fat_des);
            sb.append("体重");
        }
        if (!bloodpressure_des.equals("")) {
            sb.append("、");
            //sb.append(bloodpressure_des);
            sb.append("血压");
        }
        sb.append("偏低");

        tv_banners_low.setText(sb.toString());

    }

    private void fatlow(BodyFatSaid bodyFatSaid) {
        if (bodyFatSaid == null) {
            rl_rv_fat_low.setVisibility(View.GONE);
            return;
        }
        RateOfFatRuler.RateFatRulerBean rateFatRulerBean = RateOfFatRuler.ruler(this, bodyFatSaid.getFat());
        if (rateFatRulerBean == null) {
            rl_rv_fat_low.setVisibility(View.GONE);
            return;
        }
        String state = rateFatRulerBean.getState();
        if (state.equals("偏高") || state.equals("标准")) {
            rl_rv_fat_low.setVisibility(View.GONE);
            return;
        }

        ratio_fat_insufficient = rateFatRulerBean.getRatio();
        //rvfatlow.setCurr_progress(rateFatRulerBean.getRatio());
    }

    private void bplow(BloodPressureBean bloodPressureBean) {
        if (bloodPressureBean == null) {
            rl_rv_bp_low.setVisibility(View.GONE);
            return;
        }

        BloodPressureRuler.BloodPressureRulerBean bloodPressureRulerBean = BloodPressureRuler.ruler(bloodPressureBean.getSysBp(), bloodPressureBean.getDiaBp());
        if (bloodPressureRulerBean == null) {
            rl_rv_bp_low.setVisibility(View.GONE);
            return;
        }

        String state = bloodPressureRulerBean.getState();
        float ratio = bloodPressureRulerBean.getRatio();

        if (state == null || state.equals("") || state.equals("高血压") || state.equals("正常")) {
            rl_rv_bp_low.setVisibility(View.GONE);
            return;
        }

        ratio_bp_insufficient = ratio;
        //rvbloodpressurelow.setCurr_progress(ratio);

    }

    private void bslow() {

    }

    private void bflow() {

    }


    //刷新-肌肉含量
    private void areaOfMuscle(ComprehensiveBean comprehensiveBean) {
        if (isShowOfMuscleDes(comprehensiveBean.getBodyFatSaid())) {
            //显示肌肉区域
            textOfMuscleDes(comprehensiveBean.getBodyFatSaid());
            imageOfMuscleDes(comprehensiveBean.getBodyFatSaid());
        } else {
            //不显示肌肉区域
            tv_muscle_des.setVisibility(View.GONE);
            iv_muscle_des.setVisibility(View.GONE);
        }
    }

    private boolean isShowOfMuscleDes(BodyFatSaid bodyFatSaid) {
        if (bodyFatSaid == null) {
            return false;
        }
        float musclecontent = bodyFatSaid.getMuscle();
        String muscledes = MuscleContentRuler.ruler(this, musclecontent);
        if (muscledes == null || muscledes.equals("") || muscledes.equals("标准")) {
            return false;
        }
        return true;
    }

    private void textOfMuscleDes(BodyFatSaid bodyFatSaid) {
        //String muscledes = MuscleContentRuler.ruler(this, bodyFatSaid.getMuscle());
        String muscledes = FatRule.getRate(bodyFatSaid.getWeight(), BodyFatConst.TYPE_MUSLE_CONTENT, sex, Integer.parseInt(age), Integer.parseInt(height), bodyFatSaid.getMuscle());
        if (muscledes.equals("偏低")) {
            tv_muscle_des.setText("肌肉含量偏低，建议您适当增强有氧运动（比如：游泳、慢跑、骑自行车等）");
        }
        if (muscledes.equals("偏高")) {
            tv_muscle_des.setText("肌肉含量较高，希望您继续保持。");
        }
    }

    private void imageOfMuscleDes(BodyFatSaid bodyFatSaid) {

    }

    //刷新-室内空气质量部分
    private void areaOfIndoor(ComprehensiveBean comprehensiveBean) {
        AirCatInfo airCatInfo = comprehensiveBean.getAirCatInfo();
        if (isShowOfIndoorAirQualityDes(airCatInfo)) {
            //显示
            textOfInDoorAirQuality(airCatInfo);
        } else {
            //不显示
            tv_indoorairquality_des.setVisibility(View.GONE);
            ll_indoorairquality_des.setVisibility(View.GONE);
        }
    }

    private boolean isShowOfIndoorAirQualityDes(AirCatInfo airCatInfo) {
        if (airCatInfo == null) {
            return false;
        }
        float pm = Float.parseFloat(airCatInfo.getPm25());
        float formal = Float.parseFloat(airCatInfo.getCh2());
        float tvoc = Float.parseFloat(airCatInfo.getTvoc());

        if (pm > 75 || formal > 0.1 || tvoc > 0.5) {
            return true;
        }
        return false;
    }

    private void textOfInDoorAirQuality(AirCatInfo airCatInfo) {
        StringBuilder stringBuilder = new StringBuilder();

        float pm = Float.parseFloat(airCatInfo.getPm25());
        float formal = Float.parseFloat(airCatInfo.getCh2());
        float tvoc = Float.parseFloat(airCatInfo.getTvoc());

        if (pm > 75) {
            stringBuilder.append("PM2.5");
        }
        if (formal > 0.1) {
            stringBuilder.append("甲醛");
        }
        if (tvoc > 0.5) {
            stringBuilder.append("TVOC");
        }
        tv_indoorairquality_des.setText("室内" + stringBuilder.toString() + "超标，注意经常开窗，通风换气，或者安装空气净化器，或养一些绿色植物净化空气");
    }

    //刷新-睡眠相关
    private void areaOfSleep(ComprehensiveBean comprehensiveBean) {
        SleepDataAllBean sleepDataAllBean = comprehensiveBean.getSleepDataAllBean();
        if (isShowOfSleep(sleepDataAllBean)) {
            //显示
            textOfSleepDes(sleepDataAllBean);
        } else {
            //不显示
            ll_sleep_des.setVisibility(View.GONE);
        }
    }

    private boolean isShowOfSleep(SleepDataAllBean sleepDataAllBean) {
        if (sleepDataAllBean == null) {
            return false;
        }
        String des = SleepRuler.ruler(this, sleepDataAllBean.getTotalTime());
        if (des.equals("偏少") || des.equals("偏多")) {
            return true;
        }
        return false;
    }

    private void textOfSleepDes(SleepDataAllBean sleepDataAllBean) {
        String des = SleepRuler.ruler(this, sleepDataAllBean.getTotalTime());
        if (des.equals("偏少")) {
            tv_sleep_des.setText("您最近的睡眠较少，要有一个良好的精神状态和健康的身体，请保证充足的睡眠质睡眠");
        }
        if (des.equals("偏多")) {
            tv_sleep_des.setText("您本周的睡眠偏多，睡眠过会削弱精神和体力多，建议保证适当睡眠");
        }
    }

    //刷新-心率
    private void areaOfHeartRate(ComprehensiveBean comprehensiveBean) {
        BloodPressureBean bloodPressureBean = comprehensiveBean.getBloodPressureBean();
        if (isShowOfHeartrate(bloodPressureBean)) {
            //显示
            textOfHeartrate(bloodPressureBean);
        } else {
            //不显示
            ll_heartrate_des.setVisibility(View.GONE);
        }
    }

    private boolean isShowOfHeartrate(BloodPressureBean bloodPressureBean) {
        if (bloodPressureBean == null) {
            return false;
        }

        int des = BloodPressDataTransfer.parseHeart(bloodPressureBean.getHeartRate(), this);
        if (des==BloodPressDataTransfer.HEARTRATE_MIDDLE) {
            return false;
        }

        return true;

    }

    private void textOfHeartrate(BloodPressureBean bloodPressureBean) {
        String des = HeartrateRuler.ruler(this, bloodPressureBean.getHeartRate());
        if (des.equals("过缓")) {
            tv_heartrate_des.setText("心率过缓者易出现乏力、心悸、头晕等症状。建议您多食各种新鲜水果、蔬菜，低脂肪、低胆固醇食品，必要时请及时就医");
        }
        if (des.equals("过速")) {
            tv_heartrate_des.setText("本周您的心动过速，易出现心悸、胸闷、乏力、头晕等症状。建议您加强运动锻炼，增强体质；忌辛辣油腻食物；保证充足的睡眠。必要时请及时就医");
        }
    }

}
