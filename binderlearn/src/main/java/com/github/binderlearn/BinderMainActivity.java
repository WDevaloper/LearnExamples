package com.github.binderlearn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class BinderMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder_main);
    }

    public native String stringFromJNI();
}
