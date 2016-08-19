package com.capitalbio.oemv2.myapplication.dataFormatCheck;

/**
 * Created by 129 on 2016/4/7.
 */
public class BloodPressFormatCheck {

    public static final int SYS = 0;
    public static final int DIA = 1;
    public static final int HR = 2;

    public static boolean isLegitimate(String value,int type){
        if(value==null||value.equals("")){
            return false;
        }
        //判断字符长度是否合法
        int len = value.length();
        if(len<1||len>3){
            return false;
        }
        //判断是否含有小数点
        if(value.contains(".")){
            return  false;
        }
        //判断第一个字符是否是零

            char first = value.charAt(0);
            if(first=='0'){
                return false;
            }


        int result = Integer.parseInt(value);

        switch (type)
        {
            case SYS:
                if(result<1||result>300){
                    return  false;
                }
                break;
            case DIA:
                if(result<1||result>200){
                    return  false;
                }
                break;
            case HR:
                if(result<1||result>300){
                    return  false;
                }
                break;
        }



        return true;
    }

}
