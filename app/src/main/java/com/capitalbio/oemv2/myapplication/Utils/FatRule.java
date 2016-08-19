package com.capitalbio.oemv2.myapplication.Utils;

import com.capitalbio.oemv2.myapplication.Bean.DeviceGrade;
import com.capitalbio.oemv2.myapplication.Const.BodyFatConst;

import java.text.DecimalFormat;

/**
 * Created by wxy on 16-3-8.
 *
 * 体重 骨含量没有写
 * height 单位为cm
 */
public class FatRule {
    public static DecimalFormat df = new DecimalFormat("#.00");
    public static  String getRate(float weight,int type,String sex,int age,int height,float value){

        switch (type){
            case BodyFatConst.TYPE_WEIGHT:
                float heightf = height/100f;
                OemLog.log("personifo","height is " + height + "value is " + value);
                if(value<18.5 * heightf*heightf){
                    return  "偏低";
                }else if(value>23.9 * heightf*heightf){
                    return  "偏高";
                }else if(18.5 * heightf * heightf <= value && value <= 23.9 * heightf * heightf){
                    return  "标准";
                }

                break;
            case BodyFatConst.TYPE_BMI:

                if(age>18){
                    if(value<18.5){
                        return  "偏低";
                    }else if(value>=23.9){
                        return  "偏高";
                    }else{
                        return "标准";
                    }
                }
                break;

            case BodyFatConst.TYPE_BMR:
                double base;
                if("男".equals(sex)){
                    base = 66+(13.7*weight)+5*height-(6.8*age);

                }else {
                     base = 655+(9.6*weight)+1.8*height-(4.7*age);
                }
                OemLog.log("bmrgrade","bmr grade is weight " + weight + "height is " + height + "age " +age );

                OemLog.log("bmrgrade","bmr grade is base " + base + "value is " + value + "low " +base * 0.9);
                if(value<base * 0.9){
                    return  "偏低";
                }
                if(value>base * 1.1){
                    return  "偏高";
                }else{
                    return "标准";
                }

            case BodyFatConst.TYPE_VISCERAL_LEVAL:
                if(value<10){
                    return  "标准";
                }else if(value>14){
                    return  "偏高";
                }else if(value>=10&&value<=14) {
                    return "稍微偏高";
                }

                break;

            case BodyFatConst.TYPE_BONECONTENT:
                OemLog.log("bmrgrade","bone grade is weight " + weight + "value is " + value );

                if("男".equals(sex)){
                    if(weight<60){
                        if(value <1.6){
                            return  "偏低";
                        }else if(value>3.8){
                            return "偏高";
                        }else{
                            return  "标准";
                        }
                    }else if(weight>75){
                        if(value <2.0){
                            return  "偏低";
                        }else if(value>4.1){
                            return "偏高";
                        }else{
                            return  "标准";
                        }

                    }else{
                        if(value <1.9){
                            return  "偏低";
                        }else if(value>4.0){
                            return "偏高";
                        }else{
                            return  "标准";
                        }

                    }

                }else{
                    if(weight<45){

                        if(value <1.3){
                            return  "偏低";
                        }else if(value>3.5){
                            return "偏高";
                        }else{
                            return  "标准";
                        }

                    }else if(weight>60){
                        if(value <1.8){
                            return  "偏低";
                        }else if(value>3.8){
                            return "偏高";
                        }else{
                            return  "标准";
                        }

                    }else{
                        if(value <1.5){
                            return  "偏低";
                        }else if(value>3.7){
                            return "偏高";
                        }else{
                            return  "标准";
                        }

                    }
                }


            case BodyFatConst.TYPE_FAT_RATION:
                if("男".equals(sex)){
                    if(age>=10&&age<=19){
                        if(value>=5&&value<=12){
                            return  "偏低";
                        }else if(value>=24.1){
                            return  "偏高";
                        }else {
                            return "标准";
                        }
                    }else if(age>=20 &&age<=29){
                        if(value>=5&&value<=13){
                            return  "偏低";
                        }else if(value>=25.1){
                            return  "偏高";
                        }else {
                            return "标准";
                        }
                    }
                    else if(age>=30 &&age<=39){
                        if(value>=5&&value<=14){
                            return  "偏低";
                        }else if(value>=26.1){
                            return  "偏高";
                        }else {
                            return "标准";
                        }
                    }
                    else if(age>=40 &&age<=49){
                        if(value>=5&&value<=15){
                            return  "偏低";
                        }else if(value>=27.1){
                            return  "偏高";
                        }else {
                            return "标准";
                        }
                    }
                    else if(age>=50 &&age<=59){
                        if(value>=5&&value<=16){
                            return  "偏低";
                        }else if(value>=28.1){
                            return  "偏高";
                        }else {
                            return "标准";
                        }
                    }
                    else if(age>=60 &&age<=69){
                        if(value>=5&&value<=17){
                            return  "偏低";
                        }else if(value>=29.1){
                            return  "偏高";
                        }else {
                            return "标准";
                        }
                    }else if(age>=70){
                        if(value>=5&&value<=18){
                            return  "偏低";
                        }else if(value>=29.1){
                            return  "偏高";
                        }else {
                            return "标准";
                        }
                    }
                }else{
                    if(age>=10&&age<=19){
                        if(value>=5&&value<=17){
                            return  "偏低";
                        }else if(value>=28.1){
                            return  "偏高";
                        }else {
                            return "标准";
                        }
                    }else if(age>=20 &&age<=29){
                        if(value>=5&&value<=18){
                            return  "偏低";
                        }else if(value>29.1){
                            return  "偏高";
                        }else {
                            return "标准";
                        }
                    }
                    else if(age>=30 &&age<=39){
                        if(value>=5&&value<=19){
                            return  "偏低";
                        }else if(value>30.1){
                            return  "偏高";
                        }else {
                            return "标准";
                        }
                    }
                    else if(age>=40 &&age<=49){
                        if(value>=5&&value<=20){
                            return  "偏低";
                        }else if(value>31.1){
                            return  "偏高";
                        }else {
                            return "标准";
                        }
                    }
                    else if(age>=50 &&age<=59){
                        if(value>=5&&value<=21){
                            return  "偏低";
                        }else if(value>32.1){
                            return  "偏高";
                        }else {
                            return "标准";
                        }
                    }
                    else if(age>=60 &&age<=69){
                        if(value>=5&&value<=22){
                            return  "偏低";
                        }else if(value>33.1){
                            return  "偏高";
                        }else {
                            return "标准";
                        }
                    }else if(age>=70){
                        if(value>=5&&value<=23){
                            return  "偏低";
                        }else if(value>33.1){
                            return  "偏高";
                        }else {
                            return "标准";
                        }
                    }
                }

                break;

            case BodyFatConst.TYPE_MUSLE_CONTENT:

                if("男".equals(sex)){

                    if(age>=10&&age<=14){
                        if(value<0.44){
                            return  "偏低";
                        }else if(value>0.57){
                            return  "偏高";
                        }else {
                            return "标准";
                        }
                    }else if(age>=15&&age<=19){
                        if(value<0.43){
                            return  "偏低";
                        }else if(value>0.56){
                            return  "偏高";
                        }else {
                            return "标准";
                        }
                    }
                    else if(age>=20 &&age<=29){
                        if(value<0.42){
                            return  "偏低";
                        }else if(value>0.54){
                            return  "偏高";
                        }else {
                            return "标准";
                        }
                    }
                    else if(age>=30 &&age<=39){
                        if(value<0.41){
                            return  "偏低";
                        }else if(value>0.52){
                            return  "偏高";
                        }else {
                            return "标准";
                        }
                    }
                    else if(age>=40 &&age<=49){
                        if(value<0.40){
                            return  "偏低";
                        }else if(value>0.50){
                            return  "偏高";
                        }else {
                            return "标准";
                        }
                    }
                    else if(age>=50 &&age<=59){
                        if(value<0.39){
                            return  "偏低";
                        }else if(value>0.48){
                            return  "偏高";
                        }else {
                            return "标准";
                        }
                    }
                    else if(age>=60 &&age<=69){
                        if(value<0.38){
                            return  "偏低";
                        }else if(value>0.47){
                            return  "偏高";
                        }else {
                            return "标准";
                        }
                    }else if(age>=70){
                        if(value<0.37){
                            return  "偏低";
                        }else if(value>0.46){
                            return  "偏高";
                        }else {
                            return "标准";
                        }
                    }
                }else{
                    if(age>=10&&age<=14){
                        if(value<0.36){
                            return  "偏低";
                        }else if(value>0.43){
                            return  "偏高";
                        }else {
                            return "标准";
                        }
                    }else if(age>=15&&age<=19){
                        if(value<0.35){
                            return  "偏低";
                        }else if(value>0.41){
                            return  "偏高";
                        }else {
                            return "标准";
                        }
                    }else if(age>=20 &&age<=29){
                        if(value<0.34){
                            return  "偏低";
                        }else if(value>0.39){
                            return  "偏高";
                        }else {
                            return "标准";
                        }
                    }
                    else if(age>=30 &&age<=39){
                        if(value<0.33){
                            return  "偏低";
                        }else if(value>0.38){
                            return  "偏高";
                        }else {
                            return "标准";
                        }
                    }
                    else if(age>=40 &&age<=49){
                        if(value<0.31){
                            return  "偏低";
                        }else if(value>0.36){
                            return  "偏高";
                        }else {
                            return "标准";
                        }
                    }
                    else if(age>=50 &&age<=59){
                        if(value<0.29){
                            return  "偏低";
                        }else if(value>0.34){
                            return  "偏高";
                        }else {
                            return "标准";
                        }
                    }
                    else if(age>=60 &&age<=69){
                        if(value<0.28){
                            return  "偏低";
                        }else if(value>0.33){
                            return  "偏高";
                        }else {
                            return "标准";
                        }
                    }else if(age>=70){
                        if(value<0.27){
                            return  "偏低";
                        }else if(value>0.32){
                            return  "偏高";
                        }else {
                            return "标准";
                        }
                    }
                }



                break;

            case BodyFatConst.TYPE_WATER_CONTENT:
                if("男".equals(sex)){
                    if(age>10){
                        if(value<0.5){
                            return  "偏低";
                        }else if(value>0.65){
                            return  "偏高";
                        }else {
                            return "标准";
                        }
                    }
                }else{
                    if(value<0.45){
                        return  "偏低";
                    }else if(value>0.60){
                        return  "偏高";
                    }else {
                        return "标准";
                    }
                }
                break;

        }

        return null;
    }

  public static DeviceGrade getDeviceGrade(int age,String sex,float weight,float height,int type){
      DeviceGrade grade = new DeviceGrade();
      if (type==BodyFatConst.TYPE_WEIGHT){
          DecimalFormat df = new DecimalFormat("#.0");
          float low = (float)((height / 100) * (height / 100) * 18.5);
          float mid =(float)((23.9 * (height / 100) * (height / 100)));
          float high =(float)((40 * (height / 100) * (height / 100)));
          grade.setType(type);
          grade.setValue_low((Float.parseFloat(df.format(low))));
          grade.setValue_mid((Float.parseFloat(df.format(mid))));
          grade.setValue_high(Float.parseFloat(df.format(high)));
      }
      if (type==BodyFatConst.TYPE_BMI){
          grade.setType(type);
          grade.setValue_low(18.5f);
          grade.setValue_mid(23.9f);
          grade.setValue_high(40f);
      }
      if (type==BodyFatConst.TYPE_BMR){
          grade.setType(type);
          double base;
          if("男".equals(sex)){
               base = 66+(13.7*weight)+5*height-(6.8*age);

          }else{
               base = 655+(9.6*weight)+1.8*height-(4.7*age);
          }
          float low = (float)(base * 0.9);
          float mid = (float)(base * 1.1);
          grade.setValue_low(Float.parseFloat(df.format(low)));
          grade.setValue_mid(Float.parseFloat(df.format(mid)) );
          grade.setValue_high(3000f);
      }

      if (type==BodyFatConst.TYPE_BONECONTENT){
          grade.setType(type);
          if("男".equals(sex)){
              if(weight<60){
                  grade.setValue_low(1.6f);
                  grade.setValue_mid(3.8f);
                  grade.setValue_high(9f);
              }else if(weight>75){
                  grade.setValue_low(2f);
                  grade.setValue_mid(4.1f);
                  grade.setValue_high(9f);
              }else {
                  grade.setValue_low(1.9f);
                  grade.setValue_mid(4.0f);
                  grade.setValue_high(9f);
              }
          }else{
              if(weight<45){
                  grade.setValue_low(1.3f);
                  grade.setValue_mid(3.5f);
                  grade.setValue_high(9f);
              }else if(weight>60){
                  grade.setValue_low(1.5f);
                  grade.setValue_mid(3.7f);
                  grade.setValue_high(9f);
              }else {
                  grade.setValue_low(1.8f);
                  grade.setValue_mid(3.8f);
                  grade.setValue_high(9f);
              }

          }

      }
      if (type==BodyFatConst.TYPE_FAT_RATION){
          grade.setType(type);
          if("男".equals(sex)){
              if(age>=10&&age<=19){
                  grade.setValue_low(12f);
                  grade.setValue_mid(24f);
                  grade.setValue_high(99f);
              }else if(age>=20 &&age<=29){
                  grade.setValue_low(13f);
                  grade.setValue_mid(25f);
                  grade.setValue_high(99f);
              }
              else if(age>=30 &&age<=39){
                  grade.setValue_low(14f);
                  grade.setValue_mid(26f);
                  grade.setValue_high(99f);
              }
              else if(age>=40 &&age<=49){
                  grade.setValue_low(15f);
                  grade.setValue_mid(27f);
                  grade.setValue_high(99f);
              }
              else if(age>=50 &&age<=59){
                  grade.setValue_low(16f);
                  grade.setValue_mid(28f);
                  grade.setValue_high(99f);
              }
              else if(age>=60 &&age<=69){
                  grade.setValue_low(17f);
                  grade.setValue_mid(29f);
                  grade.setValue_high(99f);
              }else if(age>=70){
                  grade.setValue_low(18f);
                  grade.setValue_mid(29f);
                  grade.setValue_high(99f);
              }
          }else{
              if(age>=10&&age<=19){
                  grade.setValue_low(17f);
                  grade.setValue_mid(28f);
                  grade.setValue_high(99f);
              }else if(age>=20 &&age<=29){
                  grade.setValue_low(18f);
                  grade.setValue_mid(29f);
                  grade.setValue_high(99f);
              }
              else if(age>=30 &&age<=39){
                  grade.setValue_low(19f);
                  grade.setValue_mid(30f);
                  grade.setValue_high(99f);
              }
              else if(age>=40 &&age<=49){
                  grade.setValue_low(20f);
                  grade.setValue_mid(31f);
                  grade.setValue_high(99f);
              }
              else if(age>=50 &&age<=59){
                  grade.setValue_low(21f);
                  grade.setValue_mid(32f);
                  grade.setValue_high(99f);
              }
              else if(age>=60 &&age<=69){
                  grade.setValue_low(22f);
                  grade.setValue_mid(33f);
                  grade.setValue_high(99f);
              }else if(age>=70){
                  grade.setValue_low(23f);
                  grade.setValue_mid(33f);
                  grade.setValue_high(99f);
              }
          }

      }
      if (type==BodyFatConst.TYPE_MUSLE_CONTENT){
          grade.setType(type);
          if("男".equals(sex)){
              if(age>=10&&age<=14){
                  grade.setValue_low(44f);
                  grade.setValue_mid(57f);
                  grade.setValue_high(99f);
              }
              else if(age>14&&age<=19){
                  grade.setValue_low(43f);
                  grade.setValue_mid(56f);
                  grade.setValue_high(99f);
              }
              else if(age>=20 &&age<=29){
                  grade.setValue_low(42f);
                  grade.setValue_mid(54f);
                  grade.setValue_high(99f);
              }
              else if(age>=30 &&age<=39){
                  grade.setValue_low(41f);
                  grade.setValue_mid(52f);
                  grade.setValue_high(99f);
              }
              else if(age>=40 &&age<=49){
                  grade.setValue_low(40f);
                  grade.setValue_mid(50f);
                  grade.setValue_high(99f);
              }
              else if(age>=50 &&age<=59){
                  grade.setValue_low(39f);
                  grade.setValue_mid(48f);
                  grade.setValue_high(99f);
              }
              else if(age>=60 &&age<=69){
                  grade.setValue_low(38f);
                  grade.setValue_mid(47f);
                  grade.setValue_high(99f);
              }else if(age>=70){
                  grade.setValue_low(37f);
                  grade.setValue_mid(46f);
                  grade.setValue_high(99f);
              }
          }else{
              if(age>=10&&age<=14){
                  grade.setValue_low(36f);
                  grade.setValue_mid(43f);
                  grade.setValue_high(99f);
              }else if(age>=15 &&age<=19){
                  grade.setValue_low(35f);
                  grade.setValue_mid(41f);
                  grade.setValue_high(99f);
              }
              else if(age>=20 &&age<=29){
                  grade.setValue_low(34f);
                  grade.setValue_mid(39f);
                  grade.setValue_high(99f);
              }
              else if(age>=30 &&age<=39){
                  grade.setValue_low(33f);
                  grade.setValue_mid(38f);
                  grade.setValue_high(99f);
              }
              else if(age>=40 &&age<=49){
                  grade.setValue_low(31f);
                  grade.setValue_mid(36f);
                  grade.setValue_high(99f);
              }
              else if(age>=50 &&age<=59){
                  grade.setValue_low(29f);
                  grade.setValue_mid(34f);
                  grade.setValue_high(99f);
              }
              else if(age>=60 &&age<=69){
                  grade.setValue_low(28f);
                  grade.setValue_mid(33f);
                  grade.setValue_high(99f);
              }else if(age>=70){
                  grade.setValue_low(27f);
                  grade.setValue_mid(32f);
                  grade.setValue_high(99f);
              }
          }

      }
      if(type==BodyFatConst.TYPE_WATER_CONTENT){
          if("男".equals(sex)){
              grade.setValue_low(50f);
              grade.setValue_mid(65f);
              grade.setValue_high(99f);
          }else{
              grade.setValue_low(45f);
              grade.setValue_mid(60f);
              grade.setValue_high(99f);
          }
      }
      if(type==BodyFatConst.TYPE_VISCERAL_LEVAL){
          grade.setValue_low(10f);
          grade.setValue_mid(14f);
          grade.setValue_high(15f);
      }

      return  grade;
  }

    /**
     * BMI计算
     *
     * */
    public static String computeBmi(float weight, float height) {
        DecimalFormat nf = new DecimalFormat("0.0");
        return nf.format(weight / ((height / 100) * (height / 100)));
    }

}
