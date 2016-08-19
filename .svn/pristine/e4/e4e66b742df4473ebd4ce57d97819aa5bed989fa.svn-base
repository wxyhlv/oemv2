package com.capitalbio.oemv2.myapplication.Activity;

import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.Bean.WarnInfo;
import com.capitalbio.oemv2.myapplication.BraceleteLib.BraceleteDevices;
import com.capitalbio.oemv2.myapplication.Devices.Bracelete.BraceleteCommand.AndReminderBraceleteCommand;
import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;
import com.capitalbio.oemv2.myapplication.adapter.CustomWarnAdapter;

import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;

public class BraceleteCustomWarnSettingActivity extends BaseActivity implements View.OnClickListener {

    RelativeLayout contentLayout;
    ListView listview;
    CustomWarnAdapter adpter;
    List<WarnInfo> list = new ArrayList<>();

    int REQUSET =3;
    int MODIFY =4;
    @Override
    protected void initChildLayout() {
        initChildView();
    }

    private void initChildView() {
        rl.setBackgroundResource(R.color.bg_content);
        rlNavigateBar.setBackgroundResource(R.drawable.bg_tang_zhi_san_xiang);
        setLeftTopIcon(R.drawable.ic_back);
        contentLayout = (RelativeLayout) View.inflate(this, R.layout.activity_customsetting, null);
        llBody.addView(contentLayout);
        setTvTopTitle(R.string.customWarn);
        //setIvTopRight(R.string.ok);
        //  findViewById(R.id.tv_addwarn).setOnClickListener(this);
        listview = (ListView) findViewById(R.id.listview);
        list = getDate();
        adpter = new CustomWarnAdapter(getContext(), list);
        listview.setAdapter(adpter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == list.size() - 1) {

                    Intent intent = new Intent(context, BraceleteAddWarnActivity.class);
                    intent.putExtra("modify",false);

                    startActivityForResult(intent, REQUSET);
                }else{
                    Intent intent = new Intent(context, BraceleteAddWarnActivity.class);
                    intent.putExtra("modify",true);
                    intent.putExtra("position","" + position);
                    intent.putExtra("warntype",list.get(position).getWarnType());
                    intent.putExtra("warntime",list.get(position).getWarnTime());
                    OemLog.log("repeat",list.get(position).getRepeat() + " iiii");
                    intent.putExtra("warnrepeat",list.get(position).getRepeat());
                    intent.putExtra("warnmemo",list.get(position).getWarnContent());
                    startActivityForResult(intent, MODIFY);
                }
            }
        });
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                if (position != list.size() - 1) {
                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
                    final android.support.v7.app.AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    Window window = alertDialog.getWindow();
                    window.setContentView(R.layout.dialog_devicesetting);

                    TextView tvtitle = (TextView) window.findViewById(R.id.tv_devicesetting_dialog_title);
                    tvtitle.setText("是否删除提醒？");
                    RelativeLayout rlyes = (RelativeLayout) window.findViewById(R.id.rl_devicesetting_dialog_yes);
                    RelativeLayout rlno = (RelativeLayout) window.findViewById(R.id.rl_devicesetting_dialog_no);

                    rlyes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //是
                            try {
                                MyApplication.getDB().delete(list.get(position));
                            } catch (DbException e) {
                                e.printStackTrace();
                            }
                            list.remove(position);
                            adpter.notifyDataSetChanged();

                            alertDialog.dismiss();
                        }
                    });
                    rlno.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //否
                            alertDialog.dismiss();
                        }
                    });

                return true;
                }
                return false;
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }

    }

    public List<WarnInfo> getDate() {

        List<WarnInfo> infos = null;
        try {
           infos= MyApplication.getDB().selector(WarnInfo.class).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        if(infos!=null&&infos.size()>0){
            list.addAll(infos);
        }

        WarnInfo info5 = new WarnInfo();
        info5.setWarnType("+添加提醒");

        /*list.add(info1);
        list.add(info2);
        list.add(info3);*/
        list.add(info5);
        return list;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            if(requestCode==REQUSET) {
                WarnInfo  info= new WarnInfo();
                info.setTime(data.getStringExtra("warnTime"));
                info.setWarnType(data.getStringExtra("warntype"));
                info.setRepeat(data.getStringExtra("repeat"));
                OemLog.log(TAG, "设置信息是: " + "提醒时间: " + info.getTime() + " 提醒类型: " + info.getWarnType() + " 重复类型: " + info.getRepeat());
                addNotifyToBracelete(info);
                list.remove(list.size()-1);
                list.add(info);
                WarnInfo info5 = new WarnInfo();
                info5.setWarnType("+添加提醒");
                list.add(info5);
                adpter.notifyDataSetChanged();
            } else if(requestCode == MODIFY) {
                WarnInfo  info= new WarnInfo();
                info.setTime(data.getStringExtra("warnTime"));
                info.setWarnType(data.getStringExtra("warntype"));
                info.setRepeat(data.getStringExtra("repeat"));
                addNotifyToBracelete(info);
                String position = data.getStringExtra("position");
                int i = Integer.parseInt(position);
                list.remove(i);
                list.add(i,info);
                adpter.notifyDataSetChanged();

            }




        }
    }

    private void addNotifyToBracelete(WarnInfo warnInfo) {

        byte reminderType = 0;
        int reminderHour;
        int reminderMin;
        String repeatMode = null;

        //设置提醒类型
        switch (warnInfo.getWarnType()) {
            case "运动":
                reminderType = 0x02;
                break;
            case "睡觉":
                reminderType = 0x04;
                break;
            case "起床":
                reminderType = 0x08;
                break;
            case "吃饭":
                reminderType = 0x00;
                break;
            case "吃药":
                reminderType = 0x01;
                break;
            default:
                OemLog.log(TAG, "reminder type does not match...");
                break;

        }

        //设置提醒时间
        String[] tmp = warnInfo.getWarnTime().split(" ");

        String[] tmp1 = tmp[1].split(":");

        reminderHour = Integer.parseInt(tmp1[0]);
        reminderMin = Integer.parseInt(tmp1[1]);


        //设置重复类型
        switch (warnInfo.getRepeat()) {
            case "周一":
                repeatMode = "10000000";
                break;
            case "周二":
                repeatMode = "01000000";
                break;
            case "周三":
                repeatMode = "00100000";
                break;
            case "周四":
                repeatMode = "00010000";
                break;
            case "周五":
                repeatMode = "00001000";
                break;
            case "周六":
                repeatMode = "00000100";
                break;
            case "周日":
                repeatMode = "00000010";
                break;
            case "每天":
                repeatMode = "11111110";
                break;
            case "永不":
                repeatMode = "00000000";
                break;
            default:
                OemLog.log(TAG, "reminder mode does not match...");
                break;
        }

        //向命令队列中增加命令
        BraceleteDevices.getInstance().addCommandToQueue(new AndReminderBraceleteCommand(reminderType, reminderHour, reminderMin, repeatMode));

    }
}
