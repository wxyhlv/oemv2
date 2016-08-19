package com.capitalbio.oemv2.myapplication.FirstPeriod.Bean;

import java.util.ArrayList;
import java.util.List;


import android.os.Parcel;
import android.os.Parcelable;

import com.capitalbio.oemv2.myapplication.FirstPeriod.Utils.StringUtil;


public class WeatherBean implements Parcelable{

	/**城市id*/
	private String areaId;
	/**区域名称*/
	private String district;
	/**天气编号*/
	private String weatherNumber;
	/**天气现象   多云,小雨*/
	private String weatherDesc;
	/**当前温度*/
	private String currentTemperature;
	/**风力*/
	private String wind;
	/**风向*/
	private String windDirection;
	/**风力**/
	private String windPower;
	/**湿度*/
	private String humidity;
	/**空气质量*/
	private String airQuality;
	/**空气污染物浓度*/
	private int airLevel;
	/**更新时间*/
	private long updateTime;
	/**未来天气*/
	private String weatherForecastInfo;
	/**天气预报发布时间 格式: HH:mm*/
	private  long factTime;
	/**天气指数说明*/
	private String indexInfo;
	/**当前最高温度*/
	private String highTem = "*";
	/**当天最低温度*/
	private String lowTem = "*";
	/**天气预警*/
	private ArrayList<String> warningInfo;
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(areaId);
		dest.writeString(district);
		dest.writeString(weatherNumber);
		dest.writeString(weatherDesc);
		dest.writeString(currentTemperature);
		dest.writeString(wind);
		dest.writeString(windDirection);
		dest.writeString(windPower);
		dest.writeString(humidity);
		dest.writeString(airQuality);
		dest.writeInt(airLevel);
		dest.writeLong(updateTime);
		dest.writeString(weatherForecastInfo);
		dest.writeLong(factTime);
		dest.writeString(indexInfo);
		dest.writeString(highTem);
		dest.writeString(lowTem);
		dest.writeList(warningInfo);
	}
	
	
	public static Creator<WeatherBean> CREATOR = new Creator<WeatherBean>() {

		@Override
		public WeatherBean createFromParcel(Parcel source) {
			WeatherBean bean = new WeatherBean();
			bean.setAreaId(source.readString());
			bean.setDistrict(source.readString());
			bean.setWeatherNumber(source.readString());
			bean.setWeatherDesc(source.readString());
			bean.setCurrentTemperature(source.readString());
			bean.setWind(source.readString());
			bean.setWindDirection(source.readString());
			bean.setWindPower(source.readString());
			bean.setHumidity(source.readString());
			bean.setAirQuality(source.readString());
			bean.setAirLevel(source.readInt());
			bean.setUpdateTime(source.readLong());
			bean.setWeatherForecastInfo(source.readString());
			bean.setFactTime(source.readLong());
			bean.setIndexInfo(source.readString());
			bean.setHighTem(source.readString());
			bean.setLowTem(source.readString());
			bean.setWarningInfo(source.readArrayList(ArrayList.class.getClassLoader()));
			return bean;
		}

		@Override
		public WeatherBean[] newArray(int size) {
			return new WeatherBean[size];
		}
	};
	
	
	
	public String getWindDirection() {
		return windDirection;
	}
	public void setWindDirection(String windDirection) {
		this.windDirection = windDirection;
	}
	public String getWindPower() {
		return windPower;
	}
	public void setWindPower(String windPower) {
		this.windPower = windPower;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getWeatherNumber() {
		return weatherNumber;
	}
	public void setWeatherNumber(String weatherNumber) {
		this.weatherNumber = weatherNumber;
	}
	public String getWeatherDesc() {
		return weatherDesc;
	}
	public void setWeatherDesc(String weatherDesc) {
		this.weatherDesc = weatherDesc;
	}
	public String getCurrentTemperature() {
		return currentTemperature;
	}
	public void setCurrentTemperature(String currentTemperature) {
		this.currentTemperature = currentTemperature;
	}
	public String getHumidity() {
		return humidity;
	}
	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
	/**
	 * 空气污染等级
	 * @return
	 */
	public String getAirQuality() {
		if(StringUtil.isEmpty(airQuality))
		{
			return "*";
		}
		return airQuality;
	}
	public void setAirQuality(String airQuality) {
		this.airQuality = airQuality;
	}
	/**
	 * 空气污染指数
	 * @return
	 */
	public int getAirLevel() {
		return airLevel;
	}
	public void setAirLevel(int airLevel) {
		this.airLevel = airLevel;
	}
	public long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}
	public String getWeatherForecastInfo() {
		return weatherForecastInfo;
	}
	public void setWeatherForecastInfo(String weatherForecastInfo) {
		this.weatherForecastInfo = weatherForecastInfo;
	}
	public String getWind() {
		return wind;
	}
	public void setWind(String wind) {
		this.wind = wind;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public long getFactTime() {
		return factTime;
	}
	public void setFactTime(long factTime) {
		this.factTime = factTime;
	}
	public String getIndexInfo() {
		return indexInfo;
	}
	public void setIndexInfo(String indexInfo) {
		this.indexInfo = indexInfo;
	}
	
	public void setHighTem(String highTem) {
		this.highTem = highTem;
	}
	public void setLowTem(String lowTem) {
		this.lowTem = lowTem;
	}
//	/**白天温度*/
//	public String getHighTem()
//	{
//		String json = getWeatherForecastInfo();
//		if(json == null) 
//			return "*";
//		JSONArray array = JSONArray.parseArray(json);
//		if(array != null && array.size() > 0)
//		{
//			return array.getJSONObject(0).getString("fc");
//		}
//		return "*";
//	}
//	
//	/**晚上温度*/
//	public String getLowTem()
//	{
//		String json = getWeatherForecastInfo();
//		if(json == null) 
//			return "*";
//		JSONArray array = JSONArray.parseArray(json);
//		if(array != null && array.size() > 0)
//		{
//			return array.getJSONObject(0).getString("fd");
//		}
//		return "*";
//	}
	public String getHighTem() {
		return highTem;
	}
	public String getLowTem() {
		return lowTem;
	}
	public ArrayList<String> getWarningInfo() {
		return warningInfo;
	}
	public void setWarningInfo(ArrayList<String> warningInfo) {
		this.warningInfo = warningInfo;
	}
	
}
