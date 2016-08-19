package com.capitalbio.oemv2.myapplication.Utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.capitalbio.oemv2.myapplication.Bean.AirCatInfo;
import com.capitalbio.oemv2.myapplication.Bean.BloodPressureBean;
import com.capitalbio.oemv2.myapplication.Bean.ComprehensiveBean;
import com.capitalbio.oemv2.myapplication.Bean.SleepDataAllBean;
import com.capitalbio.oemv2.myapplication.Bean.SportDataDetailBean;
import com.capitalbio.oemv2.myapplication.BraceleteLib.SleepDetailData;
import com.capitalbio.oemv2.myapplication.Devices.BodyFatDevices.src.com.bayh.sdk.ble.bean.BodyFatSaid;
import com.capitalbio.oemv2.myapplication.NetUtils.HistoryDownd;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author lzq
 * @Time 2016/3/14 11:18
 */
public class ComprehensiveEvaluationUtil {

    public static final int BLOODPRESSURE = 0;
    public static final int BODYFAT = 1;
    public static final int BRACELET_SPORT = 3;
    public static final int BRACELET_SLEEP_ALL = 4;
    public static final int AIRCAT = 5;

    private boolean isover_bp = false;
    private boolean isover_bf = false;
    private boolean isover_br_sport = false;
    private boolean isover_br_sleep_all = false;
    private boolean isover_ac = false;

    private boolean isMonitor = true;

    private Context context;
    private OnAllDataDownListener listener;

    private ComprehensiveBean comprehensiveBean;

    public ComprehensiveEvaluationUtil(Context context, OnAllDataDownListener listener) {
        this.context = context;
        this.listener = listener;
        comprehensiveBean = new ComprehensiveBean();
    }

    private HashMap<String, Object> params(int type, String mac) {
        HashMap<String, Object> params = new HashMap<String, Object>();
        switch (type) {
            case BLOODPRESSURE:
                params.put("modelType", "bloodpressure");
                break;
            case BODYFAT:
                params.put("modelType", "bodyfat");
                break;
            case BRACELET_SPORT:
                params.put("modelType", "braceletSports");
                break;
            case BRACELET_SLEEP_ALL:
                params.put("modelType","braceletSleepall");
                break;
            case AIRCAT:
                params.put("modelType", "airCat");
                params.put("mac", mac);
                break;
        }
        params.put("beginDate", CalendarUtil.stringPreviousSunday() + " " + "00:00:00");
        params.put("endDate", CalendarUtil.stringThisSunday() + " " + "00:00:00");
        params.put("url", "/monitor/data/all");
        return params;
    }

    //血压计
    private void pullBloodPressure() {
        //Log.i("info","获取血压数据的方法执行了...");
        HistoryDownd.getHistory(context, params(BLOODPRESSURE, null), new HistoryDownd.RegistOnGetResult() {
            @Override
            public void OnGetResult(int code, String msg, String data) {
                Log.i("info", "======综合页面获取的血压计的历史数据=========" + Thread.currentThread().getName() + "======" + data + "======" + code + "======" + msg);
                ComprehensiveBean.Bpresult bpresult = comprehensiveBean.new Bpresult();
                bpresult.setCode(code);
                bpresult.setMsg(msg);
                bpresult.setData(data);
                comprehensiveBean.setBpresult(bpresult);

                BloodPressureBean bloodPressureBean = parseBP(data);
                if (bloodPressureBean != null) {
                    //Log.i("info","=========血压数据解析后=========="+bloodPressureBean.getSysBp()+"===="+bloodPressureBean.getDiaBp());
                    comprehensiveBean.setBloodPressureBean(bloodPressureBean);
                }

                isover_bp = true;
            }
        });
    }

    //将获取到的数据，取平均值
    private BloodPressureBean parseBP(String data) {
        if (data == null || data.equals("")) {
            return null;
        }
        List<BloodPressureBean> bloodPressureBeans = new ArrayList<BloodPressureBean>();

        com.alibaba.fastjson.JSONArray jsonArray = JSON.parseArray(data);

        for(int i=0;i<jsonArray.size();i++){
            BloodPressureBean bloodPressureBean = new BloodPressureBean();
           com.alibaba.fastjson.JSONObject jsonObject = jsonArray.getJSONObject(i);
            bloodPressureBean.setSysBp(jsonObject.getIntValue("highPressure"));
            bloodPressureBean.setDiaBp(jsonObject.getIntValue("lowPressure"));
            bloodPressureBean.setHeartRate(jsonObject.getIntValue("heartRate"));
            bloodPressureBean.setTime(jsonObject.getLongValue("testTime"));
            bloodPressureBean.setUsername(jsonObject.getString("username"));
            bloodPressureBeans.add(bloodPressureBean);
        }

        //
        //bloodPressureBeans = JSON.parseArray(data, BloodPressureBean.class);

        for(BloodPressureBean bloodPressureBean:bloodPressureBeans){
            //Log.i("info","======json转list后======="+bloodPressureBean.getSysBp());
            //Log.i("info","======json转list后======="+bloodPressureBean.getSysBp());
        }

        BloodPressureBean bloodPressureBean = new BloodPressureBean();

        if (bloodPressureBeans == null || bloodPressureBeans.size() < 1) {
            return null;
        }

        int size = bloodPressureBeans.size();

        int heartrate = 0;
        int diabp = 0;
        int sysbp = 0;


        for (BloodPressureBean bp : bloodPressureBeans) {
            heartrate += bp.getHeartRate();
            diabp += bp.getDiaBp();
            sysbp += bp.getSysBp();
        }

        heartrate /= size;
        diabp /= size;
        sysbp /= size;

        bloodPressureBean.setHeartRate(heartrate);
        bloodPressureBean.setDiaBp(diabp);
        bloodPressureBean.setSysBp(sysbp);

        //Log.i("info","==========数据解析方法里，解析后的收缩压=========="+sysbp);
        //Log.i("info","==========数据解析方法里，解析后的舒张压=========="+diabp);


        return bloodPressureBean;
    }

    //体脂称
    private void pullBodyFat() {
        HistoryDownd.getHistory(context, params(BODYFAT, null), new HistoryDownd.RegistOnGetResult() {
            @Override
            public void OnGetResult(int code, String msg, String data) {
                Log.i("info", "======综合页面获取的体脂称的历史数据=========" + Thread.currentThread().getName() + "======" + data + "======" + code + "======" + msg);
                ComprehensiveBean.Bfresult bfresult = comprehensiveBean.new Bfresult();
                bfresult.setCode(code);
                bfresult.setMsg(msg);
                bfresult.setData(data);
                comprehensiveBean.setBfresult(bfresult);

                BodyFatSaid bodyFatSaid = parseBF(data);
                if(bodyFatSaid!=null){
                    comprehensiveBean.setBodyFatSaid(bodyFatSaid);
                }

                isover_bf = true;
            }
        });
    }

    //取平均值
    private BodyFatSaid parseBF(String data) {

        if (data == null || data.equals("")) {
            return null;
        }

        com.alibaba.fastjson.JSONArray jsonArray = com.alibaba.fastjson.JSONArray.parseArray(data);

        if(jsonArray.size()<1){
            return null;
        }

        List<BodyFatSaid> bodyFatSaids = new ArrayList<BodyFatSaid>();

        for(int i=0;i<jsonArray.size();i++){
            com.alibaba.fastjson.JSONObject jsonObject = jsonArray.getJSONObject(i);

            float weight = jsonObject.getFloatValue("weight");
            float bmi = jsonObject.getFloatValue("bmi");
            int bmr = jsonObject.getIntValue("bmr");
            float fat = jsonObject.getFloatValue("fat");
            int visceralLevel = jsonObject.getIntValue("visceralLevel");
            float water = jsonObject.getFloatValue("water");
            float muscle = jsonObject.getFloatValue("muscle");
            float bone = jsonObject.getFloatValue("bone");
            long testTime = jsonObject.getLongValue("testTime");
            String username = jsonObject.getString("username");
            long ctime = jsonObject.getLongValue("ctime");
            String id = jsonObject.getString("id");

            BodyFatSaid bodyFatSaid = new BodyFatSaid();

            bodyFatSaid.setWeight(weight);
            bodyFatSaid.setBmi(bmi+"");
            bodyFatSaid.setBmrGrade(bmr+"");
            bodyFatSaid.setFat(fat);
            bodyFatSaid.setVisceraGrade(visceralLevel+"");
            bodyFatSaid.setWater(water);
            bodyFatSaid.setMuscle(muscle);
            bodyFatSaid.setBone(bone);
            bodyFatSaid.setLongTime(testTime);
            bodyFatSaid.setUserName(username);

            bodyFatSaids.add(bodyFatSaid);
        }


        if (bodyFatSaids == null || bodyFatSaids.size() < 1) {
            return null;
        }

        BodyFatSaid bodyFatSaid = new BodyFatSaid();

        int size = bodyFatSaids.size();

        float bmi = 0f;
        int bmrgrade = 0;
        float bone = 0f;
        //int bonegrade = 0;
        float fat = 0f;
        //int fatgrade = 0;
        //float height = 0f;
        //int kcal = 0;
        float muscle = 0f;
        //int musclegrade = 0;
        //float viscerafat = 0f;
        int visceragrade = 0;
        float water = 0f;
        //int watergrade = 0;
        float weight = 0f;
        //int weightgrade = 0;

        for (BodyFatSaid bfs : bodyFatSaids) {
            bmi += Float.parseFloat(bfs.getBmi());

            bmrgrade += Integer.parseInt(bfs.getBmrGrade());
            bone += bfs.getBone();
            //bonegrade += Integer.parseInt(bfs.getBoneGrade());
            fat += bfs.getFat();
            //fatgrade += Integer.parseInt(bfs.getFatGrade());
            //height += bfs.getHeight();
            //kcal += bfs.getKcal();
            muscle += bfs.getMuscle();
            //musclegrade += Integer.parseInt(bfs.getMuscleGrade());
            //viscerafat += bfs.getVisceraFat();
            visceragrade += Integer.parseInt(bfs.getVisceraGrade());
            water += bfs.getWater();
            //watergrade += Integer.parseInt(bfs.getWaterGrade());
            weight += bfs.getWeight();
            //weightgrade += Integer.parseInt(bfs.getWeightGrade());
        }

        bmi /= size;
        bmrgrade /= size;
        bone /= size;
        //bonegrade /= size;
        fat /= size;
        //fatgrade /= size;
       // height /= size;
        //kcal /= size;
        muscle /= size;
        //musclegrade /= size;
        //viscerafat /= size;
        visceragrade /= size;
        water /= size;
        //watergrade /= size;
        weight /= size;
        //weightgrade /= size;

        bodyFatSaid.setBmi(bmi + "");
        bodyFatSaid.setBmrGrade(bmrgrade + "");
        bodyFatSaid.setBone(bone);
        //bodyFatSaid.setBoneGrade(bonegrade + "");
        bodyFatSaid.setFat(fat);
        //bodyFatSaid.setFatGrade(fatgrade + "");
        //bodyFatSaid.setHeight(height);
        //bodyFatSaid.setKcal(kcal);
        bodyFatSaid.setMuscle(muscle);
        //bodyFatSaid.setMuscleGrade(musclegrade + "");
        //bodyFatSaid.setVisceraFat(viscerafat);
        bodyFatSaid.setVisceraGrade(visceragrade + "");
        bodyFatSaid.setWater(water);
        //bodyFatSaid.setWaterGrade(watergrade + "");
        bodyFatSaid.setWeight(weight);
        //bodyFatSaid.setWeightGrade(weightgrade + "");


        return bodyFatSaid;
    }

    //手环运动数据
    private void pullBraceletSport() {
        HistoryDownd.getHistory(context, params(BRACELET_SPORT, null), new HistoryDownd.RegistOnGetResult() {
            @Override
            public void OnGetResult(int code, String msg, String data) {
                Log.i("info", "======综合页面获取的手环运动的历史数据=========" + Thread.currentThread().getName() + "======" + data + "======" + code + "======" + msg);
                ComprehensiveBean.BrresultSport brresult = comprehensiveBean.new BrresultSport();
                brresult.setCode(code);
                brresult.setMsg(msg);
                brresult.setData(data);
                comprehensiveBean.setBrresultSport(brresult);

                SportDataDetailBean sportDataDetailBean = parseBRsport(data);
                if(sportDataDetailBean!=null){
                    comprehensiveBean.setSportDataDetailBean(sportDataDetailBean);
                }

                isover_br_sport = true;
            }
        });
    }

    //取平均值
    private SportDataDetailBean parseBRsport(String data) {
        if (data == null || data.equals("")) {
            return null;
        }
        List<SportDataDetailBean> sportDataDetailBeans = new ArrayList<SportDataDetailBean>();
        sportDataDetailBeans = JSON.parseArray(data, SportDataDetailBean.class);
        if (sportDataDetailBeans == null || sportDataDetailBeans.size() < 1) {
            return null;
        }



        SportDataDetailBean sportDataDetailBean = new SportDataDetailBean();

        int size = sportDataDetailBeans.size();
        int cal = 0;
        int steps = 0;

        for (SportDataDetailBean sddb : sportDataDetailBeans) {
            cal += sddb.getCal();
            steps += sddb.getSteps();
        }

        cal = (int) (((float)cal/size)*(size/7f));//这个算法--是=先把属于每一天的数据求和，然后再求七天的平均值
        steps = (int) (((float)steps/size)*(size/7f));//这个算法--是=先把属于每一天的数据求和，然后再求七天的平均值

        sportDataDetailBean.setCal(cal);
        sportDataDetailBean.setSteps(steps);

        return sportDataDetailBean;
    }

    //手环睡眠
    private void pullBraceletSleepAll() {
        HistoryDownd.getHistory(context, params(BRACELET_SLEEP_ALL, null), new HistoryDownd.RegistOnGetResult() {
            @Override
            public void OnGetResult(int code, String msg, String data) {
                Log.i("info", "======综合页面获取的手环睡眠的历史数据=========" + Thread.currentThread().getName() + "======" + data + "======" + code + "======" + msg);
                ComprehensiveBean.BrresultSleep brresult = comprehensiveBean.new BrresultSleep();
                brresult.setCode(code);
                brresult.setMsg(msg);
                brresult.setData(data);
                comprehensiveBean.setBrresultSleep(brresult);

                SleepDataAllBean sleepDataAllBean = parseBRsleepAll(data);
                if(sleepDataAllBean!=null){
                    comprehensiveBean.setSleepDataAllBean(sleepDataAllBean);
                }

                isover_br_sleep_all = true;
            }
        });
    }

    //取平均值
    private SleepDataAllBean parseBRsleepAll(String data){
        if(data==null||data.equals("")){
            return null;
        }
        List<SleepDataAllBean> sleepDataAllBeans = new ArrayList<SleepDataAllBean>();
        sleepDataAllBeans = JSON.parseArray(data,SleepDataAllBean.class);
        if(sleepDataAllBeans==null||sleepDataAllBeans.size()<1){
            return null;
        }
        SleepDataAllBean sleepDataAllBean = new SleepDataAllBean();

        int size = sleepDataAllBeans.size();

        int totalTime = 0;

        for(SleepDataAllBean sdab:sleepDataAllBeans){
            totalTime+=sdab.getTotalTime();
        }

        totalTime /=size;

        sleepDataAllBean.setTotalTime(totalTime);

        return sleepDataAllBean;
    }

    //空气猫
    private void pullAircat(String mac) {
        HistoryDownd.getHistory(context, params(AIRCAT, mac), new HistoryDownd.RegistOnGetResult() {
            @Override
            public void OnGetResult(int code, String msg, String data) {
                Log.i("info", "======综合页面获取的空气猫的历史数据=========" + Thread.currentThread().getName() + "======" + data + "======" + code + "======" + msg);
                ComprehensiveBean.Acresult acresult = comprehensiveBean.new Acresult();
                acresult.setCode(code);
                acresult.setMsg(msg);
                acresult.setData(data);
                comprehensiveBean.setAcresult(acresult);

                AirCatInfo airCatInfo = parseAC(data);
                if(airCatInfo!=null){
                    comprehensiveBean.setAirCatInfo(airCatInfo);
                }

                isover_ac = true;
            }
        });
    }

    //取平均值
    private AirCatInfo parseAC(String data) {

        if (data == null || data.equals("")) {
            return null;
        }

        List<AirCatInfo> airCatInfos = new ArrayList<AirCatInfo>();
        airCatInfos = JSON.parseArray(data, AirCatInfo.class);

        if (airCatInfos == null || airCatInfos.size() < 1) {
            return null;
        }

        AirCatInfo airCatInfo = new AirCatInfo();

        int size = airCatInfos.size();

        float tvoc = 0f;
        int pollutionLevel = 0;
        float pm25 = 0f;
        float humity = 0f;
        float temperature = 0f;
        float ch2 = 0f;

        for (AirCatInfo aci : airCatInfos) {
            tvoc += Float.parseFloat(aci.getTvoc());
            pollutionLevel += Integer.parseInt(aci.getPollutionLevel());
            pm25 += Float.parseFloat(aci.getPm25());
            humity += Float.parseFloat(aci.getHumity());
            temperature += Float.parseFloat(aci.getTemperature());
            ch2 += Float.parseFloat(aci.getCh2());
        }

        tvoc /= size;
        pollutionLevel /= size;
        pm25 /= size;
        humity /= size;
        temperature /= size;
        ch2 /= size;

        airCatInfo.setTvoc(tvoc + "");
        airCatInfo.setPollutionLevel(pollutionLevel + "");
        airCatInfo.setPm25(pm25 + "");
        airCatInfo.setHumity(humity + "");
        airCatInfo.setTemperature(temperature + "");
        airCatInfo.setCh2(ch2 + "");

        return airCatInfo;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

                listener.stop(comprehensiveBean);

        }
    };

    public void pullAllData(String mac) {

        listener.start();

        pullBloodPressure();
        pullBodyFat();
        pullBraceletSport();
        pullBraceletSleepAll();
        pullAircat(mac);

        new Thread() {
            @Override
            public void run() {
                while (isMonitor) {
                    if (isover_ac && isover_bf && isover_bp && isover_br_sport&&isover_br_sleep_all) {
                        isMonitor = false;
                        handler.sendEmptyMessage(0);
                    }
                }
            }
        }.start();
    }


    public interface OnAllDataDownListener {
        void start();

        void stop(ComprehensiveBean comprehensiveBean);
    }


}
