package com.capitalbio.oemv2.myapplication.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.capitalbio.oemv2.myapplication.Activity.AircatChangeDeviceActivity;
import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.Bean.LoginUser;
import com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.util.ByteUtil;
import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;
import com.capitalbio.oemv2.myapplication.Utils.PreferencesUtils;
import com.capitalbio.oemv2.myapplication.Utils.TimeStampUtil;
import com.capitalbio.oemv2.myapplication.View.RoundImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.xutils.ex.DbException;

import java.util.List;

/**
 * Created by wxy on 16-1-20.
 */
public class SelectUserAdapter extends BaseAdapter {
    List<LoginUser> users;
    Context context;
    SelectUserAdapter adapter;
    RegistLastDeletListener callback;

    protected ImageLoader mImageLoader = ImageLoader.getInstance();	//加载图片的
    private DisplayImageOptions mOptions;// mImageLoader的mOptions
    private void loadImage(String url, ImageView imageView){
        // 初始化mOptions
        mOptions = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.ic_scene) // 加载开始时默认图片
                .showImageForEmptyUri(R.drawable.ic_scene) // url为null显示该图片
                .showImageOnFail(R.drawable.ic_scene) // 加载失败 显示该图片
                .build();	//构建
        mImageLoader.displayImage(url, imageView, mOptions, null);	//显示图片
    }
    public SelectUserAdapter(Context context, List<LoginUser> users,RegistLastDeletListener callback) {
        this.context = context;
        this.users = users;
        adapter = this;
        this.callback = callback;
    }

    public void setData(List<LoginUser> data){
        this.users = data;
        this.notifyDataSetInvalidated();
    }
    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_login_user, null);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.ivDelete = (ImageView) convertView.findViewById(R.id.iv_delete);
            holder.iv_user_photo = (RoundImageView) convertView.findViewById(R.id.iv_user_photo);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvName.setText(users.get(position).getUsername());
       /* String path = PreferencesUtils.getString(context, users.get(position).getLoginName() + "head", "");
        if(!"".equals(path)){
            loadImage("file://" +path, holder.iv_user_photo);
        }else{*/
        byte[] bytePic = users.get(position).getHeadPic();
        Log.i("LoginActivity", "position is  " + position + bytePic);

        if (bytePic != null && bytePic.length > 0) {

            Bitmap bitmap = com.capitalbio.oemv2.myapplication.Devices.BloodPressDevice.ByteUtil.byteToBitmap(bytePic);
            holder.iv_user_photo.setImageBitmap(bitmap);
        }else{

            holder.iv_user_photo.setImageResource(R.drawable.ic_scene);
        }

        //}


        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "delete" + position, Toast.LENGTH_LONG).show();


                    OemLog.log("TIME", "before  remove");
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("确认删除吗？");
                    builder.setTitle("提示");
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {

                                MyApplication.getInstance().getDB().deleteById(LoginUser.class, users.get(position).getId());

                            } catch (DbException e) {
                                e.printStackTrace();
                            }
                            users.remove(position);
                            setData(users);

                            callback.onLastDeleteListener();

                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override


                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }

                    });
                    builder.create().show();


                    OemLog.log("TIME", "after  remove");
                }

        });

        return convertView;
    }
}

class ViewHolder {
    RoundImageView iv_user_photo;
    TextView tvName;
    ImageView ivDelete;
}
