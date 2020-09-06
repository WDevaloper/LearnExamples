package com.example.widget_define;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class ScrollChildSwipeRefreshLayout extends SwipeRefreshLayout {
    public View scrollUpChild;

    public ScrollChildSwipeRefreshLayout(@NonNull Context context) {
        super(context);
    }

    public ScrollChildSwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean canChildScrollUp() {
        if (scrollUpChild != null) {
            return scrollUpChild.canScrollVertically(-1);
        }
        return super.canChildScrollUp();
    }
}


