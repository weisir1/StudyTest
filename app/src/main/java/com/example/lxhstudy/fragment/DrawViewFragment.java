package com.example.lxhstudy.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lxhstudy.R;
import com.example.lxhstudy.view.HencoderDraw;

public class DrawViewFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return new HencoderDraw(getContext(),null);
    }
}