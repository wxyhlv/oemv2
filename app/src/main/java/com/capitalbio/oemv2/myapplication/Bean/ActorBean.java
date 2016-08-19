package com.capitalbio.oemv2.myapplication.Bean;

/**
 * @author lzq
 * @Time 2016/3/7 13:20
 */
public class ActorBean {

    private int l;//绘制区域的左
    private int t;//绘制区域的上
    private int r;//绘制区域的右
    private int b;//绘制区域的下

    private boolean isMan;//是否是男人
    private boolean isLight;//是否是亮的
    private boolean isBig;//是否是大的

    private int num;//人物排列的序号

    public ActorBean() {
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

    public boolean isMan() {
        return isMan;
    }

    public void setIsMan(boolean isMan) {
        this.isMan = isMan;
    }

    public boolean isLight() {
        return isLight;
    }

    public void setIsLight(boolean isLight) {
        this.isLight = isLight;
    }

    public boolean isBig() {
        return isBig;
    }

    public void setIsBig(boolean isBig) {
        this.isBig = isBig;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
