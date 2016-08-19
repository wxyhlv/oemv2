package com.capitalbio.oemv2.myapplication.FirstPeriod.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import cn.com.weather.api.GeoAreaAsyncTask;
import cn.com.weather.api.Weather;
import cn.com.weather.api.WeatherAsyncTask;
import cn.com.weather.constants.Constants.Language;

import com.alibaba.fastjson.JSONObject;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.capitalbio.oemv2.myapplication.FirstPeriod.Interfaces.OnWeatherReceivedListener;
import com.capitalbio.oemv2.myapplication.R;


import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * 用途：
 * 百度定位获取经纬度，
 * 根据经纬度从中国天气网获取城市id，
 * 再根据城市id获取对应城市的天气数据
 * @author jiantaotu
 *
 */

public class CatService extends Service implements BDLocationListener {
	
	private LocationClient locationclient;
	
	/**获取城市id*/
	private GeoAreaAsyncTask geoAreaAsyncTask;
	
	
	private OnWeatherReceivedListener weatherReceivedListener;
	
	@Override
	public IBinder onBind(Intent intent) {
		return new MyBinder();
	}
	
	public class MyBinder extends Binder{
		public CatService getCatService(){
			return CatService.this;
		}
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		System.out.println("catservice的oncreate执行");
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		System.out.println("catservice的onStartCommand执行");
		return super.onStartCommand(intent, flags, startId);
		
	}
	
	public void setOnWeatherReceivedListener(OnWeatherReceivedListener listener){
		this.weatherReceivedListener = listener;
	}
	
	//初始化百度定位
	private void initLocationClient(){
		locationclient = new LocationClient( getApplicationContext() );
//		locationclient.setAccessKey("402dc2667328563072e70e1e6a5c1fc2");
		//locationclient.setAccessKey("TF8bNyuokLMbZ2uyTy1N009v");
		locationclient.registerLocationListener(this);
		LocationClientOption option = new LocationClientOption();
//		option.setOpenGps(true);                                //是否打开GPS 
	    option.setCoorType("bd09ll");                           //设置返回值的坐标类型。 
	    option.setPriority(LocationClientOption.NetWorkFirst);  //设置定位优先级 
	    option.setProdName(getResources().getString(R.string.app_name2));//设置产品线名称。强烈建议您使用自定义的产品线名称，方便我们以后为您提供更高效准确的定位服务。
	    option.setScanSpan(10 * 60 * 1000);                          //设置定时定位的时间间隔。单位毫秒  (定位成功后就停止定位,只有定位失败时才会有效)
	    option.setAddrType("all");// all时，表示返回地址信息。
	    option.setLocationMode(LocationMode.Battery_Saving);
		option.setNeedDeviceDirect(false);
		option.setIsNeedAddress(true);
		locationclient.setLocOption(option);
	}
	
	//开始百度定位
	public void startLocate(){
		if(locationclient==null){
			initLocationClient();
			locationclient.start();
			locationclient.requestLocation();
		}else{
			if(!locationclient.isStarted()){
				locationclient.start();
				locationclient.requestLocation();
			}else{
				locationclient.requestLocation();
			}
		}
	}
	
	
	
	
	@Override
	public void onReceiveLocation(BDLocation arg0){
		if(arg0!=null&&arg0.getCity()!=null){
			//getCityID(arg0.getLongitude(), arg0.getLatitude());
			//System.out.println("当前城市====="+arg0.getCity());
			Log.i("onReceiveLocation","==========================当前城市="+arg0.getCity());
			if(weatherReceivedListener!=null){
				weatherReceivedListener.onLocationReceived(arg0);
			}
			getWeatherFromBaidu(arg0.getCity());
		}else{
			System.out.println("定位数据为空");
			Log.i("onReceiveLocation","==========================定位数据为空");
		}
		locationclient.stop();
	}

	
	/**
	 * 获取指定城市的天气
	 * @param areaId
	 */
	private void getWeather(final String areaId)
	{
		
		new WeatherAsyncTask(this) {
			@Override
			protected void onPostExecute(Weather arg0) {
				//保存天气信息
				if(arg0==null){
					System.out.println("未获得到天气数据");
					return;
				}
				System.out.println("空气质量=="+arg0.getAirQualityInfoTime());
				if(arg0!=null){
					if(weatherReceivedListener!=null){
						weatherReceivedListener.onWeatherReceived(arg0);
					}
				}
				CatService.this.onDestroy();
				
			}
		}.execute(areaId, Language.ZH_CN);
	}
	
	/**
	 * 根据城市名从百度获取天气
	 * @param cityname
	 */
	private void getWeatherFromBaidu(String cityname){
		
		String cit = null;
		try {
			 cit = URLEncoder.encode(uTil(cityname), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request("http://apis.baidu.com/apistore/weatherservice/cityname", "cityname="+cit);
		
	}
	
	private void request(String httpUrl, String httpArg) {
		new AsyncTask<String, String, String>() {

			
			@Override
			protected String doInBackground(String... params){
				BufferedReader reader = null;
			    String result = null;
			    StringBuffer sbf = new StringBuffer();
			    params[0] = params[0] + "?" + params[1];

			    try {
			        URL url = new URL(params[0]);
			        HttpURLConnection connection = (HttpURLConnection) url
			                .openConnection();
			        connection.setRequestMethod("GET");
			        // 填入apikey到HTTP header
			        connection.setRequestProperty("apikey",  "cb7f7e430cecde13fb8e88e3079187cd");
			        connection.connect();
			        InputStream is = connection.getInputStream();
			        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			        String strRead = null;
			        while ((strRead = reader.readLine()) != null) {
			            sbf.append(strRead);
			            sbf.append("\r\n");
			        }
			        reader.close();
			        result = sbf.toString();
			    } catch (Exception e) {
			        e.printStackTrace();
			    }
			    return result;
			}
			
			protected void onPostExecute(String result){
				if(result!=null){
					System.out.println(result);
					if(weatherReceivedListener!=null){
						weatherReceivedListener.onWeatherReceived(result);
					}
				}else{
					System.out.println("天气数据为空");
				}
				CatService.this.onDestroy();
			};
			
		}.execute(httpUrl,httpArg);
		
	    
	}
	
	
	private void getCityID(double lon,double lat){
		if(geoAreaAsyncTask==null){
			initWeatherTask();
		}
		geoAreaAsyncTask.execute(lon+"", lat+"", null, null);
	}
	
	//初始化weathertask
	private void initWeatherTask()
	{
		geoAreaAsyncTask = new GeoAreaAsyncTask(this) {
			@Override
			protected void onPostExecute(String arg0) {
				System.out.println("根据经纬度获取的地区信息=="+arg0);
				if(arg0==null||arg0.trim().equals("")){
					return;
				}
				JSONObject object = JSONObject.parseObject(arg0);
				
				//判断是否获取成功SUCCESS获取成功/FAILURE获取失败
				if(object==null){
					return;
				}
				String status = object.getString("status");
				if(status==null||!status.equals("SUCCESS")){
					return;
				}
					String areaId = object.getJSONObject("data").getString("areaId");
						//获取天气
						getWeather(areaId);
			}
		};
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		System.out.println("catService被销毁==ondestroy");
		if(locationclient!=null&&locationclient.isStarted()){
			locationclient.stop();
		}
		
	}
	
	private String uTil(String cityname){
		String name = null;
		if(cityname==null||cityname.equals("")){
			return null;
		}
		String lastone = lastSequence(cityname, 1);
		String lasttwo = lastSequence(cityname, 2);
		if(lastone!=null&&lastone.equals("市")){
			name = firstSequence(cityname, cityname.length()-1);
		}
		if(lasttwo!=null&&lasttwo.equals("地区")){
			name = firstSequence(cityname, cityname.length()-2);
		}
		return name;
	}
	
	/**
	 * 获取倒数几个字符
	 * @param last
	 * @return
	 */
	private String lastSequence(String s,int last){
		if(s==null||s.equals("")){
			return null;
		}
		if(s.length()<last){
			return null;
		}
		return s.substring(s.length()-last, s.length());
	}
	/**
	 * 获取前几个字符
	 * @param s
	 * @param first
	 * @return
	 */
	private String firstSequence(String s,int first){
		if(s==null||s.equals("")){
			return null;
		}
		if(s.length()<first){
			return null;
		}
		return s.substring(0, first);
	}

}
