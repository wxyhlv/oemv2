package com.capitalbio.oemv2.myapplication.View.charview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author wxy
 *
 */
@SuppressLint("ClickableViewAccessibility")
public  class CustomedCharViewPBM extends View implements OnGestureListener {

	Paint paint, textPaint ,paint1;

	private String[] datayleft ;// 设置左边数据 升序
	
	private HashMap<String, Object> dataright;// 设置右边数据，包括右边文字，范围背景色
	private int OFFSET_LEFT = 50;
	private int OFFSET_LEFT_min= 50;//固定y轴
	private int OFFSET_TOP = 80;
	private int TEXT_OFFSET = 20;
	private int OFFSET_RIGHT = 50;
	private int height;// 轴线高度
	private int width;// 轴线x总宽度
	private int widthVisible;//x 可见宽度
	private int[] range ;
    private String[] rightTitle;//右边标题文字
    private int[] colors;//背景色
	//private List<Integer> pbmvalues = new ArrayList<Integer>();
	
	private List<MyPoint> pbmpoints = new ArrayList<MyPoint>();
	List<RightIno> rightTitleInfos= new ArrayList<RightIno>();

	private List<Point> points = new ArrayList<Point>();
	private static final int FLING_MIN_DISTANCE = 50;
	private static final int FLING_MIN_VELOCITY = 0;
	GestureDetector mGesture = null;
	ScaleGestureDetector mScaleGestureDetector =null;
	int scale = 0;// x轴上手指移动的距离相对于x轴长度的10分之一的几小格
    int firstindex;//用于记录左边第一个数据点
	public static int y_size = 0 ;//表示y轴数字的个数；
	public static int x_size = 0;//表示x轴上一屏显示的个数
	public double maxy;//y轴上的最大刻度
	boolean ismove = false;
	Path path;Paint bgpaint;
	int[] colorsbar;
	private double[] xdata,ydata;
	private boolean isZoom;
	private int zoom ;
	private Context mContext;
	public CustomedCharViewPBM(Context context) {
		
		super(context);
		mContext =context;
		
		mGesture = new GestureDetector(this);
		mScaleGestureDetector=new ScaleGestureDetector(context,new MyScaleGestureListener());
	}

	public CustomedCharViewPBM(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext =context;

		mGesture = new GestureDetector(this);
		mScaleGestureDetector=new ScaleGestureDetector(mContext,new MyScaleGestureListener());

		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		height =heightMeasureSpec;
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		//设置y 轴左边
				
		init();
		
		drawTable(canvas);
		prepareLine();
		drawCurve(canvas);
	
	}

    //设置点数据
	public void setPointData(List<MyPoint> data){
		pbmpoints = data;
		//postInvalidate();
	}
      
	public  void init(){


	    height = getWidth();
		width = 3 * (getWidth()-OFFSET_LEFT_min-OFFSET_RIGHT);
		widthVisible = getWidth()-OFFSET_LEFT_min-OFFSET_RIGHT;
		/*colors = new int [] {getResources().getColor(R.color.bg_pmb_low),getResources().getColor(R.color.bg_pmb_nomal),
				getResources().getColor(R.color.bg_pmb_high)};*/
		// 设置画笔画轴线
		paint = new Paint();
		paint.setColor(Color.GRAY);
		paint.setDither(true);
		paint.setStrokeWidth(4);
		paint.setStyle(Paint.Style.STROKE);
		paint.setAntiAlias(true);
		// 设置文字画笔
		textPaint = new Paint();
		textPaint.setColor(Color.BLACK);
		textPaint.setDither(true);
		textPaint.setStyle(Paint.Style.STROKE);
		textPaint.setTextSize(20);
		//// 设置画笔画xu线
		paint1 = new Paint();
		paint1.setColor(Color.GRAY);
		paint1.setDither(true);
		paint1.setStrokeWidth(2);
		paint1.setStyle(Paint.Style.STROKE);
		paint1.setAntiAlias(false);
		path = new Path();
		PathEffect effects = new DashPathEffect(new float[] { 1, 2, 4, 8 }, 2);
		paint1.setPathEffect(effects);
		// 画背景颜色 动态 放前面否则遮住虚线
	    bgpaint = new Paint();


		/*//y轴上的刻度从下到上
		datayleft = new String[] { "九月", "20", "40", "60", "80", "100", "120",
				"140", "160", "180", "200" };
*/
		//maxy = 200;
		//点的yde 刻度
		/*values =  new int[]{ 105, 125, 103, 130, 110, 135, 115, 145, 100, 40,
				78, 98, 56, 100, 80, 130, 140, 180, 90, 130 };*/

		/* colorsbar = new int[]{getResources().getColor(R.color.bg_aircat_6),
				getResources().getColor(R.color.bg_aircat_5),
				getResources().getColor(R.color.bg_aircat_4),
				getResources().getColor(R.color.bg_aircat_3),
				getResources().getColor(R.color.bg_aircat_2),
				getResources().getColor(R.color.bg_aircat_1),
		};*/
	
	}


	public void drawTable(Canvas canvas) {
		
		//setRigthTitle(null);
		drawBackGround(canvas);
		
	
		for (int i = 1; i < y_size; i++) {
			path.moveTo(OFFSET_LEFT_min, OFFSET_TOP + height / y_size * i);
			path.lineTo(OFFSET_LEFT_min + widthVisible,
					OFFSET_TOP + height / y_size * i);
			canvas.drawPath(path, paint1);

		}
		
		// 画轴线 y不变
		canvas.drawLine(OFFSET_LEFT_min, OFFSET_TOP, OFFSET_LEFT_min, OFFSET_TOP
				+ height, paint);
		/*//画右边的程度线
		Paint p = new Paint();
		p.setStrokeWidth(8);
		for(int i =0;i<6;i++){
		p.setColor(colorsbar[i]);	
		canvas.drawLine(OFFSET_LEFT_min + widthVisible, OFFSET_TOP+ height * i /6, OFFSET_LEFT_min + widthVisible, OFFSET_TOP
				+ height * (i+1) /6 , p);
		}*/
		// x
		canvas.drawLine(OFFSET_LEFT_min, OFFSET_TOP + height, OFFSET_LEFT_min + widthVisible
				, OFFSET_TOP + height, paint);
		// 画左边文字
		if(ydata!=null&&ydata.length>0) {
			for (int i = 0; i < ydata.length; i++) {
				canvas.drawText(ydata[i] + "", OFFSET_LEFT_min - TEXT_OFFSET - 30,
						OFFSET_TOP + height / y_size * (y_size - i), textPaint);
			}
			// 画刻度
			for (int i = 1; i < ydata.length - 1; i++) {
				canvas.drawLine(OFFSET_LEFT_min, OFFSET_TOP + height / y_size * (y_size - i),
						OFFSET_LEFT_min - 20, OFFSET_TOP + height / y_size * (y_size - i),
						paint);
			}
		}

   
		// 画底部文字 、、
		for (int i = 0; i < 31; i++) {
			if(x_size>0){
				canvas.drawText(i + 1 + "", OFFSET_LEFT + widthVisible * i / x_size ,
						OFFSET_TOP + height + TEXT_OFFSET, textPaint);
			}

		}
		


		// 画右边文字
		Path textPath = new Path();
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.BLACK);
		paint.setTextSize(40);
		paint.setAntiAlias(true);
		if(rightTitleInfos!=null&&rightTitleInfos.size()>0){
		for (int i = 0; i <rightTitleInfos.size() ; i++) {
			textPath.moveTo(OFFSET_LEFT_min + widthVisible + 20,
					(float) (OFFSET_TOP + height * i / rightTitleInfos.size()));
			textPath.lineTo(OFFSET_LEFT_min + widthVisible + 20, OFFSET_TOP
					+ height * (i + 1) / rightTitleInfos.size());
			canvas.drawTextOnPath(rightTitleInfos.get(i).getGrade(), textPath, 30, 0, paint);
			textPath.reset();
		}
		}
	}

   //画背景
	public void drawBackGround(Canvas canvas) {
		if(rightTitleInfos.size()==0){
			return;
		}
		for(int i = 0;i<rightTitleInfos.size();i++){
			bgpaint.setColor(rightTitleInfos.get(i).getRectBGcolor());
			canvas.drawRect(OFFSET_LEFT_min, (float) (OFFSET_TOP + (height
					* (maxy - rightTitleInfos.get(i).getMax()) / maxy)), OFFSET_LEFT_min + widthVisible ,(float) (OFFSET_TOP + (height * (maxy - rightTitleInfos.get(i).getMin()) / maxy)), bgpaint);
			
			
		}

	}

	//设置右边等级信息
	public void setRigthTitle(List<RightIno> infos){
		rightTitleInfos = infos;//数据应为升序

	}

	public void setXYdata(XYData xyDatas){

			xdata =xyDatas.getxDatas();
			ydata = xyDatas.getyDatas();
			y_size = xyDatas.getYlablecount();
			x_size = xyDatas.getXlablecount();
		    maxy = ydata[ydata.length-1];


	}
	public void prepareLine() {
		boolean flag =false;
		points = new ArrayList<Point>();

		
	  for (int i = firstindex; i < firstindex +11; i++) {
			Point p = new Point();
			if(pbmpoints != null &&pbmpoints.size()>0&&i < pbmpoints.size()){
			double y = OFFSET_TOP + height * (maxy - pbmpoints.get(i).getY()) / maxy;
			int x = 0;
			x=OFFSET_LEFT_min + widthVisible * (i -firstindex )/ x_size;
			Log.i("ii", i+"");
			Log.i("firstindex", firstindex+"");
			Log.i("xx", x + "");
				p.set(x, (int)y);
			points.add(p);
			}
		
	
		}
		//Log.i("points", points.size() + "  "+points.get(points.size()-1));
	}

	public void drawCurve(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(pbmpoints.get(0).getColor());
		paint.setStrokeWidth(8);
		paint.setAntiAlias(true);
		// canvas.drawLines(line, paint);
        Bitmap dot = BitmapFactory.decodeResource(getResources(), pbmpoints.get(0).getBitmapid());
		if (points.size() >= 2) {
			for (int i = 0; i < points.size()-1; i++) {
				
				canvas.drawLine(points.get(i).x, points.get(i).y,
						points.get(i + 1).x, points.get(i + 1).y, paint);
				//绘制数据点
				canvas.drawBitmap(dot,points.get(i).x-dot.getWidth()/2,points.get(i).y-dot.getHeight()/2,null);
			}
			canvas.drawBitmap(dot,points.get(points.size()-1).x-dot.getWidth()/2,points.get(points.size()-1).y-dot.getHeight()/2,null);
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int pointCount = event.getPointerCount();
		Log.i("pointcount",""+pointCount);
		if(pointCount==1){
			isZoom = false;
			return mGesture.onTouchEvent(event); }
		else{
			isZoom = true;
			return mScaleGestureDetector.onTouchEvent(event);

		}


	}

	@Override
	public boolean onDown(MotionEvent e) {
		Log.d("viewchar", "onDown");
	//	Toast.makeText(getContext(), "你点击了屏幕" + e.getX()+ " "+e.getY(), Toast.LENGTH_SHORT).show();

		return true;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {

		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		if (e1.getX() > e2.getX()) {
			ismove = true;
			// 向左
			float x = e1.getX() - e2.getX();
			Log.i("x", x+"");
			scale = (int) (x_size*x/width * 3);

			if(scale==0){
				scale=1;
			}

			firstindex +=scale;
			Log.i("firstindex left", firstindex + "");
			OFFSET_LEFT -= scale * width / 3 / x_size;
			if(OFFSET_LEFT<=OFFSET_LEFT-width ){
				OFFSET_LEFT =50;
			}
			try {
				Thread.sleep(80);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			postInvalidate();
			Log.i("left", OFFSET_LEFT+"");
		} else if (e1.getX() < e2.getX()) {
			ismove = true;
			float x = e2.getX() - e1.getX();
			scale = (int) (x_size* 3 * x/width);
			Log.i("scale right", scale + "");
			if(scale==0){
				scale=1;
			}
			if(scale>5){scale=4;}
			firstindex -=scale;
			if(firstindex<0){firstindex = 0;}
			Log.i("firstindex right", firstindex + "");
			OFFSET_LEFT += scale * width / 3 / x_size;

			Log.i("left", OFFSET_LEFT+"");
			//if(OFFSET_LEFT < 50){
			//保证Yzhou 不变
			if(OFFSET_LEFT > 50){
				OFFSET_LEFT =50;
			}
			try {
				Thread.sleep(80);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			postInvalidate();
			//}
		}

		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}


	public  class MyScaleGestureListener implements ScaleGestureDetector.OnScaleGestureListener
	{
		private float oldDist;
		private float newDist;
		Matrix mMatrix = new Matrix();
		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			// TODO Auto-generated method stub
			newDist=detector.getCurrentSpan();

			//缩放比例
			float scale = detector.getScaleFactor();

			//float scalef=oldDist/newDist;
			Log.i("suofang---", scale +"vvvvv"+x_size +"" );


			x_size =Math.round(x_size /scale);

			if(x_size<=0){
				x_size=1;
			}

       Log.i("suofang-", "affter"+x_size +"" );
//TThread.sleep
			try {
				Thread.sleep(80);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			postInvalidate();

			oldDist=newDist;

			return false;
		}

		@Override
		public boolean onScaleBegin(ScaleGestureDetector detector) {
			// TODO Auto-generated method stub
			oldDist=detector.getCurrentSpan();
			newDist=detector.getCurrentSpan();

			return true;
		}

		@Override
		public void onScaleEnd(ScaleGestureDetector detector) {
			/*newDist=detector.getCurrentSpan();

			// mMatrix.set(myImageView.getImageMatrix());
			//缩放比例
			//float scale = detector.getScaleFactor();
			float scalef=newDist/oldDist;
			Log.i("suofang---", scalef +"");
			if(newDist >oldDist){
				x_size /=scalef;

			}
			else{
				x_size *=scalef;}
			if(x_size<=0){
				x_size=1;
			}
			oldDist=newDist;

			postInvalidate();*/
		}

	}
}
