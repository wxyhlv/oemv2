package com.capitalbio.oemv2.myapplication.dialog;

import android.app.Activity;
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
import com.capitalbio.oemv2.myapplication.Const.AircatConst;
import com.capitalbio.oemv2.myapplication.Const.Base_Url;
import com.capitalbio.oemv2.myapplication.NetUtils.HttpTools;
import com.capitalbio.oemv2.myapplication.NetUtils.NetTool;
import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;
import com.capitalbio.oemv2.myapplication.Utils.PreferencesUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

/**
 * Created by wxy on 16-1-8.
 */
public class ChangeAIrcatDialog extends Dialog implements View.OnClickListener{
    private Context context;
    private EditText edt_aircatname;
    private String TAG = "ShareMsg";
    private MyApplication myApp;
    private String mac,name;
    public ChangeAIrcatDialog(Context context,String mac,String name, PriorityListener listener) {
        super(context);
        this.context = context;
        this.mac = mac;
        this.name = name;
        this.listener = listener;
    }

    /**
     * 自定义Dialog监听器
     */
    public interface PriorityListener {
        /**
         * 回调函数，用于在Dialog的监听事件触发后刷新Activity的UI显示
         */
        public void refreshPriorityUI(String name);
    }

    private PriorityListener listener;
    public ChangeAIrcatDialog(Context context, int theme){
        super(context, theme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_aircat_change);
        findViewById(R.id.btn_edit).setOnClickListener(this);
        edt_aircatname = (EditText) findViewById(R.id.edt_aircatname);
        edt_aircatname.setText(name);
        myApp = MyApplication.getInstance();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_edit){
            name = edt_aircatname.getText().toString().trim();
        if(name ==null || "".equals(name)){
            Toast.makeText(context,"设备名称不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
            //username receiveName mac
            if(NetTool.isNetwork(context,true)){
                updateName(mac, name);
            }
            this.dismiss();
        }
    }



    /**
     * 修改设备名称
     */
    private void updateName(final String mac, final String name) {

        try {
            //构造请求json对象
            final JSONObject jsonObject = new JSONObject();
            JSONObject dataObject = new JSONObject();

            jsonObject.put("apikey", myApp.apikey);//所有接口必填
            jsonObject.put("token",myApp.getCurentToken());//所有接口必填
            jsonObject.put("username",myApp.getCurrentUserName());
            final HashMap<String,String> map = new HashMap<>();

            map.put(mac,name);

            map.put("modelType", AircatConst.modelType);
            JSONObject mapJson = new JSONObject(map);
            jsonObject.put("data", mapJson.toString());
            // jsonObject.put("data", tmpObject.toString());
            Log.d(TAG, Base_Url.Url_Base + Base_Url.updateDeviceName_URL);
            Log.d(TAG, jsonObject.toString());
            HttpTools.post(context, Base_Url.Url_Base + Base_Url.updateDeviceName_URL, jsonObject, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String result = new String(responseBody);
                    OemLog.log(TAG, result);
                //    progressBar.setVisibility(View.GONE);
                    try {
                        JSONObject jsonObject1 = new JSONObject(result);
                        String msg = jsonObject1.optString("message");
                        int code = jsonObject1.optInt("code");


                        if (code == 11) {
                            MyApplication.getInstance().exit();
                            Toast.makeText(context, "用户未登陆,请重新登陆", Toast.LENGTH_SHORT).show();

                        }
                        if (code == 0) {
                            Toast.makeText(context, "修改成功", Toast.LENGTH_SHORT).show();
                            String local_mac = MyApplication.getInstance().getAircatDeviceMac();
                            if(mac.equals(local_mac)){
                                PreferencesUtils.putString(context, "curAircatName",name);
                            }

                            dismiss();
                            listener.refreshPriorityUI(name);
                            ;
                            //     adapter.notifyDataSetChanged();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Toast.makeText(context, "服务器异常！", Toast.LENGTH_LONG).show();
                    OemLog.log(TAG, "服务器请求失败");
                  //  progressBar.setVisibility(View.GONE);

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

}
