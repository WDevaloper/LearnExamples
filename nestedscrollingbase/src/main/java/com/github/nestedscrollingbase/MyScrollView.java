package com.github.nestedscrollingbase;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingChild;
import androidx.core.view.NestedScrollingChildHelper;

public class MyScrollView extends View implements NestedScrollingChild {

    private String TAG = this.getClass().getSimpleName();

    private NestedScrollingChildHelper helper = new NestedScrollingChildHelper(this);

    public MyScrollView(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public MyScrollView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean startNestedScroll(int axes) {
        Log.e(TAG, "child startNestedScroll: ", null);
        return helper.startNestedScroll(axes);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable int[] consumed, @Nullable int[] offsetInWindow) {
        Log.e(TAG, "child dispatchNestedPreScroll: ", null);
        return super.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @Nullable int[] offsetInWindow) {
        Log.e(TAG, "child dispatchNestedScroll: ", null);
        return super.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        Log.e(TAG, "child dispatchNestedPreFling: ", null);
        return super.dispatchNestedPreFling(velocityX, velocityY);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        Log.e(TAG, "child dispatchNestedFling: ", null);
        return super.dispatchNestedFling(velocityX, velocityY, consumed);
    }
}
