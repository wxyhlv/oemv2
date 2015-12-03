package com.capitalbio.oemv2.myapplication.View.charview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.capitalbio.oemv2.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class CharViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charview);
        CustomedCharViewPBM view = (CustomedCharViewPBM) findViewById(R.id.charview);
        XYData xyData =new XYData();
        xyData.setyDatas(new double[]{Double.valueOf(0), Double.valueOf(20), Double.valueOf(40),
                Double.valueOf(60), Double.valueOf(80), Double.valueOf(100), Double.valueOf(120),
                });
        xyData.setXlablecount(7);
        xyData.setYlablecount(6);
        view.setXYdata(xyData);
        List<RightIno> rightTitleInfos =new ArrayList<>();
        RightIno info1 = new RightIno();
       // info1.setRectBGcolor(getResources().getColor(R.color.bg_pmb_low));
        info1.setRectBGcolor(Color.RED);
        info1.setMin(0);
        info1.setMax(65);
        info1.setGrade("偏低");
        RightIno info2 = new RightIno();
       // info2.setRectBGcolor(getResources().getColor(R.color.bg_pmb_nomal));
        info2.setRectBGcolor(Color.BLUE);
        info2.setMin(65);
        info2.setMax(90);
        info2.setGrade("中");
        RightIno info3 = new RightIno();
        info3.setRectBGcolor(Color.YELLOW);

        //info3.setRectBGcolor(getResources().getColor(R.color.bg_pmb_high));
        info3.setMin(90);
        info3.setMax(120);
        info3.setGrade("偏高");
        rightTitleInfos.add(info1);
        rightTitleInfos.add(info2);
        rightTitleInfos.add(info3);
        view.setRigthTitle(rightTitleInfos);
        List<MyPoint> points = new ArrayList<>();
        MyPoint point1 = new MyPoint();
        point1.setY(100);
        point1.setColor(Color.YELLOW);
        point1.setBitmapid(R.drawable.ic_point_green);
        points.add(point1);
        for(int i =0;i<6;i++){
            MyPoint point = new MyPoint();
            point.setY((int) (10 + 10 * i));
            point.setColor(Color.YELLOW);
            point.setBitmapid(R.drawable.ic_point_green);
            points.add(point);
        }

        view.setPointData(points);
        view.invalidate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
