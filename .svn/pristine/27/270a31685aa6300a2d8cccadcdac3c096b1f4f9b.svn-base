/*
 * @(#)DialogManager.java		       version: 0.1 
 * Date:2012-2-3
 *
 * Copyright (c) 2011 CFuture09, Institute of Software, 
 * Guangdong Ocean University, Zhanjiang, GuangDong, China.
 * All rights reserved.
 */
package com.capitalbio.oemv2.myapplication.FirstPeriod.Utils;

import java.util.HashMap;
import java.util.Set;

import android.app.Dialog;

/**
 * 一个Dialog管理器管理活动的Dialog。
 * 
 * @author Geek_Soledad (66704238@51uc.com)
 */
public class DialogTaskManager {

	private static DialogTaskManager DialogTaskManager = null;
	private HashMap<String, Dialog> DialogMap = null;

	private DialogTaskManager() {
		DialogMap = new HashMap<String, Dialog>();
	}

	/**
	 * 返回Dialog管理器的唯一实例对象。
	 * 
	 * @return
	 */
	public static synchronized DialogTaskManager getInstance() {
		if (DialogTaskManager == null) {
			DialogTaskManager = new DialogTaskManager();
		}
		return DialogTaskManager;
	}

	/**
	 * 将一个Dialog添加到管理器。
	 * 
	 * @param Dialog
	 */
	public Dialog putDialog(String name, Dialog Dialog) {
		return DialogMap.put(name, Dialog);
	}

	/**
	 * 得到保存在管理器中的Dialog对象。
	 * 
	 * @param name
	 * @return
	 */
	public Dialog getDialog(String name) {
		return DialogMap.get(name);
	}

	/**
	 * 返回管理器的Dialog是否为空。
	 * 
	 * @return 当且当管理器中的Dialog对象为空时返回true，否则返回false。
	 */
	public boolean isEmpty() {
		return DialogMap.isEmpty();
	}

	/**
	 * 返回管理器中Dialog对象的个数。
	 * 
	 * @return 管理器中Dialog对象的个数。
	 */
	public int size() {
		return DialogMap.size();
	}

	/**
	 * 返回管理器中是否包含指定的名字。
	 * 
	 * @param name
	 *            要查找的名字。
	 * @return 当且仅当包含指定的名字时返回true, 否则返回false。
	 */
	public boolean containsName(String name) {
		return DialogMap.containsKey(name);
	}

	/**
	 * 返回管理器中是否包含指定的Dialog。
	 * 
	 * @param Dialog
	 *            要查找的Dialog。
	 * @return 当且仅当包含指定的Dialog对象时返回true, 否则返回false。
	 */
	public boolean containsDialog(Dialog Dialog) {
		return DialogMap.containsValue(Dialog);
	}

	/**
	 * 关闭所有活动的Dialog。
	 */
	public void closeAllDialog() {
		Set<String> DialogNames = DialogMap.keySet();
		for (String string : DialogNames) {
			finisDialog(DialogMap.get(string));
		}
		DialogMap.clear();
	}

	/**
	 * 关闭所有活动的Dialog除了指定的一个之外。
	 * 
	 * @param nameSpecified
	 *            指定的不关闭的Dialog对象的名字。
	 */
	public void closeAllDialogExceptOne(String nameSpecified) {
		Set<String> DialogNames = DialogMap.keySet();
		Dialog DialogSpecified = DialogMap.get(nameSpecified);
		for (String name : DialogNames) {
			if (name.equals(nameSpecified)) {
				continue;
			}
			finisDialog(DialogMap.get(name));
		}
		DialogMap.clear();
		DialogMap.put(nameSpecified, DialogSpecified);
	}

	/**
	 * 移除Dialog对象,如果它未结束则结束它。
	 * 
	 * @param name
	 *            Dialog对象的名字。
	 */
	public void removeDialog(String name) {
		Dialog Dialog = DialogMap.remove(name);
		finisDialog(Dialog);
	}

	private final void finisDialog(Dialog Dialog) {
		if (Dialog != null) {
			if (!Dialog.isShowing()) {
				Dialog.dismiss();
			}
		}
	}

	public HashMap<String, Dialog> getDialogMap() {
		return DialogMap;
	}

	
}