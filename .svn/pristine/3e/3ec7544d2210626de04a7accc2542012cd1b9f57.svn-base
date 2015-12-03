package com.capitalbio.oemv2.myapplication.View.charview;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;

import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.View.MyYAxisValueFormatter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class ChartViewPBM extends Activity {
    LineChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_view_pbm);

        mChart = (LineChart) findViewById(R.id.mychart);

        //设置网格
        mChart.setDrawBorders(false);

        mChart.setDrawGridBackground(true);
        mChart.setGridBackgroundColor(Color.rgb(255, 235, 236));
        //在chart上的右下角加描述
        mChart.setDescription("9月");

        //设置是否可以触摸，如为false，则不能拖动，缩放等
        mChart.setTouchEnabled(true);
        //设置是否可以拖拽，缩放
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        //设置是否能扩大扩小
        mChart.setPinchZoom(true);

        // 设置背景颜色
      //   mChart.setBackgroundColor(getResources().getColor(high));
        //设置点击chart图对应的数据弹出标注
       /* MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
        // define an offset to change the original position of the marker
        // (optional)
        // set the marker to the chart
        mChart.setMarkerView(mv);
        // enable/disable highlight indicators (the lines that indicate the
        // highlighted Entry)
        mChart.getHighlightByTouchPoint(mv.getX(),mv.getY());*/
        //设置字体格式，如正楷
        Typeface tf = Typeface.createFromAsset(getAssets(),
                "OpenSans-Regular.ttf");

        XAxis xAxis = mChart.getXAxis();
       // xAxis.setValueFormatter(new MyCustomXAxisValueFormatter());
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawLabels(true);
        //xAxis.addLimitLine(llXAxis); // add x-axis limit line
        xAxis.setDrawGridLines(false);
        xAxis.setLabelsToSkip(0);
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawLimitLinesBehindData(true);
        leftAxis.setDrawLabels(true);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        //leftAxis.setValueFormatter(new MyYAxisValueFormatter());
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawLabels(true);
        rightAxis.setDrawAxisLine(false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMaxValue(150);
        rightAxis.setAxisMinValue(0);
        rightAxis.setValueFormatter(new MyYAxisValueFormatter());
       // mChart.getAxisRight().setEnabled(false);
         List<Integer> colors = new ArrayList<>();
         List<String> labels =new ArrayList<>();
        int color1 =Color.rgb(255, 235, 236);
        int color2 = Color.rgb(104, 241, 175);
        int color3 = Color.rgb(255, 250, 100);
        colors.add(color1);
        colors.add(color2);
        colors.add(color3);
        List<String> texts = new ArrayList<>();
        texts.add("偏高");
        texts.add("正常");
        texts.add("偏低");
       Legend ll= mChart.getLegend();
        ll.setCustom(colors,texts);
        // leftAxis.setLabelCount(6,false);
        ll.setEnabled(false);
        setData();
       /* //从X轴进入的动画
        mChart.animateX(4000);

        mChart.animateY(3000);   //从Y轴进入的动画
        mChart.animateXY(3000, 3000);    //从XY轴一起进入的动画*/
        //设置最小的缩放
        mChart.setScaleMinima(0.5f, 1f);


        mChart.invalidate();
    }

    private void setData() {
        String[] aa = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14"};
        String[] bb = {"122.00","134.34","85.67","117.90","65.33","44.33","120.78","122.00","85","56","117.90","123.33","100.33","140.78"};

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < aa.length; i++) {
            xVals.add(aa[i]);
        }

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for (int i = 0; i < bb.length; i++) {
            yVals.add(new Entry(Float.parseFloat(bb[i]), i));
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals, "偏高");

        set1.setDrawCubic(true);  //设置曲线为圆滑的线
        set1.setCubicIntensity(0.2f);
        set1.setDrawFilled(false);  //设置包括的范围区域填充颜色
        set1.setDrawCircles(true);  //设置有圆点
        set1.setLineWidth(2f);    //设置线的宽度
        set1.setCircleSize(5f);   //设置小圆的大小
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setColor(Color.rgb(104, 241, 175));    //设置曲线的颜色

        // create a data object with the datasets
        // LineData data = new LineData(xVals, set1);

        ArrayList<Entry> yVals_MIN = new ArrayList<Entry>();
        ArrayList<Entry> yVals_middle = new ArrayList<Entry>();
        ArrayList<Entry> yVals_max = new ArrayList<Entry>();
        for (int i = 0; i < bb.length; i++) {
            yVals_MIN.add(new Entry(45f, i));
        }


        for (int i = 0; i < bb.length; i++) {

            yVals_middle.add(new Entry(80f, i));
        }

      //  Log.i("ymax",mChart.getYMax()+"");
        LineDataSet set2 = new LineDataSet(yVals_MIN, "正常");

        set2.setDrawCubic(true);  //设置曲线为圆滑的线

        set2.setDrawFilled(true);  //设置包括的范围区域填充颜色
        set2.setDrawCircles(false);  //设置有圆点
        set2.setLineWidth(1f);    //设置线的宽度
        //set1.setHighLightColor(Color.rgb(244, 117, 117));
        set2.setColor(Color.rgb(255, 250, 100));    //设置曲线的颜色
        set2.setFillAlpha(90);
        set2.setDrawValues(false);

        set2.setFillColor(Color.rgb(255, 250, 100));

        LineDataSet set3 = new LineDataSet(yVals_middle, "偏低");

        set3.setDrawCubic(true);  //设置曲线为圆滑的线

        set3.setDrawFilled(true);  //设置包括的范围区域填充颜色
        set3.setDrawCircles(false);  //设置有圆点
        set3.setLineWidth(1f);    //设置线的宽度
        //set1.setHighLightColor(Color.rgb(244, 117, 117));
        set3.setColor(Color.rgb(104, 241, 175));    //设置曲线的颜色
        set3.setFillColor(Color.rgb(104, 241, 175));
        set3.setFillAlpha(100);
        set3.setDrawValues(false);

        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(set1);

        dataSets.add(set3);
        dataSets.add(set2);
        LineData data = new LineData(xVals, dataSets);

        mChart.setData(data);

    }


}
