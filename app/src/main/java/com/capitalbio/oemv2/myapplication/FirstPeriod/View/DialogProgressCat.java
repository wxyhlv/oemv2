package com.capitalbio.oemv2.myapplication.FirstPeriod.View;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.capitalbio.oemv2.myapplication.R;

public class DialogProgressCat extends Dialog {

	public DialogProgressCat(Context context) {
		super(context, R.style.ShareDialog);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_progress_cat);
		
	}

}
