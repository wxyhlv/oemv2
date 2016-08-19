package com.capitalbio.oemv2.myapplication.View.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.capitalbio.oemv2.myapplication.R;


/**
 * Created by 129 on 2016/3/29.
 */
public class SphygmomanometerTurntable extends View {


    private Bitmap turntable_bp;
    private Bitmap pointer_bp;

    //界面相关参数
    private MeasureParams measureParams;

    //转盘相关
    private Turntable turntable_Bean;

    //指针相关
    private Pointer pointer_Bean;

    //是否设置了超时
    private boolean isSetOvertime = false;

    //状态
    private int state = STATE_IDLE;

    //超时回调
    private OnOvertimeListener listener;


    public SphygmomanometerTurntable(Context context, AttributeSet attrs) {
        super(context, attrs);
       Log.i("info","============构造=============SphygmomanometerTurntable");
        getRes();
    }

    private void getRes() {
        //获取转盘
        BitmapDrawable bd_turnable = (BitmapDrawable) getResources().getDrawable(R.drawable.turntable_bloodpress);
        turntable_bp = bd_turnable.getBitmap();

        turntable_Bean = new Turntable();
        turntable_Bean.setWidth_px(turntable_bp.getWidth());
        turntable_Bean.setHeight_px(turntable_bp.getHeight());
        turntable_Bean.setRect_px(new Rect(0, 0, turntable_bp.getWidth(), turntable_bp.getHeight()));
        turntable_Bean.setAngel(ANGEL_MIDDLE);
        turntable_Bean.setIsTurning(false);
        turntable_Bean.setDirection_rotation(DIRECTION_ALONG);
        turntable_Bean.setPeriod_turn(PERIOD_TURN);
        turntable_Bean.setBase_turn(BASE_TURN);
        turntable_Bean.setPeriod_deceler(PERIOD_DECELER);
        turntable_Bean.setBase_deceler(BASE_DECELER);
        turntable_Bean.setPeriod_decelerate_to(PERIOD_DECELER_TO);

        //获取指针
        BitmapDrawable bd_pointer = (BitmapDrawable) getResources().getDrawable(R.drawable.pointer_bloodpress_big_text);
        pointer_bp = bd_pointer.getBitmap();

        pointer_Bean = new Pointer();
        pointer_Bean.setWidth_px(pointer_bp.getWidth());
        pointer_Bean.setHeight_px(pointer_bp.getHeight());
        pointer_Bean.setRect_px(new Rect(0, 0, pointer_bp.getWidth(), pointer_bp.getHeight()));
        pointer_Bean.setState(STATE_IDLE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //Log.i("info","=============================onMeasure");

        int width = getMeasuredWidth();

        int padding_l = getPaddingLeft();
        int padding_t = getPaddingTop();
        int padding_r = getPaddingRight();
        int padding_b = getPaddingBottom();

        int height = width - padding_l - padding_r + padding_t + padding_b;

        setMeasuredDimension(width, height);


        //初始化布局相关测量属性bean
        measureParams = new MeasureParams();
        measureParams.setWidth(getMeasuredWidth());
        measureParams.setHeight(getMeasuredHeight());
        measureParams.setPadding_l(padding_l);
        measureParams.setPadding_t(padding_t);
        measureParams.setPadding_r(padding_r);
        measureParams.setPadding_b(padding_b);


        //初始化转盘bean

        turntable_Bean.setWidth(getMeasuredWidth() - getPaddingLeft() - getPaddingRight());
        turntable_Bean.setHeight(getMeasuredHeight() - getPaddingTop() - getPaddingBottom());

        Rect rect = new Rect();
        int l = getPaddingLeft();
        int t = getPaddingTop();
        int r = width - getPaddingRight();
        int b = height - getPaddingBottom();
        rect.set(l, t, r, b);
        turntable_Bean.setRect(rect);
        turntable_Bean.setCenter_x(getPaddingLeft() + (getMeasuredWidth() - getPaddingLeft() - getPaddingRight()) / 2f);
        turntable_Bean.setCenter_y(getPaddingTop() + (getMeasuredHeight() - getPaddingTop() - getPaddingBottom()) / 2f);



        //初始化指针bean

        pointer_Bean.setWidht(getMeasuredWidth() - getPaddingLeft() - getPaddingRight());
        pointer_Bean.setHeight(getMeasuredHeight() - getPaddingTop() - getPaddingBottom());
        pointer_Bean.setRect(rect);

    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //Log.i("info", "=============================onLayout");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();

        canvas.rotate(turntable_Bean.angel, turntable_Bean.getCenter_x(), turntable_Bean.getCenter_y());

        drawTurntable(canvas);

        canvas.restore();
        drawPointer(canvas);

    }

    /**
     * 画转盘
     *
     * @param canvas
     */
    private void drawTurntable(Canvas canvas) {
        canvas.drawBitmap(turntable_bp, turntable_Bean.getRect_px(), turntable_Bean.getRect(), new Paint());
    }

    /**
     * 画指针
     *
     * @param canvas
     */
    private void drawPointer(Canvas canvas) {
        canvas.drawBitmap(pointer_bp, pointer_Bean.getRect_px(), pointer_Bean.getRect(), new Paint());
    }

    //开始
    public void startTurn() {
        //开启转动
        //如果在转动则返回
        if (state == STATE_BUSY_NORMAL) {

            //Log.i("info","===================STATE_BUSY_NORMAL");

            return;
        }
        //如果没转动，则开启转动

        if (state == STATE_IDLE) {
            //Log.i("info","===================STATE_IDLE");
            state = STATE_BUSY_NORMAL;
            turntable_Bean.period_turn = PERIOD_TURN;
            new TurnThread().start();
        }else if(state==STATE_BUSY_DECELER||state==STATE_BUSY_STOP){
            //Log.i("info","===================STATE_BUSY_DECELER-STATE_BUSY_STOP");
            state = STATE_BUSY_NORMAL;
            turntable_Bean.period_turn = PERIOD_TURN;
        }
    }


    /**
     * 从执行该方法开始，超时时间内，如果没执行stop方法，则执行stop，如果执行了stop则取消超时设置
     *
     * @param overtime
     */

    //private OvertimeThread overtimeThread;

    public void setOverTime(long overtime,OnOvertimeListener listener) {
        //重置超时

        this.listener = listener;

        if (isSetOvertime) {

            if(overtimeHandler.hasMessages(0)){
                overtimeHandler.removeMessages(0);
            }
        } else {
            isSetOvertime = true;
        }
        overtimeHandler.sendEmptyMessageDelayed(0,overtime);


    }


    /**
     * 停在指定角度上
     *
     * @param value
     */
    public void stopTurn(int value) {
        //停止转动
        //如果是停止的则返回
        if (state == STATE_IDLE ) {
            startTurn();
            stopTurn(value);
            // return;
        }
        //如果是转动的，则停止
        if (state == STATE_BUSY_NORMAL) {
            turntable_Bean.setStopvalue(value);
            switch (value) {
                case VALUE_EXCEPTION:
                    turntable_Bean.setStopangel(ANGEL_EXCEPTION);
                    break;
                case VALUE_HIGH:
                    turntable_Bean.setStopangel(ANGEL_HIGH);
                    break;
                case VALUE_HIGH1:
                    turntable_Bean.setStopangel(ANGEL_HIGH1);
                    break;
                case VALUE_HIGH2:
                    turntable_Bean.setStopangel(ANGEL_HIGH2);
                    break;
                case VALUE_HIGH3:
                    turntable_Bean.setStopangel(ANGEL_HIGH3);
                    break;
                case VALUE_LOW:
                    turntable_Bean.setStopangel(ANGEL_LOW);
                    break;
                case VALUE_MIDDLE:
                    turntable_Bean.setStopangel(ANGEL_MIDDLE);
                    break;
            }
            state = STATE_BUSY_DECELER;
            new StopThread().start();
        }else if(state == STATE_BUSY_DECELER || state == STATE_BUSY_STOP){
            turntable_Bean.setStopvalue(value);
            switch (value) {
                case VALUE_EXCEPTION:
                    turntable_Bean.setStopangel(ANGEL_EXCEPTION);
                    break;
                case VALUE_HIGH:
                    turntable_Bean.setStopangel(ANGEL_HIGH);
                    break;
                case VALUE_HIGH1:
                    turntable_Bean.setStopangel(ANGEL_HIGH1);
                    break;
                case VALUE_HIGH2:
                    turntable_Bean.setStopangel(ANGEL_HIGH2);
                    break;
                case VALUE_HIGH3:
                    turntable_Bean.setStopangel(ANGEL_HIGH3);
                    break;
                case VALUE_LOW:
                    turntable_Bean.setStopangel(ANGEL_LOW);
                    break;
                case VALUE_MIDDLE:
                    turntable_Bean.setStopangel(ANGEL_MIDDLE);
                    break;
            }
        }

    }

    public void setDirectionTurn(int direction) {
        if (turntable_Bean != null) {
            turntable_Bean.direction_rotation = direction;
        }
    }


    private class MeasureParams {

        int padding_l;
        int padding_t;
        int padding_r;
        int padding_b;

        int width;
        int height;

        Rect rect;

        public MeasureParams() {
        }

        public int getPadding_l() {
            return padding_l;
        }

        public void setPadding_l(int padding_l) {
            this.padding_l = padding_l;
        }

        public int getPadding_t() {
            return padding_t;
        }

        public void setPadding_t(int padding_t) {
            this.padding_t = padding_t;
        }

        public int getPadding_r() {
            return padding_r;
        }

        public void setPadding_r(int padding_r) {
            this.padding_r = padding_r;
        }

        public int getPadding_b() {
            return padding_b;
        }

        public void setPadding_b(int padding_b) {
            this.padding_b = padding_b;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public Rect getRect() {
            return rect;
        }

        public void setRect(Rect rect) {
            this.rect = rect;
        }
    }

    private  class Turntable {
        float angel;

        int width;
        int height;

        Rect rect;

        boolean isTurning;

        int width_px;
        int height_px;
        Rect rect_px;

        float center_x;//画布旋转的中心x
        float center_y;//画布旋转的中心y

        //旋转相关

        private int direction_rotation;//旋转方向

        private long period_turn;//转动间隔时长

        private float base_turn;//转动的最小量

        //减速相关

        private long period_deceler;//减速间隔

        private int base_deceler;//减速基量

        private long period_decelerate_to;//减速通过增加线程睡眠间隔来实现，睡眠间隔增加到的值

        int stopvalue;//要停在的值

        float stopangel;//要停在的角度



        public Turntable() {
        }

        public float getAngel() {
            return angel;
        }

        public void setAngel(float angel) {
            this.angel = angel;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public Rect getRect() {
            return rect;
        }

        public void setRect(Rect rect) {
            this.rect = rect;
        }

        public boolean isTurning() {
            return isTurning;
        }

        public void setIsTurning(boolean isTurning) {
            this.isTurning = isTurning;
        }

        public int getWidth_px() {
            return width_px;
        }

        public void setWidth_px(int width_px) {
            this.width_px = width_px;
        }

        public int getHeight_px() {
            return height_px;
        }

        public void setHeight_px(int height_px) {
            this.height_px = height_px;
        }

        public Rect getRect_px() {
            return rect_px;
        }

        public void setRect_px(Rect rect_px) {
            this.rect_px = rect_px;
        }

        public float getCenter_x() {
            return center_x;
        }

        public void setCenter_x(float center_x) {
            this.center_x = center_x;
        }

        public float getCenter_y() {
            return center_y;
        }

        public void setCenter_y(float center_y) {
            this.center_y = center_y;
        }

        public int getDirection_rotation() {
            return direction_rotation;
        }

        public void setDirection_rotation(int direction_rotation) {
            this.direction_rotation = direction_rotation;
        }

        public long getPeriod_turn() {
            return period_turn;
        }

        public void setPeriod_turn(long period_turn) {
            this.period_turn = period_turn;
        }

        public float getBase_turn() {
            return base_turn;
        }

        public void setBase_turn(float base_turn) {
            this.base_turn = base_turn;
        }

        public long getPeriod_deceler() {
            return period_deceler;
        }

        public void setPeriod_deceler(long period_deceler) {
            this.period_deceler = period_deceler;
        }

        public int getBase_deceler() {
            return base_deceler;
        }

        public void setBase_deceler(int base_deceler) {
            this.base_deceler = base_deceler;
        }

        public long getPeriod_decelerate_to() {
            return period_decelerate_to;
        }

        public void setPeriod_decelerate_to(long period_decelerate_to) {
            this.period_decelerate_to = period_decelerate_to;
        }

        public int getStopvalue() {
            return stopvalue;
        }

        public void setStopvalue(int stopvalue) {
            this.stopvalue = stopvalue;
        }

        public float getStopangel() {
            return stopangel;
        }

        public void setStopangel(float stopangel) {
            this.stopangel = stopangel;
        }


    }


    private class Pointer {

        int widht;
        int height;

        Rect rect;

        int state;

        int width_px;
        int height_px;
        Rect rect_px;

        public Pointer() {
        }

        public int getWidht() {
            return widht;
        }

        public void setWidht(int widht) {
            this.widht = widht;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public Rect getRect() {
            return rect;
        }

        public void setRect(Rect rect) {
            this.rect = rect;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getWidth_px() {
            return width_px;
        }

        public void setWidth_px(int width_px) {
            this.width_px = width_px;
        }

        public int getHeight_px() {
            return height_px;
        }

        public void setHeight_px(int height_px) {
            this.height_px = height_px;
        }

        public Rect getRect_px() {
            return rect_px;
        }

        public void setRect_px(Rect rect_px) {
            this.rect_px = rect_px;
        }
    }

    private static final int STATE_IDLE = 0x1;//空闲状态
    private static final int STATE_BUSY_NORMAL = 0x20;//正常转动状态
    private static final int STATE_BUSY_DECELER = 0x21; //减速状态
    private static final int STATE_BUSY_STOP = 0x22;//进入停止状态

    public static final int VALUE_MIDDLE = 0x3;
    public static final int VALUE_LOW = 0x4;
    public static final int VALUE_HIGH = 0x5;
    public static final int VALUE_HIGH1 = 0X6;
    public static final int VALUE_HIGH2 = 0X7;
    public static final int VALUE_HIGH3 = 0X8;
    public static final int VALUE_EXCEPTION = 0X9;

    private static final float ANGEL_MIDDLE = 0;
    private static final float ANGEL_LOW = 360f / 7 * 1;
    private static final float ANGEL_EXCEPTION = 360f / 7 * 2;
    private static final float ANGEL_HIGH3 = 360f / 7 * 3;
    private static final float ANGEL_HIGH2 = 360f / 7 * 4;
    private static final float ANGEL_HIGH1 = 360f / 7 * 5;
    private static final float ANGEL_HIGH = 360f / 7 * 6;

    public static final int DIRECTION_ALONG = 0x100;
    public static final int DIRECTION_INVERSE = 0x101;

    private static final long PERIOD_TURN = 1;//多长时间旋转一次

    private static final float BASE_TURN = 0.1f;//每次旋转增加的角度数

    private static final long PERIOD_DECELER = 200;//多长时间执行一次减速

    private static final int BASE_DECELER = 1;//每次减速增加的时间间隔量（）

    private static final long PERIOD_DECELER_TO = 2;//减速完成后的旋转时间间隔

    private static final float ALLOWABLE_ERROR = 0.1f;//最后转盘停止位置的偏移误差


    //转动线程
    private class TurnThread extends Thread {

        @Override
        public void run() {
            while (state == STATE_BUSY_NORMAL || state == STATE_BUSY_DECELER || state == STATE_BUSY_STOP) {



                try {
                    Thread.sleep(turntable_Bean.period_turn);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (state == STATE_BUSY_STOP) {
                    //Log.i("info","=================进入stop状态");
                    //Log.i("info","=========当前========="+turntable_Bean.angel%360);
                    //Log.i("info","=========目标========="+turntable_Bean.stopangel);
                    //Log.i("info","=========接近========="+Math.abs(Math.abs(turntable_Bean.angel%360)-turntable_Bean.stopangel));
                    if (turntable_Bean.direction_rotation == DIRECTION_ALONG) {
                        //顺时针
                        if (Math.abs(Math.abs(turntable_Bean.angel % 360) - turntable_Bean.stopangel) < ALLOWABLE_ERROR) {
                            turntable_Bean.angel = turntable_Bean.stopangel;
                            turnHandler.sendEmptyMessage(0);
                            break;
                        }
                    }
                    if (turntable_Bean.direction_rotation == DIRECTION_INVERSE) {
                        //逆时针
                        if (Math.abs(turntable_Bean.angel % 360 - (turntable_Bean.stopangel - 360)) < ALLOWABLE_ERROR) {
                            turntable_Bean.angel = turntable_Bean.stopangel;
                            turnHandler.sendEmptyMessage(0);
                            break;
                        }
                    }

                }
                if (turntable_Bean.direction_rotation == DIRECTION_ALONG) {
                    turntable_Bean.angel = turntable_Bean.angel + turntable_Bean.base_turn;
                    //Log.i("info","==========当前角度===DIRECTION_ALONG========"+turntable_Bean.angel);
                }
                if (turntable_Bean.direction_rotation == DIRECTION_INVERSE) {
                    turntable_Bean.angel = turntable_Bean.angel - turntable_Bean.base_turn;
                    //Log.i("info","==========当前角度===DIRECTION_INVERSE========"+turntable_Bean.angel);
                }


                turnHandler.sendEmptyMessage(0);


            }
            state = STATE_IDLE;
        }
    }

    private Handler turnHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            invalidate();

        }
    };

    //停止线程
    private class StopThread extends Thread {

        @Override
        public void run() {

            while (state == STATE_BUSY_DECELER) {

                try {
                    Thread.sleep(turntable_Bean.period_deceler);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (turntable_Bean.period_turn == turntable_Bean.period_decelerate_to) {
                    state = STATE_BUSY_STOP;
                    break;
                }

                turntable_Bean.period_turn = turntable_Bean.period_turn + turntable_Bean.base_deceler;


            }

        }
    }



    private Handler overtimeHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (state == STATE_BUSY_NORMAL) {
                stopTurn(VALUE_EXCEPTION);
                if(listener!=null){
                    listener.overTime();
                }
            }
            isSetOvertime = false;
        }
    };

    //超时回调接口
    public interface OnOvertimeListener{
        void overTime();
    }

}
