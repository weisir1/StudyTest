package com.example.lxhstudy;


import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lxhstudy.bean.ItemBean;
import com.example.lxhstudy.item.Hencoder_ScaleActivity;
import com.example.lxhstudy.item.MaterialEditTextActivity;
import com.example.lxhstudy.item.MyViewActivity;
import com.example.lxhstudy.item.PhoneBlackList;
import com.example.lxhstudy.item.PhoneManagerActivity;
import com.example.lxhstudy.item.SMSListenerActivity;
import com.example.lxhstudy.item.SensorMagneticActivity;
import com.example.lxhstudy.item.SensorOrientationActivity;
import com.example.lxhstudy.adapter.MainListViewAdapter;
import com.example.lxhstudy.view.Hencoder_ScalableImageView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ListView listview;
    private List<ItemBean> list = new ArrayList<>();
    private MainListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addData();
        initView();
    }

    private void initView() {
        listview = (ListView) findViewById(R.id.listview);
        adapter = new MainListViewAdapter(list, this);
        listview.setAdapter(adapter);
    }

    private void addData() {
        list.add(new ItemBean("来去电监听", PhoneManagerActivity.class));
        list.add(new ItemBean("黑名单监听", PhoneBlackList.class));
        list.add(new ItemBean("短信来去接收", SMSListenerActivity.class));
        list.add(new ItemBean("方位传感器", SensorOrientationActivity.class));
        list.add(new ItemBean("指北针传感器", SensorMagneticActivity.class));
        list.add(new ItemBean("自定义view", MyViewActivity.class));
        list.add(new ItemBean("自定义view之EditText", MaterialEditTextActivity.class));
        list.add(new ItemBean("自定义view之图片滑动", Hencoder_ScaleActivity.class));
    }

}