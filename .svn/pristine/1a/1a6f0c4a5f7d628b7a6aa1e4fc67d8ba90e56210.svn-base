package com.capitalbio.oemv2.myapplication.FirstPeriod.Net;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.android.pc.ioc.internet.AjaxCallBack;
import com.android.pc.ioc.internet.FastHttp;
import com.android.pc.ioc.internet.InternetConfig;
import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.FirstPeriod.Final.Base_Url;
import com.capitalbio.oemv2.myapplication.FirstPeriod.Utils.Common_dialog;
import com.capitalbio.oemv2.myapplication.FirstPeriod.Utils.MyLog;
import com.capitalbio.oemv2.myapplication.FirstPeriod.Utils.ToastMaster;
import com.capitalbio.oemv2.myapplication.Utils.Utility;

import  org.apache.http.client.methods.HttpPost;




public class HttpTools {

    //wifi网络状态是否添加限制
	private static boolean isWifi=false;

    private static MyLog log = new MyLog(HttpTools.class);
    

	/**
	 * post 请求参数
	 * 
	 * @param url
	 * @param params
	 * @param key
	 * @param context
	 * @param callback
	 */
	public static void post(String url, LinkedHashMap<String, String> params,
			int key, Context context, AjaxCallBack callback, boolean isShow,String message) {
		if (isShow) {
			Common_dialog.startWaitingDialog(context, message);
		}
		url = Base_Url.Url_Base + url;
		String paramstr = "";
		for (Iterator<String> it = params.keySet().iterator(); it.hasNext();) {
			Object key2 = it.next();
			paramstr += key2 + "=" + params.get(key2) + "&";
		}
		try {
			paramstr = paramstr.substring(0, paramstr.length() - 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.i("Post Params", paramstr);

		if (paramstr.length() == 0) {
			Log.i("接口地址====", url);
		} else {
			Log.i("接口地址加参数=====", url + "?" + paramstr);
		}
		
		InternetConfig config = new InternetConfig();
		config.setKey(key);
		config.setCharset("UTF-8");
		config.setTimeout(100000);
		
		// config.setContent_type_web(content_type_web);
		// FastHttpHander.ajax(url, params, config, callback);
		FastHttp.ajax(url, params, config, callback);
	}

	/**
	 * 
	 * Httppost请求
	 * 
	 * **/
	public static String httppostvalue(String url, List<NameValuePair> params) {
		String result = null;
		String baseurl = Base_Url.Url_Base;
		/* 建立HTTP Post连线 */
		HttpPost httpRequest = new HttpPost(baseurl + url);
		/*
		 * httpPost.addHeader("Accept", "application/json");
		 * httpPost.addHeader("Content-Type", "application/json");
		 */
		httpRequest.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");//这才是重点其他的不设置

		// 发出HTTP request
		try {
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			// 取得HTTP response
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpRequest);

			/*
                                            绑定成功，返回HTTP 200，返回数据为JSON，格式为{"code":0,"message":"success"}
                                            绑定失败，根据不同情况为：
                   1、缺少token或username,返回HTTP 400，
                                                                       消息为"Shoud provide token and username."
                   2、token错误，返回HTTP 401，
                                                                       消息为“User not login.”
                   3、缺少type,返回HTTP 400，
                                                                   消息为"Shoud provide device type."
                   4、当前用户已绑定其他设备，返回HTTP 200,返回数据为JSON，
                                                                   格式{"code":"10","message":“CurrentUser is binding another device.”}
                   5、mac地址已被其他用户绑定，返回HTTP 200,返回数据为JSON，
                                                                   格式{"code":"10","message":“Device is binded by another user.”}
            */
			
			/*
			 2.解绑设备，POST host:port/service/data/{appid}/unbindDevice
                                            所需参数：username,token,watchId
                                            返回值：
                                                        解绑成功，返回HTTP 200，返回数据为JSON，格式为{"code":0,"message":"success"}
                                                        解绑失败，根据不同情况为：
                      1、缺少token或username,返回HTTP 400，
                                                                                      消息为"Shoud provide token and username."
                      2、token错误，返回HTTP 401， 
                                                                                      消息为“User not login.”
                      3、mac没有和用户绑定，返回HTTP 200,返回数据为JSON，
                                                                                      格式{"code":"10","message":“Device is not binded by this user.”}
			 */
			
			final int statusCode = httpResponse.getStatusLine().getStatusCode();
			// 若状态码为200 ok
            if (statusCode == 200) {
				// 取出回应字串
				result = EntityUtils.toString(httpResponse.getEntity(),HTTP.UTF_8);

			} else if (statusCode == 401) {
                // token错误，返回HTTP 401， 消息为“User not login.”
                result = "{\"code\":\"401\",\"message\":\"false\"}";
                
            } else if (statusCode == 400) {
                // 缺少type,返回HTTP 400， 消息为"Shoud provide device type."
                result = "{\"code\":\"400\",\"message\":\"false\"}";
                
            } else {
                log.info("httpgetmac Error Response: "
                        + httpResponse.getStatusLine().toString());
                
                result = "{\"code\":\"10\",\"message\":\"false\"}";
            }
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			result=null;
		}
		return result;
	}

	/**
	 * 
	 * Httppost数据josn请求
	 * 
	 * */

	public static String httppost(final String url, final String value) {
		String result = null;
		String baseurl = Base_Url.Url_Base;

		/*
		 * @Override public void run() {
		 */

		HttpPost httpPost = new HttpPost(baseurl + url);
		// 设置httpPost请求参数
		HttpResponse httpResponse = null;
		try {
			StringEntity entity = new StringEntity(value, HTTP.UTF_8);
			httpPost.setEntity(entity);
			httpPost.addHeader("Accept", "application/json");
			httpPost.addHeader("Content-Type", "application/json");
			httpPost.addHeader("charset", HTTP.UTF_8);
			httpResponse = new DefaultHttpClient().execute(httpPost);
			Log.i("======weiz", "请求状态"
					+ httpResponse.getStatusLine().getStatusCode());
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				// 使用getEntity方法活得返回结果
				result = EntityUtils.toString(httpResponse.getEntity());

			} else {
				result = "";
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * } }
		 */
		/* }).start(); */

		return result;
	}

	//这个备份方法，就是换了个baseurl；
	public static String httppost2(final String url, final String value) {
		String result = null;
		//String baseurl0 = Base_Url.Url_Base;
		String baseurl = com.capitalbio.oemv2.myapplication.Const.Base_Url.Url_Base;
		/*
		 * @Override public void run() {
		 */

		HttpPost httpPost = new HttpPost(baseurl + url);
		// 设置httpPost请求参数
		HttpResponse httpResponse = null;
		try {
			StringEntity entity = new StringEntity(value, HTTP.UTF_8);
			httpPost.setEntity(entity);
			httpPost.addHeader("Accept", "application/json");
			httpPost.addHeader("Content-Type", "application/json");
			httpPost.addHeader("charset", HTTP.UTF_8);
			httpResponse = new DefaultHttpClient().execute(httpPost);
			Log.i("======weiz", "请求状态"
					+ httpResponse.getStatusLine().getStatusCode());
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				// 使用getEntity方法活得返回结果
				result = EntityUtils.toString(httpResponse.getEntity());

			} else {
				result = "";
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * } }
		 */
		/* }).start(); */

		return result;
	}

	/***
	 * HTTPGET请求
	 * 
	 * */

	public static String httpget(final String url_value,Context context,MyApplication myapp) throws IOException{
		String strResult = null;
		/* new Thread(new Runnable() { */
		String url = url_value;
		//如果添加了网络wifi限制
		if(myapp.ischeck){
			//如果当前为wifi状态
			if(Utility.isWifi(context)){
				isWifi=true;
			}else{
				isWifi=false;
				Common_dialog.stop_WaitingDialog();
			}
		}else{
		//如果不是wifi状态且为开启网络限制开启下载
			isWifi=true;	
		}
	//开启下数据下载
     if(isWifi){
		// @Override
		/* public void run() { */
    	 Log.i("weiz===","wifistatue"+isWifi );
		HttpGet httpRequest = new HttpGet(Base_Url.Url_Base
				+ Base_Url.downloaddetail_Url + url);
		// 获取HttpClient对象
		HttpClient httpClient = new DefaultHttpClient();
	    HttpParams params = httpClient.getParams();
        HttpConnectionParams.setConnectionTimeout(params, 5000);
        HttpConnectionParams.setSoTimeout(params, 8000);
		Log.i("======weiz", "数据下载请求地址：：：" + Base_Url.Url_Base
				+ Base_Url.downloaddetail_Url + url);
		/* 发送请求并等待响应 */
		try {
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			Log.i("weiz===","statueCode---"+httpResponse.getStatusLine().getStatusCode());
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				/* 读 */
			strResult = EntityUtils.toString(httpResponse.getEntity(),HTTP.UTF_8);
          
			} else {
				Common_dialog.stop_WaitingDialog();
				Log.i("weiz===", "数据结果：---" + "Error Response: "
						+ httpResponse.getStatusLine().toString());
				strResult = null;
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			throw new IOException();
		}
		try {
			new JSONObject(strResult);
		} catch (JSONException e) {
			try {
				new JSONArray(strResult);
			} catch (JSONException e1) {
				throw new IOException();
			}
		}

		/* }).start(); */
		return strResult;
     }else{
    	 Log.i("weiz===","非无线网状态");
    	 Looper.prepare();
		 ToastMaster.showToast(context, "当前网络非wifi状态不支持下载");
    	 Looper.loop();
    	 Common_dialog.stop_WaitingDialog();
    	 return "";
     }
	}
	
	
	/***
	 * HTTPGET请求
	 * 与httpget对比，就换了个请求地址
	 * */

	public static String httpget2(final String url_value,Context context,MyApplication myapp) throws IOException{
		String strResult = null;
		/* new Thread(new Runnable() { */
		String url = url_value;
		//如果添加了网络wifi限制
		if(myapp.ischeck){
			//如果当前为wifi状态
			if(Utility.isWifi(context)){
				isWifi=true;
			}else{
				isWifi=false;
				Common_dialog.stop_WaitingDialog();
			}
		}else{
		//如果不是wifi状态且为开启网络限制开启下载
			isWifi=true;	
		}
	//开启下数据下载
     if(isWifi){
		// @Override
		/* public void run() { */
    	 Log.i("weiz===","wifistatue"+isWifi );
		HttpGet httpRequest = new HttpGet(Base_Url.Url_Base
				+ Base_Url.download24hour_Url + url);
		// 获取HttpClient对象
		HttpClient httpClient = new DefaultHttpClient();
	    HttpParams params = httpClient.getParams();
        HttpConnectionParams.setConnectionTimeout(params, 5000);
        HttpConnectionParams.setSoTimeout(params, 8000);
		Log.i("======weiz", "数据下载请求地址：：：" + Base_Url.Url_Base
				+ Base_Url.download24hour_Url + url);
		/* 发送请求并等待响应 */
		try {
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			Log.i("weiz===","statueCode---"+httpResponse.getStatusLine().getStatusCode());
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				/* 读 */
			strResult = EntityUtils.toString(httpResponse.getEntity(),HTTP.UTF_8);
          
			} else {
				Common_dialog.stop_WaitingDialog();
				Log.i("weiz===", "数据结果：---" + "Error Response: "
						+ httpResponse.getStatusLine().toString());
				strResult = null;
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			throw new IOException();
		}
		try {
			if(strResult!=null){
				new JSONObject(strResult);
			}
		} catch (JSONException e) {
			try {
				if(strResult!=null){
					new JSONArray(strResult);
				}
			} catch (JSONException e1) {
				throw new IOException();
			}
		}

		/* }).start(); */
		return strResult;
     }else{
    	 Log.i("weiz===","非无线网状态");
    	 Looper.prepare();
    	 ToastMaster.showToast(context, "当前网络非wifi状态不支持下载");
    	 Looper.loop();
    	 Common_dialog.stop_WaitingDialog();
    	 return "";
     }
	}
	
	/**
	 * 获取总条数
	 * @throws IOException 
	 * 
	 * */
	public  static String getTDNumber(String url,Context context,MyApplication myapp) throws IOException{
		String strResult = null;
		url=url.replaceAll(" ", "%20");
		HttpGet httpRequest = new HttpGet(Base_Url.Url_Base
				+ Base_Url.downloadtotaldetail_Url + url);
		Log.i("weiz","URL++"+Base_Url.Url_Base
				+ Base_Url.downloadtotaldetail_Url + url);
		HttpClient httpClient = new DefaultHttpClient();
	    HttpParams params = httpClient.getParams();
        HttpConnectionParams.setConnectionTimeout(params, 5000);
        HttpConnectionParams.setSoTimeout(params, 8000);
		Log.i("======weiz", "数据下载请求地址：：：" + Base_Url.Url_Base
				+ Base_Url.downloaddetail_Url + url);
		HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(httpRequest);
			Log.i("weiz===","statueCode---"+httpResponse.getStatusLine().getStatusCode());
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				/* 读 */
			strResult = EntityUtils.toString(httpResponse.getEntity(), HTTP.UTF_8);
	      
			} else {
				Common_dialog.stop_WaitingDialog();
				Log.i("weiz===", "数据结果：---" + "Error Response: "
						+ httpResponse.getStatusLine().toString());
				strResult = null;
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			throw new IOException();
		}
	
		return strResult;
	}
	
	
	 public static String getContentCharSet(final HttpEntity entity) throws ParseException {
	        if (entity == null) {
	            throw new IllegalArgumentException("HTTP entity may not be null");
	        }
	        String charset = null;
	        if (entity.getContentType() != null) {
	            HeaderElement values[] = entity.getContentType().getElements();
	            if (values.length > 0) {
	                NameValuePair param = values[0].getParameterByName("charset");
	                if (param != null) {
	                    charset = param.getValue();
	                }
	            }
	        }
	        return charset;
	    }
	
	
	/**
	 * HTTP GET macAddress请求
	 */
	public static String httpgetmac(final String url_value)throws IOException {
		String strResult = null;
		/* new Thread(new Runnable() { */
		String url = url_value;

		// @Override
		/* public void run() { */
		HttpGet httpRequest = new HttpGet(Base_Url.Url_Base
				+ Base_Url.bindMacAddress_Url + url);
		Log.i("======weiz", "请求地址：：：" + Base_Url.Url_Base
				+ Base_Url.bindMacAddress_Url + url);
		
		HttpClient client = new DefaultHttpClient();
        // 请求超时
        client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
        // 读取超时
        client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);
        
		/* 发送请求并等待响应 */
		try {
			HttpResponse httpResponse = client.execute(httpRequest);
			
			/*
			  3.获得绑定mac，GET /{appid}/getBinding
                                                所需参数：username,token,type
                                                返回值：
                                                      成功，返回HTTP 200，返回数据为JSON，
                                                                    格式为{"code":0,"message":"success"，"data":"XXXX"},data中为mac地址
                                                       失败，根据不同情况为：
                       1、缺少token或username,返回HTTP 400，
                                                                                       消息为"Shoud provide token and username."
                       2、token错误，返回HTTP 401，
                                                                                       消息为“User not login.”
                       3、缺少type,返回HTTP 400，
                                                                                       消息为"Shoud provide device type."
                       4、该用户没有绑定设备，返回HTTP 200,返回数据为JSON，
                                                                                       格式{"code":"10","message":“No bindings.”}
			 */
			
			final int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == 200) {
			    //成功，返回HTTP 200，返回数据为JSON，
                // 格式为{"code":0,"message":"success"，"data":"XXXX"},data中为mac地址
			    // 绑定成功，用户已绑定设备，设备已被绑定
				strResult = EntityUtils.toString(httpResponse.getEntity());
				
			} else if (statusCode == 401) {
			    // token错误，返回HTTP 401， 消息为“User not login.”
			    strResult = "{\"code\":\"401\",\"message\":\"false\"}";
			    
			} else if (statusCode == 400) {
			    // 缺少type,返回HTTP 400， 消息为"Shoud provide device type."
                strResult = "{\"code\":\"400\",\"message\":\"false\"}";
                
			} else {
			    log.info("httpgetmac Error Response: "
                        + httpResponse.getStatusLine().toString());
				
				strResult = "{\"code\":\"10\",\"message\":\"false\"}";
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			throw new IOException();
		}
		try {
			new JSONObject(strResult);
		} catch (JSONException e) {
			throw new IOException();
		}
		/* }).start(); */
		return strResult;

	}

	/*
	 * public static void post2(String url, LinkedHashMap<String, String>
	 * params, int key, Context context, AjaxCallBack callback, boolean isShow)
	 * { if (isShow) { Common_dialog.StartWaitingDialog(context, ""); } String
	 * paramstr = ""; for (Iterator<String> it = params.keySet().iterator();
	 * it.hasNext();) { Object key2 = it.next(); paramstr += key2 + "=" +
	 * params.get(key2) + "&"; } try { paramstr = paramstr.substring(0,
	 * paramstr.length() - 1); } catch (Exception e) { e.printStackTrace(); }
	 * Log.i("Post Params", paramstr);
	 * 
	 * if (paramstr.length() == 0) { Log.i("接口地址====", url); } else {
	 * Log.i("接口地址加参数=====", url + "?" + paramstr); }
	 * 
	 * InternetConfig config = new InternetConfig(); config.setKey(key);
	 * config.setCharset("UTF-8"); config.setTimeout(30000); //
	 * FastHttpHander.ajax(url, params, config, callback); FastHttp.ajax(url,
	 * params, config, callback); }
	 */

	// xunits 上传
	/*
	 * public static void sendPost(String apikey) {
	 * 
	 * HttpUtils http = new HttpUtils();
	 * 
	 * http.configTimeout(10000); RequestParams params = new RequestParams();
	 * 
	 * params.addHeader("Charset", "UTF-8"); params.addHeader("Content-Type",
	 * "application/json"); String url =
	 * Base_Url.Url_Base+Base_Url.uploaddetail_Url+apikey;
	 * 
	 * 
	 * params1.put("deviceId", "111"); params1.put("testTime",
	 * "2015-03-27 13:56:00"); params1.put("modelType", "bracelet");
	 * params1.put("username", "ww123456"); params1.put("sleepTime", "360");
	 * params1.put("fallAsleep", "150"); params1.put("lightSleep", "210");
	 * params1.put("deepSleep", "150"); params1.put("sleepState", "0");//0x00:睡着
	 * 0x01:浅睡0x02:醒着0x03:准备入睡0x04:退出睡眠0x10:进入睡眠 params1.put("sleepQuality",
	 * "良好");
	 * 
	 * 
	 * params.addBodyParameter("deviceId", "111");
	 * params.addBodyParameter("testTime", "2015-03-27 13:56:00");
	 * params.addBodyParameter("modelType", "bracelet");
	 * params.addBodyParameter("username", "ww123456");
	 * params.addBodyParameter("sleepTime", "360");
	 * params.addBodyParameter("fallAsleep", "150");
	 * params.addBodyParameter("lightSleep", "210");
	 * params.addBodyParameter("deepSleep", "150");
	 * params.addBodyParameter("sleepState", "0");
	 * params.addBodyParameter("sleepQuality", "良好");
	 * 
	 * http.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {
	 * 
	 * @Override public void onFailure(HttpException arg0, String arg1) {
	 * Message msg = Message.obtain(); // msg.obj = arg0.getCause(); msg.what =
	 * 1; Log.i("=====weiz", "请求成功"+arg1); // infohandler.sendMessage(msg);
	 * 
	 * }
	 * 
	 * @Override public void onSuccess(ResponseInfo<String> arg0) { Message msg
	 * = Message.obtain(); // msg.obj = arg0.result; msg.what = 0;
	 * Log.i("=====weiz", "请求失败"); // infohandler.sendMessage(msg); }
	 * 
	 * }); }
	 */
	/**
	 * get请求方式
	 * 
	 * @param url
	 * @param params
	 * @param key
	 * @param context
	 * @param callback
	 */
	public static void get(String url, LinkedHashMap<String, String> params,
			int key, Context context, AjaxCallBack callback, boolean isShow) {
		if (isShow)
			Common_dialog.startWaitingDialog(context, "");
		url = Base_Url.Url_Base + url;
		String paramstr = "";
		for (Iterator<String> it = params.keySet().iterator(); it.hasNext();) {
			Object key2 = it.next();
			paramstr += key2 + "=" + params.get(key2) + "&";
		}
		try {
			paramstr = paramstr.substring(0, paramstr.length() - 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.i("Post Params", paramstr);

		if (paramstr.length() == 0) {
			Log.i("接口地址====", url);
		} else {
			url = url + "?" + paramstr;
			Log.i("接口地址加参数=====", url);
		}

		InternetConfig config = new InternetConfig();
		config.setKey(key);
		config.setCharset("UTF-8");
		// config.setTimeout(30000);
		// FastHttpHander.ajax(url, params, config, callback);
		FastHttp.ajaxGet(url, config, callback);
	}

	/**
	 * 数据下载
	 * 
	 * @param type
	 *            下载类型
	 * @param startday
	 *            开始日期
	 * @param endday
	 *            结束日期
	 * @param token
	 * @param username
	 * @param  what 下载的数据类型
	 * @author weiz
	 * 
	 * 
	 * **/
	public static void DownLaodValue(final String token, final String username,
			final String type, final String startday, final String endday,final  int what,
			final Handler handler,final Context context,final MyApplication myapp){
		Log.i("DownLaodValue", "DownLaodValue--");
		new Thread(new Runnable() {
		    
			@Override
			public void run() {
				
				// TODO Auto-generated method stub
				String value = null;
				Log.i("======weiz", "token" + token);
				// UserBean.isUserBeanNull(apliction);
				try {
					value = HttpTools.httpget("token=" + token + "&username="
							+ username + "&type=" + type + "&beginDate=" + startday
							+ "&endDate=" + endday,context,myapp);
					System.out.println("-------------value="+"token=" + token + "&username="
							+ username + "&type=" + type + "&beginDate=" + startday
							+ "&endDate=" + endday);
				} catch (IOException e) {
					Message msg = handler.obtainMessage(-1);
					msg.sendToTarget();
				}
				      Message msg = new Message();
				if ("".equals(value) || null == value || "[]".equals(value)) {
					msg.what = 0;
					handler.sendMessage(msg);

				} else {
					msg.obj = value;
					msg.what = what;
					handler.sendMessage(msg);
				}
			}
		}).start();
		
		System.out.println("------------------------------------------------");

		// return value;
		// JsonParser.fatvalue(value);

	}
	
	/**
	 * 获取过去24小时的整点数据
	 * @param token
	 * @param username
	 * @param type
	 * @param startday
	 * @param endday
	 * @param what
	 * @param handler
	 * @param context
	 * @param myapp
	 */
	public static void DownLaodValue24(final String token, final String username,
			final String type, final String startday, final String endday,final  int what,
			final Handler handler,final Context context,final MyApplication myapp){
		Log.i("DownLaodValue", "DownLaodValue--");
		new Thread(new Runnable() {
		    
			@Override
			public void run() {
				
				// TODO Auto-generated method stub
				String value = null;
				Log.i("======weiz", "token" + token);
				// UserBean.isUserBeanNull(apliction);
				try {
					value = HttpTools.httpget2("token=" + token + "&username="
							+ username + "&type=" + type + "&beginDate=" + startday
							+ "&endDate=" + endday,context,myapp);
					
				} catch (IOException e) {
					Message msg = handler.obtainMessage(-1000);
					msg.sendToTarget();
				}
				      Message msg = new Message();
				if ("".equals(value) || null == value || "[]".equals(value)) {
					System.out.println("=======================获取数据异常");
					msg.what = -2000;
					handler.sendMessage(msg);

				} else {
					msg.obj = value;
					msg.what = what;
					handler.sendMessage(msg);
				}
			}
		}).start();
		
		System.out.println("------------------------------------------------");

		// return value;
		// JsonParser.fatvalue(value);

	}

	/**
	 * 获取总条数
	 * */
	public static void totalDNumber(final String token, final String username,
			final String type, final String startday, final String endday,final  int what,
			final Handler handler,final Context context,final MyApplication myapp){
		new Thread(new Runnable() {
			String value = null;
			@Override
			public void run() {
				try {
					value=HttpTools.getTDNumber("token=" + token + "&username="
							+ username + "&type=" + type + "&beginDate=" + startday
							+ "&endDate=" + endday,context,myapp);
					Log.i("wei===","value:::"+"token=" + token + "&username="
							+ username + "&type=" + type + "&beginDate=" + startday
							+ "&endDate=" + endday);
					
				} catch (IOException e) {
					Message msg = handler.obtainMessage(-1);
					msg.sendToTarget();
				}
			     Message msg = new Message();
					if ("".equals(value) || null == value || "[]".equals(value)) {
						msg.what = 0;
						handler.sendMessage(msg);

					} else {
						msg.obj = value;
						msg.what = what;
						handler.sendMessage(msg);
					}
			}
		}).start();
	}
	
	/**
	 * 获取室外空气质量
	 * @param cityname
	 * @param handler
	 * @param what
	 */
	public static void getOutDoorAqi(String cityname,Handler handler,int what){
		String result = httpGet(cityname);
		
		Message message = new Message();
		message.what = what;
		message.obj = result;
		handler.sendMessage(message);
	}
	
	private static String httpGet(String cityname){
		
		HttpGet get = new HttpGet("http://a.apix.cn/apixlife/pm25/PM2.5?cityname="+cityname);
		
		get.addHeader("apix-key", "a7ab703e3067484049b9d6be17fd8eb1");
		
		HttpClient client = new DefaultHttpClient();
		HttpParams httpParams = client.getParams();
		
		HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
		HttpConnectionParams.setSoTimeout(httpParams, 8000);
		
		String result = "";
		try {
			
			HttpResponse httpResponse = client.execute(get);
			int statuscode = httpResponse.getStatusLine().getStatusCode();
			
			if(statuscode==200){
				result = EntityUtils.toString(httpResponse.getEntity());
			}
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
		
	}
	
}
