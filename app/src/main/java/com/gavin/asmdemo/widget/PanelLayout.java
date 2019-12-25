package com.gavin.asmdemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class PanelLayout extends RelativeLayout {
    private Paint mPaint;
    private Path mPath;

    public PanelLayout(Context context) {
        this(context, null);
    }

    public PanelLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PanelLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPath = new Path();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        mPath.addCircle(getWidth() / 2, getHeight() / 2, 100f, Path.Direction.CCW);
        canvas.clipPath(mPath);
        super.dispatchDraw(canvas);
    }
}
