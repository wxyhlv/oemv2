package com.capitalbio.oemv2.myapplication.Comprehensive;

import android.content.Context;

import com.capitalbio.oemv2.myapplication.Utils.PreferencesUtils;

/**
 * @author lzq
 * @Time 2016/3/18 13:45
 */
public class WeightRuler {

    /**
     *
     * @param context
     * @param weight 单位要求是千克
     * @return
     */
    public static String ruler(Context context,float weight){
        float height_cm = Integer.parseInt(PreferencesUtils.getString(context,"height"));
        float height_m = height_cm/100;
        float weight_judge_v = weight/height_m/height_m;

        float[] boundary_v = new float[]{18.5f,22.9f};
        String[] des = new String[]{"偏低","正常","偏高"};

        JudgeByValueUtil judgeByValueUtil = new JudgeByValueUtil(boundary_v,des);
        return judgeByValueUtil.judge(weight_judge_v);
    }

}
