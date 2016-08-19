package com.capitalbio.oemv2.myapplication.Utils;

/**
 * @author lzq
 * @Time 2016/3/11 14:27
 */

import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.Bean.AirCatInfo;
import com.capitalbio.oemv2.myapplication.Bean.BloodPressureBean;
import com.capitalbio.oemv2.myapplication.Bean.BodyFatBean2;
import com.capitalbio.oemv2.myapplication.Bean.SportDataDetailBean;
import com.capitalbio.oemv2.myapplication.Devices.BodyFatDevices.src.com.bayh.sdk.ble.bean.BodyFatSaid;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;

/**
 * 从本地数据库提取数据
 */

public class DataExtractionUtil {

    //提取手环数据
    public static List<SportDataDetailBean> extractBraceletData(){
        DbManager dbManager = MyApplication.getDB();
        List<SportDataDetailBean> sportDataDetailBeans = new ArrayList<SportDataDetailBean>();

        try {
            sportDataDetailBeans = dbManager.selector(SportDataDetailBean.class).and("longTime",">=",CalendarUtil.timestampPreviousSundayBeforeDawnZeroPoint()).and("longTime","<=",CalendarUtil.timestampThisSundayBeforeDawnZeroPoint()).findAll();
        } catch (DbException e) {
            e.printStackTrace();
            sportDataDetailBeans = null;
        }

        return sportDataDetailBeans;
    }


    //提取空气猫数据
    public static List<AirCatInfo> extractAirCatData(){
        DbManager dbManager = MyApplication.getDB();
        List<AirCatInfo> airCatInfos = new ArrayList<AirCatInfo>();

        try {
            airCatInfos = dbManager.selector(AirCatInfo.class).and("testTime",">=",CalendarUtil.timestampPreviousSundayBeforeDawnZeroPoint()).and("testTime","<=",CalendarUtil.timestampThisSundayBeforeDawnZeroPoint()).findAll();
        } catch (DbException e) {
            e.printStackTrace();
            airCatInfos = null;
        }

        return airCatInfos;
    }

    //提取血压计数据
    public static List<BloodPressureBean> extractBloodPressureData() {
        DbManager dbManager = MyApplication.getDB();
        List<BloodPressureBean> bloodPressureBeans = new ArrayList<BloodPressureBean>();
        try {
            bloodPressureBeans = dbManager.selector(BloodPressureBean.class).and("testTime", ">=", CalendarUtil.timestampPreviousSundayBeforeDawnZeroPoint()).and("testTime", "<=", CalendarUtil.timestampThisSundayBeforeDawnZeroPoint()).findAll();
        } catch (DbException e) {
            e.printStackTrace();
            bloodPressureBeans = null;
        }

        return bloodPressureBeans;
    }

    //提取体脂称数据
    public static List<BodyFatSaid> extractBodyFatData(){
        DbManager dbManager = MyApplication.getDB();
        List<BodyFatSaid> bodyFatSaids = new ArrayList<BodyFatSaid>();

        try {
            bodyFatSaids = dbManager.selector(BodyFatSaid.class).and("testTime",">=",CalendarUtil.timestampPreviousSundayBeforeDawnZeroPoint()).and("testTime","<=",CalendarUtil.timestampThisSundayBeforeDawnZeroPoint()).findAll();
        } catch (DbException e) {
            e.printStackTrace();
            bodyFatSaids = null;
        }

        return bodyFatSaids;
    }

}
