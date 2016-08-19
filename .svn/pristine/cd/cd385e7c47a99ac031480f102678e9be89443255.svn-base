package com.capitalbio.oemv2.myapplication.View.views;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.spec.IvParameterSpec;


import android.app.Notification.Action;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.style.LineHeightSpan.WithDensity;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class BalanceView extends ViewGroup   {

	//整个空间的宽高
	private float width,height;
	
	//绘制体重秤区域的宽高  宽高是二倍的关系
	private float width_balanc,height_balance;
	
	//白园直径相对于刻度盘直径的比例
	private float ratio_whitecircle = (float)219/578;
	
	//指针宽度相对于刻度盘的直径的比例
	private float ratio_pointer_width = (float)38/578;
	
	//指针高度相对于刻度盘的直径的比例
	private float ratio_pointer_height = (float)155/578;
	
	//底部灰色条的高度相对于刻度盘直径的比例
	private float ratio_bottomline_height = (float)6/578;
	
	private View disc;
	private View bottomline;
	private View white_circle;
	private View pointer;
	
	//刻度盘转动速度 每圈多少秒
	private int speed = 5000;
	
	//测量是否结束
	private boolean isOver = false;
	
	//测量结束要停在的刻度
	private int stopscale = 0;
	
	
	public BalanceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		System.out.println("========================================"+"onMeasure");
		width = getMeasuredWidth();
		System.out.println("========================================"+"width="+width);
		height = getMeasuredHeight();
		System.out.println("========================================"+"height="+height);
		if(width>height*2){
			//如果整个控件的宽度大于整个控件的高的2倍，那么以整个控件的高度作为绘制区域的高度
			height_balance = height;
			width_balanc = height_balance*2;
		}else{
			//不然，以整个控件的宽度作为绘制区域的宽度
			width_balanc = width;
			height_balance = width/2;
		}
		
		int childcount = getChildCount();
		for(int i=0;i<childcount;i++){
			switch (i) {
			case 0:
				disc = getChildAt(i);
				break;
			case 1:
				bottomline = getChildAt(i);
				break;
			case 2:
				white_circle = getChildAt(i);
				break;
			case 3:
				pointer = getChildAt(i);
				break;
			}
		}
		
		
	}
	
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b){
		// TODO Auto-generated method stub
		
		int[] disc_p = calcuScaleDisc();
		int[] bottom_p = calcuScaleBottom();
		int[] white_p = calcuScaleWhite();
		int[] pointer_p = calcuScalePointer();
		
		int childcount = getChildCount();
		for(int i=0;i<childcount;i++){
			switch (i) {
			case 0:
				disc.layout(disc_p[0], disc_p[1], disc_p[2], disc_p[3]);
				break;
			case 1:
				bottomline.layout(bottom_p[0],bottom_p[1],bottom_p[2],bottom_p[3]);
				break;
			case 2:
				white_circle.layout(white_p[0],white_p[1],white_p[2],white_p[3]);
				break;
			case 3:
				pointer.layout(pointer_p[0],pointer_p[1],pointer_p[2],pointer_p[3]);
				break;

			}
		}
		
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
	}
	
	//计算刻度盘的onlayout的左，上，右，下
	private int[] calcuScaleDisc(){
		int[] disc = new int[4];
		disc[0] = (int)(float)(width-width_balanc)/2;
		disc[1] = (int) (height-height_balance);
		disc[2] = (int) (width/2+width_balanc/2);
		disc[3] = (int) (height+width_balanc/2);
		return disc;
	}
	
	//计算底部灰色条的onlayout的左，上，右，下
	private int[] calcuScaleBottom(){
		int[] bottom = new int[4];
		
		bottom[0] = (int)(float)(width-width_balanc)/2;
		bottom[1] = (int) (height-width_balanc*ratio_bottomline_height);
		bottom[2] = (int) (width/2+width_balanc/2);
		bottom[3] = (int) height;
		
		return bottom;
	}
	
	//计算白圆的onlayout的左，上，右，下
	private int[] calcuScaleWhite(){
		int[] white = new int[4];
		
		//计算当前情况下，白圆的直径
		float diameter = width_balanc*ratio_whitecircle;
		
		white[0] = (int) ((width-diameter)/2);
		white[1] = (int) (height-diameter/2);
		white[2] = (int) (width/2+diameter/2);
		white[3] = (int) (height+diameter/2);
		
		return white;
	}
	
	//计算指针的onlayout的左，上，右，下
	private int[] calcuScalePointer(){
		int[] pointer = new int[4];
		
		//计算当前情况下指针的 宽和高
		float width_pointer = width_balanc*ratio_pointer_width;
		float height_pointer = width_balanc*ratio_pointer_height;
		
		pointer[0] = (int) ((width-width_pointer)/2);
		pointer[1] = (int) (height-height_pointer);
		pointer[2] = (int) (width/2+width_pointer/2);
		pointer[3] = (int) height;
		
		return pointer;
	}
	
	
	private float smallscale = 360/1500;
	private float middlescale = 360/150;
	private float bigscale = 360/15;
	//计算刻度对应的角度
	private float calcuDegree(int scale){
		int big_sum = scale/100;
		int mid_sum = scale%100/10;
		int small_sum = scale%100%10;
		return big_sum*bigscale+mid_sum*middlescale+small_sum*smallscale;
	}
	
	private long smallduration = speed/1500;
	private long middleduration = speed/150;
	private long bigduration = speed/15;
	//计算转到对应刻度需要的时间
	private long calcuDuration(int scale){
		int big_sum = scale/100;
		int mid_sum = scale%100/10;
		int small_sum = scale%100%10;
		return big_sum*bigduration+mid_sum*middleduration+small_sum*smallduration;
	}
	
	RotateAnimation animation;
	public void startMeasure(){
		 animation = new RotateAnimation(0, -360, RotateAnimation.RELATIVE_TO_SELF,0.5f , RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		animation.setDuration(speed);
		animation.setRepeatCount(30);
		animation.setRepeatMode(Animation.REVERSE);
		animation.setFillAfter(true);
		//animation.setFillBefore(false);
		//animation.setFillEnabled(true);
		animation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				System.out.println("============================onAnimationRepeat");
				if(isOver){
					System.out.println("============================测量结束 ");
					
					animation.cancel();
					
					
					RotateAnimation animation2 = new RotateAnimation(0, -calcuDegree(stopscale), Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
					animation2.setDuration(calcuDuration(stopscale));
					animation2.setFillAfter(true);
					disc.startAnimation(animation2);
				}
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				
			}
		});
		disc.startAnimation(animation);
		
		
	}
	
	public void completeMeasure(int scale){
		isOver = true;
		stopscale = scale;
	}
	
	
	
	
	


	

}
