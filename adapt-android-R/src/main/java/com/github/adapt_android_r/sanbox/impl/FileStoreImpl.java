package com.github.adapt_android_r.sanbox.impl;

import android.content.Context;

import com.github.adapt_android_r.sanbox.BaseRequest;
import com.github.adapt_android_r.sanbox.FileResponse;
import com.github.adapt_android_r.sanbox.IFile;

//Android11一下
public class FileStoreImpl implements IFile {
    @Override
    public <T extends BaseRequest> FileResponse newCreateFile(Context context, T baseRequest) {

        return null;
    }

    @Override
    public <T extends BaseRequest> FileResponse delete(Context context, T baseRequest) {
        return null;
    }

    @Override
    public <T extends BaseRequest> FileResponse renameTo(Context context, T where, T request) {
        return null;
    }

    @Override
    public <T extends BaseRequest> FileResponse copyFile(Context context, T baseRequest) {
        return null;
    }

    @Override
    public <T extends BaseRequest> FileResponse query(Context context, T baseRequest) {
        return null;
    }
}
