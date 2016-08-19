package com.capitalbio.oemv2.myapplication.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.capitalbio.oemv2.myapplication.R;


@SuppressLint("DrawAllocation")
public class MyScaleLine extends View {
	Paint paint;
	Context context;
	Paint textpaint;
	Paint onclickpaint;
	float f;
	Bitmap dian,linedian;
	int index;// 记录点的位置
	float Y1, Y2, Y3;
	// 给控件设置监听事件
	private OnItemClickListener onItemClickListener;
	private OnItemLongClickListener onItemLongClickListener;
	private float oldx;// 用于点击判断
	private float movex;// 点移动的位置
	private Boolean isonclick = false,isOne=true;
	private float width;
	//是否第一次滑动
	private boolean isFirst=true;
	// 记录所有的点
	private List<Float> savepaint = new ArrayList<Float>();
	public List<HashMap<String, String>> paintlist = new ArrayList<HashMap<String, String>>();

	public int height;
	public MyScaleLine(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public MyScaleLine(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public MyScaleLine(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}

	private void init(Context context) {
		isFirst=true;
		this.context = context;
		// 初始化线画笔
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.parseColor("#4e4e4e"));
		paint.setStrokeWidth(3f);
		// 初始化文字画笔
		textpaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		textpaint.setStyle(Style.FILL);
		textpaint.setColor(Color.parseColor("#99999999"));
		textpaint.setStrokeWidth(3f);
		textpaint.setTextAlign(Align.CENTER);
		textpaint.setTextSize(35f);

		// 初始化被点击的文字画笔
		onclickpaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		onclickpaint.setStyle(Style.FILL);
		onclickpaint.setColor(Color.parseColor("#99cc33"));
		onclickpaint.setStrokeWidth(3f);
		onclickpaint.setTextAlign(Align.CENTER);
		onclickpaint.setTextSize(35f);
	}

	// 初始化传递线的长度
	public void setwidth(float width) {
		this.width = width;
	}
	/**
	 * 排序适配器
	 * 
	 * */

	class mySortByvalue implements Comparator {
		public int compare(Object o1, Object o2) {
			HashMap<String, String> s1 =  (HashMap<String, String>) o1;
			HashMap<String, String> s2 = (HashMap<String, String>) o2;
			if (changesecond(s1.get("time")) > changesecond(s2.get("time")))
				return 1;
			else if (changesecond(s1.get("time"))< changesecond(s1.get("time"))) {
				return -1;
			}
			return 0;
		}
	}
	// 添加数据画点 重绘
	public void drawpaint(List<HashMap<String, String>> paintlist) {
		this.paintlist = paintlist;
		
		Collections.sort(paintlist,new mySortByvalue() );
		isFirst=true;
		isOne=true;
		postInvalidate();
	}

	// 将时间转化秒
	private int changesecond(String time) {

		String alltime[] = time.split(":");
		int seconds = (Integer.valueOf(alltime[0]) * 3600)
				+ (Integer.valueOf(alltime[1]) * 60)
				+ Integer.valueOf(alltime[2]);
		return seconds;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		height = getHeight()/ 2;
		// 画横线
		dian = BitmapFactory.decodeResource(getResources(), R.drawable.timelins);
		linedian=BitmapFactory.decodeResource(getResources(), R.drawable.timeline);
		if (paintlist.size() > 0 && width / 4 * (paintlist.size()+1) > width) {
			f = width / 4 * (paintlist.size()+1);
			canvas.drawLine(0, height, width / 4 * (paintlist.size()+1),
					height, paint);
		} else {
			canvas.drawLine(0, height, width, height, paint);
			f = width;
		}

		// 画点
		if (null != paintlist) {
			savepaint.clear();
			
			for (int p = 0; p < paintlist.size(); p++) {
				// 画点
				/*
				 * canvas.drawBitmap(dian, (f / 86400 * changesecond(paintlist
				 * .get(p).get("time"))), (getHeight() / 2) - (dian.getHeight()
				 * / 2), paint); savepaint.add((f / 86400 *
				 * changesecond(paintlist .get(p).get("time"))));
				 */
				if(isOne){
                   
					if(p==0){
						isonclick=true;
						index=paintlist.size()-1;
					}
					
				}
	
				// 判断是否被点击
				if (index == p && isonclick) {
					// 文字
						canvas.drawText(paintlist.get(p).get("time"), width / 4
								* (p+1) + (dian.getWidth() / 2), height
								- dian.getHeight()/2*3, onclickpaint);
						//点
						canvas.drawBitmap(dian, (width / 4 * (p+1) ),
								height - (dian.getHeight() / 2), paint);
						savepaint.add((width / 4 * (p+1)));
						isOne=false;
					
				} else {
					// 文字
					
						canvas.drawText(paintlist.get(p).get("time"), width / 4
								* (p+1) + (dian.getWidth() / 2), height
								- dian.getHeight()/2*3, textpaint);
					//点
						canvas.drawBitmap(linedian, (width / 4 * (p+1)),
								height - (linedian.getHeight() / 2), paint);
						savepaint.add((width / 4 * (p+1)));
				}
			}
		}
		if(isFirst){
			isFirst=false;
			scrollTo((int)(f-width), getScrollY());
		}
		
	}

	float x = 0;

	int i = 0;
	long curClickTime;//当前时间
	private Handler mBaseHandler = new Handler();
	long LONG_PRESS_TIME = 500;

	/**

	 * 长按线程

	 */

	private LongPressedThread mLongPressedThread;

	@SuppressWarnings("deprecation")
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		int eAction = event.getAction();

		switch (eAction & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_UP:

			i = 0;
			Y3 = event.getY();

			/*
			 * if(Y3!=Y1){ return false; }
			 */
			Log.i("movex===", "action_up--x===" + event.getX());
			if (Math.abs(event.getX() - oldx) < 100) {
				for (int w = 0; w < savepaint.size(); w++) {
					if ((event.getX() + 70) >= (savepaint.get(w) - getScrollX())
							&& (event.getX() - 70) <= (savepaint.get(w) - getScrollX())) {
						Log.i("movex===", "onclick===" + width / 4 * w);
						Log.i("uptime===", "ACTION_up===" +Calendar.getInstance().getTimeInMillis());
						index = w;
						isonclick = true;
						if (Calendar.getInstance().getTimeInMillis() - curClickTime <= LONG_PRESS_TIME) {

							//取消注册的长按事件
							Log.i("longtouch", "click-----" );
							mBaseHandler.removeCallbacks(mLongPressedThread);
                            // 响应监听事件
							onItemClickListener.OnItemClick(paintlist.get(w).get(
									"time"));

							//isFirst=true;
							postInvalidate();
							Log.i("panther", "" + w);

						}else {
							Log.i("longtouch", "longclick-----" );
						}

					}

				}

			}
			break;
		case MotionEvent.ACTION_DOWN:
			x = event.getX();
			oldx = event.getX();
			Y1 = event.getY();
			curClickTime = Calendar.getInstance().getTimeInMillis();
			mLongPressedThread = new LongPressedThread();

			mBaseHandler.postDelayed(mLongPressedThread,LONG_PRESS_TIME);
			Log.i("movex===", "ACTION_DOWN===" + event.getX());
			Log.i("longtouch", "ACTION_DOWN===" +curClickTime);
			break;
		case MotionEvent.ACTION_POINTER_1_DOWN:
			// Log.i("panther", "111111111111");
			i = 1;
			x = event.getX(1);
			break;
		case MotionEvent.ACTION_MOVE:
			if (i == 0) {
				// Log.i("panther", ""+getScrollX());
				Log.i("longtouch", "ACTION_move===");

				if (f > getWidth()) {
					if (event.getX() - x < -5) {
						if (getWidth() < (f - (getScrollX() + Math.abs(event.getX() - x)))) {
							scrollTo((int) (getScrollX() + Math.abs(event.getX() - x)), getScrollY());
							mBaseHandler.removeCallbacks(mLongPressedThread);

						}
					} else if (event.getX() - x > 5) {
						// Log.i("panther", "2222222222"+getScrollX());
						if (getScrollX() - Math.abs(event.getX() - x) > 0) {
							scrollTo((int) (getScrollX() - Math.abs(event.getX() - x)), getScrollY());
							mBaseHandler.removeCallbacks(mLongPressedThread);

						}
					}
					x = event.getX();
					movex = event.getX();
					Log.i("movex===", "movex" + movex);
					Log.i("movex===", "getScrollX()" + getScrollX());
				}
			} else if (i == 1) {
				/*
				 * if (spacing(event) < x) { f = f - 50;//
				 * (event.getX(1)-event.getX(0)); } else if (spacing(event) > x)
				 * { f = f + 50;// (event.getX(1)-event.getX(0)); } x =
				 * spacing(event); invalidate();
				 */
			}
			Y2 = event.getY();
			Log.i("=====Y=====", "Y2:::" + Y2 + "Y1:::" + Y1);
			/*
			 * if(Y1!=Y2){ return false; }
			 */
			break;
			case MotionEvent.ACTION_POINTER_1_UP:
				i = 0;


					break;
		default:
			break;
		}
		try {
			return true;
		} catch (IllegalArgumentException ex) {
			return false;
		}

	}

	private float spacing(MotionEvent event) {

		try {
			return Math.abs(event.getX(1) - event.getX(0));
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0f;
	}

	// 给控件设置监听事件
	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

	// 监听接口
	public interface OnItemClickListener {
		void OnItemClick(String num);
	}


	// 给控件设置监听事件
	public void setOnItemLongClickListener(OnItemLongClickListener onItemClickListener) {
		this.onItemLongClickListener = onItemClickListener;
	}

	// 监听接口
	public interface OnItemLongClickListener {
		void OnItemLongClick(String num);
	}
	
	/**
	 * 排序适配器
	 * 
	 * */

	class SortByvalue implements Comparator {
		public int compare(Object o1, Object o2) {
			HashMap<String, String> s1 =  (HashMap<String, String>) o1;
			HashMap<String, String> s2 = (HashMap<String, String>) o2;
			if (Integer.valueOf(s1.get("day")) > Integer.valueOf(s2.get("day")))
				return 1;
			else if ((Integer.valueOf(s1.get("day"))< Integer.valueOf(s2.get("day")))) {
				return -1;
			}
			return 0;
		}
	}

	public class LongPressedThread implements Runnable{

		@Override

		public void run() {

       //这里处理长按事件
			/*OnItemLongClickListener.OnItemLongClick(paintlist.get(index).get(
					"time"));*/
			Log.i("touch","longtouch-------------");
			onItemLongClickListener.OnItemLongClick(paintlist.get(index).get("time"));
		}

	}


}
