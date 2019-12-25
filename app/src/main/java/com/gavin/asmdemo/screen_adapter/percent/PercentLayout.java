package com.gavin.asmdemo.screen_adapter.percent;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.gavin.asmdemo.R;

public class PercentLayout extends RelativeLayout {

    public PercentLayout(Context context) {
        super(context);
    }

    public PercentLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PercentLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            ViewGroup.LayoutParams params = child.getLayoutParams();

            //如果是百分比布局属性
            if (checkLayoutParams(params)) {
                LayoutParam lp = (LayoutParam) params;
                float mLayoutHeightPercent = lp.mLayoutHeightPercent;
                float mLayoutWidthPercent = lp.mLayoutWidthPercent;
                float mLayoutMarginLeftPercent = lp.mLayoutMarginLeftPercent;
                float mLayoutMarginRightPercent = lp.mLayoutMarginRightPercent;
                float mLayoutMarginTopPercent = lp.mLayoutMarginTopPercent;
                float mLayoutMarginBottomPercent = lp.mLayoutMarginBottomPercent;

                if (mLayoutHeightPercent > 0) {
                    params.height = (int) (heightSize * mLayoutHeightPercent);
                }

                if (mLayoutWidthPercent > 0) {
                    params.width = (int) (widthSize * mLayoutWidthPercent);
                }

                if (mLayoutMarginLeftPercent > 0) {
                    ((LayoutParam) params).leftMargin = (int) (widthSize * mLayoutMarginLeftPercent);
                }

                if (mLayoutMarginRightPercent > 0) {
                    ((LayoutParam) params).rightMargin = (int) (widthSize * mLayoutMarginRightPercent);
                }

                if (mLayoutMarginTopPercent > 0) {
                    ((LayoutParam) params).topMargin = (int) (heightSize * mLayoutMarginTopPercent);
                }

                if (mLayoutMarginTopPercent > 0) {
                    ((LayoutParam) params).bottomMargin = (int) (heightSize * mLayoutMarginBottomPercent);
                }
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParam(getContext(), attrs);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParam;
    }

    // 通过查看setContentView的源码，得知，child的布局属性都是根据父容器创建的。
    //为了保证还能够使用RelativeLayout的布局属性，所以需继承RelativeLayout.LayoutParams
    public static class LayoutParam extends RelativeLayout.LayoutParams {

        private float mLayoutHeightPercent;
        private float mLayoutWidthPercent;
        private float mLayoutMarginLeftPercent;
        private float mLayoutMarginRightPercent;
        private float mLayoutMarginTopPercent;
        private float mLayoutMarginBottomPercent;

        @SuppressLint("CustomViewStyleable")
        public LayoutParam(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray typedArray = c.obtainStyledAttributes(attrs, R.styleable.MyPercentLayout);
            mLayoutHeightPercent = typedArray.getFloat(R.styleable.MyPercentLayout_layout_heightPercent2, 0f);
            mLayoutWidthPercent = typedArray.getFloat(R.styleable.MyPercentLayout_layout_widthPercent2, 0f);
            mLayoutMarginLeftPercent = typedArray.getFloat(R.styleable.MyPercentLayout_layout_marginLeftPercent2, 0f);
            mLayoutMarginRightPercent = typedArray.getFloat(R.styleable.MyPercentLayout_layout_marginRightPercent2, 0f);
            mLayoutMarginTopPercent = typedArray.getFloat(R.styleable.MyPercentLayout_layout_marginTopPercent2, 0f);
            mLayoutMarginBottomPercent = typedArray.getFloat(R.styleable.MyPercentLayout_layout_marginBottomPercent2, 0f);
            typedArray.recycle();
        }
    }
}
