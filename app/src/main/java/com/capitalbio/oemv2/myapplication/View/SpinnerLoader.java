package com.capitalbio.oemv2.myapplication.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.capitalbio.oemv2.myapplication.Const.Const;
import com.capitalbio.oemv2.myapplication.R;

/**
 * Created by liurongchan with love on 15/8/5.
 */
public class SpinnerLoader extends View {

    public static final String TAG = "SpinnerLoader";

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }


    //设备变化监听
    public ISelectedDeviceChange deviceChangeLisener;
    public int selectedDeviceIndex;

    public void setIsTouchEnd(boolean isTouchEnd) {
        this.isTouchEnd = isTouchEnd;
    }


    public Paint currentPaint;
    private boolean isTouchEnd = false;
    private int leftTouchIndex;
    private int rightTouchIndex;
    private int direction;
    private static final int POINTS_COUNT = 6;
    private static final int STEP = 3;
    private static final float BIG_STEP = 0;
    private static final int DEFAULT_COLOR = Color.rgb(205, 240, 242);
    private static final float DEFAULT_RADUIS = 180;
    private static final float DEFAULT_CIRCLE_RADUIS = 40;
    private static final float DEAFULT_MOVE_RADUIS = 30;
    private static final int SPLIT_ANGLE = 30;
    private static final int ADDITION_LENGTH = 6;
    private static final int FLAT_ANGLE = 180;

    /**
     * for save and restore instance of view.
     */
    private static final String INSTANCE_STATE = "saved_instance";
    private static final String ANGLE = "angle";
    private static final String BIGCIRCLECENTERX = "bigCircleCenterX";
    private static final String BIGCIRCLECENTERY = "bigCircleCenterY";
    private static final String RADUIS = "raduis";
    private static final String CIRCLERADUIS = "circleRaduis";
    private static final String MOVERADUIS = "moveRaduis";
    private static final String POINTCOLOR = "pointColor";
    private static final String STARTX1 = "startX1";
    private static final String STARTY1 = "startY1";
    private static final String ENDX1 = "endX1";
    private static final String ENDY1 = "endY1";
    private static final String STARTX2 = "startX2";
    private static final String STARTY2 = "startY2";
    private static final String ENDX2 = "endX2";
    private static final String ENDY2 = "endY2";
    private static final String CONTROLX1 = "controlX1";
    private static final String CONTROLY1 = "controlY1";
    private static final String BIGSTEP = "bigStep";
    private int smallCicle;
    private int middleCicle;
    private int bigCicle;
    private int smallToMiddle;
    private int middleToBig;


    private CirclePoint[] circlePoints = new CirclePoint[POINTS_COUNT];

    private CirclePoint[] initialPoints = new CirclePoint[POINTS_COUNT];
    private CirclePoint[] firstStateCirclePoint = new CirclePoint[POINTS_COUNT];

    public int angle = 0;

    public void setBigStep(int bigStep) {
        this.bigStep = bigStep;
    }

    private float bigStep;
    float bigCircleCenterX;
    float bigCircleCenterY;

    @Override
    public boolean isLaidOut() {
        return super.isLaidOut();
    }

    float raduis;
    float circleRaduis;
    float moveRaduis;
    int pointColor;


    private float startX1;
    private float startY1;
    private float startX2;
    private float startY2;

    private float controlX1;
    private float controlY1;

    private float endX1;
    private float endY1;
    private float endX2;
    private float endY2;

    private Path path1;

    private Paint big4Paint;
    private Paint middle4Paint;
    private Paint small4Paint;

    private Paint big3Paint;
    private Paint middle3Paint;
    private Paint small3Paint;


    public static Paint bigCirclePaint;
    public static Paint middleCirclePaint;
    public static Paint smallCirclePaint;
    private Paint linePaint;
    private Context mContext;
    private int mWidth;
    private int mHeight;


    private boolean isFirst = true;
    private boolean isFirstDraw = true;

    private GestureDetector mGestureDetector;
    private class DefaultGestureListener extends GestureDetector.SimpleOnGestureListener {

        // Touch down时触发
        @Override
        public boolean onDown(MotionEvent e) {
            Log.d(TAG, "onDown");
            return super.onDown(e);
        }

        // 在Touch down之后一定时间（115ms）触发
        @Override
        public void onShowPress(MotionEvent e) {
            Log.d(TAG, "onShowPress");
        }

        /**
         * 这个方法不同于onSingleTapUp，他是在GestureDetector确信用户在第一次触摸屏幕后，没有紧跟着第二次触摸屏幕，也就是不是“双击”的时候触发
         * */
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.d(TAG, "onSingleTapUp");
            return super.onSingleTapUp(e);
        }

        /**
         * @param e1 The first down motion event that started the scrolling.
         @param e2 The move motion event that triggered the current onScroll.
         @param distanceX The distance along the X axis(轴) that has been scrolled since the last call to onScroll. This is NOT the distance between e1 and e2.
         @param distanceY The distance along the Y axis that has been scrolled since the last call to onScroll. This is NOT the distance between e1 and e2.
         无论是用手拖动view，或者是以抛的动作滚动，都会多次触发 ,这个方法在ACTION_MOVE动作发生时就会触发 参看GestureDetector的onTouchEvent方法源码
          * */
        // 滑动时触发
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {

            Log.d(TAG, "onScroll");

            if((e2.getX() - e1.getX()) > 0) {
                /*mSpinerView.setBigStep(-6);
                mSpinerView.setDirection(10);*/
            } else if (e2.getX() - e1.getX() < -30){
                setBigStep(2);
                setDirection(Const.MAIN_VIEW_SLIP_DIRECTION_LEFT);
                invalidate();
            }

            return super.onScroll(e1, e2, distanceX, distanceY);
        }
        /**
         * @param e1 第1个ACTION_DOWN MotionEvent 并且只有一个
         * @param e2 最后一个ACTION_MOVE MotionEvent
         * @param velocityX X轴上的移动速度，像素/秒
         * @param velocityY Y轴上的移动速度，像素/秒
         * 这个方法发生在ACTION_UP时才会触发 参看GestureDetector的onTouchEvent方法源码
         *
         * */
        // 滑动一段距离，up时触发
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {

            Log.d(TAG, "onFling");

            return super.onFling(e1, e2, velocityX, velocityY);
        }

        // 长按后触发(Touch down之后一定时间（500ms）)
        @Override
        public void onLongPress(MotionEvent e) {

            Log.d(TAG, "onLongPress");
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            // TODO Auto-generated method stub
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            // TODO Auto-generated method stub
            return super.onDoubleTapEvent(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            // TODO Auto-generated method stub
            return super.onSingleTapConfirmed(e);
        }

    }


    public SpinnerLoader(Context context) {
        this(context, null);
    }

    public SpinnerLoader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpinnerLoader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        mGestureDetector = new GestureDetector(new DefaultGestureListener());

        smallCicle = getResources().getDimensionPixelSize(R.dimen.smallcicledp);
        middleCicle = getResources().getDimensionPixelSize(R.dimen.middlecicledp);
        bigCicle = getResources().getDimensionPixelSize(R.dimen.bigcicledp);
        //获取屏幕的宽度
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mWidth = wm.getDefaultDisplay().getWidth();

        final TypedArray attributes;
        attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SpinnerLoader,
                defStyleAttr, 0);
        pointColor = attributes.getColor(R.styleable.SpinnerLoader_point_color, DEFAULT_COLOR);
        boolean isdynamic = attributes.getBoolean(R.styleable.SpinnerLoader_isdynamic, true);
        isDynamic(isdynamic);
        attributes.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measure(widthMeasureSpec, true), measure(heightMeasureSpec, false));
    }

    private int measure(int measureSpec, boolean isWidth) {
        int result;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        int padding = isWidth ? getPaddingLeft() + getPaddingRight() : getPaddingTop() + getPaddingBottom();
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = isWidth ? getSuggestedMinimumWidth() : getSuggestedMinimumHeight();
            result += padding;
            if (mode == MeasureSpec.AT_MOST) {
                if (isWidth) {
                    result = Math.max(result, size);
                } else {
                    result = Math.min(result, size);
                }
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (isFirst) {
            init();
            isFirst = false;
        }

        //每次滑动都会改变每个小圆的圆心的坐标
        for (int i = 0; i < POINTS_COUNT; i ++) {
            CirclePoint p = circlePoints[i];
            //此处的xy是小圆圆心的坐标
            p.x = (float) (getPaddingLeft() + bigCircleCenterX + (float)Math.cos(Math.toRadians(p.currentAngle)) * 1.6 * raduis);
            p.y = (float) (getPaddingTop() + bigCircleCenterY + (float)Math.sin(Math.toRadians(p.currentAngle)) * 1.6 * raduis);
            p.currentAngle = p.currentAngle + bigStep;
            Log.d("MyTest", "x is " + p.x + " y is " + p.y);
        }

        if (isFirstDraw) {
            //进行初始状态的绘制
            for (int i = 0; i < POINTS_COUNT; i++) {
                if (i == 1 || i == 5){
                    canvas.drawCircle(circlePoints[i].x, circlePoints[i].y - getHeight() / 3, smallCicle, smallCirclePaint);
                    circlePoints[i].raduis = 100;
                } else if (i == 2 || i == 4){
                    canvas.drawCircle(circlePoints[i].x, circlePoints[i].y - getHeight()/3, middleCicle, middleCirclePaint);
                    circlePoints[i].raduis = 120;
                } else  if (i == 3) {
                    canvas.drawCircle(circlePoints[i].x, circlePoints[i].y - getHeight()/3, bigCicle, bigCirclePaint);
                    circlePoints[i].raduis = 160;
                }

                circlePoints[i].drawSelfText(canvas, this);
            }

            //保存初始状态点
            for (int i = 0; i < circlePoints.length; i++) {

                initialPoints[i] = new CirclePoint();


                initialPoints[i].currentAngle = circlePoints[i].currentAngle;
                initialPoints[i].raduis = circlePoints[i].raduis;
                initialPoints[i].x = circlePoints[i].x;
                initialPoints[i].y = circlePoints[i].y;
                initialPoints[i].color = circlePoints[i].color;
            }

            leftTouchIndex = 5;
            isFirstDraw = false;
        }

        for (int i = 1; i < POINTS_COUNT; i++) {

        }

        //向左边滑动
        if (direction == Const.MAIN_VIEW_SLIP_DIRECTION_LEFT) {
            //判断最左边点的范围
            int pointRange = isOutScreen(circlePoints[leftTouchIndex]);

            if (pointRange == Const.LEFT_OUT_OF_SCREEN) {
                //出去屏幕了, 更改最左边的点的坐标, 但是不修改半径
                circlePoints[leftTouchIndex].currentAngle = initialPoints[1].currentAngle;
                circlePoints[leftTouchIndex].raduis = 100;
                circlePoints[leftTouchIndex].x = initialPoints[1].x;
                circlePoints[leftTouchIndex].y = initialPoints[1].y;

                //更改相邻四个点的半径
                circlePoints[(leftTouchIndex + 1) > 5 ? (leftTouchIndex+1)%5:(leftTouchIndex + 1)].raduis = 120;
                circlePoints[(leftTouchIndex + 2) > 5 ? (leftTouchIndex+2)%5:(leftTouchIndex + 2)].raduis = 160;
                circlePoints[(leftTouchIndex + 3) > 5 ? (leftTouchIndex+3)%5:(leftTouchIndex + 3)].raduis = 120;
                circlePoints[(leftTouchIndex + 4) > 5 ? (leftTouchIndex+4)%5:(leftTouchIndex + 4)].raduis = 100;

                //设置当前选中设备的下标
                selectedDeviceIndex = (leftTouchIndex + 2) > 5 ? (leftTouchIndex+2)%5:(leftTouchIndex + 2);

                //挨个进行重新绘制
                circlePoints[leftTouchIndex].drawNextState(canvas, direction, true, this);
                //绘制之后的四个点
                for (int i = 1; i < 5; i++) {
                    int cicleIndex = (leftTouchIndex + i) > 5 ? (leftTouchIndex+i)%5:(leftTouchIndex + i);
                    circlePoints[cicleIndex].drawNextState(canvas, direction, true, this);
                }

                //修改最左边点的下标
                if (leftTouchIndex == 1) {
                    leftTouchIndex = 5;
                } else {
                    leftTouchIndex--;
                }

                //通知设备变化监听者
                deviceChangeLisener.selectDeviceChange(circlePoints[selectedDeviceIndex]);

            } else {
                for (int i = 1; i < 5; i++) {
                    int cicleIndex = (leftTouchIndex + i) > 5 ? (leftTouchIndex+i)%5:(leftTouchIndex + i);
                    circlePoints[cicleIndex].drawNextState(canvas, direction, false, this);
                }
                circlePoints[leftTouchIndex].drawNextState(canvas, direction, false, this);
            }
            direction = 0;
        } else {
            //非滑动绘制,在没有手势操作的情况下也应该进行一次绘制
            for (int i = 0; i < POINTS_COUNT; i++) {
                if (i == 1 || i == 5){
                    canvas.drawCircle(circlePoints[i].x, circlePoints[i].y - getHeight() / 3, smallCicle, smallCirclePaint);
                } else if (i == 2 || i == 4){
                    canvas.drawCircle(circlePoints[i].x, circlePoints[i].y - getHeight()/3, middleCicle, middleCirclePaint);
                } else  if (i == 3) {
                    canvas.drawCircle(circlePoints[i].x, circlePoints[i].y - getHeight()/3, bigCicle, bigCirclePaint);
                }
                circlePoints[i].drawSelfText(canvas, this);
            }
        }

    }

    protected void init() {
        float temp = getHeight() > getWidth() ? getWidth() / 2 : getHeight() / 2;
        raduis = temp - temp / DEFAULT_RADUIS * DEFAULT_CIRCLE_RADUIS;
        circleRaduis = DEFAULT_CIRCLE_RADUIS / DEFAULT_RADUIS * raduis;
        moveRaduis = DEAFULT_MOVE_RADUIS / DEFAULT_RADUIS * raduis;
        bigCircleCenterX = getPaddingLeft() + getWidth() / 2;
        bigCircleCenterY = getPaddingTop() + getHeight() / 2;

        path1 = new Path();
        initializePaints();
        initializePoints();
    }



    protected void initializePoints() {
        for (int i = 0; i < POINTS_COUNT; i++) {
            CirclePoint p = new CirclePoint();
            p.currentAngle = SPLIT_ANGLE * i;
            p.x = getPaddingLeft() + bigCircleCenterX + (float) Math.cos(Math.toRadians(p.currentAngle)) * raduis;
            p.y = getPaddingTop() + bigCircleCenterY + (float)Math.sin(Math.toRadians(p.currentAngle)) * raduis;
            p.color = pointColor;

            if (i == 1) {
                p.deviceName = getResources().getString(R.string.glycolipid);
            } else if (i == 2) {
                p.deviceName = getResources().getString(R.string.aircat);
            } else if (i == 3) {
                p.deviceName = getResources().getString(R.string.bracelete);
            } else if (i == 4) {
                p.deviceName = getResources().getString(R.string.bloodpress);
            } else if (i == 5) {
                p.deviceName = getResources().getString(R.string.bodyfat);
            }
            circlePoints[i] = p;
        }
    }

    protected void initializePaints() {
        bigCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bigCirclePaint.setColor(getResources().getColor(R.color.bigCircle));

        middleCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        middleCirclePaint.setColor(getResources().getColor(R.color.middleCircle));

        smallCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        smallCirclePaint.setColor(getResources().getColor(R.color.smallCircle));

        big4Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        big4Paint.setTextSize(70);
        big4Paint.setColor(getResources().getColor(R.color.blue));
        Typeface font = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD);
        big4Paint.setTypeface(font);


        middle3Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        middle3Paint.setTextSize(55);
        middle3Paint.setColor(getResources().getColor(R.color.blue));
        middle3Paint.setTypeface(font);

        small3Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        small3Paint.setTextSize(44);
        small3Paint.setColor(getResources().getColor(R.color.blue));
        small3Paint.setTypeface(font);
    }

    public void isDynamic(boolean dynamic) {
        if (dynamic) {
            bigStep = BIG_STEP;
        } else {
            bigStep = 0;
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        final Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATE,super.onSaveInstanceState());
        bundle.putInt(ANGLE, angle);
        bundle.putFloat(BIGCIRCLECENTERX, bigCircleCenterX);
        bundle.putFloat(BIGCIRCLECENTERY, bigCircleCenterY);
        bundle.putFloat(RADUIS, raduis);
        bundle.putFloat(CIRCLERADUIS, circleRaduis);
        bundle.putFloat(MOVERADUIS, moveRaduis);
        bundle.putFloat(STARTX1, startX1);
        bundle.putFloat(STARTY1, startY1);
        bundle.putFloat(ENDX1, endX1);
        bundle.putFloat(ENDY1, endY1);
        bundle.putFloat(STARTX2, startX2);
        bundle.putFloat(STARTY2, startY2);
        bundle.putFloat(ENDX2, endX2);
        bundle.putFloat(ENDY2, endY2);
        bundle.putFloat(CONTROLX1, controlX1);
        bundle.putFloat(CONTROLY1, controlY1);
        bundle.putInt(POINTCOLOR, pointColor);
        bundle.putFloat(BIGSTEP, bigStep);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if(state instanceof Bundle){
            final Bundle bundle = (Bundle)state;
            angle = bundle.getInt(ANGLE);
            bigCircleCenterX = bundle.getFloat(BIGCIRCLECENTERX);
            bigCircleCenterY = bundle.getFloat(BIGCIRCLECENTERY);
            raduis = bundle.getFloat(RADUIS);
            circleRaduis = bundle.getFloat(CIRCLERADUIS);
            moveRaduis = bundle.getFloat(MOVERADUIS);
            startX1 = bundle.getFloat(STARTX1);
            startY1 = bundle.getFloat(STARTY1);
            endX1 = bundle.getFloat(ENDX1);
            endY1 = bundle.getFloat(ENDY1);
            startX2 = bundle.getFloat(STARTX2);
            startY2 = bundle.getFloat(STARTY2);
            endX2 = bundle.getFloat(ENDX2);
            endY2 = bundle.getFloat(ENDY2);
            controlX1 = bundle.getFloat(CONTROLX1);
            controlY1 = bundle.getFloat(CONTROLY1);
            pointColor = bundle.getInt(POINTCOLOR);
            bigStep = bundle.getInt(BIGSTEP);
            init();
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE));
            return;
        }
        super.onRestoreInstanceState(state);
    }


    //x的坐标和屏幕之间是相反的关系
    public static class CirclePoint {
        public float currentAngle;
        public float raduis;
        public float x;
        public float y;
        public int color;
        public String deviceName;


        public void drawNextState(Canvas canvas, int slipDirection, boolean isOutofScreen, SpinnerLoader parent) {

            Log.d("CirclePoint", "direction is " + slipDirection + " isoutOfScreen is " + isOutofScreen + " raduis is " + this.raduis + " devices" +
                    "name is " + deviceName);

            if (slipDirection == Const.MAIN_VIEW_SLIP_DIRECTION_LEFT) {
                if (this.raduis == 160) {
                    //大圆逻辑处理
                    if (isOutofScreen) {
                        canvas.drawCircle(this.x, this.y - parent.getHeight() / 3, parent.middleCicle, parent.middleCirclePaint);
                    } else {
                        canvas.drawCircle(this.x, this.y - parent.getHeight() / 3, parent.bigCicle, parent.bigCirclePaint);
                    }

                } else if (this.raduis == 120) {
                    //中间的圆逻辑处理
                    if (isOutofScreen) {
                        if (x < parent.getWidth()/2) {
                            canvas.drawCircle(this.x, this.y - parent.getHeight() / 3, parent.bigCicle, parent.bigCirclePaint);
                        } else {
                            canvas.drawCircle(this.x, this.y - parent.getHeight() / 3, parent.smallCicle, parent.smallCirclePaint);
                        }
                    } else {
                        canvas.drawCircle(this.x, this.y - parent.getHeight() / 3, parent.middleCicle, parent.middleCirclePaint);
                    }

                } else if (this.raduis == 100) {
                    //最小的圆逻辑处理
                    if (isOutofScreen) {
                        if (x < parent.getWidth()/2) {
                            canvas.drawCircle(this.x, this.y - parent.getHeight() / 3, parent.middleCicle, parent.middleCirclePaint);
                        } else {
                            canvas.drawCircle(this.x, this.y - parent.getHeight() / 3, parent.smallCicle, parent.smallCirclePaint);
                        }
                    } else {
                        canvas.drawCircle(this.x, this.y - parent.getHeight() / 3, parent.smallCicle, parent.smallCirclePaint);
                    }
                }

                drawSelfText(canvas, parent);
            }
        }

        private void drawSelfText(Canvas canvas, SpinnerLoader parent) {

            if (raduis == 160) {
                if (deviceName.length() == 3) {
                    canvas.drawText(deviceName, x - 105, y + 25 - parent.getHeight()/3, parent.big4Paint);
                } else {
                    canvas.drawText(deviceName, x - 140, y + 25 - parent.getHeight()/3, parent.big4Paint);
                }
            } else if (raduis == 120) {
                if (deviceName.length() == 3) {
                    canvas.drawText(deviceName, x - 82, y + 25 - parent.getHeight()/3, parent.middle3Paint);
                } else {
                    canvas.drawText(deviceName, x - 110, y + 25 - parent.getHeight()/3, parent.middle3Paint);
                }
            } else if (raduis == 100) {
                if (deviceName.length() == 3) {
                    canvas.drawText(deviceName, x - 66, y + 25 - parent.getHeight()/3, parent.small3Paint);
                } else {
                    canvas.drawText(deviceName, x - 88, y + 25 - parent.getHeight()/3, parent.small3Paint);
                }
            }

        }

    }

    public int isOutScreen(CirclePoint point) {

        if (point == null) {
            return -1;
        }

        if (point.x <= -130) {
            return Const.LEFT_OUT_OF_SCREEN;
        } else if (point.x >= (getWidth() + 120)) {
            return Const.RIGHT_OUT_OF_SCREEN;
        } else if (point.x >= 100 && point.x < 120) {
            return Const.OUT_OF_SCREEN_NOT_DISPLAY;
        }
        return 0;
    }

    public void setDeviceChangeListener(ISelectedDeviceChange deviceChange) {
        deviceChangeLisener = deviceChange;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return true;
    }
}
