package com.capitalbio.oemv2.myapplication.Utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author lzq
 * @Time 2016/1/22 9:52
 */
public class WeatherAndTemParser{

    /**
     * 数组里分别存放 白天天气现象编号，夜间天气现象编号，白天天气温度，夜间天气温度
     * @param result
     * @return
     */

    public static String[] parse(String result){
         String[] hehe = new String[]{"0","0","0","0"};

        if(result!=null&&!result.equals("")){

            JSONObject jsonObject = JSONObject.parseObject(result);
            if(jsonObject!=null){
                JSONObject f = jsonObject.getJSONObject("f");
                if(f!=null){
                   JSONArray f1 = f.getJSONArray("f1");
                    if(f1!=null&&f1.size()>0){
                       JSONObject firstday =  f1.getJSONObject(0);
                        if(firstday!=null){
                            String fa = firstday.getString("fa");
                            String fb = firstday.getString("fb");
                            String fc = firstday.getString("fc");
                            String fd = firstday.getString("fd");

                            if(fa!=null&&!fa.equals("")){
                                hehe[0] = fa;
                            }
                            if(fb!=null&&!fb.equals("")){
                                hehe[1] = fb;
                            }
                            if(fc!=null&&!fc.equals("")){
                                hehe[2] = fc;
                            }
                            if(fd!=null&&!fd.equals("")){
                                hehe[3] = fd;
                            }

                        }
                    }
                }
            }

        }
        return hehe;
    }
}
