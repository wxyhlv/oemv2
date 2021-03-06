package com.capitalbio.oemv2.myapplication.Bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by wxy on 16-3-17.
 * 统计每天的睡眠总信息
 */
@Table(name = "sleepMonthData")
public class BraceleteSleepDataForMonth {
    @Column(name = "day",isId = true)
    private String day;//yyyy-MM-dd
    @Column(name = "totalSleeptime")
    private  String totalSleeptime;//睡眠总时间
    @Column(name = "deeptime")
    private  String deeptime; //深度睡眠时长
    @Column(name = "qianshuitime")
    private  String qianshuitime;//浅睡时长
    @Column(name = "quality")
    private  String quality;//睡眠质量
    @Column(name = "username")
    private String username;//用户名
    private String wakeTime;//清醒时长
    private String wakeNum;//清醒次数

    private float totalTime;//总睡眠时间

    public float getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(float totalTime) {
        this.totalTime = totalTime;
    }

    public String getWakeNum() {
        return wakeNum;
    }

    public void setWakeNum(String wakeNum) {
        this.wakeNum = wakeNum;
    }

    public String getWakeTime() {
        return wakeTime;
    }

    public void setWakeTime(String wakeTime) {
        this.wakeTime = wakeTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDeeptime() {
        return deeptime;
    }

    public void setDeeptime(String deeptime) {
        this.deeptime = deeptime;
    }

    public String getQianshuitime() {
        return qianshuitime;
    }

    public void setQianshuitime(String qianshuitime) {
        this.qianshuitime = qianshuitime;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getTotalSleeptime() {
        return totalSleeptime;
    }

    public void setTotalSleeptime(String totalSleeptime) {
        this.totalSleeptime = totalSleeptime;
    }
}
