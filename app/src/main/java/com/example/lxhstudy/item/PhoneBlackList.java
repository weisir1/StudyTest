package com.example.lxhstudy.item;

import android.Manifest;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lxhstudy.R;
import com.example.lxhstudy.interfaces.IService;
import com.example.lxhstudy.service.InPhoneService;
import com.example.lxhstudy.service.InPhoneService.*;
import com.example.lxhstudy.util.RequestPermission;


public class PhoneBlackList extends AppCompatActivity implements View.OnClickListener {

    private EditText phoneNumber;
    private Button setNumber;
    private Button cancelNumber;
    private IService iService;
    private ServiceConnectionImpl serviceConnection = new ServiceConnectionImpl();
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_black_list);
        RequestPermission.requestPms(this);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && !notificationManager.isNotificationPolicyAccessGranted()) {
            startActivity(new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == RequestPermission.REQUESTCODE)
            initView();
    }

    private void initView() {
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        setNumber = (Button) findViewById(R.id.setNumber);
        cancelNumber = (Button) findViewById(R.id.cancelNumber);
        setNumber.setOnClickListener(this);
        cancelNumber.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setNumber:
                intent = new Intent(this, InPhoneService.class);
                intent.putExtra("phoneNumber", phoneNumber.getText().toString().trim());//设置的电话号码
                bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.cancelNumber:
                if (iService != null) {
                    unbindService(serviceConnection);
                    stopService(intent);
                    Toast.makeText(this, "黑名单已取消", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private class ServiceConnectionImpl implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iService = (BinderImpl) service;
            try {
                Toast.makeText(PhoneBlackList.this, service.getInterfaceDescriptor(), Toast.LENGTH_SHORT).show();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

}