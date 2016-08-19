package com.capitalbio.oemv2.myapplication.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.capitalbio.oemv2.myapplication.R;


/**
 * @author naiyu(http://snailws.com)
 * @version 1.0
 */
public class GoalSetProgressbar extends View {

	// 画实心圆的画笔
	private Paint mCirclePaint;
	// 画圆环的画笔
	private Paint mRingPaint;
	// 画字体的画笔
	private Paint mTextPaint,mTextPaint_bold;
	// 圆形颜色
	private int mCircleColor;
	// 圆环颜色
	private int mRingColor;
	// 半径
	private float mRadius;
	// 圆环半径
	private float mRingRadius;
	// 圆环宽度
	private float mStrokeWidth;
	// 圆心x坐标
	private int mXCenter;
	// 圆心y坐标
	private int mYCenter;
	// 字的长度
	private float mTxtWidth;
	// 字的高度
	private float mTxtHeight,mTxtHeight_bold;
	// 总进度
	private int mTotalProgress = 100;
	// 当前进度
	private int mProgress;
	private int space;
	private int cursteps=0,targetsteps=0;
    private float mTextProgressHeight;

	private Bitmap mRingbg;
	public GoalSetProgressbar(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 获取自定义的属性
		initAttrs(context, attrs);
		initVariable();
	}

	private void initAttrs(Context context, AttributeSet attrs) {
		TypedArray typeArray = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.TasksCompletedView, 0, 0);
		mRadius = typeArray.getDimension(R.styleable.TasksCompletedView_radius, 80);
		mStrokeWidth = typeArray.getDimension(R.styleable.TasksCompletedView_strokeWidth, 10);
		mCircleColor = typeArray.getColor(R.styleable.TasksCompletedView_circleColor, 0xFFFFFFFF);
		mRingColor = typeArray.getColor(R.styleable.TasksCompletedView_ringColor, 0xFFFFFFFF);
		mRingRadius = mRadius ;
	}

	private void initVariable() {
		mCirclePaint = new Paint();
		mCirclePaint.setAntiAlias(true);
		mCirclePaint.setColor(mCircleColor);
		mCirclePaint.setStyle(Paint.Style.STROKE);
		mCirclePaint.setStrokeWidth(mStrokeWidth);


		mRingPaint = new Paint();
		mRingPaint.setAntiAlias(true);
		mRingPaint.setColor(mRingColor);
		mRingPaint.setStyle(Paint.Style.STROKE);
		mRingPaint.setStrokeCap(Cap.ROUND);
		mRingPaint.setStrokeWidth(mStrokeWidth);

		space = getResources().getDimensionPixelSize(R.dimen.dp_5);
		mRingbg = BitmapFactory.decodeResource(getResources(),R.drawable.goal_ring);
	}

	@Override
	protected void onDraw(Canvas canvas) {

		mXCenter = getWidth() / 2;
		Log.d("getHeight", "" + getHeight());

		mYCenter = getHeight()/ 2;
		canvas.drawCircle(mXCenter, mYCenter, mRadius, mCirclePaint);
/*
		canvas.drawCircle(mXCenter, mYCenter, mRadius, mCirclePaint);
		mCirclePaint.setColor(Color.parseColor("#DFE9E8"));
		canvas.drawCircle(mXCenter, mYCenter, mRadius, mCirclePaint);
		mCirclePaint.setColor(Color.parseColor("#DAE4E3"));
		canvas.drawCircle(mXCenter, mYCenter, mRadius, mCirclePaint);
		mCirclePaint.setColor(Color.parseColor("#D9E5E3"));
		canvas.drawCircle(mXCenter, mYCenter, mRadius, mCirclePaint);*/
	/*	Matrix matrix = new Matrix();

		float scalex = 2 * mRadius/mRingbg.getWidth();
		matrix.setScale(scalex, scalex);
		matrix.postScale(mXCenter,mYCenter);
		canvas.drawBitmap(mRingbg,matrix,mCirclePaint);*/
		if (mProgress > 0 ) {
			RectF oval = new RectF();
			oval.left = (mXCenter - mRingRadius);
			oval.top = (mYCenter - mRingRadius);
			oval.right = mRingRadius * 2 + (mXCenter - mRingRadius);
			oval.bottom = mRingRadius * 2 + (mYCenter - mRingRadius);
			canvas.drawArc(oval, -90, ((float) mProgress / 100) * 360, false, mRingPaint); //

		}
	}
	
	public void setProgress(int progress) {
		while(mProgress<progress){
			mProgress++;
			postInvalidate();

		}

	}

}
