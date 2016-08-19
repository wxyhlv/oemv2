package com.capitalbio.oemv2.myapplication.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.capitalbio.oemv2.myapplication.Bean.LineChartBg;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wxy on 15-12-12.
 */
public class ChartBackgroundView extends View{
    private Paint textPaint;//画文字
    private Paint bgPaint;//画背景
    private List<LineChartBg> bginfos = new ArrayList<>();
    private int height,width;
    public ChartBackgroundView(Context context,List<LineChartBg> bginfos ) {
        super(context);
        init();
        this.bginfos = bginfos;
    }


    public ChartBackgroundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        init();
    }
        @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
       if(bginfos!=null){
           height = 900;
           width = 1080;
           Log.i("height",height+"");
           for(int i=0;i<bginfos.size();i++){
               //list 升序
               LineChartBg bginfo =bginfos.get(i);
               bgPaint.setColor(Color.parseColor(bginfo.getColor()));
               canvas.drawRect(0,Math.round(height - bginfo.getMax()),width,Math.round(height-bginfo.getMin()),bgPaint);
           }
       }
    }

    public void init(){
        textPaint = new Paint();
        textPaint.setTextSize(15f);
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.BLACK);
        textPaint.setStyle(Paint.Style.STROKE);
        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setStyle(Paint.Style.FILL);
    }
    public void setBginfos(List<LineChartBg> bginfos){
          bginfos = bginfos;
          postInvalidate();
    }

}
