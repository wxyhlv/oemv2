package com.capitalbio.oemv2.myapplication.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.capitalbio.oemv2.myapplication.Activity.PersonalDetailsAcitivity;
import com.capitalbio.oemv2.myapplication.R;

/**
 * Created by chengkun on 15-11-25.
 */
public class SlidingMenuFragment extends Fragment implements View.OnClickListener{

    private ScrollView llParentView;
    private ImageView ivSlideUserPhoto;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        llParentView = (ScrollView) inflater.inflate(R.layout.fg_slidingmenu, null);
        initView();
        return llParentView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initView() {
        ivSlideUserPhoto = (ImageView) llParentView.findViewById(R.id.iv_slide_user_photo);

        ivSlideUserPhoto.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_slide_user_photo:
                startUserInfoActivity();
                break;
        }
    }

    private void startUserInfoActivity() {
        Intent intent = new Intent(getActivity(), PersonalDetailsAcitivity.class);
        startActivity(intent);
    }
}

