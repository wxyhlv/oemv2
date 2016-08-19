package com.capitalbio.oemv2.myapplication.Utils;

import android.content.Context;
import android.util.Log;

import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.Bean.LoginUser;

/**
 * @author lzq
 * @Time 2016/3/16 8:53
 */
public class SleepRuler {



    public static String ruler(Context context, float hour) {
        String s = "";
        int age = 0;
        //int age = Integer.parseInt(PreferencesUtils.getString(context, "age"));
        LoginUser user = MyApplication.getInstance().getCurrentUser();
        if(user != null){
            String birthString = MyApplication.getInstance().getCurrentUser().getAge();
            if(birthString != null){
                age = TimeStampUtil.getAgeByBirth(birthString);
            }
        }

        Log.e("sleepruler","age is " + age);
        Log.e("sleepruler","hour is " + hour);
        if (age <= 12) {
            //
            if (hour < 8 || hour > 12) {
                s = "较差";
            } else if (hour >= 8 && hour < 9) {
                s = "一般";
            } else if (hour >= 9 && hour < 10) {
                s = "良好";
            } else if (hour > 10 && hour <= 11) {
                s = "良好";
            } else if (hour > 11 && hour <= 12) {
                s = "一般";
            } else {
                s = "优质";
            }
        } else if (age > 12 && age <= 18) {
            //
            if(hour<7){
                s = "较差";
            }else if(hour>=7&&hour<8){
                s = "一般";
            }else if(hour>=8&&hour<9){
                s = "良好";
            }else if(hour==9){
                s = "优质";
            }else if(hour>9&&hour<=10){
                s = "良好";
            }else if(hour>10&&hour<=11){
                s = "一般";
            }else if(hour>11){
                s = "较差";
            }

        } else if (age > 18 && age < 60) {
            //
           if(hour<5){
               s = "较差";
           }else if(hour>=5&&hour<6){
               s = "一般";
           }else if(hour>=6&&hour<7){
               s = "良好";
           }else if(hour>=7&&hour<=8){
               s = "优质";
           }else if(hour>8&&hour<=9){
               s = "良好";
           }else if(hour>9&&hour<=10){
               s = "一般";
           }else if(hour>10){
               s = "较差";
           }
        } else {
            //
            if(hour<4.5){
                s = "较差";
            }else if(hour>=4.5&&hour<5.0){
                s = "一般";
            }else if(hour>=5.0&&hour<5.5){
                s = "良好";
            }else if(hour>=5.5&&hour<=7){
                s = "优质";
            }else if(hour>7&&hour<=8){
                s = "良好";
            }else if(hour>8&&hour<=9){
                s = "一般";
            }else if(hour>9){
                s = "较差";
            }
        }
        return s;
    }


}
