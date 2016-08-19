package com.capitalbio.oemv2.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.capitalbio.oemv2.myapplication.Base.Const;
import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.BraceleteLib.BraceleteDevices;
import com.capitalbio.oemv2.myapplication.Const.Base_Url;
import com.capitalbio.oemv2.myapplication.Fragment.BloodPressFragmentMeasure;
import com.capitalbio.oemv2.myapplication.Fragment.BodyFatFragmentMeasure;
import com.capitalbio.oemv2.myapplication.NetUtils.HttpTools;
import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;
import com.capitalbio.oemv2.myapplication.Utils.PreferencesUtils;
import com.capitalbio.oemv2.myapplication.Utils.Utility;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author lzq
 * @Time 2015/12/21 17:04
 */
public class DeviceSettingActivity extends BaseActivity implements View.OnClickListener {

    public static final String TAG = "DeviceSettingActivity";

    //返回键
    private RelativeLayout rlback;
    //标题
    private TextView tvtitlename;
    //绑定与解绑按钮
    private TextView tvbind;
    //查看报告按钮
    private TextView tvcheckRefer;
    //当前设备名
    private String devicename = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devicesetting);

        initWhoStartMe();
        initView();
        initViewEvent();
        initViewData();
    }

    private void initWhoStartMe(){
        devicename = getIntent().getStringExtra("type");
    }

    private void initView(){
        rlback = (RelativeLayout) this.findViewById(R.id.rl_devicesetting_back);
        tvtitlename = (TextView) this.findViewById(R.id.tv_devicesetting_titlename);
        tvbind = (TextView) this.findViewById(R.id.tv_devicesetting_unbind);
        tvcheckRefer = (TextView) this.findViewById(R.id.tv_devicesetting_reference);
    }

    private void initViewEvent(){
        rlback.setOnClickListener(this);
        tvbind.setOnClickListener(this);
        tvcheckRefer.setOnClickListener(this);
    }

    private void initViewData(){
        if(devicename.equals("体脂秤")){
            tvtitlename.setText(R.string.tizhichengshezhi);
            tvcheckRefer.setText(R.string.tizhigexiangzhibiaocankao);
        }
        if(devicename.equals("血压计")){
            tvtitlename.setText(R.string.xueyajishezhi);
            tvcheckRefer.setText(R.string.xueyagexiangzhibiaocankao);
        }
    }

    @Override
    public void onClick(View view) {
       switch (view.getId())
       {
           //返回
           case R.id.rl_devicesetting_back:
               this.finish();
               break;
           //绑定与解绑
           case R.id.tv_devicesetting_unbind:
               android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
               final android.support.v7.app.AlertDialog alertDialog = builder.create();
               alertDialog.show();
               Window window = alertDialog.getWindow();
               window.setContentView(R.layout.dialog_devicesetting);

               TextView tvtitle = (TextView) window.findViewById(R.id.tv_devicesetting_dialog_title);
               RelativeLayout rlyes = (RelativeLayout) window.findViewById(R.id.rl_devicesetting_dialog_yes);
               RelativeLayout rlno = (RelativeLayout) window.findViewById(R.id.rl_devicesetting_dialog_no);

               rlyes.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       //是
                       /*Intent to = new Intent();
                       to.setClass(DeviceSettingActivity.this,DeviceBindActivity.class);
                       to.putExtra("type",devicename);
                       startActivity(to);

                       alertDialog.dismiss();*/

                       switch (devicename) {
                           case "血压计":
                               unbindDevices(Const.TYPE_BLOODPRESS);
                               break;
                           case "体脂秤":
                               unbindDevices(Const.TYPE_BODYFAT);
                               break;
                           default:
                               break;
                       }


                       alertDialog.dismiss();
                   }
               });
               rlno.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       //否
                       alertDialog.dismiss();
                   }
               });

               break;
           //查看参考标准
           case R.id.tv_devicesetting_reference:

               switch (devicename) {
                   case "血压计":
                       Utility.startActivity(context, IndexBloodActivity.class);
                       break;
                   case "体脂秤":
                       Utility.startActivity(context, IndexBodyActivity.class);
                       break;
                   default:
                       break;
               }
               break;
       }
    }

    //解绑
    public void unbindDevices(final String deviceType){

        try {
            //构造请求json对象
            JSONObject jsonObject = new JSONObject();
            JSONObject dataObj = new JSONObject();

            jsonObject.put("apikey", MyApplication.getInstance().apikey);
            jsonObject.put("username", MyApplication.getInstance().getCurrentUserName());
            jsonObject.put("token", MyApplication.getInstance().getCurentToken());
            dataObj.put("modelType", deviceType);

            switch (deviceType) {
                case Const.TYPE_BRACELETE:
                    dataObj.put("mac", PreferencesUtils.getString(context, "default_bracelete_address", ""));
                    dataObj.put("watchId", PreferencesUtils.getString(context, "default_bracelete_address", ""));
                    break;
                case Const.TYPE_BODYFAT:
                    dataObj.put("mac", PreferencesUtils.getString(context, "default_bodyfat_address", ""));
                    dataObj.put("watchId", PreferencesUtils.getString(context, "default_bodyfat_address", ""));
                    break;
                case Const.TYPE_BLOODPRESS:
                    dataObj.put("mac", PreferencesUtils.getString(context, "default_bloodpress_address", ""));
                    dataObj.put("watchId", PreferencesUtils.getString(context, "default_bloodpress_address", ""));
                    break;
                default:
                    break;
            }

            jsonObject.put("data", dataObj.toString());
            OemLog.log(TAG, "params is ....." + Base_Url.unbindDevice_Url + "--" + jsonObject.toString());

            //
            HttpTools.post(this, Base_Url.Url_Base + Base_Url.unbindDevice_Url, jsonObject, new AsyncHttpResponseHandler() {


                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {

                    String result = new String(responseBody);
                    Log.i(TAG,"result is "+result);
                    try {
                        Intent intent;
                        JSONObject jsonObject1 = new JSONObject(result);
                        String msg = jsonObject1.optString("message");
                        if("success".equals(msg)){
                            switch (deviceType) {
                                case Const.TYPE_BLOODPRESS:
                                    Toast.makeText(context,"解绑成功",Toast.LENGTH_SHORT).show();
                                    PreferencesUtils.putString(context, "default_bloodpress_address", "");
                                    intent = new Intent();
                                    setResult(BloodPressFragmentMeasure.BLOOD_PRESS_UNBIND_SUCCESSFULL, intent);
                                    finish();
                                    break;
                                case Const.TYPE_BODYFAT:
                                    Toast.makeText(context,"解绑成功",Toast.LENGTH_SHORT).show();
                                    PreferencesUtils.putString(context, "default_bodyfat_address", "");
                                    intent = new Intent();
                                    setResult(BodyFatFragmentMeasure.BODYFAT_UNBIND_SUCCESSFULL, intent);
                                    finish();
                                    break;
                                case Const.TYPE_BRACELETE:
                                    Toast.makeText(context,"解绑成功",Toast.LENGTH_SHORT).show();
                                    BraceleteDevices.getInstance().disconnect();
                                    PreferencesUtils.putString(context, "default_bracelete_address", "");
                                    BraceleteDevices.getInstance().disconnect();
                                    break;
                                default:
                                    break;
                            }

                        }else{
                            Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                    Toast.makeText(context, "服务器异常", Toast.LENGTH_LONG).show();

                }


            });


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
