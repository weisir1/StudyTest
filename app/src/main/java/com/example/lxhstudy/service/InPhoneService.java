package com.example.lxhstudy.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Binder;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.lxhstudy.interfaces.IService;

public class InPhoneService extends Service {
    private TelephonyManager manager;
    private String phoneNumbers;
    private BinderImpl binder = new BinderImpl();
    private AudioManager audio;


    public class BinderImpl extends Binder implements IService {
        @Nullable
        @Override
        public String getInterfaceDescriptor() {
            return "过滤电话:" + phoneNumbers + "设置成功!";
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        phoneNumbers = intent.getStringExtra("phoneNumber");
        manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        manager.listen(new PhoneStateListenerImpl(), PhoneStateListener.LISTEN_CALL_STATE);   //设置监听操作
        audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        Log.i("WeiSir", "onBind: ");
        return binder;

    }


    private class PhoneStateListenerImpl extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String phoneNumber) {
            super.onCallStateChanged(state, phoneNumber);
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:      //挂断电话
                    audio.setRingerMode(AudioManager.MODE_NORMAL);
                    break;
                case TelephonyManager.CALL_STATE_RINGING:    //响铃
                    if (phoneNumbers.equals(phoneNumber)) {
                        audio.setRingerMode(AudioManager.RINGER_MODE_SILENT);    //静音
                    }
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:     //接听电话
                    break;
            }
        }
    }

 /*   private ITelephony getITelephony() {
        ITelephony iTelephony = null;
        return iTelephony;
    }*/

}
