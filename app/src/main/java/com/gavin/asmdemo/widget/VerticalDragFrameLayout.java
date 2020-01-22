package com.gavin.asmdemo.widget;

import android.content.Context;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;

/**
 * @Describe:
 * @Author: wfy
 */
public class VerticalDragFrameLayout extends FrameLayout {
    private ViewDragHelper mViewDragHelper;
    private View mTargetView;
    private int mTargetViewHeight;


    public VerticalDragFrameLayout(Context context) {
        this(context, null);
    }

    public VerticalDragFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalDragFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mViewDragHelper = ViewDragHelper.create(this,0.6f, callback);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTargetView = getChildAt(1);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            mTargetViewHeight = getChildAt(0).getHeight();
            isMenuOpen = true;
            mViewDragHelper.smoothSlideViewTo(mTargetView, 0, mTargetViewHeight);
        }
    }

    private float downY;
    private boolean isMenuOpen = false;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (isMenuOpen) return true;

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = ev.getY();
                mViewDragHelper.processTouchEvent(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY = ev.getY();
                // 向下滑动，并且lsitview要滑动到顶部
                if (moveY - downY > 0 && !canChildScrollUp()) {
                    return true;
                }
                break;
            default:
        }

        return super.onInterceptTouchEvent(ev);
    }

    public boolean canChildScrollUp() {
        if (Build.VERSION.SDK_INT < 14) {
            if (mTargetView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mTargetView;
                return absListView.getChildCount() > 0 &&
                        (absListView.getFirstVisiblePosition() > 0 ||
                                absListView.getChildAt(0).getTop() <
                                        absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(mTargetView, -1) || mTargetView.getScaleY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mTargetView, -1);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(@NonNull View view, int i) {
            return mTargetView == view;
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            top = handleEdge(top);
            return top;
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            if (mTargetView.getTop() >= mTargetViewHeight / 2) {
                isMenuOpen = true;
                mViewDragHelper.smoothSlideViewTo(releasedChild, 0, mTargetViewHeight);
//                mViewDragHelper.settleCapturedViewAt(0,mTargetViewHeight);
            } else {
                isMenuOpen = false;
                mViewDragHelper.smoothSlideViewTo(releasedChild, 0, 0);
//                mViewDragHelper.settleCapturedViewAt(0,0);
            }
            invalidate();
        }
    };

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    private int handleEdge(int top) {
        if (top > mTargetViewHeight) {
            top = mTargetViewHeight;
        }

        if (top <= 0) {
            top = 0;
        }
        return top;
    }
}
