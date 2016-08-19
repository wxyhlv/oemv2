package com.capitalbio.oemv2.myapplication.Utils;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author lzq
 * @Time 2016/1/21 16:23
 */
public class OutDoorAqi {

    private static OutDoorAqi outDoorAqi;

    private OutDoorAqi(){}

    public static OutDoorAqi getInstance(){
        if(outDoorAqi==null){
            outDoorAqi = new OutDoorAqi();
        }
        return outDoorAqi;
    }

    private OnDataReceived onDataReceived;

    public synchronized OutDoorAqi setOnDataReceived(OnDataReceived onDataReceived){
        this.onDataReceived = onDataReceived;
        return outDoorAqi;
    }

    public synchronized void get(String district){
        if(doing!=null){
            doing=null;
        }
        doing = new Doing(district);
        doing.start();
    }


    private Doing doing;

    private class Doing extends Thread{

        private String district;

        public Doing(String district) {
            this.district = district;
        }

        @Override
        public void run() {

            HttpGet httpGet = new HttpGet(Config.url+district);
            httpGet.setHeader("apix-key", Config.apix_key);

            HttpClient httpClient = new DefaultHttpClient();

            HttpResponse httpResponse = null;

            try {
                httpResponse = httpClient.execute(httpGet);
            } catch (IOException e) {
                e.printStackTrace();
                httpResponse = null;
            }

            if(httpResponse==null){
                Log.i("info","=======获取室外空气质量=======httpResponse空");
                onDataReceived.onFailure();
            }else{
                int statuscode = httpResponse.getStatusLine().getStatusCode();
                if(statuscode!=200){
                    Log.i("info","=======获取室外空气质量=======statuscode不等于200"+"----"+statuscode);
                    onDataReceived.onFailure();
                }else{
                    HttpEntity httpEntity = httpResponse.getEntity();
                    if(httpEntity==null){
                        onDataReceived.onFailure();
                    }else{
                        String result = null;
                        try {
                            result = EntityUtils.toString(httpEntity);
                        } catch (IOException e) {
                            e.printStackTrace();
                            result = null;
                        }
                        if(result==null||result.equals("")){
                            onDataReceived.onFailure();
                        }else{
                            onDataReceived.onSuccess(result);
                        }
                    }
                }
            }

        }
    }





    public interface OnDataReceived{
        void onSuccess(String result);
        void onFailure();
    }

    public class Config{
        public static final String apix_key = "a7ab703e3067484049b9d6be17fd8eb1";
        public static final String url = "http://a.apix.cn/apixlife/pm25/PM2.5?cityname=";
    }
}
