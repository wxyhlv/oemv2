package com.capitalbio.oemv2.myapplication.Bean;

import android.widget.RadioGroup;

import com.capitalbio.oemv2.myapplication.R;

/**
 * Created by wxy on 16-2-24.
 * 脑卒中致病因素
 */
public class StrokeFator {
    private String lowBirthWeight;

    private String dyslipidemia;

    private String lackOfPhysicalExercise;

    private String oftenExcessiveDrinking;

    private String mentalStress;

    private String abuseOfDrugs;

    private String sleepApnea;

    private String migraines;

    private String metabolicSyndrome;

    private String highHomocysteineLevels;
    private RadioGroup[] radioGroups = new RadioGroup[10];

    public void setLowBirthWeight(String lowBirthWeight){
        this.lowBirthWeight = lowBirthWeight;
    }
    public String getLowBirthWeight(){
        return this.lowBirthWeight;
    }
    public void setDyslipidemia(String dyslipidemia){
        this.dyslipidemia = dyslipidemia;
    }
    public String getDyslipidemia(){
        return this.dyslipidemia;
    }
    public void setLackOfPhysicalExercise(String lackOfPhysicalExercise){
        this.lackOfPhysicalExercise = lackOfPhysicalExercise;
    }
    public String getLackOfPhysicalExercise(){
        return this.lackOfPhysicalExercise;
    }
    public void setOftenExcessiveDrinking(String oftenExcessiveDrinking){
        this.oftenExcessiveDrinking = oftenExcessiveDrinking;
    }
    public String getOftenExcessiveDrinking(){
        return this.oftenExcessiveDrinking;
    }
    public void setMentalStress(String mentalStress){
        this.mentalStress = mentalStress;
    }
    public String getMentalStress(){
        return this.mentalStress;
    }
    public void setAbuseOfDrugs(String abuseOfDrugs){
        this.abuseOfDrugs = abuseOfDrugs;
    }
    public String getAbuseOfDrugs(){
        return this.abuseOfDrugs;
    }
    public void setSleepApnea(String sleepApnea){
        this.sleepApnea = sleepApnea;
    }
    public String getSleepApnea(){
        return this.sleepApnea;
    }
    public void setMigraines(String migraines){
        this.migraines = migraines;
    }
    public String getMigraines(){
        return this.migraines;
    }
    public void setMetabolicSyndrome(String metabolicSyndrome){
        this.metabolicSyndrome = metabolicSyndrome;
    }
    public String getMetabolicSyndrome(){
        return this.metabolicSyndrome;
    }
    public void setHighHomocysteineLevels(String highHomocysteineLevels){
        this.highHomocysteineLevels = highHomocysteineLevels;
    }
    public String getHighHomocysteineLevels(){
        return this.highHomocysteineLevels;
    }


    public void setRadioGroups(RadioGroup[] radiogroups){

        this.radioGroups =radiogroups;

        if("是".equals(lowBirthWeight)){
            radioGroups[0].check(R.id.rg_stroke_1_yes);
        }
        if("否".equals(lowBirthWeight)){
            radioGroups[0].check(R.id.rg_stroke_1_no);
        }
        if("是".equals(dyslipidemia)){
            radioGroups[1].check(R.id.rg_stroke_2_yes);
        }
        if("否".equals(dyslipidemia)){
            radioGroups[1].check(R.id.rg_stroke_2_no);
        }
        if("是".equals(lackOfPhysicalExercise)){
            radioGroups[2].check(R.id.rg_stroke_3_yes);
        }
        if("否".equals(lackOfPhysicalExercise)){
            radioGroups[2].check(R.id.rg_stroke_3_no);
        }
        if("是".equals(oftenExcessiveDrinking)){
            radioGroups[3].check(R.id.rg_stroke_4_yes);
        }
        if("否".equals(oftenExcessiveDrinking)){
            radioGroups[3].check(R.id.rg_stroke_4_no);
        }
        if("是".equals(mentalStress)){
            radioGroups[4].check(R.id.rg_stroke_5_yes);
        }
        if("否".equals(mentalStress)){
            radioGroups[4].check(R.id.rg_stroke_5_no);
        }
        if("是".equals(abuseOfDrugs)){
            radioGroups[5].check(R.id.rg_stroke_6_yes);
        }
        if("否".equals(abuseOfDrugs)){
            radioGroups[5].check(R.id.rg_stroke_6_no);
        }

        if("是".equals(sleepApnea)){
            radioGroups[6].check(R.id.rg_stroke_7_yes);
        }
        if("否".equals(sleepApnea)){
            radioGroups[6].check(R.id.rg_stroke_7_no);
        }
        if("是".equals(migraines)){
            radioGroups[7].check(R.id.rg_stroke_8_yes);
        }
        if("否".equals(migraines)){
            radioGroups[7].check(R.id.rg_stroke_8_no);
        }
        if("是".equals(metabolicSyndrome)){
            radioGroups[8].check(R.id.rg_stroke_9_yes);
        }
        if("否".equals(metabolicSyndrome)){
            radioGroups[8].check(R.id.rg_stroke_9_no);
        }
        if("是".equals(highHomocysteineLevels)){
            radioGroups[9].check(R.id.rg_stroke_10_yes);
        }
        if("否".equals(highHomocysteineLevels)){
            radioGroups[9].check(R.id.rg_stroke_10_no);
        }

    }
}
