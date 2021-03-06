package com.capitalbio.oemv2.myapplication.View;

import android.R.integer;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.RectF;
import android.graphics.Paint.FontMetrics;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.capitalbio.oemv2.myapplication.R;


/**
 * @author naiyu(http://snailws.com)
 * @version 1.0
 */
public class TasksCompletedView extends View {

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

	public TasksCompletedView(Context context, AttributeSet attrs) {
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
		
		mTextPaint = new Paint();
		mTextPaint.setAntiAlias(true);
		mTextPaint.setStyle(Paint.Style.FILL);
		mTextPaint.setColor(Color.WHITE);
		mTextPaint.setTextSize(getResources().getDimensionPixelSize(R.dimen.sp_20));
		
		FontMetrics fm = mTextPaint.getFontMetrics();
		mTxtHeight = (int) Math.ceil(fm.descent - fm.ascent);

		mTextPaint_bold = new Paint();
		mTextPaint_bold.setAntiAlias(true);
		mTextPaint_bold.setStyle(Paint.Style.FILL);
		mTextPaint_bold.setColor(Color.WHITE);
		mTextPaint_bold.setTextSize(getResources().getDimensionPixelSize(R.dimen.sp_26));
		Typeface font = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD);
		mTextPaint_bold.setTypeface(font);
		FontMetrics fm1 = mTextPaint_bold.getFontMetrics();
		mTxtHeight_bold = (int) Math.ceil(fm1.descent - fm1.ascent);

		space = getResources().getDimensionPixelSize(R.dimen.dp_7);
	}

	@Override
	protected void onDraw(Canvas canvas) {

		mXCenter = getWidth() / 2;
		Log.d("getHeight","" +getHeight());
		mYCenter = (getHeight()-space)/ 2;
		
		canvas.drawCircle(mXCenter, mYCenter, mRadius, mCirclePaint);
		
		if (mProgress > 0 ) {
			RectF oval = new RectF();
			oval.left = (mXCenter - mRingRadius);
			oval.top = (mYCenter - mRingRadius);
			oval.right = mRingRadius * 2 + (mXCenter - mRingRadius);
			oval.bottom = mRingRadius * 2 + (mYCenter - mRingRadius);
			canvas.drawArc(oval, -90, ((float)mProgress / 100) * 360, false, mRingPaint); //
            String txt = mProgress + "%";
            mTxtWidth = mTextPaint.measureText(txt, 0, txt.length());
			double x =0,y = 0;
			if(mProgress<=25){
				x = mXCenter+(mRadius+mTxtWidth/3) * Math.sin(((float) mProgress / 100) * 2 * Math.PI);

				y = mYCenter -mRadius * Math.cos(((float) mProgress / 100) * 2 * Math.PI);

			}else if(mProgress>25&&mProgress<=50){
				 x = mXCenter+(mRadius+mTxtWidth/3) * Math.sin(((float)mProgress /100) *2* Math.PI);

				 y = mYCenter -(mRadius+mTxtHeight) * Math.cos(((float) mProgress / 100) * 2 * Math.PI);

			}else if(mProgress>50&&mProgress<=75){
				 x = mXCenter+(mRadius+mTxtWidth) * Math.sin(((float)mProgress /100) *2* Math.PI);

				 y = mYCenter -(mRadius+mTxtHeight) * Math.cos(((float) mProgress / 100) * 2 * Math.PI);

			}else if(mProgress>75&&mProgress<=100){
				 x = mXCenter+(mRadius+mTxtWidth) * Math.sin(((float)mProgress /100) *2* Math.PI);

				 y = mYCenter -(mRadius+mTxtHeight/2) * Math.cos(((float) mProgress / 100) * 2 * Math.PI);
			}else if(mProgress>100){
				mProgress = 100;
				x = mXCenter+(mRadius+mTxtWidth/3) * Math.sin(((float) mProgress / 100) * 2 * Math.PI);

				y = mYCenter -mRadius * Math.cos(((float) mProgress / 100) * 2 * Math.PI);

			}
			/*double x = mXCenter+(mRadius+mTxtWidth/2) * Math.sin(((float)mProgress /100) * 360 * Math.PI /180);

			double y = mYCenter +(mRadius+mTxtHeight/2)*Math.cos(((float)mProgress /100) * 360* Math.PI /180);
        */
			canvas.drawText(mProgress+"%", (float) x, (float) y , mTextPaint); // 画出进度百分比
		}
	}
	
	public void setProgress(int progress) {
		if(progress>=100){
			progress = 100;
		}

		while(mProgress<progress){
			mProgress++;
			postInvalidate();

		}
	/*	mProgress = progress;
//		invalidate();
		postInvalidate();*/
	}
	public void setProgressInfo(int cursteps,int target,float progress) {
		mProgress =0;
		while(mProgress<progress){
			this.cursteps = cursteps;
			this.targetsteps =target;
			postInvalidate();
			mProgress++;
		}

	}
}
