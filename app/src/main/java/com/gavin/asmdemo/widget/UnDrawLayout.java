package com.gavin.asmdemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

public class UnDrawLayout extends FrameLayout {

    private Paint mPaint;


    public UnDrawLayout(Context context) {
        this(context, null);
    }

    public UnDrawLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UnDrawLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
    }


    //ViewGroup不会回调这个方法,设置背景也是会回调的，具体请看ViewGroup的构造方法initViewGroup
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("tag", "-----> onDraw");
        canvas.drawCircle(200, 200, 100, mPaint);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }
}
