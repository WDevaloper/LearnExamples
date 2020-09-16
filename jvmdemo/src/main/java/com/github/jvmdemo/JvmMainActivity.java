package com.github.jvmdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import java.util.HashMap;

public class JvmMainActivity extends AppCompatActivity {

    private static final String TAG = "touch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jvm_main);
    }


    @Override

    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG, "dispatchTouchEvent activity", null);
        return super.dispatchTouchEvent(ev);
    }

    class Test extends HashMap<String, String> {

    }
}
