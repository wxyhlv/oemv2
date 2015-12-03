package com.capitalbio.oemv2.myapplication.Activity;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.capitalbio.oemv2.myapplication.R;

public class PersonalDetailsAcitivity extends BaseActivity {

    FrameLayout flPersonDetail;

    private ImageView ivMale;
    private ImageView ivFemale;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_male:
                    ivMale.setBackgroundResource(R.drawable.ic_orange_circle);
                    break;
                case R.id.iv_female:
                    ivFemale.setBackgroundResource(R.drawable.ic_orange_circle);
                    break;
            }
        }
    };


    @Override
    protected void initChildLayout() {
        super.initChildLayout();
        initChildView();
    }


    private void initChildView() {

        rl.setBackgroundResource(R.drawable.bg_login);
        setLeftTopIcon(R.drawable.ic_back);
        flPersonDetail = (FrameLayout) View.inflate(this, R.layout.activity_personal_details_acitivity, null);
        llBody.addView(flPersonDetail);

        ivMale = (ImageView) flPersonDetail.findViewById(R.id.iv_male);
        ivFemale = (ImageView) flPersonDetail.findViewById(R.id.iv_female);

        ivMale.setOnClickListener(onClickListener);
        ivFemale.setOnClickListener(onClickListener);


    }

}









