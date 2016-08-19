package com.capitalbio.oemv2.myapplication.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.Bean.LoginUser;
import com.capitalbio.oemv2.myapplication.Const.Base_Url;
import com.capitalbio.oemv2.myapplication.NetUtils.HttpTools;
import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.Tools.MDTools;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.Base64;
import com.sina.weibo.sdk.api.share.Base;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.ex.DbException;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class AddAccountActivity extends BaseActivity implements View.OnClickListener {


    //back
    private RelativeLayout rlback;

    //sure
    private RelativeLayout rlsure;

    private EditText etaccount;

    private EditText etpassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);

        rlback = (RelativeLayout) this.findViewById(R.id.rl_addaccount_back);
        rlback.setOnClickListener(this);

        rlsure = (RelativeLayout) this.findViewById(R.id.rl_addaccount_sure);
        rlsure.setOnClickListener(this);

        etaccount = (EditText) this.findViewById(R.id.et_addaccount_account);
        etpassword = (EditText) this.findViewById(R.id.et_addaccount_password);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_addaccount_back:
                setResult(RSC_FAILURE);
                this.finish();
                break;
            case R.id.rl_addaccount_sure:

                String username = etaccount.getText().toString();
                String pwd = etpassword.getText().toString();

                if (username == null || username.equals("")) {
                    Toast.makeText(this, "请输入账号", Toast.LENGTH_LONG).show();
                    return;
                }
                if (pwd == null || pwd.equals("")) {
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_LONG).show();
                    return;
                }


                //检查是否已经添加
                try {
                    List<LoginUser> loginUsers = MyApplication.getDB().findAll(LoginUser.class);

                    for (int i = 0; i < loginUsers.size(); i++) {
                        LoginUser loginUser = loginUsers.get(i);
                        String un = loginUser.getUsername();
                        if (un.equals(username)) {
                            Toast.makeText(this, "该账号已添加", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }

                } catch (DbException e) {
                    e.printStackTrace();
                }

                // 调用接口判断账户及密码的合法性有效性
                verifyAccount(username, pwd);


                break;
        }

    }


    public static final int RSC_SUCCESS = 1;//添加账号成功的结果码

    public static final int RSC_FAILURE = 0;//添加账号失败的结果码


    private void verifyAccount(final String username, final String pwd) {

        JSONObject reqbody = new JSONObject();
        try {
            reqbody.put("apikey", MyApplication.getInstance().apikey);
            reqbody.put("username", username);
            reqbody.put("token", MDTools.MD5(MyApplication.getInstance().apikey + MDTools.MD5(pwd) + MyApplication.getInstance().getIMEI()));

            JSONObject reqdata = new JSONObject();
            reqdata.put("mobileId", MyApplication.getInstance().getIMEI());
            reqbody.put("data", reqdata.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HttpTools.post(this, Base_Url.Url_Base + Base_Url.verifyaccount, reqbody, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String repbody = new String(responseBody);
                try {
                    JSONObject repjbody = new JSONObject(repbody);
                    String message = repjbody.getString("message");
                    Log.i("info", "=================" + message);
                    if (message.equals("success")) {

                        //String headImage  = repjbody.getJSONObject("data").getString("headImage");

                        String repdata = repjbody.getString("data");
                        JSONObject repjdata = new JSONObject(repdata);


                        //账户信息插入本地数据库

                        LoginUser loginUser = new LoginUser();
                        loginUser.setUsername(username);
                        loginUser.setPassword(pwd);
                        if (repjdata.has("headImage")) {
                            String rephead = repjdata.getString("headImage");
                            loginUser.setHeadImage(rephead);
                            loginUser.setHeadPic(Base64.decode(rephead, Base64.DEFAULT));

                        }

                        try {
                            MyApplication.getDB().save(loginUser);
                            setResult(RSC_SUCCESS);
                            //Toast.makeText(AddAccountActivity.this, message, Toast.LENGTH_LONG).show();
                            Toast.makeText(AddAccountActivity.this, "添加成功", Toast.LENGTH_LONG).show();
                            AddAccountActivity.this.finish();
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(AddAccountActivity.this, message, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //Log.i("info", "--------onSuccess---------" + repbody);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                String repbody = new String(responseBody);
                JSONObject repjbody = null;
                String repmessage = null;
                try {
                    repjbody = new JSONObject(repbody);
                    repmessage = repjbody.getString("message");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Toast.makeText(AddAccountActivity.this, repmessage, Toast.LENGTH_LONG).show();

                //Log.i("info", "---------onFailure--------" + repbody);

            }
        });

    }

}
