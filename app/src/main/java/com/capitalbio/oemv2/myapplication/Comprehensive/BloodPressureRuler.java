package com.capitalbio.oemv2.myapplication.Comprehensive;

/**
 * @author lzq
 * @Time 2016/3/17 10:06
 */
public class BloodPressureRuler {

    private static final int LOW =  0;
    private static final int HIGH = 1;
    private static final int NORMAL = 2;

    private static final int TYPE_HIGH = 3;
    private static final int TYPE_LOW = 4;

    private static final int[] BOUNDARY_HIGH = new int[]{90,140};
    private static final int[] BOUNDARY_LOW = new int[]{60,90};

    public static BloodPressureRulerBean ruler(int high,int low){
        BloodPressureRulerBean bloodPressureRulerBean = new BloodPressureRulerBean();

        String state = "";
        float ratio = 0f;
        float average_high = (BOUNDARY_HIGH[0]+BOUNDARY_HIGH[1])/2;
        float average_low = (BOUNDARY_LOW[0]+BOUNDARY_LOW[1])/2;

        if(high<BOUNDARY_HIGH[0]){
           //低血压
            state = "低血压";
            ratio = (average_high-high)/average_high;
        }else if(high>BOUNDARY_HIGH[1]){
           //高血压
            state = "高血压";
            ratio = (high-average_high)/average_high;
        }else{
           //判断舒张压
            if(low<BOUNDARY_LOW[0]){
                //低压
                state = "低血压";
                ratio = (average_low-low)/average_low;
            }else if(low>BOUNDARY_LOW[1]){
                //高压
                state = "高血压";
                ratio = (low-average_low)/average_low;
            }else{
                //正常
                state = "正常";
                ratio = 0;
            }
        }

        bloodPressureRulerBean.setState(state);
        bloodPressureRulerBean.setRatio(ratio);

        return bloodPressureRulerBean;
    }


    public static class BloodPressureRulerBean{
        private String state;
        private float ratio;

        public BloodPressureRulerBean() {
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public float getRatio() {
            return ratio;
        }

        public void setRatio(float ratio) {
            this.ratio = ratio;
        }
    }
}
