package com.github.glide;

import android.graphics.Bitmap;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.LruCache;

import com.github.glide.resource.Resource;

public class LruMemoryCache extends LruCache<Key, Resource> implements MemoryCache {
    private ResourceRemoveListener removeListener;

    public LruMemoryCache(int maxSize) {
        super(maxSize);
    }

    @Override
    protected int sizeOf(@NonNull Key key, @NonNull Resource value) {
        Bitmap bitmap = value.getBitmap();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return bitmap.getAllocationByteCount();
        }
        int byteCount = 2;
        Bitmap.Config config = bitmap.getConfig();
        if (config == Bitmap.Config.ARGB_8888) {
            byteCount = 4;
        }
        return bitmap.getByteCount() * byteCount;
    }

    @Override
    protected void entryRemoved(boolean evicted, @NonNull Key key, @NonNull Resource oldValue, @Nullable Resource newValue) {
        super.entryRemoved(evicted, key, oldValue, newValue);
        if (removeListener != null) {
            removeListener.onResourceRemoved(oldValue);
        }

    }

    @Override
    public void setResourceRemoveListener(ResourceRemoveListener removeListener) {
        this.removeListener = removeListener;
    }
}
