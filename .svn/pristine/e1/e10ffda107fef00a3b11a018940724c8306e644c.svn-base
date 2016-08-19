package com.capitalbio.oemv2.myapplication.FirstPeriod.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

public class JsonUtil {
	public JsonUtil() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Bean对象转换为json串
	 * @param object
	 * @return
	 */
	public static String creatJsonString(Object object){
		return JSON.toJSONString(object);
	}
	
	/**
	 * json串转换为Bean对象
	 * 
	 * @param <T>
	 * @param jsonString
	 * @param cls
	 * @return
	 */
	public static <T> T getBean(String jsonString, Class<T> cls) {
		T t = null;
		try {
			t = JSON.parseObject(jsonString, cls);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * json串转换为list<bean>对象
	 * 
	 * @param <T>
	 * @param jsonString
	 * @param cls
	 * @return
	 */
	public static <T> List<T> getListBean(String jsonString, Class<T> cls) {
		List<T> list = new ArrayList<T>();
		try {
			list = JSON.parseArray(jsonString, cls);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * json串转化为 list<Map>对象
	 * 
	 * @param jsonString
	 * @return
	 */
	public static List<Map<String, Object>> getKeyMaps(String jsonString) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = JSON.parseObject(jsonString,new TypeReference<List<Map<String, Object>>>() {});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
