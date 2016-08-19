package com.capitalbio.oemv2.myapplication.Utils;

import com.alibaba.fastjson.JSONObject;

/**
 * @author lzq
 * @Time 2016/1/22 13:27
 */
public class AirCatWifiDataParser {

    /**
     * 服务器返回空气猫数据的解析
     * 返回数组：pm2.5,formal,tem,hum,tvoc,poll
     *
     * @param result
     * @return
     */

    public static String[] parse(String result) {
        String[] hehe = new String[]{"0", "0", "0", "0", "0", "0"};

        if (result != null && !result.equals("")) {
            JSONObject jsonObject = JSONObject.parseObject(result);
            if (jsonObject != null) {
                String msg = jsonObject.getString("message");
                String data = jsonObject.getString("data");

                if (msg != null && !msg.equals("")) {
                    if (data != null && !data.equals("")) {
                        JSONObject jsonObject1 = JSONObject.parseObject(data.trim());
                        if (jsonObject1 != null) {


                            String pm25 = jsonObject1.getString("pm25");
                            //保留一位
                            //pm25 = subNumber(pm25,1);
                            pm25 = DecimalRoundingUtil.roundingNew(pm25, 1);
                            String ch2 = jsonObject1.getString("ch2");
                            //保留两位
                            //ch2 = subNumber(ch2,2);
                            ch2 = DecimalRoundingUtil.roundingNew(ch2, 2);
                            String temperature = jsonObject1.getString("temperature");
                            //保留一位
                            //temperature = subNumber(temperature,1);
                            temperature = DecimalRoundingUtil.roundingNew(temperature, 1);
                            String humity = jsonObject1.getString("humity");
                            //保留一位
                            //humity = subNumber(humity, 1);
                            humity = DecimalRoundingUtil.roundingNew(humity,1);

                            String tvoc = jsonObject1.getString("tvoc");
                            //保留两位
                            //tvoc = subNumber(tvoc, 2);
                            tvoc = DecimalRoundingUtil.roundingNew(tvoc,2);

                            String pollutionLevel = jsonObject1.getString("pollutionLevel");
                            //只保留整数
                            //pollutionLevel = subNumber(pollutionLevel, -1);
                            pollutionLevel = DecimalRoundingUtil.roundingNew(pollutionLevel,0);

                            if (pm25 != null && !pm25.equals("")) {
                                hehe[0] = pm25;
                            }
                            if (ch2 != null && !ch2.equals("")) {
                                hehe[1] = ch2;
                            }
                            if (temperature != null && !temperature.equals("")) {
                                hehe[2] = temperature;
                            }
                            if (humity != null && !humity.equals("")) {
                                hehe[3] = humity;
                            }
                            //
                            if (tvoc != null && !tvoc.equals("")) {
                                hehe[4] = tvoc;
                            }

                            if (pollutionLevel != null && !pollutionLevel.equals("")) {
                                hehe[5] = pollutionLevel;
                            }

                        }

                    }
                }
            }
        }


        return hehe;
    }

    /**
     * @param bit 小数点后的保留位数
     * @return
     */
    private static String subNumber(String number, int bit) {

        String newnumber = "";

        if (!number.contains(".")) {
            return number;
        }

        //判断小数点后有几位

        int ws = number.length() - number.indexOf(".") - 1;
        //Log.i("=========","=======小数点后位数======"+ws);
        if (ws <= bit) {
            return number;
        }

        newnumber = number.substring(0, number.indexOf(".") + bit + 1);

        return newnumber;
    }


    public static int parseLevel(String v, int flag) {

        float value = Float.parseFloat(v);

        int level = -1;
        switch (flag) {
            case PM:
                if (value < 35) {
                    level = 1;
                } else if (value < 75) {
                    level = 2;
                } else if (value < 115) {
                    level = 3;
                } else if (value < 150) {
                    level = 4;
                } else if (value < 250) {
                    level = 5;
                } else {
                    level = 6;
                }

                break;
            case FORMAL:
                if (value <=0.08) {
                    level = 1;
                } else if (value <=0.1) {
                    level = 2;
                } else if (value <= 0.3) {
                    level = 3;
                } else if (value <= 0.5) {
                    level = 4;
                } else if (value <= 0.7) {
                    level = 5;
                } else {
                    level = 6;
                }
                break;
            case TEM:
                if (value <22) {
                    level = 1;
                } else if (value < 28) {
                    level = 2;
                } else{
                    level = 3;
                }
                break;
            case HUM:
                if (value < 30) {
                    level = 1;
                } else if (value < 80) {
                    level = 2;
                } else {
                    level = 3;
                }

                break;
            case TVOC:
                if (value < 0.3) {
                    level = 1;
                } else if (value < 0.6) {
                    level = 2;
                } else if (value < 1.2) {
                    level = 3;
                } else if (value < 1.8) {
                    level = 4;
                } else if (value < 2.5) {
                    level = 5;
                } else {
                    level = 6;
                }

                break;
            case POLL:
                if (value < 50) {
                    level = 1;
                } else if (value < 100) {
                    level = 2;
                } else if (value < 150) {
                    level = 3;
                } else if (value < 200) {
                    level = 4;
                } else if (value < 300) {
                    level = 5;
                } else {
                    level = 6;
                }

                break;

        }
        return level;
    }

    public static final int PM = 0;
    public static final int FORMAL = 1;
    public static final int TEM = 2;
    public static final int HUM = 3;
    public static final int TVOC = 4;
    public static final int POLL = 5;
}
