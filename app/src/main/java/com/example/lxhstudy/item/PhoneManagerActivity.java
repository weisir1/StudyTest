package com.example.lxhstudy.item;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lxhstudy.R;
import com.example.lxhstudy.receiver.PhoneBroadcastReceiver;
import com.example.lxhstudy.receiver.SMSDeliveredBroadcastReceiver;
import com.example.lxhstudy.receiver.SMSSendBroadcastReceiver;
import com.example.lxhstudy.util.RequestPermission;

import java.util.ArrayList;
import java.util.Iterator;

public class PhoneManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_manager);
        RequestPermission.requestPms(this);
    }
    private void init() {
        registerReceiver();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == RequestPermission.REQUESTCODE) {
            init();
        }
    }
    private void registerReceiver() {
        PhoneBroadcastReceiver receiver = new PhoneBroadcastReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_BOOT_COMPLETED);
        filter.addAction("android.intent.action.PHONE_STATE");
        filter.addAction(Intent.ACTION_NEW_OUTGOING_CALL);
        registerReceiver(receiver, filter);
    }


}