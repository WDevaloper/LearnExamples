package com.github.nestedscrollingbase;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingChild;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.NestedScrollingParentHelper;

public class MyScrollView extends View implements NestedScrollingChild {
    private NestedScrollingChildHelper helper = new NestedScrollingChildHelper(this);

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return helper.startNestedScroll(axes);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable int[] consumed, @Nullable int[] offsetInWindow) {
        return super.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }
}
