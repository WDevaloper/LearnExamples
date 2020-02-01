package com.gavin.asmdemo.designer_model.faced.impl;

import android.graphics.Bitmap;

import com.gavin.asmdemo.designer_model.faced.MemoryCache;

public class MemoryCacheImpl implements MemoryCache {
    @Override
    public Bitmap findMemory(String url) {
        System.out.println("内存");
        return null;
    }
}
