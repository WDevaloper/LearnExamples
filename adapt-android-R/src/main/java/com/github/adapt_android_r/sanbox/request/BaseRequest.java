package com.github.adapt_android_r.sanbox.request;

import android.content.ContentValues;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import androidx.annotation.CallSuper;


import com.github.adapt_android_r.sanbox.uitls.MimeTypeUtils;
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
    //image  video
    public String mimeType;
    private String ownerPackageName;//文件所属者


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

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    /**
     * 无论如何改参数是比=必传参数，如果是AndroidQ 会根据文件名构造URI
     *
     * @param displayName 文件名
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
        mimeType = MimeTypeUtils.getMimeType(getDisplayName());
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

    //  在数据库中是这样的Download/ExternalScopeTest/
    //  所以想通过相对路径查询sql： and relativePath = xxxx你可以使用 %LIKE%
    // 要不然会影响你的查询和删除，如果你只使用display_name 将会删除在Download的所有display_name相同的文件
    //  数据库 external.db
    public String getRelativePath() {
        if (relativePath.endsWith(File.separator)) {
            return relativePath;
        }
        return relativePath + File.separator;
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
        if (!TextUtils.isEmpty(relativePath) && Util.isAndroidQ()) {
            contentValues.put(MediaStore.Downloads.RELATIVE_PATH, getPath());
        }

        if (!TextUtils.isEmpty(title)) {
            contentValues.put(MediaStore.Downloads.TITLE, getTitle());
        }
        return contentValues;
    }
}
