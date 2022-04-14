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

public class ArrowView extends View implements SensorEventListener {

    private final Bitmap bitmap;
    private float[] allValue;

    public ArrowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.north);
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(this
                , sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
                , SensorManager.SENSOR_DELAY_GAME);     //创建了一个适合游戏于操作的方位传感器
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {   //方位传感器
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
            float x = allValue[0];
            float y = allValue[1];
//            canvas.restore();    //重置绘图对象
            canvas.translate(getWidth() / 2, getHeight() / 2);

            if (y == 0 && x > 0) {
                canvas.rotate(90);
            } else if (y == 0 && x < 0) {
                canvas.rotate(270);
            } else {
                if (y >= 0) {
                    canvas.rotate((float) Math.tanh(x / y) * 90);
                } else {
                    canvas.rotate(180 + (float) Math.tanh(x / y) * 90);
                }
            }

        }
        canvas.drawBitmap(bitmap, -bitmap.getWidth() / 2, -bitmap.getHeight() / 2, paint);
    }
}
