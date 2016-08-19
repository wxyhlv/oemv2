package com.capitalbio.oemv2.myapplication.Utils;

import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.capitalbio.oemv2.myapplication.Activity.LoginActivity;
import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.Bean.BraceleteHistory;
import com.capitalbio.oemv2.myapplication.Bean.BraceleteJson;
import com.capitalbio.oemv2.myapplication.Bean.BraceleteSleepDataForMonth;
import com.capitalbio.oemv2.myapplication.Bean.SleepDataTotalJson;
import com.capitalbio.oemv2.myapplication.Bean.SleepDetailJson;
import com.capitalbio.oemv2.myapplication.Bean.SportDataDetailBean;
import com.capitalbio.oemv2.myapplication.BraceleteLib.SleepDataTotalBean;
import com.capitalbio.oemv2.myapplication.BraceleteLib.SleepDetailData;
import com.capitalbio.oemv2.myapplication.Const.Base_Url;
import com.capitalbio.oemv2.myapplication.Const.BraceleteConst;
import com.capitalbio.oemv2.myapplication.NetUtils.HistoryDownd;
import com.capitalbio.oemv2.myapplication.NetUtils.NetTool;

import junit.framework.TestCase;

import org.xutils.db.Selector;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.db.table.DbModel;
import org.xutils.db.table.TableEntity;
import org.xutils.ex.DbException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.SimpleFormatter;

/**
 * Created by wxy on 16-3-18.
 */
public class BraceleteDataService {

    private static String TAG = "BraceleteDataService";



    /**
     * 获取昨天（晚上8：00到今天8：00的）睡眠总数据
     */
    public static SleepDataTotalJson getSleepTotalData(String day){
        String  lasttime_ = TimeStampUtil.getSpecifiedDayBefore(day)+" 20:00:00";
        long lasttime  = TimeStampUtil.getLongTime(lasttime_);
        String todayTime_ =  TimeStampUtil.currTime2(1)+" 20:00:00";
        long todayTime = TimeStampUtil.getLongTime(todayTime_);
        String[] coloumExpress = new String[]{"SUM(sumNumber) as 'totalSleeptime'","SUM(seepSleepTime) as 'sleeptime'",
                "SUM(somnolenceTime) as 'somnolenceTime'","SUM(soberTime) as 'soberTime'","SUM(rouseNumber) as 'rouseNumber'"};
        //总时间，深度睡眠时间，浅度睡眠时间，清醒时间，清醒次数
        /*
        * TODO
        * .and("longTime", ">=", lasttime).
                            and("longTime", "<=", todayTime)
        * */
        try {
            DbModel all = MyApplication.getDB().selector(SleepDataTotalBean.class)
                    .where("username", "=", MyApplication.getInstance().getCurrentUserName())
                    .select(coloumExpress).findFirst();

            if(all!=null){
                BraceleteSleepDataForMonth monthbean = new BraceleteSleepDataForMonth();

                SleepDataTotalJson bean = new SleepDataTotalJson();

                int total = all.getInt("totalSleeptime");
                int total1 = all.getInt("sleeptime");
                int total2 = all.getInt("somnolenceTime");
                int total3 = all.getInt("soberTime");
                int total4 = all.getInt("rouseNumber");
                bean.setTotalTime(total);
                bean.setDeepSleepTime(total1);
                bean.setShallowSleepTime(total2);
                bean.setAwakeTime(total3);
                bean.setWakeUpNumber(total4);

                //插入每天的睡眠总数据，用于月统计
              /*  monthbean.setDay(day);
                monthbean.setDeeptime(total1 + "分");
                monthbean.setQianshuitime(total2 + "分");
                monthbean.setTotalSleeptime(total + "分");
                MyApplication.getDB().saveOrUpdate(monthbean);*/

                Log.i("sleepTotal", "sleepTotal " + BraceleteConst.day + " " + lasttime_ + "" + lasttime + " " + todayTime_ + " " + todayTime + " " + total4
                        + all.getDataMap().toString());
                return bean;
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }




    /**
     * 从网上获取睡眠每个月的总数据
     */
    public static void getSleepTotalDataByNet() {
        String url = Base_Url.downloadtotalUrl;
        String month = BraceleteConst.month;
        String year_ = month.substring(0,4);
        String month_ = month.substring(5, month.length());
        HashMap<String, Object> param = new HashMap<>();
        param.put("modelType", "braceletSleepTotal");
        param.put("url", url);
        param.put("beginDate", month + "-01 00:00:00");
        param.put("endDate",  TimeStampUtil.getLastDayOfMonth(Integer.valueOf(year_),Integer.valueOf(month_))+" 23:59:59");
        OemLog.log(TAG, "从网上获取睡眠月的总数据" + param.toString());
        HistoryDownd.getHistory(MyApplication.getInstance(), param, new HistoryDownd.RegistOnGetResult() {
            @Override
            public void OnGetResult(int code, String msg, String data) {
                List<BraceleteSleepDataForMonth> list = JSON.parseArray(data, BraceleteSleepDataForMonth.class);
                //插入数据库
                if (list != null && list.size() > 0) {
                    /*try {
                        MyApplication.getDB().delete(SleepDetailData.class, WhereBuilder.b("userName", "=", list.get(0).getUsername()));
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                    for (int i = 0; i < list.size(); i++) {
                        SleepDetailData bean = new SleepDetailData();
                        bean.setIsUpload(true);
                        bean.setUserName(list.get(i).getUsername());
                        bean.setSleepType(list.get(i).getSleepType());
                        long time = list.get(i).getTestTime();
                        bean.setLongSleepTime(time);
                        bean.setFomatTime(TimeStampUtil.currTime3(time + ""));

                        bean.setYmd(TimeStampUtil.currTime3(time));
                        bean.setHour(TimeStampUtil.getHour(time) + "");
                        try {

                            MyApplication.getDB().save(bean);
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    }*/
                    //   getSleepDetailDataByDB();
                }

            }
        });

    }

    /**
     * 统计每天的24小时的运动数据柱状图
     * day yyyy-mm-dd
     */

    public static  List<BraceleteHistory> getSportDataDayDb(String day){

        List<BraceleteHistory> datas = new ArrayList<>();
        //mac = '"+ PreferencesUtils.getString(mContext,"default_bracelete_address")

        String sql = "select longTime,hour,SUM(steps),SUM(distance),SUM(cal) " +

                "from SportDataDetailBean " +
                " where username = '" + MyApplication.getInstance().getCurrentUserName()+"'  and ymd like '%" + day + "%' " +
                "group by hour  "+
                "order by longTime asc";
        try {
            TableEntity<?> table = TableEntity.get(MyApplication.getDB(), SportDataDetailBean.class);
            if (table.tableIsExist()) {
                Cursor cursor = MyApplication.getDB().execQuery(sql);
                while (cursor.moveToNext()) {
                    BraceleteHistory bean = new BraceleteHistory();
                    bean.setHour(cursor.getString(1));
                    bean.setSteps(cursor.getString(2));
                    Log.i("bracelete", "steps is " + cursor.getString(2));
                    bean.setDistance(cursor.getString(3));
                    bean.setCal(cursor.getString(4));
                    datas.add(bean);
                }
                Log.i("bracelete", "查询结果是： " + datas.size());
            }
        } catch (DbException e) {
            Log.i("bracelete", "find db exception" + e.getMessage());
            e.printStackTrace();
        }
        return datas;
    }


    /**
     * 统计每天运动总数据柱
     * day yyyy-mm-dd
     */

    public static  BraceleteHistory getSportTotalDataDayDb(String day){

        BraceleteHistory data = new BraceleteHistory();
        //mac = '"+ PreferencesUtils.getString(mContext,"default_bracelete_address")

        String sql = "select SUM(steps),SUM(distance),SUM(cal) " +

                "from SportDataDetailBean " +
                " where username = '" + MyApplication.getInstance().getCurrentUserName()+"'  and ymd like '%" + day + "%' " ;

        try {
            TableEntity<?> table = TableEntity.get(MyApplication.getDB(), SportDataDetailBean.class);
            if (table.tableIsExist()) {
                Cursor cursor = MyApplication.getDB().execQuery(sql);
                while (cursor.moveToNext()) {
                    BraceleteHistory bean = new BraceleteHistory();
                    bean.setSteps(cursor.getString(0));
                    Log.i("bracelete", "steps is " + cursor.getString(0));
                    bean.setDistance(cursor.getString(1));
                    bean.setCal(cursor.getString(2));
                    return bean;
                }
            }
        } catch (DbException e) {
            Log.i("bracelete", "find db exception" + e.getMessage());
            e.printStackTrace();
        }
        return data;
    }




    /**
     * 获取月度运动记录
     * @param month
     * yyyy-mm
     */
    public static List<BraceleteHistory> getSportMonthData(String month) {
        List<BraceleteHistory> list = new ArrayList<>();
        String sql = "select sum(steps) , sum(cal),sum(distance),day,ymd,longTime "
                +" from SportDataDetailBean "+
                " where  username = '"+ MyApplication.getInstance().getCurrentUserName()
                +"' and " +"ymd like '%"+month +"%' group by  day"
                + " order by longTime"
                ;

        try {
            TableEntity<?> table = TableEntity.get(MyApplication.getDB(), SportDataDetailBean.class);
            if (table.tableIsExist()) {
                Cursor cursor = MyApplication.getDB().execQuery(sql);
                while (cursor.moveToNext()) {
                    BraceleteHistory bean = new BraceleteHistory();
                    bean.setSteps(cursor.getString(0));
                    bean.setCal(cursor.getString(1));
                    bean.setDistance(cursor.getString(2));
                    bean.setDay(cursor.getString(3));
                    list.add(bean);
                    Log.i("bracelete", "月运动查询结果是" + bean.getSteps() + "  day  " + bean.getDay());

                }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

        Log.i("bracelete", "月运动查询结果是" + list.size());
        return list;
    }

    /**
     * 获取月度睡眠记录
     * @param month
     * yyyy-mm
     */
    public static List<SleepDataTotalBean> getSleepMonthData(String month) {
        Log.i("braceletesleep", "1,从数据库中获取手环月睡眠数据。。。。。。。。。。。。。。" );

        List<SleepDataTotalBean> list = new ArrayList<>();

        try {
            //TODO .and("ymd", "like ", " '%" + month + "%' ")
            String singleMonth = TimeStampUtil.getSingleYm(month);
            Log.i("braceletesleep", "2,singleMonth 月  " + singleMonth);

            String columnExp[] = {"ymd","SUM(sumNumber)","SUM(seepSleepTime)","SUM(somnolenceTime)","SUM(soberTime)"};
            List<DbModel> all = MyApplication.getDB().selector(SleepDataTotalBean.class).
                    where("username", "=", MyApplication.getInstance().getCurrentUserName())
                    .and("ymd","like","%" + month + "%")
                    .or("ymd","like","%" + singleMonth + "%")
                    .groupBy("ymd").select(columnExp).findAll();

            if(all!=null&&!all.isEmpty()){
                Log.i("braceletesleep", "3，数据库的月睡眠查询结果是" + all.size());
                for (int i = 0;i< all.size();i++){
                    SleepDataTotalBean bean = new SleepDataTotalBean();
                    bean.setSumNumber(all.get(i).getInt("SUM(sumNumber)"));
                    bean.setSeepSleepTime(all.get(i).getInt("SUM(seepSleepTime)"));
                    bean.setSomnolenceTime(all.get(i).getInt("SUM(somnolenceTime)"));
                    bean.setSoberTime(all.get(i).getInt("SUM(soberTime)"));
                    bean.setYmd(all.get(i).getString("ymd"));
                    Log.i("braceletesleep", "3.1月睡眠查询结果是" +bean.toString());

                    list.add(bean);
                }

                return list ;
            }
            Log.i("braceletesleep", "3.2数据库的月睡眠查询结果为null");

        } catch (DbException e) {
            Log.i("braceletesleep", "数据库的月睡眠查询异常" + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }


    /**
     *
     * 运动每天的明细请求
     *
     *
     */
}
