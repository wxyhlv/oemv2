/*
package com.capitalbio.oemv2.myapplication.Activity;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.Bean.AircatDevice;
import com.capitalbio.oemv2.myapplication.Const.AircatConst;
import com.capitalbio.oemv2.myapplication.Const.Base_Url;
import com.capitalbio.oemv2.myapplication.NetUtils.HttpTools;
import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;
import com.capitalbio.oemv2.myapplication.Utils.PreferencesUtils;
import com.capitalbio.oemv2.myapplication.View.QQListView;
import com.capitalbio.oemv2.myapplication.View.QQListView.DelButtonClickListener;
import com.capitalbio.oemv2.myapplication.adapter.SlideDelAircatAdapter;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;

*/
/**
 * 弃用
 *//*

public class AircatChangeDevice2Activity extends Activity implements OnItemClickListener {

    private static final String TAG = "AircatChangeDevice";

    //private ListViewCompat mListView;
    private boolean editState;
    private ToggleButton tv_edit;
    // private SlideView mLastSlideViewWithStatusOn;
    SlideDelAircatAdapter adapter;
    QQListView listview;
    private ProgressDialog mProgressDialog = null;
    int selectIndex;
    private Context context;
    MyApplication myApp;
    // 定义一个HashMap，用来存放EditText的值，Key是position
    HashMap<Integer, String> hashMap = new HashMap<Integer, String>();
    private List<AircatDevice> devices = new ArrayList<AircatDevice>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_delete);

        context = this;
        myApp = MyApplication.getInstance();
        initView();

        //   listview.setOnItemClickListener(this);
        listview.setDelButtonClickListener(new DelButtonClickListener() {
            @Override
            public void clickHappend(final int position) {
                Toast.makeText(context, position + " A " , Toast.LENGTH_LONG).show();
               // removeDevice(position);
            }
        });

        listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, position + " B", Toast.LENGTH_LONG).show();
                adapter.setChecked(position);
               */
/* if (!editState) {
                    mProgressDialog = new ProgressDialog(AircatChangeDeviceActivity.this);
                    mProgressDialog.setMessage("正在切换...");
                    mProgressDialog.setIndeterminate(true);
                    mProgressDialog.setCancelable(true);
                    mProgressDialog.setCanceledOnTouchOutside(false);
                    mProgressDialog.show();
                    //changeDevice(position);

                } else {
                    //     Toast.makeText(AircatChangeDeviceActivity.this, "修改名称！", Toast.LENGTH_LONG).show();

                }*//*

            }
        });
        tv_edit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editState = isChecked;
                adapter.setEditState(editState);
            }
        });
    }

    public void initView() {
        tv_edit = (TextView) findViewById(R.id.tv_edit);
        //  mListView = (ListViewCompat) findViewById(R.id.list);
        listview = (QQListView) findViewById(R.id.list);
        initData2();

    }

    private void initData2() {
        for(int i = 0;i<6;i++){
            AircatDevice airCatDevice = new AircatDevice();

            airCatDevice.setBindName("wxy" + i);
            if(i == 0){
                airCatDevice.setCurrent(true);

            }else {
                airCatDevice.setCurrent(false);
            }
            airCatDevice.setCtime(i);
            airCatDevice.setWay("jjjj");
            devices.add(airCatDevice);

        }
        adapter = new SlideDelAircatAdapter(context, devices,editState);
        listview.setAdapter(adapter);

    }

    public void initData() {
        //获取已绑定的设备 getBindDevice_Url
        try {
            //构造请求json对象
            final JSONObject jsonObject = new JSONObject();
            JSONObject dataObject = new JSONObject();

            jsonObject.put("apikey", MyApplication.getInstance().apikey);//所有接口必填
            jsonObject.put("token", PreferencesUtils.getString(context, "token"));//所有接口必填
            jsonObject.put("username", MyApplication.getInstance().getCurrentUser().getUsername());
            dataObject.put("modelType", AircatConst.modelType);
            jsonObject.put("data", dataObject.toString());
            // jsonObject.put("data", tmpObject.toString());
            Log.d(TAG, Base_Url.Url_Base + Base_Url.getBindDevice_Url);
            Log.d(TAG, jsonObject.toString());
            HttpTools.post(context, Base_Url.Url_Base + Base_Url.getBindDevice_Url, jsonObject, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String result = new String(responseBody);
                    Log.i(TAG, result);
                    try {
                        JSONObject jsonObject1 = new JSONObject(result);
                        String msg = jsonObject1.optString("message");
                        if (msg.equals("success")) {
                            String dataString = jsonObject1.getString("data");
                            JSONArray array = new JSONArray(dataString);
                            if (array.length() > 0) {
                                for (int i = 0; i < array.length(); i++) {
                                    AircatDevice airCatDevice = new AircatDevice();
                                    JSONObject deviceObj = array.getJSONObject(i);

                                    airCatDevice.setBindName(deviceObj.optString("bindName"));
                                    airCatDevice.setMac(deviceObj.optString("mac"));
                                    airCatDevice.setCurrent(deviceObj.optBoolean("current"));
                                    airCatDevice.setCtime(deviceObj.optLong("ctime"));
                                    airCatDevice.setUtime(deviceObj.optLong("utime"));
                                    if (airCatDevice.isCurrent()) {
                                        selectIndex = i;
                                    }
                                    devices.add(airCatDevice);
                                    //   mMessageItems.add(messageItem);
                                }
                                adapter = new SlideDelAircatAdapter(context, devices,editState);
                                listview.setAdapter(adapter);
                            }

                        } else {
                            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();

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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.e(TAG, "onItemClick position=" + position);

        if (!editState) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("正在切换...");
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(true);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();
            changeDevice(position);

        } else {
            //     Toast.makeText(AircatChangeDeviceActivity.this, "修改名称！", Toast.LENGTH_LONG).show();

        }

    }





    */
/**
     * 切换设备
     * @param position
     *//*

    private void changeDevice(final int position) {
        try {
            //构造请求json对象
            JSONObject jsonObject = new JSONObject();
            JSONObject dataObject = new JSONObject();
            AircatDevice aircatDevice = devices.get(position);
            jsonObject.put("apikey", myApp.apikey);//所有接口必填
            jsonObject.put("username",myApp.getCurrentUser().getUsername());
            jsonObject.put("token", PreferencesUtils.getString(context, "token"));
            dataObject.put("modelType", AircatConst.modelType);
            dataObject.put("mac",aircatDevice.getMac());
            dataObject.put("bindName", aircatDevice.getBindName());
            dataObject.put("way", "code");
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
                     //       MyApplication.getInstance().setAircat(devices.get(position));
                            selectIndex = position;
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
                    mProgressDialog.dismiss();
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    */
/**
     * 解绑设备
     * @param position
     *//*

    private void removeDevice(final int position) {
        try {
            //构造请求json对象
            JSONObject jsonObject = new JSONObject();
            JSONObject dataObject = new JSONObject();
            AircatDevice aircatDevice = devices.get(position);
            jsonObject.put("apikey", myApp.apikey);//所有接口必填
            jsonObject.put("username",myApp.getCurrentUser().getUsername());
            jsonObject.put("token", PreferencesUtils.getString(context, "token"));
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
                        if (code == 0) {
                            Toast.makeText(context, "删除设备成功", Toast.LENGTH_LONG).show();
                            //mMessageItems.remove(position);
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

}*/
