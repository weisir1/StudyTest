package com.example.lxhstudy.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

public class RequestPermission {
    public static int REQUESTCODE = 10;
    private static String[] PERMISSIONS = new String[]{
              Manifest.permission.ACCESS_NOTIFICATION_POLICY
            , Manifest.permission.READ_PHONE_STATE
            , Manifest.permission.PROCESS_OUTGOING_CALLS
            , Manifest.permission.MODIFY_PHONE_STATE
            , Manifest.permission.READ_CALL_LOG
            , Manifest.permission.SEND_SMS
            ,Manifest.permission.RECEIVE_SMS};

    public static void requestPms(Activity activity) {
        ActivityCompat.requestPermissions(activity,PERMISSIONS, REQUESTCODE);
    }
}
