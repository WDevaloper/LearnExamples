package com.gavin.asmdemo.widget.path;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.gavin.asmdemo.R;

/**
 * @Describe:
 * @Author: wfy
 */
public class PathMeasureView2 extends View {
    private static final String TAG = "tag";
    private Path mCirclePath = new Path();
    private Path mHookPath = new Path();
    private Path mDst = new Path();

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private PathMeasure pathMeasure = new PathMeasure();

    private float fragtion = 0;

    private PathState mPathState;

    public PathMeasureView2(Context context) {
        this(context, null);
    }

    public PathMeasureView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathMeasureView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor("#8cc350"));
        mPaint.setStrokeWidth(5);
        mPaint.setDither(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //将canvas坐标原点移动到中间
        canvas.translate(getWidth() / 2, getHeight() / 2);

        if (mPathState == null) {
            mPathState = new CircleState();
        }

        mPathState.draw(canvas);
    }


    private abstract class PathState {
        abstract void draw(Canvas canvas);
    }


    private class CircleState extends PathState {
        private CircleState() {
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, (float) (Math.PI * 2));
            valueAnimator.setDuration(5000);
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    fragtion = animation.getAnimatedFraction();
                    invalidate();
                }
            });
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    invalidate();
                }
            });
            valueAnimator.start();
        }

        @Override
        void draw(Canvas canvas) {
            mCirclePath.reset();
            mCirclePath.addCircle(0, 0, getWidth() / 2, Path.Direction.CW);
            mCirclePath.moveTo(-getWidth() / 2 + 10, 0);
            mCirclePath.lineTo(0, getWidth() / 2);
            mCirclePath.lineTo(getWidth() / 2 - 10, -getWidth() / 2 + 10);

            pathMeasure.setPath(mCirclePath, false);
            pathMeasure.getSegment(0, pathMeasure.getLength() * fragtion, mDst, true);

            //同时绘制圆和直线
            pathMeasure.nextContour();
            pathMeasure.getSegment(0, pathMeasure.getLength() * fragtion, mDst, true);

            mPaint.setColor(Color.RED);
            canvas.drawPath(mDst, mPaint);
        }
    }
}
