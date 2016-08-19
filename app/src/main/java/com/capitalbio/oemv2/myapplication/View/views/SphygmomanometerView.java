package com.capitalbio.oemv2.myapplication.View.views;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.capitalbio.oemv2.myapplication.R;

public class SphygmomanometerView extends ViewGroup {

	// 整个控件的宽，高
	private float width;
	private float height;

	// 绘制区域的匡高
	private float width_valid;
	private float height_valid;

	// 各部分的现实比例
	private float ratio_pointer = (float) 310 / 604;
	private float ratio_pointer_arrow = (float) 29 / 604;
	private float ratio_button = (float) 168 / 604;

	// 控件
	private ImageView ivdisc;
	private ImageView ivpointer;
	private ImageView ivbutton;
	
	//动画速度 一圈所用毫秒数
	private long speed = 5000;
	
	//是否测量结束
	private boolean isComplete = false;
	
	//是否准备好
	private boolean isReady = false;
	
	//测量结束停在的角度
	private float angelComplete = 0;
	
	//测量结束收尾动画
	RotateAnimation animation2;

	public SphygmomanometerView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		width = getMeasuredWidth();
		height = getMeasuredHeight();

		width_valid = height_valid = width > height ? height : width;

		int childcount = getChildCount();
		for (int i = 0; i < childcount; i++) {
			switch (i) {
			case 0:
				ivdisc = (ImageView) getChildAt(i);
				break;
			case 1:
				ivpointer = (ImageView) getChildAt(i);
				break;
			case 2:
				ivbutton = (ImageView) getChildAt(i);
				break;

			default:
				break;
			}
		}

	}

	// 计算刻度盘的摆放位置
	private int[] calcuP_disc() {
		int[] p_disc = new int[4];
		p_disc[0] = (int) ((width - width_valid) / 2);
		p_disc[1] = (int) ((height - height_valid) / 2);
		p_disc[2] = (int) (width / 2 + width_valid / 2);
		p_disc[3] = (int) (height / 2 + height_valid / 2);
		return p_disc;
	}

	// 计算指针的摆放位置
	private int[] calcuP_pointer() {
		// 先 计算当前情况下 指针的直径
		float diameter_pointer = width_valid * ratio_pointer;
		// 计算当前情况下 指针的箭头部分 的高度
		float height_pointer_arrow = width_valid * ratio_pointer_arrow;

		int[] p_pointer = new int[4];
		p_pointer[0] = (int) ((width - diameter_pointer) / 2);
		p_pointer[1] = (int) (height / 2 - diameter_pointer / 2 - height_pointer_arrow);
		p_pointer[2] = (int) (width / 2 + diameter_pointer / 2);
		p_pointer[3] = (int) (height / 2 + diameter_pointer / 2);

		return p_pointer;
	}

	// 计算按钮的摆放位置
	private int[] calcuP_button(){
		//计算当前情况下 按钮圆 的直径
		float diameter_button = width_valid*ratio_button;
		int[] p_button = new int[4];
		p_button[0] = (int) ((width-diameter_button)/2);
		p_button[1] = (int) (height/2-diameter_button/2);
		p_button[2] = (int) (width/2+diameter_button/2);
		p_button[3] = (int) (height/2+diameter_button/2);
		return p_button;
	}

	@Override
	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
		// TODO Auto-generated method stub
		//刻度盘
		int[] p_disc = calcuP_disc();
		ivdisc.layout(p_disc[0], p_disc[1], p_disc[2], p_disc[3]);
		//指针
		int[] p_pointer = calcuP_pointer();
		ivpointer.layout(p_pointer[0], p_pointer[1], p_pointer[2], p_pointer[3]);
		//按钮圆
		int[] p_button = calcuP_button();
		ivbutton.layout(p_button[0], p_button[1], p_button[2], p_button[3]);
		
		ivbutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isReady){
					ivbutton.setImageResource(R.drawable.measuring);
					RotateAnimation animation = new RotateAnimation(0, -360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
					animation.setDuration(speed);
					animation.setRepeatCount(10);
					animation.setRepeatMode(Animation.REVERSE);
					animation.setFillAfter(true);
					animation.setAnimationListener(new AnimationListener() {
						
						@Override
						public void onAnimationStart(Animation animation) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onAnimationRepeat(Animation animation) {
							// TODO Auto-generated method stub
							if(isComplete){
								animation.cancel();
								animation2 = new RotateAnimation(0, angelComplete, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
								animation2.setDuration(angleTotime(angelComplete));
								animation2.setFillAfter(true);
								animation2.setAnimationListener(new AnimationListener() {
									
									@Override
									public void onAnimationStart(Animation animation) {
										// TODO Auto-generated method stub
										
									}
									
									@Override
									public void onAnimationRepeat(Animation animation) {
										// TODO Auto-generated method stub
										
									}
									
									@Override
									public void onAnimationEnd(Animation animation) {
										// TODO Auto-generated method stub
										ivbutton.setImageResource(R.drawable.complete);
										isReady = false;
									}
								});
								ivdisc.startAnimation(animation2);
							}
						}
						
						@Override
						public void onAnimationEnd(Animation animation) {
							// TODO Auto-generated method stub
							ivbutton.setImageResource(R.drawable.complete);
							isReady = false;
						}
					});
					ivdisc.startAnimation(animation);
				}
				
			}
		});
	}
	
	//准备
	public void readyMeasure(){
		isReady = true;
		isComplete = false;
		//初始化转盘，让转盘回到正常位置
		if(animation2!=null){
			animation2.setFillAfter(false);
			animation2.setDuration(0);
			animation2.setAnimationListener(null);
			animation2.reset();
			ivdisc.startAnimation(animation2);
		}
		ivbutton.setImageResource(R.drawable.start_enable);

	}


	
	//测量结束，停在指定值
	public void stopMeasure(String str){
		angelComplete = transfer(str);
		isComplete = true;
	}
	
	private float transfer(String str){
		if(str.equals("正常")){
			return 0;
		}
		if(str.equals("正常高值")){
			return -45;
		}
		if(str.equals("1级高血压")){
			return -90;
		}
		if(str.equals("2级高血压")){
			return -135;
		}
		if(str.equals("3级高血压")){
			return -180;
		}
		if(str.equals("异常")){
			return -225;
		}
		if(str.equals("低压")){
			return -270;
		}
		if(str.equals("理想")){
			return -315;
		}
		return -225;
	}
	
	private long angleTotime(float angle){
		if(angle==0){
			return 0;
		}
		if(angle==-45){
			return speed/8;
		}
		if(angle==-90){
			return speed/4;
		}
		if(angle==-135){
			return speed/8*3;
		}
		if(angle==-180){
			return speed/2;
		}
		if(angle==-225){
			return speed/8*5;
		}
		if(angle==-270){
			return speed/4*3;
		}
		if(angle==-315){
			return speed/8*7;
		}
		return speed;
	}

}
