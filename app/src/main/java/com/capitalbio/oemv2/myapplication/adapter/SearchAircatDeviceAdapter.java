package com.capitalbio.oemv2.myapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.capitalbio.oemv2.myapplication.R;

import java.util.List;

/**
 * Created by wxy on 15-12-21.
 */
public class SearchAircatDeviceAdapter extends BaseAdapter {

    List<String> list;
    Context context;
    public SearchAircatDeviceAdapter(Context context, List<String> list){
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
            convertView = View.inflate(context, R.layout.item_device, null);
            holder = new ViewHolder();
            holder.tv_name=(TextView)convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        holder.tv_name.setText(list.get(position));
        holder.tv_name.setBackgroundResource(R.drawable.bg_aircat_search);


        return convertView;
    }

    class ViewHolder{
        private TextView tv_name;
    }
    public void setData(List<String> data){
        list = data;
        this.notifyDataSetInvalidated();
    }
}
