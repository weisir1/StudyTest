package com.example.lxhstudy.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.lxhstudy.util.Utils;

import okhttp3.internal.Util;

public class Hencoder_ClockView extends View {
    private static final int ANGLE = 120;
    private static final float RADIUS = Utils.dp2px(150);
    private static final float LENGTH = Utils.dp2px(100);
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path dash = new Path();
    private PathDashPathEffect effect;
    //  弧形形状
    private RectF RFArc;
    public Hencoder_ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Utils.dp2px(2));


        RFArc = new RectF(0 - RADIUS, 0 - RADIUS, RADIUS, RADIUS);
//      计算虚线间隔长度
        Path arc = new Path();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            arc.addArc(0 - RADIUS, 0 - RADIUS, RADIUS, RADIUS, 90 + ANGLE / 2, 360 - ANGLE);
        }
        PathMeasure pathMeasure = new PathMeasure(arc, false);
        float advance = (pathMeasure.getLength() - Utils.dp2px(2)) / 20;
        dash.addRect(0, 0, Utils.dp2px(2), Utils.dp2px(10), Path.Direction.CW);
//        参数2: advance 虚线的距离
//        参数3: phase 虚线起始间隔
        effect = new PathDashPathEffect(dash, advance, 0, PathDashPathEffect.Style.ROTATE);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//       画线
        canvas.drawArc(getWidth() / 2 - RADIUS, getHeight() / 2 - RADIUS, getWidth() / 2 + RADIUS, getHeight() / 2 + RADIUS, 90 + ANGLE / 2, 360 - ANGLE, false, paint);
//        绘制刻度  绘制刻度设置在绘制线前,会导致结果只有刻度而线消失,所以要再次绘制线条
        paint.setPathEffect(effect);
////        重新绘制线条
        canvas.drawArc(getWidth() / 2 - RADIUS, getHeight() / 2 - RADIUS, getWidth() / 2 + RADIUS, getHeight() / 2 + RADIUS, 90 + ANGLE / 2, 360 - ANGLE, false, paint);
        paint.setPathEffect(null);

//        绘制指针
        canvas.drawLine(getWidth() / 2, getHeight() / 2,
                (float) Math.cos(Math.toRadians(getAngleFromMark(5))) * LENGTH + getWidth() / 2,
                (float) Math.sin(Math.toRadians(getAngleFromMark(5))) * LENGTH + getHeight() / 2
                , paint);
    }

    int getAngleFromMark(int mark) {
        return (int) (90 + (float) ANGLE / 2 + (360 - (float) ANGLE) / 20 * mark);
    }
}
