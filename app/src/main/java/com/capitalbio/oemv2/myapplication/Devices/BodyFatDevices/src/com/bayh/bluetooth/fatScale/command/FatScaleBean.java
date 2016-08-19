package com.capitalbio.oemv2.myapplication.Devices.BodyFatDevices.src.com.bayh.bluetooth.fatScale.command;


public class FatScaleBean {
	
	private String TAG = FatScaleBean.class.getSimpleName();

	/**
	 * �˶�ģʽ:��ͨ
	 */
	public static final String SPORT_MODE_COMMON="00";
	
	/**
	 * �˶�ģʽ:ҵ���˶�Ա
	 */
	public static final String SPORT_MODE_AMATEUR_ATHLETES="01";
	
	/**
	 * �˶�ģʽ:�˶�Ա
	 */
	public static final String SPORT_MODE_ATHLETES="10";
	
	/**
	 * �Ԅe:��
	 */
	public static final char SEX_MAN='1';
	
	/**
	 * �Ԅe:Ů
	 */
	public static final char SEX_WOMAN='0';
	

	/**
	 * ����
	 */
	private float weight;
	
	/**
	 * ���
	 */
	private float height;
	
	/**
	 * ֬����
	 */
	private float fat;
	
	/**
	 * ˮ�ֺ���
	 */
	private float water;
	
	/**
	 * ���⺬��
	 */
	private float muscle;
	
	/**
	 * ����
	 */
	private float bone;
	
	/**
	 * ����֬��
	 */
	private float visceraFat;
	
	/**
	 * ����
	 */
	private int kcal;
	
	/**
	 * �Ƿ���
	 */
	private boolean whetherBarefoot;

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

	public float getKcal() {
		return kcal;
	}

	public void setKcal(int kcal) {
		this.kcal = kcal;
	}
	
	public boolean isWhetherBarefoot() {
		return whetherBarefoot;
	}

	public void setWhetherBarefoot(boolean whetherBarefoot) {
		this.whetherBarefoot = whetherBarefoot;
	}

}
