package com.capitalbio.oemv2.myapplication.Utils;

import android.util.Log;

import java.text.DecimalFormat;

/**
 * @author lzq
 * @Time 2016/1/28 10:28
 */
public class DecimalRoundingUtil {


    @Deprecated
    public static String rounding(String decimal, int digit) {
        String result = "";

        if (decimal == null || decimal.equals("")) {
            return "";
        }

        //判断字符窜是否是小数  如果不是就将原字符串返回

        boolean isDecimal = true;
        int lenght = decimal.length();
        Log.i("======","======字符串长度======"+lenght);
        for (int i = 0; i < lenght; i++) {
            String s = decimal.substring(i, i + 1);
            Log.i("=======", "======第" + i + "个字符=======" + s);

            if(isNumber(s)){

            }else{
                if(!s.equals(".")){
                    return decimal;
                }
            }
        }



        if (!decimal.contains(".")) {
            return decimal;
        }

        //判断小数点后位数

        //小数点的位置
        int indexofpoint = decimal.indexOf(".")+1;
        Log.i("======","======小数点的位置======"+indexofpoint);

        //小数点后有几位
        int digitafterpoint = lenght-indexofpoint;
        Log.i("======","======小数点后有几位======"+digitafterpoint);

        //如果小数点后的位数小于等于要求保留的位数，则返回原来的窜
        if(digitafterpoint<=digit){
            return decimal;
        }

        //获取按要求保留后的字符窜
        result = decimal.substring(0,indexofpoint+digit);
        Log.i("======","======按要求保留后======"+result);

        //判断舍去的位，决定是否五入
        String abandon = decimal.substring(indexofpoint+digit,indexofpoint+digit+1);
        Log.i("======", "=======舍弃位的第一位======" + abandon);

        int abandoni = Integer.parseInt(abandon);
        //当舍弃位大于等于5时入，比如保留两位的话，加0.01，保留一位的话，加0.1等。

        /**
         * 例外：如1.999  保留两位小数后，是2.0，而不是2.00
         * 原因：1.99+0.01 = 2.0
         */

        if(abandoni>=5){
           double resultd =  Double.parseDouble(result);
            resultd = resultd+(1/Math.pow(10,digit));
            Log.i("======","======四舍五入后======"+resultd);
            result = resultd+"";
        }



        return result;
    }

    public static String rounding(float decimal,int digit){
      return rounding(decimal,digit);
    }

    public static String rounding(double decimal,int digit){
        DecimalFormat decimalFormat = getFormat(digit);
        if(decimalFormat==null){
            return null;
        }
        return  decimalFormat.format(decimal);
    }

    public static String roundingNew(String decimal,int digit){
        double decimald = Double.parseDouble(decimal);
        return rounding(decimald,digit);
    }

    private static DecimalFormat getFormat(int digit){
        if(digit>=4){
            return null;
        }

        DecimalFormat decimalFormat =null;

        switch (digit)
        {
            case 0:
                decimalFormat = new DecimalFormat("0");
                break;
            case 1:
                decimalFormat = new DecimalFormat("0.0");
                break;
            case 2:
                decimalFormat = new DecimalFormat("0.00");
                break;
            case 3:
                decimalFormat = new DecimalFormat("0.000");
                break;
        }

        return decimalFormat;
    }

    private static boolean isNumber(String s) {


        if (s == null || s.equals("")) {
            return false;
        }

        boolean isZero;
        if (s.equals("0")) {
            isZero = true;
        } else {
            isZero = false;
        }

        boolean isOne;
        if (s.equals("1")) {
            isOne = true;
        } else {
            isOne = false;
        }


        boolean isTwo;
        if (s.equals("2")) {
            isTwo = true;
        } else {
            isTwo = false;
        }

        boolean isThree;
        if (s.equals("3")) {
            isThree = true;
        } else {
            isThree = false;
        }

        boolean isFour;
        if (s.equals("4")) {
            isFour = true;
        } else {
            isFour = false;
        }

        boolean isFive;
        if (s.equals("5")) {
            isFive = true;
        } else {
            isFive = false;
        }

        boolean isSix;
        if (s.equals("6")) {
            isSix = true;
        } else {
            isSix = false;
        }

        boolean isSeven;
        if (s.equals("7")) {
            isSeven = true;
        } else {
            isSeven = false;
        }

        boolean isEight;
        if (s.equals("8")) {
            isEight = true;
        } else {
            isEight = false;
        }

        boolean isNine;
        if (s.equals("9")) {
            isNine = true;
        } else {
            isNine = false;
        }

        if(isZero||isOne||isTwo||isThree||isFour||isFive||isSix||isSeven||isEight||isNine){
            return true;
        }else{
            return false;
        }
    }

}
