package com.capitalbio.oemv2.myapplication.Bean;

/**
 * Created by wxy on 16-3-27.
 */
public class BraceleteSleepMonthJson {

    private String sleepQuality;

    private int shallowSleepTime;

    private int awakeTime;

    private int totalSleepTime;

    private int deepSleepTime;

    private int timeToSleep;

    private int wakeUpNumber;

    private long testTime;

    private String sleepDate;

    private String username;

    private long ctime;

    private String id;

    public void setSleepQuality(String sleepQuality){
        this.sleepQuality = sleepQuality;
    }
    public String getSleepQuality(){
        return this.sleepQuality;
    }
    public void setShallowSleepTime(int shallowSleepTime){
        this.shallowSleepTime = shallowSleepTime;
    }
    public int getShallowSleepTime(){
        return this.shallowSleepTime;
    }
    public void setAwakeTime(int awakeTime){
        this.awakeTime = awakeTime;
    }
    public int getAwakeTime(){
        return this.awakeTime;
    }
    public void setTotalSleepTime(int totalSleepTime){
        this.totalSleepTime = totalSleepTime;
    }
    public int getTotalSleepTime(){
        return this.totalSleepTime;
    }
    public void setDeepSleepTime(int deepSleepTime){
        this.deepSleepTime = deepSleepTime;
    }
    public int getDeepSleepTime(){
        return this.deepSleepTime;
    }
    public void setTimeToSleep(int timeToSleep){
        this.timeToSleep = timeToSleep;
    }
    public int getTimeToSleep(){
        return this.timeToSleep;
    }
    public void setWakeUpNumber(int wakeUpNumber){
        this.wakeUpNumber = wakeUpNumber;
    }
    public int getWakeUpNumber(){
        return this.wakeUpNumber;
    }
    public void setTestTime(long testTime){
        this.testTime = testTime;
    }
    public long getTestTime(){
        return this.testTime;
    }
    public void setSleepDate(String sleepDate){
        this.sleepDate = sleepDate;
    }
    public String getSleepDate(){
        return this.sleepDate;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public String getUsername(){
        return this.username;
    }
    public void setCtime(long ctime){
        this.ctime = ctime;
    }
    public long getCtime(){
        return this.ctime;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
}
