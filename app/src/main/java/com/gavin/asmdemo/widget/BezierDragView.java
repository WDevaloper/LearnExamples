package com.gavin.asmdemo.widget;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.gavin.asmdemo.BaseCustomView;


/**
 * 消息气泡拖拽的 View
 */
public class BezierDragView extends BaseCustomView {

    public BezierDragView(Context context) {
        super(context);
    }

    @Override
    public void initView(Context context, AttributeSet attrs) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
