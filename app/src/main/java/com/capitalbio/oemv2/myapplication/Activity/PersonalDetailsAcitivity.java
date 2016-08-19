package com.capitalbio.oemv2.myapplication.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.Bean.CHDFator;
import com.capitalbio.oemv2.myapplication.Bean.CancerFator;
import com.capitalbio.oemv2.myapplication.Bean.DiabeteFactor;
import com.capitalbio.oemv2.myapplication.Bean.HbloodPresureFator;
import com.capitalbio.oemv2.myapplication.Bean.LoginUser;
import com.capitalbio.oemv2.myapplication.Bean.ObesityFator;
import com.capitalbio.oemv2.myapplication.Bean.Questionnaire;
import com.capitalbio.oemv2.myapplication.Bean.StrokeFator;
import com.capitalbio.oemv2.myapplication.Const.Base_Url;
import com.capitalbio.oemv2.myapplication.Devices.BloodPressDevice.ByteUtil;
import com.capitalbio.oemv2.myapplication.FirstPeriod.Utils.Common_dialog;
import com.capitalbio.oemv2.myapplication.NetUtils.HttpTools;
import com.capitalbio.oemv2.myapplication.NetUtils.NetTool;
import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;
import com.capitalbio.oemv2.myapplication.Utils.PreferencesUtils;
import com.capitalbio.oemv2.myapplication.Utils.TimeStampUtil;
import com.capitalbio.oemv2.myapplication.Utils.UploadFile;
import com.capitalbio.oemv2.myapplication.Utils.Utility;
import com.capitalbio.oemv2.myapplication.View.GlideCircleTransform;
import com.capitalbio.oemv2.myapplication.View.GlideRoundTransform;
import com.capitalbio.oemv2.myapplication.View.RoundImageView;
import com.capitalbio.oemv2.myapplication.View.SelectPicPopupWindow;
import com.capitalbio.oemv2.myapplication.dialog.ChangeDateDialog;
import com.capitalbio.oemv2.myapplication.dialog.ChooseHeightDialog;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.ex.DbException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class PersonalDetailsAcitivity extends BaseActivity implements View.OnClickListener,RadioGroup.OnCheckedChangeListener {

    private String TAG = "PersonalDetailsAcitivity";
    FrameLayout flPersonDetail;
    private TextView tv_birthday,tv_sex;
    private TextView tv_height;
    private TextView tv_weight;
    private TextView tv_carrer;
    private Button ivMale;
    private Button ivFemale;
    private LinearLayout layout_desease_factor;
    private ToggleButton toggleButton;
    private ViewStub viewStub_hypertension,viewStub_stroke,viewStub_chd,viewStub_diabetic,viewStub_obesity,viewStub_therioma;
    int iHypertension,iStroke,iChd ,iDiabetic,iObesity,iTherioma= 0;

    private RadioGroup radioGroupParent,radioGroupYouPast,radioGroupYouNow;
    private ViewStub viewStubParent,viewStubYouPast,viewStubYouNow;

    private ImageView roundimage;
    // 自定义的弹出框类
    SelectPicPopupWindow menuWindow;
    private  LinearLayout parentPanel,youPastPanel,youNowPanel;
    private CheckBox cb_hyper,cb_stroke,cb_chd,cb_diabetic,cb_obesity,cb_cancer,cb_others;
    private StringBuilder builder_familyHistoryDiseases = new StringBuilder() ;//父母兄弟的疾病类型
    private StringBuilder builder_personalHistoryDiseases = new StringBuilder();//父母兄弟的疾病类型
    private StringBuilder builder_personalNowDiseases = new StringBuilder();//父母兄弟的疾病类型

    private RadioGroup rg_highBP_1,rg_highBP_2,rg_highBP_3,rg_highBP_4,rg_highBP_5,rg_highBP_6,rg_highBP_7,rg_highBP_8,rg_highBP_9;

    private RadioGroup rg_stroke_1,rg_stroke_2,rg_stroke_3,rg_stroke_4,rg_stroke_5,rg_stroke_6,rg_stroke_7,rg_stroke_8,rg_stroke_9,rg_stroke_10;

    private RadioGroup rg_chd_1,rg_chd_2,rg_chd_3,rg_chd_4;
    private RadioGroup rg_diabete_2,rg_diabete_3,rg_diabete_4,rg_diabete_5,rg_diabete_6,rg_diabete_7,rg_diabete_8,rg_diabete_9,rg_diabete_10,rg_diabete_11,rg_diabete_12;

    private EditText edt_diabete_waise;//腰围
    private RadioGroup rg_obsity_1,rg_obsity_2,rg_obsity_3,rg_obsity_4,rg_obsity_5,rg_obsity_6,rg_obsity_7,rg_obsity_8,rg_obsity_9,rg_obsity_10,rg_obsity_11;
    private RadioGroup rg_cancer_1,rg_cancer_2,rg_cancer_3,rg_cancer_4,rg_cancer_5,rg_cancer_6,rg_cancer_7,rg_cancer_8;


    private LoginUser user;
    private Button btn_commit;
    // 上传照片处理
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    String photoUrl = (String) msg.obj;
                    Log.i("uploadPic", "success");
                    Toast.makeText(context,"上传头像成功!",Toast.LENGTH_LONG).show();
                   // roundimage.setImageBitmap(photo);
                    Glide.with(context).
                            load(picPath).
                            transform(new GlideCircleTransform(context)).
                            into(roundimage);
                    user.setPicpath(picPath);
                    user.setHeadPic(ByteUtil.getBytes(picPath));
/*
                    String bitmaps = new String(ByteUtil.getBytes(picPath),0);
*/
                    PreferencesUtils.putString(context, PreferencesUtils.getString(context, "loginNum") + "head", user.getPicpath());
                    try {
                        MyApplication.getDB().saveOrUpdate(user);
                     /*   MyApplication.getDB().executeUpdateDelete("update userinfo set picpath = '" + picPath +
                                "' WHERE username = '" + MyApplication.getInstance().getCurrentUserName() +"'");
                 */   } catch (DbException e) {
                        Log.i(TAG,"UPDATE FAIL");
                        e.printStackTrace();
                    }
                    Common_dialog.stop_WaitingDialog();

                    break;
                case 1:
                    Log.i("uploadPic", "fail");

                    Toast.makeText(context,"上传头像失败!",Toast.LENGTH_LONG).show();
                    Common_dialog.stop_WaitingDialog();
                    break;
                case 2:
                    String ressult = (String) msg.obj;

                    break;
                default:
                    break;
            }
        };
    };

    @Override
    protected void initChildLayout() {
        super.initChildLayout();
        user = MyApplication.getInstance().getCurrentUser();
        initChildView();
    }


    private void initChildView() {

        rl.setBackgroundResource(R.drawable.bg_login);
        setLeftTopIcon(R.drawable.ic_back);
        ivSplitLine.setVisibility(View.GONE);
        flPersonDetail = (FrameLayout) View.inflate(this, R.layout.activity_personal_details_acitivity, null);
        llBody.addView(flPersonDetail);
        btn_commit = (Button) flPersonDetail.findViewById(R.id.btn_commit);
        btn_commit.setOnClickListener(this);
        roundimage = (ImageView) flPersonDetail.findViewById(R.id.roundimage);
        roundimage.setOnClickListener(this);
        tv_sex = (TextView) flPersonDetail.findViewById(R.id.tv_sex);
        tv_sex.setOnClickListener(this);

      /*  ivMale = (Button) flPersonDetail.findViewById(R.id.button_man);
        ivFemale = (Button) flPersonDetail.findViewById(R.id.button_woman);

        ivMale.setOnClickListener(this);
        ivFemale.setOnClickListener(this);*/

        tv_birthday = (TextView) flPersonDetail.findViewById(R.id.tv_birthday);
        tv_birthday.setOnClickListener(this);

        tv_height = (TextView) flPersonDetail.findViewById(R.id.tv_height);
        tv_height.setOnClickListener(this);


        tv_weight = (TextView) flPersonDetail.findViewById(R.id.tv_weight);
        tv_weight.setOnClickListener(this);

        tv_carrer = (TextView) flPersonDetail.findViewById(R.id.tv_carrer);
        tv_carrer.setOnClickListener(this);

        layout_desease_factor = (LinearLayout) flPersonDetail.findViewById(R.id.layout_desease_factor);
        toggleButton = (ToggleButton) flPersonDetail.findViewById(R.id.togglebtn_select);
        toggleButton.setOnClickListener(this);
        layout_desease_factor.setVisibility(View.GONE);
     //
        flPersonDetail.findViewById(R.id.tv_factor_hypertension).setOnClickListener(this);
        flPersonDetail.findViewById(R.id.tv_factor_stroke).setOnClickListener(this);
        flPersonDetail.findViewById(R.id.tv_factor_chd).setOnClickListener(this);
        flPersonDetail.findViewById(R.id.tv_factor_diabetic).setOnClickListener(this);
        flPersonDetail.findViewById(R.id.tv_factor_obesity).setOnClickListener(this);
        flPersonDetail.findViewById(R.id.tv_factor_therioma).setOnClickListener(this);

        radioGroupParent = (RadioGroup) flPersonDetail.findViewById(R.id.radio_group_parent);
        radioGroupParent.setOnCheckedChangeListener(this);

        radioGroupYouPast = (RadioGroup) flPersonDetail.findViewById(R.id.radio_group_yousicked);
        radioGroupYouPast.setOnCheckedChangeListener(this);

        radioGroupYouNow = (RadioGroup) flPersonDetail.findViewById(R.id.radio_group_yousicking);
        radioGroupYouNow.setOnCheckedChangeListener(this);

        parentPanel = (LinearLayout) flPersonDetail.findViewById(R.id.parentPanel);
        youPastPanel = (LinearLayout) flPersonDetail.findViewById(R.id.youPastPanel);
        youNowPanel = (LinearLayout) flPersonDetail.findViewById(R.id.youNowPanel);
       LoginUser user = MyApplication.getInstance().getCurrentUser();
        Log.i(TAG,user==null?"1":"2");

        if (user != null) {
            byte[] bytePic = user.getHeadPic();
           if (bytePic != null && bytePic.length > 0) {
             /*   Bitmap bitmap = ByteUtil.byteToBitmap(bytePic);
                roundimage.setImageBitmap(bitmap);*/
               Glide.with(context)
                       .load(bytePic)
                       .transform(new GlideCircleTransform(this)).into(roundimage);
            }else{
               Glide.with(context)
                       .load(R.drawable.ic_scene)
                       .transform(new GlideCircleTransform(context)).into(roundimage);
           }



        }
        showUserINfo();

    }
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.btn_take_photo:
                    // 相机
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(
                            MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(new File(
                                    Utility.getCacheDirectory(PersonalDetailsAcitivity.this),
                                    "temp.jpg")));
                    startActivityForResult(intent, 1);
                    break;
                case R.id.btn_pick_photo:
                    // 相册
                    Intent intent2 = new Intent(Intent.ACTION_PICK, null);
                    intent2.setDataAndType(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intent2, 2);
                    break;
                default:
                    break;
            }

        }

    };


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_commit:
                if(checkUserInfo()){
                    updateUserINfo();
                }
                break;

            case R.id.roundimage:
               // 弹框 相册-相机
                // showtoast("弹出相册相机");
                menuWindow = new SelectPicPopupWindow(
                        PersonalDetailsAcitivity.this, itemsOnClick);
                // 显示窗口
                menuWindow.showAtLocation(
                        PersonalDetailsAcitivity.this.findViewById(R.id.main),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

                break;

            case R.id.tv_sex:
                showPopSexSelect();
                break;
            case R.id.tv_birthday:
                showPopDateSelect();
                break;

            case R.id.tv_height:
                showPopHeightSelect();
                break;

            case R.id.tv_weight:
                showPopWeightSelect();
                break;
            case R.id.tv_carrer:
                showPopCarSelect();
                break;
/*

            case R.id.button_man:
                ivMale.setBackgroundResource(R.drawable.ic_orange_circle);
                ivFemale.setBackgroundResource(R.drawable.ic_gray_circle);
                sex="男";
                break;

            case R.id.button_woman:
                ivFemale.setBackgroundResource(R.drawable.ic_orange_circle);
                ivMale.setBackgroundResource(R.drawable.ic_gray_circle);
                sex = "女";
                break;
*/

            case R.id.togglebtn_select:
                if(toggleButton.isChecked()){
                    layout_desease_factor.setVisibility(View.VISIBLE);
                }else{
                    layout_desease_factor.setVisibility(View.GONE);
                }
                break;

            case R.id.tv_factor_hypertension:

                if(viewStub_hypertension==null){
                    viewStub_hypertension = (ViewStub) flPersonDetail.findViewById(R.id.viewstub_hypertension);
                    viewStub_hypertension.inflate();

                }else if(iHypertension %2 == 0 ){
                    viewStub_hypertension.setVisibility(View.VISIBLE);
                }else {
                    viewStub_hypertension.setVisibility(View.GONE);
                }
                iHypertension ++;

                findViewById_highbp();

                break;

            case R.id.tv_factor_stroke:

                if(viewStub_stroke==null){
                    viewStub_stroke = (ViewStub) flPersonDetail.findViewById(R.id.viewstub_stroke);
                    viewStub_stroke.inflate();

                }else if(iStroke %2 == 0 ){
                    viewStub_stroke.setVisibility(View.VISIBLE);
                }else {
                    viewStub_stroke.setVisibility(View.GONE);
                }
                iStroke ++;

                findViewById_stroke();

                break;
            case R.id.tv_factor_chd:

                if(viewStub_chd==null){
                    viewStub_chd = (ViewStub) flPersonDetail.findViewById(R.id.viewstub_chd);
                    viewStub_chd.inflate();

                }else if(iChd %2 == 0 ){
                    viewStub_chd.setVisibility(View.VISIBLE);
                }else {
                    viewStub_chd.setVisibility(View.GONE);
                }
                iChd ++;

                findViewById_chd();
                break;

            case R.id.tv_factor_diabetic:

                if(viewStub_diabetic==null){
                    viewStub_diabetic = (ViewStub) flPersonDetail.findViewById(R.id.viewstub_diabetic);
                    viewStub_diabetic.inflate();

                }else if(iDiabetic %2 == 0 ){
                    viewStub_diabetic.setVisibility(View.VISIBLE);
                }else {
                    viewStub_diabetic.setVisibility(View.GONE);
                }
                iDiabetic ++;
                findViewById_diabete();

                break;

            case R.id.tv_factor_obesity:

                if(viewStub_obesity==null){
                    viewStub_obesity = (ViewStub) flPersonDetail.findViewById(R.id.viewstub_obesity);
                    viewStub_obesity.inflate();

                }else if(iObesity %2 == 0 ){
                    viewStub_obesity.setVisibility(View.VISIBLE);
                }else {
                    viewStub_obesity.setVisibility(View.GONE);
                }
                iObesity ++;
                findViewById_obesity();

                break;

            case R.id.tv_factor_therioma:

                if(viewStub_therioma==null){
                    viewStub_therioma = (ViewStub) flPersonDetail.findViewById(R.id.viewstub_therioma);
                    viewStub_therioma.inflate();

                }else if(iTherioma %2 == 0 ){
                    viewStub_therioma.setVisibility(View.VISIBLE);
                }else {
                    viewStub_therioma.setVisibility(View.GONE);
                }
                iTherioma ++;
                findViewById_cancer();
                break;
        }
    }

    private void findViewById_stroke() {
        rg_stroke_1 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_stroke_1);

        rg_stroke_2 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_stroke_2);

        rg_stroke_3 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_stroke_3);

        rg_stroke_4 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_stroke_4);

        rg_stroke_5 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_stroke_5);

        rg_stroke_6 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_stroke_6);

        rg_stroke_7 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_stroke_7);

        rg_stroke_8 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_stroke_8);

        rg_stroke_9 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_stroke_9);
        rg_stroke_10 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_stroke_10);
    }

    private void findViewById_highbp() {
        rg_highBP_1 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_highBP_1);

        rg_highBP_2 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_highBP_2);

        rg_highBP_3 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_highBP_3);

        rg_highBP_4 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_highBP_4);

        rg_highBP_5 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_highBP_5);

        rg_highBP_6 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_highBP_6);

        rg_highBP_7 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_highBP_7);

        rg_highBP_8 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_highBP_8);

        rg_highBP_9 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_highBP_9);
    }

    private void findViewById_cancer() {
        rg_cancer_1 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_cancer_1);
        rg_cancer_2 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_cancer_2);

        rg_cancer_3 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_cancer_3);
        rg_cancer_4 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_cancer_4);

        rg_cancer_5 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_cancer_5);
        rg_cancer_6 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_cancer_6);

        rg_cancer_7 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_cancer_7);
        rg_cancer_8 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_cancer_8);
    }

    private void findViewById_obesity() {
        rg_obsity_1 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_obsity_1);

        rg_obsity_2 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_obsity_2);

        rg_obsity_3 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_obsity_3);

        rg_obsity_4 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_obsity_4);

        rg_obsity_5 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_obsity_5);

        rg_obsity_6 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_obsity_6);

        rg_obsity_7 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_obsity_7);

        rg_obsity_8 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_obsity_8);

        rg_obsity_9 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_obsity_9);
        rg_obsity_10 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_obsity_10);
        rg_obsity_11 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_obsity_11);
    }

    private void findViewById_diabete() {
        edt_diabete_waise = (EditText) flPersonDetail.findViewById(R.id.edt_size);
        rg_diabete_2 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_diabete_2);
        rg_diabete_3 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_diabete_3);
        rg_diabete_4 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_diabete_4);
        rg_diabete_5 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_diabete_5);
        rg_diabete_6 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_diabete_6);
        rg_diabete_7 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_diabete_7);
        rg_diabete_8 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_diabete_8);
        rg_diabete_9 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_diabete_9);
        rg_diabete_10 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_diabete_10);
        rg_diabete_11 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_diabete_11);
        rg_diabete_12 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_diabete_12);
    }

    //检查基本用户信息
    private boolean checkUserInfo() {
        if(TextUtils.isEmpty(tv_sex.getText().toString())){
            Toast.makeText(context,"请选择性别",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(tv_birthday.getText().toString())){
            Toast.makeText(context,"请选择出生日期",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(tv_height.getText().toString())){
            Toast.makeText(context,"请选择身高",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(tv_weight.getText().toString())){
            Toast.makeText(context,"请选择体重",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(tv_carrer.getText().toString())){
            Toast.makeText(context,"请选择职业",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private  LoginUser person;
    private Questionnaire questionBean;
    private String questionnaire;
    private void showUserINfo() {
        try {

            //构造请求json对象
            JSONObject jsonObject = new JSONObject();
            final JSONObject dataObject = new JSONObject();

            jsonObject.put("apikey", myApp.apikey);//所有接口必填
            jsonObject.put("username",myApp.getCurrentUserName());
            jsonObject.put("token", myApp.getCurentToken());
              //绑定设备
            Log.d(TAG, Base_Url.Url_Base + Base_Url.getUserInfo);
            Log.d(TAG,"getUserinfo " + jsonObject.toString());
            HttpTools.post(this, Base_Url.Url_Base + Base_Url.getUserInfo, jsonObject, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String result = new String(responseBody);
                    Log.d(TAG, result);
                    try {
                        JSONObject jsonObject1 = new JSONObject(result);
                        String msg = jsonObject1.optString("message");
                        if(msg.equals("success")){
                          //  JSONObject dataobj =jsonObject1.getJSONObject("data");
                            String jsonString = jsonObject1.getString("data");
                            JSONObject datajso = new JSONObject(jsonString);
                            person = JSON.parseObject(jsonString, LoginUser.class);
                            Log.i(TAG,person.toString());
                             questionnaire = datajso.getString("questionnaire");
                            questionBean = JSON.parseObject(questionnaire,Questionnaire.class);
                            initData();
                        }else{
                            Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    OemLog.log(TAG, "服务器请求失败");
                    Toast.makeText(getContext(), "服务器请求失败", Toast.LENGTH_LONG).show();

                }
            });

        } catch (JSONException e) {
            OemLog.log(TAG,"JSON EXCEPTION !1");
            e.printStackTrace();
        }
    }

    private void initData() {
      /*  if("男".equals(person.getSex())){
            ivMale.setBackgroundResource(R.drawable.ic_orange_circle);
            ivFemale.setBackgroundResource(R.drawable.ic_gray_circle);
            sex="男";
        }
        if("女".equals(person.getSex())){
            ivFemale.setBackgroundResource(R.drawable.ic_orange_circle);
            ivMale.setBackgroundResource(R.drawable.ic_gray_circle);
            sex = "女";
        }*/
        tv_sex.setText(person.getSex()==null?"":person.getSex());

        tv_birthday.setText(person.getAge()==null?"":person.getAge());
        tv_height.setText(person.getHeight()==null?"":person.getHeight());
        tv_weight.setText(person.getWeight()==null?"":person.getWeight());
        tv_carrer.setText(person.getCareer()==null?"":person.getCareer());


        //为家族疾病赋值
        String familyHistoryDieases = questionBean.getFamilyHistoryDieases();
        Log.i(TAG,"PARENT IS" +familyHistoryDieases);
        if(familyHistoryDieases.equals("否")) {
            if(viewStubParent != null){
                viewStubParent.setVisibility(View.GONE);
            }
            radioGroupParent.check(R.id.radiobtn_parent_no);

        }else if(familyHistoryDieases!=null&&!familyHistoryDieases.equals("")){
            if(viewStubParent == null){
                viewStubParent = (ViewStub) flPersonDetail.findViewById(R.id.viewstub_parent);
                viewStubParent.inflate();
            }else{
                viewStubParent.setVisibility(View.VISIBLE);
            }
            radioGroupParent.check(R.id.radiobtn_parent_yes);
            String[] familydiease = familyHistoryDieases.split(",");
            Log.i(TAG," AFTER SPLIT "+familydiease.length);
            setCheckedDisease(parentPanel,familydiease);
        }
        //为个人历史患病赋值
        String personalHistoryDieases = questionBean.getPersonalHistoryDieases();
        if(personalHistoryDieases.equals("否")){
            if(viewStubYouPast != null){
                viewStubYouPast.setVisibility(View.GONE);
            }
            radioGroupYouPast.check(R.id.radiobtn_youpast_no);

        }else if(personalHistoryDieases!=null&&!personalHistoryDieases.equals("")){
            if(viewStubYouPast == null){
                viewStubYouPast = (ViewStub) flPersonDetail.findViewById(R.id.viewStubYouPast);
                viewStubYouPast.inflate();

            }else{
                viewStubYouPast.setVisibility(View.VISIBLE);
            }
            radioGroupYouPast.check(R.id.radiobtn_youpast_yes);

            String[] diease = personalHistoryDieases.split(",");
            Log.i(TAG," AFTER SPLIT 2 "+diease);
            setCheckedDisease(youPastPanel,diease);
        }

        //为个人现在患病赋值
        String personalNowDieases = questionBean.getPersonalNowDieases();
        if(personalNowDieases.equals("否")){
            if(viewStubYouNow != null){
                viewStubYouNow.setVisibility(View.GONE);
            }
            radioGroupYouNow.check(R.id.radiobtn_yousicking_no);

        }else if(personalNowDieases!=null&&!personalNowDieases.equals("")){
            if(viewStubYouNow == null){
                viewStubYouNow = (ViewStub) flPersonDetail.findViewById(R.id.viewStubYouNow);
                viewStubYouNow.inflate();

            }else{
                viewStubYouNow.setVisibility(View.VISIBLE);
            }
            radioGroupYouNow.check(R.id.radiobtn_yousicking_yes);

            String[] diease = personalNowDieases.split(",");
            Log.i(TAG," AFTER SPLIT 3"+diease);
            setCheckedDisease(youNowPanel, diease);
        }
        //为致病因素赋值
        setCheckedRadiobutton();
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            //你父母兄弟是否患病
            case R.id.radiobtn_parent_yes:
                if(viewStubParent == null){
                    viewStubParent = (ViewStub) flPersonDetail.findViewById(R.id.viewstub_parent);
                    viewStubParent.inflate();

                }else{
                    viewStubParent.setVisibility(View.VISIBLE);
                }
                builder_familyHistoryDiseases =new StringBuilder();

                OnSelectDisease(parentPanel,builder_familyHistoryDiseases);
                break;
            case R.id.radiobtn_parent_no:
                if(viewStubParent != null){
                   viewStubParent.setVisibility(View.GONE);
                }
                builder_familyHistoryDiseases =new StringBuilder();
                builder_familyHistoryDiseases.append("否");
                break;
            //你过去是否患病
            case R.id.radiobtn_youpast_yes:
                if(viewStubYouPast == null){
                    viewStubYouPast = (ViewStub) flPersonDetail.findViewById(R.id.viewStubYouPast);
                    viewStubYouPast.inflate();
                }else{
                    viewStubYouPast.setVisibility(View.VISIBLE);
                }
                builder_personalHistoryDiseases = new StringBuilder();

                OnSelectDisease(youPastPanel,builder_personalHistoryDiseases);

                break;
            case R.id.radiobtn_youpast_no:
                if(viewStubYouPast != null){
                    viewStubYouPast.setVisibility(View.GONE);
                }
                builder_personalHistoryDiseases = new StringBuilder();
                builder_personalHistoryDiseases.append("否");

                break;

            //你现在是否患病
            case R.id.radiobtn_yousicking_yes:
                if(viewStubYouNow == null){
                    viewStubYouNow = (ViewStub) flPersonDetail.findViewById(R.id.viewStubYouNow);
                    viewStubYouNow.inflate();
                }else{
                    viewStubYouNow.setVisibility(View.VISIBLE);
                }
                builder_personalNowDiseases =new StringBuilder();
                OnSelectDisease(youNowPanel,builder_personalNowDiseases);

                break;
            case R.id.radiobtn_yousicking_no:
                if(viewStubYouNow != null){
                    viewStubYouNow.setVisibility(View.GONE);
                }
                builder_personalNowDiseases =new StringBuilder();

                builder_personalNowDiseases.append("否");

                break;
        }
    }

    private void OnSelectDisease(LinearLayout llPanel,final StringBuilder builder) {
        cb_hyper = (CheckBox) llPanel.findViewById(R.id.cb_hypertension);
        cb_hyper.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    builder.append(cb_hyper.getText().toString()+",");
                }else{
                }
            }
        });

        cb_stroke = (CheckBox) llPanel.findViewById(R.id.cb_stroke);
        cb_stroke.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    builder.append(cb_stroke.getText().toString()+",");
                }else{

                }
            }
        });


        cb_chd = (CheckBox) llPanel.findViewById(R.id.cb_chd);
        cb_chd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    builder.append(cb_chd.getText().toString()+",");
                }else{

                }
            }
        });

        cb_diabetic = (CheckBox) llPanel.findViewById(R.id.cb_diabetic);
        cb_diabetic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    builder.append(cb_diabetic.getText().toString()+",");
                }else{
                }
            }
        });
        cb_obesity = (CheckBox) llPanel.findViewById(R.id.cb_obesity);
        cb_obesity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    builder.append(cb_obesity.getText().toString()+",");
                }else{
                }
            }
        });

        cb_cancer = (CheckBox) llPanel.findViewById(R.id.cb_therioma);
        cb_cancer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    builder.append(cb_cancer.getText().toString() + ",");
                    Log.i(TAG, "diseases are " + builder.toString());

                } else {
                }
            }
        });
       //TODO 其他

    }
    private void setCheckedDisease(LinearLayout llPanel,String[] datas){
        cb_hyper = (CheckBox) llPanel.findViewById(R.id.cb_hypertension);
        cb_stroke = (CheckBox) llPanel.findViewById(R.id.cb_stroke);
        cb_chd = (CheckBox) llPanel.findViewById(R.id.cb_chd);
        cb_diabetic = (CheckBox) llPanel.findViewById(R.id.cb_diabetic);
        cb_obesity = (CheckBox) llPanel.findViewById(R.id.cb_obesity);
        cb_cancer = (CheckBox) llPanel.findViewById(R.id.cb_therioma);

        for(int i =0;i<datas.length;i++){

            if(datas[i].equals("高血压")){
                cb_hyper.setChecked(true);
            }
            if(datas[i].equals("脑卒中")){
                cb_stroke.setChecked(true);
            }
            if(datas[i].equals("冠心病")){
                cb_chd.setChecked(true);
            }
            if(datas[i].equals("糖尿病")){
                cb_diabetic.setChecked(true);
            }
            if(datas[i].equals("肥胖症")){
                cb_obesity.setChecked(true);
            }
            if(datas[i].equals("恶性肿瘤")){
                cb_cancer.setChecked(true);
            }else{

            }
        }
    }

    public void showPopDateSelect(){
        ChangeDateDialog mChangeDateDialog = new ChangeDateDialog(
                PersonalDetailsAcitivity.this,true);
            //TODO
        String birthdayString = tv_birthday.getText().toString();
        int yearInt = 0,monthInt = 0,dayInt = 0;
        String year = null,month=null,day=null;
        if(birthdayString!=null){
            int y =  birthdayString.indexOf("年");
            int m =  birthdayString.indexOf("月");
            year = birthdayString.substring(0,y);
            month = birthdayString.substring(y+1,m);
            day = birthdayString.substring(m+1,birthdayString.length()-1);
            Log.i(TAG,"年月日"+year+" "+month + " " +day);
            yearInt = Integer.valueOf(year);
            monthInt = Integer.valueOf(month);
            dayInt = Integer.valueOf(day);
            Log.i(TAG,"年月日"+yearInt+" "+monthInt + " " +dayInt);
        }
        mChangeDateDialog.setDate(yearInt,monthInt,dayInt);
        mChangeDateDialog.show();
        mChangeDateDialog.setBirthdayListener(new ChangeDateDialog.OnBirthListener() {

            @Override
            public void onClick(String year, String month, String day) {
                // TODO Auto-generated method stub
                /*Toast.makeText(PersonalDetailsAcitivity.this,
                        year + "-" + month + "-" + day,
                        Toast.LENGTH_LONG).show();*/
                tv_birthday.setText(year+"年"+month+"月"+day+"日");
            }
        });
    }
    public void showPopHeightSelect(){
        ArrayList<String> list = new ArrayList<>();
        for(int i=1;i<221;i++){
            list.add(i+"");
        }
        ChooseHeightDialog dialog = new ChooseHeightDialog(this,list,tv_height.getText().toString(),"请选择身高");
        dialog.show();
        dialog.setBirthdayListener(new ChooseHeightDialog.OnBirthListener() {
            @Override
            public void onClick(String year) {
               /* Toast.makeText(PersonalDetailsAcitivity.this,
                        year ,Toast.LENGTH_LONG).show();*/
                tv_height.setText(year);
            }
        });
    }

    public void showPopWeightSelect(){
        ArrayList<String> list = new ArrayList<>();
        for(int i=10;i<151;i++){
            list.add(i+"");
        }
        ChooseHeightDialog dialog = new ChooseHeightDialog(this,list,tv_weight.getText().toString(),"请选择体重");
        dialog.show();
        dialog.setBirthdayListener(new ChooseHeightDialog.OnBirthListener() {
            @Override
            public void onClick(String year) {
            /*    Toast.makeText(PersonalDetailsAcitivity.this,
                        year ,Toast.LENGTH_LONG).show();*/
                tv_weight.setText(year);
            }
        });
    }

    public void showPopCarSelect(){
        ArrayList<String> list = new ArrayList<>();
        list.add("脑力劳动者");
        list.add("体力劳动者");
        list.add("运动员");
        ChooseHeightDialog dialog = new ChooseHeightDialog(this,list,tv_carrer.getText().toString(),"请选择职业");
        dialog.show();
        dialog.setBirthdayListener(new ChooseHeightDialog.OnBirthListener() {
            @Override
            public void onClick(String year) {
             /*   Toast.makeText(PersonalDetailsAcitivity.this,
                        year ,Toast.LENGTH_LONG).show();*/
                tv_carrer.setText(year);
            }
        });
    }

    public void showPopSexSelect(){
        ArrayList<String> list = new ArrayList<>();
        list.add("男");
        list.add("女");
        ChooseHeightDialog dialog = new ChooseHeightDialog(this,list,tv_sex.getText().toString(),"请选择性别");
        dialog.show();
        dialog.setBirthdayListener(new ChooseHeightDialog.OnBirthListener() {
            @Override
            public void onClick(String year) {
             /*   Toast.makeText(PersonalDetailsAcitivity.this,
                        year ,Toast.LENGTH_LONG).show();*/
                tv_sex.setText(year);
            }
        });
    }
    File picture;
    // File picture2;
    public Bitmap photo;
    private String picPath;// 上传图片路径

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            // 从相机
            // startPhotoZoom(data.getData());
            // 设置文件保存路径这里放在跟目录下
            if (resultCode == 0) {
                return;
            }
            // picture = new File(Environment.getExternalStorageDirectory()
            // + "/temp.jpg");
            // String
            // s=Utils.getCacheDirectory(FindActivity.this)+System.currentTimeMillis()+".jpg";
            // picture = new File();
            picture = new File(
                    Utility.getCacheDirectory(PersonalDetailsAcitivity.this)
                            + "/temp.jpg");
            // picPath = picture.getAbsolutePath();
            startPhotoZoom(Uri.fromFile(picture));

        } else if (requestCode == 2) {
            // 从相册
            if (data != null && data.getData()!=null) {
                startPhotoZoom(data.getData());

          /*      String[] proj = { MediaStore.Images.Media.DATA };

                // 好像是android多媒体数据库的封装接口，具体的看Android文档
                Cursor cursor = managedQuery(data.getData(), proj, null, null,
                        null);
                // 按我个人理解 这个是获得用户选择的图片的索引值
                int column_index = cursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                // 将光标移至开头 ，这个很重要，不小心很容易引起越界
                cursor.moveToFirst();
                // 最后根据索引值获取图片路径
                picPath = cursor.getString(column_index);*/

            }
        } else if (requestCode == 3) {// 裁剪
            if (data == null) {
                return;
            }

            Bundle extras = data.getExtras();
            if (extras != null) {

                photo = extras.getParcelable("data");
                // File picFile = new
                // File(Environment.getExternalStorageDirectory()
                // + "/temp.jpg");
                // picPath= picFile.getAbsolutePath();
                picPath = saveMyBitmap(System.currentTimeMillis() + "", photo);
                // iv_user_photo.setBackgroundDrawable(new
                // BitmapDrawable(photo));

                Log.i("裁减后的图片路径", "picPath=" + picPath + ";" + photo.getWidth()
                        + "," + photo.getHeight());
                /**
                 * 上传图片
                 */
                Common_dialog.startWaitingDialog(this, "上传中...");
                new Thread(new Runnable() {

                    @Override
                    public void run() {
//						BasicNameValuePair userparam = new BasicNameValuePair(
//								"username", userBean.getUsername());
//						BasicNameValuePair tokenparam = new BasicNameValuePair(
//								"token", PreferencesUtils.getString(
//										PersonalDetailsAcitivity.this,
//										"userToken"));
//						BasicNameValuePair fileparam = new BasicNameValuePair(
//								"", picPath);
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        // params.add(userparam);
                        // params.add(tokenparam);
                        // params.add(fileparam);
                        HashMap<String, Object> map = (HashMap<String, Object>) UploadFile.postFile(
                                params,
                                picPath,
                                Base_Url.Url_Base
                                        + Base_Url.upLoadphoto_Url
                                        + "?username="
                                        + MyApplication.getInstance().getCurrentUserName()
                                        + "&token="
                                        + MyApplication.getInstance().getCurentToken());

                        Message msg = Message.obtain();
                        Log.i("uploadPic",(String)map.get("msg"));
                        if ((Boolean) map.get("isSuccess")) {
                            // handler.sendEmptyMessage(0);
                            Log.i("===图片上传结果", map.get("msg") + "");
                            JSONObject object=null;
                            try {
                                object = new JSONObject(map.get(
                                        "msg").toString());

                                String photoUrl = object.getString("data");
                                // String statue = object.getString("success");
                                Log.i("=====photoUrl=====", photoUrl);

                                msg.obj = photoUrl;
                                msg.what = 0;
                                handler.sendMessage(msg);
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                        } else {

                            msg.what = 1;
                            handler.sendMessage(msg);
                        }

                    }
                }).start();

            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 图片裁剪
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 100);
        intent.putExtra("outputY", 100);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    public String saveMyBitmap(String bitName, Bitmap mBitmap) {
        File f = new File(
                Utility.getCacheDirectory(PersonalDetailsAcitivity.this)
                        + bitName + ".png");
        try {
            f.createNewFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.e("===", "在保存图片时出错：" + e.toString());
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f.getAbsolutePath();
    }


    /**
     * update userinfo
     */
    private void updateUserINfo() {
        if (NetTool.isNetwork(context, true)) {
            try {

                //构造请求json对象
                JSONObject jsonObject = new JSONObject();
                final JSONObject dataObject = new JSONObject();
                JSONObject questionObject = new JSONObject();

                JSONObject factor_hypertensionJso = new JSONObject();

                JSONObject factor_strokeJso = new JSONObject();

                JSONObject factor_chdJso = new JSONObject();

                JSONObject factor_diabeteJso = new JSONObject();

                JSONObject factor_obsityJso = new JSONObject();

                JSONObject factor_cancerJso = new JSONObject();

                jsonObject.put("apikey", myApp.apikey);//所有接口必填
                jsonObject.put("username", myApp.getCurrentUserName());
                jsonObject.put("token", myApp.getCurentToken());
                dataObject.put("sex", tv_sex.getText().toString());

                dataObject.put("career", tv_carrer.getText().toString());
                dataObject.put("height", tv_height.getText().toString());
                dataObject.put("weight", tv_weight.getText().toString());
                dataObject.put("age", tv_birthday.getText().toString());


                questionObject.put("familyHistoryDieases", builder_familyHistoryDiseases.toString());
                questionObject.put("personalHistoryDieases", builder_personalHistoryDiseases.toString());
                questionObject.put("personalNowDieases", builder_personalNowDiseases.toString());

                factor_hypertensionJso.put("exsessiveDrinking", getselectValueRadioBtn(rg_highBP_1));
                factor_hypertensionJso.put("smoke", getselectValueRadioBtn(rg_highBP_2));
                factor_hypertensionJso.put("chronicStress", getselectValueRadioBtn(rg_highBP_3));
                factor_hypertensionJso.put("familyHistoryHypertension", getselectValueRadioBtn(rg_highBP_4));
                factor_hypertensionJso.put("badEatingHabits", getselectValueRadioBtn(rg_highBP_5));
                factor_hypertensionJso.put("largeNoise", getselectValueRadioBtn(rg_highBP_6));
                factor_hypertensionJso.put("astriction", getselectValueRadioBtn(rg_highBP_7));
                factor_hypertensionJso.put("regular_use_vasopressors", getselectValueRadioBtn(rg_highBP_8));
                factor_hypertensionJso.put("educationalLevel", getselectValueRadioBtn(rg_highBP_9));

                factor_strokeJso.put("lowBirthWeight", getselectValueRadioBtn(rg_stroke_1));
                factor_strokeJso.put("dyslipidemia", getselectValueRadioBtn(rg_stroke_2));
                factor_strokeJso.put("lackOfPhysicalExercise", getselectValueRadioBtn(rg_stroke_3));
                factor_strokeJso.put("oftenExcessiveDrinking", getselectValueRadioBtn(rg_stroke_4));
                factor_strokeJso.put("mentalStress", getselectValueRadioBtn(rg_stroke_5));
                factor_strokeJso.put("abuseOfDrugs", getselectValueRadioBtn(rg_stroke_6));
                factor_strokeJso.put("sleepApnea", getselectValueRadioBtn(rg_stroke_7));
                factor_strokeJso.put("migraines", getselectValueRadioBtn(rg_stroke_8));
                factor_strokeJso.put("metabolicSyndrome", getselectValueRadioBtn(rg_stroke_9));
                factor_strokeJso.put("highHomocysteineLevels", getselectValueRadioBtn(rg_stroke_10));


                factor_chdJso.put("smoking", getselectValueRadioBtn(rg_chd_1));
                factor_chdJso.put("exerciseRegularly", getselectValueRadioBtn(rg_chd_2));
                factor_chdJso.put("earlyCHD", getselectValueRadioBtn(rg_chd_3));
                factor_chdJso.put("highLDLC", getselectValueRadioBtn(rg_chd_4));

                if (edt_diabete_waise != null && !TextUtils.isEmpty(edt_diabete_waise.getText().toString())) {
                    factor_diabeteJso.put("waistSize", edt_diabete_waise.getText().toString());
                }

                factor_diabeteJso.put("wayOfLife", getselectValueRadioBtn(rg_diabete_2));
                factor_diabeteJso.put("sugarRegulationDamaged", getselectValueRadioBtn(rg_diabete_3));
                factor_diabeteJso.put("acceptTreatmentOfBloodLipid", getselectValueRadioBtn(rg_diabete_4));
                factor_diabeteJso.put("acceptTreatmentOfPressure", getselectValueRadioBtn(rg_diabete_5));
                factor_diabeteJso.put("sclerosisOfArterialCongeeCardiovascularDiseas", getselectValueRadioBtn(rg_diabete_6));
                factor_diabeteJso.put("historyOfSexSteroidDiabetes", getselectValueRadioBtn(rg_diabete_7));
                factor_diabeteJso.put("pcos", getselectValueRadioBtn(rg_diabete_8));
                factor_diabeteJso.put("giantBaby", getselectValueRadioBtn(rg_diabete_9));
                factor_diabeteJso.put("gdm", getselectValueRadioBtn(rg_diabete_10));
                factor_diabeteJso.put("longtermAntipsychoticDrugs", getselectValueRadioBtn(rg_diabete_11));
                factor_diabeteJso.put("parentType2diabetes", getselectValueRadioBtn(rg_diabete_12));


                factor_obsityJso.put("familyObesity", getselectValueRadioBtn(rg_obsity_1));
                factor_obsityJso.put("goodEatingHabits", getselectValueRadioBtn(rg_obsity_2));
                factor_obsityJso.put("exerciseRegularly", getselectValueRadioBtn(rg_obsity_3));
                factor_obsityJso.put("offenSleepShort", getselectValueRadioBtn(rg_obsity_4));
                factor_obsityJso.put("endocrineDisorders", getselectValueRadioBtn(rg_obsity_5));
                factor_obsityJso.put("immunityStrong", getselectValueRadioBtn(rg_obsity_6));
                factor_obsityJso.put("oEatFastFood", getselectValueRadioBtn(rg_obsity_7));
                factor_obsityJso.put("birthWeightOverweight", getselectValueRadioBtn(rg_obsity_8));
                factor_obsityJso.put("feelWellEatHCAL", getselectValueRadioBtn(rg_obsity_9));
                factor_obsityJso.put("educationDegree", getselectValueRadioBtn(rg_obsity_10));
                factor_obsityJso.put("homeAdr", getselectValueRadioBtn(rg_obsity_11));


                factor_cancerJso.put("goodEatingHabits", getselectValueRadioBtn(rg_cancer_1));
                factor_cancerJso.put("excessiveDrinking", getselectValueRadioBtn(rg_cancer_2));
                factor_cancerJso.put("smoking", getselectValueRadioBtn(rg_cancer_3));
                factor_cancerJso.put("exerciseRegularly", getselectValueRadioBtn(rg_cancer_4));
                factor_cancerJso.put("airPollution", getselectValueRadioBtn(rg_cancer_5));
                factor_cancerJso.put("immunityStrong", getselectValueRadioBtn(rg_cancer_6));
                factor_cancerJso.put("hugeLifePressure", getselectValueRadioBtn(rg_cancer_7));
                factor_cancerJso.put("sensitive", getselectValueRadioBtn(rg_cancer_8));

                questionObject.put("fator_hypertension", factor_hypertensionJso);
                questionObject.put("fator_stroke", factor_strokeJso);
                questionObject.put("fator_CHD", factor_chdJso);
                questionObject.put("fator_diabete", factor_diabeteJso);
                questionObject.put("fator_obsity", factor_obsityJso);
                questionObject.put("cancer", factor_cancerJso);
                dataObject.put("questionnaire", questionObject);

                jsonObject.put("data", dataObject.toString());
                Log.d(TAG, Base_Url.Url_Base + Base_Url.reviseUserInfo_Url);
                Log.d(TAG, "json params " + jsonObject.toString());
                HttpTools.post(this, Base_Url.Url_Base + Base_Url.reviseUserInfo_Url, jsonObject, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String result = new String(responseBody);
                        Log.d(TAG, "update result is " + result);
                        try {
                            JSONObject rstObj = new JSONObject(result);
                            String msg = rstObj.optString("message");
                            if(msg.equals("success")){
                                Toast.makeText(context,"修改信息成功",Toast.LENGTH_SHORT).show();
                                int age = TimeStampUtil.getAgeByBirth(tv_birthday.getText().toString());
                                PreferencesUtils.putString(context, "birth", tv_birthday.getText().toString());
                                PreferencesUtils.putString(context, "age", age + "");
                                PreferencesUtils.putString(context, "sex", tv_sex.getText().toString());
                                PreferencesUtils.putString(context,"height",tv_height.getText().toString());
                                PreferencesUtils.putString(context,"weight",tv_weight.getText().toString());

                                Utility.startActivity(context,MainActivity.class);
                                finish();

                            } else {
                                Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        OemLog.log(TAG, "服务器请求失败");
                        Toast.makeText(getContext(), "服务器请求失败", Toast.LENGTH_LONG).show();

                    }
                });

            } catch (JSONException e) {
                OemLog.log(TAG, "JSON EXCEPTION !1");
                e.printStackTrace();
            }
        }
    }



   private String getselectValueRadioBtn(RadioGroup radioGroup){
       if(radioGroup==null){
           return null;

       }
       int id = radioGroup.getCheckedRadioButtonId();
       Log.i(TAG,"radiogroup checed ID is "+ id);
       if(id==-1){
           return null;
       }
        RadioButton radioButton = (RadioButton)findViewById(id);
        String value = radioButton.getText().toString();
       Log.i(TAG,"radiogroup checed value is "+ value);
        return  value;
    }

    private void setCheckedRadiobutton(){


                //高血压致病因素
                HbloodPresureFator fator_hypertension = questionBean.getFator_hypertension();
                if(fator_hypertension!=null){
                    if(viewStub_hypertension==null){
                        viewStub_hypertension = (ViewStub) flPersonDetail.findViewById(R.id.viewstub_hypertension);
                        viewStub_hypertension.inflate();
                    }
                    viewStub_hypertension.setVisibility(View.GONE);
                    findViewById_highbp();

                    RadioGroup[] radiogroups = {rg_highBP_1,rg_highBP_2,rg_highBP_3,
                            rg_highBP_4,rg_highBP_5,rg_highBP_6,
                            rg_highBP_7,rg_highBP_8,rg_highBP_9
                    };

                    fator_hypertension.setRadioGroups(radiogroups);

                }

                //脑卒中致病因素
                StrokeFator fator_stroke = questionBean.getFator_stroke();
                if(fator_stroke!=null){
                    if(viewStub_stroke==null){
                        viewStub_stroke = (ViewStub) flPersonDetail.findViewById(R.id.viewstub_stroke);
                        viewStub_stroke.inflate();
                    }
                    viewStub_stroke.setVisibility(View.GONE);
                    findViewById_stroke();

                    RadioGroup[] radiogroups = {rg_stroke_1,rg_stroke_2,rg_stroke_3,
                            rg_stroke_4,rg_stroke_5,rg_stroke_6,
                            rg_stroke_7,rg_stroke_8,rg_stroke_9,rg_stroke_10
                    };

                    fator_stroke.setRadioGroups(radiogroups);

                }

        //冠心病致病因素
        CHDFator fator_chd = questionBean.getFator_CHD();
        Log.i(TAG,"chd is" +fator_chd);
        if(fator_chd!=null){
            if(viewStub_chd==null){
                viewStub_chd = (ViewStub) flPersonDetail.findViewById(R.id.viewstub_chd);
                viewStub_chd.inflate();
            }
            viewStub_chd.setVisibility(View.GONE);
            findViewById_chd();

            RadioGroup[] radiogroups = {rg_chd_1,rg_chd_2,rg_chd_3,
                    rg_chd_4
            };

            fator_chd.setRadioGroups(radiogroups);

        }

        //糖尿病致病因素
        DiabeteFactor diabeteFactor = questionBean.getFator_diabete();
        if(diabeteFactor!=null){
            if(viewStub_diabetic==null){
                viewStub_diabetic = (ViewStub) flPersonDetail.findViewById(R.id.viewstub_diabetic);
                viewStub_diabetic.inflate();
            }
            viewStub_diabetic.setVisibility(View.GONE);
            findViewById_diabete();
            Log.i(TAG,"WAISTSIZE IS " +diabeteFactor.getWaistSize());
            edt_diabete_waise.setText(diabeteFactor.getWaistSize());
            RadioGroup[] radiogroups = {rg_diabete_2,rg_diabete_3,rg_diabete_4,rg_diabete_5,
                    rg_diabete_6,rg_diabete_7,rg_diabete_8,rg_diabete_9,
                    rg_diabete_10,rg_diabete_11,rg_diabete_12
            };
            diabeteFactor.setRadioGroups(radiogroups);

        }

        ObesityFator factor_obesity = questionBean.getFator_obsity();
        if(factor_obesity!=null){
            if(viewStub_obesity==null){
                viewStub_obesity = (ViewStub) flPersonDetail.findViewById(R.id.viewstub_obesity);
                viewStub_obesity.inflate();
            }
            viewStub_obesity.setVisibility(View.GONE);

            findViewById_obesity();

            RadioGroup[] radiogroups = {rg_obsity_1,rg_obsity_2,rg_obsity_3,rg_obsity_4,rg_obsity_5,
                    rg_obsity_6,rg_obsity_7,rg_obsity_8,rg_obsity_9,
                    rg_obsity_10,rg_obsity_11
            };

            factor_obesity.setRadioGroups(radiogroups);

        }


        CancerFator factor_cancer = questionBean.getCancer();
        if(factor_cancer!=null){
            if(viewStub_therioma==null){
                viewStub_therioma = (ViewStub) flPersonDetail.findViewById(R.id.viewstub_therioma);
                viewStub_therioma.inflate();
            }
            viewStub_therioma.setVisibility(View.GONE);

            findViewById_cancer();
            RadioGroup[] radiogroups = {rg_cancer_1,rg_cancer_2,rg_cancer_3,rg_cancer_4,rg_cancer_5,
                    rg_cancer_6,rg_cancer_7,rg_cancer_8
            };

            factor_cancer.setRadioGroups(radiogroups);

        }





    }

    private void findViewById_chd() {
        rg_chd_1 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_chd_1);

        rg_chd_2 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_chd_2);

        rg_chd_3 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_chd_3);

        rg_chd_4 = (RadioGroup) flPersonDetail.findViewById(R.id.rg_chd_4);
    }

}









