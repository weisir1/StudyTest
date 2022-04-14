package com.example.lxhstudy.item;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.lxhstudy.R;
import com.example.lxhstudy.receiver.SMSDeliveredBroadcastReceiver;
import com.example.lxhstudy.receiver.SMSSendBroadcastReceiver;

import java.util.ArrayList;
import java.util.Iterator;

public class SMSListenerActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText tel;
    private EditText content;
    private Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_m_s_listener);
    }
    private void initView() {
        tel = (EditText) findViewById(R.id.tel);
        content = (EditText) findViewById(R.id.content);
        send = (Button) findViewById(R.id.send);

        send.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send:
                Intent send = new Intent("SMS_SEND_ACTION");
                Intent delivered = new Intent("SMS_DELIVERED_ACTION");
                SmsManager smsManager = SmsManager.getDefault();
                String telephone = this.tel.getText().toString();
                String content = this.content.getText().toString();
                PendingIntent pendingintent = PendingIntent.getBroadcast(this, 20, send, 0);
                PendingIntent deliveredPendingIntent = PendingIntent.getBroadcast(this, 20, delivered, 0);
                registerReceiver(new SMSSendBroadcastReceiver(),new IntentFilter("SMS_SEND_ACTION"));
                registerReceiver(new SMSDeliveredBroadcastReceiver(),new IntentFilter("SMS_DELIVERED_ACTION"));
                if (content.length() > 70){
                    ArrayList<String> msgs = smsManager.divideMessage(content);// 拆分信息
                    Iterator<String> iterator = msgs.iterator();
                    while (iterator.hasNext()) {
                        String next = iterator.next();
                        smsManager.sendTextMessage(telephone,null,next,pendingintent,deliveredPendingIntent);
                    }
                }else {
                    smsManager.sendTextMessage(telephone,null,content,pendingintent,deliveredPendingIntent);
                }
                break;
        }
    }

}