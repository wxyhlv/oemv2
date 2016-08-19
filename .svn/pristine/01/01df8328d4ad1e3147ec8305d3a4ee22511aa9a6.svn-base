package com.capitalbio.oemv2.myapplication.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.capitalbio.oemv2.myapplication.Bean.AirCatInfo;
import com.capitalbio.oemv2.myapplication.Bean.AirIndexBean;
import com.capitalbio.oemv2.myapplication.Const.AircatConst;
import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.View.MyMarkerView;
import com.capitalbio.oemv2.myapplication.View.MyYAxisValueFormatter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by wxy on 16-1-10.
 */
public class AircatHistoryUtil {


    /**温度
     * falg = 1:pm2.5;flag = 2：甲醛 ；flag = 3:温度；flag = 4:湿度；flag = 5:tvoc;flag = 6:空气污染指数
     */
    public static void showDialogHistoryTem(List<AirCatInfo> airIndexBeans,
                                      Context context,int flag) {
        // float[] tems = extractionIndividualData(airIndexBeans, 1);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(true);

        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.dialog_cat_history_data);

        TextView tvtitle = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_title);
        tvtitle.setText("过去24小时趋势图");
        TextView tv_unit = (TextView) window
                .findViewById(R.id.tv_unit);

        LineChart mChart = (LineChart) window.findViewById(R.id.lc_cat_history);
        ImageView iv_close = (ImageView) window
                .findViewById(R.id.iv_cat_history_close);
        iv_close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
           //     clickable = true;
            }
        });

        RelativeLayout rlremark1 = (RelativeLayout) window
                .findViewById(R.id.rl_cat_history_dialog_remark1);
        RelativeLayout rlremark2 = (RelativeLayout) window
                .findViewById(R.id.rl_cat_history_dialog_remark2);
        RelativeLayout rlremark3 = (RelativeLayout) window
                .findViewById(R.id.rl_cat_history_dialog_remark3);
        RelativeLayout rlremark4 = (RelativeLayout) window
                .findViewById(R.id.rl_cat_history_dialog_remark4);
        RelativeLayout rlremark5 = (RelativeLayout) window
                .findViewById(R.id.rl_cat_history_dialog_remark5);
        RelativeLayout rlremark6 = (RelativeLayout) window
                .findViewById(R.id.rl_cat_history_dialog_remark6);

        TextView tvremarkwords1 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark1_words);
        TextView tvremarkwords2 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark2_words);
        TextView tvremarkwords3 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark3_words);
        TextView tvremarkwords4 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark4_words);
        TextView tvremarkwords5 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark5_words);
        TextView tvremarkwords6 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark6_words);

        TextView tvremarkvalues1 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark1_values);
        TextView tvremarkvalues2 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark2_values);
        TextView tvremarkvalues3 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark3_values);
        TextView tvremarkvalues4 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark4_values);
        TextView tvremarkvalues5 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark5_values);
        TextView tvremarkvalues6 = (TextView) window
                .findViewById(R.id.tv_cat_history_dialog_remark6_values);
        if(flag == AircatConst.flag_formaldehyde){
            tv_unit.setText("甲醛(mg/m3)");
            tvremarkvalues1.setText("<=0.08mg/m³");
            tvremarkvalues2.setText("0.09-0.1mg/m³");
            tvremarkvalues3.setText("0.11-0.3mg/m³");
            tvremarkvalues4.setText("0.31-0.5mg/m³");
            tvremarkvalues5.setText("0.51-0.7mg/m³");
            tvremarkvalues6.setText(">=0.71mg/m³");
        }
        else if(flag == AircatConst.flag_tempreature){
            tv_unit.setText("温度(°C)");
            tvremarkwords1.setText("极冷");
            tvremarkwords3.setText("偏热");
            tvremarkwords4.setText("偏冷");
            tvremarkwords5.setText("舒适（空调）");
            tvremarkwords6.setText("热");

            Calendar cal = Calendar.getInstance();
            int month = cal.get(Calendar.MONTH) + 1;
            if (month >= 5 && month <= 10) {
                tvremarkwords2.setText("舒适");
                tvremarkvalues1.setText("<=12℃");
                tvremarkvalues2.setText("23-28℃");
                tvremarkvalues3.setText("28.1-33℃");
                tvremarkvalues4.setText("12.1-18℃");
                tvremarkvalues5.setText("18.1-24℃");
                tvremarkvalues6.setText(">=33.1℃");


            } else {
                tvremarkwords2.setText("舒适（供暖）");
                tvremarkvalues1.setText("<=12℃");
                tvremarkvalues2.setText("17.1-25℃");
                tvremarkvalues3.setText("25.1-33℃");
                tvremarkvalues4.setText("12.1-17℃");
                tvremarkvalues5.setText("19-24℃");
                tvremarkvalues6.setText(">=33.1℃");

            }
        }
        else if(flag == AircatConst.flag_humidity){
            tv_unit.setText("湿度(%RH)");
            rlremark3.setVisibility(View.GONE);
            rlremark6.setVisibility(View.GONE);

            tvremarkwords1.setText("干燥");
            tvremarkwords2.setText("舒适（供暖）");

            tvremarkwords4.setText("湿润");
            tvremarkwords5.setText("舒适（空调）");
            Calendar cal = Calendar.getInstance();
            int month = cal.get(Calendar.MONTH) + 1;
            if (month >= 5 && month <= 10) {
                tvremarkvalues1.setText("<=30%");
                tvremarkvalues2.setText("30.1%-60%");
                tvremarkvalues4.setText(">=60.1%");
                tvremarkvalues5.setText("40%-50%");
            }else{
                tvremarkvalues1.setText("<=30%");
                tvremarkvalues2.setText("30.1%-80%");
                tvremarkvalues4.setText(">=80.1%");
                tvremarkvalues5.setText("40%-50%");
            }

        }else if( flag == AircatConst.flag_tvoc){


            tv_unit.setText("TVOC(mg/m3)");
            tvremarkvalues1.setText("<=0.3mg/m³");
            tvremarkvalues2.setText("0.31-0.6mg/m³");
            tvremarkvalues3.setText("0.61-1.2mg/m³");
            tvremarkvalues4.setText("1.21-1.8mg/m³");
            tvremarkvalues5.setText("1.81-2.5mg/m³");
            tvremarkvalues6.setText(">=2.51mg/m³");

        }else if(flag == AircatConst.flag_pm2_5){
            tv_unit.setText("pm2.5");
            tvremarkvalues1.setText("0-35");
            tvremarkvalues2.setText("36-75");
            tvremarkvalues3.setText("76-115");
            tvremarkvalues4.setText("116-150");
            tvremarkvalues5.setText("151-250");
            tvremarkvalues6.setText("251-999");
        }else if(flag == AircatConst.flag_air_pollution_index){
            tv_unit.setText("空气污染指数");
            tvremarkwords3.setText("轻微污染");
            tvremarkwords5.setText("中度重污染");
            tvremarkwords6.setText("重度污染");



            tvremarkvalues1.setText("0-50");
            tvremarkvalues2.setText("51-100");
            tvremarkvalues3.setText("101-150");
            tvremarkvalues4.setText("151-200");
            tvremarkvalues5.setText("201-300");
            tvremarkvalues6.setText("300-500");
        }

        initChart(mChart, context, flag);
        setData(mChart, airIndexBeans, flag);
    }

    public static void initChart(final LineChart mChart, Context context,int flag) {
       // mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Bold.ttf");
/*
        mChart.setOnChartValueSelectedListener(context);*/

        // 设置网格
        mChart.setDrawBorders(false);

        mChart.setDrawGridBackground(false);
        mChart.setGridBackgroundColor(Color.rgb(235, 246, 247));
        mChart.setAlpha(0.8f);
        // 在chart上的右下角加描述
        mChart.setDescription("");

        // 设置是否可以触摸，如为false，则不能拖动，缩放等
        mChart.setTouchEnabled(true);
        // 设置是否可以拖拽，缩放
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);
        // 设置是否能扩大扩小
        mChart.setPinchZoom(false);
        mChart.getLegend().setEnabled(false);
        // 设置背景颜色
        // mChart.setBackgroundColor(getResources().getColor(high));
        // 设置点击chart图对应的数据弹出标注

        if(flag == AircatConst.flag_pm2_5 || flag == AircatConst.flag_air_pollution_index){
            MyMarkerView mv = new MyMarkerView(context, R.layout.custom_marker_view, 1);
            // define an offset to change the original position of the marker
            // (optional)
            // mv.setOffsets(-mv.getMeasuredWidth() / 2, -mv.getMeasuredHeight());
            // set the marker to the chart
            mChart.setMarkerView(mv);
        }else{
            MyMarkerView mv = new MyMarkerView(context, R.layout.custom_marker_view, 0);
            // define an offset to change the original position of the marker
            // (optional)
            // mv.setOffsets(-mv.getMeasuredWidth() / 2, -mv.getMeasuredHeight());
            // set the marker to the chart
            mChart.setMarkerView(mv);
        }


        mChart.setNoDataTextDescription("没有历史记录");
        mChart.setNoDataText("");
        mChart.setHighlightPerDragEnabled(false);

        XAxis xAxis = mChart.getXAxis();
        // xAxis.setValueFormatter(new MyCustomXAxisValueFormatter());
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawLabels(true);
        // xAxis.addLimitLine(llXAxis); // add x-axis limit line
        xAxis.setDrawGridLines(false);
        xAxis.setLabelsToSkip(0);
        xAxis.setAvoidFirstLastClipping(false);
        xAxis.setSpaceBetweenLabels(7);
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawLimitLinesBehindData(true);
        leftAxis.setDrawLabels(true);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setLabelCount(4, false);
        leftAxis.setTextSize(13);
        leftAxis.setDrawTopYLabelEntry(true);
        mChart.getAxisRight().setEnabled(false);

        // 设置显示动画
        mChart.animateX(1500);


        // 刷新画面
        mChart.invalidate();
    }

    public static void setData(LineChart mChart, List<AirCatInfo> airIndexBeans,
                         int flag) {
        float formaldehyde_maxy = 0,tvoc_maxy=0;
        if(airIndexBeans==null||airIndexBeans.size()==0){
            return;
        }
        formaldehyde_maxy = Float.parseFloat(airIndexBeans.get(0).getCh2());
        tvoc_maxy = Float.parseFloat(airIndexBeans.get(0).getTvoc());
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < airIndexBeans.size(); i++) {
            //System.out.println("天==" + airIndexBeans.get(i).getHour());
            String testtime = airIndexBeans.get(i).getTestTime();
            String hour = TimeStampUtil.getHour(Long.parseLong(testtime)) + ":00";
            OemLog.log("Aircat",hour);
            xVals.add(hour);
        }

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for (int i = 0; i < airIndexBeans.size(); i++) {
            switch (flag) {
                case AircatConst.flag_humidity:
                    // 湿度
                    // System.out.println("设置的图表中的数据==湿度"+airIndexBeans.get(i).getHumidity());
                    String value = airIndexBeans.get(i)
                            .getHumity();
                    if(value!=null){
                        float hum_f = Float.parseFloat(value);
                        String hum_s = new DecimalFormat("#.0").format(hum_f);
                        yVals.add(new Entry(Float.parseFloat(hum_s), i));
                    }else{
                        yVals.add(new Entry(0, i));

                    }

                    break;
                case AircatConst.flag_tempreature:
                    String value_tem = airIndexBeans.get(i)
                            .getTemperature();
                    if(value_tem !=null){
                        // 温度
                        float tem_f = Float.parseFloat(value_tem);
                        String tem_s = new DecimalFormat("#.0").format(tem_f);
                        yVals.add(new Entry(Float.parseFloat(tem_s), i));
                    }else{
                        yVals.add(new Entry(0, i));

                    }

                    break;
                case AircatConst.flag_formaldehyde:
                    // 甲醛
                    String ch2 = airIndexBeans.get(i)
                            .getCh2();
                    if(ch2!=null){
                        float forma_f = Float.parseFloat(ch2);
                        formaldehyde_maxy = formaldehyde_maxy > forma_f ? formaldehyde_maxy
                                : forma_f;
                        String forma_s = new DecimalFormat("#.00").format(forma_f);
                        yVals.add(new Entry(Float.parseFloat(forma_s), i));
                    }else{
                        yVals.add(new Entry(0, i));

                    }

                    break;
                case AircatConst.flag_tvoc:
                    String tvoc = airIndexBeans.get(i).getTvoc();
                    // tvoc
                    if(tvoc !=null){
                        float aqi_f = Float.parseFloat(tvoc);
                        tvoc_maxy = tvoc_maxy > aqi_f ? tvoc_maxy : aqi_f;
                        String aqi_s = new DecimalFormat("#.00").format(aqi_f);
                        yVals.add(new Entry(Float.parseFloat(aqi_s), i));
                    }else{
                        yVals.add(new Entry(0, i));

                    }


                    break;
                case AircatConst.flag_pm2_5:
                    // PM25
                    String pm2_5 = airIndexBeans.get(i)
                            .getPm25();
                    if(pm2_5 !=null){
                        float pm25f = Float.parseFloat(pm2_5);
                  /*      String pm25s = new DecimalFormat("#.0").format(pm25f);*/
                     //   yVals.add(new Entry(Float.parseFloat(pm25s), i));
                        yVals.add(new Entry((int)pm25f, i));
                    }else{
                        yVals.add(new Entry(0, i));
                    }

                    break;
                case AircatConst.flag_air_pollution_index:
                    String pollution = airIndexBeans.get(i).getPollutionLevel();
                    if(pollution != null){
                        float pollutionf = Float.parseFloat(pollution);
                        yVals.add(new Entry(pollutionf, i));
                    }else{
                        yVals.add(new Entry(0, i));
                    }

                    break;

            }

        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals, "");

        set1.setDrawCubic(false); // 设置曲线为圆滑的线
        // set1.setCubicIntensity(0.2f);
        set1.setDrawFilled(false); // 设置包括的范围区域填充颜色
        set1.setDrawCircles(true); // 设置有圆点
        set1.setLineWidth(8f); // 设置线的宽度
        set1.setCircleSize(9f); // 设置小圆的大小
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setColor(Color.rgb(60, 193, 247)); // 设置曲线的颜色
        set1.setCircleColor(Color.rgb(60, 193, 247));
        set1.setCircleColorHole(Color.WHITE);
        set1.setDrawCircleHole(true);
        set1.setValueTextSize(0);// 圆点上文本大小

        switch (flag) {

            case AircatConst.flag_formaldehyde:
                // 甲醛
	/*		if (formaldehyde_maxy < 0.04) {
				mChart.getAxisLeft().setAxisMaxValue(0.08f);
				mChart.getAxisLeft().setAxisMinValue(0.00f);
			}else{
				mChart.getAxisLeft().resetAxisMaxValue();
				mChart.getAxisLeft().resetAxisMinValue();
			}*/
                break;
            case AircatConst.flag_tvoc:
                // tvoc
                if (tvoc_maxy < 0.04) {
                    mChart.getAxisLeft().setAxisMaxValue(0.04f);
                    mChart.getAxisLeft().setAxisMinValue(0.00f);
                }else{
                    mChart.getAxisLeft().resetAxisMaxValue();
                    mChart.getAxisLeft().resetAxisMinValue();
                }
                break;
        }
        LineData data = new LineData(xVals, set1);
        // set data
        mChart.setData(data);
        //mChart.setVisibleXRange(6f);
        // mChart.getViewPortHandler().setMinimumScaleX(4f);

        mChart.setVisibleXRange(1, 6);
        mChart.setScaleMinima(0.7f, 1f);
        mChart.moveViewToX(data.getXValCount() - 7);
        // mChart.getViewPortHandler().setMinimumScaleX(4f);
        // TODO
        mChart.invalidate();
    }
}
