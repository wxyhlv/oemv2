package com.capitalbio.oemv2.myapplication.Comprehensive;

import android.content.Context;

import com.capitalbio.oemv2.myapplication.Utils.PreferencesUtils;

/**
 * @author lzq
 * @Time 2016/3/17 13:06
 */
public class MuscleContentRuler {





    public static String ruler(Context context,float content){
         String sex = PreferencesUtils.getString(context,"sex");
         int age = Integer.parseInt(PreferencesUtils.getString(context,"age"));

        if(sex.equals("男")){
            //男
            if(age<=19){
              return bj(content,0.38f,0.56f);
            }else if(age<=29){
                return bj(content,0.43f,0.64f);
            }else if(age<=39){
                return bj(content,0.47f,0.69f);
            }else if(age<=49){
                return bj(content,0.46f,0.62f);
            }else if(age<=59){
                return bj(content,0.44f,0.59f);
            }else if(age<=70){
                return bj(content,0.41f,0.58f);
            }else{
                return bj(content,0.40f,0.57f);
            }
        }else{
            //女
            if(age<=19){
                return bj(content,0.35f,0.56f);
            }else if(age<=29){
                return bj(content,0.38f,0.58f);
            }else if(age<=39){
                return bj(content,0.41f,0.58f);
            }else if(age<=49){
                return bj(content,0.40f,0.56f);
            }else if(age<=59){
                return bj(content,0.39f,0.55f);
            }else if(age<=70){
                return bj(content,0.35f,0.51f);
            }else{
                return bj(content,0.34f,0.50f);
            }
        }
    }

    private static String bj(float param,float bondaryone,float bondarytwo){
        if(param<bondaryone){
            return "偏低";
        }else if(param>=bondaryone&&param<=bondarytwo){
            return "标准";
        }else{
            return "偏高";
        }
    }

}
