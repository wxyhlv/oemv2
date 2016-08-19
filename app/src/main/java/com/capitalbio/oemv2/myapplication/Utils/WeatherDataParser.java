package com.capitalbio.oemv2.myapplication.Utils;

/**
 * @author lzq
 * @Time 2016/1/22 11:23
 */
public class WeatherDataParser {

    public static String parseNumber(String number){
        String weathertext ="晴";
        if(number.equals("00")){}else
        if(number.equals("01")){
            weathertext = "多云";
        }else
        if(number.equals("02")){
            weathertext = "阴";
        }else
        if(number.equals("03")){
            weathertext = "阵雨";
        }else
        if(number.equals("04")){
            weathertext = "雷阵雨";
        }else
        if(number.equals("05")){
            weathertext = "雷阵雨伴有冰雹";
        }else
        if(number.equals("06")){
            weathertext = "雨夹雪";
        }else
        if(number.equals("07")){
            weathertext = "小雨";
        }else
        if(number.equals("08")){
            weathertext = "中雨";
        }else
        if(number.equals("09")){
            weathertext = "大雨";
        }else
        if(number.equals("10")){
            weathertext = "暴雨";
        }else
        if(number.equals("11")){
            weathertext = "大暴雨";
        }else
        if(number.equals("12")){
            weathertext = "特大暴雨";
        }else
        if(number.equals("13")){
            weathertext = "阵雪";
        }else
        if(number.equals("14")){
            weathertext = "小雪";
        }else
        if(number.equals("15")){
            weathertext = "中雪";
        }else
        if(number.equals("16")){
            weathertext = "大雪";
        }else
        if(number.equals("17")){
            weathertext = "暴雪";
        }else
        if(number.equals("18")){
            weathertext = "雾";
        }else
        if(number.equals("19")){
            weathertext = "冻雨";
        }else
        if(number.equals("20")){
            weathertext = "沙尘暴";
        }else
        if(number.equals("21")){
            weathertext = "小到中雨";
        }else
        if(number.equals("22")){
            weathertext = "中到大雨";
        }else
        if(number.equals("23")){
            weathertext = "大到暴雨";
        }else
        if(number.equals("24")){
            weathertext = "暴雨到大暴雨";
        }else
        if(number.equals("25")){
            weathertext = "大暴雨到特大暴雨";
        }else
        if(number.equals("26")){
            weathertext = "小到中雪";
        }else
        if(number.equals("27")){
            weathertext = "中到大雪";
        }else
        if(number.equals("28")){
            weathertext = "大到暴雪";
        }else
        if(number.equals("29")){
            weathertext = "浮尘";
        }else
        if(number.equals("30")){
            weathertext = "扬沙";
        }else
        if(number.equals("31")){
            weathertext = "强沙尘暴";
        }else
        if(number.equals("53")){
            weathertext = "霾";
        }else
        if(number.equals("99")){
            weathertext = "无";
        }


        return weathertext;
    }
}
