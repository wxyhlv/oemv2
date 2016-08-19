package com.capitalbio.oemv2.myapplication.Bean;

import android.widget.RadioGroup;

import com.capitalbio.oemv2.myapplication.R;

/**
 * Created by wxy on 16-2-24.
 * 高血压疾病的致病因素
 */
public class HbloodPresureFator {
    private String exsessiveDrinking;

    private String smoke;

    private String chronicStress;

    private String familyHistoryHypertension;

    private String badEatingHabits;

    private String largeNoise;

    private String astriction;

    private String regular_use_vasopressors;

    private String educationalLevel;

    private RadioGroup[] radioGroups = new RadioGroup[9];

    public void setExsessiveDrinking(String exsessiveDrinking){
        this.exsessiveDrinking = exsessiveDrinking;
    }
    public String getExsessiveDrinking(){
        return this.exsessiveDrinking;
    }
    public void setSmoke(String smoke){
        this.smoke = smoke;
    }
    public String getSmoke(){
        return this.smoke;
    }
    public void setChronicStress(String chronicStress){
        this.chronicStress = chronicStress;
    }
    public String getChronicStress(){
        return this.chronicStress;
    }
    public void setFamilyHistoryHypertension(String familyHistoryHypertension){
        this.familyHistoryHypertension = familyHistoryHypertension;
    }
    public String getFamilyHistoryHypertension(){
        return this.familyHistoryHypertension;
    }
    public void setBadEatingHabits(String badEatingHabits){
        this.badEatingHabits = badEatingHabits;
    }
    public String getBadEatingHabits(){
        return this.badEatingHabits;
    }
    public void setLargeNoise(String largeNoise){
        this.largeNoise = largeNoise;
    }
    public String getLargeNoise(){
        return this.largeNoise;
    }
    public void setAstriction(String astriction){
        this.astriction = astriction;
    }
    public String getAstriction(){
        return this.astriction;
    }
    public void setRegular_use_vasopressors(String regular_use_vasopressors){
        this.regular_use_vasopressors = regular_use_vasopressors;
    }
    public String getRegular_use_vasopressors(){
        return this.regular_use_vasopressors;
    }
    public void setEducationalLevel(String educationalLevel){
        this.educationalLevel = educationalLevel;
    }
    public String getEducationalLevel(){
        return this.educationalLevel;
    }


    public void setRadioGroups(RadioGroup[] radiogroups){

        this.radioGroups =radiogroups;
        if("是".equals(exsessiveDrinking)){
            radioGroups[0].check(R.id.radio_button_test_yes);
        }
        if("否".equals(exsessiveDrinking)){
            radioGroups[0].check(R.id.radio_button_test_no);
        }
        if("是".equals(smoke)){
            radioGroups[1].check(R.id.radio_button_test1_yes);
        }
        if("否".equals(smoke)){
            radioGroups[1].check(R.id.radio_button_test1_no);
        }
        if("是".equals(chronicStress)){
            radioGroups[2].check(R.id.radio_button_test2_yes);
        }
        if("否".equals(chronicStress)){
            radioGroups[2].check(R.id.radio_button_test2_no);
        }
        if("是".equals(familyHistoryHypertension)){
            radioGroups[3].check(R.id.radio_button_test3_yes);
        }
        if("否".equals(familyHistoryHypertension)){
            radioGroups[3].check(R.id.radio_button_test3_no);
        }
        if("是".equals(badEatingHabits)){
            radioGroups[4].check(R.id.radio_button_test4_yes);
        }
        if("否".equals(badEatingHabits)){
            radioGroups[4].check(R.id.radio_button_test4_no);
        }
        if("是".equals(largeNoise)){
            radioGroups[5].check(R.id.radio_button_test5_yes);
        }
        if("否".equals(largeNoise)){
            radioGroups[5].check(R.id.radio_button_test5_no);
        }
        if("是".equals(astriction)){
            radioGroups[6].check(R.id.radio_button_test6_yes);
        }
        if("否".equals(astriction)){
            radioGroups[6].check(R.id.radio_button_test6_no);
        }
        if("是".equals(regular_use_vasopressors)){
            radioGroups[7].check(R.id.radio_button_test7_yes);
        }
        if("否".equals(regular_use_vasopressors)){
            radioGroups[7].check(R.id.radio_button_test7_no);
        }
        if("大学".equals(educationalLevel)){
            radioGroups[8].check(R.id.radio_button_test8_l);
        }
        if("中学".equals(educationalLevel)){
            radioGroups[8].check(R.id.radio_button_test_8_mid);
        }if("小学".equals(educationalLevel)){
            radioGroups[8].check(R.id.radio_button_test8_s);
        }
    }
}
