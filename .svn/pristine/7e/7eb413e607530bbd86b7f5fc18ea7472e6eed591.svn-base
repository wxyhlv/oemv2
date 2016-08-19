package com.capitalbio.oemv2.myapplication.Utils;

import android.util.Log;

import java.util.Calendar;

/**
 * @author lzq
 * @Time 2016/3/11 15:25
 */
public class CalendarUtil {





    //计算距离上周周日（西方里的一周的第一天）差几天
    private static int differPreviousSunday(){
        //Log.i("info","==========距离上周周日========="+(7+Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-1));
        return 7+Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-1;
    }

    //计算距离本周周日（西方里的一周的第一天）差几天
    private static int differThisSunday(){
        //Log.i("info","==========距离上周周日========="+(Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-1));
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-1;
    }

    //获取上周周日 零点的时间戳
    public static long timestampPreviousSundayBeforeDawnZeroPoint(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -differPreviousSunday());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        return calendar.getTimeInMillis();
    }

    //获取本周周日 零点的时间戳
    public static long timestampThisSundayBeforeDawnZeroPoint(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR,-differThisSunday());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND,0);
        return calendar.getTimeInMillis();
    }

    //获取上周周日 日期字符串
    public static String stringPreviousSunday(){
        Calendar calendar = Calendar.getInstance();
        //calendar.add(Calendar.MONTH,1);
        //differPreviousSunday();
        calendar.add(Calendar.DAY_OF_YEAR, -differPreviousSunday());
        return calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH);
    }

    //获取本周周日 日期字符串
    public static String stringThisSunday(){
        Calendar calendar = Calendar.getInstance();
        //calendar.add(Calendar.MONTH,1);
        //differThisSunday();
        calendar.add(Calendar.DAY_OF_YEAR,-differThisSunday());
        return calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH);
    }






}
