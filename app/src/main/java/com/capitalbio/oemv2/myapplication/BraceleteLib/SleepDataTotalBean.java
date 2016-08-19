package com.capitalbio.oemv2.myapplication.BraceleteLib;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.util.Date;
import java.util.List;

/**
 * 睡眠数据
 * @author jiantao.tu
 *
 */
@Table(name = "SleepDataTotalBean")
public class SleepDataTotalBean {
	@Column(name = "id", isId = true)
	private int id;
	/**
	 * 睡眠记录数据
	 * @author jiantao.tu
	 *
	 */
	public static class SleepRecordData{
		/**
		 * 睡眠类型
		 */
		private SleepRecords sleepRecords;
		
		/**
		 * 时间
		 */
		private Date time;

		/**
		 * 睡眠类型
		 * @return
		 */
		public SleepRecords getSleepRecords() {
			return sleepRecords;
		}

		/**
		 * 睡眠类型
		 * @param sleepRecords
		 */
		public void setSleepRecords(SleepRecords sleepRecords) {
			this.sleepRecords = sleepRecords;
		}

		public Date getTime() {
			return time;
		}

		public void setTime(Date time) {
			this.time = time;
		}
		
		
	}

	//是否上传
	@Column(name = "isUpload")
	private boolean isUpload;

	/**
	 * 入睡时间
	 */
	@Column(name = "gotoSleepTime")
	private int gotoSleepTime;
	
	/**
	 * 清醒时间
	 */
	@Column(name = "soberTime")
	private int soberTime;
	
	/**
	 * 浅睡时间
	 */
	@Column(name = "somnolenceTime")
	private int somnolenceTime;
	
	/**
	 * 深睡时间
	 */
	@Column(name = "seepSleepTime")
	private int seepSleepTime;
	
	/**
	 * 唤醒次数
	 */
	@Column(name = "rouseNumber")
	private int rouseNumber;
	
	/**
	 * 总时间
	 */
	@Column(name = "sumNumber")
	private int sumNumber;

	@Column(name = "username")
	private String username;

	@Column(name = "ymd")
	private String ymd;

	//longTime
	@Column(name = "longTime")
	private long longTime;

	//数据来源
	@Column(name = "dataSource")
	private String dataSource;


	//记录创建时间
	@Column(name = "ctime")
	private String ctime;

	/**
	 * 睡眠记录数据
	 */
	private List<SleepRecordData> sleepRecordList;

	/**
	 * 入睡时间
	 * @return
	 */
	public int getGotoSleepTime() {
		return gotoSleepTime;
	}

	/**
	 * 入睡时间
	 * @param gotoSleepTime
	 */
	public void setGotoSleepTime(int gotoSleepTime) {
		this.gotoSleepTime = gotoSleepTime;
	}

	/**
	 * 清醒时间
	 * @return
	 */
	public int getSoberTime() {
		return soberTime;
	}

	/**
	 * 清醒时间
	 * @param soberTime
	 */
	public void setSoberTime(int soberTime) {
		this.soberTime = soberTime;
	}

	/**
	 * 浅睡时间
	 * @return
	 */
	public int getSomnolenceTime() {
		return somnolenceTime;
	}

	/**
	 * 浅睡时间
	 * @param somnolenceTime
	 */
	public void setSomnolenceTime(int somnolenceTime) {
		this.somnolenceTime = somnolenceTime;
	}

	/**
	 * 深睡时间
	 * @return
	 */
	public int getSeepSleepTime() {
		return seepSleepTime;
	}

	/**
	 * 深睡时间
	 * @param seepSleepTime
	 */
	public void setSeepSleepTime(int seepSleepTime) {
		this.seepSleepTime = seepSleepTime;
	}

	/**
	 * 唤醒次数
	 * @return
	 */
	public int getRouseNumber() {
		return rouseNumber;
	}

	/**
	 * 唤醒次数
	 * @param rouseNumber
	 */
	public void setRouseNumber(int rouseNumber) {
		this.rouseNumber = rouseNumber;
	}

	/**
	 * 总时间
	 * @return
	 */
	public int getSumNumber() {
		return sumNumber;
	}

	/**
	 * 总时间
	 * @param sumNumber
	 */
	public void setSumNumber(int sumNumber) {
		this.sumNumber = sumNumber;
	}

	/**
	 * 睡眠记录数据
	 * @return
	 */
	public List<SleepRecordData> getSleepRecordList() {
		return sleepRecordList;
	}

	/**
	 * 睡眠记录数据
	 * @param sleepRecordList
	 */
	public void setSleepRecordList(List<SleepRecordData> sleepRecordList) {
		this.sleepRecordList = sleepRecordList;
	}

	public boolean isUpload() {
		return isUpload;
	}

	public void setIsUpload(boolean isUpload) {
		this.isUpload = isUpload;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getYmd() {
		return ymd;
	}

	public void setYmd(String ymd) {
		this.ymd = ymd;
	}

	public long getLongTime() {
		return longTime;
	}

	public void setLongTime(long longTime) {
		this.longTime = longTime;
	}


	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getCtime() {
		return ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	@Override
	public String toString() {
		return "SleepDataTotalBean{" +
				"gotoSleepTime=" + gotoSleepTime +
				", id=" + id +
				", isUpload=" + isUpload +
				", soberTime=" + soberTime +
				", somnolenceTime=" + somnolenceTime +
				", seepSleepTime=" + seepSleepTime +
				", rouseNumber=" + rouseNumber +
				", sumNumber=" + sumNumber +
				", username='" + username + '\'' +
				", ymd='" + ymd + '\'' +
				", longTime=" + longTime +
				", sleepRecordList=" + sleepRecordList +
				'}';
	}
}
