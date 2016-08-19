package com.capitalbio.oemv2.myapplication.FirstPeriod.View;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.capitalbio.oemv2.myapplication.FirstPeriod.Utils.HTG;
import com.capitalbio.oemv2.myapplication.FirstPeriod.Utils.ToastMaster;
import com.capitalbio.oemv2.myapplication.R;

public class BubbleSelectValueView extends View {
	
	private int width;
	private int height;
	
	//
	private int start =0;
	private float paddingleft =-50;
	
	//两点间距
	private float padding = 120;
	
	private int amount = 9;
	

	//1,温度.2,湿度
	private int flag = 1;
	//温度
	private float tboundary1 = -40;
	private float tboundary2 = 12;
	private float tboundary3 = 17;
	private float tboundary4 = 25;
	private float tboundary5 = 33;
	private float tboundary6 = 125;
	
	//湿度
	private float hboundary1 = 0;
	private float hboundary2 = 15;
	private float hboundary3 = 30;
	private float hboundary4 = 80;
	private float hboundary5 = 90;
	private float hboundary6 = 100;
	
	//初始化复位
	private boolean isfirst = true;

	public BubbleSelectValueView(Context context, AttributeSet attrs) {
		this(context,attrs,0);
		// TODO Auto-generated constructor stub
	}
	public BubbleSelectValueView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		
		//
		paddingleft = -HTG.dip2px(context, 20);
		padding = HTG.dip2px(context, 45);
		//
		TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.BubbleSelectValueView, defStyleAttr, 0);
		
		int size = typedArray.getIndexCount();
		for(int i=0;i<size;i++){
			int attr = typedArray.getIndex(i);
			switch (attr) {
			case R.styleable.BubbleSelectValueView_flag:
				flag = typedArray.getInt(attr, 1);
				break;
			case R.styleable.BubbleSelectValueView_start:
				start = typedArray.getInt(attr, 0);
				break;

			default:
				break;
			}
		}
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		width = getMeasuredWidth();
		height = getMeasuredHeight();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		drawBubble(canvas);
		
		drawBean(canvas);
		
		drawMiddle(canvas);
		
		drawPoint(canvas);
		
		if(isfirst){
			homIng();
			invalidate();
			isfirst = false;
		}
		
	}
	
	private float down;
	private float curr;
	private float up;
	//是否滑到最小值
	private boolean ismin = false;
	//是否滑到最大值
	private boolean ismax = false;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			down = event.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			curr = event.getX();
			float dis = Math.abs(down-curr);
			if(down>curr){
				//往左滑
				switch (flag) {
				case 1:
					//温度
					if(start>=900){
						ismax = true;
						
					}else{
						ismin = false;
					}
					break;
				case 2:
					//湿度
					if(start>=900){
						ismax = true;
					}else{
						ismin= false;
					}
					break;
				}
				
				if(ismax){
					
				}else{
					calcuInit(dis, 1);
					invalidate();
				}
				
			}else if(down<curr){
				//往右滑
				switch (flag) {
				case 1:
					//温度
					if(start<=-0.5){
						ismin = true;
					}else{
						ismin = false;
					}
					break;
				case 2:
					//湿度
					if(start<=-0.5){
						ismin = true;
					}else{
						ismin = false;
					}
					break;

				}
				
				if(ismin){
					
				}else{
					calcuInit(dis, 2);
					invalidate();
				}
				
			}
			down = curr;
			break;
		case MotionEvent.ACTION_UP:
			homIng();
			invalidate();
			if(ismax){
				ToastMaster.showToast(getContext(), "已到最大值");
			}
			if(ismin){
				ToastMaster.showToast(getContext(), "已到最小值");
			}
			break;
		case MotionEvent.ACTION_CANCEL:
			
			break;

		default:
			break;
		}
		return true;
	}
	
	//计算bean
	private List<Bean> calcuBean(){
		List<Bean> beans = new ArrayList<Bean>();
		  for(int i=0;i<amount;i++){
			  Bean bean = new Bean();
			  bean.setNumb(start+i);
			  bean.setPosi_x(paddingleft+i*padding);
			  int level = calcuLevel(paddingleft+i*padding);
			  bean.setLevel(level);
			  bean.setTextsize(HTG.dip2px(getContext(), levelTotextsize(level)));
			  bean.setPointcolor(levelToPointcolor(level));
			  bean.setTextcolor(levelToTextcolor(level));
			  bean.setText((float)(start+i)/10);
			  beans.add(bean);
		  }
		  return beans;
	}
	//test
	/*private void dealwith(List<Bean> beans){
		
		float first = beans.get(0).getText();
		float last = beans.get(amount-1).getText();
		
		switch (flag) {
		case 1:
			//温度
			
			break;
		case 2:
			//湿度
			
			break;
		}
	}*/
	
	//根据posi_x计算level；
	private int calcuLevel(float x){
		float level1 = 0;
		float level2 = (float)width/7;
		float level3 = (float)width/7*2;
		float level4 = (float)width/7*3;
		float level5 = (float)width/7*4;
		float level6 = (float)width/7*5;
		float level7 = (float)width/7*6;
		float level8 = width;
		
		if(x>=level4&&x<=level5){
			return 1;
		}else if(x>=level3&&x<level4){
			return 2;
		}else if(x>=level2&&x<level3){
			return 3;
		}else if(x>=level1&&x<level2){
			return 4;
		}else if(x>level5&&x<=level6){
			return 2;
		}else if(x>level6&&x<=level7){
			return 3;
		}else if(x>level7&&x<=level8){
			return 4;
		}
		
		return 0;
	}
	
	//level 转textsize
	private int levelTotextsize(int level){
		int textsize = 0;
		switch (level) {
		case 1:
			textsize = 18;
			break;
		case 2:
			textsize = 16;
			break;
		case 3:
			textsize = 14;
			break;
		case 4:
			textsize = 12;
			break;

		default:
			break;
		}
		return textsize;
	}
	
	//level 装point color；
	private int levelToPointcolor(int level){
		int color = 0;
		switch (level) {
		case 1:
			color = getResources().getColor(R.color.gray_1);
			break;
		case 2:
			color = getResources().getColor(R.color.gray_2);
			break;
		case 3:
			color = getResources().getColor(R.color.gray_3);
			break;
		case 4:
			color = getResources().getColor(R.color.gray_4);
			break;

		default:
			break;
		}
		return color;
	}
	
	//level 转 textcolor
	private int levelToTextcolor(int level){
		int color = 0;
		switch (level) {
		case 1:
			color =  getResources().getColor(R.color.black_1);
			break;
		case 2:
			color =  getResources().getColor(R.color.black_2);
			break;
		case 3:
			color =  getResources().getColor(R.color.black_3);
			break;
		case 4:
			color =  getResources().getColor(R.color.black_4);
			break;

		default:
			break;
		}
		return color;
	}
	
	//提取中间值
	private Bean extractMiddle(List<Bean> beans){
		float level4 = (float)width/7*3;
		float level5 = (float)width/7*4;
		
		int size = beans.size();
		for(int i=0;i<size;i++){
			Bean bean = beans.get(i);
			float posi_x = bean.getPosi_x();
			if(posi_x>=level4&&posi_x<=level5){
				return bean;
			}
		}
		return null;
	}
	
	//画中间值
	private void drawMiddle(Canvas canvas){
		Bean bean = extractMiddle(calcuBean());
		
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setTextSize(HTG.dip2px(getContext(), 20));
		float text = bean.getText();
		float textsize = paint.measureText(text+"");
		
		int color = transC(text);
		paint.setColor(color);
		
		canvas.drawText(bean.getText()+"", (float)width/2-textsize/2, (float)height/3, paint);
	}
	
	//画圆点
	private void drawPoint(Canvas canvas){
		List<Bean> beans = calcuBean();
		int size = beans.size();
		
		Paint paint = new Paint();
		
		for(int i=0;i<size;i++){
			Bean bean = beans.get(i);
			int textsize = bean.getTextsize();
			paint.setTextSize(textsize);
			if(isMiddle(bean)){
				float f = bean.getText();
				paint.setColor(transC(f));
			}else{
				paint.setColor(bean.getPointcolor());
			}
			canvas.drawCircle(bean.getPosi_x(), (float)height/8*5, (float)textsize/4, paint);
		}
	}
	
	//判断一个bean，是否是中心bean
	private boolean isMiddle(Bean bean){
		float level4 = (float)width/140*65;
		float level5 = (float)width/140*75;
		
		float posi_x = bean.getPosi_x();
		if(posi_x>=level4&&posi_x<=level5){
			return true;
		}
		return false;
	}
	
	//根据时值，判断当前色值
	private int transC(float f){
		int color = 0;
		switch (flag) {
		case 1:
			//温度
			if(f>=tboundary1&&f<tboundary2){
				color = this.getResources().getColor(R.color.red_bubble);
			}else if(f>=tboundary2&&f<tboundary3){
				color = this.getResources().getColor(R.color.yellow_bubble);
			}else if(f>=tboundary3&&f<tboundary4){
				color = this.getResources().getColor(R.color.green_bubble);
			}else if(f>=tboundary4&&f<tboundary5){
				color = this.getResources().getColor(R.color.yellow_bubble);
			}else if(f>=tboundary5&&f<tboundary6){
				color = this.getResources().getColor(R.color.red_bubble);
			}
			break;
		case 2:
			//湿度
			if(f>=hboundary1&&f<hboundary2){
				color = this.getResources().getColor(R.color.red_bubble);
			}else if(f>=hboundary2&&f<hboundary3){
				color = this.getResources().getColor(R.color.yellow_bubble);
			}else if(f>=hboundary3&&f<hboundary4){
				color = this.getResources().getColor(R.color.green_bubble);
			}else if(f>=hboundary4&&f<hboundary5){
				color = this.getResources().getColor(R.color.yellow_bubble);
			}else if(f>=hboundary5&&f<hboundary6){
				color = this.getResources().getColor(R.color.red_bubble);
			}
			break;

		default:
			break;
		}
		
		return color;
	}
	
	//画气泡
	private void drawBubble(Canvas canvas){
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		Bean bean = extractMiddle(calcuBean());
		float text = bean.getText();
		int resid = 1;
		switch (flag) {
		case 1:
			//温度
			if(text>=tboundary1&&text<tboundary2){
				resid = 1;
			}else if(text>=tboundary2&&text<tboundary3){
				resid = 2;
			}else if(text>=tboundary3&&text<tboundary4){
				resid = 3;
			}else if(text>=tboundary4&&text<tboundary5){
				resid = 2;
			}else if(text>=tboundary5&&text<tboundary6){
				resid = 1;
			}
			break;
		case 2:
			//湿度
			if(text>=hboundary1&&text<hboundary2){
				resid = 1;
			}else if(text>=hboundary2&&text<hboundary3){
				resid = 2;
			}else if(text>=hboundary3&&text<hboundary4){
				resid = 3;
			}else if(text>=hboundary4&&text<hboundary5){
				resid = 2;
			}else if(text>=hboundary5&&text<hboundary6){
				resid = 1;
			}
			break;

		}
		
		Bitmap bitmap = scaleByHeight(getResource(resid), (float)height/8*5);
		
		if(bitmap==null){
			return;
		}
		
		canvas.drawBitmap(bitmap, (float)width/2-(float)bitmap.getWidth()/2,0, paint);
	}
	
	//画bean
	private void drawBean(Canvas canvas){
		Paint paint = new Paint();
		
		List<Bean> beans = calcuBean();
		int size = beans.size();
		for(int i=0;i<size;i++){
			Bean bean = beans.get(i);
			int textsize = bean.getTextsize();
			paint.setTextSize(textsize);
			float ts = paint.measureText(bean.getText()+"");
			paint.setColor(bean.getTextcolor());
			canvas.drawText(bean.getText()+"", bean.getPosi_x()-ts/2, (float)height/8*7, paint);
		}
	}
	
	//根据滑动的距离从新计算，起始值及起始值得paddingleft;
	private void calcuInit(float dis,int flag){
		switch (flag) {
		case 1:
			//往左滑
			paddingleft = paddingleft-dis;
			if(paddingleft<=-padding){
				//滑动完整量
				float full = Math.abs(paddingleft/padding);
				//滑动余量
				float session = Math.abs(paddingleft%padding);
				start = (int) (start+full);
				paddingleft = -session;
			}
			
			
			break;
		case 2:
			//往右滑
			paddingleft = paddingleft+dis;
			if(paddingleft>-padding&&paddingleft<padding){
				//无需计算完整量
				//余量
				float session2 = Math.abs(paddingleft%padding);
				paddingleft = session2;
			}else
			if(paddingleft>=padding){
				//滑动完整量
				float full2 = Math.abs(paddingleft/padding);
				//滑动余量
				float session2 = Math.abs(paddingleft%padding);
				start = (int) (start-full2);
				paddingleft = session2;
			}
			
			break;

		default:
			break;
		}
		
		
		
	}
	
	//资源获取
	private Bitmap getResource(int flag){
		Bitmap bitmap = null;
		switch (flag) {
		case 1:
			//红
			BitmapDrawable drawablered = (BitmapDrawable) this.getResources().getDrawable(R.drawable.bubble_red);
			bitmap = drawablered.getBitmap();
			break;
		case 2:
			//黄
			BitmapDrawable drawableyellow = (BitmapDrawable) this.getResources().getDrawable(R.drawable.bubble_yellow);
			bitmap = drawableyellow.getBitmap();
			break;
		case 3:
			//绿
			BitmapDrawable drawablegreen = (BitmapDrawable) this.getResources().getDrawable(R.drawable.bubble_green);
			bitmap = drawablegreen.getBitmap();
			break;

		default:
			break;
		}
		return bitmap;
	}
	
	//根据高度缩放气泡图
	private Bitmap scaleByHeight(Bitmap bitmap,float height){
		if(bitmap==null){
			return null;
		}
		int hei = bitmap.getHeight();
		float ratio = height/hei;
		Matrix matrix = new Matrix();
		matrix.postScale(ratio, ratio);
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), hei, matrix, false);
	}
	
	//归为计算
	private void homIng(){
		Bean bean = extractMiddle(calcuBean());
		float posi = bean.getPosi_x();
		float dis = Math.abs(posi-(float)width/2);
		if(posi>(float)width/2){
			//中心值点在中心的右
			paddingleft = paddingleft-dis;
		}else{
			//中心值点在中心的左
			paddingleft = paddingleft+dis;
			
		}
	}
	
	//获取当前
	public float getCV(){
		Bean bean = extractMiddle(calcuBean());
		return bean.getText();
	}
	
	private class Bean{
		private float posi_x;
		private int numb;
		private int level;
		private float text;
		private int textsize;
		private int pointcolor;
		
		private int textcolor;

		public Bean() {
		}

		public float getPosi_x() {
			return posi_x;
		}

		public void setPosi_x(float posi_x) {
			this.posi_x = posi_x;
		}

		public int getNumb() {
			return numb;
		}

		public void setNumb(int numb) {
			this.numb = numb;
		}

		public int getLevel() {
			return level;
		}

		public void setLevel(int level) {
			this.level = level;
		}

		public float getText() {
			return text;
		}

		public void setText(float text) {
			this.text = text;
		}

		public int getTextsize() {
			return textsize;
		}

		public void setTextsize(int textsize) {
			this.textsize = textsize;
		}

		public int getPointcolor() {
			return pointcolor;
		}

		public void setPointcolor(int pointcolor) {
			this.pointcolor = pointcolor;
		}

		public int getTextcolor() {
			return textcolor;
		}

		public void setTextcolor(int textcolor) {
			this.textcolor = textcolor;
		}

		
		
		
	}
	
	

}
