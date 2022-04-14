package com.example.lxhstudy.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.lxhstudy.util.Utils;

import java.util.Random;

public class Hencoder_ColoredText_View extends AppCompatTextView {
    private static final String[] TEXT = {
            "河北省", "山西省", "辽宁省", "吉林省", "黑龙江省", "江苏省", "浙江省", "安徽省", "福建省", "江西省", "山东省",
            "河南省", "湖北省", "湖南省", "广东省", "海南省", "四川省", "贵州省", "云南省", "陕西省", "甘肃省", "青海省",
            "台湾省", "北京市", "天津市", "上海市", "重庆市"
    };
    private static final int[] COLORS = {
            Color.parseColor("#E91E63"),
            Color.parseColor("#673AB7"),
            Color.parseColor("#3F51B5"),
            Color.parseColor("#2196F3"),
            Color.parseColor("#009688"),
            Color.parseColor("#FF9800"),
            Color.parseColor("#FF5722"),
            Color.parseColor("#795548")
    };

    private static final int[] TEXT_SIZES = {
            30, 22, 28
    };
    private static final Random RANDOM = new Random();
    private static final int CORNER_RADIUS = (int) Utils.dp2px(4);
    private static final int X_PADDING = (int) Utils.dp2px(16);
    private static final int Y_PADDING = (int) Utils.dp2px(8);

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public Hencoder_ColoredText_View(@NonNull Context context) {
        super(context);
    }

    public Hencoder_ColoredText_View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        setTextColor(Color.WHITE);
        setTextSize(TEXT_SIZES[RANDOM.nextInt(3)]);
        paint.setColor(COLORS[RANDOM.nextInt(COLORS.length)]);
        setPadding(X_PADDING, Y_PADDING, X_PADDING, Y_PADDING);
    }

    public void setTextForIndex(int index) {
        setText(TEXT[index]);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRoundRect(0, 0, getWidth(), getHeight(), CORNER_RADIUS, CORNER_RADIUS, paint);
        super.onDraw(canvas);
    }

}
