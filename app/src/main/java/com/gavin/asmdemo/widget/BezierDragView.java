package com.gavin.asmdemo.widget;


import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;


/**
 * 消息气泡拖拽的 View
 */
public class BezierDragView extends View {
    private ValueAnimator mAnimator;
    private Paint mPaint;
    // 固定圆心
    private PointF mFixPoint;
    // 拖拽圆心
    private PointF mDragPoint;
    private float mMinFixRadius;
    // 固定最大圆半径
    private float mMaxFixRadius;
    // 固定圆半径，随着两圆心的距离而变小
    private float mFixRadius;
    // 拖拽圆
    private float mDraRadius;

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

        mMinFixRadius = dip2px(getContext(), 2);
        mFixRadius = dip2px(getContext(), 5f);
        mMaxFixRadius = dip2px(getContext(), 7f);
        mDraRadius = dip2px(getContext(), 8f);


        mAnimator = ObjectAnimator.ofFloat(1.0f);
        mAnimator.setDuration(300);
        mAnimator.setInterpolator(new OvershootInterpolator(3.0f));
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mAnimator != null) {
            mAnimator.cancel();
            mAnimator = null;
        }
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
                if (mFixRadius > mMinFixRadius) {
                    if (mAnimator == null || !mAnimator.isRunning()) {
                        //保存原始点
                        final PointF originDragPoint = new PointF(mDragPoint.x, mDragPoint.y);
                        final PointF originFixPoint = new PointF(mFixPoint.x, mFixPoint.y);
                        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                float fraction = animation.getAnimatedFraction();
                                mDragPoint.x = originDragPoint.x + (originFixPoint.x - originDragPoint.x) * fraction;
                                mDragPoint.y = originDragPoint.y + (originFixPoint.y - originDragPoint.y) * fraction;
                                invalidate();
                            }
                        });
                        mAnimator.start();
                    }
                } else {
                    //爆炸效果
                }
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


        // 计算两个圆心之间的距离
        float pDistance = getPointDistance();

        mFixRadius = mMaxFixRadius - pDistance / 15;

        Path path = getBezierPath();
        if (path != null) {
            canvas.drawPath(path, mPaint);
            canvas.drawCircle(mFixPoint.x, mFixPoint.y, mFixRadius, mPaint);
        }
    }

    private Path getBezierPath() {
        if (mFixRadius < mMinFixRadius) {
            return null;
        }
        Path bezierPath = new Path();
        // 贝塞尔曲线怎么求？

        // 计算斜率
        float dx = mFixPoint.x - mDragPoint.x;
        float dy = mFixPoint.y - mDragPoint.y;
        //计算偏差
        if (dx == 0) dx = 0.001f;
        float tan = dy / dx;
        //通过反三角，获取角a度数值
        float arcTanA = (float) Math.atan(tan);

        // 依次计算 p0 , p1 , p2 , p3 点的位置
        float P0X = (float) (mFixPoint.x + mFixRadius * Math.sin(arcTanA));
        float P0Y = (float) (mFixPoint.y - mFixRadius * Math.cos(arcTanA));

        float P1X = (float) (mDragPoint.x + mDraRadius * Math.sin(arcTanA));
        float P1Y = (float) (mDragPoint.y - mDraRadius * Math.cos(arcTanA));

        float P2X = (float) (mDragPoint.x - mDraRadius * Math.sin(arcTanA));
        float P2Y = (float) (mDragPoint.y + mDraRadius * Math.cos(arcTanA));

        float P3X = (float) (mFixPoint.x - mFixRadius * Math.sin(arcTanA));
        float P3Y = (float) (mFixPoint.y + mFixRadius * Math.cos(arcTanA));

        // 求控制点 两个点的中心位置作为控制点
        PointF controlPoint = new PointF((mFixPoint.x + mDragPoint.x) / 2, (mFixPoint.y + mDragPoint.y) / 2);

        bezierPath.moveTo(P0X, P0Y);
        bezierPath.quadTo(controlPoint.x, controlPoint.y, P1X, P1Y);
        bezierPath.lineTo(P2X, P2Y);
        bezierPath.quadTo(controlPoint.x, controlPoint.y, P3X, P3Y);
        bezierPath.close();
        return bezierPath;
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
