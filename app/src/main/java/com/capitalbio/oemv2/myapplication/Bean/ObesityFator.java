package com.capitalbio.oemv2.myapplication.Bean;

import android.widget.RadioGroup;

import com.capitalbio.oemv2.myapplication.R;

/**
 * Created by wxy on 16-2-24.
 */
public class ObesityFator {
    private String familyObesity;

    private String goodEatingHabits;

    private String exerciseRegularly;

    private String offenSleepShort;

    private String endocrineDisorders;

    private String immunityStrong;

    private String oEatFastFood;

    private String birthWeightOverweight;

    private String feelWellEatHCAL;

    private String educationDegree;

    private String homeAdr;
    private RadioGroup[] radioGroups;

    public void setFamilyObesity (String familyObesity ){
        this.familyObesity = familyObesity ;
    }
    public String getFamilyObesity (){
        return this.familyObesity ;
    }
    public void setGoodEatingHabits(String goodEatingHabits){
        this.goodEatingHabits = goodEatingHabits;
    }
    public String getGoodEatingHabits(){
        return this.goodEatingHabits;
    }
    public void setExerciseRegularly(String exerciseRegularly){
        this.exerciseRegularly = exerciseRegularly;
    }
    public String getExerciseRegularly(){
        return this.exerciseRegularly;
    }
    public void setOffenSleepShort(String offenSleepShort){
        this.offenSleepShort = offenSleepShort;
    }
    public String getOffenSleepShort(){
        return this.offenSleepShort;
    }
    public void setEndocrineDisorders(String endocrineDisorders){
        this.endocrineDisorders = endocrineDisorders;
    }
    public String getEndocrineDisorders(){
        return this.endocrineDisorders;
    }
    public void setImmunityStrong(String immunityStrong){
        this.immunityStrong = immunityStrong;
    }
    public String getImmunityStrong(){
        return this.immunityStrong;
    }
    public void setOEatFastFood(String oEatFastFood){
        this.oEatFastFood = oEatFastFood;
    }
    public String getOEatFastFood(){
        return this.oEatFastFood;
    }
    public void setBirthWeightOverweight(String birthWeightOverweight){
        this.birthWeightOverweight = birthWeightOverweight;
    }
    public String getBirthWeightOverweight(){
        return this.birthWeightOverweight;
    }
    public void setFeelWellEatHCAL(String feelWellEatHCAL){
        this.feelWellEatHCAL = feelWellEatHCAL;
    }
    public String getFeelWellEatHCAL(){
        return this.feelWellEatHCAL;
    }
    public void setEducationDegree(String educationDegree){
        this.educationDegree = educationDegree;
    }
    public String getEducationDegree(){
        return this.educationDegree;
    }
    public void setHomeAdr(String homeAdr){
        this.homeAdr = homeAdr;
    }
    public String getHomeAdr(){
        return this.homeAdr;
    }

    public String getoEatFastFood() {
        return oEatFastFood;
    }

    public void setoEatFastFood(String oEatFastFood) {
        this.oEatFastFood = oEatFastFood;
    }

    public void setRadioGroups(RadioGroup[] radiogroups){

        this.radioGroups =radiogroups;
        if("是".equals(familyObesity)){
            radioGroups[0].check(R.id.rg_obsity_1_yes);
        }
        if("否".equals(familyObesity)){
            radioGroups[0].check(R.id.rg_obsity_1_no);
        }
        if("是".equals(goodEatingHabits)){
            radioGroups[1].check(R.id.rg_obsity_2_yes);
        }
        if("否".equals(goodEatingHabits)){
            radioGroups[1].check(R.id.rg_obsity_2_no);
        }
        if("是".equals(exerciseRegularly)){
            radioGroups[2].check(R.id.rg_obsity_3_yes);
        }
        if("否".equals(exerciseRegularly)){
            radioGroups[2].check(R.id.rg_obsity_3_no);
        }
        if("是".equals(offenSleepShort)){
            radioGroups[3].check(R.id.rg_obsity_4_yes);
        }
        if("否".equals(offenSleepShort)){
            radioGroups[3].check(R.id.rg_obsity_4_no);
        }
        if("是".equals(endocrineDisorders)){
            radioGroups[4].check(R.id.rg_obsity_5_yes);
        }
        if("否".equals(endocrineDisorders)){
            radioGroups[4].check(R.id.rg_obsity_5_no);
        }
        if("是".equals(immunityStrong)){
            radioGroups[5].check(R.id.rg_obsity_6_yes);
        }
        if("否".equals(immunityStrong)){
            radioGroups[5].check(R.id.rg_obsity_6_no);
        }

        if("是".equals(oEatFastFood)){
            radioGroups[6].check(R.id.rg_obsity_7_yes);
        }
        if("否".equals(oEatFastFood)){
            radioGroups[6].check(R.id.rg_obsity_7_no);
        }
        if("是".equals(birthWeightOverweight)){
            radioGroups[7].check(R.id.rg_obsity_8_yes);
        }
        if("否".equals(birthWeightOverweight)){
            radioGroups[7].check(R.id.rg_obsity_8_no);
        }
        if("是".equals(feelWellEatHCAL)){
            radioGroups[8].check(R.id.rg_obsity_9_yes);
        }
        if("否".equals(feelWellEatHCAL)){
            radioGroups[8].check(R.id.rg_obsity_9_no);
        }
        if("大学".equals(educationDegree)){
            radioGroups[9].check(R.id.rg_obsity_10_l);
        }
        if("中学".equals(educationDegree)){
            radioGroups[9].check(R.id.rg_obsity_10_m);
        }
        if("小学".equals(educationDegree)){
            radioGroups[9].check(R.id.rg_obsity_10_s);
        }
        if("是".equals(homeAdr)){
            radioGroups[10].check(R.id.rg_obsity_11_yes);
        }
        if("否".equals(homeAdr)){
            radioGroups[10].check(R.id.rg_obsity_11_no);
        }
    }

    @Override
    public String toString() {
        return "ObesityFator{" +
                "offenSleepShort='" + offenSleepShort + '\'' +
                ", oEatFastFood='" + oEatFastFood + '\'' +
                ", immunityStrong='" + immunityStrong + '\'' +
                ", homeAdr='" + homeAdr + '\'' +
                ", feelWellEatHCAL='" + feelWellEatHCAL + '\'' +
                ", exerciseRegularly='" + exerciseRegularly + '\'' +
                ", birthWeightOverweight='" + birthWeightOverweight + '\'' +
                ", educationDegree='" + educationDegree + '\'' +
                ", endocrineDisorders='" + endocrineDisorders + '\'' +
                ", familyObesity='" + familyObesity + '\'' +
                ", goodEatingHabits='" + goodEatingHabits + '\'' +
                '}';
    }
}
