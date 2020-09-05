package com.example.widget_define;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TimeAxisItemDecoration extends RecyclerView.ItemDecoration {
    private Paint LinePaint;
    private Paint TextPaint;
    private Paint PointPaint;
    private List<String> dynasty;
    private int divide_width = 66;

    public TimeAxisItemDecoration(Context context, List<String> dynasty) {
        LinePaint = new Paint();
        TextPaint = new Paint();
        PointPaint = new Paint();
        PointPaint.setStyle(Paint.Style.FILL);
        PointPaint.setStrokeWidth(8);
        PointPaint.setStrokeCap(Paint.Cap.ROUND);
        PointPaint.setColor(Color.RED);
        LinePaint.setColor(Color.RED);
        TextPaint.setColor(Color.RED);
        this.dynasty = dynasty;
    }

    //下方
    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int childCount = parent.getChildCount();
        RecyclerView.LayoutManager manager = parent.getLayoutManager();//为了动态获取
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            assert manager != null;
            // 求圆心
            int cx = manager.getLeftDecorationWidth(child) / 2;
            int cy = child.getTop() - 8;
            float circleRadius = 14;
            int TextSize = 32;

            LinePaint.setStrokeWidth((float) 2.0);
            LinePaint.setStyle(Paint.Style.STROKE);

            c.drawCircle(cx, cy, circleRadius, LinePaint);//圆环
            c.drawPoint(cx, cy, PointPaint);//圆点
            c.drawLine(cx, child.getTop(), cx, child.getBottom() + child.getHeight(), LinePaint);//线

            TextPaint.setTextSize(TextSize);
        }
    }

    //上方
    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        RecyclerView.LayoutManager manager = parent.getLayoutManager();//为了动态获取
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int cx = manager.getLeftDecorationWidth(child);
            int index = parent.getChildAdapterPosition(child);
            if (index < dynasty.size())
                c.drawText(dynasty.get(index), cx + 8, child.getTop() - 8, TextPaint);
        }
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            Object itemView = state.get(123);
            state.put(1234,view);
            outRect.set(divide_width, divide_width, divide_width / 2, 0);
        }

        //outRect outRect就是表示在item的上下左右所撑开的距离
        //View view:是指当前Item的View对象
        //RecyclerView parent： 是指RecyclerView 本身
        // RecyclerView.State state:通过State可以获取当前RecyclerView的状态，也可以通过State在RecyclerView各组件间传递参数
    }
}
