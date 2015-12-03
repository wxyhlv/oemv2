package com.capitalbio.oemv2.myapplication.View;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

public class MyYAxisValueFormatter implements YAxisValueFormatter {

    private DecimalFormat mFormat;

    /*public MyYAxisValueFormatter() {
        mFormat = new DecimalFormat("###,###,###,##0.0");
    }*/

    @Override
    public String getFormattedValue(float value, YAxis yAxis) {
       // return mFormat.format(value) + " $";
        if(value == 20){
            return  "偏低";
        }
        if(value == 30){
            return  "偏低";
        }
        if(value == 120){
            return  "偏高";
        }
        if(value == 60){
            return  "正常";
        }
        return  "";
    }

}
