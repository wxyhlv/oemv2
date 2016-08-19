package com.capitalbio.oemv2.myapplication.View.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * @author lzq
 * @Time 2016/3/8 13:35
 */
public class RingView extends RelativeLayout {

    //
    private Context context;

    //底环 颜色
    private int color_bg_ring = Color.parseColor("#CAD5D9");

    //底环 宽度 单位dp
    private float width_bg_ring = 15f;



    //dp 2 px
    private int dp2px(float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    //前环 颜色
    private int color_fg_ring = Color.parseColor("#51C8B5");

    //前环 宽度 单位dp
    private float width_fg_ring = 15f;

    //当前的进度
    private float curr_progress =0.5f;

    //动画细腻程度-动画自加或自减量
    private float density = 0.001f;


    /*public RingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }*/

    public RingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

    }
    /*public RingView(Context context) {
        this(context,null);
    }*/

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (width > height) {
            setMeasuredDimension(height, height);
        } else {
            setMeasuredDimension(width, width);
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

    }

    private void calculate(){
       calcuBGRing();
        calcuFGAngel();
        calcuFGRing();
    }

    //计算底环的半径
    private float calcuBGRing(){

        float width = width_bg_ring>width_fg_ring?width_bg_ring:width_fg_ring;

        return (getWidth()-dp2px(width))/2;
    }

    //计算前环左上右下
    private RectF calcuFGRing(){
        RectF rectF = new RectF();

        float width = width_bg_ring>width_fg_ring?width_bg_ring:width_fg_ring;

        float l = dp2px(width/2);
        float t = dp2px(width/2);
        float r = getWidth()-dp2px(width/2);
        float b = getHeight()-dp2px(width/2);

        rectF.set(l,t,r,b);
        return rectF;
    }

    //根据当前进度，计算划过的角度
    private float calcuFGAngel(){
        return 360*curr_progress;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBGRing(canvas);
        drawFGRing(canvas);
    }

    //画底环
    private void drawBGRing(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(color_bg_ring);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(dp2px(width_bg_ring));

        canvas.drawCircle(getWidth() / 2, getHeight() / 2, calcuBGRing(), paint);

    }

    //画前环
    private void drawFGRing(Canvas canvas){
        Paint p = new Paint();
        p.setColor(color_fg_ring);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeCap(Paint.Cap.ROUND);
        p.setStrokeWidth(dp2px(width_fg_ring));

        canvas.drawArc(calcuFGRing(), -90, calcuFGAngel(), false, p);
    }

    public void setColor_bg_ring(int color_bg_ring) {
        this.color_bg_ring = color_bg_ring;
        invalidate();
    }

    public void setWidth_bg_ring(float width_bg_ring) {
        this.width_bg_ring = width_bg_ring;
        invalidate();
    }

    public void setColor_fg_ring(int color_fg_ring) {
        this.color_fg_ring = color_fg_ring;
        invalidate();
    }

    public void setWidth_fg_ring(float width_fg_ring) {
        this.width_fg_ring = width_fg_ring;
        invalidate();
    }



    public void setDensity(float density) {
        this.density = density;
    }

    private void setProgress(float progress,boolean isstop){
        this.curr_progress = progress;
        invalidate();
        if(isstop){
            //回调结束
            if(onProgressChangeListener!=null){
                onProgressChangeListener.onStop(curr_progress);
            }
        }else{
            //回调过程
            if(onProgressChangeListener!=null){
                onProgressChangeListener.onChanging(curr_progress);
            }
        }

    }

    //当前是否在执行动画
    private boolean isAnimating = false;

    //设置当前的进度
    public void setCurr_progress(float to_progress) {
        if(to_progress==curr_progress){
            return;
        }
        if(to_progress>1||to_progress<0){
            return;
        }
        if(isAnimating){
            return;
        }

        //回调起始
        if(onProgressChangeListener!=null){
            onProgressChangeListener.onStart(curr_progress);
        }
        new MyTask(curr_progress,to_progress).start();
    }



    //动画人物
    private class MyTask extends Thread{

        private float curr;
        private float to;

        public MyTask(float curr, float to) {
            this.curr = curr;
            this.to = to;
        }

        @Override
        public void run() {
            while (true)
            {
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(to>curr){
                    curr = curr+density;
                    if(curr>to){
                        curr = to;
                        toUI(curr,true);
                        isAnimating = false;
                        break;
                    }

                }else{
                    curr = curr-density;
                    if(curr<to){
                        curr = to;
                        toUI(curr,true);
                        isAnimating = false;
                        break;
                    }
                }

                toUI(curr,false);

            }
        }

        private void toUI(float f,boolean isstop){
            Message msg = new Message();
            msg.obj = new Float(f);
            if(isstop){
                msg.what = 1;
            }else{
                msg.what = 0;
            }
            handler_anim.sendMessage(msg);
        }
    }

    //
    private Handler handler_anim = new Handler(){
        @Override
        public void handleMessage(Message msg){
            Float f = (Float) msg.obj;
            setProgress(f.floatValue(),msg.what==1);
        }
    };

    private OnProgressChangeListener onProgressChangeListener;

    public void setOnProgressChangeListener(OnProgressChangeListener onProgressChangeListener){
        this.onProgressChangeListener = onProgressChangeListener;
    }

    public interface OnProgressChangeListener{

        void onStart(float start);

        void onChanging(float curr);

        void onStop(float stop);
    }
}
