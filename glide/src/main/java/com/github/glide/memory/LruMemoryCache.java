package com.github.glide.memory;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.LruCache;

import com.github.glide.Key;
import com.github.glide.resource.Resource;

public class LruMemoryCache extends LruCache<Key, Resource> implements MemoryCache {
    private ResourceRemoveListener removeListener;
    //是否主动移除，超过maxsize，不需要放入复用池
    private boolean isRemoved;

    public LruMemoryCache(int maxSize) {
        super(maxSize);
    }

    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected int sizeOf(@NonNull Key key, @NonNull Resource value) {
        Bitmap bitmap = value.getBitmap();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//API 19
            return bitmap.getAllocationByteCount();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {//API 12
            return bitmap.getByteCount();
        }

        return bitmap.getRowBytes() * bitmap.getHeight(); //earlier version
    }

    @Override
    protected void entryRemoved(boolean evicted, @NonNull Key key, @NonNull Resource oldValue, @Nullable Resource newValue) {
        super.entryRemoved(evicted, key, oldValue, newValue);
        //你需要明白的是，当你从内存缓存移除，可能就是把它放入活动缓存中,那么你就不能执行recycler，即我们主动remove
        // 当内存缓存移除是被LruCache主动移除的，那么有几种情况：
        //第一：当我们往Lru中put时超过maxSize时，lru会主动移除(isRemoved = false)： entryRemoved(true, key, value, null);
        //第二：当我们往Lru中put时有先相同的key时，lru会主动移除并更新(isRemoved = false)：entryRemoved(false, key, previous, value);
        //第三：当我们用户主动remove，那么可能是比较新的数据，如remove并把概述放到活动缓存中(isRemoved = true)
        //第四总结：Lru中主动移除的缓存数据是很老旧的数据，那么我们可以把它放入复用池，那么我们主动移除并放到活动缓存
        // ，就不需要加入复用池中，这样就能够保证该资源在内存中只有一份数据
        if (removeListener != null && !isRemoved) {
            removeListener.onResourceRemoved(oldValue);
        }
    }

    public Resource removeToMemory(Key key) {
        isRemoved = true;
        Resource resource = remove(key);
        isRemoved = false;
        return resource;
    }

    @Override
    public void setResourceRemoveListener(ResourceRemoveListener removeListener) {
        this.removeListener = removeListener;
    }
}
