package com.capitalbio.oemv2.myapplication.Utils;

import android.content.Context;
import android.util.Log;

import java.net.ContentHandler;

/**
 * @author lzq
 * @Time 2016/3/3 10:04
 */
public class UpLoader {

    private Context context;

    private UpLoaderAsyncTask.OnCallBack onCallBack;

    public UpLoader(Context context) {
        this.context = context;
    }

    public void setOnCallBack(UpLoaderAsyncTask.OnCallBack onCallBack){
        this.onCallBack = onCallBack;
    }


    public void go(Object bean){
        UpLoadJsonDataCreater upLoadJsonDataCreater = new UpLoadJsonDataCreater(bean,context);
        String entity = upLoadJsonDataCreater.entity();

        Log.i("info","==========记录上传数据的entity=========="+entity);

        UpLoaderAsyncTask upLoaderAsyncTask = new UpLoaderAsyncTask();
        upLoaderAsyncTask.setOnCallBack(onCallBack);
        upLoaderAsyncTask.execute(entity);
    }


}
