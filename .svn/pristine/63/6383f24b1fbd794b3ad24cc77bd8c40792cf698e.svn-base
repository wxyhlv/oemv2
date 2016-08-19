package com.capitalbio.oemv2.myapplication.Utils;

import java.text.DecimalFormat;

/**
 * Created by wxy on 16-3-29.
 */
public class BraceleteRule {
//身高*系数*步数

    public static  String getDis(String sex,int height,int step){
        float result = 0;
        DecimalFormat format = new DecimalFormat("#0.00");
        if("男".equals(sex)){
            float base =0.415f;
             result = base * height *step/100/1000;
        }else{
            float base =0.413f;
             result = base * height *step/100/1000;
        }
        return  format.format(result)+"";
    }
}
