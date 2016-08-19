package com.capitalbio.oemv2.myapplication.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.Bean.LoginUser;
import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.Utils.Utility;
import com.capitalbio.oemv2.myapplication.View.GlideCircleTransform;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout rlback;

    //账号管理
    private LinearLayout llaccountmanagement;

    //关于我们
    private LinearLayout llaboutus;

    //
    private ImageView ivavatarcurrent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        find();
        init();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initAvatar();
    }

    private void find(){
        rlback = (RelativeLayout) this.findViewById(R.id.rl_setting_back);
        rlback.setOnClickListener(this);

        llaboutus = (LinearLayout) this.findViewById(R.id.ll_setting_aboutus);
        llaboutus.setOnClickListener(this);

        llaccountmanagement = (LinearLayout) this.findViewById(R.id.ll_setting_accountmanagement);
        llaccountmanagement.setOnClickListener(this);

        ivavatarcurrent = (ImageView) this.findViewById(R.id.iv_setting_avatarcurrent);

    }

    private void init(){
       initAvatar();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.rl_setting_back:
                this.finish();
                break;
            //账号管理
            case R.id.ll_setting_accountmanagement:
                Utility.startActivity(SettingActivity.this,AccountManagementActivity.class);
                break;
            //关于我们
            case R.id.ll_setting_aboutus:
                Utility.startActivity(SettingActivity.this,AboutUsActivity.class);
                break;
        }
    }

    //show avatar
    private void initAvatar(){
        LoginUser user = MyApplication.getInstance().getCurrentUser();
        Log.i(TAG, "IMAGELOADER_NO");
        if (user != null) {
            byte[] bytePic = user.getHeadPic();
            Log.i(TAG, bytePic == null ? "null" : "has");
            if(bytePic!=null && bytePic.length>0){
                  /*  Bitmap bitmap = ByteUtil.byteToBitmap(bytePic);
                    ivSlideUserPhoto.setImageBitmap(bitmap);*/
                Glide.with(context)
                        .load(bytePic)
                        .transform(new GlideCircleTransform(context)).into(ivavatarcurrent);
            }else{
                Glide.with(context)
                        .load(R.drawable.ic_scene)
                        .transform(new GlideCircleTransform(context)).into(ivavatarcurrent);
            }

        }

    }
}
