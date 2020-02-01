package com.gavin.asmdemo.widget.recycler;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class RecyclerView extends ViewGroup {
    private Adapter adapter;

    private List<View> viewList;//当前显示View

    private int currentY;// 当前滑动的Y值
    private int rowCount;//行数
    private int firstRow;//view的第一行，是占内容的几行
    private int scrollY;//y的偏移来那个


    private boolean needRelayout;


    private int width;

    private int height;

    private int[] heights;//item高度

    private Recycler recycler;

    private int touchSlop;


    public Adapter getAdapter() {
        return adapter;
    }

    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
    }


    public RecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        ViewConfiguration configuration = ViewConfiguration.get(context);
        this.touchSlop = configuration.getScaledTouchSlop();
        this.viewList = new ArrayList<>();
        this.needRelayout = true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public void scrollBy(int x, int y) {
        super.scrollBy(x, y);
    }

    interface Adapter {
        View onCreateViewHolder(int position, View contentView, ViewGroup parent);

        View onBindViewHolder(int position, View contentView, ViewGroup parent);

        int getItemViewType(int position);

        int getViewTypeCount(int position);

        int getCount();

        int getHeight();
    }
}
