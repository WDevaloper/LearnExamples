package com.github.adapt_android_r.sanbox.request;

import android.content.ContentValues;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import androidx.annotation.CallSuper;


import com.github.adapt_android_r.sanbox.uitls.Util;

import java.io.File;

public abstract class BaseRequest {
    // Android10 相对路径
    private File file;
    // type   文件的类型
    private String uriType;
    private ContentValues contentValues = new ContentValues();

    private String displayName;// 文件名
    private String relativePath;//getPath 目录
    private String title;  //标题


    public BaseRequest(File file) {
        this.file = file;
        this.relativePath = file.getName();
    }

    public File getFile() {
        return file;
    }

    public void setRelativePath(String path) {
        this.relativePath = path;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * 无论如何改参数是比=必传参数，如果是AndroidQ 会根据文件名构造URI
     *
     * @param displayName 文件名
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && !Environment.isExternalStorageLegacy()) {
            //只有调用次方才能拿到URI地址
            Util.setFileType(this);
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setUriType(String uriType) {
        this.uriType = uriType;
    }

    public String getUriType() {
        return uriType;
    }

    // 返回的外置卡的公共目录   AndroidQ会返回相对目录  AndroidQ以下返回的是绝对路径
    public abstract String getPath();

    /**
     * 一般只有Android Q的时候需要构造ContentValues
     *
     * @return ContentValues
     */
    @CallSuper
    public ContentValues getContentValues() {
        if (!TextUtils.isEmpty(displayName)) {
            contentValues.put(MediaStore.Downloads.DISPLAY_NAME, getDisplayName());
        }
        if (!TextUtils.isEmpty(relativePath) &&
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q &&
                !Environment.isExternalStorageLegacy()) {
            contentValues.put(MediaStore.Downloads.RELATIVE_PATH, getPath());
        }
        if (!TextUtils.isEmpty(title)) {
            contentValues.put(MediaStore.Downloads.TITLE, getTitle());
        }
        return contentValues;
    }
}
