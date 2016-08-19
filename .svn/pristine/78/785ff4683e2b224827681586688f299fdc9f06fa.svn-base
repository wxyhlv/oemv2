package com.capitalbio.oemv2.myapplication.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.baidu.location.BDLocation;
import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.Bean.AirIndexBean;
import com.capitalbio.oemv2.myapplication.Bean.TimeBean;
import com.capitalbio.oemv2.myapplication.FirstPeriod.Activity.CatDataInput;
import com.capitalbio.oemv2.myapplication.FirstPeriod.Adapter.CatViewPagerAdapter;
import com.capitalbio.oemv2.myapplication.FirstPeriod.AircatProtocolParse.AirCatBean;
import com.capitalbio.oemv2.myapplication.FirstPeriod.AircatProtocolParse.AirCatDataParse;
import com.capitalbio.oemv2.myapplication.FirstPeriod.Bean.CatBean;
import com.capitalbio.oemv2.myapplication.FirstPeriod.BlueTooth.BluetoothManage;
import com.capitalbio.oemv2.myapplication.FirstPeriod.BlueTooth.BluetoothState;
import com.capitalbio.oemv2.myapplication.FirstPeriod.BlueTooth.OnBluetoothListener;
import com.capitalbio.oemv2.myapplication.FirstPeriod.DB.CatDBmanager;
import com.capitalbio.oemv2.myapplication.FirstPeriod.Datapersistence.DataPersistenceManage;
import com.capitalbio.oemv2.myapplication.FirstPeriod.Datapersistence.SystemConfig;
import com.capitalbio.oemv2.myapplication.FirstPeriod.Final.Base_Url;
import com.capitalbio.oemv2.myapplication.FirstPeriod.Interfaces.OnWeatherReceivedListener;
import com.capitalbio.oemv2.myapplication.FirstPeriod.Net.HttpTools;
import com.capitalbio.oemv2.myapplication.FirstPeriod.Service.CatService;
import com.capitalbio.oemv2.myapplication.FirstPeriod.Utils.CatDataTransferUtil;
import com.capitalbio.oemv2.myapplication.FirstPeriod.Utils.CityNameUtil;
import com.capitalbio.oemv2.myapplication.FirstPeriod.Utils.HTG;
import com.capitalbio.oemv2.myapplication.FirstPeriod.Utils.JsonUtil;
import com.capitalbio.oemv2.myapplication.FirstPeriod.Utils.StringUtil;
import com.capitalbio.oemv2.myapplication.FirstPeriod.Utils.ToastMaster;
import com.capitalbio.oemv2.myapplication.FirstPeriod.View.DialogProgressCat;
import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;
import com.capitalbio.oemv2.myapplication.Utils.PreferencesUtils;
import com.capitalbio.oemv2.myapplication.Utils.TimeStampUtil;
import com.capitalbio.oemv2.myapplication.Utils.Utility;
import com.capitalbio.oemv2.myapplication.View.MyMarkerView;
import com.capitalbio.oemv2.myapplication.View.MyYAxisValueFormatter;
import com.capitalbio.oemv2.myapplication.View.views.CatQiPaoView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.client.methods.HttpPost;





public class AirCatBlueActivity extends Activity implements View.OnClickListener,
        OnBluetoothListener, OnWeatherReceivedListener,
        OnChartValueSelectedListener {


    // private ViewPager vp;
    private CatViewPagerAdapter vpadapter;
    private RelativeLayout rl_back, rl_edite;
    private ImageView iv_tianqi, iv_battery;
    private TextView tv_tem1, tv_tem2, tv_hum1, tv_hum2, tv_jiaquan1,
            tv_jiaquan2, tv_tvoc1, tv_tvoc2,tv_pm1,tv_pm2,tv_wrzs1,tv_wrzs2 ,tv_devicename, tv_date,
            tv_airquality, tv_tem, tv_tianqi;
    private CatQiPaoView cqp_tem, cqp_hum, cqp_jiaquan, cqp_tvoc,cqp_pm,cqp_wrzs;

    private BluetoothManage mBluetoothManage;
    private ProgressDialog progressDialog;

    // 当前连接设备MAC地址
    private String MAC;
    // 3分钟提交一次数据
    private int interval = 3 * 60 * 1000;
    private SystemConfig mSystemConfig;
    /** 是否是首次提醒 */
    private boolean firstWarning = true;
    /** 当电量低于该值时弹出低电量报警提示 */
    private double lowPower = 10;
    private JSONObject jsonData;

    private CatService catservice;

    private ServiceConnection sc;

    private List<TimeBean> daybeans_past;

    /**
     * 本地数据提取历史数据
     */
    private myAsyncTask myAsyncTask;

    Typeface mTf;

    // 获取本地保存的用户名
    private SharedPreferences sp;

    // 进度圈
    private DialogProgressCat dialogprogress;

    // 防止四个气泡view，被快速连续点击，而造成出现多个dialog
    private boolean clickable = true;

    //记录空气猫连接状态 0:无连接。1:有连接。2:正在连接中。
    private int CONTACT_STATE =2;

    //室外空气质量数据的接收
    private static final int OUTDOORAQI = -1;

    //接收从服务器返回的历史数据
    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            int what = msg.what;
            String result = (String) msg.obj;

            List<AirIndexBean> airIndexBeans = new ArrayList<AirIndexBean>();
            if(what==0||what==1||what==2||what==3||what==4||what==5||what==6){
                //空气猫历史数据
                List<CatBean> catBeans = new ArrayList<CatBean>();
                if(result!=null&&!result.equals("")){
                    catBeans = JsonUtil.getListBean(result, CatBean.class);
                }
                //将服务器返回的catbean数据转成airindexbean数据

                airIndexBeans = HTG.transfer(catBeans);
            }


            if(dialogprogress!=null&&dialogprogress.isShowing()){
                dialogprogress.dismiss();
            }

            switch (what) {
                case OUTDOORAQI:
                    //接收室外空气质量数据
                    //System.out.println("收到室外空气质量数据================="+result);
                    if(result!=null){
                        JSONObject object =JSONObject.parseObject(result);
                        String aqilevel = object.getJSONObject("data").getString("class");
                        System.out.println("室外空气等级=============="+aqilevel);
                        tv_airquality.setText("室外空气："+aqilevel);
                    }else{
                        tv_airquality.setText("室外空气："+"良");
                    }

                    break;
                case 0:
                    //取最近一条数据
                    if(airIndexBeans==null){
                        break;
                    }
                    AirIndexBean airIndexBean = airIndexBeans.get(airIndexBeans.size()-1);
                    showHistoryDataOnBubble(airIndexBean);
                    break;
                case 1:
                    //温度
                    System.out.println("=====温度历史数据");
                    if(airIndexBeans==null){
                        break;
                    }
                    showDialogHistoryTem(airIndexBeans, AirCatBlueActivity.this);

                    break;
                case 2:
                    //湿度
                    System.out.println("=====湿度历史数据");
                    if(airIndexBeans==null){
                        break;
                    }
                    showDialogHistoryHum(airIndexBeans);
                    break;

                case 3:
                    //甲醛
                    System.out.println("=====甲醛历史数据");
                    if(airIndexBeans==null){
                        break;
                    }
                    showDialogHistoryForma(airIndexBeans);
                    break;

                case 4:
                    //tvoc
                    System.out.println("=====tvoc历史数据");
                    if(airIndexBeans==null){
                        break;
                    }
                    showDialogHistoryTvoc(airIndexBeans);
                    break;
                case 5:
                    //pm2.5
                    if(airIndexBeans==null){
                        break;
                    }
                   showDialogHistoryPM(airIndexBeans);
                    break;
                case 6:
                    //污染指数
                    if(airIndexBeans==null){
                        break;
                    }
                    showDialogHistoryPoll(airIndexBeans);
                    break;
                case -1000:
                    //请求空气猫过去24小时历史数据-异常
                    if(dialogprogress!=null&&dialogprogress.isShowing()){
                        dialogprogress.dismiss();
                    }
                    ToastMaster.showToast(AirCatBlueActivity.this, "网络异常，请检查网络");
                    clickable = true;
                    break;
                case -2000:
                    //请求空气猫过去24小时历史数据-无数据
                    if(dialogprogress!=null&&dialogprogress.isShowing()){
                        dialogprogress.dismiss();
                    }
                    ToastMaster.showToast(AirCatBlueActivity.this, "无历史数据");
                    clickable = true;
                    break;

                default:
                    break;
            }


        };
    };

    /**
     * 因为无空气猫连接，所以从服务器获取最近一条历史数据来展示
     * @param airIndexBean
     */
    private void showHistoryDataOnBubble(AirIndexBean airIndexBean){

        String tem = airIndexBean.getTemperature();
        String hum = airIndexBean.getHumidity();
        String formal = airIndexBean.getFormaldehyde();
        String tvoc = airIndexBean.getAqi();

        float temf = Float.parseFloat(tem);
        float humf = Float.parseFloat(hum);
        float formalf = Float.parseFloat(formal);
        float tvocf = Float.parseFloat(tvoc);

        //温度
        tv_tem1.setText(temf + "℃");
        tv_tem2.setText(CatDataTransferUtil.TemUtil.temText(temf));
        cqp_tem.setARCColor(CatDataTransferUtil.TemUtil.temColor(temf, this));
        if (cqp_tem.isAlreadySetPercent()) {
            cqp_tem.updatePercent(CatDataTransferUtil.TemUtil.temPercent(temf));
        } else {
            cqp_tem.setPercent(CatDataTransferUtil.TemUtil.temPercent(temf));
        }
        //湿度
        tv_hum1.setText(humf + "%RH");
        tv_hum2.setText(CatDataTransferUtil.HumUtil.humText(humf));
        cqp_hum.setARCColor(CatDataTransferUtil.HumUtil.humColor(humf, this));
        if (cqp_hum.isAlreadySetPercent()) {
            cqp_hum.updatePercent(CatDataTransferUtil.HumUtil.humPercent(humf));
        } else {
            cqp_hum.setPercent(CatDataTransferUtil.HumUtil.humPercent(humf));
        }

        //甲醛
        tv_jiaquan1.setText(formalf + "mg/m³");
        tv_jiaquan2.setText(CatDataTransferUtil.JiaquanUtil.jiaquanText(formalf));
        cqp_jiaquan.setARCColor(CatDataTransferUtil.JiaquanUtil
                .jiaquanColor(formalf, this));
        if (cqp_jiaquan.isAlreadySetPercent()) {
            cqp_jiaquan.updatePercent(CatDataTransferUtil.JiaquanUtil
                    .jiaquanPercent(formalf));
        } else {
            cqp_jiaquan.setPercent(CatDataTransferUtil.JiaquanUtil
                    .jiaquanPercent(formalf));
        }

        //tvoc
        tv_tvoc1.setText(tvocf + "mg/m³");
        tv_tvoc2.setText(CatDataTransferUtil.TvocUtil.tvocText(tvocf));
        cqp_tvoc.setARCColor(CatDataTransferUtil.TvocUtil
                .tvocColor(tvocf, this));
        if (cqp_tvoc.isAlreadySetPercent()) {
            cqp_tvoc.updatePercent(CatDataTransferUtil.TvocUtil
                    .tvocPercent(tvocf));
        } else {
            cqp_tvoc.setPercent(CatDataTransferUtil.TvocUtil.tvocPercent(tvocf));
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_cat_blue);
        initNetStateReceiver();
        initVariable();
        initView();
        initViewData();
        contactCat();
        getWeather();
        initEvent();
        refreshVariable();
        refreshView();
        refreshViewData();
    }

    /**
     * 注册网络状态变化广播
     */
    private void initNetStateReceiver() {
        NetStateChangeReceiver changeReceiver = new NetStateChangeReceiver();
        IntentFilter filter = new IntentFilter(
                "android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(changeReceiver, filter);
    }

    // 连接空气猫
    private void contactCat() {
        tv_devicename.setText("正在查找周围设备...");
        if (!mBluetoothManage.isEnabled()) {
            mBluetoothManage.enable();
        }
        mBluetoothManage.startDiscovery();
    }

    // 开启服务获取天气数据
    private void getWeather() {
        if (catservice != null) {
            catservice.startLocate();
            return;
        }
        sc = new ServiceConnection() {

            @Override
            public void onServiceDisconnected(ComponentName name) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                System.out.println("onServiceConnected");
                catservice = ((CatService.MyBinder) service).getCatService();
                catservice.setOnWeatherReceivedListener(AirCatBlueActivity.this);
                catservice.startLocate();
            }
        };
        bindService(new Intent(this, CatService.class), sc,
                Service.BIND_AUTO_CREATE);
    }

    private void initVariable() {
        sp = getSharedPreferences("login_panther", MODE_PRIVATE);
        //
        mSystemConfig = DataPersistenceManage.getInstance(this)
                .getSystemConfig();
        mBluetoothManage = new BluetoothManage.Builder(this)
                // .DeviceName("iHealth-SENSOR")
                .DeviceName("AirCat")
                        // 识别以AirCat开头命名的设备
                .isAutomaticConnection(true).isDebug(true).isSingle(true)
                .charsetName("gb2312").OnBluetoothListener(this).build();

        //
        if (mBluetoothManage == null) {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setMessage("您的设备不支持蓝牙!")
                    .setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface arg0,
                                                    int arg1) {
                                    finish();
                                }
                            }).create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        } else {
            MyApplication application = (MyApplication) this.getApplication();
            application.setBluetoothManage(mBluetoothManage);
            mBluetoothManage.enable();
        }
        // 用户第一次使用本功能时跳到引导页面
        DataPersistenceManage manage = DataPersistenceManage.getInstance(this);
        if (manage.getSystemConfig().getHelpFirstDevice()) {
            // Intent intent = new Intent(this, ActivityDeviceHelp.class);
            // startActivityForResult(intent, ActivityDeviceHelp.FLAG);
        }

        // 初始化进度dialog
        dialogprogress = new DialogProgressCat(this);

    }

    private void initView() {
        rl_back = (RelativeLayout) this.findViewById(R.id.rl_catbt_back);
        rl_edite = (RelativeLayout) this.findViewById(R.id.rl_catbt_edite);
        //
        tv_tem1 = (TextView) this.findViewById(R.id.tv_catbt_monitor_tem1);
        tv_tem2 = (TextView) this.findViewById(R.id.tv_catbt_monitor_tem2);
        tv_hum1 = (TextView) this.findViewById(R.id.tv_catbt_monitor_hum1);
        tv_hum2 = (TextView) this.findViewById(R.id.tv_catbt_monitor_hum2);
        tv_jiaquan1 = (TextView) this
                .findViewById(R.id.tv_catbt_monitor_jiaquan1);
        tv_jiaquan2 = (TextView) this
                .findViewById(R.id.tv_catbt_monitor_jiaquan2);
        tv_tvoc1 = (TextView) this.findViewById(R.id.tv_catbt_monitor_tvoc1);
        tv_tvoc2 = (TextView) this.findViewById(R.id.tv_catbt_monitor_tvoc2);
        tv_pm1 = (TextView) this.findViewById(R.id.tv_catbt_monitor_pm2_51);
        tv_pm2 = (TextView) this.findViewById(R.id.tv_catbt_monitor_pm2_52);
        tv_wrzs1 = (TextView) this.findViewById(R.id.tv_catbt_monitor_wrzs1);
        tv_wrzs2 = (TextView) this.findViewById(R.id.tv_catbt_monitor_wrzs2);


        tv_devicename = (TextView) this
                .findViewById(R.id.tv_catbt_monitor_devicename);
        tv_date = (TextView) this.findViewById(R.id.tv_catbt_monitor_date);
        tv_airquality = (TextView) this
                .findViewById(R.id.tv_catbt_monitor_airquality);
        tv_tem = (TextView) this.findViewById(R.id.tv_catbt_monitor_tem);
        tv_tianqi = (TextView) this.findViewById(R.id.tv_catbt_monitor_tianqi);
        iv_tianqi = (ImageView) this.findViewById(R.id.iv_catbt_monitor_tianqi);
        iv_tianqi.setImageLevel(100);
        //
        cqp_tem = (CatQiPaoView) this.findViewById(R.id.cqp_catbt_monitor_tem);
        cqp_hum = (CatQiPaoView) this.findViewById(R.id.cqp_catbt_monitor_hum);
        cqp_jiaquan = (CatQiPaoView) this
                .findViewById(R.id.cqp_catbt_monitor_jiaquan);
        cqp_tvoc = (CatQiPaoView) this.findViewById(R.id.cqp_catbt_monitor_tvoc);
        cqp_pm = (CatQiPaoView) this.findViewById(R.id.cqp_catbt_monitor_pm2_5);
        cqp_wrzs = (CatQiPaoView) this.findViewById(R.id.cqp_catbt_monitor_wrzs);

        iv_battery = (ImageView) this.findViewById(R.id.iv_catbt_monitor_battery);

    }

    private void initViewData() {
        List<View> views = new ArrayList<>();
        View v_jishi = LayoutInflater.from(this).inflate(
                R.layout.viewpager_cat_jishi, null, false);

        View v_lishi = LayoutInflater.from(this).inflate(
                R.layout.viewpager_cat_lishi, null, false);
        views.add(v_jishi);
        views.add(v_lishi);
        vpadapter = new CatViewPagerAdapter(this, views);
        // vp.setAdapter(vpadapter);
    }

    private void initEvent() {
        rl_back.setOnClickListener(this);
        rl_edite.setOnClickListener(this);
        cqp_tem.setOnClickListener(this);
        cqp_hum.setOnClickListener(this);
        cqp_jiaquan.setOnClickListener(this);
        cqp_tvoc.setOnClickListener(this);
        cqp_pm.setOnClickListener(this);
        cqp_wrzs.setOnClickListener(this);
    }

    private void refreshVariable() {
    }

    private void refreshView() {
    }

    private void refreshViewData() {
    }

    @Override
    public void onClick(View v) {
        // 查看本地保存的上次连接的mac
        String mac_loc = getMac();
        // 查看当前登录的账户
        String username = getUserName();

        String cur = TimeStampUtil.currTime2(0);//0:全部时间    1：只有年月日
        String pas = TimeStampUtil.past24Hour2(0);

        //判断是否有网络
        boolean isnet = Utility.isNetworkAvailable(AirCatBlueActivity.this);

        // 判断本地是否有当前用户的数据
        boolean ishave = CatDBmanager.getInstance(this)
                .haveDataofUser(username);
        switch (v.getId()) {
            // 返回
            case R.id.rl_catbt_back:
                this.finish();
                break;
            // 编辑什么
            case R.id.rl_catbt_edite:


                HTG.to(this, CatDataInput.class);

                break;
            // 查看温度历史数据
            case R.id.cqp_catbt_monitor_tem:

                // 防止快速连续点击出现多个dialog
                if (clickable) {

                    clickable = false;

                    if (!dialogprogress.isShowing()) {
                        dialogprogress.show();
                    }

                    // 判断是否有网络
                    if(isnet){
                        System.out.println("=========================有网");
                        download(pas, cur,1);
                    }else{
                        System.out.println("=========================无网");
                        //if (ishave) {
                        // 如果有
                        showHistoryData(getUserName(), 1);
                        //} else {
                        // 如果没有
                        //}
                    }

                }

                break;
            // 查看湿度历史数据
            case R.id.cqp_catbt_monitor_hum:

                if (clickable) {

                    clickable = false;

                    if (!dialogprogress.isShowing()) {
                        dialogprogress.show();
                    }


                    // 判断是否有网络
                    if(isnet){
                        download(pas, cur,2);
                    }else{
                        //if (ishave) {
                        // 如果有
                        showHistoryData(getUserName(), 2);
                        //} else {
                        // 如果没有
                        //}
                    }
                }

                break;
            // 查看甲醛历史数据
            case R.id.cqp_catbt_monitor_jiaquan:

                if (clickable) {

                    clickable = false;

                    if (!dialogprogress.isShowing()) {
                        dialogprogress.show();
                    }

                    // 判断是否有网络
                    if(isnet){
                        download(pas, cur,3);
                    }else{
                        //if (ishave) {
                        // 如果有
                        showHistoryData(getUserName(), 3);
                        //} else {
                        // 如果没有
                        //}
                    }
                }

                break;
            // 查看TVOC历史数据
            case R.id.cqp_catbt_monitor_tvoc:

                if (clickable) {

                    clickable = false;

                    if (!dialogprogress.isShowing()) {
                        dialogprogress.show();
                    }

                    // 判断是否有网络
                    if(isnet){
                        download(pas, cur,4);
                    }else{
                        //if (ishave) {
                        // 如果有
                        showHistoryData(getUserName(), 4);
                        //} else {
                        // 如果没有
                        //}
                    }
                }
                break;
            //pm2.5
            case R.id.cqp_catbt_monitor_pm2_5:
                if (clickable) {

                    clickable = false;

                    if (!dialogprogress.isShowing()) {
                        dialogprogress.show();
                    }

                    // 判断是否有网络
                    if(isnet){
                        download(pas, cur,5);
                    }else{
                        //if (ishave) {
                        // 如果有
                        showHistoryData(getUserName(), 5);
                        //} else {
                        // 如果没有
                        //}
                    }
                }
                break;
            //污染指数
            case R.id.cqp_catbt_monitor_wrzs:
                if (clickable) {

                    clickable = false;

                    if (!dialogprogress.isShowing()) {
                        dialogprogress.show();
                    }

                    // 判断是否有网络
                    if(isnet){
                        download(pas, cur,6);
                    }else{
                        //if (ishave) {
                        // 如果有
                        showHistoryData(getUserName(), 6);
                        //} else {
                        // 如果没有
                        //}
                    }
                }
                break;
        }
    }

    /**
     * 根据用户名mac从本地获取历史数据
     *
     * @param user
     * @param flag
     */
    private void showHistoryData(String user, int flag) {
        // daybeans_past = getPastSevenDay();过去七天

        myAsyncTask = new myAsyncTask();
        myAsyncTask.execute(daybeans_past, this, flag, user);
    }

    @Override
    public void BluetoothChanged(int state) {
        if (BluetoothState.BLUETOOTH_STATE_ON == state) // 蓝牙开启
        {

        } else { // 蓝牙关闭
            closeWait();
            // AlertDialog dialog = new
            // AlertDialog.Builder(this).setMessage("蓝牙已关闭!").setPositiveButton("确定",
            // new DialogInterface.OnClickListener() {
            // public void onClick(DialogInterface arg0, int arg1) {
            // // finish();
            // shakeReady = true;
            // }
            // }).create();
            // dialog.setCanceledOnTouchOutside(false);
            // dialog.setCancelable(false);
            // dialog.show();
        }
    }

    @Override
    public void BluetoothConnected(android.bluetooth.BluetoothDevice device,
                                   int state) {
        closeWait();
        if (isFinishing()) {
            return;
        }
        if (BluetoothState.BLUETOOTH_STATE_START == state) {// 开始连接


            tv_devicename.setText("正在与设备建立连接...");

        } else if (BluetoothState.BLUETOOTH_STATE_SUCCEED == state) { // 连接成功

            CONTACT_STATE = 1;

            MAC = device.getAddress();
            //MAC = device.getName();

            tv_devicename.setText("当前设备:" + MAC);
            iv_battery.setVisibility(View.VISIBLE);
            // settingBt.setVisibility(View.VISIBLE);
            // 连接成功后告诉设备开始发送数据
            mBluetoothManage.write("LEDx");
            // if (Utils.getSeason()) {
            // 夏季
            mBluetoothManage.write("LED6");
            // } else {
            // 冬季
            // mBluetoothManage.write("LED7");
            // }

            // 连接成功

            // 当前的mac保存本地
            saveMac(MAC);

        } else if (BluetoothState.BLUETOOTH_STATE_FAIL == state
                || BluetoothState.BLUETOOTH_STATE_DISCONNECT == state) { //找到设备的前提下并开始连接，连接失败了，和已经建立的连接断开了

            CONTACT_STATE = 0;

            tv_devicename.setText("建立连接失败");
            iv_battery.setVisibility(View.INVISIBLE);
            // settingBt.setVisibility(View.INVISIBLE);
            String message = null;
            if (BluetoothState.BLUETOOTH_STATE_FAIL == state)
            {// 找到设备了，开始建立连接了，但连接失败了
                message = "建立连接失败,是否重新尝试?";
            } else {// 已经建立的连接断开了
                message = "连接已断开,是否重新建立连接?";
            }
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setMessage(message)
                    .setPositiveButton("重试",
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface arg0,
                                                    int arg1) {
                                    mBluetoothManage.startDiscovery();
                                    tv_devicename.setText("正在查找周围设备...");
                                    // mBluetoothManage.connect(device);
                                }
                            })
                    .setNegativeButton("取消",
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface arg0,
                                                    int arg1) {
                                    // finish();
                                }
                            }).setCancelable(false).create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            // 连接失败和连接断开


            download(TimeStampUtil.past24Hour2(0), TimeStampUtil.currTime2(0), 0);
        }
    }

    /**
     * 保存本地
     *
     * @param mac
     */
    private void saveMac(String mac) {
        SharedPreferences sp = this.getSharedPreferences("mac", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("mac", mac);
        editor.commit();
    }

    /**
     *
     * @param
     * @return
     */
    private String getMac() {
        SharedPreferences sp = this.getSharedPreferences("mac", MODE_PRIVATE);
        return sp.getString("mac", "");
    }

    @Override
    public void BluetoothDevice(List<android.bluetooth.BluetoothDevice> devices) {
        if (devices == null || devices.isEmpty()) {//没有找到任何设备

            CONTACT_STATE = 0;

            closeWait();
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setMessage("未搜索到设备,请将手机放在空气质量监测仪附近")
                    .setPositiveButton("重试",
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface arg0,
                                                    int arg1) {
                                    mBluetoothManage.startDiscovery();
                                    tv_devicename.setText("正在查找周围设备...");
                                }
                            })
                    .setNegativeButton("取消",
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface arg0,
                                                    int arg1) {
                                }
                            }).create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
            tv_devicename.setText("未搜索到设备");

            // 没有找到设备
            download(TimeStampUtil.past24Hour2(0), TimeStampUtil.currTime2(0), 0);
        }
    }

    @Override
    public void BluetoothFound(android.bluetooth.BluetoothDevice device){
        // TODO Auto-generated method stub

    }

    @Override
    public void BluetoothData(AirCatBean data){

        if (data==null) {
            return;
        }


        showData(data);





            // 上传数据到服务器
            //upload(airIndexBean);
            upload2(data);



    }

    //数据实时展示

    private void showData(AirCatBean airCatBean){
        float tem = airCatBean.getTemperature();
        float hum = airCatBean.getHumidity();
        float formal = airCatBean.getFormol();
        float tvoc = airCatBean.getTVOC();
        float pm = airCatBean.getPm25();
        float wrzs = airCatBean.getPollutionLevel();

        //温度
        tv_tem1.setText(tem + "℃");
        tv_tem2.setText(CatDataTransferUtil.TemUtil.temText(tem));
        cqp_tem.setARCColor(CatDataTransferUtil.TemUtil.temColor(tem, this));
        if (cqp_tem.isAlreadySetPercent()) {
            cqp_tem.updatePercent(CatDataTransferUtil.TemUtil.temPercent(tem));
        } else {
            cqp_tem.setPercent(CatDataTransferUtil.TemUtil.temPercent(tem));
        }
        //湿度
        tv_hum1.setText(hum + "%RH");
        tv_hum2.setText(CatDataTransferUtil.HumUtil.humText(hum));
        cqp_hum.setARCColor(CatDataTransferUtil.HumUtil.humColor(hum, this));
        if (cqp_hum.isAlreadySetPercent()) {
            cqp_hum.updatePercent(CatDataTransferUtil.HumUtil.humPercent(hum));
        } else {
            cqp_hum.setPercent(CatDataTransferUtil.HumUtil.humPercent(hum));
        }

        //甲醛
        tv_jiaquan1.setText(formal + "mg/m³");
        tv_jiaquan2.setText(CatDataTransferUtil.JiaquanUtil.jiaquanText(formal));
        cqp_jiaquan.setARCColor(CatDataTransferUtil.JiaquanUtil
                .jiaquanColor(formal, this));
        if (cqp_jiaquan.isAlreadySetPercent()) {
            cqp_jiaquan.updatePercent(CatDataTransferUtil.JiaquanUtil
                    .jiaquanPercent(formal));
        } else {
            cqp_jiaquan.setPercent(CatDataTransferUtil.JiaquanUtil
                    .jiaquanPercent(formal));
        }

        //tvoc
        tv_tvoc1.setText(tvoc + "mg/m³");
        tv_tvoc2.setText(CatDataTransferUtil.TvocUtil.tvocText(tvoc));
        cqp_tvoc.setARCColor(CatDataTransferUtil.TvocUtil
                .tvocColor(tvoc, this));
        if (cqp_tvoc.isAlreadySetPercent()) {
            cqp_tvoc.updatePercent(CatDataTransferUtil.TvocUtil
                    .tvocPercent(tvoc));
        } else {
            cqp_tvoc.setPercent(CatDataTransferUtil.TvocUtil.tvocPercent(tvoc));
        }


        //pm2.5
        tv_pm1.setText(pm+"μg/m³");
        tv_pm2.setText(CatDataTransferUtil.PmUtil.pmText(pm));
        cqp_pm.setARCColor(CatDataTransferUtil.PmUtil
                .pmColor(pm, this));
        if (cqp_pm.isAlreadySetPercent()) {
            cqp_pm.updatePercent(CatDataTransferUtil.PmUtil
                    .pmPercent(pm));
        } else {
            cqp_pm.setPercent(CatDataTransferUtil.PmUtil.pmPercent(pm));
        }

        //污染指数
        tv_wrzs1.setText(wrzs+"㎎/m³");
        tv_wrzs2.setText(CatDataTransferUtil.WrzsUtil.wrzsText(wrzs));
        cqp_wrzs.setARCColor(CatDataTransferUtil.WrzsUtil
                .wrzsColor(wrzs, this));
        if (cqp_wrzs.isAlreadySetPercent()) {
            cqp_wrzs.updatePercent(CatDataTransferUtil.WrzsUtil
                    .wrzsPercent(wrzs));
        } else {
            cqp_wrzs.setPercent(CatDataTransferUtil.WrzsUtil.wrzsPercent(wrzs));
        }

    }


    /**
     * 上传数据到服务器 二期接口
     *
     * @param airIndexBean
     */
    private void upload(AirIndexBean airIndexBean) {
        final org.json.JSONObject json = new org.json.JSONObject();

        //
        org.json.JSONObject jsonObject = new org.json.JSONObject();
        try {
            jsonObject.put("pm2Point5", "0");
            // jsonObject.put("pm2Point5", 0);
            jsonObject.put("temperature", airIndexBean.getTemperature());
            // jsonObject.put("temperature", 100);
            jsonObject.put("humidity", airIndexBean.getHumidity());
            // jsonObject.put("humidity", 100);
            jsonObject.put("methanal", airIndexBean.getFormaldehyde());
            // jsonObject.put("methanal", 0.0f);
            jsonObject.put("tvoc", airIndexBean.getAqi());
            // jsonObject.put("tvoc", 0.0f);
            // jsonObject.put("testTime", airIndexBean.getTime());
            jsonObject.put("testTime",
                    new Date(Long.parseLong(airIndexBean.getTime())));
            jsonObject.put("modelType", "airCat");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            json.put("apikey", "35a872f5-e411-484d-8e27-93583813d85d");
            json.put("data", jsonObject.toString());
            json.put("username", getUserName());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("==" + json.toString());
        new Thread() {
            public void run() {
                String result = uploadDo(json.toString());
                System.out.println("===" + result);
            };
        }.start();

    }

    /**
     * 调一期的借口
     *
     *
     */
    private void upload2(AirCatBean airCatBean) {
        UploadAsyncTask asyncTask = new UploadAsyncTask();
        asyncTask.execute(airCatBean);
    }

    // 数据上传请求
    private String uploadDo(String value) {
        String result = null;
        String baseurl = "http://192.168.9.130:8081/service/monitor/data/upload";

        HttpPost httpPost = new HttpPost(baseurl);
        // 设置httpPost请求参数
        HttpResponse httpResponse = null;
        try {
            StringEntity entity = new StringEntity(value, HTTP.UTF_8);
            httpPost.setEntity(entity);
            httpPost.addHeader("Accept", "application/json");
            httpPost.addHeader("Content-Type", "application/json");
            httpPost.addHeader("charset", HTTP.UTF_8);
            httpResponse = new DefaultHttpClient().execute(httpPost);
            Log.i("======weiz", "请求状态"
                    + httpResponse.getStatusLine().getStatusCode());
            System.out.println(""
                    + httpResponse.getStatusLine().getStatusCode());
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                // 使用getEntity方法活得返回结果
                result = EntityUtils.toString(httpResponse.getEntity());

            } else {
                result = "";
            }
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }

    private class UploadAsyncTask extends AsyncTask<Object, Object, Object> {

        @Override
        protected Object doInBackground(Object... params) {
            AirCatBean airCatBean = (AirCatBean) params[0];

            org.json.JSONObject jsonObject = new org.json.JSONObject();
            try {

                jsonObject.put("username", getUserName());
                jsonObject.put("deviceId", MAC);
                jsonObject.put("pm2Point5", airCatBean.getPm25());
                jsonObject.put("temperature", airCatBean.getTemperature());
                jsonObject.put("humidity", airCatBean.getHumidity());
                jsonObject.put("methanal", airCatBean.getFormol());
                jsonObject.put("tvoc", airCatBean.getTVOC());
                jsonObject.put("pollutionlevel",airCatBean.getPollutionLevel());
                jsonObject.put("testTime",
                        TimeStampUtil.currTime3(System.currentTimeMillis()+""));

                jsonObject.put("modelType", "airCat");
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            String result = HttpTools.httppost(Base_Url.uploaddetail_Url
                            + ((MyApplication) getApplication()).ApiKey,
                    jsonObject.toString());
            OemLog.log("upload",result);
            //System.out.println("调一期上传数据的借口-返回的数据=" + result);

            return result;
        }

    }

    /**
     * 从服务器请求历史数据
     *
     * @param starttime
     * @param endtime
     * @param flag 0:取最近一条历史数据。1:温度。2:湿度。3:甲醛。4:tvoc。
     * @return
     */
    private void download(String starttime,String endtime,int flag){
        System.out.println("starttime======"+starttime);
        System.out.println("endtime======"+endtime);
        String token = getToken();
        String username = getUserName();
        //HttpTools.totalDNumber(token, username,"airCat", starttime, endtime, 1, handler, this, MyApliction.app);
        //System.out.println("appid=="+MyApliction.getInstance().ApiKey);
        //HttpTools.DownLaodValue(token, username, "airCat", starttime, endtime, 1, handler, this, MyApliction.app);
        HttpTools.DownLaodValue24(token, username, "airCat", starttime, endtime, flag, handler, this, MyApplication.getInstance());
    }



    /**
     * 获取本地保存的用户名
     *
     * @return
     */
    private String getUserName() {
        String user = sp.getString("user", "");
        String username = "";
        if (!user.equals("")) {
            JSONObject jsonObject = JSONObject.parseObject(user);
            username = jsonObject.getString("username");
        }
        return username;
    }

    private int srand_a[] = { 1, 3, 5, 7, 9, 0, 2, 4, 6, 8 };
    private int srand_b[] = { 1, 2, 4, 6, 8, 1, 3, 5, 7, 9 };

    private float formula(String str) {
        if (StringUtil.isEmpty(str)) {
            return 0;
        }
        // 值
        int v = Integer.parseInt(str.substring(0, str.length() - 1));
        // 位置
        int i = Integer.parseInt(str.substring(str.length() - 1));

        // 新算法
        float f = ((float) v / 1000 / srand_b[i]) - srand_a[i];
        // 保留小数点后一位
        return Math.round(f * 100) / 100.0f;
    }

    private void closeWait(){
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void showProgress(String message) {
        if (progressDialog == null) {
            progressDialog = ProgressDialog.show(this, "", message, true, true,
                    new DialogInterface.OnCancelListener() {

                        public void onCancel(DialogInterface arg0) {
                            // finish();
                        }
                    });
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
        } else {
            progressDialog.setMessage(message);
        }
        progressDialog.show();
    }

    /**
     * 仪器关机，清空数据
     * */

    private void resetview() {
        // 温度
        tv_tem1.setText("℃");
        tv_tem2.setText("无");
        cqp_tem.setPercent(0.0f);

        // 湿度
        tv_hum1.setText("RH");
        tv_hum2.setText("无");
        cqp_hum.setPercent(0.0f);

        // TVOC
        tv_tvoc1.setText("mg/m³");
        tv_tvoc2.setText("无");
        cqp_tvoc.setPercent(0.0f);

        // 甲醛
        tv_jiaquan1.setText("mg/m³");
        tv_jiaquan2.setText("无");
        cqp_jiaquan.setPercent(0.0f);

        //pm2.5


        //污染指数



    }

    /**
     * 设备关机
     */
    private void deviceOff() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage(getString(R.string.hint_device_off));
        builder.setTitle("提示");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });
        builder.create().show();
    }

    /***
     * 设备低电量提示
     */
    private void lowPowerWarning() {
        firstWarning = false;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        dialog.setContentView(R.layout.dialog_low_power);
    }

    @Override
    public void onBackPressed() {
        closeBluetooth();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        closeBluetooth();
        super.onDestroy();
    }

    protected void closeBluetooth() {
        if (mBluetoothManage != null) {
            mBluetoothManage.disable();
//            mBluetoothManage.off();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(CONTACT_STATE==0){
            download(TimeStampUtil.past24Hour2(0), TimeStampUtil.currTime2(0), 0);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onLocationReceived(BDLocation bdlocation){
        //收到定位后，去请求室外空气质量
        final String cityname = CityNameUtil.transfer(bdlocation.getCity());
        new Thread(){
            public void run() {
                HttpTools.getOutDoorAqi(cityname, handler,OUTDOORAQI);
            };
        }.start();
    }

    @Override
    public void onWeatherReceived(Object weather){
        // 收到service获取的天气数据
        if(weather==null){
            return ;
        }
        String weat = (String) weather;

        String date = getDate(weat);
        if(date!=null&&!date.equals("")){
            //使用接口返回的日期,会有错，所以放弃
            //tv_date.setText(date);
            //使用手机系统时间
            tv_date.setText(TimeStampUtil.currTime2(2));
        }

        String tem = getTem(weat);
        if(tem!=null&&!tem.equals("")){
            tv_tem.setText(tem);
        }

        String s = getWeather(weat);
        if(s!=null&&!s.equals("")){
            tv_tianqi.setText(s);
            initWeatherIcon(iv_tianqi, s);
        }

        unbindService(sc);
        stopService(new Intent(AirCatBlueActivity.this, CatService.class));
    }

    private void initWeatherIcon(ImageView iv, String weat) {
        boolean isDay = false;// true是白天，false是夜间
        Date date = new Date(System.currentTimeMillis());
        DateFormat dateFormat = new SimpleDateFormat("HH");
        String h = dateFormat.format(date);
        int ho = Integer.parseInt(h);
        if (6 <= ho && ho <= 18) {
            isDay = true;
        }
        if (isDay) {
            // 白天
            if (weat.equals("晴")) {
                iv.setImageLevel(100);
            }
            if (weat.equals("多云")) {
                iv.setImageLevel(101);
            }
            if (weat.equals("阴")) {
                iv.setImageLevel(102);
            }
            if (weat.equals("阵雨")) {
                iv.setImageLevel(103);
            }
            if (weat.equals("雷阵雨")) {
                iv.setImageLevel(104);
            }
            if (weat.equals("雷阵雨伴有冰雹")) {
                iv.setImageLevel(105);
            }
            if (weat.equals("雨夹雪")) {
                iv.setImageLevel(106);
            }
            if (weat.equals("小雨")) {
                iv.setImageLevel(107);
            }
            if (weat.equals("中雨")) {
                iv.setImageLevel(108);
            }
            if (weat.equals("大雨")) {
                iv.setImageLevel(109);
            }
            if (weat.equals("暴雨")) {
                iv.setImageLevel(110);
            }
            if (weat.equals("大暴雨")) {
                iv.setImageLevel(111);
            }
            if (weat.equals("特大暴雨")) {
                iv.setImageLevel(112);
            }
            if (weat.equals("阵雪")) {
                iv.setImageLevel(113);
            }
            if (weat.equals("小雪")) {
                iv.setImageLevel(114);
            }
            if (weat.equals("中雪")) {
                iv.setImageLevel(115);
            }
            if (weat.equals("大雪")) {
                iv.setImageLevel(116);
            }
            if (weat.equals("暴雪")) {
                iv.setImageLevel(117);
            }
            if (weat.equals("雾")) {
                iv.setImageLevel(118);
            }
            if (weat.equals("冻雨")) {
                iv.setImageLevel(119);
            }
            if (weat.equals("沙尘暴")) {
                iv.setImageLevel(120);
            }
            if (weat.equals("小到中雨")) {
                iv.setImageLevel(121);
            }
            if (weat.equals("中到大雨")) {
                iv.setImageLevel(122);
            }
            if (weat.equals("大到暴雨")) {
                iv.setImageLevel(123);
            }
            if (weat.equals("暴雨到大暴雨")) {
                iv.setImageLevel(124);
            }
            if (weat.equals("大暴雨到特大暴雨")) {
                iv.setImageLevel(125);
            }
            if (weat.equals("小到中雪")) {
                iv.setImageLevel(126);
            }
            if (weat.equals("中到大雪")) {
                iv.setImageLevel(127);
            }
            if (weat.equals("大到暴雪")) {
                iv.setImageLevel(128);
            }
            if (weat.equals("浮尘")) {
                iv.setImageLevel(129);
            }
            if (weat.equals("扬沙")) {
                iv.setImageLevel(130);
            }
            if (weat.equals("强沙尘暴")) {
                iv.setImageLevel(131);
            }
            if (weat.equals("霾")) {
                iv.setImageLevel(153);
            }
        } else {
            // 夜间
            if (weat.equals("晴")) {
                iv.setImageLevel(200);
            }
            if (weat.equals("多云")) {
                iv.setImageLevel(201);
            }
            if (weat.equals("阴")) {
                iv.setImageLevel(202);
            }
            if (weat.equals("阵雨")) {
                iv.setImageLevel(203);
            }
            if (weat.equals("雷阵雨")) {
                iv.setImageLevel(204);
            }
            if (weat.equals("雷阵雨伴有冰雹")) {
                iv.setImageLevel(205);
            }
            if (weat.equals("雨夹雪")) {
                iv.setImageLevel(206);
            }
            if (weat.equals("小雨")) {
                iv.setImageLevel(207);
            }
            if (weat.equals("中雨")) {
                iv.setImageLevel(208);
            }
            if (weat.equals("大雨")) {
                iv.setImageLevel(209);
            }
            if (weat.equals("暴雨")) {
                iv.setImageLevel(210);
            }
            if (weat.equals("大暴雨")) {
                iv.setImageLevel(211);
            }
            if (weat.equals("特大暴雨")) {
                iv.setImageLevel(212);
            }
            if (weat.equals("阵雪")) {
                iv.setImageLevel(213);
            }
            if (weat.equals("小雪")) {
                iv.setImageLevel(214);
            }
            if (weat.equals("中雪")) {
                iv.setImageLevel(215);
            }
            if (weat.equals("大雪")) {
                iv.setImageLevel(216);
            }
            if (weat.equals("暴雪")) {
                iv.setImageLevel(217);
            }
            if (weat.equals("雾")) {
                iv.setImageLevel(218);
            }
            if (weat.equals("冻雨")) {
                iv.setImageLevel(219);
            }
            if (weat.equals("沙尘暴")) {
                iv.setImageLevel(220);
            }
            if (weat.equals("小到中雨")) {
                iv.setImageLevel(221);
            }
            if (weat.equals("中到大雨")) {
                iv.setImageLevel(222);
            }
            if (weat.equals("大到暴雨")) {
                iv.setImageLevel(223);
            }
            if (weat.equals("暴雨到大暴雨")) {
                iv.setImageLevel(224);
            }
            if (weat.equals("大暴雨到特大暴雨")) {
                iv.setImageLevel(225);
            }
            if (weat.equals("小到中雪")) {
                iv.setImageLevel(226);
            }
            if (weat.equals("中到大雪")) {
                iv.setImageLevel(227);
            }
            if (weat.equals("大到暴雪")) {
                iv.setImageLevel(228);
            }
            if (weat.equals("浮尘")) {
                iv.setImageLevel(229);
            }
            if (weat.equals("扬沙")) {
                iv.setImageLevel(230);
            }
            if (weat.equals("强沙尘暴")) {
                iv.setImageLevel(231);
            }
            if (weat.equals("霾")) {
                iv.setImageLevel(253);
            }
        }
    }

    /**
     * 从返回的天气json数据中获取日期
     *
     * @param weather
     * @return
     */
    private String getDate(String weather) {
        if (weather == null || weather.equals("")) {
            return "";
        }
        JSONObject object = JSONObject.parseObject(weather);
        if(object==null){
            return "";
        }
        JSONObject object2 = object.getJSONObject("retData");
        if(object2==null){
            return "";
        }
        String date = object2.getString("date");
        if (date == null || date.equals("")) {
            return "";
        }
        String[] dates = date.split("-");
        DateFormat f = new SimpleDateFormat("yy-MM-dd");
        Date xingqi = new Date();
        try {
            xingqi = f.parse(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        DateFormat dateFormat = new SimpleDateFormat("EEEE");
        String xq = dateFormat.format(xingqi);

        return dates[1] + "月" + dates[2] + "日" + "  " + xq;
    }

    /**
     * 从返回的天气json中获取温度
     *
     * @param weather
     * @return
     */
    private String getTem(String weather) {
        if (weather == null || weather.equals("")) {
            return "";
        }
        JSONObject object = JSONObject.parseObject(weather);
        if(object==null){
            return "";
        }
        JSONObject object2 = object.getJSONObject("retData");
        if(object2==null){
            return "";
        }
        String h_tem = object2.getString("h_tmp");
        String l_tem = object2.getString("l_tmp");
        return h_tem + "°/" + l_tem + "°";
    }

    /**
     * 从返回的天气json中获取天气
     *
     * @param weather
     * @return
     */
    private String getWeather(String weather) {
        if (weather == null || weather.equals("")) {
            return "";
        }
        JSONObject object = JSONObject.parseObject(weather);
        if(object==null){
            return "";
        }
        JSONObject object2 = object.getJSONObject("retData");
        if(object2==null){
            return "";
        }
        String weat = object2.getString("weather");
        return weat;
    }

    private List<TimeBean> getPastSevenDay() {
        List<TimeBean> beans = new ArrayList<TimeBean>();
        for (int i = -6; i < 1; i++) {
            TimeBean bean = new TimeBean();
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, i);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            bean.setYear(year);
            bean.setMonth(month);
            bean.setDay(day);
            beans.add(bean);
        }
        return beans;
    }

    /**
     * 显示温度历史数据
     */
    private void showDialogHistoryTem(List<AirIndexBean> airIndexBeans,
                                      Context context) {
        // float[] tems = extractionIndividualData(airIndexBeans, 1);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(true);

        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.dialog_cat_history_data);

        TextView tvtitle = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_title);
        tvtitle.setText("过去24小时趋势图");
        TextView tv_unit = (TextView) window
                .findViewById(R.id.tv_unit);
        tv_unit.setText("温度(°C)");
        LineChart mChart = (LineChart) window.findViewById(R.id.lc_cat_history);
        ImageView iv_close = (ImageView) window
                .findViewById(R.id.iv_cat_history_close);
        iv_close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                clickable = true;
            }
        });

        RelativeLayout rlremark1 = (RelativeLayout) window
                .findViewById(R.id.rl_cat_history_dialog_remark1);
        RelativeLayout rlremark2 = (RelativeLayout) window
                .findViewById(R.id.rl_cat_history_dialog_remark2);
        RelativeLayout rlremark3 = (RelativeLayout) window
                .findViewById(R.id.rl_cat_history_dialog_remark3);
        RelativeLayout rlremark4 = (RelativeLayout) window
                .findViewById(R.id.rl_cat_history_dialog_remark4);
        RelativeLayout rlremark5 = (RelativeLayout) window
                .findViewById(R.id.rl_cat_history_dialog_remark5);
        RelativeLayout rlremark6 = (RelativeLayout) window
                .findViewById(R.id.rl_cat_history_dialog_remark6);

        TextView tvremarkwords1 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark1_words);
        TextView tvremarkwords2 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark2_words);
        TextView tvremarkwords3 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark3_words);
        TextView tvremarkwords4 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark4_words);
        TextView tvremarkwords5 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark5_words);
        TextView tvremarkwords6 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark6_words);

        TextView tvremarkvalues1 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark1_values);
        TextView tvremarkvalues2 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark2_values);
        TextView tvremarkvalues3 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark3_values);
        TextView tvremarkvalues4 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark4_values);
        TextView tvremarkvalues5 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark5_values);
        TextView tvremarkvalues6 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark6_values);

        tvremarkwords1.setText("极冷");


        tvremarkwords3.setText("偏热");
        tvremarkwords4.setText("偏冷");
        tvremarkwords5.setText("舒适（空调）");
        tvremarkwords6.setText("热");

        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        if (month >= 5 && month <= 10) {
            tvremarkwords2.setText("舒适");
            tvremarkvalues1.setText("<=12℃");
            tvremarkvalues2.setText("23-28℃");
            tvremarkvalues3.setText("28.1-33℃");
            tvremarkvalues4.setText("12.1-18℃");
            tvremarkvalues5.setText("18.1-24℃");
            tvremarkvalues6.setText(">=33.1℃");


        } else {
            tvremarkwords2.setText("舒适（供暖）");
            tvremarkvalues1.setText("<=12℃");
            tvremarkvalues2.setText("17.1-25℃");
            tvremarkvalues3.setText("25.1-33℃");
            tvremarkvalues4.setText("12.1-17℃");
            tvremarkvalues5.setText("19-24℃");
            tvremarkvalues6.setText(">=33.1℃");

        }

        initChart(mChart, 1);
        setData(mChart, airIndexBeans, 2);
    }

    /**
     * 显示湿度历史数据
     */
    private void showDialogHistoryHum(List<AirIndexBean> airIndexBeans) {
        // float[] hums = extractionIndividualData(airIndexBeans, 2);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(true);

        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.dialog_cat_history_data);

        TextView tvtitle = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_title);
        tvtitle.setText("过去24小时趋势图");
        TextView tv_unit = (TextView) window
                .findViewById(R.id.tv_unit);
        tv_unit.setText("湿度(%RH)");
        LineChart mChart = (LineChart) window.findViewById(R.id.lc_cat_history);
        ImageView iv_close = (ImageView) window
                .findViewById(R.id.iv_cat_history_close);
        iv_close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                clickable = true;
            }
        });

        RelativeLayout rlremark1 = (RelativeLayout) window
                .findViewById(R.id.rl_cat_history_dialog_remark1);
        RelativeLayout rlremark2 = (RelativeLayout) window
                .findViewById(R.id.rl_cat_history_dialog_remark2);
        RelativeLayout rlremark3 = (RelativeLayout) window
                .findViewById(R.id.rl_cat_history_dialog_remark3);
        RelativeLayout rlremark4 = (RelativeLayout) window
                .findViewById(R.id.rl_cat_history_dialog_remark4);
        RelativeLayout rlremark5 = (RelativeLayout) window
                .findViewById(R.id.rl_cat_history_dialog_remark5);
        RelativeLayout rlremark6 = (RelativeLayout) window
                .findViewById(R.id.rl_cat_history_dialog_remark6);

        TextView tvremarkwords1 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark1_words);
        TextView tvremarkwords2 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark2_words);
        TextView tvremarkwords3 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark3_words);
        TextView tvremarkwords4 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark4_words);
        TextView tvremarkwords5 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark5_words);
        TextView tvremarkwords6 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark6_words);

        TextView tvremarkvalues1 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark1_values);
        TextView tvremarkvalues2 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark2_values);
        TextView tvremarkvalues3 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark3_values);
        TextView tvremarkvalues4 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark4_values);
        TextView tvremarkvalues5 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark5_values);
        TextView tvremarkvalues6 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark6_values);


        rlremark3.setVisibility(View.GONE);
        rlremark6.setVisibility(View.GONE);

        tvremarkwords1.setText("干燥");
        tvremarkwords2.setText("舒适（供暖）");

        tvremarkwords4.setText("湿润");
        tvremarkwords5.setText("舒适（空调）");
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        if (month >= 5 && month <= 10) {
            tvremarkvalues1.setText("<=30%");
            tvremarkvalues2.setText("30.1%-60%");
            tvremarkvalues4.setText(">=60.1%");
            tvremarkvalues5.setText("40%-50%");
        }else{
            tvremarkvalues1.setText("<=30%");
            tvremarkvalues2.setText("30.1%-80%");
            tvremarkvalues4.setText(">=80.1%");
            tvremarkvalues5.setText("40%-50%");
        }


        initChart(mChart, 2);
        setData(mChart, airIndexBeans, 1);
    }



    /**
     * 显示甲醛历史数据
     */
    private void showDialogHistoryForma(List<AirIndexBean> airIndexBeans) {
        // float[] hums = extractionIndividualData(airIndexBeans, 3);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(true);

        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.dialog_cat_history_data);

        TextView tvtitle = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_title);
        tvtitle.setText("过去24小时趋势图");
        TextView tv_unit = (TextView) window
                .findViewById(R.id.tv_unit);
        tv_unit.setText("甲醛(mg/m3)");
        LineChart mChart = (LineChart) window.findViewById(R.id.lc_cat_history);
        ImageView iv_close = (ImageView) window
                .findViewById(R.id.iv_cat_history_close);
        iv_close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                clickable = true;
            }
        });

        RelativeLayout rlremark1 = (RelativeLayout) window
                .findViewById(R.id.rl_cat_history_dialog_remark1);
        RelativeLayout rlremark2 = (RelativeLayout) window
                .findViewById(R.id.rl_cat_history_dialog_remark2);
        RelativeLayout rlremark3 = (RelativeLayout) window
                .findViewById(R.id.rl_cat_history_dialog_remark3);
        RelativeLayout rlremark4 = (RelativeLayout) window
                .findViewById(R.id.rl_cat_history_dialog_remark4);
        RelativeLayout rlremark5 = (RelativeLayout) window
                .findViewById(R.id.rl_cat_history_dialog_remark5);
        RelativeLayout rlremark6 = (RelativeLayout) window
                .findViewById(R.id.rl_cat_history_dialog_remark6);

        TextView tvremarkwords1 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark1_words);
        TextView tvremarkwords2 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark2_words);
        TextView tvremarkwords3 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark3_words);
        TextView tvremarkwords4 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark4_words);
        TextView tvremarkwords5 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark5_words);
        TextView tvremarkwords6 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark6_words);

        TextView tvremarkvalues1 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark1_values);
        TextView tvremarkvalues2 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark2_values);
        TextView tvremarkvalues3 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark3_values);
        TextView tvremarkvalues4 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark4_values);
        TextView tvremarkvalues5 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark5_values);
        TextView tvremarkvalues6 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark6_values);

        tvremarkvalues1.setText("<=0.08mg/m³");
        tvremarkvalues2.setText("0.09-0.1mg/m³");
        tvremarkvalues3.setText("0.11-0.3mg/m³");
        tvremarkvalues4.setText("0.31-0.5mg/m³");
        tvremarkvalues5.setText("0.51-0.7mg/m³");
        tvremarkvalues6.setText(">=0.71mg/m³");

        initChart(mChart, 3);
        setData(mChart, airIndexBeans, 3);
    }

    /**
     * 显示tvoc历史数据
     */
    private void showDialogHistoryTvoc(List<AirIndexBean> airIndexBeans) {
        // float[] hums = extractionIndividualData(airIndexBeans, 4);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(true);

        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.dialog_cat_history_data);

        TextView tvtitle = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_title);
        tvtitle.setText("过去24小时趋势图");
        TextView tv_unit = (TextView) window
                .findViewById(R.id.tv_unit);
        tv_unit.setText("TVOC(mg/m3)");
        LineChart mChart = (LineChart) window.findViewById(R.id.lc_cat_history);
        ImageView iv_close = (ImageView) window
                .findViewById(R.id.iv_cat_history_close);
        iv_close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                clickable = true;
            }
        });

        RelativeLayout rlremark1 = (RelativeLayout) window
                .findViewById(R.id.rl_cat_history_dialog_remark1);
        RelativeLayout rlremark2 = (RelativeLayout) window
                .findViewById(R.id.rl_cat_history_dialog_remark2);
        RelativeLayout rlremark3 = (RelativeLayout) window
                .findViewById(R.id.rl_cat_history_dialog_remark3);
        RelativeLayout rlremark4 = (RelativeLayout) window
                .findViewById(R.id.rl_cat_history_dialog_remark4);
        RelativeLayout rlremark5 = (RelativeLayout) window
                .findViewById(R.id.rl_cat_history_dialog_remark5);
        RelativeLayout rlremark6 = (RelativeLayout) window
                .findViewById(R.id.rl_cat_history_dialog_remark6);

        TextView tvremarkwords1 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark1_words);
        TextView tvremarkwords2 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark2_words);
        TextView tvremarkwords3 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark3_words);
        TextView tvremarkwords4 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark4_words);
        TextView tvremarkwords5 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark5_words);
        TextView tvremarkwords6 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark6_words);

        TextView tvremarkvalues1 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark1_values);
        TextView tvremarkvalues2 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark2_values);
        TextView tvremarkvalues3 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark3_values);
        TextView tvremarkvalues4 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark4_values);
        TextView tvremarkvalues5 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark5_values);
        TextView tvremarkvalues6 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark6_values);

        tvremarkvalues1.setText("<=0.16mg/m³");
        tvremarkvalues2.setText("0.17-0.5mg/m³");
        tvremarkvalues3.setText("0.51-1.2mg/m³");
        tvremarkvalues4.setText("1.21-1.8mg/m³");
        tvremarkvalues5.setText("1.81-2.5mg/m³");
        tvremarkvalues6.setText(">=2.51mg/m³");

        initChart(mChart, 4);
        setData(mChart, airIndexBeans, 4);

    }

    //显示pm历史数据
    private void showDialogHistoryPM(List<AirIndexBean> airIndexBeans) {
        // float[] hums = extractionIndividualData(airIndexBeans, 4);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(true);

        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.dialog_cat_history_data);

        TextView tvtitle = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_title);
        tvtitle.setText("过去24小时趋势图");
        TextView tv_unit = (TextView) window
                .findViewById(R.id.tv_unit);
        tv_unit.setText("PM2.5(μg/m3)");
        LineChart mChart = (LineChart) window.findViewById(R.id.lc_cat_history);
        ImageView iv_close = (ImageView) window
                .findViewById(R.id.iv_cat_history_close);
        iv_close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                clickable = true;
            }
        });

        RelativeLayout rlremark1 = (RelativeLayout) window
                .findViewById(R.id.rl_cat_history_dialog_remark1);
        RelativeLayout rlremark2 = (RelativeLayout) window
                .findViewById(R.id.rl_cat_history_dialog_remark2);
        RelativeLayout rlremark3 = (RelativeLayout) window
                .findViewById(R.id.rl_cat_history_dialog_remark3);
        RelativeLayout rlremark4 = (RelativeLayout) window
                .findViewById(R.id.rl_cat_history_dialog_remark4);
        RelativeLayout rlremark5 = (RelativeLayout) window
                .findViewById(R.id.rl_cat_history_dialog_remark5);
        RelativeLayout rlremark6 = (RelativeLayout) window
                .findViewById(R.id.rl_cat_history_dialog_remark6);

        TextView tvremarkwords1 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark1_words);
        TextView tvremarkwords2 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark2_words);
        TextView tvremarkwords3 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark3_words);
        TextView tvremarkwords4 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark4_words);
        TextView tvremarkwords5 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark5_words);
        TextView tvremarkwords6 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark6_words);

        TextView tvremarkvalues1 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark1_values);
        TextView tvremarkvalues2 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark2_values);
        TextView tvremarkvalues3 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark3_values);
        TextView tvremarkvalues4 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark4_values);
        TextView tvremarkvalues5 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark5_values);
        TextView tvremarkvalues6 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark6_values);

        tvremarkvalues1.setText("<=35μg/m³");
        tvremarkvalues2.setText("35.1-75μg/m³");
        tvremarkvalues3.setText("75.1-115μg/m³");
        tvremarkvalues4.setText("115.1-150μg/m³");
        tvremarkvalues5.setText("150.1-250μg/m³");
        tvremarkvalues6.setText(">=250μg/m³");

        initChart(mChart, 5);
        setData(mChart, airIndexBeans, 5);

    }

    //显示污染指数历史数据
    private void showDialogHistoryPoll(List<AirIndexBean> airIndexBeans) {
        // float[] hums = extractionIndividualData(airIndexBeans, 4);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(true);

        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.dialog_cat_history_data);

        TextView tvtitle = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_title);
        tvtitle.setText("过去24小时趋势图");
        TextView tv_unit = (TextView) window
                .findViewById(R.id.tv_unit);
        tv_unit.setText("空气污染指数(㎎/m3)");
        LineChart mChart = (LineChart) window.findViewById(R.id.lc_cat_history);
        ImageView iv_close = (ImageView) window
                .findViewById(R.id.iv_cat_history_close);
        iv_close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                clickable = true;
            }
        });

        RelativeLayout rlremark1 = (RelativeLayout) window
                .findViewById(R.id.rl_cat_history_dialog_remark1);
        RelativeLayout rlremark2 = (RelativeLayout) window
                .findViewById(R.id.rl_cat_history_dialog_remark2);
        RelativeLayout rlremark3 = (RelativeLayout) window
                .findViewById(R.id.rl_cat_history_dialog_remark3);
        RelativeLayout rlremark4 = (RelativeLayout) window
                .findViewById(R.id.rl_cat_history_dialog_remark4);
        RelativeLayout rlremark5 = (RelativeLayout) window
                .findViewById(R.id.rl_cat_history_dialog_remark5);
        RelativeLayout rlremark6 = (RelativeLayout) window
                .findViewById(R.id.rl_cat_history_dialog_remark6);

        TextView tvremarkwords1 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark1_words);
        TextView tvremarkwords2 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark2_words);
        TextView tvremarkwords3 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark3_words);
        TextView tvremarkwords4 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark4_words);
        TextView tvremarkwords5 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark5_words);
        TextView tvremarkwords6 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark6_words);

        TextView tvremarkvalues1 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark1_values);
        TextView tvremarkvalues2 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark2_values);
        TextView tvremarkvalues3 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark3_values);
        TextView tvremarkvalues4 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark4_values);
        TextView tvremarkvalues5 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark5_values);
        TextView tvremarkvalues6 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark6_values);

        tvremarkvalues1.setText("0-50㎎/m³");
        tvremarkvalues2.setText("51-100㎎/m³");
        tvremarkvalues3.setText("101-150㎎/m³");
        tvremarkvalues4.setText("151-200㎎/m³");
        tvremarkvalues5.setText("201-300㎎/m³");
        tvremarkvalues6.setText("301-500㎎/m³");

        initChart(mChart, 6);
        setData(mChart, airIndexBeans, 6);

    }



    /**
     * 从历史数据提取
     *
     * @param airIndexBeans
     * @param flag
     *            1温度。2湿度。3甲醛。4tvoc
     * @return
     */
    private float[] extractionIndividualData(List<AirIndexBean> airIndexBeans,
                                             int flag) {
        if (airIndexBeans == null || airIndexBeans.size() <= 0) {
            return new float[0];
        }
        int size = airIndexBeans.size();
        float[] array = new float[size];
        for (int i = 0; i < size; i++) {
            AirIndexBean airIndexBean = airIndexBeans.get(i);
            String content = "";
            switch (flag) {
                case 1:
                    content = airIndexBean.getTemperature();
                    break;
                case 2:
                    content = airIndexBean.getHumidity();
                    break;
                case 3:
                    content = airIndexBean.getFormaldehyde();
                    break;
                case 4:
                    content = airIndexBean.getAqi();
                    break;
            }
            float cont_f = Float.parseFloat(content);
            cont_f = new BigDecimal(cont_f).setScale(2,
                    BigDecimal.ROUND_HALF_UP).floatValue();
            array[i] = cont_f;
        }
        return array;
    }

    /**
     * 从本地数据库查询历史数据的异步任务
     *
     * @author jiantaotu
     *
     */
    private class myAsyncTask extends AsyncTask<Object, Object, Object> {
        private int flag;
        private Context context;

        @Override
        protected Object doInBackground(Object... params) {
            // TODO Auto-generated method stub
            List<TimeBean> beans = (List<TimeBean>) params[0];
            context = (Context) params[1];
            flag = (int) params[2];

            String username = (String) params[3];

            // List<AirIndexBean> airIndexBeans_for_tem = CatDBmanager
            // .getInstance(context).getPastSevenDayData(daybeans_past);
            List<AirIndexBean> airIndexBeans_for_tem = CatDBmanager
                    .getInstance(context).getPast24HourData(username);

            return airIndexBeans_for_tem;
        }

        @Override
        protected void onProgressUpdate(Object... values) {
            // TODO Auto-generated method stub
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            List<AirIndexBean> airIndexBeans = (List<AirIndexBean>) result;

            // 将进度dialog 取消
            if (dialogprogress.isShowing()) {
                dialogprogress.dismiss();
            }

            switch (flag) {
                case 1:
                    // 温度
                    showDialogHistoryTem(airIndexBeans, context);
                    break;
                case 2:
                    // 湿度
                    showDialogHistoryHum(airIndexBeans);
                    break;
                case 3:
                    // 甲醛
                    showDialogHistoryForma(airIndexBeans);
                    break;
                case 4:
                    // tvoc
                    showDialogHistoryTvoc(airIndexBeans);
                    break;
                case 5:
                    //pm2.5
                    showDialogHistoryPM(airIndexBeans);
                    break;
                case 6:
                    //污染指数
                    showDialogHistoryPoll(airIndexBeans);
                    break;
            }



        }
    }

    private class NetStateChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager manager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mobile = manager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo net = manager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo active = manager.getActiveNetworkInfo();
            if (active == null) {
                // 无网
            } else {
                // 有网
                if (active.isAvailable()) {
                    // 可用
                    if (active.isConnected()) {
                        // 已连上
                        getWeather();
                    } else {
                        // 未连上
                    }
                } else {
                    // 不可用
                }
            }
        }

    }

    private void initChart(final LineChart mChart, int flag) {
        mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Bold.ttf");

        mChart.setOnChartGestureListener(new OnChartGestureListener() {

            @Override
            public void onChartTranslate(MotionEvent arg0, float arg1,
                                         float arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onChartSingleTapped(MotionEvent arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onChartScale(MotionEvent arg0, float arg1, float arg2) {
				/*// TODO Auto-generated method stub
				// mChart.getXAxis().setLabelsToSkip(0);
				Log.d("ACTION", arg0.getAction() + "");
				int maxindex = mChart.getHighestVisibleXIndex();
				int minndex = mChart.getLowestVisibleXIndex();
				int count = maxindex - minndex;
				if (count < 24 && count > 12) {
					mChart.getXAxis().setLabelsToSkip(3);
				}
				if (count <= 12 && count > 6) {
					mChart.getXAxis().setLabelsToSkip(0);
				}
				if (count <= 6 && count >= 0) {
					mChart.getXAxis().setLabelsToSkip(0);
				}
				Log.d("xlables", "  minndex" + minndex + " minndex" + maxindex
						+ "wwwcount" + count);*/
            }


            @Override
            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override
            public void onChartLongPressed(MotionEvent arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onChartFling(MotionEvent arg0, MotionEvent arg1,
                                     float arg2, float arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onChartDoubleTapped(MotionEvent arg0) {
                // TODO Auto-generated method stub

            }
        });
        mChart.setOnChartValueSelectedListener(this);

        mChart.setMaxVisibleValueCount(24);
        // 设置网格
        mChart.setDrawBorders(false);

        mChart.setDrawGridBackground(false);
        mChart.setGridBackgroundColor(Color.rgb(235, 246, 247));
        mChart.setAlpha(0.8f);
        // 在chart上的右下角加描述
        mChart.setDescription("");

        // 设置是否可以触摸，如为false，则不能拖动，缩放等
        mChart.setTouchEnabled(true);
        // 设置是否可以拖拽，缩放
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);
        // 设置是否能扩大扩小
        mChart.setPinchZoom(false);
        mChart.getLegend().setEnabled(false);
        // 设置背景颜色
        // mChart.setBackgroundColor(getResources().getColor(high));
        // 设置点击chart图对应的数据弹出标注
        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view, 0);
        // define an offset to change the original position of the marker
        // (optional)
        // mv.setOffsets(-mv.getMeasuredWidth() / 2, -mv.getMeasuredHeight());
        // set the marker to the chart
        mChart.setMarkerView(mv);
        mChart.setNoDataTextDescription("没有历史记录");
        //mChart.setHighlightIndicatorEnabled(false);// 十字先

        XAxis xAxis = mChart.getXAxis();
        // xAxis.setValueFormatter(new MyCustomXAxisValueFormatter());
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawLabels(true);
        // xAxis.addLimitLine(llXAxis); // add x-axis limit line
        xAxis.setDrawGridLines(false);
        xAxis.setLabelsToSkip(0);
        xAxis.setAvoidFirstLastClipping(false);
        //xAxis.setSpaceBetweenLabels(7);
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawLimitLinesBehindData(true);
        leftAxis.setDrawLabels(true);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setLabelCount(4, false);
        leftAxis.setTextSize(13);
        leftAxis.setDrawTopYLabelEntry(true);
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawAxisLine(false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawLabels(true);
        //rightAxis.setValueFormatter((new MyYAxisValueFormatter("")));
        switch (flag) {
            case 1:
                // 温度
                //leftAxis.setValueFormatter(new MyYAxisValueFormatter("tem"));
                break;
            case 2:
                // 湿度
                //leftAxis.setValueFormatter(new MyYAxisValueFormatter("hum"));
                break;
            case 3:
                // 甲醛
               // leftAxis.setValueFormatter(new MyYAxisValueFormatter("forma"));
                break;
            case 4:
                // tvoc
               // leftAxis.setValueFormatter(new MyYAxisValueFormatter("tvoc"));
                break;
            case 5:
                //pm2.5

                break;
            case 6:
                //污染指数
                break;

        }


        // 设置显示动画
        mChart.animateX(1500);

        // // restrain the maximum scale-out factor
        // mChart.setScaleMinima(3f, 3f);

        // // center the view to a specific position inside the chart
        // mChart.centerViewPort(10, 50);

        // get the legend (only possible after setting data)
        // Legend l = mChart.getLegend();
        // // modify the legend ...
        // l.setPosition(LegendPosition.NONE);
        // l.setForm(LegendForm.CIRCLE);
        // mChart.setDrawLegend(false);

        // 刷新画面
        mChart.invalidate();
    }

    private void setData(LineChart mChart, List<AirIndexBean> airIndexBeans,
                         int flag) {
        float formaldehyde_maxy = 0,tvoc_maxy=0;
        if(airIndexBeans==null||airIndexBeans.size()==0){
            return;
        }
        formaldehyde_maxy = Float.parseFloat(airIndexBeans.get(0).getFormaldehyde());
        tvoc_maxy = Float.parseFloat(airIndexBeans.get(0).getAqi());
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < airIndexBeans.size(); i++) {
            //System.out.println("天==" + airIndexBeans.get(i).getHour());
            xVals.add(airIndexBeans.get(i).getHour() + ":00");
        }

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for (int i = 0; i < airIndexBeans.size(); i++) {
            switch (flag) {
                case 1:
                    // 湿度
                    // System.out.println("设置的图表中的数据==湿度"+airIndexBeans.get(i).getHumidity());
                    float hum_f = Float.parseFloat(airIndexBeans.get(i)
                            .getHumidity());
                    String hum_s = new DecimalFormat("#.0").format(hum_f);
                    yVals.add(new Entry(Float.parseFloat(hum_s), i));
                    break;
                case 2:
                    // 温度
                    float tem_f = Float.parseFloat(airIndexBeans.get(i)
                            .getTemperature());
                    String tem_s = new DecimalFormat("#.0").format(tem_f);
                    yVals.add(new Entry(Float.parseFloat(tem_s), i));
                    break;
                case 3:
                    // 甲醛
                    float forma_f = Float.parseFloat(airIndexBeans.get(i)
                            .getFormaldehyde());
                    formaldehyde_maxy = formaldehyde_maxy > forma_f ? formaldehyde_maxy
                            : forma_f;
                    String forma_s = new DecimalFormat("#.00").format(forma_f);
                    yVals.add(new Entry(Float.parseFloat(forma_s), i));
                    break;
                case 4:
                    // tvoc
                    float aqi_f = Float.parseFloat(airIndexBeans.get(i).getAqi());
                    tvoc_maxy = tvoc_maxy > aqi_f ? tvoc_maxy : aqi_f;
                    String aqi_s = new DecimalFormat("#.00").format(aqi_f);
                    yVals.add(new Entry(Float.parseFloat(aqi_s), i));
                    break;
                case 5:
                    //pm2.5

                    break;
                case 6:
                    //污染指数

                    break;

            }

        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals, "");

        set1.setDrawCubic(false); // 设置曲线为圆滑的线
        // set1.setCubicIntensity(0.2f);
        set1.setDrawFilled(false); // 设置包括的范围区域填充颜色
        set1.setDrawCircles(true); // 设置有圆点
        set1.setLineWidth(8f); // 设置线的宽度
        set1.setCircleSize(9f); // 设置小圆的大小
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setColor(Color.rgb(60, 193, 247)); // 设置曲线的颜色
        set1.setCircleColor(Color.rgb(60, 193, 247));
        set1.setCircleColorHole(Color.WHITE);
        set1.setDrawCircleHole(true);
        set1.setValueTextSize(0);// 圆点上文本大小

        switch (flag) {
            case 1:
                // 湿度
                break;
            case 2:
                // 温度
                break;
            case 3:
                // 甲醛
                if (formaldehyde_maxy < 0.04) {
                    mChart.getAxisLeft().setAxisMaxValue(0.045f);
                    mChart.getAxisLeft().setAxisMinValue(0.00f);
                }else{
                    mChart.getAxisLeft().resetAxisMaxValue();
                    mChart.getAxisLeft().resetAxisMinValue();
                }
                break;
            case 4:
                // tvoc
                if (tvoc_maxy < 0.04) {
                    mChart.getAxisLeft().setAxisMaxValue(0.045f);
                    mChart.getAxisLeft().setAxisMinValue(0.00f);
                }else{
                    mChart.getAxisLeft().resetAxisMaxValue();
                    mChart.getAxisLeft().resetAxisMinValue();
                }
                break;
            case 5:
                //pm2.5

                break;
            case 6:
                //污染指数

                break;
        }
        LineData data = new LineData(xVals, set1);
        // set data
        mChart.setData(data);
       // mChart.setVisibleXRange(6);
        // mChart.getViewPortHandler().setMinimumScaleX(4f);
        mChart.setScaleMinima(0.7f, 1f);
        // mChart.getViewPortHandler().setMinimumScaleX(4f);
        // TODO
        mChart.moveViewToX(data.getXValCount() - 7);
        mChart.invalidate();
    }

    @Override
    public void onNothingSelected() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onValueSelected(Entry arg0, int arg1, Highlight arg2) {
        // TODO Auto-generated method stub

    }

    // /////////////////////////////////////////////////////////////////////////util/////////////////////////////////////////////////////////////////
    /**
     * 获取本地保存的token
     *
     * @return
     */
    private String getToken() {
        String token = PreferencesUtils.getString(this, "userToken", "");
        return token;
    }

}
