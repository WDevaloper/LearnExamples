package com.github.adapt_android_r.sanbox.file;

import android.content.Context;

import com.github.adapt_android_r.sanbox.request.BaseRequest;
import com.github.adapt_android_r.sanbox.response.FileResponse;

public interface IFile {
    <T extends BaseRequest> FileResponse newCreateFile(Context context, T baseRequest);
    <T extends  BaseRequest> FileResponse delete(Context context, T baseRequest);
    <T extends  BaseRequest> FileResponse renameTo(Context context, T where, T request);
    <T extends  BaseRequest> FileResponse copyFile(Context context, T baseRequest);
    <T extends  BaseRequest> FileResponse query(Context context, T baseRequest);
}
