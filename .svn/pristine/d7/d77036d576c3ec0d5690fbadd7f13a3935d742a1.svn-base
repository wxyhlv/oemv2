package com.capitalbio.oemv2.myapplication.View.views;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;


import com.capitalbio.oemv2.myapplication.Bean.ViewBean;

import java.util.ArrayList;
import java.util.List;


/**
 * 该控件实现的功能：
 * 其内部的控件可以通过注册后，可以收到当自己出现在屏幕内及离开屏幕的回调
 */

/**
 *
 * @author lzq
 * @Time 2016/3/9 14:05
 */
public class WindowScrollView extends ScrollView{

    private Context context;

    public WindowScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    //窗口区域
    private Rect windowRect;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        windowRect = new Rect(0,0,w,h);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt){
        super.onScrollChanged(l, t, oldl, oldt);
        ergodic();
    }

    private void ergodic(){
        if(viewBeans==null||viewBeans.size()<1||onShowInWindowListener==null){
            return;
        }

        for(int i=0;i<viewBeans.size();i++){
            ViewBean viewBean = viewBeans.get(i);
            //当前是否在窗口内
            boolean inWindowing = viewBean.getV().getLocalVisibleRect(windowRect);
            //已存储的状态是否在窗口内
            boolean inWindowed = viewBean.isInWindow();
            if(inWindowing){
                if(!inWindowed){
                    if(onShowInWindowListener!=null){
                        onShowInWindowListener.inWindow(viewBean.getV());
                    }
                    viewBean.setInWindow(true);
                    viewBeans.add(viewBean);
                }
            }else{
                if(inWindowed){
                    if(onShowInWindowListener!=null){
                        onShowInWindowListener.outWindow(viewBean.getV());
                    }
                    viewBean.setInWindow(false);
                    viewBeans.add(viewBean);
                }
            }

        }

    }

    private OnShowInWindowListener onShowInWindowListener;

    public void setOnShowInWindowListener(OnShowInWindowListener onShowInWindowListener){
       this.onShowInWindowListener = onShowInWindowListener;
    }

    //注册者
    private List<ViewBean> viewBeans;

    public void regist(ViewBean viewBean){
        if(viewBeans==null){
            viewBeans = new ArrayList<ViewBean>();
        }
        viewBeans.add(viewBean);
    }

    public interface OnShowInWindowListener{
        void inWindow(View v);
        void outWindow(View v);
    }
}
