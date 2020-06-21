package com.github.glide.reuseable;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.LruCache;

import java.util.NavigableMap;
import java.util.TreeMap;

public class LruBitmapPool extends LruCache<Integer, Bitmap> implements BitmapPool {
    NavigableMap<Integer, Integer> map = new TreeMap<>();

    private final static int MAX_OVER_SIZE = 2;

    private boolean isRemoved;

    public LruBitmapPool(int maxSize) {
        super(maxSize);
    }

    @Override
    protected int sizeOf(@NonNull Integer key, @NonNull Bitmap value) {
        return getSize(value);
    }


    @SuppressLint("ObsoleteSdkInt")
    public int getSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//API 19
            return bitmap.getAllocationByteCount();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {//API 12
            return bitmap.getByteCount();
        }

        return bitmap.getRowBytes() * bitmap.getHeight(); //earlier version
    }

    @Override
    protected void entryRemoved(boolean evicted, @NonNull Integer key,
                                @NonNull Bitmap oldValue, @Nullable Bitmap newValue) {
        super.entryRemoved(evicted, key, oldValue, newValue);
        // 当我们用户主动put超过maxSize时，那么就需要Recycler调，即当lru主动移除，那么就需要Recycler掉
        map.remove(key);
        if (!isRemoved) {
            oldValue.recycle();
        }
    }

    /**
     * 将Bitmap放入复用池
     *
     * @param bitmap
     */
    @Override
    public void put(Bitmap bitmap) {
        if (bitmap.isRecycled()) {
            throw new RuntimeException("Bitmap is Recycled");
        }

        //isMutable 必须是true
        if (!bitmap.isMutable()) {
            bitmap.recycle();
            return;
        }

        int size = getSize(bitmap);
        if (size >= maxSize()) {
            bitmap.recycle();
            return;
        }

        put(size, bitmap);
        map.put(size, 0);
    }

    @Override
    public Bitmap get(int width, int height, Bitmap.Config config) {
        //新Bitmap一个内存大小
        int size = width * height * (config == Bitmap.Config.ARGB_8888 ? 4 : 2);
        //获取等于size或者大于size 的key
        Integer key = map.ceilingKey(size);
        if (null != key && key <= size * MAX_OVER_SIZE) {
            isRemoved = true;
            Bitmap remove = remove(key);
            isRemoved = false;
            return remove;
        }
        return null;
    }
}
