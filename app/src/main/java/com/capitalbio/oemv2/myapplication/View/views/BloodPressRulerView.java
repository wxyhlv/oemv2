package com.capitalbio.oemv2.myapplication.View.views;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.capitalbio.oemv2.myapplication.R;

public class BloodPressRulerView extends View {

	// 控件的整体宽高
	private float width, height;

	//
	private Bitmap ruler;

	// 原始图的宽高比
	private float ratio_wh;
	private float width_bitmap;
	private float height_bitmap;

	// 绘制区域的宽高
	private float width_valid, height_valid;

	// 当前值
	private int currValue = 150;
	
	//只执行一次bitmap的缩放
	private boolean isfirst = true;

	public BloodPressRulerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		BitmapDrawable bitmapDrawable = (BitmapDrawable) context.getResources()
				.getDrawable(R.drawable.bloodpressruler);
		ruler = bitmapDrawable.getBitmap();

		width_bitmap = ruler.getWidth();
		height_bitmap = ruler.getHeight();
		ratio_wh = (float) width_bitmap / height_bitmap;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		width = getMeasuredWidth();
		height = getMeasuredHeight();

		// 计算绘制区域的宽高
		width_valid = height * ratio_wh;
		height_valid = height;
		
		if(isfirst){
			isfirst = false;
			zoomBitmap();
		}

	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);

		// 画灰色背景
		drawBGgray(canvas);

		// 画红色进度
		drawBGred(canvas);

		// 画刻度
		drawScale(canvas);
	}

	private void drawBGgray(Canvas canvas) {
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.parseColor("#E8EAE9"));
		int l = (int) ((width - width_valid) / 2);
		int t = 0;
		int r = (int) (width / 2 + width_valid / 2);
		int b = (int) height;
		Rect rect = new Rect(l, t, r, b);
		canvas.drawRect(rect, paint);
	}

	private void drawBGred(Canvas canvas) {
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.parseColor("#FF8691"));
		int l = (int) ((width - width_valid) / 2);
		int t = (int) (height_valid - valueTransferHeight(currValue));
		int r = (int) (width / 2 + width_valid / 2);
		int b = (int) height * 16 / 17;
		Rect rect = new Rect(l, t, r, b);
		canvas.drawRect(rect, paint);
	}

	// 根据当前值转换成红色背景的高度
	private float valueTransferHeight(int value) {
		// 计算大刻度的高度
		float h_bigscale = height_valid / 17;
		// 计算小刻度的高度
		float h_smallscale = height_valid / 85;

		int sum_bigscale = value / 20;
		int sum_smallscale = value % 20 / 4;

		return h_bigscale + sum_bigscale * h_bigscale + sum_smallscale
				* h_smallscale;

	}

	private void drawScale(Canvas canvas) {
		
		
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		float l = (width - width_valid) / 2;
		float t = 0;

		canvas.drawBitmap(ruler, l, t, paint);
	}
	
	

	private void zoomBitmap() {
		// 根据绘制区域的高对bitmap进行缩放
		float s = height_valid / height_bitmap;
		
		Matrix matrix = new Matrix();
		matrix.postScale(s, s);
		ruler = Bitmap.createBitmap(ruler, 0, 0, ruler.getWidth(),
				ruler.getHeight(), matrix, false);
	}
	
	public void setValue(int value){
		currValue = value;
		invalidate();
	}

}
