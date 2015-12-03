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

/*<<<<<<< .mine
	 
=======*/
	//public static String Url_Base = "http://192.168.33.51:8080/service";// http://182.92.244.204:18087/service
	public static String Url_Base = "http://182.92.244.204:18087/service";//http://192.168.33.51:8080/service    http://1.202.132.124:8081/service
//>>>>>>> .r7802
	public static String ApiKey_Url = "/app/lshd_app/apikey?";// apikey
	public static String RegisterKey_Url = "/auth/user";// 注册
	public static String LoginKey_Url = "/auth/login";// 登录
	public static String Checkname_Url = "/auth/user/check/";// 检测用户名
	// 验证码发送
	public static String sendMessage_Url = "/data/sms/checkMobile";
	//找回密码验证码发送
	public static String findMessgae_Url ="/data/sms";

	//重置密码
	public static String ResetPassword_Url="/auth/pwd";

	// 个人信息-修改用户信息
	public static String reviseUserInfo_Url = "/auth/update";
	// 上传头像
	public static String upLoadphoto_Url = "/auth/upfile";
	//上传数据
	public static String uploaddetail_Url ="/data/lshd_app?apikey=";
	//下载数据
	public static String downloaddetail_Url="/data/lshd_app/all?";
	//下载数据的总条数
	public static String downloadtotaldetail_Url="/data/lshd_app/limitCount?";
	//分批下载数据
	public static String batch_Download_Url="/data/lshd_app/limit?";
	//获取绑定mac
	public static String bindMacAddress_Url="/data/lshd_app/getBinding?";
	//绑定设备
	public static String bindDevice_Url="/data/lshd_app/bindDevice";
	//解绑设备
	public static String unbindDevice_Url="/data/lshd_app/unbindDevice";
	
	/**
	 * 设置手环目标
	 */
	public static String saveUserGoal_Url="/data/lshd_app/goal";

}
