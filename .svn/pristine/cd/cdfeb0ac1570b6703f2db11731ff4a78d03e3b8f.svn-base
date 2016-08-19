package com.capitalbio.oemv2.myapplication.View.views;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.FontMetrics;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.capitalbio.oemv2.myapplication.R;

public class BubbleCudgelView extends View {

	private float width, height;

	private int levelsum;
	private int levelcurr;
	private boolean isNeutral;

	// 资源

	private Bitmap bubbleone;
	private Bitmap bubbletwo;
	private Bitmap bubblethree;
	private Bitmap bubblefour;
	private Bitmap bubblefive;
	private Bitmap bubblesix;

	private Bitmap cudgelthree;
	private Bitmap cudgelfive;
	private Bitmap cudgelsix;

	//
	private final int one = 1;
	private final int two = 2;
	private final int three = 3;
	private final int four = 4;
	private final int five = 5;
	private final int six = 6;

	// 各种显示比例
	// 气泡高度占整个控件的高度的比例
	private float ratio_bubbleheight_viewheight = (float) 13 / 16;
	// 气泡宽度与气泡高度的比例
	private float ratio_bubblewidth_bubbleheight = (float) 5 / 6;

	private String showtext = "99";

	// 动画细腻程度
	private int animexquisite = 100;

	// 是否执行动画
	private boolean isAnim = false;

	// 动画执行中
	private boolean isAnimRunning = false;

	// 当前气泡的中心点的x坐标,用于动画
	private float bubble_center_x;

	// 气泡内文本y方向上在气泡内显示比例
	private float ratio_showtext_bubbleheight_y = 0.6f;

	// 文本的宽度占气泡宽度的比例
	private float ratio_showtext_bubblewidth_width = 0.6f;

	// 文本高度占气泡高度的比例
	private float ratio_showtext_bubbleheight_height = 0.6f;

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			int wt = msg.what;
			bubble_center_x = (float) msg.obj;
			invalidate();
			if (wt == 0) {
				isAnimRunning = false;
			}

		};
	};

	// 气泡的点击事件
	private OnClickListener clickListener;

	// 记录当前气泡所在的区域
	private float[] bubbleposi;

	//记录onmeasure是否执行
	private boolean alreadyMeasure = false;

	public BubbleCudgelView(Context context) {
		this(context, null);
	}

	public BubbleCudgelView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public BubbleCudgelView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		TypedArray array = context.obtainStyledAttributes(attrs,
				R.styleable.BubbleCudgelView);

		levelsum = array.getInt(R.styleable.BubbleCudgelView_levelsum, 6);
		levelcurr = array.getInt(R.styleable.BubbleCudgelView_levelcurr, 1);

		showtext = array.getString(R.styleable.BubbleCudgelView_textshow);

		isNeutral = array.getBoolean(R.styleable.BubbleCudgelView_neutral,false);

		getRes(levelsum);

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		width = getMeasuredWidth();
		if(width!=0){
			alreadyMeasure = true;
		}
		height = getMeasuredHeight();
		Log.i("===","====onMeasure====width===="+width);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);

		// 绘制金箍棒
		drawCudgel(canvas);
		// 绘制气泡
		drawBubble(canvas);
		// 绘制气泡上的文本
		//drawBubbleText(canvas);
	}

	// 下落点是否在气泡区域
	boolean downis = false;
	// 抬起点是否在气泡区域
	boolean upis = false;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub

		// 是否是在移动
		boolean moveis = false;
		// 是否是抬起
		boolean isup = false;

		int action = event.getAction();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			// 获取下落点的x,y
			float xdown = event.getX();
			Log.i("xdown","==========xdown=========="+xdown);
			float ydown = event.getY();
			Log.i("ydown","==========ydown=========="+ydown);
			// 判断下落点是否在气泡区域
			downis = isIn(xdown, ydown, bubbleposi);
			break;
		case MotionEvent.ACTION_MOVE:
			moveis = true;
			break;
		case MotionEvent.ACTION_UP:
			isup = true;
			// 获取抬起点的x,y
			float xup = event.getX();
			float yup = event.getY();
			// 判断抬起点是否在气泡区域
			upis = isIn(xup, yup, bubbleposi);

			break;

		}

		if (moveis) {

		} else {
			if (downis && upis) {
				if (isup) {
					if (clickListener != null) {
						clickListener.onClick(this);
						
					}
				}

			}
		}

		return true;
	}

	/**
	 * 判断某个点是否在某个区域内
	 * 
	 * @param x
	 * @param y
	 * @param pos
	 * @return
	 */
	private boolean isIn(float x, float y, float[] pos) {
		boolean is = false;

		float l = pos[0];
		float t = pos[1];
		float r = pos[2];
		float b = pos[3];

		if (x > l && x < r && y > t && y < b) {
			is = true;
		}

		return is;
	}

	// 设置气泡的点击事件
	public void setBubbleOnClickListener(OnClickListener clickListener) {
		this.clickListener = clickListener;
	}

	public void setShowText(String showtext) {
		this.showtext = showtext;
		invalidate();
	}

	public void setLevel(int level, boolean isanim) {
		if (level == levelcurr) {
			return;
		}

		if (levelsum == six) {
			if (level < 1 || level > 6) {
				return;
			}
		}
		if (levelsum == five) {
			if (level < 1 || level > 5) {
				return;
			}
		}
		if (levelsum == three) {
			if (level < 1 || level > 3) {
				return;
			}
		}

		isAnim = isanim;
		if (isanim) {
			X x = new X(level);
			x.start();
		} else {
			levelcurr = level;
			invalidate();
		}

	}

	private class X extends Thread{

		private int level;

		public X(int level) {
			this.level = level;
		}

		@Override
		public void run() {

			//循环判断onmeasure是否执行
			while(!alreadyMeasure){

			}

			if (isAnimRunning) {
				return;
			}
			List<Float> floats = calcuSeriesPoint(levelcurr, level);
			Anim anim = new Anim(floats, handler);
			anim.start();
			isAnimRunning = true;
			levelcurr = level;
		
		}

	}

	private class Anim extends Thread {
		private List<Float> floats;
		private Handler handler;

		public Anim(List<Float> floats, Handler handler) {
			this.floats = floats;
			this.handler = handler;
		}

		@Override
		public void run() {
			for (int i = 0; i < floats.size(); i++) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				float temp = floats.get(i);
				// 根据当前的x坐标绘制气泡
				Message message = new Message();
				message.what = i == (floats.size() - 1) ? 0 : 1;
				message.obj = temp;
				handler.sendMessage(message);
			}
		}

	}

	// 根据起始等级和结束等级，计算一系列连续的点
	private List<Float> calcuSeriesPoint(int startlevel, int endlevel) {
		List<Float> floats = new ArrayList<Float>();
		//Log.i("=======", "=========startlevel========" + startlevel);
		//Log.i("=======","=========endlevel========"+endlevel);

		// 起始的x坐标
		float start = calcuEachLevelCenterXCoordinate(startlevel);
		//Log.i("=======","=========start========"+start);
		// 终止的x坐标
		float end = calcuEachLevelCenterXCoordinate(endlevel);
		// 两点间距离
		float dis = Math.abs(start - end);
		// 根据动画细腻程度，计算没动画一帧的距离
		float frame = dis / animexquisite;

		if (start < end) {
			for (int i = 1; i <= animexquisite; i++) {
				float next = start + frame * i;
				//Log.i("=======","=========next========"+next);
				floats.add(next);
			}
		} else {
			for (int i = 1; i <= animexquisite; i++) {
				float next = start - frame * i;
				//Log.i("=======","=========next========"+next);
				floats.add(next);
			}
		}

		return floats;
	}

	public String getShowText() {
		return showtext;
	}

	public int getLevel() {
		return levelcurr;
	}

	private void drawCudgel(Canvas canvas) {
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		RectF dst = new RectF(0, height * ratio_bubbleheight_viewheight, width,
				height);

		switch (levelsum) {
		case three:
			canvas.drawBitmap(cudgelthree, null, dst, paint);
			break;
		case five:
			canvas.drawBitmap(cudgelfive, null, dst, paint);
			break;
		case six:
			canvas.drawBitmap(cudgelsix, null, dst, paint);
			break;

		default:
			break;
		}
	}

	// 计算气泡的绘制区域
	private float[] calcuBubbleDrawArea(float xcoord) {
		float[] pos = new float[4];

		// 计算气泡的高度
		float bubbleheight = height * ratio_bubbleheight_viewheight;
		// 计算气泡的宽度
		float bubblewidth = bubbleheight * ratio_bubblewidth_bubbleheight;

		float l = xcoord - bubblewidth / 2;
		float t = 0;
		float r = xcoord + bubblewidth / 2;
		float b = height * ratio_bubblewidth_bubbleheight;

		pos[0] = l;
		pos[1] = t;
		pos[2] = r;
		pos[3] = b;

		return pos;
	}

	/**
	 * 计算每个等级中心点的x坐标
	 * 
	 * @param levelid
	 * @return
	 */
	private float calcuEachLevelCenterXCoordinate(int levelid) {

		float x = 0;

		Log.i("======","======width======"+width);
		float widtheachlevelhalf = width / levelsum / 2;
		Log.i("======","======widtheachlevelhalf======"+widtheachlevelhalf);

		switch (levelid) {
		case one:
			x = widtheachlevelhalf;
			break;
		case two:
			x = widtheachlevelhalf * 3;
			break;
		case three:
			x = widtheachlevelhalf * 5;
			break;
		case four:
			x = widtheachlevelhalf * 7;
			break;
		case five:
			x = widtheachlevelhalf * 9;
			break;
		case six:
			x = widtheachlevelhalf * 11;
			break;

		default:
			break;
		}

		return x;
	}

	private void drawBubble(Canvas canvas){
		Paint paint = new Paint();
		paint.setAntiAlias(true);

		float x;
		if (isAnim) {
			x = bubble_center_x;
		} else {
			x = calcuEachLevelCenterXCoordinate(levelcurr);
		}

		// 当前气泡的中心的x坐标

		float[] pos = calcuBubbleDrawArea(x);

		bubbleposi = pos;

		RectF dst = new RectF(pos[0], pos[1], pos[2], pos[3]);

		switch (levelsum) {
		case three:
			switch (levelcurr) {
			case one:
				if(isNeutral){
					canvas.drawBitmap(bubbletwo, null, dst, paint);
				}else{
					canvas.drawBitmap(bubbleone, null, dst, paint);
				}

				break;
			case two:
				if(isNeutral){
					canvas.drawBitmap(bubbleone, null, dst, paint);
				}else{
					canvas.drawBitmap(bubbletwo, null, dst, paint);
				}

				break;
			case three:
				canvas.drawBitmap(bubblefive, null, dst, paint);
				break;
			}
			break;
		case five:
			switch (levelcurr) {
			case one:
				if(isNeutral){
					canvas.drawBitmap(bubblethree, null, dst, paint);
				}else{
					canvas.drawBitmap(bubbleone, null, dst, paint);
				}

				break;
			case two:
				if(isNeutral){
					canvas.drawBitmap(bubbletwo, null, dst, paint);
				}else{
					canvas.drawBitmap(bubbletwo, null, dst, paint);
				}

				break;
			case three:
				if(isNeutral){
					canvas.drawBitmap(bubbleone, null, dst, paint);
				}else{
					canvas.drawBitmap(bubblethree, null, dst, paint);
				}
				break;
			case four:
				canvas.drawBitmap(bubblefive, null, dst, paint);
				break;
			case five:
				canvas.drawBitmap(bubblesix, null, dst, paint);
				break;
			}
			break;
		case six:
			switch (levelcurr) {
			case one:
				canvas.drawBitmap(bubbleone, null, dst, paint);
				break;
			case two:
				canvas.drawBitmap(bubbletwo, null, dst, paint);
				break;
			case three:
				canvas.drawBitmap(bubblethree, null, dst, paint);
				break;
			case four:
				canvas.drawBitmap(bubblefour, null, dst, paint);
				break;
			case five:
				canvas.drawBitmap(bubblefive, null, dst, paint);
				break;
			case six:
				canvas.drawBitmap(bubblesix, null, dst, paint);
				break;

			}
			break;

		}

		// 绘制文本
		// 获取气泡的宽高
		float bubbheight = height * ratio_bubbleheight_viewheight;
		float bubbwidth = bubbheight * ratio_bubblewidth_bubbleheight;
		// 获取适合当前文本的画笔
		/*Paint paint2 = searchPaint(
				bubbwidth * ratio_showtext_bubblewidth_width, bubbheight
						* ratio_showtext_bubbleheight_height, showtext);*/
		// Paint paint2 = searchPaint(bubbwidth, bubbheight, showtext);
		Paint paint2 = new Paint();
		paint2.setTextSize(getResources().getDimensionPixelSize(R.dimen.sp_16));
		float[] startpos = calcuTextDrawStartPoint(x, paint2);

		paint2.setColor(Color.WHITE);
		paint2.setAntiAlias(true);

		canvas.drawText(showtext, startpos[0], startpos[1], paint2);

	}

	/**
	 * 计算文本绘制的起始点
	 * 
	 * @param xcoord
	 */
	private float[] calcuTextDrawStartPoint(float xcoord, Paint paint) {
		float[] xy = new float[2];

		float x = 0;
		float y = 0;

		// 获取当前文本的宽度
		float widthcurrtext = paint.measureText(showtext);
		x = xcoord - widthcurrtext / 2;

		// 计算气泡的宽度
		// float widthbubble =
		// height*ratio_bubbleheight_viewheight*ratio_bubblewidth_bubbleheight;
		// x = xcoord-widthbubble/2;

		// 按照文本占气泡的比例，计算文本的宽度
		// float widthbubble2 =
		// height*ratio_bubbleheight_viewheight*ratio_bubblewidth_bubbleheight*ratio_showtext_bubblewidth_width;
		// x = xcoord-widthbubble2/2;

		y = height * ratio_bubbleheight_viewheight
				* ratio_showtext_bubbleheight_y;

		xy[0] = x;
		xy[1] = y;
		return xy;
	}

	/**
	 * 获取一个画笔,保证这个画笔绘制的文本的宽度为传入的width和高
	 * 
	 * @param width
	 * @return
	 */
	private Paint searchPaint(float width, float height, String text) {
		Paint paint = new Paint();

		float searchwidth = searchTextSizeByWidth(width, text);
		float searchheight = searchTextSizeByHeight(height, text);

		float textsize = searchwidth > searchheight ? searchheight
				: searchwidth;

		paint.setTextSize(textsize);

		return paint;
	}

	/**
	 * 获取某些文本的指定宽度的 textsize
	 * 
	 * @param width
	 * @param text
	 * @return
	 */
	private float searchTextSizeByWidth(float width, String text) {
		float textsize = 0;

		Paint paint = new Paint();

		boolean flag = true;

		while (flag) {

			textsize = paint.getTextSize();
			float textwidth = paint.measureText(text);

			// 文本宽度接近想要的宽度的程度
			float abs = Math.abs(textwidth - width);

			if (abs <= 1) {
				flag = false;
			} else {
				if (textwidth > width) {
					textsize -= 0.5;
				} else {
					textsize += 0.5;
				}
			}

			paint.setTextSize(textsize);
		}

		return textsize;
	}

	/**
	 * 获取某些文本的指定的高度的textsize
	 * 
	 * @param height
	 * @param text
	 * @return
	 */
	private float searchTextSizeByHeight(float height, String text) {
		float textsize = 0;

		Paint paint = new Paint();

		boolean flag = true;

		while (flag) {

			textsize = paint.getTextSize();

			FontMetrics fontMetrics = paint.getFontMetrics();
			float textheight = fontMetrics.descent - fontMetrics.ascent;
			// 文本高度接近想要的高度的程度
			float abs = Math.abs(textheight - height);

			if (abs < 0.5) {
				flag = false;
			} else {
				if (textheight > height) {
					textsize -= 1;
				} else {
					textsize += 1;
				}
			}

			paint.setTextSize(textsize);
		}

		return textsize;
	}

	private void drawBubbleText(Canvas canvas) {

	}

	// 获取资源

	private void getRes(int levelsum) {
		switch (levelsum) {
		case three:
			getResCudgel(three);
			break;
		case five:
			getResCudgel(five);
			break;
		case six:
			getResCudgel(six);
			break;
		default:
			break;
		}
		getResBubble(one);
		getResBubble(two);
		getResBubble(three);
		getResBubble(four);
		getResBubble(five);
		getResBubble(six);
	}

	private void getResBubble(int id) {
		switch (id) {
		case one:
			BitmapDrawable drawable1 = (BitmapDrawable) getContext()
					.getResources().getDrawable(R.drawable.bubbleone);
			bubbleone = drawable1.getBitmap();
			break;
		case two:
			BitmapDrawable drawable2 = (BitmapDrawable) getContext()
					.getResources().getDrawable(R.drawable.bubbletwo);
			bubbletwo = drawable2.getBitmap();
			break;
		case three:
			BitmapDrawable drawable3 = (BitmapDrawable) getContext()
					.getResources().getDrawable(R.drawable.bubblethree);
			bubblethree = drawable3.getBitmap();
			break;
		case four:
			BitmapDrawable drawable4 = (BitmapDrawable) getContext()
					.getResources().getDrawable(R.drawable.bubblefour);
			bubblefour = drawable4.getBitmap();
			break;
		case five:
			BitmapDrawable drawable5 = (BitmapDrawable) getContext()
					.getResources().getDrawable(R.drawable.bubblefive);
			bubblefive = drawable5.getBitmap();
			break;
		case six:
			BitmapDrawable drawable6 = (BitmapDrawable) getContext()
					.getResources().getDrawable(R.drawable.bubblesix);
			bubblesix = drawable6.getBitmap();
			break;

		}
	}

	private void getResCudgel(int id) {
		switch (id) {
		case three:
			BitmapDrawable bitmapDrawable3 = null;
			if(isNeutral){
				bitmapDrawable3 = (BitmapDrawable) getContext()
						.getResources().getDrawable(R.drawable.cudgelthree_neutral);
			}else{
				bitmapDrawable3 = (BitmapDrawable) getContext()
						.getResources().getDrawable(R.drawable.cudgelthree);
			}
			cudgelthree = bitmapDrawable3.getBitmap();
			break;
		case five:
			BitmapDrawable bitmapDrawable5 =null;
			if(isNeutral){
				bitmapDrawable5 = (BitmapDrawable) getContext()
						.getResources().getDrawable(R.drawable.cudgelfive_neutral);
			}else{
				bitmapDrawable5 = (BitmapDrawable) getContext()
						.getResources().getDrawable(R.drawable.cudgelfive);
			}
			cudgelfive = bitmapDrawable5.getBitmap();
			break;
		case six:
			BitmapDrawable bitmapDrawable6 = (BitmapDrawable) getContext()
					.getResources().getDrawable(R.drawable.cudgelsix);
			cudgelsix = bitmapDrawable6.getBitmap();
			break;

		default:
			break;
		}
	}

}
