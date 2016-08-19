package com.capitalbio.oemv2.myapplication.FirstPeriod.AircatProtocolParse;

public class Utils {

    //字节码转int工具方法
    public static int changeByteArrayToInt(byte[] source) {
        int andValue = 0x00FF;
        int destination = 0;
        for (int i = 0; i < source.length; i++) {
            destination = destination + ((source[i] & andValue) << (8 * (source.length - i - 1)));
        }
        return destination;
    }
    
}
