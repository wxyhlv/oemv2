package com.capitalbio.oemv2.myapplication.dialog;

import java.util.ArrayList;
import java.util.Calendar;


import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.Utils.TimeStampUtil;
import com.capitalbio.oemv2.myapplication.Utils.Utility;
import com.capitalbio.oemv2.myapplication.View.adapter.AbstractWheelTextAdapter;
import com.capitalbio.oemv2.myapplication.View.views.OnWheelChangedListener;
import com.capitalbio.oemv2.myapplication.View.views.OnWheelClickedListener;
import com.capitalbio.oemv2.myapplication.View.views.OnWheelScrollListener;
import com.capitalbio.oemv2.myapplication.View.views.WheelView;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TimeSelectDialog extends Dialog implements
		View.OnClickListener {

	private Context context;

	private Button btcancel, btok;
	private WheelView wvmonth, wvday, wvhour, wvminute;

	private ArrayList<String> arraymonth, arrayday, arrayhour, arrayminute;
	private CalendarTextAdapter adaptermonth, adapterday, adapterhour,
			adapterminute;

	private onCancel onCancel;
	private onOK onOk;
	private int maxTextSize = 24;
	private int minTextSize = 14;

	private int currmonth = Utility.currMonth();
	private int currday = Utility.currDay();
	private int currhour = Utility.currHour();
	private int currminute = Utility.currMinute();

	public TimeSelectDialog(Context context) {
		super(context, R.style.ShareDialog);
		// super(context);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_timeselect);

		btcancel = (Button) this.findViewById(R.id.bt_dialog_selecttime_cancel);
		btok = (Button) this.findViewById(R.id.bt_dialog_selecttime_ok);
		btcancel.setOnClickListener(this);
		btok.setOnClickListener(this);

		wvmonth = (WheelView) this
				.findViewById(R.id.wv_dialog_selecttime_month);
		wvday = (WheelView) this.findViewById(R.id.wv_dialog_selecttime_day);
		wvhour = (WheelView) this.findViewById(R.id.wv_dialog_selecttime_hour);
		wvminute = (WheelView) this
				.findViewById(R.id.wv_dialog_selecttime_minute);

		arraymonth = new ArrayList<String>();
		arrayday = new ArrayList<String>();
		arrayhour = new ArrayList<String>();
		arrayminute = new ArrayList<String>();

		initArrayMonth();
		initArrayDay(TimeStampUtil.daysInMonth(Utility.currHour()));
		initArrayHour(23);
		initArrayMinute(59);

		adaptermonth = new CalendarTextAdapter(context, arraymonth,Utility.currMonth()-1,maxTextSize,minTextSize);
		adapterday = new CalendarTextAdapter(context, arrayday,Utility.currDay()-1,maxTextSize,minTextSize);
		adapterhour = new CalendarTextAdapter(context, arrayhour,Utility.currHour(),maxTextSize,minTextSize);
		adapterminute = new CalendarTextAdapter(context, arrayminute,Utility.currMinute(),maxTextSize,minTextSize);

		wvmonth.setViewAdapter(adaptermonth);
		wvday.setViewAdapter(adapterday);
		wvhour.setViewAdapter(adapterhour);
		wvminute.setViewAdapter(adapterminute);
		


		wvmonth.setCurrentItem(Utility.currMonth()-1);
		wvday.setCurrentItem(Utility.currDay()-1);
		wvhour.setCurrentItem(Utility.currHour());
		wvminute.setCurrentItem(Utility.currMinute());
		wvmonth.setVisibleItems(5);
		wvday.setVisibleItems(5);
		wvhour.setVisibleItems(5);
		wvminute.setVisibleItems(5);

		// 月
		wvmonth.addClickingListener(new OnWheelClickedListener(){
			@Override
			public void onItemClicked(WheelView wheel, int itemIndex) {
				// TODO Auto-generated method stub
				// 回调顺序：1 点击回调
				wvmonth.setCurrentItem(itemIndex, true);
				setTextviewSize(arraymonth.get(wheel.getCurrentItem()), adaptermonth);

			}
		});
		wvmonth.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				// 回调顺序：2 必回调
				currmonth = newValue + 1;
				setTextviewSize(arraymonth.get(wheel.getCurrentItem()),adaptermonth);

			}
		});
		wvmonth.addScrollingListener(new OnWheelScrollListener() {

			@Override
			public void onScrollingStarted(WheelView wheel) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onScrollingFinished(WheelView wheel) {
				// 回调顺序：3 必回调
				
				notifyDay(wheel.getCurrentItem());

				//initMonthWheel();
				initAllWheel();
				setTextviewSize(arraymonth.get(wheel.getCurrentItem()),adaptermonth);
			}
		});

		// 日
		wvday.addClickingListener(new OnWheelClickedListener() {

			@Override
			public void onItemClicked(WheelView wheel, int itemIndex) {
				// TODO Auto-generated method stub
				wvday.setCurrentItem(itemIndex, true);
				setTextviewSize(arrayday.get(wheel.getCurrentItem()),adapterday);

			}
		});
		wvday.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				// TODO Auto-generated method stub
				currday = newValue + 1;
				setTextviewSize(arrayday.get(wheel.getCurrentItem()),adapterday);

			}
		});
		wvday.addScrollingListener(new OnWheelScrollListener() {

			@Override
			public void onScrollingStarted(WheelView wheel) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onScrollingFinished(WheelView wheel) {
				//initDayWheel();
				initAllWheel();
				setTextviewSize(arrayday.get(wheel.getCurrentItem()),adapterday);

			}
		});

		// 小时
		wvhour.addClickingListener(new OnWheelClickedListener() {

			@Override
			public void onItemClicked(WheelView wheel, int itemIndex) {
				// TODO Auto-generated method stub
				wvhour.setCurrentItem(itemIndex, true);
				setTextviewSize(arrayhour.get(wheel.getCurrentItem()),adapterhour);
			}
		});
		
		wvhour.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				// TODO Auto-generated method stub
				currhour = newValue;
				setTextviewSize(arrayhour.get(wheel.getCurrentItem()),adapterhour);

			}
		});
		wvhour.addScrollingListener(new OnWheelScrollListener() {

			@Override
			public void onScrollingStarted(WheelView wheel) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onScrollingFinished(WheelView wheel) {
				// initHourWheel();
				initAllWheel();
				setTextviewSize(arrayhour.get(wheel.getCurrentItem()),adapterhour);

			}
		});

		// 分钟
		wvminute.addClickingListener(new OnWheelClickedListener() {

			@Override
			public void onItemClicked(WheelView wheel, int itemIndex) {
				// TODO Auto-generated method stub
				wvminute.setCurrentItem(itemIndex, true);
			}
		});
		

		wvminute.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				// TODO Auto-generated method stub
				currminute = newValue;
				String currentText = (String) adapterminute.getItemText(wheel.getCurrentItem());
				setTextviewSize(currentText, adapterminute);

			}
		});
		
		wvminute.addScrollingListener(new OnWheelScrollListener() {

			@Override
			public void onScrollingStarted(WheelView wheel) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onScrollingFinished(WheelView wheel) {
				// TODO Auto-generated method stub
                  // initMinuteWheel();
				initAllWheel();
				setTextviewSize(arrayminute.get(wheel.getCurrentItem()),adapterminute);
			}
		});

	}

	private class CalendarTextAdapter extends AbstractWheelTextAdapter {

		ArrayList<String> list;

		protected CalendarTextAdapter(Context context,ArrayList<String> list, int currentItem, int maxsize, int minsize) {
			//super(context, R.layout.item_birth_year);
			super(context, R.layout.item_birth_year, NO_RESOURCE, currentItem, maxsize, minsize);
			this.list = list;
			setItemTextResource(R.id.tempValue);
		}

		@Override
		public int getItemsCount() {
			return list.size();
		}

		@Override
		protected CharSequence getItemText(int index) {
			return list.get(index) + "";
		}

	}

	/**
	 * 设置字体大小
	 *
	 * @param curriteItemText
	 * @param adapter
	 */
	public void setTextviewSize(String curriteItemText, CalendarTextAdapter adapter) {
		ArrayList<View> arrayList = adapter.getTestViews();
		int size = arrayList.size();
		String currentText;
		for (int i = 0; i < size; i++) {
			TextView textvew = (TextView) arrayList.get(i);
			currentText = textvew.getText().toString();
			if (curriteItemText.equals(currentText)) {
				textvew.setTextSize(maxTextSize);
				textvew.setTextColor(Color.parseColor("#0096c1"));
			} else {
				textvew.setTextSize(minTextSize);
				textvew.setTextColor(Color.parseColor("#8D9A99"));
			}
		}
	}

	// 初始化月份数组
	private void initArrayMonth() {
		arraymonth.clear();
		for (int i = 1; i <= 12; i++) {
			arraymonth.add(TimeStampUtil.singleIntToDoubleString(i));
		}
	}

	// 初始化日数组
	private void initArrayDay(int amountofdays) {
		arrayday.clear();
		for (int i = 1; i <= amountofdays; i++) {
			arrayday.add(TimeStampUtil.singleIntToDoubleString(i));
		}
	}

	// 初始化小时数组
	private void initArrayHour(int hour) {
		arrayhour.clear();
		for (int i = 0; i <= hour; i++) {
			arrayhour.add(TimeStampUtil.singleIntToDoubleString(i));
		}
	}

	// 初始化分钟数组
	private void initArrayMinute(int minute) {
		arrayminute.clear();
		for (int i = 0; i <=minute; i++) {
			arrayminute.add(TimeStampUtil.singleIntToDoubleString(i));
		}
	}
	
	/**
	 * 只有notifyday，每年都是12个月，每天都是24小时，每小时都是60分。只有每个月的天数会有不同，所以在切换月份时，要有notifyday。
	 * @param month
	 */
	public void notifyDay(int month){
		int days = TimeStampUtil.daysInMonth(month);
		initArrayDay(days);
		adapterday.notifyDataInvalidatedEvent();
		
		if(currday>days){
			wvday.setCurrentItem(days-1, true);
		}
		
	}
	
	/**
	 * 当滑动每个轮子，都调这个方法，刷新所有轮子的当前显示item
	 */
	private void initAllWheel(){
		//initMonthWheel();
		//initDayWheel();
		//initHourWheel();
		//initMinuteWheel();
		if(initMonthWheel()){
			if(initDayWheel()){
				if(initHourWheel()){
					initMinuteWheel();
				}
			}
		}
	}
	
	//月
	private boolean initMonthWheel(){
		int systemcurrmonth = Utility.currMonth();
		if(currmonth>=systemcurrmonth){
			wvmonth.setCurrentItem(systemcurrmonth-1, true);
			return true;
		}else{
			return false;
		}
	}
	
	//日
	private boolean initDayWheel(){
		int systemcurrday = Utility.currDay();
		if(currday>=systemcurrday){
			wvday.setCurrentItem(systemcurrday-1, true);
			return true;
		}else{
			return false;
		}
	}
	
	//小时
	private boolean initHourWheel(){
		int systemcurrhour = Utility.currHour();
		if(currhour>=systemcurrhour){
			wvhour.setCurrentItem(systemcurrhour, true);
			return true;
		}else{
			return false;
		}
	}
	
	//分钟
	private boolean initMinuteWheel(){
		int systemcurrminute = Utility.currMinute();
		if(currminute>=systemcurrminute){
			wvminute.setCurrentItem(systemcurrminute, true);
			return true;
		}else{
			return false;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_dialog_selecttime_cancel:
			if (onCancel != null) {
				onCancel.onCancel();
			}
			this.dismiss();
			break;
		case R.id.bt_dialog_selecttime_ok:
			if (onOk != null) {
				onOk.onOk(arraymonth.get(wvmonth.getCurrentItem()),
						arrayday.get(wvday.getCurrentItem()),
						arrayhour.get(wvhour.getCurrentItem()),
						arrayminute.get(wvminute.getCurrentItem()));
			}
			this.dismiss();
			break;

		default:
			break;
		}
	}

	public interface onCancel{
		void onCancel();
	}

	public interface onOK {
		void onOk(String month, String day, String hour, String minute);
	}

	public void setOnCancelListener(onCancel cancel) {
		this.onCancel = cancel;
	}

	public void setOnOkListener(onOK ok) {
		this.onOk = ok;
	}

}
