package com.capitalbio.oemv2.myapplication.Bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by wxy on 15-12-21.
 */
@Table(name = "braceleteWarn")
public class WarnInfo {
    @Column(name = "id", isId = true)
    private int id;
    @Column(name = "username")
    private String username;
    @Column(name = "warnTime")
    private String warnTime ;
    @Column(name = "warnType")
    private String warnType;
    @Column(name = "warnContent")
    private String warnContent;
    @Column(name = "repeat")
    private String repeat;

    public String getTime() {
        return warnTime;
    }

    public String getWarnType() {
        return warnType;
    }

    public void setTime(String time) {
        this.warnTime = time;
    }

    public void setWarnType(String warnType) {
        this.warnType = warnType;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWarnContent() {
        return warnContent;
    }

    public void setWarnContent(String warnContent) {
        this.warnContent = warnContent;
    }

    public String getWarnTime() {
        return warnTime;
    }

    public void setWarnTime(String warnTime) {
        this.warnTime = warnTime;
    }
}
