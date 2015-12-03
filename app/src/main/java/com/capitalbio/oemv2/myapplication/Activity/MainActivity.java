package com.capitalbio.oemv2.myapplication.Activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.capitalbio.oemv2.myapplication.Fragment.SlidingMenuFragment;
import com.capitalbio.oemv2.myapplication.R;
import com.capitalbio.oemv2.myapplication.View.ISelectedDeviceChange;
import com.capitalbio.oemv2.myapplication.View.SpinnerLoader;
import com.capitalbio.oemv2.myapplication.View.charview.ChartViewPBM;
import com.slidingmenu.lib.SlidingMenu;

/**
 * Created by chengkun on 15-11-4.
 */
public class MainActivity extends BaseActivity implements ISelectedDeviceChange, View.OnClickListener{


    public static final String TAG = "MainActivity";
    private RelativeLayout fmMainView;
    private ImageView icSelectedDevice;
    private Bitmap bmBracelet;
    private BitmapDrawable bmBraceletDrable;
    private SpinnerLoader spinnerView;
    private String currentDeviceName;

    // 侧滑菜单
    private SlidingMenu slidingMenu;

    @Override
    public void selectDeviceChange(SpinnerLoader.CirclePoint cp) {
        Log.d(TAG, "selected device changed current device is " + cp.deviceName);
        setCurrentDevice(cp);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ic_slected_device:
                startSelectedDevice(currentDeviceName);
                break;
        }

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void initChildLayout() {
        super.initChildLayout();

        rl.setBackgroundResource(R.drawable.bg_main);
        //在activity中设置手势监听

//        setNavigateBarBackGround(R.drawable.bg_main);
        setLeftTopIcon(R.drawable.ic_person);
        setIvTopRight(R.drawable.ic_share);
        setTvTopTitle(R.string.app_name);
        ivSplitLine.setBackgroundResource(R.color.mainSplitLine);

        fmMainView = (RelativeLayout) View.inflate(this, R.layout.activity_main, null);

        //设置对应设备大圈的图标
        icSelectedDevice = (ImageView) fmMainView.findViewById(R.id.ic_slected_device);
        bmBracelet = BitmapFactory.decodeResource(getResources(), R.drawable.ic_bracelet);
        bmBraceletDrable = new BitmapDrawable(bmBracelet);
        icSelectedDevice.setBackground(bmBraceletDrable);

        //取得滚轮view的引用
        spinnerView = (SpinnerLoader) fmMainView.findViewById(R.id.slid_spinner);
        spinnerView.setDeviceChangeListener(this);
        llBody.addView(fmMainView);

        icSelectedDevice.setOnClickListener(this);

        //设置初始选择的设备的名称
        currentDeviceName = getResources().getString(R.string.bracelete);

        initSlidingMenu();
    }


    private static Bitmap small(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(2.3f, 2.3f); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        return resizeBmp;
    }


    private void setCurrentDevice(SpinnerLoader.CirclePoint cp) {

        currentDeviceName = cp.deviceName;

        if (getResources().getString(R.string.bodyfat).equals(cp.deviceName)) {
            icSelectedDevice.setBackgroundResource(R.drawable.ic_body_fat);
        } else if (getResources().getString(R.string.glycolipid).equals(cp.deviceName)) {
            //设置血糖图片
        } else if (getResources().getString(R.string.bracelete).equals(cp.deviceName)) {
            icSelectedDevice.setBackgroundResource(R.drawable.ic_bracelet);
        } else if (getResources().getString(R.string.bloodpress).equals(cp.deviceName)) {
            icSelectedDevice.setBackgroundResource(R.drawable.ic_blood_press);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        spinnerView.invalidate();
    }

    private void startSelectedDevice(String deviceName) {
        Intent intent;

        if (deviceName.equals(getResources().getString(R.string.bloodpress))) {
            intent = new Intent(this, BloodPressActivity.class);
            startActivity(intent);
        }
        if (deviceName.equals(getResources().getString(R.string.bracelete))) {
            intent = new Intent(this, Bracelete2Activity.class);
            startActivity(intent);
        }
        if (deviceName.equals(getResources().getString(R.string.bodyfat))) {
            intent = new Intent(this, BodyFatActivity.class);
            startActivity(intent);
        }
    }

    /**
     * 侧滑菜单
     */
    private void initSlidingMenu() {

        // 获取屏幕宽度
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;

        // configure the SlidingMenu
        SlidingMenu menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        // 设置触摸屏幕的模式
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);

        // 设置滑动菜单视图的宽度
        menu.setBehindOffset(screenWidth / 3);
        // 设置渐入渐出效果的值
        menu.setFadeDegree(0.35f);
        /**
         * SLIDING_WINDOW will include the Title/ActionBar in the content
         * section of the SlidingMenu, while SLIDING_CONTENT does not.
         */


        //为侧滑菜单设置布局
        menu.setMenu(R.layout.leftmenu);

        getFragmentManager().beginTransaction().replace(R.id.fl_leftmenu, new SlidingMenuFragment()).commit();

        //把滑动菜单添加进所有的Activity中，可选值SLIDING_CONTENT ， SLIDING_WINDOW
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);

    }

    @Override
    protected void onPause() {
        super.onPause();
        spinnerView.invalidate();
    }

    public void edit(View view ){
        Intent i =new Intent();
        i.setClass(getApplicationContext(),ChartViewPBM.class);
        startActivity(i);
    }
}
