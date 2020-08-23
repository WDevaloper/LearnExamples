package com.github.skin_core;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

public class SkinMainActivity extends AppCompatActivity {

    // LayoutInflater.from(this); 有主题
    // LayoutInflater.from(getApplicationContext()); 无主题
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("tag", "1111111111");
        setContentView(R.layout.activity_skin_main);
        Log.e("tag", "2222222222");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        Log.e("tag", "" + name);
        return super.onCreateView(name, context, attrs);
    }
}
