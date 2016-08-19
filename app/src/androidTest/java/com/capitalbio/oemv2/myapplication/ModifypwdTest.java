package com.capitalbio.oemv2.myapplication;

import android.content.Context;
import android.os.Environment;
import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.Const.Base_Url;
import com.capitalbio.oemv2.myapplication.NetUtils.HttpTools;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.tencent.open.utils.HttpUtils;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by wxy on 16-8-12.
 */
public class ModifypwdTest extends AndroidTestCase {

    public void test() {
   /*     {
            "userId": 1006553,
                "userName": "C46261000",
                "userOrgId": 1003247,
                "userOrgName": "浦发银行",
                "roleCode": "customer_sys",
                "roleList": "1021",
                "userQX": "customer_complaint, SYS_9915bf27-a71c-47dd-9ce0-6e212a34f9b0, customer_help, SYS_e6299e49-9693-4b46-a594-2ed4931d2a16, case_new2, customer_project, customer_top, cusomer_download, customer_case, customer_myinfo",
                "userRname": "龚珉捷",
                "userPhone": "13764273085"
        }*/


    }


    public void testDownload(){





    }
   /* try {


        //构造请求json对象
        JSONObject jsonObject = new JSONObject();
        JSONObject dataObj = new JSONObject();

        jsonObject.put("apikey", MyApplication.getInstance().apikey);
        //Log.i("info", "==========apikey========" + MyApplication.getInstance().apikey);
        jsonObject.put("username", "wxy");
        //Log.i("info", "==========username========" + MyApplication.getInstance().getCurrentUserName());
        jsonObject.put("token", "c1bff8b713edd8c35b14c86ee43fb90c");
        //Log.i("info", "==========token========" +PreferencesUtils.getString(context, "token"));

        dataObj.put("newPwd", "wxy321");
        dataObj.put("oldPwd", "wxy123");


        jsonObject.put("data", dataObj.toString());


        OemLog.log("testparam", "params is ....." +  Base_Url.Url_Base + Base_Url.ModifyPassword_Url + "--" + jsonObject.toString());
        //
        HttpTools.post(getContext(), Base_Url.Url_Base + Base_Url.ModifyPassword_Url, jsonObject, new AsyncHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String result1 = new String(responseBody);
                Log.i("testresult", "======下载数据的结果是" + result1);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                Log.i("testresult", "下载数据:服务器异常");
            }


        });

    } catch (JSONException e) {
        e.printStackTrace();
    }*/

}
