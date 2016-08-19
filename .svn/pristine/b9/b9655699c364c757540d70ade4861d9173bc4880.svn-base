package com.capitalbio.oemv2.myapplication.Utils;

import android.content.Context;
import android.util.Log;

import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.Bean.AirCatInfo;
import com.capitalbio.oemv2.myapplication.Bean.BloodPressureBean;
import com.capitalbio.oemv2.myapplication.Bean.BodyFatBean2;
import com.capitalbio.oemv2.myapplication.Devices.BodyFatDevices.src.com.bayh.sdk.ble.bean.BodyFatSaid;
import com.capitalbio.oemv2.myapplication.FirstPeriod.Utils.HTG;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author lzq
 * @Time 2016/3/2 16:13
 */
public class UpLoadJsonDataCreater {

    //要上传的bean
    private Object bean;

     //上下文
    private Context context;


    public UpLoadJsonDataCreater(Object bean,Context context){
        this.bean = bean;
        this.context = context;

    }


    //将data拼到entity里
    public String entity(){
        String entity = "";

        String apikey = MyApplication.getInstance().apikey;

        String username = MyApplication.getInstance().getCurrentUserName();

        JSONObject data = data();

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("apikey",apikey);
            jsonObject.put("data",data.toString());
            jsonObject.put("username",username);
            jsonObject.put("token", PreferencesUtils.getString(context, "token"));
            entity = jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return entity;
    }



    //拼data数据
    private JSONObject data(){
        //String data = "";
        JSONObject jsonObject = new JSONObject();
        //空气猫
        if(bean instanceof AirCatInfo){
            jsonObject = aircatData((AirCatInfo)bean);
        }
        //血压计
        if(bean instanceof BloodPressureBean){
            jsonObject = bloodpressData((BloodPressureBean)bean);
        }
        //体脂称
        if(bean instanceof BodyFatSaid){
            jsonObject = bodyfatData((BodyFatSaid)bean);
        }
        return jsonObject;
    }

    //拼空气猫的data
    private JSONObject aircatData(AirCatInfo airCatInfo){
        //String data= "";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pm2Point5",airCatInfo.getPm25());
            jsonObject.put("temperature",airCatInfo.getTemperature());
            jsonObject.put("humidity",airCatInfo.getHumity());
            jsonObject.put("methanal",airCatInfo.getCh2());
            jsonObject.put("tvoc",airCatInfo.getTvoc());
            jsonObject.put("testTime",airCatInfo.getTestTime());
            jsonObject.put("modelType","airCat");
            jsonObject.put("mac",airCatInfo.getMac());
            //data = jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    //拼血压计的data
    private JSONObject bloodpressData(BloodPressureBean bloodPressBean){
        //String data = "";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("highPressure",bloodPressBean.getSysBp());
            jsonObject.put("lowPressure",bloodPressBean.getDiaBp());
            jsonObject.put("heartRate",bloodPressBean.getHeartRate());
            jsonObject.put("testTime",bloodPressBean.getTime());
            jsonObject.put("modelType","bloodpressure");
            //data = jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    //拼体脂称的data
    private JSONObject bodyfatData(BodyFatSaid bodyFatBean){
        //String data = "";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("weight",bodyFatBean.getWeight());
            jsonObject.put("testTime",bodyFatBean.getLongTime());
            jsonObject.put("modelType","bodyfat");
            //data = jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

}
