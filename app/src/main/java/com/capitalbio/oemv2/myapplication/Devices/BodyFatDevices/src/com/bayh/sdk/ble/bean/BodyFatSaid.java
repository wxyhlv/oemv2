package com.capitalbio.oemv2.myapplication.Devices.BodyFatDevices.src.com.bayh.sdk.ble.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "bodyfatsaid")
public class BodyFatSaid {
	//id
	@Column(name = "id", isId = true)
	private int id;

	//用户名
	@Column(name = "userName")
	private String userName;

	//测量时间long
	@Column(name = "longTime")
	private long longTime;

	//年月日
	@Column(name = "ymd")
	private String ymd;

	@Column(name = "testHour")
	private String testHour;
	//测量分钟
	@Column(name = "testMinute")
	private String testMinute;

	//运动模式
	@Column(name = "sportMode")
	private String sportMode;

	//性别
	@Column(name = "sex")
	private String sex;

	//体重
	@Column(name = "weight")
	private float weight;

	//身高
	@Column(name = "height")
	private float height;

	//脂肪率
	@Column(name = "fat")
	private float fat;

	//水分含量
	@Column(name = "water")
	private float water;

	//肌肉含量
	@Column(name = "muscle")
	private float muscle;

	//骨量
	@Column(name = "bone")
	private float bone;

	//内脏脂肪
	@Column(name = "visceraFat")
	private float visceraFat;

	//热量
	@Column(name = "kcal")
	private int kcal;

	//是否光脚
	@Column(name = "isBarefoot")
	private boolean isBarefoot;

	@Column(name = "bmi")
	private String bmi;

	@Column(name = "weightGrade")
	private String weightGrade;

	@Column(name = "bmrGrade")
	private String bmrGrade;

	@Column(name = "visceraGrade")
	private String visceraGrade;

	@Column(name = "boneGrade")
	private String boneGrade;

	@Column(name = "fatGrade")
	private String fatGrade;

	@Column(name = "muscleGrade")
	private String muscleGrade;

	@Column(name = "waterGrade")
	private String waterGrade;

	@Column(name = "isUpload")
	private boolean isUpload;


	@Column(name = "dataSource")
	private String dataSource;


	public String getFatGrade() {
		return fatGrade;
	}

	public void setFatGrade(String fatGrade) {
		this.fatGrade = fatGrade;
	}

	public String getBoneGrade() {
		return boneGrade;
	}

	public void setBoneGrade(String boneGrade) {
		this.boneGrade = boneGrade;
	}

	public String getBmrGrade() {
		return bmrGrade;
	}

	public void setBmrGrade(String bmrGrade) {
		this.bmrGrade = bmrGrade;
	}

	public String getMuscleGrade() {
		return muscleGrade;
	}

	public void setMuscleGrade(String muscleGrade) {
		this.muscleGrade = muscleGrade;
	}

	public String getVisceraGrade() {
		return visceraGrade;
	}

	public void setVisceraGrade(String visceraGrade) {
		this.visceraGrade = visceraGrade;
	}

	public String getWaterGrade() {
		return waterGrade;
	}

	public void setWaterGrade(String waterGrade) {
		this.waterGrade = waterGrade;
	}

	public String getWeightGrade() {
		return weightGrade;
	}

	public void setWeightGrade(String weightGrade) {
		this.weightGrade = weightGrade;
	}

	public String getBmi() {
		return bmi;
	}

	public void setBmi(String bmi) {
		this.bmi = bmi;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public long getLongTime() {
		return longTime;
	}

	public void setLongTime(long longTime) {
		this.longTime = longTime;
	}

	public String getYmd() {
		return ymd;
	}

	public void setYmd(String ymd) {
		this.ymd = ymd;
	}

	public String getSportMode() {
		return sportMode;
	}

	public void setSportMode(String sportMode) {
		this.sportMode = sportMode;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getFat() {
		return fat;
	}

	public void setFat(float fat) {
		this.fat = fat;
	}

	public float getWater() {
		return water;
	}

	public void setWater(float water) {
		this.water = water;
	}

	public float getMuscle() {
		return muscle;
	}

	public void setMuscle(float muscle) {
		this.muscle = muscle;
	}

	public float getBone() {
		return bone;
	}

	public void setBone(float bone) {
		this.bone = bone;
	}

	public float getVisceraFat() {
		return visceraFat;
	}

	public void setVisceraFat(float visceraFat) {
		this.visceraFat = visceraFat;
	}

	public int getKcal() {
		return kcal;
	}

	public void setKcal(int kcal) {
		this.kcal = kcal;
	}

	public boolean isBarefoot() {
		return isBarefoot;
	}

	public void setIsBarefoot(boolean isBarefoot) {
		this.isBarefoot = isBarefoot;
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

	public boolean isUpload() {
		return isUpload;
	}

	public void setIsUpload(boolean isUpload) {
		this.isUpload = isUpload;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "BodyFatSaid{" +
				"bmi='" + bmi + '\'' +
				", id=" + id +
				", userName='" + userName + '\'' +
				", longTime='" + longTime + '\'' +
				", ymd='" + ymd + '\'' +
				", testHour='" + testHour + '\'' +
				", testMinute='" + testMinute + '\'' +
				", sportMode='" + sportMode + '\'' +
				", sex='" + sex + '\'' +
				", weight=" + weight +
				", height=" + height +
				", fat=" + fat +
				", water=" + water +
				", muscle=" + muscle +
				", bone=" + bone +
				", visceraFat=" + visceraFat +
				", kcal=" + kcal +
				", isBarefoot=" + isBarefoot +
				", weightGrade='" + weightGrade + '\'' +
				", bmrGrade='" + bmrGrade + '\'' +
				", visceraGrade='" + visceraGrade + '\'' +
				", boneGrade='" + boneGrade + '\'' +
				", fatGrade='" + fatGrade + '\'' +
				", muscleGrade='" + muscleGrade + '\'' +
				", waterGrade='" + waterGrade + '\'' +
				'}';
	}
}
