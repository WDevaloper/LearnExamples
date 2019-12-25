package com.gavin.asmdemo.screen_adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

public class EventGroup extends FrameLayout {
    public EventGroup(Context context) {
        super(context);
    }

    public EventGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EventGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("tag", "EventGroup  ---> dispatchTouchEvent--->" + ev.getAction());
        return super.dispatchTouchEvent(ev);
    }


    /**
     * ViewGroup的onInterceptTouchEvent方法，在MOVW事件至少进来一次，也算是给ViewGroup，
     * 一次拦截的机会，比如MOVE：child调用requestDisallowInterceptTouchEvent，第一次的MOVE会回调onInterceptTouchEvent，之后就没有了
     * 如果没有调用requestDisallowInterceptTouchEvent，那么每次都会调用
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("tag", "EventGroup  ---> onInterceptTouchEvent --->" + ev.getAction());
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.e("tag", "EventGroup  ---> onTouchEvent  --->" + ev.getAction());
        return super.onTouchEvent(ev);
    }
}
