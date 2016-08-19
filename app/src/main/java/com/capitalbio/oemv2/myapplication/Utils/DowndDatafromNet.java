package com.capitalbio.oemv2.myapplication.Utils;

import android.content.Context;
import android.util.Log;

import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.Const.Base_Url;
import com.capitalbio.oemv2.myapplication.NetUtils.HttpTools;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by wxy on 16-3-3.
 */
public class DowndDatafromNet {


    //获取最新数据
    public static void getNewestWifiData(Context context,String type,String mac){
        try {
            //构造请求json对象
            JSONObject jsonObject = new JSONObject();
            JSONObject dataObj = new JSONObject();

            jsonObject.put("apikey", MyApplication.getInstance().apikey);
            jsonObject.put("username", MyApplication.getInstance().getCurrentUserName());
            jsonObject.put("token", MyApplication.getInstance().getCurentToken());

            dataObj.put("modelType", type);
            dataObj.put("mac", mac);
            jsonObject.put("data", dataObj.toString());
            OemLog.log("downloadData", Base_Url.getDataByInternet + "_"+jsonObject.toString());

            HttpTools.post(context, Base_Url.Url_Base + Base_Url.getDataByInternet, jsonObject, new AsyncHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    String result = new String(responseBody);

                    Log.i("onSuccess ", "===================================" + result);

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }

                @Override
                public void onCancel() {
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    //获取最新数据
    public static void upload(Context context){
        try {
            //构造请求json对象
            JSONObject jsonObject = new JSONObject();
            JSONObject dataObj = new JSONObject();

            jsonObject.put("apikey", MyApplication.getInstance().apikey);
            jsonObject.put("username", MyApplication.getInstance().getCurrentUserName());
            jsonObject.put("token", MyApplication.getInstance().getCurentToken());

            dataObj.put("steps", "2000");
            dataObj.put("distance", "12");
            dataObj.put("cal", "1300");
            dataObj.put("testTime", System.currentTimeMillis());
            dataObj.put("modelType", "braceletSports");
            jsonObject.put("data", dataObj.toString());
            OemLog.log("upload", Base_Url.uploadData_Url + "_"+jsonObject.toString());

            HttpTools.post(context, Base_Url.Url_Base + Base_Url.uploadData_Url, jsonObject, new AsyncHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    String result = new String(responseBody);

                    Log.i("onSuccess ", "===================================" + result);

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.i("failue ", "===================================faile");

                }

                @Override
                public void onCancel() {
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}