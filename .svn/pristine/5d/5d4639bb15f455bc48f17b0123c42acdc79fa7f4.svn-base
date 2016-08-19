package com.capitalbio.oemv2.myapplication.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.View;

import com.capitalbio.oemv2.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author wxy
 *
 */
@SuppressLint("ClickableViewAccessibility")
public  class CustomedCharViewPBM extends View  {

	Paint paint, textPaint ,paint1;

	private String[] datayleft ;// 设置左边数据 升序
	
	private HashMap<String, Object> dataright;// 设置右边数据，包括右边文字，范围背景色
	private int OFFSET_LEFT = 50;
	private int OFFSET_LEFT_min= 100;//固定y轴
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
	
	//private List<MyPoint> pbmpoints = new ArrayList<MyPoint>();
	List<LevalInfo> rightTitleInfos= new ArrayList<LevalInfo>();
	private int[] values ;
	private List<Point> points = new ArrayList<Point>();
	private static final int FLING_MIN_DISTANCE = 50;
	private static final int FLING_MIN_VELOCITY = 0;
	GestureDetector mGesture = null;

	public static int y_size = 10 ;//表示y轴数字的个数；
	public static int x_size = 10;//表示x轴上一屏显示的个数
	public int maxy;//y轴上的最大刻度
	boolean ismove = false;
	Path path;Paint bgpaint;
	public CustomedCharViewPBM(Context context) {
		
		super(context);
		
		//mGesture = new GestureDetector(this);
	}

	public CustomedCharViewPBM(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		//mGesture = new GestureDetector(this);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		//设置y 轴左边
				
		init();
		
		drawTable(canvas);

	}

      
	public  void init(){
		height = getWidth();
		width = 3 * (getWidth()-OFFSET_LEFT_min-OFFSET_RIGHT);
		widthVisible = getWidth()-OFFSET_LEFT_min-OFFSET_RIGHT;


		// 设置文字画笔
		textPaint = new Paint();
		textPaint.setColor(Color.BLACK);
		textPaint.setDither(true);
		textPaint.setStyle(Paint.Style.STROKE);
		textPaint.setTextSize(20);

		// 画背景颜色 动态 放前面否则遮住虚线
	    bgpaint = new Paint();

		maxy = 150;

	
	}

	public void setDataright(HashMap<String, Object> dataright) {
		this.dataright = dataright;
	}

	public void drawTable(Canvas canvas) {
		
		setRigthTitle(null);
		drawBackGround(canvas);
		
	/*
		for (int i = 1; i < y_size; i++) {
			path.moveTo(OFFSET_LEFT_min, OFFSET_TOP + height / y_size * i);
			path.lineTo(OFFSET_LEFT_min + widthVisible,
					OFFSET_TOP + height / y_size * i);
			canvas.drawPath(path, paint1);

		}
*/
		
	
		// 画右边文字


		// 画右边文字
		Path textPath = new Path();
		textPaint.setStyle(Paint.Style.FILL);
		textPaint.setColor(Color.BLACK);
		textPaint.setTextSize(40);
		textPaint.setAntiAlias(true);
		for (int i = 0; i < rightTitleInfos.size();i++) {
			textPath.moveTo(OFFSET_LEFT_min + widthVisible + 20,
					(float) (OFFSET_TOP + height * i / y_size));
			textPath.lineTo(OFFSET_LEFT_min + widthVisible + 20, OFFSET_TOP
					+ height * (i + 1) / y_size);
			canvas.drawTextOnPath(rightTitleInfos.get(i).getText(), textPath, 30, 0, textPaint);
			textPath.reset();
		}

		
		

	}

	public void drawBackGround(Canvas canvas) {
		if(rightTitleInfos.size()==0){
			return;
		}
		for(int i = 0;i<rightTitleInfos.size();i++){
			bgpaint.setColor(rightTitleInfos.get(i).getColorbg());
			canvas.drawRect(OFFSET_LEFT_min, (float) ((height
					* (maxy - rightTitleInfos.get(i).getMax()) / maxy)), OFFSET_LEFT_min + widthVisible, (float) ((height * (maxy - rightTitleInfos.get(i).getMin()) / maxy)), bgpaint);

		}

	}
	
	public void setRigthTitle(List<LevalInfo> infos){
		//rightTitleInfos = infos;//数据应为升序
			LevalInfo info1 = new LevalInfo();
			info1.setColorbg(getResources().getColor(R.color.bg_pmb_low));
			info1.setMin(0);
			info1.setMax(50);
		info1.setText("偏低");
			LevalInfo info2 = new LevalInfo();
			info2.setColorbg(getResources().getColor(R.color.bg_pmb_nomal));
			info2.setMin(50);
			info2.setMax(80);
		info2.setText("偏中");

		LevalInfo info3 = new LevalInfo();
			info3.setColorbg(getResources().getColor(R.color.bg_pmb_high));
			info3.setMin(80);
			info3.setMax(150);
		info3.setText("偏高");

		rightTitleInfos.add(info1);
			rightTitleInfos.add(info2);
			rightTitleInfos.add(info3);
	}


}
