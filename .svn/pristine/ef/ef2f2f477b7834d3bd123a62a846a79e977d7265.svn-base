package com.capitalbio.oemv2.myapplication.Utils;

import android.content.Context;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.capitalbio.oemv2.myapplication.R;

/**
 * @author lzq
 * @Time 2016/1/21 14:52
 */
public class BaiDuLocation {

    //private static BaiDuLocation baiDuLocation;

    public BaiDuLocation(){};

    private Context context;

    /*public static BaiDuLocation getInstance(){
        if(baiDuLocation == null){
            baiDuLocation = new BaiDuLocation();
        }
        return baiDuLocation;
    }*/

    private BDLocationListener listener;

    public  void setBDLocationListener(Context context,BDLocationListener locationListener){

            this.context = context;

        //if(listener==null){
            listener = locationListener;
        //}

    }

    private LocationClient locationclient;

    public  void locate(){
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

    public void stop(){
        if(locationclient!=null){
            locationclient.stop();
        }
        locationclient = null;

    }

    //初始化百度定位
    private void initLocationClient(){
        locationclient = new LocationClient(context);
//		locationclient.setAccessKey("402dc2667328563072e70e1e6a5c1fc2");
        //locationclient.setAccessKey("TF8bNyuokLMbZ2uyTy1N009v");
        locationclient.registerLocationListener(listener);
        LocationClientOption option = new LocationClientOption();
//		option.setOpenGps(true);                                //是否打开GPS
        option.setCoorType("bd09ll");                           //设置返回值的坐标类型。
        option.setPriority(LocationClientOption.NetWorkFirst);  //设置定位优先级
        option.setProdName(context.getResources().getString(R.string.app_name2));//设置产品线名称。强烈建议您使用自定义的产品线名称，方便我们以后为您提供更高效准确的定位服务。
        option.setScanSpan(10 * 60 * 1000);                          //设置定时定位的时间间隔。单位毫秒  (定位成功后就停止定位,只有定位失败时才会有效)
        option.setAddrType("all");// all时，表示返回地址信息。
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
        option.setNeedDeviceDirect(false);
        option.setIsNeedAddress(true);
        locationclient.setLocOption(option);
    }




}
