package com.capitalbio.oemv2.myapplication.Utils;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;

import org.json.JSONObject;

/**
 * @author lzq
 * @Time 2016/1/22 9:40
 */
public class OutDoorAqiDataParser {

    public static String parse(String result){

        String aqi = "优";

        com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(result);
        if(jsonObject!=null){
            com.alibaba.fastjson.JSONObject data = jsonObject.getJSONObject("data");
            String message = jsonObject.getString("message");
            if(message.equals("success")){
                if(data!=null){
                    String level = data.getString("class");
                    if(level!=null&&!level.equals("")){
                        aqi = level;
                    }
                }
            }
        }

        return aqi;
    }
}
