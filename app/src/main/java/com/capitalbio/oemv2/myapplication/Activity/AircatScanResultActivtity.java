package com.capitalbio.oemv2.myapplication.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.Bean.AircatDevice;
import com.capitalbio.oemv2.myapplication.Const.AircatConst;
import com.capitalbio.oemv2.myapplication.Const.Base_Url;
import com.capitalbio.oemv2.myapplication.NetUtils.HttpTools;
import com.capitalbio.oemv2.myapplication.NetUtils.NetTool;
import com.capitalbio.oemv2.myapplication.QR.MipcaActivityCapture;
import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;
import com.capitalbio.oemv2.myapplication.Utils.PreferencesUtils;
import com.capitalbio.oemv2.myapplication.Utils.Utility;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by wxy on 15-12-31.
 * 绑定设备
 */
public class AircatScanResultActivtity extends BaseActivity implements View.OnClickListener {
    LinearLayout contentLayout;
    TextView tv_result;
    private final static int SCANNIN_GREQUEST_CODE = 1;
    EditText edt_memoname;
    String mac,way;
    String TAG = "bindDevice";
    @Override
    protected void initChildLayout() {
        initChildView();
    }

    private void initChildView() {
        rl.setBackgroundResource(R.color.bg_content);
        rlNavigateBar.setBackgroundResource(R.drawable.bg_tang_zhi_san_xiang);
        setLeftTopIcon(R.drawable.ic_back);
        ivSplitLine.setBackgroundResource(R.color.mainSplitLine);

        contentLayout = (LinearLayout) View.inflate(this, R.layout.activity_aircat_scan_sucess, null);
        llBody.addView(contentLayout);

        tv_result = (TextView) findViewById(R.id.tv_result);
        mac = getIntent().getStringExtra("result");
        way = getIntent().getStringExtra("way");
        setTvTopTitle(R.string.adddevice);
        tv_result.setText(mac);
        edt_memoname = (EditText) findViewById(R.id.edt_memoname);
        findViewById(R.id.tv_bind_device).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_bind_device:
                if(NetTool.isNetwork(context,true)){
                    bindDevice();
                }
                break;

        }

    }

    public void bindDevice() {
        try {
            //构造请求json对象
            JSONObject jsonObject = new JSONObject();
            JSONObject dataObject = new JSONObject();

            jsonObject.put("apikey", myApp.apikey);//所有接口必填
            jsonObject.put("username",myApp.getCurrentUserName());
            jsonObject.put("token", myApp.getCurentToken());
            dataObject.put("modelType", AircatConst.modelType);
            dataObject.put("mac",mac);
            dataObject.put("bindName", edt_memoname.getText().toString());
            dataObject.put("way", way);
            jsonObject.put("data", dataObject.toString());
            //绑定设备
            Log.d("codeUrl", Base_Url.Url_Base + Base_Url.bindDevice_Url);
            HttpTools.post(this, Base_Url.Url_Base + Base_Url.bindDevice_Url, jsonObject, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String result = new String(responseBody);
                    OemLog.log(TAG, result);
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        int code = jsonObject.optInt("code");
                        String msg = jsonObject.optString("message");
                        if(code==11){
                            MyApplication.getInstance().exit();
                            Toast.makeText(context,"用户未登陆,请重新登陆",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AircatScanResultActivtity.this,LoginActivity.class);
                            startActivity(intent);
                        }else  if (code == 0) {
                            Toast.makeText(getContext(), "绑定设备成功", Toast.LENGTH_LONG).show();
                            AircatDevice device = new AircatDevice();
                            device.setMac(mac);
                            device.setBindName(edt_memoname.getText().toString());
                            PreferencesUtils.putString(context, "curAircatMac", mac);
                            PreferencesUtils.putString(context,"curAircatName",edt_memoname.getText().toString());
                            Intent intent =new Intent(AircatScanResultActivtity.this,AirCatActivity.class);
                            startActivity(intent);
                            finish();
                            //TODO
                        }else{
                            Toast.makeText(getContext(),msg, Toast.LENGTH_LONG).show();
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
