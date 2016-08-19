package com.capitalbio.oemv2.myapplication.Activity;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ToggleButton;

import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.Utils.PreferencesUtils;
import com.capitalbio.oemv2.myapplication.Utils.Utility;

public class BraceleteWarnSettingActivity extends BaseActivity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener {

    LinearLayout contentLayout;
    private ToggleButton togglebtn_msgwarn,togglebtn_phonewarn,togglebtn_lostwarn;
    @Override
    protected void initChildLayout() {
        initChildView();
    }

    private void initChildView() {
        rl.setBackgroundResource(R.color.bg_content);
        rlNavigateBar.setBackgroundResource(R.drawable.bg_tang_zhi_san_xiang);
        setLeftTopIcon(R.drawable.ic_back);
        contentLayout = (LinearLayout) View.inflate(this, R.layout.activity_braceletewarn_setting, null);
        llBody.addView(contentLayout);
        setTvTopTitle(R.string.warnSetting);
        findViewById(R.id.rl_customsetting).setOnClickListener(this);
        togglebtn_msgwarn = (ToggleButton) findViewById(R.id.togglebtn_msgwarn);
        togglebtn_phonewarn = (ToggleButton) findViewById(R.id.togglebtn_phonewarn);
        togglebtn_lostwarn  = (ToggleButton) findViewById(R.id.togglebtn_lostwarn);
        togglebtn_msgwarn.setOnCheckedChangeListener(this);
        togglebtn_phonewarn.setOnCheckedChangeListener(this);
        togglebtn_lostwarn.setOnCheckedChangeListener(this);
        if( PreferencesUtils.getBoolean(context, "msgWarn")){
            togglebtn_msgwarn.setChecked(true);
        }else{
            togglebtn_msgwarn.setChecked(false);
        }
        if( PreferencesUtils.getBoolean(context, "phoneWarn")){
            togglebtn_phonewarn.setChecked(true);
        }else{
            togglebtn_phonewarn.setChecked(false);
        }
        if( PreferencesUtils.getBoolean(context, "lostWarn")){
           togglebtn_lostwarn.setChecked(true);
        }else{
            togglebtn_lostwarn.setChecked(false);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_customsetting:
             Utility.startActivity(getContext(),BraceleteCustomWarnSettingActivity.class);
                break;
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case   R.id.togglebtn_msgwarn:
                //TODO 短信提醒
                // 短信提醒
                if(isChecked){
                    PreferencesUtils.putBoolean(context, "msgWarn", true);
                }else{
                    PreferencesUtils.putBoolean(context,"msgWarn",false);
                }
               break;
            case   R.id.togglebtn_phonewarn:
                // 手机电话提醒
                if(isChecked){
                    PreferencesUtils.putBoolean(context, "phoneWarn", true);
                }else{
                    PreferencesUtils.putBoolean(context,"phoneWarn",false);
                }
                break;
            case   R.id.togglebtn_lostwarn:
             //方丢
                if(isChecked){
                    PreferencesUtils.putBoolean(context, "lostWarn", true);
                }else{
                    PreferencesUtils.putBoolean(context,"lostWarn",false);
                }
                break;
        }
    }
}
