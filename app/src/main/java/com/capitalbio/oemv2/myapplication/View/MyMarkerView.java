
package com.capitalbio.oemv2.myapplication.View;

import android.content.Context;
import android.widget.TextView;

import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.Utils.AircatHistoryUtil;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.Utils;

public class MyMarkerView extends MarkerView {

    private TextView tvContent;
    private int currentFlag;

    public MyMarkerView(Context context, int layoutResource, int flag) {
        super(context, layoutResource);

        tvContent = (TextView) findViewById(R.id.tvContent);
        this.currentFlag = flag;
    }


    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        /*if (e.getXIndex() < 4) {
            xOffsetMultiplier = 3.2f;
        } else if (e.getXIndex() > 22) {
            //timestamps is an array containing xValues timestamps
            xOffsetMultiplier = 1.12f;
        } else {
            xOffsetMultiplier = 2;
        }*/

        if (e instanceof CandleEntry) {

            CandleEntry ce = (CandleEntry) e;

            tvContent.setText("" + Utils.formatNumber(ce.getHigh(), 0, true));
        } else {
            float val = e.getVal();
            if(currentFlag == 1){
                //整数
                tvContent.setText("" + (int)val);

            }else{
                tvContent.setText("" + val);

            }
        }
    }
    float xOffsetMultiplier = 2;
    @Override
    public int getXOffset(float xpos) {

        return (int) -(getWidth() / xOffsetMultiplier);
    }

    @Override
    public int getYOffset(float ypos) {
        return -getHeight();
    }
}
