package com.capitalbio.oemv2.myapplication.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.provider.ContactsContract.Contacts.Data;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


/**
 * 日期控件
 * 
 */
public class CustomCalendarView extends View implements
		View.OnTouchListener {
	private final static String TAG = "anCalendar";
	private Date selectedStartDate;
	private Date selectedEndDate;
	private Date curDate; // 
	private Date today; //
	private Date downDate; // 
	public Date getDownDate() {
		return downDate;
	}

	public void setDownDate(Date downDate) {
		this.downDate = downDate;
	}

	private Date showFirstDate, showLastDate; // 
	private int downIndex; // 
	private Calendar calendar;
	private Surface surface;
	private int[] date = new int[42]; // 
	private int curStartIndex, curEndIndex; //
	private boolean completed = false; //
	private boolean isSelectMore = false;
	private Context context;

	private OnItemClickListener onItemClickListener;
	private List<String> timeList;
	private List<String> monthList = new ArrayList<String>();// 
	private List<String> dayList = new ArrayList<String>();//
	private String currentShowMonth;
    public  int firstday;
	public  int lastday;
	public int celldownindex =-1;
	
	public CustomCalendarView(Context context, List<String> timeList,Date date) {
		super(context);
		this.timeList = timeList;// [2015-02-03 15:14:16, 2015-02-03
		// 17:23:28]
		curDate =date;
		init();
	
	}

	public CustomCalendarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	/**
	 * 
	 * 
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

	
		monthList.clear();
		dayList.clear();
		if(timeList.size()>0) {

			for (int i = 0; i < timeList.size(); i++) {
				String[] ymdArr = timeList.get(i).split("-");
				// String[] ymdArr = split[0].split("-");
				String month = ymdArr[1];
				monthList.add(month);
				String day = ymdArr[2];
				dayList.add(day);

			}
		}
      //  Date date= new Date("2015-05-08  11:33:00");
		setCurDate(selectedStartDate = selectedEndDate = today = curDate);
		
		calendar = Calendar.getInstance();
		calendar.setTime(getCurDate());
		surface = new Surface();
		surface.density = getResources().getDisplayMetrics().density;
	//	setBackgroundColor(surface.bgColor);
		setOnTouchListener(this);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		surface.width = getResources().getDisplayMetrics().widthPixels - 30;
		surface.height = (int) (getResources().getDisplayMetrics().heightPixels * 1 / 3);
		widthMeasureSpec = MeasureSpec.makeMeasureSpec(surface.width,
				MeasureSpec.EXACTLY);
		heightMeasureSpec = MeasureSpec.makeMeasureSpec(surface.height,
				MeasureSpec.EXACTLY);
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		Log.d(TAG, "[onLayout] changed:"
				+ (changed ? "new size" : "not change") + " right_:" + left
				+ " top:" + top + " left:" + right + " bottom:" + bottom);
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
            // 画第一行周文字
            float weekTextY = surface.monthHeight + surface.weekHeight * 3 / 4f;
            for (int i = 0; i < surface.weekText.length; i++) {
            	float weekTextX = i
            			* surface.cellWidth
            			+ (surface.cellWidth - surface.weekPaint
            					.measureText(surface.weekText[i])) / 2f;
            	canvas.drawText(surface.weekText[i], weekTextX, weekTextY,
            			surface.weekPaint);
            }

            calculateDate();
         
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            String curDateformat = sf.format(getCurDate());
            String[] cfArr = curDateformat.split("-");
            currentShowMonth = cfArr[1];
            drawDownOrSelectedBg(canvas);
            //画标记的日期
            for (int i = 0; i < dayList.size(); i++) {
            	if (currentShowMonth.equals(monthList.get(i))) {
            		int color = surface.textColor;
            		String pointDay = dayList.get(i);
            		int pointDayNum = Integer.parseInt(pointDay);
            		int pointIndex = curStartIndex + pointDayNum - 1;
            		drawCellPoint(canvas, pointIndex, color);
            	}
            }
            String lastcurDateformat = sf.format(showFirstDate);
            String[] lcfArr = lastcurDateformat.split("-");
            String lastMonthStartDayMonth = lcfArr[1];
            int lastMonthStartDayNumber = Integer.parseInt(lcfArr[2]);
            for (int i = 0; i < dayList.size(); i++) {
            	if (Integer.parseInt(dayList.get(i)) > lastMonthStartDayNumber
            			&& monthList.get(i).equals(lastMonthStartDayMonth)) {
            		int color = surface.textColor;
            		String pointDay = dayList.get(i);
            		int pointDayNum = Integer.parseInt(pointDay);
            		int pointIndex = pointDayNum - lastMonthStartDayNumber;
            		drawCellPoint(canvas, pointIndex, color);
            	}
            }
            String nextcurDateformat = sf.format(showLastDate);
            String[] ncfArr = nextcurDateformat.split("-");

            String nextMonthEndDayMonth = ncfArr[1];
            int nextMonthEndDayNumber = Integer.parseInt(ncfArr[2]);
            for (int i = 0; i < dayList.size(); i++) {
            	if (Integer.parseInt(dayList.get(i)) < nextMonthEndDayNumber
            			&& monthList.get(i).equals(nextMonthEndDayMonth)) {
            		int color = surface.textColor;
            		String pointDay = dayList.get(i);
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
            int todayNumber = calendar.get(Calendar.DAY_OF_MONTH);
        	todayIndex = curStartIndex + todayNumber - 1;
        	Log.i("todayIndex", todayIndex+"");
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
            	if(celldownindex == i){
            		color = surface.cellDownTextColor;
            	}
            	drawCellText(canvas, i, date[i] + "", color);
            }
            super.onDraw(canvas);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	private void calculateDate() {
		Log.d("calendar","mmmmmmmmmm",new Throwable());
		calendar.setTime(getCurDate());
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int dayInWeek = calendar.get(Calendar.DAY_OF_WEEK);
		Log.d(TAG, "day in week:" + dayInWeek);
		int monthStart = dayInWeek;
		if (monthStart == 1) {
			monthStart = 8;
		}
		monthStart -= 1; // from sunday -1;monday -2
		curStartIndex = monthStart;
		Log.i("curStartIndex", curStartIndex+"");
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
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		calendar.set(Calendar.DAY_OF_MONTH, date[41]);
		showLastDate = calendar.getTime();
	}

	/**
	 * 
	 * 
	 * @param canvas
	 * @param index
	 * @param color
	 */
	private void drawCellPoint(Canvas canvas, int index, int color) {

		int x = getXByIndex(index);
		int y = getYByIndex(index);
		surface.cellBgPaint.setColor(color);
		surface.cellBgPaint.setStyle(Paint.Style.STROKE);
		surface.cellBgPaint.setStrokeWidth(3);
		float left = surface.cellWidth * (x - 1) + surface.borderWidth;
		float top = surface.monthHeight + surface.weekHeight + (y - 1)
				* surface.cellHeight + surface.borderWidth;
		canvas.drawCircle(left + surface.cellWidth / 2-2, top
				+ surface.cellHeight / 2+3, surface.cellHeight / 2-surface.cellHeight / 10,
				surface.cellBgPaint);
	}

	/**
	 * 
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
		surface.cellBgPaint.setStyle(Paint.Style.FILL);
		float left = surface.cellWidth * (x - 1) + surface.borderWidth;
		float top = surface.monthHeight + surface.weekHeight + (y - 1)
				* surface.cellHeight + surface.borderWidth;
    	canvas.drawCircle(left + surface.cellWidth / 2-2, top
				+ surface.cellHeight / 2+3, surface.cellHeight / 2,
				surface.cellBgPaint);
	    celldownindex = index;
	//    invalidate();
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





	public void setCalendarData(Date date) {
	    curDate=date;
	    celldownindex=-1;
		invalidate();
	}
	public void setCalendarSlectedData() {

		celldownindex=1;
		invalidate();
	}

	public void getCalendatData() {
		calendar.getTime();
	}

	public boolean isSelectMore() {
		return isSelectMore;
	}

	public void setSelectMore(boolean isSelectMore) {
		this.isSelectMore = isSelectMore;
	}

	private void setSelectedDateByCoor(float x, float y) {

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
		//invalidate();
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
						onItemClickListener.OnItemClick(selectedStartDate,
								selectedEndDate, downDate);
					} else {
						selectedStartDate = selectedEndDate = downDate;
						completed = false;
					}
				} else {
					selectedStartDate = selectedEndDate = downDate;
					onItemClickListener.OnItemClick(selectedStartDate,
							selectedEndDate, downDate);
				}
				invalidate();
			}

			break;
		}
		return true;
	}

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	
	}

	public Date getCurDate() {
		return curDate;
	}

	public void setCurDate(Date curDate) {
		this.curDate = curDate;
	}

	// �����ӿ�
	public interface OnItemClickListener {
		void OnItemClick(Date selectedStartDate, Date selectedEndDate,
						 Date downDate);
	}

	/**
	 * 
	 * 1. ���ֳߴ� 2. ������ɫ����С 3. ��ǰ���ڵ���ɫ��ѡ���������ɫ
	 */
	private class Surface {
		public float density;
		public int width; // �����ؼ��Ŀ��
		public int height; // �����ؼ��ĸ߶�
		public float monthHeight; // ��ʾ�µĸ߶�
		// public float monthChangeWidth; // ��һ�¡���һ�°�ť���
		public float weekHeight; // ��ʾ���ڵĸ߶�
		public float cellWidth; // ���ڷ�����
		public float cellHeight; // ���ڷ���߶�
		public float borderWidth;
		public int bgColor = Color.parseColor("#00BC8D");
		private int textColor = Color.WHITE;
		// private int textColorUnimportant = Color.parseColor("#666666");
		private int btnColor = Color.parseColor("#0292BF");
		private int borderColor = Color.parseColor("#CCCCCC");

		// public int todayNumberColor = Color.WHITE;
		//public int todayNumberColor = Color.parseColor("#99CCFF");
		public int todayNumberColor = Color.RED;
		public int cellDownTextColor = Color.parseColor("#99CCFF");// ������ɫ
		//public int cellSelectedColor = Color.parseColor("#99CCFF");// ѡ����ɫ
		public int cellSelectedColor = Color.WHITE;// ѡ����ɫ
		public Paint borderPaint;
		public Paint monthPaint;
		public Paint weekPaint;
		public Paint datePaint;
		public Paint monthChangeBtnPaint;
		public Paint cellBgPaint;
		public Path boxPath; // �߿�·��
		// public Path preMonthBtnPath; // ��һ�°�ť������
		// public Path nextMonthBtnPath; // ��һ�°�ť������
		public String[] weekText = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
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
			weekPaint.setColor(Color.WHITE);
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
				boxPath.moveTo(0, monthHeight + weekHeight + i * cellHeight);// �ƶ�����
				boxPath.rLineTo(width, 0);// ����
				boxPath.moveTo(i * cellWidth, monthHeight);
				boxPath.rLineTo(0, height - monthHeight);
			}
			boxPath.moveTo(6 * cellWidth, monthHeight);
			boxPath.rLineTo(0, height - monthHeight);
		
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