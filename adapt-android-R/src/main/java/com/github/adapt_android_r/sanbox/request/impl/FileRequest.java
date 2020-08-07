package com.github.adapt_android_r.sanbox.request.impl;

import android.content.ContentValues;
import android.os.Environment;
import android.text.TextUtils;


import com.github.adapt_android_r.sanbox.request.BaseRequest;
import com.github.adapt_android_r.sanbox.uitls.UriTypeUtil;

import java.io.File;

public class FileRequest extends BaseRequest {
    public FileRequest(File file) {
        super(file);
    }

    //目录
    public String getPath() {
        if (!TextUtils.isEmpty(getRelativePath()) && UriTypeUtil.isAndroidQ()) {
            //相对路径 Download/Images
            return Environment.DIRECTORY_DOWNLOADS + "/" + getRelativePath();
        }
        //绝对路径  /sdcard/Download/Images
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + getRelativePath();
    }

    @Override
    public ContentValues getContentValues() {
        return super.getContentValues();
    }
}
