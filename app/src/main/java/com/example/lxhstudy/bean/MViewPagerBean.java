package com.example.lxhstudy.bean;

import androidx.fragment.app.Fragment;

import com.example.lxhstudy.view.Hencoder_Paint_view;

import java.util.List;

public class MViewPagerBean {
    private String title;
    private Fragment fragment;

    public MViewPagerBean(String title, Fragment fragment) {
        this.title = title;
        this.fragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
