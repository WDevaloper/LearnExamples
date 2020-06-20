package com.github.glide.resource;

import android.graphics.Bitmap;

import com.github.glide.Key;

public class Resource {

    private Bitmap bitmap;

    // 引用计数
    private volatile int acquired;

    private ResourceListener mResourceListener;

    private Key key;

    public interface ResourceListener {
        void onResourceReleased(Key key, Resource resource);
    }

    public void setResourceListener(Key key, ResourceListener listener) {
        this.mResourceListener = listener;
        this.key = key;
    }

    /**
     * 释放
     */
    public synchronized void recycle() {
        if (acquired > 0) {
            return;
        }
        if (!bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }

    public synchronized void release() {
        if (--acquired == 0) {
            mResourceListener.onResourceReleased(key, this);
        }
    }


    public void acquire() {
        if (bitmap.isRecycled()) {
            throw new RuntimeException("acquired a recycled resource");
        }
        synchronized (this) {
            ++acquired;
        }
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
