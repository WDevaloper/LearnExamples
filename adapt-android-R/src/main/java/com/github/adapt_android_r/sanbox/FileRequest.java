package com.github.adapt_android_r.sanbox;

import android.os.Environment;
import android.provider.MediaStore;


import com.github.adapt_android_r.sanbox.annotion.DbField;

import java.io.File;

public class FileRequest extends BaseRequest {
    //    不需要     字段不一致
    @DbField(MediaStore.Downloads.DISPLAY_NAME)
    private String displayName;
    @DbField(MediaStore.Downloads.RELATIVE_PATH)
    private String path;//getPath
    @DbField(MediaStore.Downloads.TITLE)
    private String title;


    public FileRequest(File file) {
        super(file);
        this.path = file.getName();
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPath() {
        return Environment.DIRECTORY_DOWNLOADS +"/"+path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
