package com.capitalbio.oemv2.myapplication.FirstPeriod.Bean;

import java.util.ArrayList;
import java.util.List;


import android.os.Parcel;
import android.os.Parcelable;

import com.capitalbio.oemv2.myapplication.FirstPeriod.Utils.StringUtil;


/**
 * 从服务器获取到的空气质量
 * @author jcyu
 */
public class AirQualityBean implements Parcelable{

	String aqi;
	String area;
	String co;
	String co_24h;
	String no2;
	String no2_24h;
	String o3;
	String o3_24h;
	String o3_8h;
	String o3_8h_24h;
	String pm10;
	String pm10_24;
	String pm2_5;
	String pm2_5_24;
	String position_name;
	String primary_pollutant;
	String quality;
	String so2;
	String so2_24h;
	String time_point;
	String level;	//只有地级信息中有该数据,  当前位置监测站没有该字段
	int juli;
	List<AirIndex> aqi_list = new ArrayList<AirIndex>();
	
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(aqi);
		dest.writeString(area);
		dest.writeString(co);
		dest.writeString(co_24h);
		dest.writeString(no2);
		dest.writeString(no2_24h);
		dest.writeString(o3);
		dest.writeString(o3_24h);
		dest.writeString(o3_8h);
		dest.writeString(o3_8h_24h);
		dest.writeString(pm10);
		dest.writeString(pm10_24);
		dest.writeString(pm2_5);
		dest.writeString(pm2_5_24);
		dest.writeString(position_name);
		dest.writeString(primary_pollutant);
		dest.writeString(quality);
		dest.writeString(so2);
		dest.writeString(so2_24h);
		dest.writeString(time_point);
		dest.writeString(level);
		dest.writeInt(juli);
		dest.writeTypedList(aqi_list);
	}
	
	public static Creator<AirQualityBean> CREATOR = new Creator<AirQualityBean>() {
		
		@Override
		public AirQualityBean[] newArray(int size) {
			return new AirQualityBean[size];
		}
		
		@Override
		public AirQualityBean createFromParcel(Parcel source) {
			AirQualityBean bean = new AirQualityBean();
			bean.setAqi(source.readString());
			bean.setArea(source.readString());
			bean.setCo(source.readString());
			bean.setCo_24h(source.readString());
			bean.setNo2(source.readString());
			bean.setNo2_24h(source.readString());
			bean.setO3(source.readString());
			bean.setO3_24h(source.readString());
			bean.setO3_8h(source.readString());
			bean.setO3_8h_24h(source.readString());
			bean.setPm10(source.readString());
			bean.setPm10_24(source.readString());
			bean.setPm2_5(source.readString());
			bean.setPm2_5_24(source.readString());
			bean.setPosition_name(source.readString());
			bean.setPrimary_pollutant(source.readString());
			bean.setQuality(source.readString());
			bean.setSo2(source.readString());
			bean.setSo2_24h(source.readString());
			bean.setTime_point(source.readString());
			bean.setLevel(source.readString());
			bean.setJuli(source.readInt());
			source.readTypedList(bean.getAqi_list(), AirIndex.CREATOR);
			
			return bean;
		}
	};
	
	
	public List<AirIndex> getAqi_list() {
		return aqi_list;
	}
	public void setAqi_list(List<AirIndex> aqi_list) {
		this.aqi_list = aqi_list;
	}
	public String getAqi() {
		return aqi;
	}
	
	public float getAqiFloat()
	{
		try {
			return Float.parseFloat(getAqi());
		} catch (Exception e) {
			return 0;
		}
	}
	
	public int getAqiInt()
	{
		try {
			return (int)getAqiFloat();
		} catch (Exception e) {
			return 0;
		}
	}
	public void setAqi(String aqi) {
		this.aqi = aqi;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getCo() {
		return co;
	}
	public void setCo(String co) {
		this.co = co;
	}
	public String getCo_24h() {
		return co_24h;
	}
	public void setCo_24h(String co_24h) {
		this.co_24h = co_24h;
	}
	public String getNo2() {
		return no2;
	}
	public void setNo2(String no2) {
		this.no2 = no2;
	}
	public String getNo2_24h() {
		return no2_24h;
	}
	public void setNo2_24h(String no2_24h) {
		this.no2_24h = no2_24h;
	}
	public String getO3() {
		return o3;
	}
	public void setO3(String o3) {
		this.o3 = o3;
	}
	public String getO3_24h() {
		return o3_24h;
	}
	public void setO3_24h(String o3_24h) {
		this.o3_24h = o3_24h;
	}
	public String getO3_8h() {
		return o3_8h;
	}
	public void setO3_8h(String o3_8h) {
		this.o3_8h = o3_8h;
	}
	public String getO3_8h_24h() {
		return o3_8h_24h;
	}
	public void setO3_8h_24h(String o3_8h_24h) {
		this.o3_8h_24h = o3_8h_24h;
	}
	public String getPm10() {
		return pm10;
	}
	public void setPm10(String pm10) {
		this.pm10 = pm10;
	}
	public String getPm10_24() {
		return pm10_24;
	}
	public void setPm10_24(String pm10_24) {
		this.pm10_24 = pm10_24;
	}
	public String getPm2_5() {
		return pm2_5;
	}
	public void setPm2_5(String pm2_5) {
		this.pm2_5 = pm2_5;
	}
	public String getPm2_5_24() {
		return pm2_5_24;
	}
	public void setPm2_5_24(String pm2_5_24) {
		this.pm2_5_24 = pm2_5_24;
	}
	public String getPosition_name() {
		return position_name;
	}
	public void setPosition_name(String position_name) {
		this.position_name = position_name;
	}
	public String getPrimary_pollutant() {
		if(StringUtil.isEmpty(primary_pollutant)){
			return "无";
		}
		return primary_pollutant;
	}
	public void setPrimary_pollutant(String primary_pollutant) {
		this.primary_pollutant = primary_pollutant;
	}
	public String getQuality() {
		return quality;
	}
	public void setQuality(String quality) {
		this.quality = quality;
	}
	public String getSo2() {
		return so2;
	}
	public void setSo2(String so2) {
		this.so2 = so2;
	}
	public String getSo2_24h() {
		return so2_24h;
	}
	public void setSo2_24h(String so2_24h) {
		this.so2_24h = so2_24h;
	}
	public String getTime_point() {
		return time_point;
	}
	public void setTime_point(String time_point) {
		this.time_point = time_point;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public int getJuli() {
		return juli;
	}
	public void setJuli(int juli) {
		this.juli = juli;
	}
	
	
}
