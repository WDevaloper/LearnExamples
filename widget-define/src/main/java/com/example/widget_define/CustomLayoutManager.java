package com.example.widget_define;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.recyclerview.widget.RecyclerView.HORIZONTAL;


/**
 * 自定义 RecyclerView.layoutManager  OrientationHelper
 */
public class CustomLayoutManager extends RecyclerView.LayoutManager {

    private int orientation = RecyclerView.VERTICAL;
    private int visibleCount = 15;

    /**
     * 1、 继承RecyclerView.LayoutManager并实现generateDefaultLayoutParams()方法。
     */
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        if (orientation == HORIZONTAL) {
            return new RecyclerView.LayoutParams(
                    RecyclerView.LayoutParams.WRAP_CONTENT,
                    RecyclerView.LayoutParams.MATCH_PARENT
            );
        }

        return new RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
        );
    }

    private int mItemWidth;
    private int mItemHeight;

    /**
     * 2、按需，重写onMeasure()或isAutoMeasureEnabled()方法。
     */
    @Override
    public void onMeasure(@NonNull RecyclerView.Recycler recycler, @NonNull RecyclerView.State state, int widthSpec, int heightSpec) {
        if (state.getItemCount() == 0) {
            super.onMeasure(recycler, state, widthSpec, heightSpec);
            return;
        }

        if (state.isPreLayout()) return;


        View itemView = recycler.getViewForPosition(0);
        addView(itemView);//只有添加到ViewRootImpl，才会执行绘制流程
        itemView.measure(widthSpec, heightSpec);

        mItemWidth = getDecoratedMeasuredWidth(itemView);
        mItemHeight = getDecoratedMeasuredHeight(itemView);


        detachAndScrapView(itemView, recycler);


        if (orientation == HORIZONTAL) {
            setMeasuredDimension(mItemWidth * visibleCount, mItemHeight);
        } else {
            setMeasuredDimension(mItemWidth, mItemHeight * visibleCount);
        }

    }


    @Override
    public boolean isAutoMeasureEnabled() {
        return true;
    }

    /**
     * 3、重写onLayoutChildren()开始第一次填充itemView。
     * <p>
     * <p>
     * 7、解决软键盘弹出或收起导致onLayoutChildren()方法被重新调用的问题。
     */
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
    }


    /***
     * 4、重写canScrollHorizontally()和canScrollVertically()方法支持滑动。
     */
    @Override
    public boolean canScrollHorizontally() {
        return super.canScrollHorizontally();
    }

    @Override
    public boolean canScrollVertically() {
        return super.canScrollVertically();
    }

    /**
     * 5、重写scrollHorizontallyBy()和scrollVerticallyBy()方法在滑动的时候填充和回收itemView。
     */
    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return super.scrollHorizontallyBy(dx, recycler, state);
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return super.scrollVerticallyBy(dy, recycler, state);
    }


    /**
     * 6、重写scrollToPosition()和smoothScrollToPosition()方法支持。
     *
     * @param position
     */
    @Override
    public void scrollToPosition(int position) {
        super.scrollToPosition(position);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        super.smoothScrollToPosition(recyclerView, state, position);
    }
}
