package com.example.lxhstudy.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.example.lxhstudy.R;

public class Hencoder_DrawOrder extends ImageView {
    private Paint paint;
    private Bitmap bitmap;

    public Hencoder_DrawOrder(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.phonebg);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //background之后ondraw()之前的绘制操作
        super.onDraw(canvas);
        setImageResource(R.drawable.phonebg);
        setScaleType(ScaleType.FIT_XY);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        paint.setColor(Color.RED);
        paint.setTextSize(50);
        paint.setFakeBoldText(true);
        canvas.drawText("图片信息"+bitmap.getWidth()+"x"+bitmap.getHeight(),10,100,paint);
        super.onDrawForeground(canvas);
        paint.setColor(Color.parseColor("#8A151414"));
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
    }
}
