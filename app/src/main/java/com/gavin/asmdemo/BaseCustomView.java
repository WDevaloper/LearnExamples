package com.gavin.asmdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public abstract class BaseCustomView extends View {
    public BaseCustomView(Context context) {
        this(context, null);
    }

    public BaseCustomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    public abstract void initView(Context context, AttributeSet attrs);
}
