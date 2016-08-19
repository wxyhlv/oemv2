package com.capitalbio.oemv2.myapplication.Bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by chengkun on 15-12-19.
 */
@Table(name = "SportDataTotalBean", onCreated = "CREATE UNIQUE INDEX index_name ON SportDataTotalBean(testTime)")
public class SportDataTotalBean {

    //记录id
    @Column(name = "id", isId = true)
    private int id;

    //设备id
    @Column(name = "deviceId")
    private int deviceId;

    //能量环值
    @Column(name = "relativeCal")
    private int relativeCal;

    //卡路里
    @Column(name = "cal")
    private int cal;

    //步数
    @Column(name = "steps")
    private int steps;

    //距离
    @Column(name = "distance")
    private int distance;

    //总的目标卡路里
    @Column(name = "targetCal")
    private int targetCal;

    //提交日期
    @Column(name = "testTime")
    private String testTime;

    //是否上传
    @Column(name = "isUpload")
    private boolean isUpload;

    //数据来源
    @Column(name = "dataSource")
    private String dataSource;

    public void setId(int id) {
        this.id = id;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public void setRelativeCal(int relativeCal) {
        this.relativeCal = relativeCal;
    }

    public void setCal(int cal) {
        this.cal = cal;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public void setTargetCal(int targetCal) {
        this.targetCal = targetCal;
    }

    public void setTestTime(String testTime) {
        this.testTime = testTime;
    }

    public void setIsUpload(boolean isUpload) {
        this.isUpload = isUpload;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public int getId() {
        return id;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public int getRelativeCal() {
        return relativeCal;
    }

    public int getCal() {
        return cal;
    }

    public int getSteps() {
        return steps;
    }

    public int getTargetCal() {
        return targetCal;
    }

    public String getTestTime() {
        return testTime;
    }

    public boolean isUpload() {
        return isUpload;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return "SportDataTotalBean{" +
                "cal=" + cal +
                ", id=" + id +
                ", deviceId=" + deviceId +
                ", relativeCal=" + relativeCal +
                ", steps=" + steps +
                ", distance=" + distance +
                ", targetCal=" + targetCal +
                ", testTime='" + testTime + '\'' +
                ", isUpload=" + isUpload +
                ", dataSource='" + dataSource + '\'' +
                '}';
    }
}
