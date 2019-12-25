package com.gavin.asmdemo.screen_adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class EventView extends View {
    public EventView(Context context) {
        super(context);
    }

    public EventView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EventView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e("tag", "EventView  ---> dispatchTouchEvent -->" + event.getAction());
        return super.dispatchTouchEvent(event);
    }


    /*

    chTouchEvent--->0
2019-12-16 15:55:07.711 1132-1132/com.gavin.asmdemo E/tag: EventGroup  ---> onInterceptTouchEvent --->0
2019-12-16 15:55:07.711 1132-1132/com.gavin.asmdemo E/tag: EventView  ---> dispatchTouchEvent -->0
2019-12-16 15:55:07.711 1132-1132/com.gavin.asmdemo E/tag: EventView  ---> onTouchEvent --> 0
2019-12-16 15:55:07.792 1132-1132/com.gavin.asmdemo E/tag: EventGroup  ---> dispatchTouchEvent--->2
2019-12-16 15:55:07.793 1132-1132/com.gavin.asmdemo E/tag: EventGroup  ---> onInterceptTouchEvent --->2
2019-12-16 15:55:07.793 1132-1132/com.gavin.asmdemo E/tag: EventView  ---> dispatchTouchEvent -->2
2019-12-16 15:55:07.793 1132-1132/com.gavin.asmdemo E/tag: EventView  ---> onTouchEvent --> 2
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("tag", "EventView  ---> onTouchEvent --> " + event.getAction());
        if (event.getAction() == MotionEvent.ACTION_DOWN) return true;


        // requestDisallowInterceptTouchEvent只会影响除DOWN之外的事件
        //
        // 每个事件至少会调用一次onInterceptor  MotionEvent.ACTION_MOVE, 当child调用requestDisallowInterceptTouchEvent后将不会整个事件序列将不会在调用该方法
        // 如果没有调用
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            // 为什么要在
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        return super.onTouchEvent(event);
    }
}
