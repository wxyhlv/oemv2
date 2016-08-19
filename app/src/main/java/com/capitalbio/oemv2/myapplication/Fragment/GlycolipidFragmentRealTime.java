package com.capitalbio.oemv2.myapplication.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.capitalbio.oemv2.myapplication.R;

/**
 * Created by susu on 16-5-16.
 */
public class GlycolipidFragmentRealTime extends BaseFragment{
    private View view;
    @Override
    protected View loadViewLayout(LayoutInflater inflater, ViewGroup container) {
        view=inflater.inflate(R.layout.glycolipid_real_time_measure,container,false);
        return view;
    }

    @Override
    protected void findViewById(View view) {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void processLogic() {

    }
}
