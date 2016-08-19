package com.capitalbio.oemv2.myapplication.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.capitalbio.oemv2.myapplication.Bean.AircatDevice;
import com.capitalbio.oemv2.myapplication.Bean.WarnInfo;
import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.Utils.TimeStampUtil;
import com.capitalbio.oemv2.myapplication.dialog.ChangeAIrcatDialog;

import java.util.List;

/**
 * Created by wxy on 15-12-21.
 */
public class ChangeDeviceAdapter extends BaseAdapter {

    List<AircatDevice> list;
    Context context;
    PopupWindow popupWindow;
    public ChangeDeviceAdapter(Context context, List<AircatDevice> list){
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView ==null){
            convertView = View.inflate(context, R.layout.aircatdevice_list_item, null);
            holder = new ViewHolder();
            holder.tv_devicename=(TextView)convertView.findViewById(R.id.tv_devicename);
            holder.tv_current_device=(TextView)convertView.findViewById(R.id.tv_current_device);
            holder.tv_time=(TextView)convertView.findViewById(R.id.tv_time);
            holder.tv_type = (TextView)convertView.findViewById(R.id.tv_way);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        holder.tv_devicename.setText(list.get(position).getBindName());
        holder.tv_devicename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChangeAIrcatDialog dialog = new ChangeAIrcatDialog(context, list.get(position).getMac(), list.get(position).getBindName(), new ChangeAIrcatDialog.PriorityListener() {


                    @Override
                    public void refreshPriorityUI(String name) {
                        list.get(position).setBindName(name);
                        notifyDataSetInvalidated();
                    }
                });
                dialog.setTitle("修改设备名称");
                dialog.show();
            }
        });
        holder.tv_current_device.setVisibility(list.get(position).isCurrent() ? View.VISIBLE : View.GONE);
        long ctime = list.get(position).getCtime();
        holder.tv_time.setText(TimeStampUtil.currTime3(ctime+""));

        holder.tv_type.setText("添加方式："+list.get(position).getWay());

        return convertView;
    }

    class ViewHolder{
        private TextView tv_devicename;
        private TextView tv_current_device;

        private TextView tv_time;
        private TextView tv_type;
    }

    private void showPopWindow(Context context, View parent)
    {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View vPopWindow=inflater.inflate(R.layout.pop_modifydevicename, null, false);
        //宽300 高300
        final PopupWindow popWindow = new PopupWindow(vPopWindow,300,100,true);
        Button okButton = (Button)vPopWindow.findViewById(R.id.ok);
        okButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            }
        });


        popWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);


    }
}
