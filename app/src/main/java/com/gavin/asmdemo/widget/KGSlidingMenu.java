package com.gavin.asmdemo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import com.gavin.asmdemo.R;

public class KGSlidingMenu extends HorizontalScrollView {

    private static final int DEFAULT_VELOCITY_X = 800;

    private boolean isInterceptor = false;
    private GestureDetector mGestureDetector;

    private static final String TAG = "tag";
    private boolean isMenuOpened = false;
    private View mMenuView;
    private View mContentView;
    private int mMenuWidth;
    private int menuOpenContentWidth;

    public KGSlidingMenu(Context context) {
        this(context, null);
    }

    public KGSlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KGSlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mGestureDetector = new GestureDetector(getContext(), new MySimpleOnGestureListener());
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.KGSlidingMenu);
        menuOpenContentWidth = ta.getDimensionPixelSize(R.styleable.KGSlidingMenu_menuOpenContentWidth,dip2px(getContext(), 30f));
        ta.recycle();
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ViewGroup container = (ViewGroup) getChildAt(0);
        int childCount = container.getChildCount();
        if (childCount != 2) {
            throw new RuntimeException("只能由两个child");
        }
        mMenuView = container.getChildAt(0);
        mContentView = container.getChildAt(1);
        mMenuWidth = getScreenWidth(getContext()) - menuOpenContentWidth;
        ViewGroup.LayoutParams menuLayoutParams = mMenuView.getLayoutParams();
        menuLayoutParams.width = mMenuWidth;
        mMenuView.setLayoutParams(menuLayoutParams);

        ViewGroup.LayoutParams mContentViewLayoutParams = mContentView.getLayoutParams();
        mContentViewLayoutParams.width = getScreenWidth(getContext());
        mContentView.setLayoutParams(mContentViewLayoutParams);


        // 因为不能拿到Parent宽度和child的宽度，所以不能滚动
//        scrollTo(mMenuWidth, 0);
    }


    class MySimpleOnGestureListener extends GestureDetector.SimpleOnGestureListener {

        /**
         * 快速滑动回调
         *
         * @param e1
         * @param e2
         * @param velocityX 水平方向上的速度，往左滑动负数
         * @param velocityY 垂直方向上的速度
         * @return
         */
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.e(TAG, "onFling: " + velocityX);
            if (isMenuOpened) {
                if (velocityX < -DEFAULT_VELOCITY_X) {
                    closeMenu();
                    return true;//需注意的是要返回true
                }
            } else {
                if (velocityX > DEFAULT_VELOCITY_X) {
                    openMenu();
                    return true;//需注意的是要返回true
                }
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }

    //当菜单打开的情况下，点击右边即content时，需关闭菜单，但是不能响应content中的点击事件
    // 注意：当返回true时表示拦截事件会回调onTouchEvent
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        isInterceptor = false;
        if (ev.getX() > mMenuWidth) {
            isInterceptor = true;
            closeMenu();
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //快速滑动需切换，如果返回true，需结束，不然下面会影响事件的处理，当然你也可以，把该段代码放到下面代码之后
        if (mGestureDetector.onTouchEvent(ev)) {
            return true;
        }

        if (isInterceptor) {
            return true;
        }

        if (ev.getAction() == MotionEvent.ACTION_UP) {
//            Log.e("tag", "onTouchEvent: " + getScrollX() + "  " + mMenuWidth / 2);
            if (getScrollX() > mMenuWidth / 2) {
                closeMenu();
                return true;
            } else {
                openMenu();
                return true;
            }
        }
        return super.onTouchEvent(ev);//存在filling事件的处理，造成smoothScrollBy失效
    }


    /**
     * 各种效果
     *
     * @param l
     * @param t
     * @param oldl
     * @param oldt
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        float scale = l * 1f / mMenuWidth; // 0 - 1
//        Log.e(TAG, "onScrollChanged: " + scale + " " + l);
        float rightScale = 0.7f + scale * 0.3f;//最小0.7

        mContentView.setPivotX(0);
        mContentView.setPivotY(mContentView.getHeight() / 2);
        mContentView.setScaleX(rightScale);
        mContentView.setScaleY(rightScale);


        // 0.7 - 1
        float alpha = 0.7f + (1 - scale) * 0.3f;
        mMenuView.setAlpha(alpha);

        float lifeScale = 0.8f + (1 - scale) * 0.2f;//最小0.7
        mMenuView.setPivotY(mContentView.getHeight() / 2);
        mMenuView.setScaleY(lifeScale);

        //平移
//        mMenuView.setTranslationX(scale* mMenuWidth * 0.2f);
    }

    private void closeMenu() {
        isMenuOpened = false;
        smoothScrollTo(mMenuWidth, 0);
    }

    private void openMenu() {
        isMenuOpened = true;
        smoothScrollTo(0, 0);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        // 滚动到 contentView处
        scrollTo(mMenuWidth, 0);
    }

    private int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }


    private int dip2px(Context context, float dpValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }
}
