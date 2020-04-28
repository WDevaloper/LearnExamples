package com.gavin.asmdemo.bottom_behavior;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.gavin.asmdemo.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.concurrent.locks.ReentrantLock;

public class BottomMainActivity extends AppCompatActivity implements View.OnClickListener {
    private static String TAG = "tag";

    private Button btnExpand;
    private Button btnCollapsed;
    private Button btnHide;
    private Button btnBottomsheetDialog;
    private Button btnBottomsheetDialogFragment;
    private LinearLayout llContentBottomSheet;

    private BottomSheetBehavior bottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_main);
        initViews();

        ReentrantLock reentrantLock = new ReentrantLock();
        reentrantLock.lock();
        reentrantLock.unlock();
    }


    private void initViews() {
        btnExpand = findViewById(R.id.btn_expand);
        btnCollapsed = findViewById(R.id.btn_collapsed);
        btnHide = findViewById(R.id.btn_hide);
        btnBottomsheetDialog = findViewById(R.id.btn_bottomsheet_dialog);
        btnBottomsheetDialogFragment = findViewById(R.id.btn_bottomsheet_dialog_fragment);
        llContentBottomSheet = findViewById(R.id.ll_content_bottom_sheet);

        btnExpand.setOnClickListener(this);
        btnCollapsed.setOnClickListener(this);
        btnHide.setOnClickListener(this);
        btnBottomsheetDialogFragment.setOnClickListener(this);
        btnBottomsheetDialog.setOnClickListener(this);

        /*获取behavior 控制bottomsheet的 显示 与隐藏*/
        bottomSheetBehavior = BottomSheetBehavior.from(llContentBottomSheet);
        /*bottomSheet 的 状态改变 根据不同的状态 做不同的事情*/
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                switch (newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        Log.e(TAG, "STATE_COLLAPSED 折叠");
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        Log.e(TAG, "STATE_DRAGGING 过渡状态");
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        Log.e(TAG, "STATE_EXPANDED 完全展开");
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        Log.e(TAG, "STATE_HIDDEN 隐藏状态");
                        btnExpand.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (bottomSheetBehavior != null
                                        && bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                                }
                            }
                        }, 3000);
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        Log.e(TAG, "STATE_SETTLING 自由滑动");
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                /*slideOffset bottomSheet 的 移动距离*/
                Log.e(TAG, "slideOffset------>" + slideOffset);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_expand://展开
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            case R.id.btn_collapsed://折叠 是显示刚开始预设的高度 也就是app:behavior_peekHeight这个实行的值
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                break;
            case R.id.btn_hide://隐藏 就是完全隐藏
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                break;
        }
    }
}
