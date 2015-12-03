package com.capitalbio.oemv2.myapplication.View.charview;/*
package charview.com.example.mac.charview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;

*/
/**
 * @author wxy
 *
 *//*

@SuppressLint("ClickableViewAccessibility")
public class AircatCharView extends View implements OnGestureListener {

	Paint paint, textPaint, paint1;

	private String[] datayleft;// 设置左边数据 升序

	private HashMap<String, Object> dataright;// 设置右边数据，包括右边文字，范围背景色
	private int OFFSET_LEFT = 50;
	private int OFFSET_LEFT_min = 50;// 固定y轴
	private int OFFSET_TOP = 80;
	private int TEXT_OFFSET = 20;
	private int OFFSET_RIGHT = 50;
	private int height;// 轴线高度
	private int width;// 轴线x总宽度
	private int widthVisible;// x 可见宽度
	private int[] range;
	private String[] rightTitle;// 右边标题文字

	private List<MyPoint> pbmpoints = new ArrayList<MyPoint>();
	List<LevalInfo> rightTitleInfos = new ArrayList<LevalInfo>();
	private int[] values;
	private List<Point> points = new ArrayList<Point>();
	private static final int FLING_MIN_DISTANCE = 50;
	private static final int FLING_MIN_VELOCITY = 0;
	GestureDetector mGesture = null;
	int scale = 0;// x轴上手指移动的距离相对于x轴长度的10分之一的几小格
	int firstindex;// 用于记录左边第一个数据点
	public static int y_size = 6;// 表示y轴数字的个数；
	public static int x_size = 6;// 表示x轴上一屏显示的个数
	boolean isZoom = false;//是否为缩放
	 int zoom = 1;
	Path path;
	Paint bgpaint;
	int[] colorsbar;
	private String month ="";
	private ScaleGestureDetector mScaleGestureDetector;
	private Context mContext;
	private MyPoint currentPoint;
	public AircatCharView(Context context) {

		super(context);
		mContext = context;
		mGesture = new GestureDetector(this);
		//mScaleGestureDetector=new ScaleGestureDetector(context, new MyScaleGestureListener());
	}

	public AircatCharView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		mGesture = new GestureDetector(this);
		//mScaleGestureDetector=new ScaleGestureDetector(mContext,new MyScaleGestureListener());
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		// 设置y 轴左边

		init();
		drawTable(canvas);
		prepareLine();
		drawCurve(canvas);

	}

	// 设置点数据
	@SuppressWarnings("unchecked")
	public void setPointData(HashMap<String,Object> data) {
		pbmpoints = (List<MyPoint>) data.get("data");
		month =(String) data.get("month");
		postInvalidate();
	}
	public MyPoint getCurrentPoint(){
		return currentPoint;
	}
	public void init() {
		height = getWidth();
		width = 3 * (getWidth() - OFFSET_LEFT_min - OFFSET_RIGHT);
		widthVisible = getWidth() - OFFSET_LEFT_min - OFFSET_RIGHT;
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
		// // 设置画笔画虚线
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

		// y轴上的刻度从下到上
		datayleft = new String[] { "0", "50", "100", "150", "200", "300",
				">300" };
		rightTitle = new String[] { "严重", "重度", "中度", "轻度", "良", "优" };
		colorsbar = new int[] { getResources().getColor(R.color.bg_aircat_6),
				getResources().getColor(R.color.bg_aircat_5),
				getResources().getColor(R.color.bg_aircat_4),
				getResources().getColor(R.color.bg_aircat_3),
				getResources().getColor(R.color.bg_aircat_2),
				getResources().getColor(R.color.bg_aircat_1), };

	}

	public void drawTable(Canvas canvas) {

		// 画轴线 y不变
		canvas.drawLine(OFFSET_LEFT_min, OFFSET_TOP, OFFSET_LEFT_min,
				OFFSET_TOP + height, paint);
		// 画右边的程度线
		Paint p = new Paint();
		p.setStrokeWidth(10);
		for (int i = 0; i < 6; i++) {
			p.setColor(colorsbar[i]);
			canvas.drawLine(OFFSET_LEFT_min + widthVisible, OFFSET_TOP + height
					* i / 6, OFFSET_LEFT_min + widthVisible, OFFSET_TOP
					+ height * (i + 1) / 6, p);

		}

		// x
		canvas.drawLine(OFFSET_LEFT_min, OFFSET_TOP + height, OFFSET_LEFT_min
				+ widthVisible, OFFSET_TOP + height, paint);
		// 画左边文字
		for (int i = 0; i < datayleft.length; i++) {
			canvas.drawText(datayleft[i] + "", OFFSET_LEFT_min - TEXT_OFFSET
					- 30, OFFSET_TOP + height / y_size * (y_size - i),
					textPaint);
		}
		// 画刻度及背景
		for (int i = 1; i < datayleft.length; i++) {
			canvas.drawLine(OFFSET_LEFT_min, OFFSET_TOP + height / y_size
					* (y_size - i), OFFSET_LEFT_min - 20, OFFSET_TOP + height
					/ y_size * (y_size - i), paint);
			if (i % 2 == 0) {
				bgpaint.setColor(getResources().getColor(
						R.color.bg_aircat_bg_grey));
			} else {
				bgpaint.setColor(getResources().getColor(
						R.color.bg_aircat_bg_white));
			}

			canvas.drawRect(OFFSET_LEFT_min, (float) (OFFSET_TOP + height
					/ y_size * (i - 1)), OFFSET_LEFT_min + widthVisible,
					(float) (OFFSET_TOP + height / y_size * i), bgpaint);
		}

		// 画虚线
		for (int i = 1; i < y_size; i++) {
			path.moveTo(OFFSET_LEFT_min, OFFSET_TOP + height / y_size * i);
			path.lineTo(OFFSET_LEFT_min + widthVisible, OFFSET_TOP + height
					/ y_size * i);
			canvas.drawPath(path, paint1);

		}
		canvas.drawText(month, OFFSET_LEFT_min - TEXT_OFFSET - 30, OFFSET_TOP
				+ height + TEXT_OFFSET, textPaint);

		// 画底部文字 、、
		for (int i = 0; i < 31; i++) {
			if(isZoom && zoom!=0){
				canvas.drawText(i + 1 + "", OFFSET_LEFT_min + widthVisible * i / 6/zoom
						+ 10, OFFSET_TOP + height + TEXT_OFFSET, textPaint);
			}else{
			canvas.drawText(i + 1 + "", OFFSET_LEFT + widthVisible * i / 6
					+ 10, OFFSET_TOP + height + TEXT_OFFSET, textPaint);
		}}

		// 画右边文字
		Path textPath = new Path();
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.BLACK);
		paint.setTextSize(40);
		paint.setAntiAlias(true);
		for (int i = 0; i < rightTitle.length; i++) {
			textPath.moveTo(OFFSET_LEFT_min + widthVisible + 20,
					(float) (OFFSET_TOP + height * i / y_size));
			textPath.lineTo(OFFSET_LEFT_min + widthVisible + 20, OFFSET_TOP
					+ height * (i + 1) / y_size);
			canvas.drawTextOnPath(rightTitle[i], textPath, 30, 0, paint);
			textPath.reset();
		}
	}

	// 将点值转坐标
	public void prepareLine() {
		boolean flag = false;
		points = new ArrayList<Point>();

		for (int i = firstindex; i < firstindex + 7; i++) {
			Point p = new Point();
			int y = 0, x = 0;
			if (pbmpoints != null && pbmpoints.size() > 0
					&& i < pbmpoints.size()) {
				if (pbmpoints.get(i).getY() <= 200) {
					y = OFFSET_TOP + height * (200 - pbmpoints.get(i).getY())
							/ 200;
				}
				if (pbmpoints.get(i).getY() > 200
						&& pbmpoints.get(i).getY() <=300) {
					y = OFFSET_TOP + height/6 * (300 - pbmpoints.get(i).getY())/ 100 + height/6;
						
				}
				x = OFFSET_LEFT_min + widthVisible * (i - firstindex) / x_size;
				Log.i("firstindex", firstindex + "");
				Log.d("viewchar", "POINTS" +x + "," + y );
				p.set(x, y);
				points.add(p);
			}

		}
	}

	public void drawCurve(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(pbmpoints.get(0).getColor());
		paint.setStrokeWidth(8);
		paint.setAntiAlias(true);
		// canvas.drawLines(line, paint);
		Bitmap dot = BitmapFactory.decodeResource(getResources(), pbmpoints
				.get(0).getBitmapid());
		if (points.size() >= 2) {
			for (int i = 0; i < points.size() - 1; i++) {

				canvas.drawLine(points.get(i).x, points.get(i).y,
						points.get(i + 1).x, points.get(i + 1).y, paint);
				// 绘制数据点
				canvas.drawBitmap(dot, points.get(i).x - dot.getWidth() / 2,
						points.get(i).y - dot.getHeight() / 2, null);
			}
			canvas.drawBitmap(dot,
					points.get(points.size() - 1).x - dot.getWidth() / 2,
					points.get(points.size() - 1).y - dot.getHeight() / 2, null);
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.d("viewchar", "onTouchEvent");
		 int pointCount = event.getPointerCount();
		  if(pointCount==1){
			  isZoom = false;
			 return mGesture.onTouchEvent(event); }
		  else{
			  isZoom = true;
		  }
		    return mScaleGestureDetector.onTouchEvent(event);

	}

	@Override
	public boolean onDown(MotionEvent e) {
		Log.d("viewchar", "onDown" +e.getX() + "," + e.getY() );
		// Toast.makeText(getContext(), "你点击了屏幕" + e.getX()+ " "+e.getY(),
		// Toast.LENGTH_SHORT).show();
		int x = (int) e.getX();
		int y = (int) e.getY();
		Point p = new Point();
		p.x = x;
		p.y = y;
		for(int i =0;i<points.size();i++){
			int range =Math.abs(points.get(i).x-x);
			if(range < 20 &&range >0){
				currentPoint = pbmpoints.get(i);
	    		Log.d("viewchar", "onDownAF" +x + "," + y );
			}
		}
      
		return true; 
	}
	

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		Log.d("viewchar", "onFling");
		if (e1.getX() > e2.getX()) {
			// 向左
			float x = e1.getX() - e2.getX();
			Log.i("x", x + "");
			scale = (int) (x_size * x / width * 3);

			if (scale == 0) {
				scale = 1;
			}

			firstindex += scale;
			Log.i("firstindex left", firstindex + "");
			OFFSET_LEFT -= scale * width / 3 / x_size;
			if (OFFSET_LEFT <= OFFSET_LEFT - width) {
				OFFSET_LEFT = 50;
			}
			postInvalidate();
			Log.i("left", OFFSET_LEFT + "");
		} else if (e1.getX() < e2.getX()) {
			float x = e2.getX() - e1.getX();
			scale = (int) (x_size * 3 * x / width);
			Log.i("scale right", scale + "");
			if (scale == 0) {
				scale = 1;
			}
			if (scale > 5) {
				scale = 4;
			}
			firstindex -= scale;
			if (firstindex < 0) {
				firstindex = 0;
			}
			Log.i("firstindex right", firstindex + "");
			OFFSET_LEFT += scale * width / 3 / x_size;
			Log.i("left", OFFSET_LEFT + "");
			// if(OFFSET_LEFT < 50){
			// 保证Yzhou 不变
			if (OFFSET_LEFT > 50) {
				OFFSET_LEFT = 50;
			}
			postInvalidate();
			// }
		}

		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {

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
	public  class MyScaleGestureListener implements OnScaleGestureListener
	 {
	  private float oldDist;
	  private float newDist;
	  Matrix mMatrix = new Matrix(); 
	  @Override
	  public boolean onScale(ScaleGestureDetector detector) {
	   // TODO Auto-generated method stub
	   newDist=detector.getCurrentSpan();
	  
	  // mMatrix.set(myImageView.getImageMatrix());
	   //缩放比例
	   //float scale = detector.getScaleFactor();
	   float scalef=newDist/oldDist;
	   Log.i("suofang---", scalef +"");
	  
	   zoom +=1;
	   if(zoom>=5){
		   zoom = 5;
	   }
	   
	   Log.i("suofang", zoom +"");
	 */
/*  //mMatrix.setScale(scale, scale,detector.getFocusX(),detector.getFocusY());
	   mMatrix.postScale(scale, scale,detector.getFocusX(),detector.getFocusY());
	  // myImageView.setImageMatrix(mMatrix);
*//*
	 */
/*  if(scale > 0){
	   width = (int) (width/scale);
      }*//*

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
	   // TODO Auto-generated method stub
		  postInvalidate();
	  }

	 }
}
*/
