package com.capitalbio.oemv2.myapplication.View.charview;

import android.graphics.Paint;

/**
 * Created by mac on 15/11/8.
 */
public class RightIno {
    private int rectBGcolor;
    private String grade;//程度
    private double min;
    private double max;
    private Paint textPaint;

    public int getRectBGcolor() {
        return rectBGcolor;
    }

    public void setRectBGcolor(int rectBGcolor) {
        this.rectBGcolor = rectBGcolor;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public Paint getTextPaint() {
        return textPaint;
    }

    public void setTextPaint(Paint textPaint) {
        this.textPaint = textPaint;
    }
}
