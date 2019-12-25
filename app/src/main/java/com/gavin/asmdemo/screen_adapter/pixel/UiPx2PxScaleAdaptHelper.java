package com.gavin.asmdemo.screen_adapter.pixel;

import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author wfy
 */
public class UiPx2PxScaleAdaptHelper {

    /**
     * 需注意的是设计图的尺寸的比例是1:1 即：1dp = 1px = 1sp
     * <p>
     * 字体适配，像素为单位
     * <p>
     * 布局中也需要以px为单位
     *
     * @param view TextView
     * @param size px
     */
    public static void setTextSizePx(TextView view, float size) {
        int adaptResult = UiPx2PxScaleAdapt.adapt(view.getContext()).getHorizontalAdaptResult((int) size);
        int adaptResult2 = UiPx2PxScaleAdapt.adapt(view.getContext()).getVerticalAdaptResult((int) size);
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, adaptResult);
    }


    /**
     * 实验证明字体的缩放比例选择水平或者垂直方向中最大的最为字体缩放比例，最为准确
     * <p>
     * 字体适配，像素为单位
     * <p>
     * 布局中也需要以px为单位
     *
     * @param view TextView
     * @param size px
     */
    @Deprecated()
    public static void setTextSizePx2(TextView view, float size) {
        int adaptResult = UiPx2PxScaleAdapt.adapt(view.getContext()).getHorizontalAdaptResult((int) size);
        int adaptResult2 = UiPx2PxScaleAdapt.adapt(view.getContext()).getVerticalAdaptResult((int) size);
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, Math.max(adaptResult, adaptResult2));
    }


    /**
     * 手动适配,代码中指定
     *
     * @param isRequestLayout 是否重新执行绘制流程，如果你的UI已经完成加载过程，需要传入true，重新测量绘制，
     *                        如果是没有完成加载，建议传入false，性能考虑
     */
    public static void setUiAdaptPx2Px(View dstView, int width, int height, int topMargin,
                                       int bottomMargin, int lefMargin, int rightMargin,
                                       int paddingLeft, int paddingTop, int paddingRight,
                                       int paddingBottom, boolean isRequestLayout) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) dstView.getLayoutParams();

        if (width != ViewGroup.LayoutParams.MATCH_PARENT &&
                width != ViewGroup.LayoutParams.WRAP_CONTENT) {
            layoutParams.width = UiPx2PxScaleAdapt.adapt(dstView.getContext()).getHorizontalAdaptResult(width);
        } else {
            layoutParams.width = width;
        }


        if (height != ViewGroup.LayoutParams.MATCH_PARENT &&
                height != ViewGroup.LayoutParams.WRAP_CONTENT) {
            layoutParams.height = UiPx2PxScaleAdapt.adapt(dstView.getContext()).getVerticalAdaptResult(height);
        } else {
            layoutParams.height = height;
        }

        layoutParams.setMargins(
                UiPx2PxScaleAdapt.adapt(dstView.getContext()).getHorizontalAdaptResult(lefMargin),
                UiPx2PxScaleAdapt.adapt(dstView.getContext()).getVerticalAdaptResult(topMargin),
                UiPx2PxScaleAdapt.adapt(dstView.getContext()).getHorizontalAdaptResult(rightMargin),
                UiPx2PxScaleAdapt.adapt(dstView.getContext()).getVerticalAdaptResult(bottomMargin));

        if (isRequestLayout) {
            //如果你调用setLayoutParams，就会发生重新绘制，这里需要区分一下
            //
            dstView.setLayoutParams(layoutParams);
        }


        dstView.setPadding(
                UiPx2PxScaleAdapt.adapt(dstView.getContext()).getHorizontalAdaptResult(paddingLeft),
                UiPx2PxScaleAdapt.adapt(dstView.getContext()).getVerticalAdaptResult(paddingTop),
                UiPx2PxScaleAdapt.adapt(dstView.getContext()).getHorizontalAdaptResult(paddingRight),
                UiPx2PxScaleAdapt.adapt(dstView.getContext()).getVerticalAdaptResult(paddingBottom));

    }


    /**
     * 手动适配
     * <p>
     * 在布局中指定尺寸的
     *
     * @param dstViews 指定View
     */
    public static void setUiAdaptPx2PxLayout(View... dstViews) {
        if (dstViews.length <= 0) {
            return;
        }

        for (View dstView : dstViews) {
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) dstView.getLayoutParams();
            if (layoutParams == null) {
                continue;
            }
            setUiAdaptPx2Px(dstView, layoutParams.width, layoutParams.height,
                    layoutParams.topMargin, layoutParams.bottomMargin,
                    layoutParams.leftMargin, layoutParams.rightMargin,
                    dstView.getPaddingLeft(), dstView.getPaddingTop(),
                    dstView.getPaddingRight(), dstView.getPaddingBottom(), false
            );
        }
    }

    /**
     * 自动完成适配
     * <p>
     * <p>
     * 需要注意的是第三方的控件，会造成影响，解决方案是使用手动指定大小的方式
     * <p>
     * <p>
     * <p>
     * 把适配放到Base中自动完成适配 指定的是开发者定义的布局的根布局
     *
     * @param contentView 或者开发者在xml中定义的根布局
     */
    public static void setUiAdaptPx2PxContentView(@NonNull View contentView) {
        if (!(contentView instanceof ViewGroup)) {
            setUiAdaptPx2PxLayout(contentView);
            return;
        }

        // 修改ViewGroup本身的
        ViewGroup viewGroup = (ViewGroup) contentView;
        ViewGroup.MarginLayoutParams vplp = (ViewGroup.MarginLayoutParams) viewGroup.getLayoutParams();
        setUiAdaptPx2Px(viewGroup, vplp.width, vplp.height,
                vplp.topMargin, vplp.bottomMargin,
                vplp.leftMargin, vplp.rightMargin, viewGroup.getPaddingLeft(), viewGroup.getPaddingTop(),
                viewGroup.getPaddingRight(), viewGroup.getPaddingBottom(), false
        );

        // 修改child本身的
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = viewGroup.getChildAt(i);
            setUiAdaptPx2PxContentView(child);
        }
    }
}
