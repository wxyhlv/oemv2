package com.capitalbio.oemv2.myapplication.Bean;

/**
 * Created by wxy on 15-12-12.
 */
public class LineChartBg implements  Comparable<LineChartBg>{
    private float min;//最小值
    private float max;//最大值
    private String color;//背景颜色
    private String text;//右边的标题

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getMin() {
        return min;
    }

    public String getColor() {
        return color;
    }

    public String getText() {
        return text;
    }

    @Override
    public int compareTo(LineChartBg another) {
        float value= this.getMax()-another.getMax();
        if(value>0){
            return 1;
        }else if(value == 0){
            return 0;
        }else{
            return -1;
        }


    }
}
