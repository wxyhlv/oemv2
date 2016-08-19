package com.capitalbio.oemv2.myapplication.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.capitalbio.oemv2.myapplication.Activity.LoginActivity;
import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.Bean.BPJson;
import com.capitalbio.oemv2.myapplication.Bean.BloodPressureBean;
import com.capitalbio.oemv2.myapplication.Const.Base_Url;
import com.capitalbio.oemv2.myapplication.NetUtils.HistoryDownd;
import com.capitalbio.oemv2.myapplication.NetUtils.NetTool;
import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.Utils.BloodPressDataService;
import com.capitalbio.oemv2.myapplication.Utils.BloodPressDataTransfer;
import com.capitalbio.oemv2.myapplication.Utils.Common_dialog;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;
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
 * 血压计设备的日历史界面
 */

public class BloodPressFragmentDay extends BaseFragment {
    private RelativeLayout rl_calendar;
    private CustomCalendarView calendarView;
    List<String> curmonth_historyList  =new ArrayList<>();
    List<HashMap<String,String>> curday_historyList  =new ArrayList<>();
    ArrayList<String> timePointList = new ArrayList<>();
    List<BloodPressureBean> bloodPMonthBeans =null;
    List<BloodPressureBean> bloodPDayBeans = new ArrayList<>();
    private Date date_month,curdate;
    private String day;//当前日
    private MyRecyclerView mRecyclerView;
    private GalleryAdapter mAdapter;
    private TextView tv_value_sysbp,tv_value_diabp,tv_value_heartrate;
    private String dateString;
    private RelativeLayout rlSys,rlDia,rlHeart;
    @Override
    protected View loadViewLayout(LayoutInflater inflater, ViewGroup container) {

        return inflater.inflate(R.layout.fg_bloodpress_day,container,false);
    }

    @Override
    protected void findViewById(View view) {
        rl_calendar =(RelativeLayout) view.findViewById(R.id.rl_calendar);
        rlSys = (RelativeLayout) view.findViewById(R.id.rl_bloodpressday_sys);
        rlDia = (RelativeLayout) view.findViewById(R.id.rl_bloodpressday_dia);
        rlHeart = (RelativeLayout) view.findViewById(R.id.rl_bloodpressday_heart);
        tv_value_sysbp = (TextView) view.findViewById(R.id.tv_value_sysbp);
        tv_value_diabp = (TextView) view.findViewById(R.id.tv_value_diabp);
        tv_value_heartrate = (TextView) view.findViewById(R.id.tv_value_heartrate);
        dateString = (String) getArguments().get("curmonth");
        String defaultday = TimeStampUtil.getHistoryDefaultDay(dateString);
        Date dfdate = TimeStampUtil.ymdToDate(defaultday);


        calendarView = new CustomCalendarView(getActivity(), curmonth_historyList, dfdate);
        rl_calendar.addView(calendarView);
        calendarView.setSelectMore(false); //
        calendarView.setOnItemClickListener(new CustomCalendarView.OnItemClickListener() {

            @Override
            public void OnItemClick(Date selectedStartDate,
                                    Date selectedEndDate, Date downDate) {
                getDayHistory(TimeStampUtil.dateToYmd(downDate));
                mAdapter.setSelectedPosition(timePointList.size() - 1);

                mAdapter.notifyDataSetChanged();
            }
        });
        mRecyclerView = (MyRecyclerView) view.findViewById(R.id.id_recyclerview_horizontal);


    }

    /**
     * 查询月历史纪录
     * 月份参数为“Y-M”
     * @param month
     */

    private void getMonthHistoryData(final String month) {

        //TODO 从数据库查询如果没有则从网上查询当月的历史纪录
        Log.i("bloodce", " is  month  " + month);

        getMonthDataFromDB(month);
        if(curmonth_historyList==null||curmonth_historyList.size()<=0){
            getCountSplitBet(month);
        }

    }

    private  void getMonthDataFromDB(String month) {
        try {
            curmonth_historyList.clear();
           // bloodPMonthBeans = MyApplication.getDB().findAll(BloodPressureBean.class);
            String[] colomns = new String[]{"testDate"};
            TableEntity<?> table = TableEntity.get(MyApplication.getDB(), BloodPressureBean.class);
            List<DbModel> all = null;
            if(table.tableIsExist()) {

                all = MyApplication.getDB().selector(BloodPressureBean.class).where("username", "=", MyApplication.getInstance().getCurrentUserName())
                        .and("testDate", "like", "%" + month + "%")
                        .groupBy("testDate").select(colomns).findAll();
                if (all != null && all.size() > 0) {

                    for (int i = 0; i < all.size(); i++) {
                        String testDate = all.get(i).getString("testDate");
                        if (testDate != null && !curmonth_historyList.contains(testDate)) {
                            curmonth_historyList.add(testDate);
                        }
                    }
                    Log.i("bloodhistory", " search db ..." + "db has data");
                    setMarkedDate();
                }else{
                    timePointList.clear();
                    if(mAdapter!=null){
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }
        Log.i("bloodce", "curmonth_historyList is " +curmonth_historyList.size()+"ge ");
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    private void setMarkedDate() {
        if(calendarView!=null){
        calendarView.setvalue(curmonth_historyList);
        }
        String defaultday = TimeStampUtil.getHistoryDefaultDay(dateString);

        getDayHistory(defaultday);
        if(mAdapter!=null&&timePointList.size()>=0){
            mAdapter.setSelectedPosition(timePointList.size() - 1);
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 查询日历史纪录
     * 月份参数为“Y-M”
     * @param day
     */

    private  void  getDayHistory(String day){
        Log.i("bloodce", "params day is " + day);
        timePointList.clear();

        try {
            bloodPDayBeans = MyApplication.getDB().selector(BloodPressureBean.class).where("username", "=", MyApplication.getInstance().getCurrentUserName())
                    .and("testDate", "=", day)
                    .orderBy("testTime")
                    .findAll();
            if(bloodPDayBeans!=null&& bloodPDayBeans.size()>0){
            for (int i = 0;i<bloodPDayBeans.size();i++){
                long time =bloodPDayBeans.get(i).getTime();
                if(time!=0){
                    timePointList.add(TimeStampUtil.singleIntToDoubleString(TimeStampUtil.getHour(time))
                            +":"+TimeStampUtil.singleIntToDoubleString(TimeStampUtil.getMinute(time)));
                }
                Log.i("bloodce","time is ..."+time);
            }}
        } catch (DbException e) {
            e.printStackTrace();
        }

        if(timePointList.size()>0){
            if(mRecyclerView!=null){
            mRecyclerView.scrollToPosition(timePointList.size() - 1);}

            setDataByTimePoint(bloodPDayBeans.get(timePointList.size()-1));
        }else{
            tv_value_diabp.setText("0");
            tv_value_sysbp.setText("0");
            tv_value_heartrate.setText("0");
            rlSys.setBackgroundResource(R.drawable.bodyfat_middle);
            rlDia.setBackgroundResource(R.drawable.bodyfat_middle);
            rlHeart.setBackgroundResource(R.drawable.bodyfat_middle);
        }

    }

    private void getCountSplitBet(final String month){
        if(!NetTool.isNetwork(mContext, true)){
            return;
        }
        Common_dialog.startWaitingDialog(getActivity(), "正在加载数据...");
        String url = Base_Url.downloadcount;
        HashMap<String, Object> param = new HashMap<>();
        param.put("modelType", "bloodpressure");
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
                    Log.i("bloodhistory", " dowload net..." + count +"条数据");

                    if (count > 0) {
                        //分页获取数据
                        int num = (int) Math.ceil((double) count / (double) 30);
                        for (int i = 0; i < num; i++) {

                            getDataSplitByNet(month, i * 30 , 30);
                        }
                    }else{
                        curmonth_historyList.clear();
                        if(calendarView!=null){
                            calendarView.setvalue(curmonth_historyList);
                        }
                        timePointList.clear();
                        mAdapter.notifyDataSetChanged();
                        Common_dialog.stop_WaitingDialog();
                    }
                } else {
                    Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
                    Common_dialog.stop_WaitingDialog();

                }
            }
        });
    }

    //从网上分页获取数据
   private void  getDataSplitByNet(String month,int startNum,int showCount){

       String url = Base_Url.batch_Download_Url;
       HashMap<String, Object> param = new HashMap<>();
       param.put("modelType", "bloodpressure");
       param.put("url", url);
       param.put("beginDate", month + "-01 00:00:00");
       param.put("endDate", TimeStampUtil.getLastDayOfMonth_(month) + " 23:59:59");

       param.put("startNum", startNum);
       param.put("showCount",showCount );

       String[] timeinf0 = month.split("-");
       int yearI = Integer.valueOf(timeinf0[0]);
       int monthI = Integer.valueOf(timeinf0[1]);
       param.put("endDate", TimeStampUtil.getLastDayOfMonth(yearI, monthI) + " 23:59:59");
       HistoryDownd.getHistory(mContext, param, new HistoryDownd.RegistOnGetResult() {
           @Override
           public void OnGetResult(int code, String msg, String data) {
               if (code == 11) {
                   MyApplication.getInstance().exit();
                   Toast.makeText(mContext, "用户未登陆,请重新登陆", Toast.LENGTH_SHORT).show();
                   Intent intent = new Intent(getActivity(), LoginActivity.class);
                   startActivity(intent);
               } else if (code == 0) {
                   List<BPJson> list = JSON.parseArray(data, BPJson.class);
                   OemLog.log("blooddownsplit", "list is " + list.size());
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
                       bean.setTestMinute(TimeStampUtil.getMinute(time) + "");
                       OemLog.log("blooddownsplit", "testdate is " + TimeStampUtil.currTime3(time));

                       if (!curmonth_historyList.contains(bean.getTestDate())) {
                           curmonth_historyList.add(bean.getTestDate());
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


    @Override
    public void onClick(View view) {

    }

    private int lastSelectedPosition;
    @Override
    protected void processLogic() {

        update(dateString);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.scrollToPosition(timePointList.size() - 1);
        lastSelectedPosition=timePointList.size()-1;
        mAdapter = new GalleryAdapter(mContext, timePointList,timePointList.size()-1);
        mRecyclerView.setAdapter(mAdapter);


        mAdapter.setOnItemClickLitener(new GalleryAdapter.OnItemClickLitener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                mAdapter.setSelectedPosition(position);
                lastSelectedPosition = position;
                setDataByTimePoint(bloodPDayBeans.get(position));

            }
        });
        mAdapter.setOnItemLongClickLitener(new GalleryAdapter.OnItemLongClickLitener() {
            @Override
            public void onItemLongClick(View view, final int position) {
                final int index = position;
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("确认删除吗？");
                builder.setTitle("提示");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        timePointList.remove(index);
                       if (index <=lastSelectedPosition) {
                            lastSelectedPosition -= 1;
                        }
                        mAdapter.setSelectedPosition(lastSelectedPosition);
                        int id = bloodPDayBeans.get(index).getDeviceId();
                        BloodPressDataService.deleteFromDB(id);
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

        //初始化
    }

    private void setDataByTimePoint(BloodPressureBean bloodPressureBean){
        int sysbp =  bloodPressureBean.getSysBp();
        int diaBp = bloodPressureBean.getDiaBp();
        int heartRate = bloodPressureBean.getHeartRate();
        Log.i("bloodce","bloodResult is .sysbp...."+sysbp );
        Log.i("bloodce", "bloodResult is .diaBp...." + diaBp);
        Log.i("bloodce", "bloodResult is .heartRate...." + heartRate);

        tv_value_diabp.setText(diaBp + "");
        tv_value_sysbp.setText(sysbp + "");
        tv_value_heartrate.setText(heartRate+"");
        refreshCircle(bloodPressureBean);
    }
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public void update(String date) {
        Log.i("bloodce", "当前日期.... " + date);

        String defaultday = TimeStampUtil.getHistoryDefaultDay(date);
        OemLog.log("blooddate", "defaultday is " + defaultday);
        Date dfdate = TimeStampUtil.ymdToDate(defaultday);
        getMonthHistoryData(date);
        calendarView.setCalendarData(dfdate);
    }
    //更新 三个圆
    private void refreshCircle(BloodPressureBean bloodPressureBean){
        if(bloodPressureBean==null){
            rlSys.setBackgroundResource(R.drawable.bodyfat_middle);
            rlDia.setBackgroundResource(R.drawable.bodyfat_middle);
            rlHeart.setBackgroundResource(R.drawable.bodyfat_middle);
            return;
        }

        int heart_level = BloodPressDataTransfer.parseHeart(bloodPressureBean.getHeartRate(), getActivity());
        int sys = bloodPressureBean.getSysBp();
        int dia = bloodPressureBean.getDiaBp();
        //收缩压
        if(sys>=90&&sys<=139){
            rlSys.setBackgroundResource(R.drawable.bodyfat_middle);
        }else{
            rlSys.setBackgroundResource(R.drawable.bodyfat_high);
        }
        //舒张压
        if(dia>=60&&dia<=80){
            rlDia.setBackgroundResource(R.drawable.bodyfat_middle);
        }else{
            rlDia.setBackgroundResource(R.drawable.bodyfat_high);
        }

        //心率
        if(heart_level==BloodPressDataTransfer.HEARTRATE_MIDDLE){
            rlHeart.setBackgroundResource(R.drawable.bodyfat_middle);
        }else{
            rlHeart.setBackgroundResource(R.drawable.bodyfat_high);
        }

    }

}
