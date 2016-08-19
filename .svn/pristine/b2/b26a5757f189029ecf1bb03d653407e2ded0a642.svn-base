package com.capitalbio.oemv2.myapplication.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.Bean.AircatDevice;
import com.capitalbio.oemv2.myapplication.Const.AircatConst;
import com.capitalbio.oemv2.myapplication.Const.Base_Url;
import com.capitalbio.oemv2.myapplication.NetUtils.HttpTools;
import com.capitalbio.oemv2.myapplication.NetUtils.NetTool;
import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;
import com.capitalbio.oemv2.myapplication.Utils.PreferencesUtils;
import com.capitalbio.oemv2.myapplication.View.slideDeleteView.SlideView;
import com.capitalbio.oemv2.myapplication.adapter.ChangeDeviceAdapter;
import com.capitalbio.oemv2.myapplication.dialog.ChangeAIrcatDialog;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class AircatChangeDeviceActivity extends  BaseActivity implements AdapterView.OnItemLongClickListener ,AdapterView.OnItemClickListener {

    private static final String TAG = "AircatChangeDevice";

    private ListView mListView;
   // private List<MessageItem> mMessageItems = new ArrayList<MessageItem>();
  //  private ToggleButton tv_edit;
    private TextView tv_edit;
    private SlideView mLastSlideViewWithStatusOn;
    //SlideAdapter adapter;
    ProgressBar progressBar;
    private ProgressDialog mProgressDialog = null;
    int selectIndex = -1;//用来保存初始状态的选中项
    private Context context;
    MyApplication myApp;
    // 定义一个HashMap，用来存放EditText的值，Key是position
    HashMap<Integer, String> hashMap = new HashMap<Integer, String>();
    private List<AircatDevice> devices = new ArrayList<AircatDevice>();
    private  TextView tv_empty;
    private ChangeDeviceAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_delete);
        context = this;
        myApp = MyApplication.getInstance();
        initView();
        initData();
        mListView.setOnItemClickListener(this);
        mListView.setOnItemLongClickListener(this);

       // tv_edit.setOnClickListener(this);
    }

    public void initView() {
      //  tv_edit = (TextView) findViewById(R.id.tv_edit);
        mListView = (ListView) findViewById(R.id.list);
        tv_empty = (TextView) findViewById(R.id.tv_empty);
        // tv_edit.setText("编辑");
        progressBar = (ProgressBar) findViewById(R.id.pb_progress);
        progressBar.setVisibility(View.VISIBLE);
        adapter = new ChangeDeviceAdapter(context,devices);
        mListView.setAdapter(adapter);
    }
    public  void initData() {
        //获取已绑定的设备 getBindDevice_Url
        try {
            //构造请求json对象
            final JSONObject jsonObject = new JSONObject();
            JSONObject dataObject = new JSONObject();

            jsonObject.put("apikey", myApp.apikey);//所有接口必填
            jsonObject.put("token", myApp.getCurentToken());//所有接口必填
            jsonObject.put("username",myApp.getCurrentUserName());
            dataObject.put("modelType", AircatConst.modelType);
            jsonObject.put("data", dataObject.toString());
            // jsonObject.put("data", tmpObject.toString());
            Log.d(TAG, Base_Url.Url_Base + Base_Url.getBindDevice_Url);
            Log.d(TAG, jsonObject.toString());
            HttpTools.post(context, Base_Url.Url_Base + Base_Url.getBindDevice_Url, jsonObject, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String result = new String(responseBody);
                    OemLog.log(TAG, result);
                    try {
                        progressBar.setVisibility(View.GONE);
                        JSONObject jsonObject1 = new JSONObject(result);
                        String msg = jsonObject1.optString("message");
                        int code = jsonObject1.optInt("code");


                        if(code==11){
                            MyApplication.getInstance().exit();
                            Toast.makeText(context,"用户未登陆,请重新登陆",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AircatChangeDeviceActivity.this,LoginActivity.class);
                            startActivity(intent);
                        }
                        if (code == 0) {
                            String dataString = jsonObject1.getString("data");
                            JSONArray array = new JSONArray(dataString);
                            if(array.length()>0){
                                for(int i = 0 ;i < array.length(); i ++){
                                    AircatDevice airCatDevice = new AircatDevice();
                                    JSONObject deviceObj = array.getJSONObject(i);

                                    airCatDevice.setBindName(deviceObj.optString("bindName"));
                                    airCatDevice.setMac(deviceObj.optString("mac"));
                                    airCatDevice.setCurrent(deviceObj.optBoolean("current"));
                                    airCatDevice.setCtime(deviceObj.optLong("ctime"));
                                    airCatDevice.setWay(deviceObj.optString("way"));
                                    devices.add(airCatDevice);
                                }
                                tv_empty.setVisibility(View.GONE);
                             adapter.notifyDataSetChanged();
                            }else{
                                tv_empty.setVisibility(View.VISIBLE);
                            }

                        } else  {
                            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(context, "服务器异常", Toast.LENGTH_LONG).show();

                    OemLog.log(TAG, "服务器请求失败");
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        Log.e(TAG, "onItemClick position=" + position);
        if(devices.get(position).isCurrent()){
            Toast.makeText(AircatChangeDeviceActivity.this, "不能删除当前设备！", Toast.LENGTH_LONG).show();
            return true;
        }
        new AlertDialog.Builder(AircatChangeDeviceActivity.this)
                .setTitle("是否删除设备")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        removeDevice(position);
                    }
                })
                .create()
                .show();

        return true;
    }





    /**
     * 切换设备
     * @param position
     */
    private void changeDevice(final int position) {
        try {
            //构造请求json对象
            JSONObject jsonObject = new JSONObject();
            JSONObject dataObject = new JSONObject();
            AircatDevice aircatDevice = devices.get(position);
            jsonObject.put("apikey", myApp.apikey);//所有接口必填
            jsonObject.put("username",myApp.getCurrentUserName());
            jsonObject.put("token", myApp.getCurentToken());
            dataObject.put("modelType", AircatConst.modelType);
            dataObject.put("mac",aircatDevice.getMac());
            dataObject.put("bindName", aircatDevice.getBindName());
            dataObject.put("way", aircatDevice.getWay());
            jsonObject.put("data", dataObject.toString());
            //绑定设备
            Log.d(TAG, Base_Url.Url_Base + Base_Url.bindDevice_Url);
            HttpTools.post(this, Base_Url.Url_Base + Base_Url.bindDevice_Url, jsonObject, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String result = new String(responseBody);
                    mProgressDialog.dismiss();
                    Log.d(TAG, result);
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        int code = jsonObject.optInt("code");
                        String msg = jsonObject.optString("message");
                        if (code == 0) {
                            Toast.makeText(context, "切换设备成功", Toast.LENGTH_LONG).show();
                            PreferencesUtils.putString(context, "curAircatMac", devices.get(position).getMac());
                            PreferencesUtils.putString(context, "curAircatName", devices.get(position).getBindName());
                            devices.get(position).setCurrent(true);
                            adapter.notifyDataSetChanged();

/*

                            Intent intent = new Intent(AircatChangeDeviceActivity.this, AirCatActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            startActivity(intent);*/
                            finish();

                        }else if(code==11){
                            MyApplication.getInstance().exit();
                            Toast.makeText(context,"用户未登陆,请重新登陆",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AircatChangeDeviceActivity.this,LoginActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(context,msg, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    OemLog.log(TAG, "服务器请求失败");
                    mProgressDialog.dismiss();
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    /**
     * 解绑设备
     * @param position
     */
    private void removeDevice(final int position) {
        try {
            //构造请求json对象
            JSONObject jsonObject = new JSONObject();
            JSONObject dataObject = new JSONObject();
            AircatDevice aircatDevice = devices.get(position);
            jsonObject.put("apikey", myApp.apikey);//所有接口必填
            jsonObject.put("username",myApp.getCurrentUserName());
            jsonObject.put("token", myApp.getCurentToken());
            dataObject.put("modelType", AircatConst.modelType);
            dataObject.put("mac",aircatDevice.getMac());
            jsonObject.put("data", dataObject.toString());
            //绑定设备
            Log.d(TAG, Base_Url.Url_Base + Base_Url.unbindDevice_Url);
            HttpTools.post(this, Base_Url.Url_Base + Base_Url.unbindDevice_Url, jsonObject, new AsyncHttpResponseHandler() {
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
                            Intent intent = new Intent(AircatChangeDeviceActivity.this,LoginActivity.class);
                            startActivity(intent);
                        }else  if (code == 0) {
                            Toast.makeText(context, "删除设备成功", Toast.LENGTH_LONG).show();
                            if(devices.get(position).isCurrent()){
                                PreferencesUtils.putString(context, "curAircatMac", "");
                                PreferencesUtils.putString(context,"curAircatName","");
                            }

                           // mMessageItems.remove(position);
                            devices.remove(position);
                            adapter.notifyDataSetChanged();
                        }else{
                            Toast.makeText(context,msg, Toast.LENGTH_LONG).show();
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

    public void back(View v) {
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(devices.get(position).isCurrent()){
            return  ;
        }
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("正在切换...");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        changeDevice(position);

    }


}