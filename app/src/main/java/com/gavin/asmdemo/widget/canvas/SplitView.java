package com.gavin.asmdemo.widget.canvas;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.gavin.asmdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wfy
 */
public class SplitView extends View {
    private Paint mPaint;
    private List<Ball> mBalls = new ArrayList<>();
    private ValueAnimator mValueAnimator;
    private float d = 2;

    public SplitView(Context context) {
        this(context, null);
    }

    public SplitView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SplitView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mValueAnimator = ValueAnimator.ofFloat(0, 1);
        mValueAnimator.setDuration(2000);
        mValueAnimator.setRepeatCount(-1);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                updateBall();
                invalidate();
            }
        });

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //注意需要对图片进行压缩处理
        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.pic);
        for (int i = 0; i < mBitmap.getWidth(); i++) {
            for (int j = 0; j < mBitmap.getHeight(); j++) {
                Ball ball = new Ball();
                mBalls.add(ball);
                ball.color = mBitmap.getPixel(i, j);//获取每个像素点的颜色
                ball.dx = i * d + d / 2;
                ball.dy = j * d + d / 2;
                ball.radius = d / 2;


                //速度(-20,20)
                ball.vx = (float) (Math.pow(-1, Math.ceil(Math.random() * 1000)) * 20 * Math.random());
                ball.vy = rangeInt(-15, 35);
                //加速度
                ball.ax = 0.0f;
                ball.ay = 0.98f;

            }
        }
        mBitmap.recycle();
    }

    private void updateBall() {
        for (int i = 0; i < mBalls.size(); i++) {
            Ball ball = mBalls.get(i);
            ball.dx += ball.vx;
            ball.dy += ball.vy;

            ball.vx += ball.ax;
            ball.vy += ball.ay;
        }

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //测量：如果你自定义View，不复写onMeasure方法，最后你会发现：wrap_content 和 match_parent的效果是一样的，可以进去看看onMeasure源码
        //绘制：draw -> 1、绘制自己的内容，onDraw方法，但是在View是空实现，ViewGroup也没有实现该方法；
        //测量：1、如果你自定义View，不复写onMeasure方法，最后你会发现：wrap_content 和 match_parent的效果是一样的，可以进去看看onMeasure源码

        //绘制：1、绘制自己的内容，onDraw方法，但是在View是空实现，ViewGroup也没有实现该方法；
        //     2、在1完成之后，如果是ViewGroup，接着dispatchDraw去绘制child；
        //绘制步骤：
        // ViewGroup : 绘制自己的背景 -> 绘制自己的内容 -> 绘制child -> 绘制装饰
        //        // View : 绘制自己的背景 -> 绘制自己的内容 -> 绘制装饰

        //整个流程：
        // ViewGroup onMeasure(测量) -> onLayout(摆放child,child会调用layout摆放自己) ->
        // View  onMeasure(测量) ->  onDraw

        //ViewGroup.onMeasure
        // (会调用measureChild方法测量child，measureChild方法中会调用
        // getChildMeasureSpec(parentWidthMeasureSpec,.., ...))方法通过parentWidthMeasureSpec和View自身的layoutParam参数创建child的MeasureSpec
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//
//        int width = 0;
//        int height = 0;
//
//
//        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) getLayoutParams();
//
//        if (widthMode == MeasureSpec.AT_MOST || widthMode == MeasureSpec.EXACTLY) {
//            if (lp.width > 0) {
//                //相当精确值，无需考虑parent
//                width = lp.width;
//            } else if (lp.width == ViewGroup.LayoutParams.MATCH_PARENT) {
//                // child 与parent相等
//                width = widthSize;
//            } else if (lp.width == ViewGroup.LayoutParams.WRAP_CONTENT) {
//                //由child确定width，但是不能超过parent
//                width = Math.min(mBitmapWidth, widthSize);
//            }
//        }
//
//        if (heightMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.EXACTLY) {
//            if (lp.height > 0) {
//                //相当精确值，无需考虑parent
//                height = lp.height;
//            } else if (lp.height == ViewGroup.LayoutParams.MATCH_PARENT) {
//                // child 与parent相等
//                height = heightSize;
//            } else if (lp.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
//                //由child确定width，但是不能超过parent
//                height = Math.min(mBitmapHeight, heightSize);
//            }
//        }
//        setMeasuredDimension(width, height);
    }

    private int rangeInt(int i, int j) {
        int max = Math.max(i, j);
        int min = Math.min(i, j) - 1;
        //在0到(max - min)范围内变化，取大于x的最小整数 再随机
        // Math.ceil 将小数部分一律向整数部分进位,Math.ceil(12.2)//返回13
        return (int) (min + Math.ceil(Math.random() * (max - min)));
    }


    //ViewGroup不会回调这个方法,设置背景也是会回调的，具体请看ViewGroup的构造方法initViewGroup
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(300, 300);
        for (int i = 0; i < mBalls.size(); i++) {
            Ball ball = mBalls.get(i);
            mPaint.setColor(ball.color);
            canvas.drawCircle(ball.dx, ball.dy, ball.radius, mPaint);
        }
    }

    //View和ViewGroup 也是会调用的，layout方法是摆放自己，而onLayout是根据自己的要求摆放child的
    // 你是拿不到child的，所以你无法摆放child
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.e("tag", "----->  onLayout");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mValueAnimator.cancel();
        mBalls.clear();
    }

    @Override
    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mValueAnimator.start();
        }
        return super.onTouchEvent(event);
    }
}
