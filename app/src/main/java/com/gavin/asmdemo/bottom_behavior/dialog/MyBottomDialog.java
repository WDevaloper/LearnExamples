package com.gavin.asmdemo.bottom_behavior.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.gavin.asmdemo.uitls.SystemUtil;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

//@string/bottom_sheet_behavior:com.google.android.material.bottomsheet.BottomSheetBehavior
//BottomSheetDialog: com.google.android.material.bottomsheet.BottomSheetBehavior
public class MyBottomDialog extends BottomSheetDialog {

    private static String TAG = "bottom";

    private BottomSheetBehavior behavior;

    public MyBottomDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public void setContentView(int layoutResId) {
        Log.e(TAG, "setContentView");
        View view = LayoutInflater.from(getContext()).inflate(layoutResId, null, false);
        setContentView(view);
    }


    //实际上是ContentView设置到FrameLayout中
    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        initialize(view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /*
    <FrameLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/container"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:fitsSystemWindows="true">

  <androidx.coordinatorlayout.widget.CoordinatorLayout
      android:id="@+id/coordinator"
      android:layout_width="match_parent"
      android:layout_height="match_parent"
      android:fitsSystemWindows="true">

    <View
        android:id="@+id/touch_outside"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:importantForAccessibility="no"
        android:soundEffectsEnabled="false"
        tools:ignore="UnusedAttribute"/>

    <FrameLayout
        android:id="@+id/design_bottom_sheet"
        style="?attr/bottomSheetStyle"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_gravity="center_horizontal|top"
        app:layout_behavior="@string/bottom_sheet_behavior"/>

  </androidx.coordinatorlayout.widget.CoordinatorLayout>

</FrameLayout>
     */

    private void initialize(final View view) {
        Log.e(TAG, "initialize");
        ViewGroup parent = (ViewGroup) view.getParent();//实际上是拿到design_bottom_sheet即：FrameLayout
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) parent.getLayoutParams();
        behavior = (BottomSheetBehavior) params.getBehavior();
        assert behavior != null;

        ViewGroup.LayoutParams layoutParams = parent.getLayoutParams();
        layoutParams.height = (int) (SystemUtil.getScreenHeight(getContext()) * 0.9);//一定要设置Dialog的高度，否则会出现Dialog只显示一点点
        parent.setLayoutParams(layoutParams);

        behavior.setPeekHeight((int) (SystemUtil.getScreenHeight(getContext()) * 0.3));
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    dismiss();
                }
                switch (newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED://折叠 是显示刚开始预设的高度 也就是app:behavior_peekHeight这个实行的值
                        Log.d(TAG, "STATE_COLLAPSED 折叠");
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        Log.d(TAG, "STATE_DRAGGING 过渡状态");
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        Log.d(TAG, "STATE_EXPANDED 完全展开");
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        Log.d(TAG, "STATE_HIDDEN  隐藏状态");
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        Log.d(TAG, "STATE_SETTLING 自由滑动");
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
    }

}
