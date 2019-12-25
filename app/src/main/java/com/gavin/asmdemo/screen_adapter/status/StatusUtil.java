package com.gavin.asmdemo.screen_adapter.status;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

public class StatusUtil {

    public static void setTranslucentStatus(Activity activity) {
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


    /**
     * 通过在状态栏下，添加一个和状态栏高度一致的View，并把contentView的MarginTop调整为状态栏高度
     *
     * @param activity
     * @param toolBar
     */
    public static void setStatusView(Activity activity, View toolBar) {
        setTranslucentStatus(activity);

        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();

        // 避免重复添加StatusView
        int childCount = decorView.getChildCount();
        if (childCount > 0 && decorView.getChildAt(childCount - 1) instanceof StatusView) {
            decorView.getChildAt(childCount - 1).setBackgroundColor(Color.argb(0, 0, 0, 0));
            return;
        }

        StatusView statusView = new StatusView(activity);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusHeight(activity));
        statusView.setBackgroundColor(Color.argb(0, 0, 0, 0));
        decorView.addView(statusView, layoutParams);
        if (toolBar != null) {
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) toolBar.getLayoutParams();
            lp.setMargins(0, getStatusHeight(activity), 0, 0);
        }
    }


    /**
     * 通过修改PaddingTop高度和状态栏一致
     *
     * @param activity
     * @param toolBar
     */
    public static void setStatusViewPaddingTop(Activity activity, View toolBar) {
        setTranslucentStatus(activity);
        if (toolBar != null) {
            toolBar.setPadding(toolBar.getLeft(), toolBar.getPaddingTop() + getStatusHeight(activity), toolBar.getPaddingRight(), toolBar.getPaddingBottom());
        }
    }

    private static int getStatusHeight(Context context) {
        int identifier = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(identifier);
    }

}
