package com.capitalbio.oemv2.myapplication.Fragment;



import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.squareup.leakcanary.RefWatcher;

/**
 * 
 * @类名称: BaseFragment
 * @类描述: 所有Fragment需要继承的
 * @创建人：wxy
 * @创建时间：
 * @备注：
 */
public abstract class BaseFragment extends Fragment implements OnClickListener {
	protected static Context mContext;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// 1.初始化
		mContext = getActivity().getApplicationContext();
		View loadViewLayout = init(inflater, container);
		return loadViewLayout;
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.i("ceshi","iiiiii");
	}

	/**
	 * 初始activity方法
	 */
	private View init(LayoutInflater inflater, ViewGroup container) {
		View loadViewLayout = loadViewLayout(inflater, container);
		
		findViewById(loadViewLayout);
		processLogic();
		RefWatcher refWatcher = MyApplication.getRefWatcher(getActivity());
		refWatcher.watch(this);
		return loadViewLayout;
	}

	/**
	 * 加载页面layout
	 */
	protected abstract View loadViewLayout(LayoutInflater inflater, ViewGroup container);

	/**
	 * 加载页面元素  设置点击事件
	 */
	protected abstract void findViewById(View view);

	/**
	 * 点击事件
	 */
	public abstract void onClick(View view);


	/**
	 * 业务逻辑处理，主要与后端交互
	 */
	protected abstract void processLogic();

	/**
	 * 模拟后退键
	 */
	protected void popBackStack() {
		FragmentManager fm = getFragmentManager();
		fm.popBackStackImmediate();
	}
}
