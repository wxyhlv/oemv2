package com.capitalbio.oemv2.myapplication.Utils;

import android.util.Log;

import com.capitalbio.oemv2.myapplication.Const.Const;

/**
 * Created by chengkun on 15-12-14.
 */
public class OemLog {
    public static void log(String TAG, String message) {
        if (Const.DEBUG) {
            Log.i(TAG, message);
        }
    }
}
