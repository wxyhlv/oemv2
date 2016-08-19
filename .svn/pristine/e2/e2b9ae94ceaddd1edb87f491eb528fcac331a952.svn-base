package com.capitalbio.oemv2.myapplication.FirstPeriod.AircatProtocolParse;

public class AirCatDataParse {

    private static AirCatBean airCatBean = new AirCatBean();



    public static AirCatBean dataParse(byte[] airCatData) {

        // 数据解析
        for (int j = 0; j < airCatData.length;) {
            // 加入返回list
            j = parseData(airCatData, j);
        }

        return airCatBean;
    }

    // 尽量不用递归实现,用循环和迭代实现
    private static int parseData(byte[] airCatData, int beginIndex) {
        System.out.println("开始下标为:" + beginIndex);
        // 头校验
        if ((airCatData[beginIndex] & 0xFF) != 0xaa
                || (airCatData[beginIndex + 1] & 0XFF) != 0x04) {

            return Constants.AIR_CAT_MESSAGE_PACKAGE_DATAFORMAT_ERROR;
        }

        // 每次根据新的下标值对type进行解析r
        byte[] tmpInstruction = new byte[2];
        System.arraycopy(airCatData, beginIndex + 4, tmpInstruction, 0, 2);

        int packetType = Utils.changeByteArrayToInt(tmpInstruction);

        switch (packetType) {
            case Constants.AIR_CAT_MESSAGE_SEND_ID:
                byte[] tmpValue = new byte[6];
                System.arraycopy(airCatData, beginIndex + 6, tmpValue, 0, 6);
                airCatBean.setMacAddress(tmpValue);
                beginIndex = beginIndex + 14;
                break;
            case Constants.AIR_CAT_MESSAGE_SEND_POWER:
                byte[] tmpPowValue = new byte[1];
                System.arraycopy(airCatData, beginIndex + 6, tmpPowValue, 0, 1);
                airCatBean.setPower(Utils.changeByteArrayToInt(tmpPowValue));
                beginIndex = beginIndex + 9;
                break;
            case Constants.AIR_CAT_MESSAGE_SEND_TEMP:
                if ((airCatData[beginIndex + 6] == (byte) 0xFF)) {
                    byte[] negativeTmpValue = new byte[1];
                    System.arraycopy(airCatData, beginIndex + 7, negativeTmpValue, 0, 1);
                    airCatBean.setTemperature(0 - (float) Utils.changeByteArrayToInt(negativeTmpValue) / 10);
                } else {
                    byte[] tmpValuePlus = new byte[2];
                    System.arraycopy(airCatData, beginIndex + 6, tmpValuePlus, 0, 2);
                    airCatBean.setTemperature((float) Utils.changeByteArrayToInt(tmpValuePlus) / 10);
                }
                beginIndex = beginIndex + 10;
                break;
            case Constants.AIR_CAT_MESSAGE_SEND_HUM:
                byte[] tmpHumValue = new byte[2];
                System.arraycopy(airCatData, beginIndex + 6, tmpHumValue, 0, 2);
                airCatBean.setHumidity((float) Utils.changeByteArrayToInt(tmpHumValue) / 10);
                beginIndex = beginIndex + 10;
                break;
            case Constants.AIR_CAT_MESSAGE_SEND_PM2:
                byte[] tmpPmValue = new byte[2];
                System.arraycopy(airCatData, beginIndex + 6, tmpPmValue, 0, 2);
                airCatBean.setPm25((float) Utils.changeByteArrayToInt(tmpPmValue) / 10);
                beginIndex = beginIndex + 10;
                break;
            case Constants.AIR_CAT_MESSAGE_SEND_TVOC:
                byte[] tmpTvocValue = new byte[2];
                System.arraycopy(airCatData, beginIndex + 6, tmpTvocValue, 0, 2);
                airCatBean.setTVOC((float) Utils.changeByteArrayToInt(tmpTvocValue) / 100);
                beginIndex = beginIndex + 10;
                break;
            case Constants.AIR_CAT_MESSAGE_SEND_CH2O:
                byte[] tmpChValue = new byte[2];
                System.arraycopy(airCatData, beginIndex + 6, tmpChValue, 0, 2);
                airCatBean.setFormol((float) Utils.changeByteArrayToInt(tmpChValue) / 100);
                beginIndex = beginIndex + 10;
                break;

            case Constants.AIR_CAT_MESSAGE_POLLUTION_LEVEL:
                byte[] tmpPollValue = new byte[2];
                System.arraycopy(airCatData, beginIndex + 6, tmpPollValue, 0, 2);
                airCatBean.setPollutionLevel((float) Utils.changeByteArrayToInt(tmpPollValue));
                beginIndex = beginIndex + 10;
                break;

            default:
                break;
        }

        // 返回最后指针的下标
        return beginIndex;
    }
}







