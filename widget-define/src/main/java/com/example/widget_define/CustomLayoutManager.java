package com.example.widget_define;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;


/**
 * 自定义 RecyclerView.layoutManager  OrientationHelper
 */
public class CustomLayoutManager extends RecyclerView.LayoutManager {

    /**
     * 1、 继承RecyclerView.LayoutManager并实现generateDefaultLayoutParams()方法。
     */
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return null;
    }


    /**
     * 2、按需，重写onMeasure()或isAutoMeasureEnabled()方法。
     */
    @Override
    public void onMeasure(@NonNull RecyclerView.Recycler recycler, @NonNull RecyclerView.State state, int widthSpec, int heightSpec) {
        super.onMeasure(recycler, state, widthSpec, heightSpec);
    }


    @Override
    public boolean isAutoMeasureEnabled() {
        return super.isAutoMeasureEnabled();
    }

    /**
     * 3、重写onLayoutChildren()开始第一次填充itemView。
     *
     *
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
