package com.capitalbio.oemv2.myapplication.Utils;

import android.content.Context;

/**
 * @author lzq
 * @Time 2016/3/16 16:38
 */
public class RateOfFatRuler {

    //sex
    private static final int MAN = 0;
    private static final int WOMEN = 1;

    //age
    private static final int ONE_SESSION = 2; //10-19
    private static final int TWO_SESSION = 3; //20-29
    private static final int THREE_SESSION = 4; //30-39
    private static final int FOUR_SESSION = 5; //40-49
    private static final int FIVE_SESSION = 6; //50-59
    private static final int SIX_SESSION = 7; //60-69
    private static final int SEVEN_SESSION = 8; // 70>

    //
    private static final float[] MAN_ONE = new float[]{12.1f,24f};
    private static final float[] MAN_TWO = new float[]{13.1f,25f};
    private static final float[] MAN_THREE = new float[]{14.1f,26f};
    private static final float[] MAN_FOUR = new float[]{15.1f,27f};
    private static final float[] MAN_FIVE = new float[]{16.1f,28f};
    private static final float[] MAN_SIX = new float[]{17.1f,29f};
    private static final float[] MAN_SEVEN = new float[]{18.1f,29f};

    //
    private static final float[] WOMEN_ONE = new float[]{17.1f,28f};
    private static final float[] WOMEN_TWO = new float[]{18.1f,29f};
    private static final float[] WOMEN_THREE = new float[]{19.1f,30f};
    private static final float[] WOMEN_FOUR = new float[]{20.1f,31f};
    private static final float[] WOMEN_FIVE = new float[]{21.1f,32f};
    private static final float[] WOMEN_SIX = new float[]{22.1f,33f};
    private static final float[] WOMEN_SEVEN = new float[]{23.1f,33f};

    //judge sex
    private static int judgeSex(Context context){
       String sex = PreferencesUtils.getString(context,"sex");
        if(sex!=null&&sex.equals("男")){
            return MAN;
        }else{
            return WOMEN;
        }
    }

    private static int judgeAge(Context context){
        int age = Integer.parseInt(PreferencesUtils.getString(context, "age"));
        if(age>=10&&age<=19){
            return ONE_SESSION;
        }
        if(age>=20&&age<=29){
            return TWO_SESSION;
        }
        if(age>=30&&age<=39){
            return THREE_SESSION;
        }
        if(age>=40&&age<=49){
            return FOUR_SESSION;
        }
        if(age>=50&&age<=59){
            return FIVE_SESSION;
        }
        if(age>=60&&age<=69){
            return SIX_SESSION;
        }
        if(age>=70){
            return  SEVEN_SESSION;
        }
        return SEVEN_SESSION;
    }

    public static RateFatRulerBean ruler(Context context,float fatrate){
        int sex = judgeSex(context);
        int age = judgeAge(context);

       float[] index = qiu(sex, age);

        float l_index = index[0];
        float r_index = index[1];

        String state = "";
        float rate = 1f;

        if(fatrate<l_index){
            state = "偏低";
            rate = ((l_index+r_index)/2-fatrate)/((l_index+r_index)/2);
        }else if(fatrate>=l_index&&fatrate<=r_index){
            state = "标准";
            rate = 0f;
        }else{
            state = "偏高";
            rate = (fatrate-(l_index+r_index)/2)/((l_index+r_index)/2);
        }

        RateFatRulerBean rateFatRulerBean = new RateFatRulerBean();

        rateFatRulerBean.setState(state);
        rateFatRulerBean.setRatio(rate);

        return rateFatRulerBean;
    }

    private static float[] qiu(int sex,int age){

        if(sex==MAN){
            if(age==ONE_SESSION){
                return MAN_ONE;
            }
            if(age==TWO_SESSION){
                return MAN_TWO;
            }
            if(age==THREE_SESSION){
                return MAN_THREE;
            }
            if(age==FOUR_SESSION){
                return MAN_FOUR;
            }
            if(age==FIVE_SESSION){
                return MAN_FIVE;
            }
            if(age==SIX_SESSION){
                return MAN_SIX;
            }
            if(age==SEVEN_SESSION){
                return MAN_SEVEN;
            }
        }else{
            if(age==ONE_SESSION){
               return WOMEN_ONE;
            }
            if(age==TWO_SESSION){
                return WOMEN_TWO;
            }
            if(age==THREE_SESSION){
                return WOMEN_THREE;
            }
            if(age==FOUR_SESSION){
                return WOMEN_FOUR;
            }
            if(age==FIVE_SESSION){
                return WOMEN_FIVE;
            }
            if(age==SIX_SESSION){
                return WOMEN_SIX;
            }
            if(age==SEVEN_SESSION){
                return WOMEN_SEVEN;
            }
        }
        return MAN_FIVE;
    }

    public static class RateFatRulerBean{
        String state;// des :high,low,normal
        float ratio; //high or low,how much high or low than normal;

        public RateFatRulerBean() {
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public float getRatio() {
            return ratio;
        }

        public void setRatio(float ratio) {
            this.ratio = ratio;
        }
    }

}
