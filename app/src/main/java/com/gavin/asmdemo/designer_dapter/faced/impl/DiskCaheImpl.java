package com.gavin.asmdemo.designer_dapter.faced.impl;

import android.graphics.Bitmap;

import com.gavin.asmdemo.designer_dapter.faced.DiskCahe;

public class DiskCaheImpl implements DiskCahe {
    @Override
    public Bitmap findDisk(String url) {
        System.out.println("本地");
        return null;
    }
}
