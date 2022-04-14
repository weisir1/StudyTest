package com.example.lxhstudy.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.lxhstudy.R;

public class Hencoder_canvas_view extends View {
    private final Path cPath2;
    private Paint mPaint = null;
    private Path cPath = null;
    private Bitmap bitmap;
    private Bitmap bitmap2;
    private final Camera camera;
    private float bWidth;
    private float bHeight;

    public Hencoder_canvas_view(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        cPath = new Path();
        cPath2 = new Path();
        camera = new Camera();
        cPath.addCircle(200, 200, 200, Path.Direction.CCW);
        cPath2.addCircle(700, 200, 200, Path.Direction.CW);
        cPath2.setFillType(Path.FillType.INVERSE_WINDING);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bfbg);
        bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        bWidth = bitmap2.getWidth();
        bHeight = bitmap2.getHeight();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        //----------------------绘制圆形图片切割
        canvas.save();
        canvas.clipPath(cPath);
        canvas.drawBitmap(bitmap, 0, 0, mPaint);
        canvas.restore();
//--------------------------绘制中空图片切割
        canvas.save();
        canvas.clipPath(cPath2);
        canvas.drawBitmap(bitmap, 500, -200, mPaint);
        canvas.restore();

        //--------------------旋转图片
        canvas.save();
        canvas.rotate(45, bWidth / 2, bHeight / 2);
        canvas.drawBitmap(bitmap2, 600, 400, mPaint);
        canvas.restore();


//----------------------------绘制图片下半部分
        float cWidth = bWidth / 2 + 600;
        float cHeight = bHeight / 2 + 500;
        canvas.save();
        camera.save();
        camera.setLocation(0, 0, -8);
        camera.rotateX(45);
        canvas.translate(cWidth, cHeight);
        canvas.rotate(-45);
        camera.applyToCanvas(canvas);
        canvas.rotate(45);
        canvas.translate(-cWidth, -cHeight);

        camera.restore();
        canvas.clipRect(600, cHeight, bWidth + 600, bHeight + 500);
        canvas.drawBitmap(bitmap2, 600, 500, mPaint);
        canvas.restore();

        //--------------------------绘制图片上半部分
        canvas.save();
        canvas.clipRect(600, 500, bWidth + 600, bHeight / 2 + 500);
        canvas.drawBitmap(bitmap2, 600, 500, mPaint);
        canvas.restore();

    }
}
