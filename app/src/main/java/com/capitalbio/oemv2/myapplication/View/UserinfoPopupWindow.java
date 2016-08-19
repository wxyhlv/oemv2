package com.capitalbio.oemv2.myapplication.View;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.capitalbio.oemv2.myapplication.R;


public class UserinfoPopupWindow extends PopupWindow {

	private View mMenuView;
	private LinearLayout iv_userinfo_attachment;
	private View userinfo_line_atachment;// 横线

	public UserinfoPopupWindow(Activity context, OnClickListener itemsOnClick,
			int type) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.popwindow_userinfo_uicon, null);
		mMenuView.findViewById(R.id.iv_userinfo_takepic).setOnClickListener(
				itemsOnClick);
		mMenuView.findViewById(R.id.iv_userinfo_choosepic).setOnClickListener(
				itemsOnClick);
		mMenuView.findViewById(R.id.iv_userinfo_cancle).setOnClickListener(
				itemsOnClick);
		iv_userinfo_attachment = (LinearLayout) mMenuView
				.findViewById(R.id.iv_userinfo_attachment);
		userinfo_line_atachment = mMenuView
				.findViewById(R.id.userinfo_line_atachment);
		if (type == 0) {// 从附件页面
			iv_userinfo_attachment.setVisibility(View.VISIBLE);
			userinfo_line_atachment.setVisibility(View.VISIBLE);
			iv_userinfo_attachment.setOnClickListener(itemsOnClick);
		} else if (type == 1) {// 从个人信息设置
			iv_userinfo_attachment.setVisibility(View.GONE);
			userinfo_line_atachment.setVisibility(View.GONE);
		}
		// 设置SelectPicPopupWindow的View
		this.setContentView(mMenuView);
		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.FILL_PARENT);
		// 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.WRAP_CONTENT);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		// 设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.AnimBottom);
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0x80000000);
		// 设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
		// mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
		mMenuView.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {

				int height = mMenuView.findViewById(R.id.ll_pop_uxerinfo)
						.getTop();
				int y = (int) event.getY();
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (y < height) {
						dismiss();
					}
				}
				return true;
			}
		});

	}
}
