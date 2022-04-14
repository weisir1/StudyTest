package com.example.lxhstudy.fragment;

import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lxhstudy.util.Utils;
import com.example.lxhstudy.view.Hencoder_Camera_View;

public abstract class MDrawBaseFrg extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflateView();
        if (view.getClass() == Hencoder_Camera_View.class) {
            Hencoder_Camera_View camera_view = (Hencoder_Camera_View) view;
            ObjectAnimator bottomFlip = ObjectAnimator.ofFloat(camera_view, "bottomFlip", 45);
            bottomFlip.setDuration(1500);

            ObjectAnimator flipRotationAnimation = ObjectAnimator.ofFloat(camera_view, "flipRotation", 270);
            flipRotationAnimation.setDuration(1500);

            ObjectAnimator topFlipAnimation = ObjectAnimator.ofFloat(camera_view, "topFlip", -45);
            topFlipAnimation.setDuration(1500);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playSequentially(bottomFlip,flipRotationAnimation,topFlipAnimation);    //按顺序执行
            animatorSet.setStartDelay(1000);
            animatorSet.start();
          /*  float length = Utils.dp2px(300);
            Keyframe keyframe = Keyframe.ofFloat(0, 0*length);
            Keyframe keyframe2 = Keyframe.ofFloat(0.2f, 0.4f*length);
            Keyframe keyframe3 = Keyframe.ofFloat(0.6f, 1*length);
            Keyframe keyframe4 = Keyframe.ofFloat(0.8f, 0.8f*length);
            Keyframe keyframe5 = Keyframe.ofFloat(1, 1);
            PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofKeyframe("translationX", keyframe, keyframe2, keyframe3, keyframe4, keyframe5);
            ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(camera_view, propertyValuesHolder);
            animator.setStartDelay(1000);
            animator.setDuration(1500);
            animator.start();*/


        }
        return view;
    }

    public abstract View inflateView();
}
