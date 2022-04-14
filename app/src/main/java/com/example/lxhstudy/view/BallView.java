package com.example.lxhstudy.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.lxhstudy.R;

public class BallView extends View implements SensorEventListener {

    private final Bitmap bitmap;
    private float[] allValue;
    private int xSpeed = 0;
    private int ySpeed = 0;
    private Point point = new Point();

    public BallView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(this
                , sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION)
                , SensorManager.SENSOR_DELAY_GAME);     //创建了一个适合游戏于操作的方位传感器
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {   //方位传感器
            float[] values = event.values;
            allValue = values;
            postInvalidate();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        if (allValue != null) {
            xSpeed = (int) -allValue[2];
            ySpeed = (int) -allValue[1];
        }
        point.x += xSpeed;
        point.y += ySpeed;

        if (point.x < 0) {
            point.x = 0;
        }
        if (point.y < 0) {
            point.y = 0;
        }
        if (point.x > getWidth() - bitmap.getWidth()) {
            point.x = getWidth() - bitmap.getWidth();
        }
        if (point.y > getHeight() - bitmap.getHeight()) {
            point.y = getHeight() - bitmap.getHeight();
        }
        canvas.drawBitmap(bitmap, point.x, point.y, paint);
    }
}
