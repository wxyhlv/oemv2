package com.capitalbio.oemv2.myapplication.Bean;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * Created by susu on 2016/6/30.
 */
public class Version {
   // private static Context context;
    private  Context context;
    public Version(Context context) {
        this.context = context;
    }

    /**
     * 获取软件版本号
     * @return
     */
    public int getVerCode( ) {
        int verCode = -1;
        try {
            verCode=context.getPackageManager().getPackageInfo(context.getPackageName(),0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verCode;
    }

    /**
     * 获取软件名称
     * @return
     */
    public String getVerName( ) {
        String verName = "";
        try {
            verName=context.getPackageManager().getPackageInfo(context.getPackageName(),0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }
    // 判断权限集合
    public boolean havePermissions(String... permissions) {
        int a=0;
        for (String permission : permissions) {
            if (havePermission(permission)) {
               a=a+1;
            }
        }
        return a==permissions.length;
    }

    // 判断是否缺少权限
    private boolean havePermission(String permission) {
        return ContextCompat.checkSelfPermission(context, permission) ==
                PackageManager.PERMISSION_GRANTED;
    }

    public void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("设置", okListener)
                .setNegativeButton("取消", null)
                .create()
                .show();
    }


}
