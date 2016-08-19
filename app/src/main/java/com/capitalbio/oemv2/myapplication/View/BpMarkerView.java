
package com.capitalbio.oemv2.myapplication.View;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.capitalbio.oemv2.myapplication.Bean.BloodPressureBean;
import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.Utils.TimeStampUtil;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.Utils;

import java.util.List;

public class BpMarkerView extends MarkerView {

    private TextView tvContent,tvContent1,tvContent2;
    private int currentFlag;
    private List<BloodPressureBean> data;
    public BpMarkerView(Context context, int layoutResource, int flag) {
        super(context, layoutResource);

        tvContent = (TextView) findViewById(R.id.tvContent);
        this.currentFlag = flag;
    }

    public BpMarkerView(Context context, int layoutResource, List<BloodPressureBean> data) {
        super(context, layoutResource);

        tvContent = (TextView) findViewById(R.id.tvContent);
        tvContent1 = (TextView) findViewById(R.id.tvContent1);
        tvContent2 = (TextView) findViewById(R.id.tvContent2);
        this.data = data;
    }
    float xOffsetMultiplier;

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        if (e.getXIndex() < 3) {
            xOffsetMultiplier = 3.2f;

        } else if (e.getXIndex() > 29) {
            //timestamps is an array containing xValues timestamps
            xOffsetMultiplier = 1.12f;
        } else {
            xOffsetMultiplier = 2;
        }

        if (e instanceof CandleEntry) {

            CandleEntry ce = (CandleEntry) e;

            tvContent.setText("" + Utils.formatNumber(ce.getHigh(), 0, true));
        } else {
            float val = e.getVal();
        if(data!=null){
            int index = e.getXIndex();
            for(int i = 0;i<data.size();i++){
                long time = data.get(i).getTime();
                if(index == TimeStampUtil.getDay(time) -1){
                    Log.i("text",data.get(i).getDiaBp()+";;;;;;;  "+
                            data.get(i).getSysBp()+" "+
                            data.get(i).getHeartRate()+" ");
                    tvContent2.setText("舒张压：" + data.get(i).getDiaBp()+" mmHg");
                    tvContent1.setText("收缩压：" + data.get(i).getSysBp()+" mmHg");
                    tvContent.setText("心率：" + data.get(i).getHeartRate() + " bpm ");
                }
            }

        }else{
            if(val>=200.0){
                tvContent.setText( val-200+"" );

            }else{
                tvContent.setText( val+"" );

            }
        }
        }

    }

    @Override
    public int getXOffset(float xpos) {
        return (int) -(getWidth() / xOffsetMultiplier);
    }

    @Override
    public int getYOffset(float ypos) {
        return -getHeight();
    }
}
