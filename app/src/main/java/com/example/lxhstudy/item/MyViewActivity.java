package com.example.lxhstudy.item;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.lxhstudy.R;
import com.example.lxhstudy.adapter.MVPagerAdapter;
import com.example.lxhstudy.bean.MViewPagerBean;
import com.example.lxhstudy.fragment.DrawViewFragment;
import com.example.lxhstudy.fragment.MDrawBaseFrg;
import com.example.lxhstudy.fragment.MDrawFragment;
import com.example.lxhstudy.fragment.PaintDrawFragment;
import com.example.lxhstudy.view.HencoderDraw;
import com.example.lxhstudy.view.Hencoder_Camera_View;
import com.example.lxhstudy.view.Hencoder_ClockView;
import com.example.lxhstudy.view.Hencoder_DrawOrder;
import com.example.lxhstudy.view.Hencoder_MaterialEdiText;
import com.example.lxhstudy.view.Hencoder_Paint_view;
import com.example.lxhstudy.view.Hencoder_TagLayout;
import com.example.lxhstudy.view.Hencoder_canvas_view;
import com.example.lxhstudy.view.PropertyAntView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MyViewActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private List<MViewPagerBean> list = new ArrayList<>();
    private ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_view);
        list.add(new MViewPagerBean("基础绘制",getFrgInstance(new HencoderDraw(this,null))));
        list.add(new MViewPagerBean("paint绘制",getFrgInstance(new Hencoder_Paint_view(this,null))));
        list.add(new MViewPagerBean("canvas绘制",getFrgInstance(new Hencoder_canvas_view(this,null))));
        list.add(new MViewPagerBean("绘制顺序",getFrgInstance(new Hencoder_DrawOrder(this,null))));
        list.add(new MViewPagerBean("属性动画",getFrgInstance(new PropertyAntView(this,null))));
        list.add(new MViewPagerBean("时钟绘制",getFrgInstance(new Hencoder_ClockView(this,null))));
        list.add(new MViewPagerBean("Camara动画",getFrgInstance(new Hencoder_Camera_View(this,null))));
        list.add(new MViewPagerBean("TagLayout动态宽高",getFrgInstance(new Hencoder_TagLayout(this,null))));
        initView();
    }
    private void initView() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout.addTab(tabLayout.newTab().setText("你好"));
        tabLayout.addTab(tabLayout.newTab().setText("你好"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        MVPagerAdapter adapter = new MVPagerAdapter(getSupportFragmentManager(), list);
        viewpager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewpager);

    }
    private Fragment getFrgInstance(View view){
        return new MDrawFragment(view);
    }

}