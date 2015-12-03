package com.capitalbio.oemv2.myapplication.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.capitalbio.oemv2.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日历控件 功能：获得点选的日期区间
 *
 */
public class CalendarView extends View implements
		View.OnTouchListener {
	private final static String TAG = "anCalendar";
	private Date selectedStartDate;
	private Date selectedEndDate;
	private Date curDate; // 当前日历显示的月
	private Date today; // 今天的日期文字显示红色
	private Date downDate; // 手指按下状态时临时日期
	public Date getDownDate() {
		return downDate;
	}

	public void setDownDate(Date downDate) {
		this.downDate = downDate;
	}

	private Date showFirstDate, showLastDate; // 日历显示的第一个日期和最后一个日期
	private int downIndex; // 按下的格子索引
	private Calendar calendar;
	private Surface surface;
	private int[] date = new int[42]; // 日历显示数字
	private int curStartIndex, curEndIndex; // 当前显示的日历起始的索引
	private boolean completed = false; // 为false表示只选择了开始日期，true表示结束日期也选择了
	private boolean isSelectMore = false;
	private Context context;
	// 给控件设置监听事件
	private OnItemClickListener onItemClickListener;
	private List<String> timeList = new ArrayList<String>() ;
	private List<String> monthList = new ArrayList<String>();// 月份集合
	private List<String> dayList = new ArrayList<String>();// 日期结合
	private String currentShowMonth;
	public  int firstday;
	public  int lastday;
	public CalendarView(Context context, List<String> timeList) {
		super(context);
		context =context;
		this.timeList = timeList;// [2015-02-03 15:14:16, 2015-02-03
		// 17:23:28]
		init();
	}

	public CalendarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		context =context;
		init();
	}

	/**
	 *
	 * 刷新页面
	 * */
	public void setvalue(List<String> timeList) {
		this.timeList = timeList;
		// init();
		monthList.clear();
		dayList.clear();
		for (int i = 0; i < timeList.size(); i++) {
			String[] ymdArr = timeList.get(i).split("-");
			// String[] ymdArr = split[0].split("-");
			String month = ymdArr[1];
			monthList.add(month);
			String day = ymdArr[2];
			dayList.add(day);

		}

		postInvalidate();
	}

	private void init() {

		 timeList.add("2015-11-05");
		 timeList.add("2015-11-07");
		 for (int i = 0; i < timeList.size(); i++)
		 {String str = timeList.get(i);
		 if (str.contains("-"))
		 { String[] split = str.split("-");
		String str0 = split[0];
		int parseInt = Integer.parseInt(str0);
		 if ("".equals(str) || parseInt != 2014 || parseInt != 2015
		 || parseInt != 2016) {
		 //Toast.makeText(context, "传入的时间格式有误", Toast.LENGTH_SHORT).show();
		 return;
		 }
		 } else {
		// Toast.makeText(context, "传入的时间格式有误", Toast.LENGTH_SHORT).show();
		 return;
		 }

		}
		monthList.clear();
		dayList.clear();
		for (int i = 0; i < timeList.size(); i++) {
			String[] ymdArr = timeList.get(i).split("-");
			// String[] ymdArr = split[0].split("-");
			String month = ymdArr[1];
			monthList.add(month);
			String day = ymdArr[2];
			dayList.add(day);

		}
		// Date date= new Date("2015-05-08  11:33:00");
		setCurDate(selectedStartDate = selectedEndDate = today = new Date());

		calendar = Calendar.getInstance();
		calendar.setTime(getCurDate());
		surface = new Surface();
		surface.density = getResources().getDisplayMetrics().density;
		setBackgroundColor(surface.bgColor);
		//setOnTouchListener(this);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		surface.width = getResources().getDisplayMetrics().widthPixels - 30;
		surface.height = (int) (getResources().getDisplayMetrics().heightPixels * 1 / 3);
		widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(surface.width,
				View.MeasureSpec.EXACTLY);
		heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(surface.height,
				View.MeasureSpec.EXACTLY);
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
							int bottom) {
		Log.d(TAG, "[onLayout] changed:"
				+ (changed ? "new size" : "not change") + " left:" + left
				+ " top:" + top + " right:" + right + " bottom:" + bottom);
		if (changed) {
			surface.init();
		}
		super.onLayout(changed, left, top, right, bottom);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Log.d(TAG, "onDraw");

		if (canvas == null || surface == null) {
			return;
		}

		try {
			// 画星期
			float weekTextY = surface.monthHeight + surface.weekHeight * 3 / 4f;
			for (int i = 0; i < surface.weekText.length; i++) {
				float weekTextX = i
						* surface.cellWidth
						+ (surface.cellWidth - surface.weekPaint
						.measureText(surface.weekText[i])) / 2f;
				canvas.drawText(surface.weekText[i], weekTextX, weekTextY,
						surface.weekPaint);
			}

			// 计算日期
			calculateDate();
			// 设置本月数据点
			// 获取当前显示的月份
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			String curDateformat = sf.format(getCurDate());
			String[] cfArr = curDateformat.split("-");
			currentShowMonth = cfArr[1];
			// 按下状态，选择状态背景色
			drawDownOrSelectedBg(canvas);
			for (int i = 0; i < dayList.size(); i++) {
				if (currentShowMonth.equals(monthList.get(i))) {
					int color = surface.textColor;
					String pointDay = dayList.get(i);
					// 获得有数据的日期
					int pointDayNum = Integer.parseInt(pointDay);
					int pointIndex = curStartIndex + pointDayNum - 1;
					drawCellPoint(canvas, pointIndex, color);
				}
			}
			// 设置显示上个月数据点
			String lastcurDateformat = sf.format(showFirstDate);
			String[] lcfArr = lastcurDateformat.split("-");
			// 获取上个月的月份
			String lastMonthStartDayMonth = lcfArr[1];
			// 获取本月 开始的日期数字
			int lastMonthStartDayNumber = Integer.parseInt(lcfArr[2]);
			for (int i = 0; i < dayList.size(); i++) {
				if (Integer.parseInt(dayList.get(i)) > lastMonthStartDayNumber
						&& monthList.get(i).equals(lastMonthStartDayMonth)) {
					int color = surface.textColor;
					String pointDay = dayList.get(i);
					// 获得有数据的日期
					int pointDayNum = Integer.parseInt(pointDay);
					int pointIndex = pointDayNum - lastMonthStartDayNumber;
					drawCellPoint(canvas, pointIndex, color);
				}
			}
			// 设置下个月数据点
			String nextcurDateformat = sf.format(showLastDate);
			String[] ncfArr = nextcurDateformat.split("-");
			// 获取下个月的月份
			String nextMonthEndDayMonth = ncfArr[1];
			// 获取本月 结束的日期数字
			int nextMonthEndDayNumber = Integer.parseInt(ncfArr[2]);
			for (int i = 0; i < dayList.size(); i++) {
				if (Integer.parseInt(dayList.get(i)) < nextMonthEndDayNumber
						&& monthList.get(i).equals(nextMonthEndDayMonth)) {
					int color = surface.textColor;
					String pointDay = dayList.get(i);
					// 获得有数据的日期
					int pointDayNum = Integer.parseInt(pointDay);
					int pointIndex = 41 - (nextMonthEndDayNumber - pointDayNum);
					drawCellPoint(canvas, pointIndex, color);
				}
			}

			// write date number
			// today index
			int todayIndex = -1;
			calendar.setTime(getCurDate());
			String curYearAndMonth = calendar.get(Calendar.YEAR) + ""
					+ calendar.get(Calendar.MONTH);
			calendar.setTime(today);
			String todayYearAndMonth = calendar.get(Calendar.YEAR) + ""
					+ calendar.get(Calendar.MONTH);
			if (curYearAndMonth.equals(todayYearAndMonth)) {
				int todayNumber = calendar.get(Calendar.DAY_OF_MONTH);
				todayIndex = curStartIndex + todayNumber - 1;
			}
			for (int i = 0; i < 42; i++) {
				int color = surface.textColor;
				if (isLastMonth(i)) {
					color = surface.borderColor;
				} else if (isNextMonth(i)) {
					color = surface.borderColor;
				}
				if (todayIndex != -1 && i == todayIndex) {
					color = surface.todayNumberColor;
				}
				drawCellText(canvas, i, date[i] + "", color);
			}
			super.onDraw(canvas);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void calculateDate() {
		calendar.setTime(getCurDate());
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int dayInWeek = calendar.get(Calendar.DAY_OF_WEEK);
		Log.d(TAG, "day in week:" + dayInWeek);
		int monthStart = dayInWeek;
		if (monthStart == 1) {
			monthStart = 8;
		}
		monthStart -= 2; // 以日为开头-1，以星期一为开头-2
		curStartIndex = monthStart;
		date[monthStart] = 1;
		// last month
		if (monthStart > 0) {
			calendar.set(Calendar.DAY_OF_MONTH, 0);
			int dayInmonth = calendar.get(Calendar.DAY_OF_MONTH);
			for (int i = monthStart - 1; i >= 0; i--) {
				date[i] = dayInmonth;
				dayInmonth--;
			}
			calendar.set(Calendar.DAY_OF_MONTH, date[0]);
		}
		showFirstDate = calendar.getTime();
		// this month
		calendar.setTime(getCurDate());
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 0);
		// Log.d(TAG, "m:" + calendar.get(Calendar.MONTH) + " d:" +
		// calendar.get(Calendar.DAY_OF_MONTH));
		int monthDay = calendar.get(Calendar.DAY_OF_MONTH);
		for (int i = 1; i < monthDay; i++) {
			date[monthStart + i] = i + 1;
		}
		curEndIndex = monthStart + monthDay;
		// next month
		for (int i = monthStart + monthDay; i < 42; i++) {
			date[i] = i - (monthStart + monthDay) + 1;
		}
		if (curEndIndex < 42) {
			// 显示了下一月的
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		calendar.set(Calendar.DAY_OF_MONTH, date[41]);
		showLastDate = calendar.getTime();
	}

	/**
	 * 画数据点
	 *
	 * @param canvas
	 * @param index
	 * @param color
	 */
	private void drawCellPoint(Canvas canvas, int index, int color) {
		int x = getXByIndex(index);
		int y = getYByIndex(index);
		surface.datePaint.setColor(color);
		float cellXPoint = (surface.cellWidth * (x - 1)) + surface.cellWidth
				/ 2f - 4;

		float cellYPoint = surface.monthHeight + surface.weekHeight + (y - 1)
				* surface.cellHeight + surface.cellHeight * 3 / 4 + 2;
		Bitmap pBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.calendar_data_point);
		canvas.drawBitmap(pBitmap, cellXPoint, cellYPoint, surface.datePaint);
	}

	/**
	 * 画日期文字
	 *
	 * @param canvas
	 * @param index
	 * @param text
	 */
	private void drawCellText(Canvas canvas, int index, String text, int color) {
		int x = getXByIndex(index);
		int y = getYByIndex(index);
		surface.datePaint.setColor(color);
		float cellY = surface.monthHeight + surface.weekHeight + (y - 1)
				* surface.cellHeight + surface.cellHeight * 3 / 4f - 2;
		float cellX = (surface.cellWidth * (x - 1))
				+ (surface.cellWidth - surface.datePaint.measureText(text))
				/ 2f;

		canvas.drawText(text, cellX, cellY, surface.datePaint);

	}

	/**
	 *
	 * @param canvas
	 * @param index
	 * @param color
	 */
	private void drawCellBg(Canvas canvas, int index, int color) {
		int x = getXByIndex(index);
		int y = getYByIndex(index);
		surface.cellBgPaint.setColor(color);
		float left = surface.cellWidth * (x - 1) + surface.borderWidth;
		float top = surface.monthHeight + surface.weekHeight + (y - 1)
				* surface.cellHeight + surface.borderWidth;
		canvas.drawCircle(left + surface.cellWidth / 2-2, top
						+ surface.cellHeight / 2+3, surface.cellHeight / 2,
				surface.cellBgPaint);

	}

	private void drawDownOrSelectedBg(Canvas canvas) {
		// down and not up
		// if (downDate != null) {
		// drawCellBg(canvas, downIndex, surface.cellDownColor);
		// }
		// selected bg color
		if (!selectedEndDate.before(showFirstDate)
				&& !selectedStartDate.after(showLastDate)) {
			int[] section = new int[] { -1, -1 };
			calendar.setTime(getCurDate());
			calendar.add(Calendar.MONTH, -1);
			findSelectedIndex(0, curStartIndex, calendar, section);
			if (section[1] == -1) {
				calendar.setTime(getCurDate());
				findSelectedIndex(curStartIndex, curEndIndex, calendar, section);
			}
			if (section[1] == -1) {
				calendar.setTime(getCurDate());
				calendar.add(Calendar.MONTH, 1);
				findSelectedIndex(curEndIndex, 42, calendar, section);
			}
			if (section[0] == -1) {
				section[0] = 0;
			}
			if (section[1] == -1) {
				section[1] = 41;
			}
			for (int i = section[0]; i <= section[1]; i++) {
				drawCellBg(canvas, i, surface.cellSelectedColor);
				// drawCellText(canvas, i, date[i] + "",
				// surface.todayNumberColor);
				// drawCellPoint(canvas, i, surface.textColor);
			}
		}
	}

	private void findSelectedIndex(int startIndex, int endIndex,
								   Calendar calendar, int[] section) {
		for (int i = startIndex; i < endIndex; i++) {
			calendar.set(Calendar.DAY_OF_MONTH, date[i]);
			Date temp = calendar.getTime();
			// Log.d(TAG, "temp:" + temp.toLocaleString());
			if (temp.compareTo(selectedStartDate) == 0) {
				section[0] = i;
			}
			if (temp.compareTo(selectedEndDate) == 0) {
				section[1] = i;
				return;
			}
		}
	}

	public Date getSelectedStartDate() {
		return selectedStartDate;
	}

	public Date getSelectedEndDate() {
		return selectedEndDate;
	}

	private boolean isLastMonth(int i) {
		if (i < curStartIndex) {
			return true;
		}
		return false;
	}

	private boolean isNextMonth(int i) {
		if (i >= curEndIndex) {
			return true;
		}
		return false;
	}

	private int getXByIndex(int i) {
		return i % 7 + 1; // 1 2 3 4 5 6 7
	}

	private int getYByIndex(int i) {
		return i / 7 + 1; // 1 2 3 4 5 6
	}

	// 获得当前应该显示的年月
	public String getYearAndmonth() {
		calendar.setTime(getCurDate());
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		firstday=calendar.getActualMinimum(Calendar.DATE);
		lastday=calendar.getActualMaximum(Calendar.DATE);
		if(month<10){
			return year + "-" +"0"+ month;
		}else{
			return year + "-" + month;
		}

	}
	//同步完成后日期状态恢复到当前日期
	public  void changDown(int date){
		calendar.set(Calendar.DAY_OF_MONTH,
				date);
		downDate = calendar.getTime();
		selectedStartDate = selectedEndDate = downDate;
		postInvalidate();
	}
	//设置默认显示的日
	private void monthOfDay() {

		// 如果不是当前月默认选中第一天
		/*if (!getYearAndmonth().equals(Utility.getCurrentYM())) {
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			downDate = calendar.getTime();
			selectedStartDate = selectedEndDate = downDate;
		} else {*/
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.get(Calendar.DAY_OF_MONTH));
		downDate = calendar.getTime();
		selectedStartDate = selectedEndDate = downDate;
	/*	}*/

	}
	// 上一月
	public String clickLeftMonth() {
		calendar.setTime(getCurDate());
		calendar.add(Calendar.MONTH, -1);
		setCurDate(calendar.getTime());
		//设置默认显示的日
		monthOfDay();

		invalidate();
		return getYearAndmonth();
	}

	// 下一月
	@SuppressWarnings("deprecation")
	public String clickRightMonth() {
		calendar.setTime(getCurDate());
		calendar.add(Calendar.MONTH, 1);
		setCurDate(calendar.getTime());
		// 设置默认显示的日
		monthOfDay();

		invalidate();
		return getYearAndmonth();
	}

	// 设置日历时间
	public void setCalendarData(Date date) {
		calendar.setTime(date);
		invalidate();
	}

	// 获取日历时间
	public void getCalendatData() {
		calendar.getTime();
	}

	// 设置是否多选
	public boolean isSelectMore() {
		return isSelectMore;
	}

	public void setSelectMore(boolean isSelectMore) {
		this.isSelectMore = isSelectMore;
	}

	private void setSelectedDateByCoor(float x, float y) {
		// change month
		// if (y < surface.monthHeight) {
		// // pre month
		// if (x < surface.monthChangeWidth) {
		// calendar.setTime(curDate);
		// calendar.add(Calendar.MONTH, -1);
		// curDate = calendar.getTime();
		// }
		// // next month
		// else if (x > surface.width - surface.monthChangeWidth) {
		// calendar.setTime(curDate);
		// calendar.add(Calendar.MONTH, 1);
		// curDate = calendar.getTime();
		// }
		// }
		// cell click down
		if (y > surface.monthHeight + surface.weekHeight) {
			int m = (int) (Math.floor(x / surface.cellWidth) + 1);
			int n = (int) (Math
					.floor((y - (surface.monthHeight + surface.weekHeight))
							/ Float.valueOf(surface.cellHeight)) + 1);
			downIndex = (n - 1) * 7 + m - 1;
			Log.d(TAG, "downIndex:" + downIndex);
			calendar.setTime(getCurDate());
			if (isLastMonth(downIndex)) {
				calendar.add(Calendar.MONTH, -1);
			} else if (isNextMonth(downIndex)) {
				calendar.add(Calendar.MONTH, 1);
			}
			calendar.set(Calendar.DAY_OF_MONTH, date[downIndex]);
			downDate = calendar.getTime();
		}
		invalidate();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				setSelectedDateByCoor(event.getX(), event.getY());
				break;
			case MotionEvent.ACTION_UP:
				if (downDate != null) {
					if (isSelectMore) {
						if (!completed) {
							if (downDate.before(selectedStartDate)) {
								selectedEndDate = selectedStartDate;
								selectedStartDate = downDate;
							} else {
								selectedEndDate = downDate;
							}
							completed = true;
							// 响应监听事件
							onItemClickListener.OnItemClick(selectedStartDate,
									selectedEndDate, downDate);
						} else {
							selectedStartDate = selectedEndDate = downDate;
							completed = false;
						}
					} else {
						selectedStartDate = selectedEndDate = downDate;
						// 响应监听事件
						onItemClickListener.OnItemClick(selectedStartDate,
								selectedEndDate, downDate);
					}
					invalidate();
				}

				break;
		}
		return true;
	}

	// 给控件设置监听事件
	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

	public Date getCurDate() {
		return curDate;
	}

	public void setCurDate(Date curDate) {
		this.curDate = curDate;
	}

	// 监听接口
	public interface OnItemClickListener {
		void OnItemClick(Date selectedStartDate, Date selectedEndDate,
						 Date downDate);
	}

	/**
	 *
	 * 1. 布局尺寸 2. 文字颜色，大小 3. 当前日期的颜色，选择的日期颜色
	 */
	private class Surface {
		public float density;
		public int width; // 整个控件的宽度
		public int height; // 整个控件的高度
		public float monthHeight; // 显示月的高度
		// public float monthChangeWidth; // 上一月、下一月按钮宽度
		public float weekHeight; // 显示星期的高度
		public float cellWidth; // 日期方框宽度
		public float cellHeight; // 日期方框高度
		public float borderWidth;
		public int bgColor = Color.parseColor("#FFFFFF");
		private int textColor = Color.BLACK;
		// private int textColorUnimportant = Color.parseColor("#666666");
		private int btnColor = Color.parseColor("#666666");
		private int borderColor = Color.parseColor("#CCCCCC");

		// public int todayNumberColor = Color.WHITE;
		public int todayNumberColor = Color.BLACK;

		public int cellDownColor = Color.parseColor("#CCFFFF");// 按下颜色
		public int cellSelectedColor = Color.parseColor("#99CCFF");// 选中颜色
		public Paint borderPaint;
		public Paint monthPaint;
		public Paint weekPaint;
		public Paint datePaint;
		public Paint monthChangeBtnPaint;
		public Paint cellBgPaint;
		public Path boxPath; // 边框路径
		// public Path preMonthBtnPath; // 上一月按钮三角形
		// public Path nextMonthBtnPath; // 下一月按钮三角形
		public String[] weekText = { " 周日", "周一", "周二", "周三", "周四", "周五", "周六" };

		// public String[] monthText =
		// {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};

		public void init() {
			float temp = height / 7f;
			monthHeight = 0;// (float) ((temp + temp * 0.3f) * 0.6);
			// monthChangeWidth = monthHeight * 1.5f;
			weekHeight = (float) ((temp + temp * 0.3f) * 0.7);
			cellHeight = (height - monthHeight - weekHeight) / 6f;
			cellWidth = width / 7f;
			borderPaint = new Paint();
			borderPaint.setColor(borderColor);
			borderPaint.setStyle(Paint.Style.STROKE);
			borderWidth = (float) (0.5 * density);
			// Log.d(TAG, "borderwidth:" + borderWidth);
			borderWidth = borderWidth < 1 ? 1 : borderWidth;
			borderPaint.setStrokeWidth(borderWidth);
			monthPaint = new Paint();
			monthPaint.setColor(textColor);
			monthPaint.setAntiAlias(true);
			float textSize = cellHeight * 0.4f;
			Log.d(TAG, "text size:" + textSize);
			monthPaint.setTextSize(textSize);
			monthPaint.setTypeface(Typeface.DEFAULT_BOLD);
			weekPaint = new Paint();
			weekPaint.setColor(borderColor);
			weekPaint.setAntiAlias(true);
			float weekTextSize = weekHeight * 0.6f;
			weekPaint.setTextSize(weekTextSize);
			weekPaint.setTypeface(Typeface.DEFAULT_BOLD);
			datePaint = new Paint();
			datePaint.setColor(textColor);
			datePaint.setAntiAlias(true);
			float cellTextSize = cellHeight * 0.5f;
			datePaint.setTextSize(cellTextSize);
			datePaint.setTypeface(Typeface.DEFAULT_BOLD);
			boxPath = new Path();
			// boxPath.addRect(0, 0, width, height, Direction.CW);
			// boxPath.moveTo(0, monthHeight);
			boxPath.rLineTo(width, 0);
			boxPath.moveTo(0, monthHeight + weekHeight);
			boxPath.rLineTo(width, 0);
			for (int i = 1; i < 6; i++) {
				boxPath.moveTo(0, monthHeight + weekHeight + i * cellHeight);// 移动画笔
				boxPath.rLineTo(width, 0);// 划线
				boxPath.moveTo(i * cellWidth, monthHeight);
				boxPath.rLineTo(0, height - monthHeight);
			}
			boxPath.moveTo(6 * cellWidth, monthHeight);
			boxPath.rLineTo(0, height - monthHeight);
			// preMonthBtnPath = new Path();
			// int btnHeight = (int) (monthHeight * 0.6f);
			// preMonthBtnPath.moveTo(monthChangeWidth / 2f, monthHeight / 2f);
			// preMonthBtnPath.rLineTo(btnHeight / 2f, -btnHeight / 2f);
			// preMonthBtnPath.rLineTo(0, btnHeight);
			// preMonthBtnPath.close();
			// nextMonthBtnPath = new Path();
			// nextMonthBtnPath.moveTo(width - monthChangeWidth / 2f,
			// monthHeight / 2f);
			// nextMonthBtnPath.rLineTo(-btnHeight / 2f, -btnHeight / 2f);
			// nextMonthBtnPath.rLineTo(0, btnHeight);
			// nextMonthBtnPath.close();
			monthChangeBtnPaint = new Paint();
			monthChangeBtnPaint.setAntiAlias(true);
			monthChangeBtnPaint.setStyle(Paint.Style.FILL_AND_STROKE);
			monthChangeBtnPaint.setColor(btnColor);
			cellBgPaint = new Paint();
			cellBgPaint.setAntiAlias(true);
			cellBgPaint.setStyle(Paint.Style.FILL);
			cellBgPaint.setColor(cellSelectedColor);
		}
	}
}
