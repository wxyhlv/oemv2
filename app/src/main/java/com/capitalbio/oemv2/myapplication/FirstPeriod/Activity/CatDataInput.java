package com.capitalbio.oemv2.myapplication.FirstPeriod.Activity;

import org.json.JSONException;

import com.alibaba.fastjson.JSONObject;
import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.Bean.AirCatInfo;
import com.capitalbio.oemv2.myapplication.Bean.AirIndexBean;
import com.capitalbio.oemv2.myapplication.FirstPeriod.Final.Base_Url;
import com.capitalbio.oemv2.myapplication.FirstPeriod.Net.HttpTools;
import com.capitalbio.oemv2.myapplication.FirstPeriod.Utils.HTG;
import com.capitalbio.oemv2.myapplication.FirstPeriod.Utils.ToastMaster;
import com.capitalbio.oemv2.myapplication.FirstPeriod.View.BubbleSelectValueView;
import com.capitalbio.oemv2.myapplication.FirstPeriod.View.DialogProgressCat;
import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.Utils.TimeStampCreater;
import com.capitalbio.oemv2.myapplication.Utils.UpLoader;
import com.capitalbio.oemv2.myapplication.Utils.UpLoaderAsyncTask;
import com.capitalbio.oemv2.myapplication.dialog.TimeSelectDialog;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CatDataInput extends Activity implements OnClickListener, UpLoaderAsyncTask.OnCallBack {
	
	private RelativeLayout rlback,rlsave;
	
	private Typeface fangzhengdahei,lantinghei;
	
	private TextView tvformaltext,tvformalunit,tvtemtext,tvtemunit,tvhumtext,tvhumunit,tvtvoctext,tvtvocunit;
	
	private BubbleSelectValueView bsvv_tem,bsvv_hum;
	
	private LinearLayout llmeasuretime;
	
	private TextView tvtesttime;
	
	private String yearselect,monthselect,dayselect,hourselect,minuteselect;
	
	private EditText etpm,etformal,ettem,ethum,ettvoc,etairpolu;
	
	private DialogProgressCat dialog;
	
	//是否在上传数据
	private boolean isUploading = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_catdatainput);
		initVariable();
		initView();
		//initViewData();
		initEvent();
		
	}
	
	private void initVariable(){
		//fangzhengdahei = Typeface.createFromAsset(getAssets(), "fangzhengdahei_GBK.ttf");
		//lantinghei = Typeface.createFromAsset(getAssets(), "lantinghei_GBK.ttf");
	}
	
	private void initView(){
		rlback = (RelativeLayout) this.findViewById(R.id.rl_catdatainput_back);
		rlsave = (RelativeLayout) this.findViewById(R.id.rl_catdatainput_save);
		
		tvformaltext = (TextView) this.findViewById(R.id.tv_catdatainput_formal_text);
		tvformalunit = (TextView) this.findViewById(R.id.tv_catdatainput_formal_unit);
		tvtemtext = (TextView) this.findViewById(R.id.tv_catdatainput_tem_text);
		tvtemunit = (TextView) this.findViewById(R.id.tv_catdatainput_tem_unit);
		tvhumtext = (TextView) this.findViewById(R.id.tv_catdatainput_hum_text);
		tvhumunit = (TextView) this.findViewById(R.id.tv_catdatainput_hum_unit);
		tvtvoctext = (TextView) this.findViewById(R.id.tv_catdatainput_tvoc_text);
		tvtvocunit = (TextView) this.findViewById(R.id.tv_catdatainput_tvoc_unit);
		//initTypeface();
		
		//bsvv_tem = (BubbleSelectValueView) this.findViewById(R.id.bsvv_cat_tem);
		//bsvv_hum = (BubbleSelectValueView) this.findViewById(R.id.bsvv_cat_hum);
		
		llmeasuretime = (LinearLayout) this.findViewById(R.id.ll_catinput_time);
		
		tvtesttime = (TextView) this.findViewById(R.id.tv_catdatainput_testtime);

		etpm = (EditText) this.findViewById(R.id.et_catdatainput_pm);
		etformal = (EditText) this.findViewById(R.id.et_catdatainput_formal);
		ettem  = (EditText) this.findViewById(R.id.et_catdatainput_tem);
		ethum = (EditText) this.findViewById(R.id.et_catdatainput_hum);
		ettvoc = (EditText) this.findViewById(R.id.et_catdatainput_tvoc);
		etairpolu = (EditText) this.findViewById(R.id.et_catdatainput_tapi);
		
	}
	
	private void initTypeface(){
		tvformaltext.setTypeface(fangzhengdahei);
		tvformalunit.setTypeface(lantinghei);
	}
	
	private static final int ZERO = 0;
	private static final int ONE = 1;
	private static final int TWO = 2;
	private static final int THREE = 3;
	
	private void initEvent(){
		rlback.setOnClickListener(this);
		rlsave.setOnClickListener(this);
		
		llmeasuretime.setOnClickListener(this);
		
		etformal.addTextChangedListener(new TextWatcher() {
			
			String s_before ="";
			String s_after = "";
			
			int befor;
			int after;
			
			//
			
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
				s_before = s.toString() ;
				this.after = after;
			}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count){
				// TODO Auto-generated method stub
				
				s_after = s.toString();
				this.befor = before;
			}
			
			
			
			@Override
			public void afterTextChanged(Editable s){
				// TODO Auto-generated method stub
				if(after>befor){
				   	//增添
					int len = s_before.length();
					if(len>=4){
						s.delete(4, 5);
						ToastMaster.showToast(CatDataInput.this, "甲醛数据不合法，请输入0.00-3.00的数值");
					}
					
					switch (len){
					case ZERO:
						//准备输入第一个字符
						
						//第一个字符不能输入“.”
						if(s_after.equals(".")){
							s.delete(0, 1);
							ToastMaster.showToast(CatDataInput.this, "甲醛数据不合法，请输入0.00-3.00的数值");
						}else{
							//第一个数不能大于3
							int firstbit = Integer.parseInt(s_after);
							if(firstbit>3){
								s.delete(0, 1);
								ToastMaster.showToast(CatDataInput.this, "甲醛数据不合法，请输入0.00-3.00的数值");
							}
						}
						break;
					case ONE:
						//准备输入第二个字符
							if(!s_after.contains(".")){
								s.delete(1, 2);
								ToastMaster.showToast(CatDataInput.this, "甲醛数据不合法，请输入0.00-3.00的数值");
							}
						break;
					case TWO:
						//准备输入第三个字符
						
						break;
					case THREE:
						//准备输入第四个字符
						if(s_after.equals("0.00")){
							s.delete(3, 4);
							ToastMaster.showToast(CatDataInput.this, "甲醛数据不合法，请输入0.00-3.00的数值");
						}
						break;

					}
					
				}
				if(after<befor){
					//删改
					
				}
			     
				
			}
		});
		
		ettvoc.addTextChangedListener(new TextWatcher() {
			
			String s_before ="";
			String s_after = "";
			
			int befor;
			int after;
			
			//
			
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
				s_before = s.toString() ;
				this.after = after;
			}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count){
				// TODO Auto-generated method stub
				
				s_after = s.toString();
				this.befor = before;
			}
			
			
			
			@Override
			public void afterTextChanged(Editable s){
				// TODO Auto-generated method stub
				if(after>befor){
				   	//增添
					int len = s_before.length();
					if(len>=4){
						s.delete(4,5);
						ToastMaster.showToast(CatDataInput.this, "tvoc数据不合法，请输入0.00-9.99的数值");
					}
					
					switch (len){
					case ZERO:
						//准备输入第一个字符
						
						//第一个字符不允许输入"."
						if(s_after.equals(".")){
							s.delete(0, 1);
							ToastMaster.showToast(CatDataInput.this, "tvoc数据不合法，请输入0.00-9.99的数值");
						}
						
						
						break;
					case ONE:
						//准备输入第二个字符
							if(!s_after.contains(".")){
								s.delete(1, 2);
								ToastMaster.showToast(CatDataInput.this, "tvoc数据不合法，请输入0.00-9.99的数值");
							}
						break;
					case TWO:
						//准备输入第三个字符
						
						break;
					case THREE:
						//准备输入第四个字符
						if(s_after.equals("0.00")){
							s.delete(3, 4);
							ToastMaster.showToast(CatDataInput.this, "tvoc数据不合法，请输入0.00-9.99的数值");
						}
						break;

					}
					
				}
				if(after<befor){
					//删改
					
				}
			     
				
			}
		});
	}
	
	private void initViewData(){
		tvtesttime.setText(HTG.currYear()+"-"+HTG.currMonth()+"-"+HTG.currDay()+" "+HTG.currHour()+":"+HTG.currMinute());
		yearselect = HTG.currYear()+"";
		monthselect = HTG.currMonth()+"";
		dayselect = HTG.currDay()+"";
		hourselect = HTG.currHour()+"";
		minuteselect = HTG.currMinute()+"";
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_catdatainput_back:
			System.out.println("fanhui");
			this.finish();
			break;
		case R.id.rl_catdatainput_save:
			//保存
save();
			
			break;
		case R.id.ll_catinput_time:
			TimeSelectDialog mtimeselect = new TimeSelectDialog(
                    CatDataInput.this);
			mtimeselect.show();
            mtimeselect.setOnCancelListener(new TimeSelectDialog.onCancel(){
				@Override
				public void onCancel() {
					// TODO Auto-generated method stub
				}
			});
            mtimeselect.setOnOkListener(new TimeSelectDialog.onOK(){
				@Override
				public void onOk(String month, String day, String hour, String minute) {
					//TODO Auto-generated method stub
					tvtesttime.setText(HTG.currYear()+"-"+month+"-"+day+" "+hour+":"+minute);
					yearselect = HTG.currYear()+"";
					monthselect = month;
					dayselect = day;
					hourselect = hour;
					minuteselect = minute;
				}
			});
			break;

		default:
			break;
		}
	}


	//保存数据

	private void save(){
		if (yearselect == null || yearselect.equals("") || monthselect == null || monthselect.equals("") || dayselect == null || dayselect.equals("") || hourselect == null || hourselect.equals("") || minuteselect == null || minuteselect.equals("")) {
			ToastMaster.showToast(this,"请选择测量时间");
			return;
		}

		String pm = etpm.getText().toString();
		String formal = etformal.getText().toString();
		String tem = ettem.getText().toString();
		String hum = ethum.getText().toString();
		String tvoc = ettvoc.getText().toString();
		String tapi = etairpolu.getText().toString();

		if(pm==null||pm.equals("")){
			ToastMaster.showToast(this,"请输入pm2.5值");
			return;
		}
		if(formal==null||formal.equals("")){
			ToastMaster.showToast(this,"请输入甲醛值");
			return;
		}
		if(tem==null||tem.equals("")){
			ToastMaster.showToast(this,"请输入温度值");
			return;
		}
		if(hum==null||hum.equals("")){
			ToastMaster.showToast(this,"请输入湿度值");
			return;
		}
		if(tvoc==null||tvoc.equals("")){
			ToastMaster.showToast(this,"请输入tvoc值");
			return;
		}
		if(tapi==null||tapi.equals("")){
			ToastMaster.showToast(this,"请输入空气污染指数值");
			return;
		}

		TimeStampCreater timeStampCreater = new TimeStampCreater();
		String timestamp = ""+timeStampCreater.create(yearselect,monthselect,dayselect,hourselect,minuteselect);

		AirCatInfo airCatInfo = new AirCatInfo();
		airCatInfo.setTestTime(timestamp);
		airCatInfo.setCh2(formal);
		airCatInfo.setHumity(hum);
		airCatInfo.setMac(MyApplication.getInstance().getAircatDeviceMac());
		airCatInfo.setPm25(pm);
		airCatInfo.setPollutionLevel(tapi);
		airCatInfo.setTemperature(tem);
		airCatInfo.setTvoc(tvoc);

		UpLoader upLoader = new UpLoader(this);
		upLoader.setOnCallBack(this);
		upLoader.go(airCatInfo);

	}


	@Override
	public void lesgo() {
		Log.i("info","====开始====");
	}

	@Override
	public void over(Object o) {
		Log.i("info","====结束===="+(String)o);
	}
}
