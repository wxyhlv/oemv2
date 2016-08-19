package com.capitalbio.oemv2.myapplication.View.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.capitalbio.oemv2.myapplication.R;


/**
 * @author lzq
 * @Time 2015/12/15 16:43
 */
public class BioBalance2 extends View {

    //true-right_,false-left;
    private boolean rotateDirection = true;

    private int width;
    private int height;

    private Bitmap disc = null;
    private int width_disc;
    private int height_disc;
    private Bitmap pointer = null;
    private Bitmap circleWhite = null;
    private int width_circlewhite;
    private int height_circlewhite;

    //当前的角度(0°-360°)
    private float currAngle = 0;
    //当前值（1-1500）
    private float currValue = 0;
    //下一步转动到的角度(0°-360°)
    private float nextAngle = 0;
    //下一步转动到的值（1-1500）
    private int nextValue = 0;
    //最终要转动到的角度(0°-360°)
    private float stopAngle = 0;
    //最终要转动到的值（1-1500）
    private float stopValue = 0;

    //当前转盘的状态
    private boolean isRotation = false;

    //三级刻度所占角度
    private float bigscaleangle;
    private float middlescaleangle;
    private float smallscaleangle;

    //灰色底线的高度 单位dp
    private float width_bottom_line = 5;

    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
             switch (msg.what)
             {
                 case 1:
                     invalidate();
                     break;
             }
        };
    };

    public BioBalance2(Context context) {
        super(context);
    }

    public BioBalance2(Context context, AttributeSet attrs) {
        super(context, attrs);
        BitmapDrawable drawable = (BitmapDrawable) context.getResources().getDrawable(R.drawable.balance);
        disc = drawable.getBitmap();

        BitmapDrawable drawable2 = (BitmapDrawable) context.getResources().getDrawable(R.drawable.pointer_red);
        pointer = drawable2.getBitmap();

        BitmapDrawable drawable3 = (BitmapDrawable) context.getResources().getDrawable(R.drawable.circle_white);
        circleWhite = drawable3.getBitmap();

        bigscaleangle = calcuBigScaleAngle();
        middlescaleangle = calcuMiddleScaleAngle();
        smallscaleangle = calcuSmallScaleAngle();

    }

    public BioBalance2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();

        float radius;
        if (width > height * 2) {
            //以高做半径
            radius = (float) height;
        } else {
            //以宽的一半做半径
            radius = (float) width / 2;
        }
        float radius_bitmap = (float) disc.getWidth() / 2;

        float scale = radius / radius_bitmap;
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        disc = Bitmap.createBitmap(disc, 0, 0, disc.getWidth(), disc.getHeight(), matrix, false);
        pointer = Bitmap.createBitmap(pointer, 0, 0, pointer.getWidth(), pointer.getHeight(), matrix, false);
        circleWhite = Bitmap.createBitmap(circleWhite, 0, 0, circleWhite.getWidth(), circleWhite.getHeight(), matrix, false);

        width_disc = disc.getWidth();
        height_disc = disc.getHeight();

        width_circlewhite = circleWhite.getWidth();
        height_circlewhite = circleWhite.getHeight();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawDisc(canvas);

        //画灰色底线
        drawBottomGrayLine(canvas);
        //画白色中心园
        drawWhiteCircle(canvas);

        drawPointer(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return true;
    }

    //画刻度盘
    private void drawDisc(Canvas canvas) {

        Paint paint = new Paint();

        paint.setAntiAlias(true);

        rotateDisc(nextValue);

        canvas.drawBitmap(disc, (float) (width - width_disc) / 2, height - (float) height_disc / 2, paint);


    }

    //绘制灰色底线
    private void drawBottomGrayLine(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#E5EBEC"));
        float l = (float) (width - width_disc) / 2;
        float t = height - dip2px(width_bottom_line);
        float r = (float) width / 2 + (float) width_disc / 2;
        float b = height;
        canvas.drawRect(l, t, r, b, paint);
    }

    //绘制白色中心圆
    private void drawWhiteCircle(Canvas canvas) {
        Paint paint = new Paint();
        float l = (float) (width - width_circlewhite) / 2;
        float t = height - (float) height_circlewhite / 2;
        canvas.drawBitmap(circleWhite, l, t, paint);
    }

    //刻度盘选装
    private void rotateDisc(int value) {
        float angle = valueToAngle(value);
        Matrix matrix = new Matrix();
        matrix.setRotate(angle,width_disc/2,height_disc/2);
        disc = Bitmap.createBitmap(disc, 0, 0, width_disc, height_disc, matrix, false);

    }


    //画指针
    private void drawPointer(Canvas canvas) {
        int wid = pointer.getWidth();
        int hei = pointer.getHeight();

        float left = (float) width / 2 - (float) wid / 2;
        float top = height - hei;

        Paint paint = new Paint();
        canvas.drawBitmap(pointer, left, top, paint);
    }

    //开始动画-转动到指定值处-是否要动画
    public void startRotation(final int value, boolean animation) {
        float angle = valueToAngle(value);
        if (animation) {
          new Thread(){
              @Override
              public void run() {
                  for(int i=1;i<=value;i++){
                      try {
                          Thread.sleep(100);
                      } catch (InterruptedException e) {
                          e.printStackTrace();
                      }
                      nextValue = i;
                      handler.sendEmptyMessage(1);
                  }
              }
          }.start();
        } else {

        }
    }

    //停止动画  停止在指定值处
    public void stopRotation(int value) {

    }


    /**
     * 计算当当前值为0时其他刻度值对应的角度
     *
     * @param value 传值范围闭区间【1-1500】
     * @return
     */
    private float valueToAngle(int value) {
        int bigscalesum = value / 100;
        int middlescalesum = value % 100 / 10;
        int smallscalesum = value % 100 % 10;
        float f;
        if (rotateDirection) {
            f = -(bigscalesum * bigscaleangle + middlescalesum * middlescaleangle + smallscalesum * smallscaleangle);
        } else {
            f = bigscalesum * bigscaleangle + middlescalesum * middlescaleangle + smallscalesum * smallscaleangle;
        }
        return f;
    }

    //计算大刻度占角度
    private float calcuBigScaleAngle() {
        return (float) 360 / 15;
    }

    //计算中刻度占角度
    private float calcuMiddleScaleAngle() {
        return (float) 360 / 150;
    }

    //计算小刻度占角度
    private float calcuSmallScaleAngle() {
        return (float) 360 / 1500;
    }

    //dp转px
    private float dip2px(float dip) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return dip * density + 0.5f;
    }


}
