package com.gavin.asmdemo.event;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * @Describe:
 * @Author: wfy
 * @Version: Create time: (wfy) on 2019/12/10 0:12
 * company :
 */
public class MyView extends View {
    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e("tag", this + "  dispatchTouchEvent  " + getId());
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("tag", this + "  onTouchEvent  " + getId());
        return super.onTouchEvent(event);
    }
}
