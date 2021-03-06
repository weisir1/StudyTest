package com.example.lxhstudy.view;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;

import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;

import com.example.lxhstudy.util.Utils;

public class Hencoder_ScalableImageView extends View  {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final float IMAGE_WIDTH = Utils.dp2px(200);
    private static final float OVER_SCALE_FACTOR = 1.5f;
    float originalOffsetX;
    float originalOffsetY;
    float imageWidth = IMAGE_WIDTH;
    float imageHeight;
    float offsetX;
    float offsetY;
    Bitmap bitmap;
    MRunnable runnable = new MRunnable();

    float smallScale;
    float bigScale;

    boolean big;
    float scaleFraction;
    GestureDetector detector;
    GestureDetector.OnGestureListener gestureListener = new HenGestureListener();
    GestureDetector.OnDoubleTapListener doubleTapListener = new HenDoubleTapListener();


    ObjectAnimator animator;
    OverScroller scroller;

    public Hencoder_ScalableImageView(Context context) {
        super(context);
        init(context);
    }

    public Hencoder_ScalableImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        bitmap = Utils.getAvatar(getResources(), (int) IMAGE_WIDTH);

        detector = new GestureDetector(context, gestureListener);
        detector.setOnDoubleTapListener(doubleTapListener);
       /* detector = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                return super.onDoubleTapEvent(e);
            }
        }); */ //??????????????????????????????????????????,????????????????????????????????????        detector.setIsLongpressEnabled(false);
        scroller = new OverScroller(context);
        imageHeight = bitmap.getHeight();
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);
        originalOffsetX = ((float) getWidth() - imageWidth) / 2;
        originalOffsetY = ((float) getHeight() - imageHeight) / 2;

        if ((float) imageWidth / imageHeight > (float) getWidth() / getHeight()) {
            smallScale = (float) getWidth() / imageWidth;
            bigScale = (float) getHeight() / imageHeight * OVER_SCALE_FACTOR;
        } else {
            smallScale = (float) getHeight() / imageHeight;
            bigScale = (float) getWidth() / imageWidth * OVER_SCALE_FACTOR;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(offsetX * scaleFraction, offsetY * scaleFraction);
        float scale = smallScale + (bigScale - smallScale) * scaleFraction;
        canvas.scale(scale, scale, getWidth() / 2, getHeight() / 2);
        canvas.translate(originalOffsetX, originalOffsetY);
        canvas.drawBitmap(bitmap, 0, 0, paint);
    }

    private float getScaleFraction() {
        return scaleFraction;
    }

    private void setScaleFraction(float scaleFraction) {
        this.scaleFraction = scaleFraction;
        invalidate();
    }


    private ObjectAnimator getScaleAnimator() {
        if (animator == null) {
            animator = ObjectAnimator.ofFloat(this, "scaleFraction", 0, 1);
        }
        return animator;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
       detector.onTouchEvent(event);
        return true;    //???????????????detector?????????
    }

    /*
     * GestureDetector.OnGestureListener????????????
     * */
    class HenGestureListener implements GestureDetector.OnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {  //???????????????action_down
            return true;  //??????????????????true,????????????????????????????????????view
        }

        /*
         * ???????????????
         * ????????????view?????????????????????,????????????????????????
         * ???????????????????????????100???????????????
         * */
        @Override
        public void onShowPress(MotionEvent e) {

        }


        /*
         * ?????????????????????????????????????????????????????????,????????????
         * */
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        /**
         * @param down      ????????????(????????????)
         * @param event     ????????????
         * @param distanceX ?????????event????????????event?????????????????? x   ?????????-?????????
         * @param distanceY y?????????
         */
        @Override
        public boolean onScroll(MotionEvent down, MotionEvent event, float distanceX, float distanceY) {
//        ?????????????????????
            if (big) {
                offsetX -= distanceX;
                offsetX = Math.min(offsetX, (bitmap.getWidth() * bigScale - getWidth()) / 2);
                offsetX = Math.max(offsetX, -(bitmap.getWidth() * bigScale - getWidth()) / 2);
                offsetY -= distanceY;
                offsetY = Math.min(offsetY, (bitmap.getHeight() * bigScale - getHeight()) / 2);
                offsetY = Math.max(offsetY, -(bitmap.getHeight() * bigScale - getHeight()) / 2);

                invalidate();
            }
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        /*
         * ???????????? ????????????
         * */
        @Override
        public boolean onFling(MotionEvent down, MotionEvent event, float velocityX, float velocityY) {
            if (big) {
                scroller.fling((int) offsetX, (int) offsetY, (int) velocityX, (int) velocityY,
                        (int) -(bitmap.getWidth() * bigScale - getWidth()),
                        (int) (bitmap.getWidth() * bigScale - getWidth()),
                        (int) -(bitmap.getHeight() * bigScale - getHeight()),
                        (int) (bitmap.getHeight() * bigScale - getHeight())
                );

                postOnAnimation(runnable);  //????????????????????????
            }

            return false;
        }
    }

    class MRunnable implements Runnable {
        @Override
        public void run() {
//        ??????scroller??????xy??????,???????????????
            if (scroller.computeScrollOffset()) {  //????????????????????????????????????
                offsetX = scroller.getCurrX();
                offsetY = scroller.getCurrY();
                invalidate();
                postOnAnimation(this);
            }
        }
    }

    /*
     * ????????????
     * */


    class HenDoubleTapListener implements GestureDetector.OnDoubleTapListener{

        //    ????????????????????????????????????????????????

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return false;
        }
        /*
         * ??????300??????
         * */
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.i("WeiSir", "onDoubleTap: ");
            big = !big;
            if (big) {
                getScaleAnimator().start();
            } else {
                getScaleAnimator().reverse();
            }
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            return false;
        }
    }



}
