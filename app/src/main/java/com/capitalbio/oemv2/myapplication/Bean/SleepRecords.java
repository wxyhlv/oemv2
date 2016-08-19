package com.capitalbio.oemv2.myapplication.Bean;

public enum SleepRecords{
	/**
	 * 睡着
	 */
	DOZEOFF(0x00,"睡着"),
	
	/**
	 * 浅睡
	 */
	SOMNOLENCE(0x01,"浅睡"),
	
	/**
	 * 醒着
	 */
	AWAKE(0x02,"醒着"),
	
	/**
	 * 准备入睡
	 */
	READY_SLEEP(0x03,"准备入睡"),
	
	/**
	 * 退出睡眠
	 */
	EXIT_SLEEP(0x04,"退出睡眠"),
	
	/**
	 *  进入睡眠模式(手动进入)
	 */
	ENTER_SLEEP(0x10,"进入睡眠"),
	
	/**
	 * 退出睡眠模式进入正常模式
	 */
	EXIT_ENTER_SLEEP(0x11,"退出睡眠");
	
	/**
	 * 16进制数
	 */
	private int hex;
	
	public String name;
	
	private SleepRecords(int hex,String name){
		this.hex=hex;
		this.name=name;
	}

	public int getHex(){
		return this.hex;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.name;
	}
	
	
}