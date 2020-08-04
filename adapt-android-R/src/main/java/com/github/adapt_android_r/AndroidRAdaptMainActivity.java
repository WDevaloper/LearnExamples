package com.github.adapt_android_r;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


public class AndroidRAdaptMainActivity extends AppCompatActivity {
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_androdi_r_adapt_main);
        image = findViewById(R.id.image);
    }

    public void insert(View view) {
        ContentValues values = new ContentValues();
    }

    public void query(View view) {

    }

    // 如果不是自己的图片那么需要申请权限
    public void update(View view) {

    }

    // 如果不是自己的图片那么需要申请权限
    public void delete(View view) {

    }
}
