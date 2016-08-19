package com.capitalbio.oemv2.myapplication.Utils;

import com.capitalbio.oemv2.myapplication.FirstPeriod.Utils.HTG;

import java.util.Calendar;

/**
 * @author lzq
 * @Time 2016/3/3 10:43
 */
public class TimeStampCreater{

    public TimeStampCreater() {
    }

    public long create(String month,String day,String hour,String minute){
        return create(null,month,day,hour,minute,null);
    }



    public long create(String month,String day,String hour,String minute,String second){
        return create(null,month,day,hour,minute,second);
    }

    public long create(int year,int month,int day,int hour,int minute,int second){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute, second);
        return calendar.getTimeInMillis();
    }

    public long create(String year,String month,String day,String hour,String minute,String second){
        int intyear = HTG.currYear();
        if(year!=null&&!year.equals("")){
            intyear = Integer.parseInt(year);
        }
        int intmonth = HTG.currMonth();
        if(month!=null&&!month.equals("")){
            intmonth = Integer.parseInt(month)-1;
        }
        int intday = HTG.currDay();
        if(day!=null&&!day.equals("")){
            intday = Integer.parseInt(day);
        }
        int inthour = HTG.currHour();
        if(hour!=null&&!hour.equals("")){
            inthour = Integer.parseInt(hour);
        }
        int intminute = HTG.currMinute();
        if(minute!=null&&!minute.equals("")){
            intminute = Integer.parseInt(minute);
        }
        int intsecond = 0;
        if(second!=null&&!second.equals("")){
            intsecond = Integer.parseInt(second);
        }
        return create(intyear,intmonth,intday,inthour,intminute,intsecond);
    }
}
