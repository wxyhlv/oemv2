package com.capitalbio.oemv2.myapplication.View.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;


import com.capitalbio.oemv2.myapplication.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 129 on 2016/3/22.
 */
public class VernierView extends View implements GestureDetector.OnGestureListener {

    public static final int SIX_V_VERNIER = 6;
    public static final int FIVE_V_VERNIER = 5;
    public static final int THREE_V_VERNIER = 3;

    //控件的宽高
    public int width;
    public int height;

    public Bitmap bp_ruler;
    public Bitmap bp_cursor;

    //ruler
    public int width_ruler_original;
    public int height_ruler_original;

    public int width_ruler_actual;
    public int height_ruler_actual;

    public float proportion_ruler;

    public Rect rect_ruler_original;

    public Rect rect_ruler_actual;

    //cursor
    public int width_cursor_original;
    public int height_cursor_original;

    public int width_cursor_actual;
    public int height_cursor_actual;

    public float proportion_cursor;

    public Rect rect_cursor_original;


    //尺子高度与游标高度的比
    private final float proportion_rulerH_cursorH = 22f / 50f;


    //attrs
    private float textsize;
    private int levelsize;
    private int initiallevel;
    private String initialtext;

    //动画
    private int curr_level;

    //游标
    private List<CursorPosBean> cursorPosBeans;
    private CursorPosBean cursorPosBean_curr;

    //text
    private CursorTextBean cursorText_curr;

    //
    private String unit = "";

    //
    private int decimal_type;

    //
    private int text_color;


    public VernierView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //Log.i("info", "===========================VernierView");

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.VernierView);
        textsize = typedArray.getDimension(R.styleable.VernierView_textsize, 22f);
        levelsize = typedArray.getInteger(R.styleable.VernierView_levelsize, 6);
        initiallevel = typedArray.getInteger(R.styleable.VernierView_initiallevel, 1);
        initialtext = typedArray.getString(R.styleable.VernierView_initialtext);
        unit = typedArray.getString(R.styleable.VernierView_textunit);
        decimal_type = typedArray.getInt(R.styleable.VernierView_decimal_type, 0);
        text_color = typedArray.getColor(R.styleable.VernierView_textcolor, Color.parseColor("#FFFFFF"));

        //Log.i("info", "===============" + initialtext);
        getRes(levelsize);


        initVariable(context);

    }

    private void initVariable(Context context){
        cursorPosBeans = new ArrayList<>();
        gestureDetector = new GestureDetector(this);
    }

    public void getRes(int type) {
        if (type == SIX_V_VERNIER) {
            BitmapDrawable bd_six = (BitmapDrawable) getResources().getDrawable(R.drawable.stick_six);
            bp_ruler = bd_six.getBitmap();
        }
        if (type == FIVE_V_VERNIER) {
            BitmapDrawable bd_five = (BitmapDrawable) getResources().getDrawable(R.drawable.stick_five);
            bp_ruler = bd_five.getBitmap();
        }
        if (type == THREE_V_VERNIER) {
            BitmapDrawable bd_three = (BitmapDrawable) getResources().getDrawable(R.drawable.stick_three);
            bp_ruler = bd_three.getBitmap();
        }

        width_ruler_original = bp_ruler.getWidth();
        height_ruler_original = bp_ruler.getHeight();

        rect_ruler_original = new Rect(0, 0, width_ruler_original, height_ruler_original);

        proportion_ruler = (float) width_ruler_original / height_ruler_original;

        BitmapDrawable db_cursor = (BitmapDrawable) getResources().getDrawable(R.drawable.cursor);
        bp_cursor = db_cursor.getBitmap();

        width_cursor_original = bp_cursor.getWidth();
        height_cursor_original = bp_cursor.getHeight();

        rect_cursor_original = new Rect(0, 0, width_cursor_original, height_cursor_original);

        proportion_cursor = (float) width_cursor_original / height_cursor_original;


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //Log.i("info","===========================onMeasure");

        width = getMeasuredWidth();

        width_ruler_actual = width - getPaddingLeft() - getPaddingRight();
        height_ruler_actual = (int) (width_ruler_actual / proportion_ruler);

        height_cursor_actual = (int) (height_ruler_actual / proportion_rulerH_cursorH);
        width_cursor_actual = (int) (height_cursor_actual * proportion_cursor);

        setMeasuredDimension(width, (int) (height_cursor_actual + textHeight(textsize) + getPaddingTop() + getPaddingBottom()));

        height = getMeasuredHeight();

        calculateRect();
        calcuCursorPositons();
        //初始状态的 游标bean
        cursorPosBean_curr = cursorPosBeans.get(initiallevel - 1);
        //初始状态的 游标text
        cursorText_curr = calcuCursorTextBean(initialtext, textsize);
    }

    //用线程控制这个方法，实现动画
    private CursorTextBean calcuCursorTextBean(String text, float textsize) {
        CursorTextBean cursorTextBean = new CursorTextBean();

        DecimalFormat decimalFormat;

        if(decimal_type== CursorText.DECIMAL_ONE){
            decimalFormat = new DecimalFormat("#0.0");
            text = decimalFormat.format(Float.parseFloat(text));
        }
        if(decimal_type== CursorText.DECIMAL_TWO){
            decimalFormat = new DecimalFormat("#0.00");
            text = decimalFormat.format(Float.parseFloat(text));
        }

        //Log.i("info","==============="+text);

        float textwidth = textWidth(text + unit, textsize);

        float x = cursorPosBean_curr.getL() + width_cursor_actual / 2 - textwidth / 2;
        float y = cursorPosBean_curr.getT();

        cursorTextBean.setX(x);
        cursorTextBean.setY(y);
        cursorTextBean.setText(text);
        cursorTextBean.setUnit(unit);

        return cursorTextBean;
    }

    private void calcuCursorPositons() {
        for (int i = 0; i < levelsize; i++) {
            int l = (int) (getPaddingLeft() + ((float) width_ruler_actual / levelsize / 2) * (2 * i + 1) - width_cursor_actual / 2);
            cursorPosBeans.add(calcuCursorPositonByL(l));
        }
    }

    private CursorPosBean calcuCursorPositonByL(int l) {
        CursorPosBean cursorPosBean = new CursorPosBean();
        int t = height - getPaddingBottom() - height_cursor_actual;
        int r = l + width_cursor_actual;
        int b = height - getPaddingBottom();
        cursorPosBean.setL(l);
        cursorPosBean.setT(t);
        cursorPosBean.setR(r);
        cursorPosBean.setB(b);
        cursorPosBean.setRect(new Rect(l, t, r, b));
        return cursorPosBean;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        //Log.i("info", "===========================onLayout");
    }

    private void calculateRect() {
        int l = getPaddingLeft();
        int t = height - (height_cursor_actual - height_ruler_actual) / 2 - height_ruler_actual - getPaddingBottom();
        int r = width - getPaddingRight();
        int b = height - (height_cursor_actual - height_ruler_actual) / 2 - getPaddingBottom();
        rect_ruler_actual = new Rect(l, t, r, b);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Log.i("info", "===========================onDraw");
        drawRuler(canvas);
        drawCursor(canvas);
        drawCursorText(canvas);
    }

    private void drawRuler(Canvas canvas) {
        canvas.drawBitmap(bp_ruler, rect_ruler_original, rect_ruler_actual, new Paint());
    }

    private void drawCursor(Canvas canvas) {

        canvas.drawBitmap(bp_cursor, rect_cursor_original, cursorPosBean_curr.getRect(), new Paint());
    }

    private void drawCursorText(Canvas canvas) {
        Paint p = new Paint();
        p.setTextSize(textsize);
        p.setAntiAlias(true);
        p.setColor(text_color);
           //Log.i("info","================"+cursorText_curr.getText());
            canvas.drawText(cursorText_curr.getText() + cursorText_curr.getUnit(), cursorText_curr.getX(), cursorText_curr.getY(), p);

    }


    //游标
    public void setLevel(int level) {
        Log.i("info","===========================setLevel");
        if (level < 1 || level > levelsize) {
            return;
        }
        if (level == curr_level) {
            return;
        }

        if (isCursorAniming) {
            return;
        }

        isCursorAniming = true;
        new CursorAnimThread(level).start();


    }

    private boolean isCursorAniming = false;



    //游标线程
    private class CursorAnimThread extends Thread {

        int tolevel;

        public CursorAnimThread(int tolevel) {
            this.tolevel = tolevel;
        }

        @Override
        public void run() {
            int curr_l = cursorPosBean_curr.getL();
            int curr_t = cursorPosBean_curr.getT();
            int curr_r = cursorPosBean_curr.getR();
            int curr_b = cursorPosBean_curr.getB();

            int to_l = cursorPosBeans.get(tolevel - 1).getL();

            while (true) {
                Message message = new Message();
                CursorPosBean cursorPosBean = new CursorPosBean();

                if (curr_l == to_l) {
                    message.obj = cursorPosBeans.get(tolevel - 1);
                    break;
                } else if (curr_l < to_l) {
                    //+
                    curr_l++;

                    curr_r++;


                } else {
                    //-
                    curr_l--;

                    curr_r--;


                }

                cursorPosBean.setL(curr_l);
                cursorPosBean.setT(curr_t);
                cursorPosBean.setR(curr_r);
                cursorPosBean.setB(curr_b);
                cursorPosBean.setRect(new Rect(curr_l, curr_t, curr_r, curr_b));
                message.obj = cursorPosBean;
                cursorHandler.sendMessage(message);

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            isCursorAniming = false;

        }
    }

    private Handler cursorHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            cursorPosBean_curr = (CursorPosBean) msg.obj;
            cursorText_curr = calcuCursorTextBean(cursorText_curr.getText(), textsize);
            invalidate();
        }
    };

    //设置文本动画

    public void setText(String text, String unit,int type,boolean isperformanim) {
        if (isTextAniming) {
            return;
        }
        if(isperformanim){
            isTextAniming = true;
        }
        this.unit = unit;
        this.decimal_type = type;
        if(isperformanim){
            new TextThread(text).start();
        }else{
            cursorText_curr.setText(text);
            invalidate();
        }

    }

    private boolean isTextAniming = false;

    //设置文本的动画线程

    private class TextThread extends Thread {

        String text;

        public TextThread(String text) {
            this.text = text;
        }

        @Override
        public void run() {

            String currtext = cursorText_curr.getText();
            if(decimal_type== CursorText.DECIMAL_ZERO){
                //int
                int currtext_i = Integer.parseInt(currtext);
                int totext_i = Integer.parseInt(text);



                while (true) {
                    Message message = new Message();
                    if (currtext_i == totext_i) {
                        message.obj = totext_i + "";

                        break;
                    } else if (currtext_i < totext_i) {
                        //+
                        currtext_i++;
                        message.obj = currtext_i + "";

                    } else {
                        //-
                        currtext_i--;
                        message.obj = currtext_i + "";
                    }
                    textHandler.sendMessage(message);


                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }else{
                //float
                float currtext_f = Float.parseFloat(currtext);
                float totext_f = Float.parseFloat(text);


                float add =0;
                if(decimal_type== CursorText.DECIMAL_ONE){
                    add = 0.1f;

                }
                if(decimal_type== CursorText.DECIMAL_TWO){
                    add = 0.01f;

                }

                while (true) {
                    Message message = new Message();
                    if (Math.abs(totext_f-currtext_f)<add) {

                        message.obj = totext_f + "";

                        textHandler.sendMessage(message);
                        break;
                    } else if (currtext_f < totext_f) {
                        //+
                        currtext_f = currtext_f+add;
                        message.obj = currtext_f + "";

                    } else {
                        //-
                        currtext_f =currtext_f-add;
                        message.obj = currtext_f + "";
                    }
                    textHandler.sendMessage(message);


                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }


            isTextAniming = false;
        }
    }

    private Handler textHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String text = (String) msg.obj;
            cursorText_curr = calcuCursorTextBean(text, textsize);
            invalidate();
        }
    };


    //==============触摸相关部分

    private GestureDetector gestureDetector;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
       //return gestureDetector.onTouchEvent(event);
       return super.onTouchEvent(event);
    }



    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
       // Log.i("info","=======================onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
       // Log.i("info","=======================onSingleTapUp");
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
       // Log.i("info","=======================onScroll");
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        //Log.i("info","=======================onLongPress");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        //Log.i("info","=======================onFling");
        return false;
    }

    /**
     * 获取文本高度
     *
     * @param textsize
     * @return
     */
    private float textHeight(float textsize) {
        Paint p = new Paint();
        p.setTextSize(textsize);

        Paint.FontMetrics fontMetrics = p.getFontMetrics();
        return fontMetrics.descent - fontMetrics.ascent;
    }

    private float textWidth(String text, float textsize) {
        Paint p = new Paint();
        p.setTextSize(textsize);
        return p.measureText(text);
    }

    /**
     * 游标bean
     */
    private class CursorPosBean {
        private int l;
        private int t;
        private int r;
        private int b;
        private Rect rect;

        public CursorPosBean() {
        }

        public int getL() {
            return l;
        }

        public void setL(int l) {
            this.l = l;
        }

        public int getT() {
            return t;
        }

        public void setT(int t) {
            this.t = t;
        }

        public int getR() {
            return r;
        }

        public void setR(int r) {
            this.r = r;
        }

        public int getB() {
            return b;
        }

        public void setB(int b) {
            this.b = b;
        }

        public Rect getRect() {
            return rect;
        }

        public void setRect(Rect rect) {
            this.rect = rect;
        }
    }


    private class CursorTextBean {
        private float x;
        private float y;
        private String text;
        private String unit;

        public CursorTextBean() {
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }
    }

    public class CursorText{
        public static final int DECIMAL_ONE = 1;
        public static final int DECIMAL_TWO = 2;
        public static final int DECIMAL_ZERO = 0;
    }

}
