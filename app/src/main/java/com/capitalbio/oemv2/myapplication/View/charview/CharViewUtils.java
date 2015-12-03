package com.capitalbio.oemv2.myapplication.View.charview;

import java.util.List;

/**
 * Created by mac on 15/11/9.
 */
public class CharViewUtils {
    private XYData xyData;
    private List<RightIno> infos;
    private List<MyPoint> points;
    private int xlength,ylength;

    //设置背景区域信息

    public void setInfos(List<RightIno> infos) {
        this.infos = infos;
    }

    public void setPoints(List<MyPoint> point) {
        this.points = point;
    }

    public void setXyData(XYData xyData) {
        this.xyData = xyData;
    }

    //设置点的信息

    //设置x y轴信息
    public void setXLength(int xlength){
        this.xlength = xlength;
    }
    public  void setYlength(int ylength){

        this.ylength = ylength;
    }


}
