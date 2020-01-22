package com.gavin.asmdemo.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;

@SuppressLint("DrawAllocation")
public class ColorTraceTextView extends AppCompatTextView {

    private float mCurrentProgress = 0.5f;

    private Paint mOriginPaint;
    private Paint mChangePaint;

    public ColorTraceTextView(Context context) {
        this(context, null);
    }

    public ColorTraceTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorTraceTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mChangePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mChangePaint.setTextSize(getTextSize());
        mChangePaint.setColor(Color.RED);

        mOriginPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mOriginPaint.setTextSize(getTextSize());
        mOriginPaint.setColor(getTextColors().getDefaultColor());
    }


    /**
     * 可以通过剪切范围控制字体颜色
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        String content = getText().toString();
        Rect bounds = new Rect();
        mOriginPaint.getTextBounds(content, 0, content.length(), bounds);
        int x = getWidth() / 2 - bounds.width() / 2;
        Paint.FontMetrics fontMetrics = mOriginPaint.getFontMetrics();
        float baseLine = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        canvas.drawText(content, x, baseLine, mOriginPaint);
    }
}