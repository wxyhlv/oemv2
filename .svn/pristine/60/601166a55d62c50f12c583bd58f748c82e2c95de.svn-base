package com.capitalbio.oemv2.myapplication.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;


import com.capitalbio.oemv2.myapplication.Bean.LightBeamBean;
import com.capitalbio.oemv2.myapplication.Bean.ActorBean;
import com.capitalbio.oemv2.myapplication.Bean.StageShadowBean;
import com.capitalbio.oemv2.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author lzq
 * @Time 2016/3/4 10:49
 */
public class StageView extends View {

    //灯光的个数
    private int sum_of_light = 3;

    //灯头的直径-像素
    private int diameter_of_light = 20;

    //设置灯光颜色
    private int color_of_light = Color.parseColor("#FFFFFF");

    //舞台人数-不要更改
    private static final int sum_of_people = 3;

    //阴影图片资源
    private Bitmap bp_shadow;

    //舞台阴影bean
    private List<StageShadowBean> stageShadowBeans;

    //光束bean
    private List<LightBeamBean> lightBeamBeans;

    //人物bean
    private List<ActorBean> peopleBeans;

    //光束的透明度
    private int Alpha_of_light = 50;

    //当前光束照射的位置-当等于0时为关灯
    private int focus = 1;

    //man-normal-light
    private Bitmap bp_man_normal_light;

    //man-thin-light
    private Bitmap bp_man_thin_light;

    //man-fat-light;
    private Bitmap bp_man_fat_light;

    //man-normal-dark
    private Bitmap bp_man_normal_dark;

    //man-thin-dark
    private Bitmap bp_man_thin_dark;

    //man-fat-dark
    private Bitmap bp_man_fat_dark;

    //woman-normal-light
    private Bitmap bp_woman_normal_light;

    //woman-thin-light
    private Bitmap bp_woman_thin_light;

    //woman-fat-light;
    private Bitmap bp_woman_fat_light;

    //woman-normal-dark
    private Bitmap bp_woman_normal_dark;

    //woman-thin-dark
    private Bitmap bp_woman_thin_dark;

    //woman-fat-dark
    private Bitmap bp_woman_fat_dark;


    //小人物的宽高比例
    private float ratio_people_width_height_small = 155f / 377f;

    //人物显示的大小
    private float ratio_people_shadow = 1f / 2f;

    //当前显示的男女？
    private boolean isMan = false;

    //ic_fat
    private Bitmap bp_fat;

    //ic_thin
    private Bitmap bp_thin;

    //ic_normal;
    private Bitmap bp_normal;


    public StageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getRes();
    }

    //获取图片资源
    private void getRes() {
        //获取阴影图片资源
        BitmapDrawable bd_shadow = (BitmapDrawable) getResources().getDrawable(R.drawable.shadow_stage);
        bp_shadow = bd_shadow.getBitmap();

        //获取小人
        getResPeople();

        BitmapDrawable bd_ic_fat = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_fat);
        bp_fat = bd_ic_fat.getBitmap();

        BitmapDrawable bd_ic_thin = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_thin);
        bp_thin = bd_ic_thin.getBitmap();

        BitmapDrawable bd_ic_normal = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_normal);
        bp_normal = bd_ic_normal.getBitmap();

    }

    //获取小人资源
    private void getResPeople() {
        BitmapDrawable bd_man_normal_light = (BitmapDrawable) getResources().getDrawable(R.drawable.man_normal_light_small);
        bp_man_normal_light = bd_man_normal_light.getBitmap();

        BitmapDrawable bd_man_thin_light = (BitmapDrawable) getResources().getDrawable(R.drawable.man_thin_light_small);
        bp_man_thin_light = bd_man_thin_light.getBitmap();

        BitmapDrawable bd_man_fat_light = (BitmapDrawable) getResources().getDrawable(R.drawable.man_fat_light_small);
        bp_man_fat_light = bd_man_fat_light.getBitmap();

        BitmapDrawable bd_man_normal_dark = (BitmapDrawable) getResources().getDrawable(R.drawable.man_normal_dark);
        bp_man_normal_dark = bd_man_normal_dark.getBitmap();

        BitmapDrawable bd_man_thin_dark = (BitmapDrawable) getResources().getDrawable(R.drawable.man_thin_dark);
        bp_man_thin_dark = bd_man_thin_dark.getBitmap();

        BitmapDrawable bd_man_fat_dark = (BitmapDrawable) getResources().getDrawable(R.drawable.man_fat_dark);
        bp_man_fat_dark = bd_man_fat_dark.getBitmap();

        BitmapDrawable bd_woman_normal_light = (BitmapDrawable) getResources().getDrawable(R.drawable.woman_normal_light_small);
        bp_woman_normal_light = bd_woman_normal_light.getBitmap();

        BitmapDrawable bd_woman_thin_light = (BitmapDrawable) getResources().getDrawable(R.drawable.woman_thin_light_small);
        bp_woman_thin_light = bd_woman_thin_light.getBitmap();

        BitmapDrawable bd_woman_fat_light = (BitmapDrawable) getResources().getDrawable(R.drawable.woman_fat_light_small);
        bp_woman_fat_light = bd_woman_fat_light.getBitmap();

        BitmapDrawable bd_woman_normal_dark = (BitmapDrawable) getResources().getDrawable(R.drawable.woman_normal_dark);
        bp_woman_normal_dark = bd_woman_normal_dark.getBitmap();

        BitmapDrawable bd_woman_thin_dark = (BitmapDrawable) getResources().getDrawable(R.drawable.woman_thin_dark);
        bp_woman_thin_dark = bd_woman_thin_dark.getBitmap();

        BitmapDrawable bd_woman_fat_dark = (BitmapDrawable) getResources().getDrawable(R.drawable.woman_fat_dark);
        bp_woman_fat_dark = bd_woman_fat_dark.getBitmap();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        calcuLightBeamBeans();
        calcuStageShadowBeans();
        calcuPeopleBeans();
    }

    //计算每个人的与显示有关的bean
    private void calcuPeopleBeans() {
        if (peopleBeans == null) {
            peopleBeans = new ArrayList<ActorBean>();
        } else {
            peopleBeans.clear();
        }
        calcuPeopleBeansDo();
    }

    private void calcuPeopleBeansDo() {

        for (int i = 1; i <= sum_of_people; i++) {
            peopleBeans.add(calcuPeopleBean(i));
        }
    }

    private ActorBean calcuPeopleBean(int num) {
        ActorBean peopleBean = new ActorBean();

        int session = getWidth() / sum_of_people;

        //计算人的宽和高
        float width_people = session * ratio_people_shadow;
        float height_people = width_people / ratio_people_width_height_small;

        int l = (int) ((num - 1) * session + session / 2 - width_people / 2);
        int t = (int) (getHeight() - calcuShadowHeight() / 2 - height_people);
        int r = (int) (l + width_people);
        int b = getHeight() - calcuShadowHeight() / 2;

        peopleBean.setL(l);
        peopleBean.setT(t);
        peopleBean.setR(r);
        peopleBean.setB(b);

        peopleBean.setIsMan(isMan);

        return peopleBean;
    }

    public void setSex(boolean isMan) {
        this.isMan = isMan;
        calcuPeopleBeans();
        invalidate();
    }

    public void setPeopleSize(float size) {
        this.ratio_people_shadow = size;
        calcuPeopleBeans();
        invalidate();
    }

    //计算舞台阴影bean
    private void calcuStageShadowBeans() {
        if (stageShadowBeans == null) {
            stageShadowBeans = new ArrayList<StageShadowBean>();
        } else {
            stageShadowBeans.clear();
        }
        calcuShadowDo();
    }

    //计算舞台阴影bean
    private void calcuShadowDo() {

        int shadowwidth = calcuShadowWidth();
        int shadowheight = calcuShadowHeight();

        for (int i = 1; i <= sum_of_people; i++) {
            StageShadowBean stageShadowBean = new StageShadowBean();

            stageShadowBean.setNum(i);
            stageShadowBean.setL((i - 1) * shadowwidth);
            stageShadowBean.setT(getHeight() - shadowheight);
            stageShadowBean.setR(i * shadowwidth);
            stageShadowBean.setB(getHeight());

            stageShadowBeans.add(stageShadowBean);
        }
    }

    //计算阴影的宽度
    private int calcuShadowWidth() {
        return getWidth() / sum_of_people;
    }

    //计算阴影的高度
    private int calcuShadowHeight() {
        return calcuShadowWidth() * bp_shadow.getHeight() / bp_shadow.getWidth();
    }

    //计算光束bean
    private void calcuLightBeamBeans() {
        if (lightBeamBeans == null) {
            lightBeamBeans = new ArrayList<LightBeamBean>();
        } else {
            lightBeamBeans.clear();
        }
        calcuLightDo();
    }

    //计算光束
    private void calcuLightDo() {
        for (int i = 1; i <= sum_of_light; i++) {

            for (int j = 1; j <= sum_of_people; j++) {

                LightBeamBean lightBeamBean = new LightBeamBean();

                lightBeamBean.setLt(calcuLightLT(i));
                lightBeamBean.setRt(calcuLightRT(i));
                lightBeamBean.setRb(calcuLightRB(j));
                lightBeamBean.setLb(calcuLightLB(j));
                lightBeamBean.setAlpha(Alpha_of_light);
                lightBeamBean.setColor(color_of_light);
                lightBeamBean.setRelationship(new int[]{i, j});

                lightBeamBeans.add(lightBeamBean);
            }

        }
    }

    /**
     * 计算光束的左上角坐标
     *
     * @param order 灯的序号
     * @return
     */
    private int[] calcuLightLT(int order) {
        int[] lt = new int[2];

        int session = getWidth() / (sum_of_light * 2);

        int l = (order - 1) * (session * 2) + session - (diameter_of_light / 2);
        int t = 0;

        lt[0] = l;
        lt[1] = t;

        return lt;
    }

    /**
     * 计算光束右上角坐标
     *
     * @param order
     * @return
     */
    private int[] calcuLightRT(int order) {
        int[] rt = new int[2];

        int session = getWidth() / (sum_of_light * 2);

        int r = (order - 1) * (session * 2) + session + (diameter_of_light / 2);
        int t = 0;

        rt[0] = r;
        rt[1] = t;

        return rt;
    }

    /**
     * 计算光束左下角坐标
     *
     * @param order 人的序号
     * @return
     */
    private int[] calcuLightLB(int order) {
        int[] lb = new int[2];

        int session = getWidth() / sum_of_people;

        int l = (order - 1) * session;
        int b = getHeight() - (calcuShadowHeight() / 2);

        lb[0] = l;
        lb[1] = b;

        return lb;
    }


    /**
     * 计算光束右下角坐标
     *
     * @param order 人的序号
     * @return
     */
    private int[] calcuLightRB(int order) {
        int[] rb = new int[2];

        int session = getWidth() / sum_of_people;

        int r = order * session;
        int b = getHeight() - (calcuShadowHeight() / 2);

        rb[0] = r;
        rb[1] = b;

        return rb;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制光束和舞台阴影
        if (focus != 0) {
            //当focus==0表示关灯
            drawLight(canvas);

            drawShadow(canvas);
        }

        //画人
        drawPeople(canvas);

        //画戳
        drawStamp(canvas);

    }

    private void drawPeople(Canvas canvas) {
        for (int i = 1; i <= sum_of_people; i++) {
            drawPeopleBySex(canvas, i, i == focus);
        }
    }


    private static final int thin = 1;
    private static final int normal = 2;
    private static final int fat = 3;

    private void drawPeopleBySex(Canvas canvas, int num, boolean isFocus) {
        if (isMan) {
            //男人
            if (isFocus) {
                switch (num) {
                    case thin:
                        drawPeopleDo(canvas, peopleBeans.get(num - 1), bp_man_thin_light);
                        break;
                    case normal:
                        drawPeopleDo(canvas, peopleBeans.get(num - 1), bp_man_normal_light);
                        break;
                    case fat:
                        drawPeopleDo(canvas, peopleBeans.get(num - 1), bp_man_fat_light);
                        break;
                }
            } else {
                switch (num) {
                    case thin:
                        drawPeopleDo(canvas, peopleBeans.get(num - 1), bp_man_thin_dark);
                        break;
                    case normal:
                        drawPeopleDo(canvas, peopleBeans.get(num - 1), bp_man_normal_dark);
                        break;
                    case fat:
                        drawPeopleDo(canvas, peopleBeans.get(num - 1), bp_man_fat_dark);
                        break;
                }
            }
        } else {
            //女人
            if (isFocus) {
                switch (num) {
                    case thin:
                        drawPeopleDo(canvas, peopleBeans.get(num - 1), bp_woman_thin_light);
                        break;
                    case normal:
                        drawPeopleDo(canvas, peopleBeans.get(num - 1), bp_woman_normal_light);
                        break;
                    case fat:
                        drawPeopleDo(canvas, peopleBeans.get(num - 1), bp_woman_fat_light);
                        break;
                }
            } else {
                switch (num) {
                    case thin:
                        drawPeopleDo(canvas, peopleBeans.get(num - 1), bp_woman_thin_dark);
                        break;
                    case normal:
                        drawPeopleDo(canvas, peopleBeans.get(num - 1), bp_woman_normal_dark);
                        break;
                    case fat:
                        drawPeopleDo(canvas, peopleBeans.get(num - 1), bp_woman_fat_dark);
                        break;
                }
            }
        }
    }

    private void drawPeopleDo(Canvas canvas, ActorBean peopleBean, Bitmap bp) {
        Paint p = new Paint();

        Rect src = new Rect();
        src.set(0, 0, bp.getWidth(), bp.getHeight());

        Rect dust = new Rect();
        dust.set(peopleBean.getL(), peopleBean.getT(), peopleBean.getR(), peopleBean.getB());

        canvas.drawBitmap(bp, src, dust, p);

        // bp.recycle();
    }


    /**
     * 绘制光束
     *
     * @param canvas
     */
    private void drawLight(Canvas canvas) {

        for (LightBeamBean lightBeamBean : lightBeamBeans) {
            int[] relationship = lightBeamBean.getRelationship();
            if (relationship[1] == focus) {
                drawLightBeam(canvas, lightBeamBean);
            }
        }

    }

    /**
     * 绘制阴影
     *
     * @param canvas
     */
    private void drawShadow(Canvas canvas) {
        drawStageShadow(canvas, stageShadowBeans.get(focus - 1));
    }


    /**
     * 设置当前照射的位置
     *
     * @param position
     */
    public void setFocus(int position) {
        this.focus = position;
        invalidate();
    }


    /**
     * 绘制光束
     *
     * @param canvas
     * @param lightBeamBean
     */
    private void drawLightBeam(Canvas canvas, LightBeamBean lightBeamBean) {

        int[] lt = lightBeamBean.getLt();
        int[] rt = lightBeamBean.getRt();
        int[] rb = lightBeamBean.getRb();
        int[] lb = lightBeamBean.getLb();

        int color = lightBeamBean.getColor();
        int alpha = lightBeamBean.getAlpha();

        Paint p = new Paint();
        p.setColor(lightBeamBean.getColor());
        p.setAlpha(alpha);
        p.setStyle(Paint.Style.FILL);


        Path path = new Path();
        path.moveTo(lt[0], lt[1]);
        path.lineTo(rt[0], rt[1]);
        path.lineTo(rb[0], rb[1]);
        path.lineTo(lb[0], lb[1]);
        path.close();

        canvas.drawPath(path, p);
    }

    /**
     * 绘制舞台阴影
     *
     * @param canvas
     * @param stageShadowBean
     */
    private void drawStageShadow(Canvas canvas, StageShadowBean stageShadowBean) {

        int l = stageShadowBean.getL();
        int t = stageShadowBean.getT();
        int r = stageShadowBean.getR();
        int b = stageShadowBean.getB();
        int num = stageShadowBean.getNum();

        Paint p = new Paint();

        Rect src = new Rect();
        src.set(0, 0, bp_shadow.getWidth(), bp_shadow.getHeight());
        Rect dust = new Rect();
        dust.set(l, t, r, b);

        canvas.drawBitmap(bp_shadow, src, dust, p);

    }


    //设置灯光数
    public void setSum_of_light(int sum) {
        if (sum < 0 || sum > 5) {
            return;
        }
        sum_of_light = sum;
        calcuLightBeamBeans();
    }

    //设置舞台人数
    /*public void setSum_of_people(int sum) {
        if (sum < 0 || sum > 5) {
            return;
        }
        sum_of_people = sum;
        calcuLightBeamBeans();
        calcuStageShadowBeans();
    }*/

    //设置灯头的直径
    public void setDiameter_of_light(int diameter) {
        if (diameter < 0) {
            return;
        }
        diameter_of_light = diameter;
        calcuLightBeamBeans();
    }

    private TimerTask timerTask;

    private Timer timer;

    private boolean isFocusing = false;

    //开始动画
    public void startFocus() {

        if (isFocusing) {
            return;
        }

        if (timerTask == null) {
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    handler_start.sendEmptyMessage(0);
                }
            };
        }
        if (timer == null) {
            timer = new Timer();
        }

        timer.schedule(timerTask, speed_of_light_show, speed_of_light_show);

        isFocusing = true;
    }

    private boolean isRightMoving = true;

    private Handler handler_start = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (isRightMoving) {
                focus++;
                if (focus > sum_of_people) {
                    isRightMoving = false;
                    focus = sum_of_people - 1;
                }
            } else {
                focus--;
                if (focus < 1) {
                    isRightMoving = true;
                    focus = 2;
                }
            }
            setFocus(focus);

        }
    };

    //停止动画
    public void stopFocus(final int num) {
        if (!isFocusing) {
            return;
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }

        isFocusing = false;


        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler_stop.sendEmptyMessage(num);
            }
        }, speed_of_light_show);


    }

    //动画速度
    private int speed_of_light_show = 200;

    private Handler handler_stop = new Handler() {
        @Override
        public void handleMessage(final Message msg) {

            final int num = msg.what;
            setFocus(0);
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    handler_polish.sendEmptyMessage(num);
                }
            }, 700);

        }
    };

    private Handler handler_polish = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            setFocus(msg.what);
            isAllowedSeal = true;
            invalidate();
        }
    };


    //设置灯光颜色
    public void setColorOfLight(int color) {
        this.color_of_light = color;
        calcuLightBeamBeans();
        invalidate();
    }


    //是否允许画戳
    private boolean isAllowedSeal = false;

    //绘制戳
    private void drawStamp(Canvas canvas) {
        if (!isAllowedSeal) {
            return;
        }
        switch (focus) {
            case thin:
                drawStampByBean(canvas,bp_thin,peopleBeans.get(0));
                break;
            case normal:
                drawStampByBean(canvas,bp_normal,peopleBeans.get(1));
                break;
            case fat:
                drawStampByBean(canvas,bp_fat,peopleBeans.get(2));
                break;
        }
        isAllowedSeal = false;
    }

    private void drawStampByBean(Canvas canvas,Bitmap bitmap,ActorBean peopleBean){
        canvas.drawBitmap(bitmap, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), new Rect(peopleBean.getL(), peopleBean.getT(), peopleBean.getR(), peopleBean.getB()), new Paint());
    }


}
