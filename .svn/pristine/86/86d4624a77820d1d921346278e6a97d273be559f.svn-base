package com.capitalbio.oemv2.myapplication.BraceleteLib;

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
	ENTER_SLEEP(0x10,"进入睡眠模式(手动进入)"),
	
	/**
	 * 退出睡眠模式进入正常模式
	 */
	EXIT_ENTER_SLEEP(0x11,"退出睡眠模式进入正常模式");
	
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
	
	public static SleepRecords getSleepRecords(String name){
		if(name.equals(DOZEOFF.toString())){
			return DOZEOFF;
		}else if(name.equals(SOMNOLENCE.toString())){
			return SOMNOLENCE;
		}else if(name.equals(AWAKE.toString())){
			return AWAKE;
		}else if(name.equals(READY_SLEEP.toString())){
			return READY_SLEEP;
		}else if(name.equals(EXIT_SLEEP.toString())){
			return EXIT_SLEEP;
		}else if(name.equals(ENTER_SLEEP.toString())){
			return ENTER_SLEEP;
		}else if(name.equals(EXIT_ENTER_SLEEP.toString())){
			return EXIT_ENTER_SLEEP;
		}else{
			return null;
		}
	}
	@Override
	public String toString() {
		return this.name;
	}
	
	
}