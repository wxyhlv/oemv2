package com.capitalbio.oemv2.myapplication.FirstPeriod.Bean;
/**
 * 存储天气更新规则
 * @author keyang
 *
 */
public class UpdateTimeBean {

	/**自动更新开关*/
	private boolean Automatic_update = true;
	/**更新时间间隔*/
	private int interval;
	/**更新开始时间*/
	private long start_update;
	/**更新结束时间*/
	private long stop_update;
	
	public UpdateTimeBean(){
		
	}
	
	public UpdateTimeBean(boolean automatic_update, int interval,
			long start_update, long stop_update) {
		super();
		Automatic_update = automatic_update;
		this.interval = interval;
		this.start_update = start_update;
		this.stop_update = stop_update;
	}


	public boolean isAutomatic_update() {
		return Automatic_update;
	}
	public void setAutomatic_update(boolean automatic_update) {
		Automatic_update = automatic_update;
	}
	public int getInterval() {
		return interval;
	}
	public void setInterval(int interval) {
		this.interval = interval;
	}
	public long getStart_update() {
		return start_update;
	}
	public void setStart_update(long start_update) {
		this.start_update = start_update;
	}
	public long getStop_update() {
		return stop_update;
	}
	public void setStop_update(long stop_update) {
		this.stop_update = stop_update;
	}
	
	
	
}
