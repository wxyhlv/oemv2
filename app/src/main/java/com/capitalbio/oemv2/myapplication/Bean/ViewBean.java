package com.capitalbio.oemv2.myapplication.Bean;

import android.view.View;

/**
 * @author lzq
 * @Time 2016/3/10 11:15
 */
public class ViewBean {

    private View v;

    private boolean inWindow;

    public ViewBean() {
    }

    public View getV() {
        return v;
    }

    public ViewBean setV(View v) {
        this.v = v;
       return this;
    }

    public boolean isInWindow() {
        return inWindow;
    }

    public ViewBean setInWindow(boolean inWindow) {
        this.inWindow = inWindow;
        return this;
    }
}
