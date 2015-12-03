package com.capitalbio.oemv2.myapplication.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.capitalbio.oemv2.myapplication.R;

public class LoginActivity extends Activity {

    private Button btLogin;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.bt_login:
                    checkUserInfoAndLogin();
                break;
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }


    private void init() {
        btLogin = (Button) findViewById(R.id.bt_login);


        //设置点击监听
        btLogin.setOnClickListener(onClickListener);

    }


    private void checkUserInfoAndLogin() {
        //判断用户的输入信息

        //进入主界面
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
