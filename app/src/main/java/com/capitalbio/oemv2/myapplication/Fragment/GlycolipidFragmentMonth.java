package com.capitalbio.oemv2.myapplication.Fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.View.MyMarkerView;
import com.capitalbio.oemv2.myapplication.View.MyYAxisValueFormatter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultFillFormatter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by susu on 16-5-17.
 */
public class GlycolipidFragmentMonth extends BaseFragment {
    private View view;
    private LineChart line_chart;
    private RelativeLayout rl_gly_bloodsuger,rl_gly_chole,rl_gly_tri,rl_gly_highlip,rl_gly_lowlip;
    private RadioGroup rg_gl_blood;
    private List<String> xLables;
    private ArrayList<Entry> yVals_MIN = new ArrayList<Entry>();
    private ArrayList<Entry> yVals_MID = new ArrayList<Entry>();
    private ArrayList<Entry> yVals_MAX = new ArrayList<Entry>();

    private Calendar time = Calendar.getInstance();
    private int monDay=time.getActualMaximum(Calendar.DAY_OF_MONTH);//获取天数

    private RadioButton rb_mealbefore,rb_mealafter;
    private int state=1;

    @Override
    protected View loadViewLayout(LayoutInflater inflater, ViewGroup container) {
        view=inflater.inflate(R.layout.glycolipid_month_measure,container,false);
        return view;
    }
    @Override
    protected void findViewById(View view) {
        line_chart= (LineChart) view.findViewById(R.id.line_chart);

        rl_gly_bloodsuger= (RelativeLayout) view.findViewById(R.id.rl_gly_bloodsuger);
        rl_gly_chole= (RelativeLayout) view.findViewById(R.id.rl_gly_chole);
        rl_gly_tri= (RelativeLayout) view.findViewById(R.id.rl_gly_tri);
        rl_gly_highlip= (RelativeLayout) view.findViewById(R.id.rl_gly_highlip);
        rl_gly_lowlip= (RelativeLayout) view.findViewById(R.id.rl_gly_lowlip);
        rg_gl_blood= (RadioGroup) view.findViewById(R.id.rg_gl_blood);
        rb_mealbefore= (RadioButton) view.findViewById(R.id.rb_mealbefore);
        rb_mealafter= (RadioButton) view.findViewById(R.id.rb_mealafter);

        rl_gly_bloodsuger.setOnClickListener(this);
        rl_gly_chole.setOnClickListener(this);
        rl_gly_tri.setOnClickListener(this);
        rl_gly_highlip.setOnClickListener(this);
        rl_gly_lowlip.setOnClickListener(this);



        rg_gl_blood.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_mealbefore:
                        state=1;
                        getLineData(monDay,10,1);
                        break;
                    case R.id.rb_mealafter:
                        state=2;
                        getLineData(monDay,10,1);
                        break;
                }
            }
        });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_gly_bloodsuger:
                rg_gl_blood.setVisibility(View.VISIBLE);
                rb_mealbefore.setChecked(true);
                state=1;
                getLineData(monDay,10,1);
                break;
            case R.id.rl_gly_chole:
                getLineData(monDay,10,2);
                rg_gl_blood.setVisibility(View.GONE);
                break;
            case R.id.rl_gly_tri:
                getLineData(monDay,3,3);
                rg_gl_blood.setVisibility(View.GONE);
                break;
            case R.id.rl_gly_highlip:
                getLineData(monDay,3,4);
                rg_gl_blood.setVisibility(View.GONE);
                break;
            case R.id.rl_gly_lowlip:
                getLineData(monDay,8,5);
                rg_gl_blood.setVisibility(View.GONE);
                break;
        }
    }
    @Override
    protected void processLogic() {
        showChart(line_chart);
    }
    // 设置显示的样式
    private void showChart(LineChart lineChart) {
        lineChart.setDrawBorders(false);  //是否在折线图上添加边框

        // no description text
        lineChart.setDescription("");// 数据描述
        // 如果没有数据的时候，会显示这个，类似listview的emtpyview
        lineChart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable / disable grid background
        lineChart.setDrawGridBackground(false); // 是否显示表格颜色

        // enable touch gestures
        lineChart.setTouchEnabled(true); // 设置是否可以触摸
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),   //字体
                "OpenSans-Regular.ttf");
        // enable scaling and dragging
        lineChart.setDragEnabled(true);// 是否可以拖拽
        lineChart.setScaleEnabled(false);// 是否可以缩放

        // if disabled, scaling can be done on x- and y-axis separately
        lineChart.setPinchZoom(false);//在不可用的状态下可以伸缩拉伸x轴和y轴
        lineChart.setBackgroundColor(Color.WHITE);// 设置背景

        //点击出现标注
        MyMarkerView mv = new MyMarkerView(mContext, R.layout.custom_marker_view,0);
        lineChart.setMarkerView(mv);

        //lineChart.getHighlightByTouchPoint(mv.getX(),mv.getY());
        // add data
        getLineData(monDay,10,1);// 初始设置数据

        // get the legend (only possible after setting data)
        Legend mLegend = lineChart.getLegend(); // 设置比例图标示，就是那个一组y的value的
        mLegend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        mLegend.setTypeface(tf);


        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawLabels(true);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelsToSkip(0);

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setDrawLimitLinesBehindData(true);
        leftAxis.setDrawLabels(true);

        leftAxis.setAxisMaxValue(10f); //y轴最高值
        leftAxis.setAxisMinValue(0f);  //y轴最小值
        leftAxis.setLabelCount(6, true); //y轴显示数值数量
        leftAxis.enableGridDashedLine(10f, 10f, 0f); //设置为虚线
        leftAxis.setGridLineWidth(1); //线宽度

        DecimalFormat format= new DecimalFormat("###,###,###,##0.00");
        leftAxis.setValueFormatter(new MyYAxisValueFormatter(format));  //格式化数据
        lineChart.getAxisRight().setEnabled(false); //纵轴右边不可用
        lineChart.setVisibleXRangeMaximum(7);//设置x轴数据显示个数

    }
    /**
     * 生成一个数据
     * @param days 表示图表中有多少个坐标点
     * @param yMax 用来生成range以内的随机数
     * @param type 选择类型
     * @return
     */
    private void getLineData(int days, float yMax,int type) {

        ArrayList<String> xValues = new ArrayList<String>();
        for (int i = 1; i <=days; i++) {
            // x轴显示的数据，这里默认使用数字下标显示
            xValues.add("" + i);
        }

        // y轴的数据
        ArrayList<Entry> yValues = new ArrayList<Entry>();
        DecimalFormat d1=new DecimalFormat("#.00");
        for (int i = 0; i < days; i++) {
            float value = (float) (Math.random() * yMax);
            float ff=Float.parseFloat(d1.format(value));
            yValues.add(new Entry(ff, i));
        }

        yVals_MID.clear();
        yVals_MIN.clear();
        yVals_MAX.clear();
        for (int i = 0; i < days; i++) {
            if (type == 1) {
                if (state==1){
                    yVals_MIN.add(new Entry(3.9f,i));
                    yVals_MID.add(new Entry(6.1f,i));
                    yVals_MAX.add(new Entry(10f,i));

                }else {
                    yVals_MIN.add(new Entry(3.9f,i));
                    yVals_MID.add(new Entry(7.8f,i));
                    yVals_MAX.add(new Entry(10f,i));
                }

            } else if (type == 2) {
                yVals_MIN.add(new Entry(5.18f,i));
                yVals_MID.add(new Entry(6.19f,i));
                yVals_MAX.add(new Entry(10f,i));

            } else if (type == 3) {
                yVals_MIN.add(new Entry(1.70f,i));
                yVals_MID.add(new Entry(2.25f,i));
                yVals_MAX.add(new Entry(3f,i));
            } else if (type == 4) {
                yVals_MIN.add(new Entry(1.04f,i));
                yVals_MID.add(new Entry(1.55f,i));
                yVals_MAX.add(new Entry(3f,i));
            } else if (type == 5) {
                yVals_MIN.add(new Entry(3.37f,i));
                yVals_MID.add(new Entry(4.12f,i));
                yVals_MAX.add(new Entry(8f,i));
            }
        }


        LineDataSet set1 = new LineDataSet(yValues, "血糖");
        set1.setDrawCubic(true);  //设置曲线为圆滑的线
        set1.setCubicIntensity(0.05f);
        set1.setDrawFilled(false);  //设置包括的范围区域填充颜色
        set1.setDrawCircles(true);  //设置有圆点
        set1.setLineWidth(4f);    //设置线的宽度
        set1.setCircleSize(6f);   //设置圆点的大小
        set1.setCircleColor(Color.parseColor("#ffb80c"));//设置圆点的颜色
        set1.setHighLightColor(Color.parseColor("#ffb80c")); //设置高亮色
        set1.setColor(Color.parseColor("#ffb80c"));    //设置曲线的颜色
        set1.setDrawValues(false);//数字不显示

        LineDataSet set2 = new LineDataSet(yVals_MIN, "低血糖");
        set2.setDrawFilled(true);  //设置包括的范围区域填充颜色
        set2.setDrawCircles(false);  //设置有圆点
        set2.setLineWidth(1f);    //设置线的宽度
        set2.setColor(Color.parseColor("#FFFAE0"));    //设置曲线的颜色
        set2.setFillColor(Color.parseColor("#FFFAE0")); //设置填充色
        set2.setFillAlpha(225);//填充透明度
        set2.setDrawValues(false);
        set2.setHighlightEnabled(false);

        LineDataSet set3 = new LineDataSet(yVals_MID, "正常");
        set3.setDrawFilled(true);  //设置包括的范围区域填充颜色
        set3.setDrawCircles(false);  //设置有圆点
        set3.setLineWidth(1f);    //设置线的宽度
        set3.setColor(Color.rgb(214, 246, 222));    //设置曲线的颜色
        set3.setFillColor(Color.rgb(214, 246, 222)); //
        set3.setFillAlpha(225);
        set3.setFillFormatter(new DefaultFillFormatter(3.9f));
        set3.setDrawValues(false);
        set3.setHighlightEnabled(false);

        LineDataSet set4 = new LineDataSet(yVals_MAX, "高血糖");
        set4.setDrawFilled(true);  //设置包括的范围区域填充颜色
        set4.setDrawCircles(false);  //设置有圆点
        set4.setColor(Color.parseColor("#FFEBEC"));    //设置曲线的颜色
        set4.setFillColor(Color.parseColor("#FFEBEC"));
        set4.setFillAlpha(225);
        set4.setFillFormatter(new DefaultFillFormatter(6.1f));//设置填充的下边界
        set4.setDrawValues(false);
        set4.setHighlightEnabled(false);

        if(type==1){
            set1.setCircleColor(Color.parseColor("#ffb80c"));//设置圆点的颜色
            set1.setHighLightColor(Color.parseColor("#ffb80c")); //设置高亮色
            set1.setColor(Color.parseColor("#ffb80c"));    //设置曲线的颜色
            set1.setLabel("血糖");
            set2.setLabel("低血糖");
            set3.setLabel("正常");
            set4.setLabel("高血糖");
            line_chart.getAxisLeft().setAxisMaxValue(yMax);
        }else if (type==2){
            set1.setCircleColor(Color.parseColor("#e22340"));//设置圆点的颜色
            set1.setHighLightColor(Color.parseColor("#e22340")); //设置高亮色
            set1.setColor(Color.parseColor("#e22340"));    //设置曲线的颜色
            set1.setLabel("总胆固醇");
            set2.setLabel("正常");
            set3.setLabel("边缘升高");
            set4.setLabel("升高");
            line_chart.getAxisLeft().setAxisMaxValue(yMax);
        }else if (type==3){
            set1.setLabel("甘油三脂");
            set2.setLabel("正常");
            set1.setCircleColor(Color.parseColor("#892380"));//设置圆点的颜色
            set1.setHighLightColor(Color.parseColor("#892380")); //设置高亮色
            set1.setColor(Color.parseColor("#892380"));    //设置曲线的颜色
            set3.setFillFormatter(new DefaultFillFormatter(1.70f));
            set4.setFillFormatter(new DefaultFillFormatter(2.25f));
            set3.setLabel("边缘升高");
            set4.setLabel("升高");
            line_chart.getAxisLeft().setAxisMaxValue(yMax);
        }else if (type==4){
            set1.setLabel("高密");
            set2.setLabel("降低");
            set3.setLabel("正常");
            set4.setLabel("升高");
            set1.setCircleColor(Color.parseColor("#a9d76f"));//设置圆点的颜色
            set1.setHighLightColor(Color.parseColor("#a9d76f")); //设置高亮色
            set1.setColor(Color.parseColor("#a9d76f"));    //设置曲线的颜色
            set3.setFillFormatter(new DefaultFillFormatter(1.04f));
            set4.setFillFormatter(new DefaultFillFormatter(1.55f));
            line_chart.getAxisLeft().setAxisMaxValue(yMax);
        }else if (type==5){
            set1.setLabel("低密");
            set2.setLabel("正常");
            set3.setLabel("边缘升高");
            set4.setLabel("升高");
            set1.setCircleColor(Color.parseColor("#1f71ac"));//设置圆点的颜色
            set1.setHighLightColor(Color.parseColor("#1f71ac")); //设置高亮色
            set1.setColor(Color.parseColor("#1f71ac"));    //设置曲线的颜色
            set3.setFillFormatter(new DefaultFillFormatter(3.37f));
            set4.setFillFormatter(new DefaultFillFormatter(4.12f));
            line_chart.getAxisLeft().setAxisMaxValue(yMax);
        }

        ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
        lineDataSets.add(set1);
        lineDataSets.add(set4);
        lineDataSets.add(set3);
        lineDataSets.add(set2);
        // add the datasets

        // create a data object with the datasets
        LineData lineData = new LineData(xValues, lineDataSets);//添加数据
        line_chart.setData(lineData);
        line_chart.setNoDataTextDescription("");
        line_chart.moveViewToX(lineData.getXValCount() - 6);
        line_chart.setVisibleXRangeMaximum(7);
    }

}
