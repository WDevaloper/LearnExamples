package com.github.adapt_android_r.sanbox;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import java.io.File;
import java.io.FileNotFoundException;


public class FileResponse {
    protected ContentResolver contentResolver;
    private boolean isSuccess;
    private Uri uri;
    private File file;

    public FileResponse(Uri uri, ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
        this.uri = uri;
    }

    public ParcelFileDescriptor open() throws FileNotFoundException {
        return contentResolver.openFileDescriptor(uri, "rw");
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Uri getUri() {
        return uri;
    }
}
