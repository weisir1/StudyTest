package com.example.lxhstudy.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class MDrawFragment extends MDrawBaseFrg{
    View view;

    public MDrawFragment(View view) {
        this.view = view;
    }
    @Override
    public View inflateView() {
        return view;
    }

}
