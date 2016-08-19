package com.capitalbio.oemv2.myapplication.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.capitalbio.oemv2.myapplication.R;

public class AboutUsActivity extends BaseActivity implements View.OnClickListener {

    //返回
    private RelativeLayout rlback;

    //
    private WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        find();
        init();
    }

    private void find(){
        rlback = (RelativeLayout) this.findViewById(R.id.rl_aboutus_back);
        rlback.setOnClickListener(this);
        wv = (WebView) this.findViewById(R.id.wv_aboutus);
    }

    private void init(){
        wv.loadUrl("file:///android_asset/gywm.html");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.rl_aboutus_back:
                this.finish();
                break;
        }
    }
}
