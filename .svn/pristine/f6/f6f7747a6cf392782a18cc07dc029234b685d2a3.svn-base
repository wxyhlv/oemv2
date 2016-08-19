package com.capitalbio.oemv2.myapplication.Utils;

import com.capitalbio.oemv2.myapplication.Const.GlycolipidConst;

import java.text.DecimalFormat;

/**
 * Created by susu on 16-5-19.
 */
public class GlycolipidRule {
    public static DecimalFormat df = new DecimalFormat("#.00");

    public static String getLevel(float data,String state,int type){
        switch (type){
            case GlycolipidConst.TYPE_BLOOD:
                if (state.equals("空腹")){
                    if(data<=3.9){
                        return "低血糖";
                    }else if (data<=6.1){
                        return "正常";
                    }else {
                        return "高血糖";
                    }
                }else {
                    if (data<=3.9){
                        return "低血糖";
                    }else if (data<=7.8){
                        return "正常";
                    }else if(data>7.8){
                       return "高血糖";
                    }
                }
                break;
            case GlycolipidConst.TYPE_CHOLE:
                if (data<5.18){
                    return "正常";
                }else if (data<6.19){
                    return "边缘升高";
                }else if (data>=6.19){
                    return "升高";
                }
                break;
            case GlycolipidConst.TYPE_TRI:
                if (data<1.70){
                    return "正常";
                }else if (data<2.25){
                    return "边缘升高";
                }else if (data>=2.25){
                    return "升高";
                }
                break;
            case GlycolipidConst.TYPE_HIGNLIP:
                if (data<1.04){
                    return "降低";
                }else if (data<1.55){
                    return "正常";
                }else if (data>=1.55){
                    return "升高";
                }
            case GlycolipidConst.TYPE_LOWLIP:
                if (data<3.37){
                    return "正常";
                }else if (data<=4.12){
                    return "边缘升高";
                }else if (data>4.12){
                    return "升高";
                }
                break;
        }
        return null;
    }






}
