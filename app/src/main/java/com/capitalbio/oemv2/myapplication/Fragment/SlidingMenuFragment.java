package com.capitalbio.oemv2.myapplication.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.pc.ioc.db.table.KeyValue;
import com.bumptech.glide.Glide;
import com.capitalbio.oemv2.myapplication.Activity.LoginActivity;
import com.capitalbio.oemv2.myapplication.Activity.PersonalDetailsAcitivity;
import com.capitalbio.oemv2.myapplication.Activity.SettingActivity;
import com.capitalbio.oemv2.myapplication.Activity.UserHelpActivity;
import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.Bean.LoginUser;
import com.capitalbio.oemv2.myapplication.Bean.Version;
import com.capitalbio.oemv2.myapplication.BraceleteLib.BraceleteDevices;
import com.capitalbio.oemv2.myapplication.Const.Base_Url;
import com.capitalbio.oemv2.myapplication.Devices.BloodPressDevice.ByteUtil;
import com.capitalbio.oemv2.myapplication.NetUtils.HttpTools;
import com.capitalbio.oemv2.myapplication.NetUtils.NetTool;
import com.capitalbio.oemv2.myapplication.QR.MipcaActivityCapture;
import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.Utils.OemLog;
import com.capitalbio.oemv2.myapplication.Utils.PreferencesUtils;
import com.capitalbio.oemv2.myapplication.Utils.Utility;
import com.capitalbio.oemv2.myapplication.View.GlideCircleTransform;
import com.capitalbio.oemv2.myapplication.View.UserinfoPopupWindow;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Key;

import cz.msebera.android.httpclient.Header;

/**
 * Created by chengkun on 15-11-25.
 */
public class SlidingMenuFragment extends Fragment implements View.OnClickListener {

    private ScrollView llParentView;
    private ImageView ivSlideUserPhoto;
    private String TAG = "SlidingMenuFragment";
    private Context context;
    private TextView username;
    private Version version;
    private String appAddr, versionNo="";
    private String updateContent = "优化了性能,用户体验将更好！";
    private ProgressDialog progressBar = null;
    private String[] premissions=new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private File file;
    private float totalLenght,currentLenght;
    private Handler handler=new Handler();
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        llParentView = (ScrollView) inflater.inflate(R.layout.fg_slidingmenu, null);
        context = MyApplication.getInstance();
        version = new Version(context);
        initView();
        return llParentView;
    }

    private void testVersion() {
        try {
            //构造请求json对象
            final JSONObject jsonObject = new JSONObject();
            JSONObject dataObject = new JSONObject();
            jsonObject.put("apikey", MyApplication.getInstance().apikey);
            dataObject.put("appid", "app1");
            dataObject.put("versionNo", version.getVerName().replace(".", ""));
            jsonObject.put("data", dataObject.toString());
            HttpTools.post(context, Base_Url.Url_Base + Base_Url.versionUpdate, jsonObject, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String result = new String(responseBody);
                    Log.i("version1", result);
                    try {
                        JSONObject jsonObject1 = new JSONObject(result);
                        int code = jsonObject1.optInt("code");
                        if (code == 0) {
                            String data = jsonObject1.optString("data");
                            JSONObject data1 = new JSONObject(data);
                            Log.i("version", "data:" + data);
                            appAddr = data1.optString("appAddr");
                            Log.i("version", "appAddr:" + appAddr);
                            versionNo = data1.optString("importantNo") + "_" + data1.optString("formalNo") + "_" + data1.optString("testNo");
                            if ("".equals(data1.optString("updateContent"))) {
                                updateContent = "优化了性能,用户体验将更好！";
                            } else {
                                updateContent = data1.optString("updateContent");
                            }
                            updateDialog();
                        } else if (code == 20) {
                            Toast.makeText(context, "当前已经是最新版本" + version.getVerName() + "!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Toast.makeText(context, "服务器异常,稍后再试", Toast.LENGTH_LONG).show();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("检测到最新版本" + versionNo)
                .setMessage(updateContent)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (version.havePermissions(premissions)){
                            dialog.dismiss();
                            new MyTask1().execute(appAddr);
                        }else {
                            ActivityCompat.requestPermissions(getActivity(), premissions,2);
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();
    }
    private void progressDialog(){
        progressBar=new ProgressDialog(getActivity());
        progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressBar.setTitle("下载中...");
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.setCancelable(false);
        progressBar.setMax(100);
        progressBar.show();
    }
    class MyTask1 extends AsyncTask<String,Integer,byte[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog();
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
                        totalLenght=con1.getContentLength();//总数据量
                        byte[] bt=new byte[4096];
                        int len;
                        currentLenght=0;
                        while ((len=is.read(bt))>0){
                            fs.write(bt,0,len);
                            currentLenght+=len;
                            Log.i("version", "------totalLenght:"+totalLenght+"------currentLenght:"+currentLenght);
                            float mm=currentLenght*100;
                            int aa=(int)(mm/totalLenght);
                            if (aa<0){
                                aa=100;
                            }
                            Log.i("version", "------比例:"+aa);
                            publishProgress(aa);
                        }
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
                Toast.makeText(getActivity(),"SD卡未成功加载！",Toast.LENGTH_SHORT).show();
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
            progressBar.dismiss();
            if (totalLenght==currentLenght){
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(android.content.Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
                startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());
            }else{
                Toast.makeText(getActivity(),"下载出错！",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        LoginUser user = MyApplication.getInstance().getCurrentUser();
        Log.i(TAG, "IMAGELOADER_NO");
        if (user != null) {
            byte[] bytePic = user.getHeadPic();
            Log.i(TAG, bytePic == null ? "null" : "has");
            if (bytePic != null && bytePic.length > 0) {
                  /*  Bitmap bitmap = ByteUtil.byteToBitmap(bytePic);
                    ivSlideUserPhoto.setImageBitmap(bitmap);*/
                Glide.with(context)
                        .load(bytePic)
                        .transform(new GlideCircleTransform(context)).into(ivSlideUserPhoto);
            } else {
                Glide.with(context)
                        .load(R.drawable.ic_scene)
                        .transform(new GlideCircleTransform(context)).into(ivSlideUserPhoto);


            }

            username.setText(user.getUsername());

        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initView() {
        ivSlideUserPhoto = (ImageView) llParentView.findViewById(R.id.iv_slide_user_photo);
        username = (TextView) llParentView.findViewById(R.id.tv_userName);
        ivSlideUserPhoto.setOnClickListener(this);
        llParentView.findViewById(R.id.ll_logout).setOnClickListener(this);
        String name = MyApplication.getInstance().getCurrentUserName();
        if (name != null && !"".equals(name)) {
            username.setText(name);
        } else {
            username.setText("颐健谱");
        }
        llParentView.findViewById(R.id.rl_userhelp).setOnClickListener(this);
        llParentView.findViewById(R.id.rl_versionUp).setOnClickListener(this);

        //设置
        llParentView.findViewById(R.id.rl_slidingmenue_setting).setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_versionUp:
                if (NetTool.isNetwork(context, true)) {
                    testVersion();
                }
                break;
            case R.id.rl_userhelp:
                Utility.startActivity(context, UserHelpActivity.class);
                break;
            case R.id.iv_slide_user_photo:
                if (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.CAMERA},
                            1
                    );
                } else {
                    startUserInfoActivity();

                }
                break;
            case R.id.ll_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("确定退出吗？");
                builder.setTitle("提示");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       /* if (NetTool.isNetwork(context, true)) {
                            logout();

                        }*/

                        //TODO 清除数据
                        try {
                            LoginUser user = MyApplication.getDB().selector(LoginUser.class).where("isLogin", "=", "true").findFirst();
                            user.setIsLogin("false");
                            user.setExitlogintime(System.currentTimeMillis());
                            MyApplication.getDB().saveOrUpdate(user);
                            MyApplication.getInstance().exit();
                        } catch (DbException e) {
                            e.printStackTrace();
                        }

                        BraceleteDevices.getInstance().disconnect();
                        //取消所有通知
                        NotificationManager nm = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                        nm.cancelAll();
                        Utility.startActivity(context, LoginActivity.class);
                        dialog.dismiss();
                        Toast.makeText(context, "注销成功", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override


                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }

                });
                builder.create().show();

                break;
            //设置
            case R.id.rl_slidingmenue_setting:
                Utility.startActivity(getActivity(), SettingActivity.class);
                break;
        }
    }

    private void startUserInfoActivity() {
        Intent intent = new Intent(getActivity(), PersonalDetailsAcitivity.class);
        startActivity(intent);
    }

    //注销
    public void logout() {


        try {
            //构造请求json对象
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("apikey", MyApplication.getInstance().apikey);//所有接口必填
            Log.e("apikey", "apikey " + MyApplication.getInstance().apikey);
            jsonObject.put("username", MyApplication.getInstance().getCurrentUserName());
            jsonObject.put("token", MyApplication.getInstance().getCurentToken());
            OemLog.log(TAG, ".." + jsonObject.toString());
            //请求短信验证码
            HttpTools.post(context, Base_Url.Url_Base + Base_Url.logout_url, jsonObject, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String result = new String(responseBody);
                    OemLog.log(TAG, result);
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        int code = jsonObject.optInt("code");
                        if (code == 11) {
                            MyApplication.getInstance().exit();
                            Toast.makeText(context, "用户未登陆,请重新登陆", Toast.LENGTH_SHORT).show();
                            Utility.startActivity(context, LoginActivity.class);
                        } else if (code == 4) {
                            Toast.makeText(context, "未找到APIKEY", Toast.LENGTH_SHORT).show();
                        } else if (code == 0) {
                            Toast.makeText(context, "注销成功", Toast.LENGTH_SHORT).show();
                            //TODO 清除数据
                            try {
                                LoginUser user = MyApplication.getDB().selector(LoginUser.class).where("isLogin", "=", "true").findFirst();
                                user.setIsLogin("false");
                                user.setExitlogintime(System.currentTimeMillis());
                                MyApplication.getDB().saveOrUpdate(user);
                                MyApplication.getInstance().exit();
                            } catch (DbException e) {
                                e.printStackTrace();
                            }

                            BraceleteDevices.getInstance().disconnect();
                            //取消所有通知
                            NotificationManager nm = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                            nm.cancelAll();
                            Utility.startActivity(context, LoginActivity.class);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    OemLog.log(TAG, "服务器请求失败");
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if ( grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startUserInfoActivity();
                } else{
                    Toast.makeText(context, "没有相机权限！", Toast.LENGTH_SHORT).show();
                }

                return;
            }
            case 2:{
                if(grantResults.length>0 && version.havePermissions(permissions)){
                    new MyTask1().execute(appAddr);
                    progressBar.dismiss();
                    //已获取权限
                }else{
                    //权限被拒绝
                    version.showMessageOKCancel("当前应用缺少必要权限\n" +
                                    "请点击“设置”-->“权限”或者“权限管理”打开所需权限",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    intent.setData(Uri.parse("package:"+ getActivity().getPackageName()));
                                    startActivity(intent);
                                }
                            });
                }
                return;
            }
        }
    }
}

