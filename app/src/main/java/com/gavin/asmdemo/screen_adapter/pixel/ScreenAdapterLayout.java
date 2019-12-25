package com.gavin.asmdemo.screen_adapter.pixel;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class ScreenAdapterLayout extends RelativeLayout {

    public ScreenAdapterLayout(Context context) {
        super(context);
    }

    public ScreenAdapterLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScreenAdapterLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private boolean isAdapted = false;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!isAdapted) {
            float scaleX = UiPx2PxScaleAdapt.adapt(getContext()).getHorizontalScale();
            float scaleY = UiPx2PxScaleAdapt.adapt(getContext()).getVerticalScale();
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                lp.width = Math.round(lp.width * scaleX);
                lp.height = Math.round(lp.height * scaleY);


                lp.leftMargin = Math.round(lp.leftMargin * scaleX);
                lp.rightMargin = Math.round(lp.rightMargin * scaleX);
                lp.topMargin = Math.round(lp.topMargin * scaleY);
                lp.bottomMargin = Math.round(lp.bottomMargin * scaleY);

                int left = Math.round(child.getLeft() * scaleX);
                int top = Math.round(child.getTop() * scaleY);
                int right = Math.round(child.getRight() * scaleX);
                int bottom = Math.round(child.getBottom() * scaleY);

                child.setPadding(left, top, right, bottom);
            }
            isAdapted = true;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        super.setLayoutParams(params);
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(left, top, right, bottom);
    }
}
