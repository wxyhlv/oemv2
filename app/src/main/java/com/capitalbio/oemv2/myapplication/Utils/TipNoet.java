package com.capitalbio.oemv2.myapplication.Utils;

import com.capitalbio.oemv2.myapplication.Activity.MainActivity;
import com.capitalbio.oemv2.myapplication.View.views.SphygmomanometerTurntable;

/**
 * Created by chengkun on 16-3-31.
 */
public class TipNoet {
    public static String getTips(int devicesType, int dataType, float value) {
        switch (devicesType) {
            case MainActivity.CURRENT_DEVICES_AIRCAT:
                if(value > 0 && value <= 50) {
                    return "质量状况" + value + "优";
                } else if (value > 50 && value <= 100) {
                    return "质量状况" + value + "良";
                } else if (value > 100 && value <= 150) {
                    return "质量状况" + value + "轻度污染";
                } else if (value > 150 && value <= 200) {
                    return "质量状况" + value + "中度污染";
                } else if (value > 200 && value <= 300) {
                    return "质量状况" + value + "重度污染";
                } else if (value > 300 && value <= 500) {
                    return "质量状况" + value + "严重污染";
                }
                break;
            case MainActivity.CURRENT_DEVICES_BLOODPRESS:
                switch ((int) value) {
                    case SphygmomanometerTurntable.VALUE_LOW:
                        return "低压";
                    case SphygmomanometerTurntable.VALUE_MIDDLE:
                        return "理想";
                    case SphygmomanometerTurntable.VALUE_HIGH:
                        return "正常高值";
                    case SphygmomanometerTurntable.VALUE_HIGH1:
                        return "一级高血压";
                    case SphygmomanometerTurntable.VALUE_HIGH2:
                        return "二级高血压";
                    case SphygmomanometerTurntable.VALUE_HIGH3:
                        return "三级高血压";
                    case SphygmomanometerTurntable.VALUE_EXCEPTION:
                        return "测量异常";
                    default:
                        break;
                }
                break;
            case MainActivity.CURRENT_DEVICES_BODYFAT:
                if (value < 18.5) {
                    return "您的体重过轻，请加强营养";
                } else if (value >= 18.5 && value < 22.9) {
                    return "您的体重正常，继续保持";
                } else if (value > 23 && value <= 25) {
                    return "您的体重超重，请合理膳食";
                } else if (value > 25 && value <= 30) {
                    return "你属于I度肥胖，请控制饮食，加强运动";
                } else if (value > 30 && value <= 39.9) {
                    return "您属于II度肥胖，请控制饮食，加强运动";
                } else if (value > 40) {
                    return "您属于III度肥胖,请咨询医生";
                } else
                break;
            case MainActivity.CURRENT_DEVICES_BRACELETE:
                switch (dataType) {
                    case MainActivity.CURRENT_DEVICES_BRACELETE_SLEEP:
                        if (value < 6) {
                            return "睡眠时间偏少";
                        } else if (value >= 6 && value < 10) {
                            return "睡眠不错哟";
                        } else if (value > 10) {
                            return "睡眠眠时间偏多";
                        }
                        break;
                    case MainActivity.CURRENT_DEVICES_BRACELETE_SPORT:
                        if (value < 6000) {
                            return "您的运动量偏少";
                        } else if (value >= 6000 && value < 10000) {
                            return "您的运动量不错哟";
                        } else if (value >= 10000) {
                            return "您的运动量非常棒";
                        }
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        return "";
    }
}
