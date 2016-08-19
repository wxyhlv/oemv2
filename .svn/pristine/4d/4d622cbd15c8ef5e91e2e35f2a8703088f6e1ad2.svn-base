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
import android.util.Log;
import android.view.View;

import com.capitalbio.oemv2.myapplication.R;


/**
 * Created by jiantaotu on 2016/4/7.
 */
public class BodyFat2View extends View {

    private Bitmap bp_disc;
    private Bitmap bp_pointer;

    private InnerBean innerBean;

    public BodyFat2View(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.i("info", "=========BodyFat2View=========");

        innerBean = new InnerBean();

        //获取资源
        BitmapDrawable bd_disc = (BitmapDrawable) context.getResources().getDrawable(R.drawable.scale_disc);
        bp_disc = bd_disc.getBitmap();
        innerBean.setWidth_disc_source(bp_disc.getWidth());
        innerBean.setHeight_disc_source(bp_disc.getHeight());
        innerBean.setRect_disc_source(new Rect(0, 0, bp_disc.getWidth(), bp_disc.getHeight()));

        BitmapDrawable bd_pointer = (BitmapDrawable) context.getResources().getDrawable(R.drawable.pointer_red);
        bp_pointer = bd_pointer.getBitmap();
        innerBean.setWidth_pointer_source(bp_pointer.getWidth());
        innerBean.setHeight_pointer_source(bp_pointer.getHeight());
        innerBean.setRatio_pointer((float) bp_pointer.getWidth() / bp_pointer.getHeight());
        innerBean.setRect_pointer_source(new Rect(0, 0, bp_pointer.getWidth(), bp_pointer.getHeight()));

        innerBean.setRatio_pointer_disc((float) bp_pointer.getWidth() / bp_disc.getWidth());


        innerBean.setDegree(-0.0f);
        innerBean.setValue(0.0f);

        innerBean.setIsTurning(false);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //Log.i("info", "=========onMeasure=========");

        //可绘制区域的宽高
        int width_drawable = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int height_drawable = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();

        //可绘制区域的宽高比
        float ratio_drawable = (float) width_drawable / height_drawable;
        if (ratio_drawable > 2) {
            //依据可绘制区域的高来计算

            Rect rect_disc_draw = new Rect();
            int l_rect_disc_draw = (width_drawable - 2 * height_drawable) / 2 + getPaddingLeft();
            int t_rect_disc_draw = 0 + getPaddingTop();
            int r_rect_disc_draw = getMeasuredWidth() - l_rect_disc_draw;
            int b_rect_disc_draw = getMeasuredHeight() + (height_drawable - getPaddingBottom());
            rect_disc_draw.set(l_rect_disc_draw, t_rect_disc_draw, r_rect_disc_draw, b_rect_disc_draw);

            //根据当前的转盘的大小，计算指针的宽高
            int width_pointer_draw = (int) (height_drawable * 2 * innerBean.ratio_pointer_disc);
            int height_pointer_draw = (int) (width_pointer_draw / innerBean.ratio_pointer);

            Rect rect_pointer_draw = new Rect();
            int l_rect_pointer_draw = getMeasuredWidth() / 2 - width_pointer_draw / 2;
            int t_rect_pointer_draw = getMeasuredHeight() - getPaddingBottom() - height_pointer_draw;
            int r_rect_pointer_draw = getMeasuredWidth() / 2 + width_pointer_draw / 2;
            int b_rect_pointer_draw = getMeasuredHeight() - getPaddingBottom();
            rect_pointer_draw.set(l_rect_pointer_draw, t_rect_pointer_draw, r_rect_pointer_draw, b_rect_pointer_draw);

            innerBean.setRect_disc_draw(rect_disc_draw);
            innerBean.setRect_pointer_draw(rect_pointer_draw);

        } else {
            //依据可绘制区域的宽来计算

            Rect rect_disc_draw = new Rect();
            int l_rect_disc_draw = getPaddingLeft();
            int t_rect_disc_draw = getMeasuredHeight() - getPaddingBottom() - width_drawable / 2;
            int r_rect_disc_draw = getMeasuredWidth() - getPaddingRight();
            int b_rect_disc_draw = getMeasuredHeight() + (width_drawable / 2 - getPaddingBottom());
            rect_disc_draw.set(l_rect_disc_draw, t_rect_disc_draw, r_rect_disc_draw, b_rect_disc_draw);

            //根据当前的转盘的大小，计算指针的宽高
            int width_pointer_draw = (int) (width_drawable * innerBean.ratio_pointer_disc);
            int height_pointer_draw = (int) (width_pointer_draw / innerBean.ratio_pointer);

            Rect rect_pointer_draw = new Rect();
            int l_rect_pointer_draw = getMeasuredWidth() / 2 - width_pointer_draw / 2;
            int t_rect_pointer_draw = getMeasuredHeight() - getPaddingBottom() - height_pointer_draw;
            int r_rect_pointer_draw = getMeasuredWidth() / 2 + width_pointer_draw / 2;
            int b_rect_pointer_draw = getMeasuredHeight() - getPaddingBottom();
            rect_pointer_draw.set(l_rect_pointer_draw, t_rect_pointer_draw, r_rect_pointer_draw, b_rect_pointer_draw);

            innerBean.setRect_disc_draw(rect_disc_draw);
            innerBean.setRect_pointer_draw(rect_pointer_draw);

        }

        innerBean.setPx((float) getMeasuredWidth() / 2);
        innerBean.setPy((float) getMeasuredHeight() - getPaddingBottom());

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //Log.i("info", "=========onLayout=========");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Log.i("info", "=========onDraw=========");

        canvas.save();

        canvas.rotate(innerBean.getDegree(), innerBean.getPx(), innerBean.getPy());
        //绘制转盘
        canvas.drawBitmap(bp_disc, innerBean.getRect_disc_source(), innerBean.getRect_disc_draw(), new Paint());

        canvas.restore();
        //绘制指针
        canvas.drawBitmap(bp_pointer, innerBean.getRect_pointer_source(), innerBean.getRect_pointer_draw(), new Paint());

    }


    public class InnerBean {
        public InnerBean() {
        }

        private int width_disc_source;//转盘原本的宽度
        private int height_disc_source;//转盘原本的高度
        private Rect rect_disc_source;//转盘原本的区域

        private int width_pointer_source;//指针原本宽度
        private int height_pointer_source;//指针原本高度
        private float ratio_pointer;//指针宽高比
        private Rect rect_pointer_source;//指针原本区域

        private float ratio_pointer_disc;//指针的宽度与转盘直径的比

        private Rect rect_disc_draw;//转盘的实际绘制区域
        private Rect rect_pointer_draw;//指针的实际绘制区域

        private float degree;//转盘的角度
        private float px;//转盘旋转中心
        private float py;//转盘旋转中心

        private float value;//当前的体重值

        private boolean isTurning;//转盘是否在转动中

        public int getWidth_disc_source() {
            return width_disc_source;
        }

        public void setWidth_disc_source(int width_disc_source) {
            this.width_disc_source = width_disc_source;
        }

        public int getHeight_disc_source() {
            return height_disc_source;
        }

        public void setHeight_disc_source(int height_disc_source) {
            this.height_disc_source = height_disc_source;
        }

        public Rect getRect_disc_source() {
            return rect_disc_source;
        }

        public void setRect_disc_source(Rect rect_disc_source) {
            this.rect_disc_source = rect_disc_source;
        }

        public int getWidth_pointer_source() {
            return width_pointer_source;
        }

        public void setWidth_pointer_source(int width_pointer_source) {
            this.width_pointer_source = width_pointer_source;
        }

        public int getHeight_pointer_source() {
            return height_pointer_source;
        }

        public void setHeight_pointer_source(int height_pointer_source) {
            this.height_pointer_source = height_pointer_source;
        }

        public Rect getRect_pointer_source() {
            return rect_pointer_source;
        }

        public void setRect_pointer_source(Rect rect_pointer_source) {
            this.rect_pointer_source = rect_pointer_source;
        }


        public float getRatio_pointer() {
            return ratio_pointer;
        }

        public void setRatio_pointer(float ratio_pointer) {
            this.ratio_pointer = ratio_pointer;
        }

        public float getRatio_pointer_disc() {
            return ratio_pointer_disc;
        }

        public void setRatio_pointer_disc(float ratio_pointer_disc) {
            this.ratio_pointer_disc = ratio_pointer_disc;
        }

        public Rect getRect_disc_draw() {
            return rect_disc_draw;
        }

        public void setRect_disc_draw(Rect rect_disc_draw) {
            this.rect_disc_draw = rect_disc_draw;
        }

        public Rect getRect_pointer_draw() {
            return rect_pointer_draw;
        }

        public void setRect_pointer_draw(Rect rect_pointer_draw) {
            this.rect_pointer_draw = rect_pointer_draw;
        }

        public float getDegree() {
            return degree;
        }

        public void setDegree(float degree) {
            this.degree = degree;
        }

        public float getPx() {
            return px;
        }

        public void setPx(float px) {
            this.px = px;
        }

        public float getPy() {
            return py;
        }

        public void setPy(float py) {
            this.py = py;
        }

        public float getValue() {
            return value;
        }

        public void setValue(float value) {
            this.value = value;
        }

        public boolean isTurning() {
            return isTurning;
        }

        public void setIsTurning(boolean isTurning) {
            this.isTurning = isTurning;
        }
    }

    //将具体指转为角度

    private float transfer(float value) {
        if (value < 0 || value > 150) {
            return 0.0f;
        }
        return -value * 360 / 150;
    }


    //对外公开的方法
    private   void setValue(float value) {
        if (value < 0 || value > 150) {
            return;
        }

        innerBean.setValue(value);
        innerBean.setDegree(transfer(value));
        invalidate();
    }


    /**
     * @param time 指定时间内转动结束 单位毫秒
     */
    public void startFreedom(long time) {
        if (overtimeHDL.hasMessages(0)) {
            overtimeHDL.removeMessages(0);
        }
        overtimeHDL.sendEmptyMessageDelayed(0, time);
        if (innerBean.isTurning) {
            return;
        }
        innerBean.setIsTurning(true);

        turn = new TurnTh();
        turn.start();
        new TurnTh().start();
    }

    private TurnTh turn;

    private Handler overtimeHDL = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            innerBean.setIsTurning(false);
            try {
                turn.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            turnHDL.sendEmptyMessage(0);
        }
    };

    private class TurnTh extends Thread {

        @Override
        public void run() {

            int msg = 0;


                while (innerBean.isTurning()) {

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    msg--;

                    if (msg == -150) {
                        msg = 0;
                    }

                    turnHDL.sendEmptyMessage(msg);


                }



        }
    }

    private Handler turnHDL = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //Log.i("info", "======================" + msg.what);
            setValue(msg.what);
        }
    };

    public  void stopValue(float value) {
        if (innerBean.isTurning()) {
            innerBean.setIsTurning(false);
        }
        setValue(value);
    }


}
