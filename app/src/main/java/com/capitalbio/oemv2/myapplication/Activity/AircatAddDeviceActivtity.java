package com.capitalbio.oemv2.myapplication.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.capitalbio.oemv2.myapplication.Const.AircatConst;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.Const;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.demo_activity.WifiUtils;
import com.capitalbio.oemv2.myapplication.NetUtils.NetTool;
import com.capitalbio.oemv2.myapplication.QR.MipcaActivityCapture;
import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;
import com.capitalbio.oemv2.myapplication.Utils.PreferencesUtils;
import com.capitalbio.oemv2.myapplication.Utils.Utility;
import com.capitalbio.oemv2.myapplication.dialog.WifiPwdSetDialog;
import com.capitalbio.oemv2.myapplication.zxing.camera.CameraManager;

/**
 * Created by wxy on 15-12-31.
 */
public class AircatAddDeviceActivtity extends BaseActivity implements View.OnClickListener,WifiPwdSetDialog.RegistFinishCallback {
    LinearLayout contentLayout;
    TextView tv_scanqrcode, tv_addbysearch;
    private final static int SCANNIN_GREQUEST_CODE = 1;

    @Override
    protected void initChildLayout() {
        initChildView();
    }

    private void initChildView() {
        rl.setBackgroundResource(R.drawable.bg_main);
        setLeftTopIcon(R.drawable.ic_back);
        ivSplitLine.setBackgroundResource(R.color.mainSplitLine);

        contentLayout = (LinearLayout) View.inflate(this, R.layout.activity_add_airdevice, null);
        llBody.addView(contentLayout);
        setTvTopTitle(R.string.device_add);
        findViewById(R.id.tv_addbysearch).setOnClickListener(this);
        findViewById(R.id.tv_scanqrcode).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_scanqrcode:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CAMERA},
                            1
                    );
                }else{
                    Intent intent = new Intent();
                    intent.setClass(this, MipcaActivityCapture.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
                }


                break;
            case R.id.tv_addbysearch:
                Utility.startActivity(getContext(), AircatSearcDeviceActivtity.class);

              /*  WifiUtils wifiUtils = new WifiUtils(context);
                String wifiname = wifiUtils.getWifiConnectedSsid();
                    Dialog dialog = new WifiPwdSetDialog(this,this,
                            R.style.MyDialog);

                    dialog.show();*/


                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    Intent intent = new Intent();
                    intent.setClass(this, AircatScanResultActivtity.class);
                    intent.putExtra("way", AircatConst.Way_code);
                    intent.putExtra("result", (String) bundle.get("result"));
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }

    @Override
    public void onFinish() {
        finish();
    }

   /* @Override
    public void onSetTargetAp(int result) {
        OemLog.log(TAG,result+"");
        if(result== Const.AIRCAT_COMMAND_SETTARGETAP_SUCCESSFUL){
            Utility.startActivity(getContext(), AircatSearcDeviceActivtity.class);
        }

    }*/
    public interface RegistFinishCallback{
        void onFinish();
    };


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    Intent intent = new Intent();
                    intent.setClass(this, MipcaActivityCapture.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
                } else {
                    Toast.makeText(getApplicationContext(),"没有相机权限！",Toast.LENGTH_SHORT).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }
}
