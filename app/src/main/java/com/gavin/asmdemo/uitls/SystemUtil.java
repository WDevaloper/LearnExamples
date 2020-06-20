package com.gavin.asmdemo.uitls;

import android.content.Context;

public class SystemUtil {
    public static int getScreenHeight(Context context) {
        return (context.getResources().getDisplayMetrics().heightPixels);
    }
}
