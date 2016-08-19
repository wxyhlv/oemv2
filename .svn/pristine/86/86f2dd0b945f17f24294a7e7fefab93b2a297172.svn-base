package com.capitalbio.oemv2.myapplication.FirstPeriod.Bean;

import android.os.Parcel;
import android.os.Parcelable;

public class AirIndex implements Parcelable {

	String aqi;
	String nowTime;
	
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(aqi);
		dest.writeString(nowTime);
	}
	public static final Creator<AirIndex> CREATOR = new Creator<AirIndex>() {
		@Override
		public AirIndex[] newArray(int size) {
			return new AirIndex[size];
		}
		
		@Override
		public AirIndex createFromParcel(Parcel source) {
			AirIndex bean = new AirIndex();
			bean.setAqi(source.readString());
			bean.setNowTime(source.readString());
			return bean;
		}
	};
	
	public float getAqiFloat()
	{
		try {
			return Float.parseFloat(aqi);
		} catch (Exception e) {
			return 0;
		}
	}
	public int getAqiInt() {
		return (int)getAqiFloat();
	}
	
	public String getAqi() {
		return aqi;
	}
	public void setAqi(String aqi) {
		this.aqi = aqi;
	}
	public String getNowTime() {
		return nowTime;
	}
	public void setNowTime(String nowTime) {
		this.nowTime = nowTime;
	}
	
}
