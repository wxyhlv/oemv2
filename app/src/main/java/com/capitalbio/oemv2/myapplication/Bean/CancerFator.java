package com.capitalbio.oemv2.myapplication.Bean;

import android.widget.RadioGroup;

import com.capitalbio.oemv2.myapplication.R;

/**
 * Created by wxy on 16-2-24.
 * 恶性肿瘤
 */
public class CancerFator {
    private String goodEatingHabits;

    private String excessiveDrinking;

    private String smoking;

    private String exerciseRegularly;

    private String airPollution;

    private String immunityStrong;

    private String hugeLifePressure;

    private String sensitive;
    private RadioGroup[] radioGroups;

    public void setGoodEatingHabits(String goodEatingHabits){
        this.goodEatingHabits = goodEatingHabits;
    }
    public String getGoodEatingHabits(){
        return this.goodEatingHabits;
    }
    public void setExcessiveDrinking (String excessiveDrinking ){
        this.excessiveDrinking = excessiveDrinking ;
    }
    public String getExcessiveDrinking (){
        return this.excessiveDrinking ;
    }
    public void setSmoking (String smoking ){
        this.smoking = smoking ;
    }
    public String getSmoking (){
        return this.smoking ;
    }
    public void setExerciseRegularly(String exerciseRegularly){
        this.exerciseRegularly = exerciseRegularly;
    }
    public String getExerciseRegularly(){
        return this.exerciseRegularly;
    }
    public void setAirPollution (String airPollution ){
        this.airPollution = airPollution ;
    }
    public String getAirPollution (){
        return this.airPollution ;
    }
    public void setImmunityStrong(String immunityStrong){
        this.immunityStrong = immunityStrong;
    }
    public String getImmunityStrong(){
        return this.immunityStrong;
    }
    public void setHugeLifePressure(String hugeLifePressure){
        this.hugeLifePressure = hugeLifePressure;
    }
    public String getHugeLifePressure(){
        return this.hugeLifePressure;
    }
    public void setSensitive(String sensitive){
        this.sensitive = sensitive;
    }
    public String getSensitive(){
        return this.sensitive;
    }

    public void setRadioGroups(RadioGroup[] radiogroups){

        this.radioGroups =radiogroups;
        if("是".equals(goodEatingHabits)){
            radioGroups[0].check(R.id.rg_cancer_1_yes);
        }
        if("否".equals(goodEatingHabits)){
            radioGroups[0].check(R.id.rg_cancer_1_no);
        }
        if("是".equals(excessiveDrinking)){
            radioGroups[1].check(R.id.rg_cancer_2_yes);
        }
        if("否".equals(excessiveDrinking)){
            radioGroups[1].check(R.id.rg_cancer_2_no);
        }
        if("是".equals(smoking)){
            radioGroups[2].check(R.id.rg_cancer_3_yes);
        }
        if("否".equals(smoking)){
            radioGroups[2].check(R.id.rg_cancer_3_no);
        }
        if("是".equals(exerciseRegularly)){
            radioGroups[3].check(R.id.rg_cancer_4_yes);
        }
        if("否".equals(exerciseRegularly)){
            radioGroups[3].check(R.id.rg_cancer_4_no);
        }
        if("是".equals(airPollution)){
            radioGroups[4].check(R.id.rg_cancer_5_yes);
        }
        if("否".equals(airPollution)){
            radioGroups[4].check(R.id.rg_cancer_5_no);
        }
        if("是".equals(immunityStrong)){
            radioGroups[5].check(R.id.rg_cancer_6_yes);
        }
        if("否".equals(immunityStrong)){
            radioGroups[5].check(R.id.rg_cancer_6_no);
        }
        if("是".equals(hugeLifePressure)){
            radioGroups[6].check(R.id.rg_cancer_7_yes);
        }
        if("否".equals(hugeLifePressure)){
            radioGroups[6].check(R.id.rg_cancer_7_no);
        }
        if("是".equals(sensitive)){
            radioGroups[7].check(R.id.rg_cancer_8_yes);
        }
        if("否".equals(sensitive)){
            radioGroups[7].check(R.id.rg_cancer_8_no);
        }

    }
}
