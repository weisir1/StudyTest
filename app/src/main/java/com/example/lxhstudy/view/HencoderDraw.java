package com.example.lxhstudy.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class HencoderDraw extends View {

    public HencoderDraw(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        canvas.drawRect(0, 0, getWidth(), 300, paint);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawCircle(200, 550, 100, paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20);
        paint.setColor(Color.BLACK);
        canvas.drawCircle(600, 550, 100, paint);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(200, 800, 100, paint);
        paint.setStrokeWidth(50);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(600, 800, 100, paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#962DAD"));
        canvas.drawArc(100, 1100, 600, 1600, 0, 30, true, paint);
        paint.setColor(Color.parseColor("#999CA3"));
        canvas.drawArc(100, 1100, 600, 1600, 35, 25, true, paint);
        paint.setColor(Color.parseColor("#029688"));
        canvas.drawArc(100, 1100, 600, 1600, 65, 60, true, paint);
        paint.setColor(Color.parseColor("#2196F3"));
        canvas.drawArc(100, 1100, 600, 1600, 125, 70, true, paint);
        paint.setColor(Color.parseColor("#F44236"));
        canvas.drawArc(100 - 20, 1100 - 20, 600 - 20, 1600 - 20, 200, 120, true, paint);
        paint.setColor(Color.parseColor("#FCC007"));
        canvas.drawArc(100, 1100, 600, 1600, 320, 40, true, paint);
    }
}
