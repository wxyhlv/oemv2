package com.capitalbio.oemv2.myapplication.View;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;

import java.text.DecimalFormat;

public class BpYaxisFormatter implements YAxisValueFormatter {

    private DecimalFormat mFormat;

    /*public MyYAxisValueFormatter() {
        mFormat = new DecimalFormat("###,###,###,##0.0");
    }*/

    @Override
    public String getFormattedValue(float value, YAxis yAxis) {
       // return mFormat.format(value) + " $";
        if(value >200){
            return  value - 200 +"";
        }

        return  value+"";
    }

}
