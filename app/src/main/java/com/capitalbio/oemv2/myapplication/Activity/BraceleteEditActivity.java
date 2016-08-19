package com.capitalbio.oemv2.myapplication.Activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.Utils.Utility;
import com.capitalbio.oemv2.myapplication.dialog.TimeSelectDialog;

public class BraceleteEditActivity extends BaseActivity implements View.OnClickListener {

    LinearLayout contentLayout;
    TextView mesureTime;
    @Override
    protected void initChildLayout() {
        initChildView();
    }

    private void initChildView() {
        rl.setBackgroundResource(R.color.bg_content);
        rlNavigateBar.setBackgroundResource(R.drawable.bg_tang_zhi_san_xiang);
        setLeftTopIcon(R.drawable.ic_back);
        contentLayout = (LinearLayout) View.inflate(this, R.layout.activity_bracelete_edit, null);
        llBody.addView(contentLayout);
        setTvTopTitle(R.string.braceleteEdit);
        setTvTopRight(R.string.save);
        mesureTime = (TextView) findViewById(R.id.tv_time);
        mesureTime.setOnClickListener(this);
        tvTopRight.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_time:
                TimeSelectDialog mtimeselect = new TimeSelectDialog(
                        BraceleteEditActivity.this);
                mtimeselect.show();
                mtimeselect.setOnCancelListener(new TimeSelectDialog.onCancel(){
                    @Override
                    public void onCancel() {
                        // TODO Auto-generated method stub
                    }
                });
                mtimeselect.setOnOkListener(new TimeSelectDialog.onOK(){
                    @Override
                    public void onOk(String month, String day, String hour, String minute) {
                        //TODO Auto-generated method stub
                        mesureTime.setText(Utility.currYear()+"-"+month+"-"+day+" "+hour+":"+minute);
                    }
                });
                break;
            case R.id.rl_goalset:
                Utility.startActivity(context,BraceleteGoalSettingActivity.class);
                break;
            case R.id.tv_right:
                //编辑完成

                break;
        }

    }


}
