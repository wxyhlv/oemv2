package com.capitalbio.oemv2.myapplication.FirstPeriod.DB;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.capitalbio.oemv2.myapplication.Bean.AirIndexBean;
import com.capitalbio.oemv2.myapplication.Bean.TimeBean;
import com.capitalbio.oemv2.myapplication.FirstPeriod.Utils.TimeStampUtil;

import java.util.ArrayList;
import java.util.List;

public class CatDBmanager {

	private CatDBhelper helper;
	private SQLiteDatabase db;
	private static CatDBmanager catDBmanager;

	private CatDBmanager(Context context) {
		helper = new CatDBhelper(context);
		db = helper.getWritableDatabase();
	}
	
	public static synchronized CatDBmanager getInstance(Context context){
		if(catDBmanager==null){
			catDBmanager = new CatDBmanager(context);
		}
		return catDBmanager;
	}

	// 插入数据
	/**
	 * 插入单条数据
	 * @param bean
	 */
	public void insertData(AirIndexBean bean) {
		
		String index = bean.getIndex();
		String username = bean.getUser();
		String mac = bean.getMAC();
		String time = bean.getTime();
		String aqi = bean.getAqi();
		String formaldehyde = bean.getFormaldehyde();
		String humidity = bean.getHumidity();
		String temperature = bean.getTemperature();
		String battery = bean.getBattery();
		int year = bean.getYear();
		int month = bean.getMonth();
		int day = bean.getDay();
        int hour = bean.getHour();
        int minute = bean.getMinute();
		
		
		
		db.execSQL(
				"insert into cat values(null,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
				new Object[] {index,username,time, aqi, formaldehyde, humidity,
						temperature, battery, mac, year,month,day,hour,minute});
	}
	
	//数据查询
	/**
	 * 获取最近一条数据，如果没有则返回null
	 * @return
	 */
	public AirIndexBean queryDataNewest(String username){
		AirIndexBean airIndexBean = new AirIndexBean();
		
		Cursor c = db.rawQuery("select time from cat where "+  
          "time=(select max(time) from cat where  username=?)and username=? order by time desc limit 0,1", new String[]{username,username});
		
		
		if(c==null||c.getCount()<1){
			return null;
		}
		c.moveToFirst();
		String time = c.getString(c.getColumnIndex("time"));
		airIndexBean.setTime(time);
			c.close();
		
		return airIndexBean;
	}
	
	/**
	 * 查询全部数据
	 * @return
	 */
	public List<AirIndexBean> queryData(){
		List<AirIndexBean> indexBeans = new ArrayList<AirIndexBean>();
		Cursor c = db.rawQuery("select * from cat", null);
		while(c.moveToNext()){
			AirIndexBean airIndexBean = new AirIndexBean();
			int id = c.getInt(c.getColumnIndex("id"));
			String index = c.getString(c.getColumnIndex("index_id"));
			String time = c.getString(c.getColumnIndex("time"));
			String aqi = c.getString(c.getColumnIndex("aqi"));
			String formaldehyde = c.getString(c.getColumnIndex("formaldehyde"));
			String humidity = c.getString(c.getColumnIndex("humidity"));
			String temperature = c.getString(c.getColumnIndex("temperature"));
			String battery = c.getString(c.getColumnIndex("battery"));
			String MAC = c.getString(c.getColumnIndex("MAC"));
			int year = c.getInt(c.getColumnIndex("year"));
			int month = c.getInt(c.getColumnIndex("month"));
			int day = c.getInt(c.getColumnIndex("day"));
			airIndexBean.setId(id);
			airIndexBean.setIndex(index);
			airIndexBean.setTime(time);
			airIndexBean.setAqi(aqi);
			airIndexBean.setFormaldehyde(formaldehyde);
			airIndexBean.setHumidity(humidity);
			airIndexBean.setTemperature(temperature);
			airIndexBean.setBattery(battery);
			airIndexBean.setMAC(MAC);
			airIndexBean.setYear(year);
			airIndexBean.setMonth(month);
			airIndexBean.setDay(day);
			indexBeans.add(airIndexBean);
		}
		c.close();
		return indexBeans;
	}
	/**
	 * 查询数据数量
	 * @return
	 */
	public int queryDataAmount(){
		int amount = 0;
		Cursor c = db.rawQuery("select * from cat", null);
		amount = c.getCount();
		c.close();
		return amount;
	}
	
	/**
	 * 获取过去几天的各项平均值
	 * @param beans
	 * @return
	 */
	public List<AirIndexBean> getPastSevenDayData(List<TimeBean> beans){
		List<AirIndexBean> airIndexBeans = new ArrayList<AirIndexBean>();
		int j = beans.size();
		for(int i=0;i<j;i++){
			AirIndexBean airIndexBean = new AirIndexBean();
			TimeBean bean = beans.get(i);
			List<AirIndexBean> airIndexBeans2 = new ArrayList<AirIndexBean>();
			airIndexBeans2 = getAnyDayData(bean);
			airIndexBean = getAverageAnyDay(airIndexBeans2);
			airIndexBeans.add(airIndexBean);
		}
		return airIndexBeans;
	}
	
	/**
	 * 获取任意一天的全部数据
	 * @param bean
	 * @return
	 */
	private List<AirIndexBean> getAnyDayData(TimeBean bean){
		List<AirIndexBean> airIndexBeans = new ArrayList<AirIndexBean>();
		int year = bean.getYear();
		int month = bean.getMonth();
		int day = bean.getDay();
		Cursor c = db.rawQuery("select * from cat where year=? and  month=? and day=?", new String[]{year+"",month+"",day+""});
		//System.out.println("查询到的数据数目="+c.getCount());
		if(c!=null){
			while(c.moveToNext()){
				AirIndexBean airIndexBean = new AirIndexBean();
				String temperature = c.getString(c.getColumnIndex("temperature"));
				String humidity =c.getString(c.getColumnIndex("humidity"));
				String formaldehyde = c.getString(c.getColumnIndex("formaldehyde"));
				String aqi = c.getString(c.getColumnIndex("aqi"));
				airIndexBean.setTemperature(temperature);
				airIndexBean.setHumidity(humidity);
				airIndexBean.setFormaldehyde(formaldehyde);
				airIndexBean.setAqi(aqi);
				airIndexBeans.add(airIndexBean);
			}
		}
		if(c!=null){
			c.close();
			c = null;
		}
		
		return airIndexBeans;
	}
	
	/**
	 * 求某一天的平均数据
	 * @param airIndexBeans
	 * @return
	 */
	private AirIndexBean getAverageAnyDay(List<AirIndexBean> airIndexBeans){
		AirIndexBean airIndexBean = new AirIndexBean();
		int j = airIndexBeans.size();
		float temperature = 0;
		float humidity = 0;
		float formaldehyde = 0;
		float aqi = 0;
		
		for(int i=0 ;i<j;i++){
			AirIndexBean bean =airIndexBeans.get(i);
			String tem = bean.getTemperature();
			String hum = bean.getHumidity();
			String jiaquan = bean.getFormaldehyde();
			String tvoc = bean.getAqi();
			float tem_f = Float.parseFloat(tem);
			float hum_f = Float.parseFloat(hum);
			float jiaquan_f = Float.parseFloat(jiaquan);
			float tvoc_f = Float.parseFloat(tvoc);
			temperature = (temperature+tem_f)/(i==0?1:2);
			humidity = (humidity+hum_f)/(i==0?1:2);
			formaldehyde = (formaldehyde+jiaquan_f)/(i==0?1:2);
			aqi = (aqi+tvoc_f)/(i==0?1:2);
		}
		
		airIndexBean.setTemperature(temperature+"");
		airIndexBean.setHumidity(humidity+"");
		airIndexBean.setFormaldehyde(formaldehyde+"");
		airIndexBean.setAqi(aqi+"");
		
		return airIndexBean;
	}
	
	/**
	 * 获取过去24小时每小时数据的平均值
	 * @return
	 */
	public List<AirIndexBean> getPast24HourData(String username){
		
		List<AirIndexBean> airIndexBeans = new ArrayList<AirIndexBean>();
		List<TimeBean> timebeans = TimeStampUtil.getPast24Hour();
		
		for(int i=0;i<timebeans.size();i++){
			
			//某一小时的 全部数据
			List<AirIndexBean> airIndexBeansinHour = getAnyHourData(timebeans.get(i),username);
			
			if(airIndexBeansinHour!=null&&airIndexBeansinHour.size()>0){
				//说明这个小时有数据 
				//
				AirIndexBean airIndexBeanAvinHour = new AirIndexBean();
				//求平均值
				airIndexBeanAvinHour = getAverageAnyHour(airIndexBeansinHour);
				//
				airIndexBeans.add(airIndexBeanAvinHour);
			}
		}
		/*for(int m=0;m<airIndexBeans.size();m++){
			System.out.println(airIndexBeans.get(m).getHour());
		}*/
		return airIndexBeans;
		
	}
	
	/**
	 * 获取某一小时的全部数据
	 * @param bean
	 * @return
	 */
	private List<AirIndexBean> getAnyHourData(TimeBean bean,String user){
		List<AirIndexBean> airIndexBeans = new ArrayList<AirIndexBean>();
		int year = bean.getYear();
		int month = bean.getMonth();
		int day = bean.getDay();
		int hour = bean.getHour();
		Cursor c = db.rawQuery("select * from cat where year=? and month=? and day=? and hour=? and username=?", new String[]{year+"",month+"",day+"",hour+"",user});
		if(c!=null){
			while(c.moveToNext()){
				AirIndexBean airIndexBean = new AirIndexBean();
				String username = c.getString(c.getColumnIndex("username"));
				String time = c.getString(c.getColumnIndex("time"));
				String tem = c.getString(c.getColumnIndex("temperature"));
				String hum = c.getString(c.getColumnIndex("humidity"));
				String forma = c.getString(c.getColumnIndex("formaldehyde"));
				String aqi = c.getString(c.getColumnIndex("aqi"));
				
				airIndexBean.setUser(username);
				airIndexBean.setTime(time);
				airIndexBean.setTemperature(tem);
				airIndexBean.setHumidity(hum);
				airIndexBean.setFormaldehyde(forma);
				airIndexBean.setAqi(aqi);
				airIndexBean.setYear(year);
				airIndexBean.setMonth(month);
				airIndexBean.setDay(day);
				airIndexBean.setHour(hour);
				airIndexBeans.add(airIndexBean);
			}
		}
		
		if(c!=null){
			c.close();
			c = null;
		}
		
		return airIndexBeans;
	}
	
	/**
	 * 求某单位小时内数据的平均值
	 * @return
	 */
	private AirIndexBean getAverageAnyHour(List<AirIndexBean> airIndexBeans){
		if(airIndexBeans==null){
			return null;
		}
		int size = airIndexBeans.size();
		AirIndexBean airIndexBean = new AirIndexBean();
		float tem_sum = 0;
		float hum_sum = 0;
		float forma_sum = 0;
		float aqi_sum = 0;
		for(int i=0;i<size;i++){
			String tem = airIndexBeans.get(i).getTemperature();
			String hum = airIndexBeans.get(i).getHumidity();
			String forma = airIndexBeans.get(i).getFormaldehyde();
			String aqi = airIndexBeans.get(i).getAqi();
			tem_sum = tem_sum+Float.parseFloat(tem);
			hum_sum = hum_sum+Float.parseFloat(hum==null?"0":hum);
			forma_sum = forma_sum+Float.parseFloat(forma);
			aqi_sum = aqi_sum+Float.parseFloat(aqi);
		}
		float tem_av =tem_sum/size;
		float hum_av = hum_sum/size;
		float forma_av = forma_sum/size;
		float aqi_av = aqi_sum/size;
		
		airIndexBean.setTemperature(tem_av+"");
		airIndexBean.setHumidity(hum_av+"");
		airIndexBean.setFormaldehyde(forma_av+"");
		airIndexBean.setAqi(aqi_av+"");
		airIndexBean.setYear(airIndexBeans.get(0).getYear());
		airIndexBean.setMonth(airIndexBeans.get(0).getMonth());
		airIndexBean.setDay(airIndexBeans.get(0).getDay());
		airIndexBean.setHour(airIndexBeans.get(0).getHour());
		
		return airIndexBean;
	}
	
	/**
	 * 判断本地是否有某个用户的数据
	 * @param username
	 * @return
	 */
	public boolean haveDataofUser(String username){
		boolean flag = false;
		Cursor c = db.rawQuery("select * from cat where username=?", new String[]{username});
		if(c!=null&&c.getCount()>0){
			flag = true;
		}else{
			flag = false;
		}
		if(c!=null){
			c.close();
			c = null;
		}
		return flag;
	}
	
	
	
	
	//数据删除
	/**
	 * 删除全部数据
	 */
	public void deleteData(){
		db.delete("cat", null, null);
	}
	
	//关闭数据库
	public void closeDB(){
		if(db!=null&&db.isOpen()){
			db.close();
		}
		
	}

}
