package com.capitalbio.oemv2.myapplication.Utils;

import android.os.AsyncTask;
import android.util.Log;

import com.capitalbio.oemv2.myapplication.Const.Base_Url;
import com.capitalbio.oemv2.myapplication.FirstPeriod.Net.HttpTools;


/**
 * @author lzq
 * @Time 2016/3/3 9:26
 */
public class UpLoaderAsyncTask extends AsyncTask {

    private static final String url = Base_Url.uploadData_Url;

    private OnCallBack onCallBack;

    public void setOnCallBack(OnCallBack onCallBack){
        this.onCallBack = onCallBack;
    }

    @Override
    protected void onPreExecute() {
        onCallBack.lesgo();
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        if (objects == null || objects.length < 1 || objects[0] == null) {
            return null;
        }

        String entity = (String) objects[0];

        return HttpTools.httppost2(url, entity);
    }



    @Override
    protected void onPostExecute(Object o) {
        onCallBack.over(o);
    }

    //回调接口
    public interface OnCallBack {
        public void lesgo();
        public void over(Object o);
    }
}
