package com.capitalbio.oemv2.myapplication.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.Bean.LoginUser;
import com.capitalbio.oemv2.myapplication.Const.Base_Url;
import com.capitalbio.oemv2.myapplication.NetUtils.HttpTools;
import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.Tools.MDTools;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;
import com.capitalbio.oemv2.myapplication.Utils.PreferencesUtils;
import com.capitalbio.oemv2.myapplication.Utils.TimeStampUtil;
import com.capitalbio.oemv2.myapplication.Utils.Utility;
import com.capitalbio.oemv2.myapplication.adapter.AccountManagementListAdapter;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class AccountManagementActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {


    private RelativeLayout rlback;

    private ListView lvaccountlist;



    private List<LoginUser> loginUsers;

    private AccountManagementListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_management);

        loginUsers = new ArrayList<>();

        try {
            loginUsers = MyApplication.getDB().findAll(LoginUser.class);
        } catch (DbException e) {
            e.printStackTrace();
        }

        adapter = new AccountManagementListAdapter(loginUsers,this);

        rlback = (RelativeLayout) this.findViewById(R.id.rl_accountmanagement_back);
        rlback.setOnClickListener(this);

        lvaccountlist = (ListView) this.findViewById(R.id.lv_accountmanagement);



        View footer = View.inflate(this,R.layout.adapter_accountmanagement_accountlist_footer,null);
        LinearLayout lladdaccount = (LinearLayout) footer.findViewById(R.id.ll_accountmanagement_addaccount);
        lladdaccount.setOnClickListener(this);
        LinearLayout llmodifypwd = (LinearLayout) footer.findViewById(R.id.ll_accountmanagement_modifypwd);
        llmodifypwd.setOnClickListener(this);
        lvaccountlist.addFooterView(footer);

        lvaccountlist.setAdapter(adapter);
        lvaccountlist.setOnItemClickListener(this);
        lvaccountlist.setOnItemLongClickListener(this);




    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.rl_accountmanagement_back:



                this.finish();
                break;

            case R.id.ll_accountmanagement_addaccount:

               Intent addaccount = new Intent();
                addaccount.setClass(this,AddAccountActivity.class);
                startActivityForResult(addaccount,RQC_ADDACCOUNT);
                break;
            case R.id.ll_accountmanagement_modifypwd:
                Intent modifypwd = new Intent();
                modifypwd.setClass(this,ModifyPasswordActivity.class);
                startActivityForResult(modifypwd,RQC_MODIFYPWD);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



        if(position<loginUsers.size()){

            final LoginUser loginUser = loginUsers.get(position);

            String islogin = loginUser.isLogin();



            if(islogin==null||!islogin.equals("true")){


                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("确定切换当前账号");
                builder.setNegativeButton("取消",null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                     // Log.i("info","======================"+Thread.currentThread().getName());
                        login(loginUser.getUsername(),loginUser.getPassword());
                    }
                });
                builder.create().show();

            }





        }




    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        //Log.i("info","-------------------长按");

        if(position<loginUsers.size()){

            final LoginUser loginUser = loginUsers.get(position);
            String islogin = loginUser.isLogin();

            if(islogin!=null&&islogin.equals("true")){
                Toast.makeText(this,"当前账号无法删除",Toast.LENGTH_LONG).show();
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("确定删除该账号");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            //MyApplication.getDB().delete(loginUsers.get(position));

                            WhereBuilder whereBuilder = WhereBuilder.b("username","=",loginUser.getUsername());

                            MyApplication.getDB().delete(LoginUser.class,whereBuilder);

                            loginUsers = MyApplication.getDB().findAll(LoginUser.class);

                            adapter = new AccountManagementListAdapter(loginUsers,AccountManagementActivity.this);

                            lvaccountlist.setAdapter(adapter);

                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
            }





        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

      switch (requestCode)
      {
          case RQC_ADDACCOUNT:
              switch (resultCode)
              {
                  case AddAccountActivity.RSC_SUCCESS:

                      //Log.i("info","----------------插入成功");

                      try {
                         loginUsers = MyApplication.getDB().findAll(LoginUser.class);

                          adapter = new AccountManagementListAdapter(loginUsers,this);
                          lvaccountlist.setAdapter(adapter);

                      } catch (DbException e) {
                          e.printStackTrace();
                      }

                      break;
                  case AddAccountActivity.RSC_FAILURE:

                      break;
              }

              break;
          case RQC_MODIFYPWD:

              break;
      }

    }


    private static final int RQC_ADDACCOUNT = 0;

    private static final int RQC_MODIFYPWD = 1;

    private ProgressDialog progressDialog = null;

    public void login(final String username, final String pwd) {
        if(progressDialog==null){
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在切换账号......");
            progressDialog.setTitle("账号切换");
        }

        try {
            OemLog.log(TAG,"datelogin... login");

            //构造请求json对象
            JSONObject jsonObject = new JSONObject();
            JSONObject dataObj = new JSONObject();
            jsonObject.put("apikey", MyApplication.getInstance().apikey);
            jsonObject.put("username",username);
            String password_ = MDTools.MD5(pwd);
            String mobileId = MyApplication.getInstance().getIMEI();
            OemLog.log(TAG,"imei is" + mobileId);
            final String token = MDTools.MD5(MyApplication.getInstance().apikey + password_+mobileId);
            jsonObject.put("token", token);
            dataObj.put("mobileId",MyApplication.getInstance().getIMEI());
            jsonObject.put("data",dataObj.toString());
            OemLog.log(TAG,token);

            //
            HttpTools.post(this, Base_Url.Url_Base + Base_Url.LoginKey_Url, jsonObject, new AsyncHttpResponseHandler() {
                @Override
                public void onStart() {
                    progressDialog.show();
                    super.onStart();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    String s = new String(responseBody);
                    Log.i(TAG, "result if sucess is" + s);
                    try {
                        JSONObject jo = new JSONObject(s);
                        int code = jo.optInt("code");

                        if (code == 0) {
                            //登陆成功
                            Toast.makeText(context, "切换成功", Toast.LENGTH_SHORT).show();
                            PreferencesUtils.putBoolean(MyApplication.getInstance(), "isLogin", true);
                            String data = jo.getString("data");
                            Log.i("info",data);
                            if (data.trim() != null) {
                                JSONObject dataObj = new JSONObject(data);
                                LoginUser user = new LoginUser();
                                user.setToken(token);
                                user.setLoginTime_(System.currentTimeMillis());
                                user.setLoginTime(TimeStampUtil.currTime2(0));
                                user.setIsLogin("true");
                                user.setId(dataObj.optString("id"));
                                user.setPassword(pwd);

                                user.setIdcard(dataObj.optString("idcard"));
                                user.setAge(dataObj.optString("age"));
                                user.setCareer(dataObj.optString("career"));
                                user.setCtime(dataObj.optString("ctime"));
                                user.setHeight(dataObj.optString("height"));
                                user.setWeight(dataObj.optString("weight"));
                                user.setNickname(dataObj.optString("nickname"));
                                user.setSex(dataObj.optString("sex"));
                                user.setMobile(dataObj.optString("mobile"));

                                String headData = dataObj.optString("headImage");
                                if(headData!=null){
                                    byte[] headBytes = Base64.decode(headData, Base64.DEFAULT);
                                    user.setHeadPic(headBytes);
                                }else{
                                    user.setHeadPic(null);

                                }

                                user.setUsername(dataObj.optString("username"));
                                user.setLoginName(username);
                                PreferencesUtils.putBoolean(context, "isLogin", true);


                                //将之前的当前登录账号标记制空
                               List<LoginUser> loginUsers =  MyApplication.getDB().findAll(LoginUser.class);
                                for(int i=0;i<loginUsers.size();i++){
                                    LoginUser loginUser = loginUsers.get(i);
                                    String islogin = loginUser.isLogin();
                                    if(islogin!=null&&islogin.equals("true")){
                                        loginUser.setIsLogin("false");
                                        MyApplication.getDB().saveOrUpdate(loginUser);
                                    }
                                }

                                //将之前本地已存在的当前账号删掉
                                WhereBuilder whereBuilder =WhereBuilder.b("username","=",username);
                                MyApplication.getDB().delete(LoginUser.class,whereBuilder);

                                //保存当前登录账号
                                MyApplication.getInstance().getDB().saveOrUpdate(user);


                                MyApplication.getInstance().setCurrentUser(user);
                                //  Log.i(TAG, "插入头像后" + MyApplication.getInstance().getDB().findFirst(LoginUser.class).getHeadPic());
                                PreferencesUtils.putString(context, "username", user.getUsername());
                                String birth = dataObj.optString("age");
                                String height = dataObj.optString("height");
                                String weight = dataObj.optString("weight");
                                int age = TimeStampUtil.getAgeByBirth(birth);
                                OemLog.log("loginBirth", "birth" + birth + "  age" + age);

                                PreferencesUtils.putString(context, "mobile", user.getMobile());
                                PreferencesUtils.putString(context, "token", token);

                                PreferencesUtils.putString(context,"loginNum",username);
                                PreferencesUtils.putString(context, "loginPwd", pwd);

                                OemLog.log(TAG, token);

                                //   Log.d("TAG", "loginuser is " + user.toString());
                                if(birth!=""&&birth!=null){
                                    PreferencesUtils.putBoolean(context,"personInfo",true);
                                    PreferencesUtils.putString(context, "sex", dataObj.optString("sex"));
                                    PreferencesUtils.putString(context, "birth", birth);
                                    PreferencesUtils.putString(context, "age", age+"");
                                    PreferencesUtils.putString(context, "height", height);
                                    PreferencesUtils.putString(context,"weight",weight);


                                    //Log.i("info","--------------------===================修改当前账号结束"+Thread.currentThread().getName());

                                    AccountManagementActivity.this.loginUsers = MyApplication.getDB().findAll(LoginUser.class);
                                    adapter = new AccountManagementListAdapter(AccountManagementActivity.this.loginUsers,AccountManagementActivity.this);
                                    lvaccountlist.setAdapter(adapter);



                                }else{
                                    Utility.startActivity(context, PersonalDetailsAcitivity.class);
                                }

                            }


                        } else if (code == 13) {
                            Toast.makeText(context, "密码不正确！", Toast.LENGTH_SHORT).show();


                        } else if (code == 10) {
                            Toast.makeText(context, getResources().getString(R.string.no_user), Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        //Toast.makeText(context,"json  解析异常",Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    } catch (DbException e) {
                        //Toast.makeText(context,"db操作异常",Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                        OemLog.log(TAG, e.getMessage());
                    }

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.i(TAG, "result if faliure is" + " 服务器异常");
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(context, "服务器异常！", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancel() {
                    super.onCancel();
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


}
