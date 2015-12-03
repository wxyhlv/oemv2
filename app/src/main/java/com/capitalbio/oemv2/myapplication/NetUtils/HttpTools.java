package com.capitalbio.oemv2.myapplication.NetUtils;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.ResponseHandlerInterface;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;
import cz.msebera.android.httpclient.util.EntityUtils;

/**
 * Created by chengkun on 15-11-19.
 */
public class HttpTools {

    private static AsyncHttpClient httpClient = new AsyncHttpClient();

    public static void post(Context context, String url, JSONObject json, ResponseHandlerInterface handlerInterface) {


        //设置contentType
        String contentType = "application/json";


        //设置charset编码格式
        List<Header> requestHeader = new ArrayList<>();
        Header baseHeader = new BasicHeader("charset", HTTP.UTF_8);
        Header acceptHeader = new BasicHeader("Accept", "application/json");
        requestHeader.add(baseHeader);
        requestHeader.add(acceptHeader);

        //通过获取请求的字符串
        StringEntity entity = new StringEntity(json.toString(), HTTP.UTF_8);
        try {
            Log.d("MyTest", EntityUtils.toString(entity));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //发送http请求
        httpClient.post(context, url, requestHeader.toArray(new Header[requestHeader.size()]), entity, contentType, handlerInterface);

    }




}
