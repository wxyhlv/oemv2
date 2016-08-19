package com.capitalbio.oemv2.myapplication.Utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.capitalbio.oemv2.myapplication.Activity.AirCatActivity;
import com.capitalbio.oemv2.myapplication.Activity.LoginActivity;
import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.Bean.AircatDevice;
import com.capitalbio.oemv2.myapplication.Const.AircatConst;
import com.capitalbio.oemv2.myapplication.Const.Base_Url;
import com.capitalbio.oemv2.myapplication.NetUtils.HttpTools;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by wxy on 16-1-22.
 */
public class AircatUtil {
    private Context context;
    private AircatDevice aircatDevice;
    private final String  TAG = "AircatUtil";
    RegistOnGetResult callback;
    public AircatUtil(Context context,AircatDevice aircatDevice,RegistOnGetResult callback){
        this.context = context;
        this.aircatDevice = aircatDevice;
        this.callback = callback;
    }

    public  void bindDevice() {
        try {
            //构造请求json对象
            JSONObject jsonObject = new JSONObject();
            JSONObject dataObject = new JSONObject();

            jsonObject.put("apikey", MyApplication.getInstance().apikey);//所有接口必填
            jsonObject.put("username",MyApplication.getInstance().getCurrentUserName());
            jsonObject.put("token", MyApplication.getInstance().getCurentToken());
            dataObject.put("modelType", AircatConst.modelType);
            dataObject.put("mac",aircatDevice.getMac());
            dataObject.put("bindName", aircatDevice.getBindName());
            dataObject.put("way", aircatDevice.getWay());
            jsonObject.put("data", dataObject.toString());
            //绑定设备
            Log.d("codeUrl", Base_Url.Url_Base + Base_Url.bindDevice_Url);
            OemLog.log(TAG, jsonObject.toString());
            HttpTools.post(context, Base_Url.Url_Base + Base_Url.bindDevice_Url, jsonObject, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String result = new String(responseBody);
                    OemLog.log(TAG, result);
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        int code = jsonObject.optInt("code");
                        String msg = jsonObject.optString("message");
                        callback.OnGetResult(code,msg);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    OemLog.log(TAG, "服务器请求失败");
                    Toast.makeText(context, "服务器请求失败", Toast.LENGTH_LONG).show();
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public interface  RegistOnGetResult{
        public void OnGetResult(int code,String msg);
    }
}
