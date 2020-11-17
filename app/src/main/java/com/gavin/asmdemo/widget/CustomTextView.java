package com.gavin.asmdemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CustomTextView extends View {
    public CustomTextView(Context context) {
        this(context, null);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = getMeasureWithOrHeight(widthMeasureSpec);

    }

    private int getMeasureWithOrHeight(int widthOrHeightMeasureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(widthOrHeightMeasureSpec);
        int size = MeasureSpec.getSize(widthOrHeightMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {//match_parent or xx dp
            result = size;
        } else {
        }
        return 0;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
