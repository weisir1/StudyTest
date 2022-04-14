package com.example.lxhstudy.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.lxhstudy.view.HencoderDraw;
import com.example.lxhstudy.view.Hencoder_Paint_view;

public class PaintDrawFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return new Hencoder_Paint_view(getContext(),null);
    }
}