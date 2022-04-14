package com.example.lxhstudy.adapter;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MyViewListAdapter extends BaseAdapter {
    private AppCompatActivity activity;
    private List<String> list;

    public MyViewListAdapter(AppCompatActivity activity, List<String> list) {
        this.activity = activity;
        this.list = list;
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
        TextView textView = new TextView(activity);
        textView.setText(list.get(position));
        ListView.LayoutParams params = new ListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.MATCH_PARENT);
        textView.setLayoutParams(
                params);
        textView.setTextSize(30);
        textView.setPadding(0, 10, 0, 10);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setFocusable(false);
        textView.setFocusableInTouchMode(false);
        return textView;
    }
}
