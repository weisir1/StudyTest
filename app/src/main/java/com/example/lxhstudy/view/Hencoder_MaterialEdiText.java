package com.example.lxhstudy.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lxhstudy.R;
import com.example.lxhstudy.item.MaterialEditTextActivity;
import com.example.lxhstudy.util.Utils;

public class Hencoder_MaterialEdiText extends androidx.appcompat.widget.AppCompatEditText {
    public static final float TEXT_SIZE = Utils.dp2px(12);
    public static final float TEXT_MARGIN = Utils.dp2px(8);
    public static final int TEXT_VERTICAL_OFFSET = (int) Utils.dp2px(20);
    public static final int TEXT_HORIZONTAL_OFFSET = (int) Utils.dp2px(5);
    public static final int TEXT_ANIMATION_OFFSET = (int) Utils.dp2px(16);
    private boolean floatingLabelShown;
    private ObjectAnimator animator;
    private ObjectAnimator animatorReverse;
    Rect backgroundPadding = new Rect();


    private boolean useFloatingLabel;

    public float getFloatingLabelFraction() {
        return floatingLabelFraction;
    }

    public void setFloatingLabelFraction(float floatingLabelFraction) {
        this.floatingLabelFraction = floatingLabelFraction;
        invalidate();
    }

    private float floatingLabelFraction;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public Hencoder_MaterialEdiText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
         init(context,attrs);
    }

    void init(Context context,AttributeSet attrs){
//        R.styleable.Hencoder_MaterialEdiText 的值其实是一个数组类型,数组存放内部属性(useFloatingLabel)对应的id
//        最终会通过数组中id找对应的属性,然后获取对应属性的值
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Hencoder_MaterialEdiText);//记载自定义的xml配置

//     Hencoder_MaterialEdiText_useFloatingLabel 此值可以看做一个下标, 从0开始  ,第一行内部为数组,此为获取数组内下标位置对应的数
        useFloatingLabel = typedArray.getBoolean(R.styleable.Hencoder_MaterialEdiText_useFloatingLabel, true);

        paint.setTextSize(TEXT_SIZE);
        onUseFloatingLabelChanged();
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (useFloatingLabel) {
                    if (floatingLabelShown && TextUtils.isEmpty(s)) {
                        floatingLabelShown = false;
                        getObjectAnimator().reverse();
                    } else if (!floatingLabelShown && !TextUtils.isEmpty(s)) {
                        floatingLabelShown = true;
                        getObjectAnimator().start();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    public void setUseFloatingLabel(boolean useFloatingLabel) {
        if (this.useFloatingLabel != useFloatingLabel) {
            this.useFloatingLabel = useFloatingLabel;
            onUseFloatingLabelChanged();
            //        requestLayout();  //调用此方法会重新做测量
        }
    }

    private void onUseFloatingLabelChanged() {
        getBackground().getPadding(backgroundPadding);
        if (useFloatingLabel) {   //用户决定是否使用滑动动画文字
            setPadding(getPaddingLeft(), (int) (backgroundPadding.top + TEXT_SIZE + TEXT_MARGIN), getPaddingRight(),
                    getPaddingBottom());
        } else {
            setPadding(getPaddingLeft(), backgroundPadding.top, getPaddingRight(),
                    getPaddingBottom());
        }
    }


    private ObjectAnimator getObjectAnimator() {
        if (animator == null) {
            animator = ObjectAnimator.ofFloat(Hencoder_MaterialEdiText.this, "floatingLabelFraction", 0, 1);
        }
        return animator;
    }
/*
    private ObjectAnimator getObjectAnimatorReverse() {
        if (animatorReverse == null) {
            animatorReverse = ObjectAnimator.ofFloat(Hencoder_MaterialEdiText.this, "floatingLabelFraction", 1);
        }
        return animator;
    }*/

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setAlpha((int) (0xff * floatingLabelFraction));
        float extraOffset = TEXT_ANIMATION_OFFSET * (1 - floatingLabelFraction);  //floatingLabelFraction消失时为0,显示为1
        canvas.drawText(getHint().toString(), TEXT_HORIZONTAL_OFFSET, TEXT_VERTICAL_OFFSET + extraOffset, paint);
    }
}
