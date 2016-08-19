package com.capitalbio.oemv2.myapplication.Comprehensive;

import android.content.Context;

import com.capitalbio.oemv2.myapplication.Utils.PreferencesUtils;

/**
 * @author lzq
 * @Time 2016/3/18 10:39
 */
public class HeartrateRuler {

    public static String ruler(Context context,float times){
        int age = Integer.parseInt(PreferencesUtils.getString(context,"age"));

        float[] value;
        String[] des = new String[]{"过缓","正常","过速"};
        JudgeByValueUtil judgeByValueUtil;

        if(age<18){
            value = new float[]{100f,140f};
            judgeByValueUtil = new JudgeByValueUtil(value,des);
        }else if(age>=18&&age<=60){
            value = new float[]{60f,100f};
            judgeByValueUtil = new JudgeByValueUtil(value,des);
        }else{
            value = new float[]{50f,80f};
            judgeByValueUtil = new JudgeByValueUtil(value,des);
        }
        return judgeByValueUtil.judge(times);
    }

}
