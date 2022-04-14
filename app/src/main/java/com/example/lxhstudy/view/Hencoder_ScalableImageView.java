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
        }); */ //所以直接使用提供的实现好的类,该类默认实现了这两个接口        detector.setIsLongpressEnabled(false);
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
        return true;    //将事件交给detector去处理
    }

    /*
     * GestureDetector.OnGestureListener监听回调
     * */
    class HenGestureListener implements GestureDetector.OnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {  //事件等价于action_down
            return true;  //这里必须返回true,否则后续的事件不会传入本view
        }

        /*
         * 预按下状态
         * 只有当父view是滑动空间时候,才会有预按下状态
         * 每次显示之前都会有100毫秒的延时
         * */
        @Override
        public void onShowPress(MotionEvent e) {

        }


        /*
         * 未实现双击监听时此方法作为单击监听有效,反之无效
         * */
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        /**
         * @param down      按下事件(会存起来)
         * @param event     当前事件
         * @param distanceX 上一个event点到当前event点位置的距离 x   旧位置-新位置
         * @param distanceY y遇上同
         */
        @Override
        public boolean onScroll(MotionEvent down, MotionEvent event, float distanceX, float distanceY) {
//        大图时才会移动
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
         * 快速滑动 惯性滑动
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

                postOnAnimation(runnable);  //在下一帧时被调用
            }

            return false;
        }
    }

    class MRunnable implements Runnable {
        @Override
        public void run() {
//        计算scroller当前xy的值,方便之后取
            if (scroller.computeScrollOffset()) {  //表示此动画是否没有执行完
                offsetX = scroller.getCurrX();
                offsetY = scroller.getCurrY();
                invalidate();
                postOnAnimation(this);
            }
        }
    }

    /*
     * 双击监听
     * */


    class HenDoubleTapListener implements GestureDetector.OnDoubleTapListener{

        //    此方法代替上方成为单击监听被回调

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return false;
        }
        /*
         * 小于300毫秒
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
