
package com.capitalbio.oemv2.myapplication.View;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.capitalbio.oemv2.myapplication.Bean.BraceleteHistory;
import com.capitalbio.oemv2.myapplication.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.Utils;

import java.util.List;

public class StepMarkerView extends MarkerView {

    private TextView tvContent,tvContent1,tvContent2;
    private RelativeLayout   rl_markerview;
;
    private List<BraceleteHistory> data;

    public StepMarkerView(Context context, int layoutResource, List<BraceleteHistory> data) {
        super(context, layoutResource);

        tvContent = (TextView) findViewById(R.id.tvContent);
        tvContent1 = (TextView) findViewById(R.id.tvContent1);
        tvContent2 = (TextView) findViewById(R.id.tvContent2);
        rl_markerview = (RelativeLayout) findViewById(R.id.rl_markerview);
        this.data = data;

    }
    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        if (e.getXIndex() < 4) {
            xOffsetMultiplier = 3.2f;
          //  rl_markerview.setBackgroundResource(R.drawable.right_);
        } else if (e.getXIndex() > 22) {
            //rl_markerview.setBackgroundResource(R.drawable.left);

            //timestamps is an array containing xValues timestamps
            xOffsetMultiplier = 1.12f;
        } else {
            xOffsetMultiplier = 2;
         //   rl_markerview.setBackgroundResource(R.drawable.middle_);

        }

        if (e instanceof CandleEntry) {

            CandleEntry ce = (CandleEntry) e;

            tvContent.setText("" + Utils.formatNumber(ce.getHigh(), 0, true));
        } else {
            float val = e.getVal();
            if(data!=null){
                int index = e.getXIndex();
                for(int i = 0;i<data.size();i++){
                    int hour = Integer.valueOf(data.get(i).getHour());
                    if(index ==hour){
                        tvContent.setText("步数：" + data.get(i).getSteps()+" 步 ");
                        tvContent1.setText("距离：" + data.get(i).getDistance()+"米 ");
                        tvContent2.setText("热量：" + data.get(i).getCal() + " 卡 ");
                    }
                }

            }else{
                tvContent.setText( val+"" );

            }
        }

    }
    float xOffsetMultiplier;


    @Override
    public int getXOffset(float xpos) {
        return  (int) -(getWidth() / xOffsetMultiplier);

    }

    @Override
    public int getYOffset(float ypos) {
        return -getHeight();
    }
}
