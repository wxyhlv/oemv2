package com.capitalbio.oemv2.myapplication.Bean;

/**
 * Created by wxy on 16-3-15.
 */
public class BodyJson {
    private String weight;
    private String bmi;
    private float fat;
    private float visceralLevel;
    private float water;
    private float muscle;
    private float bone;
    private int bmr;
    private long testTime;
    private long ctime;
    private String username;

    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public float getBone() {
        return bone;
    }

    public void setBone(float bone) {
        this.bone = bone;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public float getFat() {
        return fat;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }

    public float getMuscle() {
        return muscle;
    }

    public void setMuscle(float muscle) {
        this.muscle = muscle;
    }

    public long getTestTime() {
        return testTime;
    }

    public void setTestTime(long testTime) {
        this.testTime = testTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public float getVisceralLevel() {
        return visceralLevel;
    }

    public void setVisceralLevel(float visceralLevel) {
        this.visceralLevel = visceralLevel;
    }

    public float getWater() {
        return water;
    }

    public void setWater(float water) {
        this.water = water;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public int getBmr() {
        return bmr;
    }

    public void setBmr(int bmr) {
        this.bmr = bmr;
    }

    @Override
    public String toString() {
        return "BodyJson{" +
                "bmi='" + bmi + '\'' +
                ", weight='" + weight + '\'' +
                ", fat=" + fat +
                ", visceralLevel=" + visceralLevel +
                ", water=" + water +
                ", muscle=" + muscle +
                ", bone=" + bone +
                ", bmr=" + bmr +
                ", testTime=" + testTime +
                ", ctime=" + ctime +
                ", username='" + username + '\'' +
                '}';
    }
}
