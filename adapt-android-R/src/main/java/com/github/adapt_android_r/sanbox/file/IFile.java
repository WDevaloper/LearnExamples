package com.github.adapt_android_r.sanbox.file;

import android.app.RecoverableSecurityException;
import android.content.Context;

import com.github.adapt_android_r.sanbox.request.BaseRequest;
import com.github.adapt_android_r.sanbox.response.FileResponse;

public interface IFile {
    <T extends BaseRequest> FileResponse newCreateFile(Context context, T baseRequest);

    <T extends BaseRequest> FileResponse delete(Context context, T baseRequest) throws RuntimeException;

    <T extends BaseRequest> void delete(Context context, T baseRequest, FileCallback callback) throws RuntimeException;

    <T extends BaseRequest> FileResponse renameTo(Context context, T srcRequest, T destRequest) throws RuntimeException;

    <T extends BaseRequest> FileResponse renameTo(Context context, T wrapperRequest) throws RuntimeException;

    <T extends BaseRequest> FileResponse copyFile(Context context, T wrapperRequest);

    <T extends BaseRequest> FileResponse copyFile(Context context, T srcRequest, T destRequest);

    <T extends BaseRequest> FileResponse query(Context context, T baseRequest);
}
