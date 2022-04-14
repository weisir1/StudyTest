package com.example.lxhstudy.view;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.lxhstudy.util.Utils;

import okhttp3.RequestBody;
import okhttp3.internal.Util;

public class Hencoder_Camera_View extends View {

    private final Paint paint;
    private Camera camera = new Camera();
    private static final float PADDING = Utils.dp2px(100);
    private static final float IMAGE_WIDTH = Utils.dp2px(200);

    public float getTopFlip() {
        return topFlip;
    }

    public void setTopFlip(float topFlip) {
        this.topFlip = topFlip;
        invalidate();
    }

    public float getBottomFlip() {
        return bottomFlip;
    }

    public void setBottomFlip(float bottomFlip) {
        this.bottomFlip = bottomFlip;
        invalidate();
    }

    public float getFlipRotation() {
        return flipRotation;
    }

    public void setFlipRotation(float flipRotation) {
        this.flipRotation = flipRotation;
        invalidate();
    }

    float topFlip = 0;
    float bottomFlip = 0;
    float flipRotation = 0;

    public Hencoder_Camera_View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    {
        camera.setLocation(0, 0, -30);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制上半部分
        canvas.save();
        canvas.translate(PADDING + IMAGE_WIDTH / 2, PADDING + IMAGE_WIDTH / 2);
        canvas.rotate(-flipRotation);

        camera.save();
        camera.rotateX(topFlip);
        camera.applyToCanvas(canvas);
        camera.restore();

        canvas.clipRect(-IMAGE_WIDTH, -IMAGE_WIDTH, IMAGE_WIDTH, 0);
        canvas.rotate(flipRotation);
        canvas.translate(-(PADDING + IMAGE_WIDTH / 2), -(PADDING + IMAGE_WIDTH / 2));
        canvas.drawBitmap(Utils.getAvatar(getResources(), (int) IMAGE_WIDTH), PADDING, PADDING, paint);
        canvas.restore();

//        绘制下半部分
        canvas.translate(PADDING + IMAGE_WIDTH / 2, PADDING + IMAGE_WIDTH / 2);
        canvas.rotate(-flipRotation);

        camera.save();
        camera.rotateX(bottomFlip);
        camera.applyToCanvas(canvas);
        camera.restore();

        canvas.clipRect(-IMAGE_WIDTH , 0, IMAGE_WIDTH, IMAGE_WIDTH);
        canvas.rotate(flipRotation);
        canvas.translate(-(PADDING + IMAGE_WIDTH / 2), -(PADDING + IMAGE_WIDTH / 2));
        canvas.drawBitmap(Utils.getAvatar(getResources(), (int) IMAGE_WIDTH), PADDING, PADDING, paint);

    }
}
