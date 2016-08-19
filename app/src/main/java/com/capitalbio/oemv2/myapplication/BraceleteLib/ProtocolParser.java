//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.capitalbio.oemv2.myapplication.BraceleteLib;


import com.capitalbio.oemv2.myapplication.Bean.SportDataTotalBean;

import java.text.SimpleDateFormat;
import java.util.Arrays;

public class ProtocolParser {
    public static final String VERSION = "1.0";
    private static final String TAG = "ProtocolParser";
    private static Pedometer_TypeInfo.Pedometer_Type pedometer_typeInfo;
    private static String hexStr;
    private static String[] binaryArray;

    static {
        pedometer_typeInfo = Pedometer_TypeInfo.Pedometer_Type.L11;
        hexStr = "0123456789ABCDEF";
        binaryArray = new String[]{"0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111", "1000", "1001", "1010", "1011", "1100", "1101", "1110", "1111"};
    }

    public ProtocolParser() {
    }

    public static void setPedometer_typeInfo(Pedometer_TypeInfo.Pedometer_Type pedometer_typeInfo) {
    }

    public static String timeStamp2format(long time_stamp) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String reTime = df.format(Long.valueOf(time_stamp * 1000L));
        return reTime;
    }

    public static byte[] byteArrayReverse(byte[] bs) {
        for(int i = 0; i < bs.length / 2; ++i) {
            byte temp = bs[i];
            bs[i] = bs[bs.length - 1 - i];
            bs[bs.length - 1 - i] = temp;
        }

        return bs;
    }

    public static int byteToInt(byte[] b) {
        short mask = 255;
        boolean temp = false;
        int n = 0;

        for(int i = 0; i < b.length; ++i) {
            n <<= 8;
            int var5 = b[i] & mask;
            n |= var5;
        }

        return n;
    }

    public static int byteReverseToInt(byte[] b) {
        short mask = 255;
        boolean temp = false;
        int n = 0;

        for(int i = b.length - 1; i > -1; --i) {
            n <<= 8;
            int var5 = b[i] & mask;
            n |= var5;
        }

        return n;
    }

    public static byte[] intToByteArray(int i) {
        byte[] result = new byte[]{(byte)(i >> 24 & 255), (byte)(i >> 16 & 255), (byte)(i >> 8 & 255), (byte)(i & 255)};
        return result;
    }

    public static String bytes2HexString(byte[] b) {
        String ret = "";
        if(b == null) {
            return ret;
        } else {
            for(int i = 0; i < b.length; ++i) {
                String hex = Integer.toHexString(b[i] & 255);
                if(hex.length() == 1) {
                    hex = '0' + hex;
                }

                ret = ret + hex.toUpperCase();
            }

            return ret;
        }
    }

    public static String binaryString2hexString(String bString) {
        if(bString != null && !bString.equals("") && bString.length() % 8 == 0) {
            StringBuffer tmp = new StringBuffer();
            boolean iTmp = false;

            for(int i = 0; i < bString.length(); i += 4) {
                int var5 = 0;

                for(int j = 0; j < 4; ++j) {
                    var5 += Integer.parseInt(bString.substring(i + j, i + j + 1)) << 4 - j - 1;
                }

                tmp.append(Integer.toHexString(var5).toUpperCase());
            }

            return tmp.toString();
        } else {
            return null;
        }
    }

    public static String hexString2binaryString(String hexString) {
        if(hexString != null && hexString.length() % 2 == 0) {
            String bString = "";

            for(int i = 0; i < hexString.length(); ++i) {
                String tmp = "0000" + Integer.toBinaryString(Integer.parseInt(hexString.substring(i, i + 1), 16));
                bString = bString + tmp.substring(tmp.length() - 4);
            }

            return bString;
        } else {
            return null;
        }
    }

    public static byte[] binaryStr2Bytes(String binaryByteString) {
        String[] binaryStr = binaryByteString.split(",");
        byte[] byteArray = new byte[binaryStr.length];

        for(int i = 0; i < byteArray.length; ++i) {
            byteArray[i] = (byte)parse(binaryStr[i]);
        }

        return byteArray;
    }

    public static int parse(String str) {
        if(32 == str.length()) {
            str = "-" + str.substring(1);
            return -(Integer.parseInt(str, 2) + 2147483647 + 1);
        } else {
            return Integer.parseInt(str, 2);
        }
    }

    public static String bytes2BinaryStr(byte[] bArray) {
        String outStr = "";
        boolean pos = false;
        byte[] var6 = bArray;
        int var5 = bArray.length;

        for(int var4 = 0; var4 < var5; ++var4) {
            byte b = var6[var4];
            int var7 = (b & 240) >> 4;
            outStr = outStr + binaryArray[var7];
            var7 = b & 15;
            outStr = outStr + binaryArray[var7];
        }

        return outStr;
    }

    public static String binaryToHexString(byte[] bytes) {
        String result = "";
        String hex = "";

        for(int i = 0; i < bytes.length; ++i) {
            hex = String.valueOf(hexStr.charAt((bytes[i] & 240) >> 4));
            hex = hex + String.valueOf(hexStr.charAt(bytes[i] & 15));
            result = result + hex + " ";
        }

        return result;
    }

    public static byte[] hexStringToBinary(String hexString) {
        int len = hexString.length() / 2;
        byte[] bytes = new byte[len];
        boolean high = false;
        boolean low = false;

        for(int i = 0; i < len; ++i) {
            byte var6 = (byte)(hexStr.indexOf(hexString.charAt(2 * i)) << 4);
            byte var7 = (byte)hexStr.indexOf(hexString.charAt(2 * i + 1));
            bytes[i] = (byte)(var6 | var7);
        }

        return bytes;
    }

    public static SportsData parseSportTotalData(byte[] bytes) {
        if(bytes.length != 20) {
            return null;
        } else {
            SportsData mData = new SportsData();
            mData.steps = byteReverseToInt(new byte[]{bytes[11], bytes[12], bytes[13], bytes[14]});
            mData.cal = byteReverseToInt(new byte[]{bytes[7], bytes[8], bytes[9], bytes[10]});
            return mData;
        }
    }

    public static SportsData parseSportDetailData(byte[] bytes) {
        SportsData mData = new SportsData();
        if(bytes.length == 21) {
            mData.time_stamp = (long)byteReverseToInt(new byte[]{bytes[4], bytes[5], bytes[6], bytes[7]});
            mData.steps = byteReverseToInt(new byte[]{bytes[8], bytes[9], bytes[10], bytes[11]});
            mData.cal = byteReverseToInt(new byte[]{bytes[16], bytes[17], bytes[18], bytes[19]});
            return mData;
        } else if(bytes.length == 19) {
            mData.time_stamp = (long)byteReverseToInt(new byte[]{bytes[4], bytes[5], bytes[6], bytes[7]});
            mData.steps = byteReverseToInt(new byte[]{bytes[8], bytes[9], bytes[10], bytes[11]});
            mData.cal = byteReverseToInt(new byte[]{bytes[12], bytes[13], bytes[14], bytes[15]});
            return mData;
        } else {
            return null;
        }
    }

    public static String parseDeviceDN(byte[] bytes) {
        String Sn = "";
        if(bytes.length == 24 && bytes[0] == 110 && bytes[2] == 4 && bytes[23] == -113) {
            try {
                Sn = new String(Arrays.copyOfRange(bytes, 3, 23), "US-ASCII");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Sn;
    }


    public static SportDataTotalBean parseSportTotalDataBytes(byte[] bytes) {
        if(bytes.length != 20) {
            return null;
        } else {
            SportDataTotalBean mData = new SportDataTotalBean();
            mData.setSteps(byteReverseToInt(new byte[]{bytes[11], bytes[12], bytes[13], bytes[14]}));
            mData.setCal(byteReverseToInt(new byte[]{bytes[7], bytes[8], bytes[9], bytes[10]}));
            return mData;
        }
    }
}

