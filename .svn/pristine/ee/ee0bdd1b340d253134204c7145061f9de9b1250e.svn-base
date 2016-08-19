package com.capitalbio.oemv2.myapplication.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.capitalbio.oemv2.myapplication.R;

/**
 * Created by wxy on 16-7-14.
 */
public class BodyHelpFragment extends Fragment {


    private WebView webview;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fg_userhelp, container, false);
        webview = (WebView)view.findViewById(R.id.wv_userhelp);
        webview.loadUrl("file:///android_asset/zhifangchen.html");
        return view;
    }

}
