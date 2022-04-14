package com.example.lxhstudy.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.graphics.drawable.ShapeDrawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.lxhstudy.R;

public class Hencoder_Paint_view extends View {

    private Paint paint1;
    private Paint paint;
    private Shader shader;
    private final RadialGradient radialGradient;
    private final ComposeShader composeShader;
    private final Bitmap bfbg;
    private final Bitmap bf;
    private Xfermode xfermode = null;

    public Hencoder_Paint_view(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        shader = new LinearGradient(100, 100, 600, 600
                , Color.parseColor("#C3347E")
                , Color.parseColor("#487DD7")
                , Shader.TileMode.CLAMP);
        radialGradient = new RadialGradient(650, 200, 200
                , Color.parseColor("#C3347E")
                , Color.parseColor("#487DD7")
                , Shader.TileMode.MIRROR);
        bf = BitmapFactory.decodeResource(getResources(), R.drawable.bf);
        bfbg = BitmapFactory.decodeResource(getResources(), R.drawable.bfbg);
        paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        BitmapShader bfShader = new BitmapShader(bf, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        BitmapShader bfbgShader = new BitmapShader(bfbg, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        composeShader = new ComposeShader(bfbgShader, bfShader, PorterDuff.Mode.DST_IN);
        xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setShader(shader);
        canvas.drawCircle(200, 200, 200, paint);
        paint.setShader(radialGradient);
        canvas.drawCircle(650, 200, 200, paint);

        canvas.translate(0, 400);
        paint1.setShader(composeShader);
        canvas.drawRect(0, 0, bfbg.getWidth(), bfbg.getHeight(), paint1);

        canvas.translate(0, 700);
        paint1.setShader(null);

        int save = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(bfbg, 0, 0, paint1);
        paint1.setXfermode(xfermode);
        canvas.drawBitmap(bf, 0, 0, paint1);
        paint1.setXfermode(null);
        canvas.restoreToCount(save);
    }
}
