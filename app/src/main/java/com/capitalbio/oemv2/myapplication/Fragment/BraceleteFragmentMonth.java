package com.capitalbio.oemv2.myapplication.Fragment;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
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
import java.util.List;

/**
 * Created by wxy on 15-11-26.
 */
public class BraceleteFragmentMonth extends BaseFragment implements OnChartValueSelectedListener {

    private TasksCompletedView arcView;//progress
    private TextView tv_grade,tv_time_deep,tv_time_low;//sleep data
    private TextView tv_steps_value,tv_distance_value,tv_kal_value,tv_time_value;//sports data
    private BarChart mChart;
    private Typeface mtf;
    private boolean isSports;
    private RelativeLayout rl_sportsinfo,rl_sleepinfo;

    ArrayList<Integer> vals;
    @Override
    protected View loadViewLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fg_bracelete_month,container,false);
    }

    @Override
    protected void findViewById(View view) {
       //
        tv_grade = (TextView) view.findViewById(R.id.tv_grade);
        tv_time_deep = (TextView) view.findViewById(R.id.tv_time_deep);
        tv_time_low = (TextView) view.findViewById(R.id.tv_time_low);

        tv_steps_value = (TextView) view.findViewById(R.id.tv_steps_value);
        tv_distance_value = (TextView) view.findViewById(R.id.tv_distance_value);
        tv_kal_value =(TextView) view.findViewById(R.id.tv_kal_value);
        tv_time_value=(TextView) view.findViewById(R.id.tv_time_value);
        rl_sleepinfo = (RelativeLayout) view.findViewById(R.id.rl_sleepinfo);
        rl_sportsinfo = (RelativeLayout) view.findViewById(R.id.rl_sportsinfo);
        mChart = (BarChart)view.findViewById(R.id.barchart);


    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void processLogic() {
        isSports = getArguments().getBoolean("isSports");
        if(isSports){
            rl_sportsinfo.setVisibility(View.VISIBLE);
            rl_sleepinfo.setVisibility(View.GONE);
        }else{
            rl_sleepinfo.setVisibility(View.VISIBLE);
            rl_sportsinfo.setVisibility(View.GONE);
        }
        initchar();
        setData(26, 50);
Log.i("repeat","repeat.......");
    }

    public void initchar(){

        mChart.setOnChartValueSelectedListener(this);

        mChart.setDrawBarShadow(false);
        //mChart.setDrawValueAboveBar(true);

        mChart.setDescription("");
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
        xAxis.setTextSize(16);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setSpaceBetweenLabels(0);

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
            xVals.add(i+1+"");
        }

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        vals =new ArrayList<>();
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
        vals.add(1500);


        for (int i = 0; i < vals.size(); i++) {

            yVals1.add(new BarEntry(vals.get(i), i));
        }

        BarDataSet set1 = new BarDataSet(yVals1, "DataSet");

        //  set1.setBarSpacePercent(35f);
        set1.setDrawValues(false);

        set1.setColor(Color.parseColor("#4CCBD2"));
        set1.setHighLightColor(Color.parseColor("#BEEEEE"));
        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();

        //  set1.setBarSpacePercent(35f);
        set1.setDrawValues(false);

        set1.setColor(Color.parseColor("#4CCBD2"));
        set1.setHighLightColor(Color.parseColor("#BEEEEE"));
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);
        /*data.setValueTextSize(getResources().getDimensionPixelSize(R.dimen.sp_20));
        data.setValueTypeface(mtf);*/
        mChart.setData(data);
        mChart.setVisibleXRangeMinimum(7);
        mChart.setScaleMinima(3.5f,1f);
        mChart.moveViewToX(19);

    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        Log.i("index",e.getXIndex()+"");

    tv_steps_value.setText(e.getVal()+"步");

    }

    @Override
    public void onNothingSelected() {

    }
}
