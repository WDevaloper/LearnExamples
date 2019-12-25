package com.wfy.event.litener;


import com.wfy.event.MotionEvent;
import com.wfy.event.View;

public interface OnTouchListener {
    boolean onTouch(View v, MotionEvent event);
}
