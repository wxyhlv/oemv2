package com.capitalbio.oemv2.myapplication.View.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.capitalbio.oemv2.myapplication.R;


/**
 * @author jiantao.tu
 * @Time 2015/10/28 9:41
 */
public class CatQiPaoView extends ViewGroup {

    private Bitmap bitmap;
    private Context context;
    //控件的宽度
    private int width;

    private int padding;

    //private double percent=0;

    private AnimThread animThread =null;

    private float animSweepAngle=0;
    //进度弧线的颜色
    private int color_arc;
    //动画的执行时间
    private int animDuration = 1000;
    //记录是否已经设置过显示比例
    private boolean isAlreadySetPercent =false;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            long i = (long) msg.obj;
            animSweepAngle= Math.round((float)i/1000*360);
            invalidate();
        }
    };

    public CatQiPaoView(Context context) {
        super(context);
    }

    public CatQiPaoView(Context context, AttributeSet attrs) {
        this(context,attrs,0);

    }

    public CatQiPaoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        padding = this.getPaddingTop();
        getBitmap();
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,R.styleable.CatQiPaoView,defStyleAttr,0);
        color_arc = typedArray.getColor(R.styleable.CatQiPaoView_color_of_arc, 0xffffffff);
        animDuration = typedArray.getInteger(R.styleable.CatQiPaoView_animDuration,1000);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        width = calculateWidth();
        setMeasuredDimension(width + padding * 2, width + padding * 2);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

        int index = getChildCount();
        float b_0=0;
        float b_1=0;
        for (int j = 0; j < index; j++) {
            switch (j) {
                case 0:
                    //摆放第一个
                    View child0 = getChildAt(j);
                    b_0 = layoutChildOne(child0);
                    break;
                case 1:
                    //摆放第二个
                    View child1 = getChildAt(j);
                    float l_1 = (float)width/2-(float)getChildParams(child1)[0]/2+padding;
                    float t_1 =b_0 +getChildParams(child1)[2];
                    float r_1 = (float)width/2-(float)getChildParams(child1)[0]/2+getChildParams(child1)[0]+padding;
                    b_1 = b_0 +getChildParams(child1)[2]+getChildParams(child1)[1];
                    child1.layout(Math.round(l_1),Math.round(t_1),Math.round(r_1),Math.round(b_1));
                    break;
                case 2:
                    //摆放第三个
                    View child2 = getChildAt(j);
                    float l_2 = (float)width/2-(float)getChildParams(child2)[0]/2+padding;
                    float t_2 =b_1 +getChildParams(child2)[2];
                    float r_2 = (float)width/2-(float)getChildParams(child2)[0]/2+getChildParams(child2)[0]+padding;
                    float b_2 = b_1 +getChildParams(child2)[2]+getChildParams(child2)[1];
                    child2.layout(Math.round(l_2),Math.round(t_2),Math.round(r_2),Math.round(b_2));
                    break;
            }
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawQiPao(canvas);
        //画外侧的进度圈
        drawCircle(canvas);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    /**
     * 获取子控件中最宽的一个的宽度
     *
     * @return
     */
    private int checkMaxWidthChild() {
        int count = getChildCount();
        int maxwidth = 0;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            int width = child.getMeasuredWidth();
            int margin = 0;
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) child.getLayoutParams();
            if (marginLayoutParams != null) {
                margin = marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
            }
            int sum = width + margin;
            if (sum > maxwidth) {
                maxwidth = sum;
            }
        }
        return maxwidth;
    }

    /**
     * 计算所有子控件的高度和(包括margin参数在内)
     *
     * @return
     */
    private int getChildrenHeightSum() {
        int count = getChildCount();
        int heightsum = 0;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            int childheight = child.getMeasuredHeight();
            MarginLayoutParams marginLayoutParams = (MarginLayoutParams) child.getLayoutParams();
            if (marginLayoutParams != null) {
                childheight = childheight + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
            }
            heightsum = heightsum + childheight;
        }
        return heightsum;
    }

    /**
     * 根据最宽子控件的宽度和所有子控件高度的和来计算该控件需要设置的宽高
     *
     * @return
     */
    private int calculateWidth() {
        int heigh = getChildrenHeightSum();
        int width = checkMaxWidthChild();
        double height2 = Math.pow(heigh, 2);
        double width2 = Math.pow(width, 2);
        double hypotenuse2 = height2 + width2;
        double hypotenuse = Math.sqrt(hypotenuse2);
        return (int) Math.ceil(hypotenuse);
    }

    private void getBitmap() {
        BitmapDrawable drawable = (BitmapDrawable) context.getResources().getDrawable(R.drawable.qipao);
        bitmap = drawable.getBitmap();
    }

    private int bitmapWidth() {
        return bitmap.getWidth();
    }

    private float getScaleRate() {
        int bitmapwidth = bitmapWidth();
        int viewwidth = calculateWidth();
        return (float) viewwidth / bitmapwidth;
    }

    private void drawQiPao(Canvas canvas) {
        float rate = getScaleRate();
        Paint paint = new Paint();
        Matrix matrix = new Matrix();
        matrix.postScale(rate, rate);
        bitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        canvas.drawBitmap(bitmap,padding,padding,paint);
    }

    /**
     * 获取子控件的三个参数
     *
     * @param child
     * @return
     */
    private int[] getChildParams(View child) {
        int[] params = new int[3];
        int width = child.getMeasuredWidth();
        int height = child.getMeasuredHeight();
        int margintop = 0;
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) child.getLayoutParams();
        if (marginLayoutParams != null) {
            margintop = marginLayoutParams.topMargin;
        }
        params[0] = width;
        params[1] = height;
        params[2] = margintop;
        return params;
    }

    /**
     * 摆放第一个
     *
     * @param child 返回该控件的bottom y;
     */
    private float layoutChildOne(View child) {
        int[] params = getChildParams(child);
        float l = 0;
        float t = 0;
        float r = 0;
        float b = 0;
        l = (float) width / 2 - (float) params[0] / 2+padding;
        t = (float) width / 2 - gouGu((float) width / 2, (float) params[0] / 2) + params[2]+padding;
        r = (float) width / 2 - (float) params[0] / 2 + params[0]+padding;
        b = (float) width / 2 - gouGu((float) width / 2, (float) params[0] / 2) + params[2] + params[1]+padding;
        child.layout(Math.round(l), Math.round(t), Math.round(r), Math.round(b));
        return b;
    }

    /**
     * 求直角三角形的一条直角边
     *
     * @param c 斜边
     * @param a 直角边
     * @return
     */
    private int gouGu(float c, float a) {
        double c2 = Math.pow(c, 2);
        double a2 = Math.pow(a, 2);
        double b2 = c2 - a2;
        double b = Math.sqrt(b2);
        return (int) Math.floor(b);
    }

    private void drawCircle(Canvas canvas){
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color_arc);
        paint.setStrokeWidth(10);
        paint.setStrokeCap(Cap.ROUND);//设置进度条的，两头是否是圆角
        RectF rectF = new RectF(padding,padding,width+padding,width+padding);

       canvas.drawArc(rectF,-90,animSweepAngle,false,paint);
    }

    public void setPercent(float percent){
        //this.percent = percent;
    	isAlreadySetPercent = true;
        if(animThread!=null&&animThread.isAlive()){
            return;
        }else{
            animThread = null;
            animThread = new AnimThread(percent);
            animThread.start();
        }
    }
    
    public void updatePercent(float percent){
    	animSweepAngle = percent*360;
    	invalidate();
    }

    private class AnimThread extends  Thread{

        private float percent=0;
        private long sleeptime;
        public AnimThread(float percent){
            this.percent = percent;
            float sleep = (float)animDuration/Math.round(percent*1000);
            sleeptime = Math.round(sleep);
        }

        @Override
        public void run() {
           double d =  Math.ceil(percent * 1000);
            long per = Math.round(d);
            for(long i=0;i<per;i++){
                try {
                    Thread.sleep(sleeptime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Message message = new Message();
                message.obj = i;
               handler.sendMessage(message);

            }
        }
    }
    
    /**
     * 设置弧线的现实颜色
     * @param color
     */
    public void setARCColor(int color){
    	this.color_arc = color;
    }
    
    public boolean isAlreadySetPercent(){
    	return isAlreadySetPercent;
    }
}
