package com.github.jvmdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class ViewA extends View {
    private static final String TAG = "touch";

    public ViewA(Context context) {
        super(context);
    }

    public ViewA(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewA(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e(TAG, "dispatchTouchEvent ViewA ", null);
        return super.dispatchTouchEvent(event);
    }

    //会影响点击事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "onTouchEvent ViewA", null);
        return super.onTouchEvent(event);
    }

}
