package com.capitalbio.oemv2.myapplication.View.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.capitalbio.oemv2.myapplication.R;


/**
 * Created by jiantaotu on 2016/3/30.
 */
public class BloodpressRuler extends View {

    private Ruler ruler_bean;

    private Bitmap ruler_bp;

    public BloodpressRuler(Context context, AttributeSet attrs) {
        super(context, attrs);
        getRes();
        init();
    }

    private void getRes(){
        BitmapDrawable bd_ruler = (BitmapDrawable) getResources().getDrawable(R.drawable.bloodpressruler);
        ruler_bp = bd_ruler.getBitmap();
    }

    private void init(){
        ruler_bean  = new Ruler();
        Rect rect = new Rect(0,0,ruler_bp.getWidth(),ruler_bp.getHeight());
        ruler_bean.setRect_px(rect);
        ruler_bean.setAspect_ratio((float)ruler_bp.getWidth()/ruler_bp.getHeight());
        ruler_bean.setValue(0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int hei = getMeasuredHeight();
        int wid = getMeasuredWidth();


        ruler_bean.setWidth_draw(wid);
        ruler_bean.setHeight_draw(hei);

        ruler_bean.setRect_draw(new Rect(0, 0, wid, hei));
        ruler_bean.setHeigh_unit(hei / 340f);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawGray(canvas);
        drawValue(canvas);
        drawBG(canvas);

    }

    private void drawGray(Canvas canvas){
        Paint p = new Paint();
        p.setColor(Color.parseColor("#ECF2F2"));
        canvas.drawRect(new Rect(0, 0, ruler_bean.getWidth_draw(), ruler_bean.getHeight_draw()), p);
    }

    private void drawValue(Canvas canvas){
        float hei_value = (ruler_bean.getValue()+20)*ruler_bean.getHeigh_unit();
        Paint p = new Paint();
        p.setColor(Color.parseColor("#FFB9C1"));
        canvas.drawRect(new RectF(0,ruler_bean.getHeight_draw()-hei_value,ruler_bean.getWidth_draw(),ruler_bean.getHeight_draw()),p);
    }

    public void setValue(int value){
        if(ruler_bean!=null){
            ruler_bean.setValue(value);
            invalidate();
        }
    }

    private void drawBG(Canvas canvas){
        canvas.drawBitmap(ruler_bp,ruler_bean.getRect_px(),ruler_bean.getRect_draw(),new Paint());
    }


    private class Ruler{
        private Rect rect_px;

        private int width_draw;
        private int height_draw;

        private Rect rect_draw;

        private float aspect_ratio;//宽高比

        private int value;//当前显示的值

        private float heigh_unit;//一单位的高度

        public Ruler() {
        }

        public Rect getRect_px() {
            return rect_px;
        }

        public void setRect_px(Rect rect_px) {
            this.rect_px = rect_px;
        }

        public int getWidth_draw() {
            return width_draw;
        }

        public void setWidth_draw(int width_draw) {
            this.width_draw = width_draw;
        }

        public int getHeight_draw() {
            return height_draw;
        }

        public void setHeight_draw(int height_draw) {
            this.height_draw = height_draw;
        }

        public Rect getRect_draw() {
            return rect_draw;
        }

        public void setRect_draw(Rect rect_draw) {
            this.rect_draw = rect_draw;
        }

        public float getAspect_ratio() {
            return aspect_ratio;
        }

        public void setAspect_ratio(float aspect_ratio) {
            this.aspect_ratio = aspect_ratio;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public float getHeigh_unit() {
            return heigh_unit;
        }

        public void setHeigh_unit(float heigh_unit) {
            this.heigh_unit = heigh_unit;
        }
    }
}
