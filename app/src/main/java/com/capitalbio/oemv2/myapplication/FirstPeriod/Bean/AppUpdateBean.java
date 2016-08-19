package com.capitalbio.oemv2.myapplication.FirstPeriod.Bean;


/**
 * App更新Bean
 * 
 * @author xusun-n
 * 
 */
public class AppUpdateBean {
	private String appname = "";
	private String apkname = "";
	private String verName = "";
	private int verCode = 0;
	private String prompt = "";
	
	public AppUpdateBean() {
		// TODO Auto-generated constructor stub
	}
	
	public AppUpdateBean(String appname, String apkname, String verName,String prompt,
			int verCode) {
		this.appname = appname;
		this.apkname = apkname;
		this.verName = verName;
		this.prompt = prompt;
		this.verCode = verCode;
		
	}

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	public String getApkname() {
		return apkname;
	}

	public void setApkname(String apkname) {
		this.apkname = apkname;
	}

	public String getVerName() {
		return verName;
	}

	public void setVerName(String verName) {
		this.verName = verName;
	}

	public int getVerCode() {
		return verCode;
	}

	public void setVerCode(int verCode) {
		this.verCode = verCode;
	}

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}
	
}
