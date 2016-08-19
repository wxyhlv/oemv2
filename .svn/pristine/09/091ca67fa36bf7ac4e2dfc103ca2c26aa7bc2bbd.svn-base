package com.capitalbio.oemv2.myapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.capitalbio.oemv2.myapplication.Bean.LoginUser;
import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.View.GlideCircleTransform;

import java.util.List;

/**
 * Created by lzq on 16-8-12.
 */
public class AccountManagementListAdapter extends BaseAdapter{



    private List<LoginUser> loginUsers;

    private Context context;


    public AccountManagementListAdapter(List<LoginUser> loginUsers, Context context) {
        this.loginUsers = loginUsers;
        this.context = context;
    }

    public AccountManagementListAdapter add(LoginUser loginUser){
        loginUsers.add(loginUser);

        return this;
    }

    public AccountManagementListAdapter sub(LoginUser loginUser){
        loginUsers.remove(loginUser);
        return this;
    }

    @Override
    public int getCount() {
        return loginUsers.size();
    }

    @Override
    public Object getItem(int position) {
        return loginUsers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LoginUser loginUser = loginUsers.get(position);

        View view = null;


                //View view = LayoutInflater.from(context).inflate(R.layout.adapter_accountmanagement_accountlist,parent);


                 view = View.inflate(context,R.layout.adapter_accountmanagement_accountlist,null);

                ImageView iv_avatar = (ImageView) view.findViewById(R.id.iv_adapter_accountmanagementlist_avatar);

                byte[] avatar = loginUser.getHeadPic();
                if(avatar!=null&&avatar.length>0){
                    Glide.with(context).load(avatar).transform(new GlideCircleTransform(context)).into(iv_avatar);
                }else{
                    Glide.with(context).load(R.drawable.ic_scene).transform(new GlideCircleTransform(context)).into(iv_avatar);
                }

                TextView name = (TextView) view.findViewById(R.id.tv_adapter_accountmanagementlist_name);

                name.setText(loginUsers.get(position).getUsername());

                ImageView tag = (ImageView) view.findViewById(R.id.iv_adapter_accountmanagementlist_currenttag);


                String islogin = loginUser.getIsLogin();
                if(islogin!=null&&islogin.equals("true")){
                    tag.setImageResource(R.drawable.ic_hook);
                }





        return view;

    }


}
