package com.capitalbio.oemv2.myapplication.Const;

public class Base_Url {
	public final static int ApiKey = 1;// apikey
	public final static int RegisterKey = 2;// 注册
	public final static int LoginKey = 3;// 登录
	public final static int Checkname_key = 4;// 检测用户名
	// 验证码发送
	public final static int sendMessage_key = 5;

    //重置密码
	public final static int ResetPassword_key = 6;
	// 个人信息-修改用户信息
	public final static int reviseUserInfo_key = 100;
	// 上传头像
	public final static int upLoadphoto_Url_key = 99;
   //  上传数据
	public final static int uploadDetail_Url_key =101;
	//下载数据
	public final static int downloadDetail_Url_key=102;
	// 忘记密码验证码请求
	public final static int findSendSMS_Url_key=103;


	//public static String Url_Base = "http://192.168.33.51:8080/service";// http://182.92.244.204:18087/service
	//public static String Url_Base = "http://192.168.9.32:8080/service";//http://192.168.33.51:8080/service    http://1.202.132.124:8081/service
    public static String Url_Base = "http://101.200.182.112:18088/restservice";//http://192.168.33.51:8080/service    http://1.202.132.124:8081/service

	public static String ApiKey_Url = "/app/apikey?";// apikey
	public static String RegisterKey_Url = "/auth/user";// 注册
	public static String LoginKey_Url = "/auth/login";// 登录
	public static String getUserInfo = "/auth/userinfo";// 获取用户信息
	public static String CheckNameExist_Url = "/auth/user/check";// 检测用户名
    public static String logout_url = "/auth/logout";//推销
	// 验证码发送
	public static String sendMessage_Url = "/message/sms";
	//上传数据接口
	public static String uploadData_Url = "/monitor/data/upload";
	//空气猫的分享
	public static String shareAircat_Url = "/monitor/share";
	//空气猫的接收分享
	public static String receiveshareAircat_Url = "/monitor/sharelist";
	//重置密码
	public static String ResetPassword_Url="/auth/pwd/reset";
	//修改密码
	public static String ModifyPassword_Url="/auth/pwd/update";

	// 个人信息-修改用户信息
	public static String reviseUserInfo_Url = "/auth/update";
	// 上传头像
	public static String upLoadphoto_Url = "/auth/upfile";
	//上传数据
	//下载数据
	public static String history_aircat_Url="/monitor/data/hour";
	// 下载总数据
	public static String downloadtotalUrl="/monitor/data/all";
	//下载数据count
	public static String downloadcount="/monitor/data/count";
	//分批下载数据
	public static String batch_Download_Url="/monitor/data/limit";
	//按天抽样下载
	public static String downloaddayUrl="/monitor/data/day";


	//获取绑定设备
	public static String getBindDevice_Url="/monitor/device/binding";
	//4.11获得当前绑定设备
	public static String getCurentBindDevice_URL="/monitor/device/currentBinding";

	public static String updateDeviceName_URL="/monitor/device/updateName";

	//绑定设备
	public static String bindDevice_Url="/monitor/device/bind";
	//解绑设备
	public static String unbindDevice_Url="/monitor/device/unbind";

	//获取空气猫推送消息
	public static String monitorShareList_Url = "/monitor/sharelist";

	//修改密码
	public static String modifypwd_url = "/auth/pwd/update";

	
	/**
	 * 设置手环目标
	 */
	public static String saveUserGoal_Url="/monitor/goal/upload";

	//获取最新数据WiFi
	public static final String AIRCAT_WIFI_DATA = "/monitor/data/latest";
	//获取最新数据WiFi
	public static final String getDataByInternet = "/monitor/data/latest";

	//版本更新
	public static final String versionUpdate="/app/version";

	//账号校验
	public static final String verifyaccount = "/auth/checkuser";
}
