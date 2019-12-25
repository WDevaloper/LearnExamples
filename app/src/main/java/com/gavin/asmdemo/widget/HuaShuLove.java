package com.gavin.asmdemo.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.gavin.asmdemo.R;

import java.util.Random;

//花束直播
public class HuaShuLove extends FrameLayout {
    private int mHeight = 0;
    private int mWidth = 0;
    private int mIconHeight;
    private int mIconWidth;


    private Interpolator[] mInterpolator = {new AccelerateDecelerateInterpolator(),
            new AccelerateInterpolator(), new DecelerateInterpolator(), new LinearInterpolator()};
    private Random random = new Random();

    private Drawable[] icons = {
            getResources().getDrawable(R.mipmap.broken_heart),
            getResources().getDrawable(R.mipmap.heart_one),
            getResources().getDrawable(R.mipmap.love),
            getResources().getDrawable(R.mipmap.romantic_music),
            getResources().getDrawable(R.mipmap.gift),
            getResources().getDrawable(R.mipmap.cupid),
            getResources().getDrawable(R.mipmap.a),
            getResources().getDrawable(R.mipmap.b),
            getResources().getDrawable(R.mipmap.c),
            getResources().getDrawable(R.mipmap.d),
            getResources().getDrawable(R.mipmap.e),
            getResources().getDrawable(R.mipmap.f),
            getResources().getDrawable(R.mipmap.g),
            getResources().getDrawable(R.mipmap.h),
            getResources().getDrawable(R.mipmap.q),
            getResources().getDrawable(R.mipmap.rose),
            getResources().getDrawable(R.mipmap.hearts)};

    public HuaShuLove(Context context) {
        this(context, null);
    }

    public HuaShuLove(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HuaShuLove(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mIconHeight = icons[0].getIntrinsicHeight();
        mIconWidth = icons[0].getIntrinsicWidth();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();
    }

    public void addLove() {
        for (int i = 0; i < 6; i++) {
            int anInt = random.nextInt(300);
            LayoutParams layoutParams = new LayoutParams(mIconWidth, mIconHeight);
            layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
            ImageView imageView = new ImageView(getContext());
            imageView.setLayoutParams(layoutParams);
            imageView.setImageDrawable(icons[anInt % icons.length]);
            addView(imageView);

            AnimatorSet animatorSet = getAnimator(imageView);
            animatorSet.start();
        }
    }

    private AnimatorSet getAnimator(final View view) {
        AnimatorSet allAnimatorSet = new AnimatorSet();

        AnimatorSet inAnimator = new AnimatorSet();
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0.3f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0.3f, 1f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 0.3f, 1f);
        inAnimator.playTogether(scaleX, scaleY, alpha);
        inAnimator.setDuration(100);
        inAnimator.setTarget(view);

        allAnimatorSet.playSequentially(inAnimator, getBezierAnimator(view));
        allAnimatorSet.setTarget(view);
        allAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                removeView(view);
            }
        });
        return allAnimatorSet;
    }


    /*绘制自己的会child的内容*/
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    private Animator getBezierAnimator(final View view) {
        // 需确保 pointF1 的y值 大于 pointF2 的y值
        PointF pointF2 = new PointF(random.nextInt(mWidth), random.nextInt(mHeight / 2));
        PointF pointF1 = new PointF(random.nextInt(mWidth), random.nextInt(mHeight / 2) + mHeight / 2);
        // 起点位置
        PointF pointF0 = new PointF(mWidth / 2 - mIconHeight / 2, mHeight - mIconHeight);
        // 结束的位置
        PointF pointF3 = new PointF(random.nextInt(mWidth) - mIconHeight / 2, 0);
        // 估值器Evaluator,来控制view的行驶路径（不断的修改point.x,point.y）
        LoveTypeEvaluator evaluator = new LoveTypeEvaluator(pointF1, pointF2);
        // 属性动画不仅仅改变View的属性，还可以改变自定义的属性
        ValueAnimator animator = ValueAnimator.ofObject(evaluator, pointF0,
                pointF3);
        animator.setInterpolator(mInterpolator[random.nextInt(300) % mInterpolator.length]);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 不断改变ImageView的x,y的值
                PointF pointF = (PointF) animation.getAnimatedValue();
                view.setX(pointF.x);
                view.setY(pointF.y);
                view.setAlpha(1 - animation.getAnimatedFraction() + 0.1f);// 得到百分比
            }
        });
        animator.setStartDelay(20);
        animator.setTarget(view);
        animator.setDuration(3000);
        return animator;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeAllViews();
    }

    class LoveTypeEvaluator implements TypeEvaluator<PointF> {
        private PointF p1, p2;

        LoveTypeEvaluator(PointF p1, PointF p2) {
            this.p1 = p1;
            this.p2 = p2;
        }


        @Override
        public PointF evaluate(float t, PointF p0, PointF p3) {
            // t百分比， 0~1
            PointF point = new PointF();
            point.x = p0.x * (1 - t) * (1 - t) * (1 - t) //
                    + 3 * p1.x * t * (1 - t) * (1 - t)//
                    + 3 * p2.x * t * t * (1 - t)//
                    + p3.x * t * t * t;//

            point.y = p0.y * (1 - t) * (1 - t) * (1 - t) //
                    + 3 * p1.y * t * (1 - t) * (1 - t)//
                    + 3 * p2.y * t * t * (1 - t)//
                    + p3.y * t * t * t;//
            // 套用上面的公式把点返回 贝塞尔公式,完成三阶贝塞尔,两个控制点
            return point;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            addLove();
        }
        return super.onTouchEvent(event);
    }
}
