package com.github.adapt_android_r.sanbox.request.impl;

import android.content.ContentValues;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;


import com.github.adapt_android_r.sanbox.request.BaseRequest;
import com.github.adapt_android_r.sanbox.uitls.Util;

import java.io.File;

public class ImageRequest extends BaseRequest {

    public ImageRequest(File file) {
        super(file);
    }

    //目录
    @Override
    public String getPath() {
        if (!TextUtils.isEmpty(getRelativePath()) && Util.isAndroidQ()) {
            //相对路径 Pictures/Images
            return Environment.DIRECTORY_PICTURES + File.separator + getRelativePath();
        }
        //绝对路径  /sdcard/Pictures/Images
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + getRelativePath();
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues contentValues = super.getContentValues();
        if (!TextUtils.isEmpty(getMimeType())) {
            contentValues.put(MediaStore.Images.ImageColumns.MIME_TYPE, getMimeType());
        }
        return contentValues;
    }
}
