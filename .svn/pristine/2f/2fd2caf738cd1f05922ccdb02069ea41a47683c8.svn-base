package com.capitalbio.oemv2.myapplication.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.Utils.ConstantValues;
import com.capitalbio.oemv2.myapplication.Utils.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Sleephistogram1 extends View {
    /** 原点 x, y<br> 对于系统来讲屏幕的原点在左上角 */
    private final int x = 0, y = 0; // 原点的X坐标

	public String[] Data; // 数据

	/** View显示的宽度 */
	public int width;
	/** View显示的高度 */
	public int height;// 折线图高度

	// 竖线的位置
	private PointF linePoint;

	/** 弹框背景 */
	private Bitmap popBitmap;
	/** 弹出框显示的宽高 */
	private int srcPopWidth, srcPopHeight, popWidth, popHeight;

	// 竖线经过的某个点
	private Point selectPoint;
	/** 弹框的用时字体paint */
	private Paint popupTextPaint = null;
	/** 弹框的时间字体paint */
	private Paint popupTimePaint = null;
	/** 弹框的时间单位字体paint */
    private Paint popupTimeUnitPaint = null;
	/** x轴文字 */
	private Paint xLabelPaint;
	/** 背景画笔 */
	private Paint bgPaint;
	/** 轴画笔 */
	private Paint axisPaint;
	/** 竖线画笔 */
	private Paint linePaint;
	/** 浅睡画笔 */
	private Paint lightSleepPaint;
	/** 深睡画笔 */
	private Paint deepSleepPaint;
	/**清醒画笔**/
	private Paint wakePaint;
	//噪音画笔
	private Paint noisePaint;
	private Paint pressSleepPaint;
	/** 浅睡数据, 默认空 */
	private List<Map<String, String>> lightSleepList = new ArrayList<Map<String,String>>();
	/** 深睡数据, 默认空 */
	private List<Map<String, String>> deepSleepList = new ArrayList<Map<String,String>>();
	/** 清醒数据, 默认空 */
	private List<Map<String, String>> wakeList = new ArrayList<Map<String,String>>();

	/** 弹出框内容 */
	private List<Map<String, String>> popupDataList = new ArrayList<Map<String, String>>();

	private List<Map<String, String>> pointDataList = new ArrayList<>();
	private int index;

	/** 每一分钟所占的宽度 */
    private float minuteWidth;
    /** 计算8条横分割线的间隔 */
    private float minuteWidthHeight;
    float Y1, Y2, Y3;
    private float oldx;// 用于点击判断
    private float movex;// 点移动的位置

	public Sleephistogram1(Context context) {
	    this(context, null);
	}

	public Sleephistogram1(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public Sleephistogram1(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
	    // 初始化画笔和Bitmap
        initPaintAndBitmap();
        Log.d("init","init....");
	}
	
	/**
	 * 初始化宽度,高度
	 * 
	 * @param width View 宽度 = 屏幕宽度
     * @param height View 高度 = 屏幕高度 / 4
	 * void
	 */
	public void init(int width, int height) {
	    this.width = width;
        this.height = height;
        
        // 计算每一个分钟值对应的宽度
        // 一天的分钟数
        int minuteDay = 24 * 60;
        // 每一分钟所占的宽度
        minuteWidth = Utility.div(width, minuteDay);
        
        // 计算9条横分割线的间隔
        minuteWidthHeight = Utility.div(height, (8 - 1));
        
        // 获取X轴标签画笔
        initXLabelPaint(width / 30);
        
	}
	
	/**
     * 初始化画笔和Bitmap
     * 
     * void
     */ 
    private void initPaintAndBitmap() {
        // 初始化弹框背景
        initPopBitmap();
        
        if (axisPaint == null) {
            // 设置坐标画笔
            axisPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            axisPaint.setStyle(Paint.Style.FILL);
            axisPaint.setColor(Color.parseColor(ConstantValues.axisColor));
            // paint.setTextSize(12); // 设置轴文字大小
        }
        
        if (bgPaint == null) {
            // 背景#F2F2F2
            bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            //bgPaint.setStyle(Paint.Style.FILL);
            bgPaint.setAntiAlias(true);// 去锯齿
          //  bgPaint.setColor(Color.parseColor("#F2F2F2"));// 颜色
        }
        
        if (linePaint == null) {
            // 竖线画笔
            linePaint = new Paint();
            linePaint.setStyle(Paint.Style.STROKE);
            linePaint.setAntiAlias(true);// 去锯齿
            linePaint.setColor(Color.GREEN);
            linePaint.setStrokeWidth(5f);
        }
        if (noisePaint == null) {
            // 竖线画笔
            noisePaint = new Paint();
            noisePaint.setStyle(Paint.Style.STROKE);
            noisePaint.setAntiAlias(true);// 去锯齿
            noisePaint.setColor(Color.BLACK);
            noisePaint.setStrokeWidth(5f);
        }
        
        if (lightSleepPaint == null) {
            // 浅睡画笔
            lightSleepPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            lightSleepPaint.setStyle(Paint.Style.FILL);
            lightSleepPaint.setColor(Color.parseColor(ConstantValues.LightSleepColore));//
        }
        
        if (deepSleepPaint == null) {
            // 深睡画笔
            deepSleepPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            deepSleepPaint.setStyle(Paint.Style.FILL);
            deepSleepPaint.setColor(Color.parseColor(ConstantValues.DeepSleepColore));
        }
        
        if (wakePaint == null) {
            // 深睡画笔
        	wakePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        	wakePaint.setStyle(Paint.Style.FILL);
        	wakePaint.setColor(Color.parseColor(ConstantValues.wakeSleepColore));
        }
        
        if (pressSleepPaint == null) {
            // 柱形图选中效果
        	pressSleepPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        	pressSleepPaint.setStyle(Paint.Style.FILL);
        	pressSleepPaint.setColor(Color.parseColor(ConstantValues.PressSleepColore));
        }
        
        if (popupTextPaint == null) {
            popupTextPaint = new Paint();
            // 取消锯齿
            popupTextPaint.setAntiAlias(true);
            // 颜色
            popupTextPaint.setColor(Color.parseColor("#ffffff"));
            popupTextPaint.setTextAlign(Paint.Align.CENTER);
            // 线宽
            // textPaint.setStrokeWidth(3);
            // 文字大小
            popupTextPaint.setTextSize(popHeight / 6);
        }
        
        if (popupTimePaint == null) {
            popupTimePaint = new Paint();
            // 取消锯齿
            popupTimePaint.setAntiAlias(true);
            // 颜色
            popupTimePaint.setColor(Color.parseColor("#ffffff"));
//            popupTimePaint.setTextAlign(Paint.Align.CENTER);
            // 线宽
            // textPaint.setStrokeWidth(3);
            // 文字大小
            popupTimePaint.setTextSize(popHeight / 4);
        }
        
        if (popupTimeUnitPaint == null) {
            popupTimeUnitPaint = new Paint();
            // 取消锯齿
            popupTimeUnitPaint.setAntiAlias(true);
            // 颜色
            popupTimeUnitPaint.setColor(Color.parseColor("#ffffff"));
//            popupTimeUnitPaint.setTextAlign(Paint.Align.CENTER);
            // 文字大小
            popupTimeUnitPaint.setTextSize(popHeight / 7);
        }
        
        // 获取X轴标签画笔
        initXLabelPaint(width / 30);
        
        // 计算 弹出框显示的宽高
        calPopWH(srcPopWidth, srcPopHeight);
    }

    /**
     * 弹框背景
     * 
     * void
     */
    public void initPopBitmap() {
        if (popBitmap == null || popBitmap.isRecycled()) {
            popBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.backgreen);
            
            // 弹出框的宽高
            srcPopWidth =  popWidth = popBitmap.getWidth();
            srcPopHeight = popHeight = popBitmap.getHeight();
            
        }
    }
    
    /**
     * 获取X轴标签画笔
     * 
     * void
     */
    private void initXLabelPaint(int textSize) {
        
        // 绘制轴线文字
        if (xLabelPaint == null) {
            xLabelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            xLabelPaint.setStyle(Paint.Style.FILL);
            xLabelPaint.setColor(Color.BLACK);
            xLabelPaint.setTextSize(textSize); // 设置轴文字大小
        }else {
            xLabelPaint.setTextSize(textSize); // 设置轴文字大小
        }
    }
    
    /**
     * 计算弹出框显示的宽高
     * 
     * @param width
     *
     * void
     */
    private void calPopWH(int width, int height) {
        // 计算Popup显示的宽高
        // 获取 弹出框中文字的高度
        
        float textWidth = Utility.getTextWidth(popupTextPaint, "深睡眠时长");
        float timeWidth = Utility.getTextWidth(popupTimePaint, "0000");
        float timeUnitWidth = Utility.getTextWidth(popupTimeUnitPaint, "hmin");
        
        
        float maxWidth = (textWidth > (timeUnitWidth + timeWidth) ? textWidth : (timeUnitWidth + timeWidth)) 
                + ConstantValues.BarSpace * 3 * 2;
        float textHeight = popupTextPaint.getTextSize() + popupTimePaint.getTextSize() + ConstantValues.BarSpace * 3 * 2;
        
        this.popWidth = (int) (srcPopWidth > maxWidth ? maxWidth : srcPopWidth);
        this.popHeight = (int) (srcPopHeight > textHeight ? textHeight : srcPopHeight);
    }

    /**
	 * 设置睡眠数据
	 * 
	 * @param deepSleepData 深睡数据
	 * @param lightSleepData 浅睡数据
	 * void
	 */
	public void setSleepData(List<Map<String, String>> deepSleepData, List<Map<String, String>> lightSleepData,List<Map<String, String>> wakeList,List<Map<String, String>> noiseDataList) {
	    // 深睡数据
		this.deepSleepList.clear();
		this.deepSleepList.addAll(deepSleepData);
		
		// 浅睡数据
		this.lightSleepList.clear();
		this.lightSleepList.addAll(lightSleepData);
		
		// 浅睡数据
	    this.wakeList.clear();
		this.wakeList.addAll(wakeList);
		//NOISE
		this.pointDataList.clear();
		this.pointDataList.addAll(noiseDataList);
			
		
		// 清除弹框内容
		this.popupDataList.clear();
		
		// 刷新界面
		postInvalidate();
	}

	// 重写绘画方法
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		 Log.d("draw","draw....");
		initPaintAndBitmap();
		
		// 绘制背景
		drawBg(canvas, axisPaint, bgPaint);
		
		// 绘制睡眠柱状图
		drawSleepBar(canvas, lightSleepPaint, deepSleepPaint,wakePaint);
		
		// 绘制竖线
		if (null != linePoint && popupDataList.size() > 0) {
			canvas.drawLine(linePoint.x, 0, linePoint.x, height, linePaint);
	
		}
	   drawLine(canvas,noisePaint);
	}
		

    private void drawLine(Canvas canvas, Paint noisePaint2) {
    	for (int i = 0; i < pointDataList.size()-1; i++) {
    		
    		
    		     float startx = (float) computePointX(i).get("x");
    	       
    		     float starty = (float) computePointX(i).get("y");
    		  
    		     float tox = (float) computePointX(i+1).get("x");
      	       
    		     float toy = (float) computePointX(i+1).get("y");
    		  
    	
		        canvas.drawLine(startx, starty,	tox, toy, noisePaint2);
    	}
    	
	}

	private HashMap<String, Object> computePointX(int i) {
		final long startTimeL = Long.parseLong(pointDataList.get(i).get("time"));
		final long value = Long.parseLong(pointDataList.get(i).get("value")); // 时长
		
		// 起始时间 时分秒
		final String startHms = Utility.getHMS(startTimeL);
		final int startHour = Utility.getHour(startHms);
		
		// 计算起始的分钟(Rect的起点)
		final int minute = Utility.getMinute(startHms) + startHour * 60;

		final float startRect = minute * minuteWidth;
	/*	int maxh=Integer.parseInt(pointDataList.get(0).get("value"));
		for(int j= 0;j<pointDataList.size();j++){
			int value1 =Integer.parseInt(pointDataList.get(i).get("value"));
			maxh = maxh>value1?maxh:value1;
		}*/

		 float Height = Utility.div(height* value, 200);
		 HashMap<String, Object> map =new HashMap<>();
		 map.put("x",startRect );
		 map.put("y",height-Height );
		
		return map;
	}

	/**
     * 绘制背景
     * 
     * @param canvas
     * @param axisPaint
     * @param bgPaint 
     * void
     */
    public void drawBg(Canvas canvas, Paint axisPaint, Paint bgPaint) {
        // 每一小时所占的宽度
		final float hourWidth = minuteWidth * 60;
		
		// 计算文字绘制的位置
        final float textSize = xLabelPaint.getTextSize();
        final float textY = textSize + height;
		
		 final float startY = minuteWidthHeight * 7;
         
         // 绘制间隔横线
         canvas.drawLine(x, startY, 
                             width, startY, 
                             axisPaint);
         float textWidth = Utility.getTextWidth(xLabelPaint, "10:00");
         Bitmap bitmap_moon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_moon);
         Matrix matrix =new Matrix();
    /*     Log.d("div1", bitmap_moon.getHeight()+"");
         Log.d("div2",textSize+"");*/
         float sx = Utility.div(textSize,bitmap_moon.getHeight(),2);
         matrix.setScale(sx, sx);
         matrix.postTranslate(x, startY+textSize/4);
         canvas.drawBitmap(bitmap_moon, matrix ,bgPaint);
         
         Bitmap bitmap_sun = BitmapFactory.decodeResource(getResources(), R.drawable.ic_sun);
        Matrix matrix_ =new Matrix();
         
         matrix_.setScale(sx, sx);
         matrix_.postTranslate(width-x-textWidth*2-textWidth/2, startY+textSize/4);
         canvas.drawBitmap(bitmap_sun,matrix_,  bgPaint);
         canvas.drawText("0:00",
                 x + textWidth/2, textY, 
                 xLabelPaint); // 文字
         Log.d("end",width+"");
         canvas.drawText("23:00",
                 width-x-textWidth*2, textY, 
                 xLabelPaint); // 文字
    }
    
    /**
     * 绘制睡眠柱状图
     * 
     * @param canvas
     * @param lightSleepPaint
     * @param deepSleepPaint
     * void
     */
    public void drawSleepBar(Canvas canvas, Paint lightSleepPaint,
            Paint deepSleepPaint,Paint wakePaint) {
        // 浅度睡眠柱状图的高度
        float lightBarHeight = Utility.div(height, 2);
        // 显示文字
        final String lightShowType = "浅睡眠时长";
        
        // 绘制浅度睡眠数据
        if (null != lightSleepList && lightSleepList.size() > 0) {
            
            final int listdataSize = lightSleepList.size();
            for (int i = 0; i < listdataSize; i++) {
                
                final Map<String, String> hashMap = lightSleepList.get(i);
                
                
                // 绘制睡眠柱状图
                drawSleepBarAndAddPopupData(canvas, lightSleepPaint, pressSleepPaint,hashMap, 
                        lightShowType, lightBarHeight);
            }
        }
        
        // 深度睡眠柱状图的高度
        float deepBarHeight = Utility.div(height, 6);
        // 显示文字
        final String deepShowType = "深睡眠时长";
        
        // 绘制深度睡眠数据
        if (null != deepSleepList && deepSleepList.size() > 0) { 
            
            final int listdata2Size = deepSleepList.size();
            for (int i = 0; i < listdata2Size; i++) {
                
                final Map<String, String> hashMap = deepSleepList.get(i);
                
                drawSleepBarAndAddPopupData(canvas, deepSleepPaint, pressSleepPaint,hashMap, 
                        deepShowType, deepBarHeight);
            }
        }
        
        
        
     // 清醒睡眠柱状图的高度
        float wakeBarHeight = Utility.div(height, 2);
        // 显示文字
        final String wakeShowType = "清醒睡眠时长";
        
        // 绘制浅度睡眠数据
        if (null != wakeList && wakeList.size() > 0) {
            
            final int listdataSize = wakeList.size();
            for (int i = 0; i < listdataSize; i++) {
                
                final Map<String, String> hashMap = wakeList.get(i);
                
                Log.d("dddddd", "weak called" + " color is " + wakePaint.getColor());
                // 绘制睡眠柱状图
                drawSleepBarAndAddPopupData(canvas, wakePaint, pressSleepPaint,hashMap, 
                		wakeShowType, wakeBarHeight);
            }
        }
        
    }

	/**
	 * 绘制睡眠柱状图和添加弹出框显示数据
	 * 
	 * @param canvas
	 * @param paint
	 * @param hashMap
	 * @param showType
	 * @param barHeight
	 * void
	 */
    public void drawSleepBarAndAddPopupData(Canvas canvas, Paint paint,Paint paintpress,
            final Map<String, String> hashMap, final String showType,
            final float barHeight) {
        // 起始时间  2015-05-05 23:35 
        final long startTimeL = Long.parseLong(hashMap.get("time"));
        final long timeWidthL = Long.parseLong(hashMap.get("widthtime")); // 时长
        
        final String startHM=Utility.getHM(startTimeL);
        final String endHM=Utility.getHM(startTimeL+timeWidthL);
        
        // 起始时间 时分秒
        final String startHms = Utility.getHMS(startTimeL);
//        final String startHms = Utility.getYMDHMS(startTimeL);
        final int startHour = Utility.getHour(startHms);
        
        // 计算起始的分钟(Rect的起点)
        final int minute = Utility.getMinute(startHms) + startHour * 60;

        // 计算睡眠时长(分钟)，作为Rect的宽度
        // 毫秒转秒
        final float timeWidthF = Utility.div(timeWidthL, 1000);
        // 秒转分钟
        final float timeWidthMinute = Utility.div(timeWidthF, 60);
        
        // Rect起始位置
        final float startRect = minute * minuteWidth;
        // Rect结束位置
        final float endRect = startRect + timeWidthMinute * minuteWidth;
        
        // 绘制浅度睡眠柱状图
        RectF rect = new RectF(startRect, barHeight, endRect, height);
        if(linePoint!=null&&rect.contains(linePoint.x, linePoint.y)){
        
        	 canvas.drawRect(rect, paintpress);
        
        }else{
        	 canvas.drawRect(rect, paint);
        	 Log.d("ddddd", "color is " + paint.getColor());
        }
       
     
        
        // 计算睡眠时长 - 小时
        final int hour = (int) (timeWidthMinute / 60);
        // 计算睡眠时长 - 分钟
        final int min = (int) (timeWidthMinute % 60);
        
        // 添加浅度睡眠弹出框数据
        Map<String, String> map = new HashMap<String, String>();
        
        // starttime, endtime 用来计算判断手指触摸的位置
        map.put("starttime", String.valueOf(startRect));
        map.put("endtime", String.valueOf(endRect));
        
        
        map.put("startHM",startHM);
        map.put("endHM",endHM);
        // type，hour，min 是 用来显示的睡眠时长，及对应文字
        map.put("type", showType);
        map.put("hour", getFormatTimeStr(hour));
        map.put("min",  getFormatTimeStr(min));
        
        // 睡眠起始日期时分秒
        map.put("day", startHms);
        map.put("showtime", startHms);
        
        popupDataList.add(map);
    }


	/**
	 * 设置竖线显示的坐标点
	 * 
	 * @param touchPoint
	 */
	private void setTouchPoint(PointF touchPoint) {
		this.linePoint = touchPoint;
	}

	/**
     * 在重新测量大小的时候计算view显示的大小
     * 
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     * @see View#onMeasure(int, int)
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 测量宽度设置
        int width = MeasureSpec.getSize(Sleephistogram1.this.width);
        // 测量高度设置
        int height = MeasureSpec.getSize((int)(Sleephistogram1.this.height + width / 20));

        setMeasuredDimension(width, height); // 这里面是原始的大小，需要重新计算可以修改本行
    }
	
    /**
     * 取出格式化为 00 的时间值
     * 
     * void
     */
    private String getFormatTimeStr(int time) {
        return time < 10 ? ("0" + time) : String.valueOf(time);
    }
    
	/**
	 * 获取矩形区域
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 */
	private Rect makeVKFrame(int x, int y, int width, int height) {
		return new Rect(x, y, x + width, y + height);
	}

    // 在move中获取的point；
    private PointF movePoint = new PointF();
    /** 手势模式 */
    private int touchMode;
    /** 无操作 */
    private final static int NONE = 0;// 无滑动
    /** 缩放  */
//    private final static int ZOOM = 1; // 缩放
    /** 按下 */
    private final static int DOWN = 2; // 按下
    /** 长按 */
    private final static int LONGPRESS = 3; // 长按

	@SuppressLint("ClickableViewAccessibility")
    @Override
	public boolean onTouchEvent(MotionEvent event) {
		// float oldy;
		switch (event.getAction() & MotionEvent.ACTION_MASK) {

    		case MotionEvent.ACTION_DOWN: {

    		    // 触摸的起始位置
    		    final PointF startPoint = new PointF();
    		    startPoint.set(event.getX(), event.getY());
                touchMode = DOWN;
             // 设置竖线显示的坐标点
                setTouchPoint(startPoint);
                
                invalidate();
                selectPoint = findMovePoint((int) event.getX(),
                        (int) event.getY());
                touchMode = LONGPRESS;
                if(selectPoint!=null){
                	triggerClick(selectPoint.x-20,((View)this.getParent().getParent()).getTop()+selectPoint.y, popupDataList.get(index));
                }
                
                break;
    		}
    		case MotionEvent.ACTION_UP: {
    		    touchMode = NONE;
                setTouchPoint(null);
                selectPoint = null;
                
                invalidate();
    			if(null!=pwSee){
    				pwSee.dismiss();
    				pwSee=null;
    			}
                break;
    
    		}
    		case MotionEvent.ACTION_MOVE: {
    		    movePoint.set(event.getX(), event.getY());
    		    
                if (touchMode == LONGPRESS) {
                    // 竖线产生的位置，移动竖线
                    setTouchPoint(movePoint);
                    invalidate();
                    selectPoint = findMovePoint((int) event.getX(),
                            (int) event.getY());
                    
                    if(selectPoint!=null){
//                    	if(pwSee!=null){
//                    		pwSee.dismiss();
//                    		pwSee=null;
//                    	}
                    	triggerClick(selectPoint.x-20,((View)this.getParent().getParent()).getTop()+selectPoint.y, popupDataList.get(index));
                    }else{
                    	if(pwSee!=null){
                    		pwSee.dismiss();
                    	}
                    }
                }

                break;

    		}
    		case MotionEvent.ACTION_CANCEL:
    		    touchMode = NONE;
                setTouchPoint(null);
                selectPoint = null;
                if(null!=pwSee){
    				pwSee.dismiss();
    				pwSee=null;
    			}
                postInvalidate();
                
    			break;

		}
		return true;
	}
	
	private PopupWindow pwSee = null;
	private TextView tv_smsc_num,tv_smscvalue_num,tv_smsjvalue_num;
	private void triggerClick(float x, float y,
			Map<String, String> map) {
		Log.i("坐标", x + "," + y);
		// Toast.makeText(getContext(), "我弹", 0).show();
		final String hour = map.get("hour");
		final String min = map.get("min");
		if (pwSee == null) {
			pwSee = new PopupWindow();
			/**
			 * 加载popupWindow的布局文件
			 */
			View mView = View.inflate(getContext(), R.layout.pop_sleep,
					null);
			tv_smsc_num = (TextView) mView.findViewById(R.id.tv_smsc_num);
			tv_smscvalue_num = (TextView) mView.findViewById(R.id.tv_smscvalue_num);
			tv_smsjvalue_num = (TextView) mView.findViewById(R.id.tv_smsjvalue_num);
	
			/**
			 * 设置它的ContentView
			 */
			pwSee.setContentView(mView);
	
		} 
		
		tv_smsc_num.setText(map.get("type"));
		SpannableString msp =  new SpannableString(hour+"h"+min+"min");   
		msp.setSpan(new AbsoluteSizeSpan(20,true),0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  
		msp.setSpan(new AbsoluteSizeSpan(20,true),3, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //第二个参数boolean dip，如果为true，表示前面的字体大小单位为dip，否则为像素，同上。  
		
		tv_smscvalue_num.setText(msp);
		
		tv_smsjvalue_num.setText(map.get("startHM")+"—"+map.get("endHM"));
		/**
		 * 显示
		 */
		popupWindowSet(pwSee, this, x, y);
        Log.i("popheight",y+"");
		
//		tv_time.setText(bloodPressMeasureBean.getSynchroTime());
	}

	/**
	 * popupWindow设置
	 * 
	 * @param pPopupWindow
	 * @param view
	 * @param
	 */
	public void popupWindowSet(PopupWindow pPopupWindow, View view, float x,
			float y) {
		// int mwidth = (int) Math.round(Utility.getsW(this) * (0.8));
		// int mhidth = (int) Math.round(Utility.getsW(mContext) * (0.7));
		pPopupWindow.setWidth(Utility.dip2px(getContext(), 100f));
		pPopupWindow.setHeight(Utility.dip2px(getContext(), 120f));
		pPopupWindow.setFocusable(true); // 设置PopupWindow可获得焦点
		pPopupWindow.setTouchable(true); // 设置PopupWindow可触摸
		pPopupWindow.setOutsideTouchable(true); // 设置非PopupWindow区域可触摸点击消失
		pPopupWindow.setAnimationStyle(android.R.anim.fade_in);
		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
		pPopupWindow.setBackgroundDrawable(new BitmapDrawable());
		// 下面两项主要是解决软键盘遮挡问题
		pPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
		pPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

		// pPopupWindow.showAtLocation(view, Gravity.NO_GRAVITY,
		// (map.get("x").intValue()), map.get("y").intValue());
		pPopupWindow.showAtLocation(view, Gravity.NO_GRAVITY,
				(int) (x - Utility.dip2px(getContext(), 40f)),
				(int) (y +2 * Utility.dip2px(getContext(), 120f)));
	}


	/**
	 * 判断滑动过程中是否经过某点
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private Point findMovePoint(int x, int y) {

		final int size = popupDataList.size();
        for (int i = 0; i < size; i++) {
            final Map<String, String> hashMap = popupDataList.get(i);
            String startTimeStr = hashMap.get("starttime");
            String endTimeStr = hashMap.get("endtime");
            
            float startTime = Float.parseFloat(startTimeStr);
            float endTime = Float.parseFloat(endTimeStr);
            
            if (x >= startTime && x <= endTime) {
                
				index = i;
				
				Point dot = new Point(x, y);
				return dot;
			}
		}
		
		return null;
	}



}
