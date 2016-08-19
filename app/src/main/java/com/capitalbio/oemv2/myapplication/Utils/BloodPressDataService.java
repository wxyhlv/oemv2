
package com.capitalbio.oemv2.myapplication.Utils;

import android.util.Log;

import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.Bean.BloodPressureBean;

import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wxy on 16-3-19.
 */

public class BloodPressDataService {


    //删除某条记录从数据库中

    public static void deleteFromDB(int id){

        try {
            MyApplication.getDB().deleteById(BloodPressureBean.class,id);
        } catch (DbException e) {
            e.printStackTrace();
        }

    }

}

