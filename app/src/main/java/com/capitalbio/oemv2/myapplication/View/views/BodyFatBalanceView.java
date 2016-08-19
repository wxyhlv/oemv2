package com.capitalbio.oemv2.myapplication.View.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.capitalbio.oemv2.myapplication.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jiantaotu on 2016/3/21.
 */
public class BodyFatBalanceView extends View{


    //资源
    private Bitmap bm_disc;
    private Bitmap bm_pointer;

    //控件尺寸
    private int width;
    private int height;

    //刻度盘区
    private Rect src_disc;
    private Rect dst_disc;

    //指针区
    private Rect src_pointer;
    private Rect dst_pointer;

    //圆的半径
    private int radius_of_disc;

    //指针长度与刻度盘半径的比例
    private float proportion_pointer_radius = 0.5f;

    //指针自身的长宽比例
    private float proportion_pointer;

    //是否在执行动画
    private boolean isAnim = false;

    //刻度盘角度
    private float angle_disc;
    private float value_disc;

    //要停到的刻度
    private float stop;

    //动画相关参数
    private final float ORIGINAL_BASE_ANGLE = 1.0f;
    private final long ORIGINAL_BASE_TIMEDIFF = 1;
    private float base_angle = ORIGINAL_BASE_ANGLE;//动画自增角度
    private long base_timediff = ORIGINAL_BASE_TIMEDIFF;//动画自增一次时间间隔

    private long time_continu;

    //unit
    private float unit = 150f / 360;

    //减速度
    private final float deceleration = 0.001f;

    //要减速到的速度(要小于base_angle)
    private final float to_speed = 0.1f;

    //指针最后停留的位置误差
    private final float error = 0.09f;


    //
    private OnValueChangingListen listen;

    public void setOnChangingListener(OnValueChangingListen listener) {
        this.listen = listener;
    }


    public BodyFatBalanceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getRes();
    }

    private void getRes() {
        getDisc();
        getPointer();
    }

    private void getDisc() {
        BitmapDrawable bd = (BitmapDrawable) getResources().getDrawable(R.drawable.scale_disc);
        bm_disc = bd.getBitmap();
        src_disc = new Rect(0, 0, bm_disc.getWidth(), bm_disc.getHeight());
    }

    private void getPointer() {
        BitmapDrawable bd = (BitmapDrawable) getResources().getDrawable(R.drawable.pointer_red);
        bm_pointer = bd.getBitmap();
        src_pointer = new Rect(0, 0, bm_pointer.getWidth(), bm_pointer.getHeight());
        proportion_pointer = (float) bm_pointer.getHeight() / bm_pointer.getWidth();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getMeasuredWidth();
        height = getMeasuredHeight();

        calculateDiameterOfDisc();
        calculateRectOfDrawDisc();
        calculateRectOfDrawPointer();

    }

    //计算刻度盘的直径
    private void calculateDiameterOfDisc() {
        radius_of_disc = width / 2 < height ? width / 2 : height;
    }

    //计算刻度盘的绘制区域
    private void calculateRectOfDrawDisc() {
        int l = (width - radius_of_disc - radius_of_disc) / 2;
        int t = height - radius_of_disc;
        int r = width - l;
        int b = height + radius_of_disc;
        dst_disc = new Rect(l, t, r, b);
    }

    //计算指针的绘制区域
    private void calculateRectOfDrawPointer() {
        float h_pointer = radius_of_disc * proportion_pointer_radius;
        float w_pointer = h_pointer / proportion_pointer;
        int l = (int) ((width - w_pointer) / 2);
        int t = (int) (height - h_pointer);
        int r = width - l;
        int b = height;
        dst_pointer = new Rect(l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();

        canvas.rotate(angle_disc, width / 2f, height);

        Paint p = new Paint();
        p.setAntiAlias(true);

        canvas.drawBitmap(bm_disc, src_disc, dst_disc, p);

        canvas.restore();

        canvas.drawBitmap(bm_pointer, src_pointer, dst_pointer, p);

    }

    private TimerTask timerTask;
    private Timer timer;


    /**
     *
     * @param s 单位毫秒，在改时间内，如果不执行停止，则测量超时，自动停止
     */
    public void start(long s) {
        if (isAnim) {
            return;
        }


        isAnim = true;
        //还原
       reduct();
        time_continu = s;
        new StartThread().start();
        //开启定时任务
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if(isAnim){
                    new OvertimeThread().start();
                    timeoverHD.sendEmptyMessage(0);
                }
            }
        };

        timer = new Timer();
        timer.schedule(timerTask, time_continu);
    }

    //异常回调线程
    private class OvertimeThread extends Thread{

        @Override
        public void run() {
            while(true){
                if(!isAnim){
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        overHandler.sendEmptyMessage(0);
                    break;
                }
            }
        }
    }

    private Handler overHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(listen!=null){
                listen.overtime();
            }
        }
    };

    private Handler timeoverHD = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(isAnim){
                stop(0f);
            }
        }
    };

    //还原初始状态
    private void reduct(){
        base_angle = ORIGINAL_BASE_ANGLE;
    }

    public void stop(float stop) {
        if (!isAnim) {
            return;
        }
        this.stop = stop;
        timerTask.cancel();
        timer.cancel();
        new SpeedDownThread().start();

        //new StopThread().start();

    }

    private class StartThread extends Thread {

        @Override
        public void run() {

            while (isAnim) {
                try {
                    Thread.sleep(base_timediff);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                angle_disc = angle_disc - base_angle;
                angleToValue();
                startHandler.sendEmptyMessage(1);
            }

        }
    }

    private void angleToValue(){
        float angle_ = 0;
        if (angle_disc <= -360) {
            angle_ = angle_disc % 360;
        } else {
            angle_ = angle_disc;
        }
        value_disc = -(angle_ * unit);
    }

    private Handler startHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            invalidate();
            if (listen != null) {
                listen.changing(value_disc);
            }
        }
    };

    public interface OnValueChangingListen {
        void changing(float v);
        void overtime();
    }



    //动画减速
    private class SpeedDownThread extends Thread {



        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(base_timediff);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (base_angle <= to_speed) {
                    new StopThread().start();
                    break;
                }
                base_angle -= deceleration;
            }


        }
    }

    //停
    private class StopThread extends Thread{



        @Override
        public void run() {

            while(true){
                try {
                    Thread.sleep(base_timediff);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (Math.abs((value_disc-stop))<=error) {
                    isAnim = false;

                    break;
                }

            }

        }
    }




}
