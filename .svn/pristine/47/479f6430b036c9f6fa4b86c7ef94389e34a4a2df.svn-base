package com.capitalbio.oemv2.myapplication.Utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.loopj.android.http.HttpGet;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author lzq
 * @Time 2016/1/20 17:32
 *
 * 从中国天气网获取天气数据
 */


public  class ChinaWeather {

    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            String result = (String) msg.obj;
            if(what==0){
                listener.onFailure();
            }else if(what==1){
                listener.onSuccess(result);
            }
        }
    };

    private  OnReceiveWeatherListener listener;

    private Context context;

    private Doing doing;

    private static ChinaWeather chinaWeather;

    private ChinaWeather(){

    }

    public static synchronized ChinaWeather getInstance(){
        if(chinaWeather==null){
            chinaWeather = new ChinaWeather();
        }
        return chinaWeather;
    }

    public synchronized void setOnReceiveWeatherListener(OnReceiveWeatherListener onReceiveWeatherListener){

            this.listener = onReceiveWeatherListener;



    }


    public synchronized void getWeather(String areaid,String type){

       if(doing!=null){
           doing = null;
       }

        doing = new Doing(areaid,type);

        doing.start();
    }



    private class Doing extends Thread{

        private String areaid;
        private String type;


        public Doing(String areaid, String type) {
            this.areaid = areaid;
            this.type = type;
        }

        @Override
        public void run() {

            String date = getDate();
            String key = Encryption.encryptionKey(areaid, type, date);

            String url = Config.URL+"areaid="+areaid+"&type="+type+"&date="+date+"&appid="+Config.APPID_PART+"&key="+key;


            org.apache.http.client.methods.HttpGet httpGet = new org.apache.http.client.methods.HttpGet(url);
            HttpClient httpClient = new DefaultHttpClient();

            HttpResponse response = null;
            try {
                response = httpClient.execute(httpGet);
            } catch (Exception e) {
                e.printStackTrace();
                response = null;
            }



            if(response==null){
                handler.sendEmptyMessage(0);
                //listener.onFailure();
            }else{
               int statuscode =  response.getStatusLine().getStatusCode();
                if(statuscode!=200){
                    handler.sendEmptyMessage(0);
                    // listener.onFailure();
                }else{
                   HttpEntity entity = response.getEntity();
                    if(entity==null){
                        handler.sendEmptyMessage(0);
                        //listener.onFailure();
                    }else{
                    String result =null;
                        try {
                            result = EntityUtils.toString(entity,HTTP.UTF_8);
                        } catch (IOException e) {
                            e.printStackTrace();
                            result = null;
                        }
                        if(result==null||result.equals("")){
                            handler.sendEmptyMessage(0);
                            //listener.onFailure();
                        }else{
                            Message msg = new Message();
                            msg.what = 1;
                            msg.obj = result;
                            handler.sendMessage(msg);
                            //listener.onSuccess(result);
                        }
                    }
                }
            }




        }
    }


    //获取当前时间yyyyMMddHHmm格式的

    private String getDate(){

        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        month = month+1;

        String years = year+"";
        String months = month<10?"0"+month:""+month;
        String days = day<10?"0"+day:""+day;
        String hours = hour<10?"0"+hour:""+hour;
        String minutes = minute<10?"0"+minute:""+minute;


        return years+months+days+hours+minutes;
    }


    public interface OnReceiveWeatherListener{
        void onSuccess(String result);
        void onFailure();
    }




    public static class Config{
        public static final String INDEX_F = "index_f"; //指数基础接口
        public static final String INDEX_V = "index_v"; //指数常规接口
        public static final String FORECAST_F = "forecast_f"; //天预报基础接口
        public static final String FORECAST_V = "forecast_v"; //天预报常规接口

        public static final String APPID_COMPLETE = "ce1ef5fd92ff78a6"; //完整的APPID
        public static final String PRIVATE_KEY = "a47475_SmartWeatherAPI_a8dfa16";//私钥
        public static final String APPID_PART = "ce1ef5"; //APPID 的前六位

        public static final String URL = "http://open.weather.com.cn/data/?";

    }





}
