package com.capitalbio.oemv2.myapplication.Base;

import com.capitalbio.oemv2.myapplication.Bean.BloodPressureBean;

/**
 * Created by chengkun on 16-2-26.
 */
public interface IBloodPressDevicesCallBack {
    public void pressureDataCallBack(BloodPressureBean bloodPressureBean);
    public void resultDataCallBack(BloodPressureBean bloodPressureBean);
}
