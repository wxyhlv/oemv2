/*
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
import com.capitalbio.oemv2.myapplication.Utils.TimeStampUtil;
import com.capitalbio.oemv2.myapplication.View.slideDeleteView.ListViewCompat;
import com.capitalbio.oemv2.myapplication.View.slideDeleteView.SlideView;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class AircatChangeDeviceActivity2 extends  BaseActivity implements AdapterView.OnItemLongClickListener ,OnClickListener{

    private static final String TAG = "AircatChangeDevice";

    private ListViewCompat mListView;
    private boolean editState;
    private List<MessageItem> mMessageItems = new ArrayList<MessageItem>();
  //  private ToggleButton tv_edit;
    private TextView tv_edit;
    private SlideView mLastSlideViewWithStatusOn;
    SlideAdapter adapter;
    ProgressBar progressBar;
    private ProgressDialog mProgressDialog = null;
    int selectIndex = -1;//用来保存初始状态的选中项
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

        mListView.setOnItemLongClickListener(this);

        tv_edit.setOnClickListener(this);
    }

    public void initView() {
        tv_edit = (TextView) findViewById(R.id.tv_edit);
        mListView = (ListViewCompat) findViewById(R.id.list);
        tv_edit.setText("编辑");
        progressBar = (ProgressBar) findViewById(R.id.pb_progress);
        progressBar.setVisibility(View.VISIBLE);
        initData();

    }
    public  void initData() {
        //获取已绑定的设备 getBindDevice_Url
        try {
            //构造请求json对象
            final JSONObject jsonObject = new JSONObject();
            JSONObject dataObject = new JSONObject();

            jsonObject.put("apikey", myApp.apikey);//所有接口必填
            jsonObject.put("token", PreferencesUtils.getString(context, "token"));//所有接口必填
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
                            Intent intent = new Intent(AircatChangeDeviceActivity2.this,LoginActivity.class);
                            startActivity(intent);
                        }
                        if (code == 0) {
                            String dataString = jsonObject1.getString("data");
                            JSONArray array = new JSONArray(dataString);
                            if(array.length()>0){
                                for(int i = 0 ;i < array.length(); i ++){
                                    AircatDevice airCatDevice = new AircatDevice();
                                    JSONObject deviceObj = array.getJSONObject(i);
                                    MessageItem messageItem = new MessageItem();
                                    messageItem.name = deviceObj.optString("bindName");
                                    messageItem.isChecked = deviceObj.optBoolean("current");
                                    if(deviceObj.toString().contains("utime")){
                                        String utime = TimeStampUtil.timestamp2date(deviceObj.optLong("utime"));
                                        messageItem.time = "更新时间：" + utime;
                                    }else {
                                        String ctime = TimeStampUtil.timestamp2date(deviceObj.optLong("ctime"));
                                        messageItem.time = "更新时间："+ ctime;
                                    }
                                    messageItem.way = "添加方式：" + deviceObj.optString("way");
                                    airCatDevice.setBindName(deviceObj.optString("bindName"));
                                    airCatDevice.setMac(deviceObj.optString("mac"));
                                    airCatDevice.setCurrent(deviceObj.optBoolean("current"));
                                    airCatDevice.setCtime(deviceObj.optLong("ctime"));
                                    if(airCatDevice.isCurrent()){
                                        selectIndex = i;
                                    }
                                    devices.add(airCatDevice);
                                    mMessageItems.add(messageItem);
                                }
                                adapter = new SlideAdapter();
                                mListView.setAdapter(adapter);
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
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Log.e(TAG, "onItemClick position=" + position);

        if (!editState) {
            if(devices.get(position).isCurrent()){
               return  false;
            }
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("正在切换...");
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(true);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();
            changeDevice(position);

        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.tv_edit:
                if(!editState){
                    tv_edit.setText("保存");
                    editState = true;
                    adapter.notifyDataSetChanged();
                }
                else{
                    if(hashMap.size()>0){
                        if(hashMap.containsValue("")){
                            Toast.makeText(context,"设备名称不能为空！",Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if(NetTool.isNetwork(context, true)){
                            progressBar.setVisibility(View.VISIBLE);
                            updateName();
                            editState = false;

                        }
                    }else{
                        editState = false;
                        tv_edit.setText("编辑");
                        adapter.notifyDataSetChanged();
                    }

                }


                break;
        }
    }

    */
/**
     * 修改设备名称
     *//*

    private void updateName() {

        try {
            //构造请求json对象
            final JSONObject jsonObject = new JSONObject();
            JSONObject dataObject = new JSONObject();

            jsonObject.put("apikey", myApp.apikey);//所有接口必填
            jsonObject.put("token", PreferencesUtils.getString(context, "token"));//所有接口必填
            jsonObject.put("username",myApp.getCurrentUserName());
            final HashMap<String,String> map = new HashMap<>();
            for(int i = 0 ;i<devices.size();i++){
               if(hashMap.get(i) != null){

                   map.put(devices.get(i).getMac(),hashMap.get(i));
               }
            }
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
                    progressBar.setVisibility(View.GONE);
                   try {
                        JSONObject jsonObject1 = new JSONObject(result);
                        String msg = jsonObject1.optString("message");
                        int code = jsonObject1.optInt("code");


                        if (code == 11) {
                            MyApplication.getInstance().exit();
                            Toast.makeText(context, "用户未登陆,请重新登陆", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AircatChangeDeviceActivity2.this, LoginActivity.class);
                            startActivity(intent);
                        }
                        if (code == 0) {
                            tv_edit.setText("编辑");
                            Toast.makeText(context, "修改成功", Toast.LENGTH_SHORT).show();
                            adapter.notifyDataSetChanged();

                            if(map.containsKey(MyApplication.getInstance().getAircatDeviceMac())){
                                PreferencesUtils.putString(context,"curAircatName",map.get(MyApplication.getInstance().getAircatDeviceMac()));                            }
                        } else {
                            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Toast.makeText(context, "服务器异常！", Toast.LENGTH_LONG).show();
                    OemLog.log(TAG, "服务器请求失败");
                    progressBar.setVisibility(View.GONE);

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    private class SlideAdapter extends BaseAdapter implements SlideView.OnSlideListener {
        private Integer index = -1;
        private LayoutInflater mInflater;
        ViewHolder holder;

        SlideAdapter() {
            super();
            mInflater = getLayoutInflater();
        }

        @Override
        public int getCount() {
            return mMessageItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mMessageItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            SlideView slideView = (SlideView) convertView;
*/
/*
          if (slideView == null) {
                View itemView = mInflater.inflate(R.layout.list_item, null);

                slideView = new SlideView(AircatChangeDeviceActivity.this);
                slideView.setContentView(itemView);

                holder = new ViewHolder(slideView);
                slideView.setOnSlideListener(this);
                slideView.setTag(holder);
            } else {
                holder = (ViewHolder) slideView.getTag();
                holder.title.setTag(position);
            }*//*

            View itemView = mInflater.inflate(R.layout.list_item, null);

            slideView = new SlideView(AircatChangeDeviceActivity2.this);
            slideView.setContentView(itemView);

            holder = new ViewHolder(slideView);
            slideView.setOnSlideListener(this);
            slideView.setTag(holder);

            //回退的效果
            MessageItem item = mMessageItems.get(position);
            item.slideView = slideView;
            item.slideView.shrink();

            holder.title.setText(item.name);
            holder.titleTextView.setText(item.name);
            holder.way.setText(item.way);
            holder.time.setText(item.time);
            if (item.isChecked) {
                holder.checked.setVisibility(View.VISIBLE);
            } else {
                holder.checked.setVisibility(View.GONE);
            }

            holder.title.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    // 将editText中改变的值设置的HashMap中
                    hashMap.put(position, s.toString().trim());
                }
            });


            // 如果hashMap不为空，就设置的editText
            if (hashMap.get(position) != null) {
                holder.title.setText(hashMap.get(position));
                holder.titleTextView.setText(hashMap.get(position));
            }
            if (editState) {
                holder.titleTextView.setVisibility(View.GONE);
                holder.title.setVisibility(View.VISIBLE);
                holder.title.setEnabled(true);


            } else {
                holder.title.setEnabled(false);
                holder.titleTextView.setVisibility(View.VISIBLE);
                holder.title.setVisibility(View.GONE);

            }
            holder.deleteHolder.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mMessageItems!=null&&mMessageItems.size() < 2) {
                        Toast.makeText(AircatChangeDeviceActivity2.this, "必须留一个设备！", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(devices.get(position).isCurrent()){
                        Toast.makeText(AircatChangeDeviceActivity2.this, "不能删除当前设备！", Toast.LENGTH_LONG).show();
                        return;
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(AircatChangeDeviceActivity2.this);
                    builder.setMessage("确认删除吗？");
                    builder.setTitle("提示");
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            removeDevice(position);

                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override


                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }

                    });
                    builder.create().show();

                }
            });

            return slideView;
        }


        @Override
        public void onSlide(View view, int status) {
            if (mLastSlideViewWithStatusOn != null && mLastSlideViewWithStatusOn != view) {
                mLastSlideViewWithStatusOn.shrink();
            }

            if (status == SLIDE_STATUS_ON) {
                mLastSlideViewWithStatusOn = (SlideView) view;
            }
        }

    }

    public class MessageItem {
        public String name;
        public String way;
        public String time;
        public SlideView slideView;
        public boolean isChecked;
    }

    private static class ViewHolder {
        public TextView titleTextView;
        public TextView way;
        public TextView time;
        public TextView checked;
        // private ImageView iv_delete;

        public ViewGroup deleteHolder;
        private EditText title;

        ViewHolder(View view) {
            title = (EditText) view.findViewById(R.id.title);
            titleTextView = (TextView) view.findViewById(R.id.tv_devicename);
            way = (TextView) view.findViewById(R.id.way);
            time = (TextView) view.findViewById(R.id.time);
            checked = (TextView) view.findViewById(R.id.tv_current_device);
            //  iv_delete = (ImageView) view.findViewById(R.id.iv_delete);
            deleteHolder = (ViewGroup) view.findViewById(R.id.holder);
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
            jsonObject.put("username",myApp.getCurrentUserName());
            jsonObject.put("token", PreferencesUtils.getString(context, "token"));
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
                            PreferencesUtils.putString(context,"curAircatName",devices.get(position).getBindName());

                            mMessageItems.get(position).isChecked =true;
                            mMessageItems.get(selectIndex).isChecked = false;
                            adapter.notifyDataSetChanged();

*/
/*

                            Intent intent = new Intent(AircatChangeDeviceActivity.this, AirCatActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            startActivity(intent);*//*

                            finish();

                        }else if(code==11){
                            MyApplication.getInstance().exit();
                            Toast.makeText(context,"用户未登陆,请重新登陆",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AircatChangeDeviceActivity2.this,LoginActivity.class);
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
            jsonObject.put("username",myApp.getCurrentUserName());
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

                        if(code==11){
                            MyApplication.getInstance().exit();
                            Toast.makeText(context,"用户未登陆,请重新登陆",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AircatChangeDeviceActivity2.this,LoginActivity.class);
                            startActivity(intent);
                        }else  if (code == 0) {
                            Toast.makeText(context, "删除设备成功", Toast.LENGTH_LONG).show();
                            if(devices.get(position).isCurrent()){
                                PreferencesUtils.putString(context, "curAircatMac", "");
                                PreferencesUtils.putString(context,"curAircatName","");
                            }

                            mMessageItems.remove(position);
                            adapter.notifyDataSetChanged();
                            OemLog.log(TAG, selectIndex + "\\\\\\\\");
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
