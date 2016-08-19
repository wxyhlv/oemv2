package com.capitalbio.oemv2.myapplication.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.capitalbio.oemv2.myapplication.Activity.LoginActivity;
import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.Const.Base_Url;
import com.capitalbio.oemv2.myapplication.NetUtils.HttpTools;
import com.capitalbio.oemv2.myapplication.NetUtils.NetTool;
import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;
import com.capitalbio.oemv2.myapplication.Utils.PreferencesUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by wxy on 16-1-8.
 */
public class AircatShareDialog extends Dialog implements View.OnClickListener{
    private Context context;
    private EditText edt_username;
    private String username;
    private String TAG = "ShareMsg";
    private MyApplication myApp;
    public AircatShareDialog(Context context) {
        super(context);
        this.context = context;
    }
    public AircatShareDialog(Context context, int theme){
        super(context, theme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_aircat_share);
        findViewById(R.id.tv_send).setOnClickListener(this);
        edt_username = (EditText) findViewById(R.id.edt_username);
        myApp = MyApplication.getInstance();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.tv_send){
            username = edt_username.getText().toString();
            OemLog.log(TAG,"发送中");

            //username receiveName mac
            if(NetTool.isNetwork(context,true)){
                share(username);
            }

            


            this.dismiss();
        }
    }


    private void share(String username) {

        Log.d(TAG, "share...");

        try {
            //构造请求json对象
            final JSONObject jsonObject = new JSONObject();
            JSONObject dataObject = new JSONObject();

            jsonObject.put("apikey", MyApplication.getInstance().apikey);//所有接口必填
            jsonObject.put("token", MyApplication.getInstance().getCurentToken());//所有接口必填
            jsonObject.put("username",MyApplication.getInstance().getCurrentUserName());
            dataObject.put("receiveName", username);
            dataObject.put("mac",myApp.getAircatDeviceMac());
            jsonObject.put("data", dataObject.toString());
           // jsonObject.put("data", tmpObject.toString());
            Log.d("codeUrl", Base_Url.Url_Base + Base_Url.shareAircat_Url);
            Log.d(TAG, jsonObject.toString());
            HttpTools.post(context, Base_Url.Url_Base + Base_Url.shareAircat_Url, jsonObject, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String result = new String(responseBody);
                    OemLog.log(TAG, result);
                    try {
                        JSONObject jsonObject1 = new JSONObject(result);
                        String msg = jsonObject1.optString("message");
                        int code = jsonObject1.optInt("code");
                        if(code==11){
                            MyApplication.getInstance().exit();
                            Toast.makeText(context,"用户未登陆,请重新登陆",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getOwnerActivity(),LoginActivity.class);
                            context.startActivity(intent);
                        }else if(code == 0){
                            Toast.makeText(context,"发送成功",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(context,msg,Toast.LENGTH_LONG).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    OemLog.log(TAG, "服务器请求失败");
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
