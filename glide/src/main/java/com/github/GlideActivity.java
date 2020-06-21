package com.github;

import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.LruCache;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.glide.R;
import com.github.glide.load.LoaderTest;

public class GlideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        LruCache<String, String> lruCache = new LruCache<>(2);

        Glide.with(this)
                .load("")
                .into(new ImageView(this));

        new Thread() {
            @Override
            public void run() {

                new LoaderTest()
                        .test(GlideActivity.this);
            }
        }.start();

    }
}
