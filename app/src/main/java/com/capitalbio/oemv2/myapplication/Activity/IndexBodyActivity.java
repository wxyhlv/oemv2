package com.capitalbio.oemv2.myapplication.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.TextView;

import com.capitalbio.oemv2.myapplication.R;

/**
 * Created by wxy on 16-7-14.
 */
public class IndexBodyActivity extends Activity {
    private WebView webview;
    private TextView tv__titlename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  //无title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);  //全屏*/
        setContentView(R.layout.activity_index_blood);

        webview = (WebView) findViewById(R.id.webview);
        tv__titlename = (TextView) findViewById(R.id.tv__titlename);
        tv__titlename.setText("体脂秤参考标准");
        webview.loadUrl("file:///android_asset/zhifangchencankaozhibiao.html");
        findViewById(R.id.rl_devicesetting_back).
                setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           finish();
                                       }
                                   }

                );
    }
}
