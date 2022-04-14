package com.example.lxhstudy.adapter;

import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.lxhstudy.bean.MViewPagerBean;

import java.util.List;

public class MVPagerAdapter extends FragmentStatePagerAdapter {
    FragmentManager fm;
    List<MViewPagerBean> list;

    public MVPagerAdapter(@NonNull FragmentManager fm, List<MViewPagerBean> list) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.fm = fm;
        this.list = list;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return list.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).getTitle();
    }
}
