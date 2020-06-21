package com.github.glide.reuseable;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;

public interface BitmapPool {

    void put(Bitmap bitmap);

    Bitmap get(int width, int height, Bitmap.Config config);

    @SuppressLint("ObsoleteSdkInt")
    int getSize(Bitmap bitmap);
}
