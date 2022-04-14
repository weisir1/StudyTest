package com.example.lxhstudy.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.lxhstudy.service.PhoneService;

public class PhoneBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_NEW_OUTGOING_CALL.equals(intent.getAction())) {
            String outgoing = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            Intent intent1 = new Intent(context, PhoneService.class);
            intent1.putExtra("outgoingNumber", outgoing);
            context.startService(intent1);
        } else {
            context.startService(new Intent(context, PhoneService.class));
        }
    }
}
