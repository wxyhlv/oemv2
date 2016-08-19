package com.capitalbio.oemv2.myapplication.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.View.HorizontalListView;

public class HorizontalListViewAdapter extends BaseAdapter{

	List<String> times;
	Context context;
	int selectedPosition  = 0;
	HorizontalListView listview;
	public HorizontalListViewAdapter(Context context,List<String> times){

		mInflater = LayoutInflater.from(context);
//		Log.d("time0",times.get(0));
		this.times = times;
		this.context = context;
	///	this.listview = listview;
	}
	@Override
	public int getCount() {
		return times.size();
	}
	private LayoutInflater mInflater;


	@Override
	public Object getItem(int position) {
		return position;
	}
	private ViewHolder holder ;
	private static class ViewHolder {
		private TextView value ;
		private ImageView dot ;
	}
	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if(convertView==null){
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_timeline, null);
			holder.value=(TextView)convertView.findViewById(R.id.tv_value);
			holder.dot = (ImageView) convertView.findViewById(R.id.imageView);
			Log.i("convertView"," convertView is null ----"+position + "" +times.get(position));

			//holder.dot=(TextView)convertView.findViewById(R.id.tv_dot);
			convertView.setTag(holder);
			Log.i("convertView", "tag" + "--null--" + convertView.getTag());
		}else{
			holder=(ViewHolder)convertView.getTag();
			Log.i("convertView"," convertView is unnull ----"+position + "  " + times.get(position));
			Log.i("convertView", "tag" + "--unnull--" + convertView.getTag());
		}

	    holder.value.setText(times.get(position));
		if(position == selectedPosition){
			holder.dot.setBackground(context.getResources().getDrawable(R.drawable.timepoint));
			holder.value.setTextColor(Color.parseColor("#00B4BF"));
		}else {
			holder.dot.setBackground(context.getResources().getDrawable(R.drawable.timepoint_normal));
			holder.value.setTextColor(Color.BLACK);
		}

		/*//校正（处理同时上下和左右滚动出现错位情况）
		View child = ((ViewGroup) convertView).getChildAt(1);
		int head = listview.getHeadScrollX();
		if (child.getScrollX() != head) {
			child.scrollTo(mListView.getHeadScrollX(), 0);
		}*/
		//
		return convertView;
	}
	public void setSelectedPosition(int p){
		selectedPosition = p;
		this.notifyDataSetChanged();
	}
}