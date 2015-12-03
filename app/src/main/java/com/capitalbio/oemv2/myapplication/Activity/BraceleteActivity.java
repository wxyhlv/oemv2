package com.capitalbio.oemv2.myapplication.Activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.View.TasksCompletedView;
import com.capitalbio.oemv2.myapplication.dialog.ChangeBirthDialog;
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
import java.util.Date;

/**
 * Created by chengkun on 15-11-4.
 */
public class BraceleteActivity extends BaseActivity implements OnChartValueSelectedListener,View.OnClickListener {
    RelativeLayout rl_content;
    LinearLayout ll_date,ll_switch_date,ll_switch_sportorsleep;
    String yearString,monthString,dayString;
    String curyearString,curmonthString,curdayString;
    private TextView tv_year,tv_month,tv_day;
    private TextView tv_switch_month,tv_switch_day,tv_switch_sports,tv_switch_sleep;
    private TasksCompletedView arcView;
    private Typeface mtf;
    protected BarChart mChart;
    private String[] mTimes ={
            "0:00","1:00","2:00","3:00",
            "4:00","5:00","6:00","7:00",
            "8:00","9:00","10:00","11:00",
            "12:00","13:00","14:00","15:00",
            "16:00","17:00","18:00","19:00",
            "20:00","21:00","22:00","23:00",
    };
    @Override
    protected void initChildLayout() {

        //setNavigateBarBackGround(R.drawable.bg_shou_huan);

        setTvTopTitle(R.string.bracelete);
        setIvTopRight(R.drawable.ic_edit);
        setLeftTopIcon(R.drawable.ic_back);
      //  setIvSetDate(R.drawable.ic_down);
        initView_sports();
    }

    private void initView_sports() {
        setTopandBodybg(R.drawable.bg_tang_zhi_san_xiang);
        rl_content = (RelativeLayout) View.inflate(this, R.layout.body_bracelete_sports_layout, null);
        llBody.addView(rl_content);
        ll_date = (LinearLayout) rl_content.findViewById(R.id.ll_date);
        ll_date.setOnClickListener(this);
        ll_switch_date =(LinearLayout)rl_content.findViewById(R.id.ll_switch_date);
        ll_switch_sportorsleep =(LinearLayout)rl_content.findViewById(R.id.ll_switch_sportorsleep);
        tv_year = (TextView) rl_content.findViewById(R.id.tv_year);
        tv_month = (TextView) rl_content.findViewById(R.id.tv_month);
        tv_day = (TextView) rl_content.findViewById(R.id.tv_day);
        tv_switch_month = (TextView) rl_content.findViewById(R.id.tv_switch_month);
        tv_switch_day = (TextView) rl_content.findViewById(R.id.tv_switch_day);
        tv_switch_sports =(TextView) rl_content.findViewById(R.id.tv_switch_sports);
        tv_switch_sleep =(TextView) rl_content.findViewById(R.id.tv_switch_sleep);
        tv_switch_day.setOnClickListener(this);
        tv_switch_month.setOnClickListener(this);
        tv_switch_sports.setOnClickListener(this);
        tv_switch_sleep.setOnClickListener(this);
        Date date =new Date();
        curyearString = date.getYear()+1900+"年";
        curmonthString = date.getMonth()+1+"月";
        curdayString = date.getDate()+"日";

        tv_year.setText(curyearString);
        tv_month.setText(curmonthString);
        tv_day.setText(curdayString);
        arcView = (TasksCompletedView) findViewById(R.id.arcView);

        arcView.setProgressInfo(1500,3000,50);
        mChart = (BarChart) rl_content.findViewById(R.id.barchart);
        initchar();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_date:
                yearString = tv_year.getText().toString();
                monthString = tv_month.getText().toString();
                dayString = tv_day.getText().toString();
                int yearInt = Integer.valueOf(yearString.substring(0,yearString.length()-1));
                int monthInt = Integer.valueOf(monthString.substring(0, monthString.length()-1));
                int dayInt = Integer.valueOf(dayString.substring(0, dayString.length()-1));

                ChangeBirthDialog mChangeBirthDialog = new ChangeBirthDialog(
                        BraceleteActivity.this);
                mChangeBirthDialog.setDate(yearInt, monthInt,dayInt);
                mChangeBirthDialog.show();
                mChangeBirthDialog.setBirthdayListener(new ChangeBirthDialog.OnBirthListener() {

                    @Override
                    public void onClick(String year, String month, String day) {
                        // TODO Auto-generated method stub
                        Toast.makeText(BraceleteActivity.this,
                                year + "-" + month + "-" + day,
                                Toast.LENGTH_LONG).show();
                        tv_year.setText(year+"年");
                        tv_month.setText(month+"月");
                        tv_day.setText(day+"日");

                    }
                });
                break;
            case R.id.tv_switch_day:
                ll_switch_date.setBackground(getResources().getDrawable(R.drawable.switch_bracelete_day));
                tv_switch_day.setTextColor(Color.parseColor("#0EA8C3"));
                tv_switch_month.setTextColor(Color.WHITE);
                break;
            case R.id.tv_switch_month:
                ll_switch_date.setBackground(getResources().getDrawable(R.drawable.switch_bracelete_month));
                tv_switch_month.setTextColor(Color.parseColor("#0EA8C3"));
                tv_switch_day.setTextColor(Color.WHITE);
                break;
            case R.id.tv_switch_sports:
                ll_switch_sportorsleep.setBackground(getResources().getDrawable(R.drawable.switch_sportorsleep));
                tv_switch_sports.setTextColor(Color.WHITE);
                tv_switch_sleep.setTextColor(Color.parseColor("#00AEB5"));
                break;
            case R.id.tv_switch_sleep:
                ll_switch_sportorsleep.setBackground(getResources().getDrawable(R.drawable.switch_sleeporsports_));
                tv_switch_sleep.setTextColor(Color.WHITE);
                tv_switch_sports.setTextColor(Color.parseColor("#00AEB5"));

                break;

        }
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
        mtf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

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
        setData(24, 50);
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

    @SuppressLint("NewApi")
    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

        if (e == null)
            return;

        RectF bounds = mChart.getBarBounds((BarEntry) e);
        PointF position = mChart.getPosition(e, YAxis.AxisDependency.LEFT);

        Log.i("bounds", bounds.toString());
        Log.i("position", position.toString());

        Log.i("x-index",
                "low: " + mChart.getLowestVisibleXIndex() + ", high: "
                        + mChart.getHighestVisibleXIndex());
    }

    public void onNothingSelected() {
    };
}
