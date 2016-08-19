package com.capitalbio.oemv2.myapplication.View;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

/**
 * 横向翻页效果
 * @author Jiantao.Tu
 *
 */
public class MyScrollView extends HorizontalScrollView{

	private int subChildCount=0;//共有几页
	
	private ViewGroup firstChild=null;//根控件
	
	private int downX=0;//左右移动的距离
	
	private int currentPage=0;//当前移动所在的页面数
	
	/**
	 * 移动接口
	 * @author Jiantao.Tu
	 *
	 */
	public interface MoveListener{
		/**
		 * 移动定位圆点
		 * @param currentPage 当前页数
		 * @param centum 移动的百分比
		 */
	public void translation(int currentPage, float centum, int moveType, boolean isMoveEnd);
		
		/**
		 * 
		 */
		public void lastPage(boolean flag);
	}
	
	private MoveListener moveListener;
	
	private List<Integer> pointList=new ArrayList<Integer>();
	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	public MyScrollView(Context context) {
		super(context);
		init();
	}
	
	public void init(){
		setHorizontalScrollBarEnabled(false);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		receiveChildInfo();
		this.scrollTo(0, 0);
		move(0,1,true);
	}
	
	
	
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downX=(int)ev.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			if(moveListener!=null){
				float num=ev.getX()-downX;
				Log.i("tjt852", num+"");
				if(currentPage==0){// && pointList.size()!=currentPage+1
					if(num<0){
						move(Math.abs(num)/getWidth(),2,false);
					}
				}else if(pointList.size()==currentPage+1){
					if(num>0){
						move(Math.abs(num)/getWidth(),1,false);
					}
				}else{
					if(num>0){
						move(Math.abs(num)/getWidth(),1,false);
					}
					else{
						move(Math.abs(num)/getWidth(),2,false);
					}
				}
					
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			if(Math.abs(ev.getX()-downX)>getWidth()/4){
				if(ev.getX()-downX>0)
					smoothScrollToPrePage();
				else
					smoothScrollToNextPage();
			}else{
				smoothScrollToCurrent();
			}
			return true;
		}
		return super.onTouchEvent(ev);
	}
	
	public void receiveChildInfo(){
		firstChild=(ViewGroup)getChildAt(0);
		pointList.clear();
		currentPage=0;
		if(firstChild!=null){
			subChildCount=firstChild.getChildCount();
			for(int i=0;i<subChildCount;i++){
				if(firstChild.getChildAt(i).getWidth()>0)
					pointList.add(firstChild.getChildAt(i).getLeft());
				
			}
		}
	}
	
	public void smoothScrollToCurrent(){
		smoothScrollTo(currentPage*getWidth(), 0);
		move(0,0,true);
	}
	
	public void smoothScrollToNextPage(){
		if(currentPage<subChildCount-1){
			if(currentPage<subChildCount){
			currentPage++;
			if(moveListener!=null){
				if(pointList.size()-1==currentPage){
					moveListener.lastPage(true);
				}else{
					moveListener.lastPage(false);
				}
			}
			smoothScrollTo(currentPage*getWidth(), 0);
			move(0,2,true);
		}
			}
	}
	
	public void smoothScrollToPrePage(){
		if(currentPage>0){
			if(currentPage>0){
			currentPage--;
			if(moveListener!=null)
				moveListener.lastPage(false);
			smoothScrollTo(currentPage*getWidth(), 0);
			move(0,1,true);
		}
		}
	}
	
	
	public void move(float centum,int moveType,boolean isMoveEnd){
		if(moveListener!=null){
			moveListener.translation(currentPage,centum,moveType,isMoveEnd);
		}
	}
	
	public boolean gotoPage(int page){
		if(page>0 && page<subChildCount-1){
			smoothScrollTo(page, 0);
			currentPage = page;
			return true;
		}
		return false;
	}
	
	public void setMoveListener(MoveListener moveListener){
		this.moveListener=moveListener;
	}

	
}
