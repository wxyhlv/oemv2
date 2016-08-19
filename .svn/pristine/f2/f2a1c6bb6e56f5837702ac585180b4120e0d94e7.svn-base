package com.capitalbio.oemv2.myapplication.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.Bean.LoginUser;
import com.capitalbio.oemv2.myapplication.Bean.Version;
import com.capitalbio.oemv2.myapplication.Const.Base_Url;
import com.capitalbio.oemv2.myapplication.NetUtils.HttpTools;
import com.capitalbio.oemv2.myapplication.NetUtils.NetTool;
import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.Utils.PreferencesUtils;
import com.capitalbio.oemv2.myapplication.Utils.Utility;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.ex.DbException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import cz.msebera.android.httpclient.Header;


/**
 * 第一次进入加载页，并附带自动更新功能检查
 * @author jiantao.tu
 *
 */
public class LoadActivity extends Activity implements View.OnClickListener{

	private static final int REQUEST_PERMISSON_SUCCESSFULL = 200;
	private static final String TAG = "LoadActivity";
	private ImageView iv;
	private Version version;
	private ProgressDialog mProgressDialog = null;
	private String appAddr,versionNo;
	private boolean isForceUpdate=false;
	private String updateContent="";
	private View vv,vv1;
	private Button bd_cancel,bd_ensure;
	private TextView tv_dTitle,cancel_message;
	private ProgressBar progressBar;
	private File file;
	private int totalLenght,total;
	private TextView partSize,totalSize;
	private Dialog dVersion,dVersion1;
	private String[] premissions=new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
			Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE};
	private Handler handler=new Handler();

	//	private Bitmap bitmap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);  //无title
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);  //全屏*/

		/*if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_SETTINGS) == PackageManager.PERMISSION_GRANTED)) {
			//your code that requires permission
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_SETTINGS},
					REQUEST_PERMISSON_SUCCESSFULL);
		}*/

		iv=new ImageView(this);
		setContentView(iv);
		MyApplication.getInstance().addActivity(this);

		//iv.setImageResource(R.drawable.load);
		iv.setScaleType(ScaleType.FIT_XY);
		Glide.with(this).load(R.drawable.load).into(iv);
		version=new Version(LoadActivity.this);
		progressDialog();
		if (NetTool.isNetwork(LoadActivity.this,true)){
			Log.i("version", version.getVerName()+"------code");
			testVersion();
		}else {
			intentSkip();
		}


	}

	private void testVersion() {
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setMessage("正在加载,请稍后...");
		mProgressDialog.setIndeterminate(true);
		mProgressDialog.setCancelable(false);
		mProgressDialog.setCanceledOnTouchOutside(false);
		try {
			//构造请求json对象
			final JSONObject jsonObject = new JSONObject();
			JSONObject dataObject = new JSONObject();
			jsonObject.put("apikey", MyApplication.getInstance().apikey);
			dataObject.put("appid","app1");
			dataObject.put("versionNo",version.getVerName().replace(".",""));
			jsonObject.put("data", dataObject.toString());

			HttpTools.post(this, Base_Url.Url_Base + Base_Url.versionUpdate, jsonObject, new AsyncHttpResponseHandler() {
				@Override
				public void onStart() {
					mProgressDialog.show();
					super.onStart();
				}
				@Override
				public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
					if (mProgressDialog.isShowing()) {
						mProgressDialog.dismiss();
					}
					String result = new String(responseBody);
					Log.i("version",result);
					try {
						JSONObject jsonObject1 = new JSONObject(result);
						int code = jsonObject1.optInt("code");
						if(code == 0){
							Log.i("version","code:"+code);
							String data=jsonObject1.optString("data");
							JSONObject data1=new JSONObject(data);
							Log.i("version","data:"+data);
							appAddr=data1.optString("appAddr");
							Log.i("version","appAddr:"+appAddr);
							versionNo=data1.optString("importantNo")+"_"+data1.optString("formalNo")+"_"+data1.optString("testNo");
							int isMandatory=data1.optInt("isMandatory");
							if (isMandatory==0){
								isForceUpdate=false;
							}else{
								isForceUpdate=true;
							}
							if ("".equals(data1.optString("updateContent"))){
								updateContent="优化了性能,用户体验将更好！";
							}else {
								updateContent=data1.optString("updateContent");
							}
							initDate();
							updateDialog();
						}
						else if(code == 20){
							intentSkip();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				@Override
				public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
					if (mProgressDialog.isShowing()) {
						mProgressDialog.dismiss();
					}
					//Toast.makeText(LoadActivity.this, "服务器异常", Toast.LENGTH_LONG).show();
					intentSkip();
				}
				@Override
				public void onCancel() {
					super.onCancel();
					if (mProgressDialog.isShowing()) {
						mProgressDialog.dismiss();
					}
				}
			});
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void updateDialog(){
		dVersion=new Dialog(LoadActivity.this,R.style.dialog1);
		dVersion.setContentView(vv,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));
		Window dw=dVersion.getWindow();
		dw.setGravity(Gravity.CENTER);//在屏幕中位置
		WindowManager.LayoutParams lp=dw.getAttributes();
		lp.height= ViewGroup.LayoutParams.WRAP_CONTENT;//高度自适应
		lp.width= (int) (getResources().getDisplayMetrics().widthPixels*0.7);//宽度为屏幕宽度的0.6
		dw.setAttributes(lp);
		dVersion.setCancelable(false);
		dVersion.show();
	}

	private void initDate() {
		vv=getLayoutInflater().inflate(R.layout.version_update_dialog,null);
		bd_cancel= (Button) vv.findViewById(R.id.bd_cancel);
		bd_ensure= (Button) vv.findViewById(R.id.bd_ensure);
		bd_cancel.setOnClickListener(this);
		bd_ensure.setOnClickListener(this);
		tv_dTitle= (TextView) vv.findViewById(R.id.tv_dTitle);
		cancel_message= (TextView) vv.findViewById(R.id.cancel_message);
		if (isForceUpdate){
			tv_dTitle.setText("版本需要升级使用");
			bd_cancel.setText("退出");
		}else {
			tv_dTitle.setText("版本是否升级");
			bd_cancel.setText("取消");
		}
		cancel_message.setText(updateContent);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		MyApplication.getInstance().removeActivity(this);
		handler.removeCallbacksAndMessages(null);
	}

	/*@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		OemLog.log(TAG, "request result is " + requestCode);
	}*/
	public void intentSkip(){
		iv.postDelayed(new Runnable() {

			@Override
			public void run() {
				Intent i = new Intent();
				Log.e("isFirstEnterss", PreferencesUtils.getString(LoadActivity.this, "ss", "defalut"));
				SharedPreferences preferences = getSharedPreferences("oemv2Preview", Context.MODE_PRIVATE);
				String isFirstEnter = preferences.getString("isFirstEnter", "defaultname");
				Log.e("isFirstEnter", isFirstEnter + "  isFirstEnter");
				if ("1".equals(isFirstEnter)) {
					try {
						LoginUser lastuser = MyApplication.getDB().selector(LoginUser.class).where("isLogin", "=", "true").findFirst();
						Log.e("lastuser", lastuser == null ? "null" : "notnull");
						if (lastuser != null && lastuser.isRememberName()) {
							//   autoLogin(lastuser);
							i.setClass(LoadActivity.this, MainActivity.class);
						} else {
							i.setClass(LoadActivity.this, LoginActivity.class);
						}
					} catch (DbException e) {
						e.printStackTrace();
					}

				} else {
					i.setClass(LoadActivity.this, PreviewActivity.class);
				}
				startActivity(i);
				finish();
			}


		}, 1000);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.bd_cancel:
				if (bd_cancel.getText().toString().equals("取消")){
					dVersion.dismiss();
					intentSkip();
				}else{
					dVersion.dismiss();
					finish();
				}
				break;
			case R.id.bd_ensure:
				if (version.havePermissions(premissions)){
					dVersion.dismiss();
					new MyTask().execute(appAddr);
				}else {
					ActivityCompat.requestPermissions(LoadActivity.this, premissions,200);
				}
				break;
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		switch (requestCode){
			case 200:
				if(grantResults.length>0 && version.havePermissions(permissions)){
					new MyTask().execute(appAddr);
					dVersion.dismiss();
					//已获取权限
				}else{
					//权限被拒绝

					version.showMessageOKCancel("当前应用缺少必要权限\n" +
									"请点击“设置”-->“权限”或者“权限管理”打开所需权限",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
									intent.setData(Uri.parse("package:"+ getPackageName()));
									startActivity(intent);
								}
							});
				}
				break;
		}
	}

	//下载对话框
	public void progressDialog() {
		vv1 = getLayoutInflater().inflate(R.layout.progress_dialog, null);
		progressBar = (ProgressBar) vv1.findViewById(R.id.progressBar);
		partSize = (TextView) vv1.findViewById(R.id.partSize);
		totalSize = (TextView) vv1.findViewById(R.id.totalSize);
		dVersion1 = new Dialog(LoadActivity.this, R.style.dialog1);
		dVersion1.setContentView(vv1, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));
		Window dw = dVersion1.getWindow();
		dw.setGravity(Gravity.CENTER);//在屏幕中位置
		WindowManager.LayoutParams lp = dw.getAttributes();
		//Display display=getWindowManager().getDefaultDisplay();
		lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;//高度自适应
		lp.width = (int) (LoadActivity.this.getResources().getDisplayMetrics().widthPixels * 0.7);//宽度为屏幕宽度的0.6
		dw.setAttributes(lp);
		dVersion1.setCancelable(false);
	}

	class MyTask extends AsyncTask<String,Integer,byte[]> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dVersion1.show();
		}
		@Override
		protected byte[] doInBackground(String... params) {
			boolean isSDCardMounted = Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED);
			if (isSDCardMounted) {
				String aPath = Environment.getExternalStorageDirectory().getAbsolutePath();
				String filePath=aPath+ File.separator+"CapitalBio/download"+File.separator;
				//String filePath=aPath+ File.separator+"DiabetesManagement_"+versionNo+".apk";
				Log.i("version", "------filePath:"+filePath);
				File ff=new File(filePath);
				if (!ff.exists()){
					Log.i("version", "------ff根目录没有创建");
					ff.mkdirs();
				}else{
					Log.i("version", "------ff根目录创建le");
				}
				try {
					URL url=new URL(appAddr);
					HttpURLConnection con1= (HttpURLConnection) url.openConnection();
					con1.setRequestMethod("GET");
					con1.connect();
					if (con1.getResponseCode()==con1.HTTP_OK){
						file=new File(ff,"yijianpu_"+versionNo+".apk");
						if (!file.exists()){
							Log.i("version", "------file没有创建");
							file.createNewFile();
						}
						Log.i("version", "------file:"+file);
						FileOutputStream fs=new FileOutputStream(file);
						InputStream is=con1.getInputStream();
						totalLenght=con1.getContentLength();
						Log.i("version", "------totalLenght:"+totalLenght);
						handler.post(new Runnable() {
							@Override
							public void run() {
								progressBar.setMax(totalLenght);
								totalSize.setText("/"+totalLenght/1024+"kb");
							}
						});

						byte[] bt=new byte[1024];
						int len=0;
						total=0;
						while ((len=is.read(bt))>0){
							total+=len;
							final int finalTotal = total;
							handler.post(new Runnable() {
								@Override
								public void run() {
									partSize.setText((finalTotal /1024)+"kb");
								}
							});
							publishProgress(total);
							fs.write(bt,0,len);
						}
						Log.i("version", "------total:"+total);
						fs.flush();
						is.close();
						fs.close();
						con1.disconnect();
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
					Log.i("version","e:"+e.toString());
				}
			} else {
				Toast.makeText(LoadActivity.this,"SD卡未成功加载！",Toast.LENGTH_SHORT).show();
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			progressBar.setProgress(values[0]);
		}

		@Override
		protected void onPostExecute(byte[] bytes) {
			super.onPostExecute(bytes);
			dVersion1.dismiss();
			if (totalLenght==total){
				Intent intent = new Intent();
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.setAction(android.content.Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
				startActivity(intent);
				android.os.Process.killProcess(android.os.Process.myPid());
				finish();
			}else{
				Toast.makeText(LoadActivity.this,"下载出错！",Toast.LENGTH_SHORT).show();
				intentSkip();
			}
		}
	}


}
