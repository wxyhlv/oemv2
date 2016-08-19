package com.capitalbio.oemv2.myapplication.Bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by wxy on 15-12-8.
 */
@Table(name = "bloodPressure")
public class BloodPressureBean {
    //设备id
    @Column(name = "id", isId = true)
    private int deviceId;

    @Column(name = "username")
    private String username;
    //收缩压
    @Column(name = "sysBp")
    private int sysBp;
    //舒张压
    @Column(name = "diaBp")
    private int diaBp;
    //心率
    @Column(name = "heartRate")
    private int heartRate;
    //异常类型
    private int exceptionType;
    //测量时间
    @Column(name = "testTime")
    private long time;
    //测量时间年月日
    @Column(name = "testMinute")
    private String testMinute;

    @Column(name = "testHour")
    private String testHour;

    //测量时间年月日
    @Column(name = "testDate")
    private String testDate;
    //是否上传
    @Column(name = "isUpload")
    private boolean isUpload;
    //数据来源
    @Column(name = "dataSource")
    private String dataSource;

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getDiaBp() {
        return diaBp;
    }

    public void setDiaBp(int diaBp) {
        this.diaBp = diaBp;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public boolean isUpload() {
        return isUpload;
    }

    public void setIsUpload(boolean isUpload) {
        this.isUpload = isUpload;
    }

    public int getSysBp() {
        return sysBp;
    }

    public void setSysBp(int sysBp) {
        this.sysBp = sysBp;
    }

    public String getTestDate() {
        return testDate;
    }

    public void setTestDate(String testDate) {
        this.testDate = testDate;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(int exceptionType) {
        this.exceptionType = exceptionType;
    }

    public String getTestMinute() {
        return testMinute;
    }

    public void setTestMinute(String testMinute) {
        this.testMinute = testMinute;
    }

    public String getTestHour() {
        return testHour;
    }

    public void setTestHour(String testHour) {
        this.testHour = testHour;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "BloodPressureBean{" +
                "dataSource='" + dataSource + '\'' +
                ", deviceId=" + deviceId +
                ", username='" + username + '\'' +
                ", sysBp=" + sysBp +
                ", diaBp=" + diaBp +
                ", heartRate=" + heartRate +
                ", exceptionType=" + exceptionType +
                ", time=" + time +
                ", testMinute='" + testMinute + '\'' +
                ", testHour='" + testHour + '\'' +
                ", testDate='" + testDate + '\'' +
                ", isUpload=" + isUpload +
                '}';
    }
}
