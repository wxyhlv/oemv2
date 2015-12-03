package com.capitalbio.oemv2.myapplication.Base;

import android.app.Application;
import android.util.Log;

/**
 * Created by chengkun on 15-11-19.
 */
public class MyApplication extends Application{

    public static final String TAG = "MyApplication";

    private static Application app;

    //apikey
    public  String apikey = "85038e9d-b7fc-4f41-bf16-34cce1096b92";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "app is running...");
        app = this;
    }

    public static MyApplication getInstance() {
        if (app != null) {
            return (MyApplication) app;
        }
        return null;
    }

}
