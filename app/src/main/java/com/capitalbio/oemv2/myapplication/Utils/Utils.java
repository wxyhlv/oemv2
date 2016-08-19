package com.capitalbio.oemv2.myapplication.Utils;

import android.app.ActivityManager;
import android.content.Context;

import com.capitalbio.oemv2.myapplication.Base.MyApplication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chengkun on 15/11/20.
 */
public class Utils {

    public static boolean isMobilePhoneNumber(String phoneNumber) {

        boolean tag = true;

       // String reg = "^(13[0-9]|15[012356789]|18[02367895]|14[57])[0-9]{8}$"; // 验证手机号码
        String reg = "^13[0-9]{9}|15[012356789][0-9]{8}|18[0-9]{9}|14[579][0-9]{8}|17[0-9]{9}$"; // 验证手机号码
        final Pattern pattern = Pattern.compile(reg);
        final Matcher mat = pattern.matcher(phoneNumber);
        if (!mat.find()) {
            tag = false;
        }
        return tag;
    }

    public static boolean isContainChinese(String str) {

        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }
    //字节码转int工具方法
    public static int changeByteArrayToInt(byte[] source) {
        int andValue = 0x00FF;
        int destination = 0;
        for (int i = 0; i < source.length; i++) {
            destination = destination + ((source[i] & andValue) << (8 * (source.length - i - 1)));
        }
        return destination;
    }

    //ASCII转16进制
    public static String convertStringToHex(String str){

        char[] chars = str.toCharArray();

        StringBuffer hex = new StringBuffer();
        for(int i = 0; i < chars.length; i++){
            hex.append(Integer.toHexString((int)chars[i]));
        }

        return hex.toString();
    }

    //16进制转ASCII码
    public static String convertHexToString(String hex){

        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();

        //49204c6f7665204a617661 split into two characters 49, 20, 4c...
        for( int i=0; i<hex.length()-1; i+=2 ){

            //grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            //convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            //convert the decimal to character
            sb.append((char)decimal);

            temp.append(decimal);
        }

        return sb.toString();
    }
    /**
     * 数组转成十六进制字符串
     * @param  b
     * @return HexString
     */
    public static String toHexString1(byte[] b){
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < b.length; ++i){
            buffer.append(toHexString1(b[i]));
        }
        return buffer.toString();
    }
    public static String toHexString1(byte b){
        String s = Integer.toHexString(b & 0xFF);
        if (s.length() == 1){
            return "0" + s;
        }else{
            return s;
        }
    }

    public static long lastClickTime;
    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if ( time - lastClickTime < 500 && time > lastClickTime ) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public static boolean isOemServicesRunning() {
            ActivityManager manager = (ActivityManager) MyApplication.getInstance().getSystemService(Context.ACTIVITY_SERVICE);
            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                if ("com.capitalbio.oemv2.myapplication.Devices.Bracelete.OemBackgroundService".equals(service.service.getClassName())) {
                    return true;
                }
            }
            return  false;
    }


    public static String byteToMac(byte[] bytes) {
        String macstring = HexUtils.byte2HexStr(bytes);
        String data[] = macstring.split(" ");
        StringBuilder builder = new StringBuilder();
        for (int i = 6; i < data.length - 2; i++) {
            builder.append(data[i] + ":");
        }
        String macResult = builder.toString().substring(0, builder.toString().length() - 1);
        OemLog.log("macresult", macstring);

        return macResult;
    }

}
