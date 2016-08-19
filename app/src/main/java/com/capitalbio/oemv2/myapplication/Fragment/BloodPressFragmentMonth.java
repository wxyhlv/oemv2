package com.capitalbio.oemv2.myapplication.Fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.capitalbio.oemv2.myapplication.Activity.LoginActivity;
import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.Bean.BPJson;
import com.capitalbio.oemv2.myapplication.Bean.BloodPressureBean;
import com.capitalbio.oemv2.myapplication.Bean.BraceleteJson;
import com.capitalbio.oemv2.myapplication.Bean.SportDataDetailBean;
import com.capitalbio.oemv2.myapplication.Const.Base_Url;
import com.capitalbio.oemv2.myapplication.Const.BloodConst;
import com.capitalbio.oemv2.myapplication.Const.BraceleteConst;
import com.capitalbio.oemv2.myapplication.NetUtils.HistoryDownd;
import com.capitalbio.oemv2.myapplication.NetUtils.NetTool;
import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.Utils.BraceleteDataService;
import com.capitalbio.oemv2.myapplication.Utils.Common_dialog;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;
import com.capitalbio.oemv2.myapplication.Utils.PreferencesUtils;
import com.capitalbio.oemv2.myapplication.Utils.TimeStampUtil;
import com.capitalbio.oemv2.myapplication.View.BpMarkerView;
import com.capitalbio.oemv2.myapplication.View.BpYaxisFormatter;
import com.capitalbio.oemv2.myapplication.View.CustomCalendarView;
import com.capitalbio.oemv2.myapplication.View.MyMarkerView;
import com.capitalbio.oemv2.myapplication.View.MyScaleLine;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultFillFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.xutils.db.sqlite.SqlInfo;
import org.xutils.db.table.DbModel;
import org.xutils.db.table.TableEntity;
import org.xutils.ex.DbException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * 血压计设备的月历史界面
 */

public class BloodPressFragmentMonth extends BaseFragment implements OnChartValueSelectedListener {
    List<String> curmonth_historyList  =new ArrayList<>();
    List<HashMap<String,String>> curday_historyList  =new ArrayList<>();

    private CombinedChart mChart;
    private LineChart linechart;
    private int itemcount =10;

    List<BloodPressureBean> totalData = new ArrayList<>();;
  //  List<String> xLables=new ArrayList<>();

    ArrayList<Entry> yVals_MIN = new ArrayList<Entry>();
    ArrayList<Entry> yVals_middle = new ArrayList<Entry>();
    ArrayList<Entry> yVals_max = new ArrayList<Entry>();
    private int flag;//标记当前页面，1：综合，2：收缩压，3 、舒张压，4、心率

    private String monthString;

    private int lastIndex;
    @Override
    protected View loadViewLayout(LayoutInflater inflater, ViewGroup container) {

        return inflater.inflate(R.layout.fg_bloodpress_month,container,false);
    }

    @Override
    protected void findViewById(View view) {

        mChart = (CombinedChart) view.findViewById(R.id.mChart);
        linechart = (LineChart) view.findViewById(R.id.linechart);
        view.findViewById(R.id.rl_sysbp).setOnClickListener(this);
        view.findViewById(R.id.rl_diabp).setOnClickListener(this);
        view.findViewById(R.id.rl_heartrate).setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            // 收缩压
           case R.id.rl_sysbp:
               flag = 2;
               linechart.setVisibility(View.VISIBLE);

               setLineData(BloodConst.sysbp);
               mChart.setVisibility(View.GONE);

               break;
           //舒张压
            case R.id.rl_diabp:
                flag = 3;
                setLineData(BloodConst.diabp);

                linechart.setVisibility(View.VISIBLE);
                mChart.setVisibility(View.GONE);
                break;
            //心率
            case R.id.rl_heartrate:
                flag =4;
                setLineData(BloodConst.heartrate);

                linechart.setVisibility(View.VISIBLE);
                mChart.setVisibility(View.GONE);
                break;
        }

    }

    @Override
    protected void processLogic() {
        monthString = (String) getArguments().get("curmonth");
        getMonthHistory(monthString);
        flag = 1;
      /*  int  days = TimeStampUtil.daysInMonth(monthString);

        if(xLables.size()==0){
            for(int i=0;i<days;i++){
                xLables.add(i+1+"");
        }}*/
        initBlendChart();
        initLineChart();

    }

    /**
     * 初始化混合类型的折线图+柱状图
     */
    private CombinedData data;
    public void initBlendChart(){

        mChart.setDescription("");
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);
        mChart.setNoDataText("无数据");
        mChart.setDescription("");
        // draw bars behind lines
        mChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE
        });

        mChart.getAxisRight().setEnabled(false);
        mChart.getAxisLeft().setValueFormatter(new BpYaxisFormatter());
        //设置是否可以触摸，如为false，则不能拖动，缩放等
        mChart.setTouchEnabled(true);
        //设置是否可以拖拽，缩放
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);
        BpMarkerView mv = new BpMarkerView(mContext, R.layout.custom_marker_view_bp, totalData);
        // define an offset to change the original position of the marker
        // (optional)
        //mv.setOffsets(-mv.getMeasuredWidth() / 2, -mv.getMeasuredHeight());
        // set the marker to the chart
        mChart.setMarkerView(mv);
        mChart.setFilterTouchesWhenObscured(true);


        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(true);
        leftAxis.setLabelCount(9, false);
        leftAxis.setAxisMinValue(0);
        leftAxis.setAxisMaxValue(400);
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelsToSkip(0);
        mChart.setOnChartValueSelectedListener(this);
        flag = 1;
        setDataChangeMonth();

      /*  data = new CombinedData(xLables);
        data.setData(generateLineData());
        data.setData(generateBarData());
        mChart.setData(data);

        mChart.setVisibleXRangeMaximum(7);
        mChart.moveViewToX(lastIndex);
        mChart.getViewPortHandler().setMaximumScaleX(4f);
        mChart.setScaleMinima(0.7f, 1f);*/




    }

    /**
     * 生成混合类型图的折线数据
     */
    private LineData generateLineData() {

        LineData d = new LineData();

        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (int index = 0; index < totalData.size(); index++){
            int xIndex = TimeStampUtil.getDay(totalData.get(index).getTime())-1;
            if(index == totalData.size() -1){
                lastIndex = xIndex;
            }

            entries.add(new Entry(200 + totalData.get(index).getHeartRate(), xIndex));
            OemLog.log("linedata","heart is " + totalData.get(index).getHeartRate());
        }

        LineDataSet set = new LineDataSet(entries, "心率");
        set.setColor(Color.rgb(246, 163, 0));
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.rgb(246, 163, 0));
        set.setCircleSize(5f);
        set.setFillColor(Color.rgb(246, 163, 0));
        set.setDrawCubic(true);
        set.setCubicIntensity(0.05f);
        set.setDrawValues(false);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.rgb(246, 163, 0));

        set.setAxisDependency(YAxis.AxisDependency.LEFT);

        d.addDataSet(set);

        return d;
    }
    /**
     * 生成混合类型图柱状图数据
     */
    private BarData generateBarData() {

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < totalData.size(); i++) {

            int xIndex = TimeStampUtil.getDay(totalData.get(i).getTime())-1;
            float val1 = totalData.get(i).getSysBp();
            float val2 = totalData.get(i).getDiaBp();

            yVals1.add(new BarEntry(new float[] { val1, val2 }, xIndex));
        }

        BarDataSet set = new BarDataSet(yVals1, "");
        set.setColors(getColors());
        set.setStackLabels(new String[]{"舒张压", "收缩压"});


        BarData d= new BarData();
        d.addDataSet(set);
        set.setDrawValues(false);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        return d;
    }


    private int[] getColors() {

        int stacksize = 2;

        // have as many colors as stack-values per entry
        int[] colors = new int[stacksize];

        for (int i = 0; i < stacksize; i++) {
            colors[i] = ColorTemplate.BLOODPRESSURE_COLORS[i];
        }

        return colors;
    }


    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

     //   BarEntry entry = (BarEntry) e;

        if (e.getData() != null) {
            Toast.makeText(mContext, h.getXIndex(), Toast.LENGTH_LONG).show();
            Log.i("VAL SELECTED", "Value: " + dataSetIndex + "xindex" + h.getXIndex());
        } else {

            Log.i("VAL SELECTED", "Value: " + e.getXIndex());
        }
      //  mChart.getBarData().setDrawValues(true);
    }
    @Override
    public void onNothingSelected() {
        // TODO Auto-generated method stub

    }


    public void initLineChart() {

        //设置网格
        linechart.setDrawBorders(false);

        linechart.setDrawGridBackground(false);
        linechart.setBackgroundColor(Color.WHITE);

        linechart.setGridBackgroundColor(Color.rgb(255, 235, 236));
        //在chart上的右下角加描述
        linechart.setDescription("");

        //设置是否可以触摸，如为false，则不能拖动，缩放等
        linechart.setTouchEnabled(true);
        //设置是否可以拖拽，缩放
        linechart.setDragEnabled(true);
        linechart.setScaleEnabled(false);
        //设置是否能扩大扩小
        linechart.setPinchZoom(false);

        // 设置背景颜色
        //   linechart.setBackgroundColor(getResources().getColor(high));
        //设置点击chart图对应的数据弹出标注
        MyMarkerView mv = new MyMarkerView(mContext, R.layout.custom_marker_view,1);
        // define an offset to change the original position of the marker
        // (optional)
        // set the marker to the chart
        linechart.setMarkerView(mv);
        // enable/disable highlight indicators (the lines that indicate the
        // highlighted Entry)
        linechart.getHighlightByTouchPoint(mv.getX(),mv.getY());
        //设置字体格式，如正楷
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
                "OpenSans-Regular.ttf");

        XAxis xAxis = linechart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawLabels(true);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelsToSkip(0);
        YAxis leftAxis = linechart.getAxisLeft();
        leftAxis.setDrawLimitLinesBehindData(true);
        leftAxis.setDrawLabels(true);
        leftAxis.setAxisMaxValue(200f);
        leftAxis.setAxisMinValue(0f);
        leftAxis.setLabelCount(6, true);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setGridLineWidth(1);
        linechart.getAxisRight().setEnabled(false);
        List<Integer> colors = new ArrayList<>();
        List<String> labels = new ArrayList<>();


        Legend l = linechart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setTypeface(tf);
        setLineData(BloodConst.sysbp);




    }


    public void setLineData(int flag) {
        //TODO
        yVals_MIN = new ArrayList<Entry>();
        yVals_middle = new ArrayList<Entry>();
        yVals_max = new ArrayList<Entry>();
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        int  days = TimeStampUtil.daysInMonth(BloodConst.blood_month);
        List<String> xLables = new ArrayList<>();
      /*  for(int i = 0;i<days;i++){
            xLables.add(i+1+"");
        }
*/
        for(int i = 0;i<days;i++) {

            xLables.add(i+1+"");
            if (flag == BloodConst.diabp) {
                //舒张压的界限
                yVals_MIN.add(new Entry(BloodConst.diabp_low_value, i));
                yVals_middle.add(new Entry(BloodConst.diabp_middle_value, i));
                yVals_max.add(new Entry(BloodConst.diabp_high_value, i));
            } else if (flag == BloodConst.sysbp) {
                //收缩压的界限
                yVals_MIN.add(new Entry(BloodConst.sysbp_low_value, i));
                yVals_middle.add(new Entry(BloodConst.sysbp_middle_value, i));
                yVals_max.add(new Entry(BloodConst.sysbp_high_value, i));

            } else {
                //心率的界限

                String age_ = PreferencesUtils.getString(mContext, "age", "25");
                int age = Integer.valueOf(age_);
                if(age<=1){
                    yVals_MIN.add(new Entry(80, i));
                    yVals_middle.add(new Entry(140, i));
                    yVals_max.add(new Entry(BloodConst.sysbp_high_value, i));
                }else if(age>1&&age <6){
                    yVals_MIN.add(new Entry(100, i));
                    yVals_middle.add(new Entry(120, i));
                    yVals_max.add(new Entry(BloodConst.sysbp_high_value, i));
                }else if(age>6 && age<=60){
                    yVals_MIN.add(new Entry(60, i));
                    yVals_middle.add(new Entry(100, i));
                    yVals_max.add(new Entry(BloodConst.sysbp_high_value, i));
                }else{
                    yVals_MIN.add(new Entry(50, i));
                    yVals_middle.add(new Entry(80, i));
                    yVals_max.add(new Entry(BloodConst.sysbp_high_value, i));
                }

            }

        }
            for(int i = 0;i<totalData.size();i++){
            int xIndex = TimeStampUtil.getDay(totalData.get(i).getTime());
                lastIndex = xIndex-1;
            if(flag == BloodConst.diabp){
                yVals.add(new Entry(Float.parseFloat(totalData.get(i).getDiaBp()+""), xIndex-1));

            }else if(flag==BloodConst.sysbp){
                yVals.add(new Entry(Float.parseFloat(totalData.get(i).getSysBp()+""), xIndex-1));
            }else{
                //心率的界限
                yVals.add(new Entry(Float.parseFloat(totalData.get(i).getHeartRate()+""), xIndex-1));
            }
                OemLog.log("linedata","sys is " + totalData.get(i).getSysBp());
                OemLog.log("linedata","dia is " + totalData.get(i).getDiaBp());
                OemLog.log("linedata","heart_ is " + totalData.get(i).getHeartRate());

        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals, "");

        set1.setDrawCubic(true);  //设置曲线为圆滑的线
        set1.setCubicIntensity(0.02f);
        set1.setDrawFilled(false);  //设置包括的范围区域填充颜色
        set1.setDrawCircles(true);  //设置有圆点
        set1.setLineWidth(4f);    //设置线的宽度
        set1.setCircleSize(6f);   //设置小圆的大小
        if(flag == BloodConst.diabp){
            set1.setHighLightColor(Color.parseColor("#00CCFE"));
            set1.setColor(Color.parseColor("#00CCFE"));    //设置曲线的颜色
            set1.setCircleColorHole(Color.parseColor("#FFFFFF"));
            set1.setCircleColor(Color.parseColor("#00CCFE"));
            set1.setLabel("舒张压");
        }else if(flag==BloodConst.sysbp){
            set1.setHighLightColor(Color.parseColor("#7CB737"));
            set1.setColor(Color.parseColor("#7CB737"));    //设置曲线的颜色
            set1.setCircleColorHole(Color.parseColor("#FFFFFF"));
            set1.setCircleColor(Color.parseColor("#7CB737"));
            set1.setLabel("收缩压");
        }else{
            set1.setHighLightColor(Color.parseColor("#F9A300"));
            set1.setColor(Color.parseColor("#F9A300"));    //设置曲线的颜色
            set1.setCircleColorHole(Color.parseColor("#FFFFFF"));
            set1.setCircleColor(Color.parseColor("#F9A300"));
            set1.setLabel("心率");
        }
        set1.setDrawValues(false);

        // create a data object with the datasets
        // LineData data = new LineData(xVals, set1);

        //  Log.i("ymax",linechart.getYMax()+"");
        LineDataSet set2 = new LineDataSet(yVals_MIN, "偏低");

        // set2.setDrawCubic(true);  //设置曲线为圆滑的线

        set2.setDrawFilled(true);  //设置包括的范围区域填充颜色
        set2.setDrawCircles(false);  //设置有圆点
        set2.setLineWidth(1f);    //设置线的宽度
        //set1.setHighLightColor(Color.rgb(244, 117, 117));
        set2.setColor(Color.parseColor("#FFFAE0"));    //设置曲线的颜色
        set2.setFillColor(Color.parseColor("#FFFAE0"));
        set2.setFillAlpha(225);
        set2.setDrawValues(false);
        set2.setHighlightEnabled(false);

        LineDataSet set3 = new LineDataSet(yVals_middle, "正常");

        set3.setDrawCubic(true);  //设置曲线为圆滑的线

        set3.setDrawFilled(true);  //设置包括的范围区域填充颜色
        set3.setDrawCircles(false);  //设置有圆点
        set3.setLineWidth(1f);    //设置线的宽度
        set3.setColor(Color.rgb(214, 246, 222));    //设置曲线的颜色
        set3.setFillColor(Color.rgb(214, 246, 222));
        set3.setFillAlpha(225);
        set3.setHighlightEnabled(false);

        if(flag == BloodConst.sysbp){
            set3.setFillFormatter(new DefaultFillFormatter(BloodConst.sysbp_low_value));

        }else if(flag == BloodConst.diabp){
            set3.setFillFormatter(new DefaultFillFormatter(BloodConst.diabp_low_value));

        }
        else{
            set3.setFillFormatter(new DefaultFillFormatter(yVals_MIN.get(0).getVal()));

        }
        set3.setDrawValues(false);
        LineDataSet set4 = new LineDataSet(yVals_max, "偏高");

        set4.setDrawCubic(true);  //设置曲线为圆滑的线

        set4.setDrawFilled(true);  //设置包括的范围区域填充颜色
        set4.setDrawCircles(false);  //设置有圆点
        set4.setColor(Color.parseColor("#FFEBEC"));    //设置曲线的颜色
        set4.setFillColor(Color.parseColor("#FFEBEC"));
        set4.setFillAlpha(225);
        set4.setFillFormatter(new DefaultFillFormatter(80f));
        set4.setDrawValues(false);
        set4.setHighlightEnabled(false);

        if(flag == BloodConst.sysbp){
            set4.setFillFormatter(new DefaultFillFormatter(BloodConst.sysbp_middle_value));
        }else if(flag == BloodConst.diabp){
            set4.setFillFormatter(new DefaultFillFormatter(BloodConst.diabp_middle_value));
        }
        else{
            set4.setFillFormatter(new DefaultFillFormatter(yVals_middle.get(0).getVal()));
        }
        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();

        dataSets.add(set3);
        dataSets.add(set2);
        dataSets.add(set4);
        dataSets.add(set1);

        LineData data = new LineData(xLables, dataSets);

        linechart.setData(data);
        linechart.setNoDataTextDescription("");
  //      linechart.moveViewToX(data.getXValCount() - 9);
        linechart.setVisibleXRangeMaximum(7);
        linechart.moveViewToX(lastIndex);
        linechart.getViewPortHandler().setMaximumScaleX(4f);
        linechart.setScaleMinima(0.7f, 1f);
        linechart.invalidate();
    }

    public void getMonthHistoryDb(String month){

        if(totalData.size()>0){
            totalData.clear();
        }
        String sql ="select sysBp,diaBp,heartRate,testTime,testDate, MAX(testTime)" + " from bloodPressure"
                + " where testDate like '%" + month + "%' and username = '" + MyApplication.getInstance().getCurrentUserName()
                + "' group by testDate";
        try {
            TableEntity<?> table = TableEntity.get(MyApplication.getDB(), BloodPressureBean.class);
            if(table.tableIsExist()){
                SqlInfo sqlinfo = new SqlInfo(sql);
                Cursor cursor = MyApplication.getDB().execQuery(sqlinfo);
                Log.i("bloodce", "kkkkkkkkkkkkk" + cursor.getCount() + "ge ");
                while (cursor.moveToNext()) {
                    BloodPressureBean bean = new BloodPressureBean();
                    int sysBp = cursor.getInt(cursor.getColumnIndex("sysBp"));
                    int diaBp = cursor.getInt(cursor.getColumnIndex("diaBp"));
                    int heartrate = cursor.getInt(cursor.getColumnIndex("heartRate"));
                    String teatdate = cursor.getString(cursor.getColumnIndex("testDate"));
                    long time = cursor.getLong(cursor.getColumnIndex("testTime"));

                    bean.setHeartRate(heartrate);
                    bean.setSysBp(sysBp);
                    bean.setDiaBp(diaBp);
                    bean.setTestDate(teatdate);
                    bean.setTime(time);

                    totalData.add(bean);
                    Log.i("bloodce", "sysbp is " + cursor.getString(cursor.getColumnIndex("sysBp")));
                    Log.i("bloodce", "diabp is " + cursor.getString(cursor.getColumnIndex("diaBp")));
                    Log.i("bloodce", "heartRate is " + cursor.getString(cursor.getColumnIndex("heartRate")));
                    Log.i("bloodce", "testDate is " + cursor.getString(cursor.getColumnIndex("testDate")));
                    Log.i("bloodce", "testTime is " + cursor.getString(cursor.getColumnIndex("testTime")) + "\n");

                }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }


    }


    private void getMonthHistory(String monthString) {
        //首先从数据库查询
        getMonthHistoryDb(monthString);
        if(totalData.size()<=0){
            getDataByNet(monthString);

        }

    }

    //从网上获取数据
    private void  getDataByNet(String month){
        if(!NetTool.isNetwork(mContext, true)){
            return;
        }
        Common_dialog.startWaitingDialog(getActivity(), "正在加载数据...");
        String url = Base_Url.downloaddayUrl;
        HashMap<String, Object> param = new HashMap<>();
        param.put("modelType", "bloodpressure");
        param.put("url", url);
        param.put("beginDate", month + "-01 00:00:00");
        param.put("endDate", TimeStampUtil.getLastDayOfMonth_(month)+" 23:59:59");

        HistoryDownd.getHistory(mContext, param, new HistoryDownd.RegistOnGetResult() {
            @Override
            public void OnGetResult(int code, String msg, String data) {
                if (code == 11) {
                    MyApplication.getInstance().exit();
                    Toast.makeText(mContext, "用户未登陆,请重新登陆", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else if (code == 0) {
                    OemLog.log("bloodce","");

                    if(totalData.size()>0){
                        totalData.clear();
                    }
                    if(data == null || "".equals(data)|| "[]".equals(data)){
                        OemLog.log("bloodce","111111111111");
                        Toast.makeText(mContext,"无数据",Toast.LENGTH_SHORT).show();
                        setDataChangeMonth();

                    }
                    List<BPJson> list = JSON.parseArray(data, BPJson.class);
                    for (int i = 0; i < list.size(); i++) {
                        BloodPressureBean bean = new BloodPressureBean();
                        bean.setUsername(list.get(i).getUsername());
                        bean.setDataSource("网上");
                        bean.setTime(list.get(i).getTestTime());
                        bean.setSysBp(Integer.valueOf(list.get(i).getHighPressure()));
                        bean.setDiaBp(Integer.valueOf(list.get(i).getLowPressure()));
                        bean.setHeartRate(Integer.valueOf(list.get(i).getHeartRate()));
                        bean.setIsUpload(true);
                        long time = list.get(i).getTestTime();
                        bean.setTestDate(TimeStampUtil.currTime3(time));
                        bean.setTestHour(TimeStampUtil.getHour(time) + "");
                        bean.setTestMinute(TimeStampUtil.getDoubleDay(time) + "");
                        totalData.add(bean);

                        try {
                            MyApplication.getDB().saveOrUpdate(bean);
                            Log.i("bloodce", " after net insert db");
                        } catch (DbException e) {
                            e.printStackTrace();
                        }

                    }
                    OemLog.log("bloodce","22222222222");

                    setDataChangeMonth();


                  /*  if(totalData.size()> 0){
                        setDataChangeMonth();
                    }else{
                        if (mChart.getData() != null) {
                            mChart.clear();
                            mChart.setNoDataText("无数据");
                        }
                    }*/
                    // getMonthHistoryDb(monthString);

                } else {
                    Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
                }
                Common_dialog.stop_WaitingDialog();

            }
        });

    }



    public void update(String date) {
        Log.i("bloodce", "当前日期.... "+date);

        SimpleDateFormat   format = new SimpleDateFormat("yyyy-MM");
        Date dateString = null;
        try {
            dateString = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.i("bloodce", "day is " + dateString + "");
       /* int  days = TimeStampUtil.daysInMonth(date);
        Log.i("bloodce", "day is " + days + "");

        if(xLables.size()>0){xLables.clear();}

        for (int i = 0; i < days; i++) {
            xLables.add(i + 1 + "");
        }*/
        getMonthHistoryDb(date);
        if(totalData.size()<=0){
            getDataByNet(date);

        }else{
            setDataChangeMonth();
        }

  //      data.clearValues();
       // if(totalData.size()>0){







    }

    private void setDataChangeMonth() {
        OemLog.log("bloodce","setDataChangeMonth come .....");
        switch (flag){
            case 1:
                int  days = TimeStampUtil.daysInMonth(BloodConst.blood_month);
                List<String> xLables = new ArrayList<>();
                for(int i = 0;i<days;i++){
                    xLables.add(i+1+"");
                }
                data = new CombinedData(xLables);
                data.setData(generateLineData());
                data.setData(generateBarData());

                mChart.setData(data);
                mChart.setVisibleXRangeMaximum(7);
                mChart.moveViewToX(lastIndex);
                mChart.getViewPortHandler().setMaximumScaleX(4f);
                mChart.setScaleMinima(0.7f, 1f);
                mChart.invalidate();
                break;
            case 2:
                setLineData(BloodConst.sysbp);
                linechart.invalidate();

                break;
            case 3:
                setLineData(BloodConst.diabp);
                linechart.invalidate();

                break;
            case 4:
                setLineData(BloodConst.heartrate);
                linechart.invalidate();

                break;
        }
    }


}
