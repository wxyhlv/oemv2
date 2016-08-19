package com.capitalbio.oemv2.myapplication.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.capitalbio.oemv2.myapplication.FirstPeriod.Utils.DialogTaskManager;
import com.capitalbio.oemv2.myapplication.R;

public class Common_dialog {

	private static Dialog nDialog = null;
	static DialogTaskManager manager = DialogTaskManager.getInstance();
	public static ImageView mumImage;
	public static TextView  tipTextView;

	/**
	 * 加载数据
	 * 
	 * @param context
	 * @param msg
	 */
	public static void startWaitingDialog(Context context, String msg) {

		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.loading_dialog1, null);

		mumImage = (ImageView) v.findViewById(R.id.img);
		tipTextView = (TextView)v.findViewById(R.id.tipTextView);
		tipTextView.setText(msg);
		Animation mumAnimation = AnimationUtils.loadAnimation(context,
				R.anim.load_animation);
		mumImage.startAnimation(mumAnimation);
		    nDialog = new Dialog(context, R.style.loading_dialog);
		nDialog.setContentView(v, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT));
		//if(!nDialog.isShowing()){
			try {
			    nDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
		//}
	
	

//		nDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
//
//			public boolean onKey(DialogInterface dialog, int keyCode,
//					KeyEvent event) {
//
//				if (keyCode == KeyEvent.KEYCODE_HOME
//						|| keyCode == KeyEvent.KEYCODE_BACK) {
//					if (nDialog != null) {
//						if (nDialog.isShowing()) {
//
//							nDialog.dismiss();
////							nDialog = null;
//							if (mumImage != null) {
//								mumImage.clearAnimation();
//							}
//						}
//					}
//
//					return true;
//				}
//
//				return false;
//			}
//		});
	}

	/**
	 * 显示加载数据的%
	 * 
	 * */
	public static void progressUpdate(){
		mHandler.sendEmptyMessage(0);
		
		
	}
	
	 private static Handler mHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				int what = msg.what;
				if (what == 0) {	//update
					TextView tv = (TextView) nDialog.findViewById(R.id.tipTextView);
					tv.setText(DateFormat.format("yyyy-MM-dd hh:mm:ss", System  
		                    .currentTimeMillis()).toString());
					if(nDialog.isShowing()){
						mHandler.sendEmptyMessageDelayed(0,1000);
					}
				}else {
					nDialog.cancel();
				}
			}
	    };
	    
	    
	public static void stop_WaitingDialog() {
		mumImage = null;
		tipTextView=null;
		if (nDialog != null) {
			try {
				nDialog.dismiss();
				nDialog=null;

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			nDialog = null;
		}

	}
	/**
	 * 普通 是和否的对话框
	 * 
	 * @param context
	 *            上下文
	 * @param cls
	 *            点击确认要跳转的activity
	 * @param message
	 *            对话框内容
	 */
	public static void Dialog(final Context context, final Class<?> cls,
			String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message).setCancelable(false)
				.setPositiveButton("是", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						Intent i = new Intent(context, cls);
						context.startActivity(i);
					}
				})
				.setNegativeButton("否", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		AlertDialog alert = builder.create();
	}
}
