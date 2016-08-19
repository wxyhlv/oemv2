package com.capitalbio.oemv2.myapplication.BraceleteLib;

import java.util.Date;

/**
 * 运动详细数据
 * @author jiantao.tu
 *
 */
public class SportsDetailData {

	/**
	 * 运动类型
	 */
	private int sportsType;
	
	/**
	 * 运动时间
	 */
	private Date sprtsDate;
	
	/**
	 * 步数
	 */
	private int stepNumber;
	/**
	 * 距离
	 */
	private int distance;
	/**
	 * 能量环值
	 */
	private int energyRing;
	
	/**
	 * 卡路里 小卡，卡路里
	 */
	private int calorie;

	/**
	 * 运动类型
	 * @return
	 */
	public int getSportsType() {
		return sportsType;
	}

	/**
	 * 运动类型
	 * @param sportsType
	 */
	public void setSportsType(int sportsType) {
		this.sportsType = sportsType;
	}

	/**
	 * 运动时间
	 * @return
	 */
	public Date getSprtsDate() {
		return sprtsDate;
	}

	/**
	 * 运动时间
	 * @param sprtsDate
	 */
	public void setSprtsDate(Date sprtsDate) {
		this.sprtsDate = sprtsDate;
	}

	/**
	 * 步数
	 * @return
	 */
	public int getStepNumber() {
		return stepNumber;
	}
	
	/**
	 * 步数
	 * @param stepNumber
	 */
	public void setStepNumber(int stepNumber) {
		this.stepNumber = stepNumber;
	}

	/**
	 * 能量环值
	 * @return
	 */
	public int getEnergyRing() {
		return energyRing;
	}

	/**
	 * 能量环值
	 * @param energyRing
	 */
	public void setEnergyRing(int energyRing) {
		this.energyRing = energyRing;
	}

	/**
	 * 卡路里
	 * @return
	 */
	public int getCalorie() {
		return calorie;
	}

	/**
	 * 卡路里
	 * @param calorie
	 */
	public void setCalorie(int calorie) {
		this.calorie = calorie;
	}


	public void setDistance(int distance) {
		this.distance = distance;
	}

	public int getDistance() {
		return distance;
	}

	/**
     * @return
     * @see Object#toString()
     */ 
    @Override
    public String toString() {
        return "SportsDetailData [sportsType=" + sportsType + ", sprtsDate="
                + sprtsDate + ", stepNumber=" + stepNumber + ", energyRing="
                + energyRing + ", calorie=" + calorie + "]";
    }
	
}
