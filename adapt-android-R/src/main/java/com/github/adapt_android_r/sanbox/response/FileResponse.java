package com.github.adapt_android_r.sanbox.response;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileResponse {
    private ContentResolver contentResolver;

    private boolean isSuccess;
    //    路径  ---》 uri  ---》 读写
    private Uri uri;
    private File file;


    private String ownerPackageName;//文件所属者,可通过所有者对文件进行擦欧洲哦

    public FileResponse(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public ParcelFileDescriptor open() throws FileNotFoundException {
        return contentResolver.openFileDescriptor(uri, "rw");
    }


    public String getOwnerPackageName() {
        return ownerPackageName;
    }

    public void setOwnerPackageName(String ownerPackageName) {
        this.ownerPackageName = ownerPackageName;
    }

    public OutputStream openOutputStream() throws FileNotFoundException {
        return contentResolver.openOutputStream(getUri());
    }

    public InputStream openInputStream() throws FileNotFoundException {
        return contentResolver.openInputStream(getUri());
    }

}
