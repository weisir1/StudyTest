package com.example.lxhstudy.view;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Interpolator;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.KeyFrames;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import com.example.lxhstudy.R;
import com.example.lxhstudy.util.Utils;

import java.util.concurrent.atomic.AtomicInteger;

public class PropertyAntView extends ImageView implements View.OnClickListener {
    private Bitmap bitmap1;
    private Bitmap bitmap;
    private float progress;
    private Paint paint;
    private int left;
    private int top;
    private int right;
    private int bottom;
    private Paint paint1;
    private int color;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        invalidate();
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

    public PropertyAntView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        bitmap = Utils.zoomImg(bitmap, 0.5f, 0.5f);
        setImageBitmap(bitmap);
        setScaleType(ScaleType.CENTER);
        setOnClickListener(this);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);
        paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint1.setStyle(Paint.Style.FILL);

//        =====================================interpolator使用=============================================================
    }

    private boolean isFirst = true;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        left = 350 - 200;
        top = 600 - 50;
        right = 350 + 200;
        bottom = 600 + 50;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isFirst) {
            canvas.drawRect(left, top, right, bottom, paint);
            isFirst = false;
        } else {
            paint1.setColor(color);
            canvas.drawRect(left, top, right, bottom, paint);
            canvas.drawRect(left, top, progress * 400 + left, bottom, paint1);
        }
    }

    @Override
    public void onClick(View v) {
//        animate().translationX(100).rotation(360*4).setDuration(3000);
        //在0%处开始
        Keyframe keyframe = Keyframe.ofFloat(0, 0);
        Keyframe keyframe1 = Keyframe.ofFloat(0.5f, 1);
        Keyframe keyframe2 = Keyframe.ofFloat(0.8f, 0.5f);
        Keyframe keyframe3 = Keyframe.ofFloat(1, 1);
        PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofKeyframe("progress", keyframe, keyframe1, keyframe2, keyframe3);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(v,propertyValuesHolder);
        objectAnimator.setInterpolator(new FastOutSlowInInterpolator());



        ObjectAnimator colorAnimator = ObjectAnimator.ofInt(v, "color", 0xffff0000, 0xff00ff00);

        colorAnimator.setInterpolator(new DecelerateInterpolator());
        colorAnimator.setEvaluator(new HsvEvaluator());
//        colorAnimator.setEvaluator(new ArgbEvaluator());
        AnimatorSet set = new AnimatorSet();
        set.playTogether(objectAnimator, colorAnimator);
        set.setDuration(5000);
        set.start();
    }

    private class HsvEvaluator implements TypeEvaluator<Integer> {
        float[] startHsv = new float[3];
        float[] endHsv = new float[3];
        float[] outHsv = new float[3];

        @Override
        public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
            //将ARGB转换成HSV
            Color.colorToHSV(startValue, startHsv);
            Color.colorToHSV(endValue, endHsv);

            if (endHsv[0] - startHsv[0] > 180) {
                endHsv[0] -= 360;
            } else if (endHsv[0] - startHsv[0] < -180) {
                endHsv[0] += 360;
            }
            outHsv[0] = startHsv[0] + (endHsv[0] - startHsv[0]) * fraction;
            if (outHsv[0] > 360) {
                outHsv[0] -= 360;
            } else if (outHsv[0] < 0) {
                outHsv[0] += 360;
            }

            outHsv[1] = startHsv[1] + (endHsv[1] - startHsv[1]) * fraction;
            outHsv[2] = startHsv[2] + (endHsv[2] - startHsv[2]) * fraction;

            int alpha = startValue >> 24 + (int) ((endValue >> 24 - startValue >> 24) * fraction);
            return Color.HSVToColor(alpha, outHsv);
        }
    }
}
