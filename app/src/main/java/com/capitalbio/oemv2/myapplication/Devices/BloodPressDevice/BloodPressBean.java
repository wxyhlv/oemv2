package com.capitalbio.oemv2.myapplication.Devices.BloodPressDevice;

public class BloodPressBean {
    private int exceptionType;
    public int getExceptionType() {
        return exceptionType;
    }
    public void setExceptionType(int exceptionType) {
        this.exceptionType = exceptionType;
    }

    private int pressureH;
    private int pressureL;
    private int heartRate;
    public int getPressureH() {
        return pressureH;
    }
    public void setPressureH(int pressureH) {
        this.pressureH = pressureH;
    }
    public int getPressureL() {
        return pressureL;
    }
    public void setPressureL(int pressureL) {
        this.pressureL = pressureL;
    }
    public int getHeartRate() {
        return heartRate;
    }
    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }
    
    @Override
    public String toString() {
        return "Blood press bean high press is " + String.valueOf(pressureH)
                + " low press is " + String.valueOf(pressureL)
                + " heartRate is " + String.valueOf(heartRate)
                + " exception type is " + String.valueOf(exceptionType);
    }
}
