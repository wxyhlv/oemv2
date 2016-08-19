package com.capitalbio.oemv2.myapplication.Activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.pc.util.MD5;
import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.Bean.LoginUser;
import com.capitalbio.oemv2.myapplication.Const.Base_Url;
import com.capitalbio.oemv2.myapplication.NetUtils.AsyncResponseHandler;
import com.capitalbio.oemv2.myapplication.NetUtils.HttpTools;
import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.Tools.MDTools;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.ex.DbException;

import cz.msebera.android.httpclient.Header;

public class ModifyPasswordActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout rlback;

    private EditText etoldpwd;

    private EditText etnewpwd;

    private EditText etnewpwdagain;

    private Button btcommit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);

        rlback = (RelativeLayout) this.findViewById(R.id.rl_modifypassword_back);
        rlback.setOnClickListener(this);

        etoldpwd = (EditText) this.findViewById(R.id.et_modifypassword_oldpassword);
        etnewpwd = (EditText) this.findViewById(R.id.et_modifypassword_newpassword);
        etnewpwdagain = (EditText) this.findViewById(R.id.et_modifypassword_newpasswordagain);

        btcommit = (Button) this.findViewById(R.id.bt_modifypassword_commit);
        btcommit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_modifypassword_back:
                this.finish();
                break;
            case R.id.bt_modifypassword_commit:

                String oldpwd = etoldpwd.getText().toString();
                String newpwd = etnewpwd.getText().toString();
                String newpwdag = etnewpwdagain.getText().toString();


                if (oldpwd == null || oldpwd.equals("")) {
                    Toast.makeText(this, "请输入原始密码", Toast.LENGTH_LONG).show();
                    return;
                }

                if (newpwd == null || newpwd.equals("")) {
                    Toast.makeText(this, "请输入新密码", Toast.LENGTH_LONG).show();
                    return;
                }

                if (newpwdag == null || newpwdag.equals("")) {
                    Toast.makeText(this, "请再次输入原始密码", Toast.LENGTH_LONG).show();
                    return;
                }


                if (!newpwd.equals(newpwdag)) {
                    Toast.makeText(this, "两次输入的密码不一致，请确认", Toast.LENGTH_LONG).show();
                    return;
                }


                if (newpwd.length() < 6 || newpwd.length() > 16) {
                    Toast.makeText(this, "请保证密码长度在6-16之间", Toast.LENGTH_LONG).show();
                    return;
                }


                if(!hasNumber(newpwd)){
                    Toast.makeText(this,"请保证密码含有数字",Toast.LENGTH_LONG).show();
                    return;
                }

                if(!hasLetter(newpwd)){
                    Toast.makeText(this,"请保证密码含有字母",Toast.LENGTH_LONG).show();
                    return;
                }






                if (progressDialog == null) {
                    progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("正在修改密码，请稍后");
                    progressDialog.setTitle("修改密码");
                }
                progressDialog.show();

                LoginUser loginUser = MyApplication.getInstance().getCurrentUser();
                modifypwd(loginUser.getUsername(), etoldpwd.getText().toString(), etnewpwd.getText().toString());


                break;

        }
    }


    //判断是否含有数字
    private boolean hasNumber(String s) {

        if (s.contains("0") || s.contains("1") || s.contains("2") || s.contains("3") || s.contains("4") || s.contains("5") || s.contains("6") || s.contains("7") || s.contains("8") || s.contains("9")) {
            return true;
        }

        return false;
    }

    //判断是否含有字母
    private boolean hasLetter(String s) {
        if (s.contains("a") || s.contains("b") || s.contains("c") || s.contains("d") || s.contains("e") || s.contains("f") || s.contains("g") || s.contains("h") || s.contains("i") || s.contains("j") || s.contains("k") || s.contains("l") || s.contains("m") || s.contains("n") || s.contains("o") || s.contains("p") || s.contains("q") || s.contains("r") || s.contains("s") || s.contains("t") || s.contains("u") || s.contains("v") || s.contains("w") || s.contains("x") || s.contains("y") || s.contains("z") || s.contains("A") || s.contains("B") || s.contains("C") || s.contains("D") || s.contains("E") || s.contains("F") || s.contains("G") || s.contains("H") || s.contains("I") || s.contains("J") || s.contains("K") || s.contains("L") || s.contains("M") || s.contains("N") || s.contains("O") || s.contains("P") || s.contains("Q") || s.contains("R") || s.contains("S") || s.contains("T") || s.contains("U") || s.contains("V") || s.contains("W") || s.contains("X") || s.contains("Y") || s.contains("Z")) {
            return true;
        }
        return false;
    }

    //判断是否含有特俗字符
    private boolean hasSpecialChar(String s) {
        if (s.contains("~") || s.contains("!") || s.contains("@") || s.contains("#") || s.contains("$") || s.contains("%") || s.contains("^") || s.contains("&") || s.contains("*") || s.contains("(") || s.contains(")") || s.contains("——") || s.contains("+") || s.contains("-") || s.contains("=") || s.contains("`") || s.contains("{") || s.contains("}") || s.contains("|") || s.contains("【") || s.contains("】") || s.contains("、") || s.contains(":") || s.contains("") || s.contains(";") || s.contains("'") || s.contains("<") || s.contains(">") || s.contains("?") || s.contains(",") || s.contains(".") || s.contains("/")) {
            return true;
        }
        return false;
    }


    private ProgressDialog progressDialog = null;

    private void modifypwd(String username, String oldpwd, final String newpwd) {


        try {
            JSONObject reqbody = new JSONObject();

            reqbody.put("apikey", MyApplication.getInstance().apikey);
            reqbody.put("username", username);


            reqbody.put("token", MyApplication.getInstance().getCurentToken());


            JSONObject reqdata = new JSONObject();
            reqdata.put("newPwd", MDTools.MD5(newpwd));
            reqdata.put("oldPwd", MDTools.MD5(oldpwd));

            reqbody.put("data", reqdata.toString());

            HttpTools.post(this, Base_Url.Url_Base + Base_Url.modifypwd_url, reqbody, new AsyncHttpResponseHandler() {


                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {


                    try {
                        String backbody = new String(responseBody);
                        JSONObject backobject = new JSONObject(backbody);
                        String message = backobject.getString("message");

                        //Log.i("info","-------------"+message);
                        if (message != null && message.equals("success")) {
                            LoginUser loginUser = MyApplication.getInstance().getCurrentUser();
                            loginUser.setPassword(newpwd);
                            MyApplication.getDB().saveOrUpdate(loginUser);
                            dismiss();
                            Toast.makeText(ModifyPasswordActivity.this, message, Toast.LENGTH_LONG).show();
                            ModifyPasswordActivity.this.finish();
                        } else {
                            dismiss();
                            Toast.makeText(ModifyPasswordActivity.this, message, Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        dismiss();
                        Toast.makeText(ModifyPasswordActivity.this, "服务器返回数据异常", Toast.LENGTH_LONG).show();
                    } catch (DbException e) {
                        e.printStackTrace();
                        dismiss();
                        Toast.makeText(ModifyPasswordActivity.this, "修改成功，但本地数据库更新异常", Toast.LENGTH_LONG).show();
                    }


                    //Log.i("info",statusCode+"===============修改密码成功"+new String(responseBody));

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {


                    dismiss();

                    Toast.makeText(ModifyPasswordActivity.this, "onFailure", Toast.LENGTH_LONG).show();

                    //Log.i("info","===============修改密码失败"+new String(responseBody));

                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void dismiss() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
