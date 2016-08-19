package com.capitalbio.oemv2.myapplication.Comprehensive;

/**
 * @author lzq
 * @Time 2016/3/18 8:46
 */
public class JudgeByValueUtil {

    private float[] boundaryValue;
    private String[] description;


    public JudgeByValueUtil(float[] boundaryValue, String[] description) {
        this.boundaryValue = boundaryValue;
        this.description = description;
    }

    public String judge(float value){
        int length_value = boundaryValue.length;
        int length_des = description.length;

        if(length_value<1||length_des<1||(length_des-length_value)!=1){
            throw new RuntimeException("ParamsException");
        }

        for(int i=0;i<length_value;i++){
            if(value<boundaryValue[i]){
                return description[i];
            }
        }

        return description[length_des-1];
    }


}
