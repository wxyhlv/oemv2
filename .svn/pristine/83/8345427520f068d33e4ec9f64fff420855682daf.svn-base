package com.capitalbio.oemv2.myapplication.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.capitalbio.oemv2.myapplication.Bean.AircatDevice;
import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.View.slideDeleteView.SlideView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wxy on 15-12-21.
 */
public class SlideDelAircatAdapter extends BaseAdapter {

    List<AircatDevice> devices;
    boolean editState;
    Context context;
    // 定义一个HashMap，用来存放EditText的值，Key是position
    HashMap<Integer, String> hashMap = new HashMap<Integer, String>();
    int checked = -1;

    public SlideDelAircatAdapter(Context context, List<AircatDevice> list,boolean editState){
        this.context = context;
        this.devices = list;
        this.editState = editState;

    }
    @Override
    public int getCount() {
        return devices.size();
    }

    @Override
    public Object getItem(int position) {
        return devices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView ==null){
            convertView = View.inflate(context, R.layout.list_item, null);
            holder = new ViewHolder();
            holder.tvDeviceName=(TextView)convertView.findViewById(R.id.tv_devicename);
            holder.edtDeviceName = (EditText) convertView.findViewById(R.id.title);
            holder.way = (TextView) convertView.findViewById(R.id.way);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.checked = (TextView) convertView.findViewById(R.id.tv_current_device);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        //holder.title.setText(devices.get(position).getBindName());
        AircatDevice device = devices.get(position);
        Log.i("tag", device.getBindName());
        holder.tvDeviceName.setText(device.getBindName());
        holder.edtDeviceName.setText(device.getBindName());
        holder.way.setText(device.getWay());
        holder.time.setText(device.getCtime() + "");
        if(device.isCurrent()){

            holder.checked.setVisibility(View.VISIBLE);
        }else {
            holder.checked.setVisibility(View.GONE);

        }

/*

        holder.edtDeviceName.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // 将editText中改变的值设置的HashMap中
                hashMap.put(position, s.toString());
            }
        });

        // 如果hashMap不为空，就设置的editText
        if (hashMap.get(position) != null) {
            holder.tvDeviceName.setText(hashMap.get(position));
            holder.edtDeviceName.setText(hashMap.get(position));
        }
*/


        if(editState){
            holder.edtDeviceName.setVisibility(View.VISIBLE);
            holder.tvDeviceName.setVisibility(View.GONE);
            holder.edtDeviceName.setEnabled(true);

        }else{
            holder.edtDeviceName.setVisibility(View.GONE);
            holder.tvDeviceName.setVisibility(View.VISIBLE);
            holder.edtDeviceName.setEnabled(false);
        }
        return convertView;
    }

        class ViewHolder {
        public TextView tvDeviceName;
        public TextView way;
        public TextView time;
        public TextView checked;
        private EditText edtDeviceName;


    }
    public void setData(List<AircatDevice> data){
        this.devices = data;
        this.notifyDataSetInvalidated();
    }
    public void setEditState(boolean editState){
        this.editState = editState;
        this.notifyDataSetChanged();
    }
    public void setChecked(int position){
        for(int i = 0;i<devices.size();i++){
            if(position == i){
                devices.get(position).setCurrent(true);
            }else{
            devices.get(position).setCurrent(false);
        }}
        this.notifyDataSetChanged();

    }

}
