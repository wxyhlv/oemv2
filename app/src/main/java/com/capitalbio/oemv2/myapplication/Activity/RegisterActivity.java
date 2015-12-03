package com.capitalbio.oemv2.myapplication.Activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.capitalbio.oemv2.myapplication.Const.Base_Url;
import com.capitalbio.oemv2.myapplication.NetUtils.HttpTools;
import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.Tools.MDTools;
import com.capitalbio.oemv2.myapplication.Utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class RegisterActivity extends BaseActivity implements View.OnClickListener{

    public static final String TAG = "RegisterActivity";
    private int editState;

    private LinearLayout rlRegisterView;

    private View.OnFocusChangeListener focusChangeListener;

    //电话号码
    private EditText etPhoneNumber;
    private ImageView ivPhoneNumber;

    //验证码
    private EditText etVeryficationCode;
    private ImageView ivVeryficationCode;

    //用户名
    private EditText etUserName;
    private ImageView ivUserName;

    //密码
    private EditText etPassword;
    private ImageView ivPassword;

    //再次输入密码
    private EditText etRePassword;
    private ImageView ivRePassword;

    //获取验证码
    private Button btGetVeryficationCode;

    //注册
    private Button btRegister;


    //验证码是否输入正确状态
    private boolean isVeryficatioCodeCorrect = true;

    public class EditFocusChangeListener implements View.OnFocusChangeListener {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                Drawable drawable;
                switch (v.getId()) {
                    case R.id.et_phone_number:
                        drawable= getResources().getDrawable(R.drawable.ic_phone_light);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        etPhoneNumber.setCompoundDrawables(drawable, null, null, null);
                        ivPhoneNumber.setBackground(getResources().getDrawable(R.color.blue));
                        break;

                    case R.id.et_veryfication_code:
                        drawable= getResources().getDrawable(R.drawable.ic_veryfication_light);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        etVeryficationCode.setCompoundDrawables(drawable, null, null, null);
                        ivVeryficationCode.setBackgroundResource(R.color.blue);
                        break;

                    case R.id.et_user_name:
                        drawable= getResources().getDrawable(R.drawable.ic_personname_light);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        etUserName.setCompoundDrawables(drawable, null, null, null);
                        ivUserName.setBackgroundResource(R.color.blue);
                        break;

                    case R.id.et_password:
                        drawable= getResources().getDrawable(R.drawable.ic_password_light);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        etPassword.setCompoundDrawables(drawable, null, null, null);
                        ivPassword.setBackgroundResource(R.color.blue);
                        break;

                    case R.id.et_re_password:
                        drawable= getResources().getDrawable(R.drawable.ic_password_light);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        etRePassword.setCompoundDrawables(drawable, null, null, null);
                        ivRePassword.setBackgroundResource(R.color.blue);
                        break;
                }
            }
        }
    }


    @Override
    protected void initChildLayout() {
        super.initChildLayout();
        setNavigateBarBackGround(R.color.blue_light);
        setTvTopTitle(R.string.register);
        setLeftTopIcon(R.drawable.ic_back);

        focusChangeListener = new EditFocusChangeListener();

        //添加自己的view
        rlRegisterView = (LinearLayout) View.inflate(this, R.layout.activity_register, null);
        llBody.addView(rlRegisterView);

        //获取对应view的实例
        etPhoneNumber = (EditText) rlRegisterView.findViewById(R.id.et_phone_number);
        ivPhoneNumber = (ImageView) rlRegisterView.findViewById(R.id.iv_phone_number);

        etVeryficationCode = (EditText) rlRegisterView.findViewById(R.id.et_veryfication_code);
        ivVeryficationCode = (ImageView) rlRegisterView.findViewById(R.id.iv_get_veryfication_code);

        etUserName = (EditText) rlRegisterView.findViewById(R.id.et_user_name);
        ivUserName = (ImageView) rlRegisterView.findViewById(R.id.iv_user_name);

        etPassword = (EditText) rlRegisterView.findViewById(R.id.et_password);
        ivPassword = (ImageView) findViewById(R.id.iv_password);

        etRePassword = (EditText) rlRegisterView.findViewById(R.id.et_re_password);
        ivRePassword = (ImageView) rlRegisterView.findViewById(R.id.iv_re_password);

        btGetVeryficationCode = (Button) rlRegisterView.findViewById(R.id.bt_get_veryfication_code);
        btRegister = (Button) rlRegisterView.findViewById(R.id.bt_register);

        btGetVeryficationCode.setOnClickListener(this);
        btRegister.setOnClickListener(this);

        //EditText焦点监听
        etPhoneNumber.setOnFocusChangeListener(focusChangeListener);
        etVeryficationCode.setOnFocusChangeListener(focusChangeListener);
        etUserName.setOnFocusChangeListener(focusChangeListener);
        etPassword.setOnFocusChangeListener(focusChangeListener);
        etRePassword.setOnFocusChangeListener(focusChangeListener);

    }

    @Override
    public void onClick(View v) {

        Log.d(TAG, "onclick...");

        switch (v.getId()) {
            case R.id.bt_get_veryfication_code:
                checkAndGetVeryficationCode();
                break;

            case R.id.bt_register:
                registerAndAutoLogin();
                break;

            default:break;
        }

    }

    private void getVeryficationCode() {

        Log.d(TAG, "getVeryficationCode...");

        StringBuilder sb = new StringBuilder();
        sb.delete(0, sb.length());
        for (int i = 0; i < 6; i++) {
            Random random = new Random();
            int randomNumber = random.nextInt(10);
            sb.append(randomNumber);
        }

        Log.i("==随机生成的6位验证码", sb.toString() + " apikey is " + myApp.apikey);

        try {
            //构造请求json对象
            JSONObject jsonObject = new JSONObject();
            JSONObject dataObject = new JSONObject();
            JSONObject tmpObject = new JSONObject();

            jsonObject.put("apikey", myApp.apikey);

            tmpObject.put("mobile", etPhoneNumber.getText());
            tmpObject.put("content", sb.toString());
            tmpObject.put("sendType", "registerAndAutoLogin");

            jsonObject.put("data", tmpObject);
            //请求短信验证码
            HttpTools.post(this, Base_Url.Url_Base + Base_Url.sendMessage_Url, jsonObject, asyncResponseHandler);


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void networkCallBack(String result) {
        Log.d(TAG, "network callback...");
    }

    private void applyApikey() throws JSONException {

        String randomstring = MDTools.getRandomString(16);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("appid", "lshd_app");
        jsonObject.put("once", randomstring);
        jsonObject.put("enc", MDTools.MD5(randomstring + "lshd_pk"));

        HttpTools.post(this, Base_Url.Url_Base + Base_Url.ApiKey_Url, jsonObject, asyncResponseHandler);

    }

    private void checkAndGetVeryficationCode() {
        if (Utils.isMobilePhoneNumber(etPhoneNumber.getText().toString())) {
            //getVeryficationCode();
        } else {
            Toast.makeText(this, R.string.veryfication_code_is_not_correct, Toast.LENGTH_SHORT);
        }
    }

    private void registerAndAutoLogin() {
        if (isVeryficatioCodeCorrect) {
            Log.d(TAG, "before registerAndAutoLogin...");

            //检查其他的输入信息
            checkOtherInfo();

            //调用网络接口进行注册

            //注册完成进行登录
            login();

            finish();

        }
    }


    private void checkOtherInfo() {

    }

    private void login() {
        //转到登录页面进行操作
        Intent login = new Intent(this, LoginActivity.class);
        startActivity(login);
    }

}
