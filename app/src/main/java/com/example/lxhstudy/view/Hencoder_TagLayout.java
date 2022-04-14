package com.example.lxhstudy.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;

import java.util.Arrays;

public class Hencoder_TagLayout extends ViewGroup {
    Rect[] childrenBounds;

    public Hencoder_TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
//        setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
        for (int i = 0; i < 27; i++) {
            Hencoder_ColoredText_View view = new Hencoder_ColoredText_View(context);
            view.setLayoutParams(new ViewGroup.MarginLayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
            view.setTextForIndex(i);
            addView(view);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthUsed = 0;
        int lineWidthUsed = 0;
        int heightUsed = 0;
        int childCount = getChildCount();
        int maxHeight = 0;
        if (childrenBounds == null) {
            childrenBounds = new Rect[childCount];
        } else if (childrenBounds.length < childCount) {
            childrenBounds = Arrays.copyOf(childrenBounds, childCount);
        }
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            Rect rect = childrenBounds[i];
//            child.getLayoutParams()  开发者要求

//            根据开发者的要求和父view给定的要求确定子view的要求和大小
            measureChildWithMargins(child, widthMeasureSpec, lineWidthUsed, heightMeasureSpec, heightUsed);
//          判断另起一行
            if (MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.UNSPECIFIED &&  //不能添加无限宽,无限宽长度为0,child长度肯定要大,会出问题
                    child.getMeasuredWidth() + lineWidthUsed + getPaddingStart() + getPaddingEnd() >= MeasureSpec.getSize(widthMeasureSpec)) {
                lineWidthUsed = 0;
                heightUsed += maxHeight;
                maxHeight = 0;
                measureChildWithMargins(child, widthMeasureSpec, lineWidthUsed, heightMeasureSpec, heightUsed);
            }

            if (rect == null) {
                rect = childrenBounds[i] = new Rect();
            }
            rect.set(lineWidthUsed, heightUsed, lineWidthUsed + child.getMeasuredWidth(), heightUsed + child.getMeasuredHeight());
            lineWidthUsed += child.getMeasuredWidth();
            widthUsed = Math.max(widthUsed, lineWidthUsed);
            maxHeight = Math.max(maxHeight, child.getMeasuredHeight());
        }
        int width = widthUsed;   //我的layout长宽计算出来
        int height = maxHeight + heightUsed;
//        resolveSizeAndState() 会在返回值中添加一个-----是否被父view给压小了 父view根据相关计算!=0 知道该尺寸并不是子view所愿
        setMeasuredDimension(resolveSizeAndState(width, widthMeasureSpec, 0), resolveSizeAndState(height, heightMeasureSpec, 0));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            Rect rect = childrenBounds[i];
            child.layout(rect.left,rect.top,rect.right,rect.bottom);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }
}
