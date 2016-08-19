package com.capitalbio.oemv2.myapplication.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.LinearLayout;

import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.QR.QRcodeUtil;
import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;
import com.capitalbio.oemv2.myapplication.dialog.AircatShareDialog;

import java.io.File;

/**
 * Created by wxy on 15-12-31.
 */
public class AircatShareDeviceActivtity extends BaseActivity implements View.OnClickListener {
    LinearLayout contentLayout;
    private final static int SCANNIN_GREQUEST_CODE = 1;

    @Override
    protected void initChildLayout() {
        initChildView();
    }

    private void initChildView() {
        rl.setBackgroundResource(R.drawable.bg_main);
        setLeftTopIcon(R.drawable.ic_back);
        ivSplitLine.setBackgroundResource(R.color.mainSplitLine);

        contentLayout = (LinearLayout) View.inflate(this, R.layout.activity_share_airdevice, null);
        llBody.addView(contentLayout);
        setTvTopTitle(R.string.share_device);
        findViewById(R.id.tv_gerqecode).setOnClickListener(this);
        findViewById(R.id.tv_sharemsg).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_gerqecode:
                final String filePath = getFileRoot(AircatShareDeviceActivtity.this) + File.separator
                        + "qr_" + System.currentTimeMillis() + ".jpg";

                //二维码图片较大时，生成图片、保存文件的时间可能较长，因此放在新线程中
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
                        boolean success = QRcodeUtil.createQRImage(MyApplication.getInstance().getAircatDeviceMac(), 800, 800,
                                bitmap,
                                filePath);

                        if (success) {
                            Intent intent = new Intent();
                            intent.putExtra("path", filePath);
                            intent.setClass(getContext(), AirGerQECodeActivity.class);
                            startActivity(intent);

                        }
                    }
                }).start();
                break;
            case R.id.tv_sharemsg:
                Dialog dialog = new AircatShareDialog(context);
                dialog.setTitle("推送设备给");
                dialog.show();
               /* Utility.startActivity(getContext(),AircatSearcDeviceActivtity.class);
                finish();*/

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
                    OemLog.log(TAG, bundle.getString("result"));
                    //Toast.makeText(this, bundle.getString("result"),Toast.LENGTH_LONG).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(AircatShareDeviceActivtity.this);
                    builder.setMessage(bundle.getString("result") + "");
                    builder.setTitle("添加设备");
                    builder.setPositiveButton("添加设备", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override

                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }

                    });
                    builder.create().show();
                }
                break;
        }
    }

    //文件存储根目录
    private String getFileRoot(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File external = context.getExternalFilesDir(null);
            if (external != null) {
                return external.getAbsolutePath();
            }
        }

        return context.getFilesDir().getAbsolutePath();
    }
}
