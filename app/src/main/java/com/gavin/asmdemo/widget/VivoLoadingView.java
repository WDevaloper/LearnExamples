package com.gavin.asmdemo.widget;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.gavin.asmdemo.R;

public class VivoLoadingView extends View {
    private int[] mCircleColors;
    //旋转圆的画笔
    private Paint mPaint;
    //8个小球的半径
    private float mCircleRadius = 8;
    //当前大圆的旋转角度
    private float mCurrentRotateAngle = 0F;
    //旋转大圆的半径
    private float mRotateRadius = 70;
    //属性动画
    private ValueAnimator mValueAnimator;


    //表示旋转圆的中心坐标
    private float mCenterX;
    private float mCenterY;


    public VivoLoadingView(Context context) {
        this(context, null);
    }

    public VivoLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VivoLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCircleColors = context.getResources().getIntArray(R.array.laoding_circle_colors);
        mValueAnimator = ValueAnimator.ofFloat(0, (float) (Math.PI * 2));
        mValueAnimator.setRepeatCount(-1);
        mValueAnimator.setDuration(1200);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //大圆旋转的角度，为了保持小圆和小圆之间的角度，那么小圆的角度角度也要加上这个角度，因为我们计算的小圆之间的角度是
                // 是按照大圆固定时的角度计算的
                mCurrentRotateAngle = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mValueAnimator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w * 1f / 2;
        mCenterY = h * 1f / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //两个小球之间的角度，就是一周平分,即：8个小球均分2PI
        float rotateAngle = (float) (Math.PI * 2 / mCircleColors.length);


        for (int i = 0; i < mCircleColors.length; i++) {
            // x = r * cos(a) + centerX
            // y = r * sin(a) + centerY
            //小球当前的角度
            float angle = i * rotateAngle + mCurrentRotateAngle;
            float cx = (float) (Math.cos(angle) * mRotateRadius + mCenterX);
            float cy = (float) (Math.sin(angle) * mRotateRadius + mCenterY);
            mPaint.setColor(mCircleColors[mCircleColors.length - (i + 1)]);
            canvas.drawCircle(cx, cy, mCircleRadius + i, mPaint);
        }
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mValueAnimator.cancel();
        mValueAnimator = null;
    }
}
