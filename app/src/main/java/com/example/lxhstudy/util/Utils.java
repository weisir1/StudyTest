package com.example.lxhstudy.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.TypedValue;

import com.example.lxhstudy.R;

public class Utils {
    public static Bitmap zoomImg(Bitmap bm, float scaleX, float scaleY) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(scaleX, scaleY);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }

    public static float dp2px(float dip){
       return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dip, Resources.getSystem().getDisplayMetrics());
    }

    public static Bitmap getAvatar(Resources resources, int width){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, R.drawable.ic_launcher,options);
        options.inJustDecodeBounds =false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(resources,R.drawable.ic_launcher,options);
    }
}
