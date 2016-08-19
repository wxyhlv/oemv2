package com.capitalbio.oemv2.myapplication.View;
/**
 * 
 * @author wxy
 *此类表示图表右边标题及对应范围的背景
 */
public class LevalInfo {
    private String text;
    private int min;
    private int max;
    private int colorbg;
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public int getColorbg() {
		return colorbg;
	}
	public void setColorbg(int colorbg) {
		this.colorbg = colorbg;
	}
    
}
