package com.capitalbio.oemv2.myapplication.View.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author lzq
 * @Time 2015/12/14 16:13
 */
public class BioBalance3 extends View {

    private int width;
    private int height;

    //当前刻度
    private float currScale = 0;
    //刻度排列方向(true:左大右小.false：左小右大)
    private boolean flag = true;
    //最大刻度
    private float max = 150;
    //单位刻度-最小单位值所占刻度 0.1kg占角度数
    private float unit = 1;

    //灰色边缘的宽度 单位dp
    private float width_edge_gray = 15;
    //白色背景区域的半径 单位dp
    private float radius_white_bg = 50;
    //灰色底线的宽度 单位dp
    private float width_bottom_line = 5;
    //阴影宽度 单位dp
    private float width_shadow = 10;


    public BioBalance3(Context context) {
        super(context);
    }

    public BioBalance3(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BioBalance3(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawGrayBG(canvas);
        drawBlueBG(canvas);

        //画刻度
        drawScale(canvas);
        //画暗影
        drawShadow(canvas);
        //画灰色底线
        drawBottomLine(canvas);

        //drawWhiteBG(canvas);

        //画指针
        drawPointer(canvas);
        //画数值
        drawValue(canvas);
    }

    //先绘制灰色背景
    private void drawGrayBG(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#E1E1E1"));
        paint.setAntiAlias(true);
        RectF rectF = new RectF();
        rectF.set(0, 0, width, height * 2);
        canvas.drawArc(rectF, 180f, 180f, true, paint);
    }

    //绘制蓝色底
    private void drawBlueBG(Canvas canvas) {

        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#00A4BF"));
        paint.setAntiAlias(true);
        RectF rectF = new RectF();
        float width_edge_gray_f = dip2px(width_edge_gray);
        rectF.set(width_edge_gray_f, width_edge_gray_f, width - width_edge_gray_f, height * 2 - width_edge_gray_f);
        canvas.drawArc(rectF, 180f, 180f, true, paint);

    }

    //画刻度
    private void drawScale(Canvas canvas) {
    }

    //画暗影
    private void drawShadow(Canvas canvas) {
       //setShadowLayer
    }

    //画灰色底线
    private void drawBottomLine(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#E1E1E1"));

        float width_bottom_line_f = dip2px(width_bottom_line);
        canvas.drawRect(0,height-width_bottom_line_f,width,height,paint);
    }

    //绘制白色背景
    private void drawWhiteBG(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#FFFFFF"));
        paint.setAntiAlias(true);
        RectF rectF = new RectF();
        float radius_white_bg_f = dip2px(radius_white_bg);
        float left = (width - radius_white_bg_f - radius_white_bg_f) / 2;
        float top = (height * 2 - radius_white_bg_f - radius_white_bg_f) / 2;

        rectF.set(left, top, width - left, top + radius_white_bg_f * 2);
        canvas.drawArc(rectF, 180f, 180f, true, paint);
    }

    //画指针
    private void drawPointer(Canvas canvas) {
    }

    //画数值
    private void drawValue(Canvas canvas) {
    }

    //绘制一级刻度
    private void drawScale1(Canvas canvas) {


    }

    //绘制二级刻度
    private void drawScale2(Canvas canvas) {

    }

    //根据当前刻度计算所有刻度bean

    //从中间往右算
    private void calcuRightOneScale() {

    }
    //从中间往左算


    //刻度bean
    private class ScaleBean {
        private float scale;
        private float angle;//从正东方向开始起算,向上增加
        private float innerRadius;
        private float outerRadius;
        private int level;//刻度的等级

        public ScaleBean() {
        }

        public float getScale() {
            return scale;
        }

        public float getAngle() {
            return angle;
        }

        public float getOuterRadius() {
            return outerRadius;
        }

        public float getInnerRadius() {
            return innerRadius;
        }

        public void setScale(float scale) {
            this.scale = scale;
        }

        public void setAngle(float angle) {
            this.angle = angle;
        }

        public void setInnerRadius(float innerRadius) {
            this.innerRadius = innerRadius;
        }

        public void setOuterRadius(float outerRadius) {
            this.outerRadius = outerRadius;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    private int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    private int px2dip(float pxValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
