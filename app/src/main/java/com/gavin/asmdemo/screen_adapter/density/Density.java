package com.gavin.asmdemo.screen_adapter.density;

import android.app.Activity;
import android.app.Application;
import android.util.DisplayMetrics;
import android.util.Log;

//为了解决对第三方库的适配的影响，我们可以取消Density适配，然后使用自定义像素适配
//当前设备屏幕总宽度（单位为像素）/ 设计图总宽度（单位为 dp) = density
// dpi = density * 160
// px = density * dp
// dp = density / px

// img:50dp * 50dp

// 720 / 320 = 2.25
// 2.25 * 50 = 112.5px

// 1080 / 320 = 3.375
// 3.375 * 50 = 168.75px

// 比例相等
// 168.75 / 112.5 = 1.5
// 1080 / 720 = 1.5
public class Density {

    private static float WIDTH = 375; //参考设备的宽，单位是dp 1440dp / 2 = 187.5dp  居中
    private static float appDensity = 0f;
    private static float appScaleDensity = 0f;
    private static int appDensityDpi = 0;

    public static void adaptDensity(Application app, Activity activity) {
        //获取当前app的屏幕显示信息
        DisplayMetrics appMetrics = app.getResources().getDisplayMetrics();
        if (appDensity == 0) {
            appDensity = appMetrics.density;
            appScaleDensity = appMetrics.scaledDensity;
            appDensityDpi = appMetrics.densityDpi;
        }

        Log.e("TAG", appMetrics.widthPixels + "  " + appMetrics.density);

        //计算目标值density, scaleDensity, densityDpi
        float targetDensity = appMetrics.widthPixels / WIDTH;
        // 默认 density 和 scaledDensity 相等
        float targetScaleDensity = targetDensity * (appScaleDensity / appDensity);
        //dpi = density * 160
        int targetDensityDpi = (int) (targetDensity * 160);

        Log.e("TAG", "" + targetDensity);


        //替换Activity的density, scaleDensity, densityDpi
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        displayMetrics.density = targetDensity;
        displayMetrics.scaledDensity = targetScaleDensity;
        displayMetrics.densityDpi = targetDensityDpi;

        Log.e("TAG", displayMetrics.widthPixels + "  " + displayMetrics.density);

    }


    /**
     * 为了解决对第三方库的适配的影响，我们可以取消Density适配，然后使用自定义像素适配
     * <p>
     * 取消density适配
     *
     * @param activity
     */
    public static void cancelAdaptDensity(Activity activity) {
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        displayMetrics.density = appDensity;
        displayMetrics.scaledDensity = appScaleDensity;
        displayMetrics.densityDpi = appDensityDpi;
    }
}
