package com.capitalbio.oemv2.myapplication.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.capitalbio.oemv2.myapplication.Activity.LoginActivity;
import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.Bean.BodyJson;
import com.capitalbio.oemv2.myapplication.Const.Base_Url;
import com.capitalbio.oemv2.myapplication.Const.BodyFatConst;
import com.capitalbio.oemv2.myapplication.Devices.BodyFatDevices.src.com.bayh.sdk.ble.bean.BodyFatSaid;
import com.capitalbio.oemv2.myapplication.NetUtils.HistoryDownd;
import com.capitalbio.oemv2.myapplication.NetUtils.NetTool;
import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.Utils.Common_dialog;
import com.capitalbio.oemv2.myapplication.Utils.FatRule;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;
import com.capitalbio.oemv2.myapplication.Utils.PreferencesUtils;
import com.capitalbio.oemv2.myapplication.Utils.TimeStampUtil;
import com.capitalbio.oemv2.myapplication.View.CustomCalendarView;
import com.capitalbio.oemv2.myapplication.View.adapter.GalleryAdapter;
import com.capitalbio.oemv2.myapplication.View.recyclerview.MyRecyclerView;

import org.xutils.db.table.DbModel;
import org.xutils.db.table.TableEntity;
import org.xutils.ex.DbException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 历史记录日
 * Created by wxy on 15-12-4.
 */
public class BodyFatFragmentDay extends BaseFragment {
    private RelativeLayout rl_calendar;
    private CustomCalendarView calendarView;
    List<String> curmonth_historyList = new ArrayList<>();
    private Date date;
    private SimpleDateFormat format;
    ArrayList<String> timePointList = new ArrayList<>();
    private MyRecyclerView mRecyclerView;
    private GalleryAdapter mAdapter;
    private String dateString,curday;
    private String day;//日的double形式
    private List<BodyFatSaid> bodybeans;//月数据
    List<BodyFatSaid> bodyPDayBeans ;
    private TextView tv_value_weight,tv_value_bmi,tv_value_bmr,tv_value_viscus,tv_value_bone,tv_value_fat,tv_value_muscle,tv_value_water;

    private TextView tv_grade_weight,tv_grade_bmi,tv_grade_bmr,tv_grade_viscus,tv_grade_bone,tv_grade_fat,tv_grade_muscle,tv_grade_water;
    private RelativeLayout rl_weight,rl_bmi,rl_bmr,rl_viscus,rl_bone,rl_fat,rl_muscle,rl_water;

    private HashMap<String,Integer> colorMap = new HashMap<>();
    @Override
    protected View loadViewLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fg_bodyfat_day, container, false);
    }

    @Override
    protected void findViewById(View view) {
        rl_calendar = (RelativeLayout) view.findViewById(R.id.rl_calendar);
        String defaultday = TimeStampUtil.getHistoryDefaultDay(BodyFatConst.month);
        OemLog.log("blooddate", "defaultday is " + defaultday);
        Date dfdate = TimeStampUtil.ymdToDate(defaultday);
        //获取月历史记录的信息 用于日历上画记号
        calendarView = new CustomCalendarView(getActivity(), curmonth_historyList, dfdate);
        rl_calendar.addView(calendarView);
        calendarView.setSelectMore(false); //
        calendarView.setOnItemClickListener(new CustomCalendarView.OnItemClickListener() {

            @Override
            public void OnItemClick(Date selectedStartDate,
                                    Date selectedEndDate, Date downDate) {
                getTimePointDate(TimeStampUtil.dateToYmd(downDate));
                mAdapter.notifyDataSetChanged();

            }
        });
        mRecyclerView = (MyRecyclerView) view.findViewById(R.id.id_recyclerview_horizontal);
        tv_grade_weight = (TextView) view.findViewById(R.id.tv_grade_weight);
        tv_grade_bmi = (TextView) view.findViewById(R.id.tv_grade_bmi);
        tv_grade_bmr = (TextView) view.findViewById(R.id.tv_grade_bmr);
        tv_grade_viscus = (TextView) view.findViewById(R.id.tv_grade_viscus);
        tv_grade_bone = (TextView) view.findViewById(R.id.tv_grade_bone);
        tv_grade_fat = (TextView) view.findViewById(R.id.tv_grade_fat);
        tv_grade_muscle = (TextView) view.findViewById(R.id.tv_grade_muscle);
        tv_grade_water = (TextView) view.findViewById(R.id.tv_grade_water);

        tv_value_weight = (TextView) view.findViewById(R.id.tv_value_weight);
        tv_value_bmi = (TextView) view.findViewById(R.id.tv_value_bmi);
        tv_value_bmr = (TextView) view.findViewById(R.id.tv_value_bmr);
        tv_value_viscus = (TextView) view.findViewById(R.id.tv_value_viscus);
        tv_value_bone = (TextView) view.findViewById(R.id.tv_value_bone);
        tv_value_fat = (TextView) view.findViewById(R.id.tv_value_fat);
        tv_value_muscle = (TextView) view.findViewById(R.id.tv_value_muscle);
        tv_value_water = (TextView) view.findViewById(R.id.tv_value_water);

        rl_weight = (RelativeLayout) view.findViewById(R.id.rl_weight);
        rl_bmi = (RelativeLayout) view.findViewById(R.id.rl_bmi);

        rl_bmr = (RelativeLayout) view.findViewById(R.id.rl_bmr);
        rl_viscus = (RelativeLayout) view.findViewById(R.id.rl_viscus);

        rl_bone = (RelativeLayout) view.findViewById(R.id.rl_bone);

        rl_fat = (RelativeLayout) view.findViewById(R.id.rl_fat);

        rl_muscle = (RelativeLayout) view.findViewById(R.id.rl_muscle);

        rl_water = (RelativeLayout) view.findViewById(R.id.rl_water);


        colorMap.put("偏低",R.drawable.bodyfat_high);
        colorMap.put("标准",R.drawable.bodyfat_middle);
        colorMap.put("偏高",R.drawable.bodyfat_high);
        colorMap.put("稍微偏高",R.drawable.bodyfat_high);
    }

   /* @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
       // update();
    }

    @Override
    public void onResume() {
        super.onResume();
        update();
    }*/

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            update();
        }

    }

    /**
     * 获取日历控件中标记的日子
     * month yyyy-mm
     */
    public void getMonthHistoryData(String month) {
        getMonthDataFromDB(month);
        if(curmonth_historyList.size()<=0){
            getCountSplitBet(month);
        }
    }

    private void getMonthDataFromDB(String month) {
        try {
            curmonth_historyList.clear();
            String[] colomns = new String[]{"ymd"};
            TableEntity<?> table = TableEntity.get(MyApplication.getDB(), BodyFatSaid.class);
            if(table.tableIsExist()) {
                List<DbModel> all = MyApplication.getDB().selector(BodyFatSaid.class)
                        .where("username", "=", MyApplication.getInstance().getCurrentUserName())
                        .and("ymd", "like", "%" + month + "%")
                        .groupBy("ymd").select(colomns).findAll();
                if (all != null && all.size() > 0) {

                    for (int i = 0; i < all.size(); i++) {
                        String testDate = all.get(i).getString("ymd");
                        if (testDate != null && !curmonth_historyList.contains(testDate)) {
                            curmonth_historyList.add(testDate);
                        }
                    }
                    setMarkedDate();
                }else{
                    timePointList.clear();
                    if(mAdapter!=null){
                        mAdapter.notifyDataSetChanged();
                    }
                }
                Log.i("body", "curmonth_historyList is " + curmonth_historyList.size() + "ge ");

            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    private void setMarkedDate() {
        if(calendarView!=null){
            calendarView.setvalue(curmonth_historyList);
        }
        String defaultday = TimeStampUtil.getHistoryDefaultDay(BodyFatConst.month);

        getTimePointDate(defaultday);
        if(mAdapter!=null&&timePointList.size()>=0){
            mAdapter.setSelectedPosition(timePointList.size() - 1);
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
 * 获取某一天的时间点记录
 * param day yyyy-mm-dd
 */
    public void getTimePointDate(String day){
        OemLog.log("bodydb","before db");

        timePointList.clear();
        bodyPDayBeans = new ArrayList<>();
        try {
            bodyPDayBeans = MyApplication.getDB().selector(BodyFatSaid.class).where("username", "=", MyApplication.getInstance().getCurrentUserName())
                    .and("ymd", "=", day)
                    .orderBy("longTime")
                    .findAll();
            if(bodyPDayBeans!=null&& bodyPDayBeans.size()>0){
                for (int i = 0;i<bodyPDayBeans.size();i++){
                    long time =bodyPDayBeans.get(i).getLongTime();
                    if(time!=0){
                        timePointList.add(TimeStampUtil.singleIntToDoubleString(TimeStampUtil.getHour(time))+":"+
                                TimeStampUtil.singleIntToDoubleString(TimeStampUtil.getMinute(time)));
                    }
                    Log.i("body","time is ..."+TimeStampUtil.getHour(time)+":"+TimeStampUtil.getMinute(time));
                }}
        } catch (DbException e) {
            e.printStackTrace();
        }
        Log.i("body", "timePointList size is " + timePointList.size() + "");
        if(timePointList.size()>0){
            if(mRecyclerView!=null){
                mRecyclerView.scrollToPosition(timePointList.size() - 1);
            }
            setDataByTimePoint(bodyPDayBeans.get(timePointList.size() - 1));
            if(mAdapter!=null){
                mAdapter.setSelectedPosition(timePointList.size()-1);
            }
        }else{
            setDataByTimePoint(null);
        }
        OemLog.log("bodydb","after db");

    }

    private void setDataByTimePoint(BodyFatSaid bodyFatSaid){

         if(bodyFatSaid!=null){
             tv_value_weight.setText(bodyFatSaid.getWeight()+"");
             String datasource  = bodyFatSaid.getDataSource();
             if(datasource.equals("手动录入")){
                 tv_value_bmi.setText(bodyFatSaid.getBmi()+"");
                 tv_value_bmr.setText("0.0");
                 tv_value_viscus.setText("0.0");
                 tv_value_bone.setText("0.0");
                 tv_value_fat.setText("0.0");
                 tv_value_muscle.setText("0.0");
                 tv_value_water.setText("0.0");
                 String weightGrade = bodyFatSaid.getWeightGrade();
                 tv_grade_weight.setText(weightGrade);
                 tv_grade_bmi.setText(weightGrade);
                 tv_grade_bmr.setText("----");
                 tv_grade_viscus.setText("----");
                 tv_grade_bone.setText("----");
                 tv_grade_fat.setText("----");
                 tv_grade_muscle.setText("----");
                 tv_grade_water.setText("----");
                 rl_weight.setBackgroundResource(colorMap.get(weightGrade)==null?R.drawable.bodyfat_middle:colorMap.get(weightGrade));
                 rl_bmi.setBackgroundResource(colorMap.get(weightGrade)==null?R.drawable.bodyfat_middle:colorMap.get(weightGrade));
                 rl_bmr.setBackgroundResource(R.drawable.bodyfat_middle);
                 rl_bone.setBackgroundResource(R.drawable.bodyfat_middle);
                 rl_fat.setBackgroundResource(R.drawable.bodyfat_middle);
                 rl_muscle.setBackgroundResource(R.drawable.bodyfat_middle);
                 rl_viscus.setBackgroundResource(R.drawable.bodyfat_middle);
                 rl_water.setBackgroundResource(R.drawable.bodyfat_middle);
                 return;
             }else{
                 tv_value_bmi.setText(bodyFatSaid.getBmi() + "");
                 tv_value_bmr.setText(bodyFatSaid.getKcal()+"");
                 tv_value_viscus.setText(bodyFatSaid.getVisceraFat()+"");
                 tv_value_bone.setText(bodyFatSaid.getBone()+"");
                 tv_value_fat.setText(bodyFatSaid.getFat()+"%");
                 tv_value_muscle.setText(bodyFatSaid.getMuscle()+"%");
                 tv_value_water.setText(bodyFatSaid.getWater() + "%");

                 String weightGrade = bodyFatSaid.getWeightGrade()+"";
                 String bmrGrade = bodyFatSaid.getBmrGrade()+"";
                 String visceraGrade = bodyFatSaid.getVisceraGrade()+"";
                 String boneGrade = bodyFatSaid.getBoneGrade()+"";
                 String fatGrade = bodyFatSaid.getFatGrade()+"";
                 String muscleGrade = bodyFatSaid.getMuscleGrade()+"";

                 String waterGrade = bodyFatSaid.getWaterGrade()+"";

                 tv_grade_weight.setText(weightGrade);
                 tv_grade_bmi.setText(weightGrade);
                 tv_grade_bmr.setText(bmrGrade);
                 tv_grade_viscus.setText(bodyFatSaid.getVisceraGrade() + "");
                 tv_grade_bone.setText(boneGrade + "");
                 tv_grade_fat.setText(bodyFatSaid.getFatGrade() + "");
                 tv_grade_muscle.setText(bodyFatSaid.getMuscleGrade() + "");
                 tv_grade_water.setText(bodyFatSaid.getWaterGrade() + "");
                 rl_weight.setBackgroundResource(colorMap.get(weightGrade) == null ? R.drawable.bodyfat_middle : colorMap.get(weightGrade));

                 rl_bmi.setBackgroundResource(colorMap.get(weightGrade)==null?R.drawable.bodyfat_middle:colorMap.get(weightGrade));
                 rl_bmr.setBackgroundResource(colorMap.get(bmrGrade)==null?R.drawable.bodyfat_middle:colorMap.get(bmrGrade));
                 rl_viscus.setBackgroundResource(colorMap.get(visceraGrade)==null?R.drawable.bodyfat_middle:colorMap.get(visceraGrade));
                 rl_bone.setBackgroundResource(colorMap.get(boneGrade)==null?R.drawable.bodyfat_middle:colorMap.get(boneGrade));
                 rl_fat.setBackgroundResource(colorMap.get(fatGrade)==null?R.drawable.bodyfat_middle:colorMap.get(fatGrade));
                 rl_muscle.setBackgroundResource(colorMap.get(muscleGrade)==null?R.drawable.bodyfat_middle:colorMap.get(muscleGrade));
                 rl_water.setBackgroundResource(colorMap.get(waterGrade)==null?R.drawable.bodyfat_middle:colorMap.get(waterGrade));


             }


         }else{
             tv_value_weight.setText("0.0");
             tv_value_bmi.setText("0.0");
             tv_value_bmr.setText("0.0");
             tv_value_viscus.setText("0.0");
             tv_value_bone.setText("0.0");
             tv_value_fat.setText("0.0");
             tv_value_muscle.setText("0.0");
             tv_value_water.setText("0.0");
             tv_grade_weight.setText("----");
             tv_grade_bmi.setText("----");
             tv_grade_bmr.setText("----");
             tv_grade_viscus.setText("----");
             tv_grade_bone.setText("----");
             tv_grade_fat.setText("----");
             tv_grade_muscle.setText("----");
             tv_grade_water.setText("----");
             rl_weight.setBackgroundResource(R.drawable.bodyfat_middle);
             rl_bmi.setBackgroundResource(R.drawable.bodyfat_middle);
             rl_bmr.setBackgroundResource(R.drawable.bodyfat_middle);
             rl_bone.setBackgroundResource(R.drawable.bodyfat_middle);
             rl_fat.setBackgroundResource(R.drawable.bodyfat_middle);
             rl_muscle.setBackgroundResource(R.drawable.bodyfat_middle);
             rl_viscus.setBackgroundResource(R.drawable.bodyfat_middle);
             rl_water.setBackgroundResource(R.drawable.bodyfat_middle);
         }


    }
    @Override
    public void onClick(View view) {

    }
    private int lastSelectedPosition;
    @Override
    protected void processLogic() {
        update();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.scrollToPosition(timePointList.size() - 1);
        lastSelectedPosition=timePointList.size()-1;
        mAdapter = new GalleryAdapter(mContext, timePointList,lastSelectedPosition);
        mRecyclerView.setAdapter(mAdapter);


        mAdapter.setOnItemClickLitener(new GalleryAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                mAdapter.setSelectedPosition(position);
                lastSelectedPosition = position;
                //TODO
                setDataByTimePoint(bodyPDayBeans.get(position));

            }
        });
        mAdapter.setOnItemLongClickLitener(new GalleryAdapter.OnItemLongClickLitener() {
            @Override
            public void onItemLongClick(View view, int position) {
                final int index = position;
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("确认删除吗？");
                builder.setTitle("提示");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        timePointList.remove(index);
                        if (index <= lastSelectedPosition) {
                            lastSelectedPosition -= 1;
                        }
                        mAdapter.setSelectedPosition(lastSelectedPosition);
                        int id = bodyPDayBeans.get(index).getId();
                        try {
                            MyApplication.getDB().deleteById(BodyFatSaid.class,id);
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override


                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }

                });
                builder.create().show();
            }
        });

          }



    public void update() {
        String defaultday = TimeStampUtil.getHistoryDefaultDay(BodyFatConst.month);
        OemLog.log("blooddate", "defaultday is " + defaultday);
        Date dfdate = TimeStampUtil.ymdToDate(defaultday);
        calendarView.setCalendarData(dfdate);
        getMonthHistoryData(BodyFatConst.month);
        //getTimePointDate(defaultday);
    }

    private void getCountSplitBet(final String month){
        if(!NetTool.isNetwork(mContext, true)){
            return;
        }
        Common_dialog.startWaitingDialog(getActivity(), "正在加载数据...");
        String url = Base_Url.downloadcount;
        HashMap<String, Object> param = new HashMap<>();
        param.put("modelType", "bodyfat");
        param.put("url", url);
        param.put("beginDate", month+"-01 00:00:00");
        param.put("endDate", TimeStampUtil.getLastDayOfMonth_(month)+" 23:59:59");
        HistoryDownd.getHistory(mContext, param, new HistoryDownd.RegistOnGetResult() {
            @Override
            public void OnGetResult(int code, String msg, String data) {
                if (code == 11) {
                    MyApplication.getInstance().exit();
                    Toast.makeText(mContext, "用户未登陆,请重新登陆", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    Common_dialog.stop_WaitingDialog();
                } else if (code == 0) {
                    int count = Integer.valueOf(data);
                    Log.i("body", " from net  count is " + count);

                    if (count > 0) {
                        //分页获取数据
                        int num = (int) Math.ceil((double) count / (double) 50);
                        for (int i = 0; i < num; i++) {

                            getDataSplitByNet(month, i * 50 + "", 50 + "");
                        }
                    }
                } else {
                    Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
                }
                Common_dialog.stop_WaitingDialog();

            }
        });
    }

    //从网上分页获取数据
    private void  getDataSplitByNet(String month,String startNum,String showCount){

        String url = Base_Url.batch_Download_Url;
        HashMap<String, Object> param = new HashMap<>();
        param.put("modelType", "bodyfat");
        param.put("url", url);
        param.put("beginDate", month+"-01 00:00:00");
        param.put("endDate", TimeStampUtil.getLastDayOfMonth_(month)+" 23:59:59");

        param.put("startNum",startNum );
        param.put("showCount",showCount );

        HistoryDownd.getHistory(mContext, param, new HistoryDownd.RegistOnGetResult() {
            @Override
            public void OnGetResult(int code, String msg, String data) {
                if (code == 11) {
                    MyApplication.getInstance().exit();
                    Toast.makeText(mContext, "用户未登陆,请重新登陆", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else if (code == 0) {
                    List<BodyJson> list = JSON.parseArray(data, BodyJson.class);
                    for (int i = 0; i < list.size(); i++) {
                        BodyFatSaid bean = new BodyFatSaid();
                        bean.setUserName(list.get(i).getUsername());
                        bean.setIsUpload(true);
                        bean.setLongTime(list.get(i).getTestTime());
                        float bone =list.get(i).getBone();
                        bean.setBone(bone);
                        float weight = Float.parseFloat(list.get(i).getWeight());
                        bean.setWeight(weight);

                        float fat= list.get(i).getFat();
                        bean.setFat(fat);

                        float water = list.get(i).getWater();
                        bean.setWater(water);

                        float muscle = list.get(i).getMuscle();
                        bean.setMuscle(muscle);

                        float visceraFat = list.get(i).getVisceralLevel();
                        bean.setVisceraFat(list.get(i).getVisceralLevel());

                        String bmi = list.get(i).getBmi();
                        bean.setBmi(bmi);
                        String sex = PreferencesUtils.getString(mContext,"sex","男");
                        String age = PreferencesUtils.getString(mContext, "age", "20");
                        String height = PreferencesUtils.getString(mContext,"height","175");

                        int kcal = list.get(i).getBmr();
                        bean.setKcal(kcal);
                        String weightGrade = FatRule.getRate(weight,BodyFatConst.TYPE_WEIGHT,sex,Integer.parseInt(age),Integer.valueOf(height),weight);
                        String bmiGrade = weightGrade;
                        String bmrGrade = FatRule.getRate(weight,BodyFatConst.TYPE_BMR,sex,Integer.parseInt(age),Integer.valueOf(height),kcal);
                        String viscerGrade = FatRule.getRate(weight,BodyFatConst.TYPE_VISCERAL_LEVAL,sex,Integer.parseInt(age),Integer.valueOf(height),visceraFat);
                        String boneGrade = FatRule.getRate(weight,BodyFatConst.TYPE_BONECONTENT,sex,Integer.parseInt(age),Integer.valueOf(height),bone);
                        String fatGrade = FatRule.getRate(weight,BodyFatConst.TYPE_FAT_RATION,sex,Integer.parseInt(age),Integer.valueOf(height),fat);
                        String muscleGrade = FatRule.getRate(weight,BodyFatConst.TYPE_MUSLE_CONTENT,sex,Integer.parseInt(age),Integer.valueOf(height),muscle/100);
                        String waterGrade = FatRule.getRate(weight,BodyFatConst.TYPE_WATER_CONTENT,sex,Integer.parseInt(age),Integer.valueOf(height),water/100);

                        bean.setWeightGrade(weightGrade);
                        bean.setBmrGrade(bmrGrade);
                        bean.setVisceraGrade(viscerGrade);
                        bean.setBoneGrade(boneGrade);
                        bean.setFatGrade(fatGrade);
                        bean.setMuscleGrade(muscleGrade);
                        bean.setWaterGrade(waterGrade);
                        bean.setDataSource("网上");
                        long time = list.get(i).getTestTime();
                        bean.setYmd(TimeStampUtil.currTime3(time));
                        bean.setTestHour(TimeStampUtil.getHour(time) + "");
                        bean.setTestMinute(TimeStampUtil.getDoubleDay(time) + "");
                        if(!curmonth_historyList.contains(bean.getYmd())){
                            curmonth_historyList.add(bean.getYmd());
                        }

                        try {
                            MyApplication.getDB().saveOrUpdate(bean);
                            Log.i("bloodce", " after net insert db");
                        } catch (DbException e) {
                            e.printStackTrace();
                        }

                    }
                    setMarkedDate();
                   // getMonthDataFromDB(dateString);

                } else {
                    Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
                }
                Common_dialog.stop_WaitingDialog();
            }
        });

    }
}
