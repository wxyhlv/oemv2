package com.capitalbio.oemv2.myapplication.dataFormatCheck;

/**
 * Created by jiantaotu on 2016/4/7.
 */
public class BodyfatFormatCheck {

    public static boolean isLegitimate(String value){
        if(value==null||value.equals("")){
            return  false;
        }

        //判断字符长度是否合法
        int len = value.length();
        if(len<1||len>5){
            return false;
        }

        //判断第一个字符是否为1或.
        char first = value.charAt(0);
        if(first=='0'||first=='.'){
            return false;
        }

        float result = Float.parseFloat(value);
        if(result<1||result>150){
            return false;
        }

        return  true;
    }
}
