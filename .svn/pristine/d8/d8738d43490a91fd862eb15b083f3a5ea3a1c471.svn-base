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
 * @author lzq
 * @Time 2016/2/26 13:50
 */
public class SphygmomanometerView2 extends View {


    private Bitmap disc;
    private Bitmap pointer;

    //指针的圆盘部分的直径与转盘直径的比例
    private static final float ratio_diameter = (float) 78 / 151;

    //指针的箭头部分与转盘直径的比例
    private static final float ratio_tip = (float) 7 / 151;


    //转盘的角度
    private float degree;

    //handler
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            if(what==0){
                stop();
            }
            if(what==1){
                invalidate();
            }

        }
    };

    //动画线程
    private DiscRun discRun;

    //转盘的状态
    private boolean state = false;

    //停止动画的线程
    private StopRun stopRun;





    public SphygmomanometerView2(Context context, AttributeSet attrs) {
        super(context, attrs);

        getRes(context);
    }

    //资源获取
    private void getRes(Context context) {
        BitmapDrawable bddisc = (BitmapDrawable) context.getResources().getDrawable(R.drawable.disc_bloodpress);
        BitmapDrawable bdpointer = (BitmapDrawable) context.getResources().getDrawable(R.drawable.pointer_bloodpress);

        disc = bddisc.getBitmap();
        pointer = bdpointer.getBitmap();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();

        canvas.rotate(degree,getWidth()/2,getHeight()/2);

        drawDisc(canvas);

        canvas.restore();
        drawPointer(canvas);

    }

    //画转盘
    private void drawDisc(Canvas canvas) {

        Rect src = new Rect();
        src.set(0, 0, disc.getWidth(), disc.getHeight());


        Rect dst = new Rect();
        dst = calculateAreaOfShowDisc();

        Paint p = new Paint();
        p.setAntiAlias(true);

        canvas.drawBitmap(disc, src, dst, p);
    }

    //计算转盘的绘制区域
    private Rect calculateAreaOfShowDisc() {
        Rect dst = new Rect();

        int edge = calculateEdgeOfDrawableArea();

        float l;
        float t;
        float r;
        float b;

        l = (float) (getWidth() - edge) / 2;
        t = (float) (getHeight() - edge) / 2;
        r = l + edge;
        b = t + edge;

        dst.set((int) l, (int) t, (int) r, (int) b);

        return dst;
    }

    //画指针
    private void drawPointer(Canvas canvas) {
        Rect src = new Rect();
        src.set(0, 0, pointer.getWidth(), pointer.getHeight());

        Rect dst = new Rect();
        dst = calculateAreaOfShowPointer();

        Paint p = new Paint();
        p.setAntiAlias(true);

        canvas.drawBitmap(pointer, src, dst, p);

    }

    //计算指针的绘制区域
    private Rect calculateAreaOfShowPointer() {
        Rect rect = new Rect();

        int edge = calculateEdgeOfDrawableArea();
        float diameter_pointer = edge * ratio_diameter;
        float tip_pointer = edge * ratio_tip;

        int l;
        int t;
        int r;
        int b;

        l = (int) ((float) (getWidth() - diameter_pointer) / 2);
        t = (int) ((getHeight() - diameter_pointer) / 2 - tip_pointer);
        r = getWidth() - l;
        b = (int) (getHeight() - ((getHeight() - diameter_pointer) / 2));


        rect.set(l, t, r, b);
        return rect;
    }

    //计算绘制区域的边长
    private int calculateEdgeOfDrawableArea() {
        int width = getWidth();
        int height = getHeight();

        int edge;

        edge = width > height ? height : width;

        return edge;
    }

    //开始
    public void start(){
        if(state){
            return;
        }
       if(discRun==null){
           discRun = new DiscRun(handler);
       }

        discRun.start();
        state = true;
    }

    //停
    public void stop(){
       if(!state){
           return;
       }
        if(discRun==null){
            return;
        }

        state = false;
        discRun = null;
        stopRun = null;
    }

    //停在某个指定角度
    public void stop(float degree){
        if(!state){
            return;
        }
        if(stopRun!=null){
            stopRun = null;
        }
        stopRun = new StopRun(handler,degree);
        stopRun.start();
    }

    //结束转盘转动线程
    private class StopRun extends Thread{
        private Handler handler;
        private float stopdegree;//要停在的角度

        public StopRun(Handler handler, float degree) {
            this.handler = handler;
            this.stopdegree = degree;
        }

        @Override
        public void run() {
            while (state)
            {
                Log.i("info","当前角度====="+(int)(degree%360)+"要停在的角度======"+(int)stopdegree);
                if((int)(degree%360)==(int)stopdegree){
                   handler.sendEmptyMessage(0);
                }
            }
        }
    }

    //转盘转动线程
    private class DiscRun extends Thread{
       private Handler handler;

        public DiscRun(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void run() {

            while(state){
                try {
                    sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                degree +=0.1;
                handler.sendEmptyMessage(1);
            }

        }
    }

}
