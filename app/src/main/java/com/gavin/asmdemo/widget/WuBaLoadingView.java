package com.gavin.asmdemo.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gavin.asmdemo.R;

public class WuBaLoadingView extends LinearLayout {
    private ImageView mShapeView, mYingyinView;

    private int mCurrentRes = R.mipmap.c;
    private ValueAnimator mShapeDown;
    private ValueAnimator mShapeScaleDown;
    private int mCurrentShape = -360;

    public WuBaLoadingView(Context context) {
        this(context, null);
    }

    public WuBaLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WuBaLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(context, R.layout.wubaloading_view_layout, this);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mShapeView = findViewById(R.id.shape_view);
        mYingyinView = findViewById(R.id.ying_yin);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimDown();
    }

    private void startAnimUp() {
        AnimatorSet animatorSet = new AnimatorSet();
        mShapeDown = ObjectAnimator.ofFloat(mShapeView, "translationY", 260, 0);
        mShapeScaleDown = ObjectAnimator.ofFloat(mYingyinView, "scaleX", 0.3f, 1);
        animatorSet.playTogether(mShapeDown, mShapeScaleDown);
        animatorSet.setDuration(600);

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                startAnimDown();
            }

            @Override
            public void onAnimationStart(Animator animation, boolean isReverse) {
                mCurrentShape = -mCurrentShape;
                startShapeRoteAnimator();
            }
        });
        animatorSet.start();

    }

    private void startAnimDown() {
        AnimatorSet animatorSet = new AnimatorSet();
        mShapeDown = ObjectAnimator.ofFloat(mShapeView, "translationY", 0, 250);
        mShapeDown.setInterpolator(new AccelerateInterpolator());
        mShapeScaleDown = ObjectAnimator.ofFloat(mYingyinView, "scaleX", 1, 0.3f);
        animatorSet.playTogether(mShapeDown, mShapeScaleDown);
        animatorSet.setDuration(600);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                startAnimUp();
                changeShape();
            }
        });
        animatorSet.start();
    }

    private void changeShape() {
        if (mCurrentRes != R.mipmap.broken_heart) {
            mShapeView.setImageResource(R.mipmap.broken_heart);
            mCurrentRes = R.mipmap.broken_heart;
        } else {
            mShapeView.setImageResource(R.mipmap.heart_one);
            mCurrentRes = R.mipmap.heart_one;
        }
    }


    /**
     * 执行旋转动画
     */
    private void startShapeRoteAnimator() {
        ObjectAnimator roteAnimation = getUpThrowRoteAnimation();
        roteAnimation.setDuration(3000);
        roteAnimation.setInterpolator(new DecelerateInterpolator());
        roteAnimation.start();
    }

    /**
     * 得到当前正在上抛时应该旋转的动画
     */
    public ObjectAnimator getUpThrowRoteAnimation() {
        return ObjectAnimator.ofFloat(mShapeView,
                "rotationY", mShapeView.getRotation(), mCurrentShape);
    }
}
