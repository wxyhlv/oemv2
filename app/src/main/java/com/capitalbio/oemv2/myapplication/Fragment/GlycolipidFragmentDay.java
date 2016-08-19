package com.capitalbio.oemv2.myapplication.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.Bean.BloodPressureBean;
import com.capitalbio.oemv2.myapplication.Const.BodyFatConst;
import com.capitalbio.oemv2.myapplication.Const.GlycolipidConst;
import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;
import com.capitalbio.oemv2.myapplication.Utils.TimeStampUtil;
import com.capitalbio.oemv2.myapplication.View.CustomCalendarView;
import com.capitalbio.oemv2.myapplication.View.recyclerview.MyRecyclerView;

import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by susu on 16-5-16.
 */
public class GlycolipidFragmentDay extends BaseFragment{
    private View view;
    private RelativeLayout rl_gly_bloodsuger,rl_gly_chole,rl_gly_tri,rl_gly_highlip,rl_gly_lowlip;
    private RelativeLayout rl_calendar;//日历
    private TextView tv_blood_number,tv_chole_number,tv_tri_number,tv_hignlip_number,tv_lowlip_number;
    private TextView tv_blood_level,tv_chole_level,tv_tri_level,tv_highlip_level,tv_lowlip_level;
    private TextView tv_bloodstate;//空腹或饭后
    private CustomCalendarView calendarView;
    private String dateString;
    private  List<String> curmonth_historyList  =new ArrayList<>();
    @Override
    protected View loadViewLayout(LayoutInflater inflater, ViewGroup container) {
        view=inflater.inflate(R.layout.glycolipid_day_measure,container,false);

        return view;
    }
    @Override
    protected void findViewById(View view) {
        rl_gly_bloodsuger= (RelativeLayout) view.findViewById(R.id.rl_gly_bloodsuger);
        rl_gly_chole= (RelativeLayout) view.findViewById(R.id.rl_gly_chole);
        rl_gly_tri= (RelativeLayout) view.findViewById(R.id.rl_gly_tri);
        rl_gly_highlip= (RelativeLayout) view.findViewById(R.id.rl_gly_highlip);
        rl_gly_lowlip= (RelativeLayout) view.findViewById(R.id.rl_gly_lowlip);

        rl_calendar= (RelativeLayout) view.findViewById(R.id.rl_calendar);

        tv_blood_number= (TextView) view.findViewById(R.id.tv_blood_number);
        tv_chole_number= (TextView) view.findViewById(R.id.tv_chole_number);
        tv_tri_number= (TextView) view.findViewById(R.id.tv_tri_number);
        tv_hignlip_number= (TextView) view.findViewById(R.id.tv_hignlip_number);
        tv_lowlip_number= (TextView) view.findViewById(R.id.tv_lowlip_number);

        tv_blood_level= (TextView) view.findViewById(R.id.tv_blood_level);
        tv_chole_level= (TextView) view.findViewById(R.id.tv_chole_level);
        tv_tri_level= (TextView) view.findViewById(R.id.tv_tri_level);
        tv_highlip_level= (TextView) view.findViewById(R.id.tv_highlip_level);
        tv_lowlip_level= (TextView) view.findViewById(R.id.tv_lowlip_level);

        tv_bloodstate= (TextView) view.findViewById(R.id.tv_bloodstate);

        dateString = (String) getArguments().get("currmonth");  //yyyy-MM
        String defaultday = TimeStampUtil.getHistoryDefaultDay(dateString); //获取当前年月日yyyy-MM-dd
        Date dfdate = TimeStampUtil.ymdToDate(defaultday); //yyyy-MM-dd
        calendarView = new CustomCalendarView(getActivity(), curmonth_historyList, dfdate);
        rl_calendar.addView(calendarView);
        calendarView.setSelectMore(false);
        calendarView.setOnItemClickListener(new CustomCalendarView.OnItemClickListener() {
            @Override
            public void OnItemClick(Date selectedStartDate, Date selectedEndDate, Date downDate) {

            }
        });
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void processLogic() {

    }

    public void update(){
        String defaultday = TimeStampUtil.getHistoryDefaultDay(GlycolipidConst.month);
        OemLog.log("blooddate", "defaultday is " + defaultday);
        Date dfdate = TimeStampUtil.ymdToDate(defaultday);
        calendarView.setCalendarData(dfdate);
    }
}
