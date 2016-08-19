package com.capitalbio.oemv2.myapplication.Bean;

import android.widget.RadioGroup;

import com.capitalbio.oemv2.myapplication.R;

import java.util.Arrays;

/**
 * Created by wxy on 16-2-24.
 */
public class CHDFator {
    private String smoking;

    private String exerciseRegularly;

    private String earlyCHD;

    private String highLDLC;
    private RadioGroup[] radioGroups = new RadioGroup[4];

    public void setSmoking(String smoking){
        this.smoking = smoking;
    }
    public String getSmoking(){
        return this.smoking;
    }
    public void setExerciseRegularly(String exerciseRegularly){
        this.exerciseRegularly = exerciseRegularly;
    }
    public String getExerciseRegularly(){
        return this.exerciseRegularly;
    }
    public void setEarlyCHD(String earlyCHD){
        this.earlyCHD = earlyCHD;
    }
    public String getEarlyCHD(){
        return this.earlyCHD;
    }
    public void setHighLDLC(String highLDLC){
        this.highLDLC = highLDLC;
    }
    public String getHighLDLC(){
        return this.highLDLC;
    }

    public void setRadioGroups(RadioGroup[] radiogroups){

        this.radioGroups =radiogroups;
        if(radiogroups.length>=4) {
            if (("是").equals(smoking)) {
                radioGroups[0].check(R.id.rg_chd_1_yes);
            }
            if (("否").equals(smoking)){
                radioGroups[0].check(R.id.rg_chd_1_no);
            }
            if (("是").equals(exerciseRegularly)){
                radioGroups[1].check(R.id.rg_chd_2_yes);
            }
            if (("否").equals(exerciseRegularly)) {
                radioGroups[1].check(R.id.rg_chd_2_no);
            }
            if (("是").equals(earlyCHD)) {
                radioGroups[2].check(R.id.rg_chd_3_yes);
            }
            if ("否".equals(earlyCHD)) {
                radioGroups[2].check(R.id.rg_chd_3_no);
            }
            if ("是".equals(highLDLC)) {
                radioGroups[3].check(R.id.rg_chd_4_yes);
            }
            if ("否".equals(highLDLC)) {
                radioGroups[3].check(R.id.rg_chd_4_no);
            }

        }
    }

    @Override
    public String toString() {
        return "CHDFator{" +
                "earlyCHD='" + earlyCHD + '\'' +
                ", smoking='" + smoking + '\'' +
                ", exerciseRegularly='" + exerciseRegularly + '\'' +
                ", highLDLC='" + highLDLC + '\''
                ;
    }
}
