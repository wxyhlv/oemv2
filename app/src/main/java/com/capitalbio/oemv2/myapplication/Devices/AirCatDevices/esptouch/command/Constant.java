package com.capitalbio.oemv2.myapplication.Devices.AirCatDevices.esptouch.command;

public class Constant {
    public static final String AIRCAT_SEARCH_MAC_COMMAND = "search_mac";
    
    //设置AP命令的监听端口和发送端口
    public static final int AIRCAT_SET_AP_COMMAND = 0;
    public static final int AIRCAT_SET_AP_COMMAND_LISTEN_PORT = 18266;
    public static final int AIRCAT_SET_AP_COMMAND_TARGET_PORT = 7001;
    
    //搜索空气猫的命令id以及监听端口和发送端口
    public static final int AIRCAT_SEARCH_COMMAND = 1;
    public static final int AIRCAT_SEARCH_COMMAND_LISTEN_PORT = 34353;
    public static final int AIRCAT_SEARCH_COMMAND_TARGET_PORT = 23242;
}
