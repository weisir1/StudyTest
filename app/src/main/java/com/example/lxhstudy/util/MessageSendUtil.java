package com.example.lxhstudy.util;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageSendUtil {
    private Context context;
    private Intent intent ;

    public MessageSendUtil(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
    }
    public   void send(String receiveNumber,String phoneNumber,String type){
        SmsManager smsManager = SmsManager.getDefault();
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        String content = "电话号码"+phoneNumber+"\n类型:"+type+"\n时间:"
                +new SimpleDateFormat("yyyy-MM-dd HH:m:sss").format(new Date());
        smsManager.sendTextMessage(receiveNumber,null,content,pendingIntent,null);
    }
}
