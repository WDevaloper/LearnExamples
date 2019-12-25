package com.gavin.asmdemo.widget;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


/**
 * 消息气泡拖拽的 View
 */
public class BezierDragView extends View {
    private PointF mFixPoint;
    private PointF mDragPoint;

    private Paint mPaint;
    private float maxFixRadiusDistance = dip2px(getContext(), 10f);
    private float mFixRadius = dip2px(getContext(), 10f);
    private float mMinFixRadius = dip2px(getContext(), 2);

    private float mDraRadius = dip2px(getContext(), 10f);

    public BezierDragView(Context context) {
        this(context, null);
    }

    public BezierDragView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierDragView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }


    public void initView(Context context, AttributeSet attrs) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setColor(Color.RED);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initPoint(event);
                break;
            case MotionEvent.ACTION_MOVE:
                updateDragPoint(event);
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }

        invalidate();
        return true;
    }

    private void updateDragPoint(MotionEvent event) {
        mDragPoint.x = event.getX();
        mDragPoint.y = event.getY();
    }

    private void initPoint(MotionEvent event) {
        mFixPoint = new PointF(event.getX(), event.getY());
        mDragPoint = new PointF(event.getX(), event.getY());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mFixPoint == null || mDragPoint == null) {
            return;
        }
        canvas.drawCircle(mDragPoint.x, mDragPoint.y, mDraRadius, mPaint);


        float pDistance = getPointDistance();
        mFixRadius = maxFixRadiusDistance - pDistance / 14;

        if (mFixRadius > mMinFixRadius) {
            canvas.drawCircle(mFixPoint.x, mFixPoint.y, mFixRadius, mPaint);
        }
    }

    private float getPointDistance() {
        return (float) Math.sqrt(
                (mFixPoint.x - mDragPoint.x) * (mFixPoint.x - mDragPoint.x) +
                        (mFixPoint.y - mDragPoint.y) * (mFixPoint.y - mDragPoint.y)
        );
    }

    private int dip2px(Context context, float dpValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }
}
