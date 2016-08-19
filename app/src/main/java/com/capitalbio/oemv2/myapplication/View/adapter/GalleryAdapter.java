package com.capitalbio.oemv2.myapplication.View.adapter;

import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import com.capitalbio.oemv2.myapplication.Bean.LoginUser;
import com.capitalbio.oemv2.myapplication.R;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder>
{

	private int selectedPosition;
	private Context context;
	public interface OnItemClickLitener
	{
		void onItemClick(View view, int position);
	}
	public interface OnItemLongClickLitener
	{
		void onItemLongClick(View view, int position);
	}

	private OnItemClickLitener mOnItemClickLitener;

	private OnItemLongClickLitener mOnItemLongClickLitener;

	public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
	{
		this.mOnItemClickLitener = mOnItemClickLitener;
	}
	public void setOnItemLongClickLitener(OnItemLongClickLitener mOnItemLongClickLitener)
	{
		this.mOnItemLongClickLitener = mOnItemLongClickLitener;
	}


	private LayoutInflater mInflater;
	private ArrayList<String> mDatas;

	public GalleryAdapter(Context context, ArrayList<String> datats,int position)
	{
		mInflater = LayoutInflater.from(context);
		mDatas = datats;
		this.context = context;
		selectedPosition = position;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder
	{
		public ViewHolder(View arg0)
		{
			super(arg0);
		}

		ImageView mImg;
		TextView mTxt;
	}

	@Override
	public int getItemCount()
	{
		return mDatas.size();
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
	{
		View view = mInflater.inflate(R.layout.item_timeline,
				viewGroup, false);
		ViewHolder viewHolder = new ViewHolder(view);
		viewHolder.mTxt = (TextView) view.findViewById(R.id.tv_value);

		viewHolder.mImg = (ImageView) view.findViewById(R.id.imageView);
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(final ViewHolder viewHolder, final int i)
	{
		viewHolder.mTxt.setText(mDatas.get(i));

		if (mOnItemClickLitener != null)
		{
			viewHolder.itemView.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					mOnItemClickLitener.onItemClick(viewHolder.itemView, i);
				}
			});

		}
		if (mOnItemLongClickLitener != null)
		{
			viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {

				@Override
				public boolean onLongClick(View v) {
					mOnItemLongClickLitener.onItemLongClick(viewHolder.itemView, i);

					return false;
				}
			});

		}

		Log.i("Gallery","选中项 ： "+selectedPosition);
		if(i == selectedPosition){
			viewHolder.mImg.setBackground(context.getResources().getDrawable(R.drawable.timepoint));
			viewHolder.mTxt.setTextColor(Color.parseColor("#00B4BF"));
		}else {
			viewHolder.mImg.setBackground(context.getResources().getDrawable(R.drawable.timepoint_normal));
			viewHolder.mTxt.setTextColor(Color.BLACK);
		}

	}

	public void setSelectedPosition(int p){
		selectedPosition = p;
		this.notifyDataSetChanged();
	}
}
