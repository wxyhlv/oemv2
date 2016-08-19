package com.capitalbio.oemv2.myapplication.Comprehensive;

import android.content.Context;

import com.capitalbio.oemv2.myapplication.Utils.PreferencesUtils;

/**
 * @author lzq
 * @Time 2016/3/18 9:57
 */
public class SleepRuler {



    public static String ruler(Context context,float hours){
        int age = Integer.parseInt(PreferencesUtils.getString(context,"age"));

        float[] value;
        String[] des= new String[]{"偏少","标准","偏多"};
        JudgeByValueUtil judgeByValueUtil;

        if(age<18){
            value = new float[]{9,12};
            judgeByValueUtil = new JudgeByValueUtil(value,des);
            return judgeByValueUtil.judge(hours);
        }else if(age>=18&&age<=60){
            value = new float[]{7,9};
            judgeByValueUtil = new JudgeByValueUtil(value,des);
            return judgeByValueUtil.judge(hours);
        }else{
            value = new float[]{6,7};
            judgeByValueUtil = new JudgeByValueUtil(value,des);
            return judgeByValueUtil.judge(hours);
        }

    }
}
