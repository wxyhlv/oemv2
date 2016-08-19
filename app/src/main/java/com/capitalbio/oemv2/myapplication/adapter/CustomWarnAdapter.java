package com.capitalbio.oemv2.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.capitalbio.oemv2.myapplication.Bean.WarnInfo;
import com.capitalbio.oemv2.myapplication.R;

import java.util.List;

/**
 * Created by wxy on 15-12-21.
 */
public class CustomWarnAdapter extends BaseAdapter {

    List<WarnInfo> list;
    Context context;
    public CustomWarnAdapter(Context context,List<WarnInfo>list){
        this.context = context;
        this.list = list;

    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView ==null){
            convertView = View.inflate(context, R.layout.item_customwarn, null);
            holder = new ViewHolder();
            holder.tv_time=(TextView)convertView.findViewById(R.id.tv_time);
            holder.tv_type = (TextView)convertView.findViewById(R.id.tv_type);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        holder.tv_time.setText(list.get(position).getTime());
        holder.tv_type.setText(list.get(position).getWarnType());

        return convertView;
    }

    class ViewHolder{
        private TextView tv_time;
        private TextView tv_type;
    }
}
