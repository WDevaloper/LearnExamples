package com.gavin.asmdemo.screen_adapter.pixel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

@SuppressLint("StaticFieldLeak")
public class UiPx2PxScaleAdapt {
    /**
     * 适配方案：
     * 1、自定义像素是最好的适配方式，能够满足同时适配水平和垂直两个方向；
     * 2、而修改density这种适配方案，只能同时适配(水平或垂直)方向；
     * 3、当然可以使用百分比布局适配方案；
     * 4、最小宽度适配方案；
     * <p>
     * <p>
     * 为了解决对第三方库的适配的影响，我们可以取消Density适配，然后使用自定义像素适配
     * <p>
     * 还是那句话，不管你的单位是dp还是其他最终都会被转换为px
     * <p>
     * 这里是设计稿参考宽高;
     * <p>
     * <p>
     * 假如你当前屏幕宽度是720px上显示在一半：    720px / 720px *    360px     = 360px；
     * 假如你当前屏幕宽度是1080px上显示在一半：  1080px / 720px *    360px     = 540px；
     * 假如你当前屏幕宽度是1920px上显示在一半：  1920px / 720px *    360px     = 690px；
     * 假如你当前屏幕宽度是1440px上显示在一半：  1440px / 720px *    360px     = 720px；
     * 设计稿的值永远按照720px标准，他会自行缩放
     *
     * <p>
     * <p>
     * <p>
     * px = density ✖ dp  假设设计参考为360dp,当前运行设备屏幕为1080px * 1920px，那么density = px/dp = 1080/360 = 3.0
     * ----------------------------------------------------------------------------------------------------------
     * dp = px / density 屏幕的总 px 宽度 / density = 屏幕的总 dp 宽度
     * 例子：
     * 设备 1，屏幕宽度为 1080px，480DPI，屏幕总 dp 宽度为 1080 / (480 / 160) = 360dp
     * 设备 2，屏幕宽度为 1440，560DPI，屏幕总 dp 宽度为 1440 / (560 / 160) = 411dp
     * ----------------------------------------------------------------------------------------------------------
     * dpi = density * 160
     * px = (dpi / 160)✖dp
     * <p>
     * 我觉得dpi更像是作为一种标准出现。例如Android手机的：160dpi、320dpi、440dpi、480dpi
     * <p>
     * density 在每个设备上都是固定的，dpi / 160 = density，屏幕的总 px 宽度 / density = 屏幕的总 dp 宽度
     *
     * <p>
     * <p>
     * 那么今日头条的适配方案：
     * 1、当前设备屏幕总宽度（单位为像素）/ 设计图总宽度（单位为 dp) = density；
     * 2、density 的表示的就是 1dp 占当前设备多少像素；
     * 3、这个公式就是把上面公式中的 屏幕的总 dp 宽度 换成 设计图总宽度，原理都是一样的，
     * 只要 density 根据不同的设备进行实时计算并作出改变，就能保证 设计图总宽度 不变，也就完成了适配
     */

    private static UiPx2PxScaleAdapt mUIAdaptUtil;

    private static float DEFAULT_STANDARD_WIDTH = 375;//px
    private static float DEFAULT_STANDARD_HEIGHT = 667;

    //设计图参考尺寸
    private static float standardWidth = DEFAULT_STANDARD_WIDTH;//px
    private static float standardHeight = DEFAULT_STANDARD_HEIGHT;

    //这里是屏幕显示宽高
    private static int mDisplayWidth;
    private static int mDisplayHeight;


    private UiPx2PxScaleAdapt(Context context) {
        if (mDisplayWidth == 0 || mDisplayHeight == 0) {
            WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics metrics = new DisplayMetrics();
            manager.getDefaultDisplay().getMetrics(metrics);
            if (metrics.widthPixels > metrics.heightPixels) {//横屏
                mDisplayWidth = metrics.heightPixels;
                mDisplayHeight = metrics.widthPixels - getStatusBarHeight(context);
            } else {
                mDisplayWidth = metrics.widthPixels;
                mDisplayHeight = metrics.heightPixels - getStatusBarHeight(context);//为了精确一点呢，可以把状态栏高度减掉
            }
        }
    }

    public static UiPx2PxScaleAdapt adapt(Context context) {
        if (UiPx2PxScaleAdapt.mUIAdaptUtil == null) {
            synchronized (UiPx2PxScaleAdapt.class) {
                if (UiPx2PxScaleAdapt.mUIAdaptUtil == null) {
                    UiPx2PxScaleAdapt.mUIAdaptUtil = new UiPx2PxScaleAdapt(context.getApplicationContext());
                }
            }
        }
        return mUIAdaptUtil;
    }

    public int getVerticalAdaptResult(int needValuePx) {
        return Math.round((needValuePx * getVerticalScale()));
    }

    public int getHorizontalAdaptResult(int needValuePx) {
        return Math.round(needValuePx * getHorizontalScale());
    }

    /**
     * 修改设计图参考尺寸
     *
     * @param standardWidth 设计图参考宽度 单位px
     */
    public UiPx2PxScaleAdapt standardWidth(float standardWidth) {
        UiPx2PxScaleAdapt.standardWidth = standardWidth;
        return this;
    }


    /**
     * 修改设计图参考尺寸
     *
     * @param standardHeight 设计图参考高度 单位px
     */
    public UiPx2PxScaleAdapt standardHeight(float standardHeight) {
        UiPx2PxScaleAdapt.standardHeight = standardHeight;
        return this;
    }

    /**
     * @return 获取水平方向的缩放比例
     */
    public float getHorizontalScale() {
        return mDisplayWidth / standardWidth;
    }

    /**
     * @return 获取垂直方向的缩放比例
     */
    public float getVerticalScale() {
        return mDisplayHeight / standardHeight;
    }


    private int getStatusBarHeight(Context context) {
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            return context.getResources().getDimensionPixelSize(resId);
        }
        return 0;
    }
}
