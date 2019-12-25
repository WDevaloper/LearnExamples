package com.gavin.asmdemo.screen_adapter.immersive_notch;

import android.app.Activity;
import android.os.Bundle;

import com.gavin.asmdemo.R;

/**
 * @Describe: 刘海屏和沉浸式适配
 * @Author: wfy
 */
public class ImmersiveAndNotchActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notcch);
    }
}
