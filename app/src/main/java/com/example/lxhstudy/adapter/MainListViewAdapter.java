package com.example.lxhstudy.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lxhstudy.R;
import com.example.lxhstudy.bean.ItemBean;

import java.util.List;

public class MainListViewAdapter extends BaseAdapter {
    private List<ItemBean> list;
    private AppCompatActivity activity;

    public MainListViewAdapter(List<ItemBean> list, AppCompatActivity activity) {
        this.list = list;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(activity).inflate(R.layout.listitemview,null);
        Button item = convertView.findViewById(R.id.item);
        item.setText(list.get(position).getName());
        item.setOnClickListener(v -> {
            Intent intent = new Intent(activity, list.get(position).getaClass());
            activity.startActivity(intent);
        });
        return convertView;
    }
}
