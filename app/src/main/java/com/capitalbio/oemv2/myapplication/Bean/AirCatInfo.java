package com.capitalbio.oemv2.myapplication.Bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "aircatInfo")

public class AirCatInfo {

	@Column(name = "id", isId = true)
	private String id;
	@Column(name = "tvoc")
	private String tvoc;
	@Column(name = "pollutionLevel")
	private String pollutionLevel;
	@Column(name = "pm25")
	private String pm25;
	@Column(name = "humity")
	private String humity;
	@Column(name = "temperature")
	private String temperature;
	@Column(name = "ch2")
	private String ch2;
	@Column(name = "power")
	private String power;
	@Column(name = "mac")
	private String mac;
	@Column(name = "ctime")
	private String ctime;
	@Column(name = "testTime")
	private String testTime;


	public String getCh2() {
		return ch2;
	}

	public void setCh2(String ch2) {
		this.ch2 = ch2;
	}

	public String getCtime() {
		return ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	public String getHumity() {
		return humity;
	}

	public void setHumity(String humity) {
		this.humity = humity;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getPm25() {
		return pm25;
	}

	public void setPm25(String pm25) {
		this.pm25 = pm25;
	}

	public String getPollutionLevel() {
		return pollutionLevel;
	}

	public void setPollutionLevel(String pollutionLevel) {
		this.pollutionLevel = pollutionLevel;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getTestTime() {
		return testTime;
	}

	public void setTestTime(String testTime) {
		this.testTime = testTime;
	}

	public String getTvoc() {
		return tvoc;
	}

	public void setTvoc(String tvoc) {
		this.tvoc = tvoc;
	}
}
