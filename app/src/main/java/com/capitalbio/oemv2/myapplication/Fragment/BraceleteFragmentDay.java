package com.capitalbio.oemv2.myapplication.Fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.View.TasksCompletedView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

/**
 * Created by wxy on 15-11-26.
 */
public class BraceleteFragmentDay extends BaseFragment  implements OnChartValueSelectedListener {

    private TasksCompletedView arcView;//progress
    private TextView tv_distance,tv_kal,tv_hours;
    private BarChart mChart;
    private Typeface mtf;
    private String[] mTimes ={
            "0:00","1:00","2:00","3:00",
            "4:00","5:00","6:00","7:00",
            "8:00","9:00","10:00","11:00",
            "12:00","13:00","14:00","15:00",
            "16:00","17:00","18:00","19:00",
            "20:00","21:00","22:00","23:00",
    };
    @Override
    protected View loadViewLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fg_bracelete_day,container,false);
    }

    @Override
    protected void findViewById(View view) {
       //
        arcView = (TasksCompletedView) view.findViewById(R.id.arcView);
        tv_distance = (TextView) view.findViewById(R.id.tv_distance);
        tv_kal = (TextView) view.findViewById(R.id.tv_kal);
        tv_hours = (TextView) view.findViewById(R.id.tv_hours);
        mChart = (BarChart)view.findViewById(R.id.barchart);

        mChart.setBackgroundColor(Color.WHITE);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void processLogic() {
        arcView.setProgressInfo(1500,3000,100);
        initchar();
        setData(24, 50);

    }
    public void initchar(){

       mChart.setOnChartValueSelectedListener(this);

        mChart.setDrawBarShadow(false);
        //mChart.setDrawValueAboveBar(true);

        mChart.setDescription("");

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(24);


        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawGridBackground(false);
        // mChart.setDrawYLabels(false);

        //  mChart.setAutoScaleMinMaxEnabled(true);
        mChart.setScaleYEnabled(false);
        mtf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(mtf);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(3);


        YAxis leftAxis = mChart.getAxisLeft();

        leftAxis.setLabelCount(8, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(10f);
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawLabels(false);
        mChart.getAxisRight().setEnabled(false);
        mChart.getLegend().setEnabled(false);

    }

    private void setData(int count, float range) {

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xVals.add(mTimes[i % 24]);
        }

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        ArrayList<Integer> vals =new ArrayList<>();
        vals.add(0);
        vals.add(0);
        vals.add(0);
        vals.add(0);
        vals.add(0);
        vals.add(0);
        vals.add(1000);
        vals.add(500);
        vals.add(1500);
        vals.add(300);
        vals.add(400);
        vals.add(1000);

        vals.add(500);
        vals.add(300);
        vals.add(350);
        vals.add(0);
        vals.add(108);
        vals.add(800);
        vals.add(350);
        vals.add(900);
        vals.add(1000);
        vals.add(2000);
        vals.add(50);
        vals.add(100);
        for (int i = 0; i < vals.size(); i++) {

            yVals1.add(new BarEntry(vals.get(i), i));
        }

        BarDataSet set1 = new BarDataSet(yVals1, "DataSet");

        //  set1.setBarSpacePercent(35f);
        set1.setDrawValues(false);
        set1.setColor(getResources().getColor(R.color.shouhuan_day_bgcolor));
        set1.setHighLightColor(Color.parseColor("#FF7E00"));
        set1.setHighLightAlpha(255);
        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(15f);
        data.setValueTypeface(mtf);
        mChart.setData(data);
        mChart.setVisibleXRangeMinimum(7);
        mChart.setScaleMinima(1.5f,1f);
        mChart.moveViewToX(6);

    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
