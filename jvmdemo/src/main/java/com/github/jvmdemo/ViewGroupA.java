package com.github.jvmdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ViewGroupA extends FrameLayout {
    private static final String TAG = "touch";

    public ViewGroupA(@NonNull Context context) {
        super(context);
    }

    public ViewGroupA(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewGroupA(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // Activity.dispatchTouchEvent --> ViewGroup.dispatchTouchEvent
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        Log.e(TAG, "dispatchTouchEvent ViewGroupA");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "onTouchEvent ViewGroupA", null);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e(TAG, "onInterceptTouchEvent ViewGroupA", null);
        return super.onInterceptTouchEvent(ev);
    }
}
