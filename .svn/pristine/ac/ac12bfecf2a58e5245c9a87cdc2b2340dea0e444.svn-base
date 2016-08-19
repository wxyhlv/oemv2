package com.capitalbio.oemv2.myapplication.NetUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by wxy on 15-12-25.
 */
public class NetTool {

    /**
     * 检测网络 ,根据showToast来判断是否显示吐司，false 不显示
     * @方法名称:isNetwork
     * @描述: TODO
     * @创建人：pengbo
     * @创建时间：2014-5-4 下午5:20:52
     * @备注：
     * @param context
     * @param showToast
     * @return
     * @返回类型：boolean
     */
    public static boolean isNetwork(Context context,boolean showToast) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        if(showToast){
            Toast.makeText(context,"请检查当前的网络连接",Toast.LENGTH_SHORT).show();
        }
        return false;
    }


    // network available cannot ensure Internet is available
    public static boolean isNetWorkAvailable(final Context context) {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process pingProcess = runtime.exec("/system/bin/ping -c 1 www.baidu.com");
            int exitCode = pingProcess.waitFor();
            return (exitCode == 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
