package com.capitalbio.oemv2.myapplication.Activity;

import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.capitalbio.oemv2.myapplication.R;

public class UserAgreementActivity extends BaseActivity {
	private WebView wb_view;
    private LinearLayout llUserAgreement;

	protected void initChildLayout(){

		setNavigateBarBackGround(R.color.blue_light);
		setTvTopTitle(R.string.useragreement);
		setLeftTopIcon(R.drawable.ic_back);
		llUserAgreement = (LinearLayout) View.inflate(this, R.layout.ac_useragreement, null);
		llBody.addView(llUserAgreement);
		wb_view  = (WebView) findViewById(R.id.wb_view);
		wb_view.loadUrl("file:///android_asset/yhxy.html");

	}
}
