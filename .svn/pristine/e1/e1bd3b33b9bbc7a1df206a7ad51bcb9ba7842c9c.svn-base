package com.capitalbio.oemv2.myapplication.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.capitalbio.oemv2.myapplication.QR.MipcaActivityCapture;
import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;
import com.capitalbio.oemv2.myapplication.Utils.Utility;

/**
 * @author lzq
 * @Time 2015/12/22 14:18
 */
public class AirGerQECodeActivity extends BaseActivity implements View.OnClickListener {
    private final static int SCANNIN_GREQUEST_CODE = 1;
    private ImageView ivQecode;
    String TAG = "AirGerQECodeActivity";
    RelativeLayout contentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rl.setBackgroundResource(R.color.bg_content);
        rlNavigateBar.setBackgroundResource(R.color.skyblue);
        setLeftTopIcon(R.drawable.ic_back);
        contentLayout = (RelativeLayout) View.inflate(this, R.layout.activity_qecode, null);
        llBody.addView(contentLayout);
        setTvTopTitle(R.string.ger_qecode);

        ivQecode = (ImageView) findViewById(R.id.iv_qecode);
        String filepath = getIntent().getStringExtra("path");
        OemLog.log(TAG, filepath);
        ivQecode.setImageBitmap(BitmapFactory.decodeFile(filepath));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_saoyisao:
                break;
            case R.id.rl_aircat_share:
                break;
        }

    }

}
