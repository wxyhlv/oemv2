package com.capitalbio.oemv2.myapplication.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

import com.capitalbio.oemv2.myapplication.R;


public class DropView extends View{
	private int width;//控件宽度
	private int height;//控件高度
	
	private Paint paint;
	
	private int dropCount;//圆点数量
	
	private final static int eachDropWidth=10;//每个圆点的大小
	
	private final static int eachDropSpacing=25;//每个圆点的间距
	
	private int dropTotalWidth;//圆点总长度

	private int offsetWidth;//横向偏移量
	
	private int offsetHeight;//纵向偏移量
	
	private int currentCount;//当前选中的圆点
	
	private TypedArray ta;// XML配置参数获取
	
	private float centum;//移动的百分比
	
	private int moveType;//滑动类型,1向前，2向后，0为当前
	
	private boolean isMoveEnd=true;//滑动是否结束
	public DropView(Context context,int dropCount) {
		super(context);
		this.dropCount=dropCount;
		init();
	}
	
	public DropView(Context context, AttributeSet attrs) {
		super(context, attrs);
		ta = context.obtainStyledAttributes(attrs, R.styleable.DropView, 0,
				0);
		this.dropCount=ta.getInteger(R.styleable.DropView_dropCount, 0);
		ta.recycle();
		init();
	}
  
	public void inteCircle(int count){
		this.dropCount=count;
		init();
		postInvalidate();
	}
	
	public void init(){
		setWillNotDraw(false);
		paint=new Paint();
		paint.setStyle(Style.FILL);
		paint.setAntiAlias(true);
		dropTotalWidth=dropCount*eachDropWidth+(dropCount-1)*eachDropSpacing;
	}

	public void redraw(int currentCount,float centum,int moveType,boolean isMoveEnd){
		this.currentCount=currentCount;
		this.moveType=moveType;
		this.isMoveEnd=isMoveEnd;
		this.centum=centum;
		invalidate();
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		width=MeasureSpec.getSize(widthMeasureSpec);
		height=MeasureSpec.getSize(heightMeasureSpec);
		offsetWidth=(width-dropTotalWidth)/2;
		offsetHeight=height/2;
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}



	@Override
	protected void onDraw(Canvas canvas) {
		for(int i=0;i<dropCount;i++){
			int x=offsetWidth+(i*eachDropSpacing+i*eachDropWidth);
			boolean isCurrent=false;
			int color=0;
			if(i==currentCount){
				color=Color.rgb(212,212,212);  
				isCurrent=true;
			}else{
				isCurrent=false;
				color=Color.WHITE;  
			}
			
			if(!isMoveEnd){
				if(isCurrent){
					paint.setColor(Color.rgb(212,212,212));  
					paint.setStyle(Style.FILL);
					float x1=0;
					if(moveType==1){
						x1=x-(eachDropSpacing*centum);
					}else if(moveType==2){
						x1=x+(eachDropSpacing*centum);
					}
					canvas.drawCircle(x1, offsetHeight, eachDropWidth, paint);
					
					paint.setColor(Color.rgb(212,212,212));  
					paint.setStyle(Style.STROKE);
					canvas.drawCircle(x, offsetHeight, eachDropWidth, paint);
					
				}else{
					paint.setStyle(Style.FILL);
					paint.setColor(color);
					canvas.drawCircle(x, offsetHeight, eachDropWidth, paint);
				}
			}else{
				paint.setColor(color);
				paint.setStyle(Style.FILL);
				canvas.drawCircle(x, offsetHeight, eachDropWidth, paint);
			}
			
		}
		super.onDraw(canvas);
	}

	
}
