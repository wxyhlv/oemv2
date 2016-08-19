package com.capitalbio.oemv2.myapplication.Devices.Bracelete;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.capitalbio.oemv2.myapplication.Base.MyApplication;
import com.capitalbio.oemv2.myapplication.BraceleteLib.BraceleteDevices;
import com.capitalbio.oemv2.myapplication.Devices.Bracelete.BraceleteCommand.SetCallNameBraceleteCommand;
import com.capitalbio.oemv2.myapplication.Devices.Bracelete.BraceleteCommand.SetSMSNumBraceleteCommand;
import com.capitalbio.oemv2.myapplication.Utils.PreferencesUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wxy on 16-3-11.
 */

public class SmsReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        Log.e("sms", "发送人.............");

        SmsMessage msg = null;
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Object[] pdusObj = (Object[]) bundle.get("pdus");
            for (Object p : pdusObj) {
                msg= SmsMessage.createFromPdu((byte[]) p);

                String msgTxt =msg.getMessageBody();//得到消息的内容

                Date date = new Date(msg.getTimestampMillis());//时间
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String receiveTime = format.format(date);

                String senderNumber = msg.getOriginatingAddress();
             /*   boolean phoneWarn = PreferencesUtils.getBoolean(MyApplication.getInstance(), "msgWarn", false);
                if(phoneWarn){
                        BraceleteDevices.getInstance().addCommandToQueue(new SetSMSNumBraceleteCommand(MyApplication.getInstance().getApplicationContext(), senderNumber));
                }*/

                if (msgTxt.equals("Testing!")) {
                    Toast.makeText(context, "success!", Toast.LENGTH_LONG)
                            .show();
                    Log.e("sms","ok");
                    return;
                } else {
                    Toast.makeText(context, senderNumber, Toast.LENGTH_LONG).show();
                    Log.e("sms", "发送人：" + senderNumber + "  短信内容：" + msgTxt + "接受时间：" + receiveTime);
                    return;
                }
            }
            return;
        }
    }
}